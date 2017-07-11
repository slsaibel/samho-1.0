/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.samho.negocio;

import com.samho.dao.DadosDAO;
import com.samho.dao.ObjetoDAO;
import com.samho.dao.SintomasApresentadosDAO;
import java.util.Objects;

/**
 * Classe de Objeto.
 *
 * @author Saibel, Sergio Luis
 * @since  date 22/04/2017
 *
 * @version  revision 001.201700422 date 22/04/2017 author Saibel, Sergio Luís reason
 * Instanciar um objeto com métodos e atributos necessários.
 */
public final class SintomasApresentados extends Objeto implements InterfaceObj {

    // Declaração de atributos
    private long idSintomaApresentado;
    private String descricao;
    private String observacoes;
    private boolean ativo;

    private SintomasApresentadosDAO sintomaApresentadoDAO;

    // Construtor padrão
    public SintomasApresentados() {
        idSintomaApresentado = -1;
        descricao = "";
        observacoes = "";
        ativo = true;

        sintomaApresentadoDAO = new SintomasApresentadosDAO();

        adicionarCampos();
    }

    // Construtor
    public SintomasApresentados(SintomasApresentados sintomaApresentado) {
        idSintomaApresentado = sintomaApresentado.getIdSintomaApresentado();
        descricao = sintomaApresentado.getDescricao();
        observacoes = sintomaApresentado.getObservacoes();
        ativo = sintomaApresentado.isAtivo();

        sintomaApresentadoDAO = sintomaApresentado.getSintomaApresentadoDAO();

        adicionarCampos();
    }

    // Construtor
    public SintomasApresentados(long idSintomaApresentado, String descricao, 
            String observacoes, boolean ativo) {
        this.idSintomaApresentado = idSintomaApresentado;
        this.descricao = descricao;
        this.observacoes = observacoes;
        this.ativo = ativo;

        this.sintomaApresentadoDAO = new SintomasApresentadosDAO();

        adicionarCampos();
    }

    @Override
    public boolean equals(Object object) {
        boolean retorno = false;

        if (object instanceof SintomasApresentados) {
            SintomasApresentados other = (SintomasApresentados) object;
            if (idSintomaApresentado == other.getIdSintomaApresentado()) {
                retorno = true;
            }
        }

        return retorno;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + (int) (this.idSintomaApresentado ^ (this.idSintomaApresentado >>> 32));
        hash = 97 * hash + Objects.hashCode(this.descricao);
        hash = 97 * hash + Objects.hashCode(this.observacoes);
        hash = 97 * hash + (this.ativo ? 1 : 0);
        hash = 97 * hash + Objects.hashCode(this.sintomaApresentadoDAO);
        return hash;
    }

    @Override
    public ObjetoDAO getObjetoDAO() {
        return sintomaApresentadoDAO;
    }

    @Override
    public void adicionarCampos() {
        removerCampos();

        getObjetoDAO().addCampo(getDadosCodigo());
        getObjetoDAO().addCampo(getDadosDescricao());
        getObjetoDAO().addCampo(new DadosDAO("observacoes", "Observação",
                observacoes, DadosDAO.TIPO_STRING, DadosDAO.IS_IGUAL, 
                DadosDAO.NAO_CHAVE));
        getObjetoDAO().addCampo(new DadosDAO("ativo", "Ativo",
                String.valueOf(ativo), DadosDAO.TIPO_BOOLEAN, DadosDAO.IS_IGUAL, 
                DadosDAO.NAO_CHAVE));
    }

    /**
     * @return the dadosCodigo
     */
    @Override
    public DadosDAO getDadosCodigo() {
        return new DadosDAO("id_sintoma_apresentado", "Cód. Sintoma",
                String.valueOf(idSintomaApresentado), DadosDAO.TIPO_LONG, 
                DadosDAO.IS_IGUAL, DadosDAO.IS_CHAVE);
    }

    /**
     * @return the dadosDescricao
     */
    @Override
    public DadosDAO getDadosDescricao() {
        return new DadosDAO("descricao", "Descrição", descricao,
                DadosDAO.TIPO_STRING, DadosDAO.IS_IGUAL, DadosDAO.NAO_CHAVE);
    }

    /**
     * @return the idSintomaApresentado
     */
    public long getIdSintomaApresentado() {
        return idSintomaApresentado;
    }

    /**
     * @param idSintomaApresentado the idSintomaApresentado to set
     */
    public void setIdSintomaApresentado(long idSintomaApresentado) {
        this.idSintomaApresentado = idSintomaApresentado;
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
     * @return the observacoes
     */
    public String getObservacoes() {
        return observacoes;
    }

    /**
     * @param observacoes the observacoes to set
     */
    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
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
     * @return the sintomaApresentadoDAO
     */
    public SintomasApresentadosDAO getSintomaApresentadoDAO() {
        return sintomaApresentadoDAO;
    }

    /**
     * @param sintomaApresentadoDAO the sintomaApresentadoDAO to set
     */
    public void setSintomaApresentadoDAO(SintomasApresentadosDAO sintomaApresentadoDAO) {
        this.sintomaApresentadoDAO = sintomaApresentadoDAO;
    }
}
