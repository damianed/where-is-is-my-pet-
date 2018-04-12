<?php

class MySQL{
	private $conexion;

	public function MySQL(){
    $username = "teccodig_wmpUser";
    $password = "wmpUser";
    $hostname = "localhost";
    $database = "wmp";

		if(!isset($this->conexion)){
			$this->conexion = mysqli_connect($hostname,$username,$password,$database);
		}
	}

	public function query($query){
		return mysqli_query($this->conexion,$query);
	}

	public function fetchArray($consulta){
		return mysqli_fetch_array($consulta);
	}

	public function numRows($consulta){
		return mysqli_num_rows($consulta);
	}

	public function close(){
		mysqli_close($this->conexion);
	}

}
?>
