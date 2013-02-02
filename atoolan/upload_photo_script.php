<?php
$target_path = "uploads/";
/* Add the original filename to our target path.
Result is "uploads/filename.extension" */
$target_path = $target_path . basename( $_FILES['uploadedfile1']['name']);
if(move_uploaded_file($_FILES['uploadedfile1']['tmp_name'], $target_path)) {
    echo "The file ".  basename( $_FILES['uploadedfile1']['name']).
    " has been uploaded.";
} else{
    echo "There was an error uploading the file, please try again!";
//    echo "filename: " .  basename( $_FILES['uploadedfile1']['name']);
//    echo "target_path: " .$target_path1;
}


?>
