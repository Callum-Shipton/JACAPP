using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Windows.UI.ViewManagement;
using OpenGLDotNet;
using System.Diagnostics;

namespace ShootEmUp
{
    class ShootEmUp
    {
        static void Main(string[] args)
        {
            Debug.WriteLine("hello world");

            Player player = new Player(5, 5);

            Debug.WriteLine(player.getX());
            Debug.WriteLine(player.getY());
            Debug.WriteLine(player.getSpeed());
            Debug.WriteLine(player.getHealth());

            player.moveHorizontally(1);
            Debug.WriteLine(player.getX());
            player.moveHorizontally(-1);
            Debug.WriteLine(player.getX());

            player.moveVertically(1);
            Debug.WriteLine(player.getY());
            player.moveVertically(-1);
            Debug.WriteLine(player.getY());
        }
    }
}
