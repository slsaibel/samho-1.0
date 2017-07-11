/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.samho.util;

import javax.swing.JComboBox;

/**
 * Classe DAO para Objetos Tipados.
 *
 * @author Saibel, Sergio Luis
 * @since  date 31/03/2017
 *
 * @version  revision 001.20170331 date 31/03/2017 author Saibel, Sergio Luís reason
 * permitir padronisar campos de pesquisa.
 * 
 */
public class Registrador {
    
    // Declaração de atributos
    private long codigo;
    private String descricao;

    // Construtor
    public Registrador(){
        codigo = -1;
        descricao = "";
    }
    
    // Construtor
    public Registrador(long codigo, String descricao){
        this.codigo = codigo;
        this.descricao = descricao;
    }

    // Define os itens do combo
    public void definirItemCombo(JComboBox combo, Registrador item) {
        for (int i = 0; i < combo.getItemCount(); i++) {
            if (((Registrador) combo.getItemAt(i)).getCodigo() == 0
                    ? (item.getCodigo()) == 0
                    : ((Registrador) combo.getItemAt(i)).getCodigo() == item.getCodigo()) {
                combo.setSelectedIndex(i);
                return;
            } else {
            }
        }
    }
    
    /**
     * @return the codigo
     */
    public long getCodigo() {
        return codigo;
    }

    /**
     * @param codigo the codigo to set
     */
    public void setCodigo(long codigo) {
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
