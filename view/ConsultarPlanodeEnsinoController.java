package view;

import model.*;
import model.PlanoNaoExisteException;

public class ConsultarPlanodeEnsinoController implements Observer{
    private Model model;
    private ConsultarPlanoDeEnsinoView view;

    public void init(Model model, ConsultarPlanoDeEnsinoView view) {
        if (model != null && view != null){
            this.model = model;  // Guarda o modelo
            this.view = view;	 // Guarda a view
            model.attachObserver(this);
        }
    }

    public void handleEvent(String event){
        String id;
        try{
            id = model.escolherPlanoDeEnsino(event);
            ListarPlanoDeEnsinoView view1 = new ListarPlanoDeEnsinoView();
            view1.setId(id);
            view1.init(model);
        }catch(PlanoNaoExisteException e){
            view.mensagemErro();
        }
    }

    public void update(){}
}
