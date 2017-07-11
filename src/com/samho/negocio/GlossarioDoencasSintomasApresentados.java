/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.samho.negocio;

import com.samho.dao.DadosDAO;
import com.samho.dao.ObjetoDAO;
import com.samho.dao.GlossarioDoencasSintomasApresentadosDAO;
import java.util.Objects;

/**
 * Classe de Objeto.
 *
 * @author Saibel, Sergio Luis
 * @since  date 03/05/2017
 *
 * @version  revision 001.20170503 date 03/05/2017 author Saibel, Sergio Luís reason
 * Instanciar um objeto com métodos e atributos necessários.
 */
public final class GlossarioDoencasSintomasApresentados extends Objeto 
        implements InterfaceObj {

    // Declaração de atributos
    private long idGlossarioDoencaSintomaApresentado;
    private long codGlossarioDoenca;
    private long codSintomaApresentado;
    private int diasComSintoma;

    private GlossarioDoencasSintomasApresentadosDAO glossarioDoencaSintomaApresentadoDAO;

    // Construtor padrão
    public GlossarioDoencasSintomasApresentados() {
        idGlossarioDoencaSintomaApresentado = -1;
        codGlossarioDoenca = -1;
        codSintomaApresentado = -1;
        diasComSintoma = 0;

        glossarioDoencaSintomaApresentadoDAO = 
                new GlossarioDoencasSintomasApresentadosDAO();

        adicionarCampos();
    }

    // Construtor
    public GlossarioDoencasSintomasApresentados(
            GlossarioDoencasSintomasApresentados glossarioDoencaSintomaApresentado) {
        idGlossarioDoencaSintomaApresentado = 
                glossarioDoencaSintomaApresentado.getIdGlossarioDoencaSintomaApresentado();
        codGlossarioDoenca = glossarioDoencaSintomaApresentado.getCodGlossarioDoenca();
        codSintomaApresentado = glossarioDoencaSintomaApresentado.getCodSintomaApresentado();
        diasComSintoma = glossarioDoencaSintomaApresentado.getDiasComSintoma();

        glossarioDoencaSintomaApresentadoDAO = 
                glossarioDoencaSintomaApresentado.getGlossarioDoencaSintomaApresentadoDAO();

        adicionarCampos();
    }

    // Construtor
    public GlossarioDoencasSintomasApresentados(long idGlossarioDoencaSintomaApresentado, 
            long codGlossarioDoenca, long codSintomaApresentado, int diasComSintoma) {
        this.idGlossarioDoencaSintomaApresentado = idGlossarioDoencaSintomaApresentado;
        this.codGlossarioDoenca = codGlossarioDoenca;
        this.codSintomaApresentado = codSintomaApresentado;
        this.diasComSintoma = diasComSintoma;

        this.glossarioDoencaSintomaApresentadoDAO = 
                new GlossarioDoencasSintomasApresentadosDAO();

        adicionarCampos();
    }

    @Override
    public boolean equals(Object object) {
        boolean retorno = false;

        if (object instanceof GlossarioDoencasSintomasApresentados) {
            GlossarioDoencasSintomasApresentados other = (GlossarioDoencasSintomasApresentados) object;
            if (idGlossarioDoencaSintomaApresentado == other.getIdGlossarioDoencaSintomaApresentado()) {
                retorno = true;
            }
        }

        return retorno;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + (int) (this.idGlossarioDoencaSintomaApresentado ^ (this.idGlossarioDoencaSintomaApresentado >>> 32));
        hash = 67 * hash + (int) (this.codGlossarioDoenca ^ (this.codGlossarioDoenca >>> 32));
        hash = 67 * hash + (int) (this.codSintomaApresentado ^ (this.codSintomaApresentado >>> 32));
        hash = 67 * hash + this.diasComSintoma;
        hash = 67 * hash + Objects.hashCode(this.glossarioDoencaSintomaApresentadoDAO);
        return hash;
    }

    @Override
    public ObjetoDAO getObjetoDAO() {
        return glossarioDoencaSintomaApresentadoDAO;
    }

    @Override
    public void adicionarCampos() {
        removerCampos();

        getObjetoDAO().addCampo(getDadosCodigo());
        getObjetoDAO().addCampo(new DadosDAO("cod_glossario_doenca", "Cód. Glossario",
                String.valueOf(codGlossarioDoenca), DadosDAO.TIPO_LONG, 
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
        return new DadosDAO("id_glossario_doenca_sintoma_apresentado", "Cód. Gloss. Sint.", 
                String.valueOf(idGlossarioDoencaSintomaApresentado),DadosDAO.TIPO_LONG, 
                DadosDAO.IS_IGUAL, DadosDAO.IS_CHAVE);
    }

    /**
     * @return the dadosDescricao
     */
    @Override
    public DadosDAO getDadosDescricao() {
        return new DadosDAO(
                glossarioDoencaSintomaApresentadoDAO.getSQLSubstiturCampoDescricao(
                        idGlossarioDoencaSintomaApresentado), "Doença - Sintoma", "", 
                DadosDAO.TIPO_STRING, DadosDAO.IS_IGUAL, DadosDAO.NAO_CHAVE);
    }

    /**
     * @return the idGlossarioDoencaSintomaApresentado
     */
    public long getIdGlossarioDoencaSintomaApresentado() {
        return idGlossarioDoencaSintomaApresentado;
    }

    /**
     * @param idGlossarioDoencaSintomaApresentado the idGlossarioDoencaSintomaApresentado to set
     */
    public void setIdGlossarioDoencaSintomaApresentado(long idGlossarioDoencaSintomaApresentado) {
        this.idGlossarioDoencaSintomaApresentado = idGlossarioDoencaSintomaApresentado;
    }

    /**
     * @return the codGlossarioDoenca
     */
    public long getCodGlossarioDoenca() {
        return codGlossarioDoenca;
    }

    /**
     * @param codGlossarioDoenca the codGlossarioDoenca to set
     */
    public void setCodGlossarioDoenca(long codGlossarioDoenca) {
        this.codGlossarioDoenca = codGlossarioDoenca;
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
     * @return the glossarioDoencaSintomaApresentadoDAO
     */
    public GlossarioDoencasSintomasApresentadosDAO getGlossarioDoencaSintomaApresentadoDAO() {
        return glossarioDoencaSintomaApresentadoDAO;
    }

    /**
     * @param glossarioDoencaSintomaApresentadoDAO the glossarioDoencaSintomaApresentadoDAO to set
     */
    public void setGlossarioDoencaSintomaApresentadoDAO(
            GlossarioDoencasSintomasApresentadosDAO glossarioDoencaSintomaApresentadoDAO) {
        this.glossarioDoencaSintomaApresentadoDAO = glossarioDoencaSintomaApresentadoDAO;
    }
}
