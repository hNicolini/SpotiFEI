
package model;

public class Usuarios {
    protected String usuario;
    protected String senha;

    public Usuarios(Usuarios usuario1, String usuario) {
        this.usuario = usuario;
        this.senha = senha;
    }

    public Usuarios() {
    }

    public void setUsuarios(String usuario) {
        this.usuario = usuario;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
    
    public String getUsuario() {
        return usuario;
    }

    public String getSenha() {
        return senha;
    }
    
    
    
    
    
    
    
}
