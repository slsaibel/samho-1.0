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
 * @since  date 03/05/2017
 *
 * @version  revision 001.20170503 date 03/05/2017 author Saibel, Sergio Luís reason
 * permitir as operações com banco de dados para que um objeto possa ser
 * incluído, alterado, consultado ou excluído(inativado).
 *
 * A exclusão não é feita no banco de dados, para isso apenas o atributo
 * "situacao" deve ser setado.
 */
public class GlossarioDoencasMedicamentosDAO extends ObjetoDAO implements InterfaceDAO {

    public GlossarioDoencasMedicamentosDAO() {
        super("GLOSSARIO_DOENCA_MEDICAMENTOS");
    }

    public int getProximoID() {
        return super.getProximoID("id_glossario_doenca_medicamento");
    }

    public String getSQLSubstiturCampoDescricao(long idGlossarioDoencaMedicamento){
        return super.getCampo("glossario_doencas.descricao||' - '||medicamentos.descricao", 
                "glossario_doencas, medicamentos, glossario_doencas_medicamentos", 
                "glossario_doencas.id_glossario_doenca = glossario_doencas_medicamentos.cod_glossario_doenca"
                        + " AND medicamentos.id_medicamento = glossario_doencas_medicamentos.cod_medicamento"
                        + " AND glossario_doencas_medicamentos.id_glossario_doenca_medicamento = "
                        + idGlossarioDoencaMedicamento,
                true);
    }
}
