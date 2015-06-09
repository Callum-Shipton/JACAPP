package Components.Collision;

import Components.Message;
import Object.Armour;
import Object.Coin;
import Object.Entity;
import Object.Exp;
import Object.Item;
import Object.Weapon;

public class PickupCollision extends BaseCollision{

	public PickupCollision(){
		
	}
	
	@Override
	public void collision(Entity hitter, Entity hit) {
		if (coins < 99) {
			for (Entity coin : entities) {
				if (coin instanceof Coin) {
					if (coin.doesCollide(getPosX(), getPosY(), getWidth(), getHeight()) != null) {
						((Coin) coin).remove();
						coins++;
					}
				}
			}
		}
		
		for (Entity armour : entities) {
			if (armour instanceof Armour) {
				if (armour.doesCollide(getPosX(), getPosY(), getWidth(), getHeight()) != null) {
					((Armour) armour).remove();
					//add pickup to inventory
				}
			}
		}
		for (Entity item : entities) {
			if (item instanceof Item) {
				if (item.doesCollide(getPosX(), getPosY(), getWidth(), getHeight()) != null) {
					((Item) item).remove();
					//add pickup to inventory
				}
			}
		}
		for (Entity weapon : entities) {
			if (weapon instanceof Weapon) {
				if (weapon.doesCollide(getPosX(), getPosY(), getWidth(), getHeight()) != null) {
					((Weapon) weapon).remove();
					//add pickup to inventory
				}
			}
		}
		
		for (Entity exp : entities) {
			if (exp instanceof Exp) {
				if (exp.doesCollide(getPosX(), getPosY(), getWidth(), getHeight()) != null) {
					((Exp) exp).remove();
					currentExp++;
				}
			}
		}
		if (currentExp >= expBound) {
			currentExp = 0;
			if (level < 99) {
				level++;
			}
			if (expBound < 18) {
				expBound++;
			}
		}
		
	}

	@Override
	public void receive(Message m, Entity e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Entity e) {
		// TODO Auto-generated method stub
		
	}

}
