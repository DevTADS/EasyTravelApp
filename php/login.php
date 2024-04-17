<?php

// Conexión a la base de datos

$conexion = mysqli_connect("localhost", "vcrfsrrv_easytravel", "lOQuiiA)MR+u", "vcrfsrrv_easytravel");

// Verificar la conexión
if (!$conexion) {
    die("Error de conexión: " . mysqli_connect_error());
}

// Obtener el email y la contraseña enviados por POST
$email = $_POST['email'];
$password = $_POST['password'];

// Escapar los valores para evitar inyección SQL
$email = mysqli_real_escape_string($conexion, $email);
$password = mysqli_real_escape_string($conexion, $password);

// Consulta SQL para verificar si el usuario existe en la base de datos
$query = "SELECT * FROM usuario WHERE email = '$email' AND password = '$password'";

// Ejecutar la consulta
$resultado = mysqli_query($conexion, $query);

// Verificar si se encontró algún resultado
if (mysqli_num_rows($resultado) > 0) {
    // El usuario ingresó correctamente
    echo "ingreso correctamente";
} else {
    // El usuario no pudo ingresar
    echo "No pudo ingresar";
}

// Cerrar la conexión
mysqli_close($conexion);

?>
