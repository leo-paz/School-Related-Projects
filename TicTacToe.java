import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * A class modelling a tic-tac-toe (noughts and crosses, Xs and Os) game.
 * 
 * @author Leonardo Paz
 * @version November 28, 2017
 */

public class TicTacToe implements ActionListener
{
   public static final String PLAYER_X = "X"; // player using "X"
   public static final String PLAYER_O = "O"; // player using "O"
   public static final String EMPTY = " ";  // empty cell
   public static final String TIE = "T"; // game ended in a tie
   
   private static final int FRAME_WIDTH = 350; // width of frame
   private static final int FRAME_HEIGHT = 350;   // height of frame
   private static final int TEXT = 30;          // text width
   
   /** current player (PLAYER_X or PLAYER_O) */
   private String player;
   
   /** winner: PLAYER_X, PLAYER_O, TIE, EMPTY = in progress */
   private String winner;  
   
   /** number of squares still free */
   private int numFreeSquares; 
   
   /** 3x3 array representing the board of buttons */
   private JButton board[][]; 
   
   /** Window where game will be displayed */
   private JFrame frame = new JFrame("Tic-Tac-Toe");
   
   /** Menu item to reset the board */
   private JMenuItem resetMenuItem;
   
   /** Menu item to quit the game */
   private JMenuItem quitMenuItem;
   
   /** Text process of the game */
   private JLabel gameProcess;
   
   /** 
    * Constructs a new Tic-Tac-Toe board.
    */
   public TicTacToe()
   {
      setGUI(); // set up GUI
      player = PLAYER_X;
      numFreeSquares = 9;
      winner = EMPTY;
   }
   
   /** 
    * Constructs a new Tic-Tac-Toe board.
    */
   public void reset()
   {
      for (int row = 0; row < 3; row++) {
          for (int col = 0; col < 3; col++) {
              board[row][col].setText(EMPTY);
              board[row][col].setIcon(new ImageIcon(""));
              board[row][col].setEnabled(true);
          }
      }
      gameProcess.setText("Currently X's turn");
      
      player = PLAYER_X;
      numFreeSquares = 9;
      winner = EMPTY;
   }
   
   /** 
    * Constructs a new Tic-Tac-Toe board.
    */
   public void setGUI()
   {
      frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      
      JMenuBar menuBar = new JMenuBar(); // set up menu bar and add to the frame
      frame.setJMenuBar(menuBar);
      
      JMenu menu = new JMenu("Options"); // create an opetions menu and add to menu bar
      menuBar.add(menu);
      
      quitMenuItem = new JMenuItem("Quit"); // create the quit menu item and add to our menu
      menu.add(quitMenuItem);
      quitMenuItem.addActionListener(this);
      
      resetMenuItem = new JMenuItem("Reset"); // create the reset menu item and add to our menu
      menu.add(resetMenuItem);
      resetMenuItem.addActionListener(this);
      
      JPanel gameScreen = new JPanel(); // add panel where game is to be played
      gameScreen.setLayout(new GridLayout(3, 3)); // set to grid layout
      frame.getContentPane().add(gameScreen, BorderLayout.CENTER); // add to center
      
      gameProcess = new JLabel("Currently X's turn"); // create process label and add to pane
      frame.getContentPane().add(gameProcess, BorderLayout.SOUTH); // add to bottom of GUI
      
      board = new JButton[3][3]; // initialize board of buttons
      for (int row = 0; row < 3; row++) {
          for (int col = 0; col < 3; col++) {
              board[row][col] = new JButton(EMPTY);
              gameScreen.add(board[row][col]);
              board[row][col].addActionListener(this); // add a listener to each button
          }
      }
      
      frame.setVisible(true);
   }
   
   /**
    * Sets everything up for a new game. Disables all buttons.
    */
   private void clearBoard()
   {
      if (numFreeSquares==0) {
          return;
      }
      
      for (int i = 0; i < 3; i++) {
         for (int j = 0; j < 3; j++) {
            board[i][j].setEnabled(false);
         }
      }
   }

   /**
    * Returns true if filling the given square gives us a winner, and false
    * otherwise.
    *
    * @param b Square just filled
    * 
    * @return true if we have a winner, false otherwise
    */
   private boolean haveWinner(JButton b) 
   {
       // unless at least 5 squares have been filled, we don't need to go any further
       // (the earliest we can have a winner is after player X's 3rd move).

       if (numFreeSquares>4) return false;

      int row = 0;
      int col = 0;
      
       for (int i = 0; i < 3; i++) {
         for (int j = 0; j < 3; j++) {
            if (board[i][j] == b) { // found button
                row = i;
                col = j;
            }
         }
      }
       
       // check row "row"
      if ( board[row][0].getText().equals(board[row][1].getText()) &&
           board[row][0].getText().equals(board[row][2].getText()) ) return true;
       
      // check column "col"
      if ( board[0][col].getText().equals(board[1][col].getText()) &&
           board[0][col].getText().equals(board[2][col].getText()) ) return true;

      // if row=col check one diagonal
      if (row==col)
         if ( board[0][0].getText().equals(board[1][1].getText()) &&
               board[0][0].getText().equals(board[2][2].getText()) ) return true;

      // if row=2-col check other diagonal
      if (row==2-col)
         if ( board[0][2].getText().equals(board[1][1].getText()) &&
               board[0][2].getText().equals(board[2][0].getText()) ) return true;

      // no winner yet
      return false;
   }
    
   /** 
    * Action Performed is called when a button is clicked.
    * From the actionListener Interface.
    * 
    * @return the action event
    */
   public void actionPerformed(ActionEvent e)
   {
        if (e.getSource() instanceof JMenuItem) {
            JMenuItem selection = (JMenuItem) e.getSource();
            if (selection == resetMenuItem) { // clicked reset so reset game
                reset();
                return;
            }
            System.exit(0);
        }
        
        JButton button = (JButton) e.getSource(); // if it wasnt a menu item it can only be a button
        
        button.setText(player);
        if(player == PLAYER_X) { // set the button icon to an x picture
            button.setIcon(new ImageIcon("xplayer.jpg"));
            button.setDisabledIcon(new ImageIcon("xplayer.jpg"));
        }
        else { // set the button icon to an o picture
            button.setIcon(new ImageIcon("oplayer.jpg"));
            button.setDisabledIcon(new ImageIcon("oplayer.jpg"));
        }
        button.setEnabled(false);
        
        numFreeSquares--;
        
        if (haveWinner(button)) { 
            winner = player;
        }
        else if (numFreeSquares == 0) { // no more squares so must be tie
            winner = TIE;
        }
        
        if (winner != EMPTY) {
            clearBoard(); // no longer have the buttons active
            if (winner == TIE) {
                gameProcess.setText("Game Over! Tie game!");
            }
            else if (winner == PLAYER_X) {
                gameProcess.setText("Game Over! Player X wins!");
            }
            else if (winner == PLAYER_O) {
                gameProcess.setText("Game Over! Player O wins!");
            }
        }
        
        else { // game continues and swap players for each turn
            if (player == PLAYER_X) {
                player = PLAYER_O;
                gameProcess.setText("Currently O's turn");
            }
            else {
                player = PLAYER_X;
                gameProcess.setText("Currently X's turn");
            }
        }
   }
}
    


