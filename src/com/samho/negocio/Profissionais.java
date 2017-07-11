/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.samho.negocio;

import com.samho.dao.ProfissionaisDAO;
import com.samho.dao.DadosDAO;
import com.samho.dao.ObjetoDAO;
import java.util.Objects;

/**
 * Classe de Funcionários.
 *
 * @author Sergio
 * @since  date 20/04/2014
 *
 * @version  revision 001.20140420 date 20/04/2014 author Saibel, Sergio Luís reason
 * Instanciar um objeto com métodos e atributos necessários.
 */
public final class Profissionais extends Objeto implements InterfaceObj {

    // Declaração de atributos
    private long idProfissional;
    private long codPessoa;
    private boolean ativo;

    private ProfissionaisDAO profissionalDAO;

    // Construtor padrão
    public Profissionais() {
        idProfissional = -1;
        codPessoa = -1;
        ativo = false;
        
        profissionalDAO = new ProfissionaisDAO();

        adicionarCampos();
    }

    // Construtor
    public Profissionais(Profissionais profissional) {
        idProfissional = profissional.getIdProfissional();
        codPessoa = profissional.getCodPessoa();
        ativo = profissional.isAtivo();
        
        profissionalDAO = profissional.getProfissionalDAO();

        adicionarCampos();
    }

    // Construtor
    public Profissionais(int idProfissional, int codPessoa, boolean ativo) {
        this.idProfissional = idProfissional;
        this.codPessoa = codPessoa;
        this.ativo = ativo;
        
        profissionalDAO = new ProfissionaisDAO();

        adicionarCampos();
    }

    @Override
    public boolean equals(Object object) {
        boolean retorno = false;

        if (object instanceof Profissionais) {
            Profissionais other = (Profissionais) object;
            if (idProfissional == other.getIdProfissional()) {
                retorno = true;
            }
        }

        return retorno;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + (int) (this.idProfissional ^ (this.idProfissional >>> 32));
        hash = 59 * hash + (int) (this.codPessoa ^ (this.codPessoa >>> 32));
        hash = 59 * hash + (this.ativo ? 1 : 0);
        hash = 59 * hash + Objects.hashCode(this.profissionalDAO);
        return hash;
    }

    @Override
    public ObjetoDAO getObjetoDAO() {
        return profissionalDAO;
    }

    @Override
    public void adicionarCampos() {
        removerCampos();

        getObjetoDAO().addCampo(getDadosCodigo());
        getObjetoDAO().addCampo(new DadosDAO("cod_pessoa", "Cód. Pessoa",
                String.valueOf(codPessoa), DadosDAO.TIPO_LONG,
                DadosDAO.IS_IGUAL, DadosDAO.NAO_CHAVE));
        getObjetoDAO().addCampo(new DadosDAO("ativo", "Ativo",
                String.valueOf(ativo), DadosDAO.TIPO_BOOLEAN,
                DadosDAO.IS_IGUAL, DadosDAO.NAO_CHAVE));
    }

    /**
     * @return the dadosCodigo
     */
    @Override
    public DadosDAO getDadosCodigo() {
        return new DadosDAO("id_profissional", "Cód. Profissional",
                String.valueOf(idProfissional), DadosDAO.TIPO_LONG,
                DadosDAO.IS_IGUAL, DadosDAO.IS_CHAVE);
    }

    /**
     * @return the dadosDescricao
     */
    @Override
    public DadosDAO getDadosDescricao() {
        return new DadosDAO(
                profissionalDAO.getSQLSubstiturCampoDescricao(idProfissional), 
                "Profissional", "", DadosDAO.TIPO_STRING, DadosDAO.IS_IGUAL, 
                DadosDAO.NAO_CHAVE);
    }

    /**
     * @return the idProfissional
     */
    public long getIdProfissional() {
        return idProfissional;
    }

    /**
     * @param idProfissional the idProfissional to set
     */
    public void setIdProfissional(long idProfissional) {
        this.idProfissional = idProfissional;
    }

    /**
     * @return the codPessoa
     */
    public long getCodPessoa() {
        return codPessoa;
    }

    /**
     * @param codPessoa the codPessoa to set
     */
    public void setCodPessoa(long codPessoa) {
        this.codPessoa = codPessoa;
    }

    /**
     * @return the ativo
     */
    public boolean isAtivo() {
        return ativo;
    }

    /**
     * @param ativo the ativo to set
     */
    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    /**
     * @return the profissionalDAO
     */
    public ProfissionaisDAO getProfissionalDAO() {
        return profissionalDAO;
    }

    /**
     * @param profissionalDAO the profissionalDAO to set
     */
    public void setProfissionalDAO(ProfissionaisDAO profissionalDAO) {
        this.profissionalDAO = profissionalDAO;
    }

}
