import axios from "axios";

const api = axios.create({
  baseURL: "/api",
  headers: { "Content-Type": "application/json" },
});

// ===== RESTAURANTES =====
export const listarRestaurantes = () => api.get("/restaurante");
export const criarRestaurante = (data) => api.post("/restaurante", data);
export const atualizarRestaurante = (id, data) => api.put(`/restaurante/${id}`, data);
export const deletarRestaurante = (id) => api.delete(`/restaurante/${id}`);

// ===== PRODUTOS =====
export const listarProdutos = () => api.get("/produtos");
export const criarProduto = (data) => api.post("/produtos", data);
export const atualizarProduto = (id, data) => api.put(`/produtos/${id}`, data);
export const deletarProduto = (id) => api.delete(`/produtos/${id}`);

// ===== PEDIDOS =====
export const listarPedidos = () => api.get("/pedido");
export const criarPedido = (data) => api.post("/pedido", data);
export const atualizarPedido = (id, data) => api.put(`/pedido/${id}`, data);
export const deletarPedido = (id) => api.delete(`/pedido/${id}`);

export default api;