/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.samho.negocio;

import com.samho.dao.DadosDAO;
import com.samho.dao.ObjetoDAO;
import com.samho.dao.PessoasDAO;
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
public final class Pessoas extends Objeto implements InterfaceObj {

    // Declaração de constantes
    public static final int TIPO_FISICA = 1;
    public static final int TIPO_JURIDICA = 2;

    // Declaração de atributos
    private long idPessoa;
    private long codSituacao;
    private String nome;
    private String sobrenome;
    private String observacoes;
    private int tipo;

    private PessoasDAO pessoaDAO;
    private PessoasFisicas pessoaFisica;
    private PessoasJuridicas pessoaJuridica;

    // Construtor padrão
    public Pessoas() {
        idPessoa = -1;
        codSituacao = 0;
        nome = "";
        sobrenome = "";
        observacoes = "";
        this.tipo = 0;

        pessoaDAO = new PessoasDAO();

        adicionarCampos();
    }

    // Construtor
    public Pessoas(int tipo) {
        idPessoa = -1;
        codSituacao = 0;
        nome = "";
        sobrenome = "";
        observacoes = "";
        this.tipo = tipo;

        pessoaDAO = new PessoasDAO();

        if (Pessoas.TIPO_FISICA == tipo) {
            pessoaFisica = new PessoasFisicas();
        } else {
            if (Pessoas.TIPO_JURIDICA == tipo) {
                pessoaJuridica = new PessoasJuridicas();
            }
        }

        adicionarCampos();
    }

    // Construtor
    public Pessoas(Pessoas pessoa) {
        idPessoa = pessoa.getIdPessoa();
        codSituacao = pessoa.getCodSituacao();
        nome = pessoa.getNome();
        sobrenome = pessoa.getSobrenome();
        observacoes = pessoa.getObservacoes();
        this.tipo = pessoa.getTipo();

        pessoaDAO = pessoa.getPessoaDAO();

        if (Pessoas.TIPO_FISICA == tipo) {
            pessoaFisica = new PessoasFisicas();
        } else {
            if (Pessoas.TIPO_JURIDICA == tipo) {
                pessoaJuridica = new PessoasJuridicas();
            }
        }

        adicionarCampos();
    }

    // Construtor
    public Pessoas(long idPessoa, long codSituacao, String nome,
            String sobrenome, String observacoes, int tipo) {
        this.idPessoa = idPessoa;
        this.codSituacao = codSituacao;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.observacoes = observacoes;
        this.tipo = tipo;

        this.pessoaDAO = new PessoasDAO();

        if (Pessoas.TIPO_FISICA == tipo) {
            pessoaFisica = new PessoasFisicas();
        } else {
            if (Pessoas.TIPO_JURIDICA == tipo) {
                pessoaJuridica = new PessoasJuridicas();
            }
        }

        adicionarCampos();
    }

    @Override
    public boolean equals(Object object) {
        boolean retorno = false;

        if (object instanceof Pessoas) {
            Pessoas other = (Pessoas) object;
            if (idPessoa == other.getIdPessoa()) {
                retorno = true;
            }
        }

        return retorno;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + Objects.hashCode(this.idPessoa);
        hash = 73 * hash + Objects.hashCode(this.codSituacao);
        hash = 73 * hash + Objects.hashCode(this.nome);
        hash = 73 * hash + Objects.hashCode(this.sobrenome);
        hash = 73 * hash + Objects.hashCode(this.observacoes);
        hash = 73 * hash + this.tipo;
        hash = 73 * hash + Objects.hashCode(this.pessoaDAO);
        hash = 73 * hash + Objects.hashCode(this.pessoaFisica);
        hash = 73 * hash + Objects.hashCode(this.pessoaJuridica);
        return hash;
    }

    @Override
    public ObjetoDAO getObjetoDAO() {
        return pessoaDAO;
    }

    @Override
    public void adicionarCampos() {
        removerCampos();

        getObjetoDAO().addCampo(getDadosCodigo());
        getObjetoDAO().addCampo(new DadosDAO("cod_situacao", "Cód. Situação",
                String.valueOf(codSituacao), DadosDAO.TIPO_LONG,
                DadosDAO.IS_IGUAL, DadosDAO.NAO_CHAVE));
        getObjetoDAO().addCampo(new DadosDAO("nome", "Nome", nome,
                DadosDAO.TIPO_STRING, DadosDAO.IS_IGUAL, DadosDAO.NAO_CHAVE));
        getObjetoDAO().addCampo(new DadosDAO("sobrenome", "Sobrenome",
                sobrenome, DadosDAO.TIPO_STRING, DadosDAO.IS_IGUAL, 
                DadosDAO.NAO_CHAVE));
        getObjetoDAO().addCampo(new DadosDAO("observacoes", "Observação",
                observacoes, DadosDAO.TIPO_STRING, DadosDAO.IS_IGUAL, 
                DadosDAO.NAO_CHAVE));

        if (Pessoas.TIPO_FISICA == tipo) {
            pessoaFisica.adicionarCampos();
        } else {
            if (Pessoas.TIPO_JURIDICA == tipo) {
                pessoaJuridica.adicionarCampos();
            }
        }
        
        // Adicionando where de restrição ao administrador id = 0
        getObjetoDAO().addWhere(new DadosDAO(getObjetoDAO().getCampoID(), "", 
                "0", DadosDAO.TIPO_LONG, DadosDAO.IS_DIFERENTE, DadosDAO.IS_CHAVE));
    }

    /**
     * @return the dadosCodigo
     */
    @Override
    public DadosDAO getDadosCodigo() {
        return new DadosDAO("id_pessoa", "Cód. Pessoa", String.valueOf(idPessoa),
                DadosDAO.TIPO_LONG, DadosDAO.IS_IGUAL, DadosDAO.IS_CHAVE);
    }

    /**
     * @return the dadosDescricao
     */
    @Override
    public DadosDAO getDadosDescricao() {
        return new DadosDAO("nome||' '||sobrenome", "Nome", nome + " " + sobrenome,
                DadosDAO.TIPO_STRING, DadosDAO.IS_IGUAL, DadosDAO.NAO_CHAVE);
    }

    /**
     * @return the idPessoa
     */
    public long getIdPessoa() {
        return idPessoa;
    }

    /**
     * @param idPessoa the idPessoa to set
     */
    public void setIdPessoa(long idPessoa) {
        this.idPessoa = idPessoa;
    }

    /**
     * @return the codSituacao
     */
    public long getCodSituacao() {
        return codSituacao;
    }

    /**
     * @param codSituacao the codSituacao to set
     */
    public void setCodSituacao(long codSituacao) {
        this.codSituacao = codSituacao;
    }

    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return the sobrenome
     */
    public String getSobrenome() {
        return sobrenome;
    }

    /**
     * @param sobrenome the sobrenome to set
     */
    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
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
     * @return the pessoaDAO
     */
    public PessoasDAO getPessoaDAO() {
        return pessoaDAO;
    }

    /**
     * @param pessoaDAO the pessoaDAO to set
     */
    public void setPessoaDAO(PessoasDAO pessoaDAO) {
        this.pessoaDAO = pessoaDAO;
    }

    /**
     * @return the tipo
     */
    public int getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    /**
     * @return the pessoaFisica
     */
    public PessoasFisicas getPessoaFisica() {
        return pessoaFisica;
    }

    /**
     * @param pessoaFisica the pessoaFisica to set
     */
    public void setPessoaFisica(PessoasFisicas pessoaFisica) {
        this.pessoaFisica = pessoaFisica;
    }

    /**
     * @return the pessoaJuridica
     */
    public PessoasJuridicas getPessoaJuridica() {
        return pessoaJuridica;
    }

    /**
     * @param pessoaJuridica the pessoaJuridica to set
     */
    public void setPessoaJuridica(PessoasJuridicas pessoaJuridica) {
        this.pessoaJuridica = pessoaJuridica;
    }
}
