<?php
session_start();
include ("sjdlkjf/vmnbcm/qporuiow/mysqlaccess.php");

$mysql = mysqlConnect();
 //create connection and select database
$pub_name = $mysql->escape_string($_POST["pubName"]);
$pub_location = $mysql->escape_string($_POST["position"]);
$pub_description = $mysql->escape_string($_POST["pubDescription"]);
$lat = $mysql->escape_string($_POST["lat"]);
$long = $mysql->escape_string($_POST["long"]);
$file_location = "";

if(isset($_FILES["pubImage"]["name"]) && !empty($_FILES["pubImage"]["name"])) { 
	$guid = uniqid("",true);
	$target_path = "pubimg/";
	$file_extension = pathinfo($_FILES["pubImage"]["name"], PATHINFO_EXTENSION);
	$file_location = $target_path . $guid . "." . $file_extension;		//generated our new file location
	if (!move_uploaded_file($_FILES["pubImage"]["tmp_name"], $file_location)) {
		echo "Error saving image";
	}
}
else echo "pub image not uploaded " . $_FILES['pubImage']['name'];

if (!isset($_GET["pub_id"]) || empty($_GET["pub_id"])) {	//if none or an empty pub_id has been pushed redirect somewhere
	$sql = "INSERT INTO Pubs(pub_name, pub_image, pub_location, pub_description, up_rating, down_rating, latitude, longitude) VALUES ('$pub_name', '$file_location', '$pub_location', '$pub_description', 0, 0, $lat, $long)";
}
else if ( !isset($_SESSION["ADMIN"]) || empty($_SESSION["ADMIN"]) ) {
	$sql = "INSERT INTO Pubs(pub_name, pub_image, pub_location, pub_description, up_rating, down_rating, latitude, longitude) VALUES ('$pub_name', '$file_location', '$pub_location', '$pub_description', 0, 0, $lat, $long)";
}
else if ( $_SESSION["ADMIN"] == TRUE ) {
	$pub_id = $mysql->escape_string($_GET["pub_id"]);
	$sql = "INSERT INTO Pubs(pub_name, pub_image, pub_location, pub_description, up_rating, down_rating, latitude, longitude) WHERE pub_id = '$pub_id' VALUES ('$pub_name', '$file_location', '$pub_location', '$pub_description', 0, 0, $lat, $long)"; 
}


//Checking connection Connection
if ($mysql->connect_errno) {
	echo "Failed to connect to MySQL: (" . $mysql->connect_errno . ") " . $mysql->connect_error;
}

$result=$mysql->query($sql);
?>
<!DOCTYPE html>
<head>
<title>Pub Upload</title>
</head>
<body>
<?php 
	if($result) {
		echo "Pub Details Uploaded Successfully\n";
		$sql = "SELECT id_pub FROM Pubs WHERE pub_name = '$pub_name' AND pub_location = '$pub_location'";
		$result = $mysql->query($sql);
		if($result) {
			$pub_id = $result->fetch_assoc();
		}
	}
	else
	echo "Pub Details Upload Failed\n";

$mysql->close();
?> 
<br>
<a href="pubselect.html">Go Back and add another</a><br>
<a href="pubdetails.php?pub_id=<?= $pub_id["id_pub"] ?>">View the pub page</a>

</body>
</html>
