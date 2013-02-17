package gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import ai.State;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/*
 * Window that holds the entire Human vs Human game.
 */
public class HvsCGameWindow extends JFrame {	
	private ImageIcon window_font = new ImageIcon(getClass().getResource("img/window_font.jpg"));
	JLabel lblPlayer1Wins, lblPlayer2Wins, lblTies;
	// Declaration of the Game's State.
	static State state;
	// Instantiation of the Game's Board JPanel.
	private HvsCBoard hvsCBoard = new HvsCBoard();
	// Shows which player's turn is. Default is 1.
	public static int currentPlayer = 1;	
	// Variables for the 2 players colors.
	private String Player1Color, Player2Color;	
	// Statistic Variables
	public static int Player1WinsCounter, Player2WinsCounter, TiesCounter;
	// Helps for the disposal of the frame. Used at EndOfGameWindow.
	static HvsCGameWindow gw;
	// Info message for score4
	private String message = "Score 4 is a two-player game in which the players first choose a color and then take turns dropping \n" +
							"colored discs from the top into a seven-column, six-row vertically-suspended grid. The pieces fall \n" +
							"straight down, occupying the next available space within the column. The object of the game is to \n" +
							"connect four of one's own discs of the same color next to each other vertically, horizontally, or \n" +
							"diagonally before your opponent.";
	private String contactInfo = "For any bugs, questions etc please contact me at nickolaszoulis@gmail.com !";
	// MiniMax selected search depth
	public static int maxDepth;		
		
	
	public HvsCGameWindow(int firstPlayer, String Player1Color, String Player2Color,int depth, int Player1WinsCounter, int Player2WinsCounter, int TiesCounter) {
		super("Score 4");		
		if (firstPlayer == 1)
			currentPlayer = -1;
		else
			currentPlayer = 1;
		
		this.Player1Color = Player1Color;
		this.Player2Color = Player2Color;
		HvsCGameWindow.Player1WinsCounter += Player1WinsCounter;
		HvsCGameWindow.Player2WinsCounter += Player2WinsCounter;
		HvsCGameWindow.TiesCounter += TiesCounter;
		maxDepth = depth;
		// Instantiation of the Game's State.
		state = new State();
		guiStuff();
		updateLabels();			
		hvsCBoard.setPlayersColors(Player1Color, Player2Color);			
		gw = this;
		if (firstPlayer == -1)
			hvsCBoard.computerMove();
	}
	
	
	/*
	 * Updates the 3 Game Status labels.
	 */
	private void updateLabels() {		
		lblPlayer1Wins.setText(Player1Color+": "+Player1WinsCounter+"  ");
		lblPlayer2Wins.setText(Player2Color+": "+Player2WinsCounter+"  ");
		lblTies.setText("Ties: "+TiesCounter);		
	}
	
	
	/*
	 * Sets the main window and adds Board JPanel.
	 */
	private void guiStuff() {		
		getContentPane().setLayout(new FlowLayout(FlowLayout.LEFT));
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(476,477);	
		setResizable(false);
		setIconImage(window_font.getImage());
		getContentPane().add(hvsCBoard);
		
		// Centering the frame on screen!
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dimension.getWidth() - getWidth()) / 2);
		int y = (int) ((dimension.getHeight() - getHeight()) / 2);
		setLocation(x, y);
		
		lblPlayer1Wins = new JLabel("Red Wins:    ");
		getContentPane().add(lblPlayer1Wins);		
		
		lblPlayer2Wins = new JLabel("Yellow Wins:    ");
		getContentPane().add(lblPlayer2Wins);
		
		lblTies = new JLabel("Ties:      ");
		getContentPane().add(lblTies);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnGame = new JMenu("Game");
		menuBar.add(mnGame);
		
		JMenuItem mntmNewGame = new JMenuItem("New Game");
		mntmNewGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new StartWindow();
				setVisible(false); // Hides the JFrame.
				dispose(); // Destroys the JFrame object.
			}
		});
		mnGame.add(mntmNewGame);		
		mnGame.addSeparator();
		
		JMenuItem mntmExitGame = new JMenuItem("Exit Game");
		mntmExitGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);	// Terminating the program.
			}
		});
		mnGame.add(mntmExitGame);
		
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		JMenuItem mntmAboutScore = new JMenuItem("About Score 4");
		mntmAboutScore.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(null, message);
			}
		});
		mnHelp.add(mntmAboutScore);
		
		JMenuItem mntmContactInfo = new JMenuItem("Contact Info");
		mntmContactInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, contactInfo);
			}
		});
		mnHelp.add(mntmContactInfo);		
	} // guiStuff	
	
} // Game Window
