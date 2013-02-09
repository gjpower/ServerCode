<?php

 // Host address
 $db_host  = "localhost";
// Username
 $db_uid  = "root";
 // Password
 $db_pass = "";
 // Database name  
 $db_name  = "Pub_Crawl"; 
 // Table name
 $tbl_name="Comments"; 

//Establishing Connection
 mysql_connect("$db_host", "$db_uid", "$db_pass")or die("cannot connect"); 
 //Select Database
 mysql_select_db("$db_name")or die("cannot select DB");
 
 $userID = $_POST["userId"];
 $crawlID = $_POST["crawlId"];
 $type = $_POST["type"];
 $gps1 = $_POST["gps1"];
 $gps2 = $_POST["gps2"];

$Guid = NewGuid();
    
$target_path = "uploads/";
/* Add the original filename to our target path.
Result is "uploads/filename.extension" */

$temp = $_FILES['uploadedfile1']['name'] = $Guid.".jpg"; 
$URL = "http://192.168.1.15/uploads/".$temp;

$sql="INSERT INTO Comments(comment_body,id_of_crawl,id_user,type,gps,gps2) VALUES ('$URL','$crawlID','$userID','$type','$gps1','$gps2')";
 $result=mysql_query($sql);
   if($result)
   {

	$target_path = $target_path . basename( $_FILES['uploadedfile1']['name']);
	if(move_uploaded_file($_FILES['uploadedfile1']['tmp_name'], $target_path)) {
	   echo "The file ".  basename( $_FILES['uploadedfile1']['name']).
	   " has been uploaded.";
	} else
	{
	   echo "There was an error storing the file, please try again!";    
	}
	}
	else
	{
	echo "There was an error connecting to the database.";
	}
	
	
	
	// Generate Guid 
function NewGuid() { 
    $s = strtoupper(md5(uniqid(rand(),true))); 
    $guidText = 
        substr($s,0,8) . '-' . 
        substr($s,8,4) . '-' . 
        substr($s,12,4). '-' . 
        substr($s,16,4). '-' . 
        substr($s,20); 
    return $guidText;
}
// End Generate Guid 

?>
