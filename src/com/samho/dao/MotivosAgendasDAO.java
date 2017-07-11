/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.samho.dao;

/**
 * Classe DAO para Objetos Tipados.
 *
 * @author Saibel, Sergio Luis
 * @since  date 22/04/2017
 *
 * @version  revision 001.20170422 date 22/04/2017 author Saibel, Sergio Luís reason
 * permitir as operações com banco de dados. Esta classe serve para que o objeto
 * possa executar operaçoes de banco aparte.
 * Este tipo de classe extende o ObjetoDAO que é responsável por inserir, alterar
 * ou excluir um objeto do banco, bem como outras funcionalidades comuns a todos
 * os objetos.
 * 
 */
public class MotivosAgendasDAO extends ObjetoDAO implements InterfaceDAO {

    public MotivosAgendasDAO() {
        super("MOTIVOS_AGENDAS");
    }

    public int getProximoID() {
        return super.getProximoID("id_motivo_agenda");
    }
}
