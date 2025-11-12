package model;

public class Professor extends Usuario {
    private String email;
    private String titulacao;

    public Professor() {
        super();
    }

    public Professor(String email, String titulacao) {
        super();
        setEmail(email);
        setTitulacao(titulacao);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
           if(email != null){
               this.email = email;
           }
    }

    public String getTitulacao() {
        return titulacao;
    }

    public void setTitulacao(String titulacao) {
           if(titulacao != null){
               this.titulacao = titulacao;
           }
    }
    
    public String toString() {
        return "Professor{" +
                "id=" + super.getId() +
                ", nome='" + super.getNome() + '\'' +
                ", email='" + email + '\'' +
                ", titulacao='" + titulacao + '\'' +
                '}';
    }
}
