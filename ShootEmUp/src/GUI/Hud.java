package GUI;

import java.util.concurrent.CopyOnWriteArrayList;

import Components.ComponentType;
import Components.Attack.BaseAttack;
import Components.Attack.MageAttack;
import Components.Inventory.PlayerInventory;
import Display.Art;
import Display.DPDTRenderer;
import Math.Vector2;
import Object.Entity;

public class Hud extends GuiComponent{
	
	public CopyOnWriteArrayList<HudElement> hudElems;
	private HudElement infoBox;
	private HudElement healthBar;
	private HudElement manaBar;
	private HudElement xpBar;
	private HudElement moneyCounter;
	private Entity player;
	private float hudW;

	public Hud(Entity player){
		this.player = player;
		hudElems = new CopyOnWriteArrayList<HudElement>();
		infoBox = new HudElement(0.0f,0.0f,189.0f,110.0f, Art.infoBox);
		hudElems.add(infoBox);
		healthBar = new HudElement(10.0f, 10.0f, Art.healthBar.getWidth(), Art.healthBar.getHeight()/Art.healthBar.getFHeight(), Art.healthBar);
		hudElems.add(healthBar);
		manaBar = new HudElement(10.0f, 35.0f, Art.manaBar.getWidth(), Art.manaBar.getHeight()/Art.manaBar.getFHeight(), Art.manaBar);
		hudElems.add(manaBar);
		xpBar = new HudElement(10.0f, 60.0f, Art.xpBar.getWidth(), Art.xpBar.getHeight()/Art.xpBar.getFHeight(), Art.xpBar);
		hudElems.add(xpBar);
		moneyCounter = new HudElement(10.0f, 82.0f, Art.BarCoin.getWidth(), Art.BarCoin.getHeight(), Art.BarCoin);
		hudElems.add(moneyCounter);
	}
	
	public void render(DPDTRenderer r) {
		for (HudElement h : hudElems){
			h.render(r);
		}
		
		int level =  ((PlayerInventory)player.getComponent(ComponentType.INVENTORY)).getLevel();
		if(level < 10){
			r.draw(Art.numbers, new Vector2(140,82), new Vector2(16,16), 0.0f, new Vector2(level,1), new Vector2(10,1));
		} else {
			r.draw(Art.numbers, new Vector2(140,82), new Vector2(16,16), 0.0f, new Vector2((int) Math.floor(level / 10),1), new Vector2(10,1));
			r.draw(Art.numbers, new Vector2(160,82), new Vector2(16,16), 0.0f, new Vector2((level % 10),1), new Vector2(10,1));
		}
		
		int coins =  ((PlayerInventory)player.getComponent(ComponentType.INVENTORY)).getCoins();
		if(coins < 10){
			r.draw(Art.numbers, new Vector2(45,82), new Vector2(16,16), 0.0f, new Vector2(coins,1), new Vector2(10,1));
		} else {
			r.draw(Art.numbers, new Vector2(45,82), new Vector2(16,16), 0.0f, new Vector2((int) Math.floor(coins / 10),1), new Vector2(10,1));
			r.draw(Art.numbers, new Vector2(65,82), new Vector2(16,16), 0.0f, new Vector2(coins % 10,1), new Vector2(10,1));
		}
	}

	public void update() {
		int maxHealth = ((BaseAttack)player.getComponent(ComponentType.ATTACK)).getMaxHealth();
		int health = ((BaseAttack)player.getComponent(ComponentType.ATTACK)).getHealth();
		int maxMana = ((MageAttack)player.getComponent(ComponentType.ATTACK)).getMaxMana();
		int mana = ((MageAttack)player.getComponent(ComponentType.ATTACK)).getMana();
		int expBound = ((PlayerInventory)player.getComponent(ComponentType.INVENTORY)).getExpBound();
		int exp = ((PlayerInventory)player.getComponent(ComponentType.INVENTORY)).getExp();
		
		hudW = (Math.max(maxHealth, maxMana) - 18.0f)*10.0f + 189.0f;
		infoBox.setSize(hudW, 110.0f);
		healthBar.setVal(health);
		healthBar.setSize(maxHealth*Art.healthBar.getWidth()/((Art.healthBar.getHeight()/Art.healthBar.getFHeight())-1), Art.healthBar.getHeight()/Art.healthBar.getFHeight());
		healthBar.setMFrame(18.0f/maxHealth, Art.healthBar.getFHeight() );
		manaBar.setVal(mana);
		manaBar.setSize(maxMana*Art.manaBar.getWidth()/((Art.manaBar.getHeight()/Art.manaBar.getFHeight())-1), Art.manaBar.getHeight()/Art.manaBar.getFHeight());
		manaBar.setMFrame(18.0f/maxMana, Art.manaBar.getFHeight() );
		xpBar.setVal(exp);
		xpBar.setSize(expBound*Art.xpBar.getWidth()/((Art.xpBar.getHeight()/Art.xpBar.getFHeight())-1), Art.xpBar.getHeight()/Art.xpBar.getFHeight());
		xpBar.setMFrame(18.0f/expBound, Art.xpBar.getFHeight() );
		//healthBar.setMFrame((player.getMaxHealth()+1)/(player.getHealth()+1), Art.healthBar.getFHeight());
		//healthBar.setSize((((float)player.getHealth())/((float)player.getMaxHealth())) * Art.healthBar.getWidth(), healthBar.getSize().y());
	}
}
