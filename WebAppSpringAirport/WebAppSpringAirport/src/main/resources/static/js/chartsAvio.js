google.charts.load('current', {packages: ['corechart', 'bar']});
google.charts.setOnLoadCallback(drawChart1);
google.charts.setOnLoadCallback(drawChart2);

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