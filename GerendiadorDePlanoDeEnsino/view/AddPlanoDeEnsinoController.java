package view;

import model.*;

import java.util.ArrayList;

public class AddPlanoDeEnsinoController implements Observer{
    private Model model;
    private AddPlanodeEnsinoView view;
    private String id, disc, professor, metodologia, avaliacao, bibliografia;
    private int dia, mes, ano;
    private ArrayList<String> objetivos = new ArrayList<>();
    private String[] data;

    public void init(Model model, AddPlanodeEnsinoView view) {
        if (model != null && view != null){
            this.model = model;  // Guarda o modelo
            this.view = view;	 // Guarda a view
            model.attachObserver(this);
        }
    }

    public void adicionar() throws PlanoNaoExisteException {
        view.solicitarCodigo();
        id = view.getId();
        view.solicitarNome();
        disc = view.getDisciplina();
        professor = model.getNomeUsuario(model.getUsuarioAutenticado());
        boolean dataValida = false;
        do {
            view.solicitarData();
            try {
                dia = Integer.parseInt(view.getDia());
                mes = Integer.parseInt(view.getMes());
                ano = Integer.parseInt(view.getAno());

                if (ano < 1900 || ano > 2025) {
                    view.mensagem("Ano inválido! Digite entre 1900 e 2025.");
                } else {
                    if (mes <= 0 || mes > 12) {
                        view.mensagem("Mês inválido! Digite entre 1 e 12.");
                    } else {
                        switch (mes) {
                            case 1:
                            case 3:
                            case 5:
                            case 7:
                            case 8:
                            case 10:
                            case 12:
                                if (dia <= 0 || dia > 31) {
                                    view.mensagem("Dia inválido! Digite entre 1 e 31.");
                                } else {
                                    dataValida = true;
                                }
                                break;
                            case 2:
                                if (dia <= 0 || dia > 28) {
                                    view.mensagem("Dia inválido! Digite entre 1 e 31.");
                                } else {
                                    dataValida = true;
                                    break;
                                }
                            case 4:
                            case 6:
                            case 9:
                            case 11:
                                if (dia <= 0 || dia > 30) {
                                    view.mensagem("Dia inválido! Digite entre 1 e 31.");
                                } else {
                                    dataValida = true;
                                    break;
                                }
                        }

                    }
                }
            } catch (NumberFormatException e) {
                view.mensagem("Formato de data inválido! Digite no formato DD/MM/AAAA.");
            }
        } while (!dataValida);
        view.solicitarObjetivos();
        objetivos = view.getObjetivos();
        view.solicitarMetodologia();
        metodologia = view.getMetodologia();
        view.solicitarAvaliacao();
        avaliacao = view.getAvaliacao();
        view.solicitarBibliografia();
        bibliografia = view.getBibliografia();

        model.adicionarPlanoDeEnsino(id, disc, professor, dia, mes, ano, objetivos, metodologia, avaliacao, bibliografia);
        view.mensagem("Plano de ensino adicionado com sucesso!");
        PlanoDeEnsinoView view1 = new PlanoDeEnsinoView(); //ir pra tela de planos com usuário logado
        view1.init(model);
        }

    public void update(){}
}
