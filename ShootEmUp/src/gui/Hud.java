package gui;

import java.util.concurrent.CopyOnWriteArrayList;

import components.TypeComponent;
import components.attack.BaseAttack;
import components.attack.PlayerAttack;
import components.inventory.BaseInventory;
import components.inventory.TypePotion;
import components.movement.BasicMovement;
import display.Art;
import display.DPDTRenderer;
import main.ShootEmUp;
import math.Vector2;
import object.Entity;

public class Hud extends GuiComponent {

	public CopyOnWriteArrayList<Icon> hudElems;
	private Icon infoBoxTop;
	private HudBar healthBar;
	private HudBar manaBar;
	private HudBar xpBar;
	private Counter moneyCounter;
	private Counter levelCounter;
	private Counter waveCounter;
	private Counter livesCounter;
	private Entity player;

	private Icon fire;
	private Icon frost;
	private Icon poison;

	private Icon infoBoxBottom;

	private float hudW;

	public Hud(Entity player, float x, float y) {
		super(x, y);
		this.player = player;
		hudElems = new CopyOnWriteArrayList<Icon>();
		infoBoxTop = new Icon(0.0f, 0.0f, Art.getImage("BarInfoTop"), false, 1f);
		hudElems.add(infoBoxTop);
		healthBar = new HudBar(10.0f, 10.0f, Art.getImage("BarHealth"), 1f);
		hudElems.add(healthBar);
		manaBar = new HudBar(10.0f, 35.0f, Art.getImage("BarMana"), 1f);
		hudElems.add(manaBar);
		xpBar = new HudBar(10.0f, 60.0f, Art.getImage("BarXP"), 1f);
		hudElems.add(xpBar);
		moneyCounter = new Counter(10.0f, 82.0f, Art.getImage("CoinIcon"), false,
				((BaseInventory) player.getComponent(TypeComponent.INVENTORY)).getCoins(), 0.5f);
		hudElems.add(moneyCounter);
		levelCounter = new Counter(90.0f, 82.0f, Art.getImage("LevelIcon"), false,
				((BaseInventory) player.getComponent(TypeComponent.INVENTORY)).getLevel(), 0.5f);
		hudElems.add(levelCounter);
		waveCounter = new Counter(170.0f, 82.0f, Art.getImage("WaveIcon"), false,
				ShootEmUp.currentLevel.spawner.getWave(), 0.5f);
		hudElems.add(waveCounter);
		livesCounter = new Counter(250.0f, 82.0f, Art.getImage("LivesIcon"), false,
				((PlayerAttack) player.getComponent(TypeComponent.ATTACK)).getLives(), 0.5f);
		hudElems.add(livesCounter);
		fire = new Icon(0.0f, 100.0f, Art.getImage("Fire"), false, 1f);
		poison = new Icon(0.0f, 120.0f, Art.getImage("Poison"), false, 1f);
		frost = new Icon(0.0f, 140.0f, Art.getImage("Frost"), false, 1f);

		// Potions bar
		infoBoxBottom = new Icon(0.0f, ShootEmUp.height - Art.getImage("BarInfoBottom").getHeight(),
				Art.getImage("BarInfoBottom"), false, 1f);
		hudElems.add(infoBoxBottom);
	}

	@Override
	public void render(DPDTRenderer r) {
		for (Icon h : hudElems) {
			h.render(r);
		}

		if (((PlayerAttack) player.getComponent(TypeComponent.ATTACK)).isFire()) {
			fire.render(r);
		}
		if (((PlayerAttack) player.getComponent(TypeComponent.ATTACK)).isPoison()) {
			poison.render(r);
		}
		if (((BasicMovement) player.getComponent(TypeComponent.MOVEMENT)).isFrost()) {
			frost.render(r);
		}

		Vector2 size = new Vector2(16, 16);
		Vector2 maxTex = new Vector2(10, 1);

		int hPot = ((BaseInventory) player.getComponent(TypeComponent.INVENTORY)).getNumPotion(TypePotion.HEALTH);
		int mPot = ((BaseInventory) player.getComponent(TypeComponent.INVENTORY)).getNumPotion(TypePotion.MANA);
		int sPot = ((BaseInventory) player.getComponent(TypeComponent.INVENTORY)).getNumPotion(TypePotion.SPEED);
		int kPot = ((BaseInventory) player.getComponent(TypeComponent.INVENTORY)).getNumPotion(TypePotion.KNOCKBACK);
		r.draw(Art.getImage("Numbers"), new Vector2(26, ShootEmUp.height - 55), size, 0.0f, new Vector2(hPot, 1),
				maxTex);
		r.draw(Art.getImage("Numbers"), new Vector2(70, ShootEmUp.height - 55), size, 0.0f, new Vector2(mPot, 1),
				maxTex);
		r.draw(Art.getImage("Numbers"), new Vector2(114, ShootEmUp.height - 55), size, 0.0f, new Vector2(sPot, 1),
				maxTex);
		r.draw(Art.getImage("Numbers"), new Vector2(159, ShootEmUp.height - 55), size, 0.0f, new Vector2(kPot, 1),
				maxTex);
	}

	@Override
	public void update() {
		for (Icon h : hudElems) {
			h.update();
		}

		fire.update();
		poison.update();
		frost.update();
		moneyCounter.update(((BaseInventory) player.getComponent(TypeComponent.INVENTORY)).getCoins());
		levelCounter.update(((BaseInventory) player.getComponent(TypeComponent.INVENTORY)).getLevel());
		waveCounter.update(ShootEmUp.currentLevel.spawner.getWave());
		livesCounter.update(((PlayerAttack) player.getComponent(TypeComponent.ATTACK)).getLives());

		int maxHealth = ((BaseAttack) player.getComponent(TypeComponent.ATTACK)).getMaxHealth();
		int health = ((BaseAttack) player.getComponent(TypeComponent.ATTACK)).getHealth();
		int maxMana = ((PlayerAttack) player.getComponent(TypeComponent.ATTACK)).getMaxMana();
		int mana = ((PlayerAttack) player.getComponent(TypeComponent.ATTACK)).getMana();
		int expBound = ((BaseInventory) player.getComponent(TypeComponent.INVENTORY)).getExpBound();
		int exp = ((BaseInventory) player.getComponent(TypeComponent.INVENTORY)).getExp();

		if (Math.max(maxHealth, maxMana) > 18.0f) {
			hudW = ((Math.max(maxHealth, maxMana) - 18.0f) * 10.0f) + Art.getImage("BarInfoTop").getWidth();
			infoBoxTop.setSize(hudW, Art.getImage("BarInfoTop").getHeight());
		}

		healthBar.setValue(health);
		healthBar.setMaxValue(maxHealth);
		manaBar.setValue(mana);
		manaBar.setMaxValue(maxMana);
		xpBar.setValue(exp);
		xpBar.setMaxValue(expBound);
		// healthBar.setMFrame((player.getMaxHealth()+1)/(player.getHealth()+1),
		// Art.getImage("BarHealth").getFHeight());
		// healthBar.setSize((((float)player.getHealth())/((float)player.getMaxHealth()))
		// * Art.getImage("BarHealth").getWidth(), healthBar.getSize().y());
	}
}
