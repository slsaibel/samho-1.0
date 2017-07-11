/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.samho.dao.rel;

import com.samho.dao.ObjetoDAO;

/**
 * Classe DAO para Objetos Tipados.
 *
 * @author Saibel, Sergio Luis
 * @since  date 24/04/2017
 *
 * revision 001.20170424 date 24/04/2017 author Saibel, Sergio Luís reason
 * permitir as operações com banco de dados para que um objeto possa ser
 * incluído, alterado, consultado ou excluído(inativado).
 *
 * A exclusão não é feita no banco de dados, para isso apenas o atributo
 * "situacao" deve ser setado.
 */
public class RelClientesDAO extends ObjetoDAO {

    public RelClientesDAO() {
        super("VIEW_CLIENTES");
    }
}
