<?php

// Conexión a la base de datos
$conexion = mysqli_connect("localhost", "vcrfsrrv_easytravel", "lOQuiiA)MR+u", "vcrfsrrv_easytravel");

// Verificar la conexión
if (!$conexion) {
    die("Error de conexión: " . mysqli_connect_error());
}

// Recibir la descripción enviada por POST y escaparla
$description = mysqli_real_escape_string($conexion, $_POST['description']);

// Crear un array para almacenar los nombres de las imágenes
$imageNames = array();

// Guardar las imágenes en el servidor
for ($i = 1; $i <= 3; $i++) {
    $imageName = "image" . $i;

    // Verificar si se ha enviado una imagen con ese nombre
    if (isset($_FILES[$imageName]) && $_FILES[$imageName]['error'] === UPLOAD_ERR_OK) {
        $uploadDir = './uploads/'; // Ruta donde se guardarán las imágenes
        $imageTmpName = $_FILES[$imageName]['tmp_name'];
        $imageExtension = pathinfo($_FILES[$imageName]['name'], PATHINFO_EXTENSION);
        $imageName = uniqid() . '.' . $imageExtension; // Nombre único para la imagen
        $targetFile = $uploadDir . $imageName;

        if (move_uploaded_file($imageTmpName, $targetFile)) {
            $imageNames[$i] = $imageName;
        } else {
            die("Error al subir la imagen $imageName");
        }
    }
}

// Insertar la descripción y los nombres de las imágenes en la tabla reporte
$query = "INSERT INTO reporte (descripcion, imagen1, imagen2, imagen3) VALUES ('$description'";
for ($i = 1; $i <= 3; $i++) {
    if (isset($imageNames[$i])) {
        $query .= ", '" . $imageNames[$i] . "'";
    } else {
        $query .= ", NULL";
    }
}
$query .= ")";
mysqli_query($conexion, $query);

// Cerrar la conexión
mysqli_close($conexion);

echo "Datos recibidos correctamente.";

?>
