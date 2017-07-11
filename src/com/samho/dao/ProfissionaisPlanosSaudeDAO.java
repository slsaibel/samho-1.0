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
 * @since  date 01/05/2017
 *
 * @version  revision 001.20170501 date 01/05/2017 author Saibel, Sergio Luís reason
 * permitir as operações com banco de dados. Esta classe serve para que o objeto
 * possa executar operaçoes de banco aparte.
 * Este tipo de classe extende o ObjetoDAO que é responsável por inserir, alterar
 * ou excluir um objeto do banco, bem como outras funcionalidades comuns a todos
 * os objetos.
 * 
 */
public class ProfissionaisPlanosSaudeDAO extends ObjetoDAO implements InterfaceDAO {

    public ProfissionaisPlanosSaudeDAO() {
        super("PROFISSIONAIS_PLANOS_SAUDE");
    }

    public int getProximoID() {
        return super.getProximoID("id_profissional_plano_saude");
    }
    
    public String getSQLSubstiturCampoDescricao(long idProfissionalPlanoSaude){
        return super.getCampo(
                "planos_saude.descricao",
                "planos_saude, profissionais_planos_saude",
                "planos_saude.id_plano_saude = profissionais_planos_saude.cod_plano_saude"
                        + " AND profissionais_planos_saude.id_profissional_plano_saude = " 
                        + idProfissionalPlanoSaude,
                true);
    }
}
