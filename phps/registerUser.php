<?php
include("db.php");
$db=new MySQL;
if(isset($_POST['username']) && isset($_POST['password']) && isset($_POST['name']) && isset($_POST['lastName']) && isset($_POST['email'])) {
  $username=$_POST['username'];
  $password=$_POST['password'];
  $name=$_POST['name'];
  $lastName=$_POST['lastName'];
  $email=$_POST['email'];
  $db=new MySQL();
  $q = "INSERT INTO USERS(username, password, name, last_name, email) VALUES('$username', '$password', '$name', '$lastName', '$email');";

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
