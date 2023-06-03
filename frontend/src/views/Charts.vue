<script lang="ts">
import {defineComponent} from "vue";
import footerComponent from "@/components/general/footer.vue";
import headerComponent from "@/components/general/header.vue";
import mainContainer from "@/components/general/main_container.vue";
import AdminGreenhouseService from "@/services/admin/AdminGreenhouseService";
import AdminAccessPointService from "@/services/admin/AdminAccessPointService";
import Custom_Chart from '@/components/charts/line_chart_with_annotations_component.vue';
import PageHeading from "@/components/general/PageHeading.vue";
import type IGreenhouse from "@/interfaces/IGreenhouse";
import type IAccesspoint from "@/interfaces/IAccessPoint";
import EditGreenhouseDialogForm from "@/components/gardener/edit_greenhouse.vue";

export default defineComponent({
    name: "ChartsView",
    components: {
        EditGreenhouseDialogForm,
        PageHeading,
        Custom_Chart,
        headerComponent,
        footerComponent,
        mainContainer,
    },
    data: function () {
        return {
            revealedCardIndex: -1,
            expand: -1,
            sensorTypes: [] as string[],
            rawSensorTypes: [] as string[],
            greenhouse: {} as IGreenhouse,
            accesspoint: {} as IAccesspoint,
        };
    },
    methods: {
        getSensorType() {
            let greenhouseUUID = this.$route.params.id;

            AdminGreenhouseService.getGreenhouse(+greenhouseUUID).then((response) => {
                this.greenhouse = response.data;
                response.data.sensors.sort((a: { sensorType: string; }, b: { sensorType: string; }) => {
                    if (a.sensorType < b.sensorType) {
                        return -1;
                    }
                    if (a.sensorType > b.sensorType) {
                        return 1;
                    }
                    return 0;
                }).forEach((sensor: { sensorType: string; }) => {
                    const words: any = sensor.sensorType.toLowerCase().split("_");
                    for (let i = 0; i < words.length; i++) {
                        words[i] = words[i].charAt(0).toUpperCase() + words[i].slice(1);
                    }
                    this.sensorTypes.push(words.join(" "));
                    this.rawSensorTypes.push(sensor.sensorType);
                });
            });
        },
        revealCard(index: number, scrollToChart: boolean) {
            // Reveal or hide the card based on the index
            this.expand = this.expand === index ? -1 : index;
        },

        // Method to get the icon for a sensor type
        getSensorIcon(sensorType: string) {
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

        getAccesspoint() {
            const greenhouseUUID = this.$route.params.id;
            AdminAccessPointService.getAccesPointByGreenhouseUuid(+greenhouseUUID).then((response) => {
                this.accesspoint = response.data;
            });
        }
    },
    mounted() {
        this.getSensorType();
        this.getAccesspoint();

        // this.getMeasurement();
    }
});

</script>
<template>
    <v-app>
        <header-component/>
        <main-container negative>
            <PageHeading>Stats for {{ greenhouse.name }} ({{ greenhouse.location }})</PageHeading>
            <v-label class="mt-2">Accesspoint: {{ accesspoint.name }}</v-label>
            <edit-greenhouse-dialog-form :greenhouseUUID="greenhouse.uuid"/>
            <div class="container my-10">
                <v-row>

                    <v-col
                            v-for="(sensorType, index) in sensorTypes"
                            :key="sensorType"
                            :class="{ 'col-expanded': expand === index }"
                            cols="12"

                    >
                        <v-card class="card-item">
                            <div class="flex justify-start align-center p-2">
                                <v-icon :color="getSensorIconColor(sensorType)"
                                        size="35"
                                        :icon="getSensorIcon(sensorType)"

                                ></v-icon>
                                <v-card-item :title="sensorType">
                                </v-card-item>

                            </div>


                            <Custom_Chart :color="getSensorIconColor(sensorType)" :sensor-name="sensorType"
                                          :sensor-type="rawSensorTypes[index]" :greenhouse-u-u-i-d="greenhouse.uuid"/>

                            <v-divider></v-divider>
                        </v-card>
                    </v-col>
                </v-row>
            </div>
        </main-container>
        <footer-component/>
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
