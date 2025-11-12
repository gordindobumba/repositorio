package view;

import model.*;


public class LoginController implements Observer{
    private Model model; //guardar o modelo a ser controlado
    private LoginView view; //guarda a view a ser controlada
    private int siape;
    private String senha;

    // Inicialização do controller da view principal
    public void init(Model model, LoginView view) {
        if (model != null && view != null){
            this.model = model;  // Guarda o modelo
            this.view = view;	 // Guarda a view
            model.attachObserver(this);
        }
    }

    public int getSiape() {
        return siape;
    }

    //fazer login com as informaçoes fornecidas na view
    public void fazerLogin() throws PlanoNaoExisteException {
        view.login();
        siape = view.getId();
        senha = view.getSenha();
        boolean autenticado = model.autenticarUsuario(siape, senha);
        if(autenticado){
            view.mensagem("Usuário autenticado com sucesso!");
            PlanoDeEnsinoView view1 = new PlanoDeEnsinoView(siape); //ir pra tela de planos com usuário logado
            view1.init(model);
        } else{
            view.mensagem("Dados incorretos!");
        }
    }

    public void update() {

    }

}