package gui;

import java.util.concurrent.CopyOnWriteArrayList;

import org.joml.Vector2f;

import components.TypeComponent;
import components.attack.PlayerAttack;
import components.inventory.BaseInventory;
import components.inventory.TypePotion;
import components.movement.GroundMovement;
import display.ArtLoader;
import display.DPDTRenderer;
import display.BaseRenderSystem;
import entity.Entity;
import loop.GameLoop;

public class Hud extends GuiComponent {

	private final CopyOnWriteArrayList<Icon> hudElems;
	private final Icon infoBoxTop;
	private final HudBar healthBar;
	private final HudBar manaBar;
	private final HudBar xpBar;
	private final Counter moneyCounter;
	private final Counter levelCounter;
	private final Counter livesCounter;

	private final Icon fire;
	private final Icon frost;
	private final Icon poison;

	private Icon infoBoxBottom;

	private final BaseInventory playerInventory;
	private final PlayerAttack playerAttack;
	private final GroundMovement playerMovement;

	private static final String BAR_INFO_TOP = "BarInfoTop";
	private static final String BAR_INFO_BOTTOM = "BarInfoBottom";

	public Hud(Entity player, float x, float y) {
		super(x, y);

		playerInventory = player.getComponent(TypeComponent.INVENTORY);
		playerAttack = player.getComponent(TypeComponent.ATTACK);
		playerMovement = player.getComponent(TypeComponent.MOVEMENT);

		hudElems = new CopyOnWriteArrayList<>();
		infoBoxTop = new Icon(0.0f, 0.0f, ArtLoader.getImage(BAR_INFO_TOP), false, 1f);
		hudElems.add(infoBoxTop);
		healthBar = new HudBar(10.0f, 10.0f, ArtLoader.getImage("Bars"), 1, 1f);
		hudElems.add(healthBar);
		manaBar = new HudBar(10.0f, 35.0f, ArtLoader.getImage("Bars"), 2, 1f);
		hudElems.add(manaBar);
		xpBar = new HudBar(10.0f, 60.0f, ArtLoader.getImage("Bars"), 3, 1f);
		hudElems.add(xpBar);
		moneyCounter = new Counter(10.0f, 82.0f, ArtLoader.getImage("CoinIcon"), false, playerInventory.getCoins(),
				0.5f);
		hudElems.add(moneyCounter);
		levelCounter = new Counter(90.0f, 82.0f, ArtLoader.getImage("LevelIcon"), false,
				playerInventory.getLevel(), 0.5f);
		hudElems.add(levelCounter);
		livesCounter = new Counter(170.0f, 82.0f, ArtLoader.getImage("LivesIcon"), false, playerAttack.getLives(),
				0.5f);
		hudElems.add(livesCounter);
		fire = new Icon(0.0f, 100.0f, ArtLoader.getImage("Fire"), false, 1f);
		poison = new Icon(0.0f, 120.0f, ArtLoader.getImage("Poison"), false, 1f);
		frost = new Icon(0.0f, 140.0f, ArtLoader.getImage("Frost"), false, 1f);

		// Potions bar
		infoBoxBottom = new Icon(0.0f,
				GameLoop.getDisplay().getHeight() - ArtLoader.getImage(BAR_INFO_BOTTOM).getHeight(),
				ArtLoader.getImage(BAR_INFO_BOTTOM), false, 1f);
		hudElems.add(infoBoxBottom);
	}

	public void resetHud() {
		hudElems.remove(infoBoxBottom);
		infoBoxBottom = new Icon(0.0f,
				GameLoop.getDisplay().getHeight() - ArtLoader.getImage(BAR_INFO_BOTTOM).getHeight(),
				ArtLoader.getImage(BAR_INFO_BOTTOM), false, 1f);
		hudElems.add(infoBoxBottom);
	}

	@Override
	public void render(DPDTRenderer r) {

		for (Icon h : hudElems) {
			h.render(r);
		}

		if (playerAttack.isFire()) {
			fire.render(r);
		}
		if (playerAttack.isPoison()) {
			poison.render(r);
		}
		if (playerMovement.isFrost()) {
			frost.render(r);
		}

		Vector2f size = new Vector2f(16, 16);
		Vector2f maxTex = new Vector2f(10, 1);

		int hPot = playerInventory.getNumPotion(TypePotion.HEALTH);
		int mPot = playerInventory.getNumPotion(TypePotion.MANA);
		int sPot = playerInventory.getNumPotion(TypePotion.SPEED);
		int kPot = playerInventory.getNumPotion(TypePotion.KNOCKBACK);

		final String numbers = "Numbers";

		r.draw(ArtLoader.getImage(numbers), new Vector2f(26, GameLoop.getDisplay().getHeight() - 55), size, 0.0f,
				new Vector2f(hPot, 1), maxTex);
		r.draw(ArtLoader.getImage(numbers), new Vector2f(70, GameLoop.getDisplay().getHeight() - 55), size, 0.0f,
				new Vector2f(mPot, 1), maxTex);
		r.draw(ArtLoader.getImage(numbers), new Vector2f(114, GameLoop.getDisplay().getHeight() - 55), size, 0.0f,
				new Vector2f(sPot, 1), maxTex);
		r.draw(ArtLoader.getImage(numbers), new Vector2f(159, GameLoop.getDisplay().getHeight() - 55), size, 0.0f,
				new Vector2f(kPot, 1), maxTex);
	}

	@Override
	public void update() {
		for (Icon h : hudElems) {
			h.update();
		}

		fire.update();
		poison.update();
		frost.update();
		moneyCounter.update(playerInventory.getCoins());
		levelCounter.update(playerInventory.getLevel());
		livesCounter.update(playerAttack.getLives());

		int maxHealth = playerAttack.getMaxHealth();
		int health = playerAttack.getHealth();
		int maxMana = playerAttack.getMaxMana();
		int mana = playerAttack.getMana();
		int expBound = playerInventory.getExpBound();
		int exp = playerInventory.getExp();

		if (Math.max(maxHealth, maxMana) > 18.0f) {
			float hudW = ((Math.max(maxHealth, maxMana) - 18.0f) * 10.0f)
					+ ArtLoader.getImage(BAR_INFO_TOP).getWidth();
			infoBoxTop.setSize(hudW, ArtLoader.getImage(BAR_INFO_TOP).getHeight());
		}

		healthBar.setValue(health);
		healthBar.setMaxValue(maxHealth);
		manaBar.setValue(mana);
		manaBar.setMaxValue(maxMana);
		xpBar.setValue(exp);
		xpBar.setMaxValue(expBound);
	}
}
