using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ConsoleApplication1
{
    public class Piece
    {
        int[] position = new int [2];
        bool team;
        bool king = false;

        public Piece(int[] position, bool team)
        {
            this.position = position;
            this.team = team;
        }

        public int[] getPosition()
        {
            return position;
        }

        public bool getTeam()
        {
            return team;
        }

        public void setPosition(int[] newPosition)
        {
            position = newPosition;
        }

        public void setKing()
        {
            king = true;
        }

        public bool getKing()
        {
            return king;
        }
    }
}
