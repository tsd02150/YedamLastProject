 var options = {
          series: [
          {
            name: 'Actual',
            data: [
              {
                x: '2011',
                y: 1292
              },
              {
                x: '2012',
                y: 4432
              },
              {
                x: '2013',
                y: 5423
              },
              {
                x: '2014',
                y: 6653
              },
              {
                x: '2015',
                y: 8133
              },
              {
                x: '2016',
                y: 7132
              },
              {
                x: '2017',
                y: 7332
              },
              {
                x: '2018',
                y: 6553
              }
            ]
          }
        ],
          chart: {
          height: 150,
          type: 'bar'
        },
        plotOptions: {
          bar: {
            columnWidth: '60%'
          }
        },
        colors: ['rgb(103, 187, 255)'],
        dataLabels: {
          enabled: false
        }
        
        };

        var chart = new ApexCharts(document.querySelector("#stickChart"), options);
        chart.render();