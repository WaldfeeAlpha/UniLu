package break_out.model;

public interface ILevel {
    // Exercise 1
    public Ball getBall();
    
    public Paddle getPaddleTop();
    public Paddle getPaddleBottom();
    // Exercise 3
    public void setFinished(boolean finished);
}
