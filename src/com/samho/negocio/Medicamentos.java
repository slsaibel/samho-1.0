/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.samho.negocio;

import com.samho.dao.DadosDAO;
import com.samho.dao.MedicamentosDAO;
import com.samho.dao.ObjetoDAO;
import java.util.Objects;

/**
 * Classe de Objeto.
 *
 * @author Saibel, Sergio Luis
 * @since  date 01/05/2017
 *
 * @version  revision 001.201700501 date 01/05/2017 author Saibel, Sergio Luís reason
 * Instanciar um objeto com métodos e atributos necessários.
 */
public final class Medicamentos extends Objeto implements InterfaceObj {

    // Declaração de atributos
    private long idMedicamento;
    private long codPessoaJuridica;
    private String descricao;
    private String observacoes;
    private boolean ativo;

    private MedicamentosDAO medicamentoDAO;

    // Construtor padrão
    public Medicamentos() {
        idMedicamento = -1;
        codPessoaJuridica = -1;
        descricao = "";
        observacoes = "";
        ativo = true;

        medicamentoDAO = new MedicamentosDAO();

        adicionarCampos();
    }

    // Construtor
    public Medicamentos(Medicamentos medicamento) {
        idMedicamento = medicamento.getIdMedicamento();
        codPessoaJuridica = medicamento.getCodPessoaJuridica();
        descricao = medicamento.getDescricao();
        observacoes = medicamento.getObservacoes();
        ativo = medicamento.isAtivo();

        medicamentoDAO = medicamento.getMedicamentoDAO();

        adicionarCampos();
    }

    // Construtor
    public Medicamentos(long idMedicamento, long codPessoaJuridica, 
            String descricao, String observacoes, boolean ativo) {
        this.idMedicamento = idMedicamento;
        this.codPessoaJuridica = codPessoaJuridica;
        this.descricao = descricao;
        this.observacoes = observacoes;
        this.ativo = ativo;

        this.medicamentoDAO = new MedicamentosDAO();

        adicionarCampos();
    }

    @Override
    public boolean equals(Object object) {
        boolean retorno = false;

        if (object instanceof Medicamentos) {
            Medicamentos other = (Medicamentos) object;
            if (idMedicamento == other.getIdMedicamento()) {
                retorno = true;
            }
        }

        return retorno;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + (int) (this.idMedicamento ^ (this.idMedicamento >>> 32));
        hash = 37 * hash + (int) (this.codPessoaJuridica ^ (this.codPessoaJuridica >>> 32));
        hash = 37 * hash + Objects.hashCode(this.descricao);
        hash = 37 * hash + Objects.hashCode(this.observacoes);
        hash = 37 * hash + (this.ativo ? 1 : 0);
        hash = 37 * hash + Objects.hashCode(this.medicamentoDAO);
        return hash;
    }

    @Override
    public ObjetoDAO getObjetoDAO() {
        return medicamentoDAO;
    }

    @Override
    public void adicionarCampos() {
        removerCampos();

        getObjetoDAO().addCampo(getDadosCodigo());
        getObjetoDAO().addCampo(new DadosDAO("cod_pessoa_juridica", 
                "Cód. Pessoa Jurídica", String.valueOf(codPessoaJuridica), 
                DadosDAO.TIPO_LONG, DadosDAO.IS_IGUAL, DadosDAO.NAO_CHAVE));
        getObjetoDAO().addCampo(getDadosDescricao());
        getObjetoDAO().addCampo(new DadosDAO("observacoes", "Observação",
                observacoes, DadosDAO.TIPO_STRING, DadosDAO.IS_IGUAL, 
                DadosDAO.NAO_CHAVE));
        getObjetoDAO().addCampo(new DadosDAO("ativo", "Ativo",
                String.valueOf(ativo), DadosDAO.TIPO_BOOLEAN, DadosDAO.IS_IGUAL, 
                DadosDAO.NAO_CHAVE));
    }

    /**
     * @return the dadosCodigo
     */
    @Override
    public DadosDAO getDadosCodigo() {
        return new DadosDAO("id_medicamento", "Cód. Medicamento",
                String.valueOf(idMedicamento), DadosDAO.TIPO_LONG, DadosDAO.IS_IGUAL, 
                DadosDAO.IS_CHAVE);
    }

    /**
     * @return the dadosDescricao
     */
    @Override
    public DadosDAO getDadosDescricao() {
        return new DadosDAO("descricao", "Descrição", descricao,
                DadosDAO.TIPO_STRING, DadosDAO.IS_IGUAL, DadosDAO.NAO_CHAVE);
    }

    /**
     * @return the idMedicamento
     */
    public long getIdMedicamento() {
        return idMedicamento;
    }

    /**
     * @param idMedicamento the idMedicamento to set
     */
    public void setIdMedicamento(long idMedicamento) {
        this.idMedicamento = idMedicamento;
    }

    /**
     * @return the codPessoaJuridica
     */
    public long getCodPessoaJuridica() {
        return codPessoaJuridica;
    }

    /**
     * @param codPessoaJuridica the codPessoaJuridica to set
     */
    public void setCodPessoaJuridica(long codPessoaJuridica) {
        this.codPessoaJuridica = codPessoaJuridica;
    }

    /**
     * @return the descricao
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * @param descricao the descricao to set
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    /**
     * @return the observacoes
     */
    public String getObservacoes() {
        return observacoes;
    }

    /**
     * @param observacoes the observacoes to set
     */
    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
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
     * @return the medicamentoDAO
     */
    public MedicamentosDAO getMedicamentoDAO() {
        return medicamentoDAO;
    }

    /**
     * @param medicamentoDAO the medicamentoDAO to set
     */
    public void setMedicamentoDAO(MedicamentosDAO medicamentoDAO) {
        this.medicamentoDAO = medicamentoDAO;
    }
}
