<script setup>
import { ref, onMounted } from 'vue'

const restaurantes = ref([])

const carregarRestaurantes = async () => {
  try {
    const response = await fetch('http://localhost:8080/restaurante')
    restaurantes.value = await response.json()
  } catch (error) {
    console.error("Erro ao conectar com o backend Java:", error)
  }
}

onMounted(carregarRestaurantes)
</script>

<template>
  <div class="space-y-8">
    <h1 class="text-4xl font-black text-white italic tracking-tighter">NOSSAS <span class="text-orange-500">UNIDADES</span></h1>
    
    <div class="grid grid-cols-1 md:grid-cols-3 gap-6">
      <div v-for="res in restaurantes" :key="res.id" 
        class="glass-card p-8 rounded-[2.5rem] group">
        <div class="flex items-start justify-between mb-6">
          <div class="w-16 h-16 bg-gradient-to-br from-orange-500 to-red-600 rounded-2xl flex items-center justify-center text-2xl font-black text-white shadow-lg shadow-orange-500/20">
            {{ res.nome?.charAt(0) }}
          </div>
          <span class="text-[10px] bg-orange-500/10 text-orange-500 px-3 py-1 rounded-full font-black uppercase tracking-widest border border-orange-500/20">
            {{ res.tipoComidas }}
          </span>
        </div>
        
        <h2 class="text-2xl font-bold text-white mb-2 group-hover:text-orange-500 transition-colors">{{ res.nome }}</h2>
        <p class="text-slate-500 text-sm mb-8 leading-relaxed italic">{{ res.email }}</p>

        <button class="btn-order w-full">Ver Cardápio</button>
      </div>
    </div>

    <div v-if="restaurantes.length === 0" class="text-center py-20 bg-white/5 rounded-[3rem] border border-dashed border-white/10">
      <p class="text-slate-500 italic">Aguardando conexão com o servidor Java (Porta 8080)...</p>
    </div>
  </div>
</template>