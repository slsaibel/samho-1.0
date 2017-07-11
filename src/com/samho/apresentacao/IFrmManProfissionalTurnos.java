/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.samho.apresentacao;

import com.samho.dao.UsuariosDAO;
import com.samho.negocio.Principal;
import com.samho.negocio.Profissionais;
import com.samho.negocio.ProfissionaisTurnos;
import com.samho.util.JTextFieldDocument;
import com.samho.util.Util;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;

/**
 * Classe para visualização de objetos do tipo FrmManutencao.
 *
 * @author Saibel, Sergio Luis
 * @since  date 20/04/2017
 *
 * @version  revision 001.20170420 date 20/04/2017 author Saibel, Sérgio Luis reason
 * Conseguir instanciar um objeto do tipo FrmMaunutenção com os atributos e
 * métodos necessários. Esta classe tem como objetivo manipular os objetos 
 * definidos e ou obtidos a fim de manipular informações.
 * Esta extende um JInternalFrame ou seja necessita de um Frame como pai.
 * Métodos principais: Definição, Obtenção e Visualização do objeto.
 * Métodos secundários: Pesquisa de tipos de endereço e bairros
 * 
 */
public final class IFrmManProfissionalTurnos extends JInternalFrame {

    // Declaração de atributos
    private ProfissionaisTurnos profissionalTurnos;

    // Construtor
    public IFrmManProfissionalTurnos(ProfissionaisTurnos profissionalTurnos) 
            throws Exception {
        initComponents();

        Util.limparCampos(rootPane);

        this.profissionalTurnos = profissionalTurnos;

        // Permite que somente números sejam informados neste campo
        txtCodigo.setDocument(new JTextFieldDocument(true));
        txtCodProfissional.setDocument(new JTextFieldDocument(true));

        carregarObjetoIFrmManutencao(profissionalTurnos);
    }

    public void carregarObjetoIFrmManutencao(
            ProfissionaisTurnos profissionalTurnos) {
        try {
            if (profissionalTurnos.getIdProfissionalTurnos()!= -1) {
                txtCodigo.setText(String.valueOf(
                        profissionalTurnos.getIdProfissionalTurnos()));
            } else {
                txtCodigo.setText(String.valueOf(
                        profissionalTurnos.getProfissionalTurnosDAO().getProximoID()));
            }
            
            txtCodProfissional.setText(String.valueOf(
                    profissionalTurnos.getCodProfissional()));
            pesquisarCodigoDescicaoProfissional(new Profissionais());

            cbDiasSemana.setSelectedIndex(profissionalTurnos.getDiaSemana());
            chkMadrugada.setSelected(profissionalTurnos.isMadrugada());
            chkManha.setSelected(profissionalTurnos.isManha());
            chkTarde.setSelected(profissionalTurnos.isTarde());
            chkNoite.setSelected(profissionalTurnos.isNoite());
            chkAtendeFeriado.setSelected(profissionalTurnos.isAtendeFeriado());
            chkAtendeExtra.setSelected(profissionalTurnos.isAtendeExtra());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                    "Registro nao pode ser carregado.\n\nMotivo: " + ex);
        }
    }

    private void reescreverObjeto() {
        try {
            profissionalTurnos.setIdProfissionalTurnos(Long.parseUnsignedLong(
                    txtCodigo.getText()));
            profissionalTurnos.setCodProfissional(Long.parseUnsignedLong(
                    txtCodProfissional.getText()));
            profissionalTurnos.setDiaSemana(cbDiasSemana.getSelectedIndex());
            profissionalTurnos.setMadrugada(chkMadrugada.isSelected());
            profissionalTurnos.setManha(chkManha.isSelected());
            profissionalTurnos.setTarde(chkTarde.isSelected());
            profissionalTurnos.setNoite(chkNoite.isSelected());
            profissionalTurnos.setAtendeFeriado(chkAtendeFeriado.isSelected());
            profissionalTurnos.setAtendeExtra(chkAtendeExtra.isSelected());

            profissionalTurnos.adicionarCampos();
            
            // Atualiza as ações permitidas ao usuario
            UsuariosDAO.verificaAcoesUsuario();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                    "Registro nao pode ser reescrito.\n\nMotivo: " + ex);
        }
    }
    
    public void pesquisarCodigoDescicaoProfissional(Profissionais profissional) {
        Util.pesquisarCodigoDescicao(profissional, txtCodProfissional, 
                txtNomeProfissional);
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
        jLabel3 = new javax.swing.JLabel();
        txtCodProfissional = new javax.swing.JTextField();
        lblLocalizarProfissional = new javax.swing.JLabel();
        txtNomeProfissional = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        cbDiasSemana = new javax.swing.JComboBox();
        jLabel15 = new javax.swing.JLabel();
        chkMadrugada = new javax.swing.JCheckBox();
        jLabel16 = new javax.swing.JLabel();
        chkManha = new javax.swing.JCheckBox();
        jLabel17 = new javax.swing.JLabel();
        chkTarde = new javax.swing.JCheckBox();
        jLabel18 = new javax.swing.JLabel();
        chkNoite = new javax.swing.JCheckBox();
        jLabel19 = new javax.swing.JLabel();
        chkAtendeFeriado = new javax.swing.JCheckBox();
        jLabel20 = new javax.swing.JLabel();
        chkAtendeExtra = new javax.swing.JCheckBox();
        pnlBotoes = new javax.swing.JPanel();
        btnConfirmar = new javax.swing.JButton();
        btnAjudar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        mnuBarPrincipal = new javax.swing.JMenuBar();
        mnuArquivos = new javax.swing.JMenu();
        mnuAjuda = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cadastro de Turno do Profissional");
        setMaximumSize(new java.awt.Dimension(730, 264));
        setMinimumSize(new java.awt.Dimension(730, 264));
        setName(""); // NOI18N
        setPreferredSize(new java.awt.Dimension(730, 264));
        setVerifyInputWhenFocusTarget(false);
        getContentPane().setLayout(null);

        tablManutencao.setMinimumSize(new java.awt.Dimension(713, 245));
        tablManutencao.setName(""); // NOI18N

        pnlGeral.setLayout(null);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setText("Cód. Prof. Turnos");
        jLabel2.setToolTipText("");
        pnlGeral.add(jLabel2);
        jLabel2.setBounds(52, 14, 95, 14);

        txtCodigo.setEditable(false);
        txtCodigo.setBackground(java.awt.SystemColor.info);
        txtCodigo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        pnlGeral.add(txtCodigo);
        txtCodigo.setBounds(151, 11, 70, 20);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setText("Cód. Profissional");
        pnlGeral.add(jLabel3);
        jLabel3.setBounds(55, 40, 92, 14);

        txtCodProfissional.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCodProfissional.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodProfissionalFocusLost(evt);
            }
        });
        pnlGeral.add(txtCodProfissional);
        txtCodProfissional.setBounds(151, 37, 70, 20);

        lblLocalizarProfissional.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/localizar.png"))); // NOI18N
        lblLocalizarProfissional.setToolTipText("Localizar uma pessoa já cadastrada.");
        lblLocalizarProfissional.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblLocalizarProfissional.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                lblLocalizarProfissionalMouseReleased(evt);
            }
        });
        pnlGeral.add(lblLocalizarProfissional);
        lblLocalizarProfissional.setBounds(225, 37, 20, 20);

        txtNomeProfissional.setEditable(false);
        txtNomeProfissional.setBackground(java.awt.SystemColor.info);
        txtNomeProfissional.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        pnlGeral.add(txtNomeProfissional);
        txtNomeProfissional.setBounds(249, 37, 402, 20);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Período de Trabalho"));
        jPanel1.setLayout(null);

        cbDiasSemana.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Selecione os dias da semana", "Domingos", "Segundas-feiras", "Terças-feiras", "Quartas-feiras", "Quintas-feiras", "Sextas-feiras", "Sabados" }));
        jPanel1.add(cbDiasSemana);
        cbDiasSemana.setBounds(16, 16, 169, 20);

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel15.setText("Madrugada");
        jLabel15.setToolTipText("");
        jPanel1.add(jLabel15);
        jLabel15.setBounds(16, 47, 64, 14);

        chkMadrugada.setToolTipText("");
        jPanel1.add(chkMadrugada);
        chkMadrugada.setBounds(80, 44, 21, 21);

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel16.setText("Manhã");
        jPanel1.add(jLabel16);
        jLabel16.setBounds(130, 47, 38, 14);

        chkManha.setToolTipText("");
        jPanel1.add(chkManha);
        chkManha.setBounds(168, 44, 21, 21);

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel17.setText("Tarde");
        jPanel1.add(jLabel17);
        jLabel17.setBounds(221, 47, 33, 14);

        chkTarde.setToolTipText("");
        jPanel1.add(chkTarde);
        chkTarde.setBounds(254, 44, 21, 21);

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel18.setText("Noite");
        jPanel1.add(jLabel18);
        jLabel18.setBounds(293, 47, 29, 14);

        chkNoite.setToolTipText("");
        jPanel1.add(chkNoite);
        chkNoite.setBounds(322, 44, 21, 21);

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel19.setText("Atende Feriado");
        jPanel1.add(jLabel19);
        jLabel19.setBounds(361, 47, 86, 14);

        chkAtendeFeriado.setToolTipText("");
        jPanel1.add(chkAtendeFeriado);
        chkAtendeFeriado.setBounds(447, 44, 21, 21);

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel20.setText("Atende Extra");
        jPanel1.add(jLabel20);
        jLabel20.setBounds(486, 47, 74, 14);

        chkAtendeExtra.setToolTipText("");
        jPanel1.add(chkAtendeExtra);
        chkAtendeExtra.setBounds(560, 44, 21, 21);

        pnlGeral.add(jPanel1);
        jPanel1.setBounds(55, 63, 600, 75);

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
                .addGroup(pnlBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnConfirmar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAjudar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(pnlBotoes);
        pnlBotoes.setBounds(0, 175, 713, 38);

        mnuArquivos.setText("Arquivo");
        mnuBarPrincipal.add(mnuArquivos);

        mnuAjuda.setText("Ajuda");
        mnuBarPrincipal.add(mnuAjuda);

        setJMenuBar(mnuBarPrincipal);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirmarActionPerformed
        reescreverObjeto();

        Util.salvar(profissionalTurnos, this, true);
    }//GEN-LAST:event_btnConfirmarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        Util.cancelar(this);
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnAjudarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAjudarActionPerformed
        Util.ajudar();
    }//GEN-LAST:event_btnAjudarActionPerformed

    private void txtCodProfissionalFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodProfissionalFocusLost
        pesquisarCodigoDescicaoProfissional(new Profissionais());
    }//GEN-LAST:event_txtCodProfissionalFocusLost

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
                    "Pessoa não pode ser consultada.\n\nMotivo: " + e);
            }
        }
    }//GEN-LAST:event_lblLocalizarProfissionalMouseReleased

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAjudar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnConfirmar;
    private javax.swing.JComboBox cbDiasSemana;
    private javax.swing.JCheckBox chkAtendeExtra;
    private javax.swing.JCheckBox chkAtendeFeriado;
    private javax.swing.JCheckBox chkMadrugada;
    private javax.swing.JCheckBox chkManha;
    private javax.swing.JCheckBox chkNoite;
    private javax.swing.JCheckBox chkTarde;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblLocalizarProfissional;
    private javax.swing.JMenu mnuAjuda;
    private javax.swing.JMenu mnuArquivos;
    private javax.swing.JMenuBar mnuBarPrincipal;
    private javax.swing.JPanel pnlBotoes;
    private javax.swing.JPanel pnlGeral;
    private javax.swing.JTabbedPane tablManutencao;
    private javax.swing.JTextField txtCodProfissional;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtNomeProfissional;
    // End of variables declaration//GEN-END:variables

    /**
     * @return the profissionalTurnos
     */
    public ProfissionaisTurnos getProfissionalTurnos() {
        return profissionalTurnos;
    }

    /**
     * @param profissionalTurnos the profissionalTurnos to set
     */
    public void setProfissionalTurnos(ProfissionaisTurnos profissionalTurnos) {
        this.profissionalTurnos = profissionalTurnos;
    }
}