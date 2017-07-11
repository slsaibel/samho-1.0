/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.samho.negocio;

import com.samho.dao.DadosDAO;
import com.samho.dao.ObjetoDAO;
import com.samho.dao.PlanosSaudeDAO;
import java.util.Objects;

/**
 * Classe de Objeto.
 *
 * @author Saibel, Sergio Luis
 * @since  date 22/04/2017
 *
 * @version  revision 001.201700422 date 22/04/2017 author Saibel, Sergio Luís reason
 * Instanciar um objeto com métodos e atributos necessários.
 */
public final class PlanosSaude extends Objeto implements InterfaceObj {

    // Declaração de atributos
    private long idPlanoSaude;
    private String descricao;
    private String observacoes;
    private boolean ativo;

    private PlanosSaudeDAO planoSaudeDAO;

    // Construtor padrão
    public PlanosSaude() {
        idPlanoSaude = -1;
        descricao = "";
        observacoes = "";
        ativo = true;

        planoSaudeDAO = new PlanosSaudeDAO();

        adicionarCampos();
    }

    // Construtor
    public PlanosSaude(PlanosSaude planoSaude) {
        idPlanoSaude = planoSaude.getIdPlanoSaude();
        descricao = planoSaude.getDescricao();
        observacoes = planoSaude.getObservacoes();
        ativo = planoSaude.isAtivo();

        planoSaudeDAO = planoSaude.getPlanoSaudeDAO();

        adicionarCampos();
    }

    // Construtor
    public PlanosSaude(long idPlanoSaude, String descricao, 
            String observacoes, boolean ativo) {
        this.idPlanoSaude = idPlanoSaude;
        this.descricao = descricao;
        this.observacoes = observacoes;
        this.ativo = ativo;

        this.planoSaudeDAO = new PlanosSaudeDAO();

        adicionarCampos();
    }

    @Override
    public boolean equals(Object object) {
        boolean retorno = false;

        if (object instanceof PlanosSaude) {
            PlanosSaude other = (PlanosSaude) object;
            if (idPlanoSaude == other.getIdPlanoSaude()) {
                retorno = true;
            }
        }

        return retorno;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + (int) (this.idPlanoSaude ^ (this.idPlanoSaude >>> 32));
        hash = 53 * hash + Objects.hashCode(this.descricao);
        hash = 53 * hash + Objects.hashCode(this.observacoes);
        hash = 53 * hash + (this.ativo ? 1 : 0);
        hash = 53 * hash + Objects.hashCode(this.planoSaudeDAO);
        return hash;
    }

    @Override
    public ObjetoDAO getObjetoDAO() {
        return planoSaudeDAO;
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
    }

    /**
     * @return the dadosCodigo
     */
    @Override
    public DadosDAO getDadosCodigo() {
        return new DadosDAO("id_plano_saude", "Cód. Plano",
                String.valueOf(idPlanoSaude), DadosDAO.TIPO_LONG, DadosDAO.IS_IGUAL, 
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
     * @return the idPlanoSaude
     */
    public long getIdPlanoSaude() {
        return idPlanoSaude;
    }

    /**
     * @param idPlanoSaude the idPlanoSaude to set
     */
    public void setIdPlanoSaude(long idPlanoSaude) {
        this.idPlanoSaude = idPlanoSaude;
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
     * @return the planoSaudeDAO
     */
    public PlanosSaudeDAO getPlanoSaudeDAO() {
        return planoSaudeDAO;
    }

    /**
     * @param planoSaudeDAO the planoSaudeDAO to set
     */
    public void setPlanoSaudeDAO(PlanosSaudeDAO planoSaudeDAO) {
        this.planoSaudeDAO = planoSaudeDAO;
    }
}
