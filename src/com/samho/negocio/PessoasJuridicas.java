/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.samho.negocio;

import com.samho.dao.DadosDAO;
import com.samho.dao.ObjetoDAO;
import com.samho.dao.PessoasJuridicasDAO;
import com.samho.util.Formatacao;
import java.util.Calendar;
import java.util.Objects;

/**
 * Classe de Objeto.
 *
 * @author Saibel, Sergio Luis
 * @since  date 31/03/2017
 *
 * @version  revision 001.20170331 date 31/03/2017 author Saibel, Sergio Luís reason
 * Instanciar um objeto com métodos e atributos necessários.
 *
 * Esta classe é dependente da classe Pessoas
 */
public final class PessoasJuridicas extends Objeto implements InterfaceObj {

    // Declaração de atributos
    private long codPessoa;
    private long cnpj;
    private long ie;
    private Calendar dataDeFundacao;

    private PessoasJuridicasDAO pessoaJuridicaDAO;

    // Construtor Padrão
    public PessoasJuridicas() {
        codPessoa = -1;
        cnpj = 0;
        ie = 0;
        dataDeFundacao = Calendar.getInstance();

        pessoaJuridicaDAO = new PessoasJuridicasDAO();

        adicionarCampos();
    }

    // Construtor
    public PessoasJuridicas(Pessoas pessoa) {
        codPessoa = pessoa.getIdPessoa();
        cnpj = 0;
        ie = 0;
        dataDeFundacao = Calendar.getInstance();

        pessoaJuridicaDAO = new PessoasJuridicasDAO();

        adicionarCampos();
    }

    // Construtor
    @SuppressWarnings("empty-statement")
    public PessoasJuridicas(PessoasJuridicas pessoaJ) {
        codPessoa = pessoaJ.getCodPessoa();
        cnpj = pessoaJ.getCnpj();
        ie = pessoaJ.getIe();
        dataDeFundacao = pessoaJ.getDataDeFundacao();

        pessoaJuridicaDAO = pessoaJ.getPessoaJuridicaDAO();;

        adicionarCampos();
    }

    // Construtor
    public PessoasJuridicas(long codPessoa, long cnpj, long ie,
            Calendar dataDeFundacao) {
        this.codPessoa = codPessoa;
        this.cnpj = cnpj;
        this.ie = ie;
        this.dataDeFundacao = dataDeFundacao;

        pessoaJuridicaDAO = new PessoasJuridicasDAO();

        adicionarCampos();
    }

    @Override
    public boolean equals(Object object) {
        boolean retorno = false;

        if (object instanceof PessoasJuridicas) {
            PessoasJuridicas other = (PessoasJuridicas) object;
            if (getCodPessoa() == other.getCodPessoa()) {
                retorno = true;
            }
        }

        return retorno;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + (int) (this.codPessoa ^ (this.codPessoa >>> 32));
        hash = 17 * hash + (int) (this.cnpj ^ (this.cnpj >>> 32));
        hash = 17 * hash + (int) (this.ie ^ (this.ie >>> 32));
        hash = 17 * hash + Objects.hashCode(this.dataDeFundacao);
        hash = 17 * hash + Objects.hashCode(this.pessoaJuridicaDAO);
        return hash;
    }

    @Override
    public ObjetoDAO getObjetoDAO() {
        return pessoaJuridicaDAO;
    }

    @Override
    public void adicionarCampos() {
        removerCampos();

        getObjetoDAO().addCampo(getDadosCodigo());
        getObjetoDAO().addCampo(new DadosDAO("cnpj", "CNPJ", String.valueOf(cnpj),
                DadosDAO.TIPO_LONG, DadosDAO.IS_IGUAL, DadosDAO.NAO_CHAVE));
        getObjetoDAO().addCampo(new DadosDAO("ie", "IE", String.valueOf(ie),
                DadosDAO.TIPO_LONG, DadosDAO.IS_IGUAL, DadosDAO.NAO_CHAVE));
        getObjetoDAO().addCampo(new DadosDAO("data_fundacao",
                "Data fund.", Formatacao.ajustaDataAMD(dataDeFundacao),
                DadosDAO.TIPO_DATE, DadosDAO.IS_IGUAL, DadosDAO.NAO_CHAVE));
    }

    /**
     * @return the dadosCodigo
     */
    @Override
    public DadosDAO getDadosCodigo() {
        return new DadosDAO("cod_pessoa", "Cód. Pessoa", String.valueOf(
                codPessoa), DadosDAO.TIPO_LONG, DadosDAO.IS_IGUAL, 
                DadosDAO.IS_CHAVE);
    }

    /**
     * @return the dadosDescricao
     */
    @Override
    public DadosDAO getDadosDescricao() {
        return new DadosDAO(pessoaJuridicaDAO.getCampo("nome||' - '||sobrenome",
                "pessoas", "id_pessoa = cod_pessoa", false), "Cliente", "",
                DadosDAO.TIPO_STRING, DadosDAO.IS_IGUAL, DadosDAO.NAO_CHAVE);
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
     * @return the cnpj
     */
    public long getCnpj() {
        return cnpj;
    }

    /**
     * @param cnpj the cnpj to set
     */
    public void setCnpj(long cnpj) {
        this.cnpj = cnpj;
    }

    /**
     * @return the ie
     */
    public long getIe() {
        return ie;
    }

    /**
     * @param ie the ie to set
     */
    public void setIe(long ie) {
        this.ie = ie;
    }

    /**
     * @return the dataDeFundacao
     */
    public Calendar getDataDeFundacao() {
        return dataDeFundacao;
    }

    /**
     * @param dataDeFundacao the dataDeFundacao to set
     */
    public void setDataDeFundacao(Calendar dataDeFundacao) {
        this.dataDeFundacao = dataDeFundacao;
    }

    /**
     * @return the pessoaJuridicaDAO
     */
    public PessoasJuridicasDAO getPessoaJuridicaDAO() {
        return pessoaJuridicaDAO;
    }

    /**
     * @param pessoaJuridicaDAO the pessoaJuridicaDAO to set
     */
    public void setPessoaJuridicaDAO(PessoasJuridicasDAO pessoaJuridicaDAO) {
        this.pessoaJuridicaDAO = pessoaJuridicaDAO;
    }
}
