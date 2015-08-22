package ContactsManager;

/*
 * Name: Harsha Deep Reddy V
 * 
 * Date: 18 Sep 2014 
 * Purpose of this module: This is a special class for a modified texfiled, this limited text field will limit
 *                         the number of characters in the text field while maintaining the rest of the 
 *                         characterisitics of the text field.
 */

import javafx.scene.control.TextField;

public class TextFieldLimited extends TextField {  
    private int maxlength;
    
    public TextFieldLimited() {
        this.maxlength = 10;
        
    }
    public void setMaxlength(int maxlength) {
        this.maxlength = maxlength;
    }
    @Override
    public void replaceText(int start, int end, String text) {
        // Delete or backspace user input.
        if (text.equals("")) {
            super.replaceText(start, end, text);
        } else if (getText().length() < maxlength) {
            super.replaceText(start, end, text);
        }
    }

    @Override
    public void replaceSelection(String text) {
        // Delete or backspace user input.
        if (text.equals("")) {
            super.replaceSelection(text);
        } else if (getText().length() < maxlength) {
            // Add characters, but don't exceed maxlength.
            if (text.length() > maxlength - getText().length()) {
                text = text.substring(0, maxlength- getText().length());
            }
            super.replaceSelection(text);
        }
    }
}
