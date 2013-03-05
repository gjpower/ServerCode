<?php

include("inc/mysqlaccess.inc");
include ("inc/keygens.inc");

if( !isset($_POST["userID"]) ) {
	header("Location: /");
}



$userID = $_POST["userId"];		//save posted values to local variables
$crawlID = $_POST["crawlId"];	//should check if userId and crawlId exists in database
$gps1 = $_POST["gps1"];
$gps2 = $_POST["gps2"];
$datetime = date('Y-m-d H:i:s');	//generate date of comment

$mysql = mysqlConnect();	//establish connection and select database

$query="INSERT INTO Comments(comment_body,id_of_crawl,id_user,latitude,longitude, comment_time) VALUES ('$URL','$crawlID','$userID','$gps1','$gps2', '$datetime')";	//put all variables in new comment
$result=$mysql->query($query);	//run query and store result
if($result)	//check if succesful
{
	if( isset($_FILES['uploadedfile1']['name']) && !empty($_FILES['uploadedfile1']['name']) ) {	//if an image has been uploaded
		
		$comment_id = $mysql->insert_id;	//get id which was just created
		$guid = comment_image_keygen($pub_id);	//generate a hash based on the previous comment id
		$target_path = "commentimg/";
		$file_extension = pathinfo($_FILES['uploadedfile1']['name'], PATHINFO_EXTENSION);
		$file_location = $target_path . $guid . "." . $file_extension;		//generated our new file location
		
		if (!move_uploaded_file($_FILES['uploadedfile1']['name'], $file_location)) {	//if fail to move file
			echo "Error saving image";
		}
		else {
			$query = "UPDATE Comments SET image='$file_location' WHERE id_comment='$comment_id'";	//set the file location for that comment
			$result=$mysql->query($query);
		}
	}
}
else
{
echo "There was an error connecting to the database.";
}
?>
