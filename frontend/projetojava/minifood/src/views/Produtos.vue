<script setup>
import { ref, onMounted } from 'vue'

const produtos = ref([])
const restaurantes = ref([])
const novo = ref({ nome: '', descricao: '', preco: 0, urlImagem: '', restaurante: { id: null } })
const imagemPlaceholder = "https://images.unsplash.com/photo-1544025162-d76694265947?q=80&w=500&auto=format&fit=crop"

const carregarDados = async () => {
  const [resProd, resRest] = await Promise.all([
    fetch('http://localhost:8080/produtos'),
    fetch('http://localhost:8080/restaurante')
  ])
  produtos.value = await resProd.json()
  restaurantes.value = await resRest.json()
}

const salvar = async () => {
  if(!novo.value.restaurante.id) return alert("Selecione um restaurante!")
  await fetch('http://localhost:8080/produtos', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(novo.value)
  })
  novo.value = { nome: '', descricao: '', preco: 0, urlImagem: '', restaurante: { id: null } }
  carregarDados()
}

onMounted(carregarDados)
</script>

<template>
  <div class="space-y-12">
    <h1 class="text-4xl font-black text-white italic tracking-tighter">GESTÃO DE <span class="text-orange-500">CARDÁPIO</span></h1>

    <!-- FORMULÁRIO DE CADASTRO -->
    <div class="glass-card p-8 rounded-[2.5rem] border-orange-500/10">
      <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
        <input v-model="novo.nome" placeholder="Nome do prato" class="bg-white/5 border border-white/10 p-4 rounded-2xl text-white outline-none focus:border-orange-500">
        <input v-model="novo.urlImagem" placeholder="Link da imagem (URL)" class="bg-white/5 border border-white/10 p-4 rounded-2xl text-white outline-none focus:border-orange-500">
        <textarea v-model="novo.descricao" placeholder="Descrição detalhada..." class="md:col-span-2 bg-white/5 border border-white/10 p-4 rounded-2xl text-white h-24 outline-none focus:border-orange-500"></textarea>
        <input v-model.number="novo.preco" type="number" placeholder="Preço (Ex: 89.90)" class="bg-white/5 border border-white/10 p-4 rounded-2xl text-white outline-none focus:border-orange-500">
        <select v-model="novo.restaurante.id" class="bg-[#1a1f2e] text-white p-4 rounded-2xl border border-white/10">
          <option :value="null">Vincular ao Restaurante</option>
          <option v-for="r in restaurantes" :key="r.id" :value="r.id">{{ r.nome }}</option>
        </select>
      </div>
      <button @click="salvar" class="w-full mt-6 btn-order py-4 uppercase font-black tracking-[0.2em]">Cadastrar no Sistema</button>
    </div>

    <!-- LISTAGEM COM MINIATURA -->
    <div class="grid grid-cols-1 md:grid-cols-3 gap-6">
      <div v-for="p in produtos" :key="p.id" class="glass-card overflow-hidden rounded-[2rem] flex flex-col">
        <div class="h-40 bg-white/5 relative">
          <img :src="p.urlImagem || imagemPlaceholder" class="w-full h-full object-cover opacity-60">
          <div class="absolute inset-0 bg-gradient-to-t from-[#0b0f1a] to-transparent"></div>
          <span class="absolute bottom-4 left-4 text-[10px] font-black text-orange-500 uppercase">{{ p.restaurante?.nome }}</span>
        </div>
        <div class="p-5">
          <h3 class="text-lg font-bold text-white">{{ p.nome }}</h3>
          <p class="text-emerald-400 font-black">R$ {{ p.preco.toFixed(2) }}</p>
        </div>
      </div>
    </div>
  </div>
</template>