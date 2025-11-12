package model;

import java.util.HashMap;
import java.util.ArrayList;
import view.*;

public class Model {

    private HashMap<Integer,Usuario> usuarios = new HashMap<Integer, Usuario>(); // Usuários do sistema
    private Usuario usuarioAutenticado;	// Usuário autenticado pelo sistema
    private ArrayList<Observer> observers = new ArrayList<Observer>(); // Lista de observadores interessados no modelo
    private HashMap<String, PlanoDeEnsino> planosDeEnsino = new HashMap<>(); // Planos de ensino do sistema

    private static Model instanciaUnica;

    private Model(){
        super();
    }

    //padrão singleton (se não tiver instânica, cria uma, se tiver, usa ela)
    public static Model getInstancia(){
        if (instanciaUnica == null){
            instanciaUnica = new Model();
        }
        return instanciaUnica;
    }

    //método utilizado para notificar todos os observadores contidos no arrayList que o modelo mudou
    public void notifica() {
        for (Observer o : observers) {
            o.update(); // update é a operação definida na interface Observer
        }
    }

    //devolve o nome de um usuário do mapeamento (se ele existir)
    public String getNomeUsuario(int id) {
        if (id > 0) {
            Usuario usuario = usuarios.get(id);
            if (usuario != null){
                return usuario.getNome();
            }
        }
        return "";
    }

    //adiciona um usuário no mapeamento (se ele existir e não estiver mapeado)
    public void adicionarUsuario(String nome, int id, String senha) {
        if(usuarios.containsKey(id)){
            throw new RuntimeException(String.format("Já existe um usuário com o id informado (%d).", id));
        }

        Usuario usuario = new Usuario(nome, id, senha);

        usuarios.put(id, usuario);
        notifica();
    }

    //serviço para autenticar um usuário se o login estiver válido e correto
    public boolean autenticarUsuario(int id, String senha) {;
        boolean autenticado = false;
        if (id > 0 && senha != null) {
            Usuario usuario = usuarios.get(id);
            if (usuario != null && senha.equals(usuario.getSenha())){
                usuarioAutenticado = usuario;
                autenticado = true;
            }
        }
        notifica();
        return autenticado;
    }

    //desloga um usuário do sistema
    public void deslogarUsuario() {
        usuarioAutenticado = null;
        notifica();
    }

    //devolve o id do usuário autenticado, se não tiver nenhum usuário autenticado ele devolve 0
    public int getUsuarioAutenticado() {
        if (usuarioAutenticado != null){
            return usuarioAutenticado.getId();
        } else {
            return 0;
        }
    }

    //registra um observador na lista de observadores
    public void attachObserver(Observer observer) {
        if (observer != null) {
            observers.add(observer);
        }
    }

    //exclui um observador da lista de observadores
    public void detachObserver(Observer observer) {
        if (observer != null) {
            observers.remove(observer);
        }
    }

    //informa o total de usuários cadastrados
    public int getTotalUsuarios() {
        return usuarios.size();
    }

    //adiciona plano de ensino no mapamento a partir dos parâmetros informados
    public void adicionarPlanoDeEnsino(String id, String disciplina, String nomeProfessor, int diaCriacao, int mesCriacao, int anoCriacao, ArrayList<String> objetivos, String metodologia, String avaliacao, String bibliografia) {
        if(planosDeEnsino.containsKey(id)){
            throw new RuntimeException(String.format("Já existe um plano de ensino com esse id (%s).", id));
        }

        PlanoDeEnsino plano = new PlanoDeEnsino(id, disciplina, nomeProfessor, diaCriacao, mesCriacao, anoCriacao, objetivos, metodologia, avaliacao, bibliografia);

        planosDeEnsino.put(id, plano);
        notifica();
    }

    //remove plano de ensino no mapamento pelo id (se ele existir)
    public void removerPlanoDeEnsino(String id) throws PlanoNaoExisteException {
        if(id == null){
            throw new RuntimeException("Código do plano de ensino inválido.");
        } else if(planosDeEnsino.isEmpty()){
            throw new RuntimeException("Não existem planos de ensino.");
        }

        if(!planosDeEnsino.containsKey(id)){
            throw new PlanoNaoExisteException(String.format("O plano de ensino com id %s não existe.", id));
        }
        planosDeEnsino.remove(id);
        notifica();
    }

    //editar o nome do plano de ensino
    public void editarNomePlano(String idOriginal, String novoNome) throws PlanoNaoExisteException{
        if(idOriginal == null){
            throw new NullPointerException("O plano de ensino não pode ser nulo.");
        }

        if(!planosDeEnsino.containsKey(idOriginal)){
            throw new PlanoNaoExisteException(String.format("O plano de ensino com id %s não existe.", idOriginal));
        }

        planosDeEnsino.get(idOriginal).setDisciplina(novoNome);

        notifica();
    }

    //editar o professor do plano de ensino
    public void editarProfessorPlano(String idOriginal, String novoProfessor) throws PlanoNaoExisteException{
        if(idOriginal == null){
            throw new NullPointerException("O plano de ensino não pode ser nulo.");
        }

        if(!planosDeEnsino.containsKey(idOriginal)){
            throw new PlanoNaoExisteException(String.format("O plano de ensino com id %s não existe.", idOriginal));
        }

        planosDeEnsino.get(idOriginal).setNomeProfessor(novoProfessor);

        notifica();
    }

    //editar data do plano de ensino
    public void editarDataPlano(String idOriginal, int novoDia, int novoMes, int novoAno) throws PlanoNaoExisteException{
        if(idOriginal == null){
            throw new NullPointerException("O plano de ensino não pode ser nulo.");
        }

        if(!planosDeEnsino.containsKey(idOriginal)){
            throw new PlanoNaoExisteException(String.format("O plano de ensino com id %s não existe.", idOriginal));
        }

        planosDeEnsino.get(idOriginal).setDataCriacao(novoDia, novoMes, novoAno);

        notifica();
    }

    //editar os objetivos do plano de ensino
    public void editarObejtivosPlano(String idOriginal, ArrayList<String> novosObjetivos) throws PlanoNaoExisteException{
        if(idOriginal == null){
            throw new NullPointerException("O plano de ensino não pode ser nulo.");
        }

        if(!planosDeEnsino.containsKey(idOriginal)){
            throw new PlanoNaoExisteException(String.format("O plano de ensino com id %s não existe.", idOriginal));
        }

        planosDeEnsino.get(idOriginal).setObjetivos(novosObjetivos);

        notifica();
    }

    //editar metodologia do plano de ensino
    public void editarMetodoogiaPlano(String idOriginal, String novaMetodologia) throws PlanoNaoExisteException{
        if(idOriginal == null){
            throw new NullPointerException("O plano de ensino não pode ser nulo.");
        }

        if(!planosDeEnsino.containsKey(idOriginal)){
            throw new PlanoNaoExisteException(String.format("O plano de ensino com id %s não existe.", idOriginal));
        }

        planosDeEnsino.get(idOriginal).setMetodologia(novaMetodologia);

        notifica();
    }

    //editar avaliação do plano de ensino
    public void editarAvaliacaoPlano(String idOriginal, String novaAvaliacao) throws PlanoNaoExisteException{
        if(idOriginal == null){
            throw new NullPointerException("O plano de ensino não pode ser nulo.");
        }

        if(!planosDeEnsino.containsKey(idOriginal)){
            throw new PlanoNaoExisteException(String.format("O plano de ensino com id %s não existe.", idOriginal));
        }

        planosDeEnsino.get(idOriginal).setAvaliacao(novaAvaliacao);

        notifica();
    }

    //editar bibliografia do plano de ensino
    public void editarBibliografiaPlano(String idOriginal, String novaBibliografia) throws PlanoNaoExisteException{
        if(idOriginal == null){
            throw new NullPointerException("O plano de ensino não pode ser nulo.");
        }

        if(!planosDeEnsino.containsKey(idOriginal)){
            throw new PlanoNaoExisteException(String.format("O plano de ensino com id %s não existe.", idOriginal));
        }

        planosDeEnsino.get(idOriginal).setBibliografia(novaBibliografia);

        notifica();
    }

    //lista os planos de ensino cadastrados
    public String[] listarPlanosDeEnsino(){
        int planosCadastrados = planosDeEnsino.size();
        String[] planos = new String[planosCadastrados];

        int indice = 0;
        for (PlanoDeEnsino plano : planosDeEnsino.values()) {
            planos[indice] = String.format("%s - %s;", plano.getId(), plano.getDisciplina());
            indice++;
        }

        return planos;
    }

    public String escolherPlanoDeEnsino(String id) throws PlanoNaoExisteException {
        if(id == null){
            throw new PlanoNaoExisteException("Id inválido.");
        }
        if(!planosDeEnsino.containsKey(id)){
            throw new PlanoNaoExisteException(String.format("O plano de ensino com id %s não existe.", id));
        }
        return id;
    }

    //consultar informações do plano de ensino pelo id
    public String[] consultarPlano (String id) {
        String[] info = new String[8];
        if(planosDeEnsino.containsKey(id)){
            PlanoDeEnsino plano = (PlanoDeEnsino) planosDeEnsino.get(id);
            info[0] = String.format("ID: " + plano.getId());
            info[1] = String.format("Disciplina: " + plano.getDisciplina());
            info[2] = String.format("Professor: " + plano.getNomeProfessor());
            info[3] = String.format("Data de criação: " + plano.getDataCriacao());
            info[4] = String.format("Objetivos: " + plano.getObjetivos());
            info[5] = String.format("Metodologia: " + plano.getMetodologia());
            info[6] = String.format("Avaliação: " + plano.getAvaliacao());
            info[7] = String.format("Bibliografia: " + plano.getBibliografia());
            return info;
        }
        return null;
    }
}
