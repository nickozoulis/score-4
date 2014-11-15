package ai;

import java.util.ArrayList;
import java.util.Random;
import ai.State;

public class MoveGenerator {
	// Holds the max depth of the searching tree.
	private int maxDepth;
	// Holds the player so as to know whose turn is.
	public static int currentPl;	
	Random r;
	// Branch counter (of the generated children).
	public int counterCh = 0;
	public static Patterns pat;
	
	
	public MoveGenerator(int maxDepth, int currentPlayer, Patterns p) {
		this.maxDepth = maxDepth;
		MoveGenerator.currentPl = currentPlayer;
		pat = p;
		r = new Random();		
	}
	
	
	/*
	 * Initiates the MiniMax algorithm. Creates the 0-lvl children of the initial State, and foreach calls min.
	 */
	public State MiniMax(State initialState) {
		// Pruning variables
		int A = Integer.MIN_VALUE, B = Integer.MAX_VALUE;
		// Initializing local variables.
		int temp = 0, score = Integer.MIN_VALUE;
		// Holds the computer's State(move) that is going to be returned.
		State maxState = null;		
		// Generating children states.
		ArrayList <State> children = new ArrayList<State>();
		ArrayList <State> t = initialState.getChildren(currentPl);
		if (t != null) children.addAll(t);	
		else return maxState;
		
		// Foreach child calls min at a lower depth.
		for (State child : children){	
			System.out.println("======="+ ++counterCh);
			temp = min(new State(child), 1, A, B);
			
			if (temp >= score){				
				if (temp == score){
					if (r.nextInt(2) == 0){
						maxState = child;	
						score = temp;					
					}						
				}
				else {
					maxState = child;				
					score = temp;					
				}				
			} // if
			
			if (score > A) A = score;
			if (A >= B) break;  // beta cut-off	
		} // for
		
		maxState.setScore(score);
		return maxState;			
	} // MiniMax
	
	
	/*
	 * Max node producing. It calls min recursively.
	 */
	private int max(State state, int depth, int A, int B) {			
		// Initializing local variables.
		int temp = 0, score = Integer.MIN_VALUE;
		boolean terminal = false;
			
		if (state.isTerminal()) terminal = true;
		// Recursion's terminating condition.
		if (terminal || depth == maxDepth){
			if (terminal){
				state.setScore(Integer.MIN_VALUE);
				return state.getScore();
			}
			System.out.println("Reached maximum depth, MAX.");
			state.evaluate();
			System.out.println("STATE'S SCORE : " + state.getScore());
			return state.getScore();
		}		
		
		// Changing player's turn.
		currentPl *= -1;
				
		// Generating children states.
		ArrayList <State> children = new ArrayList<State>();
		ArrayList <State> t = state.getChildren(currentPl);
		if (t != null) children.addAll(t);	
		else return Integer.MAX_VALUE;		
		
		// Foreach child calls min at lower depth.
		for (State child : children){
			System.out.println("======="+ ++counterCh);
			temp = min(child, depth + 1, A, B);
			
			// Finds and stores a new max score.
			if (temp >= score){						
				if (temp == score){
					if (r.nextInt(2) == 0)						
						score = temp;													
				}
				else {					
					score = temp;
				}						
			} // if
			
			if (score > A) A = score;
			if (A >= B)	break;  // alpha cut-off
		} // for
		
		return score;		
	} // max
	
	
	/*
	 * Min node producing. It calls max recursively.
	 */
	private int min(State state, int depth, int A, int B) {		
		// Initializing local variables.
		int temp = 0, score = Integer.MAX_VALUE;
		boolean terminal = false;
		
		if (state.isTerminal()) terminal = true;
		// Recursion's terminating condition.
		if (terminal || depth == maxDepth){
			if (terminal){
				state.setScore(Integer.MAX_VALUE);
				return state.getScore();
			}				
			System.out.println("Reached maximum depth, MIN.");
			state.evaluate();
			System.out.println("STATE'S SCORE : " + state.getScore());
			return state.getScore();
		}
		
		// Changing player's turn.
		currentPl *= -1;	
				
		// Generating children states.
		ArrayList <State> children = new ArrayList<State>();
		ArrayList <State> t = state.getChildren(currentPl);
		if (t != null) children.addAll(t);	
		else return Integer.MIN_VALUE;
				
		// Foreach child calls max at lower depth.
		for (State child : children){
			System.out.println("======="+ ++counterCh);
			temp = max(child, depth + 1, A, B);
			
			// Finds and stores a new min score.
			if (temp <= score){						
				if (temp == score){
					if (r.nextInt(2) == 0)		
						score = temp;													
				}
				else 					
					score = temp;										
			} // if	
			
			if (score < B) B = score;
			if (A >= B) break;  // beta cut-off				
		} // for				 		
				
		return score;		
	} // min
	
} // MoveGenerator
