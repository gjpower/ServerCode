<?php 
if(!isset($_SESSION)) {	//if session is not set
	session_start();	//start a session this allows logging in and tracking the login 
}
?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Login/Registration</title>
</head>
<body>
<h1>Login/Register</h1>
<form action="generate.php" method="post" enctype="multipart/form-data" name="New Registration"><input name="generate" type="submit" value="Generate login" /></form>	<!-- sends nothing to generate.php -->
<form action="authenticate.php" method="post" enctype="multipart/form-data" name="login"><input name="loginkey" type="text" value="Enter your crawlkey here" maxlength="32" /><input name="submit" type="submit" value="Submit" /></form> <!-- sends login data to authenticate.php -->

</body>
</html>