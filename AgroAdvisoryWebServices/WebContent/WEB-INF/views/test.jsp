<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>


<body>

<button id='btn'>Submit</button>

</body>
<script type="text/javascript">

$('#btn').click(function(){
	alert('alert');
$.ajax({
	url : 'getUserRegistration',
	type : 'POST',
	data :{ name :'hell yeah',
		    phone: '0909090913',
		    sowingdate:'01/12/2016',
		    crop: 'Mustard',
		    state :'Andhra Pradesh',
		    district : 'Avagudem'
	},
	success : function(data){
		 alert(data);
	}
	
});
});
</script>

</html>