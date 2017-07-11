/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.samho.negocio;

import com.samho.dao.EnderecosDAO;
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
public final class Enderecos extends Objeto implements InterfaceObj {

    // Declaração de atributos
    private long idEndereco;
    private long codPessoa;
    private long codTipoEndereco;
    private long codBairro;
    private String descricao;
    private long cep;
    private String complemento;
    private boolean ativo;

    private EnderecosDAO enderecoDAO;

    // Construtor padrão
    public Enderecos() {
        idEndereco = -1;
        codPessoa = -1;
        codTipoEndereco = -1;
        codBairro = -1;
        descricao = "";
        cep = -1;
        complemento = "";
        ativo = true;

        enderecoDAO = new EnderecosDAO();

        adicionarCampos();
    }

    // Construtor
    public Enderecos(Enderecos endereco) {
        idEndereco = endereco.getIdEndereco();
        codPessoa = endereco.getCodPessoa();
        codTipoEndereco = endereco.getCodTipoEndereco();
        codBairro = endereco.getCodBairro();
        descricao = endereco.getDescricao();
        cep = endereco.getCep();
        complemento = endereco.getComplemento();
        ativo = endereco.isAtivo();

        enderecoDAO = endereco.getEnderecoDAO();

        adicionarCampos();
    }

    // Construtor
    public Enderecos(long idEndereco, long codSituacao, long codPessoa,
            long codTipoEndereco, long codBairro, String descricao, long cep,
            String complemento, boolean ativo) {
        this.idEndereco = idEndereco;
        this.codPessoa = codPessoa;
        this.codTipoEndereco = codTipoEndereco;
        this.codBairro = codBairro;
        this.descricao = descricao;
        this.cep = cep;
        this.complemento = complemento;
        this.ativo = ativo;

        enderecoDAO = new EnderecosDAO();

        adicionarCampos();
    }

    @Override
    public boolean equals(Object object) {
        boolean retorno = false;

        if (object instanceof Enderecos) {
            Enderecos other = (Enderecos) object;
            if (idEndereco == other.getIdEndereco()) {
                retorno = true;
            }
        }

        return retorno;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + (int) (this.idEndereco ^ (this.idEndereco >>> 32));
        hash = 73 * hash + (int) (this.codPessoa ^ (this.codPessoa >>> 32));
        hash = 73 * hash + (int) (this.codTipoEndereco ^ (this.codTipoEndereco >>> 32));
        hash = 73 * hash + (int) (this.codBairro ^ (this.codBairro >>> 32));
        hash = 73 * hash + Objects.hashCode(this.descricao);
        hash = 73 * hash + (int) (this.cep ^ (this.cep >>> 32));
        hash = 73 * hash + Objects.hashCode(this.complemento);
        hash = 73 * hash + (this.ativo ? 1 : 0);
        hash = 73 * hash + Objects.hashCode(this.enderecoDAO);
        return hash;
    }

    @Override
    public ObjetoDAO getObjetoDAO() {
        return enderecoDAO;
    }

    @Override
    public void adicionarCampos() {
        removerCampos();

        getObjetoDAO().addCampo(getDadosCodigo());
        getObjetoDAO().addCampo(new DadosDAO("cod_pessoa", "Cód. Pessoa",
                String.valueOf(codPessoa), DadosDAO.TIPO_LONG,
                DadosDAO.IS_IGUAL, DadosDAO.NAO_CHAVE));
        getObjetoDAO().addCampo(new DadosDAO("cod_tipo_endereco",
                "Cód. Tipo Ender.", String.valueOf(codTipoEndereco),
                DadosDAO.IS_IGUAL, DadosDAO.TIPO_LONG, DadosDAO.NAO_CHAVE));
        getObjetoDAO().addCampo(new DadosDAO("cod_bairro", "Cód. Bairro",
                String.valueOf(codBairro), DadosDAO.TIPO_LONG,
                DadosDAO.IS_IGUAL, DadosDAO.NAO_CHAVE));
        getObjetoDAO().addCampo(getDadosDescricao());
        getObjetoDAO().addCampo(new DadosDAO("cep", "CEP", String.valueOf(cep),
                DadosDAO.IS_IGUAL, DadosDAO.TIPO_LONG, DadosDAO.NAO_CHAVE));
        getObjetoDAO().addCampo(new DadosDAO("complemento", "Complemento",
                complemento, DadosDAO.TIPO_STRING, DadosDAO.IS_IGUAL, 
                DadosDAO.NAO_CHAVE));
        getObjetoDAO().addCampo(new DadosDAO("ativo", "Ativo",
                String.valueOf(isAtivo()), DadosDAO.TIPO_BOOLEAN, 
                DadosDAO.IS_IGUAL, DadosDAO.NAO_CHAVE));
    }

    /**
     * @return the dadosCodigo
     */
    @Override
    public DadosDAO getDadosCodigo() {
        return new DadosDAO("id_endereco", "Cód. Endereço",
                String.valueOf(idEndereco), DadosDAO.TIPO_LONG,
                DadosDAO.IS_IGUAL, DadosDAO.IS_CHAVE);
    }

    /**
     * @return the dadosDescricao
     */
    @Override
    public DadosDAO getDadosDescricao() {
        return new DadosDAO("descricao", "Descrição",
                String.valueOf(descricao), DadosDAO.TIPO_STRING,
                DadosDAO.IS_IGUAL, DadosDAO.NAO_CHAVE);
    }

    /**
     * @return the idEndereco
     */
    public long getIdEndereco() {
        return idEndereco;
    }

    /**
     * @param idEndereco the idEndereco to set
     */
    public void setIdEndereco(long idEndereco) {
        this.idEndereco = idEndereco;
    }

    /**
     * @return the codPessoa
     */
    public long getCodPessoa() {
        return codPessoa;
    }

    /**
     * @param codPessoa the codPessoa to set
     */
    public void setCodPessoa(long codPessoa) {
        this.codPessoa = codPessoa;
    }

    /**
     * @return the codTipoEndereco
     */
    public long getCodTipoEndereco() {
        return codTipoEndereco;
    }

    /**
     * @param codTipoEndereco the codTipoEndereco to set
     */
    public void setCodTipoEndereco(long codTipoEndereco) {
        this.codTipoEndereco = codTipoEndereco;
    }

    /**
     * @return the codBairro
     */
    public long getCodBairro() {
        return codBairro;
    }

    /**
     * @param codBairro the codBairro to set
     */
    public void setCodBairro(long codBairro) {
        this.codBairro = codBairro;
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
     * @return the cep
     */
    public long getCep() {
        return cep;
    }

    /**
     * @param cep the cep to set
     */
    public void setCep(long cep) {
        this.cep = cep;
    }

    /**
     * @return the complemento
     */
    public String getComplemento() {
        return complemento;
    }

    /**
     * @param complemento the complemento to set
     */
    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    /**
     * @return the enderecoDAO
     */
    public EnderecosDAO getEnderecoDAO() {
        return enderecoDAO;
    }

    /**
     * @param enderecoDAO the enderecoDAO to set
     */
    public void setEnderecoDAO(EnderecosDAO enderecoDAO) {
        this.enderecoDAO = enderecoDAO;
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
}
