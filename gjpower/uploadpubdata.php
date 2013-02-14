<?php
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

$sql = "INSERT INTO Pubs(pub_name, pub_image, pub_location, pub_description, user_rating, latitude, longitude) VALUES ('$pub_name', '$file_location', '$pub_location', '$pub_description', 5, $lat, $long)";

//Checking connection Connection
if ($mysql->connect_errno) {
	echo "Failed to connect to MySQL: (" . $mysql->connect_errno . ") " . $mysql->connect_error;
}

$result=$mysql->query($sql);
?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
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
<a href="pubdetails.php?pub_id=<?php echo $pub_id["id_pub"] ?>">View the pub page</a>

</body>
</html>