## Teste Inmetrics - Descrever como executar o projeto

<b>Please, in order to run this project folow the steps bellow:</b>
<ol>
	<li>Install java (recomended version 11.0.8)</li>
	<li>Instal maven (recomended version 3.6.3)</li>
	<li>Install ChromeDriver (recomended version 86)</li>
	<li>Install Chrome (recomended version 86)</li>
	<li>Instal Java IDE (recomended Eclipse IDE for Enterprise Java Developers version: 2020-09 (4.17.0))</li>
	<li>Install pluggins: Cucumber Eclipse Plugin, Natural 0.9</li>
	<li>Import project as maven project</li>
	<li>Change required configurations as chromeDriverPath at config.yaml file</li>
	<li>Perform a maven build install to import all dependencies</li>
	<li>Perform a maven custom build run using one of these values as goals</li>
	<ul>clean verify -e -Dcucumber.options="--tags @DeletarEmpregado"</ul>
	<ul>clean verify -e -Dcucumber.options="--tags @ListarEmpregado"</ul>
	<ul>clean verify -e -Dcucumber.options="--tags @CadastrarEmpregado"</ul>
	<ul>clean verify -e -Dcucumber.options="--tags @AlterarEmpregado"</ul>
	<ul>clean verify -e -Dcucumber.options="--tags @CenariosPositivos"</ul>
	<ul>clean verify -e -Dcucumber.options="--tags @CenariosNegativos"</ul>
	<ul>clean verify -e -Dcucumber.options="--tags @AlterarEmpregadoFE"</ul>
	<ul>clean verify -e -Dcucumber.options="--tags @CadastrarEmpregadoFE"</ul>
	<ul>clean verify -e -Dcucumber.options="--tags @DeletarEmpregadoFE"</ul>
	<ul>clean verify -e -Dcucumber.options="--tags @RealizarLoginFE"</ul>
	<ul>clean verify -e -Dcucumber.options="--tags @CadastrarLoginFE"</ul>	
	<li>After execution finish verify report at target/cucumber-report-html/cucumber-html-reports/</li>	
</ol>
