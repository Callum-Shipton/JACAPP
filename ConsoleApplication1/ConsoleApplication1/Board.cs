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

        public Board(Player P1, Player P2, int Width, int Height)
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

        public Board(Player P1, Player P2)
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
                                P1.addPiece((i - 1) / 2, j);
                                break;
                            default: board[i, j] = '|'; break;
                        }
                    }
                    else if (j == 1)
                    {
                        switch (i % 4)
                        {
                            case 1: board[i, j] = ' ';
                                P1.addPiece((i - 1) / 2, j);
                                break;
                            case 3: board[i, j] = ' '; break;
                            default: board[i, j] = '|'; break;
                        }
                    }
                    else if (j == (Height - 1) || j == (Height - 3))
                    {
                        switch (i % 4)
                        {
                            case 1: board[i, j] = ' ';
                                P2.addPiece((i - 1) / 2, j);
                                break;
                            case 3: board[i, j] = ' '; break;
                            default: board[i, j] = '|'; break;
                        }
                    }
                    else if (j == (Height - 2))
                    {
                        switch (i % 4)
                        {
                            case 1: board[i, j] = ' '; break;
                            case 3: board[i, j] = ' ';
                                P2.addPiece((i - 1) / 2, j);
                                break;
                            default: board[i, j] = '|'; break;
                        }
                    }
                    else if (Height >= 10)
                    {
                        if (j == (Height - 4))
                        {
                            switch (i % 4)
                            {
                                case 1: board[i, j] = ' '; break;
                                case 3: board[i, j] = ' ';
                                    P2.addPiece((i - 1) / 2, j);
                                    break;
                                default: board[i, j] = '|'; break;
                            }
                        }
                        else if (j == 3)
                        {
                            switch (i % 4)
                            {
                                case 1: board[i, j] = ' ';
                                    P1.addPiece((i - 1) / 2, j);
                                    break;
                                case 3: board[i, j] = ' '; break;
                                default: board[i, j] = '|'; break;
                            }
                        }
                        else if (Height == 12)
                        {
                            if (j == (Height - 5))
                            {
                                switch (i % 4)
                                {
                                    case 1: board[i, j] = ' ';
                                        P2.addPiece((i - 1) / 2, j);
                                        break;
                                    case 3: board[i, j] = ' '; break;
                                    default: board[i, j] = '|'; break;
                                }
                            }
                            else if (j == 4)
                            {
                                switch (i % 4)
                                {
                                    case 1: board[i, j] = ' '; break;
                                    case 3: board[i, j] = ' ';
                                        P1.addPiece((i - 1) / 2, j);
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
                        else
                        {
                            switch (i % 2)
                            {
                                case 0: board[i, j] = '|'; break;
                                case 1: board[i, j] = ' '; break;
                            }
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
            for (int j = BHeight - 1; j >= 0; j--)
            {
                for (int i = 0; i < BWidth; i++)
                {
                    if (i % 2 == 1)
                    {
                        if (P1.getPiece((i - 1) / 2, j) != null)
                        {
                            Console.Write(P1.getPieceSym((i - 1) / 2, j));
                        }
                        else if (P2.getPiece((i - 1) / 2, j) != null)
                        {
                            Console.Write(P2.getPieceSym((i - 1) / 2, j));
                        }
                        else
                        {
                            Console.Write(board[i, j]);
                        }
                    }
                    else
                    {
                        Console.Write(board[i, j]);
                    }
                }
                Console.WriteLine("  {0}", j);
            }
            Console.WriteLine();
            for (int i = 0; i < Width; i++)
            {
                Console.Write(" {0}", i);
            }
            Console.WriteLine();
        }
    }
}
