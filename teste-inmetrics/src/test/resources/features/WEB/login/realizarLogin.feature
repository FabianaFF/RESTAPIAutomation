Feature: Login de usuario ja cadastrado

@RealizarLoginFE
Scenario: Logar usuário pela interface web 
	Given Como usuário web cadastrado
	When ao enviar todos os dados para acessar sistema
	Then quero ter meu login realizado com sucesso 