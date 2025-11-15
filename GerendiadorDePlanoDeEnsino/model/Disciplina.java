package model;

import java.util.ArrayList;

public class Disciplina {
    private int id;
    private String nome;
    private  String codigo;
    private ArrayList<String> cursos;
    private int cargaHr; //Carga Horária
    private String ementa;
    private String ementaResumo;
    private  ArrayList<Aluno> alunos;

    public Disciplina() {
        super();
    }

    public Disciplina (int id, String nome, String codigo, ArrayList<String> cursos, int cargaHr, String ementa, String ementaResumo, ArrayList<Aluno> alunos) {
        super();
        setId(id);
        setNome(nome);
        setCodigo(codigo);
        setCursos(cursos);
        setCargaHr(cargaHr);
        setEmenta(ementa);
        setEmentaResumo(ementaResumo);
        setAlunos(alunos);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        if(id > 0 && this.id == 0){
            //setar só 1 vez
            this.id = id;
        }
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if(nome != null){
            this.nome = nome;
        }
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        if(codigo != null){
            if(codigo.length() == 6){
                //o código deve ter tamanho 6
                this.codigo = codigo;
            }
        }
    }

    public String getCursos() {
        //retorna cada nome de curso em uma String separados por ;
        String strCursos = "";
        for(String c: cursos){
            strCursos += String.format("%s;", c);
        }
        return strCursos;
    }

    public void setCursos(ArrayList<String> cursos) {
        if(cursos != null){
            this.cursos = cursos;
        }
    }

    public int getCargaHr() {
        return cargaHr;
    }

    public void setCargaHr(int cargaHr) {
        if(cargaHr > 0){
            this.cargaHr = cargaHr;
        }
    }

    public String getEmenta() {
        return ementa;
    }

    public void setEmenta(String ementa) {
        if(ementa != null){
            this.ementa = ementa;
        }
    }

    public String getEmentaResumo() {
        return ementaResumo;
    }

    public void setEmentaResumo(String ementaResumo) {
        if(ementaResumo != null){
            this.ementaResumo = ementaResumo;
        }
    }

    public String getAlunos() {
        String strAlunos = "";
        for(Aluno a: alunos){
            strAlunos += String.format("%s;", a.getNome());
        }
        return strAlunos;
    }

    public void setAlunos(ArrayList<Aluno> alunos) {
        if(alunos != null){
            this.alunos = alunos;
        }
    }

    public String toString() {
        return "Disciplinas{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", codigo='" + codigo + '\'' +
                ", cursos=" + cursos +
                ", cargaHr=" + cargaHr +
                ", ementa='" + ementa + '\'' +
                ", ementaResumo='" + ementaResumo + '\'' +
                ", alunos=" + alunos +
                '}';
    }
}
