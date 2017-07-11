/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.samho.negocio;

import com.samho.dao.GlossarioDoencasDAO;
import com.samho.dao.DadosDAO;
import com.samho.dao.ObjetoDAO;
import com.samho.util.Formatacao;
import java.util.Calendar;
import java.util.Objects;

/**
 * Classe de Funcionários.
 *
 * @author Sergio
 * @since  date 01/05/2017
 *
 * @version  revision 001.20170501 date 01/05/2017 author Saibel, Sergio Luís reason
 * Instanciar um objeto com métodos e atributos necessários.
 */
public final class GlossarioDoencas extends Objeto implements InterfaceObj {

    // Declaração de atributos
    private long idGlossarioDoenca;
    private long codProfissionalCadastro;
    private String CID10;
    private String descricao;
    private int diasTratamento;
    private Calendar dataRegistro;
    private String observacoes;
    private long revisao;

    private GlossarioDoencasDAO glossarioDoencaDAO;

    // Construtor padrão
    public GlossarioDoencas() {
        idGlossarioDoenca = -1;
        codProfissionalCadastro = -1;
        CID10 = "";
        descricao = "";
        diasTratamento = 999999;
        dataRegistro = Calendar.getInstance();
        observacoes = "";
        revisao = 1;

        glossarioDoencaDAO = new GlossarioDoencasDAO();

        adicionarCampos();
    }

    // Construtor
    public GlossarioDoencas(GlossarioDoencas glossarioDoenca) {
        idGlossarioDoenca = glossarioDoenca.getIdGlossarioDoenca();
        codProfissionalCadastro = glossarioDoenca.getCodProfissionalCadastro();
        CID10 = glossarioDoenca.getCID10();
        descricao = glossarioDoenca.getDescricao();
        diasTratamento = glossarioDoenca.getDiasTratamento();
        dataRegistro = glossarioDoenca.getDataRegistro();
        observacoes = glossarioDoenca.getObservacoes();
        revisao = glossarioDoenca.getRevisao();

        glossarioDoencaDAO = glossarioDoenca.getGlossarioDoencaDAO();

        adicionarCampos();
    }

    // Construtor
    public GlossarioDoencas(long idGlossarioDoenca, long codProfissionalCadastro,
            String CID10, String descricao, int diasTratamento, 
            Calendar dataRegistro, String observacoes, long revisao) {
        this.idGlossarioDoenca = idGlossarioDoenca;
        this.codProfissionalCadastro = codProfissionalCadastro;
        this.CID10 = CID10;
        this.descricao = descricao;
        this.diasTratamento = diasTratamento;
        this.dataRegistro = dataRegistro;
        this.observacoes = observacoes;
        this.revisao = revisao;

        glossarioDoencaDAO = new GlossarioDoencasDAO();

        adicionarCampos();
    }

    @Override
    public boolean equals(Object object) {
        boolean retorno = false;

        if (object instanceof GlossarioDoencas) {
            GlossarioDoencas other = (GlossarioDoencas) object;
            if (idGlossarioDoenca == other.getIdGlossarioDoenca()) {
                retorno = true;
            }
        }

        return retorno;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + (int) (this.idGlossarioDoenca ^ (this.idGlossarioDoenca >>> 32));
        hash = 83 * hash + (int) (this.codProfissionalCadastro ^ (this.codProfissionalCadastro >>> 32));
        hash = 83 * hash + Objects.hashCode(this.CID10);
        hash = 83 * hash + Objects.hashCode(this.descricao);
        hash = 83 * hash + this.diasTratamento;
        hash = 83 * hash + Objects.hashCode(this.dataRegistro);
        hash = 83 * hash + Objects.hashCode(this.observacoes);
        hash = 83 * hash + (int) (this.revisao ^ (this.revisao >>> 32));
        hash = 83 * hash + Objects.hashCode(this.glossarioDoencaDAO);
        return hash;
    }

    @Override
    public ObjetoDAO getObjetoDAO() {
        return glossarioDoencaDAO;
    }

    @Override
    public void adicionarCampos() {
        removerCampos();

        getObjetoDAO().addCampo(getDadosCodigo());
        getObjetoDAO().addCampo(new DadosDAO("cod_profissional_cadastro", 
                "Cód. Profissional", String.valueOf(codProfissionalCadastro), 
                DadosDAO.TIPO_LONG, DadosDAO.IS_IGUAL, DadosDAO.NAO_CHAVE));
        getObjetoDAO().addCampo(new DadosDAO("CID_10", "CID-10",
                String.valueOf(CID10), DadosDAO.TIPO_STRING, DadosDAO.IS_IGUAL, 
                DadosDAO.NAO_CHAVE));
        getObjetoDAO().addCampo(getDadosDescricao());
        getObjetoDAO().addCampo(new DadosDAO("dias_tratamento", 
                "Dias Tratamento", String.valueOf(diasTratamento), 
                DadosDAO.TIPO_INTEGER, DadosDAO.IS_IGUAL, DadosDAO.NAO_CHAVE));
        getObjetoDAO().addCampo(new DadosDAO("data_registro", "Data Registro",
                Formatacao.ajustaDataAMD(dataRegistro), DadosDAO.TIPO_DATE,
                DadosDAO.IS_IGUAL, DadosDAO.NAO_CHAVE));
        getObjetoDAO().addCampo(new DadosDAO("observacoes", "Observações",
                String.valueOf(observacoes), DadosDAO.TIPO_STRING,
                DadosDAO.IS_IGUAL, DadosDAO.NAO_CHAVE));
        getObjetoDAO().addCampo(new DadosDAO("revisao", "Revisão",
                String.valueOf(revisao), DadosDAO.TIPO_INTEGER, 
                DadosDAO.IS_IGUAL, DadosDAO.NAO_CHAVE));
        
        
        // Adicionando where de restrição ao administrador id = 0
        getObjetoDAO().addWhere(new DadosDAO(getObjetoDAO().getCampoID(), "", 
                "0", DadosDAO.TIPO_LONG, DadosDAO.IS_DIFERENTE, DadosDAO.IS_CHAVE));
    }

    /**
     * @return the dadosCodigo
     */
    @Override
    public DadosDAO getDadosCodigo() {
        return new DadosDAO("id_glossario_doenca", "Cód. Glossario",
                String.valueOf(idGlossarioDoenca), DadosDAO.TIPO_LONG,
                DadosDAO.IS_IGUAL, DadosDAO.IS_CHAVE);
    }

    /**
     * @return the dadosDescricao
     */
    @Override
    public DadosDAO getDadosDescricao() {
        return new DadosDAO("descricao", "Doença", descricao, DadosDAO.TIPO_STRING, 
                DadosDAO.IS_IGUAL, DadosDAO.NAO_CHAVE);
    }

    /**
     * @return the idGlossarioDoenca
     */
    public long getIdGlossarioDoenca() {
        return idGlossarioDoenca;
    }

    /**
     * @param idGlossarioDoenca the idGlossarioDoenca to set
     */
    public void setIdGlossarioDoenca(long idGlossarioDoenca) {
        this.idGlossarioDoenca = idGlossarioDoenca;
    }

    /**
     * @return the codProfissionalCadastro
     */
    public long getCodProfissionalCadastro() {
        return codProfissionalCadastro;
    }

    /**
     * @param codProfissionalCadastro the codProfissionalCadastro to set
     */
    public void setCodProfissionalCadastro(long codProfissionalCadastro) {
        this.codProfissionalCadastro = codProfissionalCadastro;
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
     * @return the diasTratamento
     */
    public int getDiasTratamento() {
        return diasTratamento;
    }

    /**
     * @param diasTratamento the diasTratamento to set
     */
    public void setDiasTratamento(int diasTratamento) {
        this.diasTratamento = diasTratamento;
    }

    /**
     * @return the dataRegistro
     */
    public Calendar getDataRegistro() {
        return dataRegistro;
    }

    /**
     * @param dataRegistro the dataRegistro to set
     */
    public void setDataRegistro(Calendar dataRegistro) {
        this.dataRegistro = dataRegistro;
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
     * @return the revisao
     */
    public long getRevisao() {
        return revisao;
    }

    /**
     * @param revisao the revisao to set
     */
    public void setRevisao(long revisao) {
        this.revisao = revisao;
    }

    /**
     * @return the glossarioDoencaDAO
     */
    public GlossarioDoencasDAO getGlossarioDoencaDAO() {
        return glossarioDoencaDAO;
    }

    /**
     * @param glossarioDoencaDAO the glossarioDoencaDAO to set
     */
    public void setGlossarioDoencaDAO(GlossarioDoencasDAO glossarioDoencaDAO) {
        this.glossarioDoencaDAO = glossarioDoencaDAO;
    }

    /**
     * @return the CID10
     */
    public String getCID10() {
        return CID10;
    }

    /**
     * @param CID10 the CID10 to set
     */
    public void setCID10(String CID10) {
        this.CID10 = CID10;
    }
}
