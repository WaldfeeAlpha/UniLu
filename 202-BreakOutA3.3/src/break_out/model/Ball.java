package break_out.model;

import break_out.Constants;

/**
 * This class contains the information about the balls characteristics and
 * behavior
 * 
 * @author iSchumacher modified by AN, CB 
 *
 */
public class Ball implements IBall {

	/**
	 * The balls position on the playground
	 */
	private Position position;

	/**
	 * The balls direction
	 */
	private Vector2D direction;

	/**
	 * The constructor of a ball The balls position and direction are initialized
	 * here.
	 */
	public Ball() {
		this.position = new Position((Constants.SCREEN_WIDTH - Constants.BALL_DIAMETER) / 2,
				Constants.SCREEN_HEIGHT - Constants.PADDLE_HEIGHT - Constants.BALL_DIAMETER);
		this.direction = new Vector2D(5.0, -5.0);
		direction.rescale();
	}

	/**
	 * The getter for the balls position
	 * 
	 * @return position The balls current position
	 */
	public Position getPosition() {
		return this.position;
	}

	/**
	 * The getter for the balls direction
	 * 
	 * @return direction The balls current direction
	 */
	public Vector2D getDirection() {
		return this.direction;
	}

	/**
	 * 
	 */
	public void updatePosition() {
		position.setX(position.getX() + direction.getDx());
		position.setY(position.getY() + direction.getDy());
	}

	/**
	 * A1.1 Collision Walls
	 * 
	 */
	public void reactOnBorder() {
		if (position.getX() >= 880 - Constants.BALL_DIAMETER) {
			double newDx = -1 * direction.getDx();
			direction.setDx(newDx);
		} else if (position.getY() <= 0) {
			double newDy = -1 * direction.getDy();
			direction.setDy(newDy);
		} else if (position.getX() <= 0) {
			double newDx = -1 * direction.getDx();
			direction.setDx(newDx);
		} else if (position.getY() >= 750 - Constants.BALL_DIAMETER) // durchmesser abziehen da linker oberer punkt des
																		// balls
		{
			double newDy = -1 * direction.getDy();
			direction.setDy(newDy);
		}
	}

	/**
	 * determineing near paddle + hit
	 *
	 */
	@Override
	public boolean hitsPaddle(Paddle p) {
		// test balls y position against paddles y values
				// paddles y values can be interpreted as a closed interval therefore if balls y position is in the interval, its true
				boolean testPaddleY = (
						p.getPosition().getY() <= this.position.getY() && this.position.getY() <= p.getPosition().getY()+p.getHeight() ||
						p.getPosition().getY() <= this.position.getY()+Constants.BALL_DIAMETER && this.position.getY()+Constants.BALL_DIAMETER <= p.getPosition().getY()+p.getHeight()
				);

				// test balls x position against paddles x values
				// paddles x values can be interpreted as a closed interval therefore if balls x position is in the interval, its true
				boolean testPaddleX = (
						p.getPosition().getX() <= this.position.getX() && this.position.getX() <= p.getPosition().getX()+p.getWidth() ||
						p.getPosition().getX() <= this.position.getX()+Constants.BALL_DIAMETER && this.position.getX()+Constants.BALL_DIAMETER <= p.getPosition().getX()+p.getWidth()
				);
		if (testPaddleY && testPaddleX) {
			return true;
		}else {
			return false;
		}
	}
	/**
	 * 
	 */
	public void reflectOnPaddle(Paddle p) {
		if (p.getPosition().getY() == 0) {
			Position offset = new Position(p.getPosition().getX() + Constants.PADDLE_WIDTH / 2, (Constants.PADDLE_HEIGHT - Constants.REFLECTION_OFFSET));
			Position current = new Position(this.position.getX() + 	Constants.BALL_DIAMETER / 2, this.position.getY() + Constants.BALL_DIAMETER / 2);
			this.direction = new Vector2D(offset, current);
			direction.rescale();
		}else if (p.getPosition().getY() != 0) {
			Position offset = new Position(p.getPosition().getX() + Constants.PADDLE_WIDTH / 2, (Constants.SCREEN_HEIGHT -Constants.PADDLE_HEIGHT + Constants.REFLECTION_OFFSET));
			Position current = new Position(this.position.getX() +Constants.BALL_DIAMETER / 2, this.position.getY() + Constants.BALL_DIAMETER / 2);
			this.direction = new Vector2D(offset, current);
			direction.rescale();
		}
	}
}