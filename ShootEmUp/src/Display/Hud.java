package Display;

import java.util.concurrent.CopyOnWriteArrayList;

import Object.Player;

public class Hud {
	
	public CopyOnWriteArrayList<HudElement> hudElems;
	private HudElement infoBox;
	private HudElement healthBar;
	private HudElement manaBar;
	private HudElement xpBar;
	private HudElement moneyCounter;
	private Player player;
	private float hudW;

	public Hud(Player player){
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
		moneyCounter = new HudElement(10.0f, 85.0f, Art.BarCoin.getWidth(), Art.BarCoin.getHeight(), Art.BarCoin);
		hudElems.add(moneyCounter);
	}
	
	public void render(DPDTRenderer r) {
		for (HudElement h : hudElems){
			h.render(r);
		}
	}

	public void update() {
		hudW = (Math.max(player.getMaxHealth(), player.getMaxMana()) - 18.0f)*10.0f + 189.0f;
		infoBox.setSize(hudW, 110.0f);
		healthBar.setVal(player.getHealth());
		healthBar.setSize(player.getMaxHealth()*Art.healthBar.getWidth()/((Art.healthBar.getHeight()/Art.healthBar.getFHeight())-1), Art.healthBar.getHeight()/Art.healthBar.getFHeight());
		healthBar.setMFrame(18.0f/player.getMaxHealth(), Art.healthBar.getFHeight() );
		manaBar.setVal(player.getMana());
		manaBar.setSize(player.getMaxMana()*Art.manaBar.getWidth()/((Art.manaBar.getHeight()/Art.manaBar.getFHeight())-1), Art.manaBar.getHeight()/Art.manaBar.getFHeight());
		manaBar.setMFrame(18.0f/player.getMaxMana(), Art.manaBar.getFHeight() );
		//healthBar.setMFrame((player.getMaxHealth()+1)/(player.getHealth()+1), Art.healthBar.getFHeight());
		//healthBar.setSize((((float)player.getHealth())/((float)player.getMaxHealth())) * Art.healthBar.getWidth(), healthBar.getSize().y());
	}
}
