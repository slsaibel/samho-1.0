/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.samho.negocio;

import com.samho.dao.DadosDAO;
import com.samho.dao.ObjetoDAO;
import com.samho.dao.ProntuariosSintomasApresentadosDAO;
import java.util.Objects;

/**
 * Classe de Objeto.
 *
 * @author Saibel, Sergio Luis
 * @since  date 02/05/2017
 *
 * @version  revision 001.20170502 date 02/05/2017 author Saibel, Sergio Luís reason
 * Instanciar um objeto com métodos e atributos necessários.
 */
public final class ProntuariosSintomasApresentados extends Objeto 
        implements InterfaceObj {

    // Declaração de atributos
    private long idProntuarioSintomaApresentado;
    private long codProntuario;
    private long codSintomaApresentado;
    private int diasComSintoma;

    private ProntuariosSintomasApresentadosDAO prontuarioSintomaApresentadoDAO;

    // Construtor padrão
    public ProntuariosSintomasApresentados() {
        idProntuarioSintomaApresentado = -1;
        codProntuario = -1;
        codSintomaApresentado = -1;
        diasComSintoma = 0;

        prontuarioSintomaApresentadoDAO = new ProntuariosSintomasApresentadosDAO();

        adicionarCampos();
    }

    // Construtor
    public ProntuariosSintomasApresentados(
            ProntuariosSintomasApresentados prontuarioSintomaApresentado) {
        idProntuarioSintomaApresentado = 
                prontuarioSintomaApresentado.getIdProntuarioSintomaApresentado();
        codProntuario = prontuarioSintomaApresentado.getCodProntuario();
        codSintomaApresentado = prontuarioSintomaApresentado.getCodSintomaApresentado();
        diasComSintoma = prontuarioSintomaApresentado.getDiasComSintoma();

        prontuarioSintomaApresentadoDAO = prontuarioSintomaApresentado.getProntuarioSintomaApresentadoDAO();

        adicionarCampos();
    }

    // Construtor
    public ProntuariosSintomasApresentados(long idProntuarioSintomaApresentado, 
            long codProntuario, long codSintomaApresentado, int diasComSintoma) {
        this.idProntuarioSintomaApresentado = idProntuarioSintomaApresentado;
        this.codProntuario = codProntuario;
        this.codSintomaApresentado = codSintomaApresentado;
        this.diasComSintoma = diasComSintoma;

        this.prontuarioSintomaApresentadoDAO = new ProntuariosSintomasApresentadosDAO();

        adicionarCampos();
    }

    @Override
    public boolean equals(Object object) {
        boolean retorno = false;

        if (object instanceof ProntuariosSintomasApresentados) {
            ProntuariosSintomasApresentados other = (ProntuariosSintomasApresentados) object;
            if (idProntuarioSintomaApresentado == other.getIdProntuarioSintomaApresentado()) {
                retorno = true;
            }
        }

        return retorno;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + (int) (this.idProntuarioSintomaApresentado ^ (this.idProntuarioSintomaApresentado >>> 32));
        hash = 29 * hash + (int) (this.codProntuario ^ (this.codProntuario >>> 32));
        hash = 29 * hash + (int) (this.codSintomaApresentado ^ (this.codSintomaApresentado >>> 32));
        hash = 29 * hash + this.diasComSintoma;
        hash = 29 * hash + Objects.hashCode(this.prontuarioSintomaApresentadoDAO);
        return hash;
    }

    @Override
    public ObjetoDAO getObjetoDAO() {
        return prontuarioSintomaApresentadoDAO;
    }

    @Override
    public void adicionarCampos() {
        removerCampos();

        getObjetoDAO().addCampo(getDadosCodigo());
        getObjetoDAO().addCampo(new DadosDAO("cod_prontuario", "Cód. Prontuario",
                String.valueOf(codProntuario), DadosDAO.TIPO_LONG, 
                DadosDAO.IS_IGUAL, DadosDAO.NAO_CHAVE));
        getObjetoDAO().addCampo(new DadosDAO("cod_sintoma_apresentado", "Cód. Sintoma",
                String.valueOf(codSintomaApresentado), DadosDAO.TIPO_LONG, 
                DadosDAO.IS_IGUAL, DadosDAO.NAO_CHAVE));
        getObjetoDAO().addCampo(new DadosDAO("dias_com_sintomas", 
                "Dias com Sintoma", String.valueOf(diasComSintoma), 
                DadosDAO.TIPO_INTEGER, DadosDAO.IS_IGUAL, DadosDAO.NAO_CHAVE));
    }

    /**
     * @return the dadosCodigo
     */
    @Override
    public DadosDAO getDadosCodigo() {
        return new DadosDAO("id_prontuario_sintoma_apresentado", "Cód. Pront. Sint.", 
                String.valueOf(idProntuarioSintomaApresentado),DadosDAO.TIPO_LONG, 
                DadosDAO.IS_IGUAL, DadosDAO.IS_CHAVE);
    }

    /**
     * @return the dadosDescricao
     */
    @Override
    public DadosDAO getDadosDescricao() {
        return new DadosDAO(
                prontuarioSintomaApresentadoDAO.getSQLSubstiturCampoDescricao(
                        idProntuarioSintomaApresentado), "Doença - Sintoma", "", 
                DadosDAO.TIPO_STRING, DadosDAO.IS_IGUAL, DadosDAO.NAO_CHAVE);
    }

    /**
     * @return the idProntuarioSintomaApresentado
     */
    public long getIdProntuarioSintomaApresentado() {
        return idProntuarioSintomaApresentado;
    }

    /**
     * @param idProntuarioSintomaApresentado the idProntuarioSintomaApresentado to set
     */
    public void setIdProntuarioSintomaApresentado(long idProntuarioSintomaApresentado) {
        this.idProntuarioSintomaApresentado = idProntuarioSintomaApresentado;
    }

    /**
     * @return the codProntuario
     */
    public long getCodProntuario() {
        return codProntuario;
    }

    /**
     * @param codProntuario the codProntuario to set
     */
    public void setCodProntuario(long codProntuario) {
        this.codProntuario = codProntuario;
    }

    /**
     * @return the codSintomaApresentado
     */
    public long getCodSintomaApresentado() {
        return codSintomaApresentado;
    }

    /**
     * @param codSintomaApresentado the codSintomaApresentado to set
     */
    public void setCodSintomaApresentado(long codSintomaApresentado) {
        this.codSintomaApresentado = codSintomaApresentado;
    }

    /**
     * @return the diasComSintoma
     */
    public int getDiasComSintoma() {
        return diasComSintoma;
    }

    /**
     * @param diasComSintoma the diasComSintoma to set
     */
    public void setDiasComSintoma(int diasComSintoma) {
        this.diasComSintoma = diasComSintoma;
    }

    /**
     * @return the prontuarioSintomaApresentadoDAO
     */
    public ProntuariosSintomasApresentadosDAO getProntuarioSintomaApresentadoDAO() {
        return prontuarioSintomaApresentadoDAO;
    }

    /**
     * @param prontuarioSintomaApresentadoDAO the prontuarioSintomaApresentadoDAO to set
     */
    public void setProntuarioSintomaApresentadoDAO(ProntuariosSintomasApresentadosDAO prontuarioSintomaApresentadoDAO) {
        this.prontuarioSintomaApresentadoDAO = prontuarioSintomaApresentadoDAO;
    }
}
