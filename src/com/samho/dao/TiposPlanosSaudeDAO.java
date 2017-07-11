/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.samho.dao;

/**
 * Classe DAO para Objetos Tipados.
 *
 * @author Saibel, Sergio Luis
 * @since  date 30/04/2017
 *
 * @version  revision 001.20170430 date 30/04/2017 author Saibel, Sergio Luís reason
 * permitir as operações com banco de dados. Esta classe serve para que o objeto
 * possa executar operaçoes de banco aparte.
 * Este tipo de classe extende o ObjetoDAO que é responsável por inserir, alterar
 * ou excluir um objeto do banco, bem como outras funcionalidades comuns a todos
 * os objetos.
 * 
 */
public class TiposPlanosSaudeDAO extends ObjetoDAO implements InterfaceDAO {

    public TiposPlanosSaudeDAO() {
        super("TIPOS_PLANOS");
    }

    public int getProximoID() {
        return super.getProximoID("id_tipo_plano");
    }
}
