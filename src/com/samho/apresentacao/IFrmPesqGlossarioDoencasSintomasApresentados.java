/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.samho.apresentacao;

import com.samho.dao.DadosDAO;
import com.samho.negocio.ProntuariosSintomasApresentados;
import com.samho.negocio.Principal;
import com.samho.util.Registrador;
import com.samho.util.Util;
import java.awt.HeadlessException;
import java.util.ArrayList;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 * Classe para visualização de objetos do tipo FrmPesquisa.
 *
 * @author Saibel, Sergio Luis
 * @since  date 04/04/2017
 *
 * @version  revision 001.20170404 date 04/04/2017 author Saibel, Sérgio Luis reason
 * Conseguir instanciar um objeto do tipo FrmPesquisa com os atributos e métodos
 * necessários para efetuar pesquisas.
 */
public class IFrmPesqGlossarioDoencasSintomasApresentados extends JInternalFrame {

    // Declaração de atributos
    private IFrmManProntuarioSintomaApresentado iFrmManProntuarioSintomaApresentado;
    private ProntuariosSintomasApresentados prontuarioSintomaApresentado;
    private JTextField jTextFieldCodigo;
    private JTextField jTextFieldDescricao;
    private ArrayList<DadosDAO> dadosWhere;

    // Construtor
    public IFrmPesqGlossarioDoencasSintomasApresentados(
            IFrmManProntuarioSintomaApresentado iFrmManProntuarioSintomaApresentado)
            throws Exception {
        inicializar(iFrmManProntuarioSintomaApresentado);
    }

    public IFrmPesqGlossarioDoencasSintomasApresentados(
            IFrmManProntuarioSintomaApresentado iFrmManProntuarioSintomaApresentado,
            JTextField jTextFieldCodigo, JTextField jTextFieldDescricao)
            throws Exception {
        inicializar(iFrmManProntuarioSintomaApresentado);

        this.jTextFieldCodigo = jTextFieldCodigo;
        this.jTextFieldDescricao = jTextFieldDescricao;
    }

    private void inicializar(
            IFrmManProntuarioSintomaApresentado iFrmManProntuarioSintomaApresentado) {
        initComponents();

        tbResultado.getTableHeader().setReorderingAllowed(false);
        this.iFrmManProntuarioSintomaApresentado = iFrmManProntuarioSintomaApresentado;
        prontuarioSintomaApresentado = 
                iFrmManProntuarioSintomaApresentado.getProntuarioSintomaApresentado();
        dadosWhere = new ArrayList<>();
        dadosWhere.addAll(prontuarioSintomaApresentado.getObjetoDAO().getCondicaoWhere());

        Util.atualizar(prontuarioSintomaApresentado, tbResultado);
    
        verificarPermissoes();
    }
    
    /**
     * Verifica as permissões do  usuário logado
     */
    public void verificarPermissoes (){
        boolean[] acoes = Util.getAcoesUsuario(
                Principal.GLOSSARIO_DOENCAS_SINTOMAS_APRESENTADOS);

        btnIncluir.setEnabled(acoes[Util.INCLUIR]);
        btnAlterar.setEnabled(acoes[Util.ALTERAR]);
        btnExcluir.setEnabled(acoes[Util.EXCLUIR]);
        btnAtualizar.setEnabled(acoes[Util.CONSULTAR]);
        btnAplicar.setEnabled(acoes[Util.INCLUIR] || acoes[Util.ALTERAR] || 
                acoes[Util.EXCLUIR]);
    }

    private void reescreverObjeto() {
        try {
            prontuarioSintomaApresentado.setIdProntuarioSintomaApresentado(
                    Long.parseUnsignedLong(tbResultado.getValueAt(
                            tbResultado.getSelectedRow(), 0).toString()));
            prontuarioSintomaApresentado.setCodProntuario(Long.parseUnsignedLong(
                    tbResultado.getValueAt(
                            tbResultado.getSelectedRow(), 1).toString()));
            // Reserva-se a próxima posição para a descrição do campo acima.
            prontuarioSintomaApresentado.setCodSintomaApresentado(
                    Long.parseUnsignedLong(tbResultado.getValueAt(
                            tbResultado.getSelectedRow(), 3).toString()));
            // Reserva-se a próxima posição para a descrição do campo acima.
            prontuarioSintomaApresentado.setDiasComSintoma(
                    Integer.parseUnsignedInt(tbResultado.getValueAt(
                            tbResultado.getSelectedRow(), 5).toString()));

            prontuarioSintomaApresentado.adicionarCampos();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                    "Registro nao pode ser instanciado.\n\nMotivo: " + ex);
        }
    }

    private void efetuarPesquisa() {
        prontuarioSintomaApresentado.removerWhere();

        if (!txtCodigo.getText().equals("")) {
            Util.verificarPesquisarCampoID(prontuarioSintomaApresentado, txtCodigo);
        }

        if (!txtDescricao.getText().equals("")) {
            Util.verificarPesquisarCampoDescricao(prontuarioSintomaApresentado, 
                    txtDescricao);
        }
        
        prontuarioSintomaApresentado.adicionarWhere(dadosWhere);

        Util.atualizar(prontuarioSintomaApresentado, tbResultado);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenu1 = new javax.swing.JMenu();
        pnlMenuBotoes = new javax.swing.JPanel();
        btnIncluir = new javax.swing.JButton();
        btnAlterar = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        btnAtualizar = new javax.swing.JButton();
        pnlConteudos = new javax.swing.JPanel();
        pnlResultado = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbResultado = new javax.swing.JTable();
        pnlPesquisarCampos1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtDescricao = new javax.swing.JTextField();
        pnlBotoes = new javax.swing.JPanel();
        btnAjudar = new javax.swing.JButton();
        btnConfirmar = new javax.swing.JButton();
        btnAplicar = new javax.swing.JButton();

        jMenu1.setText("jMenu1");

        setMaximizable(true);
        setTitle("Consultar Prontuários Sintomas Apresentados");
        setMinimumSize(new java.awt.Dimension(700, 500));

        pnlMenuBotoes.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        pnlMenuBotoes.setName("pnlTopo"); // NOI18N

        btnIncluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/incluir.png"))); // NOI18N
        btnIncluir.setText("Incluir");
        btnIncluir.setToolTipText("Incluir");
        btnIncluir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnIncluir.setMargin(new java.awt.Insets(2, 5, 2, 5));
        btnIncluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIncluirActionPerformed(evt);
            }
        });

        btnAlterar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/editar.png"))); // NOI18N
        btnAlterar.setText("Alterar");
        btnAlterar.setToolTipText("Alterar");
        btnAlterar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAlterar.setMargin(new java.awt.Insets(2, 5, 2, 5));
        btnAlterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAlterarActionPerformed(evt);
            }
        });

        btnExcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/excluir.png"))); // NOI18N
        btnExcluir.setText("Excluir");
        btnExcluir.setToolTipText("Excluir");
        btnExcluir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnExcluir.setMargin(new java.awt.Insets(2, 5, 2, 5));
        btnExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirActionPerformed(evt);
            }
        });

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        btnAtualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/atualizar.png"))); // NOI18N
        btnAtualizar.setText("Atualizar");
        btnAtualizar.setToolTipText("Atualizar");
        btnAtualizar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAtualizar.setMargin(new java.awt.Insets(2, 5, 2, 5));
        btnAtualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtualizarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlMenuBotoesLayout = new javax.swing.GroupLayout(pnlMenuBotoes);
        pnlMenuBotoes.setLayout(pnlMenuBotoesLayout);
        pnlMenuBotoesLayout.setHorizontalGroup(
            pnlMenuBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMenuBotoesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnIncluir)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAlterar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnExcluir)
                .addGap(163, 163, 163)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAtualizar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlMenuBotoesLayout.setVerticalGroup(
            pnlMenuBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(btnAtualizar)
            .addGroup(pnlMenuBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(btnIncluir)
                .addComponent(btnAlterar)
                .addComponent(btnExcluir))
        );

        pnlConteudos.setName("pnlCorpo"); // NOI18N

        pnlResultado.setBorder(javax.swing.BorderFactory.createTitledBorder("Resultados"));

        tbResultado.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tbResultado.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbResultadoMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbResultado);

        javax.swing.GroupLayout pnlResultadoLayout = new javax.swing.GroupLayout(pnlResultado);
        pnlResultado.setLayout(pnlResultadoLayout);
        pnlResultadoLayout.setHorizontalGroup(
            pnlResultadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );
        pnlResultadoLayout.setVerticalGroup(
            pnlResultadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE)
        );

        pnlPesquisarCampos1.setBorder(javax.swing.BorderFactory.createTitledBorder("Filtros para consulta"));

        jLabel3.setText("Cód. Pront. Sin.");

        txtCodigo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCodigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCodigoKeyReleased(evt);
            }
        });

        jLabel4.setText("Descrição");

        txtDescricao.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtDescricao.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtDescricaoKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout pnlPesquisarCampos1Layout = new javax.swing.GroupLayout(pnlPesquisarCampos1);
        pnlPesquisarCampos1.setLayout(pnlPesquisarCampos1Layout);
        pnlPesquisarCampos1Layout.setHorizontalGroup(
            pnlPesquisarCampos1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPesquisarCampos1Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlPesquisarCampos1Layout.setVerticalGroup(
            pnlPesquisarCampos1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPesquisarCampos1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlPesquisarCampos1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlPesquisarCampos1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4)
                        .addComponent(txtDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlPesquisarCampos1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnlConteudosLayout = new javax.swing.GroupLayout(pnlConteudos);
        pnlConteudos.setLayout(pnlConteudosLayout);
        pnlConteudosLayout.setHorizontalGroup(
            pnlConteudosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlResultado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlConteudosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlPesquisarCampos1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlConteudosLayout.setVerticalGroup(
            pnlConteudosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlConteudosLayout.createSequentialGroup()
                .addComponent(pnlPesquisarCampos1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlResultado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlBotoes.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        pnlBotoes.setName("pnlBotao"); // NOI18N
        pnlBotoes.setPreferredSize(new java.awt.Dimension(229, 33));

        btnAjudar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/ajudar.png"))); // NOI18N
        btnAjudar.setToolTipText("Ajuda");
        btnAjudar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAjudar.setEnabled(false);
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

        btnConfirmar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/confirmar.png"))); // NOI18N
        btnConfirmar.setText("Ok");
        btnConfirmar.setToolTipText("Ok");
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

        btnAplicar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/aplicar.png"))); // NOI18N
        btnAplicar.setText("Aplicar");
        btnAplicar.setToolTipText("Aplicar");
        btnAplicar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAplicar.setEnabled(false);
        btnAplicar.setMargin(new java.awt.Insets(2, 5, 2, 5));
        btnAplicar.setMaximumSize(new java.awt.Dimension(70, 20));
        btnAplicar.setMinimumSize(new java.awt.Dimension(70, 20));
        btnAplicar.setName("btnCancelar"); // NOI18N
        btnAplicar.setPreferredSize(new java.awt.Dimension(85, 25));
        btnAplicar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAplicarActionPerformed(evt);
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
                .addComponent(btnAplicar, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnlBotoesLayout.setVerticalGroup(
            pnlBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBotoesLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(pnlBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnConfirmar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAjudar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAplicar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlMenuBotoes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnlConteudos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnlBotoes, javax.swing.GroupLayout.DEFAULT_SIZE, 723, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlMenuBotoes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlConteudos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlBotoes, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnIncluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIncluirActionPerformed
        try {
            iFrmManProntuarioSintomaApresentado.carregarObjetoIFrmManutencao(
                    new ProntuariosSintomasApresentados());

            Util.incluir(this, iFrmManProntuarioSintomaApresentado);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                    "Registro nao pode ser incluido.\n\nMotivo: " + ex);
        }
    }//GEN-LAST:event_btnIncluirActionPerformed

    private void btnAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAlterarActionPerformed
        try {
            reescreverObjeto();
            iFrmManProntuarioSintomaApresentado.carregarObjetoIFrmManutencao(
                    prontuarioSintomaApresentado);

            JOptionPane.showMessageDialog(this.getParent(),
                    Util.alterar(this, iFrmManProntuarioSintomaApresentado));
        } catch (HeadlessException ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                    "Registro nao pode ser alterado.\n\nMotivo: " + ex);
        }
    }//GEN-LAST:event_btnAlterarActionPerformed

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
        Util.excluir(prontuarioSintomaApresentado, tbResultado, this);
    }//GEN-LAST:event_btnExcluirActionPerformed

    private void btnAtualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtualizarActionPerformed
        efetuarPesquisa();
    }//GEN-LAST:event_btnAtualizarActionPerformed

    private void btnAjudarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAjudarActionPerformed
        Util.ajudar();
    }//GEN-LAST:event_btnAjudarActionPerformed

    private void btnConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirmarActionPerformed
        Util.confirmar(this);
    }//GEN-LAST:event_btnConfirmarActionPerformed

    private void btnAplicarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAplicarActionPerformed
        Util.aplicar();
    }//GEN-LAST:event_btnAplicarActionPerformed

    private void tbResultadoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbResultadoMouseClicked
        if ((btnAlterar.isEnabled()) && (evt.getClickCount() == 2)) {
            try {
                reescreverObjeto();

                if ((jTextFieldCodigo != null) && (jTextFieldDescricao != null)) {
                    Registrador registrador = new Registrador();
                    registrador = prontuarioSintomaApresentado.getObjetoDAO().popularCodigoDescricao(
                            String.valueOf(prontuarioSintomaApresentado.getIdProntuarioSintomaApresentado()),
                            prontuarioSintomaApresentado.getProntuarioSintomaApresentadoDAO().getSQLSubstiturCampoDescricao(
                                    prontuarioSintomaApresentado.getIdProntuarioSintomaApresentado()));
                    jTextFieldCodigo.setText(String.valueOf(
                            registrador.getCodigo()));
                    jTextFieldDescricao.setText(registrador.getDescricao());
                } else {
                    iFrmManProntuarioSintomaApresentado.carregarObjetoIFrmManutencao(
                            prontuarioSintomaApresentado);

                    Util.alterar(this, iFrmManProntuarioSintomaApresentado);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this.getParent(),
                        "Registro nao pode ser alterado.\n\nMotivo: " + ex);
            }

            Util.cancelar(this);
        }
    }//GEN-LAST:event_tbResultadoMouseClicked

    private void txtCodigoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoKeyReleased
        efetuarPesquisa();
    }//GEN-LAST:event_txtCodigoKeyReleased

    private void txtDescricaoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescricaoKeyReleased
        efetuarPesquisa();
    }//GEN-LAST:event_txtDescricaoKeyReleased

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAjudar;
    private javax.swing.JButton btnAlterar;
    private javax.swing.JButton btnAplicar;
    private javax.swing.JButton btnAtualizar;
    private javax.swing.JButton btnConfirmar;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnIncluir;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JPanel pnlBotoes;
    private javax.swing.JPanel pnlConteudos;
    private javax.swing.JPanel pnlMenuBotoes;
    private javax.swing.JPanel pnlPesquisarCampos1;
    private javax.swing.JPanel pnlResultado;
    private javax.swing.JTable tbResultado;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtDescricao;
    // End of variables declaration//GEN-END:variables

    /**
     * @return the prontuarioSintomaApresentado
     */
    public ProntuariosSintomasApresentados getProntuarioSintomaApresentado() {
        return prontuarioSintomaApresentado;
    }

    /**
     * @param prontuarioSintomaApresentado the prontuarioSintomaApresentado to set
     */
    public void setProntuarioSintomaApresentado(ProntuariosSintomasApresentados prontuarioSintomaApresentado) {
        this.prontuarioSintomaApresentado = prontuarioSintomaApresentado;
    }
}
