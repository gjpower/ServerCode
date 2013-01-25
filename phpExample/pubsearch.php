<?php include "mysqlscripts.php" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Pub Search</title>
</head>
<body>
you searched for a pub called <?php echo $_GET['searchedpub'] ?><br />
here is a list of pubs we found with <?php echo $_GET['searchedpub'] ?> in them
<?php $pubsArray = pubSearchName($_GET['searchedpub']); ?>	<!-- get an array of pubs -->
<table><?php foreach ($pubsArray as $currentPub): ?>	<!-- move through all elements of the array and loop the code -->
<tr><td><?php echo $currentPub ?></td></tr>
<?php endforeach; ?>	<!-- end of foreach statement all html code will be looped also -->
</table>
</body>
</html>
