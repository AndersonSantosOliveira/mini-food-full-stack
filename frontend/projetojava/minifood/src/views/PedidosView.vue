<template>
  <section class="page">
    <header class="page-header">
      <h2>Pedidos</h2>
      <p class="muted">Criar e listar pedidos.</p>
    </header>

    <div class="grid">
      <!-- FORM -->
      <div class="card">
        <h3>Novo pedido</h3>

        <form @submit.prevent="salvar">
          <label>Cliente</label>
          <input v-model="form.customer" placeholder="Ex.: Anderson" />

          <label>Restaurante</label>
          <select v-model.number="form.restauranteId">
            <option value="">Selecione...</option>
            <option v-for="r in restaurantes" :key="r.id" :value="r.id">
              {{ r.nome }}
            </option>
          </select>

          <label>Produtos</label>

          <div class="box">
            <p v-if="!form.restauranteId" class="muted">
              Selecione um restaurante para ver os produtos.
            </p>

            <p v-else-if="produtosFiltrados.length === 0" class="muted">
              Nenhum produto cadastrado para este restaurante.
            </p>

            <div v-else class="checks">
              <label v-for="p in produtosFiltrados" :key="p.id" class="check">
                <input type="checkbox" :value="p.id" v-model="form.produtosIds" />
                <span>{{ p.nome }} — {{ formatarPreco(p.preco) }}</span>
              </label>
            </div>
          </div>

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
          <div v-for="p in pedidos" :key="p.id" class="item">
            <div class="item-main">

              <div class="item-header">
                <div class="item-title">
                  Pedido #{{ p.id }} • {{ p.customer || "Cliente" }}
                </div>

                <span class="badge">
                  {{ p.produtos?.length || 0 }} item(s)
                </span>
              </div>

              <dl class="meta">
                <div class="meta-row">
                  <dt>Restaurante</dt>
                  <dd>{{ p.restaurante?.nome || "—" }}</dd>
                </div>

                <div class="meta-row" v-if="p.produtos?.length">
                  <dt>Produtos</dt>
                  <dd class="wrap">
                    {{ p.produtos.map(x => x.nome).join(", ") }}
                  </dd>
                </div>
              </dl>

            </div>

            <div class="item-actions">
              <button class="btn danger" @click="remover(p.id)">
                Excluir
              </button>
            </div>
          </div>

          <p v-if="pedidos.length === 0" class="muted">
            Nenhum pedido cadastrado.
          </p>
        </div>
      </div>
    </div>
  </section>
</template>

<script>
import {
  listarPedidos,
  criarPedido,
  deletarPedido,
  listarRestaurantes,
  listarProdutos,
} from "../api.js";

export default {
  data() {
    return {
      pedidos: [],
      restaurantes: [],
      produtos: [],

      form: {
        customer: "",
        restauranteId: "",
        produtosIds: [],
      },

      erro: null,
      loading: false,
      carregando: false,
    };
  },

  computed: {
    produtosFiltrados() {
      if (!this.form.restauranteId) return [];
      return this.produtos.filter(
        p => Number(p.restaurante?.id) === Number(this.form.restauranteId)
      );
    },
  },

  async mounted() {
    await Promise.all([
      this.carregarRestaurantes(),
      this.carregarProdutos(),
      this.carregarPedidos(),
    ]);
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
      const res = await listarProdutos();
      this.produtos = res.data;
    },

    async carregarPedidos() {
      this.carregando = true;
      const res = await listarPedidos();
      this.pedidos = res.data;
      this.carregando = false;
    },

    validarPedido() {
      if (!this.form.customer?.trim()) return "Digite o nome do cliente.";
      if (!this.form.restauranteId) return "Selecione um restaurante.";
      if (!this.form.produtosIds.length) return "Selecione ao menos 1 produto.";
      return null;
    },

    async salvar() {
      const msg = this.validarPedido();
      if (msg) return (this.erro = msg);

      this.loading = true;

      await criarPedido({
        customer: this.form.customer,
        restaurante: { id: this.form.restauranteId },
        produtos: this.form.produtosIds.map(id => ({ id })),
      });

      this.form = { customer: "", restauranteId: "", produtosIds: [] };
      this.loading = false;

      this.carregarPedidos();
    },

    async remover(id) {
      if (!confirm("Excluir pedido?")) return;
      await deletarPedido(id);
      this.carregarPedidos();
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

.box {
  border: 1px solid #2a2f3a;
  background: rgba(255,255,255,.03);
  border-radius: 14px;
  padding: 10px;
  margin-bottom: 10px;
}

.checks { display: flex; flex-direction: column; gap: 8px; }
.check { display: flex; gap: 10px; align-items: center; }

.btn {
  padding: 10px 12px;
  border-radius: 12px;
  border: 1px solid rgba(79,124,255,.35);
  background: rgba(79,124,255,.18);
  color: #fff;
  cursor: pointer;
}

.btn.danger {
  background: rgba(255,79,79,.14);
  border-color: rgba(255,79,79,.35);
}

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

.wrap { word-break: break-word; }

.error { color: #ff7a7a; }
</style>