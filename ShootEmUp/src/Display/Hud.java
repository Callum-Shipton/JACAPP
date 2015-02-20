package Display;

import java.util.concurrent.CopyOnWriteArrayList;

public class Hud {
	
	public CopyOnWriteArrayList<HudElement> hudElems;
	private HudElement infoBox;
	private HudElement healthBar;
	private HudElement manaBar;
	private HudElement xpBar;
	private HudElement moneyCounter;

	public Hud(){
		hudElems = new CopyOnWriteArrayList<HudElement>();
		infoBox = new HudElement(0.0f,0.0f,189.0f,110.0f, Art.infoBox);
		hudElems.add(infoBox);
		healthBar = new HudElement(10.0f, 10.0f, 170.0f, 19.0f, Art.healthBar);
		hudElems.add(healthBar);
		healthBar.update(18);
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
	}

}
