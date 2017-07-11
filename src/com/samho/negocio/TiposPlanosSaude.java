/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.samho.negocio;

import com.samho.dao.DadosDAO;
import com.samho.dao.ObjetoDAO;
import com.samho.dao.TiposPlanosSaudeDAO;
import java.util.Objects;

/**
 * Classe de Objeto.
 *
 * @author Saibel, Sergio Luis
 * @since  date 30/04/2017
 *
 * @version  revision 001.20170430 date 30/04/2017 author Saibel, Sergio Luís reason
 * Instanciar um objeto com métodos e atributos necessários.
 */
public final class TiposPlanosSaude extends Objeto implements InterfaceObj {

    // Declaração de atributos
    private long idTipoPlano;
    private long codPlanoSaude;
    private String descricao;
    private double vlrDiferenca;
    private int carenciaMeses;
    private boolean ativo;

    private TiposPlanosSaudeDAO tipoPlanoSaudeDAO;

    // Construtor padrão
    public TiposPlanosSaude() {
        idTipoPlano = -1;
        codPlanoSaude = -1;
        descricao = "";
        vlrDiferenca = 0.0;
        carenciaMeses = 0;
        ativo = false;

        tipoPlanoSaudeDAO = new TiposPlanosSaudeDAO();

        adicionarCampos();
    }

    // Construtor
    public TiposPlanosSaude(TiposPlanosSaude tipoPlanoSaude) {
        idTipoPlano = tipoPlanoSaude.getIdTipoPlano();
        codPlanoSaude = tipoPlanoSaude.getCodPlanoSaude();
        descricao = tipoPlanoSaude.getDescricao();
        vlrDiferenca = tipoPlanoSaude.getVlrDiferenca();
        carenciaMeses = tipoPlanoSaude.getCarenciaMeses();
        ativo = tipoPlanoSaude.isAtivo();

        tipoPlanoSaudeDAO = tipoPlanoSaude.getTipoPlanoSaudeDAO();

        adicionarCampos();
    }

    // Construtor
    public TiposPlanosSaude(long idTipoPlano, long codPlanoSaude,
            String descricao, double vlr_diferenca, int carenciaMeses, 
            boolean ativo) {
        this.idTipoPlano = idTipoPlano;
        this.codPlanoSaude = codPlanoSaude;
        this.descricao = descricao;
        this.vlrDiferenca = vlr_diferenca ;
        this.carenciaMeses = carenciaMeses;
        this.ativo = false;

        tipoPlanoSaudeDAO = new TiposPlanosSaudeDAO();

        adicionarCampos();
    }

    @Override
    public boolean equals(Object object) {
        boolean retorno = false;

        if (object instanceof TiposPlanosSaude) {
            TiposPlanosSaude other = (TiposPlanosSaude) object;
            if (idTipoPlano == other.getIdTipoPlano()) {
                retorno = true;
            }
        }

        return retorno;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + (int) (this.idTipoPlano ^ (this.idTipoPlano >>> 32));
        hash = 53 * hash + (int) (this.codPlanoSaude ^ (this.codPlanoSaude >>> 32));
        hash = 53 * hash + Objects.hashCode(this.descricao);
        hash = 53 * hash + (int) (Double.doubleToLongBits(this.vlrDiferenca) ^ (Double.doubleToLongBits(this.vlrDiferenca) >>> 32));
        hash = 53 * hash + this.carenciaMeses;
        hash = 53 * hash + (this.ativo ? 1 : 0);
        hash = 53 * hash + Objects.hashCode(this.tipoPlanoSaudeDAO);
        return hash;
    }

    @Override
    public ObjetoDAO getObjetoDAO() {
        return tipoPlanoSaudeDAO;
    }

    @Override
    public void adicionarCampos() {
        removerCampos();

        getObjetoDAO().addCampo(getDadosCodigo());
        getObjetoDAO().addCampo(new DadosDAO("cod_plano_saude", "Cód. Plano", 
                String.valueOf(codPlanoSaude), DadosDAO.TIPO_LONG, 
                DadosDAO.IS_IGUAL, DadosDAO.NAO_CHAVE));
        getObjetoDAO().addCampo(getDadosDescricao());
        getObjetoDAO().addCampo(new DadosDAO("vlr_diferenca", "Vlr. Diferença",
                String.valueOf(vlrDiferenca), DadosDAO.TIPO_DOUBLE, 
                DadosDAO.IS_IGUAL, DadosDAO.NAO_CHAVE));
        getObjetoDAO().addCampo(new DadosDAO("carencia_meses", "Carência",
                String.valueOf(carenciaMeses), DadosDAO.TIPO_INTEGER, 
                DadosDAO.IS_IGUAL, DadosDAO.NAO_CHAVE));        
        getObjetoDAO().addCampo(new DadosDAO("ativo", "Ativo",
                String.valueOf(ativo), DadosDAO.TIPO_BOOLEAN, DadosDAO.IS_IGUAL, 
                DadosDAO.NAO_CHAVE));
    }

    /**
     * @return the dadosCodigo
     */
    @Override
    public DadosDAO getDadosCodigo() {
        return new DadosDAO("id_tipo_plano", "Cód. Tipo Palno",
                String.valueOf(idTipoPlano), DadosDAO.TIPO_LONG, 
                DadosDAO.IS_IGUAL, DadosDAO.IS_CHAVE);
    }

    /**
     * @return the dadosDescricao
     */
    @Override
    public DadosDAO getDadosDescricao() {
        return new DadosDAO("descricao", "Descrição", getDescricao(),
                DadosDAO.TIPO_STRING, DadosDAO.IS_IGUAL, DadosDAO.NAO_CHAVE);
    }

    /**
     * @return the idTipoPlano
     */
    public long getIdTipoPlano() {
        return idTipoPlano;
    }

    /**
     * @param idTipoPlano the idTipoPlano to set
     */
    public void setIdTipoPlano(long idTipoPlano) {
        this.idTipoPlano = idTipoPlano;
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
     * @return the vlrDiferenca
     */
    public double getVlrDiferenca() {
        return vlrDiferenca;
    }

    /**
     * @param vlrDiferenca the vlrDiferenca to set
     */
    public void setVlrDiferenca(double vlrDiferenca) {
        this.vlrDiferenca = vlrDiferenca;
    }

    /**
     * @return the carenciaMeses
     */
    public int getCarenciaMeses() {
        return carenciaMeses;
    }

    /**
     * @param carenciaMeses the carenciaMeses to set
     */
    public void setCarenciaMeses(int carenciaMeses) {
        this.carenciaMeses = carenciaMeses;
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
     * @return the tipoPlanoSaudeDAO
     */
    public TiposPlanosSaudeDAO getTipoPlanoSaudeDAO() {
        return tipoPlanoSaudeDAO;
    }

    /**
     * @param tipoPlanoSaudeDAO the tipoPlanoSaudeDAO to set
     */
    public void setTipoPlanoSaudeDAO(TiposPlanosSaudeDAO tipoPlanoSaudeDAO) {
        this.tipoPlanoSaudeDAO = tipoPlanoSaudeDAO;
    }
}
