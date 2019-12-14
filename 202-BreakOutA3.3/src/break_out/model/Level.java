package break_out.model;

import break_out.Constants;

/**
 * This class contains information about the running game
 * 
 * @author dmlux
 * @author I. Schumacher modified by AN,CB 202
 */
public class Level extends Thread implements ILevel {

	/**
	 * The game to which the level belongs
	 */
	private Game game;

	/**
	 * The number of the level
	 */
	private int levelnr;

	/**
	 * The score of the level
	 */
	private int score;

	/**
	 * The ball of the level
	 */
	private Ball ball;

	
	/**
	 * The bottom paddle of the level
	 */
	private Paddle paddleBottom;
	/**
	 * The top paddle of the level
	 */
	private Paddle paddleTop;



	/**
	 * Flag that shows if the ball was started
	 */
	private boolean ballWasStarted = false;
	
	private boolean finished=false;

	/**
	 * The constructor creates a new level object and needs the current game object,
	 * the number of the level to be created and the current score
	 * 
	 * @param game    The game object
	 * @param levelnr The number of the new level object
	 * @param score   The score
	 */
	public Level(Game game, int levelnr, int score) {
		this.game = game;
		this.levelnr = levelnr;
		this.score = score;
		this.ball = new Ball();
		this.paddleBottom = new Paddle(new Position((Constants.SCREEN_WIDTH - Constants.PADDLE_WIDTH) / 2,
				Constants.SCREEN_HEIGHT - Constants.PADDLE_HEIGHT));
		this.paddleTop = new Paddle(new Position((Constants.SCREEN_WIDTH - Constants.PADDLE_WIDTH) / 2, 0));
		loadLevelData(levelnr);
	}

	/**
	 * The getter for the ball object
	 * 
	 * @return ball The ball of the level
	 */
	public Ball getBall() {
		return this.ball;
	}

	/**
	 * Sets ballWasStarted to true, the ball is moving
	 */
	public void startBall() {
		ballWasStarted = true;
	}

	/**
	 * Sets ballWasStarted to false, the ball is stopped
	 */
	public void stopBall() {
		ballWasStarted = false;
	}

	/**
	 * Returns if the ball is moving or stopped
	 * 
	 * @return ballWasStarted True: the ball is moving; false: the ball is stopped
	 */
	public boolean ballWasStarted() {
		return ballWasStarted;
	}

	/**
	 * The method of the level thread
	 */
	public void run() {
		game.notifyObservers();

		// endless loop
		while (!finished) {
			// if ballWasStarted is true, the ball is moving
			if (ballWasStarted) {

				// Call here the balls method for updating his position on the playground
				ball.updatePosition();

				// Call here the balls method for reacting on the borders of the playground
				ball.reactOnBorder();

				// Tells the observer to repaint the components on the playground
				game.notifyObservers();
				
				paddleTop.updatePosition(ball);
				paddleBottom.updatePosition(ball);
				// hitting test of the paddles
				if (ball.hitsPaddle(paddleTop)) {ball.reflectOnPaddle(paddleTop);}
				
				if (ball.hitsPaddle(paddleBottom)) {ball.reflectOnPaddle(paddleBottom);}
			}
			// The thread pauses for a short time
			try {
				Thread.sleep(4);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Loads the information for the level from a json-file located in the folder
	 * /res of the project
	 * 
	 * @param levelnr The number X for the LevelX.json file
	 */
	private void loadLevelData(int levelnr) {

	}

	@Override
	public Paddle getPaddleTop() {
		return this.paddleTop;
	}

	@Override
	public Paddle getPaddleBottom() {
		return this.paddleBottom;
	}
	@Override
	public void setFinished(boolean finished) {
		this.finished=finished;
	}
}
