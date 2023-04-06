<template>
  <v-app>
    <header-view>
      <div class="container mx-auto">
        <v-carousel height="64vh" theme="light">
          <v-carousel-item>
            <div class="carousel__item">
              <h2>Start tracking your plants with <span>Plant Health</span></h2>
              <button>View Dashboard</button>
            </div>
          </v-carousel-item>
          <v-carousel-item>
            <div class="carousel__item">
              <h2>Start tracking your plants with <span>Plant Health</span></h2>
              <button>View Dashboard</button>
            </div>
          </v-carousel-item>
          <v-carousel-item>
            <div class="carousel__item">
              <h2>Start tracking your plants with <span>Plant Health</span></h2>
              <button>View Dashboard</button>
            </div>
          </v-carousel-item>
        </v-carousel>
      </div>
    </header-view>
  </v-app>
</template>

<script lang="ts">
import {useStore} from '@/stores/user/user';
import HeaderView from "@/components/general/header.vue";
export default {
  name: 'LandingPage',
  components: {HeaderView},
  data() {
    return {
      store: useStore(),
    }
  }
}
</script>

<style>
.carousel__item {
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
  gap: 43px;
  justify-content: center;
  align-items: center;
}
.carousel__item button {
  padding: 18px 35px;
  font-size: 20px;
  background: white;
  outline: none;
}
.v-carousel__controls {
  background: transparent;
}
.carousel__item h2 {
  letter-spacing: -0.05em;
  text-transform: uppercase;
  font-weight: 700;
  font-size: 64px;
  line-height: 92.5%;
  color: white;
  text-align: center;
  width: 80%;
}
.carousel__item span {
  font-size: 96px;
  line-height: 92.5%;
}
</style>