/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.samho.dao;

import com.samho.util.Registrador;
import javax.swing.JComboBox;

/**
 * Classe de Interface para objetosDAO.
 *
 * @author Saibel, Sergio Luis
 * @since  date 31/03/2017
 *
 * @version  revision 001.20170331 date 31/03/2017 author Saibel, Sergio Luís reason
 * garantir que as classes DAO mantenham um padrão de construção.
 */
public interface InterfaceDAO {

    public Registrador popularCodigoDescricao(String campoID,
            String descricao);

    public String incluir();

    public String alterar();

    public String excluir(String id);
    
    public String getCampoID();

    public String popularListaJComboBox(JComboBox combo, String campoCodigo,
            String campoDescricao);

}
