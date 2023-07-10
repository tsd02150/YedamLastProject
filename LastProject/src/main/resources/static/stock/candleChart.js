var options = {
		          series: [{
		          data: [
		            {
		              x: new Date(1538884800000),
		              y: [6604.98, 6606, 6604.07, 6606]
		            },
		          ]
		        }],
		          chart: {
		          type: 'candlestick',
		          height: 350
		        },
		        title: {
		          text: '주가차트',
		          align: 'left'
		        },
		        xaxis: {
		          type: 'datetime'
		        },
		        yaxis: {
		          tooltip: {
		            enabled: true
		          }
		        }
		        };

		        var chart = new ApexCharts(document.querySelector("#candleChart"), options);
		        chart.render();
		        