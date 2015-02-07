using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ShootEmUp
{
    class Player : Entity
    {
        public Player(int x, int y, int speed, int health)
            : base(x, y, speed, health)
        {
            
        }

        public Player(int x, int y)
            : base(x, y)
        {
            
        }

        public Player()
        {

        }
    }
}
