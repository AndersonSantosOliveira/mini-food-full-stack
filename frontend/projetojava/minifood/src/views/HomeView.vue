<template>
  <section class="page">
    <header class="page-header">
      <h2>Restaurantes</h2>
      <p class="muted">Cadastre, edite e remova restaurantes.</p>
    </header>

    <div class="grid">
      <!-- FORM -->
      <div class="card">
        <h3>Novo restaurante</h3>

        <form @submit.prevent="salvar">
          <label>Nome</label>
          <input v-model="form.nome" placeholder="Ex.: Churrascaria Aragão" />

          <label>Tipo de comidas</label>
          <input v-model="form.tipoComidas" placeholder="Ex.: Churrasco, Pizza, Japonesa" />

          <label>Email</label>
          <input v-model="form.email" type="email" placeholder="Ex.: contato@restaurante.com" />

          <button class="btn" type="submit" :disabled="loading">
            {{ loading ? "Salvando..." : "Salvar" }}
          </button>

          <p v-if="erro" class="error">{{ erro }}</p>
        </form>
      </div>

      <!-- LISTA -->
      <div class="card">
        <h3>Lista</h3>

        <p v-if="carregando" class="muted">Carregando...</p>

        <div v-else class="list">
          <div v-for="r in restaurantes" :key="r.id" class="item">
            <div class="item-main">
              <!-- MODO VISUAL -->
              <template v-if="editId !== r.id">
                <div class="item-header">
                  <div class="item-title">{{ r.nome }}</div>
                  <span class="badge">Restaurante</span>
                </div>

                <dl class="meta">
                  <div class="meta-row">
                    <dt>Tipo</dt>
                    <dd>{{ r.tipoComidas || "—" }}</dd>
                  </div>

                  <div class="meta-row">
                    <dt>Email</dt>
                    <dd class="mono">{{ r.email || "—" }}</dd>
                  </div>
                </dl>
              </template>

              <!-- MODO EDIÇÃO -->
              <template v-else>
                <div class="edit-grid">
                  <div class="field">
                    <label class="mini-label">Nome</label>
                    <input v-model="editForm.nome" placeholder="Nome" />
                  </div>

                  <div class="field">
                    <label class="mini-label">Tipo de comidas</label>
                    <input v-model="editForm.tipoComidas" placeholder="Tipo de comidas" />
                  </div>

                  <div class="field">
                    <label class="mini-label">Email</label>
                    <input v-model="editForm.email" type="email" placeholder="Email" />
                  </div>
                </div>
              </template>
            </div>

            <div class="item-actions">
              <button
                v-if="editId !== r.id"
                class="btn ghost"
                type="button"
                @click="iniciarEdicao(r)"
              >
                Editar
              </button>

              <button v-else class="btn" type="button" @click="confirmarEdicao(r)">
                Salvar
              </button>

              <button
                v-if="editId === r.id"
                class="btn ghost"
                type="button"
                @click="cancelarEdicao"
              >
                Cancelar
              </button>

              <button class="btn danger" type="button" @click="remover(r.id)">
                Excluir
              </button>
            </div>
          </div>

          <p v-if="restaurantes.length === 0" class="muted">Nenhum restaurante cadastrado.</p>
        </div>
      </div>
    </div>
  </section>
</template>

<script>
import {
  listarRestaurantes,
  criarRestaurante,
  atualizarRestaurante,
  deletarRestaurante,
} from "../api.js";

export default {
  data() {
    return {
      restaurantes: [],
      form: { nome: "", tipoComidas: "", email: "" },
      erro: null,
      loading: false,
      carregando: false,

      editId: null,
      editForm: { nome: "", tipoComidas: "", email: "" },
    };
  },

  async mounted() {
    await this.carregar();
  },

  methods: {
    async carregar() {
      this.carregando = true;
      this.erro = null;
      try {
        const res = await listarRestaurantes();
        this.restaurantes = res.data;
      } catch (e) {
        console.error(e);
        this.erro = "Falha ao carregar restaurantes.";
      } finally {
        this.carregando = false;
      }
    },

    validarRestaurante(obj) {
      if (!obj.nome?.trim()) return "Digite o nome.";
      if (!obj.tipoComidas?.trim()) return "Digite o tipo de comidas.";
      if (!obj.email?.trim()) return "Digite o email.";
      return null;
    },

    async salvar() {
      const msg = this.validarRestaurante(this.form);
      if (msg) {
        this.erro = msg;
        return;
      }

      this.loading = true;
      this.erro = null;

      try {
        await criarRestaurante({
          nome: this.form.nome.trim(),
          tipoComidas: this.form.tipoComidas.trim(),
          email: this.form.email.trim(),
        });

        this.form = { nome: "", tipoComidas: "", email: "" };
        await this.carregar();
      } catch (e) {
        console.error(e);
        this.erro = "Falha ao salvar restaurante.";
      } finally {
        this.loading = false;
      }
    },

    iniciarEdicao(r) {
      this.editId = r.id;
      this.editForm = {
        nome: r.nome ?? "",
        tipoComidas: r.tipoComidas ?? "",
        email: r.email ?? "",
      };
    },

    cancelarEdicao() {
      this.editId = null;
      this.editForm = { nome: "", tipoComidas: "", email: "" };
    },

    async confirmarEdicao(r) {
      const msg = this.validarRestaurante(this.editForm);
      if (msg) {
        this.erro = msg;
        return;
      }

      try {
        // manda o OBJETO COMPLETO pra não virar null no banco
        await atualizarRestaurante(r.id, {
          id: r.id,
          nome: this.editForm.nome.trim(),
          tipoComidas: this.editForm.tipoComidas.trim(),
          email: this.editForm.email.trim(),
        });

        this.cancelarEdicao();
        await this.carregar();
      } catch (e) {
        console.error(e);
        this.erro = "Falha ao atualizar restaurante.";
      }
    },

    async remover(id) {
      if (!confirm("Tem certeza que deseja excluir?")) return;
      try {
        await deletarRestaurante(id);
        await this.carregar();
      } catch (e) {
        console.error(e);
        this.erro = "Falha ao excluir restaurante.";
      }
    },
  },
};
</script>
<style scoped>
.page-header h2 {
  margin: 0 0 4px 0;
}

.muted {
  color: #a7a7a7;
}

.grid {
  display: grid;
  grid-template-columns: 1fr 1.6fr;
  gap: 16px;
}

@media (max-width: 900px) {
  .grid {
    grid-template-columns: 1fr;
  }
}

.card {
  background: #171a21;
  border: 1px solid #2a2f3a;
  border-radius: 16px;
  padding: 16px;
}

label {
  display: block;
  margin: 10px 0 6px;
  color: #cfcfcf;
  font-size: 14px;
}

input {
  width: 100%;
  padding: 10px;
  border-radius: 12px;
  border: 1px solid #2a2f3a;
  background: #0f1115;
  color: #e6e6e6;
  outline: none;
  margin-bottom: 10px;
}

input:focus {
  border-color: rgba(79, 124, 255, 0.5);
}

.btn {
  margin-top: 8px;
  padding: 10px 12px;
  border-radius: 12px;
  border: 1px solid rgba(79, 124, 255, 0.35);
  background: rgba(79, 124, 255, 0.18);
  color: #e6e6e6;
  cursor: pointer;
}

.btn:hover {
  filter: brightness(1.1);
}

.btn.ghost {
  background: transparent;
  border-color: #2a2f3a;
}

.btn.danger {
  background: rgba(255, 79, 79, 0.14);
  border-color: rgba(255, 79, 79, 0.35);
}

.list {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.item {
  display: flex;
  align-items: flex-start; /* melhor pra texto */
  justify-content: space-between;
  gap: 12px;
  padding: 12px;
  border-radius: 14px;
  border: 1px solid #2a2f3a;
  background: rgba(255, 255, 255, 0.03);
}

.item-main {
  display: flex;
  flex-direction: column;
  gap: 6px;
  width: 100%;
}

.item-actions {
  display: flex;
  gap: 8px;
  flex-wrap: wrap;
  justify-content: flex-end;
}

/* ---------- NOVO VISUAL COM RÓTULOS ---------- */
.item-header {
  display: flex;
  align-items: flex-start;
  justify-content: space-between;
  gap: 12px;
}

.item-title {
  font-weight: 800;
  font-size: 15px;
  line-height: 1.2;
}

.badge {
  font-size: 11px;
  padding: 4px 8px;
  border-radius: 999px;
  border: 1px solid rgba(79, 124, 255, 0.35);
  background: rgba(79, 124, 255, 0.1);
  color: #d7ddff;
  white-space: nowrap;
}

.meta {
  margin: 6px 0 0 0;
  display: grid;
  gap: 8px;
}

.meta-row {
  display: grid;
  grid-template-columns: 70px 1fr;
  gap: 10px;
  align-items: baseline;
}

.meta-row dt {
  opacity: 0.75;
  font-size: 12px;
  text-transform: uppercase;
  letter-spacing: 0.06em;
}

.meta-row dd {
  margin: 0;
  opacity: 0.95;
  font-size: 14px;
}

.mono {
  font-family: ui-monospace, SFMono-Regular, Menlo, Monaco, Consolas, "Liberation Mono",
    "Courier New", monospace;
  font-size: 13px;
}

/* ---------- EDIÇÃO MAIS ORGANIZADA ---------- */
.edit-grid {
  display: grid;
  gap: 10px;
}

.field input {
  margin-bottom: 0;
}

.mini-label {
  display: block;
  margin: 0 0 6px;
  font-size: 12px;
  color: #bdbdbd;
}

.error {
  color: #ff7a7a;
  margin-top: 10px;
}
</style>