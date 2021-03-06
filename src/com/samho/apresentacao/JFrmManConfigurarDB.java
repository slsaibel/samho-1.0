/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.samho.apresentacao;

import com.samho.negocio.Aparencias;
import com.samho.util.JTextFieldDocument;
import com.samho.util.Util;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import javax.swing.JOptionPane;


/**
 *
 * @author Sergio
 */
public final class JFrmManConfigurarDB extends javax.swing.JFrame {

    // Declaração de atributos
    private final Properties properties;

    // Construtor
    public JFrmManConfigurarDB() {
        initComponents();

        // Permite que somente números sejam informados neste campo
        txtNumPorta.setDocument(new JTextFieldDocument(true));

        properties = new Properties();
        
        carregarPropriedades();
        setAparenciaPage();
    }
    
    public void carregarPropriedades() {
        try {
            File file = new File("banco.properties");
            try (FileInputStream fis = new FileInputStream(file)) {
                properties.load(fis);
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                    "Não foi possível carregar as propriedades do banco.\n"
                    + "Motivo: " + ex.getMessage());
        }

        txtNomeBanco.setText(properties.getProperty("banco.nomeBanco"));
        txtDriverDB.setText(properties.getProperty("banco.driverDB"));
        txtJDBC.setText(properties.getProperty("banco.jdbcDB"));
        txtNomeServidor.setText(properties.getProperty("banco.nomeServidor"));
        txtNumPorta.setText(properties.getProperty("banco.numPorta"));
        txtUsuario.setText(properties.getProperty("banco.usuario"));
        txtSenha.setText(properties.getProperty("banco.senha"));
    }

    public void descarregarPropriedades() throws IOException {
        properties.setProperty("banco.nomeBanco", txtNomeBanco.getText());
        properties.setProperty("banco.driverDB", txtDriverDB.getText());
        properties.setProperty("banco.jdbcDB", txtJDBC.getText());
        properties.setProperty("banco.nomeServidor", txtNomeServidor.getText());
        properties.setProperty("banco.numPorta", txtNumPorta.getText());
        properties.setProperty("banco.usuario", txtUsuario.getText());
        properties.setProperty("banco.senha", txtSenha.getText());

        try {
            File file = new File("banco.properties");
            try (FileOutputStream fos = new FileOutputStream(file)) {
                properties.store(fos, "Configurações para banco de dados");
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                    "Não foi possível descarregar as propriedades do banco.\n"
                    + "Motivo: " + ex.getMessage());
        }
    }
    
    private void setAparenciaPage() {
        final Aparencias aparencias = new Aparencias();
        aparencias.carregarPropriedades();
        aparencias.aplicarAparenciaLookAndFeel(this);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tablManutencao = new javax.swing.JTabbedPane();
        pnlGeral = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        txtNomeBanco = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtDriverDB = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtJDBC = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtNomeServidor = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        txtNumPorta = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtUsuario = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtSenha = new javax.swing.JPasswordField();
        pnlBotoes = new javax.swing.JPanel();
        btnConfirmar = new javax.swing.JButton();
        btnAjudar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        mnuBarPrincipal = new javax.swing.JMenuBar();
        mnuArquivos = new javax.swing.JMenu();
        mnuAjuda = new javax.swing.JMenu();

        setTitle("Configuração da Base de Dados - SAMHO_v1.0");
        setMaximumSize(new java.awt.Dimension(720, 265));
        setMinimumSize(new java.awt.Dimension(720, 265));
        setName(""); // NOI18N
        setPreferredSize(new java.awt.Dimension(720, 265));
        setResizable(false);
        getContentPane().setLayout(null);

        tablManutencao.setMinimumSize(new java.awt.Dimension(713, 245));
        tablManutencao.setName(""); // NOI18N

        pnlGeral.setLayout(null);

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel6.setText("Nome Banco");
        pnlGeral.add(jLabel6);
        jLabel6.setBounds(64, 14, 69, 14);

        txtNomeBanco.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        pnlGeral.add(txtNomeBanco);
        txtNomeBanco.setBounds(138, 11, 500, 20);

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel12.setText("Driver JDBC");
        pnlGeral.add(jLabel12);
        jLabel12.setBounds(67, 40, 66, 14);

        txtDriverDB.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        pnlGeral.add(txtDriverDB);
        txtDriverDB.setBounds(138, 37, 500, 20);

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel11.setText("JDBC");
        pnlGeral.add(jLabel11);
        jLabel11.setBounds(105, 66, 28, 14);

        txtJDBC.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        pnlGeral.add(txtJDBC);
        txtJDBC.setBounds(138, 63, 500, 20);

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel7.setText("Nome Servidor");
        pnlGeral.add(jLabel7);
        jLabel7.setBounds(50, 92, 83, 14);

        txtNomeServidor.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        pnlGeral.add(txtNomeServidor);
        txtNomeServidor.setBounds(138, 89, 320, 20);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setText("Núm. Porta");
        pnlGeral.add(jLabel1);
        jLabel1.setBounds(500, 92, 62, 14);

        txtNumPorta.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        pnlGeral.add(txtNumPorta);
        txtNumPorta.setBounds(568, 89, 70, 20);

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel8.setText("Usuário");
        pnlGeral.add(jLabel8);
        jLabel8.setBounds(90, 118, 43, 14);

        txtUsuario.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        pnlGeral.add(txtUsuario);
        txtUsuario.setBounds(138, 115, 200, 20);

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel9.setText("Senha");
        pnlGeral.add(jLabel9);
        jLabel9.setBounds(399, 118, 35, 14);

        txtSenha.setText("jPasswordField1");
        pnlGeral.add(txtSenha);
        txtSenha.setBounds(438, 115, 200, 20);

        tablManutencao.addTab("Geral", pnlGeral);

        getContentPane().add(tablManutencao);
        tablManutencao.setBounds(0, 0, 720, 175);

        pnlBotoes.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        pnlBotoes.setMaximumSize(new java.awt.Dimension(713, 238));
        pnlBotoes.setMinimumSize(new java.awt.Dimension(713, 238));
        pnlBotoes.setName("pnlBotao"); // NOI18N
        pnlBotoes.setPreferredSize(new java.awt.Dimension(713, 238));

        btnConfirmar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/confirmar.png"))); // NOI18N
        btnConfirmar.setText("Ok");
        btnConfirmar.setToolTipText("");
        btnConfirmar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnConfirmar.setMargin(new java.awt.Insets(2, 5, 2, 5));
        btnConfirmar.setMaximumSize(new java.awt.Dimension(70, 20));
        btnConfirmar.setMinimumSize(new java.awt.Dimension(70, 20));
        btnConfirmar.setName("btnConfirmar"); // NOI18N
        btnConfirmar.setPreferredSize(new java.awt.Dimension(85, 25));
        btnConfirmar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfirmarActionPerformed(evt);
            }
        });

        btnAjudar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/ajudar.png"))); // NOI18N
        btnAjudar.setToolTipText("");
        btnAjudar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAjudar.setMargin(new java.awt.Insets(5, 5, 5, 5));
        btnAjudar.setMaximumSize(new java.awt.Dimension(70, 20));
        btnAjudar.setMinimumSize(new java.awt.Dimension(70, 20));
        btnAjudar.setName("btnAjudar"); // NOI18N
        btnAjudar.setPreferredSize(new java.awt.Dimension(85, 25));
        btnAjudar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAjudarActionPerformed(evt);
            }
        });

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/cancelar.png"))); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.setToolTipText("");
        btnCancelar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCancelar.setMargin(new java.awt.Insets(2, 5, 2, 5));
        btnCancelar.setMaximumSize(new java.awt.Dimension(70, 20));
        btnCancelar.setMinimumSize(new java.awt.Dimension(70, 20));
        btnCancelar.setName("btnCancelar"); // NOI18N
        btnCancelar.setPreferredSize(new java.awt.Dimension(85, 25));
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlBotoesLayout = new javax.swing.GroupLayout(pnlBotoes);
        pnlBotoes.setLayout(pnlBotoesLayout);
        pnlBotoesLayout.setHorizontalGroup(
            pnlBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBotoesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnAjudar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnConfirmar, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12))
        );
        pnlBotoesLayout.setVerticalGroup(
            pnlBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBotoesLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(pnlBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnConfirmar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnlBotoesLayout.createSequentialGroup()
                        .addGroup(pnlBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnAjudar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        getContentPane().add(pnlBotoes);
        pnlBotoes.setBounds(0, 179, 720, 38);

        mnuArquivos.setText("Arquivo");
        mnuBarPrincipal.add(mnuArquivos);

        mnuAjuda.setText("Ajuda");
        mnuBarPrincipal.add(mnuAjuda);

        setJMenuBar(mnuBarPrincipal);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAjudarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAjudarActionPerformed
        Util.ajudar("JFrmConfigurarDB");
    }//GEN-LAST:event_btnAjudarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirmarActionPerformed
        try {
            descarregarPropriedades();
        } catch (IOException ex) {
           JOptionPane.showMessageDialog(this.getParent(), ex);
        }
        
        this.dispose();
    }//GEN-LAST:event_btnConfirmarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAjudar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnConfirmar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu mnuAjuda;
    private javax.swing.JMenu mnuArquivos;
    private javax.swing.JMenuBar mnuBarPrincipal;
    private javax.swing.JPanel pnlBotoes;
    private javax.swing.JPanel pnlGeral;
    private javax.swing.JTabbedPane tablManutencao;
    private javax.swing.JTextField txtDriverDB;
    private javax.swing.JTextField txtJDBC;
    private javax.swing.JTextField txtNomeBanco;
    private javax.swing.JTextField txtNomeServidor;
    private javax.swing.JTextField txtNumPorta;
    private javax.swing.JPasswordField txtSenha;
    private javax.swing.JTextField txtUsuario;
    // End of variables declaration//GEN-END:variables

}
