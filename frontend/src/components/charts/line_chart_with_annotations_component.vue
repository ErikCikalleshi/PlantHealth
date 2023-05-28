<template>
    <v-btn-toggle class="ml-2" color="primary">
        <v-btn @click="zoomChart('30_min')">30M</v-btn>
        <v-btn @click="zoomChart('one_hour')">1H</v-btn>
        <v-btn @click="zoomChart('one_day')">1D</v-btn>
        <v-btn @click="zoomChart('one_week')">1W</v-btn>
        <v-btn @click="zoomChart('one_month')">1M</v-btn>
    </v-btn-toggle>
    <apexchart ref="chart" type="line" height="350" :options="chartOptions" :series="series"></apexchart>
</template>


<script lang="ts">
import {defineComponent, onMounted, ref, toRaw} from "vue";
import VueApexCharts from "vue3-apexcharts";
import type IMeasurement from "@/interfaces/IMeasurement";
import MeasurementsService from "@/services/MeasurementsService";

export default defineComponent({
    name: "Custom_Chart",
    components: {
        apexchart: VueApexCharts,
    },
    setup() {
        const chart = ref<ApexCharts>();

        onMounted(() => {
            chart.value // DIV element
        });

        return {
            chart
        }
    },
    props: {
        sensorName: {
            type: String,
            required: true,
        },
        sensorType: {
            type: String,
            required: true,
        },
        greenhouseUUID: {
            type: Number,
            required: true,
        },
        color: {
            type: String,
            required: true,
        },
    },
    data() {
        return {
            series: [
                {
                    type: 'line',
                    name: 'Measurements',
                    data: [] as {
                        x: number,
                        y: number
                    }[]
                },
                {
                    type: 'line',
                    name: 'Limit upper',
                    data: [] as {
                        x: number,
                        y: number
                    }[]
                },
                {
                    type: 'line',
                    name: 'Limit lower',
                    data: [] as {
                        x: number,
                        y: number
                    }[]
                },

            ],
            chartOptions: {
                chart: {
                    height: 350,
                    type: 'line',
                    animations:
                        {
                            speed: 500
                        },

                },
                dataLabels:
                    {
                        enabled: false
                    },
                fill: {
                    opacity: [1, 1, 1]
                },
                stroke: {
                    curve: 'smooth',
                    width: [3, 1.5, 1.5]
                },
                legend: {
                    show: true,
                },
                markers: {
                    hover: {
                        sizeOffset: 5
                    }
                },
                yaxis: {
                    type: 'numeric',
                    decimalsInFloat: 2,
                },
                xaxis: {
                    type: 'datetime',
                },
                tooltip: {
                    x: {
                        format: 'dd.MM.yyyy HH:mm'
                    }
                },
                noData: {
                    text: 'No data available',
                    align: 'center',
                    verticalAlign: 'middle',
                    offsetX: 0,
                    offsetY: 0,
                    style: {
                        color: undefined,
                        fontSize: '14px',
                        fontFamily: undefined
                    }
                },
                selection: '',
                colors: [''],
            },
        };
    },
    methods: {
        toRaw,
        async getMeasurements() {
            try {
                // const startTimestamp = new Date().getTime();
                const response = await MeasurementsService.getMeasurementsByGreenhouseIdAndSensorType(this.greenhouseUUID, this.sensorType);
                // const endTimestamp = new Date().getTime();
                // console.log("Time taken to get measurements: " + (endTimestamp - startTimestamp) + "ms");
                const measurements: IMeasurement[] = response.data;
                this.series[0].data = []; // Clear the existing data array
                this.series[1].data = []; // Clear the existing data array
                this.series[2].data = []; // Clear the existing data array

                measurements.forEach(measurement => {

                    let measurementDate: number = Number(new Date(measurement.date));
                    let measurementValue: number = Number(measurement.value);
                    this.series[2].data.push({
                        x: measurementDate,
                        y: measurement.lowerLimit
                    });
                    this.series[1].data.push({
                        x: measurementDate,
                        y: measurement.upperLimit
                    });

                    this.series[0].data.push({
                        x: measurementDate,
                        y: measurementValue,
                    });
                });
            } catch (error) {
                console.log(error);
            }
        },
        zoomChart(selection: string) {
            if (this.chart === undefined) {
                return;
            }
            const maxDate: number = this.series[0].data[this.series[0].data.length - 1].x
            const minDateData: number = this.series[0].data[0].x
            let minDate;
            if (this.chartOptions.selection === selection) {
                this.chartOptions.selection = '';
                this.chart.zoomX(minDateData, maxDate);
                return;
            }
            this.chartOptions.selection = selection;
            switch (selection) {
                case '30_min':
                    minDate = maxDate - 30 * 60 * 1000;
                    break;
                case 'one_hour':
                    minDate = maxDate - 1 * 60 * 60 * 1000;
                    break;
                case 'one_day':
                    minDate = maxDate - 24 * 60 * 60 * 1000;
                    break;
                case 'one_week':
                    minDate = maxDate - 7 * 24 * 60 * 60 * 1000;
                    break;
                case 'one_month':
                    minDate = maxDate - 30 * 24 * 60 * 60 * 1000;
                    break;
                default:
                    break;
            }

            if (minDate) {
                if (minDate < minDateData) {
                    minDate = minDateData;
                }
                this.chart.zoomX(minDate, maxDate);
            }
        },
    },
    created() {
        let colors = {} as Record<string, string>;
        colors = {
            'red': '#FF0000',
            'blue': '#0088ff',
            'teal': '#008080',
            'purple': '#800080',
            'yellow': '#ffE600',

        }
        if (colors[this.color] === undefined) {
            // black is the default color
            colors[this.color] = '#000000';
        }
        this.chartOptions.colors = [colors[this.color], '#FEB019', '#FEB019'];
    },
    mounted() {
        this.getMeasurements();
    }
})
;
</script>
