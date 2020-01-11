package break_out.model;

import java.awt.Color;

import break_out.Constants;

/**
 * 
 * @author AN,CB,Groupe 202
 * This class contains the information about the paddles characteristics and behavior
 *
 */
public class Paddle implements IPaddle{

	private int width = Constants.PADDLE_WIDTH;
	private int height = Constants.PADDLE_HEIGHT;
	private Color color = Color.WHITE;
	private Position position;
	private int direction=0;
	/**
	 * constructor for paddle
	 * @param position
	 */
	public Paddle(Position position) {
		this.position = position;
	}
	
	@Override
	public Position getPosition() {
		return position;
	}

	@Override
	public void setPosition(Position position) {
		this.position = position;
	}

	@Override
	public Color getColor() {
		return color;
	}

	@Override
	public void setColor(Color color) {
		this.color = color;
	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public void setWidth(int width) {
		this.width =  width;
	}

	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public void setHeight(int height) {
		this.height = height;
	}
    public int getDirection() {
    	return direction;
    }
    public void setDirection(int direction) {
    	this.direction=direction;
    }
    public void updatePosition(Ball ball) {
    	
    	if((position.getX() <= 0 && this.direction == -1) || 
    	  (this.position.getX() + Constants.PADDLE_WIDTH >= Constants.SCREEN_WIDTH && this.direction == 1)){
    	}else {
    		this.position.setX(position.getX() + this.direction * Constants.DX_MOVEMENT);}
    }
}
