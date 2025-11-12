package view;

import model.*;

public class PlanoDeEnsinoController implements Observer{
    private Model model;
    private PlanoDeEnsinoView view;

    public void init(Model model, PlanoDeEnsinoView view) {
        if (model != null && view != null){
            this.model = model;
            this.view = view;
            model.attachObserver(this);
        }
    }

    public int escolherOpcao(int opcao) throws OpcaoInvalidaException{
        if(opcao == 1 || opcao == 2 || opcao  == 3 || opcao == 4 || opcao == 5) {
            return opcao;
        }
        throw new OpcaoInvalidaException("Opção inválida. Tente novamente. ");
    }

    public void handleEvent(int event) throws PlanoNaoExisteException{
        try {
            escolherOpcao(event);
            switch (event) {
                case 1:
                    ConsultarPlanoDeEnsinoView view1 = new ConsultarPlanoDeEnsinoView(); //ir pra tela consultar plano
                    view1.init(model);
                    break;
                case 2:
                    AddPlanodeEnsinoView view2 = new AddPlanodeEnsinoView(); //ir pra tela adicionar plano
                    view2.init(model);
                    break;
                case 3:
                    RemoverPlanoDeEnsinoView view3 = new RemoverPlanoDeEnsinoView(); //ir pra tela remover plano
                    view3.init(model);
                    break;
                case 4:
                    EditarPlanoDeEnsinoView view4 = new EditarPlanoDeEnsinoView(); //ir pra tela editar plano
                    view4.init(model);
                    break;
                case 5:
                    model.deslogarUsuario();
                    PlanosNaoLogadoView view5 = new PlanosNaoLogadoView(); //ir pra tela de planos com usuário logado
                    view5.init(model);
                    break;
            }
        }catch(OpcaoInvalidaException e){
            view.opcaoInvalida(e.getMessage());
        }
    }

    public void update(){}
    
}
