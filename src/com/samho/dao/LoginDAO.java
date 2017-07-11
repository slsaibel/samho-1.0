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
 *
 * @author Sergio
 */
public class LoginDAO {
    public boolean checkLogin(String login, String senha) {
        boolean check = false;

        if (ConexoesDB.getInstance().getConnection() != null) {
            
            try {
                String sql = 
                        " SELECT "
                        + "      id_usuario, cod_funcionario, login, "
                        + "      senha, administrador "
                        + " FROM usuarios "
                        + "WHERE login = '" + login + "'" 
                        + "  AND senha = '" + senha + "';";

                PreparedStatement pst = 
                        ConexoesDB.getInstance().getConnection().prepareStatement(sql);

                ResultSet rs = pst.executeQuery();
                if (rs.isBeforeFirst()) {
                    if (rs.next()) {
                        check = true;
                        Principal.usuario.setIdUsuario(rs.getLong("id_usuario"));
                        Principal.usuario.setCodFuncionario(rs.getLong("cod_funcionario"));
                        Principal.usuario.setLogin(rs.getString("login"));
                        Principal.usuario.setSenha(rs.getString("senha"));
                        Principal.usuario.setAdministrador(rs.getBoolean("administrador"));
                    }
                }
                
                sql = 
                    " SELECT "
                    + "     id_acao_usuario, cod_acao, cod_usuario, "
                    + "     incluir, alterar, excluir, consultar "    
                    + " FROM acoes_usuarios WHERE cod_usuario = " 
                    + Principal.usuario.getIdUsuario() + " ORDER BY cod_acao;";
                
                pst = ConexoesDB.getInstance().getConnection().prepareStatement(sql);
                rs = pst.executeQuery();
                if (rs.isBeforeFirst()) {
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
        
        return check;
    }
}
