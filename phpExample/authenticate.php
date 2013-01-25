<?php
session_start();

include "mysqlscripts.php";	//include functions listed in mysqlscripts so they may be called here

if (!empty($_POST["loginkey"])) {	//if data posted with the loginkey name is not empty
	$loginID = $_POST["loginkey"];	//set the variable loginID to that value
	if (mysqlCheckExists($loginID)) {	//if the loginID exists in the database
		$_SESSION['crawl_id'] = mysqlCrawlId($loginID);	//get the crawlID associated with that login and set it to the session tracker
	}
}
header ('Location: index.php');	//once above has executed redirect user to the index page which will decide what to do with the user e.g display homepage
?>						
