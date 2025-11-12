package view;

import model.Model;
import model.PlanoNaoExisteException;

public class SelecionarPlanoDeEnsinoController implements Observer{
    private Model model;
    private SelecionarPlanoDeEnsinoView view;

    public void init(Model model, SelecionarPlanoDeEnsinoView view) {
        if (model != null && view != null){
            this.model = model;  // Guarda o modelo
            this.view = view;	 // Guarda a view
            model.attachObserver(this);
        }
    }

    public void handleEvent(String event){
        try{
            model.escolherPlanoDeEnsino(event);
            EditarPlanoDeEnsinoView view1 = new EditarPlanoDeEnsinoView();
            view1.init(model);
        }catch(PlanoNaoExisteException e){}
    }

    public void update(){}
}
