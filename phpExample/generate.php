<?php
if(!isset($_SESSION)) {
	session_start();
}
function generateNewKey() {	//placeholder function for generating a new key
	//MySQL magicry;
	$newKey = "cake";
	return $newKey;
}
?>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Generated Crawl Key</title>
</head>

<body>
<div>
<?php 
echo generateNewKey(); //generate key is printed using the function
?>
</div>
<?php include("login.php"); //run data from login page
?>



</body>
</html>