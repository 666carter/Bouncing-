// some of the code is referenced by CS349 SAMPLE CODE:
// 1: https://git.uwaterloo.ca/cs349-public/1195/tree/master/code/09.graphics/java/transformations
// 2: https://git.uwaterloo.ca/cs349-public/1195/tree/master/code/10.mvc/hellomvc3
package src.cs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class Main {

    public static void main(String[] args){
        JFrame frame = new JFrame("CS349 A2");
        // create Model and initialize it
        Model model = new Model();
        // create View, tell it about model
        int V;
        int FPS;
        if(args.length == 0){
             V = 2;
             FPS = 50;
        }else {
             V = Integer.parseInt(args[0]);
             FPS = Integer.parseInt(args[1]);
        }
        View view = new View(model, V, FPS);
        // tell Model about View.
        model.addView(view);
        // create second view
        //View2 view2 = new View2(model);
        //model.addView(view2);
        // create a layout panel to hold the two views
        JPanel p = new JPanel(new GridLayout(1,1));
        frame.getContentPane().add(p);
        frame.setBackground(Color.BLACK);
        // add views (each view is a JPanel)
        p.add(view);
        // create the window
        frame.pack();
        frame.setMinimumSize(new Dimension(700, 700));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}