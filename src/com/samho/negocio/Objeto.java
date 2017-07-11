/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.samho.negocio;

import com.samho.dao.DadosDAO;
import com.samho.dao.ObjetoDAO;
import java.util.ArrayList;

/**
 * Classe de Objeto.
 *
 * @author Sergio
 * @since  date 30/03/2017
 *
 * @version  revision 001.20170330 date 30/03/2017 author Saibel, Sergio Luís reason
 * Instanciar um objeto com métodos e atributos necessários do tipo
 * interfaceado.
 */
public abstract class Objeto implements InterfaceObj {

    @Override
    public abstract ObjetoDAO getObjetoDAO();

    @Override
    public abstract void adicionarCampos();
    
    @Override
    public void adicionarCampos(ArrayList<DadosDAO> lstDadosDAO) {
        lstDadosDAO.forEach((dadoDAO) -> {
            getObjetoDAO().addCampo(dadoDAO);
        });
    }

    @Override
    public void removerCampos() {
        getObjetoDAO().getCamposTabela().clear();
    }

    @Override
    public ArrayList<DadosDAO> getCamposChave() {
        ArrayList<DadosDAO> retorno = new ArrayList<>();

        getObjetoDAO().getCamposTabela().stream().filter((dadoDAO) -> {
            return (dadoDAO.getCondicaoAtributo() == DadosDAO.IS_CHAVE);
        }).forEachOrdered((dadoDAO) -> {
            retorno.add(dadoDAO);
        });

        return retorno;
    }

    @Override
    public ArrayList<DadosDAO> getCampos() {
        ArrayList<DadosDAO> retorno = new ArrayList<>();

        getObjetoDAO().getCamposTabela().forEach((dadoDAO) -> {
            retorno.add(dadoDAO);
        });

        return retorno;
    }

    @Override
    public void adicionarWhere(DadosDAO dadoDAO) {
        getObjetoDAO().addWhere(dadoDAO);
    }

    @Override
    public void adicionarWhere(ArrayList<DadosDAO> lstDadosDAO) {
        lstDadosDAO.forEach((dadoDAO) -> {
            getObjetoDAO().addWhere(dadoDAO);
        });
    }

    @Override
    public void removerWhere() {
        getObjetoDAO().getCondicaoWhere().clear();
    }

    /**
     * @return the dadosCodigo
     */
    @Override
    public abstract DadosDAO getDadosCodigo();

    /**
     * @return the dadosDescricao
     */
    @Override
    public abstract DadosDAO getDadosDescricao();

}
