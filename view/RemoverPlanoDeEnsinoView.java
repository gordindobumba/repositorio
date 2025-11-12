package view;

import model.*;
import java.util.*;

public class RemoverPlanoDeEnsinoView implements Observer{
    private Model model;
    private RemoverPlanoDeEnsinoController controller;
    private String[] planos;
    Scanner sc = Input.scanner;

    public void init(Model model){
        if(model != null){
            this.model = model;
            controller = new RemoverPlanoDeEnsinoController();
            controller.init(model, this);
            model.attachObserver(this);
            planosDeEnsino();
        }
    }

    public void mensagemErro(String mensagem){
        System.out.print(mensagem + " ");
        String opcao = sc.nextLine();
        controller.handleEvent(opcao);
    }

    public void mensagemErroVazio(){
        System.out.println("Não há listas para remover. Voltando ao menu..");
    }

    public void mensagem(String msg){
        System.out.println(msg);
        System.out.println();
    }

    public void planosDeEnsino(){
        String opcao;
        planos = model.listarPlanosDeEnsino();
        
        System.out.println("- Planos de Ensino -");
        System.out.println();
        for(int i = 0; i < planos.length; i++){
            System.out.println(planos[i]);
        }
        System.out.println();
        System.out.print("Digite o código do plano que você quer remover: ");
        opcao = sc.nextLine();
        controller.handleEvent(opcao);
    }

    public void update(){
        planos = model.listarPlanosDeEnsino();
    }
}
