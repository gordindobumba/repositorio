package view;

import model.*;

public class RemoverPlanoDeEnsinoController implements Observer{
    private Model model;
    private RemoverPlanoDeEnsinoView view;

    public void init(Model model, RemoverPlanoDeEnsinoView view) {
        if (model != null && view != null){
            this.model = model;  // Guarda o modelo
            this.view = view;	 // Guarda a view
            model.attachObserver(this);
        }
    }


    public void handleEvent(String event){
        try {
            model.removerPlanoDeEnsino(event);
            view.mensagem("Plano de ensino removido com sucesso.");
            PlanoDeEnsinoView view1 = new PlanoDeEnsinoView();
            view1.init(model);
        }catch(PlanoNaoExisteException e) {
            view.mensagemErro(e.getMessage());
        }catch(RuntimeException r){
            view.mensagemErroVazio();
        }
    }

    public void update(){}
}
