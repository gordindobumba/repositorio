package model;

public class Aluno {
    private String nome;
    private int matricula;

    public Aluno() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (nome != null) {
            this.nome = nome;
        }
    }

    public int getMatricula() {
        return matricula;
    }

    public void setMatricula(int matricula) {
        if (matricula > 0 && this.matricula == 0) {
            this.matricula = matricula;
        }
    }

    public String toString() {
        return "Nome: " + nome + '\'' +
                ", matricula=" + matricula;
    }
}
