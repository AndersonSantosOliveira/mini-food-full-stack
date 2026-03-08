import { createRouter, createWebHistory } from "vue-router";

import HomeView from "../views/HomeView.vue";
import ProdutosView from "../views/ProdutosView.vue";
import PedidosView from "../views/PedidosView.vue";
import AboutView from "../views/AboutView.vue";

const router = createRouter({
  history: createWebHistory(),
  routes: [
    { path: "/", name: "home", component: HomeView },
    { path: "/produtos", name: "produtos", component: ProdutosView },
    { path: "/pedidos", name: "pedidos", component: PedidosView },
    { path: "/about", name: "about", component: AboutView },
  ],
});

export default router;
