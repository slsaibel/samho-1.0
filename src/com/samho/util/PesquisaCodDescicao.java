/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.samho.util;


/**
 * Classe de PesquisaCodDescicao.
 *
 * @author Sergio
 * @since  date 15/04/2014
 *
 * @version  revision 001.20140415 date 15/04/2014 author Saibel, Sergio Luís reason
 * Instanciar um objeto do tipo Itens de combos com métodos e atributos
 * necessários.
 */
public class PesquisaCodDescicao {

    private String codigo;
    private String descricao;

    @Override
    public String toString() {
        return descricao;
    }

    /**
     * @return the codigo
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * @param codigo the codigo to set
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
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
}
