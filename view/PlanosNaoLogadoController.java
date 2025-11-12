package view;

import model.*;

public class PlanosNaoLogadoController implements Observer{
    private Model model;
    private PlanosNaoLogadoView view;

    public void init(Model model, PlanosNaoLogadoView view) {
        if (model != null && view != null){
            this.model = model;  // Guarda o modelo
            this.view = view;	 // Guarda a view
            model.attachObserver(this);
        }
    }

    public int escolherOpcao(int opcao) throws OpcaoInvalidaException{
        if(opcao == 1 || opcao == 2) {
            return opcao;
        }
        throw new OpcaoInvalidaException("Opção inválida. Tente novamente. ");
    }


    public void handleEvent(int event){
        try {
            int id = escolherOpcao(event);
            switch (id) {
                case 1:
                    ConsultarPlanoDeEnsinoView view1 = new ConsultarPlanoDeEnsinoView();
                    view1.init(model);
                    break;
                case 2:
                    break; // Como o menu principal está em um loop while, ele sempre será chamado se a escolha não criar uma tela nova
            }
        }catch(OpcaoInvalidaException e) {
            view.opcaoInvalida(e.getMessage());
        }
    }

    public void update(){}
}
