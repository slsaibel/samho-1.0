/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.samho.negocio;

import com.samho.dao.FuncionariosDAO;
import com.samho.dao.DadosDAO;
import com.samho.dao.ObjetoDAO;
import com.samho.util.Formatacao;
import java.util.Calendar;
import java.util.Objects;

/**
 * Classe de Funcionários.
 *
 * @author Sergio
 * @since  date 18/04/2017
 *
 * @version  revision 001.20170418 date 18/04/2017 author Saibel, Sergio Luís reason
 * Instanciar um objeto com métodos e atributos necessários.
 */
public final class Funcionarios extends Objeto implements InterfaceObj {

    // Declaração de atributos
    private long idFuncionario;
    private long codSituacao;
    private long codPessoa;
    private long codFuncao;
    private double vlrSalario;
    private int diasTrabalhadosMes;
    private double cargaHoraria;
    private Calendar dataAdmissao;
    private Calendar dataDemissao;
    private int numeroCLT;


    private FuncionariosDAO funcionarioDAO;

    // Construtor padrão
    public Funcionarios() {
        idFuncionario = -1;
        codSituacao = 0;
        codPessoa = -1;
        codFuncao = -1;
        vlrSalario = 0.00;
        diasTrabalhadosMes = 0;
        cargaHoraria = 0.00;
        dataAdmissao = Calendar.getInstance();
        dataDemissao = null;
        numeroCLT = 0;

        funcionarioDAO = new FuncionariosDAO();

        adicionarCampos();
    }

    // Construtor
    public Funcionarios(Funcionarios funcionario) {
        idFuncionario = funcionario.getIdFuncionario();
        codSituacao = funcionario.getCodSituacao();
        codPessoa = funcionario.getCodPessoa();
        codFuncao = funcionario.getCodFuncao();
        vlrSalario = funcionario.getVlrSalario();
        diasTrabalhadosMes = funcionario.getDiasTrabalhadosMes();
        cargaHoraria = funcionario.getCargaHoraria();
        dataAdmissao = funcionario.getDataAdmissao();
        dataDemissao = funcionario.getDataDemissao();
        numeroCLT = funcionario.getNumeroCLT();

        funcionarioDAO = funcionario.getFuncionarioDAO();

        adicionarCampos();
    }

    // Construtor
    public Funcionarios(int idFuncionario, int codSituacao, int codPessoa,
            int codFuncao, double vlrSalario, int diasTrabalhadosMes,
            double cargaHoraria, Calendar dataAdmissao, Calendar dataDemissao, 
            int numeroCLT) {
        this.idFuncionario = idFuncionario;
        this.codSituacao = codSituacao;
        this.codPessoa = codPessoa;
        this.codFuncao = codFuncao;
        this.vlrSalario = vlrSalario;
        this.diasTrabalhadosMes = diasTrabalhadosMes;
        this.cargaHoraria = cargaHoraria;
        this.dataAdmissao = dataAdmissao;
        this.dataDemissao = dataDemissao;
        this.numeroCLT = numeroCLT;

        funcionarioDAO = new FuncionariosDAO();

        adicionarCampos();
    }

    @Override
    public boolean equals(Object object) {
        boolean retorno = false;

        if (object instanceof Funcionarios) {
            Funcionarios other = (Funcionarios) object;
            if (idFuncionario == other.getIdFuncionario()) {
                retorno = true;
            }
        }

        return retorno;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash + (int) (this.idFuncionario ^ (this.idFuncionario >>> 32));
        hash = 11 * hash + (int) (this.codSituacao ^ (this.codSituacao >>> 32));
        hash = 11 * hash + (int) (this.codPessoa ^ (this.codPessoa >>> 32));
        hash = 11 * hash + (int) (this.codFuncao ^ (this.codFuncao >>> 32));
        hash = 11 * hash + (int) (Double.doubleToLongBits(this.vlrSalario) ^ (Double.doubleToLongBits(this.vlrSalario) >>> 32));
        hash = 11 * hash + this.diasTrabalhadosMes;
        hash = 11 * hash + (int) (Double.doubleToLongBits(this.cargaHoraria) ^ (Double.doubleToLongBits(this.cargaHoraria) >>> 32));
        hash = 11 * hash + Objects.hashCode(this.dataAdmissao);
        hash = 11 * hash + Objects.hashCode(this.dataDemissao);
        hash = 11 * hash + this.numeroCLT;
        hash = 11 * hash + Objects.hashCode(this.funcionarioDAO);
        return hash;
    }

    @Override
    public ObjetoDAO getObjetoDAO() {
        return funcionarioDAO;
    }

    @Override
    public void adicionarCampos() {
        removerCampos();

        getObjetoDAO().addCampo(getDadosCodigo());
        getObjetoDAO().addCampo(new DadosDAO("cod_situacao", "Cód. Situação",
                String.valueOf(codSituacao), DadosDAO.TIPO_LONG,
                DadosDAO.IS_IGUAL, DadosDAO.NAO_CHAVE));
        getObjetoDAO().addCampo(new DadosDAO("cod_pessoa", "Cód. Pessoa",
                String.valueOf(codPessoa), DadosDAO.TIPO_LONG,
                DadosDAO.IS_IGUAL, DadosDAO.NAO_CHAVE));
        getObjetoDAO().addCampo(new DadosDAO("cod_funcao", "Cód. Função",
                String.valueOf(codFuncao), DadosDAO.TIPO_LONG,
                DadosDAO.IS_IGUAL, DadosDAO.NAO_CHAVE));
        getObjetoDAO().addCampo(new DadosDAO("vlr_salario", "Vlr. Salário",
                String.valueOf(vlrSalario), DadosDAO.TIPO_DOUBLE,
                DadosDAO.IS_IGUAL, DadosDAO.NAO_CHAVE));
        getObjetoDAO().addCampo(new DadosDAO("dias_trabalhados_mes", 
                "Dias Trabalhados", String.valueOf(diasTrabalhadosMes), 
                DadosDAO.TIPO_INTEGER, DadosDAO.IS_IGUAL, DadosDAO.NAO_CHAVE));
        getObjetoDAO().addCampo(new DadosDAO("carga_horaria", "Carga Horária",
                String.valueOf(cargaHoraria), DadosDAO.TIPO_DOUBLE,
                DadosDAO.IS_IGUAL, DadosDAO.NAO_CHAVE));
        getObjetoDAO().addCampo(new DadosDAO("data_admissao", "Data Admissão",
                Formatacao.ajustaDataAMD(dataAdmissao), DadosDAO.TIPO_DATE,
                DadosDAO.IS_IGUAL, DadosDAO.NAO_CHAVE));
        getObjetoDAO().addCampo(new DadosDAO("data_demissao", "Data Demissão",
                Formatacao.ajustaDataAMD(dataDemissao), DadosDAO.TIPO_DATE,
                DadosDAO.IS_IGUAL, DadosDAO.NAO_CHAVE));
        getObjetoDAO().addCampo(new DadosDAO("numero_clt", "Numero CLT",
                String.valueOf(numeroCLT), DadosDAO.TIPO_INTEGER,
                DadosDAO.IS_IGUAL, DadosDAO.NAO_CHAVE));
        
        // Adicionando where de restrição ao administrador id = 0
        getObjetoDAO().addWhere(new DadosDAO(getObjetoDAO().getCampoID(), "", 
                "0", DadosDAO.TIPO_LONG, DadosDAO.IS_DIFERENTE, DadosDAO.IS_CHAVE));
    }

    /**
     * @return the dadosCodigo
     */
    @Override
    public DadosDAO getDadosCodigo() {
        return new DadosDAO("id_funcionario", "Cód. Funcionário",
                String.valueOf(idFuncionario), DadosDAO.TIPO_LONG,
                DadosDAO.IS_IGUAL, DadosDAO.IS_CHAVE);
    }

    /**
     * @return the dadosDescricao
     */
    @Override
    public DadosDAO getDadosDescricao() {
        return new DadosDAO(
                funcionarioDAO.getSQLSubstiturCampoDescricao(idFuncionario),
                "Funcionário", "", DadosDAO.TIPO_STRING, DadosDAO.IS_IGUAL, 
                DadosDAO.NAO_CHAVE);
    }

    /**
     * @return the idFuncionario
     */
    public long getIdFuncionario() {
        return idFuncionario;
    }

    /**
     * @param idFuncionario the idFuncionario to set
     */
    public void setIdFuncionario(long idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    /**
     * @return the codSituacao
     */
    public long getCodSituacao() {
        return codSituacao;
    }

    /**
     * @param codSituacao the codSituacao to set
     */
    public void setCodSituacao(long codSituacao) {
        this.codSituacao = codSituacao;
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
     * @return the codFuncao
     */
    public long getCodFuncao() {
        return codFuncao;
    }

    /**
     * @param codFuncao the codFuncao to set
     */
    public void setCodFuncao(long codFuncao) {
        this.codFuncao = codFuncao;
    }

    /**
     * @return the vlrSalario
     */
    public double getVlrSalario() {
        return vlrSalario;
    }

    /**
     * @param vlrSalario the vlrSalario to set
     */
    public void setVlrSalario(double vlrSalario) {
        this.vlrSalario = vlrSalario;
    }

    /**
     * @return the diasTrabalhadosMes
     */
    public int getDiasTrabalhadosMes() {
        return diasTrabalhadosMes;
    }

    /**
     * @param diasTrabalhadosMes the diasTrabalhadosMes to set
     */
    public void setDiasTrabalhadosMes(int diasTrabalhadosMes) {
        this.diasTrabalhadosMes = diasTrabalhadosMes;
    }

    /**
     * @return the cargaHoraria
     */
    public double getCargaHoraria() {
        return cargaHoraria;
    }

    /**
     * @param cargaHoraria the cargaHoraria to set
     */
    public void setCargaHoraria(double cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }

    /**
     * @return the dataAdmissao
     */
    public Calendar getDataAdmissao() {
        return dataAdmissao;
    }

    /**
     * @param dataAdmissao the dataAdmissao to set
     */
    public void setDataAdmissao(Calendar dataAdmissao) {
        this.dataAdmissao = dataAdmissao;
    }

    /**
     * @return the dataDemissao
     */
    public Calendar getDataDemissao() {
        return dataDemissao;
    }

    /**
     * @param dataDemissao the dataDemissao to set
     */
    public void setDataDemissao(Calendar dataDemissao) {
        this.dataDemissao = dataDemissao;
    }

    /**
     * @return the numeroCLT
     */
    public int getNumeroCLT() {
        return numeroCLT;
    }

    /**
     * @param numeroCLT the numeroCLT to set
     */
    public void setNumeroCLT(int numeroCLT) {
        this.numeroCLT = numeroCLT;
    }

    /**
     * @return the funcionarioDAO
     */
    public FuncionariosDAO getFuncionarioDAO() {
        return funcionarioDAO;
    }

    /**
     * @param funcionarioDAO the funcionarioDAO to set
     */
    public void setFuncionarioDAO(FuncionariosDAO funcionarioDAO) {
        this.funcionarioDAO = funcionarioDAO;
    }
}
