using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace ShootEmUp
{
    public abstract class Entity
    {
        private int posX;
        private int posY;
        private int health;

        public Entity(int x, int y)
        {
            posX = x;
            posY = y;
            health = 100;
        }

        public Entity(int x, int y, int health)
        {
            posX = x;
            posY = y;
            this.health = health;
        }

        public Entity()
        {

        }

        public int getX()
        {
            return posX;
        }

        public void setX(int x)
        {
            posX = x;
        }

        public int getY()
        {
            return posY;
        }

        public void setY(int y)
        {
            posY = y;
        }

        public int getHealth()
        {
            return health;
        }

        public void setHealth(int health)
        {
            this.health = health;
        }
    }
}
