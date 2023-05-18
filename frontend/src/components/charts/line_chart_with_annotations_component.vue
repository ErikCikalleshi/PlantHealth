<template>
    <apexchart :sensor="sensor" type="line" height="350" :options="chartOptions" :series="series"></apexchart>
</template>

<script>
import MeasurementsService from "@/services/MeasurementsService";
import VueApexCharts from "vue3-apexcharts";

export default {
    name: "Custom_Chart",
    components: {
        apexchart: VueApexCharts,
    },
    props: {
        sensor: {
            type: String,
            required: true,
        },
    },
    data: function() {
        return {
            chartOptions: {
                chart: {
                    id: 'vuechart-example'
                },
                annotations: {
                    xaxis: [{
                        x: 1995,
                        strokeDashArray: 0,
                        borderColor: '#775DD0',
                        label: {
                            borderColor: '#775DD0',
                            style: {
                                color: '#fff',
                                background: '#775DD0',
                            },
                            text: 'Anno Test',
                        }
                    }, {
                        x: 1996,
                        borderColor: '#FEB019',
                        label: {
                            borderColor: '#FEB019',
                            style: {
                                color: '#fff',
                                background: '#FEB019',
                            },
                            text: 'New Beginning',
                        }
                    }],
                },
            },
            series: [{
                name: 'series-1',
                data: []
            }]
        }
    },

    methods: {

        async getMeasurements() {
            try {
                const response = await MeasurementsService.getMeasurementsByGreenhouseId(1);
                console.log(response.data)
                this.measurements = response.data;
                this.series[0].data = []; // Clear the existing data array
                console.log(this.sensor);
                console.log(this.measurements.length)
                for (let i = 0; i < this.measurements.length; i++) {

                    if(this.measurements[i].sensorType !== this.sensor){
                        continue;
                    }
                    console.log(this.measurements[i].sensorType);
                    this.series[0].data.push({
                        x:i,
                        y: this.measurements[i].value
                    });
                }
                console.log(this.series);
            } catch (error) {
                console.log(error);
            }
        },

        updateAnnotations() {
            this.chartOptions.annotations = {
                xaxis: [{
                    x: 1996,
                    borderColor: '#002563',
                    label: {
                        borderColor: '#002563',
                        style: {
                            color: '#fff',
                            background: '#002563',
                        },
                        text: 'New Annotation',
                    }
                }],
            }
            console.log(this.chartOptions);
        },

        forceUpdateAnnotations() {
            this.$refs.chart.updateOptions({
                annotations: {
                    xaxis: [{
                        x: 1996,
                        borderColor: '#002563',
                        label: {
                            borderColor: '#002563',
                            style: {
                                color: '#fff',
                                background: '#002563',
                            },
                            text: 'New Annotation',
                        }
                    }],
                }
            })
        },
    },

    mounted: function () {
        this.getMeasurements();
    }
};
</script>
