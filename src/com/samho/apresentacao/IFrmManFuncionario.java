/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.samho.apresentacao;

import com.samho.negocio.Funcionarios;
import com.samho.negocio.Funcoes;
import com.samho.negocio.Pessoas;
import com.samho.negocio.Principal;
import com.samho.negocio.SituacoesFuncionarios;
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
public final class IFrmManFuncionario extends JInternalFrame {

    // Declaração de atributos
    private Funcionarios funcionario;

    // Construtor
    public IFrmManFuncionario(Funcionarios funcionario) throws Exception {
        initComponents();
        
        Formatacao.reformatarData(txtDataDeAdmissao);
        Formatacao.reformatarData(txtDataDeDemissao);
        Formatacao.reformatarHora(txtCargaHoraria);
        
        Util.limparCampos(rootPane);

        this.funcionario = funcionario;

        // Permite que somente números sejam informados neste campo
        txtCodigo.setDocument(new JTextFieldDocument(true));
        txtCodFuncao.setDocument(new JTextFieldDocument(true));
        txtCodPessoa.setDocument(new JTextFieldDocument(true));
        txtNumeroCLT.setDocument(new JTextFieldDocument(true));

        carregarObjetoIFrmManutencao(funcionario);
    }

    private void setarCampoVlrReferenciaCusto() {
        if ((txtVlrSalario.getText().length() > 0)
                && (txtCargaHoraria.getText().length() > 0)) {
            double resultado = 0;
            double vlrSalCentavos, cargaHorSegundos;
            int diasTrabalho = cbDiasTrabalho.getSelectedIndex() + 1;

            /**
             * Retirando a formatação (df2 - duas casas decimais), o valor é
             * converitdo para centavos
             */
            vlrSalCentavos = Integer.valueOf(
                    Formatacao.removerFormatacao(txtVlrSalario.getText()));

            /**
             * Faz a converção do tempo para possibilitar calcular o valor real
             * de custo por segundos.
             */
            cargaHorSegundos = Util.converterHorarioEmSegundos(
                    txtCargaHoraria.getText());

            /**
             * Divide os centavos por: numero de dias, carga horária e por 100
             * (100 centavos corespondem a 1 real), isso transforma o valor
             * apresentado no campo "Referência para custos". Logo: valor em
             * segundos passa a ser valor em reais.
             */
            if (vlrSalCentavos != 0 && cargaHorSegundos != 0 && diasTrabalho != 0) {
                resultado = (((vlrSalCentavos / cargaHorSegundos)
                        / diasTrabalho) / 100.0);
            }

            txtVlrReferenciaCusto.setText(String.valueOf(resultado));
            Formatacao.formatarDecimal2(txtVlrReferenciaCusto);
        }
    }

    public void carregarObjetoIFrmManutencao(Funcionarios funcionario) {
        try {
            if (funcionario.getIdFuncionario() != -1) {
                txtCodigo.setText(String.valueOf(
                        funcionario.getIdFuncionario()));
            } else {
                txtCodigo.setText(String.valueOf(
                        funcionario.getFuncionarioDAO().getProximoID()));
            }

            txtCodPessoa.setText(String.valueOf(funcionario.getCodPessoa()));
            pesquisarCodigoDescicaoPessoa(new Pessoas());
            txtCodFuncao.setText(String.valueOf(funcionario.getCodFuncao()));
            pesquisarCodigoDescicaoFuncao(new Funcoes());
            txtVlrSalario.setText(String.valueOf(funcionario.getVlrSalario()));
            Formatacao.formatarDecimal2(txtVlrSalario);
            txtCargaHoraria.setText(String.valueOf(
                    Util.converterHorarioDoubleEmHHmmss(
                            funcionario.getCargaHoraria())));
            cbDiasTrabalho.setSelectedIndex(
                    funcionario.getDiasTrabalhadosMes() - 1);
            setarCampoVlrReferenciaCusto();
            txtNumeroCLT.setText(String.valueOf(funcionario.getNumeroCLT()));
            txtDataDeAdmissao.setText(Formatacao.ajustaDataDMA(
                    funcionario.getDataAdmissao()));
            txtDataDeDemissao.setText(Formatacao.ajustaDataDMA(
                    funcionario.getDataDemissao()));

            cbSituacao.removeAllItems();
            SituacoesFuncionarios situacoes = new SituacoesFuncionarios();
            situacoes.getSituacaoDAO().popularListaJComboBox(cbSituacao,
                    situacoes.getDadosCodigo().getCampo(),
                    situacoes.getDadosDescricao().getCampo());
            cbSituacao.setSelectedIndex((int)funcionario.getCodSituacao());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                    "Registro nao pode ser carregado.\n\nMotivo: " + ex);
        }
    }

    private void reescreverObjeto() {
        try {
            funcionario.setIdFuncionario(Long.parseUnsignedLong(
                    txtCodigo.getText()));
            funcionario.setCodPessoa(Long.parseUnsignedLong(
                    txtCodPessoa.getText()));
            funcionario.setCodFuncao(Long.parseUnsignedLong(
                    txtCodFuncao.getText()));
            funcionario.setVlrSalario(Double.valueOf(Formatacao.ajustaDouble(
                    txtVlrSalario.getText())));
            funcionario.setCargaHoraria(Util.converterHorarioHHmmssEmDouble(
                    txtCargaHoraria.getText()));
            funcionario.setDiasTrabalhadosMes(
                    cbDiasTrabalho.getSelectedIndex() + 1);
            funcionario.setNumeroCLT(Integer.valueOf(txtNumeroCLT.getText()));
            funcionario.setDataAdmissao(Formatacao.ajustaData(
                    txtDataDeAdmissao.getText(), Formatacao.DATA_DMA));

            if (Formatacao.removerFormatacao(
                    txtDataDeDemissao.getText()).equals("")) {
                funcionario.setDataDemissao(null);
            } else {
                funcionario.setDataDemissao(Formatacao.ajustaData(
                        txtDataDeDemissao.getText(), Formatacao.DATA_DMA));
            }

            funcionario.setCodSituacao(cbSituacao.getSelectedIndex());

            funcionario.adicionarCampos();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                    "Registro nao pode ser reescrito.\n\nMotivo: " + ex);
        }
    }

    public void pesquisarCodigoDescicaoPessoa(Pessoas pessoa) {
        Util.pesquisarCodigoDescicao(pessoa, txtCodPessoa, txtNomePessoa);
    }

    public void pesquisarCodigoDescicaoFuncao(Funcoes funcao) {
        Util.pesquisarCodigoDescicao(funcao, txtCodFuncao, txtDescrFuncao);
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
        txtCodFuncao = new javax.swing.JTextField();
        lblLocalizarFuncao = new javax.swing.JLabel();
        txtDescrFuncao = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtVlrSalario = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtCargaHoraria = new javax.swing.JFormattedTextField();
        cbDiasTrabalho = new javax.swing.JComboBox();
        jLabel10 = new javax.swing.JLabel();
        txtVlrReferenciaCusto = new javax.swing.JFormattedTextField();
        jLabel12 = new javax.swing.JLabel();
        txtNumeroCLT = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtDataDeAdmissao = new javax.swing.JFormattedTextField();
        jLabel11 = new javax.swing.JLabel();
        txtDataDeDemissao = new javax.swing.JFormattedTextField();
        jLabel7 = new javax.swing.JLabel();
        cbSituacao = new javax.swing.JComboBox();
        pnlBotoes = new javax.swing.JPanel();
        btnConfirmar = new javax.swing.JButton();
        btnAjudar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        mnuBarPrincipal = new javax.swing.JMenuBar();
        mnuArquivos = new javax.swing.JMenu();
        mnuAjuda = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cadastro de Funcionário");
        setMaximumSize(new java.awt.Dimension(730, 300));
        setMinimumSize(new java.awt.Dimension(730, 300));
        setName(""); // NOI18N
        setPreferredSize(new java.awt.Dimension(730, 300));
        setVerifyInputWhenFocusTarget(false);
        getContentPane().setLayout(null);

        tablManutencao.setMinimumSize(new java.awt.Dimension(713, 245));
        tablManutencao.setName(""); // NOI18N

        pnlGeral.setLayout(null);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setText("Cód. Funcionario");
        pnlGeral.add(jLabel2);
        jLabel2.setBounds(53, 14, 92, 14);

        txtCodigo.setEditable(false);
        txtCodigo.setBackground(java.awt.SystemColor.info);
        txtCodigo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        pnlGeral.add(txtCodigo);
        txtCodigo.setBounds(149, 11, 70, 20);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setText("Cód. Pessoa");
        pnlGeral.add(jLabel1);
        jLabel1.setBounds(78, 40, 67, 14);

        txtCodPessoa.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCodPessoa.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodPessoaFocusLost(evt);
            }
        });
        pnlGeral.add(txtCodPessoa);
        txtCodPessoa.setBounds(149, 37, 70, 20);

        lblLocalizarPessoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/localizar.png"))); // NOI18N
        lblLocalizarPessoa.setToolTipText("Localizar uma pessoa já cadastrada.");
        lblLocalizarPessoa.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblLocalizarPessoa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                lblLocalizarPessoaMouseReleased(evt);
            }
        });
        pnlGeral.add(lblLocalizarPessoa);
        lblLocalizarPessoa.setBounds(223, 37, 20, 20);

        txtNomePessoa.setEditable(false);
        txtNomePessoa.setBackground(java.awt.SystemColor.info);
        txtNomePessoa.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        pnlGeral.add(txtNomePessoa);
        txtNomePessoa.setBounds(247, 37, 402, 20);

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel4.setText("Cód. Função");
        pnlGeral.add(jLabel4);
        jLabel4.setBounds(78, 66, 67, 14);

        txtCodFuncao.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCodFuncao.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodFuncaoFocusLost(evt);
            }
        });
        pnlGeral.add(txtCodFuncao);
        txtCodFuncao.setBounds(149, 63, 70, 20);

        lblLocalizarFuncao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/localizar.png"))); // NOI18N
        lblLocalizarFuncao.setToolTipText("Localizar uma função já cadastrada.");
        lblLocalizarFuncao.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblLocalizarFuncao.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                lblLocalizarFuncaoMouseReleased(evt);
            }
        });
        pnlGeral.add(lblLocalizarFuncao);
        lblLocalizarFuncao.setBounds(223, 63, 20, 20);

        txtDescrFuncao.setEditable(false);
        txtDescrFuncao.setBackground(java.awt.SystemColor.info);
        txtDescrFuncao.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        pnlGeral.add(txtDescrFuncao);
        txtDescrFuncao.setBounds(247, 63, 402, 20);

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel8.setText("Vlr. Salário");
        pnlGeral.add(jLabel8);
        jLabel8.setBounds(85, 92, 60, 14);

        txtVlrSalario.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtVlrSalario.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtVlrSalarioFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtVlrSalarioFocusLost(evt);
            }
        });
        pnlGeral.add(txtVlrSalario);
        txtVlrSalario.setBounds(149, 89, 70, 20);

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel9.setText("Carga Horária/Dia");
        pnlGeral.add(jLabel9);
        jLabel9.setBounds(244, 92, 102, 14);

        txtCargaHoraria.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(java.text.DateFormat.getTimeInstance())));
        txtCargaHoraria.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCargaHoraria.setVerifyInputWhenFocusTarget(false);
        txtCargaHoraria.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCargaHorariaFocusGained(evt);
            }
        });
        pnlGeral.add(txtCargaHoraria);
        txtCargaHoraria.setBounds(350, 89, 70, 20);

        cbDiasTrabalho.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "01 dia", "02 dias", "03 dias", "04 dias", "05 dias", "06 dias", "07 dias", "08 dias", "09 dias", "10 dias", "11 dias", "12 dias", "13 dias", "14 dias", "15 dias", "16 dias", "17 dias", "18 dias", "19 dias", "20 dias", "21 dias", "22 dias", "23 dias", "24 dias", "25 dias", "26 dias", "27 dias", "28 dias", "29 dias", "30 dias", "31 dias" }));
        cbDiasTrabalho.setToolTipText("Quantidade de dias que se pretende pagar esse funcionário.");
        cbDiasTrabalho.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                cbDiasTrabalhoFocusLost(evt);
            }
        });
        cbDiasTrabalho.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbDiasTrabalhoActionPerformed(evt);
            }
        });
        pnlGeral.add(cbDiasTrabalho);
        cbDiasTrabalho.setBounds(426, 89, 59, 20);

        jLabel10.setText("Custo/Seg.");
        pnlGeral.add(jLabel10);
        jLabel10.setBounds(521, 92, 54, 14);

        txtVlrReferenciaCusto.setEditable(false);
        txtVlrReferenciaCusto.setBackground(java.awt.SystemColor.info);
        txtVlrReferenciaCusto.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.#######"))));
        txtVlrReferenciaCusto.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        pnlGeral.add(txtVlrReferenciaCusto);
        txtVlrReferenciaCusto.setBounds(579, 89, 70, 20);

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel12.setText("Número CLT");
        pnlGeral.add(jLabel12);
        jLabel12.setBounds(78, 118, 67, 14);

        txtNumeroCLT.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtNumeroCLT.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNumeroCLTFocusGained(evt);
            }
        });
        pnlGeral.add(txtNumeroCLT);
        txtNumeroCLT.setBounds(149, 115, 70, 20);

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel6.setText("Data de Admissão");
        pnlGeral.add(jLabel6);
        jLabel6.setBounds(244, 118, 102, 14);

        try {
            txtDataDeAdmissao.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtDataDeAdmissao.setVerifyInputWhenFocusTarget(false);
        txtDataDeAdmissao.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDataDeAdmissaoFocusGained(evt);
            }
        });
        pnlGeral.add(txtDataDeAdmissao);
        txtDataDeAdmissao.setBounds(350, 115, 70, 20);

        jLabel11.setText("Data de Demissão");
        pnlGeral.add(jLabel11);
        jLabel11.setBounds(489, 118, 86, 14);

        try {
            txtDataDeDemissao.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtDataDeDemissao.setVerifyInputWhenFocusTarget(false);
        txtDataDeDemissao.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDataDeDemissaoFocusGained(evt);
            }
        });
        pnlGeral.add(txtDataDeDemissao);
        txtDataDeDemissao.setBounds(579, 115, 70, 20);

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel7.setText("Situação");
        pnlGeral.add(jLabel7);
        jLabel7.setBounds(96, 144, 49, 14);

        cbSituacao.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        pnlGeral.add(cbSituacao);
        cbSituacao.setBounds(149, 141, 169, 20);

        tablManutencao.addTab("Geral", pnlGeral);

        getContentPane().add(tablManutencao);
        tablManutencao.setBounds(0, 0, 710, 205);
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
        pnlBotoes.setBounds(0, 210, 713, 38);

        mnuArquivos.setText("Arquivo");
        mnuBarPrincipal.add(mnuArquivos);

        mnuAjuda.setText("Ajuda");
        mnuBarPrincipal.add(mnuAjuda);

        setJMenuBar(mnuBarPrincipal);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirmarActionPerformed
        reescreverObjeto();

        Util.salvar(funcionario, this, true);
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
                    "Você não pode efetuar esta consultadas.\n\nMotivo: "
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
                        "Pessoa não pode ser consultada.\n\nMotivo: " + e);
            }
        }
    }//GEN-LAST:event_lblLocalizarPessoaMouseReleased

    private void lblLocalizarFuncaoMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblLocalizarFuncaoMouseReleased
        boolean[] acoes = Util.getAcoesUsuario(Principal.FUNCOES);
            
        if (!acoes[Util.CONSULTAR]) {
            JOptionPane.showMessageDialog(this.getParent(),
                    "Você não pode efetuar esta consultadas.\n\nMotivo: "
                            + "Usuário sem permissão.");
        } else {        
            try {
                IFrmPesqFuncoes iFrmPesqFuncoes
                        = new IFrmPesqFuncoes(new IFrmManFuncao(new Funcoes()),
                                txtCodFuncao, txtDescrFuncao);

                this.getParent().remove(iFrmPesqFuncoes);
                this.getParent().add(iFrmPesqFuncoes);

                Util.centralizar(this.getParent(), iFrmPesqFuncoes);

                iFrmPesqFuncoes.setVisible(true);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this.getParent(),
                        "Função não pode ser consultada.\n\nMotivo: " + e);
            }
        }
    }//GEN-LAST:event_lblLocalizarFuncaoMouseReleased

    private void txtCodPessoaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodPessoaFocusLost
        pesquisarCodigoDescicaoPessoa(new Pessoas());
    }//GEN-LAST:event_txtCodPessoaFocusLost

    private void txtCodFuncaoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodFuncaoFocusLost
        pesquisarCodigoDescicaoFuncao(new Funcoes());
    }//GEN-LAST:event_txtCodFuncaoFocusLost

    private void txtDataDeAdmissaoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDataDeAdmissaoFocusGained
        txtDataDeAdmissao.selectAll();
    }//GEN-LAST:event_txtDataDeAdmissaoFocusGained

    private void txtDataDeDemissaoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDataDeDemissaoFocusGained
        txtDataDeDemissao.selectAll();
    }//GEN-LAST:event_txtDataDeDemissaoFocusGained

    private void txtVlrSalarioFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtVlrSalarioFocusGained
        txtVlrSalario.selectAll();
    }//GEN-LAST:event_txtVlrSalarioFocusGained

    private void txtCargaHorariaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCargaHorariaFocusGained
        txtCargaHoraria.selectAll();
    }//GEN-LAST:event_txtCargaHorariaFocusGained

    private void txtNumeroCLTFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNumeroCLTFocusGained
        txtNumeroCLT.selectAll();
    }//GEN-LAST:event_txtNumeroCLTFocusGained

    private void cbDiasTrabalhoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbDiasTrabalhoActionPerformed
        setarCampoVlrReferenciaCusto();
    }//GEN-LAST:event_cbDiasTrabalhoActionPerformed

    private void cbDiasTrabalhoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cbDiasTrabalhoFocusLost
        setarCampoVlrReferenciaCusto();
    }//GEN-LAST:event_cbDiasTrabalhoFocusLost

    private void txtVlrSalarioFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtVlrSalarioFocusLost
        Formatacao.formatarDecimal2(txtVlrSalario);
    }//GEN-LAST:event_txtVlrSalarioFocusLost

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAjudar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnConfirmar;
    private javax.swing.JComboBox cbDiasTrabalho;
    private javax.swing.JComboBox cbSituacao;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel lblLocalizarFuncao;
    private javax.swing.JLabel lblLocalizarPessoa;
    private javax.swing.JMenu mnuAjuda;
    private javax.swing.JMenu mnuArquivos;
    private javax.swing.JMenuBar mnuBarPrincipal;
    private javax.swing.JPanel pnlBotoes;
    private javax.swing.JPanel pnlGeral;
    private javax.swing.JTabbedPane tablManutencao;
    private javax.swing.JFormattedTextField txtCargaHoraria;
    private javax.swing.JTextField txtCodFuncao;
    private javax.swing.JTextField txtCodPessoa;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JFormattedTextField txtDataDeAdmissao;
    private javax.swing.JFormattedTextField txtDataDeDemissao;
    private javax.swing.JTextField txtDescrFuncao;
    private javax.swing.JTextField txtNomePessoa;
    private javax.swing.JTextField txtNumeroCLT;
    private javax.swing.JFormattedTextField txtVlrReferenciaCusto;
    private javax.swing.JTextField txtVlrSalario;
    // End of variables declaration//GEN-END:variables

    /**
     * @return the funcionario
     */
    public Funcionarios getFuncionario() {
        return funcionario;
    }

    /**
     * @param funcionario the funcionario to set
     */
    public void setFuncionario(Funcionarios funcionario) {
        this.funcionario = funcionario;
    }
}
