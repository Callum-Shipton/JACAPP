using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ConsoleApplication1
{
    class Player
    {
        private Piece[,] Pieces;
        private int count;

        private Player OtherPlayer;

        private int Width;
        private int Height;

        private string PieceSym;
        private string KingSym;

        private bool team;

        public Player(int Width, int Height, bool team)
        {
            this.Width = Width;
            this.Height = Height;
            this.team = team;
            if (team)
            {
                PieceSym = "S";
                KingSym = "$";
            }
            else
            {
                PieceSym = "O";
                KingSym = "0";
            }
            Pieces = new Piece[Width, Height];
            count = 0;
        }

        public Player(int Width, int Height, string PieceSym, string KingSym, bool team)
        {
            this.Width = Width;
            this.Height = Height;
            this.PieceSym = PieceSym;
            this.KingSym = KingSym;
            this.team = team;
            Pieces = new Piece[Width, Height];
            count = 0;
        }

        public void setOpponent(Player p)
        {
            OtherPlayer = p;
        }

        public void addPiece(int i, int j)
        {
            Pieces[i, j] = new Piece();
            count++;
        }

        public Piece getPiece(int i, int j)
        {
            return Pieces[i, j];
        }

        public int getCount()
        {
            return count;
        }

        public string getPieceSym(int i, int j)
        {
            if (Pieces[i, j].getKing())
            {
                return KingSym;
            }
            else
            {
                return PieceSym;
            }
        }

        private void move(int i, int j, int newi, int newj)
        {
            if (Math.Abs(newi - i) == 2)
            {
                OtherPlayer.Pieces[i + (newi - i) / 2, j + (newj - j) / 2] = null;
                OtherPlayer.count--;
            }
            Pieces[newi, newj] = Pieces[i, j];
            Pieces[i, j] = null;
            if ((team && newj == 0) || (!team && newj == Height - 1))
            {
                Pieces[newi, newj].setKing();
            }
        }

        public bool moveSequence(int[,] sequence)
        {
            Piece p = Pieces[sequence[0, 0], sequence[0, 1]];
            int j = 1;
            while (j < sequence.Length / 2 && checkMove(p, sequence[j - 1, 0], sequence[j - 1, 1], sequence[j, 0], sequence[j, 1]))
            {
                j++;
            };
            if (j == (sequence.Length / 2))
            {
                for (j = 1; j < sequence.Length / 2; j++)
                {
                    move(sequence[j - 1, 0], sequence[j - 1, 1], sequence[j, 0], sequence[j, 1]);
                }
                return true;
            }
            return false;
        }

        private bool checkMove(Piece p, int i, int j, int newi, int newj)
        {
            if (i < 0 || newi < 0 || j < 0 || newj < 0 || i > Width || newi > Width || j > Height || newj > Height)
            {
                return false;
            }
            if (p == null)
            {
                return false;
            }
            if ((Pieces[newi, newj] != null) || OtherPlayer.getPiece(newi, newj) != null)
            {
                return false;
            }
            if (p.getKing())
            {
                return checkKingMove(i, j, newi, newj);
            }
            if (team)
            {
                if (Math.Abs(newi - i) == 2 && newj == (j - 2))
                {
                    return checkTake(i, j, newi, newj);
                }
                if (!(Math.Abs(newi - i) == 1 && newj == (j - 1)))
                {
                    return false;
                }
                if ((i + ((newi - i) * -2) >= 0 && i + ((newi - i) * -2) <= Width))
                {
                    return !((OtherPlayer.getPiece(i + ((newi - i) * -1), newj) != null) && ((Pieces[i + ((newi - i) * -2), newj - 1] == null) || (OtherPlayer.getPiece(i + ((newi - i) * -2), newj - 1) == null)));
                }
                return true;
            }

            if (Math.Abs(newi - i) == 2 && newj == (j + 2))
            {
                return checkTake(i, j, newi, newj);
            }
            if (!(Math.Abs(newi - i) == 1 && newj == (j + 1)))
            {
                return false;
            }
            if ((i + ((newi - i) * -2) >= 0 && i + ((newi - i) * -2) <= Width))
            {
                return !((OtherPlayer.getPiece(i + ((newi - i) * -1), newj) != null) && ((Pieces[i + ((newi - i) * -2), newj + 1] == null) || (OtherPlayer.getPiece(i + ((newi - i) * -2), newj + 1) == null)));
            }
            return true;
        }


        private bool checkTake(int i, int j, int newi, int newj)
        {
            return (OtherPlayer.getPiece(i + ((newi - i) / 2), j + ((newj - j) / 2)) != null);
        }

        private bool checkKingMove(int i, int j, int newi, int newj)
        {
            if (Math.Abs(newi - i) == 2 && Math.Abs(newj - j) == 2)
            {
                return checkTake(i, j, newi, newj);
            }
            if (!(Math.Abs(newi - i) == 1 && Math.Abs(newj - j) == 1))
            {
                return false;
            }
            if ((i + ((newi - i) * -2) >= 0 && i + ((newi - i) * -2) <= Width))
            {
                if ((OtherPlayer.getPiece(i + ((newi - i) * -1), newj) != null) && ((Pieces[i + ((newi - i) * -2), newj + (newj - j)] == null) || (OtherPlayer.getPiece(i + ((newi - i) * -2), newj + (newj - j)) == null)))
                {
                    return false;
                }
            }
            if ((j + ((newj - j) * -2) >= 0 && j + ((newj - j) * -2) <= Height))
            {
                if ((OtherPlayer.getPiece(newi, j + ((newj - j) * -1)) != null) && ((Pieces[newi + (newi - i), j + ((newj - j) * -2)] == null) || (OtherPlayer.getPiece(newi + (newi - i), j + ((newj - j) * -2)) == null)))
                {
                    return false;
                }
            }
            if ((i + ((newi - i) * -2) >= 0 && i + ((newi - i) * -2) <= Width) && (j + ((newj - j) * -2) >= 0 && j + ((newj - j) * -2) <= Height))
            {
                if ((OtherPlayer.getPiece(i + ((newi - i) * -1), j + ((newj - j) * -1)) != null) && ((Pieces[i + ((newi - i) * -2), j + ((newj - j) * -2)] == null) || (OtherPlayer.getPiece(i + ((newi - i) * -2), j + ((newj - j) * -2)) == null)))
                {
                    return false;
                }
            }
            return true;
        }
    }
}