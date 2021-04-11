/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 *
 * @author Matthew Holderbaum
 */
public class Randomizer {
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run()
            {
                Randomizer randomizer = new Randomizer();
                randomizer.setup();
            }
        });
    }
    
    JFileChooser fileChooser = new JFileChooser();
    File file;
    static Random random;
    static int max = 300;
    static int minStart = 0;
    static int maxEnd = 0;
    static int min = -300;
    public Randomizer() {
        random = new Random();
    }
    
    public void setup() {
        JFrame frame = new JFrame("Artemis System Terrain Normalizer - v0.1b");
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(500,500));
        frame.setLayout(new BorderLayout());
        JPanel mid = new JPanel(new BorderLayout());
        JLabel doneLabel = new JLabel("Conversion Complete!");
        doneLabel.setVisible(false);
        
//        JMenuBar menuBar = new JMenuBar();
//        JMenu menu = new JMenu("Connection");
//        menuBar.add(menu);
//        JMenuItem connectMenuItem = new JMenuItem("Choose File");
//        
//        connectMenuItem.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                
//                
//                
//                mid.setVisible(false);
//            }
//        }); 
        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel panel = new JPanel(new BorderLayout());
        mainPanel.add(panel,BorderLayout.PAGE_START);
        
        JTextField fileLoc = new JTextField();
        JLabel fileLabel = new JLabel("File: ");
        JButton chooseButton = new JButton("Choose File");
        chooseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fileChooser = new JFileChooser();
                fileChooser.showOpenDialog(frame);
                file = fileChooser.getSelectedFile();
                fileLoc.setText(file.getPath()); 
                doneLabel.setVisible(false);
            }
        });
        
        panel.add(fileLabel,BorderLayout.LINE_START);
        panel.add(fileLoc,BorderLayout.CENTER);
        panel.add(chooseButton,BorderLayout.LINE_END);
        
       
        mainPanel.add(mid,BorderLayout.PAGE_END);
        
        
        mainPanel.add(doneLabel, BorderLayout.CENTER);
        
        JButton convert = new JButton("Randomize Asteroids");
        convert.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setAsteroidHeights(file);
                doneLabel.setVisible(true);
            }
        });
        
        mid.add(convert);
        
        frame.add(mainPanel);
        frame.pack();
        
    }
    
    public static void setAsteroidHeights(File file) {
        FileInputStream fis;
        try {
            fis = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }
        StringBuilder sb = new StringBuilder();
        try(BufferedReader br = new BufferedReader( new InputStreamReader(fis))) {
            HashMap<String,String> map = new HashMap<>();
            String line;
            while(( line = br.readLine()) != null ) {
                if (line.contains("type=\"asteroids\"")) {
                    int start = min;//start = random.nextInt(maxStart - minStart) + minStart;
                    int end = max;//random.nextInt(maxEnd - minEnd) + minEnd;
                    System.out.println(line);
                    line = line.replace("startY=\"0.0\"", "startY=\"" + start + ".0\"").replace("endY=\"0.0\"", "endY=\"" + end + ".0\"");
                    sb.append(line.replace("startY=\"0.0\"", "startY=\"" + start + ".0\"").replace("endY=\"0.0\"", "endY=\"" + end + ".0\""));
                } else {
                    sb.append(line);
                }
                sb.append("\n");
            }
            fis.close();
            br.close();
            
            FileWriter f2 = new FileWriter(file, false);
            f2.write(sb.toString());
            f2.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
