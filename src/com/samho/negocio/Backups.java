/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.samho.negocio;

import com.samho.apresentacao.IFrmProgress;
import com.samho.util.Formatacao;
import com.samho.util.Util;
import java.awt.Container;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;
import javax.swing.SwingWorker;

/**
 * Classe de Backups.
 *
 * @author Sergio
 * @since  date 26/10/2014
 *
 * @version  revision 001.20141026 date 26/10/2014 author Saibel, Sergio Luís reason
 * Instanciar um objeto com métodos e atributos necessários.
 */
public class Backups {

    // Declaração de atributos
    private String nomeBanco;
    private String nomeServidor;
    private String numPorta;
    private String usuario;
    private String senha;
    private String caminhoPgRestore;
    private String caminhoPgDump;
    private String arquivoBackup;

    private Properties properties;

    // Construtor padrão
    public Backups() {
        properties = new Properties();
    }

    public String carregarPropriedades() {
        String retorno = "";

        try {
            File file = new File("backup.properties");
            try (FileInputStream fis = new FileInputStream(file)) {
                properties.load(fis);
            }
        } catch (IOException ex) {
            retorno = "Não foi possível carregar as propriedades do backup.\n"
                    + "Motivo: " + ex.getMessage();
        }

        nomeBanco = properties.getProperty("backup.nomeBanco");
        nomeServidor = properties.getProperty("backup.nomeServidor");
        numPorta = properties.getProperty("backup.numPorta");
        usuario = properties.getProperty("backup.usuario");
        senha = properties.getProperty("backup.senha");
        caminhoPgRestore = properties.getProperty("backup.caminhoPgRestore");
        caminhoPgDump = properties.getProperty("backup.caminhoPgDump");
        arquivoBackup = gerarNomeArquivoBackup(
        properties.getProperty("backup.arquivoBackup"));

        return retorno;
    }

    /**
     * Para cada vez que for executado o backup um novo caminho deve ser gerado
     * para evitar substituição de arquivos existentes. Por isso o arquivo deve
     * ter a data e hora local ao final do nome.
     *
     * @param dir Diretório onde o backup deverá se guardado.
     * @return Nome do arquivo de backup.
     */
    private String gerarNomeArquivoBackup(String dir) {
        String retorno;

        File dir2 = new File(dir = dir.substring(
                0, dir.lastIndexOf("\\")) + "\\backups");
        if (!dir2.isDirectory()) {
            dir2.mkdirs();
        }

        retorno = (dir2 + "\\bkp_" + Formatacao.removerFormatacao( 
                Formatacao.ajustaDataAMD(Calendar.getInstance())) + "-" +
                Formatacao.removerFormatacao(Formatacao.getHoraAtual()) + ".tar");

        return retorno;
    }

    public String getCaminhoArquivo() {
        String retorno = arquivoBackup;
        for (int i = 0;; i++) {
            File arq = new File(retorno);
            if (!arq.exists()) {
                break;
            } else {
                if (retorno.indexOf('~') > 0) {
                    retorno = retorno.substring(0, retorno.lastIndexOf('~'));
                }
                retorno = (retorno + "~" + (i + 1));
            }
        }

        return retorno;
    }

    public String getCaminhoArquivo(String arquivoBackup) {
        String retorno = arquivoBackup;
        for (int i = 0;; i++) {
            File arq = new File(retorno);
            if (!arq.exists()) {
                break;
            } else {
                if (retorno.indexOf('~') > 0) {
                    retorno = retorno.substring(0, retorno.lastIndexOf('~'));
                }
                retorno = (retorno + "~" + (i + 1));
            }
        }

        return retorno;
    }

    public String descarregarPropriedades() throws IOException {
        String retorno = "";

        properties.setProperty("backup.nomeBanco", nomeBanco);
        properties.setProperty("backup.nomeServidor", nomeServidor);
        properties.setProperty("backup.numPorta", numPorta);
        properties.setProperty("backup.usuario", usuario);
        properties.setProperty("backup.senha", senha);
        properties.setProperty("backup.caminhoPgRestore", caminhoPgRestore);
        properties.setProperty("backup.caminhoPgDump", caminhoPgDump);
        // Salva propriedade somente com o diretório de backup
        arquivoBackup = arquivoBackup.substring(0, arquivoBackup.lastIndexOf('\\') + 1);
        properties.setProperty("backup.arquivoBackup", arquivoBackup);

        try {
            File file = new File("backup.properties");
            try (FileOutputStream fos = new FileOutputStream(file)) {
                properties.store(fos, "Configurações para backup de banco");
            }
        } catch (IOException ex) {
            retorno = "Não foi possível descarregar as propriedades do backup.\n"
                    + "Motivo: " + ex.getMessage();
        }

        return retorno;
    }

    public String gerarBackup(Container parent) {
        String retorno = "";

        try {
            List<String> comandos = new ArrayList<>();
            comandos.add("\"" + caminhoPgDump + "\"");
            comandos.add("-i");
            comandos.add("-h");
            comandos.add(nomeServidor);
            comandos.add("-p");
            comandos.add(numPorta);
            comandos.add("-U");
            comandos.add(usuario);
            comandos.add("-F");
            comandos.add("t");
            comandos.add("-b");
            comandos.add("-v");
            comandos.add("-f");
            comandos.add("\"" + arquivoBackup + "\"");
            comandos.add(nomeBanco);

            ProcessBuilder pb = new ProcessBuilder(comandos);
            pb.environment().put("PGPASSWORD", senha);
            pb.redirectErrorStream(true);

            final IFrmProgress iFrmProgress = new IFrmProgress();

            parent.getParent().remove(iFrmProgress);
            parent.getParent().add(iFrmProgress);

            Util.centralizar(parent.getParent(), iFrmProgress);

            iFrmProgress.setVisible(true);

            final Process process = pb.start();

            try (BufferedReader resultado = new BufferedReader(
                    new InputStreamReader(process.getInputStream()))) {
                SwingWorker worker = new SwingWorker() {
                    @Override
                    protected Object doInBackground() throws Exception {
                        String progresso;
                        while ((progresso = resultado.readLine()) != null) {
                            iFrmProgress.atualizarProgresso(progresso, false);
                        }
                        progresso = "Backup realizado com sucesso.";
                        iFrmProgress.atualizarProgresso(progresso, true);

                        return null;
                    }

                    @Override
                    protected void done() {
                        iFrmProgress.setVisible(false);
                    }
                };

                worker.execute();

                final int dcertExitCode = process.waitFor();
            }
        } catch (IOException | InterruptedException ex) {
            retorno = "Não foi possível fazer backup do banco.\n"
                    + "Motivo: " + ex.getMessage();
        }

        return retorno;
    }

    public String restaurarBackup(Container parent) {
        String retorno = "";

        try {
            List<String> comandos = new ArrayList<>();
            comandos.add("\"" + caminhoPgRestore + "\"");
            comandos.add("-c");
            comandos.add("-d");
            comandos.add(nomeBanco);
            comandos.add("-F");
            comandos.add("t");
            comandos.add("-v");
            comandos.add("-h");
            comandos.add(nomeServidor);
            comandos.add("-p");
            comandos.add(numPorta);
            comandos.add("-U");
            comandos.add(usuario);
            comandos.add("\"" + arquivoBackup + "\"");

            ProcessBuilder pb = new ProcessBuilder(comandos);
            pb.environment().put("PGPASSWORD", senha);
            pb.redirectErrorStream(true);

            final IFrmProgress iFrmProgress = new IFrmProgress();

            parent.getParent().remove(iFrmProgress);
            parent.getParent().add(iFrmProgress);

            Util.centralizar(parent.getParent(), iFrmProgress);

            iFrmProgress.setVisible(true);

            final Process process = pb.start();

            try (BufferedReader resultado = new BufferedReader(
                    new InputStreamReader(process.getInputStream()))) {
                SwingWorker worker = new SwingWorker() {
                    @Override
                    protected Object doInBackground() throws Exception {
                        String progresso;
                        while ((progresso = resultado.readLine()) != null) {
                            iFrmProgress.atualizarProgresso(progresso, false);
                        }
                        progresso = "Backup realizado com sucesso.";
                        iFrmProgress.atualizarProgresso(progresso, true);

                        return null;
                    }

                    @Override
                    protected void done() {
                        iFrmProgress.setVisible(false);
                    }
                };

                worker.execute();

                final int dcertExitCode = process.waitFor();
            }
        } catch (IOException | InterruptedException ex) {
            retorno = "Não foi possível fazer restore do banco.\n"
                    + "Motivo: " + ex.getMessage();
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
     * @return the caminhoPgDump
     */
    public String getCaminhoPgDump() {
        return caminhoPgDump;
    }

    /**
     * @param caminhoPgDump the caminhoPgDump to set
     */
    public void setCaminhoPgDump(String caminhoPgDump) {
        this.caminhoPgDump = caminhoPgDump;
    }

    /**
     * @return the arquivoBackup
     */
    public String getArquivoBackup() {
        return arquivoBackup;
    }

    /**
     * @param arquivoBackup the arquivoBackup to set
     */
    public void setArquivoBackup(String arquivoBackup) {

        this.arquivoBackup = arquivoBackup;
    }

    /**
     * @return the caminhoPgRestore
     */
    public String getCaminhoPgRestore() {
        return caminhoPgRestore;
    }

    /**
     * @param caminhoPgRestore the caminhoPgRestore to set
     */
    public void setCaminhoPgRestore(String caminhoPgRestore) {
        this.caminhoPgRestore = caminhoPgRestore;
    }
}
