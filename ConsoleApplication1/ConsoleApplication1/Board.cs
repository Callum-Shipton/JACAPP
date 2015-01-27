using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ConsoleApplication1
{
    class Board
    {
        private int Width;
        private int Height;
        private int BWidth;
        private int BHeight;

        private Player P1;
        private Player P2;

        private char[,] board;

        Board(Player P1, Player P2, int Width, int Height)
        {
            this.P1 = P1;
            this.P2 = P2;
            this.Width = Width;
            this.Height = Height;
            BWidth = (Width * 2) + 1;
            BHeight = Height;
            board = new char[BWidth, BHeight];
            initBoard();
        }

        Board(Player P1, Player P2)
        {
            this.P1 = P1;
            this.P2 = P2;
            Width = 8;
            Height = 8;
            BWidth = (Width * 2) + 1;
            BHeight = Height;
            board = new char[BWidth, BHeight];
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
                                P1.addPiece(i, j);
                                break;
                            default: board[i, j] = '|'; break;
                        }
                    }
                    else if (j == 1)
                    {
                        switch (i % 4)
                        {
                            case 1: board[i, j] = ' ';
                                P1.addPiece(i, j);
                                break;
                            case 3: board[i, j] = ' '; break;
                            default: board[i, j] = '|'; break;
                        }
                    }
                    else if (j == (Height) || j == (Height - 2))
                    {
                        switch (i % 4)
                        {
                            case 1: board[i, j] = ' ';
                                P2.addPiece(i, j);
                                break;
                            case 3: board[i, j] = ' '; break;
                            default: board[i, j] = '|'; break;
                        }

                    }
                    else if (j == (Height - 1))
                    {
                        switch (i % 4)
                        {
                            case 1: board[i, j] = ' '; break;
                            case 3: board[i, j] = ' ';
                                P2.addPiece(i, j);
                                break;
                            default: board[i, j] = '|'; break;
                        }
                    }
                    else
                    {
                        switch (i % 2)
                        {
                            case 0: board[i, j] = '|'; break;
                            case 1: board[i, j] = ' '; break;
                        }
                    }
                }
            }
        }

        public void printBoard()
        {
            for (int j = BHeight; j > 0; j--)
            {
                for (int i = 0; i < BWidth; i++)
                {
                    if (P1.getPiece(i, j) != null)
                    {
                        Console.Write(P1.getPieceSym(i, j));
                    }
                    else if (P2.getPiece(i, j) != null)
                    {
                        Console.Write(P2.getPieceSym(i, j));
                    }
                    else
                    {
                        Console.Write(board[i, j]);
                    }
                }
                Console.WriteLine();
            }
        }
    }
}
