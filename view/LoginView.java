package view;

import java.util.*;
import model.*;

public class LoginView implements Observer{
    private String senha;
    private int id, opcao;
    private Model model;
    private LoginController controller;
    Scanner sc = Input.scanner;

    public void init(Model model) throws PlanoNaoExisteException {
        if(model != null){
            this.model = model;
            controller = new LoginController();
            controller.init(model, this);
            model.attachObserver(this);
            controller.fazerLogin();
        }
    }

    //função para realizar login
    public void login() {
        System.out.println();
        System.out.println("Login do Professor");
        System.out.print("SIAPE: ");
        id = sc.nextInt();
        sc.nextLine(); //limpar buffer
        System.out.print("Senha: ");
        senha = sc.nextLine();
    }

    //função para apresentar algum retorno do controller
    public void mensagem(String msg){
        System.out.println(msg);
        System.out.println();
    }

    public String mensagemErroLogin(String msg){
        String mensagem;

        System.out.println(msg);
        System.out.println();
        mensagem = sc.nextLine();

        return mensagem;

    }

    public int getId() {
        return id;
    }

    public String getSenha() {
        return senha;
    }

    public void update(){
        return;
    }
}
