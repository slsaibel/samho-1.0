/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.samho.apresentacao;

import com.samho.negocio.PlanosSaude;
import com.samho.negocio.Profissionais;
import com.samho.negocio.ProfissionaisPlanosSaude;
import com.samho.negocio.Principal;
import com.samho.util.Formatacao;
import com.samho.util.JTextFieldDocument;
import com.samho.util.Registrador;
import com.samho.util.Util;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;

/**
 * Classe para visualização de objetos do tipo FrmManutencao.
 *
 * @author Saibel, Sergio Luis
 * @since  date 03/04/2017
 *
 * @version  revision 001.20170403 date 03/04/2017 author Saibel, Sérgio Luis reason
 * Conseguir instanciar um objeto do tipo FrmMaunutenção com os atributos e
 * métodos necessários. Esta classe tem como objetivo manipular os objetos 
 * definidos e ou obtidos a fim de manipular informações.
 * Esta extende um JInternalFrame ou seja necessita de um Frame como pai.
 * Métodos principais: Definição, Obtenção e Visualização do objeto.
 * 
 */
public final class IFrmManProfissionalPlanoSaude extends JInternalFrame {

    // Declaração de atributos
    private ProfissionaisPlanosSaude profissionalPlanoSaude;

    // Construtor
    public IFrmManProfissionalPlanoSaude(
            ProfissionaisPlanosSaude profissionalPlanoSaude) 
            throws Exception {
        initComponents();
        
        Formatacao.reformatarData(txtDataDeAdesao);
        
        Util.limparCampos(rootPane);

        this.profissionalPlanoSaude = profissionalPlanoSaude;

        // Permite que somente números sejam informados neste campo
        txtCodigo.setDocument(new JTextFieldDocument(true));
        txtCodPlanoSaude.setDocument(new JTextFieldDocument(true));
        txtCodProfissional.setDocument(new JTextFieldDocument(true));

        carregarObjetoIFrmManutencao(profissionalPlanoSaude);
    }

    public void carregarObjetoIFrmManutencao(
            ProfissionaisPlanosSaude profissionalPlanoSaude) {
        try {
            if (profissionalPlanoSaude.getIdProfissionalPlanoSaude()!= -1) {
                txtCodigo.setText(String.valueOf(
                        profissionalPlanoSaude.getIdProfissionalPlanoSaude()));
            } else {
                txtCodigo.setText(String.valueOf(
                        profissionalPlanoSaude.getProfissionalPlanoSaudeDAO().getProximoID()));
            }

            txtCodProfissional.setText(String.valueOf(
                    profissionalPlanoSaude.getCodProfissional()));
            pesquisarCodigoDescicaoProfissional(new Profissionais());
            txtCodPlanoSaude.setText(String.valueOf(
                    profissionalPlanoSaude.getCodPlanoSaude()));
            pesquisarCodigoDescicaoPlanoSaude(new PlanosSaude());
            txtDataDeAdesao.setText(Formatacao.ajustaDataDMA(
                    profissionalPlanoSaude.getDataAdesao()));
            chkAtivo.setSelected(this.profissionalPlanoSaude.isAtivo());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                    "Registro nao pode ser carregado.\n\nMotivo: " + ex);
        }
    }

    private void reescreverObjeto() {
        try {
            profissionalPlanoSaude.setIdProfissionalPlanoSaude(
                    Long.parseUnsignedLong(txtCodigo.getText()));
            profissionalPlanoSaude.setCodProfissional(Long.parseUnsignedLong(
                    txtCodProfissional.getText()));
            profissionalPlanoSaude.setCodPlanoSaude(Long.parseUnsignedLong(
                    txtCodPlanoSaude.getText()));
            profissionalPlanoSaude.setDataAdesao(Formatacao.ajustaData(
                    txtDataDeAdesao.getText(), Formatacao.DATA_DMA));
            profissionalPlanoSaude.setAtivo(chkAtivo.isSelected());
            
            profissionalPlanoSaude.adicionarCampos();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                    "Registro nao pode ser reescrito.\n\nMotivo: " + ex);
        }
    }

    public void pesquisarCodigoDescicaoProfissional(Profissionais profissional) {
        Registrador registrador = new Registrador();
        registrador = profissional.getObjetoDAO().popularCodigoDescricao(
                String.valueOf(profissionalPlanoSaude.getCodProfissional()),
                profissional.getProfissionalDAO().getSQLSubstiturCampoDescricao(
                        profissionalPlanoSaude.getCodProfissional()));
        txtCodProfissional.setText(String.valueOf(registrador.getCodigo()));
        txtNomeProfissional.setText(registrador.getDescricao());
    }

    public void pesquisarCodigoDescicaoPlanoSaude(PlanosSaude planoSaude) {
        Util.pesquisarCodigoDescicao(planoSaude, txtCodPlanoSaude, 
                txtDescrPlanoSaude);
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
        txtCodProfissional = new javax.swing.JTextField();
        lblLocalizarProfissional = new javax.swing.JLabel();
        txtNomeProfissional = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtCodPlanoSaude = new javax.swing.JTextField();
        lblLocalizarPlanoSaude = new javax.swing.JLabel();
        txtDescrPlanoSaude = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtDataDeAdesao = new javax.swing.JFormattedTextField();
        jLabel7 = new javax.swing.JLabel();
        chkAtivo = new javax.swing.JCheckBox();
        pnlBotoes = new javax.swing.JPanel();
        btnConfirmar = new javax.swing.JButton();
        btnAjudar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        mnuBarPrincipal = new javax.swing.JMenuBar();
        mnuArquivos = new javax.swing.JMenu();
        mnuAjuda = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cadastro de Plano atendidos pelo Profissional");
        setMaximumSize(new java.awt.Dimension(729, 261));
        setMinimumSize(new java.awt.Dimension(729, 261));
        setName(""); // NOI18N
        setPreferredSize(new java.awt.Dimension(729, 261));
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
        jLabel2.setText("Cód. Prof. Plano");
        jLabel2.setToolTipText("");
        pnlGeral.add(jLabel2);
        jLabel2.setBounds(71, 14, 87, 14);

        txtCodigo.setEditable(false);
        txtCodigo.setBackground(java.awt.SystemColor.info);
        txtCodigo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        pnlGeral.add(txtCodigo);
        txtCodigo.setBounds(162, 11, 70, 20);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Cód. Profissional");
        pnlGeral.add(jLabel1);
        jLabel1.setBounds(57, 40, 100, 14);

        txtCodProfissional.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCodProfissional.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodProfissionalFocusLost(evt);
            }
        });
        pnlGeral.add(txtCodProfissional);
        txtCodProfissional.setBounds(162, 37, 70, 20);

        lblLocalizarProfissional.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/localizar.png"))); // NOI18N
        lblLocalizarProfissional.setToolTipText("Localizar uma pessoa já cadastrada.");
        lblLocalizarProfissional.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblLocalizarProfissional.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                lblLocalizarProfissionalMouseReleased(evt);
            }
        });
        pnlGeral.add(lblLocalizarProfissional);
        lblLocalizarProfissional.setBounds(236, 37, 20, 20);

        txtNomeProfissional.setEditable(false);
        txtNomeProfissional.setBackground(java.awt.SystemColor.info);
        txtNomeProfissional.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        pnlGeral.add(txtNomeProfissional);
        txtNomeProfissional.setBounds(260, 37, 402, 20);

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel4.setText("Cód. Plano Saúde");
        pnlGeral.add(jLabel4);
        jLabel4.setBounds(62, 66, 96, 14);

        txtCodPlanoSaude.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCodPlanoSaude.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodPlanoSaudeFocusLost(evt);
            }
        });
        pnlGeral.add(txtCodPlanoSaude);
        txtCodPlanoSaude.setBounds(162, 63, 70, 20);

        lblLocalizarPlanoSaude.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/localizar.png"))); // NOI18N
        lblLocalizarPlanoSaude.setToolTipText("Localizar uma função já cadastrada.");
        lblLocalizarPlanoSaude.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblLocalizarPlanoSaude.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                lblLocalizarPlanoSaudeMouseReleased(evt);
            }
        });
        pnlGeral.add(lblLocalizarPlanoSaude);
        lblLocalizarPlanoSaude.setBounds(236, 63, 20, 20);

        txtDescrPlanoSaude.setEditable(false);
        txtDescrPlanoSaude.setBackground(java.awt.SystemColor.info);
        txtDescrPlanoSaude.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        pnlGeral.add(txtDescrPlanoSaude);
        txtDescrPlanoSaude.setBounds(260, 63, 402, 20);

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel6.setText("Data de Adesão");
        pnlGeral.add(jLabel6);
        jLabel6.setBounds(69, 91, 89, 14);

        try {
            txtDataDeAdesao.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtDataDeAdesao.setVerifyInputWhenFocusTarget(false);
        txtDataDeAdesao.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDataDeAdesaoFocusGained(evt);
            }
        });
        pnlGeral.add(txtDataDeAdesao);
        txtDataDeAdesao.setBounds(162, 88, 70, 20);

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel7.setText("Ativo");
        pnlGeral.add(jLabel7);
        jLabel7.setBounds(125, 115, 30, 14);

        chkAtivo.setToolTipText("");
        pnlGeral.add(chkAtivo);
        chkAtivo.setBounds(162, 112, 21, 21);

        tablManutencao.addTab("Geral", pnlGeral);

        getContentPane().add(tablManutencao);
        tablManutencao.setBounds(0, 0, 710, 170);
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
        pnlBotoes.setBounds(0, 173, 713, 38);

        mnuArquivos.setText("Arquivo");
        mnuBarPrincipal.add(mnuArquivos);

        mnuAjuda.setText("Ajuda");
        mnuBarPrincipal.add(mnuAjuda);

        setJMenuBar(mnuBarPrincipal);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirmarActionPerformed
        reescreverObjeto();

        Util.salvar(profissionalPlanoSaude, this, true);
    }//GEN-LAST:event_btnConfirmarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        Util.cancelar(this);
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnAjudarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAjudarActionPerformed
        Util.ajudar();
    }//GEN-LAST:event_btnAjudarActionPerformed

    private void lblLocalizarProfissionalMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblLocalizarProfissionalMouseReleased
        boolean[] acoes = Util.getAcoesUsuario(Principal.PROFISSIONAIS);

        if (!acoes[Util.CONSULTAR]) {
            JOptionPane.showMessageDialog(this.getParent(),
                    "Você não pode efetuar esta consulta.\n\nMotivo: "
                            + "Usuário sem permissão.");
        } else {        
            try {
                IFrmPesqProfissionais iFrmPesqProfissionais = 
                        new IFrmPesqProfissionais(new IFrmManProfissional(
                                new Profissionais()), txtCodProfissional, 
                        txtNomeProfissional);

                this.getParent().remove(iFrmPesqProfissionais);
                this.getParent().add(iFrmPesqProfissionais);

                Util.centralizar(this.getParent(), iFrmPesqProfissionais);

                iFrmPesqProfissionais.setVisible(true);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this.getParent(),
                        "Profissionais não podem ser consultados.\n\nMotivo: " + e);
            }
        }
    }//GEN-LAST:event_lblLocalizarProfissionalMouseReleased

    private void txtCodProfissionalFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodProfissionalFocusLost
        pesquisarCodigoDescicaoProfissional(new Profissionais());
    }//GEN-LAST:event_txtCodProfissionalFocusLost

    private void txtDataDeAdesaoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDataDeAdesaoFocusGained
        txtDataDeAdesao.selectAll();
    }//GEN-LAST:event_txtDataDeAdesaoFocusGained

    private void lblLocalizarPlanoSaudeMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblLocalizarPlanoSaudeMouseReleased
        boolean[] acoes = Util.getAcoesUsuario(Principal.PLANOS_SAUDE);

        if (!acoes[Util.CONSULTAR]) {
            JOptionPane.showMessageDialog(this.getParent(),
                    "Você não pode efetuar esta consulta.\n\nMotivo: "
                            + "Usuário sem permissão.");
        } else { 
            try {
                IFrmPesqPlanosSaude iFrmPesqPlanosSaude = 
                        new IFrmPesqPlanosSaude(new IFrmManPlanoSaude(
                                new PlanosSaude()), txtCodPlanoSaude, 
                                txtDescrPlanoSaude);

                this.getParent().remove(iFrmPesqPlanosSaude);
                this.getParent().add(iFrmPesqPlanosSaude);

                Util.centralizar(this.getParent(), iFrmPesqPlanosSaude);

                iFrmPesqPlanosSaude.setVisible(true);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this.getParent(),
                    "Planos de Saúde não podem ser consultados.\n\nMotivo: " + e);
            }
        }
    }//GEN-LAST:event_lblLocalizarPlanoSaudeMouseReleased

    private void txtCodPlanoSaudeFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodPlanoSaudeFocusLost
        pesquisarCodigoDescicaoPlanoSaude(new PlanosSaude());
    }//GEN-LAST:event_txtCodPlanoSaudeFocusLost

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAjudar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnConfirmar;
    private javax.swing.JCheckBox chkAtivo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel lblLocalizarPlanoSaude;
    private javax.swing.JLabel lblLocalizarProfissional;
    private javax.swing.JMenu mnuAjuda;
    private javax.swing.JMenu mnuArquivos;
    private javax.swing.JMenuBar mnuBarPrincipal;
    private javax.swing.JPanel pnlBotoes;
    private javax.swing.JPanel pnlGeral;
    private javax.swing.JTabbedPane tablManutencao;
    private javax.swing.JTextField txtCodPlanoSaude;
    private javax.swing.JTextField txtCodProfissional;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JFormattedTextField txtDataDeAdesao;
    private javax.swing.JTextField txtDescrPlanoSaude;
    private javax.swing.JTextField txtNomeProfissional;
    // End of variables declaration//GEN-END:variables

    /**
     * @return the profissionalPlanoSaude
     */
    public ProfissionaisPlanosSaude getProfissionalPlanoSaude() {
        return profissionalPlanoSaude;
    }

    /**
     * @param profissionalPlanoSaude the profissionalPlanoSaude to set
     */
    public void setProfissionalPlanoSaude(ProfissionaisPlanosSaude profissionalPlanoSaude) {
        this.profissionalPlanoSaude = profissionalPlanoSaude;
    }
}
