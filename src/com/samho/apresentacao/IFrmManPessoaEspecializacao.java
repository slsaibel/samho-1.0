/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.samho.apresentacao;

import com.samho.negocio.Especializacoes;
import com.samho.negocio.Pessoas;
import com.samho.negocio.PessoasEspecializacoes;
import com.samho.negocio.Principal;
import com.samho.util.Formatacao;
import com.samho.util.JTextFieldDocument;
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
public final class IFrmManPessoaEspecializacao extends JInternalFrame {

    // Declaração de atributos
    private PessoasEspecializacoes pessoaEspecializacao;

    // Construtor
    public IFrmManPessoaEspecializacao(
            PessoasEspecializacoes pessoaEspecializacao) throws Exception {
        initComponents();
        
        Formatacao.reformatarData(txtDataDeConclusao);
        
        Util.limparCampos(rootPane);

        this.pessoaEspecializacao = pessoaEspecializacao;

        // Permite que somente números sejam informados neste campo
        txtCodigo.setDocument(new JTextFieldDocument(true));
        txtCodEspecializacao.setDocument(new JTextFieldDocument(true));
        txtCodPessoa.setDocument(new JTextFieldDocument(true));
        txtNumeroRegistro.setDocument(new JTextFieldDocument(true));
        // Converte para caixa alta a string informada no campo
        txtInstituicao.setDocument(new JTextFieldDocument(false));

        carregarObjetoIFrmManutencao(pessoaEspecializacao);
    }

    public void carregarObjetoIFrmManutencao(
            PessoasEspecializacoes pessoaEspecializacao) {
        try {
            if (pessoaEspecializacao.getIdPessoaEspecializacao() != -1) {
                txtCodigo.setText(String.valueOf(
                        pessoaEspecializacao.getIdPessoaEspecializacao()));
            } else {
                txtCodigo.setText(String.valueOf(
                        pessoaEspecializacao.getPessoaEspecializacaoDAO().getProximoID()));
            }

            txtCodPessoa.setText(String.valueOf(
                    pessoaEspecializacao.getCodPessoa()));
            pesquisarCodigoDescicaoPessoa(new Pessoas());
            txtCodEspecializacao.setText(String.valueOf(
                    pessoaEspecializacao.getCodEspecializacao()));
            pesquisarCodigoDescicaoEspecializacao(new Especializacoes());
            txtInstituicao.setText(pessoaEspecializacao.getInstituicao());
            txtNumeroRegistro.setText(String.valueOf(
                    pessoaEspecializacao.getNumRegistro()));
            txtDataDeConclusao.setText(Formatacao.ajustaDataDMA(
                    pessoaEspecializacao.getDataConclusao()));
            chkAtivo.setSelected(pessoaEspecializacao.isAtivo());
            txtObservacoes.setText(pessoaEspecializacao.getObservacoes());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                    "Registro nao pode ser carregado.\n\nMotivo: " + ex);
        }
    }

    private void reescreverObjeto() {
        try {
            pessoaEspecializacao.setIdPessoaEspecializacao(
                    Long.parseUnsignedLong(txtCodigo.getText()));
            pessoaEspecializacao.setCodPessoa(Long.parseUnsignedLong(
                    txtCodPessoa.getText()));
            pessoaEspecializacao.setCodEspecializacao(Long.parseUnsignedLong(
                    txtCodEspecializacao.getText()));
            pessoaEspecializacao.setInstituicao(txtInstituicao.getText());
            pessoaEspecializacao.setNumRegistro(Integer.valueOf(
                    txtNumeroRegistro.getText()));
            pessoaEspecializacao.setDataConclusao(Formatacao.ajustaData(
                    txtDataDeConclusao.getText(), Formatacao.DATA_DMA));
            pessoaEspecializacao.setAtivo(chkAtivo.isSelected());
            pessoaEspecializacao.setObservacoes(txtObservacoes.getText());
            
            pessoaEspecializacao.adicionarCampos();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                    "Registro nao pode ser reescrito.\n\nMotivo: " + ex);
        }
    }

    public void pesquisarCodigoDescicaoPessoa(Pessoas pessoa) {
        Util.pesquisarCodigoDescicao(pessoa, txtCodPessoa, txtNomePessoa);
    }

    public void pesquisarCodigoDescicaoEspecializacao(Especializacoes especializacao) {
        Util.pesquisarCodigoDescicao(
                especializacao, txtCodEspecializacao, txtDescrEspecializacao);
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
        txtCodPessoa = new javax.swing.JTextField();
        lblLocalizarPessoa = new javax.swing.JLabel();
        txtNomePessoa = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtCodEspecializacao = new javax.swing.JTextField();
        lblLocalizarEspecializacao = new javax.swing.JLabel();
        txtDescrEspecializacao = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtInstituicao = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtNumeroRegistro = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtDataDeConclusao = new javax.swing.JFormattedTextField();
        jLabel7 = new javax.swing.JLabel();
        chkAtivo = new javax.swing.JCheckBox();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtObservacoes = new javax.swing.JTextArea();
        pnlBotoes = new javax.swing.JPanel();
        btnConfirmar = new javax.swing.JButton();
        btnAjudar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        mnuBarPrincipal = new javax.swing.JMenuBar();
        mnuArquivos = new javax.swing.JMenu();
        mnuAjuda = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cadastro de Especialização por Pessoa");
        setMaximumSize(new java.awt.Dimension(730, 370));
        setMinimumSize(new java.awt.Dimension(730, 370));
        setName(""); // NOI18N
        setPreferredSize(new java.awt.Dimension(730, 370));
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
        jLabel2.setText("Cód. Prof. Espec.");
        pnlGeral.add(jLabel2);
        jLabel2.setBounds(67, 14, 91, 14);

        txtCodigo.setEditable(false);
        txtCodigo.setBackground(java.awt.SystemColor.info);
        txtCodigo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        pnlGeral.add(txtCodigo);
        txtCodigo.setBounds(162, 11, 70, 20);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setText("Cód. Pessoa");
        pnlGeral.add(jLabel1);
        jLabel1.setBounds(91, 40, 67, 14);

        txtCodPessoa.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCodPessoa.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodPessoaFocusLost(evt);
            }
        });
        pnlGeral.add(txtCodPessoa);
        txtCodPessoa.setBounds(162, 37, 70, 20);

        lblLocalizarPessoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/localizar.png"))); // NOI18N
        lblLocalizarPessoa.setToolTipText("Localizar uma pessoa já cadastrada.");
        lblLocalizarPessoa.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblLocalizarPessoa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                lblLocalizarPessoaMouseReleased(evt);
            }
        });
        pnlGeral.add(lblLocalizarPessoa);
        lblLocalizarPessoa.setBounds(236, 37, 20, 20);

        txtNomePessoa.setEditable(false);
        txtNomePessoa.setBackground(java.awt.SystemColor.info);
        txtNomePessoa.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        pnlGeral.add(txtNomePessoa);
        txtNomePessoa.setBounds(260, 37, 402, 20);

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel4.setText("Cód. Especialização");
        pnlGeral.add(jLabel4);
        jLabel4.setBounds(50, 66, 108, 14);

        txtCodEspecializacao.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCodEspecializacao.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodEspecializacaoFocusLost(evt);
            }
        });
        pnlGeral.add(txtCodEspecializacao);
        txtCodEspecializacao.setBounds(162, 63, 70, 20);

        lblLocalizarEspecializacao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/localizar.png"))); // NOI18N
        lblLocalizarEspecializacao.setToolTipText("Localizar uma função já cadastrada.");
        lblLocalizarEspecializacao.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblLocalizarEspecializacao.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                lblLocalizarEspecializacaoMouseReleased(evt);
            }
        });
        pnlGeral.add(lblLocalizarEspecializacao);
        lblLocalizarEspecializacao.setBounds(236, 63, 20, 20);

        txtDescrEspecializacao.setEditable(false);
        txtDescrEspecializacao.setBackground(java.awt.SystemColor.info);
        txtDescrEspecializacao.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        pnlGeral.add(txtDescrEspecializacao);
        txtDescrEspecializacao.setBounds(260, 63, 402, 20);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setText("Instituição");
        pnlGeral.add(jLabel3);
        jLabel3.setBounds(97, 92, 61, 14);

        txtInstituicao.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        pnlGeral.add(txtInstituicao);
        txtInstituicao.setBounds(162, 89, 500, 20);

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel12.setText("Número Registro");
        pnlGeral.add(jLabel12);
        jLabel12.setBounds(63, 118, 95, 14);

        txtNumeroRegistro.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtNumeroRegistro.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNumeroRegistroFocusGained(evt);
            }
        });
        pnlGeral.add(txtNumeroRegistro);
        txtNumeroRegistro.setBounds(162, 115, 70, 20);

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel6.setText("Data de Conclusão");
        pnlGeral.add(jLabel6);
        jLabel6.setBounds(257, 118, 104, 14);

        try {
            txtDataDeConclusao.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtDataDeConclusao.setVerifyInputWhenFocusTarget(false);
        txtDataDeConclusao.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDataDeConclusaoFocusGained(evt);
            }
        });
        pnlGeral.add(txtDataDeConclusao);
        txtDataDeConclusao.setBounds(365, 115, 70, 20);

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel7.setText("Ativo");
        pnlGeral.add(jLabel7);
        jLabel7.setBounds(128, 141, 30, 14);

        chkAtivo.setToolTipText("");
        pnlGeral.add(chkAtivo);
        chkAtivo.setBounds(162, 138, 21, 21);

        jLabel8.setText("Observações");
        pnlGeral.add(jLabel8);
        jLabel8.setBounds(95, 161, 63, 14);

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setAutoscrolls(true);
        jScrollPane1.setMaximumSize(new java.awt.Dimension(32767, 500));

        txtObservacoes.setColumns(60);
        txtObservacoes.setRows(5);
        txtObservacoes.setToolTipText("");
        txtObservacoes.setAutoscrolls(false);
        txtObservacoes.setMaximumSize(new java.awt.Dimension(497, 74));
        txtObservacoes.setMinimumSize(new java.awt.Dimension(497, 74));
        jScrollPane1.setViewportView(txtObservacoes);

        pnlGeral.add(jScrollPane1);
        jScrollPane1.setBounds(165, 161, 497, 74);

        tablManutencao.addTab("Geral", pnlGeral);

        getContentPane().add(tablManutencao);
        tablManutencao.setBounds(0, 0, 710, 275);
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

        Util.salvar(pessoaEspecializacao, this, true);
    }//GEN-LAST:event_btnConfirmarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        Util.cancelar(this);
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnAjudarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAjudarActionPerformed
        Util.ajudar();
    }//GEN-LAST:event_btnAjudarActionPerformed

    private void lblLocalizarPessoaMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblLocalizarPessoaMouseReleased
        boolean[] acoes = Util.getAcoesUsuario(Principal.PESSOAS);

        if (!acoes[Util.CONSULTAR]) {
            JOptionPane.showMessageDialog(this.getParent(),
                    "Você não pode efetuar esta consulta.\n\nMotivo: "
                            + "Usuário sem permissão.");
        } else {        
            try {
                IFrmPesqPessoas iFrmPesqPessoas = new IFrmPesqPessoas(
                        new IFrmManPessoa(new Pessoas()), txtCodPessoa, txtNomePessoa);

                this.getParent().remove(iFrmPesqPessoas);
                this.getParent().add(iFrmPesqPessoas);

                Util.centralizar(this.getParent(), iFrmPesqPessoas);

                iFrmPesqPessoas.setVisible(true);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this.getParent(),
                        "Pessoas não podem ser consultadas.\n\nMotivo: " + e);
            }
        }
    }//GEN-LAST:event_lblLocalizarPessoaMouseReleased

    private void txtCodPessoaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodPessoaFocusLost
        pesquisarCodigoDescicaoPessoa(new Pessoas());
    }//GEN-LAST:event_txtCodPessoaFocusLost

    private void txtDataDeConclusaoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDataDeConclusaoFocusGained
        txtDataDeConclusao.selectAll();
    }//GEN-LAST:event_txtDataDeConclusaoFocusGained

    private void txtNumeroRegistroFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNumeroRegistroFocusGained
        txtNumeroRegistro.selectAll();
    }//GEN-LAST:event_txtNumeroRegistroFocusGained

    private void lblLocalizarEspecializacaoMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblLocalizarEspecializacaoMouseReleased
        boolean[] acoes = Util.getAcoesUsuario(Principal.ESPECIALIZACOES);

        if (!acoes[Util.CONSULTAR]) {
            JOptionPane.showMessageDialog(this.getParent(),
                    "Você não pode efetuar esta consulta.\n\nMotivo: "
                            + "Usuário sem permissão.");
        } else { 
            try {
                IFrmPesqEspecializacoes iFrmPesqEspecializacoes = 
                        new IFrmPesqEspecializacoes(new IFrmManEspecializacao(
                                new Especializacoes()), txtCodEspecializacao, 
                                txtDescrEspecializacao);

                this.getParent().remove(iFrmPesqEspecializacoes);
                this.getParent().add(iFrmPesqEspecializacoes);

                Util.centralizar(this.getParent(), iFrmPesqEspecializacoes);

                iFrmPesqEspecializacoes.setVisible(true);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this.getParent(),
                    "Especializações não podem ser consultadas.\n\nMotivo: " + e);
            }
        }
    }//GEN-LAST:event_lblLocalizarEspecializacaoMouseReleased

    private void txtCodEspecializacaoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodEspecializacaoFocusLost
        pesquisarCodigoDescicaoEspecializacao(new Especializacoes());
    }//GEN-LAST:event_txtCodEspecializacaoFocusLost

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAjudar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnConfirmar;
    private javax.swing.JCheckBox chkAtivo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblLocalizarEspecializacao;
    private javax.swing.JLabel lblLocalizarPessoa;
    private javax.swing.JMenu mnuAjuda;
    private javax.swing.JMenu mnuArquivos;
    private javax.swing.JMenuBar mnuBarPrincipal;
    private javax.swing.JPanel pnlBotoes;
    private javax.swing.JPanel pnlGeral;
    private javax.swing.JTabbedPane tablManutencao;
    private javax.swing.JTextField txtCodEspecializacao;
    private javax.swing.JTextField txtCodPessoa;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JFormattedTextField txtDataDeConclusao;
    private javax.swing.JTextField txtDescrEspecializacao;
    private javax.swing.JTextField txtInstituicao;
    private javax.swing.JTextField txtNomePessoa;
    private javax.swing.JTextField txtNumeroRegistro;
    private javax.swing.JTextArea txtObservacoes;
    // End of variables declaration//GEN-END:variables

    /**
     * @return the pessoaEspecializacao
     */
    public PessoasEspecializacoes getPessoaEspecializacao() {
        return pessoaEspecializacao;
    }

    /**
     * @param pessoaEspecializacao the pessoaEspecializacao to set
     */
    public void setPessoaEspecializacao(PessoasEspecializacoes pessoaEspecializacao) {
        this.pessoaEspecializacao = pessoaEspecializacao;
    }
}
