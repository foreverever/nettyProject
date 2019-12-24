<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>대국민 투표 실시간 모니터링</title>
<style>
.tableFixHead {
	overflow-y: auto;
	height: 360px;
}

.talbeFixHead thead th {
	position: sticky;
	top: 0;
}
</style>

<!-- Custom fonts for this template-->
<link href="bootstrap/vendor/fontawesome-free/css/all.min.css"
	rel="stylesheet" type="text/css">
<link
	href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
	rel="stylesheet">

<!-- Custom styles for this template-->
<link href="bootstrap/css/sb-admin-2.min.css" rel="stylesheet">

<!-- Bootstrap core JavaScript-->
<script src="bootstrap/vendor/jquery/jquery.min.js"></script>
<script src="bootstrap/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

<!-- Core plugin JavaScript-->
<script src="bootstrap/vendor/jquery-easing/jquery.easing.min.js"></script>

<!-- Custom scripts for all pages-->
<script src="bootstrap/js/sb-admin-2.min.js"></script>

<!-- Page level plugins -->
<script src="bootstrap/vendor/chart.js/Chart.min.js"></script>

<script src="js/main.js"></script>
</head>

<body id="page-top">
	<div class="container-fluid">
		<div class="row">
			<div class="col-lg-5">
				<div class="card shadow mb-4">
					<div class="card-header py-3">
						<h6 class="m-0 font-weight-bold text-primary">실시간 트래픽 현황</h6>
					</div>
					<div class="card-body">
						<div class="chart-area">
							<canvas id="line"></canvas>
						</div>
						<hr>
					</div>
				</div>
			</div>
			<div class="col-lg-5">
				<div class="card shadow mb-4">
					<div class="card-header py-3">
						<h6 class="m-0 font-weight-bold text-primary">
							시간별 트래픽
							<button id="reChart" type="button"
								class="btn-dark btn-sm float-right">새로고침</button>
						</h6>
					</div>
					<div class="card-body">
						<div class="chart-bar">
							<canvas id="bar"></canvas>
						</div>
						<hr>
					</div>
				</div>
			</div>
			<div class="col-lg-2">
				<div class="card shadow mb-4">
					<div class="card-header py-3">
						<h6 class="m-0 font-weight-bold text-primary">실시간 투표현황</h6>
					</div>
					<div class="card-body">
						<div class="table-responsive">
							<div class="tableFixHead">
								<table class="table table-bordered" id="dataTable" width="100%"
									cellspacing="0">
									<thead>
										<tr>
											<th>순위</th>
											<th>번호</th>
											<th>득표수</th>
										</tr>
									</thead>
									<tbody id="rank"></tbody>
								</table>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-lg-12">
				<div class="card shadow mb-4">
					<div class="card-header py-3">
						<h6 class="m-0 font-weight-bold text-primary">트래픽 처리현황</h6>
					</div>
					<div class="card-body">
						<div class="table-responsive">
							<form id="search">
								<b>조회기간 </b> <input id="startDay" type="date" name="startDay">
								<input id="startTime" type="time" name="startTime"> - <input
									id="endDay" type="date" name="endDay"> <input
									id="endTime" type="time" name="endTime">
								<button id="search" onclick="content(); return false;"
									type="button" class="btn-dark btn-sm">조회</button>
								<button id="reset" type="button" class="btn-dark btn-sm">초기화</button>
							</form>
							<br>
							<div class="tableFixHead">
								<table class="table table-bordered" id="dataTable" width="100%"
									cellspacing="0">
									<thead>
										<tr>
											<th>NO</th>
											<th>요청IP</th>
											<th>요청시간</th>
											<th>처리시간</th>
										</tr>
									</thead>
									<tbody id="content"></tbody>
								</table>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>