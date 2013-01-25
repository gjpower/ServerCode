<?php 
	session_start();	//start tracking session
	if ( !isset($_SESSION['crawl_id']) && empty($_SESSION['crawl_id'])) {	//if crawl id isn't set i.e not logged in
		include "login.php";	//load data from login page and display
	}
	else if (isset($_GET['searchedpub'])) {	//if user searched for a pub
		include "pubsearch.php";
	}
	else include "home.php";	//load data from homepage and display if you are logged in
?>
