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
import api from "@/services/api";
import {th} from "vuetify/locale";

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
  data: function () {
    return {
      revealedCardIndex: -1,
      expand: -1,
      chartOptions: {
        chart: {
          id: "vuechart-example",
        },
        xaxis: {
          categories:
        },
      },
      series: [
        {
          name: "series-1",
          data: [30, 40, 35, 50, 49, 60, 70, 91],
        },
      ],
      sensorTypes: [] as string[],
    };
  },
  methods: {
    getSensorType() {
      let greenhouseUUID = this.$route.params.id;
      console.log(this.$route.params.id)

      api.get(`greenhouse/get/${greenhouseUUID}`).then((response) => {
        response.data.sensors.forEach((sensor: { sensorType: string; }) => {
          const words: any = sensor.sensorType.toLowerCase().split("_");
          for (let i = 0; i < words.length; i++) {
            words[i] = words[i].charAt(0).toUpperCase() + words[i].slice(1);
          }
          this.sensorTypes.push(words.join(" "));
        });
      });
    },
    revealCard(index: number, scrollToChart: boolean) {
      // Reveal or hide the card based on the index
      this.expand = this.expand === index ? -1 : index;

      if (scrollToChart) {
        // Scroll to the chart if specified
        this.$nextTick(() => {
          this.scrollToChart();
        });
      }
    },
    scrollToChart() {
      // Find the element for the revealed card
      // @ts-ignore
      const cardElement = this.$refs.cardRef[this.expand];

      if (cardElement) {
        // Find the chart element within the card
        const chartElement = cardElement.querySelector(".chart-container");
        if (chartElement) {
          // Calculate the scroll position by adding 300 pixels to the current scroll position
          const scrollPosition = chartElement.offsetTop + 300;

          // Scroll to the chart element with auto-scrolling
          window.scrollTo({
            top: scrollPosition,
            behavior: "smooth",
          });
        }
      }
    },
    // Method to get the icon for a sensor type
    getSensorIcon(sensorType: string) {
      console.log(sensorType)
      // Define the mapping of sensor types to icons
      const iconMapping: any = {
        "Air Quality": "mdi-air-filter",
        "Temperature": "mdi-thermometer",
        "Light": "mdi-lightbulb",
        "Humidity Dirt": "mdi-water",
        "Air Pressure": "mdi-gauge",
        "Humidity Air": "mdi-water-percent",
      };

      // Return the corresponding icon for the sensor type
      return iconMapping[sensorType] || "";
    },

    // Method to get the color for a sensor icon
    getSensorIconColor(sensorType: string) {
      // Define the color mapping for the icons
      const colorMapping: any = {
        "Light": "yellow",
        "Air Quality": "purple",
        "Humidity Dirt": "blue",
        "Humidity Air": "teal",
        "Temperature": "red",
      };

      // Return the corresponding color for the sensor type
      return colorMapping[sensorType] || "";
    },

    // Method to get the size for a sensor icon
    getSensorIconSize(sensorType: string) {
      // Define the size mapping for the icons
      const sizeMapping: any = {
        "Light": "24",
        "Air Quality": "24",
        "Humidity Dirt": "24",
        "Humidity Air": "24",
        "Temperature": "24",
        // Add more sensor types and sizes as needed
      };

      // Return the corresponding size for the sensor type
      return sizeMapping[sensorType] || "";
    },
  },
  mounted() {
    this.getSensorType();
  }


});

</script>
<template>
  <v-app>
    <header-component />
    <main-container negative>
      <div class="container mx-auto">
        <v-row>
          <v-col
              v-for="(sensorType, index) in sensorTypes"
              :key="sensorType"
              :class="{ 'col-expanded': expand === index }"
              cols="12"
          >
            <v-card ref="cardRef" class="card-item">
              <v-card-item :title="sensorType">
                <template v-slot:subtitle>
                  Extreme Weather Alert
                </template>
              </v-card-item>
              <v-card-text class="py-0">
                <v-row align="center" no-gutters>
                  <v-col class="text-h2" cols="6">
                    64&deg;F
                  </v-col>

                  <v-col cols="6" class="text-right">
                    <v-icon
                        :color="getSensorIconColor(sensorType)"
                        size="88"
                        :icon="getSensorIcon(sensorType)"

                    ></v-icon>
                  </v-col>
                </v-row>
              </v-card-text>

              <div class="d-flex py-3 justify-space-between">
                <v-list-item density="compact" prepend-icon="mdi-weather-windy">
                  <v-list-item-subtitle>123 km/h</v-list-item-subtitle>
                </v-list-item>

                <v-list-item density="compact" prepend-icon="mdi-weather-pouring">
                  <v-list-item-subtitle>48%</v-list-item-subtitle>
                </v-list-item>
              </div>
              <v-expand-transition>
                <template v-if="expand === index">
                  <apexchart type="line" height="350" ref="chart" :options="chartOptions" :series="series"/>
                </template>
              </v-expand-transition>

              <v-divider></v-divider>

              <v-card-actions>
                <v-btn @click="revealCard(index, true)"  variant="outlined">
                  {{ expand === index ? "Hide Report" : "Full Report" }}
                </v-btn>
              </v-card-actions>
            </v-card>
          </v-col>
        </v-row>
      </div>
    </main-container>
    <footer-component />
  </v-app>
</template>

<style>
.col-expanded {
  flex-grow: 1;
}

.card-item {
  margin-bottom: 10px;
}
</style>
