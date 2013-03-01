<?php
session_start();
include ("sjdlkjf/vmnbcm/qporuiow/mysqlaccess.php");

$mysql = mysqlConnect(); //create connection and select database
//Checking connection Connection
if ($mysql->connect_errno) {
	echo "Failed to connect to MySQL: (" . $mysql->connect_errno . ") " . $mysql->connect_error;
}

$sql_query = "SELECT * FROM Pubs ORDER BY pub_name";

$result = $mysql->query($sql_query);

if ($result) {

	while( $row = $result->fetch_array()) {
		$rows[] = $row;
	}
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
