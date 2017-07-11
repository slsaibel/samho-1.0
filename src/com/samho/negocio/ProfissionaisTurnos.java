/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.samho.negocio;

import com.samho.dao.ProfissionaisTurnosDAO;
import com.samho.dao.DadosDAO;
import com.samho.dao.ObjetoDAO;
import java.util.Objects;

/**
 * Classe de Funcionários.
 *
 * @author Sergio
 * @since  date 20/04/2014
 *
 * @version  revision 001.20140420 date 20/04/2014 author Saibel, Sergio Luís reason
 * Instanciar um objeto com métodos e atributos necessários.
 */
public final class ProfissionaisTurnos extends Objeto implements InterfaceObj {
    
    // Declaração de atributos
    private long idProfissionalTurnos;
    private long codProfissional;
    private int diaSemana;
    private boolean madrugada;
    private boolean manha;
    private boolean tarde;
    private boolean noite;
    private boolean atendeFeriado;
    private boolean atendeExtra;

    private ProfissionaisTurnosDAO profissionalTurnosDAO;

    // Construtor padrão
    public ProfissionaisTurnos() {
        idProfissionalTurnos = -1;
        codProfissional = -1;
        diaSemana = -1;
        madrugada = false;
        manha = false;
        tarde = false;
        noite = false;
        atendeFeriado = false;
        atendeExtra = false;
        
        profissionalTurnosDAO = new ProfissionaisTurnosDAO();

        adicionarCampos();
    }

    // Construtor
    public ProfissionaisTurnos(ProfissionaisTurnos profissionalTurnos) {
        idProfissionalTurnos = profissionalTurnos.getIdProfissionalTurnos();
        codProfissional = profissionalTurnos.getCodProfissional();
        diaSemana = profissionalTurnos.getDiaSemana();
        madrugada = profissionalTurnos.isMadrugada();
        manha = profissionalTurnos.isManha();
        tarde = profissionalTurnos.isTarde();
        noite = profissionalTurnos.isNoite();
        atendeFeriado = profissionalTurnos.isAtendeFeriado();
        atendeExtra = profissionalTurnos.isAtendeExtra();
        
        profissionalTurnosDAO = profissionalTurnos.getProfissionalTurnosDAO();

        adicionarCampos();
    }

    // Construtor
    public ProfissionaisTurnos(int idProfissionalTurnos, int codProfissional,
            int diaSemana, boolean madrugada, boolean manha, boolean tarde, 
            boolean noite, boolean atendeFeriado, boolean atendeExtra) {
        this.idProfissionalTurnos = idProfissionalTurnos;
        this.codProfissional = codProfissional;
        this.diaSemana = diaSemana;
        this.madrugada = madrugada;
        this.manha = manha;
        this.tarde = tarde;
        this.noite = noite;
        this.atendeFeriado = atendeFeriado;
        this.atendeExtra = atendeExtra;
        
        profissionalTurnosDAO = new ProfissionaisTurnosDAO();

        adicionarCampos();
    }

    @Override
    public boolean equals(Object object) {
        boolean retorno = false;

        if (object instanceof ProfissionaisTurnos) {
            ProfissionaisTurnos other = (ProfissionaisTurnos) object;
            if (idProfissionalTurnos == other.getIdProfissionalTurnos()) {
                retorno = true;
            }
        }

        return retorno;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + (int) (this.idProfissionalTurnos ^ (this.idProfissionalTurnos >>> 32));
        hash = 17 * hash + (int) (this.codProfissional ^ (this.codProfissional >>> 32));
        hash = 17 * hash + this.diaSemana;
        hash = 17 * hash + (this.madrugada ? 1 : 0);
        hash = 17 * hash + (this.manha ? 1 : 0);
        hash = 17 * hash + (this.tarde ? 1 : 0);
        hash = 17 * hash + (this.noite ? 1 : 0);
        hash = 17 * hash + (this.atendeFeriado ? 1 : 0);
        hash = 17 * hash + (this.atendeExtra ? 1 : 0);
        hash = 17 * hash + Objects.hashCode(this.profissionalTurnosDAO);
        return hash;
    }


    @Override
    public ObjetoDAO getObjetoDAO() {
        return profissionalTurnosDAO;
    }

    @Override
    public void adicionarCampos() {
        removerCampos();

        getObjetoDAO().addCampo(getDadosCodigo());
        getObjetoDAO().addCampo(new DadosDAO("cod_profissional", 
                "Cód. Profissional", String.valueOf(codProfissional), 
                DadosDAO.TIPO_LONG, DadosDAO.IS_IGUAL, DadosDAO.NAO_CHAVE));
        getObjetoDAO().addCampo(new DadosDAO("dia_semana", "Dia Semana",
                String.valueOf(diaSemana), DadosDAO.TIPO_INTEGER,
                DadosDAO.IS_IGUAL, DadosDAO.NAO_CHAVE));
        getObjetoDAO().addCampo(new DadosDAO("madrugada", "Madrugada",
                String.valueOf(madrugada), DadosDAO.TIPO_BOOLEAN,
                DadosDAO.IS_IGUAL, DadosDAO.NAO_CHAVE));
        getObjetoDAO().addCampo(new DadosDAO("manha", "Manha",
                String.valueOf(manha), DadosDAO.TIPO_BOOLEAN,
                DadosDAO.IS_IGUAL, DadosDAO.NAO_CHAVE));
        getObjetoDAO().addCampo(new DadosDAO("tarde", "Tarde",
                String.valueOf(tarde), DadosDAO.TIPO_BOOLEAN,
                DadosDAO.IS_IGUAL, DadosDAO.NAO_CHAVE));
        getObjetoDAO().addCampo(new DadosDAO("noite", "Noite",
                String.valueOf(noite), DadosDAO.TIPO_BOOLEAN,
                DadosDAO.IS_IGUAL, DadosDAO.NAO_CHAVE));
        getObjetoDAO().addCampo(new DadosDAO("atende_feriado", "Feriado",
                String.valueOf(atendeFeriado), DadosDAO.TIPO_BOOLEAN,
                DadosDAO.IS_IGUAL, DadosDAO.NAO_CHAVE));
        getObjetoDAO().addCampo(new DadosDAO("atende_extra", "Extra",
                String.valueOf(atendeExtra), DadosDAO.TIPO_BOOLEAN,
                DadosDAO.IS_IGUAL, DadosDAO.NAO_CHAVE));
    }

    /**
     * @return the dadosCodigo
     */
    @Override
    public DadosDAO getDadosCodigo() {
        return new DadosDAO("id_profissional_turno", "Cód. Prof. Turno",
                String.valueOf(idProfissionalTurnos), DadosDAO.TIPO_LONG,
                DadosDAO.IS_IGUAL, DadosDAO.IS_CHAVE);
    }

    /**
     * @return the dadosDescricao
     */
    @Override
    public DadosDAO getDadosDescricao() {
        return new DadosDAO(
                profissionalTurnosDAO.getSQLSubstiturCampoDescricao(codProfissional), 
                "Profissional", "", DadosDAO.TIPO_STRING, DadosDAO.IS_IGUAL, 
                DadosDAO.NAO_CHAVE);
    }

    /**
     * @return the idProfissionalTurnos
     */
    public long getIdProfissionalTurnos() {
        return idProfissionalTurnos;
    }

    /**
     * @param idProfissionalTurnos the idProfissionalTurnos to set
     */
    public void setIdProfissionalTurnos(long idProfissionalTurnos) {
        this.idProfissionalTurnos = idProfissionalTurnos;
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
     * @return the diaSemana
     */
    public int getDiaSemana() {
        return diaSemana;
    }

    /**
     * @param diaSemana the diaSemana to set
     */
    public void setDiaSemana(int diaSemana) {
        this.diaSemana = diaSemana;
    }

    /**
     * @return the madrugada
     */
    public boolean isMadrugada() {
        return madrugada;
    }

    /**
     * @param madrugada the madrugada to set
     */
    public void setMadrugada(boolean madrugada) {
        this.madrugada = madrugada;
    }

    /**
     * @return the manha
     */
    public boolean isManha() {
        return manha;
    }

    /**
     * @param manha the manha to set
     */
    public void setManha(boolean manha) {
        this.manha = manha;
    }

    /**
     * @return the tarde
     */
    public boolean isTarde() {
        return tarde;
    }

    /**
     * @param tarde the tarde to set
     */
    public void setTarde(boolean tarde) {
        this.tarde = tarde;
    }

    /**
     * @return the noite
     */
    public boolean isNoite() {
        return noite;
    }

    /**
     * @param noite the noite to set
     */
    public void setNoite(boolean noite) {
        this.noite = noite;
    }

    /**
     * @return the profissionalTurnosDAO
     */
    public ProfissionaisTurnosDAO getProfissionalTurnosDAO() {
        return profissionalTurnosDAO;
    }

    /**
     * @param profissionalTurnosDAO the profissionalTurnosDAO to set
     */
    public void setProfissionalTurnosDAO(ProfissionaisTurnosDAO profissionalTurnosDAO) {
        this.profissionalTurnosDAO = profissionalTurnosDAO;
    }

    /**
     * @return the atendeFeriado
     */
    public boolean isAtendeFeriado() {
        return atendeFeriado;
    }

    /**
     * @param atendeFeriado the atendeFeriado to set
     */
    public void setAtendeFeriado(boolean atendeFeriado) {
        this.atendeFeriado = atendeFeriado;
    }

    /**
     * @return the atendeExtra
     */
    public boolean isAtendeExtra() {
        return atendeExtra;
    }

    /**
     * @param atendeExtra the atendeExtra to set
     */
    public void setAtendeExtra(boolean atendeExtra) {
        this.atendeExtra = atendeExtra;
    }
}
