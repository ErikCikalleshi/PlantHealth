<template>
  <div id="chart">
    <apexchart type="line" height="350" :options="chartOptions" :series="series"></apexchart>
  </div>
</template>

<script>
import VueApexCharts from "vue3-apexcharts";

export default {
  name: "Custom_Chart",
  components: {
    apexchart: VueApexCharts,
  },
  data: function () {
    return {
      series: [{
        name: 'Prices',
        data: [10, 20, 30, 40, 50, 60, 70, 80, 90, 100]
      }],
      chartOptions: {
        chart: {
          height: 350,
          type: 'line',
          id: 'areachart-2'
        },
        annotations: {
          yaxis: [{
            y: 8200,
            borderColor: '#00E396',
            label: {
              borderColor: '#00E396',
              style: {
                color: '#fff',
                background: '#00E396',
              },
              text: 'Support',
            }
          }, {
            y: 8600,
            y2: 9000,
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
          xaxis: [{
            x: new Date(2017,10,23).getTime(),
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
            x: new Date(2017,10,26).getTime(),
            x2: new Date(2017,10,28).getTime(),
            fillColor: '#B3F7CA',
            opacity: 0.4,
            label: {
              borderColor: '#B3F7CA',
              style: {
                fontSize: '10px',
                color: '#fff',
                background: '#00E396',
              },
              offsetY: -10,
              text: 'X-axis range',
            }
          }],
          points: [{
            x: new Date(2017,10,1).getTime(),
            y: 8607.55,
            marker: {
              size: 8,
              fillColor: '#fff',
              strokeColor: 'red',
              radius: 2,
              cssClass: 'apexcharts-custom-class'
            },
            label: {
              borderColor: '#FF4560',
              offsetY: 0,
              style: {
                color: '#fff',
                background: '#FF4560',
              },

              text: 'Point Annotation',
            }
          }, {
            x: new Date(2017,10,23).getTime(),
            y: 9340.85,
            marker: {
              size: 0
            },
          }]
        },
        dataLabels: {
          enabled: false
        },
        stroke: {
          curve: 'straight'
        },
        grid: {
          padding: {
            right: 30,
            left: 20
          }
        },
        title: {
          text: 'Line with Annotations',
          align: 'left'
        },
        labels: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct'],
        xaxis: {
          type: 'datetime',
        },
      },
    }
  }
};
</script>
