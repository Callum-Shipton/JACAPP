package gui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import components.TypeComponent;
import components.inventory.PlayerInventory;
import display.Art;
import display.DPDTRenderer;
import main.ShootEmUp;
import object.InventoryItem;

public class Inventory extends MenuItem{

	private int row = 0;
	private int column = 0;
	
	private ArrayList<InventoryItem> i;
	private List<Button> buttons = new ArrayList<Button>();
	
	public Inventory(int x, int y, ArrayList<InventoryItem> i){
		super(x, y);
		this.i = i;
		addButtons();
	}
	
	private void addButtons(){
		row = 0;
        column = 0;
		
    	Iterator<InventoryItem> items = i.iterator();
        
		while(items.hasNext()){
			InventoryItem item = items.next();
			addButton(new Button(TypeButton.OTHER, item.getInventoryImage(), x + ((item.getInventoryImage().getWidth() * row)), y + (((item.getInventoryImage().getHeight()/2) * column))));
			row++;
			if(row > 10){
				row = 0;
				column++;
			}
		}
	}
	
	public void update(){
		for (Button button : buttons) {
            button.update();
            if (button.hasClicked()){
            	ButtonHandler.selectButton(button.getType());
            	if(button.getType() != TypeButton.OTHER){
            		button.postAction();
            	}
            }
        }
		Iterator<Button> Buttons = buttons.iterator();
    	Button itemButton;
    	boolean change = false;
    	int position = 0;
		while(Buttons.hasNext()){
			itemButton = Buttons.next();
			if(itemButton.hasClicked()){
				((PlayerInventory)(ShootEmUp.currentLevel.getPlayer().getComponent(TypeComponent.INVENTORY))).equipItem(position);
				itemButton.postAction();
				Buttons.remove();
				change = true;
			}

			position++;
		}
		
		if(change == true){
			buttons.clear();
			addButtons();
		}
		
	}
	
	public void render(DPDTRenderer d){
		for (Button button : buttons) {
            button.render(Art.stat);
		}
	}
	

    private Button addButton(Button button) {
        buttons.add(button);
        return button;
    }
}
