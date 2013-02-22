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
 $tbl_name="Users"; 

//Establishing Connection
 mysql_connect("$db_host", "$db_uid", "$db_pass")or die("cannot connect"); 
 //Select Database
 mysql_select_db("$db_name")or die("cannot select DB");
 
//Store information passed  
 $name = $_POST["UserName"];
 $userid = $_POST["UserID"];
 
 $sql="REPLACE INTO Users SET id_user = '$userid', user_name = '$name';";

 $result=mysql_query($sql);
   if($result)
     echo "New User Uploaded Successfully";
      else
    echo "Comment Upload Failed";
 
 mysql_close();   
?>