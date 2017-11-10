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
<script type="text/javascript">


	function addShop(id,shopName,address,desc,phoneNo){
		//alert('clicked'+id+shopName+desc);
		 $.ajax({
			
			url:"verifyshop",
			data:{
				id:id,
				 'shopName':shopName,
				 'address':address,
				 'desc':desc,
				 'phoneNo':phoneNo
			},
		   success:function(result){
			   alert(result);
		   },
		   error:function(result){
			   alert(result);
		   }
			
		}); 
	}
	


</script>
</head>
<body>

	<div class="container">

		<c:forEach items="${beanList}" var="item">
			<div class="add-shop col-md-12 col-xs-12 col-sm-12 col-lg-12">
		<div class="col-md-4 col-lg-4"><img  class="img-responsive" src="feedback/${item.imageName }" alt="no-image"/></div>
				
				<div class="col-md-8 col-lg-8">
				<label>${item.id}</label>
				<label>Name :- ${item.name}</label><br> 
					<label>Phone Number :- ${item.phoneNo}</label><br> 
					 Shop Name :- <input type="text"value="${item.shopName}" name="shopName"> <br>
					 Address :- <input type="text"value="${item.address}" name="address"> <br>
					 Description :-<input type="text"	value="${item.description}" name="desc"> <br>
					 <label> Registered For :- ${item.type}</label>
					 <button onclick="addShop(${item.id},'${item.shopName}','${item.address}','${item.description}','${item.phoneNo}')">Approve</button>
		
				
				</div>
				
			</div>
			<br>
			<br>
  ______________________________________________________________________________________
  </c:forEach>


	</div>


</body>

</html>