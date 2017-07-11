/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.samho.negocio;

import com.samho.dao.EstadosDAO;
import com.samho.dao.DadosDAO;
import com.samho.dao.ObjetoDAO;
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
public final class Estados extends Objeto implements InterfaceObj {

    // Declaração de atributos
    private long idEstado;
    private long codPais;
    private int codigoIBGE;
    private String sigla;
    private String descricao;

    private EstadosDAO estadoDAO;

    // Construtor padrão
    public Estados() {
        idEstado = -1;
        codPais = -1;
        codigoIBGE = 0;
        sigla = "";
        descricao = "";

        estadoDAO = new EstadosDAO();

        adicionarCampos();
    }

    // Construtor
    public Estados(Estados estado) {
        idEstado = estado.getIdEstado();
        codPais = estado.getCodPais();
        codigoIBGE = estado.getCodigoIBGE();
        sigla = estado.getSigla();
        descricao = estado.getDescricao();

        estadoDAO = estado.getEstadoDAO();

        adicionarCampos();
    }

    // Construtor
    public Estados(int idEstado, int codPais, int codigoIBGE, String sigla,
            String descricao) {
        this.idEstado = idEstado;
        this.codPais = codPais;
        this.codigoIBGE = codigoIBGE;
        this.sigla = sigla;
        this.descricao = descricao;

        this.estadoDAO = new EstadosDAO();

        adicionarCampos();
    }

    @Override
    public boolean equals(Object object) {
        boolean retorno = false;

        if (object instanceof Estados) {
            Estados other = (Estados) object;
            if (idEstado == other.getIdEstado()) {
                retorno = true;
            }
        }

        return retorno;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + (int) (this.idEstado ^ (this.idEstado >>> 32));
        hash = 67 * hash + (int) (this.codPais ^ (this.codPais >>> 32));
        hash = 67 * hash + this.codigoIBGE;
        hash = 67 * hash + Objects.hashCode(this.sigla);
        hash = 67 * hash + Objects.hashCode(this.descricao);
        hash = 67 * hash + Objects.hashCode(this.estadoDAO);
        return hash;
    }

    @Override
    public ObjetoDAO getObjetoDAO() {
        return estadoDAO;
    }

    @Override
    public void adicionarCampos() {
        removerCampos();

        getObjetoDAO().addCampo(getDadosCodigo());
        getObjetoDAO().addCampo(new DadosDAO("cod_pais", "Cód. Pais",
                String.valueOf(codPais), DadosDAO.TIPO_LONG,
                DadosDAO.IS_IGUAL, DadosDAO.NAO_CHAVE));
        getObjetoDAO().addCampo(new DadosDAO("codigo_ibge", "Código IBGE",
                String.valueOf(codigoIBGE), DadosDAO.TIPO_INTEGER,
                DadosDAO.IS_IGUAL, DadosDAO.NAO_CHAVE));
        getObjetoDAO().addCampo(new DadosDAO("sigla", "Sigla", sigla,
                DadosDAO.TIPO_STRING, DadosDAO.IS_IGUAL, DadosDAO.NAO_CHAVE));
        getObjetoDAO().addCampo(getDadosDescricao());
    }

    /**
     * @return the dadosCodigo
     */
    @Override
    public DadosDAO getDadosCodigo() {
        return new DadosDAO("id_estado", "Cód. Estado", String.valueOf(idEstado),
                DadosDAO.TIPO_LONG, DadosDAO.IS_IGUAL, DadosDAO.IS_CHAVE);
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
     * @return the idEstado
     */
    public long getIdEstado() {
        return idEstado;
    }

    /**
     * @param idEstado the idEstado to set
     */
    public void setIdEstado(long idEstado) {
        this.idEstado = idEstado;
    }

    /**
     * @return the codPais
     */
    public long getCodPais() {
        return codPais;
    }

    /**
     * @param codPais the codPais to set
     */
    public void setCodPais(long codPais) {
        this.codPais = codPais;
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
     * @return the codigoIBGE
     */
    public int getCodigoIBGE() {
        return codigoIBGE;
    }

    /**
     * @param codigoIBGE the codigoIBGE to set
     */
    public void setCodigoIBGE(int codigoIBGE) {
        this.codigoIBGE = codigoIBGE;
    }

    /**
     * @return the sigla
     */
    public String getSigla() {
        return sigla;
    }

    /**
     * @param sigla the sigla to set
     */
    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    /**
     * @return the estadoDAO
     */
    public EstadosDAO getEstadoDAO() {
        return estadoDAO;
    }

    /**
     * @param estadoDAO the estadoDAO to set
     */
    public void setEstadoDAO(EstadosDAO estadoDAO) {
        this.estadoDAO = estadoDAO;
    }
}
