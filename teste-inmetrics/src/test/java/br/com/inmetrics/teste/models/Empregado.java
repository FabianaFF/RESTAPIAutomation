package br.com.inmetrics.teste.models;

public class Empregado {

	private Acesso acesso;
	private Departamento departamento;
	private int departamentoId;
	private String admissao;
	private String cargo;
	private String comissao;
	private String cpf;
	private String nome;
	private String salario;
	private String sexo;
	private String tipoContratacao;
	
	public Acesso getAcesso() {
		return acesso;
	}
	public void setAcesso(Acesso acesso) {
		this.acesso = acesso;
	}
	public Departamento getDepartamento() {
		return departamento;
	}
	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}
	public int getDepartamentoId() {
		return departamentoId;
	}
	public void setDepartamentoId(int departamentoId) {
		this.departamentoId = departamentoId;
	}
	public String getAdmissao() {
		return admissao;
	}
	public void setAdmissao(String admissao) {
		this.admissao = admissao;
	}
	public String getCargo() {
		return cargo;
	}
	public void setCargo(String cargo) {
		this.cargo = cargo;
	}
	public String getComissao() {
		return comissao;
	}
	public void setComissao(String comissao) {
		this.comissao = comissao;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getSalario() {
		return salario;
	}
	public void setSalario(String salario) {
		this.salario = salario;
	}
	public String getSexo() {
		return sexo;
	}
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	public String getTipoContratacao() {
		return tipoContratacao;
	}
	public void setTipoContratacao(String tipoContratacao) {
		this.tipoContratacao = tipoContratacao;
	}	
	
	
}