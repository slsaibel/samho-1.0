/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.samho.dao;

/**
 * Classe para manipulação de Objetos Tipados.
 *
 * @author Sergio
 * @since  date 19/04/2017
 *
 * @version  revision 001.20170419 date 19/04/2017 author Saibel, Sergio Luís reason
 * Incluido atributo para condição na clausula WHERE e tipos de condição.
 * 
 * revision 001.20170331 date 31/03/2017 author Saibel, Sergio Luís reason
 * permitir as operações com banco de dados. Esta classe serve para que o objeto
 * possa executar operaçoes de banco aparte.
 * Este tipo de classe é responsável por manter objetos que correspondem a 
 * instruções de banco de dados. Estas instruções servem para guardar 
 * informações utilizadas pelo ObjetoDAO para manipilação de objetos no banco.
 * 
 */
public class DadosDAO {

    // Declaração de atributos
    private String campo;
    private String descricao;
    private String valor;
    private int tipo;
    private int condicao;
    private int condicaoAtributo;
    
    // Constantes para tipo de dado
    public static final int TIPO_INTEGER = 0;
    public static final int TIPO_STRING = 1;
    public static final int TIPO_DATE = 2;
    public static final int TIPO_DOUBLE = 3;
    public static final int TIPO_BOOLEAN = 4;
    public static final int TIPO_LONG = 5;
    
    // Constantes para condição
    public static final int IS_IGUAL = 0;
    public static final int IS_DIFERENTE = 1;
    public static final int IS_MAIOR = 2;
    public static final int IS_MENOR = 3;
    public static final int IS_MAIOR_IGUAL = 4;
    public static final int IS_MENOR_IGUAL = 5;

    // Constantes para tipo de dado
    public static final int IS_CHAVE = 0;
    public static final int NAO_CHAVE = 1;
    public static final int COMPLETO = 2;
    public static final int PARCIAL = 3;

    // Construtor
    public DadosDAO(DadosDAO dadoDAO) {
        campo = dadoDAO.campo;
        descricao = dadoDAO.descricao;
        tipo = dadoDAO.tipo;
        valor = dadoDAO.valor;
        condicao = dadoDAO.condicao;
        condicaoAtributo = dadoDAO.getCondicaoAtributo();
    }

    // Construtor
    public DadosDAO(String campo, String descricao, String valor, int tipo,
            int condicao, int condicaoAtributo) {
        this.campo = campo;
        this.descricao = descricao;
        this.tipo = tipo;
        this.valor = valor;
        this.condicao = condicao;
        this.condicaoAtributo = condicaoAtributo;
    }

    /**
     * @return the campo
     */
    public String getCampo() {
        return campo;
    }

    /**
     * @param campo the campo to set
     */
    public void setCampo(String campo) {
        this.campo = campo;
    }

    /**
     * @return the descricao
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * @param descricao the descricao to set
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    /**
     * @return the valor
     */
    public String getValor() {
        return valor;
    }

    /**
     * @param valor the valor to set
     */
    public void setValor(String valor) {
        this.valor = valor;
    }

    /**
     * @return the tipo
     */
    public int getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(int tipo) {
        this.tipo = tipo;
    }
    
        /**
     * @return the condicao
     */
    public int getCondicao() {
        return condicao;
    }

    /**
     * @param condicao the condicao to set
     */
    public void setCondicao(int condicao) {
        this.condicao = condicao;
    }

    /**
     * @return the condicaoAtributo
     */
    public int getCondicaoAtributo() {
        return condicaoAtributo;
    }

    /**
     * @param condicaoAtributo the condicaoAtributo to set
     */
    public void setCondicaoAtributo(int condicaoAtributo) {
        this.condicaoAtributo = condicaoAtributo;
    }
}
