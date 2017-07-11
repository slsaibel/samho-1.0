/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.samho.negocio;

import com.samho.dao.TelefonesDAO;
import com.samho.dao.DadosDAO;
import com.samho.dao.ObjetoDAO;
import java.util.Objects;

/**
 * Classe de Objeto.
 *
 * @author Saibel, Sergio Luis
 * @since  date 31/03/2017
 *
 * @version  revision 001.20170331 date 31/03/2017 author Saibel, Sergio Luís reason
 * Instanciar um objeto com métodos e atributos necessários.
 */
public final class Telefones extends Objeto implements InterfaceObj {

    // Declaração de atributos
    private long idTelefone;
    private long codPessoa;
    private long codTipoTelefone;
    private long numero;
    private long ramal;
    private String contato;
    private boolean ativo;

    private TelefonesDAO telefonesDAO;

    // Construtor padrão
    public Telefones() {
        idTelefone = -1;
        codPessoa = -1;
        codTipoTelefone = -1;
        numero = 0;
        ramal = 0;
        contato = "";
        ativo = true;

        telefonesDAO = new TelefonesDAO();

        adicionarCampos();
    }

    // Construtor
    public Telefones(Telefones telefone) {
        idTelefone = telefone.getIdTelefone();
        codPessoa = telefone.getCodPessoa();
        codTipoTelefone = telefone.getCodTipoTelefone();
        numero = telefone.getNumero();
        ramal = telefone.getRamal();
        contato = telefone.getContato();
        ativo = telefone.isAtivo();

        telefonesDAO = telefone.getTelefonesDAO();

        adicionarCampos();
    }

    // Construtor
    public Telefones(long idEndereco, long codPessoa, long codTipoTelefone, 
            long numero, long ramal, String complemento, boolean ativo) {
        this.idTelefone = idEndereco;
        this.codPessoa = codPessoa;
        this.codTipoTelefone = codTipoTelefone;
        this.numero = numero;
        this.ramal = ramal;
        this.contato = complemento;
        this.ativo = ativo;

        telefonesDAO = new TelefonesDAO();

        adicionarCampos();
    }

    @Override
    public boolean equals(Object object) {
        boolean retorno = false;

        if (object instanceof Telefones) {
            Telefones other = (Telefones) object;
            if (idTelefone == other.getIdTelefone()) {
                retorno = true;
            }
        }

        return retorno;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + (int) (this.idTelefone ^ (this.idTelefone >>> 32));
        hash = 97 * hash + (int) (this.codPessoa ^ (this.codPessoa >>> 32));
        hash = 97 * hash + (int) (this.codTipoTelefone ^ (this.codTipoTelefone >>> 32));
        hash = 97 * hash + (int) (this.numero ^ (this.numero >>> 32));
        hash = 97 * hash + (int) (this.ramal ^ (this.ramal >>> 32));
        hash = 97 * hash + Objects.hashCode(this.contato);
        hash = 97 * hash + (this.ativo ? 1 : 0);
        hash = 97 * hash + Objects.hashCode(this.telefonesDAO);
        return hash;
    }

    @Override
    public ObjetoDAO getObjetoDAO() {
        return telefonesDAO;
    }

    @Override
    public void adicionarCampos() {
        removerCampos();

        getObjetoDAO().addCampo(getDadosCodigo());
        getObjetoDAO().addCampo(new DadosDAO("cod_pessoa", "Cód. Pessoa",
                String.valueOf(codPessoa), DadosDAO.TIPO_LONG,
                DadosDAO.IS_IGUAL, DadosDAO.NAO_CHAVE));
        getObjetoDAO().addCampo(new DadosDAO("cod_tipo_telefone",
                "Cód. Tipo Tel.", String.valueOf(codTipoTelefone),
                DadosDAO.TIPO_LONG, DadosDAO.IS_IGUAL, DadosDAO.NAO_CHAVE));
        getObjetoDAO().addCampo(new DadosDAO("numero", "Número",
                String.valueOf(numero), DadosDAO.TIPO_LONG,
                DadosDAO.IS_IGUAL, DadosDAO.NAO_CHAVE));
        getObjetoDAO().addCampo(new DadosDAO("ramal", "Ramal", String.valueOf(
                ramal), DadosDAO.TIPO_LONG, DadosDAO.IS_IGUAL, 
                DadosDAO.NAO_CHAVE));
        getObjetoDAO().addCampo(new DadosDAO("contato", "Contato",
                contato, DadosDAO.TIPO_STRING, DadosDAO.IS_IGUAL, 
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
        return new DadosDAO("id_telefone", "Cód. Telefone",
                String.valueOf(idTelefone), DadosDAO.TIPO_LONG,
                DadosDAO.IS_IGUAL, DadosDAO.IS_CHAVE);
    }

    /**
     * @return the dadosDescricao
     */
    @Override
    public DadosDAO getDadosDescricao() {
        return new DadosDAO("contato", "Contato",
                String.valueOf(contato), DadosDAO.TIPO_STRING,
                DadosDAO.IS_IGUAL, DadosDAO.NAO_CHAVE);
    }

    /**
     * @return the idTelefone
     */
    public long getIdTelefone() {
        return idTelefone;
    }

    /**
     * @param idTelefone the idTelefone to set
     */
    public void setIdTelefone(long idTelefone) {
        this.idTelefone = idTelefone;
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
     * @return the codTipoTelefone
     */
    public long getCodTipoTelefone() {
        return codTipoTelefone;
    }

    /**
     * @param codTipoTelefone the codTipoTelefone to set
     */
    public void setCodTipoTelefone(long codTipoTelefone) {
        this.codTipoTelefone = codTipoTelefone;
    }

    /**
     * @return the numero
     */
    public long getNumero() {
        return numero;
    }

    /**
     * @param numero the numero to set
     */
    public void setNumero(long numero) {
        this.numero = numero;
    }

    /**
     * @return the ramal
     */
    public long getRamal() {
        return ramal;
    }

    /**
     * @param ramal the ramal to set
     */
    public void setRamal(long ramal) {
        this.ramal = ramal;
    }

    /**
     * @return the contato
     */
    public String getContato() {
        return contato;
    }

    /**
     * @param contato the contato to set
     */
    public void setContato(String contato) {
        this.contato = contato;
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
     * @return the telefonesDAO
     */
    public TelefonesDAO getTelefonesDAO() {
        return telefonesDAO;
    }

    /**
     * @param telefonesDAO the telefonesDAO to set
     */
    public void setTelefonesDAO(TelefonesDAO telefonesDAO) {
        this.telefonesDAO = telefonesDAO;
    }
    
}
