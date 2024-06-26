<?php

$conexion = mysqli_connect("localhost", "oeuopnyh_easy", "4)DxTFg+wjuI", "oeuopnyh_easy");

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
$query = "SELECT id_empresa, nombre FROM empresa WHERE correo = '$correo' AND password = '$password'";

// Ejecutar la consulta
$resultado = mysqli_query($conexion, $query);

// Verificar si se encontró algún resultado
if (mysqli_num_rows($resultado) > 0) {
    // Obtener el id_empresa y el nombre de la empresa
    $fila = mysqli_fetch_assoc($resultado);
    $id_empresa = $fila['id_empresa'];
    $nombre = $fila['nombre'];
    
    // Empresa ingresó correctamente
    echo json_encode(array("status" => "success", "id_empresa" => $id_empresa, "nombre" => $nombre));
} else {
    // La empresa no pudo ingresar
    echo json_encode(array("status" => "error", "message" => "Correo o contraseña incorrectos"));
}

// Cerrar la conexión
mysqli_close($conexion);

?>
