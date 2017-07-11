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
public class ProntuariosMedicamentosDAO extends ObjetoDAO implements InterfaceDAO {

    public ProntuariosMedicamentosDAO() {
        super("PRONTUARIOS_MEDICAMENTOS");
    }

    public int getProximoID() {
        return super.getProximoID("id_prontuario_medicamento");
    }

    public String getSQLSubstiturCampoDescricao(long idProntuarioMedicamento){
        return super.getCampo("prontuarios.id_prontuario||' - '||medicamentos.descricao", 
                "prontuarios, medicamentos, prontuarios_medicamentos", 
                "prontuarios.id_prontuario = prontuarios_medicamentos.cod_prontuario"
                        + " AND medicamentos.id_medicamento = prontuarios_medicamentos.cod_medicamento"
                        + " AND prontuarios_medicamentos.id_prontuario_medicamento = " + idProntuarioMedicamento,
                true);
    }
}
