/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.samho.negocio;

import com.samho.dao.ClientesDAO;
import com.samho.dao.DadosDAO;
import com.samho.dao.ObjetoDAO;
import com.samho.util.Formatacao;
import java.util.Calendar;
import java.util.Objects;

/**
 * Classe de Clientes.
 *
 * @author Sergio
 * @since  date 18/04/2014
 *
 * @version  revision 001.20140418 date 18/04/2014 author Saibel, Sergio Luís reason
 * Instanciar um objeto com métodos e atributos necessários.
 */
public class Clientes extends Objeto implements InterfaceObj {

    // Declaração de atributos
    private long idCliente;
    private long codPessoa;
    private long codSituacao;
    private Calendar dataDeCadastro;
    private String observacoes;

    private ClientesDAO clienteDAO;

    // Construtor padrão
    public Clientes() {
        idCliente = -1;
        codPessoa = -1;
        codSituacao = 0;
        dataDeCadastro = Calendar.getInstance();
        observacoes = "";

        clienteDAO = new ClientesDAO();

        adicionarCampos();
    }

    // Construtor
    public Clientes(Clientes cliente) {
        idCliente = cliente.getIdCliente();
        codPessoa = cliente.getCodPessoa();
        codSituacao = cliente.getCodSituacao();
        dataDeCadastro = cliente.getDataDeCadastro();
        observacoes = cliente.getObservacoes();

        clienteDAO = cliente.getClienteDAO();

        adicionarCampos();
    }

    // Construtor
    public Clientes(long idCliente, long codPessoa, long codSituacao,
            Calendar dataDeCadastro, String observacoes) {
        this.idCliente = idCliente;
        this.codPessoa = codPessoa;
        this.codSituacao = codSituacao;
        this.dataDeCadastro = dataDeCadastro;
        this.observacoes = observacoes;

        clienteDAO = new ClientesDAO();

        adicionarCampos();
    }

    @Override
    public boolean equals(Object object) {
        boolean retorno = false;

        if (object instanceof Clientes) {
            Clientes other = (Clientes) object;
            if (idCliente == other.getIdCliente()) {
                retorno = true;
            }
        }

        return retorno;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + Objects.hashCode(this.idCliente);
        hash = 23 * hash + Objects.hashCode(this.codPessoa);
        hash = 23 * hash + Objects.hashCode(this.codSituacao);
        hash = 23 * hash + Objects.hashCode(this.dataDeCadastro);
        hash = 23 * hash + Objects.hashCode(this.observacoes);
        hash = 23 * hash + Objects.hashCode(this.clienteDAO);
        return hash;
    }

    @Override
    public ObjetoDAO getObjetoDAO() {
        return clienteDAO;
    }

    @Override
    public void adicionarCampos() {
        removerCampos();

        getObjetoDAO().addCampo(getDadosCodigo());
        getObjetoDAO().addCampo(new DadosDAO("cod_pessoa", "Cód. Pessoa",
                String.valueOf(codPessoa), DadosDAO.TIPO_INTEGER, 
                DadosDAO.IS_IGUAL, DadosDAO.NAO_CHAVE));
        getObjetoDAO().addCampo(new DadosDAO("cod_situacao", "Cód. Situação",
                String.valueOf(codSituacao), DadosDAO.TIPO_INTEGER, 
                DadosDAO.IS_IGUAL, DadosDAO.NAO_CHAVE));
        getObjetoDAO().addCampo(new DadosDAO("data_cadastro", "Data Cadastro",
                Formatacao.ajustaDataAMD(dataDeCadastro), DadosDAO.TIPO_DATE,
                DadosDAO.IS_IGUAL, DadosDAO.NAO_CHAVE));
        getObjetoDAO().addCampo(new DadosDAO("observacoes", "Observações",
                observacoes, DadosDAO.TIPO_STRING, DadosDAO.IS_IGUAL,
                DadosDAO.NAO_CHAVE));
    }

    /**
     * @return the dadosCodigo
     */
    @Override
    public DadosDAO getDadosCodigo() {
        return new DadosDAO("id_cliente", "Cód. Cliente",
                String.valueOf(idCliente), DadosDAO.TIPO_INTEGER, 
                DadosDAO.IS_IGUAL, DadosDAO.IS_CHAVE);
    }

    /**
     * @return the dadosDescricao
     */
    @Override
    public DadosDAO getDadosDescricao() {
        return new DadosDAO(clienteDAO.getSQLSubstiturCampoDescricao(idCliente),
                "Cliente", "", DadosDAO.TIPO_STRING, DadosDAO.IS_IGUAL,
                DadosDAO.NAO_CHAVE);
    }

    /**
     * @return the idCliente
     */
    public long getIdCliente() {
        return idCliente;
    }

    /**
     * @param idCliente the idCliente to set
     */
    public void setIdCliente(long idCliente) {
        this.idCliente = idCliente;
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
     * @return the dataDeCadastro
     */
    public Calendar getDataDeCadastro() {
        return dataDeCadastro;
    }

    /**
     * @param dataDeCadastro the dataDeCadastro to set
     */
    public void setDataDeCadastro(Calendar dataDeCadastro) {
        this.dataDeCadastro = dataDeCadastro;
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
     * @return the clienteDAO
     */
    public ClientesDAO getClienteDAO() {
        return clienteDAO;
    }

    /**
     * @param clienteDAO the clienteDAO to set
     */
    public void setClienteDAO(ClientesDAO clienteDAO) {
        this.clienteDAO = clienteDAO;
    }
}
