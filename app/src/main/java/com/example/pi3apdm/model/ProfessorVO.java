package com.example.pi3apdm.model;

public class ProfessorVO {
    private int id;
    private String nome;
    private String matricula;

    public ProfessorVO() {


    }

    public ProfessorVO(int id,String nome) {
        this.id = id;
        this.nome = nome;
    }

    public ProfessorVO(String nome) {
        this.nome = nome;
    }

    public ProfessorVO(int id, String nome, String matricula) {
        this.id = id;
        this.nome = nome;
        this.matricula = matricula;
    }

    public ProfessorVO(String nome, String matricula) {
        this.nome = nome;
        this.matricula = matricula;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }


    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }


    public String getMatricula() {
        return matricula;
    }
    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

}
