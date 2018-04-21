<?php
include("db.php");
$db=new MySQL;
if(isset($_POST['username']) && isset($_POST['password'])) {
  $username=$_POST['username'];
  $password=$_POST['password'];
  $db=new MySQL();
  $q = "INSERT INTO USERS(username, password) VALUES('$username', '$password');";

  $result=$db->query($q);
  if ($result>0) {
  	echo "success";
  }
  else {
  	echo "fail";
  }
  $db->close();
  }
?>
