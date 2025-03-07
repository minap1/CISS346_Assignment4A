//package org.example;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.*;
//
//public class GUI implements ActionListener {
//    private int clicks = 0;
//    private JLabel label = new JLabel("Start Server");
//    private JFrame frame = new JFrame();
//
//
//    public GUI(int type) {
//        System.out.println(type);
//        // the clickable button
//        JButton button = new JButton("Click Me");
//        button.addActionListener(this);
//
//        // the panel with the button and text
//        JPanel panel = new JPanel();
//        panel.setBorder(BorderFactory.createEmptyBorder(100, 100, 150, 100));
//        panel.setLayout(new GridLayout(1, 0));
//        panel.add(button);
//        panel.add(label);
//
//
//
//        //Server
//        if(type == 0) {
//
//
//            frame.add(panel, BorderLayout.CENTER);
//            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//            frame.setTitle("SMP Server Version 1.0");
//            frame.pack();
//            frame.setVisible(true);
//        }
//        //Producer
//        else if(type == 1) {
//            frame.add(panel, BorderLayout.CENTER);
//            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//            frame.setTitle("SMP Message Producer Version 1.0");
//            frame.pack();
//            frame.setVisible(true);
//        }
//        //Consumer
//        else if(type == 2) {
//            frame.add(panel, BorderLayout.CENTER);
//            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//            frame.setTitle("SMP Message Consumer Version 1.0");
//            frame.pack();
//            frame.setVisible(true);
//        }
//
//    }
//
//    // process the button clicks
//    public void actionPerformed(ActionEvent e) {
//        clicks++;
//        label.setText("Number of clicks:  " + clicks);
//    }
//
//    // create one Frame
//    public static void main(String[] args) {
//        new GUI(Integer.valueOf(args[0]));
//    }
//}
