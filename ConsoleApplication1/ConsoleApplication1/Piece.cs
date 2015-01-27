using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ConsoleApplication1
{
    public class Piece
    {
        private bool king = false;

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
