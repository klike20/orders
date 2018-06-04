<html>
<head>
<script src="jquery-3.3.1.min.js"></script>
<script>
$( document ).ready(function() {
var i = 0;
var myusername = $("#username").val();
$.ajax({
  type: "GET",
  url: "rest/companies",
  data: myusername,
  cache: false,
  success: function(data){
     $("#resultarea").text(data);
  }
});

$( "#registerDiv" ).hide();
$( "#productsDiv" ).hide();
$( "#productDiv" ).hide();

$( "#login" ).click(function() {
	var myusername = $("#username").val();
	$.ajax({
		  type: "POST",
		  url: "/rest/companies",
		  data: myusername,
		  cache: false,
		  success: function(data){
		     $("#resultarea").text(data);
		  }
		});
});
	
$( "#register" ).click(function() {
	$( "#loginDiv" ).hide();
	$( "#registerDiv" ).show();
});

$( "#submitRegister" ).click(function() {

	var company = $("#company").val();
	var user = $("#user").val();
	var phone = $("#phone").val();
	var email = $("#email").val();
	var theData = "{"+company+","+user+","+phone+","+email+"}";
	
	alert(theData);
	
	$.ajax({
		  type: "POST",
		  url: "rest/register",
		  data: theData,
		  cache: false,
		  success: function(data){
		     $("#resultarea").text("Register success: "+data);
		     $( "#registerDiv" ).hide();
		     $( "#productsDiv" ).show();
		  }
		});
 });
 
 $( "#addProduct" ).click(function() {
	
	 $( "#productsDiv" ).hide();
     $( "#productDiv" ).show();

	
 });
 
 $( "#add" ).click(function() {
	 
	 var productName = $("#productName").val();
	 var unit = $("#unit").val();
	 var amount = $("#amount").val();
	 var obs = $("#obs").val();
	 $("#products").append('<li><input type="hidden" name="productsItem" value="'+productName+","+unit+","+amount+","+obs+'">'+productName+" "+unit+" "+amount+" "+obs+' <input type="button" value="-" onclick="deleteItem(this);" /></li>');
	 i++;
	 $( "#productsDiv" ).show();
     $( "#productDiv" ).hide();

	
 });
 
 $( "#send" ).click(function() {
	 var order = "";
	 $('[name="productsItem"]').each(function() {
		  order = order + $( this ).val() + ";";
		});
	 
	 alert(order);
	 
	 $.ajax({
		  type: "POST",
		  url: "rest/saveOrder",
		  data: order,
		  cache: false,
		  success: function(data){
		     $("#resultarea").text("Register success: "+data);
		  }
		});
	 
  });
  
 
});

function deleteItem(item) {
	 alert(item);
	 $(item).parent().remove();
}
</script>
</head>
<body>
	<div id="loginDiv">
		Usuario: <input type="text" name="username" id="username">
		<button type="button" id="login">Entrar</button>
		<button type="button" id="register">Registrarse</button>
	</div>
	<div id="registerDiv">
		Nombre de la empresa: <input type="text" name="company" id="company">
		Usuario: <input type="text" name="user" id="user">
		Telefono: <input type="text" name="phone" id="phone">
		Email: <input type="text" name="email" id="email">
		<button type="button" id="submitRegister">Registro</button>
	</div>
	<div id="productsDiv">
		Agregar producto: <button type="button" id="addProduct">+</button>
		
		<ul id="products">
		</ul>
		
		<button type="button" id="send">Enviar</button>
	</div>
	<div id="productDiv">
		Producto: <input type="text" name="productName" id="productName">
		Unidad: <select name="unit" id="unit"> <option value="kg">kg</option>  <option value="und">und</option></select>
		Cantidad: <input type="text" name="amount" id="amount">
		Observaciones: <input type="text" name="obs" id="obs">
		<button type="button" id="add">Agregar</button>
	</div>
	<h2>Hello World!!</h2>
	<div id="resultarea"></div>
</body>
</html>
