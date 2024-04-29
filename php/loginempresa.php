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
$query = "SELECT id_empresa FROM empresa WHERE correo = '$correo' AND password = '$password'";

// Ejecutar la consulta
$resultado = mysqli_query($conexion, $query);

// Verificar si se encontró algún resultado
if (mysqli_num_rows($resultado) > 0) {
    // Obtener el id_empresa
    $fila = mysqli_fetch_assoc($resultado);
    $id_empresa = $fila['id_empresa'];
    
    // Empresa ingresó correctamente
    echo json_encode(array("status" => "success", "id_empresa" => $id_empresa));
} else {
    // La empresa no pudo ingresar
    echo json_encode(array("status" => "error", "message" => "No pudo ingresar"));
}

// Cerrar la conexión
mysqli_close($conexion);

?>
