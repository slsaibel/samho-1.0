/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.samho.apresentacao;

import com.samho.dao.DadosDAO;
import com.samho.negocio.Clientes;
import com.samho.negocio.Principal;
import com.samho.negocio.Prontuarios;
import com.samho.negocio.ProntuariosMedicamentos;
import com.samho.negocio.ProntuariosSintomasApresentados;
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
public final class IFrmManProntuario extends JInternalFrame {

    // Declaração de atributos
    private Prontuarios prontuario;
    private ProntuariosSintomasApresentados prontuarioSintomaApresentado;
    private ProntuariosMedicamentos prontuarioMedicamento;

    // Construtor
    public IFrmManProntuario(Prontuarios prontuario) throws Exception {
        initComponents();
        
        Formatacao.reformatarData(txtDataDeConsulta);
        
        Util.limparCampos(rootPane);

        this.prontuario = prontuario;

        // Permite que somente números sejam informados neste campo
        txtCodigo.setDocument(new JTextFieldDocument(true));
        txtCodCliente.setDocument(new JTextFieldDocument(true));
                
        // Quebra de linha automática
        txtObservacoes.setLineWrap(true);
        txtObservacoes.setWrapStyleWord(true);
        txtMotivoConsulta.setLineWrap(true);
        txtMotivoConsulta.setWrapStyleWord(true);
        txtDiagnostico.setLineWrap(true);
        txtDiagnostico.setWrapStyleWord(true);
        txtParecerProfissional.setLineWrap(true);
        txtParecerProfissional.setWrapStyleWord(true);
        
        if (Util.getAcoesUsuario(Principal.PRONTUARIOS_SINTOMAS_APRESENTADOS)[Util.CONSULTAR] == false) {
            tablManutencao.remove(pnlDetalhe2);
        }
        
        if (Util.getAcoesUsuario(Principal.PRONTUARIOS_MEDICAMENTOS)[Util.CONSULTAR] == false) {
            tablManutencao.remove(pnlDetalhe3);
        }
        
        tablManutencao.setSelectedIndex(0);

        carregarObjetoIFrmManutencao(prontuario);
    }

    public void carregarObjetoIFrmManutencao(Prontuarios prontuario) {
        try {
            if (prontuario.getIdProntuario()!= -1) {
                txtCodigo.setText(String.valueOf(prontuario.getIdProntuario()));
            } else {
                txtCodigo.setText(String.valueOf(
                        prontuario.getProntuarioDAO().getProximoID()));
            }

            txtCodCliente.setText(String.valueOf(prontuario.getCodCliente()));
            pesquisarCodigoDescicaoCliente(new Clientes());
            txtMotivoConsulta.setText(String.valueOf(
                    prontuario.getMotivoConsulta()));
            txtDiagnostico.setText(String.valueOf(prontuario.getDiagnostico()));
            txtDataDeConsulta.setText(Formatacao.ajustaDataDMA(
                    prontuario.getDataConsulta()));
            txtParecerProfissional.setText(String.valueOf(
                    prontuario.getParecerProfissional()));
            txtObservacoes.setText(String.valueOf(prontuario.getObservacoes()));
            chkAtivo.setSelected(prontuario.isDeveRetornar());
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                    "Registro nao pode ser carregado.\n\nMotivo: " + ex);
        }
    }

    private void reescreverObjeto() {
        try {
            prontuario.setIdProntuario(Long.parseUnsignedLong(
                    txtCodigo.getText()));
            prontuario.setCodCliente(Long.parseUnsignedLong(
                    txtCodCliente.getText()));
            prontuario.setMotivoConsulta(txtMotivoConsulta.getText());
            prontuario.setDiagnostico(txtDiagnostico.getText());
            prontuario.setDataConsulta(Formatacao.ajustaData(
                    txtDataDeConsulta.getText(), Formatacao.DATA_DMA));
            prontuario.setParecerProfissional(txtParecerProfissional.getText());
            prontuario.setObservacoes(txtObservacoes.getText());
            prontuario.setDeveRetornar(chkAtivo.isSelected());
            
            prontuario.adicionarCampos();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                    "Registro nao pode ser reescrito.\n\nMotivo: " + ex);
        }
    }

    public void pesquisarCodigoDescicaoCliente(Clientes cliente) {
        Registrador registrador = new Registrador();
        registrador = cliente.getObjetoDAO().popularCodigoDescricao(
                String.valueOf(prontuario.getCodCliente()),
                cliente.getClienteDAO().getSQLSubstiturCampoDescricao(
                        prontuario.getCodCliente()));
        txtCodCliente.setText(String.valueOf(registrador.getCodigo()));
        txtNomeCliente.setText(registrador.getDescricao());
    }
    
    private void reescreverObjetoSintomasApresentados() {
        try {
            prontuarioSintomaApresentado.setIdProntuarioSintomaApresentado(
                    Long.parseUnsignedLong(tbSintomasApresentados.getValueAt(
                            tbSintomasApresentados.getSelectedRow(), 0).toString()));
            prontuarioSintomaApresentado.setCodProntuario(Long.parseUnsignedLong(
                    tbSintomasApresentados.getValueAt(
                            tbSintomasApresentados.getSelectedRow(), 1).toString()));
            // Reserva-se a próxima posição para a descrição do campo acima.
            prontuarioSintomaApresentado.setCodSintomaApresentado(
                    Long.parseUnsignedLong(tbSintomasApresentados.getValueAt(
                            tbSintomasApresentados.getSelectedRow(), 3).toString()));
            // Reserva-se a próxima posição para a descrição do campo acima.
            prontuarioSintomaApresentado.setDiasComSintoma(
                    Integer.parseUnsignedInt(tbSintomasApresentados.getValueAt(
                            tbSintomasApresentados.getSelectedRow(), 5).toString()));

            prontuarioSintomaApresentado.adicionarCampos();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                    "Registro nao pode ser instanciado.\n\nMotivo: " + ex);
        }
    }

    private void reescreverObjetoMedicamentos() {
        try {
            prontuarioMedicamento.setIdProntuarioMedicamento(
                    Long.parseUnsignedLong(tbMedicamentos.getValueAt(
                            tbMedicamentos.getSelectedRow(), 0).toString()));
            prontuarioMedicamento.setCodProntuario(Long.parseUnsignedLong(
                    tbMedicamentos.getValueAt(
                            tbMedicamentos.getSelectedRow(), 1).toString()));
            // Reserva-se a próxima posição para a descrição do campo acima.
            prontuarioMedicamento.setCodMedicamento(
                    Long.parseUnsignedLong(tbMedicamentos.getValueAt(
                            tbMedicamentos.getSelectedRow(), 3).toString()));
            // Reserva-se a próxima posição para a descrição do campo acima.
            prontuarioMedicamento.setDiasComTratamento(
                    Integer.parseUnsignedInt(tbMedicamentos.getValueAt(
                            tbMedicamentos.getSelectedRow(), 5).toString()));
            prontuarioMedicamento.setQuantidade(
                    Integer.parseUnsignedInt(tbMedicamentos.getValueAt(
                            tbMedicamentos.getSelectedRow(), 6).toString()));
            prontuarioMedicamento.setParecerProfissional(tbMedicamentos.getValueAt(
                    tbMedicamentos.getSelectedRow(), 7).toString());

            prontuarioMedicamento.adicionarCampos();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                    "Registro nao pode ser instanciado.\n\nMotivo: " + ex);
        }
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
        txtCodCliente = new javax.swing.JTextField();
        lblLocalizarCliente = new javax.swing.JLabel();
        txtNomeCliente = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtDataDeConsulta = new javax.swing.JFormattedTextField();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtMotivoConsulta = new javax.swing.JTextArea();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtObservacoes = new javax.swing.JTextArea();
        jLabel7 = new javax.swing.JLabel();
        chkAtivo = new javax.swing.JCheckBox();
        pnlDetalhe = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtDiagnostico = new javax.swing.JTextArea();
        pnlDetalhe1 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        txtParecerProfissional = new javax.swing.JTextArea();
        pnlDetalhe2 = new javax.swing.JPanel();
        pnlTurnosProfissional1 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tbSintomasApresentados = new javax.swing.JTable();
        btnIncluirSintomaApresentado = new javax.swing.JButton();
        btnExcluirSintomaApresentado = new javax.swing.JButton();
        btnAtualizarSintomasApresentados = new javax.swing.JButton();
        pnlDetalhe3 = new javax.swing.JPanel();
        pnlTurnosProfissional2 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tbMedicamentos = new javax.swing.JTable();
        btnIncluirMedicamento = new javax.swing.JButton();
        btnExcluirMedicamento = new javax.swing.JButton();
        btnAtualizarMedicamentos = new javax.swing.JButton();
        pnlBotoes = new javax.swing.JPanel();
        btnConfirmar = new javax.swing.JButton();
        btnAjudar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        mnuBarPrincipal = new javax.swing.JMenuBar();
        mnuArquivos = new javax.swing.JMenu();
        mnuAjuda = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cadastro de Prontuário do Cliente");
        setToolTipText("");
        setMaximumSize(new java.awt.Dimension(730, 404));
        setMinimumSize(new java.awt.Dimension(730, 404));
        setName(""); // NOI18N
        setPreferredSize(new java.awt.Dimension(730, 404));
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
        jLabel2.setText("Cód. Prontuario");
        pnlGeral.add(jLabel2);
        jLabel2.setBounds(68, 23, 87, 14);

        txtCodigo.setEditable(false);
        txtCodigo.setBackground(java.awt.SystemColor.info);
        txtCodigo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        pnlGeral.add(txtCodigo);
        txtCodigo.setBounds(160, 20, 70, 20);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setText("Cód. Cliente");
        pnlGeral.add(jLabel1);
        jLabel1.setBounds(88, 48, 66, 14);

        txtCodCliente.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCodCliente.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodClienteFocusLost(evt);
            }
        });
        pnlGeral.add(txtCodCliente);
        txtCodCliente.setBounds(160, 45, 70, 20);

        lblLocalizarCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/localizar.png"))); // NOI18N
        lblLocalizarCliente.setToolTipText("Localizar uma pessoa já cadastrada.");
        lblLocalizarCliente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblLocalizarCliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                lblLocalizarClienteMouseReleased(evt);
            }
        });
        pnlGeral.add(lblLocalizarCliente);
        lblLocalizarCliente.setBounds(234, 45, 20, 20);

        txtNomeCliente.setEditable(false);
        txtNomeCliente.setBackground(java.awt.SystemColor.info);
        txtNomeCliente.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        pnlGeral.add(txtNomeCliente);
        txtNomeCliente.setBounds(258, 45, 402, 20);

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel6.setText("Data da Consulta");
        pnlGeral.add(jLabel6);
        jLabel6.setBounds(60, 73, 96, 14);

        try {
            txtDataDeConsulta.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtDataDeConsulta.setVerifyInputWhenFocusTarget(false);
        txtDataDeConsulta.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDataDeConsultaFocusGained(evt);
            }
        });
        pnlGeral.add(txtDataDeConsulta);
        txtDataDeConsulta.setBounds(160, 70, 70, 20);

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel4.setText("Motivo Consulta");
        pnlGeral.add(jLabel4);
        jLabel4.setBounds(65, 100, 91, 14);

        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane2.setAutoscrolls(true);
        jScrollPane2.setMaximumSize(new java.awt.Dimension(32767, 500));

        txtMotivoConsulta.setColumns(60);
        txtMotivoConsulta.setLineWrap(true);
        txtMotivoConsulta.setRows(5);
        txtMotivoConsulta.setText("Motivo da consulta");
        txtMotivoConsulta.setToolTipText("");
        txtMotivoConsulta.setAutoscrolls(false);
        txtMotivoConsulta.setMaximumSize(new java.awt.Dimension(497, 74));
        txtMotivoConsulta.setMinimumSize(new java.awt.Dimension(497, 74));
        jScrollPane2.setViewportView(txtMotivoConsulta);

        pnlGeral.add(jScrollPane2);
        jScrollPane2.setBounds(160, 95, 497, 74);

        jLabel8.setText("Observações");
        pnlGeral.add(jLabel8);
        jLabel8.setBounds(92, 178, 63, 14);

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
        jScrollPane1.setBounds(160, 175, 497, 74);

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel7.setText("Necessita Retornar");
        pnlGeral.add(jLabel7);
        jLabel7.setBounds(45, 252, 110, 14);

        chkAtivo.setToolTipText("");
        pnlGeral.add(chkAtivo);
        chkAtivo.setBounds(160, 250, 21, 21);

        tablManutencao.addTab("Geral", pnlGeral);

        pnlDetalhe.setLayout(null);

        jScrollPane3.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane3.setAutoscrolls(true);
        jScrollPane3.setMaximumSize(new java.awt.Dimension(32767, 500));

        txtDiagnostico.setColumns(60);
        txtDiagnostico.setRows(5);
        txtDiagnostico.setToolTipText("");
        txtDiagnostico.setAutoscrolls(false);
        txtDiagnostico.setMaximumSize(new java.awt.Dimension(497, 74));
        txtDiagnostico.setMinimumSize(new java.awt.Dimension(497, 74));
        jScrollPane3.setViewportView(txtDiagnostico);

        pnlDetalhe.add(jScrollPane3);
        jScrollPane3.setBounds(0, 0, 700, 280);

        tablManutencao.addTab("Diagnóstico", pnlDetalhe);

        pnlDetalhe1.setLayout(null);

        jScrollPane4.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane4.setAutoscrolls(true);
        jScrollPane4.setMaximumSize(new java.awt.Dimension(32767, 500));

        txtParecerProfissional.setColumns(60);
        txtParecerProfissional.setRows(5);
        txtParecerProfissional.setToolTipText("");
        txtParecerProfissional.setAutoscrolls(false);
        txtParecerProfissional.setMaximumSize(new java.awt.Dimension(497, 74));
        txtParecerProfissional.setMinimumSize(new java.awt.Dimension(497, 74));
        jScrollPane4.setViewportView(txtParecerProfissional);

        pnlDetalhe1.add(jScrollPane4);
        jScrollPane4.setBounds(0, 0, 700, 280);

        tablManutencao.addTab("Parecer Profissional", pnlDetalhe1);

        pnlTurnosProfissional1.setBorder(javax.swing.BorderFactory.createTitledBorder("Sintomas Aopresentados"));
        pnlTurnosProfissional1.setLayout(null);

        tbSintomasApresentados.setModel(new javax.swing.table.DefaultTableModel(
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
        tbSintomasApresentados.setName(""); // NOI18N
        tbSintomasApresentados.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tbSintomasApresentadosMouseReleased(evt);
            }
        });
        jScrollPane5.setViewportView(tbSintomasApresentados);

        pnlTurnosProfissional1.add(jScrollPane5);
        jScrollPane5.setBounds(6, 16, 600, 260);

        btnIncluirSintomaApresentado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/incluir.png"))); // NOI18N
        btnIncluirSintomaApresentado.setText("Incluir");
        btnIncluirSintomaApresentado.setToolTipText("Incluir um endereços para a pessoa.");
        btnIncluirSintomaApresentado.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnIncluirSintomaApresentado.setMargin(new java.awt.Insets(2, 5, 2, 5));
        btnIncluirSintomaApresentado.setMaximumSize(new java.awt.Dimension(69, 29));
        btnIncluirSintomaApresentado.setMinimumSize(null);
        btnIncluirSintomaApresentado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIncluirSintomaApresentadoActionPerformed(evt);
            }
        });
        pnlTurnosProfissional1.add(btnIncluirSintomaApresentado);
        btnIncluirSintomaApresentado.setBounds(610, 15, 81, 29);

        btnExcluirSintomaApresentado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/excluir.png"))); // NOI18N
        btnExcluirSintomaApresentado.setText("Excluir");
        btnExcluirSintomaApresentado.setToolTipText("Excluir um  endereço para a pessoa.");
        btnExcluirSintomaApresentado.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnExcluirSintomaApresentado.setMargin(new java.awt.Insets(2, 5, 2, 5));
        btnExcluirSintomaApresentado.setMinimumSize(null);
        btnExcluirSintomaApresentado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirSintomaApresentadoActionPerformed(evt);
            }
        });
        pnlTurnosProfissional1.add(btnExcluirSintomaApresentado);
        btnExcluirSintomaApresentado.setBounds(610, 50, 81, 29);

        btnAtualizarSintomasApresentados.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/atualizar.png"))); // NOI18N
        btnAtualizarSintomasApresentados.setText("Atualizar");
        btnAtualizarSintomasApresentados.setToolTipText("Atualizar lista de endereços da pessoa.");
        btnAtualizarSintomasApresentados.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAtualizarSintomasApresentados.setMargin(new java.awt.Insets(2, 5, 2, 5));
        btnAtualizarSintomasApresentados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtualizarSintomasApresentadosActionPerformed(evt);
            }
        });
        pnlTurnosProfissional1.add(btnAtualizarSintomasApresentados);
        btnAtualizarSintomasApresentados.setBounds(610, 85, 81, 29);

        javax.swing.GroupLayout pnlDetalhe2Layout = new javax.swing.GroupLayout(pnlDetalhe2);
        pnlDetalhe2.setLayout(pnlDetalhe2Layout);
        pnlDetalhe2Layout.setHorizontalGroup(
            pnlDetalhe2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 705, Short.MAX_VALUE)
            .addGroup(pnlDetalhe2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pnlDetalhe2Layout.createSequentialGroup()
                    .addComponent(pnlTurnosProfissional1, javax.swing.GroupLayout.PREFERRED_SIZE, 701, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 4, Short.MAX_VALUE)))
        );
        pnlDetalhe2Layout.setVerticalGroup(
            pnlDetalhe2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 282, Short.MAX_VALUE)
            .addGroup(pnlDetalhe2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(pnlTurnosProfissional1, javax.swing.GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE))
        );

        tablManutencao.addTab("Sintomas Apresentados", pnlDetalhe2);

        pnlTurnosProfissional2.setBorder(javax.swing.BorderFactory.createTitledBorder("Medicamentos"));
        pnlTurnosProfissional2.setLayout(null);

        tbMedicamentos.setModel(new javax.swing.table.DefaultTableModel(
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
        tbMedicamentos.setName(""); // NOI18N
        tbMedicamentos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tbMedicamentosMouseReleased(evt);
            }
        });
        jScrollPane6.setViewportView(tbMedicamentos);

        pnlTurnosProfissional2.add(jScrollPane6);
        jScrollPane6.setBounds(6, 16, 600, 260);

        btnIncluirMedicamento.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/incluir.png"))); // NOI18N
        btnIncluirMedicamento.setText("Incluir");
        btnIncluirMedicamento.setToolTipText("Incluir um endereços para a pessoa.");
        btnIncluirMedicamento.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnIncluirMedicamento.setMargin(new java.awt.Insets(2, 5, 2, 5));
        btnIncluirMedicamento.setMaximumSize(new java.awt.Dimension(69, 29));
        btnIncluirMedicamento.setMinimumSize(null);
        btnIncluirMedicamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIncluirMedicamentoActionPerformed(evt);
            }
        });
        pnlTurnosProfissional2.add(btnIncluirMedicamento);
        btnIncluirMedicamento.setBounds(610, 15, 81, 29);

        btnExcluirMedicamento.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/excluir.png"))); // NOI18N
        btnExcluirMedicamento.setText("Excluir");
        btnExcluirMedicamento.setToolTipText("Excluir um  endereço para a pessoa.");
        btnExcluirMedicamento.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnExcluirMedicamento.setMargin(new java.awt.Insets(2, 5, 2, 5));
        btnExcluirMedicamento.setMinimumSize(null);
        btnExcluirMedicamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirMedicamentoActionPerformed(evt);
            }
        });
        pnlTurnosProfissional2.add(btnExcluirMedicamento);
        btnExcluirMedicamento.setBounds(610, 50, 81, 29);

        btnAtualizarMedicamentos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/atualizar.png"))); // NOI18N
        btnAtualizarMedicamentos.setText("Atualizar");
        btnAtualizarMedicamentos.setToolTipText("Atualizar lista de endereços da pessoa.");
        btnAtualizarMedicamentos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAtualizarMedicamentos.setMargin(new java.awt.Insets(2, 5, 2, 5));
        btnAtualizarMedicamentos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtualizarMedicamentosActionPerformed(evt);
            }
        });
        pnlTurnosProfissional2.add(btnAtualizarMedicamentos);
        btnAtualizarMedicamentos.setBounds(610, 85, 81, 29);

        javax.swing.GroupLayout pnlDetalhe3Layout = new javax.swing.GroupLayout(pnlDetalhe3);
        pnlDetalhe3.setLayout(pnlDetalhe3Layout);
        pnlDetalhe3Layout.setHorizontalGroup(
            pnlDetalhe3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 705, Short.MAX_VALUE)
            .addGroup(pnlDetalhe3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pnlDetalhe3Layout.createSequentialGroup()
                    .addComponent(pnlTurnosProfissional2, javax.swing.GroupLayout.PREFERRED_SIZE, 701, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 4, Short.MAX_VALUE)))
        );
        pnlDetalhe3Layout.setVerticalGroup(
            pnlDetalhe3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 282, Short.MAX_VALUE)
            .addGroup(pnlDetalhe3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(pnlTurnosProfissional2, javax.swing.GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE))
        );

        tablManutencao.addTab("Medicamentos", pnlDetalhe3);

        getContentPane().add(tablManutencao);
        tablManutencao.setBounds(0, 0, 710, 310);
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
        pnlBotoes.setBounds(0, 315, 713, 38);

        mnuArquivos.setText("Arquivo");
        mnuBarPrincipal.add(mnuArquivos);

        mnuAjuda.setText("Ajuda");
        mnuBarPrincipal.add(mnuAjuda);

        setJMenuBar(mnuBarPrincipal);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirmarActionPerformed
        reescreverObjeto();

        Util.salvar(prontuario, this, true);
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
                    "Você não pode efetuar esta consulta.\n\nMotivo: "
                            + "Usuário sem permissão.");
        } else {        
            try {
                IFrmPesqClientes iFrmPesqClientes = new IFrmPesqClientes(
                        new IFrmManCliente(new Clientes()), txtCodCliente, txtNomeCliente);

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

    private void txtCodClienteFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodClienteFocusLost
        pesquisarCodigoDescicaoCliente(new Clientes());
    }//GEN-LAST:event_txtCodClienteFocusLost

    private void txtDataDeConsultaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDataDeConsultaFocusGained
        txtDataDeConsulta.selectAll();
    }//GEN-LAST:event_txtDataDeConsultaFocusGained

    private void tbSintomasApresentadosMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbSintomasApresentadosMouseReleased
        if (evt.getClickCount() == 2) {
            boolean[] acoes = Util.getAcoesUsuario(Principal.PRONTUARIOS_SINTOMAS_APRESENTADOS);

            if (!acoes[Util.ALTERAR]) {
                JOptionPane.showMessageDialog(this.getParent(),
                    "Você não pode efetuar esta alteração.\n\nMotivo: "
                    + "Usuário sem permissão.");
            } else {
                try {
                    reescreverObjetoSintomasApresentados();

                    IFrmManProntuarioSintomaApresentado iFrmManProntuarioSintomaApresentado =
                    new IFrmManProntuarioSintomaApresentado(prontuarioSintomaApresentado);

                    this.getParent().remove(iFrmManProntuarioSintomaApresentado);
                    this.getParent().add(iFrmManProntuarioSintomaApresentado);

                    Util.centralizar(this.getParent(), iFrmManProntuarioSintomaApresentado);

                    iFrmManProntuarioSintomaApresentado.setVisible(true);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this.getParent(),
                        "Registro nao pode ser alterado.\n\nMotivo: " + ex);
                }
            }
        }
    }//GEN-LAST:event_tbSintomasApresentadosMouseReleased

    private void btnIncluirSintomaApresentadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIncluirSintomaApresentadoActionPerformed
        boolean[] acoes = Util.getAcoesUsuario(Principal.PRONTUARIOS_SINTOMAS_APRESENTADOS);

        if (!acoes[Util.INCLUIR]) {
            JOptionPane.showMessageDialog(this.getParent(),
                "Você não pode efetuar esta inclusão.\n\nMotivo: "
                + "Usuário sem permissão.");
        } else {
            try {
                ProntuariosSintomasApresentados prontSintomaApresentado =
                new ProntuariosSintomasApresentados();
                prontSintomaApresentado.setCodProntuario(
                    prontuario.getIdProntuario());
                IFrmManProntuarioSintomaApresentado iFrmManProntuarioSintomaApresentado =
                new IFrmManProntuarioSintomaApresentado(prontSintomaApresentado);

                this.getParent().remove(iFrmManProntuarioSintomaApresentado);
                this.getParent().add(iFrmManProntuarioSintomaApresentado);

                Util.centralizar(this.getParent(), iFrmManProntuarioSintomaApresentado);

                iFrmManProntuarioSintomaApresentado.setVisible(true);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this.getParent(),
                    "Sintoma não pode ser incluído.\n\nMotivo: " + ex);
            }
        }
    }//GEN-LAST:event_btnIncluirSintomaApresentadoActionPerformed

    private void btnExcluirSintomaApresentadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirSintomaApresentadoActionPerformed
        boolean[] acoes = Util.getAcoesUsuario(Principal.PRONTUARIOS_SINTOMAS_APRESENTADOS);

        if (!acoes[Util.EXCLUIR]) {
            JOptionPane.showMessageDialog(this.getParent(),
                "Você não pode efetuar esta exclusão.\n\nMotivo: "
                + "Usuário sem permissão.");
        } else {
            Util.excluir(prontuarioSintomaApresentado, tbSintomasApresentados, this);
        }
    }//GEN-LAST:event_btnExcluirSintomaApresentadoActionPerformed

    private void btnAtualizarSintomasApresentadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtualizarSintomasApresentadosActionPerformed
        Util.atualizar(prontuarioSintomaApresentado, tbSintomasApresentados);
    }//GEN-LAST:event_btnAtualizarSintomasApresentadosActionPerformed

    private void tbMedicamentosMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbMedicamentosMouseReleased
        if (evt.getClickCount() == 2) {
            boolean[] acoes = Util.getAcoesUsuario(Principal.PRONTUARIOS_MEDICAMENTOS);

            if (!acoes[Util.ALTERAR]) {
                JOptionPane.showMessageDialog(this.getParent(),
                    "Você não pode efetuar esta alteração.\n\nMotivo: "
                    + "Usuário sem permissão.");
            } else {
                try {
                    reescreverObjetoMedicamentos();

                    IFrmManProntuarioMedicamento iFrmManProntuarioMedicamento =
                    new IFrmManProntuarioMedicamento(prontuarioMedicamento);

                    this.getParent().remove(iFrmManProntuarioMedicamento);
                    this.getParent().add(iFrmManProntuarioMedicamento);

                    Util.centralizar(this.getParent(), iFrmManProntuarioMedicamento);

                    iFrmManProntuarioMedicamento.setVisible(true);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this.getParent(),
                        "Registro nao pode ser alterado.\n\nMotivo: " + ex);
                }
            }
        }
    }//GEN-LAST:event_tbMedicamentosMouseReleased

    private void btnIncluirMedicamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIncluirMedicamentoActionPerformed
        boolean[] acoes = Util.getAcoesUsuario(Principal.PRONTUARIOS_MEDICAMENTOS);

        if (!acoes[Util.INCLUIR]) {
            JOptionPane.showMessageDialog(this.getParent(),
                "Você não pode efetuar esta inclusão.\n\nMotivo: "
                + "Usuário sem permissão.");
        } else {
            try {
                ProntuariosMedicamentos prontMedicamentos =
                        new ProntuariosMedicamentos();
                prontMedicamentos.setCodProntuario(
                    prontuario.getIdProntuario());
                IFrmManProntuarioMedicamento iFrmManProntuarioMedicamento =
                new IFrmManProntuarioMedicamento(prontMedicamentos);

                this.getParent().remove(iFrmManProntuarioMedicamento);
                this.getParent().add(iFrmManProntuarioMedicamento);

                Util.centralizar(this.getParent(), iFrmManProntuarioMedicamento);

                iFrmManProntuarioMedicamento.setVisible(true);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this.getParent(),
                    "Sintoma não pode ser incluído.\n\nMotivo: " + ex);
            }
        }
    }//GEN-LAST:event_btnIncluirMedicamentoActionPerformed

    private void btnExcluirMedicamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirMedicamentoActionPerformed
        boolean[] acoes = Util.getAcoesUsuario(Principal.PRONTUARIOS_MEDICAMENTOS);

        if (!acoes[Util.EXCLUIR]) {
            JOptionPane.showMessageDialog(this.getParent(),
                "Você não pode efetuar esta exclusão.\n\nMotivo: "
                + "Usuário sem permissão.");
        } else {
            Util.excluir(prontuarioMedicamento, tbMedicamentos, this);
        }
    }//GEN-LAST:event_btnExcluirMedicamentoActionPerformed

    private void btnAtualizarMedicamentosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtualizarMedicamentosActionPerformed
        Util.atualizar(prontuarioMedicamento, tbMedicamentos);
    }//GEN-LAST:event_btnAtualizarMedicamentosActionPerformed

    private void tablManutencaoStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_tablManutencaoStateChanged
        if (tablManutencao.getSelectedIndex() > 0) {
            try {
                reescreverObjeto();
                Util.salvar(prontuario, this, false);
                
                // Sintomas Apresentados
                prontuarioSintomaApresentado = new ProntuariosSintomasApresentados();
                prontuarioSintomaApresentado.setCodProntuario(Long.parseUnsignedLong(
                        txtCodigo.getText()));
                prontuarioSintomaApresentado.adicionarWhere(new DadosDAO(
                        "cod_prontuario", "", txtCodigo.getText(), 
                        DadosDAO.TIPO_LONG, DadosDAO.IS_IGUAL, DadosDAO.NAO_CHAVE));
                prontuarioSintomaApresentado.getProntuarioSintomaApresentadoDAO().popularGridJTable(
                        tbSintomasApresentados);
                
                // Medicamentos
                prontuarioMedicamento = new ProntuariosMedicamentos();
                prontuarioMedicamento.setCodProntuario(Long.parseUnsignedLong(
                        txtCodigo.getText()));
                prontuarioMedicamento.adicionarWhere(new DadosDAO(
                        "cod_prontuario", "", txtCodigo.getText(), 
                        DadosDAO.TIPO_LONG, DadosDAO.IS_IGUAL, DadosDAO.NAO_CHAVE));
                prontuarioMedicamento.getProntuarioMedicamentoDAO().popularGridJTable(
                        tbMedicamentos);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this.getParent(),
                        "Registro nao pode ser carregado.\n\nMotivo: " + ex);
            }
        }
    }//GEN-LAST:event_tablManutencaoStateChanged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAjudar;
    private javax.swing.JButton btnAtualizarMedicamentos;
    private javax.swing.JButton btnAtualizarSintomasApresentados;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnConfirmar;
    private javax.swing.JButton btnExcluirMedicamento;
    private javax.swing.JButton btnExcluirSintomaApresentado;
    private javax.swing.JButton btnIncluirMedicamento;
    private javax.swing.JButton btnIncluirSintomaApresentado;
    private javax.swing.JCheckBox chkAtivo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JLabel lblLocalizarCliente;
    private javax.swing.JMenu mnuAjuda;
    private javax.swing.JMenu mnuArquivos;
    private javax.swing.JMenuBar mnuBarPrincipal;
    private javax.swing.JPanel pnlBotoes;
    private javax.swing.JPanel pnlDetalhe;
    private javax.swing.JPanel pnlDetalhe1;
    private javax.swing.JPanel pnlDetalhe2;
    private javax.swing.JPanel pnlDetalhe3;
    private javax.swing.JPanel pnlGeral;
    private javax.swing.JPanel pnlTurnosProfissional1;
    private javax.swing.JPanel pnlTurnosProfissional2;
    private javax.swing.JTabbedPane tablManutencao;
    private javax.swing.JTable tbMedicamentos;
    private javax.swing.JTable tbSintomasApresentados;
    private javax.swing.JTextField txtCodCliente;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JFormattedTextField txtDataDeConsulta;
    private javax.swing.JTextArea txtDiagnostico;
    private javax.swing.JTextArea txtMotivoConsulta;
    private javax.swing.JTextField txtNomeCliente;
    private javax.swing.JTextArea txtObservacoes;
    private javax.swing.JTextArea txtParecerProfissional;
    // End of variables declaration//GEN-END:variables

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
