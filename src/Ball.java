/**
 * This class is aimed to declare a ball and the method of moving, collision,draw and clear it
 * @author Hephaest
 * @version 2019/7/5
 * @since jdk_1.8.202
 */
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class Ball{

	/**
	 * declare variables of ball
	 */
	private int xPos, yPos, size, xSpeed, ySpeed,mass;
	private Color color;
	private BallFrame bf;

	/**
	 * Constructor for objects of class Ball
	 * @param xPos the x position of the ball.
	 * @param yPos the y position of the ball.
	 * @param size the size of the ball.
	 * @param xSpeed the x speed of the ball.
	 * @param ySpeed the y speed of the ball.
	 * @param color the color of the ball.
	 * @param mass the mass of the ball.
	 * @param bf the instance of the BallFrame.
	 */
	public Ball(int xPos, int yPos, int size, int xSpeed, int ySpeed, Color color, int mass, BallFrame bf) {
		super();
		this.xPos = xPos;
		this.yPos = yPos;
		this.size = size;
		this.xSpeed = xSpeed;
		this.ySpeed = ySpeed;
		this.color = color;
		this.mass = mass;
		this.bf = bf;
	}

	/**
	 * use method of Graphics to create a colorful ball
	 * @param g A ball
	 */
	public void drawBall(Graphics g) {
		if(xPos + size> bf.getWidth() - 4) xPos = bf.getWidth() - size - 4;
		else if(xPos < 4) xPos = 4;
		if(yPos < 4) yPos = 4;
		else if(yPos > bf.getHeight()) yPos = bf.getHeight() - size - 4;
		g.setColor(color);		
		g.fillOval(xPos, yPos, size, size);	
	}

	/**
	 * This method tries to figure out
	 * if ball is going to hit the boundary,change its direction
	 * @param bf the instance of the BallFrame.
	 */
	public void moveBall(BallFrame bf) {
		if (xPos + size + xSpeed > bf.getWidth() - 4 || xPos + xSpeed < 4)
		{
			xSpeed = -xSpeed;
		}
		if (yPos + ySpeed < 2 || yPos + size + ySpeed > bf.getHeight() - 163)
		{
			ySpeed = - ySpeed;
		}
		xPos += xSpeed;		
		yPos += ySpeed;

	}

	/**
	 * This method is used to figure out whether collision could happen.
	 * And Try not to overlap balls.
	 * @param balls whole balls.
	 */
	public void collision(ArrayList<Ball> balls) {
		for (int i = 0; i < balls.size(); i++) {
			Ball ball = balls.get(i);
			if (ball != this) {		
				double d1 = Math.abs(this.xPos - ball.xPos);	
				double d2 = Math.abs(this.yPos - ball.yPos);	
				double d3 = Math.sqrt(Math.pow(d1,2) + Math.pow(d2,2));	
				if (d3 <= (this.size / 2 + ball.size / 2)) {
					if (this.xPos > ball.xPos) {
						xPos++;
						while(Math.sqrt(Math.pow(this.xPos - ball.xPos,2) + Math.pow(d2,2)) < this.size / 2 + ball.size / 2) xPos++;
					} else {
						ball.xPos++;
						while(Math.sqrt(Math.pow(ball.xPos - this.xPos,2) + Math.pow(d2,2)) < this.size / 2 + ball.size / 2) ball.xPos++;
					}

					/* Using perfectly elastic collision */
					this.xSpeed=((this.mass - ball.mass) * this.xSpeed + 2 * ball.mass * ball.xSpeed)/(this.mass + ball.mass);
					this.ySpeed=((this.mass - ball.mass) * this.ySpeed + 2 * ball.mass * ball.ySpeed)/(this.mass + ball.mass);
					ball.xSpeed=((ball.mass - this.mass) * ball.xSpeed + 2 * this.mass * this.xSpeed)/(this.mass + ball.mass);
					ball.ySpeed=((ball.mass - this.mass) * ball.ySpeed + 2 * this.mass * this.ySpeed)/(this.mass + ball.mass);
				}
			}
		}

	}

}
