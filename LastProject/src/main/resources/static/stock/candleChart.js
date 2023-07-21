
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
          type: 'candlestick',
        },
        title: {
          text: '주가차트',
          align: 'left'
        },
        annotations: {
          xaxis: [
            {
              x: 'Oct 06 14:00',
              borderColor: '#00E396',
              label: {
                borderColor: '#00E396',
                style: {
                  fontSize: '12px',
                  color: '#fff',
                  background: '#00E396'
                },
                orientation: 'horizontal',
                offsetY: 7,
                text: 'Annotation Test'
              }
            }
          ]
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
          }
        },
        yaxis: {
          tooltip: {
            enabled: true
          }
        }
        };

        var chart = new ApexCharts(document.querySelector("#candleChart"), options);
        chart.render();
		 }