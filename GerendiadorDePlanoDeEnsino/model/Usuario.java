package model;


public class Usuario  {
	private String nome;	
	private int id;	
	private String senha;
	
	public Usuario() {
		super();
	}

	public Usuario(String nome, int id, String senha) {
		setNome(nome);
		setId(id);
		setSenha(senha);
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		if(nome != null){
			this.nome = nome;
		}
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		if(id > 0 && this.id == 0){
			//setar id apenas 1 vez
			this.id = id;
		}
	}
	
	public String getSenha() {
		return senha;
	}
	
	public void setSenha(String senha) {
		if(senha != null){
			if((senha.length() >= 8) && (senha.length() <= 20)){
				//senha com no mínimo 8 caracteres e no máximo 20
				this.senha = senha;
			}
		}
	}

	public String toString() {
		return nome + " " + id;
	}
}
