 
 function makeStickChart(chartData){
 	 let chartArr =[];
 	 $(chartData).each(function(idx,item){
 	 	let cdata = {
 	 					x:makeYear(item.taDt),
 	 					y:item.vol
 	 				}
 	 	chartArr.push(cdata);
 	 })
	 var options = {
	          series: [
	          {
	            name: 'Actual',
	            data: chartArr
	          }
	        ],
	          chart: {
	          height: 150,
	          type: 'bar'
	        },
	        plotOptions: {
	          bar: {
	            columnWidth: '40%'
	          }
	        },
	        colors: ['rgb(103, 187, 255)'],
	        dataLabels: {
	          enabled: false
	        }
	        ,
        xaxis: {
           tickAmount: 7,
        },
	        };
	
	        var chart = new ApexCharts(document.querySelector("#stickChart"), options);
	        chart.render();
	        
       }