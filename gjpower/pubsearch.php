<?php
if(!isset($_SESSION)) {
	session_start();
}
include ("sjdlkjf/vmnbcm/qporuiow/mysqlaccess.php");

$mysql = mysqlConnect(); //create connection and select database
//Checking connection Connection
if ($mysql->connect_errno) {
	echo "Failed to connect to MySQL: (" . $mysql->connect_errno . ") " . $mysql->connect_error;
}

if ( isset($_GET["name"]) && !empty($_GET["name"]) ) {
	if ( isset($_GET["address"]) && !empty($_GET["address"]) ) {
		$sql_query = "SELECT * FROM Pubs WHERE pub_name LIKE '%" . $_GET["name"] . "%' AND pub_location LIKE '%" . $_GET["address"] . "%'";
	}
	else {
		$sql_query = "SELECT * FROM Pubs WHERE pub_name LIKE '%" . $_GET["name"] . "%'";
	}
}
else if ( isset($_GET["address"]) && !empty($_GET["address"]) ) {
	$sql_query = "SELECT * FROM Pubs WHERE pub_location LIKE '%" . $_GET["address"] . "%'";
}
else {
	header('Location: index.php');
}




$result = $mysql->query($sql_query);

if (!$result) {
	header('Location: index.php');
}

while( $row = $result->fetch_array()) {
	$rows[] = $row;
}

if( !isset($rows) ) {
	header('Location: index.php');
}
//$row = $result->fetch_array();
?>
<!DOCTYPE html>
<html>

<head>

<title>Pub List</title>

</head>

<body>


<table>
<?php foreach ($rows as $thispub): ?>
<tr><div id="name_link" ><a href="pubdetails.php?pub_id=<?= $thispub["id_pub"] ?>"><?= $thispub["pub_name"] ?></a></div><div id="address"><?= $thispub["pub_location"] ?></div></tr>
<?php endforeach; ?>
</table>
	
	
</body>

</html>
