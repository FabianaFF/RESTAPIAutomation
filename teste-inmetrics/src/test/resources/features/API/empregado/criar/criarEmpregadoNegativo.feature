Feature: Cadastro de novos empregados na plataforma validando permissão de acesso e formato dos dados

@CenariosNegativos
@CadastrarEmpregado
Scenario: Validação de permissão de acesso 
	Given Como usuário não cadastrado
	And forneço todos os dados para cadastrar novo empregado
	When envio o request para a API
	Then valido o responseCode '401'

@CenariosNegativos
@CadastrarEmpregado
Scenario: Validação formato do payload 
	Given Como usuário cadastrado com permissão de criaçao de empregados
	And forneço dados com formato errado para cadastrar novo empregado
	When envio o request para a API
	Then valido o responseCode '400'