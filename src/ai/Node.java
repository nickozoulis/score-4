package ai;

import java.awt.Point;

public class Node {
	// Nodes's coordinates
	private Point point;		
	// 1 , -1 for player's Nodes, 0 for no Node.
	private int value;


	/*
	 * Default Constructor
	 */
	public Node() {		
		setPoint(new Point());
		setValue(0);		
	}
	
	
	public Node(int x, int y) {
		setPoint(new Point(x, y));
		setValue(0);
	}
	
	
	/*
	 *  Copy Constructor
	 */
	public Node(Node p) {
		setPoint(new Point(p.point.x, p.point.y));
		setValue(p.getValue());	
	}
	
	
	@Override
	public boolean equals(Object obj) {
		Node p = (Node)obj;
		
		if (this.value == p.value && p.value == 1 || p.value == -1)
			return true;
		else 
			return false;
	}
	
	
	/*
	 * Appropriate method to get a Node's neighbour Nodes, according to the parameter given.
	 * 0 : UP | 1 : DOWN | 2 : RIGHT | 3 : LEFT | 4 : UP_LEFT | 5 : UP_RIGHT |6 : DOWN_RIGHT |7 : DOWN_LEFT |	
	 */
	public static Point getNeighbour(Point point, int i) {
		Point p = new Point(point.x, point.y);				
		switch (i){
			// Up
			case 0:
				p.x -= 1;				
				break;
			// Down
			case 1:
				p.x += 1;
				break;
			// Right
			case 2:
				p.y += 1;
				break;
			// Left
			case 3:
				p.y -= 1;
				break;
			// UpLeft
			case 4:
				p.x -= 1; 
				p.y -= 1;
				break;
			//UpRight
			case 5:
				p.x -= 1; 
				p.y += 1;
				break;
			//DownRight
			case 6:
				p.x += 1; 
				p.y += 1;
				break;
			//DownLeft
			case 7:
				p.x += 1; 
				p.y -= 1;
				break;
		}		
		// Out-of-Bounds check
		if (p.x < 0 || p.y < 0 || p.x > 5 || p.y > 6)
			return new Point(-1, -1);
		else
			return p;
	} // getPoint
	
	
	/*
	 * Set, get methods
	 */	
	public Point getPoint() {return point;}
	public void setPoint(Point point) {this.point = point;}
	public int getValue() {return value;}
	public void setValue(int value) {this.value = value;}
	
} // Point
