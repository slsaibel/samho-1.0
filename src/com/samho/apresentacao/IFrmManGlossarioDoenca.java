/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.samho.apresentacao;

import com.samho.dao.DadosDAO;
import com.samho.negocio.Profissionais;
import com.samho.negocio.Principal;
import com.samho.negocio.GlossarioDoencas;
import com.samho.negocio.GlossarioDoencasMedicamentos;
import com.samho.negocio.GlossarioDoencasSintomasApresentados;
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
public final class IFrmManGlossarioDoenca extends JInternalFrame {

    // Declaração de atributos
    private GlossarioDoencas glossarioDoenca;
    private GlossarioDoencasSintomasApresentados glossarioDoencaSintomaApresentado;
    private GlossarioDoencasMedicamentos glossarioDoencaMedicamento;

    // Construtor
    public IFrmManGlossarioDoenca(GlossarioDoencas glossarioDoenca) throws Exception {
        initComponents();
        
        Formatacao.reformatarData(txtDataDeRegistro);
        
        Util.limparCampos(rootPane);

        this.glossarioDoenca = glossarioDoenca;

        // Permite que somente números sejam informados neste campo
        txtCodigo.setDocument(new JTextFieldDocument(true));
        txtCodProfissionalRegistro.setDocument(new JTextFieldDocument(true));
        txtDiasTratamento.setDocument(new JTextFieldDocument(true));
                
        // Quebra de linha automática
        txtObservacoes.setLineWrap(true);
        txtObservacoes.setWrapStyleWord(true);
        txtDescricao.setLineWrap(true);
        txtDescricao.setWrapStyleWord(true);
        
        if (Util.getAcoesUsuario(Principal.GLOSSARIO_DOENCAS_SINTOMAS_APRESENTADOS)[Util.CONSULTAR] == false) {
            tablManutencao.remove(pnlDetalhe);
        }
        
        if (Util.getAcoesUsuario(Principal.GLOSSARIO_DOENCAS_SINTOMAS_APRESENTADOS)[Util.CONSULTAR] == false) {
            tablManutencao.remove(pnlDetalhe1);
        }

        tablManutencao.setSelectedIndex(0);
        
        carregarObjetoIFrmManutencao(glossarioDoenca);
    }

    public void carregarObjetoIFrmManutencao(GlossarioDoencas glossarioDoenca) {
        try {
            if (glossarioDoenca.getIdGlossarioDoenca()!= -1) {
                txtCodigo.setText(String.valueOf(
                        glossarioDoenca.getIdGlossarioDoenca()));
            } else {
                txtCodigo.setText(String.valueOf(
                        glossarioDoenca.getGlossarioDoencaDAO().getProximoID()));
            }
         
            txtCodProfissionalRegistro.setText(String.valueOf(
                    glossarioDoenca.getCodProfissionalCadastro()));
            pesquisarCodigoDescicaoProfissional(new Profissionais());
            txtCID10.setText(String.valueOf(glossarioDoenca.getCID10()));
            txtDescricao.setText(String.valueOf(glossarioDoenca.getDescricao()));
            txtDiasTratamento.setText(String.valueOf(
                    glossarioDoenca.getDiasTratamento()));
            txtDataDeRegistro.setText(Formatacao.ajustaDataDMA(
                    glossarioDoenca.getDataRegistro()));
            txtObservacoes.setText(String.valueOf(
                    glossarioDoenca.getObservacoes()));
            lblRevisao.setText(String.valueOf(glossarioDoenca.getRevisao()));
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                    "Registro nao pode ser carregado.\n\nMotivo: " + ex);
        }
    }

    private void reescreverObjeto() {
        try {
            glossarioDoenca.setIdGlossarioDoenca(Long.parseUnsignedLong(
                    txtCodigo.getText()));
            glossarioDoenca.setCodProfissionalCadastro(Long.parseUnsignedLong(
                    txtCodProfissionalRegistro.getText()));
            glossarioDoenca.setCID10(txtCID10.getText());
            glossarioDoenca.setDescricao(txtDescricao.getText());
            glossarioDoenca.setDiasTratamento(Integer.parseUnsignedInt(
                    txtDiasTratamento.getText()));
            glossarioDoenca.setDataRegistro(Formatacao.ajustaData(
                    txtDataDeRegistro.getText(), Formatacao.DATA_DMA));
            glossarioDoenca.setObservacoes(txtObservacoes.getText());
            glossarioDoenca.setRevisao(Long.parseUnsignedLong(
                    lblRevisao.getText()));
            
            glossarioDoenca.adicionarCampos();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                    "Registro nao pode ser reescrito.\n\nMotivo: " + ex);
        }
    }
    
    private void reescreverObjetoSintomasApresentados() {
        try {
            glossarioDoencaSintomaApresentado.setIdGlossarioDoencaSintomaApresentado(
                    Long.parseUnsignedLong(tbSintomasApresentados.getValueAt(
                            tbSintomasApresentados.getSelectedRow(), 0).toString()));
            glossarioDoencaSintomaApresentado.setCodGlossarioDoenca(Long.parseUnsignedLong(
                    tbSintomasApresentados.getValueAt(
                            tbSintomasApresentados.getSelectedRow(), 1).toString()));
            // Reserva-se a próxima posição para a descrição do campo acima.
            glossarioDoencaSintomaApresentado.setCodSintomaApresentado(
                    Long.parseUnsignedLong(tbSintomasApresentados.getValueAt(
                            tbSintomasApresentados.getSelectedRow(), 3).toString()));
            // Reserva-se a próxima posição para a descrição do campo acima.
            glossarioDoencaSintomaApresentado.setDiasComSintoma(
                    Integer.parseUnsignedInt(tbSintomasApresentados.getValueAt(
                            tbSintomasApresentados.getSelectedRow(), 5).toString()));

            glossarioDoencaSintomaApresentado.adicionarCampos();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                    "Registro nao pode ser instanciado.\n\nMotivo: " + ex);
        }
    }
    
        private void reescreverObjetoMedicamentos() {
        try {
            glossarioDoencaMedicamento.setIdGlossarioDoencasMedicamento(
                    Long.parseUnsignedLong(tbMedicamentos.getValueAt(
                            tbMedicamentos.getSelectedRow(), 0).toString()));
            glossarioDoencaMedicamento.setCodGlossarioDoenca(
                    Long.parseUnsignedLong(tbMedicamentos.getValueAt(
                            tbMedicamentos.getSelectedRow(), 1).toString()));
            // Reserva-se a próxima posição para a descrição do campo acima.
            glossarioDoencaMedicamento.setCodMedicamento(
                    Long.parseUnsignedLong(tbMedicamentos.getValueAt(
                            tbMedicamentos.getSelectedRow(), 3).toString()));
            // Reserva-se a próxima posição para a descrição do campo acima.
            glossarioDoencaMedicamento.setDiasComTratamento(
                    Integer.parseUnsignedInt(tbMedicamentos.getValueAt(
                            tbMedicamentos.getSelectedRow(), 5).toString()));
            glossarioDoencaMedicamento.setQuantidade(
                    Integer.parseUnsignedInt(tbMedicamentos.getValueAt(
                            tbMedicamentos.getSelectedRow(), 6).toString()));
            glossarioDoencaMedicamento.setParecerProfissional(tbMedicamentos.getValueAt(
                    tbMedicamentos.getSelectedRow(), 7).toString());

            glossarioDoencaMedicamento.adicionarCampos();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                    "Registro nao pode ser instanciado.\n\nMotivo: " + ex);
        }
    }

    public void pesquisarCodigoDescicaoProfissional(Profissionais profissional) {
        Registrador registrador = new Registrador();
        registrador = profissional.getObjetoDAO().popularCodigoDescricao(
                String.valueOf(glossarioDoenca.getCodProfissionalCadastro()),
                profissional.getProfissionalDAO().getSQLSubstiturCampoDescricao(
                        glossarioDoenca.getCodProfissionalCadastro()));
        txtCodProfissionalRegistro.setText(String.valueOf(registrador.getCodigo()));
        txtNomeProfissionalRegistro.setText(registrador.getDescricao());
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
        txtCodProfissionalRegistro = new javax.swing.JTextField();
        lblLocalizarProfissionalRegistro = new javax.swing.JLabel();
        txtNomeProfissionalRegistro = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtCID10 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtDataDeRegistro = new javax.swing.JFormattedTextField();
        jLabel12 = new javax.swing.JLabel();
        txtDiasTratamento = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtDescricao = new javax.swing.JTextArea();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtObservacoes = new javax.swing.JTextArea();
        jLabel9 = new javax.swing.JLabel();
        lblRevisao = new javax.swing.JLabel();
        pnlDetalhe = new javax.swing.JPanel();
        pnlTurnosProfissional1 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tbSintomasApresentados = new javax.swing.JTable();
        btnIncluirSintomaApresentado = new javax.swing.JButton();
        btnExcluirSintomaApresentado = new javax.swing.JButton();
        btnAtualizarSintomasApresentados = new javax.swing.JButton();
        pnlDetalhe1 = new javax.swing.JPanel();
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
        setTitle("Cadastro de Glossário de Doença");
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
        jLabel2.setText("Cód. Glossário");
        pnlGeral.add(jLabel2);
        jLabel2.setBounds(76, 23, 79, 14);

        txtCodigo.setEditable(false);
        txtCodigo.setBackground(java.awt.SystemColor.info);
        txtCodigo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        pnlGeral.add(txtCodigo);
        txtCodigo.setBounds(160, 20, 70, 20);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setText("Cód. Profissional Reg.");
        pnlGeral.add(jLabel1);
        jLabel1.setBounds(34, 48, 120, 14);

        txtCodProfissionalRegistro.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCodProfissionalRegistro.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodProfissionalRegistroFocusLost(evt);
            }
        });
        pnlGeral.add(txtCodProfissionalRegistro);
        txtCodProfissionalRegistro.setBounds(160, 45, 70, 20);

        lblLocalizarProfissionalRegistro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/localizar.png"))); // NOI18N
        lblLocalizarProfissionalRegistro.setToolTipText("Localizar uma pessoa já cadastrada.");
        lblLocalizarProfissionalRegistro.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblLocalizarProfissionalRegistro.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                lblLocalizarProfissionalRegistroMouseReleased(evt);
            }
        });
        pnlGeral.add(lblLocalizarProfissionalRegistro);
        lblLocalizarProfissionalRegistro.setBounds(234, 45, 20, 20);

        txtNomeProfissionalRegistro.setEditable(false);
        txtNomeProfissionalRegistro.setBackground(java.awt.SystemColor.info);
        txtNomeProfissionalRegistro.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        pnlGeral.add(txtNomeProfissionalRegistro);
        txtNomeProfissionalRegistro.setBounds(258, 45, 402, 20);

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel13.setText("CID-10");
        pnlGeral.add(jLabel13);
        jLabel13.setBounds(115, 73, 39, 14);

        txtCID10.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCID10.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCID10FocusGained(evt);
            }
        });
        pnlGeral.add(txtCID10);
        txtCID10.setBounds(160, 70, 70, 20);

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel6.setText("Data da Registro");
        pnlGeral.add(jLabel6);
        jLabel6.setBounds(270, 73, 95, 14);

        try {
            txtDataDeRegistro.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtDataDeRegistro.setVerifyInputWhenFocusTarget(false);
        txtDataDeRegistro.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDataDeRegistroFocusGained(evt);
            }
        });
        pnlGeral.add(txtDataDeRegistro);
        txtDataDeRegistro.setBounds(370, 70, 70, 20);

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel12.setText("Dias de Tratamento");
        pnlGeral.add(jLabel12);
        jLabel12.setBounds(470, 73, 112, 14);

        txtDiasTratamento.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtDiasTratamento.setText("999999");
        txtDiasTratamento.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDiasTratamentoFocusGained(evt);
            }
        });
        pnlGeral.add(txtDiasTratamento);
        txtDiasTratamento.setBounds(590, 70, 70, 20);

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel4.setText("Descrição");
        pnlGeral.add(jLabel4);
        jLabel4.setBounds(101, 100, 55, 14);

        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane2.setAutoscrolls(true);
        jScrollPane2.setMaximumSize(new java.awt.Dimension(32767, 500));

        txtDescricao.setColumns(60);
        txtDescricao.setLineWrap(true);
        txtDescricao.setRows(5);
        txtDescricao.setToolTipText("");
        txtDescricao.setAutoscrolls(false);
        txtDescricao.setMaximumSize(new java.awt.Dimension(497, 74));
        txtDescricao.setMinimumSize(new java.awt.Dimension(497, 74));
        jScrollPane2.setViewportView(txtDescricao);

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

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel9.setText("Revisão:");
        pnlGeral.add(jLabel9);
        jLabel9.setBounds(105, 260, 50, 14);

        lblRevisao.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblRevisao.setText("0000000001");
        lblRevisao.setToolTipText("");
        pnlGeral.add(lblRevisao);
        lblRevisao.setBounds(160, 260, 70, 14);

        tablManutencao.addTab("Geral", pnlGeral);

        pnlTurnosProfissional1.setBorder(javax.swing.BorderFactory.createTitledBorder("Sintomas Apresentados"));
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

        javax.swing.GroupLayout pnlDetalheLayout = new javax.swing.GroupLayout(pnlDetalhe);
        pnlDetalhe.setLayout(pnlDetalheLayout);
        pnlDetalheLayout.setHorizontalGroup(
            pnlDetalheLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 705, Short.MAX_VALUE)
            .addGroup(pnlDetalheLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pnlDetalheLayout.createSequentialGroup()
                    .addComponent(pnlTurnosProfissional1, javax.swing.GroupLayout.PREFERRED_SIZE, 701, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 4, Short.MAX_VALUE)))
        );
        pnlDetalheLayout.setVerticalGroup(
            pnlDetalheLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 282, Short.MAX_VALUE)
            .addGroup(pnlDetalheLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(pnlTurnosProfissional1, javax.swing.GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE))
        );

        tablManutencao.addTab("Sintomas Apresentados", pnlDetalhe);

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

        javax.swing.GroupLayout pnlDetalhe1Layout = new javax.swing.GroupLayout(pnlDetalhe1);
        pnlDetalhe1.setLayout(pnlDetalhe1Layout);
        pnlDetalhe1Layout.setHorizontalGroup(
            pnlDetalhe1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 705, Short.MAX_VALUE)
            .addGroup(pnlDetalhe1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pnlDetalhe1Layout.createSequentialGroup()
                    .addComponent(pnlTurnosProfissional2, javax.swing.GroupLayout.PREFERRED_SIZE, 701, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 4, Short.MAX_VALUE)))
        );
        pnlDetalhe1Layout.setVerticalGroup(
            pnlDetalhe1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 282, Short.MAX_VALUE)
            .addGroup(pnlDetalhe1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(pnlTurnosProfissional2, javax.swing.GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE))
        );

        tablManutencao.addTab("Medicamentos", pnlDetalhe1);

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

        Util.salvar(glossarioDoenca, this, true);
    }//GEN-LAST:event_btnConfirmarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        Util.cancelar(this);
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnAjudarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAjudarActionPerformed
        Util.ajudar();
    }//GEN-LAST:event_btnAjudarActionPerformed

    private void lblLocalizarProfissionalRegistroMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblLocalizarProfissionalRegistroMouseReleased
        boolean[] acoes = Util.getAcoesUsuario(Principal.PROFISSIONAIS);

        if (!acoes[Util.CONSULTAR]) {
            JOptionPane.showMessageDialog(this.getParent(),
                    "Você não pode efetuar esta consulta.\n\nMotivo: "
                            + "Usuário sem permissão.");
        } else {        
            try {
                IFrmPesqProfissionais iFrmPesqProfissionais = 
                        new IFrmPesqProfissionais(new IFrmManProfissional(
                                new Profissionais()), 
                        txtCodProfissionalRegistro, txtNomeProfissionalRegistro);

                this.getParent().remove(iFrmPesqProfissionais);
                this.getParent().add(iFrmPesqProfissionais);

                Util.centralizar(this.getParent(), iFrmPesqProfissionais);

                iFrmPesqProfissionais.setVisible(true);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this.getParent(),
                        "Profissional não pode ser consultado.\n\nMotivo: " + e);
            }
        }
    }//GEN-LAST:event_lblLocalizarProfissionalRegistroMouseReleased

    private void txtCodProfissionalRegistroFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodProfissionalRegistroFocusLost
        pesquisarCodigoDescicaoProfissional(new Profissionais());
    }//GEN-LAST:event_txtCodProfissionalRegistroFocusLost

    private void txtDataDeRegistroFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDataDeRegistroFocusGained
        txtDataDeRegistro.selectAll();
    }//GEN-LAST:event_txtDataDeRegistroFocusGained

    private void txtDiasTratamentoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDiasTratamentoFocusGained
        txtDiasTratamento.selectAll();
    }//GEN-LAST:event_txtDiasTratamentoFocusGained

    private void txtCID10FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCID10FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCID10FocusGained

    private void tbSintomasApresentadosMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbSintomasApresentadosMouseReleased
        if (evt.getClickCount() == 2) {
            boolean[] acoes = Util.getAcoesUsuario(Principal.GLOSSARIO_DOENCAS_SINTOMAS_APRESENTADOS);

            if (!acoes[Util.ALTERAR]) {
                JOptionPane.showMessageDialog(this.getParent(),
                    "Você não pode efetuar esta alteração.\n\nMotivo: "
                    + "Usuário sem permissão.");
            } else {
                try {
                    reescreverObjetoSintomasApresentados();

                    IFrmManGlossarioDoencaSintomaApresentado iFrmManGlossarioDoencaSintomaApresentado =
                    new IFrmManGlossarioDoencaSintomaApresentado(glossarioDoencaSintomaApresentado);

                    this.getParent().remove(iFrmManGlossarioDoencaSintomaApresentado);
                    this.getParent().add(iFrmManGlossarioDoencaSintomaApresentado);

                    Util.centralizar(this.getParent(), iFrmManGlossarioDoencaSintomaApresentado);

                    iFrmManGlossarioDoencaSintomaApresentado.setVisible(true);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this.getParent(),
                        "Registro nao pode ser alterado.\n\nMotivo: " + ex);
                }
            }
        }
    }//GEN-LAST:event_tbSintomasApresentadosMouseReleased

    private void btnIncluirSintomaApresentadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIncluirSintomaApresentadoActionPerformed
        boolean[] acoes = Util.getAcoesUsuario(Principal.GLOSSARIO_DOENCAS_SINTOMAS_APRESENTADOS);

        if (!acoes[Util.INCLUIR]) {
            JOptionPane.showMessageDialog(this.getParent(),
                "Você não pode efetuar esta inclusão.\n\nMotivo: "
                + "Usuário sem permissão.");
        } else {
            try {
                GlossarioDoencasSintomasApresentados glDoencaSintomaApresentado =
                    new GlossarioDoencasSintomasApresentados();
                glDoencaSintomaApresentado.setCodGlossarioDoenca(
                    glDoencaSintomaApresentado.getIdGlossarioDoencaSintomaApresentado());
                IFrmManGlossarioDoencaSintomaApresentado iFrmManGlossarioDoencaSintomaApresentado =
                    new IFrmManGlossarioDoencaSintomaApresentado(glDoencaSintomaApresentado);

                this.getParent().remove(iFrmManGlossarioDoencaSintomaApresentado);
                this.getParent().add(iFrmManGlossarioDoencaSintomaApresentado);

                Util.centralizar(this.getParent(), iFrmManGlossarioDoencaSintomaApresentado);

                iFrmManGlossarioDoencaSintomaApresentado.setVisible(true);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this.getParent(),
                    "Sintoma não pode ser incluído.\n\nMotivo: " + ex);
            }
        }
    }//GEN-LAST:event_btnIncluirSintomaApresentadoActionPerformed

    private void btnExcluirSintomaApresentadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirSintomaApresentadoActionPerformed
        boolean[] acoes = Util.getAcoesUsuario(Principal.GLOSSARIO_DOENCAS_SINTOMAS_APRESENTADOS);

        if (!acoes[Util.EXCLUIR]) {
            JOptionPane.showMessageDialog(this.getParent(),
                "Você não pode efetuar esta exclusão.\n\nMotivo: "
                + "Usuário sem permissão.");
        } else {
            Util.excluir(glossarioDoencaSintomaApresentado, tbSintomasApresentados, this);
        }
    }//GEN-LAST:event_btnExcluirSintomaApresentadoActionPerformed

    private void btnAtualizarSintomasApresentadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtualizarSintomasApresentadosActionPerformed
        Util.atualizar(glossarioDoencaSintomaApresentado, tbSintomasApresentados);
    }//GEN-LAST:event_btnAtualizarSintomasApresentadosActionPerformed

    private void tbMedicamentosMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbMedicamentosMouseReleased
        if (evt.getClickCount() == 2) {
            boolean[] acoes = Util.getAcoesUsuario(Principal.GLOSSARIO_DOENCA_MEDICAMENTOS);

            if (!acoes[Util.ALTERAR]) {
                JOptionPane.showMessageDialog(this.getParent(),
                    "Você não pode efetuar esta alteração.\n\nMotivo: "
                    + "Usuário sem permissão.");
            } else {
                try {
                    reescreverObjetoMedicamentos();

                    IFrmManGlossarioDoencaMedicamento iFrmManGlossarioDoencaMedicamento =
                    new IFrmManGlossarioDoencaMedicamento(glossarioDoencaMedicamento);

                    this.getParent().remove(iFrmManGlossarioDoencaMedicamento);
                    this.getParent().add(iFrmManGlossarioDoencaMedicamento);

                    Util.centralizar(this.getParent(), iFrmManGlossarioDoencaMedicamento);

                    iFrmManGlossarioDoencaMedicamento.setVisible(true);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this.getParent(),
                        "Registro nao pode ser alterado.\n\nMotivo: " + ex);
                }
            }
        }
    }//GEN-LAST:event_tbMedicamentosMouseReleased

    private void btnIncluirMedicamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIncluirMedicamentoActionPerformed
        boolean[] acoes = Util.getAcoesUsuario(Principal.GLOSSARIO_DOENCA_MEDICAMENTOS);

        if (!acoes[Util.INCLUIR]) {
            JOptionPane.showMessageDialog(this.getParent(),
                "Você não pode efetuar esta inclusão.\n\nMotivo: "
                + "Usuário sem permissão.");
        } else {
            try {
                GlossarioDoencasMedicamentos glDoencaMedicamento =
                new GlossarioDoencasMedicamentos();
                glDoencaMedicamento.setCodGlossarioDoenca(
                    glDoencaMedicamento.getIdGlossarioDoencasMedicamento());
                IFrmManGlossarioDoencaMedicamento iFrmManGlossarioDoencaMedicamento =
                new IFrmManGlossarioDoencaMedicamento(glDoencaMedicamento);

                this.getParent().remove(iFrmManGlossarioDoencaMedicamento);
                this.getParent().add(iFrmManGlossarioDoencaMedicamento);

                Util.centralizar(this.getParent(), iFrmManGlossarioDoencaMedicamento);

                iFrmManGlossarioDoencaMedicamento.setVisible(true);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this.getParent(),
                    "Medicamento não pode ser incluído.\n\nMotivo: " + ex);
            }
        }
    }//GEN-LAST:event_btnIncluirMedicamentoActionPerformed

    private void btnExcluirMedicamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirMedicamentoActionPerformed
        boolean[] acoes = Util.getAcoesUsuario(Principal.GLOSSARIO_DOENCA_MEDICAMENTOS);

        if (!acoes[Util.EXCLUIR]) {
            JOptionPane.showMessageDialog(this.getParent(),
                "Você não pode efetuar esta exclusão.\n\nMotivo: "
                + "Usuário sem permissão.");
        } else {
            Util.excluir(glossarioDoencaMedicamento, tbMedicamentos, this);
        }
    }//GEN-LAST:event_btnExcluirMedicamentoActionPerformed

    private void btnAtualizarMedicamentosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtualizarMedicamentosActionPerformed
        Util.atualizar(glossarioDoencaMedicamento, tbMedicamentos);
    }//GEN-LAST:event_btnAtualizarMedicamentosActionPerformed

    private void tablManutencaoStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_tablManutencaoStateChanged
        if (tablManutencao.getSelectedIndex() > 0) {
            try {
                reescreverObjeto();
                Util.salvar(glossarioDoenca, this, false);
                
                // Sintomas Apresentados
                glossarioDoencaSintomaApresentado = 
                        new GlossarioDoencasSintomasApresentados();
                glossarioDoencaSintomaApresentado.setCodGlossarioDoenca(
                        Long.parseUnsignedLong(txtCodigo.getText()));
                glossarioDoencaSintomaApresentado.adicionarWhere(new DadosDAO(
                        "cod_glossario_doenca", "", txtCodigo.getText(), 
                        DadosDAO.TIPO_LONG, DadosDAO.IS_IGUAL, DadosDAO.NAO_CHAVE));
                glossarioDoencaSintomaApresentado.getGlossarioDoencaSintomaApresentadoDAO().popularGridJTable(
                        tbSintomasApresentados);
                
                // Medicamentos
                glossarioDoencaMedicamento = new GlossarioDoencasMedicamentos();
                glossarioDoencaMedicamento.setCodGlossarioDoenca(
                        Long.parseUnsignedLong(txtCodigo.getText()));
                glossarioDoencaMedicamento.adicionarWhere(new DadosDAO(
                        "cod_glossario_doenca", "", txtCodigo.getText(), 
                        DadosDAO.TIPO_LONG, DadosDAO.IS_IGUAL, DadosDAO.NAO_CHAVE));
                glossarioDoencaMedicamento.getGlossarioDoencaMedicamentoDAO().popularGridJTable(
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
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JLabel lblLocalizarProfissionalRegistro;
    private javax.swing.JLabel lblRevisao;
    private javax.swing.JMenu mnuAjuda;
    private javax.swing.JMenu mnuArquivos;
    private javax.swing.JMenuBar mnuBarPrincipal;
    private javax.swing.JPanel pnlBotoes;
    private javax.swing.JPanel pnlDetalhe;
    private javax.swing.JPanel pnlDetalhe1;
    private javax.swing.JPanel pnlGeral;
    private javax.swing.JPanel pnlTurnosProfissional1;
    private javax.swing.JPanel pnlTurnosProfissional2;
    private javax.swing.JTabbedPane tablManutencao;
    private javax.swing.JTable tbMedicamentos;
    private javax.swing.JTable tbSintomasApresentados;
    private javax.swing.JTextField txtCID10;
    private javax.swing.JTextField txtCodProfissionalRegistro;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JFormattedTextField txtDataDeRegistro;
    private javax.swing.JTextArea txtDescricao;
    private javax.swing.JTextField txtDiasTratamento;
    private javax.swing.JTextField txtNomeProfissionalRegistro;
    private javax.swing.JTextArea txtObservacoes;
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

    /**
     * @return the glossarioDoencaMedicamento
     */
    public GlossarioDoencasMedicamentos getGlossarioDoencaMedicamento() {
        return glossarioDoencaMedicamento;
    }

    /**
     * @param glossarioDoencaMedicamento the glossarioDoencaMedicamento to set
     */
    public void setGlossarioDoencaMedicamento(GlossarioDoencasMedicamentos glossarioDoencaMedicamento) {
        this.glossarioDoencaMedicamento = glossarioDoencaMedicamento;
    }
}
