/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.samho.dao;

import com.samho.negocio.AcoesUsuarios;
import com.samho.negocio.Principal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Classe DAO para Objetos Tipados.
 *
 * @author Saibel, Sergio Luis
 * @since  date 05/04/2017
 *
 * @version  revision 001.20170405 date 05/04/2017 author Saibel, Sergio Luís reason
 * permitir as operações com banco de dados. Esta classe serve para que o objeto
 * possa executar operaçoes de banco aparte.
 * Este tipo de classe extende o ObjetoDAO que é responsável por inserir, alterar
 * ou excluir um objeto do banco, bem como outras funcionalidades comuns a todos
 * os objetos.
 * 
 */
public class UsuariosDAO extends ObjetoDAO implements InterfaceDAO {

    public UsuariosDAO() {
        super("USUARIOS");
    }

    public int getProximoID() {
        return super.getProximoID("id_usuario");
    }
    
    public static void verificaAcoesUsuario() {
        try {
            String sql = 
                " SELECT "
                + "     id_acao_usuario, cod_acao, cod_usuario, "
                + "     incluir, alterar, excluir, consultar "    
                + " FROM acoes_usuarios WHERE cod_usuario = " 
                + Principal.usuario.getIdUsuario() + " ORDER BY cod_acao;";

            PreparedStatement pst = ConexoesDB.getInstance().getConnection().prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            if (rs.isBeforeFirst()) {
                Principal.usuario.getPermissoes().clear();
                                    
                while (rs.next()) {
                    AcoesUsuarios acoesUsuario = new AcoesUsuarios();

                    acoesUsuario.setIdAcaoUsuario(rs.getLong("id_acao_usuario"));
                    acoesUsuario.setCodAcao(rs.getLong("cod_acao"));
                    acoesUsuario.setCodUsuario(rs.getLong("cod_usuario"));
                    acoesUsuario.setIncluir(rs.getBoolean("incluir"));
                    acoesUsuario.setAlterar(rs.getBoolean("alterar"));
                    acoesUsuario.setExcluir(rs.getBoolean("excluir"));
                    acoesUsuario.setConsultar(rs.getBoolean("consultar"));

                    Principal.usuario.getPermissoes().add(acoesUsuario);
                }
            }
        } catch (SQLException eSQL) {
            System.err.println(eSQL);
        }
    } 
}
