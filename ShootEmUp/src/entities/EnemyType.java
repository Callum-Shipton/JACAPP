package entities;

import components.attack.TypeAttack;
import display.Image;

public class EnemyType {

		Image image;
		TypeAttack attack;
		int health;
		int speed;
	
		public EnemyType(Image image, TypeAttack attack, int health, int speed){
			this.image = image;
			this.attack = attack;
			this.health = health;
			this.speed = speed;
		}

		public Image getImage() {
			return image;
		}

		public TypeAttack getAttack() {
			return attack;
		}

		public int getHealth() {
			return health;
		}

		public int getSpeed() {
			return speed;
		}
}
