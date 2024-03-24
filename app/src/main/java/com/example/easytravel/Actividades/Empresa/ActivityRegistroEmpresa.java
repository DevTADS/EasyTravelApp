package com.example.easytravel.Actividades.Empresa;

import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.easytravel.R;
import com.example.easytravel.Firebase.Autenticacion_FirebaseAuthHelper;
import com.example.easytravel.Firebase.BaseDatos_FirestoreHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.firestore.DocumentReference;

import java.util.HashMap;
import java.util.Map;

public class ActivityRegistroEmpresa extends AppCompatActivity {

    private EditText campoUsuario;
    private EditText campoContraseña;
    private EditText campoCorreo;
    private AutoCompleteTextView campoPais;
    private EditText campoTelefono;
    private EditText campoDireccion;
    private Button botonRegistrarse;
    private Autenticacion_FirebaseAuthHelper authHelper;
    private BaseDatos_FirestoreHelper basededatosFirestoreHelper;
    private boolean contraseñaVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empresa_registro);

        // Inicializar FirebaseAuthHelper y FirestoreHelper
        authHelper = new Autenticacion_FirebaseAuthHelper();
        basededatosFirestoreHelper = new BaseDatos_FirestoreHelper();

        // Inicializar vistas
        campoUsuario = findViewById(R.id.nombre_empresa);
        campoContraseña = findViewById(R.id.password);
        campoCorreo = findViewById(R.id.correo);
        campoPais = findViewById(R.id.autoCompleteTextViewCountry);
        campoTelefono = findViewById(R.id.telefono);
        campoDireccion = findViewById(R.id.direccion);
        botonRegistrarse = findViewById(R.id.btn_registrarse);

        // Configurar ArrayAdapter para el AutoCompleteTextView de país
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, getResources().getStringArray(R.array.countries));
        campoPais.setAdapter(adapter);

        // Configurar OnClickListener para el botón de registro
        botonRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String nombreEmpresa = campoUsuario.getText().toString();
                final String pais = campoPais.getText().toString();
                final String telefono = campoTelefono.getText().toString();
                final String direccion = campoDireccion.getText().toString();
                final String correo = campoCorreo.getText().toString();
                final String contraseña = campoContraseña.getText().toString();

                if (nombreEmpresa.isEmpty() || pais.isEmpty() || telefono.isEmpty() || direccion.isEmpty() || correo.isEmpty() || contraseña.isEmpty()) {
                    validacion();
                } else {

                    authHelper.crearEmpresaConCorreoYContraseña(correo, contraseña, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                String idEmpresa = task.getResult().getUser().getUid();

                                // Agregar el UID de la empresa como un campo en los datos
                                Map<String, Object> datosEmpresa = new HashMap<>();
                                datosEmpresa.put("idEmpresa", idEmpresa); // Agrega el UID como un campo en los datos
                                datosEmpresa.put("nombre", nombreEmpresa);
                                datosEmpresa.put("pais", pais);
                                datosEmpresa.put("telefono", telefono);
                                datosEmpresa.put("direccion", direccion);
                                datosEmpresa.put("correo", correo);

                                basededatosFirestoreHelper.addEmpresa("empresa", datosEmpresa, new OnCompleteListener<DocumentReference>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentReference> task) {
                                        if (task.isSuccessful()) {
                                            // Éxito al agregar la empresa en la colección "empresa"
                                            Toast.makeText(ActivityRegistroEmpresa.this, "Los datos de la empresa se han registrado correctamente", Toast.LENGTH_SHORT).show();
                                            limpiarCampos();
                                            Toast.makeText(ActivityRegistroEmpresa.this, "Aguarde la validación de parte de los Administradores", Toast.LENGTH_SHORT).show();
                                        } else {
                                            // Error al agregar la empresa en la colección "empresa"
                                            Toast.makeText(ActivityRegistroEmpresa.this, "Error al registrar empresa", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            } else {
                                // Error al crear la empresa en Firebase Authentication
                                Toast.makeText(ActivityRegistroEmpresa.this, "Error al registrar empresa", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


                }
            }
        });

        // Configurar OnClickListener para el ícono de la contraseña
        campoContraseña.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.icono_password, 0);
        campoContraseña.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (campoContraseña.getRight() - campoContraseña.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        cambiarVisibilidadContraseña();
                        return true;
                    }
                }
                return false;
            }
        });
    }

    // Método para cambiar la visibilidad de la contraseña
    private void cambiarVisibilidadContraseña() {
        if (contraseñaVisible) {
            // Cambiar a contraseña oculta
            campoContraseña.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            contraseñaVisible = false;
        } else {
            // Cambiar a texto visible
            campoContraseña.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            contraseñaVisible = true;
        }
        // Mover el cursor al final del texto
        campoContraseña.setSelection(campoContraseña.getText().length());
    }

    // Método para limpiar campos de texto
    private void limpiarCampos() {
        campoUsuario.setText("");
        campoCorreo.setText("");
        campoContraseña.setText("");
        campoPais.setText("");
        campoTelefono.setText("");
        campoDireccion.setText("");
    }

    // Método para validar campos de texto
    private void validacion() {
        if (campoUsuario.getText().toString().isEmpty()) {
            campoUsuario.setError("Requerido");
        }
        if (campoCorreo.getText().toString().isEmpty()) {
            campoCorreo.setError("Requerido");
        }
        if (campoContraseña.getText().toString().isEmpty()) {
            campoContraseña.setError("Requerido");
        }
        if (campoPais.getText().toString().isEmpty()) {
            campoPais.setError("Requerido");
        }
        if (campoTelefono.getText().toString().isEmpty()) {
            campoTelefono.setError("Requerido");
        }
        if (campoDireccion.getText().toString().isEmpty()) {
            campoDireccion.setError("Requerido");
        }
    }
}
