<?php
 //Host address
 $db_host  = "localhost";
 //Username
 $db_uid  = "root";
 //Password
 $db_pass = "";
 //Database name  
 $db_name  = "Pub_Crawl"; 
 
 // Establishes the PHP & MySQL connection  
 $db_con = mysql_connect($db_host,$db_uid,$db_pass) or die('could not connect');
 mysql_select_db($db_name);
 $crawlid = $_POST["CrawlID"];
 $sql = "SELECT * FROM Comments WHERE  id_of_crawl = '$crawlid'";
 $result = mysql_query($sql);
 while($row=mysql_fetch_assoc($result))
 $output[]=$row;
 print(json_encode($output));
 mysql_close();   
?>
