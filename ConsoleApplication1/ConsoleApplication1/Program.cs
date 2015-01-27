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
        In:
            Console.WriteLine("Input (1) for standard game, (2) for custom:");
            String Game = Console.ReadLine();
            if (Game == "Q")
            {
                Console.Clear();
                goto In;
            }
            int GameInt;
            bool In = int.TryParse(Game, out GameInt);
            if (!In)
            {
                Console.WriteLine("Invalid input.");
                goto In;
            }
            Player P1;
            Player P2;
            Board B;
            bool Turn = true;
            bool Play = true;
            if (GameInt == 2)
            {
                Console.WriteLine("Input Player 1 Symbol:");
                string Sym1 = Console.ReadLine();
                Console.WriteLine("Input Player 1 King Sumbol");
                string KSym1 = Console.ReadLine();
                Console.WriteLine("Input Player 2 Symbol:");
                string Sym2 = Console.ReadLine();
                Console.WriteLine("Input Player 2 King Sumbol");
                string KSym2 = Console.ReadLine();
            WIn:
                Console.WriteLine("Input Board Width:");
                int Width = int.Parse(Console.ReadLine());
                if (Width <= 3)
                {
                    Console.WriteLine("Width too small (>3)");
                    goto WIn;
                }
                if (Width >= 13)
                {
                    Console.WriteLine("Width too large (<13)");
                    goto WIn;
                }
            HIn:
                Console.WriteLine("Input Board Height");
                int Height = int.Parse(Console.ReadLine());
                if (Height <= 7)
                {
                    Console.WriteLine("Height too small (>7)");
                    goto HIn;
                }
                else if (Height % 2 != 0)
                {
                    Console.WriteLine("Height must be a multiple of 2");
                    goto HIn;
                }

                P1 = new Player(Width, Height, Sym1, KSym1, false);
                P2 = new Player(Width, Height, Sym2, KSym2, true);
                P1.setOpponent(P2);
                P2.setOpponent(P1);
                B = new Board(P1, P2, Width, Height);
            }
            else if (GameInt == 1)
            {
                P1 = new Player(8, 8, false);
                P2 = new Player(8, 8, true);
                P1.setOpponent(P2);
                P2.setOpponent(P1);
                B = new Board(P1, P2);
            }
            else
            {
                Console.WriteLine("Invalid input.");
                goto In;
            }
            B.printBoard();
            Console.WriteLine("Type (Q) as move to quit.");
            Console.WriteLine("Move Format: '(i-start) (j-start) (i-next) (j-next) ... (i-finish) (j-finish)'");
            while (Play)
            {
                String x;
                String[] M = null;
                int[,] MS = null;
                bool move = false;
                switch (Turn)
                {
                    case true:
                    P2In:
                        Console.WriteLine("Player 2, Your turn.");
                        Console.WriteLine("Input Move sequence:");
                        x = Console.ReadLine();
                        if (x == "Q")
                        {
                            Play = false;
                            break;
                        }
                        else if (x == "R")
                        {
                            Console.Clear();
                            goto In;
                        }
                        M = x.Split(' ');
                        if (M.Length % 2 != 0)
                        {
                            Console.WriteLine("Invalid Move Sequence Length");
                            goto P2In;
                        }
                        MS = new int[M.Length / 2, 2];
                        for (int i = 0; i < M.Length; i += 2)
                        {
                            bool res = int.TryParse(M[i], out MS[i / 2, 0]);
                            bool res1 = int.TryParse(M[i + 1], out MS[i / 2, 1]);
                            if (!res || !res1)
                            {
                                Console.WriteLine("Invalid int parsing at move {0}", i / 2);
                                goto P2In;
                            }
                        }
                        move = P2.moveSequence(MS);
                        if (!move)
                        {
                            Console.WriteLine("Illegal Move Sequence");
                            goto P2In;
                        }
                        Turn = false;
                        Console.Clear();
                        B.printBoard();
                        break;
                    case false:
                    P1In:
                        Console.WriteLine("Player 1, Your turn.");
                        Console.WriteLine("Input Move sequence:");
                        x = Console.ReadLine();
                        if (x == "Q")
                        {
                            Play = false;
                            break;
                        }
                        else if (x == "R")
                        {
                            Console.Clear();
                            goto In;
                        }
                        M = x.Split(' ');
                        if (M.Length % 2 != 0)
                        {
                            Console.WriteLine("Invalid Move Sequence Length");
                            goto P1In;
                        }
                        MS = new int[M.Length / 2, 2];
                        for (int i = 0; i < M.Length; i += 2)
                        {
                            bool res = int.TryParse(M[i], out MS[i / 2, 0]);
                            bool res1 = int.TryParse(M[i + 1], out MS[i / 2, 1]);
                            if (!res || !res1)
                            {
                                Console.WriteLine("Invalid int parsing at move {0}", i / 2);
                                goto P1In;
                            }
                        }
                        move = P1.moveSequence(MS);
                        if (!move)
                        {
                            Console.WriteLine("Illegal Move Sequence");
                            goto P1In;
                        }
                        Turn = true;
                        Console.Clear();
                        B.printBoard();
                        if (P1.getCount() == 0)
                        {
                            Console.WriteLine("Player 2 has won!");
                            goto In;
                        }
                        else if (P2.getCount() == 0)
                        {
                            Console.WriteLine("Player 1 has won!");
                            goto In;
                        }
                        break;
                }
            }
            Environment.Exit(0);
        }
    }
}
