package guitictactoe;
/*
 * This game is a TicTacToe game that you can play in a separate window.
 * Author: Suhas Makineni
 * Date: 10/31/22
 */

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class GUITicTacToe implements ActionListener{
	private static final int ACTION_PERFORMED = 0;
	// creates label, variable, button, text field, and containers for the game
	
	JFrame frame = new JFrame();
	JButton[][] button = new JButton[3][3];
	int[][] board = new int [3][3];
	final int BLANK = 0;
	final int X_MOVE = 1;
	final int O_MOVE = 2;
	final int X_TURN = 0;
	final int O_TURN = 1;
	int turn = X_TURN;
	Container center = new Container();
	JLabel xLabel = new JLabel("X wins: 0");
	JLabel oLabel = new JLabel("O wins: 0");
	JLabel turnlabel = new JLabel("X's turn");
	JButton resetbutton = new JButton("Reset");
	JButton xChangeName = new JButton("Change X's Name");
	//JButton oChangeName = new JButton("Change O's Name");
	JTextField xChangeField = new JTextField();
	JTextField oChangeField = new JTextField();
	Container north = new Container();
	Container south = new Container();
	String xPlayerName = "X";
	String oPlayerName = "CPU";
	int xwins = 0;
	int owins = 0;
	
	public GUITicTacToe() { // layout for TicTacToe Window
		frame.setSize(400, 400);
		// center grid container
		frame.setLayout(new BorderLayout());
		center.setLayout(new GridLayout(3,3));
		for (int i = 0; i < button.length; i++) {
			for (int j = 0; j < button[0].length; j++) {
				button[i][j] = new JButton(i+","+j);
				center.add(button[i][j]);
				button[i][j].addActionListener(this);
			}
		}
		frame.add(center, BorderLayout.CENTER);
		// north container
		north.setLayout(new GridLayout(3,2));
		north.add(xLabel);
		north.add(oLabel);
		north.add(xChangeName);
		xChangeName.addActionListener(this);
		//north.add(oChangeName);
		//oChangeName.addActionListener(this);
		north.add(xChangeField);
		north.add(oChangeField);
		south.setLayout(new FlowLayout());
		resetbutton.addActionListener(this);
		south.add(resetbutton);
		south.add(turnlabel);
		frame.add(north, BorderLayout.NORTH);
		frame.add(south, BorderLayout.SOUTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
	}

	public static void main(String[] args) {
		GUITicTacToe myTTT = new GUITicTacToe();
		
//		while (true) {
//			if (myTTT.turn == myTTT.O_TURN)
//				myTTT.playComp();
//		}
	}

	@Override
	public void actionPerformed(ActionEvent event) { // uses the tie and win condition to check for a win or tie 
		JButton current;
		boolean gridButton = false;
		for (int i = 0; i < button.length; i++) {
			for (int j = 0; j < button[0].length; j++) {
				if (event.getSource().equals(button[i][j])) {
					gridButton = true;
					current = button[i][j];
					// sets the turn to X turn before the game starts
					if (board[i][j] == BLANK) {
						// sets the turn to O turn after an X turn
						if (turn == X_TURN) {
							current.setText("X");
							current.setEnabled(false);
							board [i][j] = X_MOVE;
							turn = O_TURN;
						}
						// sets the turn to X turn after an O turn
						else {
							current.setText("O");
							current.setEnabled(false);
							board[i][j] = O_MOVE;
							turn = X_TURN;
						}
						// check for wins and ties
						if (checkWin(X_MOVE) == true) {
							// x wins
							xwins++;
							xLabel.setText(xPlayerName + " wins!: " + xwins);
							System.out.println(xPlayerName + " wins!: " + xwins);
							clearBoard();
						}
						else if (checkWin(O_MOVE)== true) {
							// o wins
							owins++;
							oLabel.setText(oPlayerName + " wins!: " + owins);
							System.out.println(oPlayerName + " wins!: " + owins);
							clearBoard();
						}
						else if (checkTie(X_MOVE) == true) {
							// tie on an X move
							System.out.println("It's a tie!");
							clearBoard();
						}
						else if (checkTie(O_MOVE) == true) {
							// tie on an O move
							System.out.println("It's a tie!");
							clearBoard();
						}
					}
				}
				if (gridButton == true)
					break;
			}
			if (gridButton == true)
				break;
			
		}
		if (gridButton == false) {
			// creates a print message for an X win, this function doesn't actually print a win message, only creates the message
			if (event.getSource().equals(xChangeName) == true) {
				xPlayerName = xChangeField.getText();
				if (xPlayerName.trim().length()<1) {
					xPlayerName = "X";
				}
				xLabel.setText(xPlayerName.trim() + " wins!: " + xwins);				
			}
			/*
			// creates a print message for an O win
			else if (event.getSource().equals(oChangeName) == true) {
				oPlayerName = oChangeField.getText();
				if (oPlayerName.trim().length()<1) {
					oPlayerName = "O"; 
				} 
				oLabel.setText(oPlayerName + " wins!: " + owins);
			} */
			else if (event.getSource().equals(resetbutton) == true) {
				clearBoard();
			}

		}
		
		if (turn == X_TURN) 
			turnlabel.setText("It's "+xPlayerName+" turn");
		else 
			turnlabel.setText("It's "+oPlayerName+" turn");
		
		if (turn == O_TURN)
			playComp();

		
	}
	
		public boolean checkWin(int player) { //  win conditions 
			if (board[0][0] == player && board[0][1] == player && board[0][2] == player) {
				return true;
			}
			if (board[1][0] == player && board[1][1] == player && board[1][2] == player) {
				return true;
			}	
			if (board[2][0] == player && board[2][1] == player && board[2][2] == player) {
				return true;
			}	
			if (board[0][0] == player && board[1][0] == player && board[2][0] == player) {
				return true;
			}	
			if (board[0][1] == player && board[1][1] == player && board[2][1] == player) {
				return true;
			}	
			if (board[0][2] == player && board[1][2] == player && board[2][2] == player) {
				return true;
			}	
			if (board[0][0] == player && board[1][1] == player && board[2][2] == player) {
				return true;
			}	
			if (board[0][2] == player && board[1][1] == player && board[2][0] == player) {
				return true;
			}	
			
			return false;
		}
		
		public boolean checkTie(int player) { // tie condition
			//  a tie is declared if every space is used
			for (int row = 0; row < board.length; row++) {
				for (int column = 0; column < board[0].length; column++) {
					if (board[row][column] == BLANK) {
						return false;
					}
				}
			}
			return true;
		}
		
		public void clearBoard() { // clears the board when called
			// resets board to blank
			for (int a = 0; a < board.length; a++) {
				for (int b = 0; b < board[0].length; b++) {
					board[a][b] = BLANK;
					button[a][b].setText("");
					button[a][b].setEnabled(true);
				}
	
			}
			turn = X_TURN;
		}
		public void playComp() {
			
			int blankCell = 0;
			int blankCellX = 0;
			int blankCellY = 0;
			int numOs = 0;
			int numXs = 0;
			//check to win first
			//check rows first
			for (int i = 0; i < board.length; i++) {
				for (int j = 0; j < board[i].length; j++) {
					if (board[i][j] == O_MOVE)
						numOs++;
					else if (board[i][j] == BLANK) {
						blankCell = 1;
						blankCellX = i;
						blankCellY = j;
					}
				}
				//check for winning
				if (numOs == 2 && blankCell == 1){
					JButton cur_button = button[blankCellX][blankCellY];
					cur_button.grabFocus();
					cur_button.doClick(1000);
					return;
				}
				blankCell = 0;
				numOs = 0;
			}
			//check columns
			for (int j = 0; j < board[0].length; j++) {
				for (int i = 0; i < board.length; i++) {
					if (board[i][j] == O_MOVE)
						numOs++;
					else if (board[i][j] == BLANK) {
						blankCell = 1;
						blankCellX = i;
						blankCellY = j;
					}

				}
				if (numOs == 2 && blankCell == 1){
					JButton cur_button = button[blankCellX][blankCellY];
					oChangeField.setText(blankCellX+","+blankCellY);
					cur_button.grabFocus();
					cur_button.doClick(1000);
					return;
				}
				blankCell = 0;
				numOs = 0;
			}
			//check left diagonal
			for (int i = 0; i < board.length; i++) {
				if (board[i][i] == O_MOVE)
					numOs++;
				else if (board[i][i] == BLANK) {
					blankCell = 1;
					blankCellX = i;
					blankCellY = i;
				}
			}
			if (numOs == 2 && blankCell == 1) {
				JButton cur_button = button[blankCellX][blankCellY];
				cur_button.grabFocus();
				cur_button.doClick(1000);
				return;
			}
			blankCell = 0;
			numOs = 0;
			//check right diagonal
			int column = 2;
			for (int i = 0; i < board.length; i++) {
				if (board[i][column] == O_MOVE)
					numOs++;
				else if (board[i][column] == BLANK) {
					blankCell = 1;
					blankCellX = i;
					blankCellY = column;
				}
				column --;
			}
			if (numOs == 2 && blankCell == 1) {
				JButton cur_button = button[blankCellX][blankCellY];
				cur_button.grabFocus();
				cur_button.doClick(1000);
				return;
			}
			
			//check for blocking
			for (int i = 0; i < board.length; i++) {
				for (int j = 0; j < board[i].length; j++) {
					if (board[i][j] == X_MOVE)
						numXs++;
					else if (board[i][j] == BLANK) {
						blankCell = 1;
						blankCellX = i;
						blankCellY = j;
					}
				}
				//check for blocking
				if (numXs == 2 && blankCell ==1) {
					JButton cur_button = button [blankCellX][blankCellY];
					cur_button.grabFocus();
					cur_button.doClick(1000);
					return;
				}
				
				blankCell = 0;
				numXs = 0;
			}
			//check columns
			for (int j = 0; j < board[0].length; j++) {
				for (int i = 0; i < board.length; i++) {
					if (board[i][j] == X_MOVE)
						numXs++;
					else if (board[i][j] == BLANK) {
						blankCell = 1;
						blankCellX = i;
						blankCellY = j;
					}

				}
				if (numXs == 2 && blankCell == 1){
					JButton cur_button = button[blankCellX][blankCellY];					oChangeField.setText(blankCellX+","+blankCellY);
					cur_button.grabFocus();
					cur_button.doClick(1000);
					return;
				}
				blankCell = 0;
				numXs = 0;
			}
			//check left diagonal
			for (int i = 0; i < board.length; i++) {
				if (board[i][i] == X_MOVE)
					numXs++;
				else if (board[i][i] == BLANK) {
					blankCell = 1;
					blankCellX = i;
					blankCellY = i;
				}
			}
			if (numXs == 2 && blankCell == 1) {
				JButton cur_button = button[blankCellX][blankCellY];
				cur_button.grabFocus();
				cur_button.doClick(1000);
				return;
			}
			blankCell = 0;
			numXs = 0;
			//check right diagonal
			column = 2;
			for (int i = 0; i < board.length; i++) {
				if (board[i][column] == X_MOVE)
					numXs++;
				else if (board[i][column] == BLANK) {
					blankCell = 1;
					blankCellX = i;
					blankCellY = column;
				}
				column --;
			}
			if (numXs == 2 && blankCell == 1) {
				JButton cur_button = button[blankCellX][blankCellY];
				cur_button.grabFocus();
				cur_button.doClick(1000);
				return;
			}
		
			//let's pick a random cell
			Random rand = new Random();
			for (int a = 0; a < 100; a++) {
				int random_cell = rand.nextInt(9);
				int curr_cell = 0;
				for (int i = 0; i < board.length; i++) {
					for (int j = 0; j < board[i].length; j++) {
						curr_cell = i*3 + j;
						if (random_cell == curr_cell) {
							if (board[i][j] == BLANK ) {
								JButton cur_button = button[i][j];
								cur_button.grabFocus();
								cur_button.doClick(1000);
								return;
							}
						}
					}
				}
			}

			return;

		}
}
			