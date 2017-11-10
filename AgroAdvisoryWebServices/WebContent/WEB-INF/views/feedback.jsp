<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
<title>Insert title here</title>
</head>
<body>
	<!--  <audio controls> <source src='feedback/6666666666_Advisory_2017_05_10_15_05_14.wav' type="audio/mpeg"></audio>  -->
	<div class="container">
		<div class="header">
			<h1 class="text-center">User Feedback</h1>
			<hr>
			<hr>
			<label class="col-xs-6 col-md-6 col-sm-12 col-lg-6 ">Select
				Date (yyyy-MM-dd):-</label><select
				class="col-xs-6 col-md-6 col-sm-12 col-lg-6 " id="date-select">
				<c:forEach items="${dates}" var="dates">
					<option value="${dates}">${dates}</option>
				</c:forEach>
			</select>

		</div>

		<div id="feedback-table"
			class="col-xs-12 col-md-12 col-lg-12 col-sm-12"></div>
	</div>
</body>
<script type="text/javascript">
	$(document).ready(function() {
		$('#date-select').change(function() {

			var selectedDate = $('#date-select :selected').text();
			var url = 'feedbackOnDate/' + selectedDate;
			$.ajax({
				url : url,
				success : function(result) {
					$('#feedback-table').empty();
					$.each(result, function(key, value) {

						$('#feedback-table').append('<h3>' + key + '</h3>');
						$.each(value.audioFile, function(i, val) {
							//alert(val);
                               $('#feedback-table').append("<audio controls preload='none'> <source src='feedback/"+val+"' type='audio/wav' /></audio> &nbsp;"); 
                            /*  $('#feedback-table').append("<audio controls preload='none'> <source id="+key+"_"+i+" src='http://www.sample-videos.com/audio/mp3/india-national-anthem.mp3' type='audio/mp3' /></audio> &nbsp;");
						$('#'+key+"_"+i).attr("src","feedback/"+val); */
						})
						$('#feedback-table').append("<br>");
						$.each(value.imageFile, function(i, val) {
							//alert("image name"+val);
							$('#feedback-table').append("<img src='feedback/"+val+"' width='200' height='200'> &nbsp;");
						})

					});
				},
				error : function() {
					alert('Error: ' + arg[0]);
				}

			})

		});

	});
</script>
</html>