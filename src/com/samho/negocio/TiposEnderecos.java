/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.samho.negocio;

import com.samho.dao.DadosDAO;
import com.samho.dao.ObjetoDAO;
import com.samho.dao.TiposEnderecosDAO;
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
public final class TiposEnderecos extends Objeto implements InterfaceObj {

    // Declaração de atributos
    private long idTipoEndereco;
    private String descricao;
    private boolean ativo;

    private TiposEnderecosDAO tipoEnderecoDAO;

    // Construtor padrão
    public TiposEnderecos() {
        idTipoEndereco = -1;
        descricao = "";
        ativo = true;

        tipoEnderecoDAO = new TiposEnderecosDAO();

        adicionarCampos();
    }

    // Construtor
    public TiposEnderecos(TiposEnderecos tipoEndereco) {
        idTipoEndereco = tipoEndereco.getIdTipoEndereco();
        descricao = tipoEndereco.getDescricao();
        ativo = tipoEndereco.isAtivo();

        tipoEnderecoDAO = tipoEndereco.getTipoEnderecoDAO();

        adicionarCampos();
    }

    // Construtor
    public TiposEnderecos(int idTipoEndereco, String descricao, boolean ativo) {
        this.idTipoEndereco = idTipoEndereco;
        this.descricao = descricao;
        this.ativo = ativo;

        this.tipoEnderecoDAO = new TiposEnderecosDAO();

        adicionarCampos();
    }

    @Override
    public boolean equals(Object object) {
        boolean retorno = false;

        if (object instanceof TiposEnderecos) {
            TiposEnderecos other = (TiposEnderecos) object;
            if (idTipoEndereco == other.getIdTipoEndereco()) {
                retorno = true;
            }
        }

        return retorno;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + (int) (this.idTipoEndereco ^ (this.idTipoEndereco >>> 32));
        hash = 47 * hash + Objects.hashCode(this.descricao);
        hash = 47 * hash + (this.ativo ? 1 : 0);
        hash = 47 * hash + Objects.hashCode(this.tipoEnderecoDAO);
        return hash;
    }

    @Override
    public ObjetoDAO getObjetoDAO() {
        return tipoEnderecoDAO;
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
        return new DadosDAO("id_tipo_endereco", "Cód. Tipo Ender.",
                String.valueOf(idTipoEndereco), DadosDAO.TIPO_LONG,
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
     * @return the idTipoEndereco
     */
    public long getIdTipoEndereco() {
        return idTipoEndereco;
    }

    /**
     * @param idTipoEndereco the idTipoEndereco to set
     */
    public void setIdTipoEndereco(long idTipoEndereco) {
        this.idTipoEndereco = idTipoEndereco;
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
     * @return the tipoEnderecoDAO
     */
    public TiposEnderecosDAO getTipoEnderecoDAO() {
        return tipoEnderecoDAO;
    }

    /**
     * @param tipoEnderecoDAO the tipoEnderecoDAO to set
     */
    public void setTipoEnderecoDAO(TiposEnderecosDAO tipoEnderecoDAO) {
        this.tipoEnderecoDAO = tipoEnderecoDAO;
    }

}
