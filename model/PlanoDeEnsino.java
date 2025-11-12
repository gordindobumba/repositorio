package model;

import java.util.ArrayList;

public class PlanoDeEnsino {
    private String id;
    private String disciplina;
    private String nomeProfessor;
    private int diaCriacao;
    private int mesCriacao;
    private int anoCriacao;
    private String dataCriacao;
    private ArrayList<String> objetivos;
    private String metodologia;
    private String avaliacao;
    private String bibliografia;

    public PlanoDeEnsino() {
        super();
    }

    public PlanoDeEnsino(String id, String disciplina, String nomeProfessor, int diaCriacao, int mesCriacao, int anoCriacao, ArrayList<String> objetivos, String metodologia, String avaliacao, String bibliografia){
        setId(id);
        setDisciplina(disciplina);
        setNomeProfessor(nomeProfessor);
        setDataCriacao(diaCriacao, mesCriacao, anoCriacao);
        setObjetivos(objetivos);
        setMetodologia(metodologia);
        setAvaliacao(avaliacao);
        setBibliografia(bibliografia);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        if(id != null){
            this.id = id;
        }
    }

    public String getDisciplina() {
        return disciplina;
    }

    public void setDisciplina (String disciplina) {
        if(disciplina != null){
            this.disciplina = disciplina;
        }
    }

    public String getNomeProfessor() {
        return nomeProfessor;
    }

    public void setNomeProfessor(String nomeProfessor) {
        if(nomeProfessor != null){
            this.nomeProfessor = nomeProfessor;
        }
    }

    public String getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(int diaCriacao, int mesCriacao, int anoCriacao) {
        if (anoCriacao > 1900 && anoCriacao <= 2025) {
            if (mesCriacao > 0 && mesCriacao <= 12) {
                if (diaCriacao > 0 && diaCriacao <= 31) {
                    switch (mesCriacao) {
                        case 1:
                        case 3:
                        case 5:
                        case 7:
                        case 8:
                        case 10:
                        case 12:
                            this.diaCriacao = diaCriacao;
                            break;
                        case 2:
                            if (diaCriacao <= 28) {
                                this.diaCriacao = diaCriacao;
                            }
                            break;
                        case 4:
                        case 6:
                        case 9:
                        case 11:
                            if (diaCriacao <= 30) {
                                this.diaCriacao = diaCriacao;
                            }
                            break;
                    }
                }
                this.mesCriacao = mesCriacao;
            }
            this.anoCriacao = anoCriacao;
        }
        dataCriacao = String.format("%d/%d/%d", diaCriacao, mesCriacao, anoCriacao);
    }


public String getObjetivos() {
        //retornar String com os objetivos do ArrayList, cada objetivo é separado por ;
        String strObjetivos = "";
        for(String o: objetivos){
            strObjetivos += String.format("%s;", o);
        }
        return strObjetivos;
    }

    public void setObjetivos(ArrayList<String> objetivos) {
        if(objetivos != null && !objetivos.isEmpty()){
            this.objetivos = objetivos;
        }
    }

    public String getMetodologia() {
        return metodologia;
    }

    public void setMetodologia(String metodologia) {
        if(metodologia != null){
            this.metodologia = metodologia;
        }
    }

    public String getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(String avaliacao) {
        if(avaliacao != null){
            this.avaliacao = avaliacao;
        }
    }

    public String getBibliografia() {
        return bibliografia;
    }

    public void setBibliografia(String bibliografia) {
        if(bibliografia != null){
            this.bibliografia = bibliografia;
        }
    }

    public String toString() {
        return "PlanoDeEnsino{" +
                "id=" + id +
                ", disciplina=" + disciplina +
                ", professor=" + nomeProfessor +
                ", dataCriacao=" + dataCriacao +
                ", objetivos=" + objetivos +
                ", metodologia='" + metodologia + '\'' +
                ", avaliacao='" + avaliacao + '\'' +
                ", bibliografia='" + bibliografia + '\'' +
                '}';
    }
}
