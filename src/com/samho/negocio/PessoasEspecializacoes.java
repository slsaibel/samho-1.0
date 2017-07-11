/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.samho.negocio;

import com.samho.dao.PessoasEspecializacoesDAO;
import com.samho.dao.DadosDAO;
import com.samho.dao.ObjetoDAO;
import com.samho.util.Formatacao;
import java.util.Calendar;
import java.util.Objects;

/**
 * Classe de Funcionários.
 *
 * @author Sergio
 * @since  date 18/04/2014
 *
 * @version  revision 001.20140418 date 18/04/2014 author Saibel, Sergio Luís reason
 * Instanciar um objeto com métodos e atributos necessários.
 */
public final class PessoasEspecializacoes extends Objeto implements InterfaceObj {

    // Declaração de atributos
    private long idPessoaEspecializacao;
    private long codPessoa;
    private long codEspecializacao;
    private String instituicao;
    private int numRegistro;
    private Calendar dataConclusao;
    private boolean ativo;
    private String observacoes;


    private PessoasEspecializacoesDAO pessoaEspecializacaoDAO;

    // Construtor padrão
    public PessoasEspecializacoes() {
        idPessoaEspecializacao = -1;
        codPessoa = -1;
        codEspecializacao = -1;
        instituicao = "";
        numRegistro = -1;
        dataConclusao = null;
        ativo = true;
        observacoes = "";

        pessoaEspecializacaoDAO = new PessoasEspecializacoesDAO();

        adicionarCampos();
    }

    // Construtor
    public PessoasEspecializacoes(
            PessoasEspecializacoes profissionalEspecializacao) {
        idPessoaEspecializacao = 
                profissionalEspecializacao.getIdPessoaEspecializacao();
        codPessoa = profissionalEspecializacao.getCodPessoa();
        codEspecializacao = profissionalEspecializacao.getCodEspecializacao();
        instituicao = profissionalEspecializacao.getInstituicao();
        numRegistro = profissionalEspecializacao.getNumRegistro();
        dataConclusao = profissionalEspecializacao.getDataConclusao();
        ativo = profissionalEspecializacao.isAtivo();
        observacoes = profissionalEspecializacao.getObservacoes();

        pessoaEspecializacaoDAO = 
                profissionalEspecializacao.getPessoaEspecializacaoDAO();

        adicionarCampos();
    }

    // Construtor
    public PessoasEspecializacoes(long idProfissionalEspecializacao, 
            long codProfissional, long codEspecializacao, String instituicao,
            int numRegistro, Calendar dataConclusao, boolean ativo, 
            String observacoes) {
        this.idPessoaEspecializacao = idProfissionalEspecializacao;
        this.codPessoa = codProfissional;
        this.codEspecializacao = codEspecializacao;
        this.instituicao = instituicao;
        this.numRegistro = numRegistro;
        this.dataConclusao = dataConclusao;
        this.ativo = ativo;
        this.observacoes = observacoes;

        this.pessoaEspecializacaoDAO = new PessoasEspecializacoesDAO();

        adicionarCampos();
    }

    @Override
    public boolean equals(Object object) {
        boolean retorno = false;

        if (object instanceof PessoasEspecializacoes) {
            PessoasEspecializacoes other = (PessoasEspecializacoes) object;
            if (idPessoaEspecializacao == other.getIdPessoaEspecializacao()) {
                retorno = true;
            }
        }
        
        return retorno;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 23 * hash + (int) (this.idPessoaEspecializacao ^ (this.idPessoaEspecializacao >>> 32));
        hash = 23 * hash + (int) (this.codPessoa ^ (this.codPessoa >>> 32));
        hash = 23 * hash + (int) (this.codEspecializacao ^ (this.codEspecializacao >>> 32));
        hash = 23 * hash + Objects.hashCode(this.instituicao);
        hash = 23 * hash + this.numRegistro;
        hash = 23 * hash + Objects.hashCode(this.dataConclusao);
        hash = 23 * hash + (this.ativo ? 1 : 0);
        hash = 23 * hash + Objects.hashCode(this.observacoes);
        hash = 23 * hash + Objects.hashCode(this.pessoaEspecializacaoDAO);
        return hash;
    }

    @Override
    public ObjetoDAO getObjetoDAO() {
        return pessoaEspecializacaoDAO;
    }

    @Override
    public void adicionarCampos() {
        removerCampos();

        getObjetoDAO().addCampo(getDadosCodigo());
        getObjetoDAO().addCampo(new DadosDAO("cod_pessoa", "Cód. Pessoa", 
                String.valueOf(codPessoa), DadosDAO.TIPO_LONG, DadosDAO.IS_IGUAL, 
                DadosDAO.NAO_CHAVE));
        getObjetoDAO().addCampo(new DadosDAO("cod_especializacao", 
                "Cód. Especialização", String.valueOf(codEspecializacao), 
                DadosDAO.TIPO_LONG, DadosDAO.IS_IGUAL, DadosDAO.NAO_CHAVE));
        getObjetoDAO().addCampo(new DadosDAO("instituicao", "Instituição",
                String.valueOf(instituicao), DadosDAO.TIPO_STRING,
                DadosDAO.IS_IGUAL, DadosDAO.NAO_CHAVE));
        getObjetoDAO().addCampo(new DadosDAO("num_registro", "Núm. Registro",
                String.valueOf(numRegistro), DadosDAO.TIPO_INTEGER,
                DadosDAO.IS_IGUAL, DadosDAO.NAO_CHAVE));
        getObjetoDAO().addCampo(new DadosDAO("data_conclusao", "Data Conclusão",
                Formatacao.ajustaDataAMD(dataConclusao), DadosDAO.TIPO_DATE,
                DadosDAO.IS_IGUAL, DadosDAO.NAO_CHAVE));
        getObjetoDAO().addCampo(new DadosDAO("ativo", "Ativo",
                String.valueOf(ativo), DadosDAO.TIPO_BOOLEAN, DadosDAO.IS_IGUAL,
                DadosDAO.NAO_CHAVE));
        getObjetoDAO().addCampo(new DadosDAO("observacoes", "Observações",
                String.valueOf(observacoes), DadosDAO.TIPO_STRING,
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
        return new DadosDAO("id_pessoa_especializacao", "Cód. Pessoa Esp.",
                String.valueOf(idPessoaEspecializacao), DadosDAO.TIPO_LONG,
                DadosDAO.IS_IGUAL, DadosDAO.IS_CHAVE);
    }

    /**
     * @return the dadosDescricao
     */
    @Override
    public DadosDAO getDadosDescricao() {
        return new DadosDAO(
                pessoaEspecializacaoDAO.getSQLSubstiturCampoDescricao(codEspecializacao),
                "Pessoa", "", DadosDAO.TIPO_STRING, DadosDAO.IS_IGUAL, 
                DadosDAO.NAO_CHAVE);
    }

    /**
     * @return the idPessoaEspecializacao
     */
    public long getIdPessoaEspecializacao() {
        return idPessoaEspecializacao;
    }

    /**
     * @param idPessoaEspecializacao the idPessoaEspecializacao to set
     */
    public void setIdPessoaEspecializacao(long idPessoaEspecializacao) {
        this.idPessoaEspecializacao = idPessoaEspecializacao;
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
     * @return the codEspecializacao
     */
    public long getCodEspecializacao() {
        return codEspecializacao;
    }

    /**
     * @param codEspecializacao the codEspecializacao to set
     */
    public void setCodEspecializacao(long codEspecializacao) {
        this.codEspecializacao = codEspecializacao;
    }

    /**
     * @return the instituicao
     */
    public String getInstituicao() {
        return instituicao;
    }

    /**
     * @param instituicao the instituicao to set
     */
    public void setInstituicao(String instituicao) {
        this.instituicao = instituicao;
    }

    /**
     * @return the numRegistro
     */
    public int getNumRegistro() {
        return numRegistro;
    }

    /**
     * @param numRegistro the numRegistro to set
     */
    public void setNumRegistro(int numRegistro) {
        this.numRegistro = numRegistro;
    }

    /**
     * @return the dataConclusao
     */
    public Calendar getDataConclusao() {
        return dataConclusao;
    }

    /**
     * @param dataConclusao the dataConclusao to set
     */
    public void setDataConclusao(Calendar dataConclusao) {
        this.dataConclusao = dataConclusao;
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
     * @return the pessoaEspecializacaoDAO
     */
    public PessoasEspecializacoesDAO getPessoaEspecializacaoDAO() {
        return pessoaEspecializacaoDAO;
    }

    /**
     * @param pessoaEspecializacaoDAO the pessoaEspecializacaoDAO to set
     */
    public void setPessoaEspecializacaoDAO(PessoasEspecializacoesDAO pessoaEspecializacaoDAO) {
        this.pessoaEspecializacaoDAO = pessoaEspecializacaoDAO;
    }
}
