using System;
using System.Collections.Generic;
using System.Collections;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ConsoleApplication1
{
    class Program
    {
        static void Main(string[] args)
        {

        }
    }
    class Board
    {
        private int Width;
        private int Height;
        private int BWidth;
        private int BHeight;

        private Piece[,] P1Pieces;
        private Piece[,] P2Pieces;

        private char[,] board;
        private char P1 = 'S';
        private char P2 = 'O';

        Board(char P1, char P2, int Width, int Height)
        {
            this.Width = Width;
            this.Height = Height;
            BWidth = (Width * 2) + 1;
            BHeight = Height;
            board = new char[BWidth, BHeight];
            this.P1 = P1;
            this.P2 = P2;
            P1Pieces = new Piece[Width, Height];
            P2Pieces = new Piece[Width, Height];
            initBoard();
        }

        Board(char P1, char P2)
        {
            Width = 8;
            Height = 8;
            BWidth = (Width * 2) + 1;
            BHeight = Height;
            board = new char[BWidth, BHeight];
            this.P1 = P1;
            this.P2 = P2;
            P1Pieces = new Piece[Width, Height];
            P2Pieces = new Piece[Width, Height];
            initBoard();
        }

        Board(int Width, int Height)
        {
            this.Width = Width;
            this.Height = Height;
            BWidth = (Width * 2) + 1;
            BHeight = Height;
            board = new char[BWidth, BHeight];
            P1Pieces = new Piece[Width, Height];
            P2Pieces = new Piece[Width, Height];
            initBoard();
        }

        Board()
        {
            Width = 8;
            Height = 8;
            BWidth = (Width * 2) + 1;
            BHeight = Height;
            board = new char[BWidth, BHeight];
            P1Pieces = new Piece[Width, Height];
            P2Pieces = new Piece[Width, Height];
            initBoard();
        }

        private void initBoard()
        {
            for (int j = 0; j < BHeight; j++)
            {
                for (int i = 0; i < BWidth; i++)
                {
                    if (j == 0 || j == 2)
                    {
                         switch (i % 4)
                         {
                            case 1: board[i, j] = ' '; break;
                            case 3: board[i, j] = ' '; 
                                P1Pieces[i,j] = new Piece(new int[] {i,j}, P1, false);
                                break;
                            default: board[i, j] = '|'; break;
                         }
                    }
                    else if(j == 1){
                        switch (i % 4)
                        {
                            case 1: board[i, j] = ' ';
                                P1Pieces[i, j] = new Piece(new int[] { i, j }, P1, false);
                                break;
                            case 3: board[i, j] = ' '; break;
                            default: board[i, j] = '|'; break;
                        }
                    }
                    else if(j == (Height) || j == (Height - 2)){
                        switch (i % 4)
	                    {
                            case 1: board[i,j] = ' ';
                                P2Pieces[i, j] = new Piece(new int[] { i, j }, P2, true);
                                break;
                            case 3: board[i,j] = ' '; break;
		                    default: board[i, j] = '|'; break;
	                    }
                                
                    }
                    else if(j == (Height - 1)){
                         switch (i % 4)
	                     {
                             case 1: board[i,j] = ' '; break;
                             case 3: board[i,j] = ' ';
                                 P2Pieces[i, j] = new Piece(new int[] { i, j }, P2, true);
                                 break;
		                    default: board[i, j] = '|'; break;
	                     }
                    }
                    else{
                        switch (i % 2)
                        {
                            case 0: board[i,j] = '|';break;
                            case 1: board[i,j] = ' ';break;
                        }
                    }
                }
            }
        }        

        public void printBoard()
        {
            for (int i = 0; i < BWidth; i++)
            {
                for (int j = 0; j < BHeight; j++)
                {
                    if (P1Pieces[i, j] != null)
                    {
                        Console.Write(P1Pieces[i, j].getPiece());
                    }
                    else if (P2Pieces[i, j] != null)
                    {
                        Console.Write(P2Pieces[i, j].getPiece());
                    }
                    else {
                        Console.Write(board[i, j]);
                    }
                }
                Console.WriteLine();
            }
        }

       public bool move(Piece p, int[] newPos){
           if(newPos[0] >= Width || newPos[0] < 0 || newPos[1] >= Height || newPos[1] < 0) {
                return false;    
           }
           switch (p.getTeam())
	        {

            }
       } 

    }
}
