/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.samho.util;

import com.samho.dao.DadosDAO;
import com.samho.negocio.AcoesUsuarios;
import com.samho.negocio.InterfaceObj;
import com.samho.negocio.Principal;
import java.awt.Component;
import java.awt.Container;
import java.beans.PropertyVetoException;
import java.io.File;
import java.io.IOException;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * Classe Útil.
 *
 * @author Saibel, Sergio Luis
 * @since  date 31/03/2017
 *
 * @version  revision 001.20170331 date 31/03/2017 author Saibel, Sergio Luís reason
 * Guardar métodos importantes para o andamento do sistema.
 */
public class Util {
    public static final int INCLUIR = 0;
    public static final int ALTERAR = 1;
    public static final int EXCLUIR = 2;
    public static final int CONSULTAR = 3;

    /**
     * Varre om container a procura de campos de edição, caso os encontre limpa
     * os valores contidos nos mesmos.
     *
     * @param container Container que deve ser varrido a porcura de campos de
     * edição.
     */
    public static void limparCampos(Container container) {
        Component components[] = container.getComponents();
        for (Component component : components) {
            if (component instanceof JFormattedTextField) {
                JFormattedTextField field = (JFormattedTextField) component;
                field.setValue(null);
            } else if (component instanceof JTextField) {
                JTextField field = (JTextField) component;
                field.setText("");
            } else if (component instanceof JTextArea) {
                JTextArea area = (JTextArea) component;
                area.setText("");
            } else if (component instanceof Container) {
                limparCampos((Container) component);
            }
        }
    }

    /**
     * Centraliza o JInternalFrame com base no parent.
     *
     * @param container Container que deve ser utilizado como base para
     * centralizar outros frames.
     * @param jInternalFrame Frame a ser centralizado no container.
     */
    public static void centralizar(Container container,
            JInternalFrame jInternalFrame) {
        jInternalFrame.setLocation(
                container.getWidth() / 2 - jInternalFrame.getWidth() / 2,
                container.getHeight() / 2 - jInternalFrame.getHeight() / 2);
    }
    
    /**
     * Centraliza o JInternalFrame com base no parent.
     *
     * @param jFrame: Frame de ajuda.
     */
    public static void centralizar(JFrame jFrame) {
        jFrame.setLocationRelativeTo(null);
    }

    /**
     * Maximiza o frame.
     *
     * @param jInternalFrame Frame a ser maximizado.
     * @throws java.beans.PropertyVetoException
     */
    public static void maximizar(JInternalFrame jInternalFrame)
            throws PropertyVetoException {
        jInternalFrame.setMaximum(true);
    }

    /**
     * Converte horas em segundos.
     *
     * @param valor String deve ter o valor passado no formato HH:mm:ss
     *
     * @return Valor em formato double que representa um horário válido no
     * formato de segundos
     */
    public static int converterHorarioEmSegundos(String valor) {
        String[] arrayHora = valor.split(":");
        int retorno = 0;

        retorno += Double.valueOf(arrayHora[0]) * 3600;
        retorno += Double.valueOf(arrayHora[1]) * 60;
        retorno += Double.valueOf(arrayHora[2]);

        return retorno;
    }

    /**
     * Converte segundos em HH:mm:ss.
     *
     * @param valor Double valor passado em formato double
     *
     * @return Valor em formato string que representa um horário válido no
     * formato HH:mm:ss
     */
    public static String converterHorarioDoubleEmHHmmss(double valor) {
        long s = Math.round(valor * 3600);
        long h = s / 3600;
        long m = (s - 3600 * h) / 60;
        s = s % 60;

        String sh = h < 10 ? "0" + h : String.valueOf(h);
        String sm = m < 10 ? "0" + m : String.valueOf(m);
        String ss = s < 10 ? "0" + s : String.valueOf(s);

        return sh + ":" + sm + ":" + ss;
    }

    /**
     * Converte HH:mm:ss em dopuble.
     *
     * @param valor String deve ter o valor passado no formato HH:mm:ss
     *
     * @return Valor em formato double que representa um horário válido no
     * formato hh,mm
     */
    public static double converterHorarioHHmmssEmDouble(String valor) {
        double segundos = converterHorarioEmSegundos(valor);
        double retorno = (Math.floor(segundos / 60) + ((segundos % 60) / 60)) / 60;
        return retorno;
    }

    /**
     * Calcula data a partir do tempo e data inicial.
     *
     * @param dataIni String deve ter o valor passado no formato dd/mm/yyyy
     * @param tempo String deve ter o valor passado no formato HH:mm:ss
     *
     * @return Valor em formato String que representa uma nova data acrecida
     * pelo tempo informado no formato dd/mm/yyyy
     */
    public static String calcularProximaDataDMA(String dataIni, String tempo) {
        String[] data = dataIni.split("/");

        int tempoEmSegundos = (int) converterHorarioEmSegundos(tempo);

        return calcularProximaDataDMA(
                Integer.parseInt(data[0]) + tempoEmSegundos / 3600 / 9,
                Integer.parseInt(data[1]), Integer.parseInt(data[2]));
    }

    /**
     * Calcula data a partir do dma.
     *
     * @param d int dia a iniciar o calculo
     * @param m mês dia a iniciar o calculo
     * @param a ano dia a iniciar o calculo
     *
     * @return Valor em formato String que representa uma nova data acrecida
     * pelo tempo informado no formato dd/mm/yyyy
     */
    public static String calcularProximaDataDMA(int d, int m, int a) {
        int[] dias = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

        if (a % 4 == 0 && (a % 100 != 0 || a % 400 == 0)) {
            dias[1] = 29;
        }

        while (d > dias[m - 1]) {
            d = d - dias[m - 1];
            m++;
            while (m > 12) {
                m = m - 12;
                a++;
            }
        }

        String sd = d < 9 ? "0" + d : String.valueOf(d);
        String sm = m < 9 ? "0" + m : String.valueOf(m);
        String sa = String.valueOf(a);

        if (a < 999) {
            if (a > 99) {
                sa = "0" + a;
            } else {
                if (a > 9) {
                    sa = "00" + a;
                } else {
                    sa = "000" + a;
                }
            }
        }

        return sd + "/" + sm + "/" + sa;
    }

    /**
     * Habilita ou desabilita todos os componentes do painel com excessão dos
     * labels
     *
     * @param jPanel painel que está sendo habilitado ou desabilitado.
     * @param habilitar <i>true</i> habilita os componentesdo painel.
     */
    public static void habilitarDesabilitarPainel(JPanel jPanel, boolean habilitar) {
        for (int i = 0; i < jPanel.getComponentCount(); i++) {
            if (jPanel.getComponent(i) instanceof JPanel) {
                habilitarDesabilitarPainel(
                        (JPanel) jPanel.getComponent(i), habilitar);
            }

            if (!(jPanel.getComponent(i) instanceof JLabel)) {
                jPanel.getComponent(i).setEnabled(habilitar);
            }

        }
    }

    /**
     * Caminho da Aplicação
     *
     * @return Retorna o caminho onde a aplicaçãoesta rodando.
     * @throws IOException erro se houver problemas com o arquivo.
     */
    public static String getAppPath() throws IOException {
        String retorno;
        File f = new File("");

        retorno = f.getCanonicalPath();

        return retorno;
    }
    
    /**
     * Incluir.
     *
     * Procedimento genérico com a finalidadde de incluir objetos no banco.
     * @param parent: Form ao qual será ancorado o JInternalFrame de manutenção.
     * @param iFrmManutencao: JInternalFrame de manutenção.
     * @return Retorna um JInternalFrame de manutenção para incluir objetos.
     */
    public static String incluir(Component parent,
            JInternalFrame iFrmManutencao) {
        return criarManutencao(parent, iFrmManutencao);
    }

    /**
     * Alterar.
     *
     * Procedimento genérico com a finalidadde de alterar objetos no banco.
     * @param parent: Form ao qual será ancorado o JInternalFrame de manutenção.
     * @param iFrmManutencao: JInternalFrame de manutenção.
     * @return Retorna um JInternalFrame de manutenção para alterar objetos.
     */
    public static String alterar(Component parent,
            JInternalFrame iFrmManutencao) {
        return criarManutencao(parent, iFrmManutencao);
    }

    /**
     * Criar Manutenção.
     *
     * Procedimento genérico com a finalidadde de criar JInternalFrame genérico.
     * @param parent: Form ao qual será ancorado o JInternalFrame.
     * @param iFrmManutencao: JInternalFrame.
     * @return Retorna um JInternalFrame genérico de manutenção.
     */
    private static String criarManutencao(Component parent,
            JInternalFrame iFrmManutencao) {
        String retorno = null;

        try {
            parent.getParent().remove(iFrmManutencao);
            parent.getParent().add(iFrmManutencao);

            Util.centralizar(parent.getParent(), iFrmManutencao);

            iFrmManutencao.setVisible(true);
        } catch (Exception ex) {
            retorno = ex.toString();
        }

        return retorno;
    }
    
    /**
     * Excluir
     * 
     * Excluir objetos do banco de dados.
     * 
     * @param objeto Objeto a ser excluído
     * @param jTable JTable onde sete objeto se encontra para poder reescrever.
     * @param component Componente pai para ceder o Parent.
     */
    public static void excluir(InterfaceObj objeto, JTable jTable,
            Component component) {
        if (jTable.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(component.getParent(),
                    "Você deve selecionar um registro para ser excluido.");
        } else {
            int selectedOption
                    = JOptionPane.showConfirmDialog(null,
                            "Deseja realmente excluir o registro?",
                            "Sistema informa:", JOptionPane.YES_NO_OPTION);
            if (selectedOption == JOptionPane.YES_OPTION) {
                String msg = objeto.getObjetoDAO().excluir(
                        String.valueOf(jTable.getValueAt(jTable.getSelectedRow(), 0)));
                if (msg == null) {
                    JOptionPane.showMessageDialog(component.getParent(),
                            "Registro excluído com sucesso.");
                } else {
                    JOptionPane.showMessageDialog(component.getParent(),
                            "Registro nao pode ser excluído.\n\nMotivo: " + msg);
                }
            }
        }
    }

    /**
     * Atualizar.
     *
     * Procedimento genérico com a finalidadde de alterar um objeto genérico.
     * @param objeto: Objeto genérico baseado na interface de objetos.
     * @param jTable: Tabela na qual o objeto deverá ser apresentado.
     */
    public static void atualizar(InterfaceObj objeto, JTable jTable) {
        objeto.getObjetoDAO().popularGridJTable(jTable);
    }

    /**
     * Ajudar.
     *
     * Procedimento específico com a finalidadde de ajudar o usuário.
     * @param urlString Endereço a página html
     */
    public static void ajudar(String urlString) {
        displayHtmlBowser(urlString);
    }
    
    /**
     * Ajudar.
     *
     * Procedimento genérico com a finalidadde de ajudar o usuário.
     */
    public static void ajudar() {
        displayHtmlBowser("index");
    }
    
    private static void displayHtmlBowser(String urlString){
        try{
            Runtime.getRuntime().exec("rundll32 SHELL32.DLL, ShellExec_RunDLL " +
                    new File("..").getCanonicalPath() + "\\help\\" + 
                    urlString + ".html");
        } catch (IOException e){
        }
    }
    
    private static void displayHtml(String urlString){
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container con = frame.getContentPane();
        JEditorPane jep = new JEditorPane();
        JScrollPane jsp = new JScrollPane(jep);
        con.add(jsp);
        jep.setContentType("text/html");
        
        try{
            jep.setPage(
                    new File("..").getCanonicalPath() + "\\help\\" + 
                            urlString + ".html");
        }
            catch (IOException e){
        }
        frame.setBounds(50, 50, 600, 800);
        frame.setVisible(true);
    }

    /**
     * Aplicar.
     *
     * Procedimento para aplicar alterações sem salvar no banco.
     * @return Retorna uma JInternalFrame com informações do sistema
     */
    public static String aplicar() {
        return "Aplicar não disponível para esta versão.";
    }

    /**
     * Verificar pesquisa campo ID.
     *
     * Procedimento para efetuar pesquisa atravéz de campo ID.
     * @param objeto: Objeto genérico baseado na interface de objetos.
     * @param jTextField: Campo para retornar a informação solicitada.
     * @return 
     *      Retorna null caso consiga efetuar a pesquisa ou uma menságem com o 
     *      problema encontrado. 
     */
    public static String verificarPesquisarCampoID(InterfaceObj objeto,
            JTextField jTextField) {
        String retorno = null;

        try {
            int codigo = Integer.valueOf(jTextField.getText());

            objeto.adicionarWhere(new DadosDAO(
                    objeto.getDadosCodigo().getCampo(), "", 
                    String.valueOf(codigo), DadosDAO.TIPO_INTEGER, 
                    DadosDAO.IS_IGUAL, DadosDAO.IS_CHAVE));
        } catch (NumberFormatException e) {
            retorno = "Informe somente números neste campo.";
            jTextField.setText("");
        }

        return retorno;
    }

    /**
     * Verificar pesquisa campo Descrição.
     *
     * Procedimento para efetuar pesquisa atravéz de campo descricao.
     * @param objeto: Objeto genérico baseado na interface de objetos.
     * @param jTextField: Campo para retornar a informação solicitada.
     */
    public static void verificarPesquisarCampoDescricao(InterfaceObj objeto,
            JTextField jTextField) {
        objeto.adicionarWhere(new DadosDAO(
                objeto.getDadosDescricao().getCampo(), "", jTextField.getText(),
                DadosDAO.TIPO_STRING, DadosDAO.IS_IGUAL, DadosDAO.NAO_CHAVE));
    }
    
    /**
     * Verificar pesquisa objeto ativo.
     *
     * Procedimento para efetuar pesquisa atravéz de campo descricao.
     * @param objeto: Objeto genérico baseado na interface de objetos.
     * @param jCheckBox: Campo para retornar a informação solicitada.
     */
    public static void verificarPesquisarObjetoAtivo(InterfaceObj objeto, 
            JCheckBox jCheckBox) {
        objeto.adicionarWhere(new DadosDAO("ativo", "",
                String.valueOf(jCheckBox.isSelected()), DadosDAO.TIPO_BOOLEAN,
                DadosDAO.IS_IGUAL, DadosDAO.NAO_CHAVE));
    }

    /**
     * Verificar pesquisa objeto boolean.
     *
     * Procedimento para efetuar pesquisa atravéz de campo descricao.
     * @param objeto: Objeto genérico baseado na interface de objetos.
     * @param campoBoolean Campo de procura no banco
     * @param jCheckBox: Campo para retornar a informação solicitada.
     */
    public static void verificarPesquisarObjetoAtivo(InterfaceObj objeto, 
            String campoBoolean, JCheckBox jCheckBox) {
        objeto.adicionarWhere(new DadosDAO(campoBoolean, "",
                String.valueOf(jCheckBox.isSelected()), DadosDAO.TIPO_BOOLEAN,
                DadosDAO.IS_IGUAL, DadosDAO.NAO_CHAVE));
    }
    
    /**
     * Verificar pesquisa campo Código e Descrição.
     *
     * Procedimento para efetuar pesquisa atravéz de campo ID.
     * @param objeto: Objeto genérico baseado na interface de objetos.
     * @param jTextFieldCodigo: Campo de código.
     * @param jTextFieldDescricao: Campo de descrição.
     * @return 
     *      Retorna null caso consiga efetuar a pesquisa ou uma menságem com o 
     *      problema encontrado. 
     */
    public static String pesquisarCodigoDescicao(InterfaceObj objeto,
            JTextField jTextFieldCodigo, JTextField jTextFieldDescricao) {
        String retorno = null;

        try {
            int codigo;
            if (!jTextFieldCodigo.getText().equals("")) {
                codigo = Integer.valueOf(jTextFieldCodigo.getText());
            } else {
                codigo = Integer.valueOf(objeto.getDadosCodigo().getValor());
            }

            objeto.adicionarWhere(
                    new DadosDAO(objeto.getDadosCodigo().getCampo(), "",
                            String.valueOf(codigo), DadosDAO.TIPO_LONG,
                            DadosDAO.IS_IGUAL, DadosDAO.IS_CHAVE));

            definirObjeto(jTextFieldCodigo, jTextFieldDescricao,
                    objeto.getObjetoDAO().popularCodigoDescricao(
                            objeto.getDadosCodigo().getCampo(),
                            objeto.getDadosDescricao().getCampo()));
        } catch (NumberFormatException e) {
            retorno = "Informe somente números neste campo.";
            jTextFieldCodigo.setText("");
            jTextFieldDescricao.setText("");
        }

        return retorno;
    }

    /**
     * Pesquisa Usuário.
     *
     * Procedimento para efetuar pesquisa atravéz do usuário do sistema.
     * @param objeto: Objeto genérico baseado na interface de objetos.
     * @param jTextFieldLogin: Dados de login. 
     * @param jTextFieldSenha: Dados de senha.
     * @return 
     *      Retorna null caso consiga efetuar a pesquisa ou uma menságem com o 
     *      problema encontrado. 
     */
    public static String pesquisarUsuario(InterfaceObj objeto,
            JTextField jTextFieldLogin, JTextField jTextFieldSenha) {
        String retorno = null;

        try {
            objeto.adicionarWhere(
                    new DadosDAO("login", "", jTextFieldLogin.getText(),
                            DadosDAO.TIPO_STRING, DadosDAO.IS_IGUAL, 
                            DadosDAO.NAO_CHAVE));
            objeto.adicionarWhere(
                    new DadosDAO("senha", "", jTextFieldSenha.getText(),
                            DadosDAO.TIPO_STRING, DadosDAO.IS_IGUAL, 
                            DadosDAO.NAO_CHAVE));

            definirObjeto(jTextFieldLogin, jTextFieldSenha,
                    objeto.getObjetoDAO().popularCodigoDescricao(
                            objeto.getDadosCodigo().getCampo(),
                            objeto.getDadosDescricao().getCampo()));
        } catch (NumberFormatException e) {
            retorno = "Informe somente números neste campo.";
            jTextFieldLogin.setText("");
            jTextFieldSenha.setText("");
        }

        return retorno;
    }

    /**
     * Definir Objeto.
     *
     * Procedimento definir um objeto genérico baseado na interface de objetos.
     * @param jTextFieldCodigo: Campo de código.
     * @param jTextFieldDescricao: campo de descrição.
     * @param registrador: registrador para código e descrição
     */
    public static void definirObjeto(JTextField jTextFieldCodigo,
            JTextField jTextFieldDescricao, Registrador registrador) {
        jTextFieldCodigo.setText(String.valueOf(registrador.getCodigo()));
        jTextFieldDescricao.setText(String.valueOf(registrador.getDescricao()));
    }

    /**
     * Salvar.
     *
     * Procedimento definir um objeto genérico baseado na interface de objetos.
     * @param objeto: Objeto genérico baseado na interface de objetos.
     * @param jInternalFrame: JInternalFrame do qual a informação está vindo
     * @param isFechar: Fechar o JInternalFrame depois de salvar.
     */
    public static void salvar(InterfaceObj objeto, JInternalFrame jInternalFrame,
            boolean isFechar) {
        String msg;
        if (Integer.parseInt(objeto.getDadosCodigo().getValor())
                >= objeto.getObjetoDAO().getProximoID(
                        objeto.getDadosCodigo().getCampo())) {
            msg = objeto.getObjetoDAO().incluir();
            if (isFechar && msg == null) {
                JOptionPane.showMessageDialog(
                        jInternalFrame, "Registro incluído com sucesso.");
            }
        } else {
            objeto.adicionarWhere(objeto.getCamposChave());
            msg = objeto.getObjetoDAO().alterar();
            if (isFechar && msg == null) {
                JOptionPane.showMessageDialog(jInternalFrame.getParent(),
                        "Registro alterado com sucesso.");
            }
        }

        if (msg == null) {
            if (isFechar) {
                jInternalFrame.dispose();
            }
        } else {
            JOptionPane.showMessageDialog(jInternalFrame.getParent(),
                    "Registro nao pode ser incluído/alterado.\n\nMotivo: " + msg);
        }
    }
    
     /**
     * Salvar.
     *
     * Procedimento definir um objeto genérico baseado na interface de objetos.
     * @param objeto: Objeto genérico baseado na interface de objetos.
     * @param jInternalFrame: JInternalFrame do qual a informação está vindo
     * @param isFechar: Fechar o JInternalFrame depois de salvar.
     */
    public static void salvarFilho(InterfaceObj objeto, JInternalFrame jInternalFrame,
            boolean isFechar) {
        String msg;
        if (Integer.parseInt(objeto.getDadosCodigo().getValor())
                >= objeto.getObjetoDAO().getProximoID(
                        objeto.getDadosCodigo().getCampo())) {
            msg = objeto.getObjetoDAO().incluir();
        } else {
            objeto.adicionarWhere(objeto.getCamposChave());
            msg = objeto.getObjetoDAO().alterar();
        }

        if (msg != null) {
            JOptionPane.showMessageDialog(jInternalFrame.getParent(),
                    "Registro nao pode ser incluído/alterado.\n\nMotivo: " + msg);
        }
    }
    

    /**
     * Confirmar.
     *
     * @param iFrame: JInternalFrame a ser fechado.
     */
    public static void confirmar(JInternalFrame iFrame) {
        iFrame.dispose();
    }

    /**
     * Cancelar.
     *
     * @param iFrame: JInternalFrame a ser fechado.
     */
    public static void cancelar(JInternalFrame iFrame) {
        iFrame.dispose();
    }
    
    /**
     * 
     * getAcoesUsuario
     * 
     * Retorna um ARRAY conrtendo as permissões do sistema.
     * 
     * @param idAcao Identificador da ação.
     * @return vetro de permissões
     */
    public static boolean[] getAcoesUsuario(int idAcao){
        int contador = 0;
        boolean[] retorno = new boolean[4];

        for (AcoesUsuarios permissoe : Principal.usuario.getPermissoes()) {
            contador++;

            if ( contador == idAcao ) {
                AcoesUsuarios acoesUsuario = new AcoesUsuarios(permissoe);
                
                retorno[INCLUIR] = acoesUsuario.isIncluir();
                retorno[ALTERAR] = acoesUsuario.isAlterar();
                retorno[EXCLUIR] = acoesUsuario.isExcluir();
                retorno[CONSULTAR] = acoesUsuario.isConsultar();
            }
        }
        
        return retorno;
    }
    
        /**
     * Efetua uma pesquisa
     * @param combo combo com dados para pesquisa
     * @param item Item pesquisado
     */
    public void definirItemCombo(JComboBox combo, PesquisaCodDescicao item) {
        for (int i = 0; i < combo.getItemCount(); i++) {
            if (((PesquisaCodDescicao) combo.getItemAt(i)).getCodigo() == null
                    ? (item.getCodigo()) == null
                    : ((PesquisaCodDescicao) combo.getItemAt(i)).getCodigo().equals(item.getCodigo())) {
                combo.setSelectedIndex(i);
                return;
            } else {
            }
        }
    }
}
