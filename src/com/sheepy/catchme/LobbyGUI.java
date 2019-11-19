/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sheepy.catchme;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ckrittima
 */
public class LobbyGUI implements ActionListener {
    private JFrame fr;
    private JPanel p, p2;
    private JLabel lb;
    private JTextField tf1, tf2, tf3, tf4, tf5;
    private JButton btn;
    
    public void init(){
        fr = new JFrame("Lobby");
        p = new JPanel();
        p2 = new JPanel();
        lb = new JLabel("Waiting For Players...", JLabel.CENTER);
        tf1 = new JTextField();
        tf2 = new JTextField();
        tf3 = new JTextField();
        tf4 = new JTextField();
        tf5 = new JTextField();
        btn = new JButton("Start");
        
        btn.addActionListener(this);
        
        tf1.setEditable(false);
        tf2.setEditable(false);
        tf3.setEditable(false);
        tf4.setEditable(false);
        tf5.setEditable(false);
        
        lb.setFont(new Font("TimesRoman", Font.BOLD, 45));
        tf1.setFont(new Font("TimesRoman", Font.PLAIN, 30));
        tf2.setFont(new Font("TimesRoman", Font.PLAIN, 30));
        tf3.setFont(new Font("TimesRoman", Font.PLAIN, 30));
        tf4.setFont(new Font("TimesRoman", Font.PLAIN, 30));
        tf5.setFont(new Font("TimesRoman", Font.PLAIN, 30));
        btn.setFont(new Font("TimesRoman", Font.BOLD, 25));
        
        tf1.setHorizontalAlignment(JTextField.CENTER);
        tf2.setHorizontalAlignment(JTextField.CENTER);
        tf3.setHorizontalAlignment(JTextField.CENTER);
        tf4.setHorizontalAlignment(JTextField.CENTER);
        tf5.setHorizontalAlignment(JTextField.CENTER);
        
        tf1.setText("Sheep 1");
        tf2.setText("Sheep 2");
        tf3.setText("Sheep 3");
        tf4.setText("Sheep 4");
        tf5.setText("Wolf");
        
        
        fr.setLayout(new BorderLayout());
        p.setLayout(new GridLayout(5, 1));
        p2.setLayout(new FlowLayout());
        p.add(tf1);
        p.add(tf2);
        p.add(tf3);
        p.add(tf4);
        p.add(tf5);
        p2.add(btn);
        
        fr.add(p, BorderLayout.CENTER);
        fr.add(lb, BorderLayout.NORTH);
        fr.add(p2, BorderLayout.SOUTH);
        
        fr.pack();
        fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fr.setSize(640, 640);
        fr.setVisible(true);
    }
    public static void main(String[] args) {
        new LobbyGUI().init();
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource().equals(btn)){
            try {
                fr.dispose();
                GameBoard gameBoard = new GameBoard();
            } catch (InterruptedException ex) {
                Logger.getLogger(LobbyGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
