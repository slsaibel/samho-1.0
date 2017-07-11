/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.samho.negocio;

import com.samho.dao.ClientesTiposPlanosDAO;
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
public final class ClientesTiposPlanos extends Objeto implements InterfaceObj {

    // Declaração de atributos
    private long idClienteTipoPlano;
    private long codCliente;
    private long codTipoPlano;
    private Calendar dataAdesao;
    private boolean ativo;


    private ClientesTiposPlanosDAO clienteTipoPlanoDAO;

    // Construtor padrão
    public ClientesTiposPlanos() {
        idClienteTipoPlano = -1;
        codCliente = -1;
        codTipoPlano = -1;
        dataAdesao = null;
        ativo = true;

        clienteTipoPlanoDAO = new ClientesTiposPlanosDAO();

        adicionarCampos();
    }

    // Construtor
    public ClientesTiposPlanos(ClientesTiposPlanos clienteTipoPlano) {
        idClienteTipoPlano = clienteTipoPlano.getIdClienteTipoPlano();
        codCliente = clienteTipoPlano.getCodCliente();
        codTipoPlano = clienteTipoPlano.getCodTipoPlano();
        dataAdesao = clienteTipoPlano.getDataAdesao();
        ativo = clienteTipoPlano.isAtivo();

        clienteTipoPlanoDAO = clienteTipoPlano.getClienteTipoPlanoDAO();

        adicionarCampos();
    }

    // Construtor
    public ClientesTiposPlanos(long idClienteTipoPlano, long codCliente, 
            long codTipoPlano, Calendar dataAdesao, boolean ativo) {
        this.idClienteTipoPlano = idClienteTipoPlano;
        this.codCliente = codCliente;
        this.codTipoPlano = codTipoPlano;
        this.dataAdesao = dataAdesao;
        this.ativo = ativo;

        this.clienteTipoPlanoDAO = new ClientesTiposPlanosDAO();

        adicionarCampos();
    }

    @Override
    public boolean equals(Object object) {
        boolean retorno = false;

        if (object instanceof ClientesTiposPlanos) {
            ClientesTiposPlanos other = (ClientesTiposPlanos) object;
            if (idClienteTipoPlano == other.getIdClienteTipoPlano()) {
                retorno = true;
            }
        }
        
        return retorno;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 13 * hash + (int) (this.idClienteTipoPlano ^ (this.idClienteTipoPlano >>> 32));
        hash = 13 * hash + (int) (this.codCliente ^ (this.codCliente >>> 32));
        hash = 13 * hash + (int) (this.codTipoPlano ^ (this.codTipoPlano >>> 32));
        hash = 13 * hash + Objects.hashCode(this.dataAdesao);
        hash = 13 * hash + (this.ativo ? 1 : 0);
        hash = 13 * hash + Objects.hashCode(this.clienteTipoPlanoDAO);
        return hash;
    }

    @Override
    public ObjetoDAO getObjetoDAO() {
        return clienteTipoPlanoDAO;
    }

    @Override
    public void adicionarCampos() {
        removerCampos();

        getObjetoDAO().addCampo(getDadosCodigo());
        getObjetoDAO().addCampo(new DadosDAO("cod_cliente", "Cód. Cliente", 
                String.valueOf(codCliente), DadosDAO.TIPO_LONG, DadosDAO.IS_IGUAL, 
                DadosDAO.NAO_CHAVE));
        getObjetoDAO().addCampo(new DadosDAO("cod_tipo_plano", 
                "Cód. Tip. Plano", String.valueOf(codTipoPlano), 
                DadosDAO.TIPO_LONG, DadosDAO.IS_IGUAL, DadosDAO.NAO_CHAVE));
        getObjetoDAO().addCampo(new DadosDAO("data_adesao", "Data Adesão",
                Formatacao.ajustaDataAMD(dataAdesao), DadosDAO.TIPO_DATE,
                DadosDAO.IS_IGUAL, DadosDAO.NAO_CHAVE));
        getObjetoDAO().addCampo(new DadosDAO("ativo", "Ativo",
                String.valueOf(ativo), DadosDAO.TIPO_BOOLEAN, DadosDAO.IS_IGUAL,
                DadosDAO.NAO_CHAVE));
        
        // Adicionando where de restrição ao administrador id = 0
        getObjetoDAO().addWhere(new DadosDAO(getObjetoDAO().getCampoID(), "", 
                "0", DadosDAO.TIPO_LONG, DadosDAO.IS_DIFERENTE, DadosDAO.IS_CHAVE));
    }

    /**
     * @return the dadosCodigo
     */
    @Override
    public DadosDAO getDadosCodigo() {
        return new DadosDAO("id_cliente_tipo_plano", "Cód. Cli. Plano",
                String.valueOf(idClienteTipoPlano), DadosDAO.TIPO_LONG,
                DadosDAO.IS_IGUAL, DadosDAO.IS_CHAVE);
    }

    /**
     * @return the dadosDescricao
     */
    @Override
    public DadosDAO getDadosDescricao() {
        return new DadosDAO(
                clienteTipoPlanoDAO.getSQLSubstiturCampoDescricao(idClienteTipoPlano),
                "Tipo Plano Saúde", "", DadosDAO.TIPO_STRING, DadosDAO.IS_IGUAL, 
                DadosDAO.NAO_CHAVE);
    }

    /**
     * @return the idClienteTipoPlano
     */
    public long getIdClienteTipoPlano() {
        return idClienteTipoPlano;
    }

    /**
     * @param idClienteTipoPlano the idClienteTipoPlano to set
     */
    public void setIdClienteTipoPlano(long idClienteTipoPlano) {
        this.idClienteTipoPlano = idClienteTipoPlano;
    }

    /**
     * @return the codCliente
     */
    public long getCodCliente() {
        return codCliente;
    }

    /**
     * @param codCliente the codCliente to set
     */
    public void setCodCliente(long codCliente) {
        this.codCliente = codCliente;
    }

    /**
     * @return the codTipoPlano
     */
    public long getCodTipoPlano() {
        return codTipoPlano;
    }

    /**
     * @param codTipoPlano the codTipoPlano to set
     */
    public void setCodTipoPlano(long codTipoPlano) {
        this.codTipoPlano = codTipoPlano;
    }

    /**
     * @return the dataAdesao
     */
    public Calendar getDataAdesao() {
        return dataAdesao;
    }

    /**
     * @param dataAdesao the dataAdesao to set
     */
    public void setDataAdesao(Calendar dataAdesao) {
        this.dataAdesao = dataAdesao;
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
     * @return the clienteTipoPlanoDAO
     */
    public ClientesTiposPlanosDAO getClienteTipoPlanoDAO() {
        return clienteTipoPlanoDAO;
    }

    /**
     * @param clienteTipoPlanoDAO the clienteTipoPlanoDAO to set
     */
    public void setClienteTipoPlanoDAO(ClientesTiposPlanosDAO clienteTipoPlanoDAO) {
        this.clienteTipoPlanoDAO = clienteTipoPlanoDAO;
    }
}
