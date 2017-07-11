/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.samho.apresentacao;

import com.samho.dao.DadosDAO;
import com.samho.negocio.Agendas;
import com.samho.negocio.Clientes;
import com.samho.negocio.Funcionarios;
import com.samho.negocio.Principal;
import com.samho.negocio.Profissionais;
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
public class IFrmPesqAgendas extends JInternalFrame {

    // Declaração de atributos
    private IFrmManAgenda iFrmManAgenda;
    private Agendas agenda;
    private JTextField jTextFieldCodigo;
    private JTextField jTextFieldDescricao;
    private ArrayList<DadosDAO> dadosWhere;

    // Construtor
    public IFrmPesqAgendas(IFrmManAgenda iFrmManAgenda) throws Exception {
        inicializar(iFrmManAgenda);
    }

    public IFrmPesqAgendas(IFrmManAgenda iFrmManAgenda,
            JTextField jTextFieldCodigo, JTextField jTextFieldDescricao)
            throws Exception {
        inicializar(iFrmManAgenda);

        this.jTextFieldCodigo = jTextFieldCodigo;
        this.jTextFieldDescricao = jTextFieldDescricao;
    }

    private void inicializar(IFrmManAgenda iFrmManAgenda) {
        initComponents();

        tbResultado.getTableHeader().setReorderingAllowed(false);
        this.iFrmManAgenda = iFrmManAgenda;
        agenda = iFrmManAgenda.getAgenda();
        dadosWhere = new ArrayList<>();
        dadosWhere.addAll(agenda.getObjetoDAO().getCondicaoWhere());

        Util.atualizar(agenda, tbResultado);
    
        verificarPermissoes();
    }
    
    /**
     * Verifica as permissões do  usuário logado
     */
      public void verificarPermissoes (){
        boolean[] acoes = Util.getAcoesUsuario(Principal.AGENDAS);

        btnIncluir.setEnabled(acoes[Util.INCLUIR]);
        btnAlterar.setEnabled(acoes[Util.ALTERAR]);
        btnExcluir.setEnabled(acoes[Util.EXCLUIR]);
        btnAtualizar.setEnabled(acoes[Util.CONSULTAR]);
        btnAplicar.setEnabled(acoes[Util.INCLUIR] || acoes[Util.ALTERAR] || 
                acoes[Util.EXCLUIR]);
    }

    private void reescreverObjeto() {
        try {
            agenda.setIdAgenda(Long.parseUnsignedLong(tbResultado.getValueAt(
                    tbResultado.getSelectedRow(), 0).toString()));
            agenda.setCodCliente(Long.parseUnsignedLong(tbResultado.getValueAt(
                    tbResultado.getSelectedRow(), 1).toString()));
            // Reserva-se a próxima posição para a descrição do campo acima.
            agenda.setCodFuncionario(Long.parseUnsignedLong(tbResultado.getValueAt(
                    tbResultado.getSelectedRow(), 3).toString()));
            // Reserva-se a próxima posição para a descrição do campo acima.
            agenda.setCodProfissional(Long.parseUnsignedLong(tbResultado.getValueAt(
                    tbResultado.getSelectedRow(), 5).toString()));
            // Reserva-se a próxima posição para a descrição do campo acima.
            agenda.setCodMotivoAgenda(Long.parseUnsignedLong(tbResultado.getValueAt(
                    tbResultado.getSelectedRow(), 7).toString()));
            // Desabilita os itens alterados por padrão
            agenda.setDataAgendamento(Formatacao.ajustaData(tbResultado.getValueAt(
                    tbResultado.getSelectedRow(), 9).toString(), Formatacao.DATA_AMD));
            agenda.setHorario(Double.parseDouble(tbResultado.getValueAt(
                    tbResultado.getSelectedRow(), 10).toString()));
            agenda.setObservacoes(tbResultado.getValueAt(
                    tbResultado.getSelectedRow(), 11).toString());
            
            agenda.adicionarCampos();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                    "Registro nao pode ser instanciado.\n\nMotivo: " + ex);
        }
    }

    private void efetuarPesquisa() {
        agenda.removerWhere();

        if (!txtCodigo.getText().equals("")) {
            Util.verificarPesquisarCampoID(agenda, txtCodigo);
        }

        if (!txtCodCliente.getText().equals("")) {
            Util.verificarPesquisarCampoDescricao(agenda, txtCodCliente);
        }
        
        if (!txtCodFuncionario.getText().equals("")) {
            Util.verificarPesquisarCampoDescricao(agenda, txtCodFuncionario);
        }
        
        if (!txtCodProfissional.getText().equals("")) {
            Util.verificarPesquisarCampoDescricao(agenda, txtCodProfissional);
        }
        
        if (!txtDataDeAgendamento.getText().equals("")) {
            Util.verificarPesquisarCampoDescricao(agenda, txtDataDeAgendamento);
        }

        agenda.adicionarWhere(dadosWhere);

        Util.atualizar(agenda, tbResultado);
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
        jLabel3 = new javax.swing.JLabel();
        txtCodCliente = new javax.swing.JTextField();
        lblLocalizarCliente = new javax.swing.JLabel();
        txtNomeCliente = new javax.swing.JTextField();
        txtNomeFuncionario = new javax.swing.JTextField();
        lblLocalizarFuncionario = new javax.swing.JLabel();
        txtCodFuncionario = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtCodProfissional = new javax.swing.JTextField();
        lblLocalizarProfissional = new javax.swing.JLabel();
        txtNomeProfissional = new javax.swing.JTextField();
        txtDataDeAgendamento = new javax.swing.JFormattedTextField();
        jLabel6 = new javax.swing.JLabel();
        pnlResultado = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbResultado = new javax.swing.JTable();
        pnlBotoes = new javax.swing.JPanel();
        btnAjudar = new javax.swing.JButton();
        btnConfirmar = new javax.swing.JButton();
        btnAplicar = new javax.swing.JButton();

        jMenu1.setText("jMenu1");

        setMaximizable(true);
        setTitle("Consultar Agendas");
        setMinimumSize(new java.awt.Dimension(809, 717));
        setPreferredSize(new java.awt.Dimension(809, 717));

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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

        jLabel1.setText("Cód. Agenda");

        txtCodigo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCodigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCodigoKeyReleased(evt);
            }
        });

        jLabel3.setText("Cód. Cliente");

        txtCodCliente.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCodCliente.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodClienteFocusLost(evt);
            }
        });

        lblLocalizarCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/localizar.png"))); // NOI18N
        lblLocalizarCliente.setToolTipText("Localizar uma pessoa já cadastrada.");
        lblLocalizarCliente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblLocalizarCliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                lblLocalizarClienteMouseReleased(evt);
            }
        });

        txtNomeCliente.setEditable(false);
        txtNomeCliente.setBackground(java.awt.SystemColor.info);
        txtNomeCliente.setHorizontalAlignment(javax.swing.JTextField.LEFT);

        txtNomeFuncionario.setEditable(false);
        txtNomeFuncionario.setBackground(java.awt.SystemColor.info);
        txtNomeFuncionario.setHorizontalAlignment(javax.swing.JTextField.LEFT);

        lblLocalizarFuncionario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/localizar.png"))); // NOI18N
        lblLocalizarFuncionario.setToolTipText("Localizar uma função já cadastrada.");
        lblLocalizarFuncionario.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblLocalizarFuncionario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                lblLocalizarFuncionarioMouseReleased(evt);
            }
        });

        txtCodFuncionario.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCodFuncionario.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodFuncionarioFocusLost(evt);
            }
        });

        jLabel4.setText("Cód. Funcionário");

        jLabel5.setText("Cód. Profissional");

        txtCodProfissional.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCodProfissional.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodProfissionalFocusLost(evt);
            }
        });

        lblLocalizarProfissional.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/localizar.png"))); // NOI18N
        lblLocalizarProfissional.setToolTipText("Localizar uma pessoa já cadastrada.");
        lblLocalizarProfissional.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblLocalizarProfissional.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                lblLocalizarProfissionalMouseReleased(evt);
            }
        });

        txtNomeProfissional.setEditable(false);
        txtNomeProfissional.setBackground(java.awt.SystemColor.info);
        txtNomeProfissional.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtNomeProfissional.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomeProfissionalActionPerformed(evt);
            }
        });

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

        jLabel6.setText("Data de Agendamento");

        javax.swing.GroupLayout pnlPesquisarCamposLayout = new javax.swing.GroupLayout(pnlPesquisarCampos);
        pnlPesquisarCampos.setLayout(pnlPesquisarCamposLayout);
        pnlPesquisarCamposLayout.setHorizontalGroup(
            pnlPesquisarCamposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPesquisarCamposLayout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addGroup(pnlPesquisarCamposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlPesquisarCamposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlPesquisarCamposLayout.createSequentialGroup()
                        .addComponent(txtCodFuncionario, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(4, 4, 4)
                        .addComponent(lblLocalizarFuncionario)
                        .addGap(4, 4, 4)
                        .addComponent(txtNomeFuncionario, javax.swing.GroupLayout.PREFERRED_SIZE, 402, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlPesquisarCamposLayout.createSequentialGroup()
                        .addComponent(txtCodProfissional, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(7, 7, 7)
                        .addComponent(lblLocalizarProfissional)
                        .addGap(4, 4, 4)
                        .addComponent(txtNomeProfissional, javax.swing.GroupLayout.PREFERRED_SIZE, 402, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtDataDeAgendamento, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlPesquisarCamposLayout.createSequentialGroup()
                        .addComponent(txtCodCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblLocalizarCliente)
                        .addGap(4, 4, 4)
                        .addComponent(txtNomeCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 402, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(118, 118, 118))
        );
        pnlPesquisarCamposLayout.setVerticalGroup(
            pnlPesquisarCamposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPesquisarCamposLayout.createSequentialGroup()
                .addGroup(pnlPesquisarCamposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlPesquisarCamposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlPesquisarCamposLayout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel3))
                    .addComponent(txtCodCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblLocalizarCliente)
                    .addComponent(txtNomeCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(pnlPesquisarCamposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlPesquisarCamposLayout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel4))
                    .addComponent(txtCodFuncionario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblLocalizarFuncionario)
                    .addComponent(txtNomeFuncionario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(pnlPesquisarCamposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlPesquisarCamposLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(pnlPesquisarCamposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblLocalizarProfissional)
                            .addComponent(txtNomeProfissional, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pnlPesquisarCamposLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(pnlPesquisarCamposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txtCodProfissional, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(6, 6, 6)
                .addGroup(pnlPesquisarCamposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlPesquisarCamposLayout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(jLabel6))
                    .addComponent(txtDataDeAgendamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(37, 37, 37))
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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlResultadoLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jScrollPane1))
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
                .addComponent(pnlPesquisarCampos, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
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
            .addComponent(pnlBotoes, javax.swing.GroupLayout.DEFAULT_SIZE, 793, Short.MAX_VALUE)
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
            iFrmManAgenda.carregarObjetoIFrmManutencao(new Agendas());

            Util.incluir(this, iFrmManAgenda);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                    "Registro nao pode ser incluido.\n\nMotivo: " + ex);
        }
    }//GEN-LAST:event_btnIncluirActionPerformed

    private void btnAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAlterarActionPerformed
        try {
            reescreverObjeto();
            iFrmManAgenda.carregarObjetoIFrmManutencao(agenda);

            Util.alterar(this, iFrmManAgenda);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                    "Registro nao pode ser alterado.\n\nMotivo: " + ex);
        }
    }//GEN-LAST:event_btnAlterarActionPerformed

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
        Util.excluir(agenda, tbResultado, this);
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

    private void tbResultadoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbResultadoMouseClicked
        if ((btnAlterar.isEnabled()) && (evt.getClickCount() == 2)) {
            try {
                reescreverObjeto();

                if ((jTextFieldCodigo == null) && (jTextFieldDescricao == null)) {
                    iFrmManAgenda.carregarObjetoIFrmManutencao(agenda);

                    Util.alterar(this, iFrmManAgenda);
                } else {
                    jTextFieldCodigo.setText(String.valueOf(
                            agenda.getIdAgenda()));
                    jTextFieldDescricao.setText("");

                    Util.cancelar(this);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this.getParent(),
                        "Registro nao pode ser alterado.\n\nMotivo: " + ex);
            }
        }
    }//GEN-LAST:event_tbResultadoMouseClicked

    private void txtCodClienteFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodClienteFocusLost
        pesquisarCodigoDescicaoCliente(new Clientes());
    }//GEN-LAST:event_txtCodClienteFocusLost

    private void lblLocalizarClienteMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblLocalizarClienteMouseReleased
        boolean[] acoes = Util.getAcoesUsuario(Principal.CLIENTES);

        if (!acoes[Util.CONSULTAR]) {
            JOptionPane.showMessageDialog(this.getParent(),
                "Você não pode efetuar esta consultadas.\n\nMotivo: "
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

    private void txtCodFuncionarioFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodFuncionarioFocusLost
        pesquisarCodigoDescicaoFuncionario(new Funcionarios());
    }//GEN-LAST:event_txtCodFuncionarioFocusLost

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
                "Funcionário não pode ser consultado.\n\nMotivo: " + e);
        }
        }
    }//GEN-LAST:event_lblLocalizarProfissionalMouseReleased

    private void txtNomeProfissionalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomeProfissionalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNomeProfissionalActionPerformed

    private void txtDataDeAgendamentoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDataDeAgendamentoFocusGained
        txtDataDeAgendamento.selectAll();
    }//GEN-LAST:event_txtDataDeAgendamentoFocusGained

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAjudar;
    private javax.swing.JButton btnAlterar;
    private javax.swing.JButton btnAplicar;
    private javax.swing.JButton btnAtualizar;
    private javax.swing.JButton btnConfirmar;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnIncluir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblLocalizarCliente;
    private javax.swing.JLabel lblLocalizarFuncionario;
    private javax.swing.JLabel lblLocalizarProfissional;
    private javax.swing.JPanel pnlBotoes;
    private javax.swing.JPanel pnlConteudos;
    private javax.swing.JPanel pnlMenuBotoes;
    private javax.swing.JPanel pnlPesquisarCampos;
    private javax.swing.JPanel pnlResultado;
    private javax.swing.JTable tbResultado;
    private javax.swing.JTextField txtCodCliente;
    private javax.swing.JTextField txtCodFuncionario;
    private javax.swing.JTextField txtCodProfissional;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JFormattedTextField txtDataDeAgendamento;
    private javax.swing.JTextField txtNomeCliente;
    private javax.swing.JTextField txtNomeFuncionario;
    private javax.swing.JTextField txtNomeProfissional;
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
