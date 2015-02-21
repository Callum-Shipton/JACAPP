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

	public Hud(Player player){
		this.player = player;
		
		hudElems = new CopyOnWriteArrayList<HudElement>();
		infoBox = new HudElement(0.0f,0.0f,189.0f,110.0f, Art.infoBox);
		hudElems.add(infoBox);
		healthBar = new HudElement(10.0f, 10.0f, 170.0f, 19.0f, Art.healthBar);
		hudElems.add(healthBar);
		healthBar.setVal(18);
		healthBar.setMFrame(player.getMaxHealth()/player.getHealth(), Art.healthBar.getFHeight() );
		manaBar = new HudElement(10.0f, 35.0f, 170.0f, 19.0f, Art.manaBar);
		hudElems.add(manaBar);
		manaBar.update(18);
		xpBar = new HudElement(10.0f, 60.0f, 170.0f, 19.0f, Art.xpBar);
		hudElems.add(xpBar);
		moneyCounter = new HudElement(10.0f, 85.0f, 18.0f, 18.0f, Art.BarCoin);
		hudElems.add(moneyCounter);
	}
	
	public void render(DPDTRenderer r) {
		for (HudElement h : hudElems){
			h.render(r);
		}
	}

	public void update() {
		healthBar.setVal(player.getHealth()-1);
	//	healthBar.setMFrame((player.getMaxHealth()+1)/(player.getHealth()+1), Art.healthBar.getFHeight());
		//healthBar.setSize((((float)player.getHealth())/((float)player.getMaxHealth())) * Art.healthBar.getWidth(), healthBar.getSize().y());
	}

}
