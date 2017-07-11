/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.samho.negocio;

import com.samho.dao.AcoesUsuariosDAO;
import com.samho.dao.DadosDAO;
import com.samho.dao.ObjetoDAO;
import java.util.Objects;

/**
 * Classe de Objeto.
 *
 * @author Saibel, Sergio Luis
 * @since  date 05/04/2017
 *
 * @version  revision 001.20170405 date 05/04/2017 author Saibel, Sergio Luís reason
 * Instanciar um objeto com métodos e atributos necessários.
 */
public final class AcoesUsuarios extends Objeto implements InterfaceObj {

    // Declaração de atributos
    private long idAcaoUsuario;
    private long codAcao;
    private long codUsuario;
    private boolean incluir;
    private boolean alterar;
    private boolean excluir;
    private boolean consultar;

    private AcoesUsuariosDAO acaoUsuarioDAO;

    // Construtor padrão
    public AcoesUsuarios() {
        idAcaoUsuario = -1;
        codAcao = -1;
        codUsuario = -1;
        incluir = true;
        alterar = true;
        excluir = true;
        consultar = true;

        acaoUsuarioDAO = new AcoesUsuariosDAO();

        adicionarCampos();
    }

    // Construtor
    public AcoesUsuarios(AcoesUsuarios acaoUsuario) {
        idAcaoUsuario = acaoUsuario.idAcaoUsuario;
        codAcao = acaoUsuario.codAcao;
        codUsuario = acaoUsuario.codUsuario;
        incluir = acaoUsuario.incluir;
        alterar = acaoUsuario.alterar;
        excluir = acaoUsuario.excluir;
        consultar = acaoUsuario.consultar;

        acaoUsuarioDAO = new AcoesUsuariosDAO();

        adicionarCampos();
    }

    // Construtor
    public AcoesUsuarios(long idAcaoUsuario, long codAcao, long codUsuario,
            boolean incluir, boolean alterar, boolean excluir, boolean consultar) {
        this.idAcaoUsuario = idAcaoUsuario;
        this.codAcao = codAcao;
        this.codUsuario = codUsuario;
        this.incluir = incluir;
        this.alterar = alterar;
        this.excluir = excluir;
        this.consultar = consultar;

        this.acaoUsuarioDAO = new AcoesUsuariosDAO();

        adicionarCampos();
    }

    @Override
    public boolean equals(Object object) {
        boolean retorno = false;

        if (object instanceof AcoesUsuarios) {
            AcoesUsuarios other = (AcoesUsuarios) object;
            if (idAcaoUsuario == other.getIdAcaoUsuario()) {
                retorno = true;
            }
        }

        return retorno;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + (int) (this.idAcaoUsuario ^ (this.idAcaoUsuario >>> 32));
        hash = 53 * hash + (int) (this.codAcao ^ (this.codAcao >>> 32));
        hash = 53 * hash + (int) (this.codUsuario ^ (this.codUsuario >>> 32));
        hash = 53 * hash + (this.incluir ? 1 : 0);
        hash = 53 * hash + (this.alterar ? 1 : 0);
        hash = 53 * hash + (this.excluir ? 1 : 0);
        hash = 53 * hash + (this.consultar ? 1 : 0);
        hash = 53 * hash + Objects.hashCode(this.acaoUsuarioDAO);
        return hash;
    }

    @Override
    public ObjetoDAO getObjetoDAO() {
        return acaoUsuarioDAO;
    }

    @Override
    public void adicionarCampos() {
        removerCampos();

        getObjetoDAO().addCampo(getDadosCodigo());
        getObjetoDAO().addCampo(new DadosDAO("cod_acao", 
                "Cód. Ação Usuario/Tabelas", String.valueOf(codAcao),
                DadosDAO.TIPO_LONG, DadosDAO.IS_IGUAL, DadosDAO.NAO_CHAVE));
        getObjetoDAO().addCampo(new DadosDAO("cod_usuario", "Cód. Usuário",
                String.valueOf(codUsuario), DadosDAO.TIPO_LONG, 
                DadosDAO.IS_IGUAL, DadosDAO.NAO_CHAVE));
        getObjetoDAO().addCampo(new DadosDAO("incluir", "Incluir",
                String.valueOf(incluir), DadosDAO.TIPO_BOOLEAN, 
                DadosDAO.IS_IGUAL, DadosDAO.NAO_CHAVE));
        getObjetoDAO().addCampo(new DadosDAO("alterar", "Alterar",
                String.valueOf(alterar), DadosDAO.TIPO_BOOLEAN, 
                DadosDAO.IS_IGUAL, DadosDAO.NAO_CHAVE));
        getObjetoDAO().addCampo(new DadosDAO("excluir", "Excluir",
                String.valueOf(excluir), DadosDAO.TIPO_BOOLEAN, 
                DadosDAO.IS_IGUAL, DadosDAO.NAO_CHAVE));
        getObjetoDAO().addCampo(new DadosDAO("consultar", "Consultar",
                String.valueOf(consultar), DadosDAO.TIPO_BOOLEAN, 
                DadosDAO.IS_IGUAL, DadosDAO.NAO_CHAVE));
        
        // Adicionando where de restrição ao administrador id = 0
        getObjetoDAO().addWhere(new DadosDAO("cod_usuario", "", "0", 
                DadosDAO.TIPO_LONG, DadosDAO.IS_DIFERENTE, DadosDAO.NAO_CHAVE));
    }

    /**
     * @return the dadosCodigo
     */
    @Override
    public DadosDAO getDadosCodigo() {
        return new DadosDAO("id_acao_usuario", "Cód. Ação", 
                String.valueOf(idAcaoUsuario), DadosDAO.TIPO_LONG, 
                DadosDAO.IS_IGUAL, DadosDAO.IS_CHAVE);
    }

    /**
     * @return the dadosDescricao
     */
    @Override
    public DadosDAO getDadosDescricao() {
        return new DadosDAO("", "", "", DadosDAO.TIPO_STRING, DadosDAO.IS_IGUAL, 
                DadosDAO.NAO_CHAVE);
    }

    /**
     * @return the idAcaoUsuario
     */
    public long getIdAcaoUsuario() {
        return idAcaoUsuario;
    }

    /**
     * @param idAcaoUsuario the idAcaoUsuario to set
     */
    public void setIdAcaoUsuario(long idAcaoUsuario) {
        this.idAcaoUsuario = idAcaoUsuario;
    }

    /**
     * @return the codAcao
     */
    public Long getCodAcao() {
        return codAcao;
    }

    /**
     * @param codAcao the codAcao to set
     */
    public void setCodAcao(Long codAcao) {
        this.codAcao = codAcao;
    }

    /**
     * @return the codUsuario
     */
    public Long getCodUsuario() {
        return codUsuario;
    }

    /**
     * @param codUsuario the codUsuario to set
     */
    public void setCodUsuario(Long codUsuario) {
        this.codUsuario = codUsuario;
    }

    /**
     * @return the incluir
     */
    public boolean isIncluir() {
        return incluir;
    }

    /**
     * @param incluir the incluir to set
     */
    public void setIncluir(boolean incluir) {
        this.incluir = incluir;
    }

    /**
     * @return the alterar
     */
    public boolean isAlterar() {
        return alterar;
    }

    /**
     * @param alterar the alterar to set
     */
    public void setAlterar(boolean alterar) {
        this.alterar = alterar;
    }

    /**
     * @return the excluir
     */
    public boolean isExcluir() {
        return excluir;
    }

    /**
     * @param excluir the excluir to set
     */
    public void setExcluir(boolean excluir) {
        this.excluir = excluir;
    }

    /**
     * @return the consultar
     */
    public boolean isConsultar() {
        return consultar;
    }

    /**
     * @param consultar the consultar to set
     */
    public void setConsultar(boolean consultar) {
        this.consultar = consultar;
    }

    /**
     * @return the acaoUsuarioDAO
     */
    public AcoesUsuariosDAO getAcaoUsuarioDAO() {
        return acaoUsuarioDAO;
    }

    /**
     * @param acaoUsuarioDAO the acaoUsuarioDAO to set
     */
    public void setAcaoUsuarioDAO(AcoesUsuariosDAO acaoUsuarioDAO) {
        this.acaoUsuarioDAO = acaoUsuarioDAO;
    }

}
