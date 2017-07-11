/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.samho.util;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 * Classe do JTextFieldDocument
 *
 * @author Saibel, Sergio Luis
 * @since  date 31/03/2017
 *
 * @version  revision 001.20170331 date 31/03/2017 author Saibel, Sergio Luís reason
 * reescrever métodos do componente para que recebam somente números.
 * 
 */
public class JTextFieldDocument extends PlainDocument {

    private final boolean numero;

    public JTextFieldDocument(boolean isNumber) {
        super();

        numero = isNumber;
    }

    @Override
    public void insertString(int offs, String str, AttributeSet attribute)
            throws BadLocationException {
        if (numero) {
            for (char c : str.toCharArray()) {
                if (!Character.isDigit(c)) {
                    return;
                }
            }
        }

        super.insertString(offs, str.toUpperCase(), attribute);
    }
}
