package ui;

import javax.swing.*;

public class ReadUI {
    private JTextPane textContent;
    private JPanel mainPanel;

    public JComponent getComponent() {
        return mainPanel;
    }

    public JTextPane getTextContent() {
        return textContent; //返回文字面板（用JScrollPane环绕）
    }
}
