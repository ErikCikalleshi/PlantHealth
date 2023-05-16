<template>
    <apexchart
        ref="realtimeChart"
        type="line"
        height="500"
        :options="chartOptions"
        :series="series"
    />
</template>

<script>
export default {
    data() {
        return {
            series: [{
                name: 'Desktops',
                data: [10, 41, 35, 51, 49, 62, 69, 91, 99],
            }],
            chartOptions: {
                colors: ['#FCCF31', '#17ead9', '#f02fc2'],
                chart: {
                    height: 350,
                },
                grid: {
                    show: true,
                    strokeDashArray: 0,
                    xaxis: {
                        lines: {
                            show: true,
                        },
                    },
                },
                stroke: {
                    curve: 'straight',
                    width: 5,
                },
                // grid: {
                //   padding: {
                //     left: 0,
                //     right: 0,
                //   },
                // },
                dropShadow: {
                    enabled: true,
                    opacity: 0.3,
                    blur: 5,
                    left: -7,
                    top: 22,
                },
                dataLabels: {
                    enabled: false,
                },
                title: {
                    text: 'Line',
                    align: 'left',
                    style: {
                        color: '#FFF',
                    },
                },
                xaxis: {
                    categories: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep'],
                    labels: {
                        style: {
                            colors: '#fff',
                        },
                    },
                },
                yaxis: {
                    labels: {
                        style: {
                            color: '#fff',
                        },
                    },
                },
            },
        };
    },
    mounted() {
        this.setDataLineChart();
    },
    methods: {
        getRandomArbitrary(min, max) {
            return Math.floor(Math.random() * 99);
        },
        setDataLineChart() {
            setInterval(() => {
                this.series[0].data.splice(0, 1);
                this.series[0].data.push(this.getRandomArbitrary(0, 99));
                this.updateSeriesLine();
            }, 3000);
        },
        updateSeriesLine() {
            this.$refs.realtimeChart.updateSeries([{
                data: this.series[0].data,
            }], false, true);
        },
    },
};
</script>
