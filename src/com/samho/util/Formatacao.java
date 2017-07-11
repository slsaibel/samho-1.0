/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.samho.util;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import javax.swing.JFormattedTextField;
import javax.swing.JTextField;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;

public class Formatacao {
    // Constantes para tipo de datas
    public static final int COMPLETA = 0;
    public static final int SOMENTE_DATA = 1;
    public static final int DATA_RESUMIDA = 2;
    public static final int DATA_CURTA = 3;
    public static final int DATA_DMA = 4;
    public static final int DATA_AMD = 5;
    
    // Constantes para dias de semana
    public static final ArrayList<String> DIAS_SEMANA_ABREV = new ArrayList(
            Arrays.asList("Todos","Dom.","Seg.","Ter.","Qua.","Qui.","Sex.","Sáb."));
    public static final ArrayList<String> DIAS_SEMANA_COMPL = new ArrayList(
            Arrays.asList("Todos","Domingo","Segunda-feira","Terça-feira",
                    "Quarta-feira","Quinta-feira","Sexta-feira","Sábado"));

    static DecimalFormat df2 = new DecimalFormat(
            "#,##0.00", new DecimalFormatSymbols(new Locale("pt", "BR")));
    static DecimalFormat df6 = new DecimalFormat(
            "#,##0.000000", new DecimalFormatSymbols(new Locale("pt", "BR")));

    public static JFormattedTextField getFormatado(String formato) {
        JFormattedTextField campoFormatado = null;
        MaskFormatter format = new MaskFormatter();

        format.setPlaceholderCharacter(' ');
        format.setValueContainsLiteralCharacters(false);

        try {
            format.setMask(formato);
            campoFormatado = new JFormattedTextField(format);
        } catch (ParseException ex) {
        }
        return campoFormatado;
    }

    public static void formatarDecimal2(JTextField campo) {
        if (!campo.getText().equals("")) {
            campo.setText(df2.format(Double.parseDouble(campo.getText())));
        }
    }

    public static void formatarDecimal6(JTextField campo) {
        if (!campo.getText().equals("")) {
            campo.setText(df6.format(Double.parseDouble(campo.getText())));
        }
    }

    public static JFormattedTextField getTelefone() {
        return getFormatado("##(##)#########");
    }

    public static JFormattedTextField getCNPJ() {
        return getFormatado("##.###.###/####-##");
    }

    public static JFormattedTextField getCPF() {
        return getFormatado("###.###.###-##");
    }

    public static JFormattedTextField getData() {
        return getFormatado("##/##/####");
    }

    public static void reformatarData(JFormattedTextField campo) {
        try {
            String str = removerFormatacao(campo.getText());

            MaskFormatter m = new MaskFormatter();
            m.setPlaceholderCharacter(' ');
            m.setMask("##/##/####");
            campo.setFormatterFactory(null);
            campo.setFormatterFactory(new DefaultFormatterFactory(m));
            campo.setText(str);
            campo.selectAll();
        } catch (ParseException e) {
            System.err.println(e);
        }
    }

    public static void reformatarHora(JFormattedTextField campo) {
        try {
            String str = removerFormatacao(campo.getText());

            MaskFormatter m = new MaskFormatter();
            m.setPlaceholderCharacter('0');
            m.setMask("##:##:##");
            campo.setFormatterFactory(null);
            campo.setFormatterFactory(new DefaultFormatterFactory(m));
            campo.setText(str);
            campo.selectAll();
        } catch (ParseException e) {
            System.err.println(e);
        }
    }

    public static void reformatarCep(JFormattedTextField campo) {
        try {
            String str = removerFormatacao(campo.getText());

            MaskFormatter m = new MaskFormatter();
            m.setPlaceholderCharacter(' ');
            m.setMask("##.###-###");
            campo.setFormatterFactory(null);
            campo.setFormatterFactory(new DefaultFormatterFactory(m));
            campo.setText(str);
            campo.selectAll();
        } catch (ParseException e) {
            System.err.println(e);
        }
    }

    public static void reformatarCpf(JFormattedTextField campo) {
        try {
            String str = removerFormatacao(campo.getText());

            MaskFormatter m = new MaskFormatter();
            m.setPlaceholderCharacter(' ');
            m.setMask("###.###.###-##");
            campo.setFormatterFactory(null);
            campo.setFormatterFactory(new DefaultFormatterFactory(m));
            campo.setText(str);
            campo.selectAll();
        } catch (ParseException e) {
            System.err.println(e);
        }
    }

    public static void reformatarCnpj(JFormattedTextField campo) {
        try {
            String str = removerFormatacao(campo.getText());

            MaskFormatter m = new MaskFormatter();
            m.setPlaceholderCharacter(' ');
            m.setMask("##.###.###/####-##");
            campo.setFormatterFactory(null);
            campo.setFormatterFactory(new DefaultFormatterFactory(m));
            campo.setText(str);
            campo.selectAll();
        } catch (ParseException e) {
            System.err.println(e);
        }
    }

    public static void reformatarTelefone(JFormattedTextField campo) {
        try {
            String str = removerFormatacao(campo.getText());

            MaskFormatter m = new MaskFormatter();
            m.setPlaceholderCharacter(' ');
            m.setMask("##(##)#####-####");
            campo.setFormatterFactory(null);
            campo.setFormatterFactory(new DefaultFormatterFactory(m));
            campo.setText(str);
            campo.selectAll();
        } catch (ParseException e) {
            System.err.println(e);
        }
    }

    public static String ajustaDataDMA(Calendar calendar) {
        String retorno = null;
        if (calendar != null) {
            Date data = calendar.getTime();
            SimpleDateFormat dataFormatada = new SimpleDateFormat("dd/MM/yyyy");
            retorno = dataFormatada.format(data);
        }
        
        return retorno;
    }
    
    public static Calendar ajustaData(String data, int tipo) {
        Calendar calendar = null;
        if (tipo == Formatacao.DATA_AMD) {
            if (!data.equals("")) {
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    calendar = Calendar.getInstance();
                    calendar.setTime(sdf.parse(data));
                } catch (ParseException e) {
                }
            }
        } else {
            if (tipo == Formatacao.DATA_DMA) {
                if (!data.equals("")) {
                    try {
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                        calendar = Calendar.getInstance();
                        calendar.setTime(sdf.parse(data));
                    } catch (ParseException e) {
                    }
                }
            }
        }

        return calendar;
    }

    public static String ajustaDataAMD(Calendar calendar) {
        String retorno = null;
        if (calendar != null) {
            Date data = calendar.getTime();
            SimpleDateFormat dataFormatada = new SimpleDateFormat("yyyy-MM-dd");
            retorno = dataFormatada.format(data);
        }
        
        return retorno;
    }

    public static String ajustaDouble(String valor) {
        String retorno;

        retorno = valor.replaceAll("\\.", "");
        retorno = retorno.replaceAll(",", ".");

        return retorno;
    }

    public static String removerFormatacao(String dado) {
        String retorno = "";
        for (int i = 0; i < dado.length(); i++) {
            if (dado.charAt(i) != '.' && dado.charAt(i) != '('
                    && dado.charAt(i) != ')' && dado.charAt(i) != '/'
                    && dado.charAt(i) != '-' && dado.charAt(i) != ' '
                    && dado.charAt(i) != ',' && dado.charAt(i) != ':') {
                retorno += dado.charAt(i);
            }
        }

        return retorno;
    }

    public static String getDataAtual(int tipoData) {
        Calendar calendar = Calendar.getInstance();
        Date data = calendar.getTime();
        String retorno = "";
        DateFormat dateFormat;
        
        switch (tipoData) {
            case COMPLETA:
                dateFormat = DateFormat.getDateInstance(DateFormat.FULL); 
		retorno = dateFormat.format(data);
                break;
            case SOMENTE_DATA:
                dateFormat = DateFormat.getDateInstance(DateFormat.LONG); 
		retorno = dateFormat.format(data);
                break;
            case DATA_RESUMIDA:
                dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM); 
		retorno = dateFormat.format(data);
                break;
            case DATA_CURTA:
                dateFormat = DateFormat.getDateInstance(DateFormat.SHORT); 
		retorno = dateFormat.format(data);
                break;
        }
    
        return retorno;
    }

    public static String getHoraAtual() {
        Date now = new Date();
        DateFormat df = new SimpleDateFormat("hh:mm:ss");
        String dataHoje = df.format(now);

        return dataHoje;
    }
}
