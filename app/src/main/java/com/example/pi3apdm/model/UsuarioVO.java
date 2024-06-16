package com.example.pi3apdm.model;

public class UsuarioVO {
    private int id;
    private String nome;
    private String matricula;
    private String senha;
    private boolean isAProfessor;

    public UsuarioVO() {


    }

    public UsuarioVO(int id,String nome) {
        this.id = id;
        this.nome = nome;
    }

    public UsuarioVO(String nome) {
        this.nome = nome;
    }

    public UsuarioVO(int id, String nome, String matricula) {
        this.id = id;
        this.nome = nome;
        this.matricula = matricula;
    }

    public UsuarioVO(String nome, String matricula) {
        this.nome = nome;
        this.matricula = matricula;
    }

    public UsuarioVO(int id, String nome, String matricula, String senha, boolean isAProfessor) {
        this.id = id;
        this.nome = nome;
        this.matricula = matricula;
        this.senha = senha;
        this.isAProfessor = isAProfessor;
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

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public boolean isProfessor() {
        return isAProfessor;
    }

    public void setProfessor(boolean isAProfessor) {
        this.isAProfessor = isAProfessor;
    }
}
