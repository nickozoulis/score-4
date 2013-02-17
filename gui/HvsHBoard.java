package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import ai.State;

public class HvsHBoard extends JPanel implements ActionListener {
	// Proper class so as to handle mouse events.
	private MouseHandler mouse;	
	private JLabel board[][], clicked;		
	// Image instatiation
	private ImageIcon empty = new ImageIcon(getClass().getResource("img/empty.jpg"));	
	private ImageIcon red = new ImageIcon(getClass().getResource("img/red.jpg"));
	private ImageIcon yellow = new ImageIcon(getClass().getResource("img/yellow.jpg"));
	// Animation Variables
	private Timer animator;
	private int delay = 10, animCounter = -1, x, y;	
	// Variables for the 2 players colors.
	private String Player1Color, Player2Color;
	
	
	public HvsHBoard() {		
		setLayout(new GridLayout(6, 7));
		setBorder(BorderFactory.createLineBorder(Color.BLACK,5));		
		board = new JLabel[6][7];
		mouse = new MouseHandler();		
		animator = new Timer(delay, this);
		
		for(int i=0; i<6; i++){
			for(int j=0; j<7; j++){			
				board[i][j] = new JLabel(empty);
				board[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
				board[i][j].addMouseListener(mouse);				
				add(board[i][j]);
			}
		}				
	} // Board
	
	
	/*
	 * Updates the board according to the given State.
	 */
	private void update(State state) {
		for(int j=0; j<7; j++){
			for(int i=0; i<6; i++){
				if (state.array[i][j].getValue() == 0)
					board[i][j].setIcon(empty);
				else if (state.array[i][j].getValue() == 1)
					board[i][j].setIcon(red);
				else 
					board[i][j].setIcon(yellow);
			}
		}		
	}
	

	/*
	 * Handles the mouse events.
	 */
	private class MouseHandler implements MouseListener {
		/*
		 * Makes the human's move.
		 */
		public void mouseClicked(MouseEvent e) {
			clicked = ((JLabel)e.getSource());	
					
			for(int i=0; i<6; i++){
				for(int j=0; j<7; j++){				
					if (clicked.equals(board[i][j]))						
						y = j;						
				}
			}
			
			// Obtains the valid row of the clicked column.
			x = HvsHGameWindow.state.moveIndex[y];	
			
			if (x >= 0){
				// Starts the animation.
				animator.start();		
				// Changes the State according to the human's move.
				HvsHGameWindow.state.move(y, HvsHGameWindow.currentPlayer);
				// Checks if the winning conditions are satisfied.
				if (HvsHGameWindow.state.isTerminal())
					endOfGame();						
			}				
		} // mouseClicked	
		
	
		/*		 
		 * Makes the entered cell's border white according to State's moveIndex.
		 */
		public void mouseEntered(MouseEvent e) {
			int y = 0;
			JLabel entered = ((JLabel)e.getSource());				
			for(int i=0; i<6; i++){
				for(int j=0; j<7; j++){				
					if (entered.equals(board[i][j]))						
						y = j;						
				}
			}	
			int x = HvsHGameWindow.state.moveIndex[y];
			if (x >= 0)
				board[x][y].setBorder(BorderFactory.createLineBorder(Color.WHITE));			
		}			
		
		
		/*		 
		 * Makes the exited cell's border black according to State's moveIndex.
		 */
		public void mouseExited(MouseEvent e) {
			int y = 0;
			JLabel exited = ((JLabel)e.getSource());
			exited.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			for(int i=0; i<6; i++){
				for(int j=0; j<7; j++){				
					if (exited.equals(board[i][j]))						
						y = j;						
				}
			}	
			for(int i=0; i<6; i++){
				board[i][y].setBorder(BorderFactory.createLineBorder(Color.BLACK));
			}
		}			
		
		public void mouseReleased(MouseEvent e) {}
		public void mousePressed(MouseEvent e) {}			
	} // MouseHandler
	
		
	/*
	 * Method that handles the animation action after mouse click.	  
	 */
	public void actionPerformed(ActionEvent e) {						
		if (animCounter != x)
			++animCounter;
		
		if (HvsHGameWindow.currentPlayer == 1){
			if (Player1Color.equals("Red"))
				board[animCounter][y].setIcon(red);	
			else if (Player1Color.equals("Yellow"))
				board[animCounter][y].setIcon(yellow);	
		}
		else {
			if (Player2Color.equals("Red"))
				board[animCounter][y].setIcon(red);	
			else if (Player2Color.equals("Yellow"))
				board[animCounter][y].setIcon(yellow);
		}
		
		if (animCounter == x){
			animator.stop();
			animCounter = -1;			
			for(int i=0; i<x; i++){
				board[i][y].setIcon(empty);;
			}
			if (HvsHGameWindow.currentPlayer == 1){
				if (Player1Color.equals("Red"))
					board[x][y].setIcon(red);	
				else if (Player1Color.equals("Yellow"))
					board[x][y].setIcon(yellow);	
			}
			else {
				if (Player2Color.equals("Red"))
					board[x][y].setIcon(red);	
				else if (Player2Color.equals("Yellow"))
					board[x][y].setIcon(yellow);
			}
			
			// Changing the playing player.
			if (HvsHGameWindow.currentPlayer == 1)
				HvsHGameWindow.currentPlayer = -1; 
			else if (HvsHGameWindow.currentPlayer == -1)
				HvsHGameWindow.currentPlayer = 1;
		} // if	
	} // actionPerformed
	
	
	/*
	 * Used in GameWindows so as to set the selected colors.
	 */
	public void setPlayersColors(String p1, String p2){
		Player1Color = p1;
		Player2Color = p2;
	}
	
	
	/*
	 * After end-of-game method. Announces the results through a new window.
	 */
	private void endOfGame() {		
		new EndOfGameWindow(State.TIE, HvsHGameWindow.currentPlayer, Player1Color, Player2Color);
	}
	
} //HvsHBoard
