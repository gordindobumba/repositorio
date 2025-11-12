package view;

import model.*;
import model.PlanoNaoExisteException;

import java.util.*;

public class PlanoDeEnsinoView implements Observer{
    private Model model;
    private PlanoDeEnsinoController controller;
    private String[] planos;
    private int useId;
    Scanner sc = Input.scanner;

    public PlanoDeEnsinoView() {}

    public PlanoDeEnsinoView(int userId) {
        this();
        this.useId = userId;
    }


    public void init(Model model) throws PlanoNaoExisteException {
        if(model != null){
            this.model = model;
            controller = new PlanoDeEnsinoController();
            controller.init(model, this);
            planosDeEnsino();
            model.attachObserver(this);
        }
    }

    public void opcaoInvalida(String mensagem) throws PlanoNaoExisteException{
        System.out.print(mensagem);
        int id = sc.nextInt();
        controller.handleEvent(id);
    }

    public void planosDeEnsino() throws PlanoNaoExisteException{
        String opcao;

        planos = model.listarPlanosDeEnsino();
        
        System.out.println("- Planos de Ensino -");
        System.out.println();
        System.out.println("Seus Planos de Ensino");

        System.out.println();
        if(planos == null){
            System.out.println("   Ainda não há planos.   ");
        }else{
            for(int i = 0; i < planos.length; i++){
                System.out.println(planos[i]);
            }
        }
        System.out.println();
        System.out.println("1 - |  Consultar plano |");
        System.out.println("2 - |  Adicionar plano |");
        System.out.println("3 - |  Remover plano   |");
        System.out.println("4 - |  Editar plano    |");
        System.out.println("5 - |  Logout          |");
        System.out.println();
        System.out.print("Digite a opcao que deseja realizar: ");
        opcao = sc.nextLine();
        controller.handleEvent(Integer.parseInt(opcao));
    }

    public void mensagem(String msg){
        System.out.println(msg);
        System.out.println();
    }

    public int mensagemEscolha(String msg){
        int escolha;

        System.out.println(msg);
        System.out.println();
        escolha = sc.nextInt();
        sc.nextLine();

        return escolha;
    }
    public void update(){
        planos = model.listarPlanosDeEnsino();
    }
}
