<?php

$conexion = mysqli_connect("localhost", "oeuopnyh_easy", "4)DxTFg+wjuI", "oeuopnyh_easy");

if (!$conexion) {
    echo "Error de conexión";
    exit();
}

// Obtener los valores enviados desde la aplicación Android
$nombre = $_POST['nombre'];
$pais = $_POST['pais'];
$ciudad = $_POST['ciudad'];
$telefono = $_POST['telefono'];
$direccion = $_POST['direccion'];
$id_empresa = $_POST['id_empresa'];
$foto = $_POST['foto'];

// Verificar si se subió un archivo
if (!empty($foto)) {
    // Escapar los valores para evitar inyección SQL
    $nombre = mysqli_real_escape_string($conexion, $nombre);
    $pais = mysqli_real_escape_string($conexion, $pais);
    $ciudad = mysqli_real_escape_string($conexion, $ciudad);
    $telefono = mysqli_real_escape_string($conexion, $telefono);
    $direccion = mysqli_real_escape_string($conexion, $direccion);
    $id_empresa = mysqli_real_escape_string($conexion, $id_empresa);

    // Crear la consulta SQL para insertar los datos en la tabla hotel
    $query = "INSERT INTO hotel (nombre, pais, ciudad, telefono, direccion, id_empresa, foto) VALUES ('$nombre', '$pais', '$ciudad', '$telefono', '$direccion', '$id_empresa', '$foto')";

    // Ejecutar la consulta
    $resultado = mysqli_query($conexion, $query);

    // Verificar si la consulta se ejecutó correctamente
    if ($resultado) {
        echo "Datos insertados";
    } else {
        echo "Error al insertar los datos: " . mysqli_error($conexion);
    }
} else {
    echo "Error: No se recibió ninguna imagen.";
}

// Cerrar la conexión a la base de datos
mysqli_close($conexion);

?>
