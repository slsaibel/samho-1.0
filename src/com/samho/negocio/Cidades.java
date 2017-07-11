/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.samho.negocio;

import com.samho.dao.CidadesDAO;
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
public final class Cidades extends Objeto implements InterfaceObj {

    // Declaração de atributos
    private long idCidade;
    private long codEstado;
    private long codigoIBGE;
    private String descricao;

    private CidadesDAO cidadeDAO;

    // Construtor padrão
    public Cidades() {
        idCidade = -1;
        codEstado = -1;
        codigoIBGE = 0;
        descricao = "";

        cidadeDAO = new CidadesDAO();

        adicionarCampos();
    }

    // Construtor
    public Cidades(Cidades cidade) {
        idCidade = cidade.getIdCidade();
        codEstado = cidade.getCodEstado();
        codigoIBGE = cidade.getCodigoIBGE();
        descricao = cidade.getDescricao();

        cidadeDAO = cidade.getCidadeDAO();

        adicionarCampos();
    }

    // Construtor
    public Cidades(int idCidade, int codEstado, int codigoIBGE,
            String descricao) {
        this.idCidade = idCidade;
        this.codEstado = codEstado;
        this.codigoIBGE = codigoIBGE;
        this.descricao = descricao;

        this.cidadeDAO = new CidadesDAO();

        adicionarCampos();
    }

    @Override
    public boolean equals(Object object) {
        boolean retorno = false;

        if (object instanceof Cidades) {
            Cidades other = (Cidades) object;
            if (idCidade == other.getIdCidade()) {
                retorno = true;
            }
        }

        return retorno;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + (int) (this.idCidade ^ (this.idCidade >>> 32));
        hash = 37 * hash + (int) (this.codEstado ^ (this.codEstado >>> 32));
        hash = 37 * hash + (int) (this.codigoIBGE ^ (this.codigoIBGE >>> 32));
        hash = 37 * hash + Objects.hashCode(this.descricao);
        hash = 37 * hash + Objects.hashCode(this.cidadeDAO);
        return hash;
    }

    @Override
    public ObjetoDAO getObjetoDAO() {
        return cidadeDAO;
    }

    @Override
    public void adicionarCampos() {
        removerCampos();

        getObjetoDAO().addCampo(getDadosCodigo());
        getObjetoDAO().addCampo(new DadosDAO("cod_estado", "Cód. Estado",
                String.valueOf(codEstado), DadosDAO.TIPO_LONG,
                DadosDAO.IS_IGUAL, DadosDAO.NAO_CHAVE));
        getObjetoDAO().addCampo(new DadosDAO("codigo_ibge", "Código IBGE",
                String.valueOf(codigoIBGE), DadosDAO.TIPO_INTEGER,
                DadosDAO.IS_IGUAL, DadosDAO.NAO_CHAVE));
        getObjetoDAO().addCampo(getDadosDescricao());
    }

    /**
     * @return the dadosCodigo
     */
    @Override
    public DadosDAO getDadosCodigo() {
        return new DadosDAO("id_cidade", "Cód. Cidade", String.valueOf(idCidade),
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
     * @return the idCidade
     */
    public long getIdCidade() {
        return idCidade;
    }

    /**
     * @param idCidade the idCidade to set
     */
    public void setIdCidade(long idCidade) {
        this.idCidade = idCidade;
    }

    /**
     * @return the codEstado
     */
    public long getCodEstado() {
        return codEstado;
    }

    /**
     * @param codEstado the codEstado to set
     */
    public void setCodEstado(long codEstado) {
        this.codEstado = codEstado;
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
    public long getCodigoIBGE() {
        return codigoIBGE;
    }

    /**
     * @param codigoIBGE the codigoIBGE to set
     */
    public void setCodigoIBGE(long codigoIBGE) {
        this.codigoIBGE = codigoIBGE;
    }

    /**
     * @return the cidadeDAO
     */
    public CidadesDAO getCidadeDAO() {
        return cidadeDAO;
    }

    /**
     * @param cidadeDAO the cidadeDAO to set
     */
    public void setCidadeDAO(CidadesDAO cidadeDAO) {
        this.cidadeDAO = cidadeDAO;
    }
}
