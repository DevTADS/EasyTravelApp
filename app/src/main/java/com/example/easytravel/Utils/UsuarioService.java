package com.example.easytravel.Utils;

import com.example.easytravel.Model.Usuario;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UsuarioService {

    @GET("listar")
    Call<List<Usuario>> getUsuarios();

    @POST("agregar")
    Call<Usuario> addUsuario(@Body Usuario u);

    @POST("actualizar/{id}")
    Call<Usuario>actualizarUsuario(@Body Usuario u,@Path("id") int id);

    @POST("eliminar/{id}")
    Call<Usuario>eliminarUsuario(@Path("id")int id);

}
