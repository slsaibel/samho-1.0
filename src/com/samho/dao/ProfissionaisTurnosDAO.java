/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.samho.dao;

/**
 * Classe DAO para Objetos Tipados.
 *
 * @author Saibel, Sergio Luis
 * @since  date 20/04/2017
 *
 * @version  revision 001.20170420 date 20/04/2017 author Saibel, Sergio Luís reason
 * permitir as operações com banco de dados. Esta classe serve para que o objeto
 * possa executar operaçoes de banco aparte.
 * Este tipo de classe extende o ObjetoDAO que é responsável por inserir, alterar
 * ou excluir um objeto do banco, bem como outras funcionalidades comuns a todos
 * os objetos.
 * 
 */
public class ProfissionaisTurnosDAO extends ObjetoDAO implements InterfaceDAO {

    public ProfissionaisTurnosDAO() {
        super("PROFISSIONAIS_TURNOS");
    }

    public int getProximoID() {
        return super.getProximoID("id_profissional_turno");
    }
    
    public String getSQLSubstiturCampoDescricao(long idProfissional){
        return super.getCampo("pessoas.nome||' '||pessoas.sobrenome", 
                "pessoas, profissionais, profissionais_turnos", 
                "pessoas.id_pessoa = profissionais.cod_pessoa"
                        + " AND profissionais.id_profissional = profissionais_turnos.cod_profissional"
                        + " AND profissionais_turnos.cod_proissional = " + idProfissional,
                true);
    }
}
