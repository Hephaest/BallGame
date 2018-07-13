/**
 * This class mainly use functions of GUI combined some knowledges about Graphics.
 * @author Miao Cai 
 * Class: CS1602 in Lancaster University
 * Student ID:16722043
 * @version 2018/4/10
 * @since jdk_1.8.162
 */
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
public class BallFrame extends JFrame {
	private ArrayList<Ball> ball = new ArrayList<Ball>(); 
	private Image img;	
	private Graphics2D graph;
	/**
	 * Jpanel helps us create text fields and buttons row by row.
	 */
	 // set up row 1
    JPanel row1 = new JPanel();
    JLabel mass = new JLabel("mass:", JLabel.RIGHT);
    JTextField massText = new JTextField("0");
    JLabel xSpeed = new JLabel("xSpeed:", JLabel.RIGHT);
    JTextField xSpeedText = new JTextField("0");
    JLabel xPosition = new JLabel("xPosition:", JLabel.RIGHT);
    JTextField xPositionText = new JTextField("0");
    JLabel size = new JLabel("size:", JLabel.RIGHT);
    JTextField sizeText = new JTextField("0");
    JLabel ySpeed = new JLabel("ySpeed:", JLabel.RIGHT);
    JTextField ySpeedText = new JTextField("0");
    JLabel yPosition = new JLabel("yPosition:", JLabel.RIGHT);
    JTextField yPositionText = new JTextField("0");
    // set up row 2
    JPanel row2 = new JPanel();
    JButton stop = new JButton("Stop");
    JButton Continue = new JButton("Continue");
    JButton clear = new JButton("Clear");
    JButton play = new JButton("Play");
    JButton reset = new JButton("Reset");
    public BallFrame()
    {
    	super("BallGame!");
    	setSize(600, 600);
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	/**
    	 * set to BorderLayout
    	 * This borderLayout do nothing important except makes our rows at the top.
    	 * if user perfer bottom, all we need is to change "NORTH" into "South".
    	 */
        setLayout(new BorderLayout());
        add(row1, BorderLayout.NORTH);
        add(row2, BorderLayout.NORTH);
        //set to GridLayout whole framework
    	GridLayout layout = new GridLayout(5,2,10,10);
    	setLayout(layout);
    	//specify how many text fields should be filled in one line.
    	GridLayout layout2 = new GridLayout(2, 3, 10, 10);
    	row1.setLayout(layout2);
    	// the following process is to add them into window
    	row1.add(mass);
    	massText=new JTextField();
    	row1.add(massText);
    	row1.add(xSpeed);
    	xSpeedText=new JTextField();
    	row1.add(xSpeedText);
    	row1.add(xPosition);
    	xPositionText=new JTextField();
    	row1.add(xPositionText);
    	row1.add(size);
    	sizeText=new JTextField();
    	row1.add(sizeText);
    	row1.add(ySpeed);
    	ySpeedText=new JTextField();
    	row1.add(ySpeedText);
    	row1.add(yPosition);
    	yPositionText=new JTextField();
    	row1.add(yPositionText);
    	add(row1);
    	//make button in the center
        FlowLayout layout3 = new FlowLayout(FlowLayout.CENTER,
                10, 10);
        row2.setLayout(layout3);
        row2.add(play);
        row2.add(stop);
        row2.add(reset);
        row2.add(Continue);
        row2.add(clear);
        add(row2);
        
        setResizable(false);
    	setVisible(true);
    }
    //Main function, nothing else need to explain...
	public static void main(String[] args) {
		BallFrame.setLookAndFeel();
		BallFrame bf = new BallFrame();
		bf.UI();
	}

	public void UI() {	    	                   
		Listener lis = new Listener(this, ball);	
		// Add listeners
		this.addMouseListener(lis);	
		clear.addActionListener(lis);
		Continue.addActionListener(lis);
    	stop.addActionListener(lis);
        play.addActionListener(lis);
        reset.addActionListener(lis);
		Thread current = new Thread(lis);     
		current.start();						

	}
	/**
	 * This method is to ensure across operation system could have a ability to show window
	 */
    private static void setLookAndFeel() {
        try {
            UIManager.setLookAndFeel(
                "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel"
            );
        } catch (Exception exc) {
            // ignore error
        }
    }
	/**
	 * This method is used to help us create a canvas.
	 */
	public void paint(Graphics g) {
		img = this.createImage(this.getWidth(), this.getHeight());	
		graph = (Graphics2D)img.getGraphics();;		
		//Anti-aliasing,make ball look more smooth 
		graph.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON); 
		//for each ball, do the same process, after collision we'd better whether it's going to hit boundary or not.
		for (int i = 0; i < ball.size(); i++) {
			Ball myball = (Ball) ball.get(i);	
//			myball.clearBall(graph, this);		
			myball.moveBall(graph,this);					
			myball.collision(graph,ball);
			myball.moveBall(graph,this);
			myball.drawBall(graph);				
		} 
		//If the canvas's height over 160, it will hide our buttons.
		g.drawImage(img, 0, 160, this);	
	}
	
}
