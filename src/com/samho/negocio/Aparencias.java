/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.samho.negocio;

import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * Classe de Objeto.
 *
 * @author Saibel, Sergio Luis
 * @since  date 31/03/2017
 *
 * @version  revision 001.20170331 date 31/03/2017 author Saibel, Sergio Luís reason
 * Instanciar um objeto com métodos e atributos necessários.
 */
public class Aparencias {

    // Declaração de atributos
    private String caminhoImagemFundo;
    private String lookAndFeel;
    private final UIManager.LookAndFeelInfo[] looks;

    private final Properties properties;

    // Construtor padrão
    public Aparencias() {
        this.looks = UIManager.getInstalledLookAndFeels();
        properties = new Properties();
    }

    public String carregarPropriedades() {
        String retorno = "";

        try {
            File file = new File("aparencia.properties");
            try (FileInputStream fis = new FileInputStream(file)) {
                properties.load(fis);
            }
        } catch (IOException ex) {
            retorno = "Não foi possível carregar as propriedades da aparência.\n"
                    + "Motivo: " + ex.getMessage();
        }

        caminhoImagemFundo = properties.getProperty("aparencia.caminhoImagemFundo");
        lookAndFeel = properties.getProperty("aparencia.LookAndFeel");

        return retorno;
    }

    public void aplicarAparenciaImagemFundoJLabel(JLabel lblImagemFundo) {
        if (!caminhoImagemFundo.equals("")) {
            ImageIcon imagemFundo = new ImageIcon(caminhoImagemFundo);

            lblImagemFundo.setIcon(new ImageIcon(
                    imagemFundo.getImage().getScaledInstance(lblImagemFundo.getWidth(),
                            lblImagemFundo.getHeight(), Image.SCALE_DEFAULT)));
        }
    }
    
    public void aplicarAparenciaLookAndFeel(JFrame jFrame) {
        if (!lookAndFeel.equals("")) {
            // aplica skin = LookAndFeel a todas as janelas
            for (UIManager.LookAndFeelInfo look : looks) {
                if (lookAndFeel.equals(look.getName())) {
                    try {
                        UIManager.setLookAndFeel(look.getClassName());
                        SwingUtilities.updateComponentTreeUI(jFrame);
                    }catch (ClassNotFoundException | InstantiationException |
                            IllegalAccessException | UnsupportedLookAndFeelException e) {
                    }
                }
            }
	} 
    }

    public String descarregarPropriedades() throws IOException {
        String retorno = "";

        properties.setProperty("aparencia.caminhoImagemFundo", caminhoImagemFundo);
        properties.setProperty("aparencia.LookAndFeel", lookAndFeel);
        
        try {
            File file = new File("aparencia.properties");
            try (FileOutputStream fos = new FileOutputStream(file)) {
                properties.store(fos, "Configurações para aparências do SAMHO");
            }
        } catch (IOException ex) {
            retorno = "Não foi possível descarregar as propriedades da aparência.\n"
                    + "Motivo: " + ex.getMessage();
        }

        return retorno;
    }

    /**
     * @return the caminhoImagemFundo
     */
    public String getCaminhoImagemFundo() {
        return caminhoImagemFundo;
    }

    /**
     * @param caminhoImagemFundo the caminhoImagemFundo to set
     */
    public void setCaminhoImagemFundo(String caminhoImagemFundo) {
        this.caminhoImagemFundo = caminhoImagemFundo;
    }

    /**
     * @return the lookAndFeel
     */
    public String getLookAndFeel() {
        return lookAndFeel;
    }

    /**
     * @param lookAndFeel the lookAndFeel to set
     */
    public void setLookAndFeel(String lookAndFeel) {
        this.lookAndFeel = lookAndFeel;
    }
}
