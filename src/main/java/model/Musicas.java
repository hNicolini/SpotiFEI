
package model;


public class Musicas {
    protected String titulo;
    protected String autor;
    protected String genero;
    protected int id;

    public Musicas(String titulo, String autor, String genero,int id) {
        this.titulo = titulo;
        this.autor = autor;
        this.genero = genero;
        this.id = id;
    }

    public Musicas() {
    
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public String getGenero() {
        return genero;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }
    
    
    
}
