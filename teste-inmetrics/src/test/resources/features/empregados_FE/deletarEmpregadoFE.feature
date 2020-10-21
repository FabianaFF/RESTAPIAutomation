Feature: Deletar Empregado cadastrado pelo site

@DeletarEmpregadoFE
Scenario: Deletar empregado pela interface web 
	Given Como usuário web cadastrado e logado com permissão de deleção
	When ao selecionar funcionário para deletar na listagem
	And clicar no botão para deletar
	Then verifico que a deleção de funcionário foi realizada com sucesso