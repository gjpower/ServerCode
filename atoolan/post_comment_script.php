<?php
    //test commit
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
 
//Store information passed  
 $comment = $_POST["Comment"];
 $userid = $_POST["UserID"];
 $crawlid = $_POST["CrawlID"];

 $sql="INSERT INTO Comments(comment_body,id_of_crawl,id_user) VALUES ('$comment','$userid','$crawlid')";
 $result=mysql_query($sql);
   if($result)
     echo "Comment Uploaded Successfully";
      else
    echo "Comment Upload Failed";
 
 mysql_close();   
?>