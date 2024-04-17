<?php

// Conexión a la base de datos

$conexion = mysqli_connect("localhost", "vcrfsrrv_easytravel", "lOQuiiA)MR+u", "vcrfsrrv_easytravel");

// Verificar la conexión
if (!$conexion) {
    die("Error de conexión: " . mysqli_connect_error());
}

// Obtener el correo y la contraseña enviados por POST
$correo = $_POST['correo'];
$password = $_POST['password'];

// Escapar los valores para evitar inyección SQL
$correo = mysqli_real_escape_string($conexion, $correo);
$password = mysqli_real_escape_string($conexion, $password);

// Consulta SQL para verificar si la empresa existe en la base de datos
$query = "SELECT * FROM empresa WHERE correo = '$correo' AND password = '$password'";

// Ejecutar la consulta
$resultado = mysqli_query($conexion, $query);

// Verificar si se encontró algún resultado
if (mysqli_num_rows($resultado) > 0) {
    // Empresa ingresó correctamente
    echo "ingreso correctamente";
} else {
    // La empresa no pudo ingresar
    echo "No pudo ingresar";
}

// Cerrar la conexión
mysqli_close($conexion);

?>
