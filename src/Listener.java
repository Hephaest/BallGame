/**
 * This class is aimed to receive event from clicking in BallFrame GUI.
 * you can generate a ball by clicking mouse or click responding button ("Play").  
 * @author Miao Cai 
 * Class: CS1602 in Lancaster University
 * Student ID:16722043
 * @version 2018/4/10
 * @since jdk_1.8.162
 */
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;

public class Listener extends MouseAdapter implements ActionListener,Runnable {
	/**
	 * declare variables of Listener
	 * We must decide when should change the boolean value of clearFlag and pauseFlag. 
	 */
	private BallFrame bf;	
	private Graphics g;		 
	private Random rand = new Random();
	private volatile boolean clearFlag = false, pauseFlag = false;
	private ArrayList<Ball> ball = new ArrayList<Ball>();
	Thread playing;
	/**
	 * Constructor for objects of class Listener
	 * Not only should we control JTextField and Jbutton in BallFrame, but also numbers of all.
	 */
	public Listener(BallFrame bf, ArrayList ball) {
		this.bf = bf;
		this.ball = ball;
	}
	/**
	 * I perfer to call this method as "naughty mouse"
	 * each time we click our mouse,we don't know the exact information of such ball because of random choice.
	 */
	public void mousePressed(MouseEvent e) {
		int x = e.getX();	
		int y = e.getY();	
		Ball myball = new Ball(x, y, 30, (1+rand.nextInt(9)*(Math.random()>0.5?1:-1)),
				(1+rand.nextInt(9)*(Math.random()>0.5?1:-1)), 
				new Color(rand.nextInt(255),rand.nextInt(255),rand.nextInt(255)),rand.nextInt(9)+1);
		ball.add(myball);
	}
	@Override
	/**
	 * This method is from Runnable
	 * Don't change clearFlag easily! 
	 * if the condition of while loop is false, everything stops!
	 */
	public void run() {
		while (!clearFlag) {
			if(!pauseFlag)
			{
				bf.repaint();	
				try {
					Thread.sleep(30);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
			
	/**
	 * This method is used to make a respond to a button.
	 */
	
	public void actionPerformed(ActionEvent event) {
        String command = event.getActionCommand();
        if (command.equals("Play")) {
            startPlaying();
        }
        if (command.equals("Stop")) {
            stopPlaying();
        }
        if (command.equals("Reset")) {
            setAllFields();
        }
        if (command.equals("Continue")) {
            continuePlaying();
        }
        if (command.equals("Clear")) {
            clearPlaying();
        }
    }
	/**
	 * This method is used to make a respond to "Reset".
	 * Every text field will be set as default.
	 * And user cannot click "Reset" again or "Continue".
	 */
	void setAllFields() {
		bf.massText.setText("0");
		bf.xSpeedText.setText("0");
		bf.xPositionText.setText("0");
		bf.sizeText.setText("0");
		bf.ySpeedText.setText("0");
		bf.yPositionText.setText("0");
		bf.reset.setEnabled(false);
		bf.play.setEnabled(true);
		bf.Continue.setEnabled(false);
		bf.clear.setEnabled(true);
    }
	/**
	 * This method is used to make a respond to "Play".
	 * we create a new thread and let clearFlag is false, which means run() can run.
	 * we need to convert Jpanel into int. Then we could use Ball class to define each ball.
	 * And user cannot click "play" again or "Continue".
	 */
	void startPlaying() {   
        playing = new Thread(this);
        playing.start();
        clearFlag = false;
        bf.play.setEnabled(false);
        bf.Continue.setEnabled(false);
        bf.stop.setEnabled(true);
        bf.reset.setEnabled(true);
        bf.clear.setEnabled(true);
        String xP=bf.xPositionText.getText();
        int x=Integer.parseInt(xP);
        String yP=bf.yPositionText.getText();
        int y=Integer.parseInt(yP);
        String Size=bf.sizeText.getText();
        int size=Integer.parseInt(Size);
        String Xspeed=bf.xSpeedText.getText();
        int xspeed=Integer.parseInt(Xspeed);
        String Yspeed=bf.ySpeedText.getText();
        int yspeed=Integer.parseInt(Yspeed);
        String Mass=bf.massText.getText();
        int mass=Integer.parseInt(Mass);
        Ball myball = new Ball(x, y, size, xspeed,yspeed, 
				new Color(rand.nextInt(255),rand.nextInt(255),rand.nextInt(255)),mass);
		ball.add(myball);
    }
	/**
	 * This method is used to make a respond to "Stop".
	 * It seems all ball stop but this isn't caused by thread, we just do not repaint.
	 * And user cannot click "Stop" again.
	 */
    void stopPlaying() {
        bf.stop.setEnabled(false);
        bf.play.setEnabled(true);
        bf.reset.setEnabled(true);
        bf.Continue.setEnabled(true);
        bf.clear.setEnabled(true);
        pauseFlag=true;
    }
    /**
	 * This method is used to make a respond to "Continue".
	 * we change the value of pauseFlag in order to repaint window again and again.
	 * we should remember that our thread "Playing" still working!
	 * And user cannot click "Continue" again.
	 */
    void continuePlaying()
    {
    	bf.stop.setEnabled(true);
        bf.play.setEnabled(true);
        bf.reset.setEnabled(true);
        bf.Continue.setEnabled(false);
        bf.clear.setEnabled(true);
        pauseFlag=false;
    }
    /**
	 * This method is used to make a respond to "Clear".
	 * In this time, I suggest that our thread should be declare as null to decrease waste space of CPU.
	 * The previous step is not enough to draw a blank window, the next step is to clear all ball and repaint.
	 * And user cannot click "Clear" again, "Stop" and "Continue".
	 */
    void clearPlaying()
    {
    	bf.clear.setEnabled(false);
    	bf.stop.setEnabled(false);
        bf.play.setEnabled(true);
        bf.reset.setEnabled(true);
        bf.Continue.setEnabled(false);
        playing = null;
        clearFlag = true;
        ball.clear();
        bf.repaint();
    }
}

