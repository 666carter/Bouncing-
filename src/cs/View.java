// some of the code is referenced by CS349 SAMPLE CODE:
// 1: https://git.uwaterloo.ca/cs349-public/1195/tree/master/code/09.graphics/java/transformations
// 2: https://git.uwaterloo.ca/cs349-public/1195/tree/master/code/10.mvc/hellomvc3
package src.cs;

import com.sun.org.apache.xpath.internal.operations.Mod;
import javafx.scene.shape.Line;
import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.*;
import javax.swing.SwingUtilities;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.ArrayList;
import java.awt.*;
import javax.vecmath.*;
import java.awt.geom.*;
import java.io.*;
import java.lang.*;

//********************************************Layout for the Menubar********************************************
class MenubarLayout extends JPanel {
    Model m;
    int score;
    public MenubarLayout(Model model) {
        this.m = model;
        this.setPreferredSize(new Dimension(800, 100));
        this.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 3));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        int posx = getSize().width / 8;
        int yy = (getSize().height / 2);
        int posy = yy + 5;
        int posx2 = (int)(getSize().width / 2.5);
        int posx3 = (int)(getSize().width / 1.3);
        g.setColor(Color.WHITE);
        Graphics2D g2 = (Graphics2D) g;
        g.setFont(new Font("Comic Sans MS", Font.BOLD, 25));
        if(m.getEndValue() == false){
            score = m.getCounterValue();
        }
        //g2.drawString("Score: " + score + "                   " + "Speed Level: " + "3" + "                   " + "FPS: " + "100", posx, posy);
        g2.drawString("Score: " + score,  posx, posy);
        g2.drawString("Speed Level: " + m.V,  posx2, posy);
        g2.drawString("FPS: " + m.FPS,  posx3, posy);
    }
}

//********************************************Layout for the Main Game Area********************************************
class Canvas extends JPanel {
    int startx = 0;
    int y5 = 0;
    private int x, y;
    private int dx = 5;
    private int dy = 5;
    private final int ball_width = 20;
    private final int target_width = 800;
    private final int target_height = 800;
    boolean run = false;
    boolean gameover = false;
    boolean start = false;
    private int panelpositionX;
    private int panelpositionY;
    private int panellength = 250;
    int V;

    //******************set point2d for the panel******************
    Point2d M = new Point2d();
    Point2d Mstart = new Point2d();
    Model model;

    //******************attributes for coloured blocks, randomly assigning colors to blocks******************
    ArrayList<Color> colorsList = new ArrayList<>();
    ArrayList<Color> blockColors = new ArrayList<>();
    ArrayList<Rectangle> blocksList = new ArrayList<>();
    ArrayList<Boolean> blockAvailable = new ArrayList<>();
    //115
    private int blockwidth = (800 - 24) / 7;
    private final int blockheight = 35;
    private final int blockoriginwidth = (800 - 24) / 7;
    private int currentX = 5;
    private int currentY = 5;

    //******************attributes for game over status******************
    int posx;
    int posy;
    int posy2;
    int posxrec;
    int posyrec;
    int score = 0;

    MenubarLayout mb;

    Canvas(Model model, MenubarLayout mb) {
        this.model = model;
        this.mb = mb;
        if(V == 1){
            dx = 5;
            dy = 5;
        }else if(V == 2){
            dx = 10;
            dy = 10;
        }else if(V == 3){
            dx = 15;
            dy = 15;
        }
        //********************************************set the colors of colorslist********************************************
        colorsList.add(Color.BLUE);
        colorsList.add(Color.YELLOW);
        colorsList.add(Color.GREEN);
        colorsList.add(Color.PINK);
        colorsList.add(Color.RED);
        colorsList.add(Color.ORANGE);

        //********************************************create the blocks********************************************
        //GridLayout gBlocks = new GridLayout(5,7);
        for(int i = 0; i < 5; ++i){
            for(int j = 0; j < 7; ++j) {
                blockAvailable.add(true);
                Rectangle r = new Rectangle(currentX, currentY, blockwidth, blockheight);
                blocksList.add(r);
                currentX = blockwidth + currentX + 2;
            }
            currentX = 5;
            currentY = blockheight + currentY + 2;
        }
        //set colors for each one
        for(int i = 0; i < 35; ++i){
            Random rand = new Random();
            Color randomElement = colorsList.get(rand.nextInt(colorsList.size()));
            blockColors.add(randomElement);
        }
        //********************************************ball starting location********************************************
        M.y = getSize().height - 40;
        panelpositionY = 300;
        //Random rand2 = new Random();
        //x = (rand2.nextInt(target_width));
        x = 400;
        y = 400;

        //******************************************** MOUSE + KEY LISTENER IN Canvas ********************************************
        Canvas c = this;
        this.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if((start == false) && (gameover == false)) {
                    if(hittest2(startx, y5, 500, 60)){
                        //run = !run;
                        model.stateHandler(1, c);
                    }
                }
            }
        });

        this.addMouseMotionListener(new MouseAdapter() {
            public void mouseMoved(MouseEvent e) {
                M.x = e.getX();
                Mstart.x = e.getX();
                Mstart.y = e.getY();
                repaint();
            }
        });

    }


    @Override
    public Dimension getPreferredSize() {
        return new Dimension(target_width, target_height);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        //paint three components: 1: balls 2: blocks 3: panel
        if(start == false){ //************************************************************if the game not started*************************************************************
            startx = getSize().width / 4;
            int starty = 80;
            int howtoplayy = getSize().height / 4;
            int y1 = howtoplayy + 40;
            int y2 = y1 + 40;
            int y3 = y2 + 40;
            int y4 = y3 + 100;
            g.setColor(Color.WHITE);
            g.setFont(new Font("Comic Sans MS", Font.BOLD, 70));
            Graphics2D g2 = (Graphics2D) g;
            g2.drawString("BREAKOUT", startx, starty);
            g.setFont(new Font("Impact", Font.BOLD, 25));
            g2.drawString("Instruction?", startx, howtoplayy);
            g2.drawString("Space -- Pause the game", startx, y1);
            g2.drawString("ESC -- Quit the game", startx, y2);
            g2.drawString("Mouse Left/Right -- Move the ball", startx, y3);
            g.setFont(new Font("Comic Sans MS", Font.BOLD, 50));
            y5 = y4 - 30;
            if(hittest2(startx, y5, 500, 60)) {
                g.setColor(Color.LIGHT_GRAY);
            }else{
                g.setColor(Color.WHITE);
            }
            g2.drawString("Click here to start!", startx, y4);
            g.setColor(Color.WHITE);
            int y6 = y4 + 140;
            int x6 = (int)(getSize().width / 5.5);
            int x7 = (int)(getSize().width / 1.5);
            g.setFont(new Font("Comic Sans MS", Font.BOLD, 25));
            //g2.drawString("Created by: Jinnong Zhao", x6, y6);
            //g2.drawString("Userid: j286zhao", x7, y6);
            g2.drawString("Created by: Jinnong Zhao (j286zhao)", startx, y6);
        }else if(gameover == false) {    //*************************************************************if the game is running(ball not fall on the ground)*************************************************************
            //Update the blocks position due to resizing
            blockwidth = ((getSize().width) - 24) / 7;
            if(blockoriginwidth != blockwidth) {
                int resizeX = 5;
                int resizeY = 5;
                int counter = 0;
                int counterav = 0;
                for (int i = 0; i < 5; ++i) {
                    for (int j = 0; j < 7; ++j) {
                        //ONLY resizing when the blocks exist
                        if (blockAvailable.get(counterav) == true) {
                            blocksList.get(counter).setRect(resizeX, resizeY, blockwidth, blockheight);
                        }
                        resizeX = blockwidth + resizeX + 2;
                        counterav++;
                        counter++;
                    }
                    resizeX = 5;
                    resizeY = blockheight + resizeY + 2;
                }
            }

            for (int i = 1; i < 4; ++i) {
                if (i == 1) {
                    g.setColor(Color.RED);
                    g.fillOval(x, y, ball_width, ball_width);
                } else if (i == 2) {
                    int lenblocks = blocksList.size();
                    for (int j = 0; j < lenblocks; ++j) {
                        if (blockAvailable.get(j) == true) {
                            int x1 = (int) blocksList.get(j).getX();
                            int y1 = (int) blocksList.get(j).getY();
                            //System.out.println(x1);
                            Color c = blockColors.get(j);
                            g.setColor(c);
                            Graphics2D g2 = (Graphics2D) g;
                            //g2.setStroke(new BasicStroke(2));
                            g2.fillRoundRect(x1, y1, blockwidth, blockheight, 5, 5);
                            g.setColor(Color.WHITE);
                            g2.drawRoundRect(x1, y1, blockwidth, blockheight, 5, 5);
                        }
                    }
                } else if (i == 3) {
                    g.setColor(Color.BLUE);
                    Graphics2D g2 = (Graphics2D) g;
                    g2.translate(M.x, M.y);
                    g2.setStroke(new BasicStroke(7));
                    //g2.drawLine(0, (getSize().height - 20), 250, (getSize().height - 20));
                    g2.fillRoundRect(0, (getSize().height - 20), 250, 20, 15, 15);
                    AffineTransform gg = g2.getTransform();
                    panelpositionY = (getSize().height - 40);
                    panelpositionX = (int) gg.getTranslateX();
                    //System.out.println(panelpositionX);
                }
            }
        }else{  //*************************************************************if the game is end*************************************************************
            g.setColor(Color.WHITE);
            Graphics2D g2 = (Graphics2D) g;
            posx = (getSize().width) / 3;
            posy = (getSize().height) / 2;
            posy2 = posy + 20;
            posxrec = posx - 30;
            posyrec = posy - 30;
            g.setFont(new Font("Comic Sans MS", Font.BOLD, 17));
            if(model.getEndValue() == false){
                score = model.getCounterValue();
                model.setEndValue(true);
            }
            g2.drawString("Good Job but Game Over ^--^", posx, posy);
            g2.drawString("Your Score is " + score, posx, posy2);
            g2.setStroke(new BasicStroke(5));
            g2.drawRoundRect(posxrec, posyrec, 300, 75,
            20, 20);
        }
    }

    public boolean hittest2(int startx, int starty, int wid, int hei){
        int endx = startx + wid;
        int endy = starty + hei;
        if((endx >= Mstart.getX()) && (startx <= Mstart.getX()) && (endy >= Mstart.getY()) && (starty <= Mstart.getY())){
            return true;
        }
        return false;
    }

    public void handle_animation2() {
        //System.out.println(model.speed);
        boolean hit = hittest(x, y, panelpositionX, panelpositionY, panellength, 1);
        if(hit == true){ // *************************************************************hit test for panel*************************************************************
            model.stateHandler(3, null);
            dy *= -1;
        }else{  // *************************************************************hit test for blocks*************************************************************
            int len = blocksList.size();
            for (int i = 0; i < len; ++i) {
                //ONLY do hittest with blocks when it's available
                if (blockAvailable.get(i) == true) {
                    int x2 = (int) blocksList.get(i).getX();
                    int y2 = (int) blocksList.get(i).getY() + blockheight;
                    int y3 = (int) blocksList.get(i).getY();
                    int x5 = (int) blocksList.get(i).getX() + blockwidth;
                    //TYPE: 2 means bot line, 3 means upper line, 4 means left, 5 means right
                    boolean hit2 = hittest(x, y, x2, y2, blockwidth, 2);
                    boolean hit3 = hittest(x, y, x2, y3, blockwidth, 3);
                    boolean hit4 = hittest(x, y, x2, y3, blockheight, 4);
                    boolean hit5 = hittest(x, y, x5, y3, blockheight, 5);
                    if ((hit2 == true) || (hit3 == true)) {
                        model.stateHandler(3, null);
                        dy *= -1;
                        //blocksList.remove(i);
                        //blockColors.remove(i);
                        blockAvailable.set(i, false);
                        break;
                    } else if ((hit4 == true) || (hit5 == true)) {
                        model.stateHandler(3, null);
                        dx *= -1;
                        //blocksList.remove(i);
                        //blockColors.remove(i);
                        blockAvailable.set(i, false);
                        break;
                    }
                }
            }
        }
            //*************************************************************hit test for edges*************************************************************
            // if we hit the edge of the window, change direction
            if (x < 0 || x > (getSize().width - ball_width)) {
                model.stateHandler(3, null);
                dx *= -1;
            }
            if (y < 0) {
                model.stateHandler(3, null);
                dy *= -1;
            }
            //*************************************************************end the game if hitting the ground*************************************************************
            if(y > (getSize().height - ball_width)){
                model.stateHandler(4, null);
                gameover = true;
                repaint();
                mb.repaint();
            }

        // move the ball
        x += dx;
        y += dy;

        // force painting
       // repaint();
       // mb.repaint();
    }

    public void handle_animation(){
       repaint();
       mb.repaint();
    }

    //hittest for ball, testing balls hitting for both panel and blocks
    public boolean hittest(int x, int y, int x1, int y1, int len, int type) {
        double distance;
        if((type == 1) || (type == 2) || (type == 3)) {
            int x2 = x1 + len;
            distance = Line2D.ptSegDist(x1, y1, x2, y1, x, y);
        }else{
            int y2 = y1 + len;
            distance = Line2D.ptSegDist(x1, y1, x1, y2, x, y);
        }

        if(type == 1) {
            if (distance - ball_width <= 10) {
                return true;
            }
        }else{
            if (distance <= 7) {
                return true;
            }
        }
        return false;
    }


}

class View extends JPanel implements IView {

    // the view's main user interface
    //set to default value
    static int FPS = 30;
     int V = 1;
    // the model that this view is showing
    static Model model;
    //private boolean gameover = false;

    public View(Model model, int V, int FPS) {
        // set the model
        this.model = model;
        //set the speed and the FPS
        this.FPS = FPS;
        this.V = V;
        model.V = V;
        model.FPS = FPS;
        model.stateHandler(5, null);
        //set the layout
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 3));
        MenubarLayout menu = new MenubarLayout(model);
        Canvas canvas = new Canvas(model, menu);
        canvas.V = V;
        Component one = Box.createVerticalStrut(5);
        one.setBackground(Color.BLACK);
        //canvas.setLayout(new BorderLayout());

        //System.out.println(canvas.getPreferredSize());
        //********************************************add the menu bar to the View********************************************
        this.add(menu);
        this.add(one);
        /*button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                model.stateHandler(1, canvas);
            }
        });*/
        //********************************************add the main area layout two the View********************************************
        this.add(canvas);
        canvas.setBackground(Color.BLACK);
        menu.setBackground(Color.BLACK);
        canvas.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if((canvas.start == true) && (canvas.gameover == false)) {
                    if(e.getExtendedKeyCode() == 27){
                        canvas.gameover = true;
                        model.stateHandler(4, null);
                        canvas.repaint();
                        menu.repaint();
                    }else if(e.getExtendedKeyCode() == 32){
                        canvas.run = !canvas.run;
                    }
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
            }
        });
        canvas.setFocusable(true);
        canvas.requestFocusInWindow();
    }

    // IView interface
    public void updateView(boolean start, boolean pause, int state, Canvas canvas) {
        if((start == true) && (state == 1)) {
            //draw the ball and corresponding movement
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    createAndShowGUI(canvas);
                }
            });
        }
    }

    private static void createAndShowGUI(Canvas canvas) {
        // timer ticks every time we want to advance a frame
        // scheduled to run every 1000/FPS ms
        Timer timer2 = new Timer();
        TimerTask task2 = new TimerTask()  {
            @Override
            public void run() {
                if(canvas.run) {
                    canvas.handle_animation2();
                }
            }
        };
        timer2.schedule(task2, 0, (100/model.speed));

        Timer timer = new Timer();
        TimerTask task = new TimerTask()  {
            @Override
            public void run() {
                if(canvas.run) {
                    canvas.handle_animation();
                }
            }
        };
        timer.schedule(task, 0, (1000/FPS));


    }

}