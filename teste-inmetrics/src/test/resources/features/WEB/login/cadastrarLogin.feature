Feature: Cadastro de novo usuario pelo site

@CadastrarLoginFE
Scenario: Cadastrar novo Login pela interface web 
	Given Como usuário web não cadastrado
	When ao enviar todos os dados para cadastrar novo acesso
	Then quero ter meu cadastro realizado com sucesso 