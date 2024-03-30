package com.example.easytravel.Actividades.Usuario;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.easytravel.R;


public class LoginUsuario extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;
    private Button loginButton;

    private Button registerButton;
    private boolean passwordVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_login);



        // Inicializar vistas
        emailEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.btn_login);
        registerButton = findViewById(R.id.btn_registro);



        // Configurando OnClickListener para el botón de registro
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abrir la actividad de registro al hacer clic en el botón de registro
                startActivity(new Intent(LoginUsuario.this, RegistroUsuario.class));
            }
        });

        // Configurando OnClickListener para el ícono de la contraseña
        passwordEditText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.icono_password, 0);
        passwordEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (passwordEditText.getRight() - passwordEditText.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        togglePasswordVisibility();
                        return true;
                    }
                }
                return false;
            }
        });
    }

    private void togglePasswordVisibility() {
        if (passwordVisible) {
            // Cambiar a contraseña oculta
            passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            passwordVisible = false;
        } else {
            // Cambiar a texto visible
            passwordEditText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            passwordVisible = true;
        }
        // Mover el cursor al final del texto
        passwordEditText.setSelection(passwordEditText.getText().length());
    }
}
