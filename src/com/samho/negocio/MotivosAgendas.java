/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.samho.negocio;

import com.samho.dao.DadosDAO;
import com.samho.dao.ObjetoDAO;
import com.samho.dao.MotivosAgendasDAO;
import java.util.Objects;

/**
 * Classe de Objeto.
 *
 * @author Saibel, Sergio Luis
 * @since  date 07/05/2017
 *
 * @version  revision 001.201700507 date 07/05/2017 author Saibel, Sergio Luís reason
 * Instanciar um objeto com métodos e atributos necessários.
 */
public final class MotivosAgendas extends Objeto implements InterfaceObj {

    // Declaração de atributos
    private long idMotivoAgenda;
    private String descricao;
    private String observacoes;
    private boolean ativo;

    private MotivosAgendasDAO motivoAgendaDAO;

    // Construtor padrão
    public MotivosAgendas() {
        idMotivoAgenda = -1;
        descricao = "";
        observacoes = "";
        ativo = true;

        motivoAgendaDAO = new MotivosAgendasDAO();

        adicionarCampos();
    }

    // Construtor
    public MotivosAgendas(MotivosAgendas sintomaApresentado) {
        idMotivoAgenda = sintomaApresentado.getIdMotivoAgenda();
        descricao = sintomaApresentado.getDescricao();
        observacoes = sintomaApresentado.getObservacoes();
        ativo = sintomaApresentado.isAtivo();

        motivoAgendaDAO = sintomaApresentado.getMotivoAgendaDAO();

        adicionarCampos();
    }

    // Construtor
    public MotivosAgendas(long idMotivoAgenda, String descricao, 
            String observacoes, boolean ativo) {
        this.idMotivoAgenda = idMotivoAgenda;
        this.descricao = descricao;
        this.observacoes = observacoes;
        this.ativo = ativo;

        this.motivoAgendaDAO = new MotivosAgendasDAO();

        adicionarCampos();
    }

    @Override
    public boolean equals(Object object) {
        boolean retorno = false;

        if (object instanceof MotivosAgendas) {
            MotivosAgendas other = (MotivosAgendas) object;
            if (idMotivoAgenda == other.getIdMotivoAgenda()) {
                retorno = true;
            }
        }

        return retorno;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + (int) (this.idMotivoAgenda ^ (this.idMotivoAgenda >>> 32));
        hash = 97 * hash + Objects.hashCode(this.descricao);
        hash = 97 * hash + Objects.hashCode(this.observacoes);
        hash = 97 * hash + (this.ativo ? 1 : 0);
        hash = 97 * hash + Objects.hashCode(this.motivoAgendaDAO);
        return hash;
    }

    @Override
    public ObjetoDAO getObjetoDAO() {
        return motivoAgendaDAO;
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
        return new DadosDAO("id_motivo_agenda", "Cód. Motivo",
                String.valueOf(idMotivoAgenda), DadosDAO.TIPO_LONG, 
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
     * @return the idMotivoAgenda
     */
    public long getIdMotivoAgenda() {
        return idMotivoAgenda;
    }

    /**
     * @param idMotivoAgenda the idMotivoAgenda to set
     */
    public void setIdMotivoAgenda(long idMotivoAgenda) {
        this.idMotivoAgenda = idMotivoAgenda;
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
     * @return the motivoAgendaDAO
     */
    public MotivosAgendasDAO getMotivoAgendaDAO() {
        return motivoAgendaDAO;
    }

    /**
     * @param motivoAgendaDAO the motivoAgendaDAO to set
     */
    public void setMotivoAgendaDAO(MotivosAgendasDAO motivoAgendaDAO) {
        this.motivoAgendaDAO = motivoAgendaDAO;
    }
}
