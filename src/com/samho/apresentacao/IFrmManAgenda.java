/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.samho.apresentacao;

import com.samho.dao.DadosDAO;
import com.samho.negocio.Agendas;
import com.samho.negocio.Funcionarios;
import com.samho.negocio.Clientes;
import com.samho.negocio.Profissionais;
import com.samho.negocio.Principal;
import com.samho.negocio.MotivosAgendas;
import com.samho.negocio.ProfissionaisTurnos;
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
public final class IFrmManAgenda extends JInternalFrame {

    // Declaração de atributos
    private Agendas agenda;
    private Profissionais profissional;
    private ProfissionaisTurnos profissionalTurnos;

    // Construtor
    public IFrmManAgenda(Agendas agenda) throws Exception {
        initComponents();
        
        Formatacao.reformatarData(txtDataDeAgendamento);
        Formatacao.reformatarHora(txtHorario);
        
        Util.limparCampos(rootPane);

        this.agenda = agenda;

        // Permite que somente números sejam informados neste campo
        txtCodigo.setDocument(new JTextFieldDocument(true));
        txtCodCliente.setDocument(new JTextFieldDocument(true));
        txtCodFuncionario.setDocument(new JTextFieldDocument(true));
        txtCodProfissional.setDocument(new JTextFieldDocument(true));

        carregarObjetoIFrmManutencao(agenda);
    }

    public void carregarObjetoIFrmManutencao(Agendas agenda) {
        try {
            if (agenda.getIdAgenda()!= -1) {
                txtCodigo.setText(String.valueOf(agenda.getIdAgenda()));
            } else {
                txtCodigo.setText(String.valueOf(
                        agenda.getAgendaDAO().getProximoID()));
            }

            txtCodCliente.setText(String.valueOf(agenda.getCodCliente()));
            pesquisarCodigoDescicaoCliente(new Clientes());
            txtCodFuncionario.setText(String.valueOf(agenda.getCodFuncionario()));
            pesquisarCodigoDescicaoFuncionario(new Funcionarios());
            txtCodProfissional.setText(String.valueOf(agenda.getCodProfissional()));
            pesquisarCodigoDescicaoProfissional(new Profissionais());
            txtDataDeAgendamento.setText(Formatacao.ajustaDataDMA(
                    agenda.getDataAgendamento()));
            txtHorario.setText(String.valueOf(
                    Util.converterHorarioDoubleEmHHmmss(agenda.getHorario())));
            txtObservacoes.setText(agenda.getObservacoes());
            
            cbMotivoAgendamento.removeAllItems();
            MotivosAgendas motivos = new MotivosAgendas();
            motivos.getMotivoAgendaDAO().popularListaJComboBox(
                    cbMotivoAgendamento, motivos.getDadosCodigo().getCampo(),
                    motivos.getDadosDescricao().getCampo());
            cbMotivoAgendamento.setSelectedIndex((int)agenda.getCodMotivoAgenda());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                    "Registro nao pode ser carregado.\n\nMotivo: " + ex);
        }
    }

    private void reescreverObjeto() {
        try {
            agenda.setIdAgenda(Long.parseUnsignedLong(
                    txtCodigo.getText()));
            agenda.setCodCliente(Long.parseUnsignedLong(
                    txtCodCliente.getText()));
            agenda.setCodFuncionario(Long.parseUnsignedLong(
                    txtCodFuncionario.getText()));
            agenda.setCodProfissional(Long.parseUnsignedLong(
                    txtCodProfissional.getText()));
            agenda.setCodMotivoAgenda((long) cbMotivoAgendamento.getSelectedIndex());
            agenda.setDataAgendamento(Formatacao.ajustaData(
                    txtDataDeAgendamento.getText(), Formatacao.DATA_DMA));
            agenda.setHorario(Util.converterHorarioHHmmssEmDouble(
                    txtHorario.getText()));
            agenda.setObservacoes(txtObservacoes.getText());
            
            agenda.adicionarCampos();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                    "Registro nao pode ser reescrito.\n\nMotivo: " + ex);
        }
    }
    
    private void reescreverObjetoProfissionais() {
        try {
            profissional.setIdProfissional(Long.parseUnsignedLong(
                    tbResultado.getValueAt(
                            tbResultado.getSelectedRow(), 0).toString()));
            profissional.setCodPessoa(Long.parseUnsignedLong(
                    tbResultado.getValueAt(
                            tbResultado.getSelectedRow(), 1).toString()));
            // Reserva-se a próxima posição para a descrição do campo acima.
            profissional.setAtivo(false);

            profissional.adicionarCampos();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                    "Registro nao pode ser instanciado.\n\nMotivo: " + ex);
        }
    }

    public void pesquisarCodigoDescicaoCliente(Clientes cliente) {
        Util.pesquisarCodigoDescicao(cliente, txtCodCliente, txtNomeCliente);
    }

    public void pesquisarCodigoDescicaoFuncionario(Funcionarios funcionario) {
        Util.pesquisarCodigoDescicao(funcionario, txtCodFuncionario, 
                txtNomeFuncionario);
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
        jLabel1 = new javax.swing.JLabel();
        txtCodCliente = new javax.swing.JTextField();
        lblLocalizarCliente = new javax.swing.JLabel();
        txtNomeCliente = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtCodFuncionario = new javax.swing.JTextField();
        lblLocalizarFuncionario = new javax.swing.JLabel();
        txtNomeFuncionario = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtDataDeAgendamento = new javax.swing.JFormattedTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        cbMotivoAgendamento = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        txtCodProfissional = new javax.swing.JTextField();
        lblLocalizarProfissional = new javax.swing.JLabel();
        txtNomeProfissional = new javax.swing.JTextField();
        txtCodigo = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtObservacoes = new javax.swing.JTextArea();
        jLabel8 = new javax.swing.JLabel();
        txtHorario = new javax.swing.JFormattedTextField();
        jPanel1 = new javax.swing.JPanel();
        pnlResultado = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbResultado = new javax.swing.JTable();
        cbDiasSemana = new javax.swing.JComboBox();
        pnlBotoes = new javax.swing.JPanel();
        btnConfirmar = new javax.swing.JButton();
        btnAjudar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        mnuBarPrincipal = new javax.swing.JMenuBar();
        mnuArquivos = new javax.swing.JMenu();
        mnuAjuda = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cadastro de Agendas");
        setMaximumSize(new java.awt.Dimension(729, 398));
        setMinimumSize(new java.awt.Dimension(729, 398));
        setName(""); // NOI18N
        setPreferredSize(new java.awt.Dimension(729, 398));
        setVerifyInputWhenFocusTarget(false);
        getContentPane().setLayout(null);

        tablManutencao.setMinimumSize(new java.awt.Dimension(713, 245));
        tablManutencao.setName(""); // NOI18N
        tablManutencao.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                tablManutencaoStateChanged(evt);
            }
        });

        pnlGeral.setLayout(null);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setText("Cód. Agenda");
        pnlGeral.add(jLabel2);
        jLabel2.setBounds(75, 14, 70, 14);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setText("Cód. Cliente");
        pnlGeral.add(jLabel1);
        jLabel1.setBounds(78, 40, 66, 14);

        txtCodCliente.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCodCliente.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodClienteFocusLost(evt);
            }
        });
        pnlGeral.add(txtCodCliente);
        txtCodCliente.setBounds(149, 37, 70, 20);

        lblLocalizarCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/localizar.png"))); // NOI18N
        lblLocalizarCliente.setToolTipText("Localizar uma pessoa já cadastrada.");
        lblLocalizarCliente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblLocalizarCliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                lblLocalizarClienteMouseReleased(evt);
            }
        });
        pnlGeral.add(lblLocalizarCliente);
        lblLocalizarCliente.setBounds(223, 37, 20, 20);

        txtNomeCliente.setEditable(false);
        txtNomeCliente.setBackground(java.awt.SystemColor.info);
        txtNomeCliente.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        pnlGeral.add(txtNomeCliente);
        txtNomeCliente.setBounds(247, 37, 402, 20);

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel4.setText("Cód. Funcionário");
        pnlGeral.add(jLabel4);
        jLabel4.setBounds(53, 66, 92, 14);

        txtCodFuncionario.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCodFuncionario.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodFuncionarioFocusLost(evt);
            }
        });
        pnlGeral.add(txtCodFuncionario);
        txtCodFuncionario.setBounds(149, 63, 70, 20);

        lblLocalizarFuncionario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/localizar.png"))); // NOI18N
        lblLocalizarFuncionario.setToolTipText("Localizar uma função já cadastrada.");
        lblLocalizarFuncionario.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblLocalizarFuncionario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                lblLocalizarFuncionarioMouseReleased(evt);
            }
        });
        pnlGeral.add(lblLocalizarFuncionario);
        lblLocalizarFuncionario.setBounds(223, 63, 20, 20);

        txtNomeFuncionario.setEditable(false);
        txtNomeFuncionario.setBackground(java.awt.SystemColor.info);
        txtNomeFuncionario.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        pnlGeral.add(txtNomeFuncionario);
        txtNomeFuncionario.setBounds(247, 63, 402, 20);

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel9.setText("Data de Agendamento");
        pnlGeral.add(jLabel9);
        jLabel9.setBounds(15, 118, 127, 14);

        try {
            txtDataDeAgendamento.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtDataDeAgendamento.setVerifyInputWhenFocusTarget(false);
        txtDataDeAgendamento.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDataDeAgendamentoFocusGained(evt);
            }
        });
        pnlGeral.add(txtDataDeAgendamento);
        txtDataDeAgendamento.setBounds(149, 115, 70, 20);

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel6.setText("Horário Agendamento");
        pnlGeral.add(jLabel6);
        jLabel6.setBounds(320, 118, 125, 14);

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel7.setText("Motivo Agendamento");
        pnlGeral.add(jLabel7);
        jLabel7.setBounds(23, 144, 122, 14);

        cbMotivoAgendamento.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        pnlGeral.add(cbMotivoAgendamento);
        cbMotivoAgendamento.setBounds(149, 141, 169, 20);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setText("Cód. Profissional");
        pnlGeral.add(jLabel3);
        jLabel3.setBounds(52, 92, 93, 14);

        txtCodProfissional.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCodProfissional.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodProfissionalFocusLost(evt);
            }
        });
        pnlGeral.add(txtCodProfissional);
        txtCodProfissional.setBounds(149, 89, 70, 20);

        lblLocalizarProfissional.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/localizar.png"))); // NOI18N
        lblLocalizarProfissional.setToolTipText("Localizar uma pessoa já cadastrada.");
        lblLocalizarProfissional.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblLocalizarProfissional.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                lblLocalizarProfissionalMouseReleased(evt);
            }
        });
        pnlGeral.add(lblLocalizarProfissional);
        lblLocalizarProfissional.setBounds(223, 89, 20, 20);

        txtNomeProfissional.setEditable(false);
        txtNomeProfissional.setBackground(java.awt.SystemColor.info);
        txtNomeProfissional.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        pnlGeral.add(txtNomeProfissional);
        txtNomeProfissional.setBounds(247, 89, 402, 20);

        txtCodigo.setEditable(false);
        txtCodigo.setBackground(java.awt.SystemColor.info);
        txtCodigo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        pnlGeral.add(txtCodigo);
        txtCodigo.setBounds(149, 11, 70, 20);

        jScrollPane1.setAutoscrolls(true);
        jScrollPane1.setMaximumSize(new java.awt.Dimension(32767, 500));

        txtObservacoes.setColumns(60);
        txtObservacoes.setRows(5);
        txtObservacoes.setAutoscrolls(false);
        txtObservacoes.setMaximumSize(new java.awt.Dimension(164, 94));
        txtObservacoes.setMinimumSize(new java.awt.Dimension(164, 94));
        jScrollPane1.setViewportView(txtObservacoes);

        pnlGeral.add(jScrollPane1);
        jScrollPane1.setBounds(149, 168, 500, 100);

        jLabel8.setText("Observações");
        pnlGeral.add(jLabel8);
        jLabel8.setBounds(80, 170, 63, 10);

        try {
            txtHorario.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtHorario.setVerifyInputWhenFocusTarget(false);
        txtHorario.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtHorarioFocusGained(evt);
            }
        });
        pnlGeral.add(txtHorario);
        txtHorario.setBounds(450, 115, 70, 20);

        tablManutencao.addTab("Geral", pnlGeral);

        jPanel1.setLayout(null);

        pnlResultado.setBorder(javax.swing.BorderFactory.createTitledBorder("Horários"));

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
        jScrollPane2.setViewportView(tbResultado);

        javax.swing.GroupLayout pnlResultadoLayout = new javax.swing.GroupLayout(pnlResultado);
        pnlResultado.setLayout(pnlResultadoLayout);
        pnlResultadoLayout.setHorizontalGroup(
            pnlResultadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 688, Short.MAX_VALUE)
        );
        pnlResultadoLayout.setVerticalGroup(
            pnlResultadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 217, Short.MAX_VALUE)
        );

        jPanel1.add(pnlResultado);
        pnlResultado.setBounds(0, 40, 700, 240);

        cbDiasSemana.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Selecione os dias da semana", "Domingos", "Segundas-feiras", "Terças-feiras", "Quartas-feiras", "Quintas-feiras", "Sextas-feiras", "Sabados" }));
        cbDiasSemana.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbDiasSemanaActionPerformed(evt);
            }
        });
        jPanel1.add(cbDiasSemana);
        cbDiasSemana.setBounds(10, 10, 169, 20);

        tablManutencao.addTab("Horários / Profissionais", jPanel1);

        getContentPane().add(tablManutencao);
        tablManutencao.setBounds(0, 0, 710, 307);
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
        pnlBotoes.setBounds(0, 310, 713, 38);

        mnuArquivos.setText("Arquivo");
        mnuBarPrincipal.add(mnuArquivos);

        mnuAjuda.setText("Ajuda");
        mnuBarPrincipal.add(mnuAjuda);

        setJMenuBar(mnuBarPrincipal);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirmarActionPerformed
        reescreverObjeto();

        Util.salvar(agenda, this, true);
    }//GEN-LAST:event_btnConfirmarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        Util.cancelar(this);
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnAjudarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAjudarActionPerformed
        Util.ajudar();
    }//GEN-LAST:event_btnAjudarActionPerformed

    private void lblLocalizarClienteMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblLocalizarClienteMouseReleased
        boolean[] acoes = Util.getAcoesUsuario(Principal.CLIENTES);
            
        if (!acoes[Util.CONSULTAR]) {
            JOptionPane.showMessageDialog(this.getParent(),
                    "Você não pode efetuar esta consultadas.\n\nMotivo: "
                            + "Usuário sem permissão.");
        } else {        
            try {
                IFrmPesqClientes iFrmPesqClientes = new IFrmPesqClientes(
                        new IFrmManCliente(new Clientes()), txtCodCliente, 
                        txtNomeCliente);

                this.getParent().remove(iFrmPesqClientes);
                this.getParent().add(iFrmPesqClientes);

                Util.centralizar(this.getParent(), iFrmPesqClientes);

                iFrmPesqClientes.setVisible(true);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this.getParent(),
                        "Cliente não pode ser consultado.\n\nMotivo: " + e);
            }
        }
    }//GEN-LAST:event_lblLocalizarClienteMouseReleased

    private void lblLocalizarFuncionarioMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblLocalizarFuncionarioMouseReleased
        boolean[] acoes = Util.getAcoesUsuario(Principal.FUNCIONARIOS);
            
        if (!acoes[Util.CONSULTAR]) {
            JOptionPane.showMessageDialog(this.getParent(),
                    "Você não pode efetuar esta consultadas.\n\nMotivo: "
                            + "Usuário sem permissão.");
        } else {        
            try {
                IFrmPesqFuncionarios iFrmPesqFuncionarios
                        = new IFrmPesqFuncionarios(new IFrmManFuncionario(
                                new Funcionarios()),
                                txtCodFuncionario, txtNomeFuncionario);

                this.getParent().remove(iFrmPesqFuncionarios);
                this.getParent().add(iFrmPesqFuncionarios);

                Util.centralizar(this.getParent(), iFrmPesqFuncionarios);

                iFrmPesqFuncionarios.setVisible(true);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this.getParent(),
                        "Funcionário não pode ser consultado.\n\nMotivo: " + e);
            }
        }
    }//GEN-LAST:event_lblLocalizarFuncionarioMouseReleased

    private void txtCodClienteFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodClienteFocusLost
        pesquisarCodigoDescicaoCliente(new Clientes());
    }//GEN-LAST:event_txtCodClienteFocusLost

    private void txtCodFuncionarioFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodFuncionarioFocusLost
        pesquisarCodigoDescicaoFuncionario(new Funcionarios());
    }//GEN-LAST:event_txtCodFuncionarioFocusLost

    private void txtDataDeAgendamentoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDataDeAgendamentoFocusGained
        txtDataDeAgendamento.selectAll();
    }//GEN-LAST:event_txtDataDeAgendamentoFocusGained

    private void txtCodProfissionalFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodProfissionalFocusLost
        pesquisarCodigoDescicaoProfissional(new Profissionais());
    }//GEN-LAST:event_txtCodProfissionalFocusLost

    private void lblLocalizarProfissionalMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblLocalizarProfissionalMouseReleased
        boolean[] acoes = Util.getAcoesUsuario(Principal.PROFISSIONAIS);
            
        if (!acoes[Util.CONSULTAR]) {
            JOptionPane.showMessageDialog(this.getParent(),
                    "Você não pode efetuar esta consultadas.\n\nMotivo: "
                            + "Usuário sem permissão.");
        } else {        
            try {
                IFrmPesqProfissionais iFrmPesqProfissionais
                        = new IFrmPesqProfissionais(new IFrmManProfissional(
                                new Profissionais()),
                                txtCodProfissional, txtNomeProfissional);

                this.getParent().remove(iFrmPesqProfissionais);
                this.getParent().add(iFrmPesqProfissionais);

                Util.centralizar(this.getParent(), iFrmPesqProfissionais);

                iFrmPesqProfissionais.setVisible(true);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this.getParent(),
                        "Prolfissional não pode ser consultado.\n\nMotivo: " + e);
            }
        }
    }//GEN-LAST:event_lblLocalizarProfissionalMouseReleased

    private void txtHorarioFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtHorarioFocusGained
        txtHorario.selectAll();
    }//GEN-LAST:event_txtHorarioFocusGained

    private void cbDiasSemanaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbDiasSemanaActionPerformed
        profissionalTurnos = new ProfissionaisTurnos();
        profissionalTurnos.setCodProfissional(Long.parseUnsignedLong(
                txtCodProfissional.getText()));
        profissionalTurnos.adicionarWhere(new DadosDAO("cod_profissional", "", 
                txtCodProfissional.getText(), DadosDAO.TIPO_LONG, 
                DadosDAO.IS_IGUAL, DadosDAO.NAO_CHAVE));
        profissionalTurnos.adicionarWhere(new DadosDAO("dia_semana", "", 
                String.valueOf(cbDiasSemana.getSelectedIndex()), DadosDAO.TIPO_LONG, 
                DadosDAO.IS_IGUAL, DadosDAO.NAO_CHAVE));
        profissionalTurnos.getProfissionalTurnosDAO().popularGridJTable(
                tbResultado);

        Util.atualizar(profissionalTurnos, tbResultado);
    }//GEN-LAST:event_cbDiasSemanaActionPerformed

    private void tablManutencaoStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_tablManutencaoStateChanged
        if (tablManutencao.getSelectedIndex() > 0) {
            try {
                reescreverObjeto();
                Util.salvar(agenda, this, false);
                
                // Profissionais turnos
                profissionalTurnos = new ProfissionaisTurnos();
                profissionalTurnos.setCodProfissional(Long.parseUnsignedLong(
                        txtCodProfissional.getText()));
                profissionalTurnos.adicionarWhere(new DadosDAO("cod_Profissional",
                        "", txtCodProfissional.getText(), DadosDAO.TIPO_LONG, 
                        DadosDAO.IS_IGUAL, DadosDAO.NAO_CHAVE));
                profissionalTurnos.getProfissionalTurnosDAO().popularGridJTable(
                        tbResultado);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this.getParent(),
                        "Registro nao pode ser carregado.\n\nMotivo: " + ex);
            }
        }
    }//GEN-LAST:event_tablManutencaoStateChanged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAjudar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnConfirmar;
    private javax.swing.JComboBox cbDiasSemana;
    private javax.swing.JComboBox cbMotivoAgendamento;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblLocalizarCliente;
    private javax.swing.JLabel lblLocalizarFuncionario;
    private javax.swing.JLabel lblLocalizarProfissional;
    private javax.swing.JMenu mnuAjuda;
    private javax.swing.JMenu mnuArquivos;
    private javax.swing.JMenuBar mnuBarPrincipal;
    private javax.swing.JPanel pnlBotoes;
    private javax.swing.JPanel pnlGeral;
    private javax.swing.JPanel pnlResultado;
    private javax.swing.JTabbedPane tablManutencao;
    private javax.swing.JTable tbResultado;
    private javax.swing.JTextField txtCodCliente;
    private javax.swing.JTextField txtCodFuncionario;
    private javax.swing.JTextField txtCodProfissional;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JFormattedTextField txtDataDeAgendamento;
    private javax.swing.JFormattedTextField txtHorario;
    private javax.swing.JTextField txtNomeCliente;
    private javax.swing.JTextField txtNomeFuncionario;
    private javax.swing.JTextField txtNomeProfissional;
    private javax.swing.JTextArea txtObservacoes;
    // End of variables declaration//GEN-END:variables

    /**
     * @return the agenda
     */
    public Agendas getAgenda() {
        return agenda;
    }

    /**
     * @param agenda the agenda to set
     */
    public void setAgenda(Agendas agenda) {
        this.agenda = agenda;
    }
}
