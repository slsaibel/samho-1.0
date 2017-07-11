/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.samho.negocio;

import com.samho.apresentacao.JFrmLogin;
import com.samho.dao.ConexoesDB;
import com.samho.util.Util;

/**
 * Classe de Principal da aplicação.
 *
 * @author Saibel, Sergio Luis
 * @since  date 31/03/2017
 *
 * @version  revision 001.20170331 date 31/03/2017 author Saibel, Sergio Luís reason
 * Clase principal da aplicação,
 */
public class Principal {   
    public static Usuarios usuario;
    
    public static final int SITUACOES_PESSOAS = 1;
    public static final int PESSOAS_FISICAS = 2;
    public static final int PAISES = 3;
    public static final int ESTADOS = 4;
    public static final int CIDADES = 5;
    public static final int TIPOS_ENDERECOS = 6;
    public static final int BAIRROS = 7;
    public static final int ENDERECOS = 8;
    public static final int FUNCOES = 9;
    public static final int TIPOS_TELEFONES = 10;
    public static final int TELEFONES = 11;
    public static final int SITUACOES_FUNCIONARIOS = 12;
    public static final int ACOES = 13;
    public static final int USUARIOS = 14;
    public static final int ACOES_USUARIOS = 15;
    public static final int FUNCIONARIOS = 16;
    public static final int ESPECIALIZACOES = 17;
    public static final int PESSOAS_ESPECIALIZACOES = 18;
    public static final int SITUACOES_CLIENTES = 19;
    public static final int PESSOAS = 20;
    public static final int CLIENTES = 21;
    public static final int TIPOS_PLANOS = 22;
    public static final int CLIENTES_TIPOS_PLANOS = 23;
    public static final int PLANOS_SAUDE = 24;
    public static final int PROFISSIONAIS_PLANOS_SAUDE = 25;
    public static final int PROFISSIONAIS = 26;
    public static final int PESSOAS_JURIDICAS = 27;
    public static final int PRONTUARIOS_SINTOMAS_APRESENTADOS = 28;
    public static final int PRONTUARIOS = 29;
    public static final int PRONTUARIOS_MEDICAMENTOS = 30;
    public static final int SINTOMAS_APRESENTADOS = 31;
    public static final int GLOSSARIO_DOENCAS_SINTOMAS_APRESENTADOS = 32;
    public static final int GLOSSARIO_DOENCAS = 33;
    public static final int MEDICAMENTOS = 34;
    public static final int GLOSSARIO_DOENCA_MEDICAMENTOS = 35;
    public static final int PROFISSIONAIS_TURNOS = 36;
    public static final int MOTIVOS_AGENDAS = 37;
    public static final int AGENDAS = 38;
    public static final int TIPOS_EXAMES = 39;
    public static final int EXAMES = 40;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {  
        java.awt.EventQueue.invokeLater(() -> {
            if (ConexoesDB.getInstance().getConnection() != null) {
                Principal.usuario = new Usuarios();
                
                JFrmLogin jFrmLogin = new JFrmLogin();
                
                 Util.centralizar(jFrmLogin);
                 
                jFrmLogin.setVisible(true);
            }
        });
    }
}
