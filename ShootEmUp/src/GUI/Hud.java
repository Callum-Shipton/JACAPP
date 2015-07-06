package GUI;

import java.util.concurrent.CopyOnWriteArrayList;

import Components.ComponentType;
import Components.Attack.BaseAttack;
import Components.Attack.PlayerAttack;
import Components.Inventory.PlayerInventory;
import Display.Art;
import Display.DPDTRenderer;
import Math.Vector2;
import Object.Entity;

public class Hud extends GuiComponent{
	
	public CopyOnWriteArrayList<HudElement> hudElems;
	private HudElement infoBox;
	private HudBar healthBar;
	private HudBar manaBar;
	private HudBar xpBar;
	private HudElement moneyCounter;
	private Entity player;
	private float hudW;

	public Hud(Entity player){
		this.player = player;
		hudElems = new CopyOnWriteArrayList<HudElement>();
		infoBox = new HudElement(0.0f,0.0f,189.0f,110.0f, Art.infoBox, false);
		hudElems.add(infoBox);
		healthBar = new HudBar(10.0f, 10.0f, Art.healthBar.getWidth()/Art.healthBar.getFWidth(), Art.healthBar.getHeight()/Art.healthBar.getFHeight(), Art.healthBar);
		hudElems.add(healthBar);
		manaBar = new HudBar(10.0f, 35.0f, Art.manaBar.getWidth()/Art.manaBar.getFWidth(), Art.manaBar.getHeight()/Art.manaBar.getFHeight(), Art.manaBar);
		hudElems.add(manaBar);
		xpBar = new HudBar(10.0f, 60.0f, Art.xpBar.getWidth()/Art.xpBar.getFWidth(), Art.xpBar.getHeight()/Art.xpBar.getFHeight(), Art.xpBar);
		hudElems.add(xpBar);
		moneyCounter = new HudElement(10.0f, 82.0f, (Art.coin.getWidth() / Art.coin.getFWidth()), Art.coin.getHeight(), Art.coin, true);
		hudElems.add(moneyCounter);
	}
	
	public void render(DPDTRenderer r) {
		for (HudElement h : hudElems){
			h.render(r);
		}
		
		int level =  ((PlayerInventory)player.getComponent(ComponentType.INVENTORY)).getLevel();
		Vector2 size = new Vector2(16,16);
		Vector2 maxTex = new Vector2(10,1);
		
		if(level < 10){
			r.draw(Art.numbers, new Vector2(140,82), size, 0.0f, new Vector2(level,1), maxTex);
		} else {
			r.draw(Art.numbers, new Vector2(140,82), size, 0.0f, new Vector2((int) Math.floor(level / 10),1), maxTex);
			r.draw(Art.numbers, new Vector2(160,82), size, 0.0f, new Vector2((level % 10),1), maxTex);
		}
		
		int coins =  ((PlayerInventory)player.getComponent(ComponentType.INVENTORY)).getCoins();
		if(coins < 10){
			r.draw(Art.numbers, new Vector2(45,82), size, 0.0f, new Vector2(coins,1), maxTex);
		} else {
			r.draw(Art.numbers, new Vector2(45,82), size, 0.0f, new Vector2((int) Math.floor(coins / 10),1), maxTex);
			r.draw(Art.numbers, new Vector2(65,82), size, 0.0f, new Vector2(coins % 10,1), maxTex);
		}
	}

	public void update() {
		int maxHealth = ((BaseAttack)player.getComponent(ComponentType.ATTACK)).getMaxHealth();
		int health = ((BaseAttack)player.getComponent(ComponentType.ATTACK)).getHealth();
		int maxMana = ((PlayerAttack)player.getComponent(ComponentType.ATTACK)).getMaxMana();
		int mana = ((PlayerAttack)player.getComponent(ComponentType.ATTACK)).getMana();
		int expBound = ((PlayerInventory)player.getComponent(ComponentType.INVENTORY)).getExpBound();
		int exp = ((PlayerInventory)player.getComponent(ComponentType.INVENTORY)).getExp();
		
		if(Math.max(maxHealth, maxMana) > 18.0f) {
			hudW =  (Math.max(maxHealth, maxMana)-18.0f)*10.0f + 189.0f;
			infoBox.setSize(hudW, 110.0f);
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
