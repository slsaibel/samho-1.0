/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.samho.negocio;

import com.samho.dao.AcoesDAO;
import com.samho.dao.DadosDAO;
import com.samho.dao.ObjetoDAO;
import java.util.Objects;

/**
 * Classe de Objeto.
 *
 * @author Saibel, Sergio Luis
 * @since  date 05/04/2017
 *
 * @version  revision 001.20170405 date 05/04/2017 author Saibel, Sergio Luís reason
 * Instanciar um objeto com métodos e atributos necessários.
 */
public final class Acoes extends Objeto implements InterfaceObj {

    // Declaração de atributos
    private long idAcao;
    private String descricao;

    private AcoesDAO acaoDAO;

    // Construtor padrão
    public Acoes() {
        idAcao = -1;
        descricao = "";

        acaoDAO = new AcoesDAO();

        adicionarCampos();
    }

    // Construtor
    public Acoes(Acoes funcao) {
        idAcao = funcao.getIdAcao();
        descricao = funcao.getDescricao();
    
        acaoDAO = funcao.getAcaoDAO();

        adicionarCampos();
    }

    // Construtor
    public Acoes(int idAcao, String descricao) {
        this.idAcao = idAcao;
        this.descricao = descricao;

        this.acaoDAO = new AcoesDAO();

        adicionarCampos();
    }

    @Override
    public boolean equals(Object object) {
        boolean retorno = false;

        if (object instanceof Acoes) {
            Acoes other = (Acoes) object;
            if (idAcao == other.getIdAcao()) {
                retorno = true;
            }
        }

        return retorno;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + (int) (this.idAcao ^ (this.idAcao >>> 32));
        hash = 59 * hash + Objects.hashCode(this.descricao);
        hash = 59 * hash + Objects.hashCode(this.acaoDAO);
        return hash;
    }

    @Override
    public ObjetoDAO getObjetoDAO() {
        return acaoDAO;
    }

    @Override
    public void adicionarCampos() {
        removerCampos();

        getObjetoDAO().addCampo(getDadosCodigo());
        getObjetoDAO().addCampo(getDadosDescricao());
    }

    /**
     * @return the dadosCodigo
     */
    @Override
    public DadosDAO getDadosCodigo() {
        return new DadosDAO("id_acao", "Cód. Ação", String.valueOf(idAcao),
                DadosDAO.TIPO_LONG, DadosDAO.IS_IGUAL, DadosDAO.IS_CHAVE);
    }

    /**
     * @return the dadosDescricao
     */
    @Override
    public DadosDAO getDadosDescricao() {
        return new DadosDAO("descricao", "Descrição", descricao,
                DadosDAO.TIPO_STRING, DadosDAO.IS_IGUAL, DadosDAO.NAO_CHAVE);
    }

    /**
     * @return the idAcao
     */
    public long getIdAcao() {
        return idAcao;
    }

    /**
     * @param idAcao the idAcao to set
     */
    public void setIdAcao(long idAcao) {
        this.idAcao = idAcao;
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
     * @return the acaoDAO
     */
    public AcoesDAO getAcaoDAO() {
        return acaoDAO;
    }

    /**
     * @param acaoDAO the acaoDAO to set
     */
    public void setAcaoDAO(AcoesDAO acaoDAO) {
        this.acaoDAO = acaoDAO;
    }
}
