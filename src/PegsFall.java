/******************************************************************************************************************
	File: PegsFall.java 
	Purpose: Connect Four.  I didn't think "SnakesAndLadders" was challenging enough but I didn't have enough time to 
      restart, so I made another game.
   Author: Thomas Rosehart
	Date: June 21, 2017
	Based on: ICS3U Java Final Assignment
*******************************************************************************************************************/
import javax.sound.sampled.Clip;

class PegsFall
{
   static Board boar = new Board(8,10);
   static Coordinate clickSpot;
   static String[][] array = new String[8][10];
   static String colour[] = {"yellow","black"};
   static int count = 0, row = 1, col = 1;
   static boolean canFall = true;
   static boolean gameOn = true;
   static Clip chip = JCanvas.loadClip("Thud sound.wav");
   
   static void setup()
   {
      for(int n = 0; n < 8; n++)
         for(int i = 0; i < 10; i++)
            array[n][i] = "empty";
   }
   static void pickSpot()
   {
      boar.displayMessage("Player "+(count%2+1)+"'s turn!");
      while(row != 0)
      {
         clickSpot = boar.getClick();
         row = clickSpot.getRow();
         col = clickSpot.getCol();
      }
   }
   
   static void movePiece()throws InterruptedException
   {
      boolean canFall = true;
      //makes sure peg can be put at least one space under chosen point
      while(canFall)
      {
         if(array[row][col].equals("empty") && array[row+1][col].equals("empty"))
         {
            //makes sure peg can be put at least one space under chosen point
            if(array[row+1][col].equals("empty") && row < 7)
            {
               Thread.sleep(30);
               boar.removePeg(row,col);
               row = row+1;
            
               boar.putPeg(colour[count%2],row,col);
               //makes point "full" if at bottom/lowest possible place
               if((row == 7 || array[row+1][col].equals("yellow") || array[row+1][col].equals("black"))  )//&& array[row][col].equals("empty"))
               {
                  canFall = false;
                  if(count%2 == 0)
                     array[row][col] = "yellow";
                  if(count%2 == 1)
                     array[row][col] = "black";
               }
            }  
         } 
         else if (array[row][col].equals("empty") && !array[row+1][col].equals("empty"))// || array[row+1][col].equals("black"))
         {
            boar.putPeg(colour[count%2],row,col);
            canFall = false;
            if(count%2 == 0)
               array[row][col] = "yellow";
            if(count%2 == 1)
               array[row][col] = "black";
         }
         else if(array[row][col].equals("yellow") || array[row][col].equals("black"))
         {
            canFall = false;
            count--;
         }
      }
      Clip chip = JCanvas.loadClip("Thud sound.wav");
      JCanvas.playClip(chip);
   
      check();
      count++;
   }
   
   static void check()
   {
      if(col >= 3&&array[row][col-1].equals(colour[count%2])&&array[row][col-2].equals(colour[count%2])&&array[row][col-3].equals(colour[count%2]))
      {
         boar.displayMessage("Player "+(count%2+1)+" wins!");
         gameOn = false;
      }
      if(col <= 6&&array[row][col+1].equals(colour[count%2])&&array[row][col+2].equals(colour[count%2])&&array[row][col+3].equals(colour[count%2]))
      {
         boar.displayMessage("Player "+(count%2+1)+" wins!");
         gameOn = false;
      }
      if(col <= 7&&col >= 1&&array[row][col+1].equals(colour[count%2])&&array[row][col-1].equals(colour[count%2])&&array[row][col+2].equals(colour[count%2]))
      {
         boar.displayMessage("Player "+(count%2+1)+" wins!");
         gameOn = false;
      }
      if(col >= 2&&col <= 8&&array[row][col+1].equals(colour[count%2])&&array[row][col-1].equals(colour[count%2])&&array[row][col-2].equals(colour[count%2]))
      {
         boar.displayMessage("Player "+(count%2+1)+" wins!");
         gameOn = false;
      }
      if(row <= 4&&array[row+1][col].equals(colour[count%2])&&array[row+2][col].equals(colour[count%2])&&array[row+3][col].equals(colour[count%2]))
      {
         boar.displayMessage("Player "+(count%2+1)+" wins!");
         gameOn = false;
      }
      if(col <= 6&&row >= 3&&array[row-1][col+1].equals(colour[count%2])&&array[row-2][col+2].equals(colour[count%2])&&array[row-3][col+3].equals(colour[count%2]))
      {
         boar.displayMessage("Player "+(count%2+1)+" wins!");
         gameOn = false;
      }
      if(col >= 3&&row >= 3&&array[row-1][col-1].equals(colour[count%2])&&array[row-2][col-2].equals(colour[count%2])&&array[row-3][col-3].equals(colour[count%2]))
      {
         boar.displayMessage("Player "+(count%2+1)+" wins!");
         gameOn = false;
      }
      if(col <= 6&&row <= 4&&array[row+1][col+1].equals(colour[count%2])&&array[row+2][col+2].equals(colour[count%2])&&array[row+3][col+3].equals(colour[count%2]))
      {
         boar.displayMessage("Player "+(count%2+1)+" wins!");
         gameOn = false;
      }
      if(col >= 3&&row <= 4&&array[row+1][col-1].equals(colour[count%2])&&array[row+2][col-2].equals(colour[count%2])&&array[row+3][col-3].equals(colour[count%2]))
      {
         boar.displayMessage("Player "+(count%2+1)+" wins!");
         gameOn = false;
      }
      if(col <= 7&&col >= 1&&row >= 2&&row <= 6&&array[row-1][col+1].equals(colour[count%2])&&array[row-2][col+2].equals(colour[count%2])&&array[row+1][col-1].equals(colour[count%2]))
      {
         boar.displayMessage("Player "+(count%2+1)+" wins!");
         gameOn = false;
      }
      if(col <= 7&&col >= 1&&row >= 1&& row <= 5&&array[row+1][col+1].equals(colour[count%2])&&array[row+2][col+2].equals(colour[count%2])&&array[row-1][col-1].equals(colour[count%2]))
      {
         boar.displayMessage("Player "+(count%2+1)+" wins!");
         gameOn = false;
      }
      if(col <= 8&&col >= 2&&row >= 1&&row <= 5&&array[row+1][col-1].equals(colour[count%2])&&array[row+2][col-2].equals(colour[count%2])&&array[row-1][col+1].equals(colour[count%2]))
      {
         boar.displayMessage("Player "+(count%2+1)+" wins!");
         gameOn = false;
      }
      if(col <= 8&&col >= 2&&row >= 2&&row <= 6&&array[row-1][col-1].equals(colour[count%2])&&array[row-2][col-2].equals(colour[count%2])&&array[row+1][col+1].equals(colour[count%2]))
      {
         boar.displayMessage("Player "+(count%2+1)+" wins!");
         gameOn = false;
      }
   }
   
   static void end()throws InterruptedException
   {
      Thread.sleep(1000);
      for(int i = 0; i < 10; i++)
      {
         for(int m = 0; m<8; m++)
         {
            for(int n = 0; n<10; n++)
            {
               Thread.sleep(20-(i*2));
               boar.putPeg(colour[0],m,n);
            }
         }
         for(int m = 7; m>=0; m--)
         {
            for(int n = 9; n>=0; n--)
            {
               Thread.sleep(20-(i*2));
               boar.putPeg(colour[1],m,n);
            }
         }
      }
   }
   
   public static void main(String[] args)throws InterruptedException
   {    
      setup();
      while(gameOn)
      {
         pickSpot();
         movePiece();
                  
         row = 1;
      }
      end();
   }
}