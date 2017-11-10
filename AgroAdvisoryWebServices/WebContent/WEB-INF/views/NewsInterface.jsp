<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Fresh News Update</title>
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script
	src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>


<script type="text/javascript"
	src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.8.11/jquery-ui.min.js"></script>
	<script>
	$(document).ready(function() {
		$('#datePicker').datepicker({
			todayHighlight : true,
			autoclose : true,
			format : 'dd/mm/yyyy'
		});
	});
</script>
<script type="text/javascript" src="https://code.jquery.com/jquery-1.11.3.min.js"></script>

	<!-- Include Date Range Picker -->
	<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.4.1/js/bootstrap-datepicker.min.js"></script>
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.4.1/css/bootstrap-datepicker3.css" />
 <script type="text/javascript">
function stateSelect(value){
	alert(value);
	
	$.ajax({
		url :'getDists',
		   data:{
			   dist:value
	        	},
	        success: function(data1){
	        	$('#districtList').append(data1);
	        	
	        }	
		
		
	})
}
 </script>

</head>

<body>
	<div class="container">
		<div id="newsInsert">
		
		<center> <h1>Save News </h1></center>
		
			<form:form action="saveNews" commandName='initBean'>

				<div class="col-xs-10 col-md-10 col-lg-10 col-sm-10"
					style="margin: 40px; auto; ">
					<!-- <label class="control-label col-xs-4 col-md-4 col-lg-4 col-sm-4">News
						Title :-</label> <input
						class=" col-xs-8 col-md-8 col-lg-8 col-sm-8"
						type="text" name="newsTitle"> -->
						
						<form:label  class="control-label col-xs-4 col-md-4 col-lg-4 col-sm-4" path="newsTitle">News Title :-</form:label>
						<form:input class=" col-xs-8 col-md-8 col-lg-8 col-sm-8" path="newsTitle"/>
				</div>
				
				
				
				<div class="col-xs-10 col-md-10 col-lg-10 col-sm-10"
					style="margin: 40px; auto; ">
					<!-- <label class="control-label col-xs-4 col-md-4 col-lg-4 col-sm-4">News
						Title :-</label> <input
						class=" col-xs-8 col-md-8 col-lg-8 col-sm-8"
						type="text" name="newsTitle"> -->
						
						<form:label  class="control-label col-xs-4 col-md-4 col-lg-4 col-sm-4" path="newsType">News Type :-</form:label>
						<form:input class=" col-xs-8 col-md-8 col-lg-8 col-sm-8" path="newsType"/>
				</div>
				
				
				<div class="col-xs-10 col-md-10 col-lg-10 col-sm-10"
					style="margin: 40px; auto; ">
					<!-- <label class="control-label col-xs-4 col-md-4 col-lg-4 col-sm-4">Crop Name
					 :-</label> <select class=" col-xs-8 col-md-8 col-lg-8 col-sm-8" name="cropName"></select> -->
					 
					 <form:label class="control-label col-xs-4 col-md-4 col-lg-4 col-sm-4" path="cropName">Crop :-</form:label>
						<form:select class=" col-xs-8 col-md-8 col-lg-8 col-sm-8" path="cropName" items="${cropNameList}"> </form:select>
				</div>
				
					<div class="col-xs-10 col-md-10 col-lg-10 col-sm-10"
					style="margin: 40px; auto; ">
					<!-- <label class="control-label col-xs-4 col-md-4 col-lg-4 col-sm-4">States
					 :-</label> <select class=" col-xs-8 col-md-8 col-lg-8 col-sm-8" name="states"></select> -->
					 <form:label class="control-label col-xs-4 col-md-4 col-lg-4 col-sm-4" path="state">states :-</form:label>
						<form:select onchange="stateSelect(value)" class=" col-xs-8 col-md-8 col-lg-8 col-sm-8" path="state" items="${stateList}"/>
				</div>
				
				<div class="col-xs-10 col-md-10 col-lg-10 col-sm-10"
					style="margin: 40px; auto; ">
					<form:label class="control-label col-xs-4 col-md-4 col-lg-4 col-sm-4" path="district">District :-</form:label>
						<form:select id='districtList' class=" col-xs-8 col-md-8 col-lg-8 col-sm-8" path="district" ><form:option value="All">All</form:option> </form:select>
				</div>
				
				<div class="col-xs-10 col-md-10 col-lg-10 col-sm-10"
					style="margin: 40px; auto; ">
					<form:label path="date" class="control-label col-xs-4 col-md-4 col-lg-4 col-sm-4">Date
					 :-</form:label> <div class="input-group input-append date" id="datePicker" >
							<form:input path="date" class="col-xs-12 col-md-12 col-lg-12 col-sm-12" /> <span
								class="input-group-addon add-on"><span
								class="glyphicon glyphicon-calendar"></span></span>
						</div>
				</div>
				
				
				<div class="col-xs-10 col-md-10 col-lg-10 col-sm-10"
					style="margin: 40px; auto; ">
					<form:label path="message" class="control-label col-xs-4 col-md-4 col-lg-4 col-sm-4">Message
					 :-</form:label> <form:textarea rows="10" cols="70" path="message"></form:textarea>
				</div>
				
				<div class="col-xs-10 col-md-10 col-lg-10 col-sm-10"
					style="margin: 40px; auto; ">
					<form:label class="control-label col-xs-4 col-md-4 col-lg-4 col-sm-4" path="link">Link :-</form:label> <form:input
						class=" col-xs-8 col-md-8 col-lg-8 col-sm-8"
						type="text" path="link"/>
				</div>
				
				<!-- <div class="col-xs-10 col-md-10 col-lg-10 col-sm-10"
					style="margin: 40px; auto; ">
					<label class="control-label col-xs-4 col-md-4 col-lg-4 col-sm-4">Image :-</label> <input
						class=" col-xs-8 col-md-8 col-lg-8 col-sm-8"
						type="file" name="image">
				</div>
				 -->
				
				<div class="col-xs-10 col-md-10 col-lg-10 col-sm-10"
					style="margin: 40px; auto; ">
					 <center><input
						
						type="submit" value="Save Data"></center>
				</div>
				
			</form:form>
		</div>



	</div>
</body>
</html>