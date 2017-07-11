/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.samho.negocio;

import com.samho.dao.BairrosDAO;
import com.samho.dao.DadosDAO;
import  com.samho.dao.ObjetoDAO;
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
public final class Bairros extends Objeto implements InterfaceObj {

    // Declaração de atributos
    private long idBairro;
    private long codCidade;
    private String descricao;

    private BairrosDAO bairroDAO;

    // Construtor padrão
    public Bairros() {
        idBairro = -1;
        codCidade = -1;
        descricao = "";

        bairroDAO = new BairrosDAO();

        adicionarCampos();
    }

    // Construtor
    public Bairros(Bairros bairro) {
        idBairro = bairro.getIdBairro();
        codCidade = bairro.getCodCidade();
        descricao = bairro.getDescricao();

        bairroDAO = bairro.getBairroDAO();

        adicionarCampos();
    }

    // Construtor
    public Bairros(int idBairro, int codSituacao, String descricao) {
        this.idBairro = idBairro;
        this.codCidade = codSituacao;
        this.descricao = descricao;

        this.bairroDAO = new BairrosDAO();

        adicionarCampos();
    }

    @Override
    public boolean equals(Object object) {
        boolean retorno = false;

        if (object instanceof Bairros) {
            Bairros other = (Bairros) object;
            if (idBairro == other.getIdBairro()) {
                retorno = true;
            }
        }

        return retorno;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + (int) (this.idBairro ^ (this.idBairro >>> 32));
        hash = 59 * hash + (int) (this.codCidade ^ (this.codCidade >>> 32));
        hash = 59 * hash + Objects.hashCode(this.descricao);
        hash = 59 * hash + Objects.hashCode(this.bairroDAO);
        return hash;
    }

    @Override
    public ObjetoDAO getObjetoDAO() {
        return bairroDAO;
    }

    @Override
    public void adicionarCampos() {
        removerCampos();

        getObjetoDAO().addCampo(getDadosCodigo());
        getObjetoDAO().addCampo(new DadosDAO("cod_cidade", "Cód. Cidade",
                String.valueOf(codCidade), DadosDAO.TIPO_LONG,
                DadosDAO.IS_IGUAL, DadosDAO.NAO_CHAVE));
        getObjetoDAO().addCampo(getDadosDescricao());
    }

    @Override
    public DadosDAO getDadosCodigo() {
        return new DadosDAO("id_bairro", "Cód. Bairro", String.valueOf(idBairro),
                DadosDAO.TIPO_LONG, DadosDAO.IS_IGUAL, DadosDAO.IS_CHAVE);
    }

    @Override
    public DadosDAO getDadosDescricao() {
        return new DadosDAO("descricao", "Descrição", descricao,
                DadosDAO.TIPO_STRING, DadosDAO.IS_IGUAL, DadosDAO.NAO_CHAVE);
    }

    /**
     * @return the idBairro
     */
    public long getIdBairro() {
        return idBairro;
    }

    /**
     * @param idBairro the idBairro to set
     */
    public void setIdBairro(long idBairro) {
        this.idBairro = idBairro;
    }

    /**
     * @return the codCidade
     */
    public long getCodCidade() {
        return codCidade;
    }

    /**
     * @param codCidade the codCidade to set
     */
    public void setCodCidade(long codCidade) {
        this.codCidade = codCidade;
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
     * @return the bairroDAO
     */
    public BairrosDAO getBairroDAO() {
        return bairroDAO;
    }

    /**
     * @param bairroDAO the bairroDAO to set
     */
    public void setBairroDAO(BairrosDAO bairroDAO) {
        this.bairroDAO = bairroDAO;
    }
}
