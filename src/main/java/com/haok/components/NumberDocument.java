package com.haok.components;

import javax.swing.text.AttributeSet;
import javax.swing.text.PlainDocument;

public class NumberDocument extends PlainDocument {
    final int max;

    public NumberDocument(int max) {
        super();
        this.max = max;
    }

    public void insertString(int offset, String str, AttributeSet attr)
            throws javax.swing.text.BadLocationException {
        if (str == null) {
            return;
        }

        char[] s = str.toCharArray();
        int length = 0;
        // only number
        for (int i = 0; i < s.length; i++) {
            if ((s[i] >= '0') && (s[i] <= '9')) {
                s[length++] = s[i];
            }
            // insert content
            super.insertString(offset, new String(s, 0, length), attr);
        }
        int num = Integer.parseInt(getText(0, getLength()));
        if (num > max) {
            this.remove(0, getLength());
        }
    }
}
