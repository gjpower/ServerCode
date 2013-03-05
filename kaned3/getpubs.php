<?php
    
    class Pub{
        public $pubname = "";
        public $publocation = "";
        public $latitude = "";
        public $longitude = "";
        public $pubdescription = "";
        public $uprating = "";
        public $downrating = "";
    }
    
    $con = mysql_connect("localhost", "root", "");
    if (!$con)
    {
        die('Could not connect: ' . mysql_error());
    }
    
    mysql_select_db("Pub_Crawl", $con);
    
    //Request latitude and longitude values from Java code
    //$currentLatitude = $_POST["Latitude"];
    //$currentLongitude = $_POST["Longitude"];
    $crawlid = $_POST["CrawlID"];
    
    $result = mysql_query("SELECT * FROM Pubs ORDER BY pub_name ASC");
    
    $Pubs = array();
    
    while ($row = mysql_fetch_array($result))
    {
        $Pub = new Pub();
        $Pub->pubname = $row['pub_name'];
        $Pub->publocation = $row['pub_location'];
        $Pub->latitude = $row['latitude'];
        $Pub->longitude = $row['longitude'];
        $Pub->pubdescription = $row['pub_description'];
        $Pub->uprating = $row['up_rating'];
        $Pub->downrating = $row['down_rating'];

        $Pubs[] = $Pub;
    }
    echo json_encode($Pubs);
    mysql_close($con);    
?>