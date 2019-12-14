package break_out.model;

import java.awt.Color;

public class Stone implements IStone{
	private int type;
	private int value;
	private Color color;
	private Position position;

	public Stone(int type, Position position){
		this.type = type;
		this.position = position;
	if(type == 1) {
		this.value = 5;
		this.color = Color.DARK_GRAY;
	}
	else if(type == 2){
		this.value = 10;
		this.color = Color.CYAN;
	}
	else if(type == 3){
		this.value = 15;
		this.color = Color.RED;
	}
	}
	
	
    public int getType() {
    	return type;
    }
    public int getValue() {
    	return value;
    }
    public Color getColor() {
    	return color;
    }
    public Position getPosition() {
    	return position;
    }
    public void setType(int type) {
    	this.type=type;
    }
    public void setValue(int value) {
    	this.value=value;
    }
    public void setColor(Color color) {
    	this.color=color;
    }
    public void setPosition(Position position) {
    	this.position=position;
    }
}
