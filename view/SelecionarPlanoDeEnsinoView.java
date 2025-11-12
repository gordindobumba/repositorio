package view;

import model.*;
import java.util.*;

public class SelecionarPlanoDeEnsinoView implements Observer{
    private Model model;
    private SelecionarPlanoDeEnsinoController controller;
    private String[] planos;
    Scanner sc = Input.scanner;

    public void init(Model model){
        if(model != null){
            this.model = model;
            controller = new SelecionarPlanoDeEnsinoController();
            model.attachObserver(this);
            planosDeEnsino();
        }
    }
    
    public void planosDeEnsino(){
        String opcao;
        String[] planos = model.listarPlanosDeEnsino();
        
        System.out.println("- Planos de Ensino -");
        System.out.println();
        for(int i = 1; 0 < planos.length; i++){
            System.out.println(planos[i]);
        }
        System.out.println();
        System.out.print("Qual plano você quer editar? ");
        opcao = sc.nextLine();
    }

    public void update(){
        planos = model.listarPlanosDeEnsino();
    }
}
