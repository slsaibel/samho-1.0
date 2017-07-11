/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.samho.negocio;

import com.samho.dao.DadosDAO;
import com.samho.dao.ObjetoDAO;
import com.samho.dao.ProntuariosDAO;
import com.samho.util.Formatacao;
import java.util.Calendar;
import java.util.Objects;

/**
 * Classe de Objeto.
 *
 * @author Saibel, Sergio Luis
 * @since  date 22/04/2017
 *
 * @version  revision 001.20170422 date 22/04/2017 author Saibel, Sergio Luís reason
 * Instanciar um objeto com métodos e atributos necessários.
 */
public final class Prontuarios extends Objeto implements InterfaceObj {

    // Declaração de atributos
    private long idProntuario;
    private long codCliente;
    private String motivoConsulta;
    private String diagnostico;
    private Calendar dataConsulta;
    private String parecerProfissional;
    private String observacoes;
    private boolean deveRetornar;

    private ProntuariosDAO prontuarioDAO;

    // Construtor padrão
    public Prontuarios() {
        idProntuario = -1;
        codCliente = -1;
        motivoConsulta = "";
        diagnostico = "";
        dataConsulta = Calendar.getInstance();
        parecerProfissional = "";
        observacoes = "";
        deveRetornar = false;

        prontuarioDAO = new ProntuariosDAO();

        adicionarCampos();
    }

    // Construtor
    public Prontuarios(Prontuarios prontuario) {
        idProntuario = prontuario.getIdProntuario();
        codCliente = prontuario.getCodCliente();
        motivoConsulta = prontuario.getMotivoConsulta();
        diagnostico = prontuario.getDiagnostico();
        dataConsulta = prontuario.getDataConsulta();
        parecerProfissional = prontuario.getParecerProfissional();
        observacoes = prontuario.getObservacoes();
        deveRetornar = prontuario.isDeveRetornar();

        prontuarioDAO = prontuario.getProntuarioDAO();

        adicionarCampos();
    }

    // Construtor
    public Prontuarios(long idProntuario, long codCliente, String motivoConsulta,
            String diagnostico, Calendar dataConsulta, String parecerProfissional,
            String observacoes, boolean deveRetornar) {
        this.idProntuario = idProntuario;
        this.codCliente = codCliente;
        this.motivoConsulta = motivoConsulta;
        this.diagnostico = diagnostico;
        this.dataConsulta = dataConsulta;
        this.parecerProfissional = parecerProfissional;
        this.observacoes = observacoes;
        this.deveRetornar = deveRetornar;

        this.prontuarioDAO = new ProntuariosDAO();

        adicionarCampos();
    }

    @Override
    public boolean equals(Object object) {
        boolean retorno = false;

        if (object instanceof Prontuarios) {
            Prontuarios other = (Prontuarios) object;
            if (idProntuario == other.getIdProntuario()) {
                retorno = true;
            }
        }

        return retorno;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + (int) (this.idProntuario ^ (this.idProntuario >>> 32));
        hash = 59 * hash + (int) (this.codCliente ^ (this.codCliente >>> 32));
        hash = 59 * hash + Objects.hashCode(this.motivoConsulta);
        hash = 59 * hash + Objects.hashCode(this.diagnostico);
        hash = 59 * hash + Objects.hashCode(this.dataConsulta);
        hash = 59 * hash + Objects.hashCode(this.parecerProfissional);
        hash = 59 * hash + Objects.hashCode(this.observacoes);
        hash = 59 * hash + (this.deveRetornar ? 1 : 0);
        hash = 59 * hash + Objects.hashCode(this.prontuarioDAO);
        return hash;
    }

    @Override
    public ObjetoDAO getObjetoDAO() {
        return prontuarioDAO;
    }

    @Override
    public void adicionarCampos() {
        removerCampos();

        getObjetoDAO().addCampo(getDadosCodigo());
        getObjetoDAO().addCampo(new DadosDAO("cod_cliente", "Cód. Cliente",
                String.valueOf(codCliente), DadosDAO.TIPO_LONG, DadosDAO.IS_IGUAL, 
                DadosDAO.NAO_CHAVE));
        getObjetoDAO().addCampo(new DadosDAO("motivo_consulta", "Motivo Consulta",
                String.valueOf(motivoConsulta), DadosDAO.TIPO_STRING, DadosDAO.IS_IGUAL, 
                DadosDAO.NAO_CHAVE));
        getObjetoDAO().addCampo(new DadosDAO("diagnostico", "Diagnóstico", 
                diagnostico, DadosDAO.TIPO_STRING, DadosDAO.IS_IGUAL,  
                DadosDAO.NAO_CHAVE));
        getObjetoDAO().addCampo(new DadosDAO("data_consulta", "Data Consulta",
                Formatacao.ajustaDataAMD(dataConsulta), DadosDAO.TIPO_DATE,
                DadosDAO.IS_IGUAL, DadosDAO.NAO_CHAVE));
        getObjetoDAO().addCampo(new DadosDAO("parecer_profissional", "Parecer Prof.", 
                parecerProfissional, DadosDAO.TIPO_STRING, DadosDAO.IS_IGUAL,
                DadosDAO.NAO_CHAVE));
        getObjetoDAO().addCampo(new DadosDAO("observacoes", "Observações", 
                observacoes, DadosDAO.TIPO_STRING, DadosDAO.IS_IGUAL,
                DadosDAO.NAO_CHAVE));
        getObjetoDAO().addCampo(new DadosDAO("deve_retornar", "Retornar",
                String.valueOf(deveRetornar), DadosDAO.TIPO_BOOLEAN,
                DadosDAO.IS_IGUAL, DadosDAO.NAO_CHAVE));
    }

    /**
     * @return the dadosCodigo
     */
    @Override
    public DadosDAO getDadosCodigo() {
        return new DadosDAO("id_prontuario", "Cód. Prontuário", 
                String.valueOf(idProntuario),DadosDAO.TIPO_LONG, 
                DadosDAO.IS_IGUAL, DadosDAO.IS_CHAVE);
    }

    /**
     * @return the dadosDescricao
     */
    @Override
    public DadosDAO getDadosDescricao() {
        return new DadosDAO("id_prontuario||' - '||data_consulta", "Prontuário", 
                "", DadosDAO.IS_IGUAL, DadosDAO.TIPO_STRING, DadosDAO.NAO_CHAVE);
    }

    /**
     * @return the idProntuario
     */
    public long getIdProntuario() {
        return idProntuario;
    }

    /**
     * @param idProntuario the idProntuario to set
     */
    public void setIdProntuario(long idProntuario) {
        this.idProntuario = idProntuario;
    }

    /**
     * @return the codCliente
     */
    public long getCodCliente() {
        return codCliente;
    }

    /**
     * @param codCliente the codCliente to set
     */
    public void setCodCliente(long codCliente) {
        this.codCliente = codCliente;
    }

    /**
     * @return the motivoConsulta
     */
    public String getMotivoConsulta() {
        return motivoConsulta;
    }

    /**
     * @param motivoConsulta the motivoConsulta to set
     */
    public void setMotivoConsulta(String motivoConsulta) {
        this.motivoConsulta = motivoConsulta;
    }

    /**
     * @return the diagnostico
     */
    public String getDiagnostico() {
        return diagnostico;
    }

    /**
     * @param diagnostico the diagnostico to set
     */
    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    /**
     * @return the dataConsulta
     */
    public Calendar getDataConsulta() {
        return dataConsulta;
    }

    /**
     * @param dataConsulta the dataConsulta to set
     */
    public void setDataConsulta(Calendar dataConsulta) {
        this.dataConsulta = dataConsulta;
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
     * @return the deveRetornar
     */
    public boolean isDeveRetornar() {
        return deveRetornar;
    }

    /**
     * @param deveRetornar the deveRetornar to set
     */
    public void setDeveRetornar(boolean deveRetornar) {
        this.deveRetornar = deveRetornar;
    }

    /**
     * @return the prontuarioDAO
     */
    public ProntuariosDAO getProntuarioDAO() {
        return prontuarioDAO;
    }

    /**
     * @param prontuarioDAO the prontuarioDAO to set
     */
    public void setProntuarioDAO(ProntuariosDAO prontuarioDAO) {
        this.prontuarioDAO = prontuarioDAO;
    }
}
