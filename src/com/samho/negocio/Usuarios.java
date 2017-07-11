/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.samho.negocio;

import com.samho.dao.UsuariosDAO;
import com.samho.dao.DadosDAO;
import com.samho.dao.ObjetoDAO;
import java.util.ArrayList;
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
public final class Usuarios extends Objeto implements InterfaceObj {

    // Declaração de atributos
    private long idUsuario;
    private long codFuncionario;
    private String login;
    private String senha;
    private boolean administrador;
    private ArrayList<AcoesUsuarios> permissoes;

    private UsuariosDAO usuarioDAO;

    // Construtor padrão
    public Usuarios() {
        idUsuario = -1;
        codFuncionario = -1;
        login = "";
        senha = "";
        administrador = true;
        
        permissoes = new ArrayList<>();

        usuarioDAO = new UsuariosDAO();

        adicionarCampos();
    }

    // Construtor
    public Usuarios(Usuarios usuario) {
        idUsuario = usuario.getIdUsuario();
        codFuncionario = usuario.getCodFuncionario();
        login = usuario.getLogin();
        senha = usuario.getSenha();
        administrador = usuario.isAdministrador();
        
        permissoes = usuario.getPermissoes();

        usuarioDAO = usuario.getUsuarioDAO();

        adicionarCampos();
    }

    // Construtor
    public Usuarios(long idUsuario, long codFuncionario, long codPessoa,
            String login, String senha, boolean administrador, 
            ArrayList<AcoesUsuarios> permissoes) {
        this.idUsuario = idUsuario;
        this.codFuncionario = codFuncionario;
        this.login = login;
        this.senha = senha;
        this.administrador = administrador;
        
        this.permissoes = permissoes;

        usuarioDAO = new UsuariosDAO();

        adicionarCampos();
    }

    @Override
    public boolean equals(Object object) {
        boolean retorno = false;

        if (object instanceof Usuarios) {
            Usuarios other = (Usuarios) object;
            if (idUsuario == other.getIdUsuario()) {
                retorno = true;
            }
        }

        return retorno;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + (int) (this.idUsuario ^ (this.idUsuario >>> 32));
        hash = 97 * hash + (int) (this.codFuncionario ^ (this.codFuncionario >>> 32));
        hash = 97 * hash + Objects.hashCode(this.login);
        hash = 97 * hash + Objects.hashCode(this.senha);
        hash = 97 * hash + (this.administrador ? 1 : 0);
        hash = 97 * hash + Objects.hashCode(this.usuarioDAO);
        return hash;
    }

    @Override
    public ObjetoDAO getObjetoDAO() {
        return usuarioDAO;
    }

    @Override
    public void adicionarCampos() {
        removerCampos();

        getObjetoDAO().addCampo(getDadosCodigo());
        getObjetoDAO().addCampo(new DadosDAO("cod_funcionario", "Cód. Funcionário",
                String.valueOf(codFuncionario), DadosDAO.TIPO_LONG,
                DadosDAO.IS_IGUAL, DadosDAO.NAO_CHAVE));
        getObjetoDAO().addCampo(getDadosDescricao());
        getObjetoDAO().addCampo(new DadosDAO("senha", "Senha",
                senha, DadosDAO.TIPO_STRING, DadosDAO.IS_IGUAL, 
                DadosDAO.NAO_CHAVE));
        getObjetoDAO().addCampo(new DadosDAO("administrador", "Administrador",
                String.valueOf(isAdministrador()), DadosDAO.TIPO_BOOLEAN, 
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
        return new DadosDAO("id_usuario", "Cód. Usuário",
                String.valueOf(idUsuario), DadosDAO.TIPO_LONG,
                DadosDAO.IS_IGUAL, DadosDAO.IS_CHAVE);
    }

    /**
     * @return the dadosDescricao
     */
    @Override
    public DadosDAO getDadosDescricao() {
        return new DadosDAO("login", "Login", String.valueOf(login), 
                DadosDAO.TIPO_STRING, DadosDAO.IS_IGUAL, DadosDAO.NAO_CHAVE);
    }

    /**
     * @return the idUsuario
     */
    public long getIdUsuario() {
        return idUsuario;
    }

    /**
     * @param idUsuario the idUsuario to set
     */
    public void setIdUsuario(long idUsuario) {
        this.idUsuario = idUsuario;
    }

    /**
     * @return the codFuncionario
     */
    public long getCodFuncionario() {
        return codFuncionario;
    }

    /**
     * @param codFuncionario the codFuncionario to set
     */
    public void setCodFuncionario(long codFuncionario) {
        this.codFuncionario = codFuncionario;
    }

    /**
     * @return the login
     */
    public String getLogin() {
        return login;
    }

    /**
     * @param login the login to set
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * @return the senha
     */
    public String getSenha() {
        return senha;
    }

    /**
     * @param senha the senha to set
     */
    public void setSenha(String senha) {
        this.senha = senha;
    }

    /**
     * @return the administrador
     */
    public boolean isAdministrador() {
        return administrador;
    }

    /**
     * @param administrador the administrador to set
     */
    public void setAdministrador(boolean administrador) {
        this.administrador = administrador;
    }

    /**
     * @return the usuarioDAO
     */
    public UsuariosDAO getUsuarioDAO() {
        return usuarioDAO;
    }

    /**
     * @param usuarioDAO the usuarioDAO to set
     */
    public void setUsuarioDAO(UsuariosDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    /**
     * @return the permissoes
     */
    public ArrayList<AcoesUsuarios> getPermissoes() {
        return permissoes;
    }

    /**
     * @param permissoes the permissoes to set
     */
    public void setPermissoes(ArrayList<AcoesUsuarios> permissoes) {
        this.permissoes = permissoes;
    }
}
