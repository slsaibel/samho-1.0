/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.samho.negocio;

import com.samho.dao.DadosDAO;
import com.samho.dao.ObjetoDAO;
import com.samho.dao.SituacoesPessoasDAO;
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
public final class SituacoesPessoas extends Objeto implements InterfaceObj {

    // Declaração de atributos
    private long idSituacao;
    private String descricao;
    private String observacoes;
    private boolean ativo;

    private SituacoesPessoasDAO situacaoDAO;

    // Construtor padrão
    public SituacoesPessoas() {
        idSituacao = -1;
        descricao = "";
        observacoes = "";
        ativo = true;

        situacaoDAO = new SituacoesPessoasDAO();

        adicionarCampos();
    }

    // Construtor
    public SituacoesPessoas(SituacoesPessoas situacao) {
        idSituacao = situacao.getIdSituacao();
        descricao = situacao.getDescricao();
        observacoes = situacao.getObservacoes();
        ativo = situacao.isAtivo();

        situacaoDAO = situacao.getSituacaoDAO();

        adicionarCampos();
    }

    // Construtor
    public SituacoesPessoas(int idSituacao, String descricao, 
            String observacoes, boolean ativo) {
        this.idSituacao = idSituacao;
        this.descricao = descricao;
        this.observacoes = observacoes;
        this.ativo = ativo;

        this.situacaoDAO = new SituacoesPessoasDAO();

        adicionarCampos();
    }

    @Override
    public boolean equals(Object object) {
        boolean retorno = false;

        if (object instanceof SituacoesPessoas) {
            SituacoesPessoas other = (SituacoesPessoas) object;
            if (idSituacao == other.getIdSituacao()) {
                retorno = true;
            }
        }

        return retorno;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + (int) (this.idSituacao ^ (this.idSituacao >>> 32));
        hash = 59 * hash + Objects.hashCode(this.descricao);
        hash = 59 * hash + Objects.hashCode(this.observacoes);
        hash = 59 * hash + (this.ativo ? 1 : 0);
        hash = 59 * hash + Objects.hashCode(this.situacaoDAO);
        return hash;
    }

    @Override
    public ObjetoDAO getObjetoDAO() {
        return situacaoDAO;
    }

    @Override
    public void adicionarCampos() {
        removerCampos();

        getObjetoDAO().addCampo(getDadosCodigo());
        getObjetoDAO().addCampo(getDadosDescricao());
        getObjetoDAO().addCampo(new DadosDAO("observacoes", "Observação",
                observacoes, DadosDAO.TIPO_STRING, DadosDAO.IS_IGUAL, 
                DadosDAO.NAO_CHAVE));
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
        return new DadosDAO("id_situacao", "Cód. Situação",
                String.valueOf(idSituacao), DadosDAO.TIPO_LONG, DadosDAO.IS_IGUAL, 
                DadosDAO.IS_CHAVE);
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
     * @return the idSituacao
     */
    public long getIdSituacao() {
        return idSituacao;
    }

    /**
     * @param idSituacao the idSituacao to set
     */
    public void setIdSituacao(long idSituacao) {
        this.idSituacao = idSituacao;
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
     * @return the observacoes
     */
    public String getObservacoes() {
        return observacoes;
    }

    /**
     * @param observacoes the observacoes to set
     */
    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
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
    
    /**
     * @return the situacaoDAO
     */
    public SituacoesPessoasDAO getSituacaoDAO() {
        return situacaoDAO;
    }

    /**
     * @param situacaoDAO the situacaoDAO to set
     */
    public void setSituacaoDAO(SituacoesPessoasDAO situacaoDAO) {
        this.situacaoDAO = situacaoDAO;
    }
}
