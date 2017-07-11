/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.samho.negocio;

import com.samho.util.FabricaJSP;
import java.io.IOException;

/**
 *
 * @author Sergio


import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.text.DateFormat;
import java.util.Date;

public class teste {

	public static void main(String[] args) {
		Calendar c = Calendar.getInstance();
		Date data = c.getTime();
		
		DateFormat f = DateFormat.getDateInstance(DateFormat.FULL); //Data COmpleta
		System.out.println("Data brasileira: "+f.format(data));

                f = DateFormat.getDateInstance(DateFormat.LONG);
		System.out.println("Data sem o dia descrito: "+f.format(data));
		
		f = DateFormat.getDateInstance(DateFormat.MEDIUM);
		System.out.println("Data resumida 1: "+f.format(data));
		
		f = DateFormat.getDateInstance(DateFormat.SHORT);
		System.out.println("Data resumida 2: "+f.format(data));
                
                
                        try {
			System.out.println("/  -> " + new File("/").getCanonicalPath());
			System.out.println(".. -> " + new File("..").getCanonicalPath());
			System.out.println(".  -> " + new File(".").getCanonicalPath());
		} catch (IOException e) {
		}
	}
   
}
*/

/**
import javax.swing.*;
import java.awt.event.*;
import java.awt.*; 
public class teste extends JFrame{ 
	private UIManager.LookAndFeelInfo[] looks = UIManager.getInstalledLookAndFeels(); 
	private JLabel lbLabel01 = new JLabel("Nome :"); 
	private JLabel lbLabel02 = new JLabel("E-mail :"); 
	private JTextField jtTexto01 = new JTextField( 10 ); 
	private JTextField jtTexto02 = new JTextField( 10 ); 
	private JButton jbOk = new JButton("Enviar"); 
	private JButton jbLim = new JButton("Limpar"); 
	private JRadioButton[] escolha = new JRadioButton[ looks.length ]; 
	private ButtonGroup grupo = new ButtonGroup(); 
	
	public static void main( String[] args ){ 
            teste lfd = new teste(); 
            lfd.show(); 
	} 
	
	public teste(){ 
		super("Selecione um LF"); 
		
		Container c = getContentPane(); 
		c.setLayout ( new FlowLayout()); 
		c.add(lbLabel01); 
		c.add(jtTexto01); 
		c.add(lbLabel02); 
		c.add(jtTexto02); 
		c.add(jbOk); 
		c.add(jbLim); 
		
		ItemSelecionado iselect = new ItemSelecionado(); 
		for (int i = 0; i < looks.length; i++){ 
			escolha[i] = new JRadioButton( looks[i].getName() ); 
			escolha[i].addItemListener( iselect ); 
			grupo.add( escolha[i] ); 
			c.add( escolha[i] ); 
		} 
		escolha[2].setSelected( true ); 
		setDefaultCloseOperation( EXIT_ON_CLOSE ); 
		setSize(200,250); 
	} 
	
	public void atualiza(int i){ 
		try { 
			UIManager.setLookAndFeel(looks[i].getClassName()); 
			SwingUtilities.updateComponentTreeUI(this); 
		} catch(Exception e) { 
			e.printStackTrace(); 
		} 
	} 

	private class ItemSelecionado implements ItemListener {
		public void itemStateChanged( ItemEvent e) {
			for (int i=0; i < escolha.length; i++){
				if (escolha[i].isSelected()) {
					atualiza(i);
				}
			} 
		}
	} 
} */

public class teste {

	public static void main(String[] args) throws IOException {
            Pessoas pessoa = new Pessoas();
            FabricaJSP.criarJSP(pessoa, "Pessoas", "UTF-8");
            //FabricaJSP.criarJSP(pessoa, "Pessoas", "ISO-8859-1");
        }
}