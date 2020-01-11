package break_out.model;
import java.awt.Robot;
import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import break_out.Constants;
import break_out.controller.JSONReader;

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

	private ArrayList<Stone>stones=new ArrayList<Stone>();
	
	/**
	 * The number of the level
	 */
	private int levelnr;

	private int vita;
	
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
				
				if(ball.hitsStone(stones)) {
				Rectangle ballRect = new Rectangle((int)ball.getPosition().getX(),(int)ball.getPosition().getY(),
						Constants.BALL_DIAMETER, Constants.BALL_DIAMETER);
				Rectangle stoneRect = new Rectangle((int)ball.getHitStone().getPosition().getX(),(int)ball.getHitStone().getPosition().getY(),
						(Constants.SCREEN_WIDTH/Constants.SQUARES_X)-3, (Constants.SCREEN_HEIGHT/Constants.SQUARES_Y)-3);
				ball.reflectOnStone(ballRect, stoneRect);
				updateStonesAndScore();
				if(allStonesBroken()) {
					setFinished(true);
					Robot robot;
					try {
						robot = new Robot();
						robot.keyPress(KeyEvent.VK_ESCAPE);
						robot.keyRelease(KeyEvent.VK_ESCAPE);
					} catch (AWTException e) {
						
					}
					
				}
				}
		

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
		JSONReader reader = new JSONReader("res/Level"+levelnr+".json");
		int[][] stoneTypes = reader.getStones2DArray();
		this.vita=reader.getLifeCounter();
		
		for(int y=0; y<Constants.SQUARES_Y; y++) {
			for(int x=0; x<Constants.SQUARES_X; x++) {
				if(stoneTypes[y][x] !=0) {
					Position stonePosition = new Position((Constants.SCREEN_WIDTH/Constants.SQUARES_X)*x,
														 (Constants.SCREEN_HEIGHT/Constants.SQUARES_Y)*y);
					Stone stone = new Stone(stoneTypes[y][x], stonePosition);
					stones.add(stone);
				}
			}
			
		}
	}
	private boolean allStonesBroken() {
		return this.stones.size()==0;
	}
	

	private void updateStonesAndScore() {
		this.score = ball.getHitStone().getValue();
		if(ball.getHitStone().getType() -1>0) {
			ball.getHitStone().setType(ball.getHitStone().getType()-1);
		}else{
		this.stones.remove(ball.getHitStone());	
		}
	}
	
	@Override
	public Paddle getPaddleTop() {
		return this.paddleTop;
	}

	@Override
	public Paddle getPaddleBottom() {
		return this.paddleBottom;
	}
	
	public ArrayList<Stone> getStone(){
		ArrayList<Stone> exemplum=new ArrayList<>();
		exemplum.addAll(stones);
		return exemplum;
	}
	
	@Override
	public void setFinished(boolean finished) {
		this.finished=finished;
	}

	@Override
	public ArrayList<Stone> getStones() {
		// TODO Auto-generated method stub
		return null;
	}
}
