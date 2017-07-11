/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.samho.apresentacao;

import com.samho.dao.DadosDAO;
import com.samho.negocio.Agendas;
import com.samho.negocio.Clientes;
import com.samho.negocio.ClientesTiposPlanos;
import com.samho.negocio.Pessoas;
import com.samho.negocio.Principal;
import com.samho.negocio.Prontuarios;
import com.samho.negocio.SituacoesClientes;
import com.samho.util.Formatacao;
import com.samho.util.JTextFieldDocument;
import com.samho.util.Util;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;

/**
 * Classe para visualização de objetos do tipo FrmManutencao.
 *
 * @author Saibel, Sergio Luis
 * @since  date 22/04/2017
 *
 * @version  revision 001.20140422 date 22/04/2017 author Saibel, Sérgio Luis reason
 * Conseguir instanciar um objeto do tipo FrmMaunutenção com os atributos e
 * métodos necessários. Esta classe servirá como base para todos os FRMs de
 * manutenção.
 */
public class IFrmManCliente extends JInternalFrame {

    // Declaração de atributos
    private Clientes cliente;
    private Prontuarios prontuario;
    private ClientesTiposPlanos clienteTipoPlanos;
    private Agendas agenda;

    // Construtor
    public IFrmManCliente(Clientes cliente) throws Exception {
        initComponents();

        Util.limparCampos(rootPane);

        this.cliente = cliente;

        // Permite que somente números sejam informados neste campo
        txtCodigo.setDocument(new JTextFieldDocument(true));
        txtCodPessoa.setDocument(new JTextFieldDocument(true));

        // Quebra de linha automática
        txtObservacoes.setLineWrap(true);
        txtObservacoes.setWrapStyleWord(true);
        
        if (Util.getAcoesUsuario(Principal.AGENDAS)[Util.CONSULTAR] == false) {
            tablManutencao.remove(pnlDetalhe);
        }
        
        if (Util.getAcoesUsuario(Principal.PLANOS_SAUDE)[Util.CONSULTAR] == false) {
            tablManutencao.remove(pnlDetalhe1);
        }
        
        if (Util.getAcoesUsuario(Principal.PRONTUARIOS)[Util.CONSULTAR] == false) {
            tablManutencao.remove(pnlDetalhe2);
        }
        
        tablManutencao.setSelectedIndex(0);

        carregarObjetoIFrmManutencao(cliente);
    }

    public void carregarObjetoIFrmManutencao(Clientes cliente) {
        try {
            if (cliente.getIdCliente() != -1) {
                txtCodigo.setText(String.valueOf(cliente.getIdCliente()));
            } else {
                txtCodigo.setText(String.valueOf(
                        cliente.getClienteDAO().getProximoID()));
            }

            txtCodPessoa.setText(String.valueOf(cliente.getCodPessoa()));
            pesquisarCodigoDescicaoPessoa(new Pessoas());
            txtDataCadastro.setText(Formatacao.ajustaDataDMA(
                    cliente.getDataDeCadastro()));
            txtObservacoes.setText(cliente.getObservacoes());

            cbSituacao.removeAllItems();
            SituacoesClientes situacoes = new SituacoesClientes();
            situacoes.getSituacaoDAO().popularListaJComboBox(cbSituacao,
                    situacoes.getDadosCodigo().getCampo(),
                    situacoes.getDadosDescricao().getCampo());
            cbSituacao.setSelectedIndex((int) cliente.getCodSituacao());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                    "Registro nao pode ser carregado.\n\nMotivo: " + ex);
        }
    }

    private void reescreverObjeto() {
        try {
            cliente.setIdCliente(Long.parseUnsignedLong(txtCodigo.getText()));
            cliente.setCodPessoa(Long.parseUnsignedLong(txtCodPessoa.getText()));
            cliente.setDataDeCadastro(Formatacao.ajustaData(
                    txtDataCadastro.getText(), Formatacao.DATA_DMA));
            cliente.setObservacoes(txtObservacoes.getText());
            cliente.setCodSituacao(cbSituacao.getSelectedIndex());

            cliente.adicionarCampos();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                    "Registro nao pode ser reescrito.\n\nMotivo: " + ex);
        }
    }
    
    private void reescreverObjetoAgenda() {
        try {
            agenda.setIdAgenda(Long.parseUnsignedLong(tbAgendas.getValueAt(
                    tbAgendas.getSelectedRow(), 0).toString()));
            agenda.setCodCliente(Long.parseUnsignedLong(tbAgendas.getValueAt(
                    tbAgendas.getSelectedRow(), 1).toString()));
            // Reserva-se a próxima posição para a descrição do campo acima.
            agenda.setCodFuncionario(Long.parseUnsignedLong(tbAgendas.getValueAt(
                    tbAgendas.getSelectedRow(), 3).toString()));
            // Reserva-se a próxima posição para a descrição do campo acima.
            agenda.setCodProfissional(Long.parseUnsignedLong(tbAgendas.getValueAt(
                    tbAgendas.getSelectedRow(), 5).toString()));
            // Reserva-se a próxima posição para a descrição do campo acima.
            agenda.setCodMotivoAgenda(Long.parseUnsignedLong(tbAgendas.getValueAt(
                    tbAgendas.getSelectedRow(), 7).toString()));
            // Desabilita os itens alterados por padrão
            agenda.setDataAgendamento(Formatacao.ajustaData(tbAgendas.getValueAt(
                    tbAgendas.getSelectedRow(), 9).toString(), Formatacao.DATA_AMD));
            agenda.setHorario(Double.parseDouble(tbAgendas.getValueAt(
                    tbAgendas.getSelectedRow(), 10).toString()));
            agenda.setObservacoes(tbAgendas.getValueAt(
                    tbAgendas.getSelectedRow(), 11).toString());
            
            agenda.adicionarCampos();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                    "Registro nao pode ser instanciado.\n\nMotivo: " + ex);
        }
    }

    private void reescreverObjetoClienteTiposPlanos() {
        try {
            clienteTipoPlanos.setIdClienteTipoPlano(
                    Long.parseUnsignedLong(tbPlanosSaude.getValueAt(
                            tbPlanosSaude.getSelectedRow(), 0).toString()));
            clienteTipoPlanos.setCodCliente(Long.parseUnsignedLong(
                    tbPlanosSaude.getValueAt(
                            tbPlanosSaude.getSelectedRow(), 1).toString()));
            // Reserva-se a próxima posição para a descrição do campo acima.
            clienteTipoPlanos.setCodTipoPlano(Long.parseUnsignedLong(
                    tbPlanosSaude.getValueAt(
                            tbPlanosSaude.getSelectedRow(), 3).toString()));
            // Reserva-se a próxima posição para a descrição do campo acima.
            clienteTipoPlanos.setDataAdesao(
                    Formatacao.ajustaData(tbPlanosSaude.getValueAt(
                            tbPlanosSaude.getSelectedRow(), 5).toString(),
                            Formatacao.DATA_AMD));
            clienteTipoPlanos.setAtivo(false);

            clienteTipoPlanos.adicionarCampos();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                    "Registro nao pode ser instanciado.\n\nMotivo: " + ex);
        }
    }
    
    private void reescreverObjetoProntuario() {
        try {
            prontuario.setIdProntuario(Long.parseUnsignedLong(
                    tbProntuarios.getValueAt(
                            tbProntuarios.getSelectedRow(), 0).toString()));
            prontuario.setCodCliente(Long.parseUnsignedLong(
                    tbProntuarios.getValueAt(
                            tbProntuarios.getSelectedRow(), 1).toString()));
            // Reserva-se a próxima posição para a descrição do campo acima.
            prontuario.setMotivoConsulta(tbProntuarios.getValueAt(
                    tbProntuarios.getSelectedRow(), 3).toString());
            prontuario.setDiagnostico(tbProntuarios.getValueAt(
                    tbProntuarios.getSelectedRow(), 4).toString());
            prontuario.setDataConsulta(
                    Formatacao.ajustaData(tbProntuarios.getValueAt(
                            tbProntuarios.getSelectedRow(), 5).toString(),
                            Formatacao.DATA_AMD));
            prontuario.setParecerProfissional(tbProntuarios.getValueAt(
                    tbProntuarios.getSelectedRow(), 6).toString());
            prontuario.setObservacoes(tbProntuarios.getValueAt(
                    tbProntuarios.getSelectedRow(), 7).toString());
            // Busca o campo que foi selecionado pelo usuário.
            prontuario.setDeveRetornar(Boolean.parseBoolean(
                    prontuario.getObjetoDAO().getCampo("deve_retornar", 
                            "prontuarios", prontuario.getIdProntuario()
                                    + " = id_prontuario", false)));

            prontuario.adicionarCampos();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                    "Registro nao pode ser instanciado.\n\nMotivo: " + ex);
        }
    }

    public void pesquisarCodigoDescicaoPessoa(Pessoas pessoa) {
        Util.pesquisarCodigoDescicao(pessoa, txtCodPessoa, txtNomePessoa);
    }

    private void salvarCliente(boolean fecharFrame) {
        reescreverObjeto();

        Util.salvar(cliente, this, fecharFrame);
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
        lblLocalizar = new javax.swing.JLabel();
        txtNomePessoa = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtDataCadastro = new javax.swing.JFormattedTextField();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtObservacoes = new javax.swing.JTextArea();
        jLabel7 = new javax.swing.JLabel();
        cbSituacao = new javax.swing.JComboBox();
        pnlDetalhe = new javax.swing.JPanel();
        pnlProdutos = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbAgendas = new javax.swing.JTable();
        btnIncluirAgenda = new javax.swing.JButton();
        btnExcluirAgenda = new javax.swing.JButton();
        btnAtualizarAgendas = new javax.swing.JButton();
        pnlDetalhe1 = new javax.swing.JPanel();
        pnlProdutos1 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbPlanosSaude = new javax.swing.JTable();
        btnIncluirPlanoSaude = new javax.swing.JButton();
        btnExcluirPlanoSaude = new javax.swing.JButton();
        btnAtualizarPlanosSaude = new javax.swing.JButton();
        pnlDetalhe2 = new javax.swing.JPanel();
        pnlProdutos2 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tbProntuarios = new javax.swing.JTable();
        btnIncluirProntuario = new javax.swing.JButton();
        btnExcluirProntuario = new javax.swing.JButton();
        btnAtualizarProntuarios = new javax.swing.JButton();
        pnlBotoes = new javax.swing.JPanel();
        btnConfirmar = new javax.swing.JButton();
        btnAjudar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        mnuBarPrincipal = new javax.swing.JMenuBar();
        mnuArquivos = new javax.swing.JMenu();
        mnuAjuda = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cadastro de Cliente");
        setMaximumSize(new java.awt.Dimension(730, 481));
        setMinimumSize(new java.awt.Dimension(730, 481));
        setName(""); // NOI18N
        setPreferredSize(new java.awt.Dimension(730, 481));
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
        jLabel2.setText("Cód. Cliente");
        pnlGeral.add(jLabel2);
        jLabel2.setBounds(65, 13, 66, 14);

        txtCodigo.setEditable(false);
        txtCodigo.setBackground(java.awt.SystemColor.info);
        txtCodigo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        pnlGeral.add(txtCodigo);
        txtCodigo.setBounds(137, 11, 70, 20);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setText("Cód. Pessoa");
        pnlGeral.add(jLabel1);
        jLabel1.setBounds(65, 38, 67, 14);

        txtCodPessoa.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCodPessoa.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodPessoaFocusLost(evt);
            }
        });
        pnlGeral.add(txtCodPessoa);
        txtCodPessoa.setBounds(137, 36, 70, 20);

        lblLocalizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/localizar.png"))); // NOI18N
        lblLocalizar.setToolTipText("Localizar uma pessoa já cadastrada.");
        lblLocalizar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblLocalizar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                lblLocalizarMouseReleased(evt);
            }
        });
        pnlGeral.add(lblLocalizar);
        lblLocalizar.setBounds(211, 36, 20, 20);

        txtNomePessoa.setEditable(false);
        txtNomePessoa.setBackground(java.awt.SystemColor.info);
        txtNomePessoa.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        pnlGeral.add(txtNomePessoa);
        txtNomePessoa.setBounds(235, 36, 402, 20);

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel6.setText("Data de cadastro");
        pnlGeral.add(jLabel6);
        jLabel6.setBounds(35, 65, 97, 14);

        try {
            txtDataCadastro.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtDataCadastro.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDataCadastroFocusGained(evt);
            }
        });
        pnlGeral.add(txtDataCadastro);
        txtDataCadastro.setBounds(137, 62, 70, 20);

        jLabel8.setText("Observações");
        pnlGeral.add(jLabel8);
        jLabel8.setBounds(68, 92, 63, 14);

        txtObservacoes.setColumns(20);
        txtObservacoes.setRows(5);
        jScrollPane1.setViewportView(txtObservacoes);

        pnlGeral.add(jScrollPane1);
        jScrollPane1.setBounds(137, 88, 500, 237);

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel7.setText("Situação");
        pnlGeral.add(jLabel7);
        jLabel7.setBounds(83, 334, 49, 14);

        cbSituacao.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        pnlGeral.add(cbSituacao);
        cbSituacao.setBounds(137, 331, 169, 20);

        tablManutencao.addTab("Geral", pnlGeral);

        pnlProdutos.setBorder(javax.swing.BorderFactory.createTitledBorder("Agendas do Cliente"));
        pnlProdutos.setLayout(null);

        tbAgendas.setModel(new javax.swing.table.DefaultTableModel(
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
        tbAgendas.setName(""); // NOI18N
        tbAgendas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tbAgendasMouseReleased(evt);
            }
        });
        jScrollPane2.setViewportView(tbAgendas);

        pnlProdutos.add(jScrollPane2);
        jScrollPane2.setBounds(6, 16, 570, 313);

        btnIncluirAgenda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/incluir.png"))); // NOI18N
        btnIncluirAgenda.setText("Incluir");
        btnIncluirAgenda.setToolTipText("Incluir um produto para o cliente.");
        btnIncluirAgenda.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnIncluirAgenda.setMargin(new java.awt.Insets(2, 5, 2, 5));
        btnIncluirAgenda.setMaximumSize(new java.awt.Dimension(69, 29));
        btnIncluirAgenda.setMinimumSize(null);
        btnIncluirAgenda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIncluirAgendaActionPerformed(evt);
            }
        });
        pnlProdutos.add(btnIncluirAgenda);
        btnIncluirAgenda.setBounds(582, 16, 90, 29);

        btnExcluirAgenda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/excluir.png"))); // NOI18N
        btnExcluirAgenda.setText("Excluir");
        btnExcluirAgenda.setToolTipText("Excluir um produto do o cliente.");
        btnExcluirAgenda.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnExcluirAgenda.setMargin(new java.awt.Insets(2, 5, 2, 5));
        btnExcluirAgenda.setMinimumSize(null);
        btnExcluirAgenda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirAgendaActionPerformed(evt);
            }
        });
        pnlProdutos.add(btnExcluirAgenda);
        btnExcluirAgenda.setBounds(582, 51, 90, 29);

        btnAtualizarAgendas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/atualizar.png"))); // NOI18N
        btnAtualizarAgendas.setText("Atualizar");
        btnAtualizarAgendas.setToolTipText("Atualizar lista de produtos do cliente.");
        btnAtualizarAgendas.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAtualizarAgendas.setMargin(new java.awt.Insets(2, 5, 2, 5));
        btnAtualizarAgendas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtualizarAgendasActionPerformed(evt);
            }
        });
        pnlProdutos.add(btnAtualizarAgendas);
        btnAtualizarAgendas.setBounds(582, 86, 90, 29);

        javax.swing.GroupLayout pnlDetalheLayout = new javax.swing.GroupLayout(pnlDetalhe);
        pnlDetalhe.setLayout(pnlDetalheLayout);
        pnlDetalheLayout.setHorizontalGroup(
            pnlDetalheLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlDetalheLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlProdutos, javax.swing.GroupLayout.DEFAULT_SIZE, 685, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlDetalheLayout.setVerticalGroup(
            pnlDetalheLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDetalheLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlProdutos, javax.swing.GroupLayout.DEFAULT_SIZE, 337, Short.MAX_VALUE)
                .addContainerGap())
        );

        tablManutencao.addTab("Agendas", pnlDetalhe);

        pnlProdutos1.setBorder(javax.swing.BorderFactory.createTitledBorder("Planos de Saúde do CLiente"));
        pnlProdutos1.setLayout(null);

        tbPlanosSaude.setModel(new javax.swing.table.DefaultTableModel(
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
        tbPlanosSaude.setName(""); // NOI18N
        tbPlanosSaude.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tbPlanosSaudeMouseReleased(evt);
            }
        });
        jScrollPane3.setViewportView(tbPlanosSaude);

        pnlProdutos1.add(jScrollPane3);
        jScrollPane3.setBounds(6, 16, 570, 313);

        btnIncluirPlanoSaude.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/incluir.png"))); // NOI18N
        btnIncluirPlanoSaude.setText("Incluir");
        btnIncluirPlanoSaude.setToolTipText("Incluir um produto para o cliente.");
        btnIncluirPlanoSaude.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnIncluirPlanoSaude.setMargin(new java.awt.Insets(2, 5, 2, 5));
        btnIncluirPlanoSaude.setMaximumSize(new java.awt.Dimension(69, 29));
        btnIncluirPlanoSaude.setMinimumSize(null);
        btnIncluirPlanoSaude.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIncluirPlanoSaudeActionPerformed(evt);
            }
        });
        pnlProdutos1.add(btnIncluirPlanoSaude);
        btnIncluirPlanoSaude.setBounds(582, 16, 90, 29);

        btnExcluirPlanoSaude.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/excluir.png"))); // NOI18N
        btnExcluirPlanoSaude.setText("Excluir");
        btnExcluirPlanoSaude.setToolTipText("Excluir um produto do o cliente.");
        btnExcluirPlanoSaude.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnExcluirPlanoSaude.setMargin(new java.awt.Insets(2, 5, 2, 5));
        btnExcluirPlanoSaude.setMinimumSize(null);
        btnExcluirPlanoSaude.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirPlanoSaudeActionPerformed(evt);
            }
        });
        pnlProdutos1.add(btnExcluirPlanoSaude);
        btnExcluirPlanoSaude.setBounds(582, 51, 90, 29);

        btnAtualizarPlanosSaude.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/atualizar.png"))); // NOI18N
        btnAtualizarPlanosSaude.setText("Atualizar");
        btnAtualizarPlanosSaude.setToolTipText("Atualizar lista de produtos do cliente.");
        btnAtualizarPlanosSaude.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAtualizarPlanosSaude.setMargin(new java.awt.Insets(2, 5, 2, 5));
        btnAtualizarPlanosSaude.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtualizarPlanosSaudeActionPerformed(evt);
            }
        });
        pnlProdutos1.add(btnAtualizarPlanosSaude);
        btnAtualizarPlanosSaude.setBounds(582, 86, 90, 29);

        javax.swing.GroupLayout pnlDetalhe1Layout = new javax.swing.GroupLayout(pnlDetalhe1);
        pnlDetalhe1.setLayout(pnlDetalhe1Layout);
        pnlDetalhe1Layout.setHorizontalGroup(
            pnlDetalhe1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlDetalhe1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlProdutos1, javax.swing.GroupLayout.DEFAULT_SIZE, 685, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlDetalhe1Layout.setVerticalGroup(
            pnlDetalhe1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDetalhe1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlProdutos1, javax.swing.GroupLayout.DEFAULT_SIZE, 337, Short.MAX_VALUE)
                .addContainerGap())
        );

        tablManutencao.addTab("Planos de Saúde", pnlDetalhe1);

        pnlProdutos2.setBorder(javax.swing.BorderFactory.createTitledBorder("Prontuários do Cliente"));
        pnlProdutos2.setLayout(null);

        tbProntuarios.setModel(new javax.swing.table.DefaultTableModel(
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
        tbProntuarios.setName(""); // NOI18N
        tbProntuarios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tbProntuariosMouseReleased(evt);
            }
        });
        jScrollPane4.setViewportView(tbProntuarios);

        pnlProdutos2.add(jScrollPane4);
        jScrollPane4.setBounds(6, 16, 570, 313);

        btnIncluirProntuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/incluir.png"))); // NOI18N
        btnIncluirProntuario.setText("Incluir");
        btnIncluirProntuario.setToolTipText("Incluir um produto para o cliente.");
        btnIncluirProntuario.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnIncluirProntuario.setMargin(new java.awt.Insets(2, 5, 2, 5));
        btnIncluirProntuario.setMaximumSize(new java.awt.Dimension(69, 29));
        btnIncluirProntuario.setMinimumSize(null);
        btnIncluirProntuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIncluirProntuarioActionPerformed(evt);
            }
        });
        pnlProdutos2.add(btnIncluirProntuario);
        btnIncluirProntuario.setBounds(582, 16, 90, 29);

        btnExcluirProntuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/excluir.png"))); // NOI18N
        btnExcluirProntuario.setText("Excluir");
        btnExcluirProntuario.setToolTipText("Excluir um produto do o cliente.");
        btnExcluirProntuario.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnExcluirProntuario.setMargin(new java.awt.Insets(2, 5, 2, 5));
        btnExcluirProntuario.setMinimumSize(null);
        btnExcluirProntuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirProntuarioActionPerformed(evt);
            }
        });
        pnlProdutos2.add(btnExcluirProntuario);
        btnExcluirProntuario.setBounds(582, 51, 90, 29);

        btnAtualizarProntuarios.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/atualizar.png"))); // NOI18N
        btnAtualizarProntuarios.setText("Atualizar");
        btnAtualizarProntuarios.setToolTipText("Atualizar lista de produtos do cliente.");
        btnAtualizarProntuarios.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAtualizarProntuarios.setMargin(new java.awt.Insets(2, 5, 2, 5));
        btnAtualizarProntuarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtualizarProntuariosActionPerformed(evt);
            }
        });
        pnlProdutos2.add(btnAtualizarProntuarios);
        btnAtualizarProntuarios.setBounds(582, 86, 90, 29);

        javax.swing.GroupLayout pnlDetalhe2Layout = new javax.swing.GroupLayout(pnlDetalhe2);
        pnlDetalhe2.setLayout(pnlDetalhe2Layout);
        pnlDetalhe2Layout.setHorizontalGroup(
            pnlDetalhe2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlDetalhe2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlProdutos2, javax.swing.GroupLayout.DEFAULT_SIZE, 685, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlDetalhe2Layout.setVerticalGroup(
            pnlDetalhe2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDetalhe2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlProdutos2, javax.swing.GroupLayout.DEFAULT_SIZE, 337, Short.MAX_VALUE)
                .addContainerGap())
        );

        tablManutencao.addTab("Prontuários", pnlDetalhe2);

        getContentPane().add(tablManutencao);
        tablManutencao.setBounds(10, 0, 710, 387);
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
        pnlBotoes.setBounds(0, 392, 713, 38);

        mnuArquivos.setText("Arquivo");
        mnuBarPrincipal.add(mnuArquivos);

        mnuAjuda.setText("Ajuda");
        mnuBarPrincipal.add(mnuAjuda);

        setJMenuBar(mnuBarPrincipal);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirmarActionPerformed
        salvarCliente(true);
    }//GEN-LAST:event_btnConfirmarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        Util.cancelar(this);
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnAjudarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAjudarActionPerformed
        Util.ajudar();
    }//GEN-LAST:event_btnAjudarActionPerformed

    private void btnExcluirAgendaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirAgendaActionPerformed
        boolean[] acoes = Util.getAcoesUsuario(Principal.AGENDAS);

        if (!acoes[Util.EXCLUIR]) {
            JOptionPane.showMessageDialog(this.getParent(),
                "Você não pode efetuar esta exclusão.\n\nMotivo: "
                + "Usuário sem permissão.");
        } else {
            Util.excluir(agenda, tbAgendas, this);
        }
    }//GEN-LAST:event_btnExcluirAgendaActionPerformed

    private void btnIncluirAgendaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIncluirAgendaActionPerformed
        boolean[] acoes = Util.getAcoesUsuario(Principal.AGENDAS);

        if (!acoes[Util.INCLUIR]) {
            JOptionPane.showMessageDialog(this.getParent(),
                "Você não pode efetuar esta inclusão.\n\nMotivo: "
                + "Usuário sem permissão.");
        } else {
            try {
                Agendas agendas = new Agendas();
                agendas.setCodCliente(cliente.getIdCliente());
                IFrmManAgenda iFrmManAgenda = new IFrmManAgenda(agendas);

                this.getParent().remove(iFrmManAgenda);
                this.getParent().add(iFrmManAgenda);

                Util.centralizar(this.getParent(), iFrmManAgenda);

                iFrmManAgenda.setVisible(true);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this.getParent(),
                    "Agenda não pode ser incluída.\n\nMotivo: " + ex);
            }
        }
    }//GEN-LAST:event_btnIncluirAgendaActionPerformed

    private void lblLocalizarMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblLocalizarMouseReleased
        boolean[] acoes = Util.getAcoesUsuario(Principal.PESSOAS);
            
        if (!acoes[Util.CONSULTAR]) {
            JOptionPane.showMessageDialog(this.getParent(),
                    "Você não pode efetuar esta consultadas.\n\nMotivo: "
                            + "Usuário sem permissão.");
        } else {        
            try {
                IFrmPesqPessoas iFrmPesqPessoas = new IFrmPesqPessoas(
                        new IFrmManPessoa(new Pessoas()), txtCodPessoa, 
                        txtNomePessoa);

                this.getParent().remove(iFrmPesqPessoas);
                this.getParent().add(iFrmPesqPessoas);

                Util.centralizar(this.getParent(), iFrmPesqPessoas);

                iFrmPesqPessoas.setVisible(true);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this.getParent(),
                        "Pessoa não pode ser consultada.\n\nMotivo: " + e);
            }
        }
    }//GEN-LAST:event_lblLocalizarMouseReleased

    private void btnAtualizarAgendasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtualizarAgendasActionPerformed
        Util.atualizar(agenda, tbAgendas);
    }//GEN-LAST:event_btnAtualizarAgendasActionPerformed

    private void tbAgendasMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbAgendasMouseReleased
        if (evt.getClickCount() == 2) {
            boolean[] acoes = Util.getAcoesUsuario(Principal.AGENDAS);

            if (!acoes[Util.ALTERAR]) {
                JOptionPane.showMessageDialog(this.getParent(),
                    "Você não pode efetuar esta alteração.\n\nMotivo: "
                    + "Usuário sem permissão.");
            } else {
                try {
                    reescreverObjetoAgenda();

                    IFrmManAgenda iFrmManAgenda = new IFrmManAgenda(agenda);

                    this.getParent().remove(iFrmManAgenda);
                    this.getParent().add(iFrmManAgenda);

                    Util.centralizar(this.getParent(), iFrmManAgenda);

                    iFrmManAgenda.setVisible(true);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this.getParent(),
                        "Registro nao pode ser alterado.\n\nMotivo: " + ex);
                }
            }
        }
    }//GEN-LAST:event_tbAgendasMouseReleased

    private void txtCodPessoaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodPessoaFocusLost
        pesquisarCodigoDescicaoPessoa(new Pessoas());
    }//GEN-LAST:event_txtCodPessoaFocusLost

    private void txtDataCadastroFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDataCadastroFocusGained
        Formatacao.reformatarData(txtDataCadastro);
    }//GEN-LAST:event_txtDataCadastroFocusGained

    private void tablManutencaoStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_tablManutencaoStateChanged
        if (tablManutencao.getSelectedIndex() > 0) {
            try {
                reescreverObjeto();
                Util.salvar(cliente, this, false);
                
                // Agendas
                agenda = new Agendas();
                agenda.setCodCliente(Long.parseUnsignedLong(
                        txtCodigo.getText()));
                agenda.adicionarWhere(new DadosDAO("cod_cliente", "",
                        txtCodigo.getText(), DadosDAO.TIPO_LONG, 
                        DadosDAO.IS_IGUAL, DadosDAO.NAO_CHAVE));
                agenda.getAgendaDAO().popularGridJTable(tbAgendas);
                
                // Tipos de Planos
                clienteTipoPlanos = new ClientesTiposPlanos();
                clienteTipoPlanos.setCodCliente(Long.parseUnsignedLong(
                        txtCodigo.getText()));
                clienteTipoPlanos.adicionarWhere(new DadosDAO("cod_cliente", "",
                        txtCodigo.getText(), DadosDAO.TIPO_LONG, 
                        DadosDAO.IS_IGUAL, DadosDAO.NAO_CHAVE));
                clienteTipoPlanos.getClienteTipoPlanoDAO().popularGridJTable(
                        tbPlanosSaude);
                
                // Prontuários
                prontuario = new Prontuarios();
                prontuario.setCodCliente(Long.parseUnsignedLong(txtCodigo.getText()));
                prontuario.adicionarWhere(new DadosDAO("cod_cliente", "", 
                        txtCodigo.getText(), DadosDAO.TIPO_LONG, 
                        DadosDAO.IS_IGUAL, DadosDAO.NAO_CHAVE));
                prontuario.getProntuarioDAO().popularGridJTable(tbProntuarios);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this.getParent(),
                        "Registro nao pode ser carregado.\n\nMotivo: " + ex);
            }
        }
    }//GEN-LAST:event_tablManutencaoStateChanged

    private void tbPlanosSaudeMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbPlanosSaudeMouseReleased
        if (evt.getClickCount() == 2) {
            boolean[] acoes = Util.getAcoesUsuario(Principal.CLIENTES_TIPOS_PLANOS);

            if (!acoes[Util.ALTERAR]) {
                JOptionPane.showMessageDialog(this.getParent(),
                    "Você não pode efetuar esta alteração.\n\nMotivo: "
                    + "Usuário sem permissão.");
            } else {
                try {
                    reescreverObjetoClienteTiposPlanos();

                    IFrmManClienteTipoPlano iFrmManClienteTipoPlano =
                            new IFrmManClienteTipoPlano(clienteTipoPlanos);

                    this.getParent().remove(iFrmManClienteTipoPlano);
                    this.getParent().add(iFrmManClienteTipoPlano);

                    Util.centralizar(this.getParent(), iFrmManClienteTipoPlano);

                    iFrmManClienteTipoPlano.setVisible(true);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this.getParent(),
                        "Registro nao pode ser alterado.\n\nMotivo: " + ex);
                }
            }
        }
    }//GEN-LAST:event_tbPlanosSaudeMouseReleased

    private void btnIncluirPlanoSaudeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIncluirPlanoSaudeActionPerformed
        boolean[] acoes = Util.getAcoesUsuario(Principal.CLIENTES_TIPOS_PLANOS);

        if (!acoes[Util.INCLUIR]) {
            JOptionPane.showMessageDialog(this.getParent(),
                "Você não pode efetuar esta inclusão.\n\nMotivo: "
                + "Usuário sem permissão.");
        } else {
            try {
                ClientesTiposPlanos cliTipoPlano = new ClientesTiposPlanos();
                cliTipoPlano.setCodCliente(cliente.getIdCliente());
                IFrmManClienteTipoPlano iFrmManClienteTipoPlano = 
                        new IFrmManClienteTipoPlano(cliTipoPlano);

                this.getParent().remove(iFrmManClienteTipoPlano);
                this.getParent().add(iFrmManClienteTipoPlano);

                Util.centralizar(this.getParent(), iFrmManClienteTipoPlano);

                iFrmManClienteTipoPlano.setVisible(true);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this.getParent(),
                    "Tipo de Plano de Saúde não pode ser incluído.\n\nMotivo: " + ex);
            }
        }
    }//GEN-LAST:event_btnIncluirPlanoSaudeActionPerformed

    private void btnExcluirPlanoSaudeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirPlanoSaudeActionPerformed
        boolean[] acoes = Util.getAcoesUsuario(Principal.CLIENTES_TIPOS_PLANOS);

        if (!acoes[Util.EXCLUIR]) {
            JOptionPane.showMessageDialog(this.getParent(),
                "Você não pode efetuar esta exclusão.\n\nMotivo: "
                + "Usuário sem permissão.");
        } else {
            Util.excluir(clienteTipoPlanos, tbPlanosSaude, this);
        }
    }//GEN-LAST:event_btnExcluirPlanoSaudeActionPerformed

    private void btnAtualizarPlanosSaudeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtualizarPlanosSaudeActionPerformed
        Util.atualizar(clienteTipoPlanos, tbPlanosSaude);
    }//GEN-LAST:event_btnAtualizarPlanosSaudeActionPerformed

    private void tbProntuariosMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbProntuariosMouseReleased
        if (evt.getClickCount() == 2) {
            boolean[] acoes = Util.getAcoesUsuario(Principal.PRONTUARIOS);

            if (!acoes[Util.ALTERAR]) {
                JOptionPane.showMessageDialog(this.getParent(),
                    "Você não pode efetuar esta alteração.\n\nMotivo: "
                    + "Usuário sem permissão.");
            } else {
                try {
                    reescreverObjetoProntuario();

                    IFrmManProntuario iFrmManProntuario =
                            new IFrmManProntuario(prontuario);

                    this.getParent().remove(iFrmManProntuario);
                    this.getParent().add(iFrmManProntuario);

                    Util.centralizar(this.getParent(), iFrmManProntuario);

                    iFrmManProntuario.setVisible(true);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this.getParent(),
                        "Registro nao pode ser alterado.\n\nMotivo: " + ex);
                }
            }
        }
    }//GEN-LAST:event_tbProntuariosMouseReleased

    private void btnIncluirProntuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIncluirProntuarioActionPerformed
        boolean[] acoes = Util.getAcoesUsuario(Principal.PRONTUARIOS);

        if (!acoes[Util.INCLUIR]) {
            JOptionPane.showMessageDialog(this.getParent(),
                "Você não pode efetuar esta inclusão.\n\nMotivo: "
                + "Usuário sem permissão.");
        } else {
            try {
                Prontuarios pront = new Prontuarios();
                pront.setCodCliente(cliente.getIdCliente());
                IFrmManProntuario iFrmManProntuario = new IFrmManProntuario(pront);

                this.getParent().remove(iFrmManProntuario);
                this.getParent().add(iFrmManProntuario);

                Util.centralizar(this.getParent(), iFrmManProntuario);

                iFrmManProntuario.setVisible(true);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this.getParent(),
                    "Prontuários não pode ser incluído.\n\nMotivo: " + ex);
            }
        }
    }//GEN-LAST:event_btnIncluirProntuarioActionPerformed

    private void btnExcluirProntuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirProntuarioActionPerformed
        boolean[] acoes = Util.getAcoesUsuario(Principal.PRONTUARIOS);

        if (!acoes[Util.EXCLUIR]) {
            JOptionPane.showMessageDialog(this.getParent(),
                "Você não pode efetuar esta exclusão.\n\nMotivo: "
                + "Usuário sem permissão.");
        } else {
            Util.excluir(prontuario, tbProntuarios, this);
        }
    }//GEN-LAST:event_btnExcluirProntuarioActionPerformed

    private void btnAtualizarProntuariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtualizarProntuariosActionPerformed
        Util.atualizar(prontuario, tbProntuarios);
    }//GEN-LAST:event_btnAtualizarProntuariosActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAjudar;
    private javax.swing.JButton btnAtualizarAgendas;
    private javax.swing.JButton btnAtualizarPlanosSaude;
    private javax.swing.JButton btnAtualizarProntuarios;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnConfirmar;
    private javax.swing.JButton btnExcluirAgenda;
    private javax.swing.JButton btnExcluirPlanoSaude;
    private javax.swing.JButton btnExcluirProntuario;
    private javax.swing.JButton btnIncluirAgenda;
    private javax.swing.JButton btnIncluirPlanoSaude;
    private javax.swing.JButton btnIncluirProntuario;
    private javax.swing.JComboBox cbSituacao;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel lblLocalizar;
    private javax.swing.JMenu mnuAjuda;
    private javax.swing.JMenu mnuArquivos;
    private javax.swing.JMenuBar mnuBarPrincipal;
    private javax.swing.JPanel pnlBotoes;
    private javax.swing.JPanel pnlDetalhe;
    private javax.swing.JPanel pnlDetalhe1;
    private javax.swing.JPanel pnlDetalhe2;
    private javax.swing.JPanel pnlGeral;
    private javax.swing.JPanel pnlProdutos;
    private javax.swing.JPanel pnlProdutos1;
    private javax.swing.JPanel pnlProdutos2;
    private javax.swing.JTabbedPane tablManutencao;
    private javax.swing.JTable tbAgendas;
    private javax.swing.JTable tbPlanosSaude;
    private javax.swing.JTable tbProntuarios;
    private javax.swing.JTextField txtCodPessoa;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JFormattedTextField txtDataCadastro;
    private javax.swing.JTextField txtNomePessoa;
    private javax.swing.JTextArea txtObservacoes;
    // End of variables declaration//GEN-END:variables

    /**
     * @return the cliente
     */
    public Clientes getCliente() {
        return cliente;
    }

    /**
     * @param cliente the cliente to set
     */
    public void setCliente(Clientes cliente) {
        this.cliente = cliente;
    }

    /**
     * @return the prontuario
     */
    public Prontuarios getProntuario() {
        return prontuario;
    }

    /**
     * @param prontuario the prontuario to set
     */
    public void setProntuario(Prontuarios prontuario) {
        this.prontuario = prontuario;
    }

    /**
     * @return the clienteTipoPlanos
     */
    public ClientesTiposPlanos getClienteTipoPlanos() {
        return clienteTipoPlanos;
    }

    /**
     * @param clienteTipoPlanos the clienteTipoPlanos to set
     */
    public void setClienteTipoPlanos(ClientesTiposPlanos clienteTipoPlanos) {
        this.clienteTipoPlanos = clienteTipoPlanos;
    }

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
