Feature: Alteração Empregado cadastrado pelo site

@AlterarEmpregadoFE
Scenario Outline: Alterar empregado cadastrado pela interface web 
	Given Como usuário web cadastrado e logado com permissão de alteração
	When ao selecionar funcionário para alterar na listagem
	And enviar '<campo>' alterado para '<valor>'
	Then verifico que a alteração de funcionário foi realizada com sucesso
	
	Examples:
		|campo|valor|		
		|sexo|-|
		|tipoContratacao|clt|
