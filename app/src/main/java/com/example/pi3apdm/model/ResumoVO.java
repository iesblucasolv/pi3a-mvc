package com.example.pi3apdm.model;

public class ResumoVO {
    private int id;
    private String titulo;
    private String conteudo;
    private int professorID;

    public ResumoVO() {


    }

    public ResumoVO(int id,String conteudo) {
        this.id = id;
        this.conteudo = conteudo;
    }

    public ResumoVO(String titulo) {
        this.titulo = titulo;
    }

    public ResumoVO(int id, String titulo, String conteudo, int professorID) {
        this.id = id;
        this.titulo = titulo;
        this.conteudo = conteudo;
        this.professorID = professorID;
    }

    public ResumoVO(String titulo, String conteudo) {
        this.titulo = titulo;
        this.conteudo = conteudo;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }


    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }


    public String getConteudo() {
        return conteudo;
    }
    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }


    public int getProfessorID() {
        return professorID;
    }
    public void setProfessorID(int professorID) { this.professorID = professorID; }
}
