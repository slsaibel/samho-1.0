/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.samho.apresentacao;

import com.samho.negocio.Medicamentos;
import com.samho.negocio.ProntuariosMedicamentos;
import com.samho.negocio.Prontuarios;
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
public final class IFrmManProntuarioMedicamento extends JInternalFrame {

    // Declaração de atributos
    private ProntuariosMedicamentos prontuarioMedicamento;

    // Construtor
    public IFrmManProntuarioMedicamento(
            ProntuariosMedicamentos prontuarioMedicamento) throws Exception {
        initComponents();
        
        Util.limparCampos(rootPane);

        this.prontuarioMedicamento = prontuarioMedicamento;

        // Permite que somente números sejam informados neste campo
        txtCodigo.setDocument(new JTextFieldDocument(true));
        txtCodMedicamento.setDocument(new JTextFieldDocument(true));
        txtCodProntuario.setDocument(new JTextFieldDocument(true));
        txtDiasComTratamento.setDocument(new JTextFieldDocument(true));
        
        // Quebra de linha automática
        txtParecerProfissional.setLineWrap(true);
        txtParecerProfissional.setWrapStyleWord(true);

        carregarObjetoIFrmManutencao(prontuarioMedicamento);
    }

    public void carregarObjetoIFrmManutencao(
            ProntuariosMedicamentos prontuarioMedicamento) {
        try {
            if (prontuarioMedicamento.getIdProntuarioMedicamento()!= -1) {
                txtCodigo.setText(String.valueOf(
                        prontuarioMedicamento.getIdProntuarioMedicamento()));
            } else {
                txtCodigo.setText(String.valueOf(
                        prontuarioMedicamento.getProntuarioMedicamentoDAO().getProximoID()));
            }

            txtCodProntuario.setText(String.valueOf(
                    prontuarioMedicamento.getCodProntuario()));
            pesquisarCodigoDescicaoProntuario(new Prontuarios());
            txtCodMedicamento.setText(String.valueOf(
                    prontuarioMedicamento.getCodMedicamento()));
            pesquisarCodigoDescicaoMedicamento(new Medicamentos());
            txtDiasComTratamento.setText(String.valueOf(
                    prontuarioMedicamento.getDiasComTratamento()));
            txtQtdTratamento.setText(String.valueOf(
                    prontuarioMedicamento.getQuantidade()));
            txtParecerProfissional.setText(String.valueOf(
                    prontuarioMedicamento.getParecerProfissional()));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                    "Registro nao pode ser carregado.\n\nMotivo: " + ex);
        }
    }

    private void reescreverObjeto() {
        try {
            prontuarioMedicamento.setIdProntuarioMedicamento(
                    Long.parseUnsignedLong(txtCodigo.getText()));
            prontuarioMedicamento.setCodProntuario(Long.parseUnsignedLong(
                    txtCodProntuario.getText()));
            prontuarioMedicamento.setCodMedicamento(
                    Long.parseUnsignedLong(txtCodMedicamento.getText()));
            prontuarioMedicamento.setDiasComTratamento(
                    Integer.parseUnsignedInt(txtDiasComTratamento.getText()));
            prontuarioMedicamento.setQuantidade(Integer.parseUnsignedInt(
                    txtQtdTratamento.getText()));
            prontuarioMedicamento.setParecerProfissional(
                    txtParecerProfissional.getText());            
                    
            
            prontuarioMedicamento.adicionarCampos();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                    "Registro nao pode ser reescrito.\n\nMotivo: " + ex);
        }
    }

    public void pesquisarCodigoDescicaoProntuario(Prontuarios prontuario) {
        Util.pesquisarCodigoDescicao(prontuario, txtCodProntuario, 
                txtNomeProntuario);
    }

    public void pesquisarCodigoDescicaoMedicamento(Medicamentos medicamento) {
        Util.pesquisarCodigoDescicao(medicamento, txtCodMedicamento, 
                txtDescrMedicamento);
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
        txtCodProntuario = new javax.swing.JTextField();
        lblLocalizarProntuario = new javax.swing.JLabel();
        txtNomeProntuario = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtCodMedicamento = new javax.swing.JTextField();
        lblLocalizarMedicamento = new javax.swing.JLabel();
        txtDescrMedicamento = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtParecerProfissional = new javax.swing.JTextArea();
        jLabel8 = new javax.swing.JLabel();
        txtDiasComTratamento = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtQtdTratamento = new javax.swing.JTextField();
        pnlBotoes = new javax.swing.JPanel();
        btnConfirmar = new javax.swing.JButton();
        btnAjudar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        mnuBarPrincipal = new javax.swing.JMenuBar();
        mnuArquivos = new javax.swing.JMenu();
        mnuAjuda = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cadastro de Prontuário e Medicamentos");
        setMaximumSize(new java.awt.Dimension(729, 367));
        setMinimumSize(new java.awt.Dimension(729, 367));
        setName(""); // NOI18N
        setPreferredSize(new java.awt.Dimension(729, 367));
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
        jLabel2.setText("Cód. Pront. Medicamento");
        jLabel2.setToolTipText("");
        pnlGeral.add(jLabel2);
        jLabel2.setBounds(17, 14, 141, 14);

        txtCodigo.setEditable(false);
        txtCodigo.setBackground(java.awt.SystemColor.info);
        txtCodigo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        pnlGeral.add(txtCodigo);
        txtCodigo.setBounds(162, 11, 70, 20);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Cód. Prontuário");
        pnlGeral.add(jLabel1);
        jLabel1.setBounds(67, 40, 90, 14);

        txtCodProntuario.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCodProntuario.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodProntuarioFocusLost(evt);
            }
        });
        pnlGeral.add(txtCodProntuario);
        txtCodProntuario.setBounds(162, 37, 70, 20);

        lblLocalizarProntuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/localizar.png"))); // NOI18N
        lblLocalizarProntuario.setToolTipText("Localizar uma pessoa já cadastrada.");
        lblLocalizarProntuario.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblLocalizarProntuario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                lblLocalizarProntuarioMouseReleased(evt);
            }
        });
        pnlGeral.add(lblLocalizarProntuario);
        lblLocalizarProntuario.setBounds(236, 37, 20, 20);

        txtNomeProntuario.setEditable(false);
        txtNomeProntuario.setBackground(java.awt.SystemColor.info);
        txtNomeProntuario.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        pnlGeral.add(txtNomeProntuario);
        txtNomeProntuario.setBounds(260, 37, 402, 20);

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel4.setText("Cód. Medicamento");
        pnlGeral.add(jLabel4);
        jLabel4.setBounds(54, 66, 104, 14);

        txtCodMedicamento.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCodMedicamento.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodMedicamentoFocusLost(evt);
            }
        });
        pnlGeral.add(txtCodMedicamento);
        txtCodMedicamento.setBounds(162, 63, 70, 20);

        lblLocalizarMedicamento.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/localizar.png"))); // NOI18N
        lblLocalizarMedicamento.setToolTipText("Localizar uma função já cadastrada.");
        lblLocalizarMedicamento.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblLocalizarMedicamento.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                lblLocalizarMedicamentoMouseReleased(evt);
            }
        });
        pnlGeral.add(lblLocalizarMedicamento);
        lblLocalizarMedicamento.setBounds(236, 63, 20, 20);

        txtDescrMedicamento.setEditable(false);
        txtDescrMedicamento.setBackground(java.awt.SystemColor.info);
        txtDescrMedicamento.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        pnlGeral.add(txtDescrMedicamento);
        txtDescrMedicamento.setBounds(260, 63, 402, 20);

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel6.setText("Parecer Profissional");
        pnlGeral.add(jLabel6);
        jLabel6.setBounds(45, 120, 112, 14);

        txtParecerProfissional.setColumns(20);
        txtParecerProfissional.setRows(5);
        jScrollPane1.setViewportView(txtParecerProfissional);

        pnlGeral.add(jScrollPane1);
        jScrollPane1.setBounds(163, 115, 500, 130);

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel8.setText("Dias com Tratamento");
        pnlGeral.add(jLabel8);
        jLabel8.setBounds(36, 91, 122, 14);
        pnlGeral.add(txtDiasComTratamento);
        txtDiasComTratamento.setBounds(162, 89, 70, 20);

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel7.setText("Qtd. de Tratamento");
        pnlGeral.add(jLabel7);
        jLabel7.setBounds(312, 91, 115, 14);
        pnlGeral.add(txtQtdTratamento);
        txtQtdTratamento.setBounds(427, 89, 70, 20);

        tablManutencao.addTab("Geral", pnlGeral);

        getContentPane().add(tablManutencao);
        tablManutencao.setBounds(0, 0, 710, 277);
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
        pnlBotoes.setBounds(0, 280, 713, 38);

        mnuArquivos.setText("Arquivo");
        mnuBarPrincipal.add(mnuArquivos);

        mnuAjuda.setText("Ajuda");
        mnuBarPrincipal.add(mnuAjuda);

        setJMenuBar(mnuBarPrincipal);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirmarActionPerformed
        reescreverObjeto();

        Util.salvar(prontuarioMedicamento, this, true);
    }//GEN-LAST:event_btnConfirmarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        Util.cancelar(this);
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnAjudarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAjudarActionPerformed
        Util.ajudar();
    }//GEN-LAST:event_btnAjudarActionPerformed

    private void lblLocalizarProntuarioMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblLocalizarProntuarioMouseReleased
        boolean[] acoes = Util.getAcoesUsuario(Principal.PRONTUARIOS);

        if (!acoes[Util.CONSULTAR]) {
            JOptionPane.showMessageDialog(this.getParent(),
                    "Você não pode efetuar esta consulta.\n\nMotivo: "
                            + "Usuário sem permissão.");
        } else {        
            try {
                IFrmPesqProntuarios iFrmPesqProntuarios =
                        new IFrmPesqProntuarios(new IFrmManProntuario(
                                new Prontuarios()), txtCodProntuario, 
                        txtNomeProntuario);

                this.getParent().remove(iFrmPesqProntuarios);
                this.getParent().add(iFrmPesqProntuarios);

                Util.centralizar(this.getParent(), iFrmPesqProntuarios);

                iFrmPesqProntuarios.setVisible(true);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this.getParent(),
                        "Prontuários não podem ser consultados.\n\nMotivo: " + e);
            }
        }
    }//GEN-LAST:event_lblLocalizarProntuarioMouseReleased

    private void txtCodProntuarioFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodProntuarioFocusLost
        pesquisarCodigoDescicaoProntuario(new Prontuarios());
    }//GEN-LAST:event_txtCodProntuarioFocusLost

    private void lblLocalizarMedicamentoMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblLocalizarMedicamentoMouseReleased
        boolean[] acoes = Util.getAcoesUsuario(Principal.MEDICAMENTOS);

        if (!acoes[Util.CONSULTAR]) {
            JOptionPane.showMessageDialog(this.getParent(),
                    "Você não pode efetuar esta consulta.\n\nMotivo: "
                            + "Usuário sem permissão.");
        } else { 
            try {
                IFrmPesqMedicamentos iFrmPesqMedicamentos = 
                        new IFrmPesqMedicamentos(new IFrmManMedicamento(
                                new Medicamentos()), txtCodMedicamento, 
                                txtDescrMedicamento);

                this.getParent().remove(iFrmPesqMedicamentos);
                this.getParent().add(iFrmPesqMedicamentos);

                Util.centralizar(this.getParent(), iFrmPesqMedicamentos);

                iFrmPesqMedicamentos.setVisible(true);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this.getParent(),
                    "Medicamentos não podem ser consultados.\n\nMotivo: " + e);
            }
        }
    }//GEN-LAST:event_lblLocalizarMedicamentoMouseReleased

    private void txtCodMedicamentoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodMedicamentoFocusLost
        pesquisarCodigoDescicaoMedicamento(new Medicamentos());
    }//GEN-LAST:event_txtCodMedicamentoFocusLost

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAjudar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnConfirmar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblLocalizarMedicamento;
    private javax.swing.JLabel lblLocalizarProntuario;
    private javax.swing.JMenu mnuAjuda;
    private javax.swing.JMenu mnuArquivos;
    private javax.swing.JMenuBar mnuBarPrincipal;
    private javax.swing.JPanel pnlBotoes;
    private javax.swing.JPanel pnlGeral;
    private javax.swing.JTabbedPane tablManutencao;
    private javax.swing.JTextField txtCodMedicamento;
    private javax.swing.JTextField txtCodProntuario;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtDescrMedicamento;
    private javax.swing.JTextField txtDiasComTratamento;
    private javax.swing.JTextField txtNomeProntuario;
    private javax.swing.JTextArea txtParecerProfissional;
    private javax.swing.JTextField txtQtdTratamento;
    // End of variables declaration//GEN-END:variables

    /**
     * @return the prontuarioMedicamento
     */
    public ProntuariosMedicamentos getProntuarioMedicamento() {
        return prontuarioMedicamento;
    }

    /**
     * @param prontuarioMedicamento the prontuarioMedicamento to set
     */
    public void setProntuarioMedicamento(ProntuariosMedicamentos prontuarioMedicamento) {
        this.prontuarioMedicamento = prontuarioMedicamento;
    }
}
