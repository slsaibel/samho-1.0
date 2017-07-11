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
 * Classe de Interface para Objetos.
 *
 * @author Sergio
 * @since  date 07/04/2017
 *
 * @version  revision 002.20170407 date 07/04/2017 author Saibel, Sergio Luís reason
 * Adicionada a opção para tratamento de permissões de usuário.
 * 
 * revision 001.20170330 date 30/03/2017 author Saibel, Sergio Luís reason
 * Garantir o mínimo de metodos necessários para manipulação de objetos.
 */
public interface InterfaceObj {

    public abstract ObjetoDAO getObjetoDAO();

    public abstract void adicionarCampos();
    
//    public abstract int getIdObjeto();

    public void adicionarCampos(ArrayList<DadosDAO> lstDadosDAO);

    public void removerCampos();

    public void adicionarWhere(DadosDAO dadoDAO);

    public void adicionarWhere(ArrayList<DadosDAO> lstDadosDAO);

    public void removerWhere();

    public ArrayList<DadosDAO> getCamposChave();

    public ArrayList<DadosDAO> getCampos();

    public abstract DadosDAO getDadosCodigo();

    public abstract DadosDAO getDadosDescricao();
}
