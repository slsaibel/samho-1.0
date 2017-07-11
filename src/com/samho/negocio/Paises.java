/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.samho.negocio;

import com.samho.dao.DadosDAO;
import com.samho.dao.ObjetoDAO;
import com.samho.dao.PaisesDAO;
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
public final class Paises extends Objeto implements InterfaceObj {

    // Declaração de atributos
    private long idPais;
    private int codigoIBGE;
    private String sigla;
    private String descricao;

    private PaisesDAO paisesDAO;

    // Construtor padrão
    public Paises() {
        idPais = -1;
        codigoIBGE = -1;
        sigla = "";
        descricao = "";

        paisesDAO = new PaisesDAO();

        adicionarCampos();
    }

    // Construtor
    public Paises(Paises pais) {
        idPais = pais.getIdPais();
        codigoIBGE = pais.getCodigoIBGE();
        sigla = pais.getSigla();
        descricao = pais.getDescricao();

        paisesDAO = pais.getPaisesDAO();

        adicionarCampos();
    }

    // Construtor
    public Paises(long idPais, int codigoIBGE, String sigla, String descricao) {
        this.idPais = idPais;
        this.codigoIBGE = codigoIBGE;
        this.sigla = sigla;
        this.descricao = descricao;

        this.paisesDAO = new PaisesDAO();

        adicionarCampos();
    }

    @Override
    public boolean equals(Object object) {
        boolean retorno = false;

        if (object instanceof Paises) {
            Paises other = (Paises) object;
            if (idPais == other.getIdPais()) {
                retorno = true;
            }
        }

        return retorno;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 13 * hash + (int) (this.idPais ^ (this.idPais >>> 32));
        hash = 13 * hash + this.codigoIBGE;
        hash = 13 * hash + Objects.hashCode(this.sigla);
        hash = 13 * hash + Objects.hashCode(this.descricao);
        hash = 13 * hash + Objects.hashCode(this.paisesDAO);
        return hash;
    }

    @Override
    public ObjetoDAO getObjetoDAO() {
        return paisesDAO;
    }

    @Override
    public void adicionarCampos() {
        removerCampos();

        getObjetoDAO().addCampo(getDadosCodigo());
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
        return new DadosDAO("id_pais", "Cód. País", String.valueOf(idPais), 
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
     * @return the idPais
     */
    public long getIdPais() {
        return idPais;
    }

    /**
     * @param idPais the idPais to set
     */
    public void setIdPais(long idPais) {
        this.idPais = idPais;
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
     * @return the paisesDAO
     */
    public PaisesDAO getPaisesDAO() {
        return paisesDAO;
    }

    /**
     * @param paisesDAO the paisesDAO to set
     */
    public void setPaisesDAO(PaisesDAO paisesDAO) {
        this.paisesDAO = paisesDAO;
    }

}
