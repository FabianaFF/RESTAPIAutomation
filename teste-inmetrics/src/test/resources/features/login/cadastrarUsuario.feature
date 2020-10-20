Feature: Cadastro de novo usuario

@CadastrarLogin
Scenario: Cadastrar usuário pela interface web 
	Given Como usuário web não cadastrado
	When ao enviar todos os dados para cadastrar novo acesso
	Then quero ter meu cadastro realizado com sucesso 