<?php
include('db.php');
$db=new MySQL();

if(isset($_POST['query'])){
  header('Content-type: application/json');
  $q = $_POST['query'];
  $result=$db->query($q);
  $resultSet = $db->fetchArray($result);
  $db->close();
  echo json_encode($resultSet);
}
?>
