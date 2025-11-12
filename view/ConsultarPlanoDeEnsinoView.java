package view;


import java.util.*;
import model.*;
import model.Model;

public class ConsultarPlanoDeEnsinoView implements Observer{
    private Model model;
    private ConsultarPlanodeEnsinoController controller;
    private String[] planos;
    Scanner sc = Input.scanner;

    public void init(Model model){
        if(model != null){
            this.model = model;
            controller = new ConsultarPlanodeEnsinoController();
            controller.init(model, this);
            model.attachObserver(this);
            planosDeEnsino();
        }
    }

    public void mensagemErro(){
        System.out.println("Esse plano não existe. Voltando ao menu...");
    }

    public void planosDeEnsino(){
        String opcao;
        planos = model.listarPlanosDeEnsino();
        
        System.out.println("- Planos de Ensino -");
        System.out.println();
        if(planos == null){
            System.out.println("Ainda não há planos.");
        }else{
            for(int i = 0; i < planos.length; i++){
                System.out.println(planos[i]);
            }
        }
        System.out.println();
        System.out.print("Digite o código do plano que quer consultar: ");
        String event = sc.nextLine();
        controller.handleEvent(event);
    }

    public void update(){
        planos = model.listarPlanosDeEnsino();
    }
}
