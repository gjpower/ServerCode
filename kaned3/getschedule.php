<?php
    
    class Venue{
        public $time = "";
        public $pubname = "";
        public $publocation = "";
        public $latitude = "";
        public $longitude = "";
    }
    
    $con = mysql_connect("localhost", "root", "");
    if (!$con)
    {
        die('Could not connect: ' . mysql_error());
    }
    
    mysql_select_db("Pub_Crawl", $con);
    $crawlid = $_POST["CrawlID"];
    $result = mysql_query("SELECT Schedules.schedule_time, Pubs.pub_name, Pubs.pub_location FROM Schedules INNER JOIN Pubs ON Pubs.id_pub = Schedules.id_pub WHERE id_of_crawl = '$crawlid' ORDER BY schedule_time ASC");
    
    $Schedule = array();
    
    while ($row = mysql_fetch_array($result))
    {
        $Venue = new Venue();
        $Venue->time = $row['schedule_time'];
        $Venue->pubname = $row['pub_name'];
        $Venue->publocation = $row['pub_location'];
        $Venue->latitude = $row['latitude'];
        $Venue->longitude = $row['longitude'];
        $Schedule[] = $Venue;
    }
    echo(json_encode($Schedule));
    mysql_close($con);
?>


//Permanently save time, name and location