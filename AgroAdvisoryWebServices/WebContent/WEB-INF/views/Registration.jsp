<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<!-- #include -->
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script
	src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

<script type="text/javascript"
	src="https://code.jquery.com/jquery-1.11.3.min.js"></script>
<script type="text/javascript"
	src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.8.11/jquery-ui.min.js"></script>
<title>User Registration</title>
<style>
.btn {
	background-color: #4CAF50; /* Green */
	border: none;
	color: white;
	padding: 15px 32px;
	text-align: center;
	text-decoration: none;
	display: inline-block;
	font-size: 16px;
	margin: 4px 3px;
	cursor: pointer;
	-webkit-transition-duration: 0.4s; /* Safari */
	transition-duration: 0.4s;
}

.bt {
	background-color: #4CAF50; /* Green */
	border: none;
	color: white;
	padding: 10px 24px;
	text-align: center;
	text-decoration: none;
	display: inline-block;
	font-size: 14px;
	margin: 4px 3px;
	cursor: pointer;
	-webkit-transition-duration: 0.4s; /* Safari */
	transition-duration: 0.4s;
}

.btn2:hover {
	box-shadow: 0 12px 16px 0 rgba(0, 0, 0, 0.24), 0 17px 50px 0
		rgba(0, 0, 0, 0.19);
}
</style>

<script type="text/javascript">
	function numbersonlyT(e) {
		var unicode = e.charCode ? e.charCode : e.keyCode;

		if (unicode === 46 || unicode === 39) {
			return true;
		}

		if (unicode !== 8) { //if the key isn't the backspace key (which we should allow)
			if (unicode !== 45) {
				if (unicode<48||unicode>57) {//if not a number
					return false; //disable key press
				}
			}
		}
	}
</script>
<script type="text/javascript">
	$(document).ready(function() {
		$("#myBtn").click(function() {
			alert('ffff');
			var str = $("form").serialize();
			$.ajax({
				type : "POST",
				url : "getUserRegistrations",
				data : str,
				success : function(result) {
					alert(result);
				},
				error : function() {
					alert('Error: ' + arg[0]);
				}
			});
			/* var skyval = $("#sky").val();
			if(skyval==1){
				skyval="Partly Cloud";
			}
			else if(skyval==2){
				skyval="Overcast";
			}
			else{
				skyval="Clear Sky";
			}
			var rainval= $("#rainfall").val();
			if(rainval==1){
				rainval="Yes";
			}
			else{
				rainval="No";
			}		    	
			var obserName="Observer Name : "+$("#obser").val();
			var ghi="GHI : "+$("#ghi").val();
			var ac="AC Output : "+$("#ac").val();
			var sky="Sky : "+skyval;
			var rainfall="Rainfall : "+rainval;
			var obtime="Observer Time : "+$("#obtime").val();
			var data=ghi+'<br>'+ac+'<br>'+sky+'<br>'+rainfall+'<br>'+obtime+'<br>'+obserName;    	
			document.getElementById("ob").innerHTML = data;   
			$("#myModal").modal('show'); */

		});
	});
</script>
<script type="text/javascript">
	function getStates() {
		var cpid = $('#crop-type').val();
		var stlist = " ";
		if (cpid == 0) {
			alert('please select crop');
		} else {
			$.ajax({
				type : "Get",
				url : "getStates",
				data : {
					cropId : cpid
				},
				success : function(res) {
					$('#statetype').empty();
					$('#districttype').empty();
					$('#stationtype').empty();
					$('#villagetype').empty();
					$('#statetype').append(res);
				},
				error : function() {
					alert('Not found');
				}
			});
		}

	}
	function getDistrict() {
		var cpid = $('#statetype').val();
		if (cpid == 0) {
			alert('please select crop');
		} else {
			$.ajax({
				type : "Get",
				url : "getDists",
				data : {
					dist : cpid
				},
				success : function(res) {
					$('#districttype').empty();
					$('#stationtype').empty();
					$('#villagetype').empty();
					$('#districttype').append(res);
				},
				error : function() {
					alert('Not found');
				}
			});
		}

	}
	function getStations() {
		var stns = $('#statetype').val();
		var dists = $('#districttype').val();
		$.ajax({
			type : "Get",
			url : "getStns",
			data : {
				dists : dists,
				statn : stns

			},
			success : function(res) {
				$('#stationtype').empty();
				$('#villagetype').empty();
				$('#stationtype').append(res);
			},
			error : function() {
				alert('Not found');
			}
		});

	}
	function getVillages() {
		var stns = $('#statetype').val();
		var dists = $('#districttype').val();
		var tehsils = $('#stationtype').val();
		$.ajax({
			type : "Get",
			url : "getVillages",
			data : {
				dists : dists,
				statn : stns,
				tehsil : tehsils 

			},
			success : function(res) {
				$('#villagetype').empty();
				$('#villagetype').append(res);
			},
			error : function() {
				alert('Not found');
			}
		});

	}
</script>
<script>
	$(document).ready(function() {
		$('#datePicker').datepicker({
			todayHighlight : true,
			autoclose : true,
			format : 'dd/mm/yyyy'
		});
	});
</script>
</head>
<body>
	<div class="container">
		<form id="form">
			<div class="col-xs" style="margin-top: 45px;">
				<center>
					<label style="font-size: 18px; color: black;">Registration
						Form</label>
				</center>
			</div>
			<div class="form-horizontal" style="margin-top: 25px;">
				<div class="form-group">
					<label class="control-label col-xs-4">User Name</label>
					<div class="col-xs-4">
						<input id="name" name="name" class="form-control" type="text" />
						<!-- <input id="ghi" class="form-control" type="text" name="ghi" onkeypress="return numbersonlyT(event);" pattern="^[1-9]\d*(\.\d+)?$"> -->
					</div>

				</div>
				<div class="form-group">
					<label class="control-label col-xs-4">Phone No.</label>
					<div class="col-xs-4">
						<input type="text" id="phoneNo" name="phone" maxlength="10" class="form-control"
							onkeypress="return numbersonlyT(event);">
					</div>

				</div>
				<div class="form-group">
					<label class="control-label col-xs-4">Crop Name</label>
					<div class="col-xs-4">
						<select id="crop-type" class="form-control" name="crop"
							onchange="getStates()">
							<option value="0">--- Please select ---</option>
							<c:forEach var="cplist" items="${crplist}">
								<option value="${cplist.cropId }">${cplist.cropname }</option>
							</c:forEach>
						</select>
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-xs-4">State</label>
					<div class="col-xs-4">
						<select id="statetype" class="form-control" name="state"
							onchange="getDistrict()">
							<option value="">--- Please select ---</option>
						</select>
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-xs-4">District</label>
					<div class="col-xs-4">
						<select id="districttype" class="form-control" name="district"
							onchange="getStations()">
							<option value="">--- Please select ---</option>
						</select>
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-xs-4">Tehsil</label>
					<div class="col-xs-4">
						<select id="stationtype" class="form-control" name="tehsil" onchange="getVillages()">
							<option value="">--- Please select ---</option>
						</select>
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-xs-4">Village</label>
					<div class="col-xs-4">
						<select id="villagetype" class="form-control" name="villageId">
							<option value="">--- Please select ---</option>
						</select>
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-4 control-label">Sowing Date</label>
					<div class="col-xs-4 date">
						<div class="input-group input-append date" id="datePicker">
							<input type="text" class="form-control" name="sowingdate" /> <span
								class="input-group-addon add-on"><span
								class="glyphicon glyphicon-calendar"></span></span>
						</div>
					</div>
				</div>
								<div class="form-group">
					<center>
						<button id="myBtn" type="button" class="btn btn2">Register</button>
					</center>
				</div>
			</div>
		</form>
	</div>
	<!-- Extra JavaScript/CSS added manually in "Settings" tab -->
	<!-- Include jQuery -->
	<script type="text/javascript" src="https://code.jquery.com/jquery-1.11.3.min.js"></script>

	<!-- Include Date Range Picker -->
	<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.4.1/js/bootstrap-datepicker.min.js"></script>
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.4.1/css/bootstrap-datepicker3.css" />

	
</body>
</html>