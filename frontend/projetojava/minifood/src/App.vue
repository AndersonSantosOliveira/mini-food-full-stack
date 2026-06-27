<script setup>
import { ref } from 'vue'
// Importação dos componentes que você já tem na pasta views
import Produtos from './views/Produtos.vue'
import Restaurante from './views/Restaurante.vue'
import PedidosView from './views/PedidosView.vue'

// Estado para controlar qual tela mostrar
const abaAtiva = ref('pedidos') // Começa na tela de fazer pedidos
</script>

<template>
  <div class="min-h-screen bg-[#05070a] text-slate-200">
    
    <!-- NAVBAR (Usando suas cores e Tailwind v4) -->
    <nav class="sticky top-0 z-[100] bg-[#05070a]/80 backdrop-blur-xl border-b border-white/5 px-8 py-4">
      <div class="max-w-7xl mx-auto flex justify-between items-center">
        <div class="flex items-center gap-10">
          <h1 class="text-2xl font-black italic text-transparent bg-clip-text bg-gradient-to-r from-orange-400 to-red-600 tracking-tighter">
            MINI-FOOD
          </h1>
          
          <!-- Menu de Navegação -->
          <div class="hidden md:flex gap-2 bg-white/5 p-1 rounded-2xl border border-white/5">
            <button 
              v-for="aba in ['pedidos', 'produtos', 'restaurante']" 
              :key="aba"
              @click="abaAtiva = aba"
              :class="abaAtiva === aba ? 'bg-orange-600 text-white shadow-lg shadow-orange-500/20' : 'text-slate-400 hover:bg-white/5'"
              class="px-6 py-2 rounded-xl text-xs font-black uppercase tracking-widest transition-all duration-300"
            >
              {{ aba }}
            </button>
          </div>
        </div>

        <div class="flex items-center gap-4 text-[10px] font-bold text-slate-500">
          <span class="px-3 py-1 border border-white/10 rounded-full">SISTEMA ATIVO</span>
          <button class="hover:text-white transition-colors">SAIR</button>
        </div>
      </div>
    </nav>

    <!-- ÁREA DE CONTEÚDO COM TRANSIÇÃO SUAVE -->
    <main class="p-8 max-w-7xl mx-auto">
      <Transition name="fade" mode="out-in">
        
        <!-- Renderização Condicional das Telas -->
        <div v-if="abaAtiva === 'pedidos'" key="pedidos">
          <PedidosView />
        </div>

        <div v-else-if="abaAtiva === 'produtos'" key="produtos">
          <Produtos />
        </div>

        <div v-else-if="abaAtiva === 'restaurante'" key="restaurante">
          <Restaurante />
        </div>

      </Transition>
    </main>

    <!-- RODAPÉ DISCRETO -->
    <footer class="p-8 text-center border-t border-white/5 opacity-30">
      <p class="text-[10px] font-bold uppercase tracking-[0.5em]">Gerenciamento Mini-Food © 2026</p>
    </footer>
  </div>
</template>

<style>
/* Animação de troca de tela */
.fade-enter-active, .fade-leave-active {
  transition: opacity 0.3s ease, transform 0.3s ease;
}
.fade-enter-from {
  opacity: 0;
  transform: translateY(10px);
}
.fade-leave-to {
  opacity: 0;
  transform: translateY(-10px);
}
</style>