/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.samho.negocio;

import com.samho.dao.FuncoesDAO;
import com.samho.dao.DadosDAO;
import com.samho.dao.ObjetoDAO;
import java.util.Objects;

/**
 * Classe de Objeto.
 *
 * @author Saibel, Sergio Luis
 * @since  date 31/03/2017
 *
 * @version  revision 001.20170331 date 31/03/2017 author Saibel, Sergio Luís reason
 * Instanciar um objeto com métodos e atributos necessários.
 */
public final class Funcoes extends Objeto implements InterfaceObj {

    // Declaração de atributos
    private long idFuncao;
    private String descricao;
    private boolean ativo;

    private FuncoesDAO funcaoDAO;

    // Construtor padrão
    public Funcoes() {
        idFuncao = -1;
        descricao = "";
        ativo = true;

        funcaoDAO = new FuncoesDAO();

        adicionarCampos();
    }

    // Construtor
    public Funcoes(Funcoes funcao) {
        idFuncao = funcao.getIdFuncao();
        descricao = funcao.getDescricao();
        ativo = funcao.isAtivo();

        funcaoDAO = funcao.getFuncaoDAO();

        adicionarCampos();
    }

    // Construtor
    public Funcoes(int idFuncao, String descricao, boolean ativo) {
        this.idFuncao = idFuncao;
        this.descricao = descricao;
        this.ativo = ativo;

        this.funcaoDAO = new FuncoesDAO();

        adicionarCampos();
    }

    @Override
    public boolean equals(Object object) {
        boolean retorno = false;

        if (object instanceof Funcoes) {
            Funcoes other = (Funcoes) object;
            if (idFuncao == other.getIdFuncao()) {
                retorno = true;
            }
        }

        return retorno;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + (int) (this.idFuncao ^ (this.idFuncao >>> 32));
        hash = 79 * hash + Objects.hashCode(this.descricao);
        hash = 79 * hash + (this.ativo ? 1 : 0);
        hash = 79 * hash + Objects.hashCode(this.funcaoDAO);
        return hash;
    }

    @Override
    public ObjetoDAO getObjetoDAO() {
        return funcaoDAO;
    }

    @Override
    public void adicionarCampos() {
        removerCampos();

        getObjetoDAO().addCampo(getDadosCodigo());
        getObjetoDAO().addCampo(getDadosDescricao());
        getObjetoDAO().addCampo(new DadosDAO("ativo", "Ativo",
                String.valueOf(ativo), DadosDAO.TIPO_BOOLEAN, DadosDAO.IS_IGUAL, 
                DadosDAO.NAO_CHAVE));
        
        // Adicionando where de restrição ao administrador id = 0
        getObjetoDAO().addWhere(new DadosDAO(getObjetoDAO().getCampoID(), "", 
                "0", DadosDAO.TIPO_LONG, DadosDAO.IS_DIFERENTE, DadosDAO.IS_CHAVE));
    }

    /**
     * @return the dadosCodigo
     */
    @Override
    public DadosDAO getDadosCodigo() {
        return new DadosDAO("id_funcao", "Cód. Função", String.valueOf(idFuncao),
                DadosDAO.TIPO_LONG, DadosDAO.IS_IGUAL, DadosDAO.IS_CHAVE);
    }

    /**
     * @return the dadosDescricao
     */
    @Override
    public DadosDAO getDadosDescricao() {
        return new DadosDAO("descricao", "Descrição", getDescricao(),
                DadosDAO.TIPO_STRING, DadosDAO.IS_IGUAL, DadosDAO.NAO_CHAVE);
    }

    /**
     * @return the idFuncao
     */
    public long getIdFuncao() {
        return idFuncao;
    }

    /**
     * @param idFuncao the idFuncao to set
     */
    public void setIdFuncao(long idFuncao) {
        this.idFuncao = idFuncao;
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
     * @return the funcaoDAO
     */
    public FuncoesDAO getFuncaoDAO() {
        return funcaoDAO;
    }

    /**
     * @param funcaoDAO the funcaoDAO to set
     */
    public void setFuncaoDAO(FuncoesDAO funcaoDAO) {
        this.funcaoDAO = funcaoDAO;
    }

    /**
     * @return the ativo
     */
    public boolean isAtivo() {
        return ativo;
    }

    /**
     * @param ativo the ativo to set
     */
    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

}
