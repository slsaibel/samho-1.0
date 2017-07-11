/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.samho.util;

import com.samho.negocio.InterfaceObj;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author Sergio
 */
public class FabricaJSP_ {

    public FabricaJSP_(InterfaceObj objeto) throws IOException {
        File diretorio = new File("c:/teste_jsp");
        diretorio.mkdir();

        try (FileWriter arq = new FileWriter(diretorio + "/" + objeto.getObjetoDAO().getTabela().toLowerCase() + ".jsp")) {
            PrintWriter gravarArq = new PrintWriter(arq);

            gravarArq.printf("<%%-- %n");
            gravarArq.printf("    Document   : view_bairros%n");
            gravarArq.printf("    Created on : 11/06/2017, 22:54:49%n");
            gravarArq.printf("    Author     : Sergio%n");
            gravarArq.printf("--%%>%n");
            gravarArq.printf("%n");
            gravarArq.printf("<%%@page import=\"com.samho.necocio.Bairros\"%%>%n");
            gravarArq.printf("<%%@page contentType=\"text/html\" pageEncoding=\"ISO-8859-1\"%%>%n");
            gravarArq.printf("<?xml version=\"1.0\" pageEncoding=\"ISO-8859-1\"?>%n");
            gravarArq.printf("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">%n");
            gravarArq.printf("<html xmlns=\"http://www.w3.org/1999/xhtml\">%n");
            gravarArq.printf("    <head>%n");
            gravarArq.printf("        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"/>%n");
            gravarArq.printf("        <title>SAMHO - Sistema de Agendamento Médico Hospitalar</title>%n");
            gravarArq.printf("%n");
            gravarArq.printf("        <!-- Estilos da página -->%n");
            gravarArq.printf("        <link rel=\"stylesheet\" type=\"text/css\" href=\"<%%=request.getContextPath()%%>/css/style.css\" />%n");
            gravarArq.printf("        <!-- Fonte de icones -->%n");
            gravarArq.printf("        <link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css\">%n");
            gravarArq.printf("%n");
            gravarArq.printf("            <script>%n");
            gravarArq.printf("                // Função javascript para incluir um registro%n");
            gravarArq.printf("                function incluirRegistro() {%n");
            gravarArq.printf("                    var acao = \"incluir\";%n");
            gravarArq.printf("                    window.location.href = \"<%%=request.getContextPath()%%>/bairro?acao=\" + acao;%n");
            gravarArq.printf("                }%n");
            gravarArq.printf("%n");
            gravarArq.printf("                // Função javascript para chamar edição de registro%n");
            gravarArq.printf("                function alterarRegistro(codigo) {%n");
            gravarArq.printf("                    var acao = \"alterar\";%n");
            gravarArq.printf("                    window.location.href = \"<%%=request.getContextPath()%%>/bairro?codigo=\" + codigo + \"&acao=\" + acao;%n");
            gravarArq.printf("                }%n");
            gravarArq.printf("%n");
            gravarArq.printf("                // Função javascript para chamar Srvlet de exclusão de registro%n");
            gravarArq.printf("                function excluirRegistro(codigo) {%n");
            gravarArq.printf("                    if (confirm(\'Confirma exclusão do bairro \' + codigo + \'?\')) {%n");
            gravarArq.printf("                        var acao = \"excluir\";%n");
            gravarArq.printf("                        window.location.href = \"<%%=request.getContextPath()%%>/bairro?codigo=\" + codigo + \"&acao=\" + acao;%n");
            gravarArq.printf("                    } else {%n");
            gravarArq.printf("                        alert(\'Exclusão cancelada.\');%n");
            gravarArq.printf("                    }%n");
            gravarArq.printf("                }%n");
            gravarArq.printf("            </script> %n");
            gravarArq.printf("    </head>%n");
            gravarArq.printf("    <body>%n");
            gravarArq.printf("        <div id=\"main\">%n");
            gravarArq.printf("            <header>%n");
            gravarArq.printf("                <div id=\"logo\">%n");
            gravarArq.printf("                    <div id=\"logo_text\">%n");
            gravarArq.printf("                        <!-- class=\"logo_colour\", allows you to change the colour of the text -->%n");
            gravarArq.printf("                        <h1><a href=\"<%%=request.getContextPath()%%>/index.html\"><span class=\"logo_colour\">SAMHO</span></a></h1>%n");
            gravarArq.printf("                        <h2>Sistema de Agendamento Médico Hospitalar</h2>%n");
            gravarArq.printf("                    </div>%n");
            gravarArq.printf("                </div>%n");
            gravarArq.printf("                <nav>%n");
            gravarArq.printf("                    <div id=\"menu_container\">%n");
            gravarArq.printf("                        <ul class=\"sf-menu\" id=\"nav\">%n");
            gravarArq.printf("                            <li><a href=\"<%%=request.getContextPath()%%>/index.html\">Inicio</a></li>%n");
            gravarArq.printf("                            <li><a href=\"#\">Ajuda</a></li>%n");
            gravarArq.printf("                        </ul>%n");
            gravarArq.printf("                    </div>%n");
            gravarArq.printf("                </nav>%n");
            gravarArq.printf("            </header>%n");
            gravarArq.printf("%n");
            gravarArq.printf("            <div id=\"site_content\">%n");
            gravarArq.printf("                <!--%n");
            gravarArq.printf("                <div id=\"sidebar_container\">%n");
            gravarArq.printf("                        <div class=\"sidebar\">%n");
            gravarArq.printf("                        </div>%n");
            gravarArq.printf("                </div>%n");
            gravarArq.printf("                out.print(\"<h1>Alteração OK</h1>Registro alterado com Sucesso.%n");
            gravarArq.printf("                -->%n");
            gravarArq.printf("                <div>%n");
            gravarArq.printf("                    <!-- Formulário de cadcastro -->%n");
            gravarArq.printf("                    <h2>Consulta de Enderecos</h2>%n");
            gravarArq.printf("                    <form action=\"#\" method=\"post\">%n");
            gravarArq.printf("                        <div class=\"form_settings\">%n");
            gravarArq.printf("                            <%%%n");
            gravarArq.printf("                                Bairros objeto = new Bairros();%n");
            gravarArq.printf("                                objeto.getObjetoDAO().setCamposTabelaFormatados();%n");
            gravarArq.printf("                                Object[] cabecalho = objeto.getObjetoDAO().getCabecalhoTabela();%n");
            gravarArq.printf("                                Object[][] dados = objeto.getObjetoDAO().getDadosTabela();%n");
            gravarArq.printf("                            %%>%n");
            gravarArq.printf("                            <table>%n");
            gravarArq.printf("                                <thead>%n");
            gravarArq.printf("                                    <tr>%n");
            gravarArq.printf("                                        <th><%%= cabecalho[0].toString()%%></th>%n");
            gravarArq.printf("                                            <%%%n");
            gravarArq.printf("                                                for (int i = 1; i < cabecalho.length; i++) {%n");
            gravarArq.printf("                                                    String nomeCabecalho = cabecalho[i].toString();%n");
            gravarArq.printf("                                                    if ((nomeCabecalho.indexOf(\'.\') > 0)%n");
            gravarArq.printf("                                                            && (nomeCabecalho.substring(0, nomeCabecalho.indexOf(\'.\')).equals(\"Cód\"))) {%n");
            gravarArq.printf("                                            %%>%n");
            gravarArq.printf("                                        <th width=\"0\"></th>%n");
            gravarArq.printf("                                            <%%%n");
            gravarArq.printf("                                            } else {%n");
            gravarArq.printf("                                            %%>%n");
            gravarArq.printf("                                        <th><%%= nomeCabecalho%%></th>%n");
            gravarArq.printf("                                            <%%%n");
            gravarArq.printf("                                                    }%n");
            gravarArq.printf("                                                }%n");
            gravarArq.printf("                                            %%>%n");
            gravarArq.printf("                                        <th></th>%n");
            gravarArq.printf("                                        <th><input type=\"button\" class=\"button_top\" value=\"Incluir\" name=\"incluir\" onclick=\"incluirRegistro();\"/></th>%n");
            gravarArq.printf("                                    </tr>%n");
            gravarArq.printf("                                </thead>%n");
            gravarArq.printf("                                <tbody>%n");
            gravarArq.printf("                                    <!-- Cria nova linha na tabela -->%n");
            gravarArq.printf("                                    <%%                                        int col = 0;%n");
            gravarArq.printf("                                        int lin = 0;%n");
            gravarArq.printf("                                        for (lin = 0; lin < dados.length; lin++) {%n");
            gravarArq.printf("                                    %%>%n");
            gravarArq.printf("                                    <tr>%n");
            gravarArq.printf("                                        <%%%n");
            gravarArq.printf("                                            for (col = 0; col < dados[lin].length; col++) {%n");
            gravarArq.printf("                                        %%>%n");
            gravarArq.printf("                                        <td><%%= dados[lin][col] == null ? \"-\" : dados[lin][col].toString()%%></td>%n");
            gravarArq.printf("                                        <%%%n");
            gravarArq.printf("                                            }%n");
            gravarArq.printf("                                        %%>%n");
            gravarArq.printf("                                        <td class=\"button\"> <input type=\"button\" class=\"button_top\" value=\"Alterar\" name=\"alterar\" onclick=\"alterarRegistro(<%%= Integer.parseUnsignedInt(dados[lin][0].toString())%%>);\"/> </td>%n");
            gravarArq.printf("                                        <td class=\"button\"> <input type=\"button\" class=\"button_top\" value=\"Excluir\" name=\"excluir\" onclick=\"excluirRegistro(<%%= Integer.parseUnsignedInt(dados[lin][0].toString())%%>);\"/> </td>%n");
            gravarArq.printf("                                    </tr>%n");
            gravarArq.printf("                                    <%%%n");
            gravarArq.printf("                                        }%n");
            gravarArq.printf("                                    %%>%n");
            gravarArq.printf("%n");
            gravarArq.printf("                                </tbody>%n");
            gravarArq.printf("                            </table>%n");
            gravarArq.printf("                        </div>%n");
            gravarArq.printf("                    </form>%n");
            gravarArq.printf("                </div>%n");
            gravarArq.printf("            </div>%n");
            gravarArq.printf("            <div id=\"scroll\">%n");
            gravarArq.printf("                <a title=\"Mover para cima\" class=\"top\" href=\"#\"><img src=\"<%%=request.getContextPath()%%>/images/up.png\" alt=\"top\" /></a>%n");
            gravarArq.printf("            </div>%n");
            gravarArq.printf("            <footer>%n");
            gravarArq.printf("                <p>%n");
            gravarArq.printf("                    <a href=\"<%%=request.getContextPath()%%>/index.html\">Inicio</a> | %n");
            gravarArq.printf("                    <a href=\"#\">Ajuda</a>%n");
            gravarArq.printf("                </p>%n");
            gravarArq.printf("                <p>Copyright &copy; Saibel, Sergio Luis | 2017</p>%n");
            gravarArq.printf("            </footer>%n");
            gravarArq.printf("        </div>%n");
            gravarArq.printf("    </body>%n");
            gravarArq.printf("</html>%n");

            arq.close();
        }
    }

}
