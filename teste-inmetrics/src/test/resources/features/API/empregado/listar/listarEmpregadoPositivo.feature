Feature: Listagem de empregados cadastrados na plataforma

@CenariosPositivos
@ListarEmpregado1
Scenario: Listar todos os empregados cadastrados
	Given Como usuário cadastrado com permissão de leitura de empregados
	When ao enviar o GET request para a API
	Then verifico que o responseCode recebido pelo GET request é '200'
	And verifico que o response contem uma lista de empregados

@CenariosPositivos
@ListarEmpregado
Scenario: Validar se todos os os dados dos empregados estão sendo retornados
	Given Como usuário cadastrado com permissão de leitura de empregados
	When ao enviar o GET request para a API	
	And verifico se response mostra todos os dados esperados de cada empregados