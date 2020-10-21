Feature: Cadastrar novo Empregado pelo site

@CadastrarEmpregadoFE
Scenario: Cadastro de novos empregados pela interface web 
	Given Como usuário web cadastrado e logado com permissão de cadastro
	When ao selecionar opção Novo Funcionário
	And enviar todos os campos requeridos pelo cadastro
	Then verifico que a criação de novo funcionário foi realizada com sucesso