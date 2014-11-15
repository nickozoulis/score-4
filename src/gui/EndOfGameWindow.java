package gui;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class EndOfGameWindow extends JFrame {
	private ImageIcon window_font = new ImageIcon(getClass().getResource("img/window_font.jpg"));	
	private String winner, player1Color, player2Color;	
	private int p1wins = 0, p2wins = 0, ties = 0;
	private int nextFirstPlayer; 
	
	public EndOfGameWindow(boolean tie, int winner, String player1Color, String player2Color) {
		super("End of Game!");		
		this.player1Color = player1Color;
		this.player2Color = player2Color;
		if (tie) {
			this.winner = "Tie!";
			ties = 1;
		}
		else {
			if (winner == 1) {
				this.winner = player1Color;
				p1wins = 1;
				nextFirstPlayer = -1;
			}
			else {
				this.winner = player2Color;
				p2wins = 1;
				nextFirstPlayer = 1;
			}
		}		
		guiStuff();
	}
	

	private void guiStuff() {
		setIconImage(window_font.getImage());
		getContentPane().setLayout(null);
		setSize(317,159);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBackground(new Color(70, 130, 180));		
		setResizable(false);
		
		// Centering the frame on screen!
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dimension.getWidth() - getWidth()) / 2);
		int y = (int) ((dimension.getHeight() - getHeight()) / 2);
		setLocation(x, y);
		
		JLabel lblTheWinnerIs = new JLabel("Winner of the game is : "+this.winner);
		lblTheWinnerIs.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblTheWinnerIs.setBounds(62, 11, 239, 28);
		getContentPane().add(lblTheWinnerIs);
		
		JLabel lblPlayAgain = new JLabel("Play Again ?");
		lblPlayAgain.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblPlayAgain.setBounds(122, 50, 81, 21);
		getContentPane().add(lblPlayAgain);
		
		JButton btnYes = new JButton("YES");
		btnYes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {	
				if (HvsHGameWindow.gw != null){
					HvsHGameWindow.gw.setVisible(false); // Hides the previous frame.
					// New Game
					new HvsHGameWindow(nextFirstPlayer, player1Color, player2Color, p1wins, p2wins, ties);	
				}
				else if (HvsCGameWindow.gw != null){
					HvsCGameWindow.gw.setVisible(false); // Hides the previous frame.
					// New Game
					new HvsCGameWindow(nextFirstPlayer, player1Color, player2Color,HvsCGameWindow.maxDepth, p1wins, p2wins, ties);
				}
				setVisible(false); // Hides the JFrame.
				dispose(); // Destroys the JFrame object.
			}
		});
		btnYes.setBounds(48, 82, 89, 23);
		getContentPane().add(btnYes);
		
		JButton btnNo = new JButton("NO");
		btnNo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnNo.setBounds(177, 82, 89, 23);
		getContentPane().add(btnNo);		
	}
	
} // EndOfGameWindow 
