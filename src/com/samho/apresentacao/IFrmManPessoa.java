/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.samho.apresentacao;

import com.samho.dao.DadosDAO;
import com.samho.negocio.Enderecos;
import com.samho.negocio.Pessoas;
import com.samho.negocio.PessoasEspecializacoes;
import com.samho.negocio.PessoasFisicas;
import com.samho.negocio.Principal;
import com.samho.negocio.SituacoesPessoas;
import com.samho.negocio.Telefones;
import com.samho.util.Formatacao;
import com.samho.util.JTextFieldDocument;
import com.samho.util.Util;
import static com.samho.util.Util.habilitarDesabilitarPainel;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;

/**
 * Classe para visualização de objetos do tipo FrmManutencao.
 *
 * @author Saibel, Sergio Luis
 * @since  date 31/03/2017
 *
 * @version  revision 001.20170331 date 31/03/2017 author Saibel, Sérgio Luis reason
 * Conseguir instanciar um objeto do tipo FrmMaunutenção com os atributos e
 * métodos necessários. Esta classe tem como objetivo manipular os objetos 
 * definidos e ou obtidos a fim de manipular informações.
 * Esta extende um JInternalFrame ou seja necessita de um Frame como pai.
 * Métodos principais: Definição, Obtenção e Visualização do objeto.
 * Métodos secundários: Definir endereço e telefone.
 * 
 * Esta classe necessita de um método próprio de salvamento, pois uma pessoa 
 * pode ser fisico/juridica e possuir vários telefones e endereços.
 * 
 */
public final class IFrmManPessoa extends JInternalFrame {

    // Declaração de atributos
    private Pessoas pessoa;
    private Enderecos endereco;
    private Telefones telefone;
    private PessoasEspecializacoes pessoaEspecializacao;

    // Construtor
    public IFrmManPessoa(Pessoas pessoa) throws Exception {
        initComponents();
        
        Formatacao.reformatarCpf(txtCPF);
        Formatacao.reformatarData(txtDataDeNascimento);
        Formatacao.reformatarCnpj(txtCNPJ);
        Formatacao.reformatarData(txtDataDeFundacao);

        Util.limparCampos(rootPane);

        this.pessoa = pessoa;

        // Permite que somente números sejam informados neste campo
        txtCodigo.setDocument(new JTextFieldDocument(true));
        //txtCPF.setDocument(new JTextFieldDocument(true));
        txtRG.setDocument(new JTextFieldDocument(true));
        //txtDataDeNascimento.setDocument(new JTextFieldDocument(true));
        //txtCNPJ.setDocument(new JTextFieldDocument(true));
        txtIE.setDocument(new JTextFieldDocument(true)); 
        //txtDataDeFundacao.setDocument(new JTextFieldDocument(true));
        // Converte para caixa alta a string informada no campo
        txtNome.setDocument(new JTextFieldDocument(false));
        txtSobrenome.setDocument(new JTextFieldDocument(false));
        txtNomeDaMae.setDocument(new JTextFieldDocument(false));

        // Quebra de linha automática
        txtObservacoes.setLineWrap(true);
        txtObservacoes.setWrapStyleWord(true);

        if (pessoa.getTipo() == Pessoas.TIPO_FISICA) {
            tablManutencao.remove(pnlDetalhe1);
        } else {
            tablManutencao.remove(pnlDetalhe);
        }
              
        if (Util.getAcoesUsuario(Principal.ESPECIALIZACOES)[Util.CONSULTAR] == false) {
            tablManutencao.remove(pnlDetalhe2);
        }
        
        tablManutencao.setSelectedIndex(0);

        carregarObjetoIFrmManutencao(pessoa);
    }

    public void carregarObjetoIFrmManutencao(Pessoas pessoa) {
        // carregar campos da aba geral
        if (pessoa.getIdPessoa() != -1) {
            txtCodigo.setText(String.valueOf(pessoa.getIdPessoa()));
        } else {
            txtCodigo.setText(String.valueOf(
                    pessoa.getPessoaDAO().getProximoID()));
        }
        txtNome.setText(pessoa.getNome());
        txtSobrenome.setText(pessoa.getSobrenome());
        txtObservacoes.setText(pessoa.getObservacoes());
        //  carregar campos da aba detalhes
        if (pessoa.getTipo() == Pessoas.TIPO_FISICA) {
            lblNome.setText("Nome");
            lblSobrenome.setText("Sobrenome");
            txtCPF.setText(String.valueOf(pessoa.getPessoaFisica().getCpf()));
            txtRG.setText(String.valueOf(pessoa.getPessoaFisica().getRg()));
            txtDataDeNascimento.setText(Formatacao.ajustaDataDMA(
                    pessoa.getPessoaFisica().getDataDeNascimento()));
            txtNomeDaMae.setText(pessoa.getPessoaFisica().getNomeDaMae());
            if (pessoa.getPessoaFisica().getSexo()
                    == PessoasFisicas.MASCULINO) {
                rbMasculino.setSelected(true);
            } else {
                rbFeminino.setSelected(true);
            }

            cbSituacao.removeAllItems();
            SituacoesPessoas situacoes = new SituacoesPessoas();
            situacoes.getSituacaoDAO().popularListaJComboBox(cbSituacao,
                    situacoes.getDadosCodigo().getCampo(),
                    situacoes.getDadosDescricao().getCampo());
            cbSituacao.setSelectedIndex(Integer.parseUnsignedInt(
                        String.valueOf(pessoa.getCodSituacao())));
        } else {
            //  carregar campos da aba detalhes1
            if (pessoa.getTipo() == Pessoas.TIPO_JURIDICA) {
                lblNome.setText("Nome");
                lblSobrenome.setText("Rasão Social");
                txtCNPJ.setText(String.valueOf(pessoa.getPessoaJuridica().getCnpj()));
                txtIE.setText(String.valueOf(pessoa.getPessoaJuridica().getIe()));
                txtDataDeFundacao.setText(Formatacao.ajustaDataDMA(
                        pessoa.getPessoaJuridica().getDataDeFundacao()));

                cbSituacao1.removeAllItems();
                SituacoesPessoas situacoes = new SituacoesPessoas();
                situacoes.getSituacaoDAO().popularListaJComboBox(cbSituacao1,
                        situacoes.getDadosCodigo().getCampo(),
                        situacoes.getDadosDescricao().getCampo());
                cbSituacao1.setSelectedIndex(Integer.parseUnsignedInt(
                        String.valueOf(pessoa.getCodSituacao())));
            }
        }
    }

    private void reescreverObjeto() {
        try {
            // reescrever pessoa
            pessoa.setIdPessoa(Long.parseUnsignedLong(txtCodigo.getText()));
            pessoa.setNome(txtNome.getText());
            pessoa.setSobrenome(txtSobrenome.getText());
            pessoa.setObservacoes(txtObservacoes.getText());
            // reescrever pessoa física
            if (pessoa.getTipo() == Pessoas.TIPO_FISICA) {
                pessoa.getPessoaFisica().setCodPessoa(pessoa.getIdPessoa());
                pessoa.getPessoaFisica().setCpf(Long.parseUnsignedLong(
                        Formatacao.removerFormatacao(txtCPF.getText())));
                pessoa.getPessoaFisica().setRg(Long.parseUnsignedLong(
                        txtRG.getText()));
                pessoa.getPessoaFisica().setDataDeNascimento(
                        Formatacao.ajustaData(txtDataDeNascimento.getText(), 
                                Formatacao.DATA_DMA));
                pessoa.getPessoaFisica().setNomeDaMae(
                        txtNomeDaMae.getText());
                if (rbFeminino.isSelected()) {
                    pessoa.getPessoaFisica().setSexo(
                            PessoasFisicas.FEMININO);
                } else {
                    pessoa.getPessoaFisica().setSexo(
                            PessoasFisicas.MASCULINO);
                }
                pessoa.setCodSituacao(Long.parseUnsignedLong(
                        String.valueOf(cbSituacao.getSelectedIndex())));
            } else {
                if (pessoa.getTipo() == Pessoas.TIPO_JURIDICA) {
                    pessoa.getPessoaJuridica().setCodPessoa(pessoa.getIdPessoa());
                    pessoa.getPessoaJuridica().setCnpj(Long.parseUnsignedLong(
                            Formatacao.removerFormatacao(txtCNPJ.getText())));
                    pessoa.getPessoaJuridica().setIe(Long.parseUnsignedLong(
                            txtIE.getText()));
                    pessoa.getPessoaJuridica().setDataDeFundacao(
                            Formatacao.ajustaData(txtDataDeFundacao.getText(),
                                    Formatacao.DATA_DMA));
                    pessoa.setCodSituacao(cbSituacao1.getSelectedIndex());
                }
            }

            pessoa.adicionarCampos();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                    "Registro nao pode ser reescrito.\n\nMotivo: " + ex);
        }
    }

    private void reescreverObjetoEndereco(JTable tblEnderecos) {
        try {
            endereco.setIdEndereco(Long.parseUnsignedLong(
                    tblEnderecos.getValueAt(
                            tblEnderecos.getSelectedRow(), 0).toString()));
            endereco.setCodPessoa(Long.parseUnsignedLong(
                    tblEnderecos.getValueAt(
                            tblEnderecos.getSelectedRow(), 1).toString()));
            // Reserva-se a próxima posição para a descrição do campo acima.
            endereco.setCodTipoEndereco(Long.parseUnsignedLong(
                    tblEnderecos.getValueAt(
                            tblEnderecos.getSelectedRow(), 3).toString()));
            // Reserva-se a próxima posição para a descrição do campo acima.
            endereco.setCodBairro(Long.parseUnsignedLong(
                    tblEnderecos.getValueAt(
                            tblEnderecos.getSelectedRow(), 5).toString()));
            // Reserva-se a próxima posição para a descrição do campo acima.
            endereco.setDescricao(tblEnderecos.getValueAt(
                    tblEnderecos.getSelectedRow(), 7).toString());
            endereco.setCep(Long.parseUnsignedLong(
                    Formatacao.removerFormatacao(tblEnderecos.getValueAt(
                            tblEnderecos.getSelectedRow(), 8).toString())));
            endereco.setComplemento(tblEnderecos.getValueAt(
                    tblEnderecos.getSelectedRow(), 9).toString());
            endereco.setAtivo(true);

            endereco.adicionarCampos();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                    "Registro nao pode ser reescrito.\n\nMotivo: " + ex);
        }
    }

    private void reescreverObjetoTelefone(JTable tblTelefones) {
        try {
            telefone.setIdTelefone(Long.parseUnsignedLong(
                    tblTelefones.getValueAt(
                            tblTelefones.getSelectedRow(), 0).toString()));
            telefone.setCodPessoa(Long.parseUnsignedLong(
                    tblTelefones.getValueAt(
                            tblTelefones.getSelectedRow(), 1).toString()));
            // Reserva-se a próxima posição para a descrição do campo acima.
            telefone.setCodTipoTelefone(Long.parseUnsignedLong(
                    tblTelefones.getValueAt(
                            tblTelefones.getSelectedRow(), 3).toString()));
            // Reserva-se a próxima posição para a descrição do campo acima.
            telefone.setNumero(Long.parseUnsignedLong(
                    Formatacao.removerFormatacao(tblTelefones.getValueAt(
                            tblTelefones.getSelectedRow(), 5).toString())));
            telefone.setRamal(Long.parseUnsignedLong(
                    tblTelefones.getValueAt(
                            tblTelefones.getSelectedRow(), 6).toString()));
            telefone.setContato(tblTelefones.getValueAt(
                    tblTelefones.getSelectedRow(), 7).toString());
            telefone.setAtivo(true);

            telefone.adicionarCampos();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                    "Registro nao pode ser reescrito.\n\nMotivo: " + ex);
        }
    }
    
    private void reescreverObjetoPessoaEspecializacoes() {
        try {
           pessoaEspecializacao.setIdPessoaEspecializacao(
                    Long.parseUnsignedLong(tbEspecializacoes.getValueAt(
                            tbEspecializacoes.getSelectedRow(), 0).toString()));
            pessoaEspecializacao.setCodPessoa(Long.parseUnsignedLong(
                    tbEspecializacoes.getValueAt(
                            tbEspecializacoes.getSelectedRow(), 1).toString()));
            // Reserva-se a próxima posição para a descrição do campo acima.
            pessoaEspecializacao.setCodEspecializacao(Long.parseUnsignedLong(
                    tbEspecializacoes.getValueAt(
                            tbEspecializacoes.getSelectedRow(), 3).toString()));
            // Reserva-se a próxima posição para a descrição do campo acima.
            pessoaEspecializacao.setInstituicao(tbEspecializacoes.getValueAt(
                            tbEspecializacoes.getSelectedRow(), 5).toString());
            pessoaEspecializacao.setNumRegistro(Integer.parseInt(
                    tbEspecializacoes.getValueAt(
                            tbEspecializacoes.getSelectedRow(), 6).toString()));
            pessoaEspecializacao.setDataConclusao(
                    Formatacao.ajustaData(tbEspecializacoes.getValueAt(
                            tbEspecializacoes.getSelectedRow(), 7).toString(),
                            Formatacao.DATA_AMD));
            pessoaEspecializacao.setAtivo(false);
            pessoaEspecializacao.setObservacoes(tbEspecializacoes.getValueAt(
                    tbEspecializacoes.getSelectedRow(), 9).toString());

            pessoaEspecializacao.adicionarCampos();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                    "Registro nao pode ser reescrito.\n\nMotivo: " + ex);
        }
    }
    

    private void salvarPessoa(boolean fecharFrame) {
        reescreverObjeto();
        
        if (fecharFrame) {
            tablManutencao.setSelectedIndex(0);
        }
        Util.salvar(pessoa, this, fecharFrame);
        if (pessoa.getTipo() == Pessoas.TIPO_FISICA) {
            Util.salvarFilho(pessoa.getPessoaFisica(), this, fecharFrame);
        } else {
            Util.salvarFilho(pessoa.getPessoaJuridica(), this, fecharFrame);
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

        rdgSexo = new javax.swing.ButtonGroup();
        tablManutencao = new javax.swing.JTabbedPane();
        pnlGeral = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        lblNome = new javax.swing.JLabel();
        txtNome = new javax.swing.JTextField();
        lblSobrenome = new javax.swing.JLabel();
        txtSobrenome = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtObservacoes = new javax.swing.JTextArea();
        pnlDetalhe = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txtCPF = new javax.swing.JFormattedTextField();
        jLabel5 = new javax.swing.JLabel();
        txtRG = new javax.swing.JFormattedTextField();
        jLabel6 = new javax.swing.JLabel();
        txtDataDeNascimento = new javax.swing.JFormattedTextField();
        jLabel7 = new javax.swing.JLabel();
        txtNomeDaMae = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        rbMasculino = new javax.swing.JRadioButton();
        rbFeminino = new javax.swing.JRadioButton();
        jLabel10 = new javax.swing.JLabel();
        cbSituacao = new javax.swing.JComboBox();
        pnlEnderecos = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbEnderecos = new javax.swing.JTable();
        btnIncluirEndereco = new javax.swing.JButton();
        btnExcluirEndereco = new javax.swing.JButton();
        btnAtualizarEnderecos = new javax.swing.JButton();
        pnlTelefones = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbTelefones = new javax.swing.JTable();
        btnIncluirTelefone = new javax.swing.JButton();
        btnExcluirTelefone = new javax.swing.JButton();
        btnAtualizarTelefones = new javax.swing.JButton();
        pnlDetalhe1 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        txtCNPJ = new javax.swing.JFormattedTextField();
        jLabel12 = new javax.swing.JLabel();
        txtIE = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtDataDeFundacao = new javax.swing.JFormattedTextField();
        jLabel14 = new javax.swing.JLabel();
        cbSituacao1 = new javax.swing.JComboBox();
        pnlEnderecos1 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tbEnderecos1 = new javax.swing.JTable();
        btnIncluirEndereco1 = new javax.swing.JButton();
        btnExcluirEndereco1 = new javax.swing.JButton();
        btnAtualizarEnderecos1 = new javax.swing.JButton();
        pnlTelefones1 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tbTelefones1 = new javax.swing.JTable();
        btnIncluirTelefone1 = new javax.swing.JButton();
        btnExcluirTelefone1 = new javax.swing.JButton();
        btnAtualizarTelefones1 = new javax.swing.JButton();
        pnlDetalhe2 = new javax.swing.JPanel();
        pnlEspecializacoes = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tbEspecializacoes = new javax.swing.JTable();
        btnIncluirEspecializacao = new javax.swing.JButton();
        btnExcluirEspecializacao = new javax.swing.JButton();
        btnAtualizarEspecializacoes = new javax.swing.JButton();
        pnlBotoes = new javax.swing.JPanel();
        btnConfirmar = new javax.swing.JButton();
        btnAjudar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        mnuBarPrincipal = new javax.swing.JMenuBar();
        mnuArquivos = new javax.swing.JMenu();
        mnuAjuda = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cadastro de Pessoa");
        setMaximumSize(new java.awt.Dimension(730, 504));
        setMinimumSize(new java.awt.Dimension(730, 504));
        setPreferredSize(new java.awt.Dimension(730, 504));
        getContentPane().setLayout(null);

        tablManutencao.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                tablManutencaoStateChanged(evt);
            }
        });

        pnlGeral.setLayout(null);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setText("Cód. Pessoa");
        pnlGeral.add(jLabel1);
        jLabel1.setBounds(68, 14, 67, 14);

        txtCodigo.setEditable(false);
        txtCodigo.setBackground(java.awt.SystemColor.info);
        txtCodigo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCodigo.setToolTipText("");
        pnlGeral.add(txtCodigo);
        txtCodigo.setBounds(139, 11, 70, 20);

        lblNome.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblNome.setText("Nome");
        pnlGeral.add(lblNome);
        lblNome.setBounds(103, 40, 32, 14);

        txtNome.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        pnlGeral.add(txtNome);
        txtNome.setBounds(139, 37, 500, 20);

        lblSobrenome.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblSobrenome.setText("Sobrenome");
        pnlGeral.add(lblSobrenome);
        lblSobrenome.setBounds(70, 66, 65, 14);

        txtSobrenome.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        pnlGeral.add(txtSobrenome);
        txtSobrenome.setBounds(139, 63, 500, 20);

        jLabel8.setText("Observações");
        pnlGeral.add(jLabel8);
        jLabel8.setBounds(72, 89, 63, 14);

        txtObservacoes.setColumns(20);
        txtObservacoes.setRows(5);
        jScrollPane1.setViewportView(txtObservacoes);

        pnlGeral.add(jScrollPane1);
        jScrollPane1.setBounds(139, 89, 500, 280);

        tablManutencao.addTab("Geral", pnlGeral);

        pnlDetalhe.setLayout(null);

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel4.setText("CPF");
        pnlDetalhe.add(jLabel4);
        jLabel4.setBounds(117, 14, 20, 14);

        try {
            txtCPF.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtCPF.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCPF.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCPFFocusGained(evt);
            }
        });
        pnlDetalhe.add(txtCPF);
        txtCPF.setBounds(141, 11, 115, 20);

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel5.setText("RG");
        pnlDetalhe.add(jLabel5);
        jLabel5.setBounds(297, 14, 16, 14);

        try {
            txtRG.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##########")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtRG.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtRG.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtRGFocusGained(evt);
            }
        });
        pnlDetalhe.add(txtRG);
        txtRG.setBounds(317, 11, 100, 20);

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel6.setText("Data de Nascimento");
        pnlDetalhe.add(jLabel6);
        jLabel6.setBounds(454, 14, 113, 14);

        try {
            txtDataDeNascimento.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtDataDeNascimento.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDataDeNascimentoFocusGained(evt);
            }
        });
        pnlDetalhe.add(txtDataDeNascimento);
        txtDataDeNascimento.setBounds(571, 11, 70, 20);

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel7.setText("Nome da mãe");
        pnlDetalhe.add(jLabel7);
        jLabel7.setBounds(60, 40, 77, 14);

        txtNomeDaMae.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        pnlDetalhe.add(txtNomeDaMae);
        txtNomeDaMae.setBounds(141, 37, 500, 20);

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel9.setText("Sexo");
        pnlDetalhe.add(jLabel9);
        jLabel9.setBounds(109, 66, 28, 14);

        rdgSexo.add(rbMasculino);
        rbMasculino.setSelected(true);
        rbMasculino.setText("Masculino");
        pnlDetalhe.add(rbMasculino);
        rbMasculino.setBounds(141, 62, 71, 23);

        rdgSexo.add(rbFeminino);
        rbFeminino.setText("Feminino");
        pnlDetalhe.add(rbFeminino);
        rbFeminino.setBounds(214, 62, 67, 23);

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel10.setText("Situação");
        pnlDetalhe.add(jLabel10);
        jLabel10.setBounds(388, 66, 49, 14);

        cbSituacao.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        pnlDetalhe.add(cbSituacao);
        cbSituacao.setBounds(441, 63, 200, 20);

        pnlEnderecos.setBorder(javax.swing.BorderFactory.createTitledBorder("Endereços"));

        tbEnderecos.setModel(new javax.swing.table.DefaultTableModel(
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
        tbEnderecos.setName(""); // NOI18N
        tbEnderecos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tbEnderecosMouseReleased(evt);
            }
        });
        jScrollPane2.setViewportView(tbEnderecos);

        btnIncluirEndereco.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/incluir.png"))); // NOI18N
        btnIncluirEndereco.setText("Incluir");
        btnIncluirEndereco.setToolTipText("Incluir um endereços para a pessoa.");
        btnIncluirEndereco.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnIncluirEndereco.setMargin(new java.awt.Insets(2, 5, 2, 5));
        btnIncluirEndereco.setMaximumSize(new java.awt.Dimension(69, 29));
        btnIncluirEndereco.setMinimumSize(null);
        btnIncluirEndereco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIncluirEnderecoActionPerformed(evt);
            }
        });

        btnExcluirEndereco.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/excluir.png"))); // NOI18N
        btnExcluirEndereco.setText("Excluir");
        btnExcluirEndereco.setToolTipText("Excluir um  endereço para a pessoa.");
        btnExcluirEndereco.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnExcluirEndereco.setMargin(new java.awt.Insets(2, 5, 2, 5));
        btnExcluirEndereco.setMinimumSize(null);
        btnExcluirEndereco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirEnderecoActionPerformed(evt);
            }
        });

        btnAtualizarEnderecos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/atualizar.png"))); // NOI18N
        btnAtualizarEnderecos.setText("Atualizar");
        btnAtualizarEnderecos.setToolTipText("Atualizar lista de endereços da pessoa.");
        btnAtualizarEnderecos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAtualizarEnderecos.setMargin(new java.awt.Insets(2, 5, 2, 5));
        btnAtualizarEnderecos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtualizarEnderecosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlEnderecosLayout = new javax.swing.GroupLayout(pnlEnderecos);
        pnlEnderecos.setLayout(pnlEnderecosLayout);
        pnlEnderecosLayout.setHorizontalGroup(
            pnlEnderecosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlEnderecosLayout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 513, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlEnderecosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnExcluirEndereco, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnIncluirEndereco, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnAtualizarEnderecos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlEnderecosLayout.setVerticalGroup(
            pnlEnderecosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlEnderecosLayout.createSequentialGroup()
                .addComponent(btnIncluirEndereco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnExcluirEndereco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAtualizarEnderecos)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlDetalhe.add(pnlEnderecos);
        pnlEnderecos.setBounds(36, 89, 622, 133);

        pnlTelefones.setBorder(javax.swing.BorderFactory.createTitledBorder("Telefones"));

        tbTelefones.setModel(new javax.swing.table.DefaultTableModel(
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
        tbTelefones.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tbTelefonesMouseReleased(evt);
            }
        });
        jScrollPane3.setViewportView(tbTelefones);

        btnIncluirTelefone.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/incluir.png"))); // NOI18N
        btnIncluirTelefone.setText("Incluir");
        btnIncluirTelefone.setToolTipText("Incluir um telefone para a pessoa.");
        btnIncluirTelefone.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnIncluirTelefone.setMargin(new java.awt.Insets(2, 5, 2, 5));
        btnIncluirTelefone.setMaximumSize(new java.awt.Dimension(69, 29));
        btnIncluirTelefone.setMinimumSize(null);
        btnIncluirTelefone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIncluirTelefoneActionPerformed(evt);
            }
        });

        btnExcluirTelefone.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/excluir.png"))); // NOI18N
        btnExcluirTelefone.setText("Excluir");
        btnExcluirTelefone.setToolTipText("Excluir um telefone para a pessoa.");
        btnExcluirTelefone.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnExcluirTelefone.setMargin(new java.awt.Insets(2, 5, 2, 5));
        btnExcluirTelefone.setMinimumSize(null);
        btnExcluirTelefone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirTelefoneActionPerformed(evt);
            }
        });

        btnAtualizarTelefones.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/atualizar.png"))); // NOI18N
        btnAtualizarTelefones.setText("Atualizar");
        btnAtualizarTelefones.setToolTipText("Atualizar lista de telefones da pessoa.");
        btnAtualizarTelefones.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAtualizarTelefones.setMargin(new java.awt.Insets(2, 5, 2, 5));
        btnAtualizarTelefones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtualizarTelefonesActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlTelefonesLayout = new javax.swing.GroupLayout(pnlTelefones);
        pnlTelefones.setLayout(pnlTelefonesLayout);
        pnlTelefonesLayout.setHorizontalGroup(
            pnlTelefonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTelefonesLayout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 511, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlTelefonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnAtualizarTelefones, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnExcluirTelefone, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnIncluirTelefone, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(12, Short.MAX_VALUE))
        );
        pnlTelefonesLayout.setVerticalGroup(
            pnlTelefonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTelefonesLayout.createSequentialGroup()
                .addComponent(btnIncluirTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnExcluirTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAtualizarTelefones)
                .addGap(0, 11, Short.MAX_VALUE))
            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );

        pnlDetalhe.add(pnlTelefones);
        pnlTelefones.setBounds(36, 228, 622, 133);

        tablManutencao.addTab("Complemento", pnlDetalhe);

        pnlDetalhe1.setLayout(null);

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel11.setText("CNPJ");
        pnlDetalhe1.add(jLabel11);
        jLabel11.setBounds(77, 14, 27, 14);

        try {
            txtCNPJ.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtCNPJ.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCNPJ.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCNPJFocusGained(evt);
            }
        });
        pnlDetalhe1.add(txtCNPJ);
        txtCNPJ.setBounds(108, 11, 115, 20);

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel12.setText("IE");
        pnlDetalhe1.add(jLabel12);
        jLabel12.setBounds(281, 14, 11, 14);

        txtIE.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtIE.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtIEFocusGained(evt);
            }
        });
        pnlDetalhe1.add(txtIE);
        txtIE.setBounds(296, 11, 100, 20);

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel13.setText("Data de Fundação");
        pnlDetalhe1.add(jLabel13);
        jLabel13.setBounds(466, 14, 101, 14);

        try {
            txtDataDeFundacao.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtDataDeFundacao.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDataDeFundacaoFocusGained(evt);
            }
        });
        pnlDetalhe1.add(txtDataDeFundacao);
        txtDataDeFundacao.setBounds(571, 11, 70, 20);

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel14.setText("Situação");
        pnlDetalhe1.add(jLabel14);
        jLabel14.setBounds(55, 40, 49, 14);

        cbSituacao1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        pnlDetalhe1.add(cbSituacao1);
        cbSituacao1.setBounds(108, 37, 200, 20);

        pnlEnderecos1.setBorder(javax.swing.BorderFactory.createTitledBorder("Endereços"));

        tbEnderecos1.setModel(new javax.swing.table.DefaultTableModel(
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
        tbEnderecos1.setName(""); // NOI18N
        tbEnderecos1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tbEnderecos1MouseReleased(evt);
            }
        });
        jScrollPane4.setViewportView(tbEnderecos1);

        btnIncluirEndereco1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/incluir.png"))); // NOI18N
        btnIncluirEndereco1.setText("Incluir");
        btnIncluirEndereco1.setToolTipText("Incluir um endereços para a pessoa.");
        btnIncluirEndereco1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnIncluirEndereco1.setMargin(new java.awt.Insets(2, 5, 2, 5));
        btnIncluirEndereco1.setMaximumSize(new java.awt.Dimension(69, 29));
        btnIncluirEndereco1.setMinimumSize(null);
        btnIncluirEndereco1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIncluirEndereco1ActionPerformed(evt);
            }
        });

        btnExcluirEndereco1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/excluir.png"))); // NOI18N
        btnExcluirEndereco1.setText("Excluir");
        btnExcluirEndereco1.setToolTipText("Excluir um  endereço para a pessoa.");
        btnExcluirEndereco1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnExcluirEndereco1.setMargin(new java.awt.Insets(2, 5, 2, 5));
        btnExcluirEndereco1.setMinimumSize(null);
        btnExcluirEndereco1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirEndereco1ActionPerformed(evt);
            }
        });

        btnAtualizarEnderecos1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/atualizar.png"))); // NOI18N
        btnAtualizarEnderecos1.setText("Atualizar");
        btnAtualizarEnderecos1.setToolTipText("Atualizar lista de endereços da pessoa.");
        btnAtualizarEnderecos1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAtualizarEnderecos1.setMargin(new java.awt.Insets(2, 5, 2, 5));
        btnAtualizarEnderecos1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtualizarEnderecos1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlEnderecos1Layout = new javax.swing.GroupLayout(pnlEnderecos1);
        pnlEnderecos1.setLayout(pnlEnderecos1Layout);
        pnlEnderecos1Layout.setHorizontalGroup(
            pnlEnderecos1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlEnderecos1Layout.createSequentialGroup()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 513, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlEnderecos1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnExcluirEndereco1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnIncluirEndereco1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnAtualizarEnderecos1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlEnderecos1Layout.setVerticalGroup(
            pnlEnderecos1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlEnderecos1Layout.createSequentialGroup()
                .addComponent(btnIncluirEndereco1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnExcluirEndereco1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAtualizarEnderecos1)
                .addContainerGap(13, Short.MAX_VALUE))
            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );

        pnlDetalhe1.add(pnlEnderecos1);
        pnlEnderecos1.setBounds(41, 63, 622, 135);

        pnlTelefones1.setBorder(javax.swing.BorderFactory.createTitledBorder("Telefones"));

        tbTelefones1.setModel(new javax.swing.table.DefaultTableModel(
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
        tbTelefones1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tbTelefones1MouseReleased(evt);
            }
        });
        jScrollPane5.setViewportView(tbTelefones1);

        btnIncluirTelefone1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/incluir.png"))); // NOI18N
        btnIncluirTelefone1.setText("Incluir");
        btnIncluirTelefone1.setToolTipText("Incluir um telefone para a pessoa.");
        btnIncluirTelefone1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnIncluirTelefone1.setMargin(new java.awt.Insets(2, 5, 2, 5));
        btnIncluirTelefone1.setMaximumSize(new java.awt.Dimension(69, 29));
        btnIncluirTelefone1.setMinimumSize(null);
        btnIncluirTelefone1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIncluirTelefone1ActionPerformed(evt);
            }
        });

        btnExcluirTelefone1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/excluir.png"))); // NOI18N
        btnExcluirTelefone1.setText("Excluir");
        btnExcluirTelefone1.setToolTipText("Excluir um telefone para a pessoa.");
        btnExcluirTelefone1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnExcluirTelefone1.setMargin(new java.awt.Insets(2, 5, 2, 5));
        btnExcluirTelefone1.setMinimumSize(null);
        btnExcluirTelefone1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirTelefone1ActionPerformed(evt);
            }
        });

        btnAtualizarTelefones1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/atualizar.png"))); // NOI18N
        btnAtualizarTelefones1.setText("Atualizar");
        btnAtualizarTelefones1.setToolTipText("Atualizar lista de telefones da pessoa.");
        btnAtualizarTelefones1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAtualizarTelefones1.setMargin(new java.awt.Insets(2, 5, 2, 5));
        btnAtualizarTelefones1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtualizarTelefones1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlTelefones1Layout = new javax.swing.GroupLayout(pnlTelefones1);
        pnlTelefones1.setLayout(pnlTelefones1Layout);
        pnlTelefones1Layout.setHorizontalGroup(
            pnlTelefones1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTelefones1Layout.createSequentialGroup()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 515, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlTelefones1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnAtualizarTelefones1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnExcluirTelefone1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnIncluirTelefone1, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlTelefones1Layout.setVerticalGroup(
            pnlTelefones1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(pnlTelefones1Layout.createSequentialGroup()
                .addComponent(btnIncluirTelefone1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnExcluirTelefone1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAtualizarTelefones1)
                .addGap(0, 44, Short.MAX_VALUE))
        );

        pnlDetalhe1.add(pnlTelefones1);
        pnlTelefones1.setBounds(41, 203, 622, 166);

        tablManutencao.addTab("Complemento", pnlDetalhe1);

        pnlEspecializacoes.setBorder(javax.swing.BorderFactory.createTitledBorder("Endereços"));

        tbEspecializacoes.setModel(new javax.swing.table.DefaultTableModel(
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
        tbEspecializacoes.setName(""); // NOI18N
        tbEspecializacoes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tbEspecializacoesMouseReleased(evt);
            }
        });
        jScrollPane6.setViewportView(tbEspecializacoes);

        btnIncluirEspecializacao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/incluir.png"))); // NOI18N
        btnIncluirEspecializacao.setText("Incluir");
        btnIncluirEspecializacao.setToolTipText("Incluir um endereços para a pessoa.");
        btnIncluirEspecializacao.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnIncluirEspecializacao.setMargin(new java.awt.Insets(2, 5, 2, 5));
        btnIncluirEspecializacao.setMaximumSize(new java.awt.Dimension(69, 29));
        btnIncluirEspecializacao.setMinimumSize(null);
        btnIncluirEspecializacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIncluirEspecializacaoActionPerformed(evt);
            }
        });

        btnExcluirEspecializacao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/excluir.png"))); // NOI18N
        btnExcluirEspecializacao.setText("Excluir");
        btnExcluirEspecializacao.setToolTipText("Excluir um  endereço para a pessoa.");
        btnExcluirEspecializacao.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnExcluirEspecializacao.setMargin(new java.awt.Insets(2, 5, 2, 5));
        btnExcluirEspecializacao.setMinimumSize(null);
        btnExcluirEspecializacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirEspecializacaoActionPerformed(evt);
            }
        });

        btnAtualizarEspecializacoes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/atualizar.png"))); // NOI18N
        btnAtualizarEspecializacoes.setText("Atualizar");
        btnAtualizarEspecializacoes.setToolTipText("Atualizar lista de endereços da pessoa.");
        btnAtualizarEspecializacoes.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAtualizarEspecializacoes.setMargin(new java.awt.Insets(2, 5, 2, 5));
        btnAtualizarEspecializacoes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtualizarEspecializacoesActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlEspecializacoesLayout = new javax.swing.GroupLayout(pnlEspecializacoes);
        pnlEspecializacoes.setLayout(pnlEspecializacoesLayout);
        pnlEspecializacoesLayout.setHorizontalGroup(
            pnlEspecializacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlEspecializacoesLayout.createSequentialGroup()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 599, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlEspecializacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnExcluirEspecializacao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnIncluirEspecializacao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnAtualizarEspecializacoes))
                .addContainerGap())
        );
        pnlEspecializacoesLayout.setVerticalGroup(
            pnlEspecializacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlEspecializacoesLayout.createSequentialGroup()
                .addComponent(btnIncluirEspecializacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnExcluirEspecializacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAtualizarEspecializacoes)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 359, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout pnlDetalhe2Layout = new javax.swing.GroupLayout(pnlDetalhe2);
        pnlDetalhe2.setLayout(pnlDetalhe2Layout);
        pnlDetalhe2Layout.setHorizontalGroup(
            pnlDetalhe2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 708, Short.MAX_VALUE)
            .addGroup(pnlDetalhe2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(pnlEspecializacoes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlDetalhe2Layout.setVerticalGroup(
            pnlDetalhe2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 382, Short.MAX_VALUE)
            .addGroup(pnlDetalhe2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(pnlEspecializacoes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tablManutencao.addTab("Especializações", pnlDetalhe2);

        getContentPane().add(tablManutencao);
        tablManutencao.setBounds(0, 0, 713, 410);

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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 495, Short.MAX_VALUE)
                .addComponent(btnConfirmar, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
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
        pnlBotoes.setBounds(0, 414, 713, 38);

        mnuArquivos.setText("Arquivo");
        mnuBarPrincipal.add(mnuArquivos);

        mnuAjuda.setText("Ajuda");
        mnuBarPrincipal.add(mnuAjuda);

        setJMenuBar(mnuBarPrincipal);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirmarActionPerformed
        tablManutencao.setSelectedIndex(0);
        
        salvarPessoa(true);
    }//GEN-LAST:event_btnConfirmarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        tablManutencao.setSelectedIndex(0);
        
        Util.cancelar(this);
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnAjudarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAjudarActionPerformed
        Util.ajudar();
    }//GEN-LAST:event_btnAjudarActionPerformed

    private void btnExcluirEnderecoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirEnderecoActionPerformed
        boolean[] acoes = Util.getAcoesUsuario(Principal.ENDERECOS);

        if (!acoes[Util.EXCLUIR]) {
            JOptionPane.showMessageDialog(this.getParent(),
                    "Você não pode efetuar esta exclusão.\n\nMotivo: "
                            + "Usuário sem permissão.");
        } else {
            Util.excluir(endereco, tbEnderecos, this);
        }
    }//GEN-LAST:event_btnExcluirEnderecoActionPerformed

    private void btnIncluirEnderecoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIncluirEnderecoActionPerformed
        boolean[] acoes = Util.getAcoesUsuario(Principal.ENDERECOS);
            
        if (!acoes[Util.INCLUIR]) {
            JOptionPane.showMessageDialog(this.getParent(),
                    "Você não pode efetuar esta inclusão.\n\nMotivo: "
                            + "Usuário sem permissão.");
        } else {
            try {
                salvarPessoa(false);

                Enderecos enderecos = new Enderecos();
                enderecos.setCodPessoa(pessoa.getIdPessoa());
                IFrmManEndereco iFrmManEndereco = new IFrmManEndereco(enderecos);

                this.getParent().remove(iFrmManEndereco);
                this.getParent().add(iFrmManEndereco);

                Util.centralizar(this.getParent(), iFrmManEndereco);

                iFrmManEndereco.setVisible(true);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this.getParent(),
                        "Endereço não pode ser incluído.\n\nMotivo: " + ex);
            }
        }
    }//GEN-LAST:event_btnIncluirEnderecoActionPerformed

    private void btnIncluirTelefoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIncluirTelefoneActionPerformed
        boolean[] acoes = Util.getAcoesUsuario(Principal.TELEFONES);
            
        if (!acoes[Util.INCLUIR]) {
            JOptionPane.showMessageDialog(this.getParent(),
                    "Você não pode efetuar esta inclusão.\n\nMotivo: "
                            + "Usuário sem permissão.");
        } else {        
            try {
                salvarPessoa(false);

                Telefones telefones = new Telefones();
                telefones.setCodPessoa(pessoa.getIdPessoa());
                IFrmManTelefone iFrmManTelefone = new IFrmManTelefone(telefones);

                this.getParent().remove(iFrmManTelefone);
                this.getParent().add(iFrmManTelefone);

                Util.centralizar(this.getParent(), iFrmManTelefone);

                iFrmManTelefone.setVisible(true);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this.getParent(),
                        "Telefone não pode ser incluído.\n\nMotivo: " + ex);
            }
        }
    }//GEN-LAST:event_btnIncluirTelefoneActionPerformed

    private void btnExcluirTelefoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirTelefoneActionPerformed
        boolean[] acoes = Util.getAcoesUsuario(Principal.TELEFONES);

        if (!acoes[Util.EXCLUIR]) {
            JOptionPane.showMessageDialog(this.getParent(),
                    "Você não pode efetuar esta exclusão.\n\nMotivo: "
                            + "Usuário sem permissão.");
        } else {
            Util.excluir(telefone, tbTelefones, this);
        }
    }//GEN-LAST:event_btnExcluirTelefoneActionPerformed

    private void btnExcluirEndereco1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirEndereco1ActionPerformed
        boolean[] acoes = Util.getAcoesUsuario(Principal.ENDERECOS);

        if (!acoes[Util.EXCLUIR]) {
            JOptionPane.showMessageDialog(this.getParent(),
                    "Você não pode efetuar esta exclusão.\n\nMotivo: "
                            + "Usuário sem permissão.");
        } else {
            Util.excluir(endereco, tbEnderecos1, this);
        }
    }//GEN-LAST:event_btnExcluirEndereco1ActionPerformed

    private void btnIncluirEndereco1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIncluirEndereco1ActionPerformed
        boolean[] acoes = Util.getAcoesUsuario(Principal.ENDERECOS);
            
        if (!acoes[Util.INCLUIR]) {
            JOptionPane.showMessageDialog(this.getParent(),
                    "Você não pode efetuar esta inclusão.\n\nMotivo: "
                            + "Usuário sem permissão.");
        } else {        
            try {
                salvarPessoa(false);

                Enderecos enderecos = new Enderecos();
                enderecos.setCodPessoa(pessoa.getIdPessoa());
                IFrmManEndereco iFrmManEndereco = new IFrmManEndereco(enderecos);

                this.getParent().remove(iFrmManEndereco);
                this.getParent().add(iFrmManEndereco);

                Util.centralizar(this.getParent(), iFrmManEndereco);

                iFrmManEndereco.setVisible(true);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this.getParent(),
                        "Endereço não pode ser incluído.\n\nMotivo: " + ex);
            }
        }
    }//GEN-LAST:event_btnIncluirEndereco1ActionPerformed

    private void btnIncluirTelefone1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIncluirTelefone1ActionPerformed
        boolean[] acoes = Util.getAcoesUsuario(Principal.TELEFONES);
            
        if (!acoes[Util.INCLUIR]) {
            JOptionPane.showMessageDialog(this.getParent(),
                    "Você não pode efetuar esta inclusão.\n\nMotivo: "
                            + "Usuário sem permissão.");
        } else {
            try {
                salvarPessoa(false);

                Telefones telefones = new Telefones();
                telefones.setCodPessoa(pessoa.getIdPessoa());
                IFrmManTelefone iFrmManTelefone = new IFrmManTelefone(telefones);

                this.getParent().remove(iFrmManTelefone);
                this.getParent().add(iFrmManTelefone);

                Util.centralizar(this.getParent(), iFrmManTelefone);

                iFrmManTelefone.setVisible(true);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this.getParent(),
                        "Telefone não pode ser incluído.\n\nMotivo: " + ex);
            }
        }
    }//GEN-LAST:event_btnIncluirTelefone1ActionPerformed

    private void btnExcluirTelefone1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirTelefone1ActionPerformed
        boolean[] acoes = Util.getAcoesUsuario(Principal.USUARIOS);
            
        if (!acoes[Util.EXCLUIR]) {
            JOptionPane.showMessageDialog(this.getParent(),
                    "Você não pode efetuar esta exclusão.\n\nMotivo: "
                            + "Usuário sem permissão.");
        } else {
            Util.excluir(telefone, tbTelefones1, this);
        }
    }//GEN-LAST:event_btnExcluirTelefone1ActionPerformed

    private void tbEnderecosMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbEnderecosMouseReleased
        if (evt.getClickCount() == 2) {
            boolean[] acoes = Util.getAcoesUsuario(Principal.ENDERECOS);

            if (!acoes[Util.ALTERAR]) {
                JOptionPane.showMessageDialog(this.getParent(),
                        "Você não pode efetuar esta alteração.\n\nMotivo: "
                                + "Usuário sem permissão.");
            } else {
                try {
                    reescreverObjetoEndereco(tbEnderecos);

                    IFrmManEndereco iFrmManEndereco = new IFrmManEndereco(endereco);

                    this.getParent().remove(iFrmManEndereco);
                    this.getParent().add(iFrmManEndereco);

                    Util.centralizar(this.getParent(), iFrmManEndereco);

                    iFrmManEndereco.setVisible(true);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this.getParent(),
                            "Registro nao pode ser alterado.\n\nMotivo: " + ex);
                }
            }
        }
    }//GEN-LAST:event_tbEnderecosMouseReleased

    private void tbTelefonesMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbTelefonesMouseReleased
        if (evt.getClickCount() == 2) {
            boolean[] acoes = Util.getAcoesUsuario(Principal.TELEFONES);

            if (!acoes[Util.ALTERAR]) {
                JOptionPane.showMessageDialog(this.getParent(),
                        "Você não pode efetuar esta alteração.\n\nMotivo: "
                                + "Usuário sem permissão.");
            } else {
                try {
                    reescreverObjetoTelefone(tbTelefones);

                    IFrmManTelefone iFrmManTelefone = new IFrmManTelefone(telefone);

                    this.getParent().remove(iFrmManTelefone);
                    this.getParent().add(iFrmManTelefone);

                    Util.centralizar(this.getParent(), iFrmManTelefone);

                    iFrmManTelefone.setVisible(true);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this.getParent(),
                            "Registro nao pode ser alterado.\n\nMotivo: " + ex);
                }
            }
        }
    }//GEN-LAST:event_tbTelefonesMouseReleased

    private void tbEnderecos1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbEnderecos1MouseReleased
        if (evt.getClickCount() == 2) {
            boolean[] acoes = Util.getAcoesUsuario(Principal.ENDERECOS);

            if (!acoes[Util.ALTERAR]) {
                JOptionPane.showMessageDialog(this.getParent(),
                        "Você não pode efetuar esta alteração.\n\nMotivo: "
                                + "Usuário sem permissão.");
            } else {            
                try {
                    reescreverObjetoEndereco(tbEnderecos1);

                    IFrmManEndereco iFrmManEndereco = new IFrmManEndereco(endereco);

                    this.getParent().remove(iFrmManEndereco);
                    this.getParent().add(iFrmManEndereco);

                    iFrmManEndereco.setVisible(true);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this.getParent(),
                            "Registro nao pode ser alterado.\n\nMotivo: " + ex);
                }
            }
        }
    }//GEN-LAST:event_tbEnderecos1MouseReleased

    private void tbTelefones1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbTelefones1MouseReleased
        if (evt.getClickCount() == 2) {
            boolean[] acoes = Util.getAcoesUsuario(Principal.TELEFONES);

            if (!acoes[Util.ALTERAR]) {
                JOptionPane.showMessageDialog(this.getParent(),
                        "Você não pode efetuar esta alteração.\n\nMotivo: "
                                + "Usuário sem permissão.");
            } else {            
                try {
                    reescreverObjetoTelefone(tbTelefones1);

                    IFrmManTelefone iFrmManTelefone = new IFrmManTelefone(telefone);

                    this.getParent().remove(iFrmManTelefone);
                    this.getParent().add(iFrmManTelefone);
                    
                    iFrmManTelefone.setVisible(true);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this.getParent(),
                            "Registro nao pode ser alterado.\n\nMotivo: " + ex);
                }
            }
        }
    }//GEN-LAST:event_tbTelefones1MouseReleased

    private void btnAtualizarEnderecos1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtualizarEnderecos1ActionPerformed
        Util.atualizar(endereco, tbEnderecos1);
    }//GEN-LAST:event_btnAtualizarEnderecos1ActionPerformed

    private void btnAtualizarTelefones1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtualizarTelefones1ActionPerformed
        Util.atualizar(telefone, tbTelefones1);
    }//GEN-LAST:event_btnAtualizarTelefones1ActionPerformed

    private void btnAtualizarEnderecosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtualizarEnderecosActionPerformed
        Util.atualizar(endereco, tbEnderecos);
    }//GEN-LAST:event_btnAtualizarEnderecosActionPerformed

    private void btnAtualizarTelefonesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtualizarTelefonesActionPerformed
        Util.atualizar(telefone, tbTelefones);
    }//GEN-LAST:event_btnAtualizarTelefonesActionPerformed

    private void txtRGFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtRGFocusGained
        txtRG.selectAll();
    }//GEN-LAST:event_txtRGFocusGained

    private void txtIEFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtIEFocusGained
        txtIE.selectAll();
    }//GEN-LAST:event_txtIEFocusGained

    private void tablManutencaoStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_tablManutencaoStateChanged
        try {
            if (tablManutencao.getSelectedIndex() == 1) {
                if (pessoa.getTipo() == Pessoas.TIPO_FISICA) {
                    habilitarDesabilitarPainel(pnlDetalhe, true);
                    habilitarDesabilitarPainel(pnlDetalhe1, false);

                    endereco = new Enderecos();
                    endereco.setCodPessoa(Long.parseUnsignedLong(
                            txtCodigo.getText()));
                    endereco.adicionarWhere(new DadosDAO("cod_pessoa", "",
                            txtCodigo.getText(), DadosDAO.TIPO_LONG, 
                            DadosDAO.IS_IGUAL, DadosDAO.NAO_CHAVE));
                    endereco.getEnderecoDAO().popularGridJTable(tbEnderecos);

                    telefone = new Telefones();
                    telefone.setCodPessoa(Long.parseUnsignedLong(
                            txtCodigo.getText()));
                    telefone.adicionarWhere(new DadosDAO("cod_pessoa", "",
                            txtCodigo.getText(), DadosDAO.TIPO_LONG, 
                            DadosDAO.IS_IGUAL, DadosDAO.NAO_CHAVE));
                    telefone.getTelefonesDAO().popularGridJTable(tbTelefones);
                } else {
                    if (pessoa.getTipo() == Pessoas.TIPO_JURIDICA) {
                        habilitarDesabilitarPainel(pnlDetalhe, false);
                        habilitarDesabilitarPainel(pnlDetalhe1, true);

                        endereco = new Enderecos();
                        endereco.setCodPessoa(Long.parseUnsignedLong(
                                txtCodigo.getText()));
                        endereco.adicionarWhere(new DadosDAO("cod_pessoa", "",
                                txtCodigo.getText(), DadosDAO.TIPO_LONG, 
                                DadosDAO.IS_IGUAL, DadosDAO.NAO_CHAVE));
                        endereco.getEnderecoDAO().popularGridJTable(tbEnderecos1);

                        telefone = new Telefones();
                        telefone.setCodPessoa(Long.parseUnsignedLong(
                                txtCodigo.getText()));
                        telefone.adicionarWhere(new DadosDAO("cod_pessoa", "",
                                txtCodigo.getText(), DadosDAO.TIPO_LONG, 
                                DadosDAO.IS_IGUAL, DadosDAO.NAO_CHAVE));
                        telefone.getTelefonesDAO().popularGridJTable(tbTelefones1);
                    }
                }
            }

            pessoaEspecializacao = new PessoasEspecializacoes();
            pessoaEspecializacao.setCodPessoa(txtCodigo.getText().equals("") 
                    ? -1 : Long.parseUnsignedLong(txtCodigo.getText()));
            pessoaEspecializacao.adicionarWhere(new DadosDAO("cod_pessoa", "",
                    txtCodigo.getText(), DadosDAO.TIPO_LONG, DadosDAO.IS_IGUAL, 
                    DadosDAO.NAO_CHAVE));
            pessoaEspecializacao.getPessoaEspecializacaoDAO().popularGridJTable(
                    tbEspecializacoes);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                    "Registro nao pode ser carregado.\n\nMotivo: " + ex);
        }
    }//GEN-LAST:event_tablManutencaoStateChanged

    private void txtCPFFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCPFFocusGained
        txtCPF.selectAll();
    }//GEN-LAST:event_txtCPFFocusGained

    private void txtDataDeNascimentoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDataDeNascimentoFocusGained
        txtDataDeNascimento.selectAll();
    }//GEN-LAST:event_txtDataDeNascimentoFocusGained

    private void txtCNPJFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCNPJFocusGained
        txtCNPJ.selectAll();
    }//GEN-LAST:event_txtCNPJFocusGained

    private void txtDataDeFundacaoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDataDeFundacaoFocusGained
        txtDataDeFundacao.selectAll();
    }//GEN-LAST:event_txtDataDeFundacaoFocusGained

    private void tbEspecializacoesMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbEspecializacoesMouseReleased
        if (evt.getClickCount() == 2) {
            boolean[] acoes = Util.getAcoesUsuario(Principal.ESPECIALIZACOES);

            if (!acoes[Util.ALTERAR]) {
                JOptionPane.showMessageDialog(this.getParent(),
                        "Você não pode efetuar esta alteração.\n\nMotivo: "
                                + "Usuário sem permissão.");
            } else {
                try {
                    reescreverObjetoPessoaEspecializacoes();

                    IFrmManPessoaEspecializacao iFrmManPessoaEspecializacao = 
                            new IFrmManPessoaEspecializacao(pessoaEspecializacao);

                    this.getParent().remove(iFrmManPessoaEspecializacao);
                    this.getParent().add(iFrmManPessoaEspecializacao);

                    iFrmManPessoaEspecializacao.setVisible(true);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this.getParent(),
                            "Registro nao pode ser alterado.\n\nMotivo: " + ex);
                }
            }
        }
    }//GEN-LAST:event_tbEspecializacoesMouseReleased

    private void btnExcluirEspecializacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirEspecializacaoActionPerformed
        boolean[] acoes = Util.getAcoesUsuario(Principal.ESPECIALIZACOES);

        if (!acoes[Util.EXCLUIR]) {
            JOptionPane.showMessageDialog(this.getParent(),
                    "Você não pode efetuar esta exclusão.\n\nMotivo: "
                            + "Usuário sem permissão.");
        } else {
            Util.excluir(pessoaEspecializacao, tbEspecializacoes, this);
        }
    }//GEN-LAST:event_btnExcluirEspecializacaoActionPerformed

    private void btnIncluirEspecializacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIncluirEspecializacaoActionPerformed
        boolean[] acoes = Util.getAcoesUsuario(Principal.ESPECIALIZACOES);

        if (!acoes[Util.INCLUIR]) {
            JOptionPane.showMessageDialog(this.getParent(),
                    "Você não pode efetuar esta inclusão.\n\nMotivo: "
                            + "Usuário sem permissão.");
        } else {
            try {
                salvarPessoa(false);

                PessoasEspecializacoes pessoasEspecializacoes = 
                        new PessoasEspecializacoes();
                pessoasEspecializacoes.setCodPessoa(pessoa.getIdPessoa());
                IFrmManPessoaEspecializacao iFrmManPessoaEspecializacao = 
                        new IFrmManPessoaEspecializacao(pessoasEspecializacoes);

                this.getParent().remove(iFrmManPessoaEspecializacao);
                this.getParent().add(iFrmManPessoaEspecializacao);

                Util.centralizar(this.getParent(), iFrmManPessoaEspecializacao);

                iFrmManPessoaEspecializacao.setVisible(true);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this.getParent(),
                        "Especialização não pode ser incluída.\n\nMotivo: " + ex);
            }
        }
    }//GEN-LAST:event_btnIncluirEspecializacaoActionPerformed

    private void btnAtualizarEspecializacoesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtualizarEspecializacoesActionPerformed
        Util.atualizar(pessoaEspecializacao, tbEspecializacoes);
    }//GEN-LAST:event_btnAtualizarEspecializacoesActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAjudar;
    private javax.swing.JButton btnAtualizarEnderecos;
    private javax.swing.JButton btnAtualizarEnderecos1;
    private javax.swing.JButton btnAtualizarEspecializacoes;
    private javax.swing.JButton btnAtualizarTelefones;
    private javax.swing.JButton btnAtualizarTelefones1;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnConfirmar;
    private javax.swing.JButton btnExcluirEndereco;
    private javax.swing.JButton btnExcluirEndereco1;
    private javax.swing.JButton btnExcluirEspecializacao;
    private javax.swing.JButton btnExcluirTelefone;
    private javax.swing.JButton btnExcluirTelefone1;
    private javax.swing.JButton btnIncluirEndereco;
    private javax.swing.JButton btnIncluirEndereco1;
    private javax.swing.JButton btnIncluirEspecializacao;
    private javax.swing.JButton btnIncluirTelefone;
    private javax.swing.JButton btnIncluirTelefone1;
    private javax.swing.JComboBox cbSituacao;
    private javax.swing.JComboBox cbSituacao1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JLabel lblNome;
    private javax.swing.JLabel lblSobrenome;
    private javax.swing.JMenu mnuAjuda;
    private javax.swing.JMenu mnuArquivos;
    private javax.swing.JMenuBar mnuBarPrincipal;
    private javax.swing.JPanel pnlBotoes;
    private javax.swing.JPanel pnlDetalhe;
    private javax.swing.JPanel pnlDetalhe1;
    private javax.swing.JPanel pnlDetalhe2;
    private javax.swing.JPanel pnlEnderecos;
    private javax.swing.JPanel pnlEnderecos1;
    private javax.swing.JPanel pnlEspecializacoes;
    private javax.swing.JPanel pnlGeral;
    private javax.swing.JPanel pnlTelefones;
    private javax.swing.JPanel pnlTelefones1;
    private javax.swing.JRadioButton rbFeminino;
    private javax.swing.JRadioButton rbMasculino;
    private javax.swing.ButtonGroup rdgSexo;
    private javax.swing.JTabbedPane tablManutencao;
    private javax.swing.JTable tbEnderecos;
    private javax.swing.JTable tbEnderecos1;
    private javax.swing.JTable tbEspecializacoes;
    private javax.swing.JTable tbTelefones;
    private javax.swing.JTable tbTelefones1;
    private javax.swing.JFormattedTextField txtCNPJ;
    private javax.swing.JFormattedTextField txtCPF;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JFormattedTextField txtDataDeFundacao;
    private javax.swing.JFormattedTextField txtDataDeNascimento;
    private javax.swing.JTextField txtIE;
    private javax.swing.JTextField txtNome;
    private javax.swing.JTextField txtNomeDaMae;
    private javax.swing.JTextArea txtObservacoes;
    private javax.swing.JFormattedTextField txtRG;
    private javax.swing.JTextField txtSobrenome;
    // End of variables declaration//GEN-END:variables

    /**
     * @return the pessoa
     */
    public Pessoas getPessoa() {
        return pessoa;
    }

    /**
     * @param pessoa the pessoa to set
     */
    public void setPessoa(Pessoas pessoa) {
        this.pessoa = pessoa;
    }

}
