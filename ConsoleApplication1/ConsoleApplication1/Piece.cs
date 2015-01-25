using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ConsoleApplication1
{
    public class Piece
    {
        private int[] position;
        private char piece;
        private bool team;
        private bool king = false;

        public Piece(int[] position, char piece, bool team)
        {
            this.position = position;
            this.piece = piece;
            this.team = team;
        }

        public int[] getPosition()
        {
            return position;
        }

        public void setPosition(int[] newPosition)
        {
            position = newPosition;
        }

        public char getPiece()
        {
            return piece;
        }

        public bool getTeam()
        {
            return team;
        }

        public void setKing()
        {
            king = true;
            if (!team)
            {
                piece = '$';
            }
            else
            {
                piece = '0';
            }
        }

        public bool getKing()
        {
            return king;
        }
    }
}
