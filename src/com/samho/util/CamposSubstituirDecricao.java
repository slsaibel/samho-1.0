/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.samho.util;

/**
 * Classe de Objeto.
 *
 * @author Saibel, Sergio Luis
 * @since  date 31/03/2017
 *
 * @version  revision 001.20170331 date 31/03/2017 author Saibel, Sergio Luís reason
 * Instanciar um objeto com métodos e atributos necessários.
 */
public class CamposSubstituirDecricao {

    // Declaração de atributos
    private String nome_campo_id;
    private String nome_campo_cod;
    private String nome_campo_descricao;
    private String nome_tabela;

    public CamposSubstituirDecricao(String nome_campo_id, String nome_campo_cod,
            String nome_campo_descricao, String nome_tabela) {
        this.nome_campo_id = nome_campo_id;
        this.nome_campo_cod = nome_campo_cod;
        this.nome_campo_descricao = nome_campo_descricao;
        this.nome_tabela = nome_tabela;
    }

    /**
     * @return the nome_campo_id
     */
    public String getNome_campo_id() {
        return nome_campo_id;
    }

    /**
     * @param nome_campo_id the nome_campo_id to set
     */
    public void setNome_campo_id(String nome_campo_id) {
        this.nome_campo_id = nome_campo_id;
    }

    /**
     * @return the nome_campo_cod
     */
    public String getNome_campo_cod() {
        return nome_campo_cod;
    }

    /**
     * @param nome_campo_cod the nome_campo_cod to set
     */
    public void setNome_campo_cod(String nome_campo_cod) {
        this.nome_campo_cod = nome_campo_cod;
    }

    /**
     * @return the nome_campo_descricao
     */
    public String getNome_campo_descricao() {
        return nome_campo_descricao;
    }

    /**
     * @param nome_campo_descricao the nome_campo_descricao to set
     */
    public void setNome_campo_descricao(String nome_campo_descricao) {
        this.nome_campo_descricao = nome_campo_descricao;
    }

    /**
     * @return the nome_tabela
     */
    public String getNome_tabela() {
        return nome_tabela;
    }

    /**
     * @param nome_tabela the nome_tabela to set
     */
    public void setNome_tabela(String nome_tabela) {
        this.nome_tabela = nome_tabela;
    }
}
