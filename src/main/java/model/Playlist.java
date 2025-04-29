
package model;

public class Playlist {
    protected Musicas musica;
    protected Usuarios usuario;
    protected Playlist Id;

    public Playlist(Musicas musica, Usuarios usuario) {
        this.musica = musica;
        this.usuario = usuario;
    }

    public Playlist() {
    }
    
    

    public Playlist getId() {
        return Id;
    }

    public void setId(Playlist Id) {
        this.Id = Id;
    }

    public Musicas getMusica() {
        return musica;
    }

    public void setMusica(Musicas musica) {
        this.musica = musica;
    }

    public Usuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
    }
}
