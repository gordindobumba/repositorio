package view;

import model.Model;
import model.PlanoNaoExisteException;

import java.util.*;

public class CadastroView implements Observer{
    private String usuario, senha1, senha2;
    private int siape;
    private String opcao = "2";
    private Model model;
    private CadastroController controller;
    public Scanner sc = Input.scanner;

    public void init(Model model){
        if(model != null){
            this.model = model;
            controller = new CadastroController();
            controller.init(model, this);
            model.attachObserver(this);
            controller.cadastrar();
        }
    }

    //função para solicitar o nome do professor
    public void solicitarUsuario(){
        System.out.println();
        System.out.println("- Cadastro do Professor -");
        System.out.println();
        System.out.print("Nome: ");
        usuario = sc.nextLine();
    }

    //função para solicitar o siape do professor
    public void solicitarSIAPE(){
        System.out.print("SIAPE: ");
        siape = Integer.parseInt(sc.nextLine());
    }

    public void solicitarSenha(){
        System.out.print("Senha(no minimo 8 digitos): ");
        senha1 = sc.nextLine();
        System.out.print("Confirmar senha: ");
        senha2 = sc.nextLine();
    }

    //função para apresentar mensagens de retorno na view
    public void mensagem(String msg){
        System.out.println(msg);
        System.out.println();
    }

    public void opcao(){
        System.out.println("1 - |Voltar       |");
        System.out.println("2 - |Novo cadastro|");
        System.out.println();
        System.out.print("Digite a opcao que deseja realizar: ");
        opcao = sc.nextLine();
        controller.handleEvent(opcao);
    }

    public String getOpcao(){
        return opcao;
    }

    public int getSiape(){
        return siape;
    }

    public String getUsuario(){
        return usuario;
    }

    public String getSenha(){
        return senha1;
    }

    public String getSenha2(){
        return senha2;
    }

    public void update(){}
}
