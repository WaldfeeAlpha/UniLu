package break_out.model;

import break_out.Constants;
import break_out.model.Position;

/**
 * This class represent a two dimensional vector.
 * 
 * @author I. Schumacher modifide by AN, CB
 */
public class Vector2D {

	/**
	 * The x part of the vector
	 */
	private double dx;

	/**
	 * The y part of the vector
	 */
	private double dy;
	

	/**
	 * This constructor creates a new vector with the given x and y parts.
	 * 
	 * @param dx the delta x part for the new vector
	 * @param dy the delty y part for the new vector
	 */
	public Vector2D(double dx, double dy) {
		this.dx = dx;
		this.dy = dy;
	 }
	
	/**
	 * This constructor creates a new vector with the given x and y parts.
	 * 
	 * @param posStart starting pos. 
	 * @param posEnd end pos.
	 */
	public Vector2D(Position posStart, Position posEnd) {
		this.dx = posEnd.getX()-posStart.getX();
		this.dy = posEnd.getY()-posStart.getY();
	}

	/**
	 * Getter for the dx-part
	 * 
	 * @return dx The dx part of this vector
	 */
	public double getDx() {
		return dx;
	}

	/**
	 * Setter for the dx-part
	 * 
	 * @param dx The new dx part of this vector
	 */
	public void setDx(double dx) {
		this.dx = dx;
	}

	/**
	 * Getter for the dy-part
	 * 
	 * @return dy The dy part of this vector
	 */
	public double getDy() {
		return dy;
	}

	/**
	 * Setter for the dy-part
	 * 
	 * @param dy The new dy part of this vector
	 */
	public void setDy(double dy) {
		this.dy = dy;
	}
	
	/**
	 * Method rescale Einheitsvektor bilden
	 */
	public void rescale () {
		double laenge=Math.sqrt(dx*dx+dy*dy);
		this.dx=dx/laenge*Constants.BALL_SPEED;
		this.dy=dy/laenge*Constants.BALL_SPEED;
	}
}
