google.charts.load('current', {packages: ['corechart', 'bar', 'line']});
google.charts.setOnLoadCallback(drawChart1);
google.charts.setOnLoadCallback(drawChart2);
google.charts.setOnLoadCallback(drawChart4);

function drawChart1() {
	var arr =
		[
         ['Service', 'Rating',],
         ['', 1.0],
         ['', 1.0]
       ];
	$.ajax({
		type:'GET',
		url:'/api/avioKompanije/chart1/' + localStorage.getItem("avio_id"),
		dataType:'json',
		contentType: 'application/json',
		beforeSend : function(request) {
			request.setRequestHeader("Authorization", "Bearer "
					+ localStorage.getItem("accessToken"));
		},
		success:function(data){
			console.log(data);
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
		          title: 'Airlines',
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
	var arr = [['Flights', 'Rating', { role: 'style' }]];
	$.ajax({
		type:'GET',
		url:'/api/avioKompanije/chart2/' + localStorage.getItem("avio_id"),
		dataType:'json',
		contentType: 'application/json',
		beforeSend : function(request) {
			request.setRequestHeader("Authorization", "Bearer "
					+ localStorage.getItem("accessToken"));
		},
		success:function(data){
			console.log(data);
			var list = data == null ? [] : (data instanceof Array ? data : [ data ]);
			
			$.each(list, function(index, flights){
				if( flights.car === "Average"){
					arr.push([flights.car, flights.carRating , 'red']);
				}else{
					arr.push([flights.car, flights.carRating , '']);
				}
			});
			
			var dataChart = google.visualization.arrayToDataTable(arr);
		    var dataView = new google.visualization.DataView( dataChart );
		    dataView.setColumns([{calc: "stringify" , sourceColumn : 0, type:'string'}, 1, 2]);
		    var options = {
		      title: 'Flight ratings',
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
		        title: 'Flights' ,
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
		    var chart = new google.visualization.BarChart(document.getElementById('chart_flightsRating'));
		    chart.draw(dataView, options);
		}
	});
  }



function drawChart4() {

    var chartDiv = document.getElementById('chart_income');

    var dataT = new google.visualization.DataTable();
    dataT.addColumn('date', 'Month');
    dataT.addColumn('number', "Income in $");
	$.ajax({
		type:'GET',
		url:'/api/avioKompanije/chart4/' + localStorage.getItem("avio_id"),
		dataType:'json',
		contentType: 'application/json',
		beforeSend : function(request) {
			request.setRequestHeader("Authorization", "Bearer "
					+ localStorage.getItem("accessToken"));
		},
		success:function(data){
			console.log(data);
			console.log(data[5]);
			var year = new Date().getFullYear()

			dataT.addRows([
		      [new Date(year, 0),  data[0]],
		      [new Date(year, 1),  data[1]],
		      [new Date(year, 2),  data[2]],
		      [new Date(year, 3),  data[3]],
		      [new Date(year, 4),  data[4]],
		      [new Date(year, 5),  data[5]],
		      [new Date(year, 6),  data[6]],
		      [new Date(year, 7),  data[7]],
		      [new Date(year, 8),  data[8]],
		      [new Date(year, 9),  data[9]],
		      [new Date(year, 10), data[10]],
		      [new Date(year, 11), data[11]],
		    ]);

		    var materialOptions = {
		      chart: {
		        title: 'Yearly income by months',

			      chartArea: {width: '50%'}
		      },
		      width: 700,
		      height: 400,
		      series: {
		        // Gives each series an axis name that matches the Y-axis below.
		        0: {axis: 'Income'}
		      },
		      axes: {
		        // Adds labels to each axis; they don't have to match the axis names.
		        y: {
		        	Income: {label: 'Income (Dollar)'}
		        }
		      }
		    };

		    var classicOptions = {
		      title: 'Yearly income by months',
		      titleTextStyle: {
		          fontSize: 20
		      },
		      width: 1000,
		      height: 350,
		      // Gives each series an axis that matches the vAxes number below.
		      series: {
		        0: {targetAxisIndex: 0},
		        1: {targetAxisIndex: 1}
		      },/*
		      vAxes: {
		        // Adds titles to each axis.
		        0: {title: 'Income in dollars'}
		      },*/
		      hAxis: {
		        ticks: [new Date(year, 0), new Date(year, 1), new Date(year, 2), new Date(year, 3),
		                new Date(year, 4),  new Date(year, 5), new Date(year, 6), new Date(year, 7),
		                new Date(year, 8), new Date(year, 9), new Date(year, 10), new Date(year, 11)
		               ]
		      },
		      vAxis: {
			        title: 'Income in dollars' ,
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
		    };

		    function drawMaterialChart() {
		      var materialChart = new google.charts.Line(chartDiv);
		      materialChart.draw(dataT, materialOptions);
		    }

		    function drawClassicChart() {
		      var classicChart = new google.visualization.LineChart(chartDiv);
		      classicChart.draw(dataT, classicOptions);
		    }

		    drawClassicChart();
		}
	});

  }
