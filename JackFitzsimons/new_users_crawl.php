<?php
    //test commit
 // Host address
 $db_host  = "164.138.29.169";
 // Username
 $db_uid  = "root";
 // Password
 $db_pass = "notASecurePassword";
 // Database name  
 $db_name  = "Pub_Crawl"; 
 // Table name
 $tbl_name="Users_To_Crawl"; 

//Establishing Connection
 mysql_connect("$db_host", "$db_uid", "$db_pass")or die("cannot connect"); 
 //Select Database
 mysql_select_db("$db_name")or die("cannot select DB");
 
//Store information passed  
 $userid = $_POST["UserID"];
 $crawlid = $_POST["CrawlID"];

 $sql="INSERT INTO Users_To_Crawl(id_of_crawl,id_user) VALUES ('$userid','$crawlid')";
 $result=mysql_query($sql);
   if($result)
     echo "Users New Crawl Uploaded Successfully";
      else
    echo "Comment Upload Failed";
 
 mysql_close();   
?>