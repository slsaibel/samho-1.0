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
public class ClientesTiposPlanosDAO extends ObjetoDAO implements InterfaceDAO {

    public ClientesTiposPlanosDAO() {
        super("CLIENTES_TIPOS_PLANOS");
    }

    public int getProximoID() {
        return super.getProximoID("id_cliente_tipo_plano");
    }
    
    public String getSQLSubstiturCampoDescricao(long idClienteTipoPlano){
        return super.getCampo(
                "planos_saude.descricao||' - '||tipos_planos.descricao",
                "planos_saude, tipos_planos, clientes_tipos_planos",
                "planos_saude.id_plano_saude = tipos_planos.cod_plano_saude"
                        + " AND tipos_planos.id_tipo_plano = clientes_tipos_planos.cod_tipo_plano"
                        + " AND clientes_tipos_planos.id_cliente_tipo_plano = " + idClienteTipoPlano,
                true);
    }
}
