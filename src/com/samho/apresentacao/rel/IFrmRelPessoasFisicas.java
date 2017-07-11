/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.samho.apresentacao.rel;

import com.samho.apresentacao.IFrmManPessoa;
import com.samho.apresentacao.IFrmPesqPessoas;
import com.samho.dao.ConexoesDB;
import com.samho.dao.DadosDAO;
import com.samho.negocio.Pessoas;
import com.samho.negocio.Principal;
import com.samho.negocio.SituacoesPessoas;
import com.samho.util.Formatacao;
import com.samho.util.ReportUtils;
import com.samho.negocio.rel.RelPessoasFisicas;
import com.samho.util.PesquisaCodDescicao;
import com.samho.util.Util;
import java.awt.Cursor;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 * Classe para visualização de objetos para geração de relatórios.
 *
 * @author Saibel, Sergio Luis
 * @since  date 28/04/2017
 *
 * @version  revision 001.20170428 date 28/04/2017 author Saibel, Sérgio Luis reason
 * Conseguir instanciar um objeto do tipo FrmRelatórios com os atributos e
 * métodos necessários para efetuar estes relatórios.
 */
public class IFrmRelPessoasFisicas extends JInternalFrame {

    // Declaração de atributos
    private final RelPessoasFisicas relPessoasFisicas;
    private final ArrayList<DadosDAO> dadosWhere;

    // Construtor
    public IFrmRelPessoasFisicas() throws Exception {
        initComponents();

        relPessoasFisicas = new RelPessoasFisicas();
        dadosWhere = new ArrayList<>();
        dadosWhere.addAll(relPessoasFisicas.getObjetoDAO().getCondicaoWhere());

        cbSituacao.removeAllItems();
        SituacoesPessoas situacoes = new SituacoesPessoas();
        situacoes.getSituacaoDAO().popularListaJComboBox(cbSituacao,
                situacoes.getDadosCodigo().getCampo(),
                situacoes.getDadosDescricao().getCampo());
        cbSituacao.setSelectedIndex(0);
    }

    public void pesquisarCodigoDescicaoCliente(Pessoas pessoas) {
        Util.pesquisarCodigoDescicao(pessoas, txtCodigo, txtDescricao);
    }

    /**
     * Obtendo o arquivo do relatório.
     *
     * Utilizando um InputStream para obter o arquivo que está dentro do
     * projeto. Fazendo isso, gera problema quando o projeto é empacotado em um
     * .jar.
     *
     * O caminho do .jasper inicia com /nomeDoPacote, ou seja, a raiz da
     * localização das classes compiladas do no projeto.
     *
     * @throws net.sf.jasperreports.engine.JRException
     */
    public void abrirRelatorioPessoasFisicas() throws JRException {
        URL web = getClass().getResource("/com/samho/rel/");

        /**
         * Para criar um mapa de parâmetros
         *
         * ** Map parametros = new HashMap();
         *
         * Insere o parâmetro primeiroNome no mapa, com o valor F% ou seja,
         * todos que tenham primeiro nome começando com a letra F.
         *
         * ** parametros.put("primeiroNome", "F%");
         *
         * No relatório, inserir um campo dinâmico (Text Field) com seu valor
         * para “$P{primeiroNome}” (sem as aspas). Isso é para mostrar o valor
         * do parâmetro no relatório. *
         */
        Map parametros = new HashMap();
        parametros.put("REPORT_CONNECTION", ConexoesDB.getInstance().getConnection());  
        parametros.put("SUBREPORT_DIR", web.toString());
        
        InputStream inputStream = getClass().getResourceAsStream(
                "/com/samho/rel/RelPessoasFisicas.jasper");

        ReportUtils.openReport("RelPessoasFisicas", inputStream, parametros,
                new JRBeanCollectionDataSource(getListaObjeto()));
    }
    
    private List getListaObjeto(){
        List retorno = new ArrayList();

        ArrayList<DadosDAO> listaDadosCampos = new ArrayList();
        listaDadosCampos.addAll(relPessoasFisicas.getObjetoDAO().popularLista());

        for (int i = 0; i < listaDadosCampos.size();i++) {
            // Monta um objeto Contrato para enviar para o relatório
            RelPessoasFisicas relPessoaFisica = new RelPessoasFisicas();
            relPessoaFisica.setId_pessoa(
                    Long.parseUnsignedLong(listaDadosCampos.get(i++).getValor()));
            relPessoaFisica.setNomecompleto(listaDadosCampos.get(i++).getValor());
            relPessoaFisica.setCpf(
                    Long.parseUnsignedLong(listaDadosCampos.get(i++).getValor()));
            relPessoaFisica.setRg(
                    Long.parseUnsignedLong(listaDadosCampos.get(i++).getValor()));
            relPessoaFisica.setNome_mae(listaDadosCampos.get(i++).getValor());
            relPessoaFisica.setData_nascimento(Formatacao.ajustaData(
                    listaDadosCampos.get(i++).getValor(), Formatacao.DATA_DMA));
            relPessoaFisica.setSexo(listaDadosCampos.get(i++).getValor());
            relPessoaFisica.setSituacao(listaDadosCampos.get(i++).getValor());
            relPessoaFisica.setObservacao(listaDadosCampos.get(i).getValor());

            // Adiciona o objeto a uma lista
            retorno.add(relPessoaFisica);
        }
        
        return retorno;
    }

    private void efetuarPesquisa() {
        relPessoasFisicas.removerWhere();

        // id_pessoa
        if (!txtCodigo.getText().equals("")) {
            relPessoasFisicas.adicionarWhere(new DadosDAO("id_pessoa", 
                    "Cód. Pessoa", txtCodigo.getText(), DadosDAO.TIPO_LONG, 
                    DadosDAO.IS_IGUAL, DadosDAO.COMPLETO));
        }
        
        // nomecompleto
        if (!txtDescricao.getText().equals("")) {
            relPessoasFisicas.adicionarWhere(new DadosDAO("nomecompleto", 
                    "Nome Pessoa", txtDescricao.getText(), DadosDAO.TIPO_STRING,
                    DadosDAO.IS_IGUAL, DadosDAO.PARCIAL));
        }

        //data_nascimento inicial
        if (!Formatacao.removerFormatacao(txtDataNascIni.getText()).equals("")) {
            relPessoasFisicas.adicionarWhere(new DadosDAO("data_nascimento",
                    "Data Nasc. Ini.", txtDataNascIni.getText(), 
                    DadosDAO.TIPO_DATE, DadosDAO.IS_MAIOR_IGUAL, DadosDAO.COMPLETO));
        }
        
        //data_nascimento final
        if (!Formatacao.removerFormatacao(txtDataNascFin.getText()).equals("")) {
            relPessoasFisicas.adicionarWhere(new DadosDAO("data_nascimento",
                    "Data Nasc. Fin.", txtDataNascIni.getText(), 
                    DadosDAO.TIPO_DATE, DadosDAO.IS_MENOR_IGUAL, DadosDAO.COMPLETO));
        }

        // situacao
        if (cbSituacao.getSelectedIndex() > 0) {
            PesquisaCodDescicao pesquisaCodDescicao = 
                    (PesquisaCodDescicao) cbSituacao.getSelectedItem();
            relPessoasFisicas.adicionarWhere(new DadosDAO("situacao", "Situação",
                    pesquisaCodDescicao.getDescricao(), DadosDAO.TIPO_STRING,
                    DadosDAO.IS_IGUAL, DadosDAO.COMPLETO));
        }

        // Adiciona as demais condições padrão
        relPessoasFisicas.adicionarWhere(dadosWhere);
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
        tablManutencao = new javax.swing.JTabbedPane();
        pnlGeral = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        lblLocalizarAcao = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtDescricao = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtDataNascIni = new javax.swing.JFormattedTextField();
        jLabel8 = new javax.swing.JLabel();
        txtDataNascFin = new javax.swing.JFormattedTextField();
        jLabel7 = new javax.swing.JLabel();
        cbSituacao = new javax.swing.JComboBox();
        pnlBotoes = new javax.swing.JPanel();
        btnConfirmar = new javax.swing.JButton();
        btnAjudar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        mnuBarPrincipal = new javax.swing.JMenuBar();
        mnuArquivos = new javax.swing.JMenu();
        mnuAjuda = new javax.swing.JMenu();

        jMenu1.setText("jMenu1");

        setTitle("Relatório de Pessoas");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setMaximumSize(new java.awt.Dimension(515, 277));
        setMinimumSize(new java.awt.Dimension(515, 277));
        setPreferredSize(new java.awt.Dimension(515, 277));
        try {
            setSelected(true);
        } catch (java.beans.PropertyVetoException e1) {
            e1.printStackTrace();
        }
        getContentPane().setLayout(null);

        tablManutencao.setMinimumSize(new java.awt.Dimension(713, 245));
        tablManutencao.setName(""); // NOI18N

        pnlGeral.setLayout(null);

        jLabel1.setText("Cód. Pessoa");
        pnlGeral.add(jLabel1);
        jLabel1.setBounds(54, 30, 60, 14);

        txtCodigo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        pnlGeral.add(txtCodigo);
        txtCodigo.setBounds(118, 27, 70, 20);

        lblLocalizarAcao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/localizar.png"))); // NOI18N
        lblLocalizarAcao.setToolTipText("Localizar um tipo de endereço já cadastrado.");
        lblLocalizarAcao.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblLocalizarAcao.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                lblLocalizarAcaoMouseReleased(evt);
            }
        });
        pnlGeral.add(lblLocalizarAcao);
        lblLocalizarAcao.setBounds(193, 25, 20, 20);

        jLabel3.setText("Nome");
        pnlGeral.add(jLabel3);
        jLabel3.setBounds(88, 56, 27, 14);

        txtDescricao.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        pnlGeral.add(txtDescricao);
        txtDescricao.setBounds(119, 53, 304, 20);

        jLabel6.setText("Data Nasc. Ini.");
        pnlGeral.add(jLabel6);
        jLabel6.setBounds(43, 82, 72, 14);

        try {
            txtDataNascIni.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtDataNascIni.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDataNascIniFocusGained(evt);
            }
        });
        pnlGeral.add(txtDataNascIni);
        txtDataNascIni.setBounds(119, 79, 70, 20);

        jLabel8.setText("Data Nasc. Fin.");
        pnlGeral.add(jLabel8);
        jLabel8.setBounds(270, 83, 75, 14);

        try {
            txtDataNascFin.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtDataNascFin.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDataNascFinFocusGained(evt);
            }
        });
        pnlGeral.add(txtDataNascFin);
        txtDataNascFin.setBounds(350, 80, 70, 20);

        jLabel7.setText("Situação");
        pnlGeral.add(jLabel7);
        jLabel7.setBounds(74, 108, 41, 14);

        cbSituacao.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        pnlGeral.add(cbSituacao);
        cbSituacao.setBounds(119, 105, 169, 20);

        tablManutencao.addTab("Geral", pnlGeral);

        getContentPane().add(tablManutencao);
        tablManutencao.setBounds(0, 0, 500, 183);

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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 280, Short.MAX_VALUE)
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
        pnlBotoes.setBounds(0, 187, 500, 38);

        mnuArquivos.setText("Arquivo");
        mnuBarPrincipal.add(mnuArquivos);

        mnuAjuda.setText("Ajuda");
        mnuBarPrincipal.add(mnuAjuda);

        setJMenuBar(mnuBarPrincipal);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtDataNascIniFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDataNascIniFocusGained
        Formatacao.reformatarData(txtDataNascIni);
    }//GEN-LAST:event_txtDataNascIniFocusGained

    private void btnAjudarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAjudarActionPerformed
        Util.ajudar();
    }//GEN-LAST:event_btnAjudarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        Util.cancelar(this);
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirmarActionPerformed
        try {
            // Muda o cursor
            getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));  
        
            efetuarPesquisa();

            abrirRelatorioPessoasFisicas();
            
            // Muda cursor
            getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        } catch (JRException e) {
            System.out.printf(e.toString());
            JOptionPane.showMessageDialog(this.getParent(),
                    "Relatório não pode ser gerado.\n\nMotivo: " + e);
        }
    }//GEN-LAST:event_btnConfirmarActionPerformed

    private void txtDataNascFinFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDataNascFinFocusGained
        Formatacao.reformatarData(txtDataNascIni);
    }//GEN-LAST:event_txtDataNascFinFocusGained

    private void lblLocalizarAcaoMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblLocalizarAcaoMouseReleased
        boolean[] acoes = Util.getAcoesUsuario(Principal.PESSOAS_FISICAS);

        if (!acoes[Util.CONSULTAR]) {
            JOptionPane.showMessageDialog(this.getParent(),
                "Você não pode efetuar esta consultadas.\n\nMotivo: "
                + "Usuário sem permissão.");
        } else {
            try {
                IFrmPesqPessoas iFrmPesqPessoas = new IFrmPesqPessoas(
                    new IFrmManPessoa(new Pessoas(Pessoas.TIPO_FISICA)),
                            txtCodigo, txtDescricao);

                this.getParent().remove(iFrmPesqPessoas);
                this.getParent().add(iFrmPesqPessoas);

                Util.centralizar(this.getParent(), iFrmPesqPessoas);

                iFrmPesqPessoas.setVisible(true);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this.getParent(),
                    "Ações não podem ser consultadas.\n\nMotivo: " + e);
            }
        }
    }//GEN-LAST:event_lblLocalizarAcaoMouseReleased

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAjudar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnConfirmar;
    private javax.swing.JComboBox cbSituacao;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JLabel lblLocalizarAcao;
    private javax.swing.JMenu mnuAjuda;
    private javax.swing.JMenu mnuArquivos;
    private javax.swing.JMenuBar mnuBarPrincipal;
    private javax.swing.JPanel pnlBotoes;
    private javax.swing.JPanel pnlGeral;
    private javax.swing.JTabbedPane tablManutencao;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JFormattedTextField txtDataNascFin;
    private javax.swing.JFormattedTextField txtDataNascIni;
    private javax.swing.JTextField txtDescricao;
    // End of variables declaration//GEN-END:variables
}
