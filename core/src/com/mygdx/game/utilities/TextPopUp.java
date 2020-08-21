package com.mygdx.game.utilities;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;


//only show text and an exit button
public class TextPopUp extends PopUp  {

    public TextPopUp(String title, int width, int height, String textfilePath) {
        super(title, width, height);
        String content = getContent(textfilePath);

        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();

        JLabel text = new JLabel(content);
        panel1.add(text);

        JButton OKButton = new JButton("OK");
        OKButton.addActionListener(this);
        panel2.add(OKButton);

        frame.add(panel1, BorderLayout.CENTER);
        frame.add(panel2, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    @Override
    //when click the button
    public void actionPerformed(ActionEvent e) {
        this.close();
    }

    private String getContent(String textFilePath)
    {
        String content = new String();
        String entry;

        try {
            BufferedReader bfReader = new BufferedReader(
                    new FileReader(textFilePath));
            while ((entry = bfReader.readLine()) != null) {
                content += entry + "<br/>";
            }
            bfReader.close();
        } catch (IOException e)
        {
            System.out.println(e);
        }
        return "<html>" + content + "</html>";
    }
}
