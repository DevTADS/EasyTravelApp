<?php

$conexion = mysqli_connect("localhost", "vcrfsrrv_easytravel", "lOQuiiA)MR+u", "vcrfsrrv_easytravel");

if (!$conexion) {
    echo "Error de conexión";
    exit(); // Si hay un error de conexión, detener la ejecución del script
}

// Crear la consulta SQL para listar todas las empresas
$query = "SELECT * FROM empresa";

// Ejecutar la consulta
$resultado = mysqli_query($conexion, $query);

// Verificar si hay resultados
if (mysqli_num_rows($resultado) > 0) {
    // Crear un array para almacenar los datos de las empresas
    $empresas = array();
    while ($fila = mysqli_fetch_assoc($resultado)) {
        // Agregar cada fila a la lista de empresas
        $empresas[] = $fila;
    }
    // Imprimir las empresas en formato JSON
    echo json_encode($empresas);
} else {
    echo "No se encontraron empresas";
}

// Cerrar la conexión a la base de datos
mysqli_close($conexion);

?>
