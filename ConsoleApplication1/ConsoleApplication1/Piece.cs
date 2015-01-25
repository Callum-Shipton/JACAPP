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

        public Piece(int[] position, Boolean team)
        {
            this.position = position;
        }
    }
}
