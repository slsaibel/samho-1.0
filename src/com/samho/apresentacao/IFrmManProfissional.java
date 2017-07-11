package com.samho.apresentacao;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import com.samho.dao.DadosDAO;
import com.samho.dao.UsuariosDAO;
import com.samho.negocio.Pessoas;
import com.samho.negocio.Principal;
import com.samho.negocio.Profissionais;
import com.samho.negocio.ProfissionaisPlanosSaude;
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
public final class IFrmManProfissional extends JInternalFrame {

    // Declaração de atributos
    private Profissionais profissional;
    private ProfissionaisTurnos profissionalTurnos;
    private ProfissionaisPlanosSaude profissionalPlanoSaude;
    

    // Construtor
    public IFrmManProfissional(Profissionais profissional) throws Exception {
        initComponents();

        Util.limparCampos(rootPane);

        this.profissional = profissional;

        // Permite que somente números sejam informados neste campo
        txtCodigo.setDocument(new JTextFieldDocument(true));
        txtCodPessoa.setDocument(new JTextFieldDocument(true));
        
        if (Util.getAcoesUsuario(Principal.PROFISSIONAIS_TURNOS)[Util.CONSULTAR] == false) {
            tablManutencao.remove(pnlDetalhe);
        }
        
        if (Util.getAcoesUsuario(Principal.PROFISSIONAIS_PLANOS_SAUDE)[Util.CONSULTAR] == false) {
            tablManutencao.remove(pnlDetalhe1);
        }

        carregarObjetoIFrmManutencao(profissional);
    }

    public void carregarObjetoIFrmManutencao(Profissionais profissional) {
        try {
            if (profissional.getIdProfissional()!= -1) {
                txtCodigo.setText(String.valueOf(
                        profissional.getIdProfissional()));
            } else {
                txtCodigo.setText(String.valueOf(
                        profissional.getProfissionalDAO().getProximoID()));
            }

            txtCodPessoa.setText(String.valueOf(profissional.getCodPessoa()));
            pesquisarCodigoDescicaoPessoa(new Pessoas());
            chkAtivo.setSelected(profissional.isAtivo());
            
            // Carregando o objeto de profissionais por turno
            profissionalTurnos = new ProfissionaisTurnos();
            profissionalTurnos.setCodProfissional(txtCodigo.getText().equals("") 
                    ? -1 : Long.parseUnsignedLong(txtCodigo.getText()));
            profissionalTurnos.adicionarWhere(new DadosDAO("cod_profissional", "",
                    txtCodigo.getText(), DadosDAO.TIPO_LONG, DadosDAO.IS_IGUAL, 
                    DadosDAO.NAO_CHAVE));
            profissionalTurnos.getProfissionalTurnosDAO().popularGridJTable(
                    tbTurnosProfissional);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                    "Registro nao pode ser carregado.\n\nMotivo: " + ex);
        }
    }

    private void reescreverObjeto() {
        try {
            profissional.setIdProfissional(Long.parseUnsignedLong(
                    txtCodigo.getText()));
            profissional.setCodPessoa(Long.parseUnsignedLong(
                    txtCodPessoa.getText()));
            profissional.setAtivo(chkAtivo.isSelected());

            profissional.adicionarCampos();
            
            // Atualiza as ações permitidas ao usuario
            UsuariosDAO.verificaAcoesUsuario();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                    "Registro nao pode ser reescrito.\n\nMotivo: " + ex);
        }
    }
    
    private void reescreverObjetoTurnosProfissionais() {
        try {
            profissionalTurnos.setIdProfissionalTurnos(Long.parseUnsignedLong(
                    tbTurnosProfissional.getValueAt(
                            tbTurnosProfissional.getSelectedRow(), 0).toString()));
            profissionalTurnos.setCodProfissional(Long.parseUnsignedLong(
                    tbTurnosProfissional.getValueAt(
                            tbTurnosProfissional.getSelectedRow(), 1).toString()));
            // Reserva-se a próxima posição para a descrição do campo acima.
            profissionalTurnos.setDiaSemana(Integer.parseUnsignedInt(
                    tbTurnosProfissional.getValueAt(
                            tbTurnosProfissional.getSelectedRow(), 3).toString()));
            // Seta como falso por padrão
            profissionalTurnos.setMadrugada(false);
            profissionalTurnos.setManha(false);
            profissionalTurnos.setTarde(false);
            profissionalTurnos.setNoite(false);
            profissionalTurnos.setAtendeFeriado(false);
            profissionalTurnos.setAtendeExtra(false);

            profissionalTurnos.adicionarCampos();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                    "Registro nao pode ser reescrito.\n\nMotivo: " + ex);
        }
    }
    
    private void reescreverObjetoPlanosSaudeProfissional() {
        try {
            profissionalPlanoSaude.setIdProfissionalPlanoSaude(
                    Long.parseUnsignedLong(tbPlanosSaudeProfissional.getValueAt(
                            tbPlanosSaudeProfissional.getSelectedRow(), 0).toString()));
            profissionalPlanoSaude.setCodProfissional(Long.parseUnsignedLong(
                    tbPlanosSaudeProfissional.getValueAt(
                            tbPlanosSaudeProfissional.getSelectedRow(), 1).toString()));
            // Reserva-se a próxima posição para a descrição do campo acima.
            profissionalPlanoSaude.setCodPlanoSaude(Long.parseUnsignedLong(
                    tbPlanosSaudeProfissional.getValueAt(
                            tbPlanosSaudeProfissional.getSelectedRow(), 3).toString()));
            // Reserva-se a próxima posição para a descrição do campo acima.
            profissionalPlanoSaude.setDataAdesao(
                    Formatacao.ajustaData(tbPlanosSaudeProfissional.getValueAt(
                            tbPlanosSaudeProfissional.getSelectedRow(), 5).toString(),
                            Formatacao.DATA_AMD));
            profissionalPlanoSaude.setAtivo(false);

            profissionalPlanoSaude.adicionarCampos();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                    "Registro nao pode ser instanciado.\n\nMotivo: " + ex);
        }
    }

    public void pesquisarCodigoDescicaoPessoa(Pessoas pessoa) {
        Util.pesquisarCodigoDescicao(pessoa, txtCodPessoa, txtNomePessoa);
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
        jLabel7 = new javax.swing.JLabel();
        chkAtivo = new javax.swing.JCheckBox();
        pnlDetalhe = new javax.swing.JPanel();
        pnlTurnosProfissional = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tbTurnosProfissional = new javax.swing.JTable();
        btnIncluirTurnoProfissional = new javax.swing.JButton();
        btnExcluirTurnoProfissional = new javax.swing.JButton();
        btnAtualizarTurnosProfissional = new javax.swing.JButton();
        pnlDetalhe1 = new javax.swing.JPanel();
        pnlTurnosProfissional1 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tbPlanosSaudeProfissional = new javax.swing.JTable();
        btnIncluirPlanoSaudeProfissional = new javax.swing.JButton();
        btnExcluirPlanoSaudeProfissional = new javax.swing.JButton();
        btnAtualizarPlanosSaudeProfissional = new javax.swing.JButton();
        pnlBotoes = new javax.swing.JPanel();
        btnConfirmar = new javax.swing.JButton();
        btnAjudar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        mnuBarPrincipal = new javax.swing.JMenuBar();
        mnuArquivos = new javax.swing.JMenu();
        mnuAjuda = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cadastro de Profissional");
        setMaximumSize(new java.awt.Dimension(730, 338));
        setMinimumSize(new java.awt.Dimension(730, 338));
        setName(""); // NOI18N
        setPreferredSize(new java.awt.Dimension(730, 338));
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
        jLabel2.setText("Cód. Profissional");
        pnlGeral.add(jLabel2);
        jLabel2.setBounds(52, 14, 92, 14);

        txtCodigo.setEditable(false);
        txtCodigo.setBackground(java.awt.SystemColor.info);
        txtCodigo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        pnlGeral.add(txtCodigo);
        txtCodigo.setBounds(148, 11, 70, 20);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setText("Cód. Pessoa");
        pnlGeral.add(jLabel1);
        jLabel1.setBounds(77, 40, 67, 14);

        txtCodPessoa.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCodPessoa.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodPessoaFocusLost(evt);
            }
        });
        pnlGeral.add(txtCodPessoa);
        txtCodPessoa.setBounds(148, 37, 70, 20);

        lblLocalizarPessoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/localizar.png"))); // NOI18N
        lblLocalizarPessoa.setToolTipText("Localizar uma pessoa já cadastrada.");
        lblLocalizarPessoa.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblLocalizarPessoa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                lblLocalizarPessoaMouseReleased(evt);
            }
        });
        pnlGeral.add(lblLocalizarPessoa);
        lblLocalizarPessoa.setBounds(222, 37, 20, 20);

        txtNomePessoa.setEditable(false);
        txtNomePessoa.setBackground(java.awt.SystemColor.info);
        txtNomePessoa.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        pnlGeral.add(txtNomePessoa);
        txtNomePessoa.setBounds(246, 37, 402, 20);

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel7.setText("Ativo");
        pnlGeral.add(jLabel7);
        jLabel7.setBounds(110, 65, 30, 14);

        chkAtivo.setToolTipText("");
        pnlGeral.add(chkAtivo);
        chkAtivo.setBounds(145, 62, 21, 21);

        tablManutencao.addTab("Geral", pnlGeral);

        pnlDetalhe.setLayout(null);

        pnlTurnosProfissional.setBorder(javax.swing.BorderFactory.createTitledBorder("Turnos"));
        pnlTurnosProfissional.setLayout(null);

        tbTurnosProfissional.setModel(new javax.swing.table.DefaultTableModel(
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
        tbTurnosProfissional.setName(""); // NOI18N
        tbTurnosProfissional.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tbTurnosProfissionalMouseReleased(evt);
            }
        });
        jScrollPane4.setViewportView(tbTurnosProfissional);

        pnlTurnosProfissional.add(jScrollPane4);
        jScrollPane4.setBounds(6, 16, 601, 186);

        btnIncluirTurnoProfissional.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/incluir.png"))); // NOI18N
        btnIncluirTurnoProfissional.setText("Incluir");
        btnIncluirTurnoProfissional.setToolTipText("Incluir um endereços para a pessoa.");
        btnIncluirTurnoProfissional.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnIncluirTurnoProfissional.setMargin(new java.awt.Insets(2, 5, 2, 5));
        btnIncluirTurnoProfissional.setMaximumSize(new java.awt.Dimension(69, 29));
        btnIncluirTurnoProfissional.setMinimumSize(null);
        btnIncluirTurnoProfissional.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIncluirTurnoProfissionalActionPerformed(evt);
            }
        });
        pnlTurnosProfissional.add(btnIncluirTurnoProfissional);
        btnIncluirTurnoProfissional.setBounds(613, 16, 81, 29);

        btnExcluirTurnoProfissional.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/excluir.png"))); // NOI18N
        btnExcluirTurnoProfissional.setText("Excluir");
        btnExcluirTurnoProfissional.setToolTipText("Excluir um  endereço para a pessoa.");
        btnExcluirTurnoProfissional.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnExcluirTurnoProfissional.setMargin(new java.awt.Insets(2, 5, 2, 5));
        btnExcluirTurnoProfissional.setMinimumSize(null);
        btnExcluirTurnoProfissional.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirTurnoProfissionalActionPerformed(evt);
            }
        });
        pnlTurnosProfissional.add(btnExcluirTurnoProfissional);
        btnExcluirTurnoProfissional.setBounds(613, 51, 81, 29);

        btnAtualizarTurnosProfissional.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/atualizar.png"))); // NOI18N
        btnAtualizarTurnosProfissional.setText("Atualizar");
        btnAtualizarTurnosProfissional.setToolTipText("Atualizar lista de endereços da pessoa.");
        btnAtualizarTurnosProfissional.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAtualizarTurnosProfissional.setMargin(new java.awt.Insets(2, 5, 2, 5));
        btnAtualizarTurnosProfissional.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtualizarTurnosProfissionalActionPerformed(evt);
            }
        });
        pnlTurnosProfissional.add(btnAtualizarTurnosProfissional);
        btnAtualizarTurnosProfissional.setBounds(613, 86, 81, 29);

        pnlDetalhe.add(pnlTurnosProfissional);
        pnlTurnosProfissional.setBounds(-2, 2, 710, 220);
        pnlTurnosProfissional.getAccessibleContext().setAccessibleName("");

        tablManutencao.addTab("Turnos", pnlDetalhe);

        pnlTurnosProfissional1.setBorder(javax.swing.BorderFactory.createTitledBorder("Planos"));
        pnlTurnosProfissional1.setLayout(null);

        tbPlanosSaudeProfissional.setModel(new javax.swing.table.DefaultTableModel(
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
        tbPlanosSaudeProfissional.setName(""); // NOI18N
        tbPlanosSaudeProfissional.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tbPlanosSaudeProfissionalMouseReleased(evt);
            }
        });
        jScrollPane5.setViewportView(tbPlanosSaudeProfissional);

        pnlTurnosProfissional1.add(jScrollPane5);
        jScrollPane5.setBounds(6, 16, 601, 186);

        btnIncluirPlanoSaudeProfissional.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/incluir.png"))); // NOI18N
        btnIncluirPlanoSaudeProfissional.setText("Incluir");
        btnIncluirPlanoSaudeProfissional.setToolTipText("Incluir um endereços para a pessoa.");
        btnIncluirPlanoSaudeProfissional.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnIncluirPlanoSaudeProfissional.setMargin(new java.awt.Insets(2, 5, 2, 5));
        btnIncluirPlanoSaudeProfissional.setMaximumSize(new java.awt.Dimension(69, 29));
        btnIncluirPlanoSaudeProfissional.setMinimumSize(null);
        btnIncluirPlanoSaudeProfissional.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIncluirPlanoSaudeProfissionalActionPerformed(evt);
            }
        });
        pnlTurnosProfissional1.add(btnIncluirPlanoSaudeProfissional);
        btnIncluirPlanoSaudeProfissional.setBounds(613, 16, 81, 29);

        btnExcluirPlanoSaudeProfissional.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/excluir.png"))); // NOI18N
        btnExcluirPlanoSaudeProfissional.setText("Excluir");
        btnExcluirPlanoSaudeProfissional.setToolTipText("Excluir um  endereço para a pessoa.");
        btnExcluirPlanoSaudeProfissional.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnExcluirPlanoSaudeProfissional.setMargin(new java.awt.Insets(2, 5, 2, 5));
        btnExcluirPlanoSaudeProfissional.setMinimumSize(null);
        btnExcluirPlanoSaudeProfissional.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirPlanoSaudeProfissionalActionPerformed(evt);
            }
        });
        pnlTurnosProfissional1.add(btnExcluirPlanoSaudeProfissional);
        btnExcluirPlanoSaudeProfissional.setBounds(613, 51, 81, 29);

        btnAtualizarPlanosSaudeProfissional.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/atualizar.png"))); // NOI18N
        btnAtualizarPlanosSaudeProfissional.setText("Atualizar");
        btnAtualizarPlanosSaudeProfissional.setToolTipText("Atualizar lista de endereços da pessoa.");
        btnAtualizarPlanosSaudeProfissional.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAtualizarPlanosSaudeProfissional.setMargin(new java.awt.Insets(2, 5, 2, 5));
        btnAtualizarPlanosSaudeProfissional.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtualizarPlanosSaudeProfissionalActionPerformed(evt);
            }
        });
        pnlTurnosProfissional1.add(btnAtualizarPlanosSaudeProfissional);
        btnAtualizarPlanosSaudeProfissional.setBounds(613, 86, 81, 29);

        javax.swing.GroupLayout pnlDetalhe1Layout = new javax.swing.GroupLayout(pnlDetalhe1);
        pnlDetalhe1.setLayout(pnlDetalhe1Layout);
        pnlDetalhe1Layout.setHorizontalGroup(
            pnlDetalhe1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlDetalhe1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(pnlTurnosProfissional1, javax.swing.GroupLayout.PREFERRED_SIZE, 710, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        pnlDetalhe1Layout.setVerticalGroup(
            pnlDetalhe1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlDetalhe1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(pnlTurnosProfissional1, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pnlTurnosProfissional1.getAccessibleContext().setAccessibleName("");

        tablManutencao.addTab("Planos de Saúde", pnlDetalhe1);

        getContentPane().add(tablManutencao);
        tablManutencao.setBounds(0, 0, 713, 245);
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
        pnlBotoes.setBounds(0, 250, 713, 38);

        mnuArquivos.setText("Arquivo");
        mnuBarPrincipal.add(mnuArquivos);

        mnuAjuda.setText("Ajuda");
        mnuBarPrincipal.add(mnuAjuda);

        setJMenuBar(mnuBarPrincipal);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirmarActionPerformed
        reescreverObjeto();

        Util.salvar(profissional, this, true);
    }//GEN-LAST:event_btnConfirmarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        Util.cancelar(this);
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnAjudarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAjudarActionPerformed
        Util.ajudar();
    }//GEN-LAST:event_btnAjudarActionPerformed

    private void txtCodPessoaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodPessoaFocusLost
        pesquisarCodigoDescicaoPessoa(new Pessoas());
    }//GEN-LAST:event_txtCodPessoaFocusLost

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
                    "Pessoa não pode ser consultada.\n\nMotivo: " + e);
            }
        }
    }//GEN-LAST:event_lblLocalizarPessoaMouseReleased

    private void tbTurnosProfissionalMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbTurnosProfissionalMouseReleased
        if (evt.getClickCount() == 2) {
            boolean[] acoes = Util.getAcoesUsuario(Principal.PROFISSIONAIS_TURNOS);

            if (!acoes[Util.ALTERAR]) {
                JOptionPane.showMessageDialog(this.getParent(),
                        "Você não pode efetuar esta alteração.\n\nMotivo: "
                                + "Usuário sem permissão.");
            } else { 
                try {
                    reescreverObjetoTurnosProfissionais();

                    IFrmManProfissionalTurnos iFrmManProfissionalTurnos = 
                            new IFrmManProfissionalTurnos(profissionalTurnos);

                    this.getParent().remove(iFrmManProfissionalTurnos);
                    this.getParent().add(iFrmManProfissionalTurnos);

                    Util.centralizar(this.getParent(), iFrmManProfissionalTurnos);

                    iFrmManProfissionalTurnos.setVisible(true);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this.getParent(),
                        "Registro nao pode ser alterado.\n\nMotivo: " + ex);
                }
            }
        }
    }//GEN-LAST:event_tbTurnosProfissionalMouseReleased

    private void btnExcluirTurnoProfissionalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirTurnoProfissionalActionPerformed
        boolean[] acoes = Util.getAcoesUsuario(Principal.PROFISSIONAIS_TURNOS);

        if (!acoes[Util.EXCLUIR]) {
            JOptionPane.showMessageDialog(this.getParent(),
                    "Você não pode efetuar esta exclusão.\n\nMotivo: "
                            + "Usuário sem permissão.");
        } else { 
            Util.excluir(profissionalTurnos, tbTurnosProfissional, this);
        }
    }//GEN-LAST:event_btnExcluirTurnoProfissionalActionPerformed

    private void btnIncluirTurnoProfissionalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIncluirTurnoProfissionalActionPerformed
        boolean[] acoes = Util.getAcoesUsuario(Principal.PROFISSIONAIS_TURNOS);

        if (!acoes[Util.INCLUIR]) {
            JOptionPane.showMessageDialog(this.getParent(),
                    "Você não pode efetuar esta inclusão.\n\nMotivo: "
                            + "Usuário sem permissão.");
        } else { 
            try {
                ProfissionaisTurnos profTurno = new ProfissionaisTurnos();
                profTurno.setCodProfissional(
                        profissional.getIdProfissional());
                IFrmManProfissionalTurnos iFrmManProfissionalTurnos = 
                        new IFrmManProfissionalTurnos(profTurno);

                this.getParent().remove(iFrmManProfissionalTurnos);
                this.getParent().add(iFrmManProfissionalTurnos);

                Util.centralizar(this.getParent(), iFrmManProfissionalTurnos);

                iFrmManProfissionalTurnos.setVisible(true);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this.getParent(),
                    "Turno não pode ser incluído.\n\nMotivo: " + ex);
            }
        }
    }//GEN-LAST:event_btnIncluirTurnoProfissionalActionPerformed

    private void btnAtualizarTurnosProfissionalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtualizarTurnosProfissionalActionPerformed
        Util.atualizar(profissionalTurnos, tbTurnosProfissional);
    }//GEN-LAST:event_btnAtualizarTurnosProfissionalActionPerformed

    private void tablManutencaoStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_tablManutencaoStateChanged
        if (tablManutencao.getSelectedIndex() > 0) {
            try {
                reescreverObjeto();
                Util.salvar(profissional, this, false);
                
                // Turnos
                profissionalTurnos = new ProfissionaisTurnos();
                profissionalTurnos.setCodProfissional(Long.parseUnsignedLong(
                        txtCodigo.getText()));
                profissionalTurnos.adicionarWhere(new DadosDAO(
                        "cod_profissional", "", txtCodigo.getText(), 
                        DadosDAO.TIPO_LONG, DadosDAO.IS_IGUAL, DadosDAO.NAO_CHAVE));
                profissionalTurnos.getProfissionalTurnosDAO().popularGridJTable(
                        tbTurnosProfissional);
                
                // Planos de Saúde
                profissionalPlanoSaude = new ProfissionaisPlanosSaude();
                profissionalPlanoSaude.setCodProfissional(Long.parseUnsignedLong(
                        txtCodigo.getText()));
                profissionalPlanoSaude.adicionarWhere(new DadosDAO(
                        "cod_profissional", "", txtCodigo.getText(), 
                        DadosDAO.TIPO_LONG, DadosDAO.IS_IGUAL, DadosDAO.NAO_CHAVE));
                profissionalPlanoSaude.getProfissionalPlanoSaudeDAO().popularGridJTable(
                        tbPlanosSaudeProfissional);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this.getParent(),
                        "Registro nao pode ser carregado.\n\nMotivo: " + ex);
            }
        }
    }//GEN-LAST:event_tablManutencaoStateChanged

    private void tbPlanosSaudeProfissionalMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbPlanosSaudeProfissionalMouseReleased
        if (evt.getClickCount() == 2) {
            boolean[] acoes = Util.getAcoesUsuario(Principal.PROFISSIONAIS_PLANOS_SAUDE);

            if (!acoes[Util.ALTERAR]) {
                JOptionPane.showMessageDialog(this.getParent(),
                        "Você não pode efetuar esta alteração.\n\nMotivo: "
                                + "Usuário sem permissão.");
            } else { 
                try {
                    reescreverObjetoPlanosSaudeProfissional();

                    IFrmManProfissionalPlanoSaude iFrmManProfissionalPlanoSaude = 
                            new IFrmManProfissionalPlanoSaude(profissionalPlanoSaude);

                    this.getParent().remove(iFrmManProfissionalPlanoSaude);
                    this.getParent().add(iFrmManProfissionalPlanoSaude);

                    Util.centralizar(this.getParent(), iFrmManProfissionalPlanoSaude);

                    iFrmManProfissionalPlanoSaude.setVisible(true);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this.getParent(),
                        "Registro nao pode ser alterado.\n\nMotivo: " + ex);
                }
            }
        }
    }//GEN-LAST:event_tbPlanosSaudeProfissionalMouseReleased

    private void btnIncluirPlanoSaudeProfissionalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIncluirPlanoSaudeProfissionalActionPerformed
        boolean[] acoes = Util.getAcoesUsuario(Principal.PROFISSIONAIS_PLANOS_SAUDE);

        if (!acoes[Util.INCLUIR]) {
            JOptionPane.showMessageDialog(this.getParent(),
                    "Você não pode efetuar esta inclusão.\n\nMotivo: "
                            + "Usuário sem permissão.");
        } else { 
            try {
                ProfissionaisPlanosSaude profPlanoSaude = 
                        new ProfissionaisPlanosSaude();
                profPlanoSaude.setCodProfissional(
                        profissional.getIdProfissional());
                IFrmManProfissionalPlanoSaude iFrmManProfissionalPlanoSaude = 
                        new IFrmManProfissionalPlanoSaude(profPlanoSaude);

                this.getParent().remove(iFrmManProfissionalPlanoSaude);
                this.getParent().add(iFrmManProfissionalPlanoSaude);

                Util.centralizar(this.getParent(), iFrmManProfissionalPlanoSaude);

                iFrmManProfissionalPlanoSaude.setVisible(true);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this.getParent(),
                    "Plano de Saúde não pode ser incluído.\n\nMotivo: " + ex);
            }
        }
    }//GEN-LAST:event_btnIncluirPlanoSaudeProfissionalActionPerformed

    private void btnExcluirPlanoSaudeProfissionalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirPlanoSaudeProfissionalActionPerformed
        boolean[] acoes = Util.getAcoesUsuario(Principal.PROFISSIONAIS_PLANOS_SAUDE);

        if (!acoes[Util.EXCLUIR]) {
            JOptionPane.showMessageDialog(this.getParent(),
                    "Você não pode efetuar esta exclusão.\n\nMotivo: "
                            + "Usuário sem permissão.");
        } else { 
            Util.excluir(profissionalPlanoSaude, tbPlanosSaudeProfissional, this);
        }
    }//GEN-LAST:event_btnExcluirPlanoSaudeProfissionalActionPerformed

    private void btnAtualizarPlanosSaudeProfissionalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtualizarPlanosSaudeProfissionalActionPerformed
        Util.atualizar(profissionalPlanoSaude, tbPlanosSaudeProfissional);
    }//GEN-LAST:event_btnAtualizarPlanosSaudeProfissionalActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAjudar;
    private javax.swing.JButton btnAtualizarPlanosSaudeProfissional;
    private javax.swing.JButton btnAtualizarTurnosProfissional;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnConfirmar;
    private javax.swing.JButton btnExcluirPlanoSaudeProfissional;
    private javax.swing.JButton btnExcluirTurnoProfissional;
    private javax.swing.JButton btnIncluirPlanoSaudeProfissional;
    private javax.swing.JButton btnIncluirTurnoProfissional;
    private javax.swing.JCheckBox chkAtivo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JLabel lblLocalizarPessoa;
    private javax.swing.JMenu mnuAjuda;
    private javax.swing.JMenu mnuArquivos;
    private javax.swing.JMenuBar mnuBarPrincipal;
    private javax.swing.JPanel pnlBotoes;
    private javax.swing.JPanel pnlDetalhe;
    private javax.swing.JPanel pnlDetalhe1;
    private javax.swing.JPanel pnlGeral;
    private javax.swing.JPanel pnlTurnosProfissional;
    private javax.swing.JPanel pnlTurnosProfissional1;
    private javax.swing.JTabbedPane tablManutencao;
    private javax.swing.JTable tbPlanosSaudeProfissional;
    private javax.swing.JTable tbTurnosProfissional;
    private javax.swing.JTextField txtCodPessoa;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtNomePessoa;
    // End of variables declaration//GEN-END:variables

    /**
     * @return the profissional
     */
    public Profissionais getProfissional() {
        return profissional;
    }

    /**
     * @param profissional the profissional to set
     */
    public void setProfissional(Profissionais profissional) {
        this.profissional = profissional;
    }

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
