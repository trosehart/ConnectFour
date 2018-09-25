/******************************************************************************************************************
	File: SnakesAndLadders.java 
	Purpose: A game of snakes and ladders where the number of players is chosen at the start.  Each player chooses 
      their colour and their rolls are generated randomly.  If they land on a snake, they move down to its base.  
      If they land on a ladder, they move up to its top.
   Author: Thomas Rosehart
	Date: June 21, 2017
	Based on: ICS3U Java Final Assignment
*******************************************************************************************************************/

import java.util.Scanner;
import javax.swing.*;
import javax.sound.sampled.Clip;

class SnakesAndLadders
{
   //things for Board
   static Board boar = new Board(15,15);
   static Board colourB = new Board(9);
   static Coordinate clickSpot;
   //arrays for finding snakes/ladders, storing colours, storing row and column of each piece, storing colour of each piece
   static String[][] checking = new String[15][15];
   static String[] colours = {"red","blue","yellow","green","cyan","pink","black","white","orange"}; 
   static int[] playerRow;
   static int[] playerCol;
   static String[] playerColo;
   //static integers for knowing who's turn it is, how many squares the user moves, number of players
   static int count = 0;
   static int roll;
   static int numPlayers;
   //false if player reaches the end
   static boolean gameOn = true; 
   //sound effects for ladders and snakes
   static Clip snakeSlide = JCanvas.loadClip("whistleup.wav");
   static Clip upLadder = JCanvas.loadClip("upsound.wav");
   
   static void setUpBoard()
   {
      //SETTING UP SNAKES AND LADDERS
      for(int i = 0; i < 15; i++)
         for(int n = 0; n < 15; n++)
            checking[i][n] = "empty";
      //draws "ladders" (stairs)     
      for(int i = 0; i < 5; i++)
      {
         int row = (int)(Math.random()*11+2);
         int col = (int)(Math.random()*11+2);
      
         boar.drawLine(row,col,row+1,col);
         boar.drawLine(row+1,col,row+1,col-1);
         boar.drawLine(row+1,col-1,row+2,col-1);
         boar.drawLine(row+2,col-1,row+2,col-2);
         checking[row+2][col-2] = "ladder";
      }
      //draws "snakes" (diagonal lines)
      for(int i = 0; i < 5; i++)
      {
         int row = (int)(Math.random()*11+1);
         int col = (int)(Math.random()*11+1);
         
         boar.drawLine(row,col,row+3,col+3);
         checking[row][col] = "snake";
      }
   }
   //music is played during game
   static void music()
   {
      //JCanvas canvas = new JCanvas();
      Clip backgMusic = JCanvas.loadClip("soundbackground2.wav");
      JCanvas.playClip(backgMusic);
      JCanvas.loopClip(backgMusic,10);
   }
   //sets up arrays for pieces
   static void setUpPiece()
   {
      //user enters number of players
      Scanner inData = new Scanner(System.in);
      String numPlayerString = JOptionPane.showInputDialog("Enter number of players: ");
      numPlayers = Integer.parseInt(numPlayerString);
      
      playerRow = new int[numPlayers];
      playerCol = new int[numPlayers];
      playerColo = new String[numPlayers];
      //sets up arrays storing positions of pieces
      for(int i = 0; i < numPlayers; i++)
      {
         playerRow[i] = 14;
         playerCol[i] = 0;
      }
   }
   //users choose what colour they want to play as
   static void setUpColour()
   {
      //USER CHOOSES COLOUR AND IT IS SAVED TO AN ARRAY
      for(int i = 0; i < 9; i++)
         colourB.putPeg(colours[i],0,i);
      colourB.displayMessage("Pick your colour");
      boar.displayMessage("Player "+(count+1)+", choose your colour.");
      clickSpot = colourB.getClick();
      
      int col = clickSpot.getCol();
      playerColo[count] = colours[col];
      
      boar.putPeg(colours[col],14,0);
   }
   
   static void roll()throws InterruptedException
   {
      //NUMBERS BETWEEN 1 AND 12 ARE GENERATED 50 TIMES TO MAKE IT LOOK LIKE SOMETHING IS HAPPENING AND THE FINAL ONE IS THEIR ROLL 
      for(int i = 0; i < 50; i++)
      {
         roll = (int)(Math.random()*12+1);
         boar.displayMessage("Player "+(count%numPlayers+1)+", click your piece to move  "+roll+"  spaces");
         Thread.sleep(20); 
      }
      play();
   }

   static void play()throws InterruptedException
   {
      clickSpot = boar.getClick();
      //if user is at end
      if(playerRow[count%numPlayers] == 0 && playerCol[count%numPlayers] + roll >= 14)
      {
         boar.removePeg(playerRow[count%numPlayers],playerCol[count%numPlayers]);
         boar.putPeg(playerColo[count%numPlayers],0,14);
         gameOn = false;
         boar.displayMessage("Player "+(count%numPlayers+1)+" wins!");
      }
      else
      {
         boar.removePeg(playerRow[count%numPlayers],playerCol[count%numPlayers]);
         //depending on row user is in, different things may occur
         if(playerRow[count%numPlayers]%2 == 0)
         {
            if(playerCol[count%numPlayers]+roll <= 14)
            {
               playerCol[count%numPlayers] = playerCol[count%numPlayers] + roll;
               boar.putPeg(playerColo[count%numPlayers],playerRow[count%numPlayers],playerCol[count%numPlayers]);
            }
            //if roll would take them to next line
            else if(playerCol[count%numPlayers]+roll > 14)
            {
               int goRight = 14 - playerCol[count%numPlayers];
               int goLeft = roll - goRight;
               playerRow[count%numPlayers] = playerRow[count%numPlayers] - 1;
               playerCol[count%numPlayers] = 15 - goLeft;
               boar.putPeg(playerColo[count%numPlayers],playerRow[count%numPlayers],playerCol[count%numPlayers]);
            }
         }
         else if(playerRow[count%numPlayers]%2 == 1)
         {
            if(playerCol[count%numPlayers]-roll >= 0)
            {
               playerCol[count%numPlayers] = playerCol[count%numPlayers] - roll;
               boar.putPeg(playerColo[count%numPlayers],playerRow[count%numPlayers],playerCol[count%numPlayers]);
            }
            //if roll would take them to next line
            else if(playerCol[count%numPlayers] - roll < 0)
            {
               int goRight = roll - playerCol[count%numPlayers];
               playerCol[count%numPlayers] = goRight - 1;
               playerRow[count%numPlayers] = playerRow[count%numPlayers] - 1;
               boar.putPeg(playerColo[count%numPlayers],playerRow[count%numPlayers],playerCol[count%numPlayers]);
            }
         }
         boolean checkAgain = true;
         //if the position they are placed after going up/down a ladder/snake is another ladder/snake, it checks if they need to be moved again
         while(checkAgain)
         {
            //if they land on snake, else if they land on ladder
            if(checking[playerRow[count%numPlayers]][playerCol[count%numPlayers]].equals("snake"))
            {
               Thread.sleep(100);
               JCanvas.playClip(snakeSlide);
               //move to bottom of snake
               boar.removePeg(playerRow[count%numPlayers],playerCol[count%numPlayers]);
               playerRow[count%numPlayers] = playerRow[count%numPlayers] + 3;
               playerCol[count%numPlayers] = playerCol[count%numPlayers] + 3;
            }
            else if(checking[playerRow[count%numPlayers]][playerCol[count%numPlayers]].equals("ladder"))
            {
               Thread.sleep(100);
               JCanvas.playClip(upLadder);
               //move to top of ladder
               boar.removePeg(playerRow[count%numPlayers],playerCol[count%numPlayers]);
               playerRow[count%numPlayers] = playerRow[count%numPlayers] - 2;
               playerCol[count%numPlayers] = playerCol[count%numPlayers] + 2;
            }
            else
            {
               checkAgain = false;
            }
         }
         //places all pieces again to be sure they appear on screen
         for(int i = 0; i < numPlayers; i++)
            boar.putPeg(playerColo[i],playerRow[i],playerCol[i]);
      }
      //count increases so correct player moves next
      count++;
   }
   
   public static void main(String[] args)throws InterruptedException
   {
      setUpBoard();
      music();
      setUpPiece();
      while(count < numPlayers)
      {
         //EACH USER CHOOSES THEIR COLOUR
         setUpColour();
         roll();
      }
      while(gameOn)
      {
         roll();
      }
   }
}
