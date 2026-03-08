<template>
  <div class="layout">
    <header class="topbar">
      <div class="brand">
        <div class="logo">MF</div>
        <div>
          <div class="title">MiniFood</div>
          <div class="subtitle">Painel de Gestão</div>
        </div>
      </div>

      <nav class="nav">
        <RouterLink class="link" to="/">Restaurantes</RouterLink>
        <RouterLink class="link" to="/produtos">Produtos</RouterLink>
        <RouterLink class="link" to="/pedidos">Pedidos</RouterLink>
        <RouterLink class="link" to="/about">Sobre</RouterLink>
      </nav>
    </header>

    <main class="content">
      <RouterView />
    </main>

    <footer class="footer">
      MiniFood • Vue + Spring Boot + MySQL
    </footer>
  </div>
</template>

<script setup>
import { RouterLink, RouterView } from "vue-router";
</script>

<style>
:root{
  --bg: #0b0f14;
  --panel: #111827;
  --card: #0f172a;
  --muted: #94a3b8;
  --text: #e5e7eb;
  --brand: #22c55e;
  --brand2: #16a34a;
  --danger: #ef4444;
  --border: rgba(255,255,255,.08);
}

*{ box-sizing: border-box; }
body{
  margin:0;
  font-family: system-ui, -apple-system, Segoe UI, Roboto, Arial, sans-serif;
  background: radial-gradient(1200px 600px at 20% -10%, rgba(34,197,94,.18), transparent 60%),
              radial-gradient(900px 500px at 90% 10%, rgba(59,130,246,.12), transparent 55%),
              var(--bg);
  color: var(--text);
}

.layout{ min-height: 100vh; display:flex; flex-direction:column; }

.topbar{
  display:flex;
  align-items:center;
  justify-content:space-between;
  padding: 14px 18px;
  border-bottom: 1px solid var(--border);
  background: rgba(17, 24, 39, .75);
  backdrop-filter: blur(10px);
  position: sticky;
  top: 0;
  z-index: 10;
}

.brand{ display:flex; gap:12px; align-items:center; }
.logo{
  width:40px;height:40px;border-radius:12px;
  display:grid;place-items:center;
  background: linear-gradient(135deg, var(--brand), var(--brand2));
  font-weight:800;
}
.title{ font-weight:800; letter-spacing:.3px; }
.subtitle{ font-size:12px; color: var(--muted); margin-top:2px; }

.nav{ display:flex; gap:10px; flex-wrap:wrap; }
.link{
  text-decoration:none;
  color: var(--text);
  font-weight:600;
  padding: 8px 10px;
  border-radius: 12px;
  border: 1px solid transparent;
}
.link.router-link-active{
  border-color: rgba(34,197,94,.35);
  background: rgba(34,197,94,.12);
}

.content{ width: min(1100px, 92vw); margin: 18px auto; flex:1; }
.footer{ border-top: 1px solid var(--border); padding: 14px 18px; color: var(--muted); text-align:center; }
</style>