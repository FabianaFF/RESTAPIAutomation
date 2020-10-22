Feature: Alteração Empregado cadastrado pelo site

@AlterarEmpregadoFE
Scenario: Alterar empregado cadastrado pela interface web 
	Given Como usuário web cadastrado e logado com permissão de alteração
	When ao selecionar funcionário para alterar na listagem
	And enviar as alterações de dados do empregado
	Then verifico que a alteração de funcionário foi realizada com sucesso