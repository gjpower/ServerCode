<?php
session_start();
if (isset($_SESSION["ADMIN"]) && !empty($_SESSION["ADMIN"])) {	//check if you should be allowed here
	if ($_SESSION["ADMIN"] == TRUE)
		$admin_rights = TRUE;
}
else {
	header ('Location: index.php');	//if not send them away
}
if ( !isset($_GET["edit"]) || empty($_GET["edit"]) ) {
	header ('Location: index.php');
}

include ("sjdlkjf/vmnbcm/qporuiow/mysqlaccess.php");
$mysql = mysqlConnect(); //create connection and select database
//Checking connection Connection
if ($mysql->connect_errno) {
	echo "Failed to connect to MySQL: (" . $mysql->connect_errno . ") " . $mysql->connect_error;
}


$pub_id = $mysql->escape_string($_GET["edit"]);

$sql_query = "SELECT * FROM Pubs WHERE id_pub = '$pub_id'";

$result = $mysql->query($sql_query);
if($result) {
	if($result_array = $result->fetch_assoc()) {
		$pub_id = $result_array["pub_id"];
		$pub_name = $result_array["pub_name"];
		$pub_location = $result_array["pub_location"];
		$pub_lat = $result_array["latitude"];
		$pub_long = $result_array["longitude"];
		$pub_description = $result_array["pub_description"];
		$pub_image = $result_array["pub_image"];
	}
	else header ('Location: index.php');	//if query returns nothing redirect to homepage
}
else header ('Location: index.php');		//if query fails redirect

?>


<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title></title>
<link rel="stylesheet" type="text/css" href="theStyleSheet.css">

<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />

    <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyB2J5mFUg4TvrqmCVvTznGkcXh3ENf6-zk&sensor=true"></script>
    <script src="theJavaScript.js"></script>


<body>

		<form action="uploadpubdata.php?pub_id=<?= $pub_id ?>" method="post" enctype="multipart/form-data">
		Pub Name:<input type="text" name="pubName" value="<?= $pub_name ?>" class="smallInput" /><br>
		Profile Image:<input type="file" name="pubImage" /><br>
		Address:<br><textarea name="position" id="address" value="<?= $pub_location ?>" rows="5" cols="20"></textarea>
		<p>Please use format "Street with optional number, City Area, City, Country<br> e.g College Green, Dublin 2, Co. Dublin, Ireland</p>
		<input type="button" name="useMap" value="Correct Address" onclick="getLocation()" /><input type="button" name="useCurrent" value="Use current location" onclick="useCurrentLocation()" /><br>
		<label for="lat">Latitude:</label><input type="text" name="lat" value="<?= $pub_lat ?>" id="lat"/><label for="long">Longitude:</label><input type="text" name="long" value="<?= $pub_long ?>" id="long"/><br>
						<div id="maps_api"></div>
				
		<label for="pubDescription">Pub Description:</label><br><textarea name="pubDescription" value="<?= $pub_description ?>" class="largeDescription" cols="40" rows="10" maxlength="250"></textarea><br>
		<input type="submit" name="submit" value="submit" />
		
		</form>
		
		




</body>
</html>
