package view;

import model.*;


public class TelaInicialController implements Observer{
    private Model model; //guardar o modelo a ser controlado
    private TelaInicialView view; //guarda a view a ser controlada

    // Inicialização do controller da view principal
    public void init(Model model, TelaInicialView view) {
        if (model != null && view != null){
            this.model = model;  // Guarda o modelo
            this.view = view;	 // Guarda a view
            model.attachObserver(this);
        }
    }

    public void update() {}

    public void handleEvent(int event) throws PlanoNaoExisteException {
        switch (event) {
            case 1:
                PlanosNaoLogadoView view1 = new PlanosNaoLogadoView(); // ir para tela de consultar plano de ensino não logado
                view1.init(model);
                break;
            case 2:
                LoginView view2 = new LoginView(); // ir para tela de login de usuário
                view2.init(model);
                break;
            case 3:
                CadastroView view3 = new CadastroView(); //ir para tela de cadastro
                view3.init(model);
                break;
            case 4:
                view.terminarSistema(); //sair
                break;
            default:
                view.mensagem("Opção invalida. Tente novamente!");
                break;
        }
    }
}