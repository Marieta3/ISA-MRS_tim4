google.charts.load('current', {packages: ['corechart','line', 'bar']});
google.charts.setOnLoadCallback(drawChart1);
google.charts.setOnLoadCallback(drawChart2);
google.charts.setOnLoadCallback(drawChart);

test();
function test() {
	$.ajax({
		type:'GET',
		url:'/api/rentACars/chart4/' + localStorage.getItem("rent_id"),
		dataType:'json',
		contentType: 'application/json',
		beforeSend : function(request) {
			request.setRequestHeader("Authorization", "Bearer "
					+ localStorage.getItem("accessToken"));
		},
		success:function(data){
			console.log(data);
		}
	});
}

function drawChart1() {
	var arr =
		[
         ['Service', 'Rating',],
         ['', 1.0],
         ['', 1.0]
       ];
	$.ajax({
		type:'GET',
		url:'/api/rentACars/chart1/' + localStorage.getItem("rent_id"),
		dataType:'json',
		contentType: 'application/json',
		beforeSend : function(request) {
			request.setRequestHeader("Authorization", "Bearer "
					+ localStorage.getItem("accessToken"));
		},
		success:function(data){
			//console.log(data);
			arr[1][0] = data.service;
			arr[1][1] = data.serviceRating;
			
			arr[2][0] = data.others;
			arr[2][1] = data.othersRating;
		    var dataChart = google.visualization.arrayToDataTable(arr);

		    var dataView = new google.visualization.DataView( dataChart );
		    dataView.setColumns([{calc: "stringify" , sourceColumn : 0, type:'string'}, 1]);
			var options = {
			    title: 'Rating of service',
			    chartArea: {width: '50%'},
			    hAxis: {
			      title: 'Rating',
			      ticks: [1,2,3,4,5],
			      minValue:0,
			      maxValue:5,
			      viewWindow : {
			    	  max:5
			      },
		          titleTextStyle: {
		        	  fontSize: 15,
		          	  bold : true
		          }
			    },
		        vAxis: {
		          title: 'Rent-A-Car',
			      textPosition : 'out',
			      textStyle: {
			      	fontSize : 10,
			        bold : false
			      },
		          titleTextStyle: {
		        	  fontSize: 15,
		          	  bold : true
		          }
		        },
			    width:500,
			    height:250,
			    titleTextStyle: {
			        fontSize: 20
			    }
			};
			
			var chart = new google.visualization.BarChart(document.getElementById('chart_serviceRating'));			
			chart.draw(dataChart, options);
			
		}
	});
} 

function drawChart2() {
	var arr = [['Car model', 'Rating', { role: 'style' }]];
	$.ajax({
		type:'GET',
		url:'/api/rentACars/chart2/' + localStorage.getItem("rent_id"),
		dataType:'json',
		contentType: 'application/json',
		beforeSend : function(request) {
			request.setRequestHeader("Authorization", "Bearer "
					+ localStorage.getItem("accessToken"));
		},
		success:function(data){
			console.log(data);
			var list = data == null ? [] : (data instanceof Array ? data : [ data ]);
			
			$.each(list, function(index, cars){
				if( cars.car === "Average"){
					arr.push([cars.car, cars.carRating , 'red']);
				}else{
					arr.push([cars.car, cars.carRating , '']);
				}
			});
			
			var dataChart = google.visualization.arrayToDataTable(arr);
		    var dataView = new google.visualization.DataView( dataChart );
		    dataView.setColumns([{calc: "stringify" , sourceColumn : 0, type:'string'}, 1, 2]);
		    var options = {
		      title: 'Vehicle ratings',
		      chartArea: {width: '50%'},
		      hAxis: {
			      title: 'Rating',
			      ticks: [1,2,3,4,5],
			      minValue:0,
			      maxValue:5,
			      viewWindow : {
			    	  max:5
			      },
		          titleTextStyle: {
		        	  fontSize: 15,
		          	  bold : true
		          }
		      },
		      vAxis: {
		        title: 'Vehicles' ,
		        textPosition : 'out',
		        textStyle : {
		        	fontSize : 10,
		        	bold : false
		        },
		        titleTextStyle: {
		      	  fontSize: 15,
		      	  bold : true
		        }
		      },
		      width:500,

		      titleTextStyle: {
		          fontSize: 20
		      }
		    };
		    var chart = new google.visualization.BarChart(document.getElementById('chart_carsRating'));
		    chart.draw(dataView, options);
		}
	});
  }

function drawChart4() {
	var chartDiv = document.getElementById('chart_income');
	
	var data = new google.visualization.DataTable();
	data.addColumn('date', 'Month');
	data.addColumn('number', "Income");
	
	data.addRows([
	  [new Date(2014, 0),  5000],
	  [new Date(2014, 1),   0],
	  [new Date(2014, 2),   69.69],
	  [new Date(2014, 3),  452.9],
	  [new Date(2014, 4),  6666.3],
	  [new Date(2014, 5),    4559],
	  [new Date(2014, 6), 100.6],
	  [new Date(2014, 7), 1066.3],
	  [new Date(2014, 8),  7888.4],
	  [new Date(2014, 9),  40.4],
	  [new Date(2014, 10), 145.1],
	  [new Date(2014, 11), 1500]
	]);
	
	var materialOptions = {
	  chart: {
	    title: 'Monthly income in this year'
	  },
	  width: 500,
	  height: 300,
	};
	
	var classicOptions = {
	  title: 'Monthly income in this year',
	  width: 900,
	  height: 500,
	  // Gives each series an axis that matches the vAxes number below.
	  series: {
	    0: {targetAxisIndex: 0},
	    1: {targetAxisIndex: 1}
	  },
	  vAxes: {
	    // Adds titles to each axis.
	    0: {title: 'Income in dollars'}
	  },
	  hAxis: {
	    ticks: [new Date(2014, 0), new Date(2014, 1), new Date(2014, 2), new Date(2014, 3),
	            new Date(2014, 4),  new Date(2014, 5), new Date(2014, 6), new Date(2014, 7),
	            new Date(2014, 8), new Date(2014, 9), new Date(2014, 10), new Date(2014, 11)
	           ]
	  },
	  vAxis: {
	    /*viewWindow: {
	      max: 30
	    }*/
	  }
	};
	
	  var materialChart = new google.charts.Line(chartDiv);
	  materialChart.draw(data, materialOptions);

}

function drawChart() {

    var chartDiv = document.getElementById('chart_income');

    var data = new google.visualization.DataTable();
    data.addColumn('date', 'Month');
    data.addColumn('number', "Income in $");

    data.addRows([
      [new Date(2014, 0),  5000],
      [new Date(2014, 1),   0],
      [new Date(2014, 2),   69.69],
      [new Date(2014, 3),  452.9],
      [new Date(2014, 4),  6666.3],
      [new Date(2014, 5),    4559],
      [new Date(2014, 6), 100.6],
      [new Date(2014, 7), 1066.3],
      [new Date(2014, 8),  7888.4],
      [new Date(2014, 9),  40.4],
      [new Date(2014, 10), 145.1],
      [new Date(2014, 11), 1500]
    ]);

    var materialOptions = {
      chart: {
        title: 'Yearly income by months'
      },
      width: 900,
      height: 500,
      /*series: {
        // Gives each series an axis name that matches the Y-axis below.
        0: {axis: 'Temps'},
        1: {axis: 'Daylight'}
      },
      axes: {
        // Adds labels to each axis; they don't have to match the axis names.
        y: {
          Temps: {label: 'Temps (Celsius)'},
          Daylight: {label: 'Daylight'}
        }
      }*/
    };

    var classicOptions = {
      title: 'Yearly income by months',
      width: 1000,
      height: 350,
      // Gives each series an axis that matches the vAxes number below.
      series: {
        0: {targetAxisIndex: 0},
        1: {targetAxisIndex: 1}
      },
      vAxes: {
        // Adds titles to each axis.
        0: {title: 'Income in dollars'}
      },
      hAxis: {
        ticks: [new Date(2014, 0), new Date(2014, 1), new Date(2014, 2), new Date(2014, 3),
                new Date(2014, 4),  new Date(2014, 5), new Date(2014, 6), new Date(2014, 7),
                new Date(2014, 8), new Date(2014, 9), new Date(2014, 10), new Date(2014, 11)
               ]
      },
      vAxis: {
        /*viewWindow: {
          max: 30
        }*/
      }
    };

    function drawMaterialChart() {
      var materialChart = new google.charts.Line(chartDiv);
      materialChart.draw(data, materialOptions);
    }

    function drawClassicChart() {
      var classicChart = new google.visualization.LineChart(chartDiv);
      classicChart.draw(data, classicOptions);
    }

    drawClassicChart();

  }
