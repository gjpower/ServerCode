<?php
if(!isset($_SESSION)) {
	session_start();
}
$_SESSION["ADMIN"] = TRUE;
header ('Location: index.php');
?>
