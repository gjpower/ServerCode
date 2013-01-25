<?php
	function mysqlCheckExists($login_value) {
		if ($login_value == "cake")	//check if a login_id exists in the database
			return 1;	//return 1 if true
		else
			return 0;	//else return 0
	}
	
	function mysqlCrawlId($login_value) {
		if ($login_value =="cake")	//if the login_id exists
			return "sjksuckjhsd";	//return the crawl_id for that value
		else
			return "gibberish";
	}
	
	function pubSearchName($pub_name) {
		$pubs_found = array("Fitsimons'","Nicholsons'","Toolan's","Power's","Benilov's");
			return $pubs_found;
	}
	
	function pubSearchAddress($pub_address) {
	}



?>