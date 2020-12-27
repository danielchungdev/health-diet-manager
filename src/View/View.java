package View;
import Controller.Controller;

import Model.Model;

import javax.swing.*;
import java.io.IOException;

public class View {
    JFrame frame = new JFrame("Wellness Manager");

    public View(){
        frame.setSize(300, 335);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setResizable(true); //Setting this to true allows for fixing the bug by resizing the window
    }

    public JFrame getFrame(){
        return this.frame;
    }

    public void clear(){
        this.frame.getContentPane().removeAll();
        this.frame.getContentPane().revalidate();
        this.frame.getContentPane().repaint();
    }
}
