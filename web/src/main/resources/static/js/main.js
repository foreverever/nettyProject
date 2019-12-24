/**
 * 
 */

var chartLabels = [ 'start' ];
var chartData = [ null ];
var barData = [ 0, 0, 0, 0, 0, 0, 0, 0 ];
var lineChart;
var barChart;

$(document).ready(function() {
	start();
	setInterval("chart()", 1000); // 주기 설정 -1초
	setInterval("rank()", 10000); // 10초
});

function start() {	
	rank();
	
	// content Form 기본값 정의
	$("#startDay").val(toDate(new Date()).slice(0, 10));
	$("#startTime").val('00:00');
	$("#endDay").val(toDate(new Date()).slice(0, 10));
	$("#endTime").val(toDate(new Date()).slice(11, 16));

	// 초기화면 표시
	time();
	$("#reChart").trigger("click");

	// 선 그래프 정의
	var ctx = $("#line");
	lineChart = new Chart(ctx, {
		type : 'line',
		data : {
			labels : chartLabels,
			datasets : [ {
				label : "Traffic",
				lineTension : 0.3,
				backgroundColor : "rgba(78, 115, 223, 0.05)",
				borderColor : "rgba(78, 115, 223, 1)",
				pointRadius : 3,
				pointBackgroundColor : "rgba(78, 115, 223, 1)",
				pointBorderColor : "rgba(78, 115, 223, 1)",
				pointHoverRadius : 3,
				pointHoverBackgroundColor : "rgba(78, 115, 223, 1)",
				pointHoverBorderColor : "rgba(78, 115, 223, 1)",
				pointHitRadius : 10,
				pointBorderWidth : 2,
				data : chartData
			} ]
		},
		options : {
			maintainAspectRatio : false,
			layout : {
				padding : {
					left : 10,
					right : 25,
					top : 25,
					bottom : 0
				}
			},
			scales : {
				xAxes : [ {
					gridLines : {
						display : false,
						drawBorder : false
					}
				} ],
				yAxes : [ {
					ticks : {
						beginAtZero : true
					}
				} ]
			},
			legend : {
				display : false
			},
		}
	});

	// 막대 그래프 정의
	var ctx2 = $("#bar");
	barChart = new Chart(ctx2, {
		type : 'bar',
		data : {
			labels : [ "0-3", "3-6", "6-9", "9-12", "12-15", "15-18", "18-21",
					"21-24" ],
			datasets : [ {
				label : "Request",
				backgroundColor : "#4e73df",
				hoverBackgroundColor : "#2e59d9",
				borderColor : "#4e73df",
				data : barData
			} ],
		},
		options : {
			maintainAspectRatio : false,
			layout : {
				padding : {
					left : 10,
					right : 25,
					top : 25,
					bottom : 0
				}
			},
			scales : {
				xAxes : [ {
					gridLines : {
						display : false,
						drawBorder : false
					}
				} ],
				yAxes : [ {
					ticks : {
						beginAtZero : true
					},
					gridLines : {
						color : "rgb(234, 236, 244)",
						zeroLineColor : "rgb(234, 236, 244)",
						drawBorder : false
					}
				} ],
			},
			legend : {
				display : false
			},
			tooltips : {
				titleMarginBottom : 10,
				titleFontColor : '#6e707e',
				titleFontSize : 14,
				backgroundColor : "rgb(255,255,255)",
				bodyFontColor : "#858796",
				borderColor : '#dddfeb',
				borderWidth : 1,
				xPadding : 15,
				yPadding : 15,
				displayColors : false,
				caretPadding : 10,
			},
		}
	});
}

// Bar 새로고침 버튼 이벤트
function time() {
	$("#reChart").click(function() {
		$.ajax({
			url : "time",
			type : "GET",
			success : function(data) {
				for ( var str in data) {
					barData.shift();
					barData.push(data[str]);
				}
				barChart.update();
			},
			error : function() {
				/*alert("BarChart Error!!");*/
			}
		});
	});
}

// 트래픽 현황
function content() {
	var formData = $("#search").serialize();
	$.ajax({
		url : "content",
		type : "GET",
		data : formData,
		success : function(data) {
			$("#content").empty();
			for (var i = 0; i < data.length; i++) {
				var tr = $("<tr></tr>").appendTo($("#content"));
				$("<td></td>").text(i+1).appendTo(tr);
				$("<td></td>").text(data[i].ip).appendTo(tr);
				$("<td></td>").text(toDate(data[i].startTime)).appendTo(tr);
				$("<td></td>").text(toDate(data[i].endTime)).appendTo(tr);
			}
		},
		error : function() {
			/*alert("Content Error!");*/
		}
	});
}

// line그래프
function chart() {
	$.ajax({
		url : "graph",
		type : "GET",
		success : function(data) {
			chartData.push(data);
			chartLabels.push(data);

			if (chartData.length > 8) {
				chartData.shift();
				chartLabels.shift();
			}
			lineChart.update();
		},
		error : function() {
			/*alert("LineChart Error!");*/
		}
	});
}

function rank() {
	$.ajax({
		url : "rank",
		type : "GET",
		success : function(data) {
			$("#rank").empty();
			for (var i = 0; i < data.length; i++) {
				var tr = $("<tr></tr>").appendTo("#rank");

				$("<th></th>").text(i + 1).appendTo(tr);
				$("<td></td>").text(data[i].content).appendTo(tr);
				$("<td></td>").text(data[i].cnt).appendTo(tr);
			}
		},
		error: function(){
			
		}
	})
}

// content 초기화 버튼 이벤트
$(function() {
	$("#reset").click(function() {
		$("#startDay").val(toDate(new Date()).slice(0, 10));
		$("#startTime").val('00:00');
		$("#endDay").val(toDate(new Date()).slice(0, 10));
		$("#endTime").val(toDate(new Date()).slice(11, 16));
	});
});

function toDate(timestamp) {
	var date = new Date(timestamp);
	var year = date.getFullYear(), month = date.getMonth() + 1, day = date
			.getDate(), hour = date.getHours(), min = date.getMinutes(), sec = date
			.getSeconds();

	var result = year + "-" + (month < 10 ? "0" + month : month) + "-"
			+ (day < 10 ? "0" + day : day) + " "
			+ (hour < 10 ? "0" + hour : hour) + ":"
			+ (min < 10 ? "0" + min : min) + ":" + (sec < 10 ? "0" + sec : sec);

	return result;
}