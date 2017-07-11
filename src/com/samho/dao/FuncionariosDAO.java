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
 * @since  date 04/04/2017
 *
 * @version  revision 001.20170404 date 04/04/2017 author Saibel, Sergio Luís reason
 * permitir as operações com banco de dados. Esta classe serve para que o objeto
 * possa executar operaçoes de banco aparte.
 * Este tipo de classe extende o ObjetoDAO que é responsável por inserir, alterar
 * ou excluir um objeto do banco, bem como outras funcionalidades comuns a todos
 * os objetos.
 * 
 */
public class FuncionariosDAO extends ObjetoDAO implements InterfaceDAO {

    public FuncionariosDAO() {
        super("FUNCIONARIOS");
    }

    public int getProximoID() {
        return super.getProximoID("id_funcionario");
    }
    
    public String getSQLSubstiturCampoDescricao(long idFuncionario){
        return super.getCampo("pessoas.nome||' '||pessoas.sobrenome", 
                "pessoas, funcionarios", "pessoas.id_pessoa = funcionarios.cod_pessoa"
                        + " AND funcionarios.id_funcionario = " + idFuncionario, true);
    }
}
