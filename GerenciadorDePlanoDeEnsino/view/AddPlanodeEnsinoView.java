package view;

import model.*;
import java.util.*;

public class AddPlanodeEnsinoView implements Observer{
    private String id, disc, professor, dia, mes, ano, metodologia, avaliacao, bibliografia;
    private ArrayList<String> objetivos = new ArrayList<>();
    private String[] data;
    private Model model;
    private AddPlanoDeEnsinoController controller;
    Scanner sc = Input.scanner;

    public void init(Model model) throws PlanoNaoExisteException {
        if(model != null){
            this.model = model;
            controller = new AddPlanoDeEnsinoController();
            controller.init(model, this);
            model.attachObserver(this);
            controller.adicionar();
        }
    }

    //função para solicitar código do plano
    public void solicitarCodigo(){
        System.out.println("- Adicionar Plano de Ensino -");
        System.out.println();
        System.out.print("Código: ");
        id  = sc.nextLine();
    }

    //função para solicitar nome da disciplina
    public void solicitarNome(){
        System.out.print("Nome da disciplina: ");
        disc = sc.nextLine();
    }

    //função para solicitar data
    public void solicitarData(){
        System.out.print("Data de criação, em DD/MM/AAAA: ");
        data = sc.nextLine().split("/");
        dia = data[0];
        mes = data[1];
        ano = data[2];
    }

    //função para solicitar objetivos
    public void solicitarObjetivos(){
        System.out.print("Objetivos (Digite -- quando terminar de adicionar os objetivos): ");
        while(true){
            String objetivo = sc.nextLine();
            objetivos.add(objetivo);
            if(objetivo.equals("--")){
                break;
            }
        }
    }

    //função para solicitar metodologia
    public void solicitarMetodologia(){
        System.out.print("Metodologia:  ");
        metodologia = sc.nextLine();
    }

    //função para solicitar método de avaliação
    public void solicitarAvaliacao(){
        System.out.print("Avaliação: ");
        avaliacao = sc.nextLine();
    }

    //função para solicitar disciplina
    public void solicitarBibliografia(){
        System.out.print("Bibliografia: ");
        bibliografia = sc.nextLine();
    }

    //função para apresentar mensagem de retorno do controller
    public void mensagem(String msg){
        System.out.println(msg);
        System.out.println();
    }

    public String getDisciplina(){
        return disc;
    }
    
    public String getId(){
        return id;
    }
    
    public String getProfessor(){
        return professor;
    }

    public String getDia(){
        return dia;
    }

    public String getMes(){
        return mes;
    }

    public String getAno(){
        return ano;
    }

    public ArrayList<String> getObjetivos(){
        return objetivos;
    }

    public String getMetodologia(){
        return metodologia;
    }

    public String getAvaliacao(){
        return avaliacao;
    }

    public String getBibliografia(){
        return bibliografia;
    }

    public void update(){}
}
