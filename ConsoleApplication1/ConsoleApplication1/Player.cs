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
        private char PieceSym;
        private char KingSym;
        private bool team;

        Player(bool team)
        {
            this.team = team;
            if (team)
            {
                PieceSym = 'O';
                KingSym = '0';
            }
            else
            {
                PieceSym = 'S';
                KingSym = '$';
            }
        }

        Player(char PieceSym, char KingSym, bool team)
        {
            this.PieceSym = PieceSym;
            this.KingSym = KingSym;
            this.team = team;
        }


        public void addPiece(int i, int j)
        {
            Pieces[i,j] = new Piece();
        }

        public Piece getPiece(int i, int j)
        {
            return Pieces[i,j];
        }

        public char getPieceSym(int i, int j)
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
    
    
    }
}
