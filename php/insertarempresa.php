<?php

$conexion = mysqli_connect("localhost", "oeuopnyh_easy", "4)DxTFg+wjuI", "oeuopnyh_easy");

if (!$conexion) {
    echo "Error de conexión";
    exit(); // Si hay un error de conexión, detener la ejecución del script
}

// Obtener los valores enviados desde la aplicación Android
$nombre = $_POST['nombre'];
$correo = $_POST['correo'];
$password = $_POST['password'];
$pais = $_POST['pais'];
$telefono = $_POST['telefono'];
$direccion = $_POST['direccion'];

// Escapar los valores para evitar inyección SQL
$nombre = mysqli_real_escape_string($conexion, $nombre);
$correo = mysqli_real_escape_string($conexion, $correo);
$password = mysqli_real_escape_string($conexion, $password);
$pais = mysqli_real_escape_string($conexion, $pais);
$telefono = mysqli_real_escape_string($conexion, $telefono);
$direccion = mysqli_real_escape_string($conexion, $direccion);

// Crear la consulta SQL para insertar los datos en la tabla empresa
$query = "INSERT INTO empresa (nombre, correo, password, pais, telefono, direccion) VALUES ('$nombre', '$correo', '$password', '$pais', '$telefono', '$direccion')";

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
