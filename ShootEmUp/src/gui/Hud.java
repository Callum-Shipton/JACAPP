package gui;

import java.util.concurrent.CopyOnWriteArrayList;

import main.ShootEmUp;
import math.Vector2;
import components.ComponentType;
import components.attack.BaseAttack;
import components.attack.PlayerAttack;
import components.inventory.PlayerInventory;
import components.inventory.TypePotion;
import display.Art;
import display.DPDTRenderer;
import entities.Entity;

public class Hud extends GuiComponent{
	
	public CopyOnWriteArrayList<HudElement> hudElems;
	private HudElement infoBoxTop;
	private HudBar healthBar;
	private HudBar manaBar;
	private HudBar xpBar;
	private HudElement moneyCounter;
	private HudElement levelCounter;
	private HudElement waveCounter;
	private Entity player;
	
	private HudElement infoBoxBottom;
	
	private float hudW;

	public Hud(Entity player){
		this.player = player;
		hudElems = new CopyOnWriteArrayList<HudElement>();
		infoBoxTop = new HudElement(0.0f,0.0f,Art.infoBoxTop.getWidth(),Art.infoBoxTop.getHeight(), Art.infoBoxTop, false);
		hudElems.add(infoBoxTop);
		healthBar = new HudBar(10.0f, 10.0f, Art.healthBar.getWidth()/Art.healthBar.getFWidth(), Art.healthBar.getHeight()/Art.healthBar.getFHeight(), Art.healthBar);
		hudElems.add(healthBar);
		manaBar = new HudBar(10.0f, 35.0f, Art.manaBar.getWidth()/Art.manaBar.getFWidth(), Art.manaBar.getHeight()/Art.manaBar.getFHeight(), Art.manaBar);
		hudElems.add(manaBar);
		xpBar = new HudBar(10.0f, 60.0f, Art.xpBar.getWidth()/Art.xpBar.getFWidth(), Art.xpBar.getHeight()/Art.xpBar.getFHeight(), Art.xpBar);
		hudElems.add(xpBar);
		moneyCounter = new HudElement(10.0f, 82.0f, (Art.coin.getWidth() / Art.coin.getFWidth()), Art.coin.getHeight(), Art.coin, true);
		hudElems.add(moneyCounter);
		levelCounter = new HudElement(68.0f, 84.0f, Art.level.getWidth(), Art.level.getHeight(), Art.level, false);
		hudElems.add(levelCounter);
		waveCounter = new HudElement(154.0f, 83.0f, Art.wave.getWidth(), Art.wave.getHeight(), Art.wave, false);
		hudElems.add(waveCounter);
		
		//Potions bar
		infoBoxBottom = new HudElement(0.0f,ShootEmUp.HEIGHT - Art.infoBoxBottom.getHeight(),Art.infoBoxBottom.getWidth(),Art.infoBoxBottom.getHeight(), Art.infoBoxBottom, false);
		hudElems.add(infoBoxBottom);
	}
	
	public void render(DPDTRenderer r) {
		for (HudElement h : hudElems){
			h.render(r);
		}
		
		int level =  ((PlayerInventory)player.getComponent(ComponentType.INVENTORY)).getLevel();
		Vector2 size = new Vector2(16,16);
		Vector2 maxTex = new Vector2(10,1);
		
		int coins =  ((PlayerInventory)player.getComponent(ComponentType.INVENTORY)).getCoins();
		if(coins < 10){
			r.draw(Art.numbers, new Vector2(28,82), size, 0.0f, new Vector2(coins,1), maxTex);
		} else {
			r.draw(Art.numbers, new Vector2(28,82), size, 0.0f, new Vector2((int) Math.floor(coins / 10),1), maxTex);
			r.draw(Art.numbers, new Vector2(48,82), size, 0.0f, new Vector2(coins % 10,1), maxTex);
		}
		
		if(level < 10){
			r.draw(Art.numbers, new Vector2(113,82), size, 0.0f, new Vector2(level,1), maxTex);
		} else {
			r.draw(Art.numbers, new Vector2(113,82), size, 0.0f, new Vector2((int) Math.floor(level / 10),1), maxTex);
			r.draw(Art.numbers, new Vector2(133,82), size, 0.0f, new Vector2((level % 10),1), maxTex);
		}
		int hPot = ((PlayerInventory)player.getComponent(ComponentType.INVENTORY)).getNumPotion(TypePotion.HEALTH);
		int mPot = ((PlayerInventory)player.getComponent(ComponentType.INVENTORY)).getNumPotion(TypePotion.MANA);
		int sPot = ((PlayerInventory)player.getComponent(ComponentType.INVENTORY)).getNumPotion(TypePotion.SPEED);
		int kPot = ((PlayerInventory)player.getComponent(ComponentType.INVENTORY)).getNumPotion(TypePotion.KNOCKBACK);
		r.draw(Art.numbers, new Vector2(26, ShootEmUp.HEIGHT - 55), size, 0.0f, new Vector2(hPot,1), maxTex);
		r.draw(Art.numbers, new Vector2(70, ShootEmUp.HEIGHT - 55), size, 0.0f, new Vector2(mPot,1), maxTex);
		r.draw(Art.numbers, new Vector2(114, ShootEmUp.HEIGHT - 55), size, 0.0f, new Vector2(sPot,1), maxTex);
		r.draw(Art.numbers, new Vector2(159, ShootEmUp.HEIGHT - 55), size, 0.0f, new Vector2(kPot,1), maxTex);
		
		
		int wave =  ShootEmUp.currentLevel.spawner.getWave();
		if(wave < 10){
			r.draw(Art.numbers, new Vector2(200,82), size, 0.0f, new Vector2(wave,1), maxTex);
		} else {
			r.draw(Art.numbers, new Vector2(200,82), size, 0.0f, new Vector2((int) Math.floor(wave / 10),1), maxTex);
			r.draw(Art.numbers, new Vector2(220,82), size, 0.0f, new Vector2(wave % 10,1), maxTex);
		}
	}

	public void update() {
		for (HudElement h : hudElems){
			h.update();
		}
		
		int maxHealth = ((BaseAttack)player.getComponent(ComponentType.ATTACK)).getMaxHealth();
		int health = ((BaseAttack)player.getComponent(ComponentType.ATTACK)).getHealth();
		int maxMana = ((PlayerAttack)player.getComponent(ComponentType.ATTACK)).getMaxMana();
		int mana = ((PlayerAttack)player.getComponent(ComponentType.ATTACK)).getMana();
		int expBound = ((PlayerInventory)player.getComponent(ComponentType.INVENTORY)).getExpBound();
		int exp = ((PlayerInventory)player.getComponent(ComponentType.INVENTORY)).getExp();
		
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
