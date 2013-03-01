<?php
session_start();

if (!isset($_GET["pub_id"]) || empty($_GET["pub_id"])) {	//if none or an empty pub_id has been pushed redirect somewhere
	header ('Location: index.php');
}
include ("sjdlkjf/vmnbcm/qporuiow/mysqlaccess.php");

if (isset($_SESSION["ADMIN"]) && !empty($_SESSION["ADMIN"])) {
	if ($_SESSION["ADMIN"] == TRUE)
		$admin_rights = TRUE;
}
	

$mysql = mysqlConnect(); //create connection and select database
//Checking connection Connection
if ($mysql->connect_errno) {
	echo "Failed to connect to MySQL: (" . $mysql->connect_errno . ") " . $mysql->connect_error;
}


$pub_id = $mysql->escape_string($_GET["pub_id"]);

$sql_query = "SELECT * FROM Pubs WHERE id_pub = '$pub_id'";

$result = $mysql->query($sql_query);
if($result) {
	if($result_array = $result->fetch_assoc()) {
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

<head>
<title><?php echo $pub_name ?></title>
</head>

<body>
<div><h1><?php echo $pub_name ?></h1></div><?php if(admin_rights == TRUE): ?><a href="pubedit.php?edit=<?= $pub_id ?>">Edit</a><?php endif; ?>
<?php if (isset($pub_image) && !empty($pub_image) ) :?><div><p><img src="<?php echo $pub_image ?>" ></p></div><?php endif; ?>
<div><p>Address: <?php echo $pub_location ?></p></div>
<div><p>Position: <?php echo $pub_lat ?>  <?php echo $pub_long ?></p></div>
<div><p><img src="https://maps.googleapis.com/maps/api/staticmap?markers=<?php echo $pub_lat?>,<?php echo $pub_long?>&zoom=16&size=500x500&key=AIzaSyB2J5mFUg4TvrqmCVvTznGkcXh3ENf6-zk&sensor=false"></p></div>
<div><p>Description: <?php echo $pub_description ?></p></div>


</body>

</html>
