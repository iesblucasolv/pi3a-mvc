package com.example.pi3apdm.model;

public class TurmaVO {
    private int id;
    private String codigo_turma;
    private String sala;
    private String disciplina;
    private String horario;
    private String periodo;
    private String dia_semana;
    private String professor;

    public TurmaVO() {


    }

    public TurmaVO(int id,String codigo_turma) {
        this.id = id;
        this.codigo_turma = codigo_turma;
    }

    public TurmaVO(String sala) {
        this.sala = sala;
    }

    public TurmaVO(int id, String codigo_turma, String sala, String disciplina, String horario, String periodo, String dia_semana, String professor) {
        this.id = id;
        this.codigo_turma = codigo_turma;
        this.sala = sala;
        this.disciplina = disciplina;
        this.horario = horario;
        this.periodo = periodo;
        this.dia_semana = dia_semana;
        this.professor = professor;

    }

    public TurmaVO(String disciplina, String professor) {
        this.disciplina = disciplina;
        this.professor = professor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodigo_turma() {
        return codigo_turma;
    }

    public void setCodigo_turma(String codigo_turma) {
        this.codigo_turma = codigo_turma;
    }

    public String getSala() {
        return sala;
    }

    public void setSala(String sala) {
        this.sala = sala;
    }

    public String getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(String disciplina) {
        this.disciplina = disciplina;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public String getDia_semana() {
        return dia_semana;
    }

    public void setDia_semana(String dia_semana) {
        this.dia_semana = dia_semana;
    }

    public String getProfessor() {
        return professor;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }
}
