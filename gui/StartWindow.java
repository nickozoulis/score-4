package gui;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JSpinner;
import javax.swing.SpinnerListModel;
import javax.swing.JButton;
import java.awt.Color;

/*
 * Window that lets the player to choose the search depth of the Minimax, colors of the Nodes, mode of the game.
 */
public class StartWindow extends JFrame {
	private HvsHGameWindow gw;
	private HvsCGameWindow gw2;
	private ImageIcon window_font= new ImageIcon(getClass().getResource("img/window_font.jpg"));	
	JRadioButton rdbtnEasy, rdbtnMedium, rdbtnHard, rdbtnHumanVsHuman, rdbtnHumanVsComputer;
	ButtonGroup groupDif, groupGameMode, groupPlayingFirst;
	JSpinner spinner, spinner_1;
	JOptionPane errorWindow;
	JLabel lblDifficulty;
	private JLabel lblPlayingFirst;
	private JRadioButton rdbtnYou;
	private JRadioButton rdbtnOpponent;
	private int firstPlayer, depth;
	private String Player1, Player2;
	
	public StartWindow() {
		super("Score 4 - Settings");
		
		// Centering the frame on screen!
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dimension.getWidth() - getWidth()) / 2);
	    int y = (int) ((dimension.getHeight() - getHeight()) / 2);
	    setLocation(x - 200, y - 200);
		
		getContentPane().setBackground(new Color(70, 130, 180));
		getContentPane().setForeground(Color.WHITE);
		setIconImage(window_font.getImage());
		getContentPane().setLayout(null);
		
		lblDifficulty = new JLabel("Difficulty: ");
		lblDifficulty.setBounds(75, 91, 63, 15);
		lblDifficulty.setFont(new Font("Tahoma", Font.BOLD, 12));
		getContentPane().add(lblDifficulty);
		lblDifficulty.setVisible(false);
		
		rdbtnEasy = new JRadioButton("Easy");
		rdbtnEasy.setBackground(new Color(70, 130, 180));
		rdbtnEasy.setBounds(144, 88, 55, 23);
		getContentPane().add(rdbtnEasy);
		rdbtnEasy.setVisible(false);
		
		rdbtnMedium = new JRadioButton("Medium");
		rdbtnMedium.setBackground(new Color(70, 130, 180));
		rdbtnMedium.setBounds(201, 88, 79, 23);
		getContentPane().add(rdbtnMedium);
		rdbtnMedium.setVisible(false);
		
		rdbtnHard = new JRadioButton("Hard");
		rdbtnHard.setBackground(new Color(70, 130, 180));
		rdbtnHard.setBounds(281, 88, 63, 23);
		getContentPane().add(rdbtnHard);	
		rdbtnHard.setVisible(false);

	    // Group the radio buttons.
	    groupDif = new ButtonGroup();
	    groupDif.add(rdbtnEasy);
	    groupDif.add(rdbtnMedium);
	    groupDif.add(rdbtnHard);
		
	    JLabel lblGameMode = new JLabel("Game Mode: ");
		lblGameMode.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblGameMode.setBounds(59, 27, 79, 14);
		lblGameMode.setVerticalAlignment(SwingConstants.BOTTOM);
		getContentPane().add(lblGameMode);
		
		rdbtnHumanVsHuman = new JRadioButton("Human vs Human");
		rdbtnHumanVsHuman.setBackground(new Color(70, 130, 180));
		rdbtnHumanVsHuman.setBounds(144, 22, 128, 23);
		getContentPane().add(rdbtnHumanVsHuman);
		rdbtnHumanVsHuman.addActionListener(new ActionListener() {
			// Button's action code.
			public void actionPerformed(ActionEvent e) {					
				lblDifficulty.setVisible(false);
				rdbtnEasy.setVisible(false);
				rdbtnMedium.setVisible(false);
				rdbtnHard.setVisible(false);
			}		
		});
		
		rdbtnHumanVsComputer = new JRadioButton("Human vs Computer");
		rdbtnHumanVsComputer.setBackground(new Color(70, 130, 180));
		rdbtnHumanVsComputer.setBounds(144, 48, 152, 23);
		getContentPane().add(rdbtnHumanVsComputer);
		rdbtnHumanVsComputer.addActionListener(new ActionListener() {
			// Button's action code.
			public void actionPerformed(ActionEvent e) {					
				lblDifficulty.setVisible(true);
				rdbtnEasy.setVisible(true);
				rdbtnMedium.setVisible(true);
				rdbtnHard.setVisible(true);
			}		
		});	
		
		// Group the radio buttons.
	    groupGameMode = new ButtonGroup();
	    groupGameMode.add(rdbtnHumanVsHuman);
	    groupGameMode.add(rdbtnHumanVsComputer);	    
		
	    JLabel lblNodeColor = new JLabel("Node Color:");
		lblNodeColor.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNodeColor.setBounds(62, 137, 79, 14);
		getContentPane().add(lblNodeColor);
		
		JLabel lblYou = new JLabel("You");
		lblYou.setFont(new Font("Tahoma", Font.ITALIC, 12));
		lblYou.setBounds(144, 137, 46, 14);
		getContentPane().add(lblYou);
		
		JLabel lblOpponent = new JLabel("Opponent");
		lblOpponent.setFont(new Font("Tahoma", Font.ITALIC, 12));
		lblOpponent.setBounds(144, 164, 63, 14);
		getContentPane().add(lblOpponent);
		
		spinner = new JSpinner();
		spinner.setModel(new SpinnerListModel(new String[] {"Red", "Yellow"}));
		spinner.setBounds(229, 134, 67, 20);
		getContentPane().add(spinner);
		
		spinner_1 = new JSpinner();
		spinner_1.setModel(new SpinnerListModel(new String[] {"Yellow", "Red"}));
		spinner_1.setBounds(229, 162, 67, 20);
		getContentPane().add(spinner_1);
		
		JButton btnPlay = new JButton("Play!");
		btnPlay.setBounds(154, 249, 95, 36);
		getContentPane().add(btnPlay);
		
		lblPlayingFirst = new JLabel("Playing First: ");
		lblPlayingFirst.setFont(new Font("Segoe UI", Font.BOLD, 12));
		lblPlayingFirst.setBounds(59, 202, 79, 23);
		getContentPane().add(lblPlayingFirst);
		
		rdbtnYou = new JRadioButton("You");
		rdbtnYou.setBackground(new Color(70, 130, 180));
		rdbtnYou.setBounds(144, 202, 55, 23);
		getContentPane().add(rdbtnYou);
		
		rdbtnOpponent = new JRadioButton("Opponent");
		rdbtnOpponent.setBackground(new Color(70, 130, 180));
		rdbtnOpponent.setBounds(212, 202, 84, 23);
		getContentPane().add(rdbtnOpponent);
		
		// Group the radio buttons.
	    groupPlayingFirst = new ButtonGroup();
	    groupPlayingFirst.add(rdbtnYou);
	    groupPlayingFirst.add(rdbtnOpponent);
		
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(424,326);	
		setResizable(false);
		btnPlay.addActionListener(new ActionListener() {
			// Button's action code.
			public void actionPerformed(ActionEvent e) {	
				// Checks if the valid conditions are selected.
				if (checkValidity() == 1){
					gatherInfo();
					// Initializing the statistics.
					HvsHGameWindow.Player1WinsCounter = 0;
					HvsHGameWindow.Player2WinsCounter = 0;
					HvsHGameWindow.TiesCounter = 0;
					gw = new HvsHGameWindow(firstPlayer, Player1, Player2, 0, 0, 0);
					setVisible(false); // Hides the JFrame.
					dispose(); // Destroys the JFrame object.
				}
				else if (checkValidity() == 2){
					gatherInfo();
					// Initializing the statistics.
					HvsCGameWindow.Player1WinsCounter = 0;
					HvsCGameWindow.Player2WinsCounter = 0;
					HvsCGameWindow.TiesCounter = 0;
					gw2 = new HvsCGameWindow(firstPlayer, Player1, Player2, depth, 0, 0, 0);
					setVisible(false); // Hides the JFrame.
					dispose(); // Destroys the JFrame object.
				}					
				else 
					JOptionPane.showMessageDialog(null, "Check your selections again!", "Invalid selections", JOptionPane.WARNING_MESSAGE);					
			}				
		});				
	} // Constructor
	
	
	/*
	 * Gathers the info needed for the game from the selected components.
	 */
	private void gatherInfo() {		
		Player1 = (String)spinner.getValue();		
		Player2 = (String)spinner_1.getValue();				
		if (rdbtnYou.isSelected())
			firstPlayer = 1;
		else if (rdbtnOpponent.isSelected())
			firstPlayer = -1;	
		// If Human vs Computer mode is selected
		if (rdbtnHumanVsComputer.isSelected()){
			if (rdbtnEasy.isSelected())
				depth = 2;
			else if (rdbtnMedium.isSelected())
				depth = 4;
			else if (rdbtnHard.isSelected())
				depth = 6;
		}		
	}
	
	
	/*
	 * Checks if the valid conditions are selected.
	 */
	private int checkValidity() {
		if (rdbtnHumanVsHuman.isSelected()){
			if (!spinner.getValue().equals(spinner_1.getValue()))
				if (rdbtnYou.isSelected() || rdbtnOpponent.isSelected())
					return 1;
		}
		else if (rdbtnHumanVsComputer.isSelected()){			
			if (rdbtnEasy.isSelected() || rdbtnMedium.isSelected() || rdbtnHard.isSelected())
				if (!spinner.getValue().equals(spinner_1.getValue()))
					if (rdbtnYou.isSelected() || rdbtnOpponent.isSelected())
						return 2;
		}
		return -1;		
	}
	
} // StartWindow
