package view;

import model.Model;
import model.PlanoNaoExisteException;

import java.util.*;

public class ListarPlanoDeEnsinoView implements Observer{
    private Model model;
    private ListarPlanoDeEnsinoController controller;
    private String id;
    Scanner sc = new Scanner(System.in);

    public void init(Model model) throws PlanoNaoExisteException {
        if(model != null){
            this.model = model;
            controller = new ListarPlanoDeEnsinoController();
            controller.init(model, this);
            model.attachObserver(this);
            listarPlano();
        }
    }

    public void setId(String id){
        this.id = id;
    }
    public String getId(){
        return id;
    }

    public void listarPlano() throws PlanoNaoExisteException {
        System.out.println();
        String [] dados = model.consultarPlano(id);
        for(int i = 0; i < 8 ; i++){
            System.out.println(dados[i]);
        }
        System.out.println();
        System.out.print("Digite qualquer caractere para voltar ao menu. ");
        String event = sc.nextLine();
        controller.handleEvent(event);
    }

    public void update(){}
}
