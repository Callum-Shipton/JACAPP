package gui;

import java.util.concurrent.CopyOnWriteArrayList;

import object.Entity;
import main.ShootEmUp;
import math.Vector2;
import components.TypeComponent;
import components.attack.BaseAttack;
import components.attack.PlayerAttack;
import components.inventory.PlayerInventory;
import components.inventory.TypePotion;
import display.Art;
import display.DPDTRenderer;

public class Hud extends GuiComponent{
	
	public CopyOnWriteArrayList<Icon> hudElems;
	private Icon infoBoxTop;
	private HudBar healthBar;
	private HudBar manaBar;
	private HudBar xpBar;
	private Counter moneyCounter;
	private Counter levelCounter;
	private Counter waveCounter;
	private Entity player;
	
	private Icon infoBoxBottom;
	
	private float hudW;

	public Hud(Entity player){
		this.player = player;
		hudElems = new CopyOnWriteArrayList<Icon>();
		infoBoxTop = new Icon(0.0f,0.0f,Art.infoBoxTop.getWidth(),Art.infoBoxTop.getHeight(), Art.infoBoxTop, false);
		hudElems.add(infoBoxTop);
		healthBar = new HudBar(10.0f, 10.0f, Art.healthBar.getWidth()/Art.healthBar.getFWidth(), Art.healthBar.getHeight()/Art.healthBar.getFHeight(), Art.healthBar);
		hudElems.add(healthBar);
		manaBar = new HudBar(10.0f, 35.0f, Art.manaBar.getWidth()/Art.manaBar.getFWidth(), Art.manaBar.getHeight()/Art.manaBar.getFHeight(), Art.manaBar);
		hudElems.add(manaBar);
		xpBar = new HudBar(10.0f, 60.0f, Art.xpBar.getWidth()/Art.xpBar.getFWidth(), Art.xpBar.getHeight()/Art.xpBar.getFHeight(), Art.xpBar);
		hudElems.add(xpBar);
		moneyCounter = new Counter(10.0f, 82.0f, (Art.coin.getWidth() / Art.coin.getFWidth()), Art.coin.getHeight(), Art.coin, true);
		hudElems.add(moneyCounter);
		levelCounter = new Counter(68.0f, 84.0f, Art.level.getWidth(), Art.level.getHeight(), Art.level, false);
		hudElems.add(levelCounter);
		waveCounter = new Counter(154.0f, 83.0f, Art.wave.getWidth(), Art.wave.getHeight(), Art.wave, false);
		hudElems.add(waveCounter);
		
		//Potions bar
		infoBoxBottom = new Icon(0.0f,ShootEmUp.height - Art.infoBoxBottom.getHeight(),Art.infoBoxBottom.getWidth(),Art.infoBoxBottom.getHeight(), Art.infoBoxBottom, false);
		hudElems.add(infoBoxBottom);
	}
	
	public void render(DPDTRenderer r) {
		for (Icon h : hudElems){
			h.render(r);
		}
	
		Vector2 size = new Vector2(16,16);
		Vector2 maxTex = new Vector2(10,1);
				
		int hPot = ((PlayerInventory)player.getComponent(TypeComponent.INVENTORY)).getNumPotion(TypePotion.HEALTH);
		int mPot = ((PlayerInventory)player.getComponent(TypeComponent.INVENTORY)).getNumPotion(TypePotion.MANA);
		int sPot = ((PlayerInventory)player.getComponent(TypeComponent.INVENTORY)).getNumPotion(TypePotion.SPEED);
		int kPot = ((PlayerInventory)player.getComponent(TypeComponent.INVENTORY)).getNumPotion(TypePotion.KNOCKBACK);
		r.draw(Art.numbers, new Vector2(26, ShootEmUp.height - 55), size, 0.0f, new Vector2(hPot,1), maxTex);
		r.draw(Art.numbers, new Vector2(70, ShootEmUp.height - 55), size, 0.0f, new Vector2(mPot,1), maxTex);
		r.draw(Art.numbers, new Vector2(114, ShootEmUp.height - 55), size, 0.0f, new Vector2(sPot,1), maxTex);
		r.draw(Art.numbers, new Vector2(159, ShootEmUp.height - 55), size, 0.0f, new Vector2(kPot,1), maxTex);
	}

	public void update() {
		for (Icon h : hudElems){
			h.update();
		}
		moneyCounter.update(((PlayerInventory)player.getComponent(TypeComponent.INVENTORY)).getCoins());
		levelCounter.update(((PlayerInventory)player.getComponent(TypeComponent.INVENTORY)).getLevel());
		waveCounter.update(ShootEmUp.currentLevel.spawner.getWave());
		
		int maxHealth = ((BaseAttack)player.getComponent(TypeComponent.ATTACK)).getMaxHealth();
		int health = ((BaseAttack)player.getComponent(TypeComponent.ATTACK)).getHealth();
		int maxMana = ((PlayerAttack)player.getComponent(TypeComponent.ATTACK)).getMaxMana();
		int mana = ((PlayerAttack)player.getComponent(TypeComponent.ATTACK)).getMana();
		int expBound = ((PlayerInventory)player.getComponent(TypeComponent.INVENTORY)).getExpBound();
		int exp = ((PlayerInventory)player.getComponent(TypeComponent.INVENTORY)).getExp();
		
		if(Math.max(maxHealth, maxMana) > 18.0f) {
			hudW =  (Math.max(maxHealth, maxMana)-18.0f)*10.0f + Art.infoBoxTop.getWidth();
			infoBoxTop.setSize(hudW, Art.infoBoxTop.getHeight());
		}

		healthBar.setValue(health);
		healthBar.setMaxValue(maxHealth);
		manaBar.setValue(mana);
		manaBar.setMaxValue(maxMana);
		xpBar.setValue(exp);
		xpBar.setMaxValue(expBound);
		//healthBar.setMFrame((player.getMaxHealth()+1)/(player.getHealth()+1), Art.healthBar.getFHeight());
		//healthBar.setSize((((float)player.getHealth())/((float)player.getMaxHealth())) * Art.healthBar.getWidth(), healthBar.getSize().y());
	}
}
