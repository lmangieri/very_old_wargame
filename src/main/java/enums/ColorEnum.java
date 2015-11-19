package enums;

import java.awt.Color;

public enum ColorEnum {
	WHITE (Color.WHITE,"white"),
	BLACK (Color.BLACK,"black"),
	GREEN (Color.GREEN,"green"),
	RED (Color.RED,"red"),
	YELLOW (Color.YELLOW,"yellow"),
	BLUE (Color.BLUE,"blue");
	
	private Color color;
	private String name;
	
	ColorEnum(Color color,String name) {
		this.color = color;
		this.name = name;
	}
	
	public Color getColor() { 
		return color;
	}
	
	public String getName() {
		return this.name;
	}
}
