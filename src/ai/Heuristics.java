package ai;

import java.awt.Point;
import java.util.ArrayList;

public class Heuristics {
	
	/*
	 * Finds how many 1-out-of-4 winning lines there are.
	 */
	public static int heuristic1(State state) {		
		int sum = 0;			
		sum += horizontalCheck(state, 1);
		sum += verticalCheck(state, 1);	
		sum += diagonalCheck(state, 1);
		return sum;
	} 
		
	
	/*
	 * Finds how many 2-out-of-4 winning lines there are.
	 */
	public static int heuristic2(State state) {
		int sum = 0;			
		sum += horizontalCheck(state, 2);
		sum += verticalCheck(state, 2);	
		sum += diagonalCheck(state, 2);		
		return sum;
	} 

	
	/*
	 * Finds how many 3-out-of-4 winning lines there are.
	 */
	public static int heuristic3(State state) {
		int sum = 0;			
		sum += horizontalCheck(state, 3);
		sum += verticalCheck(state, 3);	
		sum += diagonalCheck(state, 3);		
		return sum;
	} 	
	

	/*
	 * Crosses (horizontally) the State's array and passes to checkH() a 4-element-line, that is going to be compared with the Pattern's tables, in order to
	 * figure out whether this is a wanted condition!
	 */
	private static int horizontalCheck(State state, int num) {
		int counter = 0;
		for (int i=5; i>=0; i--){
			int a = 0, b = 1, c = 2, d = 3;
			// Four loops needed
			for (int j=0; j<=3; j++){
				if (checkH(state.array[i][a].getValue(), state.array[i][b].getValue(), state.array[i][c].getValue(), state.array[i][d].getValue(), num))
					counter++;
				a++;b++;c++;d++;
			}				
		}		
		return counter;
	}
	
	
	/*
	 * Crosses (vertically) the State's array and passes to checkH() a 4-element-line, that is going to be compared with the Pattern's tables, in order to
	 * figure out whether this is a wanted condition!
	 */
	private static int verticalCheck(State state, int num) {
		int counter = 0;
		for (int j=0; j<=6; j++){
			int a = 0, b = 1, c = 2, d = 3;
			// Three loops needed
			for (int i=0; i<=2; i++){
				if (checkH(state.array[a][j].getValue(), state.array[b][j].getValue(), state.array[c][j].getValue(), state.array[d][j].getValue(), num))
					counter++;
				a++;b++;c++;d++;
			}			
		}		
		return counter;
	}
	
	
	/*
	 * Crosses (diagonally) the State's array and passes to checkNeighbours an array's Node, so as to find its 4 diagonal neighbours(if it has).
	 * 4 -> Up_Left Neighbour
	 * 5-> Up_Right Neighbour
	 */
	private static int diagonalCheck(State state, int num) {
		int counter = 0;
		for (int i=5; i>=0 ; i--){
			for (int j=0; j<=6; j++){
				if (checkNeighbours(state, state.array[i][j], num, 4))
					counter++;			
				if (checkNeighbours(state, state.array[i][j], num, 5))
					counter++;
			}
		}		
		return counter;
	}


	/*
	 * Finds the 4 diagonal Neighbours and passes them to checkH, in order to figure out whether this is a wanted condition!
	 */
	private static boolean checkNeighbours(State state, Node n, int num, int i) {
		ArrayList<Integer> t = new ArrayList<Integer>();
		int counter = 1;
		Point p = Node.getNeighbour(new Point(n.getPoint().x, n.getPoint().y), i);
		t.add(state.array[n.getPoint().x][n.getPoint().y].getValue());
		while (p.x != -1 && counter != 4){
			t.add(state.array[p.x][p.y].getValue());
			counter++;
			p = Node.getNeighbour(new Point(p.x, p.y), i);		
		}
		if (t.size() == 4) 
			if (checkH(t.get(0), t.get(1), t.get(2), t.get(3), num))
				return true;		
		return false;
	}


	/*
	 * Checks if the inserted 4-element line (a-b-c-d) is contained in any row of the Patterns.
	 */
	private static boolean checkH(int a, int b, int c, int d, int num) {	
		switch (num){
			case 1:
				for (int i=0; i<=3; i++){			
					if (a == MoveGenerator.pat.h1[i][0] && b == MoveGenerator.pat.h1[i][1] && c == MoveGenerator.pat.h1[i][2] && d == MoveGenerator.pat.h1[i][3]) 
						return true;
				}
				break;
			case 2:
				for (int i=0; i<=5; i++){			
					if (a == MoveGenerator.pat.h2[i][0] && b == MoveGenerator.pat.h2[i][1] && c == MoveGenerator.pat.h2[i][2] && d == MoveGenerator.pat.h2[i][3]) 
						return true;
				}
				break;
			case 3:
				for (int i=0; i<=3; i++){			
					if (a == MoveGenerator.pat.h3[i][0] && b == MoveGenerator.pat.h3[i][1] && c == MoveGenerator.pat.h3[i][2] && d == MoveGenerator.pat.h3[i][3]) 
						return true;
				}
				break;
		} // switch
		return false;
	} // checkH
	
} // Heuristics 
