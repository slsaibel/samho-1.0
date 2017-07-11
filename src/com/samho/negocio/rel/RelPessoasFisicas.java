/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.samho.negocio.rel;

import com.samho.dao.rel.RelPessoasFisicasDAO;
import com.samho.dao.DadosDAO;
import com.samho.dao.ObjetoDAO;
import com.samho.negocio.InterfaceObj;
import com.samho.negocio.Objeto;
import com.samho.util.Formatacao;
import java.util.Calendar;
import java.util.Objects;

/**
 * Classe de PessoasView.
 *
 * @author Saibel, Sergio Luis
 * @since date 24/06/2014
 *
 * @version  revision 001.20140624 date 24/06/2014 author Saibel, Sérgio Luis reason
 * Instanciar um objeto com métodos e atributos necessários.
 */
public class RelPessoasFisicas extends Objeto implements InterfaceObj{

    // Declaração de atributos
    // 
    private long id_pessoa;
    private String nomecompleto;
    private long cpf;
    private long rg;
    private String nome_mae;
    private Calendar data_nascimento;
    private String sexo;
    private String situacao;
    private String observacao;
  
    private RelPessoasFisicasDAO relPessoasFisicasDAO;

    // Construtor padrão
    public RelPessoasFisicas() {
        id_pessoa = -1;
        nomecompleto = "";
        cpf = 0;
        rg = 0;
        nome_mae = "";
        data_nascimento = Calendar.getInstance();
        sexo = "";
        situacao = "";
        observacao = "";

        relPessoasFisicasDAO = new RelPessoasFisicasDAO();

        adicionarCampos();
    }

    @Override
    public boolean equals(Object object) {
        boolean retorno = false;

        if (object instanceof RelPessoasFisicas) {
            RelPessoasFisicas other = (RelPessoasFisicas) object;
            if (id_pessoa == other.getId_pessoa()) {
                retorno = true;
            }
        }

        return retorno;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + (int) (this.id_pessoa ^ (this.id_pessoa >>> 32));
        hash = 79 * hash + Objects.hashCode(this.nomecompleto);
        hash = 79 * hash + (int) (this.cpf ^ (this.cpf >>> 32));
        hash = 79 * hash + (int) (this.rg ^ (this.rg >>> 32));
        hash = 79 * hash + Objects.hashCode(this.nome_mae);
        hash = 79 * hash + Objects.hashCode(this.data_nascimento);
        hash = 79 * hash + Objects.hashCode(this.sexo);
        hash = 79 * hash + Objects.hashCode(this.situacao);
        hash = 79 * hash + Objects.hashCode(this.observacao);
        hash = 79 * hash + Objects.hashCode(this.relPessoasFisicasDAO);
        return hash;
    }

    @Override
    public void adicionarCampos() {
        removerCampos();
        
        getObjetoDAO().addCampo(getDadosCodigo());
        getObjetoDAO().addCampo(getDadosDescricao());
        getObjetoDAO().addCampo(new DadosDAO("cpf", "CPF", 
                String.valueOf(cpf), DadosDAO.TIPO_STRING, DadosDAO.IS_IGUAL, 
                DadosDAO.COMPLETO));
        getObjetoDAO().addCampo(new DadosDAO("rg", "RG", String.valueOf(cpf), 
                DadosDAO.TIPO_STRING, DadosDAO.IS_IGUAL, DadosDAO.COMPLETO));
        getObjetoDAO().addCampo(new DadosDAO("nome_mae", "Nome Mãe", 
                nome_mae, DadosDAO.TIPO_STRING, DadosDAO.IS_IGUAL, 
                DadosDAO.COMPLETO));
        getObjetoDAO().addCampo(new DadosDAO("data_nascimento",
                "Data nasc.", Formatacao.ajustaDataAMD(data_nascimento),
                DadosDAO.TIPO_DATE, DadosDAO.IS_IGUAL, DadosDAO.NAO_CHAVE));
        getObjetoDAO().addCampo(new DadosDAO("sexo", "Sexo",
                String.valueOf(sexo), DadosDAO.TIPO_STRING, DadosDAO.IS_IGUAL, 
                DadosDAO.NAO_CHAVE));
        getObjetoDAO().addCampo(new DadosDAO("situacao", "Situação", situacao,
                DadosDAO.TIPO_STRING, DadosDAO.IS_IGUAL, DadosDAO.COMPLETO));
        getObjetoDAO().addCampo(new DadosDAO("observacao", "Observações",
                observacao, DadosDAO.TIPO_STRING, DadosDAO.IS_IGUAL,
                DadosDAO.NAO_CHAVE));
    }

    @Override
    public ObjetoDAO getObjetoDAO() {
        return relPessoasFisicasDAO;
    }

    @Override
    public DadosDAO getDadosCodigo() {
        return new DadosDAO("id_pessoa", "Cód. Pessoa", String.valueOf(id_pessoa), 
                DadosDAO.TIPO_LONG, DadosDAO.IS_IGUAL, DadosDAO.COMPLETO);
    }

    @Override
    public DadosDAO getDadosDescricao() {
        return new DadosDAO("nomecompleto", "Nome Pessoa", 
                String.valueOf(nomecompleto), DadosDAO.TIPO_STRING, 
                DadosDAO.IS_IGUAL, DadosDAO.PARCIAL);
    }

    /**
     * @return the id_pessoa
     */
    public long getId_pessoa() {
        return id_pessoa;
    }

    /**
     * @param id_pessoa the id_pessoa to set
     */
    public void setId_pessoa(long id_pessoa) {
        this.id_pessoa = id_pessoa;
    }

    /**
     * @return the nomecompleto
     */
    public String getNomecompleto() {
        return nomecompleto;
    }

    /**
     * @param nomecompleto the nomecompleto to set
     */
    public void setNomecompleto(String nomecompleto) {
        this.nomecompleto = nomecompleto;
    }

    /**
     * @return the cpf
     */
    public long getCpf() {
        return cpf;
    }

    /**
     * @param cpf the cpf to set
     */
    public void setCpf(long cpf) {
        this.cpf = cpf;
    }

    /**
     * @return the rg
     */
    public long getRg() {
        return rg;
    }

    /**
     * @param rg the rg to set
     */
    public void setRg(long rg) {
        this.rg = rg;
    }

    /**
     * @return the nome_mae
     */
    public String getNome_mae() {
        return nome_mae;
    }

    /**
     * @param nome_mae the nome_mae to set
     */
    public void setNome_mae(String nome_mae) {
        this.nome_mae = nome_mae;
    }

    /**
     * @return the data_nascimento
     */
    public Calendar getData_nascimento() {
        return data_nascimento;
    }

    /**
     * @param data_nascimento the data_nascimento to set
     */
    public void setData_nascimento(Calendar data_nascimento) {
        this.data_nascimento = data_nascimento;
    }

    /**
     * @return the sexo
     */
    public String getSexo() {
        return sexo;
    }

    /**
     * @param sexo the sexo to set
     */
    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    /**
     * @return the situacao
     */
    public String getSituacao() {
        return situacao;
    }

    /**
     * @param situacao the situacao to set
     */
    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    /**
     * @return the observacao
     */
    public String getObservacao() {
        return observacao;
    }

    /**
     * @param observacao the observacao to set
     */
    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
}
