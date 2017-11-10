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
<title>Buyer Seller Approval</title>
</head>
<body>
	<div class="container">
	  <h1 class="text-center"> User Posts</h1>
	  <br><br>
	  <select id="dates" class="col-md-12 col-lg-12">
	       <c:forEach var="date" items="${dates}">
	        <option>${date}
	       </c:forEach>
	      
	  </select>
	     <div id="posts">
	     <c:forEach var="bean" items='${beanlist }'>
			<div class="col-md-12 col-lg-12 ">
				<c:if test="${bean.type == 'seller'}">

					<img alt="" src="seller/${bean.imageName}"
						class="col-md-4 col-lg-4" height="300px">
				</c:if>

				<c:if test="${bean.type == 'rent'}">

					<img alt="" src="renter/${bean.imageName}"
						class="col-md-4 col-lg-4" height="300px">
				</c:if>
				<div class="col-lg-8 col-md-8">

					<h4>${bean.name}</h4>
					<h4>${bean.phoneNo}</h4>
					<h4>${bean.itemName}</h4>
					<h4>price :- ${bean.price}</h4>
					<h4>${bean.type}| Posted on :- ${bean.postDate}</h4>
					<h4>Description :- ${bean.description}</h4>
					<c:if test="${bean.approval}">
						<p>Approved &#9989;</p>
						<button onclick="disapprove(${bean.id},'${bean.type}')">
							Disapprove</button>
					</c:if>
					<c:if test="${!bean.approval}">
						<button onclick="approve(${bean.id},'${bean.type}')">Approve</button>
					</c:if>
				</div>

			</div>
			<br>
			<br>
			<h4>_____________________________________________________________________________________________________________________________</h4>

		</c:forEach>

	     
	     
	     
	     </div>
		

	</div>

</body>
<script type="text/javascript">
 function approve(id,type){
	 alert('clicked');
	 var url='buyerseller/updateapproval/'+id+'/'+type;
	$.ajax({
		url:url,
		success:function(result){
			
			location.reload();
		}
	})
	
	 
 }
 
 function disapprove(id,type){
	 alert('clicked');
	 var url='buyerseller/disapproval/'+id+'/'+type;
	$.ajax({
		url:url,
		success:function(result){
			
			location.reload();
		}
	})
	
	 
 }
 
$('#dates').change(function(){
	var date=this.value;
	alert(date);
	var url='getposts/'+date
	$.ajax({
		
		url:url,
		success:function(result){
			
			var posts=result;
			$('#posts').empty();
			for(i=0;i<posts.length;i++){
				if(posts[i].type=='seller')
				{
					$('#posts').append("<img alt='' src=seller/"+posts[i].imageName+" class='col-md-4 col-lg-4' height='300px'>");
				}else
				{
					$('#posts').append("<img alt='' src=renter/"+posts[i].imageName+" class='col-md-4 col-lg-4' height='300px'>");
				}
				$('#posts').append("<div class='col-lg-8 col-md-8' id='sub-posts"+i+"'></div>");
				$("#sub-posts"+i).append("<h4>"+posts[i].name+"</h4>");
				$("#sub-posts"+i).append("<h4>"+posts[i].phoneNo+"</h4>");
				$("#sub-posts"+i).append("<h4>"+posts[i].itemName+"</h4>");
				$("#sub-posts"+i).append("<h4> Price :-"+posts[i].price+"</h4>");
				$("#sub-posts"+i).append("<h4> "+posts[i].type+" | "+ posts[i].date+"</h4>");
				$("#sub-posts"+i).append("<h4> Description :-"+posts[i].desc+"</h4>");
				
				if(posts[i].approval){
					$("#sub-posts"+i).append("<p>Approved &#9989;</p>");
					$("#sub-posts"+i).append("<button pid="+posts[i].id+" ptype="+posts[i].type+" do='d'>Disapprove</button>");
				}
				else
				{
					$("#sub-posts"+i).append("<button pid="+posts[i].id+" ptype="+posts[i].type+" do='a' >Approve</button>");
				}
				
				
				$("#posts").append("<h4>______________________________________________________________________________</h4>");
			}
			
		}
	})
	
	
})
$('body').on('click','button',function(){
	var id=$(this).attr('pid');
	var type=$(this).attr('ptype');
	var d=$(this).attr('do');
	
	if(d=='a'){
		
		 var url='buyerseller/updateapproval/'+id+'/'+type;
			$.ajax({
				url:url,
				success:function(result){
					
					alert('approved');
				}
			})
	}
	else{
		 var url='buyerseller/disapproval/'+id+'/'+type;
			$.ajax({
				url:url,
				success:function(result){
					
					alert('disapproved');
				}
			})
	}
	
})
</script>
</html>