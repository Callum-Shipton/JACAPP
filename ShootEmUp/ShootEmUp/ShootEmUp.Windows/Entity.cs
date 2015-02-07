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
        private int speed;
        private int health;

        protected Entity(int x, int y)
        {
            posX = x;
            posY = y;
            health = 100;
            speed = 10;
        }

        protected Entity(int x, int y, int speed, int health)
        {
            posX = x;
            posY = y;
            this.speed = speed;
            this.health = health;
        }

        protected Entity()
        {

        }

        //methods

        public void moveVertically(int direction)
        {
            posY += (speed * direction);
        }

        public void moveHorizontally(int direction)
        {
            posX += (speed * direction);
        }

        //get and set methods

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

        public int getSpeed()
        {
            return speed;
        }

        public void setSpeed(int speed)
        {
            this.speed = speed;
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
