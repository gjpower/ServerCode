<?php

function mysqlConnect() {
	// Host address
	$db_host = "localhost";
	// Username
	$db_uid = "root";
	// Password
	$db_pass = "notASecurePassword";
	// Database name
	$db_name = "Pub_Crawl";
	
	$mysql = new mysqli($db_host, $db_uid, $db_pass, $db_name);
	
	return $mysql;
}

?>