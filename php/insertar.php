<?php

$conexion = mysqli_connect("localhost", "vcrfsrrv_easytravel", "lOQuiiA)MR+u", "vcrfsrrv_easytravel");

if (!$conexion) {
    echo "Error de conexión";
    exit(); // Si hay un error de conexión, detener la ejecución del script
}

// Obtener los valores enviados desde la aplicación Android
$nombre = $_POST['nombre'];
$email = $_POST['email'];
$password = $_POST['password'];
$pais = $_POST['pais'];
$ciudad = $_POST['ciudad'];
$cedula = $_POST['cedula'];
$telefono = $_POST['telefono'];
$direccion = $_POST['direccion'];

// Escapar los valores para evitar inyección SQL
$nombre = mysqli_real_escape_string($conexion, $nombre);
$email = mysqli_real_escape_string($conexion, $email);
$password = mysqli_real_escape_string($conexion, $password);
$pais = mysqli_real_escape_string($conexion, $pais);
$ciudad = mysqli_real_escape_string($conexion, $ciudad);
$cedula = mysqli_real_escape_string($conexion, $cedula);
$telefono = mysqli_real_escape_string($conexion, $telefono);
$direccion = mysqli_real_escape_string($conexion, $direccion);

// Crear la consulta SQL para insertar los datos en la tabla usuario
$query = "INSERT INTO usuario (nombre, email, password, pais, ciudad, cedula, telefono, direccion) VALUES ('$nombre', '$email', '$password', '$pais', '$ciudad', '$cedula', '$telefono', '$direccion')";

// Ejecutar la consulta
$resultado = mysqli_query($conexion, $query);

// Verificar si la consulta se ejecutó correctamente
if ($resultado) {
    echo "Datos insertados"; // Enviar respuesta de éxito a la aplicación Android
} else {
    echo "Error al insertar los datos: " . mysqli_error($conexion); // Enviar mensaje de error a la aplicación Android
}

// Cerrar la conexión a la base de datos
mysqli_close($conexion);

?>
