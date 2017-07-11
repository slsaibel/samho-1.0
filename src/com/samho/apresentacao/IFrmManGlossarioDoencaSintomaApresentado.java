/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.samho.apresentacao;

import com.samho.negocio.GlossarioDoencasSintomasApresentados;
import com.samho.negocio.GlossarioDoencas;
import com.samho.negocio.SintomasApresentados;
import com.samho.negocio.Principal;
import com.samho.util.JTextFieldDocument;
import com.samho.util.Util;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;

/**
 * Classe para visualização de objetos do tipo FrmManutencao.
 *
 * @author Saibel, Sergio Luis
 * @since  date 02/05/2017
 *
 * @version  revision 001.20170502 date 02/05/2017 author Saibel, Sérgio Luis reason
 * Conseguir instanciar um objeto do tipo FrmMaunutenção com os atributos e
 * métodos necessários. Esta classe tem como objetivo manipular os objetos 
 * definidos e ou obtidos a fim de manipular informações.
 * Esta extende um JInternalFrame ou seja necessita de um Frame como pai.
 * Métodos principais: Definição, Obtenção e Visualização do objeto.
 * 
 */
public final class IFrmManGlossarioDoencaSintomaApresentado extends JInternalFrame {

    // Declaração de atributos
    private GlossarioDoencasSintomasApresentados glossarioDoencaSintomaApresentado;

    // Construtor
    public IFrmManGlossarioDoencaSintomaApresentado(
            GlossarioDoencasSintomasApresentados glossarioDoencaSintomaApresentado) 
            throws Exception {
        initComponents();
        
        Util.limparCampos(rootPane);

        this.glossarioDoencaSintomaApresentado = glossarioDoencaSintomaApresentado;

        // Permite que somente números sejam informados neste campo
        txtCodigo.setDocument(new JTextFieldDocument(true));
        txtCodSintomaApresentado.setDocument(new JTextFieldDocument(true));
        txtCodGlossario.setDocument(new JTextFieldDocument(true));
        txtDiasComSintoma.setDocument(new JTextFieldDocument(true));

        carregarObjetoIFrmManutencao(glossarioDoencaSintomaApresentado);
    }

    public void carregarObjetoIFrmManutencao(
            GlossarioDoencasSintomasApresentados glossarioDoencaSintomaApresentado) {
        try {
            if (glossarioDoencaSintomaApresentado.getIdGlossarioDoencaSintomaApresentado()!= -1) {
                txtCodigo.setText(String.valueOf(
                        glossarioDoencaSintomaApresentado.getIdGlossarioDoencaSintomaApresentado()));
            } else {
                txtCodigo.setText(String.valueOf(
                        glossarioDoencaSintomaApresentado.getGlossarioDoencaSintomaApresentadoDAO().getProximoID()));
            }

            txtCodGlossario.setText(String.valueOf(
                    glossarioDoencaSintomaApresentado.getCodGlossarioDoenca()));
            pesquisarCodigoDescicaoGlossarioDoenca(new GlossarioDoencas());
            txtCodSintomaApresentado.setText(String.valueOf(
                    glossarioDoencaSintomaApresentado.getCodSintomaApresentado()));
            pesquisarCodigoDescicaoSintomas(new SintomasApresentados());
            txtDiasComSintoma.setText(String.valueOf(
                    glossarioDoencaSintomaApresentado.getDiasComSintoma()));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                    "Registro nao pode ser carregado.\n\nMotivo: " + ex);
        }
    }

    private void reescreverObjeto() {
        try {
            glossarioDoencaSintomaApresentado.setIdGlossarioDoencaSintomaApresentado(
                    Long.parseUnsignedLong(txtCodigo.getText()));
            glossarioDoencaSintomaApresentado.setCodGlossarioDoenca(
                    Long.parseUnsignedLong(txtCodGlossario.getText()));
            glossarioDoencaSintomaApresentado.setCodSintomaApresentado(
                    Long.parseUnsignedLong(txtCodSintomaApresentado.getText()));
            glossarioDoencaSintomaApresentado.setDiasComSintoma(
                    Integer.parseUnsignedInt(txtDiasComSintoma.getText()));
                    
            
            glossarioDoencaSintomaApresentado.adicionarCampos();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                    "Registro nao pode ser reescrito.\n\nMotivo: " + ex);
        }
    }

    public void pesquisarCodigoDescicaoGlossarioDoenca(
            GlossarioDoencas glossarioDoenca) {
        Util.pesquisarCodigoDescicao(glossarioDoenca, txtCodGlossario, 
                txtNomeGlossario);
    }

    public void pesquisarCodigoDescicaoSintomas(
            SintomasApresentados sintomaApresentado) {
        Util.pesquisarCodigoDescicao(sintomaApresentado, 
                txtCodSintomaApresentado, txtDescrSintomaApresentado);
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
        jLabel2 = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        txtCodGlossario = new javax.swing.JTextField();
        lblLocalizarGlossario = new javax.swing.JLabel();
        txtNomeGlossario = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtCodSintomaApresentado = new javax.swing.JTextField();
        lblLocalizarSintomaApresentado = new javax.swing.JLabel();
        txtDescrSintomaApresentado = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtDiasComSintoma = new javax.swing.JTextField();
        pnlBotoes = new javax.swing.JPanel();
        btnConfirmar = new javax.swing.JButton();
        btnAjudar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        mnuBarPrincipal = new javax.swing.JMenuBar();
        mnuArquivos = new javax.swing.JMenu();
        mnuAjuda = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cadastro de Glossário e Sintomas Apresentados");
        setMaximumSize(new java.awt.Dimension(729, 238));
        setMinimumSize(new java.awt.Dimension(729, 238));
        setName(""); // NOI18N
        setPreferredSize(new java.awt.Dimension(729, 238));
        try {
            setSelected(true);
        } catch (java.beans.PropertyVetoException e1) {
            e1.printStackTrace();
        }
        setVerifyInputWhenFocusTarget(false);
        getContentPane().setLayout(null);

        tablManutencao.setMinimumSize(new java.awt.Dimension(713, 245));
        tablManutencao.setName(""); // NOI18N

        pnlGeral.setLayout(null);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setText("Cód. Gloss. Sintomas");
        jLabel2.setToolTipText("");
        pnlGeral.add(jLabel2);
        jLabel2.setBounds(42, 14, 116, 14);

        txtCodigo.setEditable(false);
        txtCodigo.setBackground(java.awt.SystemColor.info);
        txtCodigo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        pnlGeral.add(txtCodigo);
        txtCodigo.setBounds(162, 11, 70, 20);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Cód. Glossário");
        pnlGeral.add(jLabel1);
        jLabel1.setBounds(67, 40, 90, 14);

        txtCodGlossario.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCodGlossario.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodGlossarioFocusLost(evt);
            }
        });
        pnlGeral.add(txtCodGlossario);
        txtCodGlossario.setBounds(162, 37, 70, 20);

        lblLocalizarGlossario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/localizar.png"))); // NOI18N
        lblLocalizarGlossario.setToolTipText("Localizar uma pessoa já cadastrada.");
        lblLocalizarGlossario.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblLocalizarGlossario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                lblLocalizarGlossarioMouseReleased(evt);
            }
        });
        pnlGeral.add(lblLocalizarGlossario);
        lblLocalizarGlossario.setBounds(236, 37, 20, 20);

        txtNomeGlossario.setEditable(false);
        txtNomeGlossario.setBackground(java.awt.SystemColor.info);
        txtNomeGlossario.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        pnlGeral.add(txtNomeGlossario);
        txtNomeGlossario.setBounds(260, 37, 402, 20);

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel4.setText("Cód. Sintoma");
        pnlGeral.add(jLabel4);
        jLabel4.setBounds(84, 66, 74, 14);

        txtCodSintomaApresentado.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCodSintomaApresentado.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodSintomaApresentadoFocusLost(evt);
            }
        });
        pnlGeral.add(txtCodSintomaApresentado);
        txtCodSintomaApresentado.setBounds(162, 63, 70, 20);

        lblLocalizarSintomaApresentado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/localizar.png"))); // NOI18N
        lblLocalizarSintomaApresentado.setToolTipText("Localizar uma função já cadastrada.");
        lblLocalizarSintomaApresentado.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblLocalizarSintomaApresentado.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                lblLocalizarSintomaApresentadoMouseReleased(evt);
            }
        });
        pnlGeral.add(lblLocalizarSintomaApresentado);
        lblLocalizarSintomaApresentado.setBounds(236, 63, 20, 20);

        txtDescrSintomaApresentado.setEditable(false);
        txtDescrSintomaApresentado.setBackground(java.awt.SystemColor.info);
        txtDescrSintomaApresentado.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        pnlGeral.add(txtDescrSintomaApresentado);
        txtDescrSintomaApresentado.setBounds(260, 63, 402, 20);

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel6.setText("Dias com Sintoma");
        pnlGeral.add(jLabel6);
        jLabel6.setBounds(57, 91, 101, 14);
        pnlGeral.add(txtDiasComSintoma);
        txtDiasComSintoma.setBounds(162, 89, 70, 20);

        tablManutencao.addTab("Geral", pnlGeral);

        getContentPane().add(tablManutencao);
        tablManutencao.setBounds(0, 0, 710, 147);
        tablManutencao.getAccessibleContext().setAccessibleName("");

        pnlBotoes.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        pnlBotoes.setName("pnlBotao"); // NOI18N
        pnlBotoes.setPreferredSize(new java.awt.Dimension(229, 33));

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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 493, Short.MAX_VALUE)
                .addComponent(btnConfirmar, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12))
        );
        pnlBotoesLayout.setVerticalGroup(
            pnlBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBotoesLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(pnlBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnConfirmar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAjudar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(pnlBotoes);
        pnlBotoes.setBounds(0, 150, 713, 38);

        mnuArquivos.setText("Arquivo");
        mnuBarPrincipal.add(mnuArquivos);

        mnuAjuda.setText("Ajuda");
        mnuBarPrincipal.add(mnuAjuda);

        setJMenuBar(mnuBarPrincipal);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirmarActionPerformed
        reescreverObjeto();

        Util.salvar(glossarioDoencaSintomaApresentado, this, true);
    }//GEN-LAST:event_btnConfirmarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        Util.cancelar(this);
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnAjudarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAjudarActionPerformed
        Util.ajudar();
    }//GEN-LAST:event_btnAjudarActionPerformed

    private void lblLocalizarGlossarioMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblLocalizarGlossarioMouseReleased
        boolean[] acoes = Util.getAcoesUsuario(Principal.GLOSSARIO_DOENCAS);

        if (!acoes[Util.CONSULTAR]) {
            JOptionPane.showMessageDialog(this.getParent(),
                    "Você não pode efetuar esta consulta.\n\nMotivo: "
                            + "Usuário sem permissão.");
        } else {        
            try {
                IFrmPesqGlossarioDoencas iFrmPesqGlossarioDoencas =
                        new IFrmPesqGlossarioDoencas(new IFrmManGlossarioDoenca(
                                new GlossarioDoencas()), txtCodGlossario, 
                        txtNomeGlossario);

                this.getParent().remove(iFrmPesqGlossarioDoencas);
                this.getParent().add(iFrmPesqGlossarioDoencas);

                Util.centralizar(this.getParent(), iFrmPesqGlossarioDoencas);

                iFrmPesqGlossarioDoencas.setVisible(true);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this.getParent(),
                        "Doenças não podem ser consultadas.\n\nMotivo: " + e);
            }
        }
    }//GEN-LAST:event_lblLocalizarGlossarioMouseReleased

    private void txtCodGlossarioFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodGlossarioFocusLost
        pesquisarCodigoDescicaoGlossarioDoenca(new GlossarioDoencas());
    }//GEN-LAST:event_txtCodGlossarioFocusLost

    private void lblLocalizarSintomaApresentadoMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblLocalizarSintomaApresentadoMouseReleased
        boolean[] acoes = Util.getAcoesUsuario(Principal.SINTOMAS_APRESENTADOS);

        if (!acoes[Util.CONSULTAR]) {
            JOptionPane.showMessageDialog(this.getParent(),
                    "Você não pode efetuar esta consulta.\n\nMotivo: "
                            + "Usuário sem permissão.");
        } else { 
            try {
                IFrmPesqSintomasApresentados iFrmPesqSintomasApresentados = 
                        new IFrmPesqSintomasApresentados(new IFrmManSintomaApresentado(
                                new SintomasApresentados()), txtCodSintomaApresentado, 
                                txtDescrSintomaApresentado);

                this.getParent().remove(iFrmPesqSintomasApresentados);
                this.getParent().add(iFrmPesqSintomasApresentados);

                Util.centralizar(this.getParent(), iFrmPesqSintomasApresentados);

                iFrmPesqSintomasApresentados.setVisible(true);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this.getParent(),
                    "Sintomas não podem ser consultados.\n\nMotivo: " + e);
            }
        }
    }//GEN-LAST:event_lblLocalizarSintomaApresentadoMouseReleased

    private void txtCodSintomaApresentadoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodSintomaApresentadoFocusLost
        pesquisarCodigoDescicaoSintomas(new SintomasApresentados());
    }//GEN-LAST:event_txtCodSintomaApresentadoFocusLost

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAjudar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnConfirmar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel lblLocalizarGlossario;
    private javax.swing.JLabel lblLocalizarSintomaApresentado;
    private javax.swing.JMenu mnuAjuda;
    private javax.swing.JMenu mnuArquivos;
    private javax.swing.JMenuBar mnuBarPrincipal;
    private javax.swing.JPanel pnlBotoes;
    private javax.swing.JPanel pnlGeral;
    private javax.swing.JTabbedPane tablManutencao;
    private javax.swing.JTextField txtCodGlossario;
    private javax.swing.JTextField txtCodSintomaApresentado;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtDescrSintomaApresentado;
    private javax.swing.JTextField txtDiasComSintoma;
    private javax.swing.JTextField txtNomeGlossario;
    // End of variables declaration//GEN-END:variables

    /**
     * @return the glossarioDoencaSintomaApresentado
     */
    public GlossarioDoencasSintomasApresentados getGlossarioDoencaSintomaApresentado() {
        return glossarioDoencaSintomaApresentado;
    }

    /**
     * @param glossarioDoencaSintomaApresentado the glossarioDoencaSintomaApresentado to set
     */
    public void setGlossarioDoencaSintomaApresentado(GlossarioDoencasSintomasApresentados glossarioDoencaSintomaApresentado) {
        this.glossarioDoencaSintomaApresentado = glossarioDoencaSintomaApresentado;
    }
}
