/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.samho.negocio;

import com.samho.dao.AgendasDAO;
import com.samho.dao.DadosDAO;
import com.samho.dao.ObjetoDAO;
import com.samho.util.Formatacao;
import java.util.Calendar;
import java.util.Objects;

/**
 * Classe de Objeto.
 *
 * @author Saibel, Sergio Luis
 * @since  date 07/05/2017
 *
 * @version  revision 001.20170507 date 07/05/2017 author Saibel, Sergio Luís reason
 * Instanciar um objeto com métodos e atributos necessários.
 */
public final class Agendas extends Objeto implements InterfaceObj {

    // Declaração de atributos
    private long idAgenda;
    private long codCliente;
    private long codFuncionario;
    private long codProfissional;
    private long codMotivoAgenda;
    private Calendar dataAgendamento;
    private double horario;
    private String observacoes;

    private AgendasDAO agendaDAO;

    // Construtor padrão
    public Agendas() {
        idAgenda = -1;
        codCliente = -1;
        codFuncionario = -1;
        codProfissional = -1;
        codMotivoAgenda = -1;
        dataAgendamento = Calendar.getInstance();
        horario = 0.0;
        observacoes = "";

        agendaDAO = new AgendasDAO();

        adicionarCampos();
    }

    // Construtor
    public Agendas(Agendas agenda) {
        idAgenda = agenda.getIdAgenda();
        codCliente = agenda.getCodCliente();
        codFuncionario = agenda.getCodFuncionario();
        codProfissional = agenda.getCodProfissional();
        codMotivoAgenda = agenda.getCodMotivoAgenda();
        dataAgendamento = agenda.getDataAgendamento();
        horario = agenda.getHorario();
        observacoes = agenda.getObservacoes();

        agendaDAO = agenda.getAgendaDAO();
        
        adicionarCampos();
    }

    // Construtor
    public Agendas(long idAgenda, long codCliente, long codFuncionario, 
            long codProfissional, long codMotivoAgenda, Calendar dataAgendamento,
            double horario, String observacoes) {
        this.idAgenda = idAgenda;
        this.codCliente = codCliente;
        this.codFuncionario = codFuncionario;
        this.codProfissional = codProfissional;
        this.codMotivoAgenda = codMotivoAgenda;
        this.dataAgendamento = dataAgendamento;
        this.horario = horario;
        this.observacoes = observacoes;

        agendaDAO = new AgendasDAO();

        adicionarCampos();
    }

    @Override
    public boolean equals(Object object) {
        boolean retorno = false;

        if (object instanceof Agendas) {
            Agendas other = (Agendas) object;
            if (idAgenda == other.getIdAgenda()) {
                retorno = true;
            }
        }

        return retorno;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + (int) (this.idAgenda ^ (this.idAgenda >>> 32));
        hash = 97 * hash + (int) (this.codCliente ^ (this.codCliente >>> 32));
        hash = 97 * hash + (int) (this.codFuncionario ^ (this.codFuncionario >>> 32));
        hash = 97 * hash + (int) (this.codProfissional ^ (this.codProfissional >>> 32));
        hash = 97 * hash + (int) (this.codMotivoAgenda ^ (this.codMotivoAgenda >>> 32));
        hash = 97 * hash + Objects.hashCode(this.dataAgendamento);
        hash = 97 * hash + (int) (Double.doubleToLongBits(this.horario) ^ (Double.doubleToLongBits(this.horario) >>> 32));
        hash = 97 * hash + Objects.hashCode(this.observacoes);
        hash = 97 * hash + Objects.hashCode(this.agendaDAO);
        return hash;
    }

    @Override
    public ObjetoDAO getObjetoDAO() {
        return agendaDAO;
    }

    @Override
    public void adicionarCampos() {
        removerCampos();

        getObjetoDAO().addCampo(getDadosCodigo());
        getObjetoDAO().addCampo(new DadosDAO("cod_cliente","Cód. Cliente", 
                String.valueOf(codCliente), DadosDAO.TIPO_LONG, DadosDAO.IS_IGUAL, 
                DadosDAO.NAO_CHAVE));
        getObjetoDAO().addCampo(new DadosDAO("cod_funcionario","Cód. Funcionário", 
                String.valueOf(codFuncionario), DadosDAO.TIPO_LONG, 
                DadosDAO.IS_IGUAL, DadosDAO.NAO_CHAVE));
        getObjetoDAO().addCampo(new DadosDAO("cod_profissional","Cód. Profissional", 
                String.valueOf(codProfissional), DadosDAO.TIPO_LONG, DadosDAO.IS_IGUAL, 
                DadosDAO.NAO_CHAVE));
        getObjetoDAO().addCampo(new DadosDAO("cod_motivo_agenda","Cód. Motivo Agenda", 
                String.valueOf(codMotivoAgenda), DadosDAO.TIPO_LONG, DadosDAO.IS_IGUAL, 
                DadosDAO.NAO_CHAVE));
        getObjetoDAO().addCampo(new DadosDAO("data_agendamento","Data Agendamento", 
                Formatacao.ajustaDataAMD(dataAgendamento), DadosDAO.TIPO_DATE,
                DadosDAO.IS_IGUAL, DadosDAO.NAO_CHAVE));
        getObjetoDAO().addCampo(new DadosDAO("horario", "Horário",
                String.valueOf(horario), DadosDAO.TIPO_DOUBLE,
                DadosDAO.IS_IGUAL, DadosDAO.NAO_CHAVE));
        getObjetoDAO().addCampo(new DadosDAO("observacoes","Observações", 
                observacoes, DadosDAO.TIPO_STRING, DadosDAO.IS_IGUAL, 
                DadosDAO.NAO_CHAVE));
    }

    /**
     * @return the dadosCodigo
     */
    @Override
    public DadosDAO getDadosCodigo() {
        return new DadosDAO("id_agenda", "Cód. Agenda", String.valueOf(idAgenda),
                DadosDAO.TIPO_LONG, DadosDAO.IS_IGUAL, DadosDAO.IS_CHAVE);
    }

    /**
     * @return the dadosDescricao
     */
    @Override
    public DadosDAO getDadosDescricao() {
        return new DadosDAO("id_agenda||' - '||data_agendamento", "Agenda", 
                "", DadosDAO.IS_IGUAL, DadosDAO.TIPO_STRING, DadosDAO.NAO_CHAVE);
    }

    /**
     * @return the idAgenda
     */
    public long getIdAgenda() {
        return idAgenda;
    }

    /**
     * @param idAgenda the idAgenda to set
     */
    public void setIdAgenda(long idAgenda) {
        this.idAgenda = idAgenda;
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
     * @return the codFuncionario
     */
    public long getCodFuncionario() {
        return codFuncionario;
    }

    /**
     * @param codFuncionario the codFuncionario to set
     */
    public void setCodFuncionario(long codFuncionario) {
        this.codFuncionario = codFuncionario;
    }

    /**
     * @return the codProfissional
     */
    public long getCodProfissional() {
        return codProfissional;
    }

    /**
     * @param codProfissional the codProfissional to set
     */
    public void setCodProfissional(long codProfissional) {
        this.codProfissional = codProfissional;
    }

    /**
     * @return the dataAgendamento
     */
    public Calendar getDataAgendamento() {
        return dataAgendamento;
    }

    /**
     * @param dataAgendamento the dataAgendamento to set
     */
    public void setDataAgendamento(Calendar dataAgendamento) {
        this.dataAgendamento = dataAgendamento;
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
     * @return the agendaDAO
     */
    public AgendasDAO getAgendaDAO() {
        return agendaDAO;
    }

    /**
     * @param agendaDAO the agendaDAO to set
     */
    public void setAgendaDAO(AgendasDAO agendaDAO) {
        this.agendaDAO = agendaDAO;
    }

    /**
     * @return the codMotivoAgenda
     */
    public long getCodMotivoAgenda() {
        return codMotivoAgenda;
    }

    /**
     * @param codMotivoAgenda the codMotivoAgenda to set
     */
    public void setCodMotivoAgenda(long codMotivoAgenda) {
        this.codMotivoAgenda = codMotivoAgenda;
    }

    /**
     * @return the horario
     */
    public double getHorario() {
        return horario;
    }

    /**
     * @param horario the horario to set
     */
    public void setHorario(double horario) {
        this.horario = horario;
    }
}
