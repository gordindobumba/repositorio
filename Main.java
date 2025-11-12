import model.*;
import view.*;

public class Main {
    public static void main(String[] args) throws PlanoNaoExisteException {
        Model model = Model.getInstancia(); //primeiro instancia-se o modelo
        TelaInicialView view = new TelaInicialView(); //depois cria-se a primeira view
        view.init(model); //por fim, inicializa a view passando o modelo
    }
}