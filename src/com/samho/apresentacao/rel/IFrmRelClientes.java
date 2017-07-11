/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.samho.apresentacao.rel;

import com.samho.dao.DadosDAO;
import com.samho.negocio.Clientes;
import com.samho.negocio.SituacoesClientes;
import com.samho.util.Formatacao;
import com.samho.util.ReportUtils;
import com.samho.negocio.rel.RelClientes;
import com.samho.util.PesquisaCodDescicao;
import com.samho.util.Util;
import java.io.InputStream;
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
 * @since  date 18/04/2014
 *
 * @version  revision 001.20140418 date 18/04/2014 author Saibel, Sérgio Luis reason
 * Conseguir instanciar um objeto do tipo FrmRelatórios com os atributos e
 * métodos necessários para efetuar estes relatórios.
 */
public class IFrmRelClientes extends JInternalFrame {

    // Declaração de atributos
    private final RelClientes viewCliente;
    private final ArrayList<DadosDAO> dadosWhere;

    // Construtor
    public IFrmRelClientes() throws Exception {
        initComponents();

        viewCliente = new RelClientes();
        dadosWhere = new ArrayList<>();
        dadosWhere.addAll(viewCliente.getObjetoDAO().getCondicaoWhere());

        cbSituacao.removeAllItems();
        SituacoesClientes situacoes = new SituacoesClientes();
        situacoes.getSituacaoDAO().popularListaJComboBox(cbSituacao,
                situacoes.getDadosCodigo().getCampo(),
                situacoes.getDadosDescricao().getCampo());
        cbSituacao.setSelectedIndex(0);
    }

    public void pesquisarCodigoDescicaoCliente(Clientes cliente) {
        Util.pesquisarCodigoDescicao(cliente, txtCodigo, txtDescricao);
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
    public void abrirRelatorioClientes() throws JRException {
        List lista = new ArrayList();

        // Monta um objeto para enviar para o relatório
        ArrayList<DadosDAO> listaDadosCampos = new ArrayList();
        listaDadosCampos.addAll(viewCliente.getObjetoDAO().popularLista());

        int i = 0;
        RelClientes viewClientes = new RelClientes();
        viewClientes.setId_cliente(Integer.parseInt(listaDadosCampos.get(i++).getValor()));
        viewClientes.setNome(listaDadosCampos.get(i++).getValor());
        viewClientes.setData_cadastro(listaDadosCampos.get(i++).getValor());
        viewClientes.setSituacao(listaDadosCampos.get(i).getValor());

        lista.add(viewClientes);

        InputStream inputStream = getClass().getResourceAsStream(
                "/com/page/relatorios/RelClientes.jasper");

        /**
         * Para criar um mapa de parâmetros
         *
         * ** Map parametros = new HashMap();
         *
         * Insere o parâmetro primeiroNome no mapa, com o valor F% ou seja,
         * todos os clientes que tenham primeiro nome começando com a letra F.
         *
         * ** parametros.put("primeiroNome", "F%");
         *
         * No relatório, inserir um campo dinâmico (Text Field) com seu valor
         * para “$P{primeiroNome}” (sem as aspas). Isso é para mostrar o valor
         * do parâmetro no relatório. *
         */
        Map parametros = new HashMap();
        parametros.put("primeiroNome", "F%");

        ReportUtils.openReport("RelClientes", inputStream, parametros,
                new JRBeanCollectionDataSource(lista));
    }

    private void efetuarPesquisa() {
        viewCliente.removerWhere();

        if (!txtCodigo.getText().equals("")) {
            viewCliente.adicionarWhere(new DadosDAO("id_cliente", "Cód. Cliente", 
                    txtCodigo.getText(), DadosDAO.TIPO_INTEGER, DadosDAO.IS_IGUAL,
                    DadosDAO.COMPLETO));
        }

        if (!txtDescricao.getText().equals("")) {
            viewCliente.adicionarWhere(new DadosDAO("nome", "Nome Cliente", 
                    txtDescricao.getText(), DadosDAO.TIPO_STRING,
                    DadosDAO.IS_IGUAL, DadosDAO.PARCIAL));
        }

        if (!Formatacao.removerFormatacao(txtDataCadastro.getText()).equals("")) {
            viewCliente.adicionarWhere(new DadosDAO("data_cadastro",
                    "Data Cadastro.", txtDataCadastro.getText(),
                    DadosDAO.TIPO_DATE, DadosDAO.IS_IGUAL, DadosDAO.COMPLETO));
        }

        if (cbSituacao.getSelectedIndex() > 0) {
            PesquisaCodDescicao pesquisaCodDescicao = 
                    (PesquisaCodDescicao) cbSituacao.getSelectedItem();
            viewCliente.adicionarWhere(new DadosDAO("situacao", "Situação",
                    pesquisaCodDescicao.getDescricao(), DadosDAO.TIPO_INTEGER,
                    DadosDAO.IS_IGUAL, DadosDAO.COMPLETO));
        }

        viewCliente.adicionarWhere(dadosWhere);
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
        pnlPesquisarCampos = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtDescricao = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtDataCadastro = new javax.swing.JFormattedTextField();
        jLabel7 = new javax.swing.JLabel();
        cbSituacao = new javax.swing.JComboBox();
        pnlBotoes = new javax.swing.JPanel();
        btnAjudar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnConfirmar = new javax.swing.JButton();

        jMenu1.setText("jMenu1");

        setTitle("Relatório de Clientes");
        setMaximumSize(new java.awt.Dimension(491, 256));
        setMinimumSize(new java.awt.Dimension(491, 256));
        setPreferredSize(new java.awt.Dimension(491, 256));
        try {
            setSelected(true);
        } catch (java.beans.PropertyVetoException e1) {
            e1.printStackTrace();
        }

        tablManutencao.setMinimumSize(new java.awt.Dimension(713, 245));
        tablManutencao.setName(""); // NOI18N

        pnlPesquisarCampos.setBorder(javax.swing.BorderFactory.createTitledBorder("Filtros para consulta"));

        jLabel1.setText("Cód. Cliente");

        txtCodigo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jLabel3.setText("Nome");

        txtDescricao.setHorizontalAlignment(javax.swing.JTextField.LEFT);

        jLabel6.setText("Data Cadastro");

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

        jLabel7.setText("Situação");

        cbSituacao.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout pnlPesquisarCamposLayout = new javax.swing.GroupLayout(pnlPesquisarCampos);
        pnlPesquisarCampos.setLayout(pnlPesquisarCamposLayout);
        pnlPesquisarCamposLayout.setHorizontalGroup(
            pnlPesquisarCamposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPesquisarCamposLayout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(pnlPesquisarCamposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlPesquisarCamposLayout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlPesquisarCamposLayout.createSequentialGroup()
                        .addGroup(pnlPesquisarCamposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlPesquisarCamposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtDataCadastro, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, 304, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbSituacao, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(43, Short.MAX_VALUE))
        );
        pnlPesquisarCamposLayout.setVerticalGroup(
            pnlPesquisarCamposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPesquisarCamposLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlPesquisarCamposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlPesquisarCamposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlPesquisarCamposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6)
                    .addComponent(txtDataCadastro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlPesquisarCamposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(cbSituacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnlGeralLayout = new javax.swing.GroupLayout(pnlGeral);
        pnlGeral.setLayout(pnlGeralLayout);
        pnlGeralLayout.setHorizontalGroup(
            pnlGeralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlGeralLayout.createSequentialGroup()
                .addComponent(pnlPesquisarCampos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        pnlGeralLayout.setVerticalGroup(
            pnlGeralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlGeralLayout.createSequentialGroup()
                .addComponent(pnlPesquisarCampos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 2, Short.MAX_VALUE))
        );

        tablManutencao.addTab("Geral", pnlGeral);

        pnlBotoes.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        pnlBotoes.setName("pnlBotao"); // NOI18N
        pnlBotoes.setPreferredSize(new java.awt.Dimension(229, 33));

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlBotoes, javax.swing.GroupLayout.DEFAULT_SIZE, 475, Short.MAX_VALUE)
            .addComponent(tablManutencao, javax.swing.GroupLayout.PREFERRED_SIZE, 475, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(tablManutencao, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(pnlBotoes, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtDataCadastroFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDataCadastroFocusGained
        Formatacao.reformatarData(txtDataCadastro);
    }//GEN-LAST:event_txtDataCadastroFocusGained

    private void btnAjudarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAjudarActionPerformed
        Util.ajudar();
    }//GEN-LAST:event_btnAjudarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        Util.cancelar(this);
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirmarActionPerformed
        try {
            efetuarPesquisa();

            abrirRelatorioClientes();
        } catch (JRException e) {
            System.out.printf(e.toString());
            JOptionPane.showMessageDialog(this.getParent(),
                    "Relatório não pode ser gerado.\n\nMotivo: " + e);
        }
    }//GEN-LAST:event_btnConfirmarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAjudar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnConfirmar;
    private javax.swing.JComboBox cbSituacao;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JPanel pnlBotoes;
    private javax.swing.JPanel pnlGeral;
    private javax.swing.JPanel pnlPesquisarCampos;
    private javax.swing.JTabbedPane tablManutencao;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JFormattedTextField txtDataCadastro;
    private javax.swing.JTextField txtDescricao;
    // End of variables declaration//GEN-END:variables
}
