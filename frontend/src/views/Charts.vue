<script lang="ts">
import {defineComponent} from "vue";
import footerComponent from "@/components/general/footer.vue";
import headerComponent from "@/components/general/header.vue";
import mainContainer from "@/components/general/main_container.vue";
import axios from "axios";
import {API_BASE_URL} from "@/services";
import {useStore as userStore} from "@/stores/user/user";
import PageHeading from "@/components/general/PageHeading.vue";
import HeaderView from "@/components/general/header.vue";
import FooterView from "@/components/general/footer.vue";
import VueApexCharts from "vue3-apexcharts";

export default defineComponent({
  name: "ChartsView",
  components: {
    FooterView,
    HeaderView,
    PageHeading,
    // eslint-disable-next-line vue/no-unused-components
    headerComponent,
    footerComponent,
    mainContainer,
  },
  data: function() {
    return {
      reveal: false,
      chartOptions: {
        chart: {
          id: "vuechart-example",
        },
        xaxis: {
          categories: [1991, 1992, 1993, 1994, 1995, 1996, 1997, 1998],
        },
      },
      series: [
        {
          name: "series-1",
          data: [30, 40, 35, 50, 49, 60, 70, 91],
        },
      ],
    };
  },
  methods: {

  },
  mounted() {

  }


});

</script>

<template>
  <v-app>
    <header-component />
    <main-container negative>
      <div class="container mx-auto">
        <div class="bg-white rounded-lg p-6 shadow-lg">
          <v-card
              class="mx-auto"
              max-width="344"
          >
            <v-card-text>
              <div>Word of the Day</div>
              <p class="text-h4 text--primary">
                el·ee·mos·y·nar·y
              </p>
              <p>adjective</p>
              <div class="text--primary">
                relating to or dependent on charity; charitable.<br>
                "an eleemosynary educational institution."
              </div>
            </v-card-text>
            <v-card-actions>
              <v-btn
                  variant="text"
                  color="teal-accent-4"
                  @click="reveal = true"
              >
                Learn More
              </v-btn>
            </v-card-actions>

            <v-expand-transition>
              <v-card
                  v-if="reveal"
                  class="v-card--reveal"
                  style="height: 100%;"
              >
                <v-card-text class="pb-0">
                  <p class="text-h4 text--primary">
                    Origin
                  </p>
                  <p>late 16th century (as a noun denoting a place where alms were distributed): from medieval Latin eleemosynarius, from late Latin eleemosyna ‘alms’, from Greek eleēmosunē ‘compassion’ </p>
                </v-card-text>
                <v-card-actions class="pt-0">
                  <v-btn
                      variant="text"
                      color="teal-accent-4"
                      @click="reveal = false"
                  >
                    Close
                  </v-btn>
                </v-card-actions>
              </v-card>
            </v-expand-transition>
          </v-card>
          <apexchart
              width="500"
              type="bar"
              :options="chartOptions"
              :series="series"
          ></apexchart>
        </div>
      </div>
    </main-container>
    <footer-component />
  </v-app>
</template>


<style>

.v-card--reveal {
  bottom: 0;
  opacity: 1 !important;
  position: absolute;
  width: 100%;
}



</style>
