/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.samho.dao;

/**
 * Classe DAO para Objetos Tipados.
 *
 * @author Sergio
 * @since  date 02/05/2017
 *
 * @version  revision 001.20170502 date 02/05/2017 author Saibel, Sergio Luís reason
 * permitir as operações com banco de dados para que um objeto possa ser
 * incluído, alterado, consultado ou excluído(inativado).
 *
 * A exclusão não é feita no banco de dados, para isso apenas o atributo
 * "situacao" deve ser setado.
 */
public class ProntuariosSintomasApresentadosDAO extends ObjetoDAO implements InterfaceDAO {

    public ProntuariosSintomasApresentadosDAO() {
        super("PRONTUARIOS_SINTOMAS_APRESENTADOS");
    }

    public int getProximoID() {
        return super.getProximoID("id_prontuario_sintoma_apresentado");
    }

    public String getSQLSubstiturCampoDescricao(long idProntuarioSintomaApresentado){
        return super.getCampo("prontuarios.id_prontuario||' - '||sintomas_apresentados.descricao", 
                "prontuarios, sintomas_apresentados, prontuarios_sintomas_apresentados", 
                "prontuarios.id_prontuario = prontuarios_sintomas_apresentados.cod_prontuario"
                        + " AND sintomas_apresentados.id_sintoma_apresentado ="
                        + "     prontuarios_sintomas_apresentados.cod_sintoma_apresentado"
                        + " AND prontuarios_sintomas_apresentados.id_prontuario_sintoma_apresentado = "
                        + idProntuarioSintomaApresentado,
                true);
    }
}
