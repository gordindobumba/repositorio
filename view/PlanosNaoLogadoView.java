package view;

import model.Model;
import java.util.*;

public class PlanosNaoLogadoView implements Observer{
    private Model model;
    private PlanosNaoLogadoController controller;
    private String[] planos;
    Scanner sc = Input.scanner;

    public void init(Model model){
        if(model != null){
            this.model = model;
            controller = new PlanosNaoLogadoController();
            controller.init(model, this);
            planosDeEnsino();
            model.attachObserver(this);
        }
    }

    public void opcaoInvalida(String mensagem){
        System.out.print(mensagem);
        int id = sc.nextInt();
        controller.handleEvent(id);
    }
    
    public void planosDeEnsino(){
        int opcao;
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
        System.out.print("1 - |Consultar|" + "   " + "2 - |Voltar|");
        System.out.println();
        System.out.print("Digite a opção que deseja realizar: ");
        opcao = sc.nextInt();
        sc.nextLine();
        controller.handleEvent(opcao);
    }

    public void update(){
        planos = model.listarPlanosDeEnsino();
    }
}
