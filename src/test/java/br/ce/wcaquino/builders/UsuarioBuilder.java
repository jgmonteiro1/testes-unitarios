package br.ce.wcaquino.builders;

import br.ce.wcaquino.entidades.Usuario;

public class UsuarioBuilder {

    private Usuario usuario;

    private UsuarioBuilder(){
    }

    public static UsuarioBuilder usuarioBuilder(){
        UsuarioBuilder builder = new UsuarioBuilder();
        builder.usuario = new Usuario();
        builder.usuario.setNome("Usuario builder 1");
        return builder;
    }

    public Usuario now(){
        return usuario;
    }
}
