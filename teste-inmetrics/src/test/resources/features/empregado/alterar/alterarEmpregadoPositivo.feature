Feature: Alteração de empregados cadastrados na plataforma

@CenariosPositivos
@AlterarEmpregado
Scenario Outline: Alterar empregado cadastrado
	Given Como usuário cadastrado com permissão de Alterar cadastro de empregados
	And usando o id de um empregado que possa ser alterado
	And enviando o valor '<valor>' para o campo '<campo>'
	When ao enviar o PUT request para a API 
	Then verifico que o responseCode recebido pelo PUT request é '201'
	And verifico que o campo '<campo>' atualizado para '<valor>'
	
	Examples:
	|campo|valor|
	|nome| Fake name 4|
	|cargo| Fake cargo 4|