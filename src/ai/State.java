package ai;

import java.awt.Point;
import java.util.ArrayList;

public class State {
	// Internal structure to hold the 6x7 array.
	public Node array[][];
	// An assistant array so as to know in which row a move should be done. Initial state : [5,5,5,5,5,5,5]
	public int[] moveIndex;
	private int score;	
	public static boolean TIE = false;
	// Weights for the State's evaluation.
	private int w1 = 1, w2 = 10, w3 = 100;
	
	
	
	/*
	 * Default Constructor
	 */
	public State() {
		// Instantiation of the move Index
		moveIndex = new int[7];
		// Instantiation of the array
		array = new Node[6][7];			
		for (int i=0; i<6; i++){
			for (int j=0; j<7; j++){
				array[i][j] = new Node(i,j);				
			}
		}		
		for (int j=0; j<7; j++){
			moveIndex[j] = 5;
		}
	}
	
	
	/*
	 *  Copy Constructor
	 */
	public State(State original) {
		this.score = original.score;
		this.moveIndex = new int[7];
		this.array = new Node[6][7];
		for (int i=0; i<6; i++){
			for (int j=0; j<7; j++){
				this.array[i][j] = new Node(original.array[i][j]);				
			}
		}
		for (int j=0; j<7; j++){
			this.moveIndex[j] = original.moveIndex[j];
		}
	}
	
	
	/*
	 * Makes valid moves.
	 * -Args-
	 * col : The column of the player's move.
	 * player: Which player's move is being made[-1 or 1].
	 */
	public boolean move(int col, int player) {		
		if (moveIndex[col] < 0)
			return false;		
		
		// Changes the Node's value at the correct array's cell, in order to conduct a move.
		array[moveIndex[col]][col].setValue(player);
		// Decrements the proper counter, so as it shows the next possible move's row.
		moveIndex[col]--;
		return true;
	}
	
	
	/*
	 * Generates all the next valid moves.
	 * -Args-	 
	 * player: Which player's move is being made[-1 or 1].
	 */
	public ArrayList<State> getChildren(int player) {
		ArrayList<State> children = new ArrayList<State>();		
		State temp = null;
		
		for (int i=0; i<7; i++){
			temp = new State(this);
			if (temp.move(i, player))
				children.add(temp);					
		}		
		return children;
	}
	
	
	/*
	 * Checks if the winning conditions are satisfied or there is no other move to be made.
	 */
	public boolean isTerminal() {
		if (isArrayFull()){
			TIE = true;
			return true;	
		}
		else if (horizontalWin()) return true;		
		else if (verticalWin()) return true;
		else if (diagonalWin()) return true;
		else return false;		
	} 

	
	/*
	 * Checks if the array is full.
	 */
	private boolean isArrayFull() {
		int k = 0;		 
		for (int i=0; i<7; i++){			
			if (moveIndex[i] == -1) 
				k++;
		}			
		if (k == 7) return true;
		else return false;
	}
	
	
	/*
	 * Checks if there is any horizontal winning condition.
	 */
	private boolean horizontalWin() {		
		int counter = 1;	
		int value = 0;
		for (int i=5; i>=0; i--){
			value = array[i][0].getValue();
			for (int j=1; j<7; j++){
				if (value == array[i][j].getValue() && value != 0)
					counter++;
				else {
					value = array[i][j].getValue();					
					counter = 1;					
				}				
				if (counter == 4) return true;				
			}
			counter = 1;
		}	
		return false;
	}
	
	
	/*
	 * Checks if there is any vertical winning condition.
	 */
	private boolean verticalWin() {
		int counter = 1;	
		int value = 0;
		for (int j=6; j>=0; j--){
			value = array[5][j].getValue();
			for (int i=4; i>=0; i--){
				if (value == array[i][j].getValue() && value != 0)
					counter++;
				else {
					value = array[i][j].getValue();					
					counter = 1;					
				}				
				if (counter == 4) return true;				
			}
			counter = 1;
		}	
		return false;		
	}


	/*
	 * Checks if there is any diagonal winning condition. Uses checkNeighbours().
	 */
	private boolean diagonalWin() {		
		for (int i=5; i>=0 ; i--){
			for (int j=0; j<=6; j++){
				if (check(array[i][j]))
					return true;							
			}
		}		
		return false;
	}	


	/*
	 * Checks for the up_left(4) and up_right(5) neighbours of the Node n.
	 */
	private boolean check(Node n) {		
		if (checkNeighbours(n, 4)) return true;		
		if (checkNeighbours(n, 5)) return true;	
		return false;
	}
	
	
	/*
	 * Method that finds if there is any diagonal winning condition.
	 * -Args-
	 * n: The Node whose neigbours are examined.	
	 * i: 4 -> UP_LEFT, 5 -> UP_RIGHT
	 */
	private boolean checkNeighbours(Node n, int i) {	
		int counter = 1;
		Point p = Node.getNeighbour(new Point(n.getPoint().x, n.getPoint().y), i);	
		int value = array[n.getPoint().x][n.getPoint().y].getValue();
		while (p.x != -1){
			if (value == array[p.x][p.y].getValue() && value != 0)
				counter++;
			else {
				value = array[p.x][p.y].getValue();
				counter = 1;
			}
			if (counter == 4)
				return true;
			p = Node.getNeighbour(new Point(p.x, p.y), i);			
		}		
		return false;	
	}


	/*
	 * Evaluates the current State according to the heuristics and the weights.
	 */
	public void evaluate() {	
		setScore((w1 * Heuristics.heuristic1(this)) + 
				 (w2 * Heuristics.heuristic2(this)) + 
				 (w3 * Heuristics.heuristic3(this)) -
				 // Calculates the score of the opponent.
				 evaluateOpponentScore(this));
	}
	
	
	private int evaluateOpponentScore(State state) {
		State temp = new State(this);
		for (int i=0; i<=5; i++){
			for (int j=0; j<=6; j++){
				// Inverts the array so as to use the patterns
				temp.array[i][j].setValue(temp.array[i][j].getValue() * -1);
			}
		}
		return ((w1 * Heuristics.heuristic1(temp)) + (w2 * Heuristics.heuristic2(temp)) +  (w3 * Heuristics.heuristic3(temp)));		
	}


	/*
	 * Set, get methods
	 */
	public int getScore() {return score;}
	public void setScore(int score) {this.score = score;}	
	
} // State
