/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.samho.util;

import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 * Classe de Objeto.
 *
 * @author Saibel, Sergio Luis
 * @since  date 31/03/2017
 *
 * @version  revision 001.20170331 date 31/03/2017 author Saibel, Sergio Luís reason
 * Instanciar um objeto com métodos e atributos necessários.
 */
public class GridJTable extends JTable {

    /**
     * Formatar tabela - JTable
     * 
     * @param tabela JTable a ser formatada.
     * @param dadosTabela Matriz de dados para popular tabela.
     * @param cabecalho Dados do cabeçalho.
     * @param colImagem Informação das colunas que possuem imagens.
     */
    public static void formatarTabela(JTable tabela, Object[][] dadosTabela,
            Object[] cabecalho, final ArrayList<Integer> colImagem) {
        // configuracoes adicionais no componente tabela
        tabela.setModel(new DefaultTableModel(dadosTabela, cabecalho) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

            // método que determina a coluna que o objeto ImageIcon devera aparecer
            @Override
            public Class getColumnClass(int column) {
                for (Integer integer : colImagem) {
                    if (column == integer) {
                        return ImageIcon.class;
                    }
                }

                return Object.class;
            }
        });

        // permite a ordenação das  linhas atravéz do click na coluna
        tabela.setAutoCreateRowSorter(true);

        // permite seleção de apenas uma linha da tabela
        tabela.setSelectionMode(0);

        // não permite a troca de posição das colunas
        tabela.getTableHeader().setReorderingAllowed(false);

        // renderizacao das linhas da tabela = mudar a cor
        tabela.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected,
                        hasFocus, row, column);
                if (row % 2 == 0) {
                    setBackground(new Color(153, 204, 255));
                } else {
                    setBackground(new Color(153, 153, 153));
                }
                return this;
            }
        });

        /**
         * Redimensiona as colunas de uma tabela ignorando o primeiro registro,
         * que representa o ID da tabela
         */
        TableColumn column;
        for (int i = 1; i < tabela.getColumnCount(); i++) {
            column = tabela.getColumnModel().getColumn(i);

            /**
             * Pega o identificador da coluna: ID, Código, Nome... Como padrão
             * a primeira coluna representa o código do que se esta
             * pesquisando, esta deve apareced, porém as demais não precisam
             * aparecedr, pois se tratam de campos de uso do proprio sistema.
             * Caso o cliente precise destes códigos este método deve ser
             * revisado.
             */
            String nomeColuna = column.getIdentifier().toString();

            if (nomeColuna.indexOf('.') > 0) {
                // Redimensiona as colunas de uma tabela cujo nome começar com "Cód"
                if (nomeColuna.substring(0, nomeColuna.indexOf('.')).equals("Cód")) {
                    column.setMinWidth(0);
                    column.setMaxWidth(0);
                    column.setPreferredWidth(0);
                }
            }
        }
    }

}
