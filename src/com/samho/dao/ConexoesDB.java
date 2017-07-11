/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.samho.dao;

import com.samho.util.Registrador;
import com.samho.apresentacao.JFrmManConfigurarDB;
import com.samho.util.Util;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import javax.swing.JComboBox;

/**
 * Classe de manipulação de conexões com o banco de dados.
 * 
 * @author Saibel, Sergio Luis
 * @since  date 31/03/2017
 *
 * @version  revision 001.20170331 date 31/03/2017 author Saibel, Sergio Luís reason
 * permitir as operações com banco de dados. Esta classe serve para conecões e
 * instâncias de banco.
 * Possui metodos para informar os bancos de dados possíveis de conexão.
 * 
 */
public final class ConexoesDB {

    // Declaração de atributos
    private String nomeBanco;
    private String driverDB;
    private String jdbcDB;
    private String nomeServidor;
    private String numPorta;
    private String usuario;
    private String senha;

    private final Properties properties;

    private static ConexoesDB instancia = null;
    private Connection conexao = null;

    // Construtor padrão
    public ConexoesDB() {
        properties = new Properties();

        conectarDB();
    }

    public String carregarPropriedades() {
        String retorno = "";

        try {
            File file = new File("banco.properties");
            try (FileInputStream fis = new FileInputStream(file)) {
                properties.load(fis);
            }
        } catch (IOException ex) {
            retorno = "Não foi possível carregar as propriedades do banco.\n"
                    + "Motivo: " + ex.getMessage();
        }

        nomeBanco = properties.getProperty("banco.nomeBanco");
        driverDB = properties.getProperty("banco.driverDB");
        jdbcDB = properties.getProperty("banco.jdbcDB");
        nomeServidor = properties.getProperty("banco.nomeServidor");
        numPorta = properties.getProperty("banco.numPorta");
        usuario = properties.getProperty("banco.usuario");
        senha = properties.getProperty("banco.senha");

        return retorno;
    }

    public String descarregarPropriedades() throws IOException {
        String retorno = "";

        properties.setProperty("banco.nomeBanco", nomeBanco);
        properties.setProperty("banco.driverDB", driverDB);
        properties.setProperty("banco.jdbcDB", jdbcDB);
        properties.setProperty("banco.nomeServidor", nomeServidor);
        properties.setProperty("banco.numPorta", numPorta);
        properties.setProperty("banco.usuario", usuario);
        properties.setProperty("banco.senha", senha);

        try {
            File file = new File("banco.properties");
            try (FileOutputStream fos = new FileOutputStream(file)) {
                properties.store(fos, "Configurações para banco de dados");
            }
        } catch (IOException ex) {
            retorno = "Não foi possível descarregar as propriedades do banco.\n"
                    + "Motivo: " + ex.getMessage();
        }

        return retorno;
    }

    public String conectarDB() {
        String retorno = "";
        try {
            // Carrega as propriedades do banco
            carregarPropriedades();

            // Carrega Driver do Banco de Dados
            Class.forName(driverDB);

            if (usuario.length() != 0) {
                // conexão COM usuário e senha
                conexao = DriverManager.getConnection("jdbc:" + jdbcDB + "://"
                        + nomeServidor + ":" + numPorta + "/" + nomeBanco,
                        usuario, senha);
            } else {
                // conexão SEM usuário e senha
                conexao = DriverManager.getConnection(
                        jdbcDB + "//" + nomeServidor + ":" + numPorta + "/"
                        + nomeBanco);
            }
        } catch (ClassNotFoundException | SQLException ex) {
            retorno = "Não foi possível conectar ao banco.\nMotivo: " + ex;
        }

        return retorno;
    }

    // Retorna instância
    public static ConexoesDB getInstance() {
        if (instancia == null) {
            instancia = new ConexoesDB();
        }
        return instancia;
    }

    // Retorna conexão
    public Connection getConnection() {
        if (conexao == null) {
            
            JFrmManConfigurarDB jFrmManConfigurarDB = new JFrmManConfigurarDB();
            
            Util.centralizar(jFrmManConfigurarDB);
            
            jFrmManConfigurarDB.setVisible(true);
            throw new RuntimeException("conexao==null");
        }
        return conexao;
    }

    // Efetua fechamento da conexão
    public void shutDown() {
        try {
            conexao.close();
            instancia = null;
            conexao = null;
        } catch (SQLException eSQL) {
            System.err.println(eSQL);
        }
    }
    
    public String popularJComboBoxDB(JComboBox combo, String usuario) {
        String retorno = null;

        Registrador registrador = new Registrador();
        registrador.setCodigo(0);
        registrador.setDescricao("Selecionar...");
        combo.addItem(registrador);

        try {
            Statement st
                    = ConexoesDB.getInstance().getConnection().createStatement();

            String sql
                    = "SELECT pd.datname, pu.usename"
                    + "  FROM pg_database pd, pg_user pu"
                    + " WHERE pu.usesysid = pd.datdba "
                    + "   AND pu.usename = '" + usuario + "'";

            ResultSet rs = st.executeQuery(sql);
            int itemSelecionado = 0;
            if (rs.isBeforeFirst()) {
                int cont = 1;
                while (rs.next()) {
                    registrador = new Registrador();
                    registrador.setCodigo(cont);
                    registrador.setDescricao(rs.getString(1));
                    
                    if(registrador.getDescricao().equals(nomeBanco)){
                        itemSelecionado = cont;
                    }

                    combo.addItem(registrador);
                    
                    cont++;
                }
            }
            
            combo.setSelectedIndex(itemSelecionado);
        } catch (SQLException SQLe) {
            retorno = "Erro ao popular combo: " + SQLe;
        }

        return retorno;
    }

    /**
     * @return the nomeBanco
     */
    public String getNomeBanco() {
        return nomeBanco;
    }

    /**
     * @param nomeBanco the nomeBanco to set
     */
    public void setNomeBanco(String nomeBanco) {
        this.nomeBanco = nomeBanco;
    }

    /**
     * @return the dirver
     */
    public String getDriverDB() {
        return driverDB;
    }

    /**
     * @param driverDB the dirver to set
     */
    public void setDriverDB(String driverDB) {
        this.driverDB = driverDB;
    }

    /**
     * @return the jdbc
     */
    public String getJdbcDB() {
        return jdbcDB;
    }

    /**
     * @param jdbcDB the jdbc to set
     */
    public void setJdbcDB(String jdbcDB) {
        this.jdbcDB = jdbcDB;
    }

    /**
     * @return the nomeServidor
     */
    public String getNomeServidor() {
        return nomeServidor;
    }

    /**
     * @param nomeServidor the nomeServidor to set
     */
    public void setNomeServidor(String nomeServidor) {
        this.nomeServidor = nomeServidor;
    }

    /**
     * @return the numPorta
     */
    public String getNumPorta() {
        return numPorta;
    }

    /**
     * @param numPorta the numPorta to set
     */
    public void setNumPorta(String numPorta) {
        this.numPorta = numPorta;
    }

    /**
     * @return the usuario
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    /**
     * @return the senha
     */
    public String getSenha() {
        return senha;
    }

    /**
     * @param senha the senha to set
     */
    public void setSenha(String senha) {
        this.senha = senha;
    }

    /**
     * @return the properties
     */
    public Properties getProperties() {
        return properties;
    }
}
