/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.samho.util;

import com.samho.negocio.InterfaceObj;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sergio
 */
public class FabricaJSP {

    private static File diretorio;
    private static OutputStreamWriter arquivo;
    private static PrintWriter gravarArquivo;
    private static InterfaceObj objeto;
    private static String nomeClasse;
    private static String descricaoClasse;
    private static String encoding;

    private static void inicializar() {
        diretorio = new File("c:/_jsp");
        diretorio.mkdirs();

        nomeClasse = FabricaJSP.objeto.getClass().getSimpleName();
        
        try {
            arquivo = new OutputStreamWriter(new FileOutputStream(
                    diretorio + "/" + nomeClasse + ".jsp"), encoding);
        } catch (FileNotFoundException | UnsupportedEncodingException ex) {
            Logger.getLogger(FabricaJSP.class.getName()).log(Level.SEVERE, null, ex);
        }

        gravarArquivo = new PrintWriter(arquivo);
    }

    public static void criarJSP(InterfaceObj objeto, String descricaoClasse, 
            String encoding) {
        FabricaJSP.objeto = objeto;
        FabricaJSP.descricaoClasse = descricaoClasse;
        FabricaJSP.encoding = encoding;

        inicializar();

        montarPaginaJSP();

        try {
            arquivo.close();
        } catch (IOException ex) {
            Logger.getLogger(FabricaJSP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void montarPaginaJSP() {
        montarInfoClasse();
        montarImport();

        gravarArquivo.printf("<html xmlns=\"http://www.w3.org/1999/xhtml\">%n");

        montarHead("SAMHO - Sistema de Agendamento Médico Hospitalar");
        montarBody();

        gravarArquivo.printf("</html>%n");
    }

    private static void montarInfoClasse() {
        gravarArquivo.printf("<%%-- %n");
        gravarArquivo.printf("    Document   : view_\"" + nomeClasse.toLowerCase() + "\"%n");
        gravarArquivo.printf("    Created on : \"" + Formatacao.getDataAtual(Formatacao.COMPLETA) + "\"%n");
        gravarArquivo.printf("    Author     : \"" + System.getProperty("user.name") + "\"%n");
        gravarArquivo.printf("--%%>%n");
    }

    private static void montarImport() {
        gravarArquivo.printf("<%%@page import=\"" + objeto.getClass().getName() + "\"%%>%n");
        gravarArquivo.printf("<%%@page contentType=\"text/html\" pageEncoding=\"" + encoding + "\"%%>%n");
        gravarArquivo.printf("<?xml version=\"1.0\" pageEncoding=\"" + encoding + "\"?>%n");
        gravarArquivo.printf("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">%n");
    }

    private static void montarBody() {
        gravarArquivo.printf("    <body>%n");
        gravarArquivo.printf("        <div id=\"main\">%n");

        montarHeader();
        montarSiteContent();
        montarScroll();
        montarFooter();

        gravarArquivo.printf("        </div>%n");
        gravarArquivo.printf("    </body>%n");
    }

    private static void montarHead(String title) {
        gravarArquivo.printf("    <head>%n");
        gravarArquivo.printf("        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=" + encoding + "\"/>%n");
        gravarArquivo.printf("        <title>" + title + "</title>%n");
        gravarArquivo.printf("%n");
        gravarArquivo.printf("        <!-- Estilos da página -->%n");
        gravarArquivo.printf("        <link rel=\"stylesheet\" type=\"text/css\" href=\"<%%=request.getContextPath()%%>/css/style.css\" />%n");
        gravarArquivo.printf("        <!-- Fonte de icones -->%n");
        gravarArquivo.printf("        <link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css\">%n");
        gravarArquivo.printf("%n");
        gravarArquivo.printf("            <script>%n");
        gravarArquivo.printf("                // Função javascript para incluir um registro%n");
        gravarArquivo.printf("                function incluirRegistro() {%n");
        gravarArquivo.printf("                    var acao = \"incluir\";%n");
        gravarArquivo.printf("                    window.location.href = \"<%%=request.getContextPath()%%>/" + nomeClasse.toLowerCase() + "?acao=\" + acao;%n");
        gravarArquivo.printf("                }%n");
        gravarArquivo.printf("%n");
        gravarArquivo.printf("                // Função javascript para chamar edição de registro%n");
        gravarArquivo.printf("                function alterarRegistro(codigo) {%n");
        gravarArquivo.printf("                    var acao = \"alterar\";%n");
        gravarArquivo.printf("                    window.location.href = \"<%%=request.getContextPath()%%>/" + nomeClasse.toLowerCase() + "?codigo=\" + codigo + \"&acao=\" + acao;%n");
        gravarArquivo.printf("                }%n");
        gravarArquivo.printf("%n");
        gravarArquivo.printf("                // Função javascript para chamar Srvlet de exclusão de registro%n");
        gravarArquivo.printf("                function excluirRegistro(codigo) {%n");
        gravarArquivo.printf("                    if (confirm(\'Confirma exclusão do objeto do tipo \"" + descricaoClasse + "\" \' + codigo + \'?\')) {%n");
        gravarArquivo.printf("                        var acao = \"excluir\";%n");
        gravarArquivo.printf("                        window.location.href = \"<%%=request.getContextPath()%%>/" + nomeClasse.toLowerCase() + "?codigo=\" + codigo + \"&acao=\" + acao;%n");
        gravarArquivo.printf("                    } else {%n");
        gravarArquivo.printf("                        alert(\'Exclusão cancelada.\');%n");
        gravarArquivo.printf("                    }%n");
        gravarArquivo.printf("                }%n");
        gravarArquivo.printf("            </script> %n");
        gravarArquivo.printf("    </head>%n");
    }

    private static void montarHeader() {
        gravarArquivo.printf("            <header>%n");
        gravarArquivo.printf("                <div id=\"logo\">%n");
        gravarArquivo.printf("                    <div id=\"logo_text\">%n");
        gravarArquivo.printf("                        <!-- class=\"logo_colour\", allows you to change the colour of the text -->%n");
        gravarArquivo.printf("                        <h1><a href=\"<%%=request.getContextPath()%%>/index.html\"><span class=\"logo_colour\">SAMHO</span></a></h1>%n");
        gravarArquivo.printf("                        <h2>Sistema de Agendamento Médico Hospitalar</h2>%n");
        gravarArquivo.printf("                    </div>%n");
        gravarArquivo.printf("                </div>%n");
        gravarArquivo.printf("                <nav>%n");
        gravarArquivo.printf("                    <div id=\"menu_container\">%n");
        gravarArquivo.printf("                        <ul class=\"sf-menu\" id=\"nav\">%n");
        gravarArquivo.printf("                            <li><a href=\"<%%=request.getContextPath()%%>/index.html\">Inicio</a></li>%n");
        gravarArquivo.printf("                            <li><a href=\"#\">Ajuda</a></li>%n");
        gravarArquivo.printf("                        </ul>%n");
        gravarArquivo.printf("                    </div>%n");
        gravarArquivo.printf("                </nav>%n");
        gravarArquivo.printf("            </header>%n");
    }

    private static void montarSiteContent() {
        gravarArquivo.printf("            <div id=\"site_content\">%n");
        gravarArquivo.printf("                <!--%n");
        gravarArquivo.printf("                <div id=\"sidebar_container\">%n");
        gravarArquivo.printf("                        <div class=\"sidebar\">%n");
        gravarArquivo.printf("                        </div>%n");
        gravarArquivo.printf("                </div>%n");
        gravarArquivo.printf("                out.print(\"<h1>Alteração OK</h1>Registro alterado com Sucesso.%n");
        gravarArquivo.printf("                -->%n");
        gravarArquivo.printf("                <div>%n");
        gravarArquivo.printf("                    <!-- Formulário de cadcastro -->%n");
        gravarArquivo.printf("                    <h2>Consulta de \"" + descricaoClasse + "\"</h2>%n");
        gravarArquivo.printf("                    <form action=\"#\" method=\"post\">%n");
        gravarArquivo.printf("                        <div class=\"form_settings\">%n");
        gravarArquivo.printf("                            <%%%n");
        gravarArquivo.printf("                                Objeto objeto = new " + nomeClasse + "();%n");
        gravarArquivo.printf("                                objeto.getObjetoDAO().setCamposTabelaFormatados();%n");
        gravarArquivo.printf("                                Object[] cabecalho = objeto.getObjetoDAO().getCabecalhoTabela();%n");
        gravarArquivo.printf("                                Object[][] dados = objeto.getObjetoDAO().getDadosTabela();%n");
        gravarArquivo.printf("                            %%>%n");
        gravarArquivo.printf("                            <table>%n");
        gravarArquivo.printf("                                <thead>%n");
        gravarArquivo.printf("                                    <tr>%n");
        gravarArquivo.printf("                                        <th><%%= cabecalho[0].toString()%%></th>%n");
        gravarArquivo.printf("                                            <%%%n");
        gravarArquivo.printf("                                                for (int i = 1; i < cabecalho.length; i++) {%n");
        gravarArquivo.printf("                                                    String nomeCabecalho = cabecalho[i].toString();%n");
        gravarArquivo.printf("                                                    if ((nomeCabecalho.indexOf(\'.\') > 0)%n");
        gravarArquivo.printf("                                                            && (nomeCabecalho.substring(0, nomeCabecalho.indexOf(\'.\')).equals(\"Cód\"))) {%n");
        gravarArquivo.printf("                                            %%>%n");
        gravarArquivo.printf("                                        <th width=\"0\"></th>%n");
        gravarArquivo.printf("                                            <%%%n");
        gravarArquivo.printf("                                            } else {%n");
        gravarArquivo.printf("                                            %%>%n");
        gravarArquivo.printf("                                        <th><%%= nomeCabecalho%%></th>%n");
        gravarArquivo.printf("                                            <%%%n");
        gravarArquivo.printf("                                                    }%n");
        gravarArquivo.printf("                                                }%n");
        gravarArquivo.printf("                                            %%>%n");
        gravarArquivo.printf("                                        <th></th>%n");
        gravarArquivo.printf("                                        <th><input type=\"button\" class=\"button_top\" value=\"Incluir\" name=\"incluir\" onclick=\"incluirRegistro();\"/></th>%n");
        gravarArquivo.printf("                                    </tr>%n");
        gravarArquivo.printf("                                </thead>%n");
        gravarArquivo.printf("                                <tbody>%n");
        gravarArquivo.printf("                                    <!-- Cria nova linha na tabela -->%n");
        gravarArquivo.printf("                                    <%%                                        int col = 0;%n");
        gravarArquivo.printf("                                        int lin = 0;%n");
        gravarArquivo.printf("                                        for (lin = 0; lin < dados.length; lin++) {%n");
        gravarArquivo.printf("                                    %%>%n");
        gravarArquivo.printf("                                    <tr>%n");
        gravarArquivo.printf("                                        <%%%n");
        gravarArquivo.printf("                                            for (col = 0; col < dados[lin].length; col++) {%n");
        gravarArquivo.printf("                                        %%>%n");
        gravarArquivo.printf("                                        <td><%%= dados[lin][col] == null ? \"-\" : dados[lin][col].toString()%%></td>%n");
        gravarArquivo.printf("                                        <%%%n");
        gravarArquivo.printf("                                            }%n");
        gravarArquivo.printf("                                        %%>%n");
        gravarArquivo.printf("                                        <td class=\"button\"> <input type=\"button\" class=\"button_top\" value=\"Alterar\" name=\"alterar\" onclick=\"alterarRegistro(<%%= Integer.parseUnsignedInt(dados[lin][0].toString())%%>);\"/> </td>%n");
        gravarArquivo.printf("                                        <td class=\"button\"> <input type=\"button\" class=\"button_top\" value=\"Excluir\" name=\"excluir\" onclick=\"excluirRegistro(<%%= Integer.parseUnsignedInt(dados[lin][0].toString())%%>);\"/> </td>%n");
        gravarArquivo.printf("                                    </tr>%n");
        gravarArquivo.printf("                                    <%%%n");
        gravarArquivo.printf("                                        }%n");
        gravarArquivo.printf("                                    %%>%n");
        gravarArquivo.printf("%n");
        gravarArquivo.printf("                                </tbody>%n");
        gravarArquivo.printf("                            </table>%n");
        gravarArquivo.printf("                        </div>%n");
        gravarArquivo.printf("                    </form>%n");
        gravarArquivo.printf("                </div>%n");
        gravarArquivo.printf("            </div>%n");
    }

    private static void montarScroll() {
        gravarArquivo.printf("            <div id=\"scroll\">%n");
        gravarArquivo.printf("                <a title=\"Mover para cima\" class=\"top\" href=\"#\"><img src=\"<%%=request.getContextPath()%%>/images/up.png\" alt=\"top\" /></a>%n");
        gravarArquivo.printf("            </div>%n");
    }

    private static void montarFooter() {
        gravarArquivo.printf("            <footer>%n");
        gravarArquivo.printf("                <p>%n");
        gravarArquivo.printf("                    <a href=\"<%%=request.getContextPath()%%>/index.html\">Inicio</a> | %n");
        gravarArquivo.printf("                    <a href=\"#\">Ajuda</a>%n");
        gravarArquivo.printf("                </p>%n");
        gravarArquivo.printf("                <p>Copyright &copy; Saibel, Sergio Luis | 2017</p>%n");
        gravarArquivo.printf("            </footer>%n");
    }

}
