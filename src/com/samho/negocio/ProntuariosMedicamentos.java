/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.samho.negocio;

import com.samho.dao.DadosDAO;
import com.samho.dao.ObjetoDAO;
import com.samho.dao.ProntuariosMedicamentosDAO;
import java.util.Objects;


/**
 * Classe de Objeto.
 *
 * @author Saibel, Sergio Luis
 * @since  date 02/05/2017
 *
 * @version  revision 001.20170502 date 02/05/2017 author Saibel, Sergio Luís reason
 * Instanciar um objeto com métodos e atributos necessários.
 */
public final class ProntuariosMedicamentos extends Objeto 
        implements InterfaceObj {

    // Declaração de atributos
    private long idProntuarioMedicamento;
    private long codProntuario;
    private long codMedicamento;
    private int diasComTratamento;
    private int quantidade;
    private String parecerProfissional;

    private ProntuariosMedicamentosDAO prontuarioMedicamentoDAO;

    // Construtor padrão
    public ProntuariosMedicamentos() {
        idProntuarioMedicamento = -1;
        codProntuario = -1;
        codMedicamento = -1;
        diasComTratamento = 0;
        quantidade = 0;
        parecerProfissional = "";

        prontuarioMedicamentoDAO = new ProntuariosMedicamentosDAO();

        adicionarCampos();
    }

    // Construtor
    public ProntuariosMedicamentos(
            ProntuariosMedicamentos prontuarioMedicamento) {
        idProntuarioMedicamento = 
                prontuarioMedicamento.getIdProntuarioMedicamento();
        codProntuario = prontuarioMedicamento.getCodProntuario();
        codMedicamento = prontuarioMedicamento.getCodMedicamento();
        diasComTratamento = prontuarioMedicamento.getDiasComTratamento();
        quantidade = prontuarioMedicamento.getQuantidade();
        parecerProfissional = prontuarioMedicamento.getParecerProfissional();

        prontuarioMedicamentoDAO = prontuarioMedicamento.getProntuarioMedicamentoDAO();

        adicionarCampos();
    }

    // Construtor
    public ProntuariosMedicamentos(long idProntuarioMedicamento, 
            long codProntuario, long codMedicamento, int diasComTratamento,
            int quantidade, String parecerProfissional) {
        this.idProntuarioMedicamento = idProntuarioMedicamento;
        this.codProntuario = codProntuario;
        this.codMedicamento = codMedicamento;
        this.diasComTratamento = diasComTratamento;
        this.quantidade = quantidade;
        this.parecerProfissional = parecerProfissional;

        this.prontuarioMedicamentoDAO = new ProntuariosMedicamentosDAO();

        adicionarCampos();
    }

    @Override
    public boolean equals(Object object) {
        boolean retorno = false;

        if (object instanceof ProntuariosMedicamentos) {
            ProntuariosMedicamentos other = (ProntuariosMedicamentos) object;
            if (idProntuarioMedicamento == other.getIdProntuarioMedicamento()) {
                retorno = true;
            }
        }

        return retorno;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + (int) (this.idProntuarioMedicamento ^ (this.idProntuarioMedicamento >>> 32));
        hash = 83 * hash + (int) (this.codProntuario ^ (this.codProntuario >>> 32));
        hash = 83 * hash + (int) (this.codMedicamento ^ (this.codMedicamento >>> 32));
        hash = 83 * hash + this.diasComTratamento;
        hash = 83 * hash + this.quantidade;
        hash = 83 * hash + Objects.hashCode(this.parecerProfissional);
        hash = 83 * hash + Objects.hashCode(this.prontuarioMedicamentoDAO);
        return hash;
    }

    @Override
    public ObjetoDAO getObjetoDAO() {
        return prontuarioMedicamentoDAO;
    }

    @Override
    public void adicionarCampos() {
        removerCampos();

        getObjetoDAO().addCampo(getDadosCodigo());
        getObjetoDAO().addCampo(new DadosDAO("cod_prontuario", "Cód. Prontuario",
                String.valueOf(codProntuario), DadosDAO.TIPO_LONG, 
                DadosDAO.IS_IGUAL, DadosDAO.NAO_CHAVE));
        getObjetoDAO().addCampo(new DadosDAO("cod_medicamento", "Cód. Medicamento",
                String.valueOf(codMedicamento), DadosDAO.TIPO_LONG, 
                DadosDAO.IS_IGUAL, DadosDAO.NAO_CHAVE));
        getObjetoDAO().addCampo(new DadosDAO("dias_com_tratamento", 
                "Dias com Tratamento", String.valueOf(diasComTratamento), 
                DadosDAO.TIPO_INTEGER, DadosDAO.IS_IGUAL, DadosDAO.NAO_CHAVE));
        getObjetoDAO().addCampo(new DadosDAO("quantidade", "Quantidade", 
                String.valueOf(quantidade), DadosDAO.TIPO_INTEGER, 
                DadosDAO.IS_IGUAL, DadosDAO.NAO_CHAVE));
        getObjetoDAO().addCampo(new DadosDAO("parecer_profissional", 
                "Parecer Profissional", String.valueOf(parecerProfissional), 
                DadosDAO.TIPO_STRING, DadosDAO.IS_IGUAL, DadosDAO.NAO_CHAVE));
    }

    /**
     * @return the dadosCodigo
     */
    @Override
    public DadosDAO getDadosCodigo() {
        return new DadosDAO("id_prontuario_medicamento", "Cód. Pront. Med.", 
                String.valueOf(idProntuarioMedicamento),DadosDAO.TIPO_LONG, 
                DadosDAO.IS_IGUAL, DadosDAO.IS_CHAVE);
    }

    /**
     * @return the dadosDescricao
     */
    @Override
    public DadosDAO getDadosDescricao() {
                return new DadosDAO(
                prontuarioMedicamentoDAO.getSQLSubstiturCampoDescricao(
                        idProntuarioMedicamento), "Doença - Medicamento", "", 
                DadosDAO.TIPO_STRING, DadosDAO.IS_IGUAL, DadosDAO.NAO_CHAVE);
    }

    /**
     * @return the idProntuarioMedicamento
     */
    public long getIdProntuarioMedicamento() {
        return idProntuarioMedicamento;
    }

    /**
     * @param idProntuarioMedicamento the idProntuarioMedicamento to set
     */
    public void setIdProntuarioMedicamento(long idProntuarioMedicamento) {
        this.idProntuarioMedicamento = idProntuarioMedicamento;
    }

    /**
     * @return the codProntuario
     */
    public long getCodProntuario() {
        return codProntuario;
    }

    /**
     * @param codProntuario the codProntuario to set
     */
    public void setCodProntuario(long codProntuario) {
        this.codProntuario = codProntuario;
    }

    /**
     * @return the codMedicamento
     */
    public long getCodMedicamento() {
        return codMedicamento;
    }

    /**
     * @param codMedicamento the codMedicamento to set
     */
    public void setCodMedicamento(long codMedicamento) {
        this.codMedicamento = codMedicamento;
    }

    /**
     * @return the diasComTratamento
     */
    public int getDiasComTratamento() {
        return diasComTratamento;
    }

    /**
     * @param diasComTratamento the diasComTratamento to set
     */
    public void setDiasComTratamento(int diasComTratamento) {
        this.diasComTratamento = diasComTratamento;
    }

    /**
     * @return the prontuarioMedicamentoDAO
     */
    public ProntuariosMedicamentosDAO getProntuarioMedicamentoDAO() {
        return prontuarioMedicamentoDAO;
    }

    /**
     * @param prontuarioMedicamentoDAO the prontuarioMedicamentoDAO to set
     */
    public void setProntuarioMedicamentoDAO(ProntuariosMedicamentosDAO prontuarioMedicamentoDAO) {
        this.prontuarioMedicamentoDAO = prontuarioMedicamentoDAO;
    }

    /**
     * @return the quantidade
     */
    public int getQuantidade() {
        return quantidade;
    }

    /**
     * @param quantidade the quantidade to set
     */
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    /**
     * @return the parecerProfissional
     */
    public String getParecerProfissional() {
        return parecerProfissional;
    }

    /**
     * @param parecerProfissional the parecerProfissional to set
     */
    public void setParecerProfissional(String parecerProfissional) {
        this.parecerProfissional = parecerProfissional;
    }
}
