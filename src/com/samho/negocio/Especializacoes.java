/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.samho.negocio;

import com.samho.dao.DadosDAO;
import com.samho.dao.ObjetoDAO;
import com.samho.dao.EspecializacoesDAO;
import java.util.Objects;

/**
 * Classe de Objeto.
 *
 * @author Saibel, Sergio Luis
 * @since  date 20/04/2017
 *
 * @version  revision 001.20170420 date 20/04/2017 author Saibel, Sergio Luís reason
 * Instanciar um objeto com métodos e atributos necessários.
 */
public final class Especializacoes extends Objeto implements InterfaceObj {

    // Declaração de atributos
    private long idEspecializacao;
    private String descricao;
    private String observacoes;

    private EspecializacoesDAO especializacaoDAO;

    // Construtor padrão
    public Especializacoes() {
        idEspecializacao = -1;
        descricao = "";
        observacoes = "";

        especializacaoDAO = new EspecializacoesDAO();

        adicionarCampos();
    }

    // Construtor
    public Especializacoes(Especializacoes especializacao) {
        idEspecializacao = especializacao.getIdEspecializacao();
        descricao = especializacao.getDescricao();
        observacoes = especializacao.getObservacoes();

        especializacaoDAO = especializacao.getEspecializacaoDAO();

        adicionarCampos();
    }

    // Construtor
    public Especializacoes(int idSituacao, String descricao, 
            String observacoes, boolean ativo) {
        this.idEspecializacao = idSituacao;
        this.descricao = descricao;
        this.observacoes = observacoes;

        especializacaoDAO = new EspecializacoesDAO();

        adicionarCampos();
    }

    @Override
    public boolean equals(Object object) {
        boolean retorno = false;

        if (object instanceof Especializacoes) {
            Especializacoes other = (Especializacoes) object;
            if (idEspecializacao == other.getIdEspecializacao()) {
                retorno = true;
            }
        }

        return retorno;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 41 * hash + (int) (this.idEspecializacao ^ (this.idEspecializacao >>> 32));
        hash = 41 * hash + Objects.hashCode(this.descricao);
        hash = 41 * hash + Objects.hashCode(this.observacoes);
        hash = 41 * hash + Objects.hashCode(this.especializacaoDAO);
        return hash;
    }

    @Override
    public ObjetoDAO getObjetoDAO() {
        return especializacaoDAO;
    }

    @Override
    public void adicionarCampos() {
        removerCampos();

        getObjetoDAO().addCampo(getDadosCodigo());
        getObjetoDAO().addCampo(getDadosDescricao());
        getObjetoDAO().addCampo(new DadosDAO("observacoes", "Observação",
                observacoes, DadosDAO.TIPO_STRING, DadosDAO.IS_IGUAL, 
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
        return new DadosDAO("id_especializacao", "Cód. Especialização",
                String.valueOf(idEspecializacao), DadosDAO.TIPO_LONG, 
                DadosDAO.IS_IGUAL, DadosDAO.IS_CHAVE);
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
     * @return the idEspecializacao
     */
    public long getIdEspecializacao() {
        return idEspecializacao;
    }

    /**
     * @param idEspecializacao the idEspecializacao to set
     */
    public void setIdEspecializacao(long idEspecializacao) {
        this.idEspecializacao = idEspecializacao;
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
     * @return the especializacaoDAO
     */
    public EspecializacoesDAO getEspecializacaoDAO() {
        return especializacaoDAO;
    }

    /**
     * @param especializacaoDAO the especializacaoDAO to set
     */
    public void setEspecializacaoDAO(EspecializacoesDAO especializacaoDAO) {
        this.especializacaoDAO = especializacaoDAO;
    }
}
