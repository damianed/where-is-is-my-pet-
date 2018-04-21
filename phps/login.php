<?php
include("db.php");
$db=new MySQL();

if(isset($_GET['username'])){
  $username=$_GET['username'];
  $password=$_GET['password'];
  $db=new MySQL();
  $q = "SELECT * FROM user where username=$username and password=$password";

  $result=$db->consulta($query);
  $rows=$db->numbRows($result);
  if($rows>0){
    echo true;
  }else {
    echo false;
  }
  $db->close();
}else{
  echo "username is not set";
}

?>
