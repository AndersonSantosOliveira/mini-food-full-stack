<script setup>
import { ref, onMounted, computed } from 'vue'

const produtosDisponiveis = ref([])
const carrinho = ref([])
const cliente = ref('')
const carregando = ref(false)

// Função para buscar os produtos do seu backend Java
const carregar = async () => {
  try {
    const resProd = await fetch('http://localhost:8080/produtos')
    const dados = await resProd.json()
    // Aqui garantimos que o Vue veja a lista de produtos da sua tabela
    produtosDisponiveis.value = dados 
  } catch (e) { 
    console.error("Erro ao conectar com o servidor Java.", e) 
  }
}

const adicionarAoCarrinho = (produto) => {
  const itemExistente = carrinho.value.find(item => item.id === produto.id)
  if (itemExistente) {
    itemExistente.quantidade++
  } else {
    carrinho.value.push({ ...produto, quantidade: 1 })
  }
}

const removerDoCarrinho = (produtoId) => {
  const itemExistente = carrinho.value.find(item => item.id === produtoId)
  if (itemExistente) {
    if (itemExistente.quantidade > 1) {
      itemExistente.quantidade--
    } else {
      carrinho.value = carrinho.value.filter(item => item.id !== produtoId)
    }
  }
}

const totalCarrinho = computed(() => {
  return carrinho.value.reduce((acc, item) => acc + (item.preco * item.quantidade), 0)
})

// FUNÇÃO CORRIGIDA PARA O SEU BACKEND JAVA
const finalizarPedido = async () => {
  if (!cliente.value || carrinho.value.length === 0) {
    alert("Por favor, informe seu nome e adicione itens ao carrinho.")
    return
  }
  
  carregando.value = true
  
  // 1. Criamos a lista de produtos repetindo o ID conforme a quantidade
  const listaProdutosParaJava = []
  carrinho.value.forEach(item => {
    for (let i = 0; i < item.quantidade; i++) {
      listaProdutosParaJava.push({ id: item.id })
    }
  })

  // 2. IMPORTANTE: Pegamos o ID do restaurante que está dentro do produto
  // Se o seu banco estiver correto, p.restaurante.id existe.
  const restauranteId = carrinho.value[0].restaurante?.id

  if (!restauranteId) {
    alert("Erro: Este produto não tem um restaurante vinculado no banco de dados!")
    carregando.value = false
    return
  }

  // 3. Montamos o JSON exatamente como o seu PedidosService.java espera
  const payload = { 
    customer: cliente.value, 
    produtos: listaProdutosParaJava, 
    restaurante: { id: restauranteId } 
  }

  try {
    const response = await fetch('http://localhost:8080/pedido', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(payload)
    })
    
    if (response.ok) {
      alert(`Pedido de ${cliente.value} enviado com sucesso!`)
      cliente.value = ''
      carrinho.value = []
      await carregar() // Atualiza a lista se necessário
    } else {
      const erroTexto = await response.text()
      console.error("Erro do Java:", erroTexto)
      alert("O servidor Java recusou o pedido. Verifique o console do IntelliJ.")
    }
  } catch (e) { 
    console.error("Erro de rede:", e)
    alert("Erro ao conectar no Backend. O projeto Java está rodando?") 
  } finally { 
    carregando.value = false 
  }
}

onMounted(carregar)
</script>

<template>
  <div class="grid grid-cols-1 lg:grid-cols-12 gap-8 p-4">
    <div class="lg:col-span-8 space-y-6">
      <h2 class="text-3xl font-black text-white italic uppercase">Cardápio <span class="text-orange-500">Digital</span></h2>
      
      <div class="grid grid-cols-1 md:grid-cols-2 gap-8">
        <div v-for="p in produtosDisponiveis" :key="p.id" class="flex flex-col group">
          
          <div class="mb-2">
            <span class="bg-emerald-600 text-white text-[10px] font-black px-4 py-1.5 rounded-lg uppercase tracking-widest shadow-lg">
              {{ p.restaurante?.nome || 'Churrascaria Ouro Verde' }}
            </span>
          </div>

          <div class="glass-card overflow-hidden flex flex-col rounded-[2.5rem] border border-white/5 hover:border-orange-500/40 transition-all bg-slate-900">
            <div class="h-48 overflow-hidden bg-slate-800">
              <img :src="p.urlImagem" 
                   class="w-full h-full object-cover group-hover:scale-105 transition-transform duration-500"
                   alt="Imagem do prato">
            </div>

            <div class="p-6 flex flex-col justify-between flex-grow">
              <div>
                <h3 class="text-xl font-bold text-white mb-1">{{ p.nome }}</h3>
                <p class="text-slate-500 text-xs line-clamp-2 mb-4">{{ p.descricao }}</p>
              </div>
              
              <div class="flex justify-between items-center mt-auto">
                <span class="text-emerald-400 font-black text-xl">R$ {{ p.preco.toFixed(2) }}</span>
                <button @click="adicionarAoCarrinho(p)" class="w-12 h-12 bg-orange-500 text-white rounded-2xl shadow-lg hover:bg-orange-600 transition-all font-bold text-2xl">+</button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <div class="lg:col-span-4">
      <div class="bg-[#0b0f1a] border border-orange-500/20 p-6 rounded-[2.5rem] sticky top-24 shadow-2xl">
        <h2 class="text-xl font-black text-white italic mb-6 uppercase tracking-tighter">Meu Pedido</h2>
        <input v-model="cliente" placeholder="Informe seu nome" class="w-full bg-white/5 border border-white/10 p-4 rounded-2xl text-white mb-6 outline-none focus:border-orange-500 transition-all">
        
        <div class="max-h-60 overflow-y-auto space-y-3 mb-6 pr-2">
          <div v-for="item in carrinho" :key="item.id" class="flex justify-between items-center bg-white/5 p-4 rounded-2xl border border-white/5">
            <div class="flex flex-col">
              <span class="text-xs font-bold text-white">{{ item.nome }}</span>
              <span class="text-[10px] text-emerald-400 font-bold">R$ {{ (item.preco * item.quantidade).toFixed(2) }}</span>
            </div>
            
            <div class="flex items-center gap-3 bg-black/40 p-2 rounded-xl border border-white/10">
              <button @click="removerDoCarrinho(item.id)" class="text-orange-500 hover:text-white px-2 font-black text-xl">-</button>
              <span class="text-white text-xs font-bold">{{ item.quantidade }}</span>
              <button @click="adicionarAoCarrinho(item)" class="text-orange-500 hover:text-white px-2 font-black text-xl">+</button>
            </div>
          </div>
        </div>

        <div class="border-t border-white/10 pt-6">
          <div class="flex justify-between items-end mb-6">
            <span class="text-slate-500 font-black text-xs uppercase tracking-widest">Total</span>
            <span class="text-3xl font-black text-white italic tracking-tighter">R$ {{ totalCarrinho.toFixed(2) }}</span>
          </div>
          
          <button @click="finalizarPedido" :disabled="!cliente || carrinho.length === 0 || carregando" 
            class="w-full py-5 bg-orange-500 hover:bg-orange-600 shadow-xl shadow-orange-500/30 text-white font-black rounded-3xl transition-all uppercase tracking-widest text-sm">
            {{ carregando ? 'Processando...' : 'Confirmar Pedido' }}
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.glass-card {
  background: rgba(255, 255, 255, 0.03);
  backdrop-filter: blur(10px);
}
</style>