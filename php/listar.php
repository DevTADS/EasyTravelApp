<?php

$conexion = mysqli_connect("localhost", "oeuopnyh_easy", "4)DxTFg+wjuI", "oeuopnyh_easy");

if (!$conexion) {
    echo "Error de conexión";
    exit(); // Si hay un error de conexión, detener la ejecución del script
}

// Crear la consulta SQL para listar todos los usuarios
$query = "SELECT * FROM usuario";

// Ejecutar la consulta
$resultado = mysqli_query($conexion, $query);

// Verificar si hay resultados
if (mysqli_num_rows($resultado) > 0) {
    // Crear un array para almacenar los datos de los usuarios
    $usuarios = array();
    while ($fila = mysqli_fetch_assoc($resultado)) {
        // Agregar cada fila a la lista de usuarios
        $usuarios[] = $fila;
    }
    // Imprimir los usuarios en formato JSON
    echo json_encode($usuarios);
} else {
    echo "No se encontraron usuarios";
}

// Cerrar la conexión a la base de datos
mysqli_close($conexion);

?>
