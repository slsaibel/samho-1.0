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
public class GlossarioDoencasSintomasApresentadosDAO extends ObjetoDAO implements InterfaceDAO {

    public GlossarioDoencasSintomasApresentadosDAO() {
        super("GLOSSARIO_DOENCAS_SINTOMAS_APRESENTADOS");
    }

    public int getProximoID() {
        return super.getProximoID("id_glossario_doenca_sintoma_apresentado");
    }
    
    public String getSQLSubstiturCampoDescricao(long idGlossarioDoencaSintomaApresentado){
        return super.getCampo("glossario_doencas.descricao||' - '||sintomas_apresentados.descricao", 
                "glossario_doencas, sintomas_apresentados, glossario_doencas_sintomas_apresentados", 
                "glossario_doencas.id_glossario_doenca = glossario_doencas_sintomas_apresentados.cod_glossario_doenca"
                        + " AND sintomas_apresentados.id_sintoma_apresentado ="
                        + "     glossario_doencas_sintomas_apresentados.cod_sintoma_apresentado"
                        + " AND glossario_doencas_sintomas_apresentados.id_glossario_doenca_sintoma_apresentado = "
                        + idGlossarioDoencaSintomaApresentado,
                true);
    }
}
