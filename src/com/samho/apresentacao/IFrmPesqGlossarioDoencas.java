/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.samho.apresentacao;

import com.samho.dao.DadosDAO;
import com.samho.negocio.GlossarioDoencas;
import com.samho.negocio.Principal;
import com.samho.util.Formatacao;
import com.samho.util.Util;
import java.util.ArrayList;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 * Classe para visualização de objetos do tipo FrmPesquisa.
 *
 * @author Saibel, Sergio Luis
 * @since  date 08/04/2017
 *
 * @version  revision 001.20170408 date 08/04/2017 author Saibel, Sérgio Luis reason
 * Conseguir instanciar um objeto do tipo FrmPesquisa com os atributos e
 * métodos necessários. Esta classe tem como objetivo mostrar objetos obtidos 
 * do banco a fim de visualizar estas informações.
 * Esta extende um JInternalFrame ou seja necessita de um Frame como pai.
 * Permite: Pesquisar, Incluir, Alterar e Excluir um objeto.
 * 
 */
public class IFrmPesqGlossarioDoencas extends JInternalFrame {

    // Declaração de atributos
    private IFrmManGlossarioDoenca iFrmManGlossarioDoenca;
    private GlossarioDoencas glossarioDoenca;
    private JTextField jTextFieldCodigo;
    private JTextField jTextFieldDescricao;
    private ArrayList<DadosDAO> dadosWhere;

    // Construtor
    public IFrmPesqGlossarioDoencas(IFrmManGlossarioDoenca iFrmManGlossarioDoenca)
            throws Exception {
        inicializar(iFrmManGlossarioDoenca);
    }

    public IFrmPesqGlossarioDoencas(IFrmManGlossarioDoenca iFrmManGlossarioDoenca,
            JTextField jTextFieldCodigo, JTextField jTextFieldDescricao)
            throws Exception {
        inicializar(iFrmManGlossarioDoenca);

        this.jTextFieldCodigo = jTextFieldCodigo;
        this.jTextFieldDescricao = jTextFieldDescricao;
    }

    private void inicializar(IFrmManGlossarioDoenca iFrmManGlossarioDoenca) {
        initComponents();

        tbResultado.getTableHeader().setReorderingAllowed(false);
        this.iFrmManGlossarioDoenca = iFrmManGlossarioDoenca;
        glossarioDoenca = iFrmManGlossarioDoenca.getGlossarioDoenca();
        dadosWhere = new ArrayList<>();
        dadosWhere.addAll(glossarioDoenca.getObjetoDAO().getCondicaoWhere());

        Util.atualizar(glossarioDoenca, tbResultado);
    
        verificarPermissoes();
    }
    
    /**
     * Verifica as permissões do  usuário logado
     */
      public void verificarPermissoes (){
        boolean[] acoes = Util.getAcoesUsuario(Principal.GLOSSARIO_DOENCAS);

        btnIncluir.setEnabled(acoes[Util.INCLUIR]);
        btnAlterar.setEnabled(acoes[Util.ALTERAR]);
        btnExcluir.setEnabled(acoes[Util.EXCLUIR]);
        btnAtualizar.setEnabled(acoes[Util.CONSULTAR]);
        btnAplicar.setEnabled(acoes[Util.INCLUIR] || acoes[Util.ALTERAR] || 
                acoes[Util.EXCLUIR]);
    }

    private void reescreverObjeto() {
        try {
            glossarioDoenca.setIdGlossarioDoenca(Long.parseUnsignedLong(
                    tbResultado.getValueAt(
                            tbResultado.getSelectedRow(), 0).toString()));
            glossarioDoenca.setCodProfissionalCadastro(
                    Long.parseUnsignedLong(tbResultado.getValueAt(
                            tbResultado.getSelectedRow(), 1).toString()));
            // Reserva-se a próxima posição para a descrição do campo acima.
            glossarioDoenca.setCID10(tbResultado.getValueAt(
                    tbResultado.getSelectedRow(), 3).toString());
            glossarioDoenca.setDescricao(tbResultado.getValueAt(
                    tbResultado.getSelectedRow(), 4).toString());
            glossarioDoenca.setDiasTratamento(Integer.parseUnsignedInt(
                    tbResultado.getValueAt(
                            tbResultado.getSelectedRow(), 5).toString()));
            glossarioDoenca.setDataRegistro(
                    Formatacao.ajustaData(tbResultado.getValueAt(
                            tbResultado.getSelectedRow(), 6).toString(),
                            Formatacao.DATA_AMD));
            glossarioDoenca.setObservacoes(tbResultado.getValueAt(
                    tbResultado.getSelectedRow(), 7).toString());
            glossarioDoenca.setRevisao(Integer.parseUnsignedInt(
                    tbResultado.getValueAt(
                            tbResultado.getSelectedRow(), 8).toString()));
            
            glossarioDoenca.adicionarCampos();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                    "Registro nao pode ser instanciado.\n\nMotivo: " + ex);
        }
    }

    private void efetuarPesquisa() {
        glossarioDoenca.removerWhere();

        if (!txtCodigo.getText().equals("")) {
            Util.verificarPesquisarCampoID(glossarioDoenca, txtCodigo);
        }

        if (!txtDescricao.getText().equals("")) {
            Util.verificarPesquisarCampoDescricao(glossarioDoenca, txtDescricao);
        }
        
        if (!txtCID10.getText().equals("")) {
            Util.verificarPesquisarCampoDescricao(glossarioDoenca, txtCID10);
        }

        glossarioDoenca.adicionarWhere(dadosWhere);

        Util.atualizar(glossarioDoenca, tbResultado);
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
        btnAtualizar = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        pnlConteudos = new javax.swing.JPanel();
        pnlPesquisarCampos = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtDescricao = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtCID10 = new javax.swing.JTextField();
        pnlResultado = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbResultado = new javax.swing.JTable();
        pnlBotoes = new javax.swing.JPanel();
        btnAjudar = new javax.swing.JButton();
        btnConfirmar = new javax.swing.JButton();
        btnAplicar = new javax.swing.JButton();

        jMenu1.setText("jMenu1");

        setMaximizable(true);
        setTitle("Consultar Planos Glossário de Doneças");
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

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        javax.swing.GroupLayout pnlMenuBotoesLayout = new javax.swing.GroupLayout(pnlMenuBotoes);
        pnlMenuBotoes.setLayout(pnlMenuBotoesLayout);
        pnlMenuBotoesLayout.setHorizontalGroup(
            pnlMenuBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMenuBotoesLayout.createSequentialGroup()
                .addComponent(btnIncluir)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAlterar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnExcluir)
                .addGap(163, 163, 163)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAtualizar)
                .addContainerGap(240, Short.MAX_VALUE))
        );
        pnlMenuBotoesLayout.setVerticalGroup(
            pnlMenuBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlMenuBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(btnAlterar)
                .addComponent(btnExcluir)
                .addComponent(btnIncluir))
            .addGroup(pnlMenuBotoesLayout.createSequentialGroup()
                .addComponent(btnAtualizar)
                .addGap(0, 1, Short.MAX_VALUE))
        );

        pnlConteudos.setName("pnlCorpo"); // NOI18N

        pnlPesquisarCampos.setBorder(javax.swing.BorderFactory.createTitledBorder("Filtros para consulta"));

        jLabel1.setText("Cód. Glossário");

        txtCodigo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCodigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCodigoKeyReleased(evt);
            }
        });

        jLabel2.setText("Doença");

        txtDescricao.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtDescricao.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtDescricaoKeyReleased(evt);
            }
        });

        jLabel7.setText("CID-10");

        txtCID10.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtCID10.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCID10KeyReleased(evt);
            }
        });

        javax.swing.GroupLayout pnlPesquisarCamposLayout = new javax.swing.GroupLayout(pnlPesquisarCampos);
        pnlPesquisarCampos.setLayout(pnlPesquisarCamposLayout);
        pnlPesquisarCamposLayout.setHorizontalGroup(
            pnlPesquisarCamposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlPesquisarCamposLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlPesquisarCamposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlPesquisarCamposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlPesquisarCamposLayout.createSequentialGroup()
                        .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtCID10, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27))
        );
        pnlPesquisarCamposLayout.setVerticalGroup(
            pnlPesquisarCamposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPesquisarCamposLayout.createSequentialGroup()
                .addGroup(pnlPesquisarCamposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlPesquisarCamposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(txtDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlPesquisarCamposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlPesquisarCamposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtCID10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 1, Short.MAX_VALUE))
        );

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
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout pnlConteudosLayout = new javax.swing.GroupLayout(pnlConteudos);
        pnlConteudos.setLayout(pnlConteudosLayout);
        pnlConteudosLayout.setHorizontalGroup(
            pnlConteudosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlPesquisarCampos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnlResultado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnlConteudosLayout.setVerticalGroup(
            pnlConteudosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlConteudosLayout.createSequentialGroup()
                .addComponent(pnlPesquisarCampos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
            .addComponent(pnlBotoes, javax.swing.GroupLayout.DEFAULT_SIZE, 713, Short.MAX_VALUE)
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
            iFrmManGlossarioDoenca.carregarObjetoIFrmManutencao(
                    new GlossarioDoencas());

            Util.incluir(this, iFrmManGlossarioDoenca);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                    "Registro nao pode ser incluido.\n\nMotivo: " + ex);
        }
    }//GEN-LAST:event_btnIncluirActionPerformed

    private void btnAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAlterarActionPerformed
        try {
            reescreverObjeto();
            iFrmManGlossarioDoenca.carregarObjetoIFrmManutencao(glossarioDoenca);

            Util.alterar(this, iFrmManGlossarioDoenca);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                    "Registro nao pode ser alterado.\n\nMotivo: " + ex);
        }
    }//GEN-LAST:event_btnAlterarActionPerformed

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
        Util.excluir(glossarioDoenca, tbResultado, this);
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

    private void txtCodigoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoKeyReleased
        efetuarPesquisa();
    }//GEN-LAST:event_txtCodigoKeyReleased

    private void txtDescricaoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescricaoKeyReleased
        efetuarPesquisa();
    }//GEN-LAST:event_txtDescricaoKeyReleased

    private void tbResultadoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbResultadoMouseClicked
        if ((btnAlterar.isEnabled()) && (evt.getClickCount() == 2)) {
            try {
                reescreverObjeto();

                if ((jTextFieldCodigo == null) && (jTextFieldDescricao == null)) {
                    iFrmManGlossarioDoenca.carregarObjetoIFrmManutencao(
                            glossarioDoenca);

                    Util.alterar(this, iFrmManGlossarioDoenca);
                } else {
                    jTextFieldCodigo.setText(String.valueOf(
                            glossarioDoenca.getIdGlossarioDoenca()));
                    jTextFieldDescricao.setText(glossarioDoenca.getDescricao());

                    Util.cancelar(this);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this.getParent(),
                        "Registro nao pode ser alterado.\n\nMotivo: " + ex);
            }
        }
    }//GEN-LAST:event_tbResultadoMouseClicked

    private void txtCID10KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCID10KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCID10KeyReleased

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAjudar;
    private javax.swing.JButton btnAlterar;
    private javax.swing.JButton btnAplicar;
    private javax.swing.JButton btnAtualizar;
    private javax.swing.JButton btnConfirmar;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnIncluir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JPanel pnlBotoes;
    private javax.swing.JPanel pnlConteudos;
    private javax.swing.JPanel pnlMenuBotoes;
    private javax.swing.JPanel pnlPesquisarCampos;
    private javax.swing.JPanel pnlResultado;
    private javax.swing.JTable tbResultado;
    private javax.swing.JTextField txtCID10;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtDescricao;
    // End of variables declaration//GEN-END:variables

    /**
     * @return the glossarioDoenca
     */
    public GlossarioDoencas getGlossarioDoenca() {
        return glossarioDoenca;
    }

    /**
     * @param glossarioDoenca the glossarioDoenca to set
     */
    public void setGlossarioDoenca(GlossarioDoencas glossarioDoenca) {
        this.glossarioDoenca = glossarioDoenca;
    }

}
