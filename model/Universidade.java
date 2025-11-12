package model;

import java.util.Objects;

public class Universidade {
    int id;
    String unidade;
    String instituicao;

    public Universidade() {
        super();
    }

    public Universidade(int id, String unidade, String instituicao){
        super();
        setId(id);
        setUnidade(unidade);
        setInstituicao(instituicao);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        if(id > 0 && this.id == 0){
            //setar id apenas 1 vez
            this.id = id;
        }
    }

    public String getUnidade() {
        return unidade;
    }

    public void setUnidade(String unidade) {
        if(unidade != null){
            this.unidade = unidade;
        }
    }

    public String getInstituicao() {
        return instituicao;
    }

    public void setInstituicao(String instituicao) {
        if(instituicao != null){
            this.instituicao = instituicao;
        }
    }

    public String toString() {
        return "Universidade{" +
                "id=" + id +
                ", unidade='" + unidade + '\'' +
                ", instituicao='" + instituicao + '\'' +
                '}';
    }
}
