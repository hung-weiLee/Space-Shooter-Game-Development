package com.mygdx.game.utilities;

import com.badlogic.gdx.Input;
import com.mygdx.game.entities.players.hero.Hero;
import com.mygdx.game.entities.players.hero.HeroInputKeyManager;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SettingPopUp extends PopUp {
	private String[] cheat = {"Enable","Disable"};
    private String[] keys = {"A","B","C","D","E","F","G","H","I",
            "J","K","L","M","N","O","P","Q","R","S","T","U","V",
            "W","X","Y","Z","0","1","2","3",
            "4","5","6","7","8","9",
            "Up","Down","Left","Right","Space","Enter",
            "L-Shift","R-Shift", ";","L-Alt",
            "R-Alt", "R-Ctrl","L-Ctrl"};
    private JButton saveButton;
    private JButton cancelButton;
    private JComboBox upBox;
    private JComboBox rightBox;
    private JComboBox downBox;
    private JComboBox leftBox;
    private JComboBox shootBox;
    private JComboBox bombBox;
    private JComboBox speedBox;
	private JComboBox cheatBox;
    private JPanel errPanel;
    private CardLayout errCardLayout;
    private JLabel errMsg;
    private Container pane;

    public SettingPopUp(String title, int width, int height) {
        super(title, width, height);

        pane = frame.getContentPane();
        pane.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        //item1: err message
        errCardLayout = new CardLayout();
        errPanel = new JPanel(errCardLayout);
        errMsg = new JLabel("**No duplicate keys are allowed**");
        errMsg.setBackground(Color.orange);
        errMsg.setOpaque(true);
        errPanel.add(errMsg, "err");//card 1
        errPanel.add(new JLabel(), "no err");//card 2
        errCardLayout.show(errPanel, "no err");
        constraints.fill = GridBagConstraints.CENTER;
        constraints.gridwidth = 3;
        constraints.gridx = 0;
        constraints.gridy = 0;
        pane.add(errPanel, constraints);

        //item2: up key
        JPanel upPanel = new JPanel();
        upPanel.add(new JLabel("To move up"));
        upBox = new JComboBox(keys);
        upBox.setSelectedItem(Input.Keys.toString(HeroInputKeyManager.upKey)); //default
        upPanel.add(upBox);
        constraints.fill = GridBagConstraints.CENTER;
        constraints.gridwidth = 1;
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.insets = new Insets(15,0,0,0);
        pane.add(upPanel, constraints);

        //item3: left key
        JPanel leftPanel = new JPanel();
        leftPanel.add(new JLabel("To move left"));
        leftBox = new JComboBox(keys);
        leftBox.setSelectedItem(Input.Keys.toString(HeroInputKeyManager.leftKey));//default
        leftPanel.add(leftBox);
        constraints.fill = GridBagConstraints.EAST;
        constraints.gridwidth = 1;
        constraints.gridx = 0;
        constraints.gridy = 2;
        pane.add(leftPanel, constraints);


        //item7: cheat Mode
        JPanel cheatPanel = new JPanel();
        cheatPanel.add(new JLabel("Cheat Mode"));
        cheatBox = new JComboBox(cheat);
        cheatBox.setSelectedItem("Disable");//default
        cheatPanel.add(cheatBox);
        constraints.fill = GridBagConstraints.WEST;
        constraints.gridwidth = 1;
        constraints.gridx = 1;
        constraints.gridy = 2;
        pane.add(cheatPanel, constraints);

        //item4: right key
        JPanel rightPanel = new JPanel();
        rightPanel.add(new JLabel("To move right"));
        rightBox = new JComboBox(keys);
        rightBox.setSelectedItem(Input.Keys.toString(HeroInputKeyManager.rightKey)); //default
        rightPanel.add(rightBox);
        constraints.fill = GridBagConstraints.WEST;
        constraints.gridwidth = 1;
        constraints.gridx = 2;
        constraints.gridy = 2;
        pane.add(rightPanel, constraints);

        //item5: down key
        JPanel downPanel = new JPanel();
        downPanel.add(new JLabel("To move down"));
        downBox = new JComboBox(keys);
        downBox.setSelectedItem(Input.Keys.toString(HeroInputKeyManager.downKey));//default
        downPanel.add(downBox);
        constraints.fill = GridBagConstraints.CENTER;
        constraints.gridwidth = 1;
        constraints.gridx = 1;
        constraints.gridy = 3;
        pane.add(downPanel, constraints);

        //item5: shoot key
        JPanel shootPanel = new JPanel();
        shootPanel.add(new JLabel("To shoot"));
        shootBox = new JComboBox(keys);
        shootBox.setSelectedItem(Input.Keys.toString(HeroInputKeyManager.shootKey));//default
        shootPanel.add(shootBox);
        constraints.fill = GridBagConstraints.EAST;
        constraints.gridwidth = 1;
        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.insets = new Insets(20,0,20,0);
        pane.add(shootPanel, constraints);

        //item5.5: bomb key
        JPanel bombPanel = new JPanel();
        bombPanel.add(new JLabel("Bomb"));
        bombBox = new JComboBox(keys);
        bombBox.setSelectedItem(Input.Keys.toString(HeroInputKeyManager.bombKey));//default
        bombPanel.add(bombBox);
        constraints.fill = GridBagConstraints.EAST;
        constraints.gridwidth = 1;
        constraints.gridx = 1;
        constraints.gridy = 4;
        constraints.insets = new Insets(20,0,20,0);
        pane.add(bombPanel, constraints);

        //item6: speed key
        JPanel speedPanel = new JPanel();
        speedPanel.add(new JLabel("To slow speed"));
        speedBox = new JComboBox(keys);
        speedBox.setSelectedItem(Input.Keys.toString(HeroInputKeyManager.slowSpeedKey));//default
        speedPanel.add(speedBox);
        constraints.fill = GridBagConstraints.WEST;
        constraints.gridwidth = 1;
        constraints.gridx = 2;
        constraints.gridy = 4;
        constraints.insets = new Insets(20,0,20,0);
        pane.add(speedPanel, constraints);


        //item7: cancel button
        cancelButton = new JButton("Cancel Change");
        cancelButton.addActionListener(this);
        constraints.fill = GridBagConstraints.EAST;
        constraints.gridwidth = 1;
        constraints.gridx = 0;
        constraints.gridy = 5;
        constraints.insets = new Insets(0,0,30,0);
        pane.add(cancelButton, constraints);

        //item8: save button
        saveButton = new JButton("Save Change");
        saveButton.addActionListener(this);
        constraints.fill = GridBagConstraints.WEST;
        constraints.gridwidth = 1;
        constraints.gridx = 2;
        constraints.gridy = 5;
        constraints.insets = new Insets(0,0,30,0);
        pane.add(saveButton, constraints);


        frame.pack();
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == saveButton) {
            //check for duplicate keys
            String[] allKeys = {upBox.getSelectedItem()+"",rightBox.getSelectedItem()+"",
                    downBox.getSelectedItem()+"",leftBox.getSelectedItem()+"",speedBox.getSelectedItem()+"",
                    shootBox.getSelectedItem()+""};
            Set<String> uniqueKeys = new HashSet<String>(Arrays.asList(allKeys));
            if (uniqueKeys.size() < allKeys.length) //have duplicate keys
            {
                errCardLayout.show(errPanel, "err");
                return;
            }
            else {
                //save to HeroMoveManager
                HeroInputKeyManager.upKey = Input.Keys.valueOf(upBox.getSelectedItem() + "");
                HeroInputKeyManager.downKey = Input.Keys.valueOf(downBox.getSelectedItem() + "");
                HeroInputKeyManager.leftKey = Input.Keys.valueOf(leftBox.getSelectedItem() + "");
                HeroInputKeyManager.rightKey = Input.Keys.valueOf(rightBox.getSelectedItem() + "");
                HeroInputKeyManager.shootKey = Input.Keys.valueOf(shootBox.getSelectedItem() + "");
                HeroInputKeyManager.bombKey = Input.Keys.valueOf(bombBox.getSelectedItem() + "");
				HeroInputKeyManager.slowSpeedKey = Input.Keys.valueOf(speedBox.getSelectedItem() + "");
				if(cheatBox.getSelectedItem()=="Enable")
					Hero.cheat=true;
				else
					Hero.cheat=false;
            }
        }

        this.close();
    }
}
