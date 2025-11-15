package view;

import model.*;

import java.util.ArrayList;
import java.util.Scanner;

public class EditarPlanoDeEnsinoController implements Observer{
    private Model model;
    private EditarPlanoDeEnsinoView view;
    private String[] planos;
    private String id, disc, professor, dia, mes, ano, metodologia, avaliacao, bibliografia;
    private String[] data;
    private ArrayList<String> objetivos = new ArrayList<>();

    public void init(Model model, EditarPlanoDeEnsinoView view) {
        if (model != null && view != null){
            this.model = model;  // Guarda o modelo
            this.view = view;	 // Guarda a view
            model.attachObserver(this);
        }
    }

    //função para encontrar plano a ser editado pelo código
    public void encontrarPlano() throws PlanoNaoExisteException {
        planos = model.listarPlanosDeEnsino();

        view.mensagem("Planos de Ensino");

        view.mostrarPlanosDoUsuario(planos);

        view.solicitarCodigo();
        id = view.getId();
        String idPlano = model.escolherPlanoDeEnsino(id);
        if(id.equals(idPlano)){
            view.mensagem("Plano de ensino encontrado!");
            view.opcao();
        } else {
            view.mensagem("Plano de ensino inválido. Tente novamente!");
            encontrarPlano();
        }
    }

    //função para editar aquilo que o usuário escolheu
    public void handleEvent(String event) throws PlanoNaoExisteException {
        PlanoDeEnsinoView view1 = new PlanoDeEnsinoView();
        switch (event) {
            case "1" : //editar nome
                view.solicitarNome();
                disc = view.getDisciplina();
                model.editarNomePlano(id, disc);
                view.mensagem("Plano de ensino editado com sucesso!");
                view1.init(model);
                break;
            case "2" : //editar professor
                view.solicitarProfessor();
                professor = view.getProfessor();
                model.editarProfessorPlano(id, professor);
                view.mensagem("Plano de ensino editado com sucesso!");
                view1.init(model);
                break;
            case "3": //editar data
                view.solicitarData();
                dia = view.getDia();
                mes = view.getMes();
                ano = view.getAno();
                model.editarDataPlano(id, Integer.parseInt(dia), Integer.parseInt(mes), Integer.parseInt(ano));
                view.mensagem("Plano de ensino editado com sucesso!");
                view1.init(model);
                break;
            case "4": //editar objetivos
                view.solicitarObjetivos();
                objetivos = view.getObjetivos();
                model.editarObejtivosPlano(id, objetivos);
                view.mensagem("Plano de ensino editado com sucesso!");
                view1.init(model);
                break;
            case "5": //editar metodologia
                view.solicitarMetodologia();
                metodologia = view.getMetodologia();
                model.editarMetodoogiaPlano(id, metodologia);
                view.mensagem("Plano de ensino editado com sucesso!");
                view1.init(model);
                break;
            case "6": //editar avaliação
                view.solicitarAvaliacao();
                avaliacao = view.getAvaliacao();
                model.editarAvaliacaoPlano(id, avaliacao);
                view.mensagem("Plano de ensino editado com sucesso!");
                view1.init(model);
                break;
            case "7": //editar bibliografia
                view.solicitarBibliografia();
                bibliografia = view.getBibliografia();
                model.editarBibliografiaPlano(id, bibliografia);
                view.mensagem("Plano de ensino editado com sucesso!");
                view1.init(model);
                break;
            default:
                view.mensagem("Opção inválida.Tente novamente!");
                view.opcao();
                break;
        }
    }

    public void update(){planos = model.listarPlanosDeEnsino();}
}
