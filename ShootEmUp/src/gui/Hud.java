package gui;

import java.util.concurrent.CopyOnWriteArrayList;

import components.TypeComponent;
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

	private Icon fire;
	private Icon frost;
	private Icon poison;

	private Icon infoBoxBottom;

	private float hudW;
	
	BaseInventory PI;
	PlayerAttack PA;
	BasicMovement PM;

	public Hud(Entity player, float x, float y) {
		super(x, y);
		
		PI =  player.getComponent(TypeComponent.INVENTORY);
		PA = player.getComponent(TypeComponent.ATTACK);
		PM = player.getComponent(TypeComponent.MOVEMENT);
		
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
				PI.getCoins(), 0.5f);
		hudElems.add(moneyCounter);
		levelCounter = new Counter(90.0f, 82.0f, Art.getImage("LevelIcon"), false,
				PI.getLevel(), 0.5f);
		hudElems.add(levelCounter);
		waveCounter = new Counter(170.0f, 82.0f, Art.getImage("WaveIcon"), false,
				ShootEmUp.getCurrentLevel().getSpawner().getWave(), 0.5f);
		hudElems.add(waveCounter);
		livesCounter = new Counter(250.0f, 82.0f, Art.getImage("LivesIcon"), false,
				PA.getLives(), 0.5f);
		hudElems.add(livesCounter);
		fire = new Icon(0.0f, 100.0f, Art.getImage("Fire"), false, 1f);
		poison = new Icon(0.0f, 120.0f, Art.getImage("Poison"), false, 1f);
		frost = new Icon(0.0f, 140.0f, Art.getImage("Frost"), false, 1f);

		// Potions bar
		infoBoxBottom = new Icon(0.0f, ShootEmUp.getDisplay().getHeight() - Art.getImage("BarInfoBottom").getHeight(),
				Art.getImage("BarInfoBottom"), false, 1f);
		hudElems.add(infoBoxBottom);
	}

	@Override
	public void render(DPDTRenderer r) {

		for (Icon h : hudElems) {
			h.render(r);
		}

		if (PA.isFire()) {
			fire.render(r);
		}
		if (PA.isPoison()) {
			poison.render(r);
		}
		if (PM.isFrost()) {
			frost.render(r);
		}

		Vector2 size = new Vector2(16, 16);
		Vector2 maxTex = new Vector2(10, 1);
		
		int hPot = PI.getNumPotion(TypePotion.HEALTH);
		int mPot = PI.getNumPotion(TypePotion.MANA);
		int sPot = PI.getNumPotion(TypePotion.SPEED);
		int kPot = PI.getNumPotion(TypePotion.KNOCKBACK);
		
		r.draw(Art.getImage("Numbers"), new Vector2(26, ShootEmUp.getDisplay().getHeight() - 55), size, 0.0f, new Vector2(hPot, 1),
				maxTex);
		r.draw(Art.getImage("Numbers"), new Vector2(70, ShootEmUp.getDisplay().getHeight() - 55), size, 0.0f, new Vector2(mPot, 1),
				maxTex);
		r.draw(Art.getImage("Numbers"), new Vector2(114, ShootEmUp.getDisplay().getHeight() - 55), size, 0.0f, new Vector2(sPot, 1),
				maxTex);
		r.draw(Art.getImage("Numbers"), new Vector2(159, ShootEmUp.getDisplay().getHeight() - 55), size, 0.0f, new Vector2(kPot, 1),
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
		moneyCounter.update(PI.getCoins());
		levelCounter.update(PI.getLevel());
		waveCounter.update(ShootEmUp.getCurrentLevel().getSpawner().getWave());
		livesCounter.update(PA.getLives());
		
		int maxHealth = PA.getMaxHealth();
		int health = PA.getHealth();
		int maxMana = PA.getMaxMana();
		int mana = PA.getMana();
		int expBound = PI.getExpBound();
		int exp = PI.getExp();

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
	}
}
