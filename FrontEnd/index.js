function onSignIn(googleUser) {
	var profile = googleUser.getBasicProfile();
	var id_token = googleUser.getAuthResponse().id_token;
  	console.log('1 : ID: ' + profile.getId()); // Do not send to your backend! Use an ID token instead.
  	console.log('2 : Name: ' + profile.getName());
  	console.log('3 : Image URL: ' + profile.getImageUrl());
  	console.log('4 : Email: ' + profile.getEmail()); // This is null if the 'email' scope is not present.
  	console.log('5 : Token: ' + id_token);
  	
  
	$(".g-signin2").css("display","none");
	$(".data").css("display","block");
	$("#loadEvent").css("display","block");
	$("#pic").attr('src',profile.getImageUrl());
	$("#email").text(profile.getEmail());
	$("#profile").text(profile.getId());
	
	var url = 'https://httpbin.org/post';
	//var url = 'http://ec2-34-221-77-242.us-west-2.compute.amazonaws.com:5000/post';
	var xhr = new XMLHttpRequest();
	xhr.open('POST',url,true)
	xhr.send();
	
	xhr.onreadystatechange = processRequest;
	
	function processRequest(e){
		if (xhr.readyState == 4 && xhr.status == 200)
		{
			console.log(xhr.responseText);
			var myArr = JSON.parse(this.responseText);
    		myFunction(myArr);
			//alert(xhr.responseText);
		}
		else
		{
			alert(xhr.status);
		}
	}
	
	function myFunction(arr) {
	alert(arr.origin);
  	var out = "";
  	var i;
  	for(i = 0; i < arr.length; i++) {
    	out += '<a href="' + arr[i].url + '">' + 
    	arr[i].display + '</a><br>';
  	}
	}
	
}

function signOut() {
    var auth2 = gapi.auth2.getAuthInstance();
    auth2.signOut().then(function () {
    $(".g-signin2").css("display","block");
	$(".data").css("display","none");
      console.log('User signed out.');
    });
    alert("User Signed Out");
  }
  
  
