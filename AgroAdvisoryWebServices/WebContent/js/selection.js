
/**
 * Vicky
 */

function getStations(){	
	var st=$('#statetype').val();
	if(st==0){
		alert('Please Select State.');
	}
	else{
		$.ajax({
			type:"POST", 
			url:"CropValue.html",
			data:{state : st},
			success:function(res){			
				 $('#stationtype').empty();			
				$('#stationtype').append(res);
				},
				error: function(){				
			        alert('Error: ');
			        alert(args[0]);
	            }		
			});		
	}
	
	
}
function getStates(){
	var cpid=$('#crop-type').val();
	if(cpid==0){
		alert('please select crop');
	}
	else{
		$.ajax({
			type:"POST",
			url:"getState",
			data:{cropId : cpid},
			success:function(res){
				$('#statetype').empty();
				$('#stationtype').empty();
				$('#cropvariety').empty();
				$('#statetype').append(res);
				},
				error: function(){				
			        alert('Not found');		       
	            }
			});
	}
	
}
function getVariety(){
	var st=$('#statetype').val();
	var crpid=$('#crop-type').val();
	if(st==0){
		alert('Please select crop variety');
	}
	else{
		$.ajax({
			type:"POST", 
			url:"GetCropVariety.html",
			data:{
				state : st,
				cropid : crpid
				},
			success:function(res){			
				 $('#cropvariety').empty();			
				$('#cropvariety').append(res);
				},
				error: function(){				
			        alert('Not Found ');		       
	            }		
			});
	}
	
}


function recoverPassword(){
	var userid=$('#email').val();	
	if(userid == "" || userid == null){
		alert("Please Fill Email id");		
	}
	else{
		alert(userid);
		$.ajax({
			type:"POST", 
			url:"GettingPassword.html",
			data:{userEmail : userid},
			success:function(res){			
				 alert(res);
				},
				error: function(){				
			        alert('Error: ');
			        alert(args[0]);
	            }		
			});
	}
}


