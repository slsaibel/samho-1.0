/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.samho.negocio;

import com.samho.dao.ProfissionaisPlanosSaudeDAO;
import com.samho.dao.DadosDAO;
import com.samho.dao.ObjetoDAO;
import com.samho.util.Formatacao;
import java.util.Calendar;
import java.util.Objects;

/**
 * Classe de Funcionários.
 *
 * @author Sergio
 * @since  date 18/04/2014
 *
 * @version  revision 001.20140418 date 18/04/2014 author Saibel, Sergio Luís reason
 * Instanciar um objeto com métodos e atributos necessários.
 */
public final class ProfissionaisPlanosSaude extends Objeto implements InterfaceObj {

    // Declaração de atributos
    private long idProfissionalPlanoSaude;
    private long codProfissional;
    private long codPlanoSaude;
    private Calendar dataAdesao;
    private boolean ativo;


    private ProfissionaisPlanosSaudeDAO profissionalPlanoSaudeDAO;

    // Construtor padrão
    public ProfissionaisPlanosSaude() {
        idProfissionalPlanoSaude = -1;
        codProfissional = -1;
        codPlanoSaude = -1;
        dataAdesao = null;
        ativo = true;

        profissionalPlanoSaudeDAO = new ProfissionaisPlanosSaudeDAO();

        adicionarCampos();
    }

    // Construtor
    public ProfissionaisPlanosSaude(ProfissionaisPlanosSaude profissionalPlanoSaude) {
        idProfissionalPlanoSaude = profissionalPlanoSaude.getIdProfissionalPlanoSaude();
        codProfissional = profissionalPlanoSaude.getCodProfissional();
        codPlanoSaude = profissionalPlanoSaude.getCodPlanoSaude();
        dataAdesao = profissionalPlanoSaude.getDataAdesao();
        ativo = profissionalPlanoSaude.isAtivo();

        profissionalPlanoSaudeDAO = profissionalPlanoSaude.getProfissionalPlanoSaudeDAO();

        adicionarCampos();
    }

    // Construtor
    public ProfissionaisPlanosSaude(long idProfissionalPlanoSaude, 
            long codProfissional, long codTipoPlano, Calendar dataAdesao, 
            boolean ativo) {
        this.idProfissionalPlanoSaude = idProfissionalPlanoSaude;
        this.codProfissional = codProfissional;
        this.codPlanoSaude = codTipoPlano;
        this.dataAdesao = dataAdesao;
        this.ativo = ativo;

        this.profissionalPlanoSaudeDAO = new ProfissionaisPlanosSaudeDAO();

        adicionarCampos();
    }

    @Override
    public boolean equals(Object object) {
        boolean retorno = false;

        if (object instanceof ProfissionaisPlanosSaude) {
            ProfissionaisPlanosSaude other = (ProfissionaisPlanosSaude) object;
            if (idProfissionalPlanoSaude == other.getIdProfissionalPlanoSaude()) {
                retorno = true;
            }
        }
        
        return retorno;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + (int) (this.idProfissionalPlanoSaude ^ (this.idProfissionalPlanoSaude >>> 32));
        hash = 37 * hash + (int) (this.codProfissional ^ (this.codProfissional >>> 32));
        hash = 37 * hash + (int) (this.codPlanoSaude ^ (this.codPlanoSaude >>> 32));
        hash = 37 * hash + Objects.hashCode(this.dataAdesao);
        hash = 37 * hash + (this.ativo ? 1 : 0);
        hash = 37 * hash + Objects.hashCode(this.profissionalPlanoSaudeDAO);
        return hash;
    }

    @Override
    public ObjetoDAO getObjetoDAO() {
        return profissionalPlanoSaudeDAO;
    }

    @Override
    public void adicionarCampos() {
        removerCampos();

        getObjetoDAO().addCampo(getDadosCodigo());
        getObjetoDAO().addCampo(new DadosDAO("cod_profissional", "Cód. Profissional", 
                String.valueOf(codProfissional), DadosDAO.TIPO_LONG, DadosDAO.IS_IGUAL, 
                DadosDAO.NAO_CHAVE));
        getObjetoDAO().addCampo(new DadosDAO("cod_plano_saude", "Cód. Plano Saúde",
                String.valueOf(codPlanoSaude),DadosDAO.TIPO_LONG, DadosDAO.IS_IGUAL, 
                DadosDAO.NAO_CHAVE));
        getObjetoDAO().addCampo(new DadosDAO("data_adesao", "Data Adesão",
                Formatacao.ajustaDataAMD(dataAdesao), DadosDAO.TIPO_DATE,
                DadosDAO.IS_IGUAL, DadosDAO.NAO_CHAVE));
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
        return new DadosDAO("id_profissional_plano_saude", "Cód. Prof. Plano",
                String.valueOf(idProfissionalPlanoSaude), DadosDAO.TIPO_LONG,
                DadosDAO.IS_IGUAL, DadosDAO.IS_CHAVE);
    }

    /**
     * @return the dadosDescricao
     */
    @Override
    public DadosDAO getDadosDescricao() {
        return new DadosDAO(
                profissionalPlanoSaudeDAO.getSQLSubstiturCampoDescricao(
                        idProfissionalPlanoSaude), "Plano Saúde", "", 
                DadosDAO.TIPO_STRING, DadosDAO.IS_IGUAL, DadosDAO.NAO_CHAVE);
    }

    /**
     * @return the idProfissionalPlanoSaude
     */
    public long getIdProfissionalPlanoSaude() {
        return idProfissionalPlanoSaude;
    }

    /**
     * @param idProfissionalPlanoSaude the idProfissionalPlanoSaude to set
     */
    public void setIdProfissionalPlanoSaude(long idProfissionalPlanoSaude) {
        this.idProfissionalPlanoSaude = idProfissionalPlanoSaude;
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
     * @return the codPlanoSaude
     */
    public long getCodPlanoSaude() {
        return codPlanoSaude;
    }

    /**
     * @param codPlanoSaude the codPlanoSaude to set
     */
    public void setCodPlanoSaude(long codPlanoSaude) {
        this.codPlanoSaude = codPlanoSaude;
    }

    /**
     * @return the dataAdesao
     */
    public Calendar getDataAdesao() {
        return dataAdesao;
    }

    /**
     * @param dataAdesao the dataAdesao to set
     */
    public void setDataAdesao(Calendar dataAdesao) {
        this.dataAdesao = dataAdesao;
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
     * @return the profissionalPlanoSaudeDAO
     */
    public ProfissionaisPlanosSaudeDAO getProfissionalPlanoSaudeDAO() {
        return profissionalPlanoSaudeDAO;
    }

    /**
     * @param profissionalPlanoSaudeDAO the profissionalPlanoSaudeDAO to set
     */
    public void setProfissionalPlanoSaudeDAO(ProfissionaisPlanosSaudeDAO profissionalPlanoSaudeDAO) {
        this.profissionalPlanoSaudeDAO = profissionalPlanoSaudeDAO;
    }
}
