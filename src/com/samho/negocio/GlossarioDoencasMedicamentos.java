/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.samho.negocio;

import com.samho.dao.DadosDAO;
import com.samho.dao.ObjetoDAO;
import com.samho.dao.GlossarioDoencasMedicamentosDAO;
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
public final class GlossarioDoencasMedicamentos extends Objeto 
        implements InterfaceObj {

    // Declaração de atributos
    private long idGlossarioDoencasMedicamento;
    private long codGlossarioDoenca;
    private long codMedicamento;
    private int diasComTratamento;
    private int quantidade;
    private String parecerProfissional;

    private GlossarioDoencasMedicamentosDAO glossarioDoencaMedicamentoDAO;

    // Construtor padrão
    public GlossarioDoencasMedicamentos() {
        idGlossarioDoencasMedicamento = -1;
        codGlossarioDoenca = -1;
        codMedicamento = -1;
        diasComTratamento = 0;
        quantidade = 0;
        parecerProfissional = "";

        glossarioDoencaMedicamentoDAO = new GlossarioDoencasMedicamentosDAO();

        adicionarCampos();
    }

    // Construtor
    public GlossarioDoencasMedicamentos(
            GlossarioDoencasMedicamentos glossarioDoencaMedicamento) {
        idGlossarioDoencasMedicamento = 
                glossarioDoencaMedicamento.getIdGlossarioDoencasMedicamento();
        codGlossarioDoenca = glossarioDoencaMedicamento.getCodGlossarioDoenca();
        codMedicamento = glossarioDoencaMedicamento.getCodMedicamento();
        diasComTratamento = glossarioDoencaMedicamento.getDiasComTratamento();
        quantidade = glossarioDoencaMedicamento.getQuantidade();
        parecerProfissional = glossarioDoencaMedicamento.getParecerProfissional();

        glossarioDoencaMedicamentoDAO = glossarioDoencaMedicamento.getGlossarioDoencaMedicamentoDAO();

        adicionarCampos();
    }

    // Construtor
    public GlossarioDoencasMedicamentos(long idGlossarioDoencasMedicamento, 
            long codGlossarioDoenca, long codMedicamento, int diasComTratamento,
            int quantidade, String parecerProfissional) {
        this.idGlossarioDoencasMedicamento = idGlossarioDoencasMedicamento;
        this.codGlossarioDoenca = codGlossarioDoenca;
        this.codMedicamento = codMedicamento;
        this.diasComTratamento = diasComTratamento;
        this.quantidade = quantidade;
        this.parecerProfissional = parecerProfissional;

        this.glossarioDoencaMedicamentoDAO = new GlossarioDoencasMedicamentosDAO();

        adicionarCampos();
    }

    @Override
    public boolean equals(Object object) {
        boolean retorno = false;

        if (object instanceof GlossarioDoencasMedicamentos) {
            GlossarioDoencasMedicamentos other = (GlossarioDoencasMedicamentos) object;
            if (idGlossarioDoencasMedicamento == other.getIdGlossarioDoencasMedicamento()) {
                retorno = true;
            }
        }

        return retorno;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + (int) (this.idGlossarioDoencasMedicamento ^ (this.idGlossarioDoencasMedicamento >>> 32));
        hash = 53 * hash + (int) (this.codGlossarioDoenca ^ (this.codGlossarioDoenca >>> 32));
        hash = 53 * hash + (int) (this.codMedicamento ^ (this.codMedicamento >>> 32));
        hash = 53 * hash + this.diasComTratamento;
        hash = 53 * hash + this.quantidade;
        hash = 53 * hash + Objects.hashCode(this.parecerProfissional);
        hash = 53 * hash + Objects.hashCode(this.glossarioDoencaMedicamentoDAO);
        return hash;
    }

    @Override
    public ObjetoDAO getObjetoDAO() {
        return glossarioDoencaMedicamentoDAO;
    }

    @Override
    public void adicionarCampos() {
        removerCampos();

        getObjetoDAO().addCampo(getDadosCodigo());
        getObjetoDAO().addCampo(new DadosDAO("cod_glossario_doenca", "Cód. Glossario",
                String.valueOf(codGlossarioDoenca), DadosDAO.TIPO_LONG, 
                DadosDAO.IS_IGUAL, DadosDAO.NAO_CHAVE));
        getObjetoDAO().addCampo(new DadosDAO("cod_medicamento", "Cód. Medicamento",
                String.valueOf(codMedicamento), DadosDAO.TIPO_LONG, 
                DadosDAO.IS_IGUAL, DadosDAO.NAO_CHAVE));
        getObjetoDAO().addCampo(new DadosDAO("dias_com_tratamento", 
                "Dias com Tratamento", String.valueOf(diasComTratamento), 
                DadosDAO.TIPO_INTEGER, DadosDAO.IS_IGUAL, DadosDAO.NAO_CHAVE));
        getObjetoDAO().addCampo(new DadosDAO("quantidade", "Quantidade", 
                String.valueOf(quantidade), DadosDAO.TIPO_INTEGER, 
                DadosDAO.IS_IGUAL, DadosDAO.NAO_CHAVE));
        getObjetoDAO().addCampo(new DadosDAO("parecer_profissional", 
                "Parecer Profissional", String.valueOf(parecerProfissional), 
                DadosDAO.TIPO_STRING, DadosDAO.IS_IGUAL, DadosDAO.NAO_CHAVE));
    }

    /**
     * @return the dadosCodigo
     */
    @Override
    public DadosDAO getDadosCodigo() {
        return new DadosDAO("id_glossario_doenca_medicamento", "Cód. Gloss. Med.", 
                String.valueOf(idGlossarioDoencasMedicamento),DadosDAO.TIPO_LONG, 
                DadosDAO.IS_IGUAL, DadosDAO.IS_CHAVE);
    }

    /**
     * @return the dadosDescricao
     */
    @Override
    public DadosDAO getDadosDescricao() {
                return new DadosDAO(
                        glossarioDoencaMedicamentoDAO.getSQLSubstiturCampoDescricao(
                                idGlossarioDoencasMedicamento), "Doença - Medicamento", "", 
                DadosDAO.TIPO_STRING, DadosDAO.IS_IGUAL, DadosDAO.NAO_CHAVE);
    }

    /**
     * @return the idGlossarioDoencasMedicamento
     */
    public long getIdGlossarioDoencasMedicamento() {
        return idGlossarioDoencasMedicamento;
    }

    /**
     * @param idGlossarioDoencasMedicamento the idGlossarioDoencasMedicamento to set
     */
    public void setIdGlossarioDoencasMedicamento(long idGlossarioDoencasMedicamento) {
        this.idGlossarioDoencasMedicamento = idGlossarioDoencasMedicamento;
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
     * @return the codMedicamento
     */
    public long getCodMedicamento() {
        return codMedicamento;
    }

    /**
     * @param codMedicamento the codMedicamento to set
     */
    public void setCodMedicamento(long codMedicamento) {
        this.codMedicamento = codMedicamento;
    }

    /**
     * @return the diasComTratamento
     */
    public int getDiasComTratamento() {
        return diasComTratamento;
    }

    /**
     * @param diasComTratamento the diasComTratamento to set
     */
    public void setDiasComTratamento(int diasComTratamento) {
        this.diasComTratamento = diasComTratamento;
    }

    /**
     * @return the quantidade
     */
    public int getQuantidade() {
        return quantidade;
    }

    /**
     * @param quantidade the quantidade to set
     */
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    /**
     * @return the parecerProfissional
     */
    public String getParecerProfissional() {
        return parecerProfissional;
    }

    /**
     * @param parecerProfissional the parecerProfissional to set
     */
    public void setParecerProfissional(String parecerProfissional) {
        this.parecerProfissional = parecerProfissional;
    }

    /**
     * @return the glossarioDoencaMedicamentoDAO
     */
    public GlossarioDoencasMedicamentosDAO getGlossarioDoencaMedicamentoDAO() {
        return glossarioDoencaMedicamentoDAO;
    }

    /**
     * @param glossarioDoencaMedicamentoDAO the glossarioDoencaMedicamentoDAO to set
     */
    public void setGlossarioDoencaMedicamentoDAO(GlossarioDoencasMedicamentosDAO glossarioDoencaMedicamentoDAO) {
        this.glossarioDoencaMedicamentoDAO = glossarioDoencaMedicamentoDAO;
    }
}
