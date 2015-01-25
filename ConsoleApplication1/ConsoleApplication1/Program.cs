using System;
using System.Collections.Generic;
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

        private char[,] board;
        private char P1 = '$';
        private char P2 = '0';

        Board(char P1, char P2, int Width, int Height)
        {
            this.Width = Width;
            this.Height = Height;
            BWidth = (Width * 2) + 1;
            BHeight = Height;
            board = new char[BWidth, BHeight];
            this.P1 = P1;
            this.P2 = P2;
            fillBoard();
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
            fillBoard();
        }

        Board(int Width, int Height)
        {
            this.Width = Width;
            this.Height = Height;
            BWidth = (Width * 2) + 1;
            BHeight = Height;
            board = new char[BWidth, BHeight];
            fillBoard();
        }

        Board()
        {
            Width = 8;
            Height = 8;
            BWidth = (Width * 2) + 1;
            BHeight = Height;
            board = new char[BWidth, BHeight];
            fillBoard();
        }

        private void fillBoard()
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
                            case 3: board[i, j] = P1; break;
                            default: board[i, j] = '|'; break;
                         }
                    }
                    else if(j == 1){
                        switch (i % 4)
                        {
                                case 1: board[i, j] = P1; break;
                                case 3: board[i, j] = ' '; break;
                                default: board[i, j] = '|'; break;
                        }
                    }
                    else if(j == (Height) || j == (Height - 2)){
                        switch (i % 4)
	                    {
                            case 1: board[i,j] = P2; break;
                            case 3: board[i,j] = ' '; break;
		                    default: board[i, j] = '|'; break;
	                    }
                                
                    }
                    else if(j == (Height - 1)){
                         switch (i % 4)
	                     {
                             case 1: board[i,j] = ' '; break;
                             case 3: board[i,j] = P2; break;
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
        }
    }
}
