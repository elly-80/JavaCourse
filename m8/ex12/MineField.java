/**
 * Programmer:			Sean Thames
 * Date:				2013-07-11
 * Filename:			MineField.java
 * Assignment:			Ch 15 Ex 12
 *
 * Description:         A) Create a Mine Field game in which the user attempts
 * to click [2]0 panels of a grid before hitting the "bomb." Set up a JFrame 
 * using BorderLayout, use the NORTH region for a congratulatory message, and
 * use the CENTER region for the game. In the CENTER region, create a 
 * four-by-five grid using GridLayout and populate the grid with JPanels. Set 
 * the background color for all the JPanels to Color.BLUE. Randomly choose one 
 * of the panels to be the bomb; the other 19 panels are "safe." Allow the 
 * player to click on grids. If the player chooses a safe-panel, turn the panel 
 * to Color.WHITE. If the player chooses the bomb panel, turn the panel to 
 * Color.RED and turn all remaining panels white. If the user successfully 
 * chooses 10 safe panels before choosing the bomb, display a congratulatory
 * message in the NORTH JFrame region. Save the game as MineField.java.
 * 
 * B) Improve the Mine Field game by allowing the user to choose a difficulty
 * level before beginning. Place three buttons labeled "Easy", "Intermediate", 
 * and "Difficult" in one region of the JFrame, and place the game grid and 
 * congratulatory message in the other regions. Require the user to select a 
 * difficulty level before starting the game, then disable the buttons. If the 
 * user chooses "Easy", the user must select only five safe panels to win the 
 * game. If the user selects "Intermediate", require 10 safe panels, as in the 
 * original game. If the user selects "Difficult", require 15 safe panels. Save
 * the game as MineField2.java
 * 
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.Color;
import java.util.Random;

public class MineField extends JFrame implements ActionListener
{
	private Random random = new Random();
	private final int NUMBER_OF_TILES = 20;
	private final int NUMBER_OF_TURNS = 10;
	
	private int timesSafe = 0;
	private int bombLocation = 0;
	
	private final int WIDTH = 400;
	private final int HEIGHT = 350;
	
	private BorderLayout mainLayout = new BorderLayout();
	private GridLayout gameLayout = new GridLayout(4, 5, 2, 2);
	
	private Container gameContainer = new Container();
	private CardLayout cards = new CardLayout();
	
	private JLabel title = new JLabel("Please click a square", JLabel.CENTER);
	private JPanel[] bombPanels = new JPanel[NUMBER_OF_TILES];
	private JButton[] bombButtons = new JButton[NUMBER_OF_TILES];
	
	private final String BOMB = "<html><font face=\"monospace\">&nbsp&nbsp;,-*<br>&nbsp;(_) BOOM!</font></html>";
	private JLabel bomb = new JLabel(BOMB);
	
	
	public MineField()
	{
		super("MineField");
		setSize(WIDTH, HEIGHT);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		gameContainer.setLayout(gameLayout);
		
		bombLocation = random.nextInt(NUMBER_OF_TILES);
		
		add(title, BorderLayout.NORTH);
		add(gameContainer, BorderLayout.CENTER);
		drawGameGrid();
	}
	
	public void actionPerformed(ActionEvent e)
	{
		timesSafe += 1;
		JButton source = (JButton)e.getSource();
		if(source == bombButtons[bombLocation])
			goBoom(source);
		else
			fizzle(source);
		
		if(timesSafe >= NUMBER_OF_TURNS)
			victory();
	}
	
	private void drawGameGrid()
	{
		for(int i = 0; i < NUMBER_OF_TILES; i++)
		{
			bombPanels[i] = new JPanel(cards);
			bombButtons[i] = new JButton("Click me!");
			bombButtons[i].setBackground(null);
			bombPanels[i].add("card" + i, bombButtons[i]);
			bombPanels[i].setBackground(Color.BLUE);
			gameContainer.add(bombPanels[i]);
			bombButtons[i].addActionListener(this);
		}
	}

	private void goBoom(JButton b)
	{
		Container con = b.getParent();
		for(JButton a : bombButtons)
			fizzle(a);
		
		title.setText("Uh oh!");
		con.removeAll();
		con.add(bomb);
		con.setBackground(Color.RED);
		revalidate();
	}
	private void fizzle(JButton b)
	{
		Container con = b.getParent();
		con.setBackground(Color.WHITE);
		b.removeActionListener(this);
		b.setText("");
		b.setEnabled(false);
		
		revalidate();
	}
	private void victory()
	{
		for(JButton a : bombButtons)
			fizzle(a);
			
		title.setText("Congratulations!");
		
		revalidate();
	}
	public static void main(String[] args)
	{
		MineField game = new MineField();
		game.setVisible(true);
	}
}
