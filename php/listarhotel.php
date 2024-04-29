<?php
$conexion = mysqli_connect("localhost", "oeuopnyh_easy", "4)DxTFg+wjuI", "oeuopnyh_easy");

// Verificar la conexión
if (!$conexion) {
    die("Error de conexión: " . mysqli_connect_error());
}

// Consulta SQL para obtener la información de los hoteles
$query = "SELECT foto, nombre, telefono, CONCAT(direccion, ', ', ciudad, ', ', pais) AS direccion_completa FROM hotel";

// Ejecutar la consulta
$resultado = mysqli_query($conexion, $query);

// Verificar si se encontró algún resultado
if ($resultado && mysqli_num_rows($resultado) > 0) {
    // Crear un array para almacenar los hoteles
    $hoteles = array();
    
    // Obtener los datos de cada hotel
    while ($fila = mysqli_fetch_assoc($resultado)) {
        $hoteles[] = $fila;
    }
    
    // Mostrar la información de los hoteles en formato JSON
    echo json_encode(array("status" => "success", "hoteles" => $hoteles));
} else {
    // No se encontraron hoteles o hubo un error en la consulta
    echo json_encode(array("status" => "error", "message" => "No se encontraron hoteles"));
}

// Liberar el resultado
mysqli_free_result($resultado);

// Cerrar la conexión
mysqli_close($conexion);
?>
