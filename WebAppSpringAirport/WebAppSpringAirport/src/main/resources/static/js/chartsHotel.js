google.charts.load('current', {packages: ['corechart', 'bar', 'line']});
$(document).ready(function () {
    var today = new Date();
    var day=today.getDate()>9?today.getDate():"0"+today.getDate(); // format should be "DD" not "D" e.g 09
    var month=(today.getMonth()+1)>9?(today.getMonth()+1):"0"+(today.getMonth()+1);
    var year=today.getFullYear();
    var week = $('#weekChart3').val();
    console.log(week);
    $("#monthChart3").attr('min', '2019-01');
    $("#monthChart3").attr('max', year + '-' + month );
    $("#monthChart3").attr('value', year + '-' + month );

    $("#weekChart3").attr('min', '2019-W01');
    $("#weekChart3").attr('max', '2019-W53' );
    $("#weekChart3").attr('value', '2019-W25' );

    $("#dayChart3").attr('min', '2019-01-01');
    $("#dayChart3").attr('max', year + '-' + month + '-' + day );
    $("#dayChart3").attr('value', year + '-' + month + '-' + day );

    $("#monthChart3").removeAttr('disabled');
    $("#weekChart3").attr('disabled','disabled');
    $("#dayChart3").attr('disabled','disabled');
    
    $("#testBtn").click(function(){
    	drawChart1();
    	drawChart2();
    	drawChart4();
    }); 
    /*
    google.charts.setOnLoadCallback(drawChart1);
    google.charts.setOnLoadCallback(drawChart2);
    google.charts.setOnLoadCallback(drawChart4);*/
    //google.charts.setOnLoadCallback(drawChart3_1);
    //google.charts.setOnLoadCallback(drawChart3_3);
    
    
    $("#chart3 input:radio").change(function() {
        if ($(this).val() === "monthly") {
            $("#monthChart3").removeAttr('disabled');
            $("#weekChart3").attr('disabled','disabled');
            $("#dayChart3").attr('disabled','disabled');               
        }else if ($(this).val() === "weekly") {
            $("#monthChart3").attr('disabled','disabled');
            $("#weekChart3").removeAttr('disabled');
            $("#dayChart3").attr('disabled','disabled');
        }
        else {
            $("#monthChart3").attr('disabled','disabled');
            $("#weekChart3").attr('disabled','disabled');
            $("#dayChart3").removeAttr('disabled');
        }
      });
    
})

$("#showGraph3").click(function(ev){
    ev.preventDefault()// cancel form submission
    var checked = $("input[name='category']:checked").val();
    if( checked  === "monthly")
	{
    	var month = $("#monthChart3").val();
    	if( month === "" )
		{
    		notify("Please choose a valid week!", 'danger');
    		return;
		}
    	drawChart3_3();
	}
    else if(checked  === "weekly")
	{
    	var week = $("#weekChart3").val();
    	if( week === "" )
		{
    		notify("Please choose a valid week!", 'danger');
    		return;
		}
    	drawChart3_2();
	}
    else if(checked  === "daily")
	{
    	var day = $("#dayChart3").val();
    	if( day === ""){
    		notify("Please choose a valid day!", 'danger');
    		return;
    	}
    	drawChart3_1();
	}
    
});

function drawChart1() {
	var arr =
		[
         ['Service', 'Rating',],
         ['', 1.0],
         ['', 1.0]
       ];
	$.ajax({
		type:'GET',
		url:'/api/hotels/chart1/' + localStorage.getItem("hotel_id"),
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
		    console.log(arr);
		    
		    var dataView = new google.visualization.DataView( dataChart );
		    dataView.setColumns([{calc: "stringify" , sourceColumn : 0, type:'string'}, 1]);
			var options = {
			    title: 'Rating of service',
			    chartArea: {width: '80%'},
			    hAxis: {
			      title: 'Rating',
			      ticks: [1,2,3,4,5],
			      minValue:0,
			      maxValue:5,
			      viewWindow : {
			    	  max:5
			      },
		          titleTextStyle: {
		        	  fontSize: 15
		          }
			    },
		        vAxis: {
		          title: 'Hotel',
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
			    titleTextStyle: {
			        fontSize: 20
			    },
			      height:350
			};
			
			var chart = new google.visualization.BarChart(document.getElementById('chart_serviceRating'));	
			console.log(document.getElementById('chart_serviceHotelRating'));
			chart.draw(dataChart, options);
			
		}
	});
} 


function drawChart2() {
	var arr = [['Car model', 'Rating', { role: 'style' }]];
	$.ajax({
		type:'GET',
		url:'/api/hotels/chart2/' + localStorage.getItem("hotel_id"),
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
		      title: 'Room ratings',
		      chartArea: {width: '80%'},
		      hAxis: {
			      title: 'Rating',
			      ticks: [1,2,3,4,5],
			      minValue:0,
			      maxValue:5,
			      viewWindow : {
			    	  max:5
			      },
		          titleTextStyle: {
		        	  fontSize: 15
		          }
		      },
		      vAxis: {
		        title: 'Rooms' ,
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
		      height:450,

		      titleTextStyle: {
		          fontSize: 20
		      }
		    };
		    var chart = new google.visualization.BarChart(document.getElementById('chart_roomsRating'));
		    chart.draw(dataView, options);
		}
	});
  }

function drawChart3_1() {
	document.getElementById('chart_chart3').html = "";
	var datumP = $("#dayChart3").val();
	if(datumP === "")
	{
		notify("Please select date!", 'danger');
	}

    var dataT = new google.visualization.DataTable();
    dataT.addColumn('timeofday', 'Time of Day');
    dataT.addColumn('number', 'Issued rooms');

	var day = $("#dayChart3").val();
	$.ajax({
		type:'POST',
		url:'/api/hotels/chart3/daily/' + localStorage.getItem("hotel_id"),
		dataType:'json',
		data : chart3Json('daily', datumP),
		contentType: 'application/json',
		beforeSend : function(request) {
			request.setRequestHeader("Authorization", "Bearer "
					+ localStorage.getItem("accessToken"));
		},
		success:function(data){
			console.log(data);
		    dataT.addRows([
		      [{v: [1, 0, 0], f: '1:00'}, data[0]],
		      [{v: [2, 0, 0], f: '2:00'}, data[1]],
		      [{v: [3, 0, 0], f: '3:00'}, data[2]],
		      [{v: [4, 0, 0], f: '4:00'}, data[3]],
		      [{v: [5, 0, 0], f: '5:00'}, data[4]],
		      [{v: [6, 0, 0], f: '6:00'}, data[5]],
		      [{v: [7, 0, 0], f: '7:00'}, data[6]],
		      [{v: [8, 0, 0], f: '8:00'}, data[7]],
		      [{v: [9, 0, 0], f: '9:00'}, data[8]],
		      [{v: [10, 0, 0], f:'10:00'}, data[9]],
		      [{v: [11, 0, 0], f: '11:00'},data[10]],
		      [{v: [12, 0, 0], f: '12:00'},data[11]],
		      [{v: [13, 0, 0], f: '13:00'}, data[12]],
		      [{v: [14, 0, 0], f: '14:00'}, data[13]],
		      [{v: [15, 0, 0], f: '15:00'}, data[14]],
		      [{v: [16, 0, 0], f: '16:00'}, data[15]],
		      [{v: [17, 0, 0], f: '17:00'}, data[16]],
		      [{v: [18, 0, 0], f: '18:00'}, data[17]],
		      [{v: [19, 0, 0], f: '19:00'}, data[18]],
		      [{v: [20, 0, 0], f: '20:00'}, data[19]],
		      [{v: [21, 0, 0], f: '21:00'}, data[20]],
		      [{v: [22, 0, 0], f: '22:00'}, data[21]],
		      [{v: [23, 0, 0], f: '23:00'}, data[22]],
		      [{v: [24, 0, 0], f: '24:00'}, data[23]]
		    ]);

		    var options = {
		      title: 'Issued rooms on selected day',
			  chartArea: {width: '50%'},
		      
		      hAxis: {
		        title: 'Time of Day',
		        format: 'h:mm a'
		      },
		      vAxis: {
		        title: 'Issued rooms',
		        minValue : 0,

			      viewWindow : {
			    	  min : 0
			      },
		      },
		      height:450
		    };

		    var materialChart = new google.charts.Bar(document.getElementById('chart_chart3'));
		    materialChart.draw(dataT, options);
		}
	});
}

function drawChart3_2() {
	document.getElementById('chart_chart3').html = "";
	var datumP = $("#weekChart3").val();
	if(datumP === "")
	{
		notify("Please select date!", 'danger');
	}

    var dataT = new google.visualization.DataTable();
    dataT.addColumn('string', 'Day of week');
    dataT.addColumn('number', 'Issued rooms');

	var day = $("#weekChart3").val();
	$.ajax({
		type:'POST',
		url:'/api/hotels/chart3/weekly/' + localStorage.getItem("hotel_id"),
		dataType:'json',
		data : chart3Json('weekly', datumP),
		contentType: 'application/json',
		beforeSend : function(request) {
			request.setRequestHeader("Authorization", "Bearer "
					+ localStorage.getItem("accessToken"));
		},
		success:function(data){
			console.log(data);
			dataT.addRows([
			 		      ["Monday" , data[0]],
			 		      ["Tuesday" , data[1]],
			 		      ["Wednesday" , data[2]],
			 		      ["Thursday" , data[3]],
			 		      ["Friday", data[4]],
			 		      ["Saturday", data[5]],
			 		      ["Sunday", data[6]]
			 		    ]);
		    var options = {
		      title: 'Rooms issued in selected week',
			  chartArea: {width: '50%'},
		      
		      hAxis: {
		        title: 'Day of week',
			      viewWindow : {
			    	  min : 1,
			    	  max : 31
			    	  
			      },
		      },
		      vAxis: {
		        title: 'Issued rooms',
		        minValue : 0,

			      viewWindow : {
			    	  min : 0
			      },
		      },
		      height:450,
		    };

		    var materialChart = new google.charts.Bar(document.getElementById('chart_chart3'));
		    materialChart.draw(dataT, options);
		}
	});
}

function drawChart3_3() {
	document.getElementById('chart_chart3').html = "";
	var datumP = $("#monthChart3").val();
	if(datumP === "")
	{
		notify("Please select date!", 'danger');
	}

    var dataT = new google.visualization.DataTable();
    dataT.addColumn('number', 'Day of month');
    dataT.addColumn('number', 'Issued rooms');

	var day = $("#dayChart3").val();
	$.ajax({
		type:'POST',
		url:'/api/hotels/chart3/monthly/' + localStorage.getItem("hotel_id"),
		dataType:'json',
		data : chart3Json('monthly', datumP),
		contentType: 'application/json',
		beforeSend : function(request) {
			request.setRequestHeader("Authorization", "Bearer "
					+ localStorage.getItem("accessToken"));
		},
		success:function(data){
			console.log(data);
			var list = data == null ? [] : (data instanceof Array ? data : [ data ]);

			var arr = [];
			$.each(list, function(index, tickets){
				arr.push([ index+1, tickets ])
			});
			console.log(arr);
		    dataT.addRows(arr);
		    var options = {
		      title: 'Rooms issued in selected month',
			  chartArea: {width: '50%'},
		      
		      hAxis: {
		        title: 'Day of the month',
			      viewWindow : {
			    	  min : 1,
			    	  max : 31
			    	  
			      },
		      },
		      vAxis: {
		        title: 'Issued rooms',
		        minValue : 0,

			      viewWindow : {
			    	  min : 0
			      },
		      },
		      height:450
		    };

		    var materialChart = new google.charts.Bar(document.getElementById('chart_chart3'));
		    materialChart.draw(dataT, options);
		}
	});
}

function chart3Json(type, value) {
	return JSON.stringify({
		"type" : type,
		"value" : value
	});
}

function drawChart4() {

    var chartDiv = document.getElementById('chart_income');

    var dataT = new google.visualization.DataTable();
    dataT.addColumn('date', 'Month');
    dataT.addColumn('number', "Income in $");
	$.ajax({
		type:'GET',
		url:'/api/hotels/chart4/' + localStorage.getItem("hotel_id"),
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

				      height:450,
		    		chart: {
			    		title: 'Yearly income by months',

			    chartArea: {width: '80%'}
		      },
		      series: {
		        // Gives each series an axis name that matches the Y-axis below.
		        0: {axis: 'Income'}
		      },
		      axes: {
		        // Adds labels to each axis; they don't have to match the axis names.
		        y: {
		        	Income: {label: 'Income (Dollar)'}
		        }
		      },
		        titleTextStyle: {
			      	  fontSize: 20,
			      	  bold : true
			        },
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

		    drawMaterialChart();
		}
	});

  }
