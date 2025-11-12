package view;

import model.*;

public class CadastroController implements Observer {
    private Model model;
    private CadastroView view;
    private String usuario, senha1, senha2;
    private int siape;

    public void init(Model model, CadastroView view) {  
        if (model != null && view != null) {
            this.model = model;  // Guarda o modelo
            this.view = view;     // Guarda a view
            model.attachObserver(this);
        }
    }

    public void cadastrar(){
        view.solicitarUsuario();
        usuario = view.getUsuario();
        view.solicitarSIAPE();
        siape = view.getSiape();
        view.solicitarSenha();


        while (view.getSenha().length() < 8){
            view.mensagem("A senha deve ter no minimo 8 digitos!");
            view.solicitarSenha();
        }

        while (!view.getSenha().equals(view.getSenha2())) {
            view.mensagem("As senhas devem coincidir!");
            view.solicitarSenha();
        }
        senha1 = view.getSenha();
        model.adicionarUsuario(usuario, siape, senha1);
        view.mensagem("Cadastro efetuado com sucesso!");

        view.opcao();
    }
    public void handleEvent(String event){
        switch (event) {
            case "1" : //voltar
                break;
            case "2" : //cadastrar
                CadastroView view3 = new CadastroView(); //ir para tela de cadastro
                view3.init(model);
                break;
            default:
                view.mensagem("Opção inválida.Tente novamente!");
                view.opcao();
                break;
        }
    }

    public void update(){}
}


