<template>
    <apexchart ref="chart" color="color" :sensor="sensor" type="line" height="350" :options="chartOptions"
               :series="series" :annotations="chartOptions.annotations.yaxis"></apexchart>
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
        color: {
            type: String,
            required: true,
        },
    },
    data: function () {
        let colors = {};
        console.log(this.color)
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
        return {
            chartOptions: {
                chart: {
                    id: 'vuechart-example'
                },
                colors: [colors[this.color]],
                annotations: {
                    yaxis: [],
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
            let bool_test = true;
            let upperLimit = 0;
            let lowerLimit = 0;
            try {
                const response = await MeasurementsService.getMeasurementsByGreenhouseId(1);

                this.measurements = response.data;
                this.series[0].data = []; // Clear the existing data array


                for (let i = 0; i < this.measurements.length; i++) {

                    if (this.measurements[i].sensorType !== this.sensor) {
                        continue;
                    }

                    let date_cu;
                    // format the date to be displayed on the chart to be HH:MM:SS
                    date_cu = this.measurements[i].date.substring(11, 19);

                    this.series[0].data.push({
                        x: date_cu,
                        y: this.measurements[i].value
                    });
                    // clear annotations
                    this.chartOptions.annotations.yaxis = [];

                    if(this.chartOptions.annotations.yaxis.length === 0 && bool_test) {
                        console.log("In chartoptions")
                        console.log(this.measurements[i])
                        bool_test = false;
                        upperLimit = this.measurements[i].upperLimit;
                        lowerLimit = this.measurements[i].lowerLimit;
                        //this.updateAnnotations(this.measurements[i].upperLimit, this.measurements[i].lowerLimit)
                    }

                    // this.chartOptions.annotations.yaxis.push({
                    //     y: this.measurements[i].lowerLimit,
                    //     y2: this.measurements[i].upperLimit,
                    //     borderColor: '#000',
                    //     fillColor: '#FEB019',
                    //     opacity: 0.2,
                    //     label: {
                    //         borderColor: '#333',
                    //         style: {
                    //             fontSize: '10px',
                    //             color: '#333',
                    //             background: '#FEB019',
                    //         },
                    //         text: 'Y-axis range',
                    //     }
                    // });
                }

            } catch (error) {
                console.log(error);
            }
            return [upperLimit, lowerLimit];
        },

        updateAnnotations(upper, lower) {
            console.log(upper, lower)
            this.chartOptions.annotations = {
                yaxis: [{
                    y: lower,
                    y2: upper,
                    borderColor: '#000',
                    fillColor: '#FEB019',
                    opacity: 0.2,
                    label: {
                        borderColor: '#333',
                        style: {
                            fontSize: '10px',
                            color: '#333',
                            background: '#FEB019',
                        },
                        text: 'Y-axis range',
                    }
                }],
            }
            console.log(this.chartOptions);
            // remount the chart
            // this.$refs.chart.updateOptions({
            //     annotations: {
            //         yaxis: [{
            //             y: 20,
            //             y2: 30,
            //             borderColor: '#000',
            //             fillColor: '#FEB019',
            //             opacity: 0.2,
            //             label: {
            //                 borderColor: '#333',
            //                 style: {
            //                     fontSize: '10px',
            //                     color: '#333',
            //                     background: '#FEB019',
            //                 },
            //                 text: 'Y-axis range',
            //             }
            //         }],
            //     }
            // })
        },


        forceUpdateAnnotations(upper, lower) {
            console.log(upper, lower)
            this.$refs.chart.updateOptions({
                annotations: {
                    yaxis: [{
                        y: lower,
                        y2: upper,
                        borderColor: '#000',
                        fillColor: '#FEB019',
                        opacity: 0.2,
                        label: {
                            borderColor: '#333',
                            style: {
                                fontSize: '10px',
                                color: '#333',
                                background: '#FEB019',
                            },
                            text: 'Y-axis range',
                        }
                    }],
                }
            })
            console.log(this.$refs.chart.options);
        },


    },

    mounted: async function () {
        let limits = await this.getMeasurements();

        this.$nextTick(() => {
            // Use nextTick to ensure that the chart component is mounted
            // this.updateAnnotations(limits[1], limits[0]);
            this.forceUpdateAnnotations(limits[0], limits[1]);
        });
    }
};
</script>
