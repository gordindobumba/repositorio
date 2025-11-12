package view;

import model.*;

public class ListarPlanoDeEnsinoController implements Observer{
    private Model model;
    private ListarPlanoDeEnsinoView view;
    String plano;

    public void init(Model model, ListarPlanoDeEnsinoView view) {
        if (model != null && view != null){
            this.model = model;  // Guarda o modelo
            this.view = view;	 // Guarda a view
            model.attachObserver(this);
            plano = view.getId();
        }
    }

    public void handleEvent(String event) throws PlanoNaoExisteException{
        PlanoDeEnsinoView view1 = new PlanoDeEnsinoView();
        view1.init(model);
    }

    public void update(){}
}
