<template>
    <apexchart type="line" height="350" :options="chartOptions" :series="series"></apexchart>
</template>


<script lang="ts">
import {defineComponent, toRaw} from "vue";
import VueApexCharts from "vue3-apexcharts";
import type IMeasurement from "@/interfaces/IMeasurement";
import MeasurementsService from "@/services/MeasurementsService";

export default defineComponent({
    name: "Custom_Chart",
    components: {
        apexchart: VueApexCharts,
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
            type: String,
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
                        x: string,
                        y: number
                    }[]
                },
                {
                    type: 'line',
                    name: 'Limit upper',
                    data: [] as {
                        x: string,
                        y: number
                    }[]
                },
                {
                    type: 'line',
                    name: 'Limit lower',
                    data: [] as {
                        x: string,
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
                        }
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
                xaxis:{
                    type: 'datetime',
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
            },
        };
    },
    methods: {
        toRaw,
        async getMeasurements() {
            try {
                const startTimestamp = new Date().getTime();
                const response = await MeasurementsService.getMeasurementsByGreenhouseIdAndSensorType(this.greenhouseUUID, this.sensorType);
                const endTimestamp = new Date().getTime();
                console.log("Time taken to get measurements: " + (endTimestamp - startTimestamp) + "ms");
                const measurements: IMeasurement[] = response.data;
                this.series[0].data = []; // Clear the existing data array
                this.series[1].data = []; // Clear the existing data array
                this.series[2].data = []; // Clear the existing data array

                measurements.forEach(measurement => {

                    let measurementDate: Date = new Date(measurement.date);
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
            console.log(this.series[0].data.length + " " + this.series[1].data.length);

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
        this.getMeasurements();
    },
});
</script>
