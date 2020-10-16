Feature: Cadastro de novos empregados na plataforma validando permissão de acesso e persistência dos dados obrigatórios

@CenariosPositivos
@CadastrarEmpregado
Scenario: Cadastrar um novo empregado provendo todos os dados
	Given Como usuário cadastrado com permissão de criaçao de empregados
	And forneço todos os dados para cadastrar novo empregado
	When envio o request para a API
	Then verifico que os dados foram persistidos corretamente

@CenariosPositivos
@CadastrarEmpregado
Scenario: Cadastrar um novo empregado receber o responseCode correto
	Given Como usuário cadastrado com permissão de criaçao de empregados
	And forneço todos os dados para cadastrar novo empregado
	When envio o request para a API
	Then valido o responseCode '201'