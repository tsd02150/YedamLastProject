
function makeCandleChart(chartData){
		let chartArr = [];
  			$(chartData).each(function(idx,item){
  				let cdata = {
  									 x: new Date(item.taDt),
  									 y: [item.opPrc , item.hprc , item.lprc, item.clPrc]
  									}
  				chartArr.push(cdata);
  			})
	
        var options = {
          series: [{
          name: 'candle',
          data: chartArr
        			}],
          chart: {
          height: 350,
          
          type: 'candlestick'
        },
        title: {
          text: '주가차트',
          align: 'left'
        },
        tooltip: {
          enabled: true,
        },
        xaxis: {
          type: 'category',
          labels: {
            formatter: function(val) {
              return dayjs(val).format('YY-MM-DD')
            }
          },
           tickAmount: 7,
        },
        yaxis: {
          tooltip: {
            enabled: true
          }
        },
        plotOptions: {
            candlestick: {
                colors: {
                    upward: '#dc3545',
                    downward: '#2C6EF2'
                }
            },
        }
        };

        var chart = new ApexCharts(document.querySelector("#candleChart"), options);
        chart.render();
		 }