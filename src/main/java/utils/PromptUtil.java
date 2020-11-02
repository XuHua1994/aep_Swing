package utils;

import javax.swing.*;

public class PromptUtil {

    public static void outputError(String msg){
        JOptionPane.showMessageDialog(null, msg, "提示", JOptionPane.INFORMATION_MESSAGE);
    }
}
