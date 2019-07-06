/**
 * This class mainly use functions of GUI combined some knowledge about Graphics.
 * @author Hephaest
 * @version 2019/7/5
 * @since jdk_1.8.202
 */
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.util.ArrayList;

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
	 * JPanel helps us create text fields and buttons row by row.
	 */
    JPanel row1 = new JPanel();
    JLabel mass = new JLabel("mass:", JLabel.RIGHT);
    JTextField massText, xSpeedText, xPositionText, sizeText, ySpeedText, yPositionText;
    JLabel xSpeed = new JLabel("xSpeed:", JLabel.RIGHT);
    JLabel xPosition = new JLabel("xPosition:", JLabel.RIGHT);
    JLabel size = new JLabel("size:", JLabel.RIGHT);
    JLabel ySpeed = new JLabel("ySpeed:", JLabel.RIGHT);
    JLabel yPosition = new JLabel("yPosition:", JLabel.RIGHT);

    JPanel row2 = new JPanel();
    JButton stop = new JButton("Stop");
    JButton Continue = new JButton("Continue");
    JButton clear = new JButton("Clear");
    JButton play = new JButton("Play");
    JButton reset = new JButton("Reset");

	/**
	 * Constructor for objects of class BallFrame
	 */
	public BallFrame()
    {
    	super("BallGame");
    	setSize(600, 600);
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    	//specify how many text fields should be filled in one line.
    	row1.setLayout(new GridLayout(2, 3, 10, 10));

    	// the following process is to add them into window
    	row1.add(mass);
    	massText = new JTextField("1");
    	row1.add(massText);
    	row1.add(xSpeed);
    	xSpeedText = new JTextField("1");
    	row1.add(xSpeedText);
    	row1.add(xPosition);
    	xPositionText = new JTextField("0");
    	row1.add(xPositionText);
    	row1.add(size);
    	sizeText = new JTextField("50");
    	row1.add(sizeText);
    	row1.add(ySpeed);
    	ySpeedText = new JTextField("1");
    	row1.add(ySpeedText);
    	row1.add(yPosition);
    	yPositionText = new JTextField("0");
    	row1.add(yPositionText);
    	add(row1,"North");

    	//make buttons in the center
        FlowLayout layout3 = new FlowLayout(FlowLayout.CENTER, 10, 10);
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

	/**
	 * Add listeners.
	 */
	public void UI() {	    	                   
		Listener lis = new Listener(this, ball);
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
	 * This method is to ensure across operation system could have an ability to show window
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
		// Need to repaint!
		row1.repaint(0,0,this.getWidth(), 80);
		row2.repaint(0,0,this.getWidth(), 42);
		img = this.createImage(this.getWidth(), this.getHeight());
		graph = (Graphics2D)img.getGraphics();
		//Anti-aliasing,make ball look more smooth
		graph.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		graph.setBackground(getBackground());
		//for each ball, do the same process, after collision we'd better whether it's going to hit boundary or not.
		for (int i = 0; i < ball.size(); i++) {
			Ball myBall = ball.get(i);
			myBall.drawBall(graph);
			myBall.collision(ball);
			myBall.moveBall(this);
		}
		//If the canvas's height over 160, it will hide our buttons.
		g.drawImage(img, 0, 150, this);
	}
	
}
