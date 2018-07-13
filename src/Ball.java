/**
 * This class is aimed to declare a ball and the method of moving, collision,draw and clear it
 * @author Miao Cai 
 * Class: CS1602 in Lancaster University
 * Student ID:16722043
 * @version 2018/4/10
 * @since jdk_1.8.162
 */
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JFrame;

public class Ball{
	/**
	 * declare variables of ball
	 */
	private int xPos, yPos, size, xSpeed, ySpeed,mass;
	private Color color;
	/**
	 * Constructor for objects of class Ball
	 */
	public Ball(int xPos, int yPos, int size, int xSpeed, int ySpeed, Color color,int mass) {
		super();
		this.xPos = xPos;
		this.yPos = yPos;
		this.size = size;
		this.xSpeed = xSpeed;
		this.ySpeed = ySpeed;
		this.color = color;
		this.mass=mass;
	}
	/**
	 * use method of Graphics to create a colorful ball
	 * @param g A ball
	 */
	public void drawBall(Graphics g) {
		g.setColor(color);		
		g.fillOval(xPos, yPos, size, size);	
	}
	/**
	 * This method tries to figure out
	 * @param g A ball
	 * if ball is going to hit the boundary,change its direction
	 */
	public void moveBall(Graphics g,BallFrame bf) {
		if (xPos+size>bf.getWidth() || xPos <0) 
		{
			xSpeed = -xSpeed;
		}
		if (yPos<0 ||(yPos+size >bf.getHeight()-160)) 
		{
			ySpeed = -ySpeed;
		}
		xPos += xSpeed;		
		yPos += ySpeed;		
	}

//	public void clearBall(Graphics g, BallFrame bf) {
//		g.setColor(bf.getBackground());		
//		g.fillOval(xPos, yPos, size,size );		
//	}

	public void collision(Graphics g, ArrayList<Ball> balls) {
		for (int i = 0; i < balls.size(); i++) {
			Ball ball = (Ball) balls.get(i);	
			if (ball != this) {		
				double d1 = Math.abs(this.xPos - ball.xPos);	
				double d2 = Math.abs(this.yPos - ball.yPos);	
				double d3 = Math.sqrt(Math.pow(d1,2) + Math.pow(d2,2));	
				if (d3 <= (this.size / 2 + ball.size / 2)) {
				/**
				 * Using perfectly elastic collision 
				 */
//					this.xSpeed=((this.mass-ball.mass)*this.xSpeed+2*ball.mass*ball.xSpeed)/(this.mass+ball.mass);
//					this.ySpeed=((this.mass-ball.mass)*this.ySpeed+2*ball.mass*ball.ySpeed)/(this.mass+ball.mass);
//					ball.xSpeed=((ball.mass-this.mass)*ball.xSpeed+2*this.mass*this.xSpeed)/(this.mass+ball.mass);
//					ball.ySpeed=((ball.mass-this.mass)*ball.ySpeed+2*this.mass*this.ySpeed)/(this.mass+ball.mass);
					/**
					 * The ball only bounces without transmitting energy.
					 */
					this.xSpeed=-this.xSpeed;
					this.ySpeed=-this.ySpeed;
					ball.xSpeed=-ball.xSpeed;
					ball.ySpeed=-ball.ySpeed;
				}
			}
		}

	}

}
