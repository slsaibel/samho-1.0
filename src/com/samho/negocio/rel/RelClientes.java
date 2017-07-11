/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.samho.negocio.rel;

import com.samho.dao.rel.RelClientesDAO;
import com.samho.dao.DadosDAO;
import com.samho.dao.ObjetoDAO;
import com.samho.negocio.Objeto;
import java.util.Objects;

/**
 * Classe de PessoasView.
 *
 * @author Saibel, Sergio Luis
 * @since  date 24/06/2014
 *
 * @version  revision 001.20140624 date 24/06/2014 author Saibel, Sérgio Luis reason
 * Instanciar um objeto com métodos e atributos necessários.
 */
public class RelClientes extends Objeto{

    // Declaração de atributos
    private int id_cliente;
    private String nome;
    private String data_cadastro;
    private String situacao;

    private RelClientesDAO clienteVIEW;

    // Construtor padrão
    public RelClientes() {
        id_cliente = -1;
        nome = "";
        data_cadastro = null;
        situacao = "";

        clienteVIEW = new RelClientesDAO();

        adicionarCampos();
    }

    @Override
    public boolean equals(Object object) {
        boolean retorno = false;

        if (object instanceof RelClientes) {
            RelClientes other = (RelClientes) object;
            if (id_cliente == other.getId_cliente()) {
                retorno = true;
            }
        }

        return retorno;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + this.id_cliente;
        hash = 17 * hash + Objects.hashCode(this.nome);
        hash = 17 * hash + Objects.hashCode(this.data_cadastro);
        hash = 17 * hash + Objects.hashCode(this.situacao);
        hash = 17 * hash + Objects.hashCode(this.clienteVIEW);
        return hash;
    }

    @Override
    public void adicionarCampos() {
        removerCampos();

        clienteVIEW.addCampo(new DadosDAO("id_cliente", "Cód. Cliente",
                String.valueOf(id_cliente), DadosDAO.TIPO_INTEGER,
                DadosDAO.IS_IGUAL, DadosDAO.COMPLETO));
        clienteVIEW.addCampo(new DadosDAO("nome", "Nome", nome,
                DadosDAO.TIPO_STRING, DadosDAO.IS_IGUAL,DadosDAO.COMPLETO));
        clienteVIEW.addCampo(new DadosDAO("data_cadastro", "Data Cadastro",
                data_cadastro, DadosDAO.TIPO_DATE, DadosDAO.IS_IGUAL, 
                DadosDAO.COMPLETO));
        clienteVIEW.addCampo(new DadosDAO("situacao", "Situação",situacao,
                DadosDAO.TIPO_STRING, DadosDAO.IS_IGUAL, DadosDAO.COMPLETO));
    }

    /**
     * @return the id_cliente
     */
    public int getId_cliente() {
        return id_cliente;
    }

    /**
     * @param id_cliente the id_cliente to set
     */
    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
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
     * @return the data_cadastro
     */
    public String getData_cadastro() {
        return data_cadastro;
    }

    /**
     * @param data_cadastro the data_cadastro to set
     */
    public void setData_cadastro(String data_cadastro) {
        this.data_cadastro = data_cadastro;
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
     * @return the clienteVIEW
     */
    public RelClientesDAO getClienteVIEW() {
        return clienteVIEW;
    }

    /**
     * @param clienteVIEW the clienteVIEW to set
     */
    public void setClienteVIEW(RelClientesDAO clienteVIEW) {
        this.clienteVIEW = clienteVIEW;
    }

    @Override
    public ObjetoDAO getObjetoDAO() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public DadosDAO getDadosCodigo() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public DadosDAO getDadosDescricao() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
