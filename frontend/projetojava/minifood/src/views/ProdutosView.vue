<template>
  <section class="page">
    <header class="page-header">
      <h2>Produtos</h2>
      <p class="muted">Cadastre, edite e remova produtos.</p>
    </header>

    <div class="grid">
      <!-- FORM -->
      <div class="card">
        <h3>Novo produto</h3>

        <form @submit.prevent="salvar">
          <label>Nome</label>
          <input v-model="form.nome" placeholder="Ex.: Picanha" />

          <label>Descrição</label>
          <input v-model="form.descricao" placeholder="Ex.: Churrasco de Picanha " />

          <label>Preço</label>
          <input v-model.number="form.preco" type="number" step="0.01" />

          <label>Restaurante</label>
          <select v-model.number="form.restauranteId">
            <option value="">Selecione...</option>
            <option v-for="r in restaurantes" :key="r.id" :value="r.id">
              {{ r.nome }}
            </option>
          </select>

          <button class="btn" :disabled="loading">
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
          <div v-for="p in produtos" :key="p.id" class="item">
            <div class="item-main">
              <!-- VISUAL -->
              <template v-if="editId !== p.id">
                <div class="item-header">
                  <div class="item-title">{{ p.nome }}</div>
                  <span class="badge">
                    {{ formatarPreco(p.preco) }}
                  </span>
                </div>

                <dl class="meta">
                  <div class="meta-row">
                    <dt>Descrição</dt>
                    <dd>{{ p.descricao || "—" }}</dd>
                  </div>

                  <div class="meta-row">
                    <dt>Restaurante</dt>
                    <dd>{{ p.restaurante?.nome || "Sem restaurante" }}</dd>
                  </div>
                </dl>
              </template>

              <!-- EDIÇÃO -->
              <template v-else>
                <div class="edit-grid">
                  <div class="field">
                    <label class="mini-label">Nome</label>
                    <input v-model="editForm.nome" />
                  </div>

                  <div class="field">
                    <label class="mini-label">Descrição</label>
                    <input v-model="editForm.descricao" />
                  </div>

                  <div class="field">
                    <label class="mini-label">Preço</label>
                    <input v-model.number="editForm.preco" type="number" step="0.01" />
                  </div>

                  <div class="field">
                    <label class="mini-label">Restaurante</label>
                    <select v-model.number="editForm.restauranteId">
                      <option value="">Selecione...</option>
                      <option v-for="r in restaurantes" :key="r.id" :value="r.id">
                        {{ r.nome }}
                      </option>
                    </select>
                  </div>
                </div>
              </template>
            </div>

            <div class="item-actions">
              <button v-if="editId !== p.id" class="btn ghost" @click="iniciarEdicao(p)">
                Editar
              </button>

              <button v-else class="btn" @click="confirmarEdicao(p)">
                Salvar
              </button>

              <button v-if="editId === p.id" class="btn ghost" @click="cancelarEdicao">
                Cancelar
              </button>

              <button class="btn danger" @click="remover(p.id)">
                Excluir
              </button>
            </div>
          </div>

          <p v-if="produtos.length === 0" class="muted">
            Nenhum produto cadastrado.
          </p>
        </div>
      </div>
    </div>
  </section>
</template>

<script>
import {
  listarProdutos,
  criarProduto,
  atualizarProduto,
  deletarProduto,
  listarRestaurantes,
} from "../api.js";

export default {
  data() {
    return {
      produtos: [],
      restaurantes: [],

      form: { nome: "", descricao: "", preco: null, restauranteId: "" },
      editForm: { nome: "", descricao: "", preco: null, restauranteId: "" },

      editId: null,
      erro: null,
      loading: false,
      carregando: false,
    };
  },

  async mounted() {
    await Promise.all([this.carregarRestaurantes(), this.carregarProdutos()]);
  },

  methods: {
    formatarPreco(valor) {
      return new Intl.NumberFormat("pt-BR", {
        style: "currency",
        currency: "BRL",
      }).format(valor || 0);
    },

    async carregarRestaurantes() {
      const res = await listarRestaurantes();
      this.restaurantes = res.data;
    },

    async carregarProdutos() {
      this.carregando = true;
      try {
        const res = await listarProdutos();
        this.produtos = res.data;
      } finally {
        this.carregando = false;
      }
    },

    validarProduto(obj) {
      if (!obj.nome?.trim()) return "Digite o nome.";
      if (!obj.descricao?.trim()) return "Digite a descrição.";
      if (!obj.preco && obj.preco !== 0) return "Digite o preço.";
      if (!obj.restauranteId) return "Selecione o restaurante.";
      return null;
    },

    async salvar() {
      const msg = this.validarProduto(this.form);
      if (msg) return (this.erro = msg);

      this.loading = true;

      await criarProduto({
        nome: this.form.nome,
        descricao: this.form.descricao,
        preco: this.form.preco,
        restaurante: { id: this.form.restauranteId },
      });

      this.form = { nome: "", descricao: "", preco: null, restauranteId: "" };
      this.loading = false;
      this.carregarProdutos();
    },

    iniciarEdicao(p) {
      this.editId = p.id;
      this.editForm = {
        nome: p.nome,
        descricao: p.descricao,
        preco: p.preco,
        restauranteId: p.restaurante?.id,
      };
    },

    cancelarEdicao() {
      this.editId = null;
    },

    async confirmarEdicao(p) {
      await atualizarProduto(p.id, {
        id: p.id,
        nome: this.editForm.nome,
        descricao: this.editForm.descricao,
        preco: this.editForm.preco,
        restaurante: { id: this.editForm.restauranteId },
      });

      this.cancelarEdicao();
      this.carregarProdutos();
    },

    async remover(id) {
      if (!confirm("Excluir produto?")) return;
      await deletarProduto(id);
      this.carregarProdutos();
    },
  },
};
</script>

<style scoped>
.page-header h2 { margin: 0 0 4px; }
.muted { color: #a7a7a7; }

.grid {
  display: grid;
  grid-template-columns: 1fr 1.6fr;
  gap: 16px;
}

.card {
  background: #171a21;
  border: 1px solid #2a2f3a;
  border-radius: 16px;
  padding: 16px;
}

input, select {
  width: 100%;
  padding: 10px;
  border-radius: 12px;
  border: 1px solid #2a2f3a;
  background: #0f1115;
  color: #e6e6e6;
  margin-bottom: 10px;
}

.btn {
  padding: 10px 12px;
  border-radius: 12px;
  border: 1px solid rgba(79,124,255,.35);
  background: rgba(79,124,255,.18);
  color: #fff;
  cursor: pointer;
}

.btn.ghost { background: transparent; border-color: #2a2f3a; }
.btn.danger { background: rgba(255,79,79,.14); border-color: rgba(255,79,79,.35); }

.list { display: flex; flex-direction: column; gap: 10px; }

.item {
  display: flex;
  justify-content: space-between;
  padding: 12px;
  border-radius: 14px;
  border: 1px solid #2a2f3a;
  background: rgba(255,255,255,.03);
}

.item-main { width: 100%; }

.item-header {
  display: flex;
  justify-content: space-between;
}

.item-title { font-weight: 800; }

.badge {
  padding: 4px 8px;
  border-radius: 999px;
  background: rgba(79,124,255,.15);
  border: 1px solid rgba(79,124,255,.35);
}

.meta { margin-top: 8px; display: grid; gap: 6px; }

.meta-row {
  display: grid;
  grid-template-columns: 100px 1fr;
}

.edit-grid { display: grid; gap: 10px; }

.error { color: #ff7a7a; }
</style>