/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.samho.negocio;

import com.samho.dao.DadosDAO;
import com.samho.dao.ObjetoDAO;
import com.samho.dao.TiposTelefonesDAO;
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
public final class TiposTelefones extends Objeto implements InterfaceObj {

    // Declaração de atributos
    private long idTipoTelefone;
    private String descricao;
    private boolean ativo;

    private TiposTelefonesDAO tipoTelefoneDAO;

    // Construtor padrão
    public TiposTelefones() {
        idTipoTelefone = -1;
        descricao = "";
        ativo = true;

        tipoTelefoneDAO = new TiposTelefonesDAO();

        adicionarCampos();
    }

    // Construtor
    public TiposTelefones(TiposTelefones tipoTelefone) {
        idTipoTelefone = tipoTelefone.getIdTipoTelefone();
        descricao = tipoTelefone.getDescricao();
        ativo = tipoTelefone.isAtivo();

        tipoTelefoneDAO = tipoTelefone.getTipoTelefoneDAO();

        adicionarCampos();
    }

    // Construtor
    public TiposTelefones(int idTipoTelefone, String descricao, boolean ativo) {
        this.idTipoTelefone = idTipoTelefone;
        this.descricao = descricao;
        this.ativo = ativo;

        this.tipoTelefoneDAO = new TiposTelefonesDAO();

        adicionarCampos();
    }

    @Override
    public boolean equals(Object object) {
        boolean retorno = false;

        if (object instanceof TiposTelefones) {
            TiposTelefones other = (TiposTelefones) object;
            if (idTipoTelefone == other.getIdTipoTelefone()) {
                retorno = true;
            }
        }

        return retorno;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + (int) (this.idTipoTelefone ^ (this.idTipoTelefone >>> 32));
        hash = 31 * hash + Objects.hashCode(this.descricao);
        hash = 31 * hash + (this.ativo ? 1 : 0);
        hash = 31 * hash + Objects.hashCode(this.tipoTelefoneDAO);
        return hash;
    }

    @Override
    public ObjetoDAO getObjetoDAO() {
        return tipoTelefoneDAO;
    }

    @Override
    public void adicionarCampos() {
        removerCampos();

        getObjetoDAO().addCampo(getDadosCodigo());
        getObjetoDAO().addCampo(getDadosDescricao());
        getObjetoDAO().addCampo(new DadosDAO("ativo", "Ativo",
                String.valueOf(ativo), DadosDAO.TIPO_BOOLEAN, DadosDAO.IS_IGUAL, 
                DadosDAO.NAO_CHAVE));
    }

    /**
     * @return the dadosCodigo
     */
    @Override
    public DadosDAO getDadosCodigo() {
        return new DadosDAO("id_tipo_telefone", "Cód. Tipo Tel.",
                String.valueOf(idTipoTelefone), DadosDAO.TIPO_LONG,
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
     * @return the idTipoTelefone
     */
    public long getIdTipoTelefone() {
        return idTipoTelefone;
    }

    /**
     * @param idTipoTelefone the idTipoTelefone to set
     */
    public void setIdTipoTelefone(long idTipoTelefone) {
        this.idTipoTelefone = idTipoTelefone;
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
     * @return the tipoTelefoneDAO
     */
    public TiposTelefonesDAO getTipoTelefoneDAO() {
        return tipoTelefoneDAO;
    }

    /**
     * @param tipoTelefoneDAO the tipoTelefoneDAO to set
     */
    public void setTipoTelefoneDAO(TiposTelefonesDAO tipoTelefoneDAO) {
        this.tipoTelefoneDAO = tipoTelefoneDAO;
    }

}
