package gui;

import java.util.concurrent.CopyOnWriteArrayList;

import components.TypeComponent;
import components.attack.PlayerAttack;
import components.inventory.BaseInventory;
import components.inventory.TypePotion;
import components.movement.BasicMovement;
import display.Art;
import display.DPDTRenderer;
import main.Loop;
import math.Vector2;
import object.Entity;

public class Hud extends GuiComponent {

	private CopyOnWriteArrayList<Icon> hudElems;
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

	private BaseInventory PI;
	private PlayerAttack PA;
	private BasicMovement PM;

	public Hud(Entity player, float x, float y) {
		super(x, y);

		this.PI = player.getComponent(TypeComponent.INVENTORY);
		this.PA = player.getComponent(TypeComponent.ATTACK);
		this.PM = player.getComponent(TypeComponent.MOVEMENT);

		this.hudElems = new CopyOnWriteArrayList<Icon>();
		this.infoBoxTop = new Icon(0.0f, 0.0f, Art.getImage("BarInfoTop"), false, 1f);
		this.hudElems.add(this.infoBoxTop);
		this.healthBar = new HudBar(10.0f, 10.0f, Art.getImage("BarHealth"), 1f);
		this.hudElems.add(this.healthBar);
		this.manaBar = new HudBar(10.0f, 35.0f, Art.getImage("BarMana"), 1f);
		this.hudElems.add(this.manaBar);
		this.xpBar = new HudBar(10.0f, 60.0f, Art.getImage("BarXP"), 1f);
		this.hudElems.add(this.xpBar);
		this.moneyCounter = new Counter(10.0f, 82.0f, Art.getImage("CoinIcon"), false, this.PI.getCoins(), 0.5f);
		this.hudElems.add(this.moneyCounter);
		this.levelCounter = new Counter(90.0f, 82.0f, Art.getImage("LevelIcon"), false, this.PI.getLevel(), 0.5f);
		this.hudElems.add(this.levelCounter);
		this.waveCounter = new Counter(170.0f, 82.0f, Art.getImage("WaveIcon"), false,
				Loop.getCurrentLevel().getSpawner().getWave(), 0.5f);
		this.hudElems.add(this.waveCounter);
		this.livesCounter = new Counter(250.0f, 82.0f, Art.getImage("LivesIcon"), false, this.PA.getLives(), 0.5f);
		this.hudElems.add(this.livesCounter);
		this.fire = new Icon(0.0f, 100.0f, Art.getImage("Fire"), false, 1f);
		this.poison = new Icon(0.0f, 120.0f, Art.getImage("Poison"), false, 1f);
		this.frost = new Icon(0.0f, 140.0f, Art.getImage("Frost"), false, 1f);

		// Potions bar
		this.infoBoxBottom = new Icon(0.0f,
				Loop.getDisplay().getHeight() - Art.getImage("BarInfoBottom").getHeight(),
				Art.getImage("BarInfoBottom"), false, 1f);
		this.hudElems.add(this.infoBoxBottom);
	}

	@Override
	public void render(DPDTRenderer r) {

		for (Icon h : this.hudElems) {
			h.render(r);
		}

		if (this.PA.isFire()) {
			this.fire.render(r);
		}
		if (this.PA.isPoison()) {
			this.poison.render(r);
		}
		if (this.PM.isFrost()) {
			this.frost.render(r);
		}

		Vector2 size = new Vector2(16, 16);
		Vector2 maxTex = new Vector2(10, 1);

		int hPot = this.PI.getNumPotion(TypePotion.HEALTH);
		int mPot = this.PI.getNumPotion(TypePotion.MANA);
		int sPot = this.PI.getNumPotion(TypePotion.SPEED);
		int kPot = this.PI.getNumPotion(TypePotion.KNOCKBACK);

		r.draw(Art.getImage("Numbers"), new Vector2(26, Loop.getDisplay().getHeight() - 55), size, 0.0f,
				new Vector2(hPot, 1), maxTex);
		r.draw(Art.getImage("Numbers"), new Vector2(70, Loop.getDisplay().getHeight() - 55), size, 0.0f,
				new Vector2(mPot, 1), maxTex);
		r.draw(Art.getImage("Numbers"), new Vector2(114, Loop.getDisplay().getHeight() - 55), size, 0.0f,
				new Vector2(sPot, 1), maxTex);
		r.draw(Art.getImage("Numbers"), new Vector2(159, Loop.getDisplay().getHeight() - 55), size, 0.0f,
				new Vector2(kPot, 1), maxTex);
	}

	@Override
	public void update() {
		for (Icon h : this.hudElems) {
			h.update();
		}

		this.fire.update();
		this.poison.update();
		this.frost.update();
		this.moneyCounter.update(this.PI.getCoins());
		this.levelCounter.update(this.PI.getLevel());
		this.waveCounter.update(Loop.getCurrentLevel().getSpawner().getWave());
		this.livesCounter.update(this.PA.getLives());

		int maxHealth = this.PA.getMaxHealth();
		int health = this.PA.getHealth();
		int maxMana = this.PA.getMaxMana();
		int mana = this.PA.getMana();
		int expBound = this.PI.getExpBound();
		int exp = this.PI.getExp();

		if (Math.max(maxHealth, maxMana) > 18.0f) {
			this.hudW = ((Math.max(maxHealth, maxMana) - 18.0f) * 10.0f) + Art.getImage("BarInfoTop").getWidth();
			this.infoBoxTop.setSize(this.hudW, Art.getImage("BarInfoTop").getHeight());
		}

		this.healthBar.setValue(health);
		this.healthBar.setMaxValue(maxHealth);
		this.manaBar.setValue(mana);
		this.manaBar.setMaxValue(maxMana);
		this.xpBar.setValue(exp);
		this.xpBar.setMaxValue(expBound);
	}
}
