package view;

import java.util.*;
import model.*;

public class TelaInicialView implements Observer{
    private Model model;
    private TelaInicialController controller;
    private boolean terminar = false;
    Scanner sc = Input.scanner;

    public void terminarSistema(){
        terminar = true;
    }

    public void init(Model model) throws PlanoNaoExisteException {
        if(model != null){
            this.model = model;
            controller = new TelaInicialController();
            controller.init(model, this);
            model.attachObserver(this);
            menuPrincipal();
        }
    }

    public void mensagem(String msg){
        System.out.println(msg);
        System.out.println();
    }

    public void menuPrincipal() throws PlanoNaoExisteException{
        String[] menu = {"1 - | Consultar             |", "2 - | Login (Professor)     |", "3 - | Cadastrar (Professor) |", "4 - | Encerrar o programa   |"};
        do{
            System.out.println();
            System.out.println("- Gerenciador de Planos de Ensino -");
            System.out.println();
            System.out.println(menu[0]);
            System.out.println(menu[1]);
            System.out.println(menu[2]);
            System.out.println(menu[3]);
            System.out.println();
            System.out.print("Digite a opcao que deseja realizar:  ");
            int event = sc.nextInt();
            sc.nextLine();
            controller.handleEvent(event);
        }while(terminar == false);
    }

    public void update(){
        return;
    }
}