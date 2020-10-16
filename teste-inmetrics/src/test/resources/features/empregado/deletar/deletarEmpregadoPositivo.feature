Feature: Deleção de empregados cadastrados na plataforma

@CenariosPositivos
@DeletarEmpregado
Scenario: Deletar empregado cadastrado
	Given Como usuário cadastrado com permissão de Deleção de empregados
	And usando o id de um empregado ja cadastrado
	When ao enviar o DELETE request para a API 
	Then verifico que o responseCode recebido pelo DELETE request é '200' ou '204'