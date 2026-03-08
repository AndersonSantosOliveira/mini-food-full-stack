<template>
  <div class="card" @click="$emit('select', restaurante)">
    <img :src="imagem" alt="Foto do restaurante" />
    <h3>{{ restaurante.nome }}</h3>
    <p>{{ restaurante.tipoComidas }}</p>
  </div>
</template>

<script>
export default {
  name: 'RestauranteCard',

  props: {
    restaurante: {
      type: Object,
      required: true
    }
  },

computed: {
  imagem() {
    return new URL('../assets/logo.svg', import.meta.url).href
    // ou: return 'https://via.placeholder.com/200x120'
  }
}
}
</script>

<style scoped>
.card {
  border: 1px solid #ccc;
  padding: 10px;
  cursor: pointer;
  border-radius: 4px;
  width: 200px;
}

.card img {
  width: 100%;
  height: 120px;
  object-fit: cover;
  border-radius: 4px;
}
</style>