package model;

import java.util.Date;

public class Atividade {
    private int id;
    private int diaCriacao;
    private int mesCriacao;
    private int anoCriacao;
    private int[] dataCriacao = new int[3];
    private String tema;
    private String tipoAtividade;
    private String conteudoPrevisto;
    private String recursosDidaticos;

    public Atividade() {
    }

    public Atividade(int id, int diaCriacao, int mesCriacao, int anoCriacao, String tema, String tipoAtividade, String conteudoPrevisto, String recursosDidaticos) {
        setId(id);
        setDataCriacao(diaCriacao, mesCriacao, anoCriacao);
        setTema(tema);
        setTipoAtividade(tipoAtividade);
        setConteudoPrevisto(conteudoPrevisto);
        setRecursosDidaticos(recursosDidaticos);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (id > 0 && this.id == 0) {
            this.id = id;
        }
    }

    public int[] getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(int diaCriacao, int mesCriacao, int anoCriacao) {
        if(anoCriacao > 1900 && anoCriacao <= 2025) {
            if(mesCriacao > 0 && mesCriacao <= 12) {
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
                                if(diaCriacao <= 28) {
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

        dataCriacao[0] = diaCriacao;
        dataCriacao[1] = mesCriacao;
        dataCriacao[2] = anoCriacao;
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        if (tema != null) {
            this.tema = tema;
        }
    }

    public String getTipoAtividade() {
        return tipoAtividade;
    }

    public void setTipoAtividade(String tipoAtividade) {
        if (tipoAtividade.equals("PRATICA") || tipoAtividade.equals("TEORICA")) {
            this.tipoAtividade = tipoAtividade;
        }
    }

    public String getConteudoPrevisto() {
        return conteudoPrevisto;
    }

    public void setConteudoPrevisto(String conteudoPrevisto) {
        if (conteudoPrevisto != null) {
            this.conteudoPrevisto = conteudoPrevisto;
        }
    }

    public String getRecursosDidaticos() {
        return recursosDidaticos;
    }

    public void setRecursosDidaticos(String recursosDidaticos) {
        if  (recursosDidaticos != null) {
            this.recursosDidaticos = recursosDidaticos;
        }
    }

    public String toString() {
        return "ID: " + id +
                ", DataCriacao: " + dataCriacao +
                ", Tema='" + tema +
                ", TipoAtividade: " + tipoAtividade +
                ", ConteudoPrevisto: " + conteudoPrevisto +
                ", RecursosDidaticos=: " + recursosDidaticos;
    }
}
