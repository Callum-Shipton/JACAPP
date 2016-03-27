package gui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import components.TypeComponent;
import components.inventory.PlayerInventory;
import display.Art;
import main.ShootEmUp;
import object.InventoryItem;

public class Inventory {

	private int row = 0;
	private int column = 0;
	
	private final int inventoryX = 30;
	private final int inventoryY = 30;
	
	private ArrayList<InventoryItem> i;
	private List<Button> buttons = new ArrayList<Button>();
	
	public Inventory(ArrayList<InventoryItem> i){
		this.i = i;
		addButtons();
	}
	
	private void addButtons(){
		row = 0;
        column = 0;
		
    	Iterator<InventoryItem> items = i.iterator();
        
		while(items.hasNext()){
			InventoryItem item = items.next();
			addButton(new Button(TypeButton.OTHER, item.getInventoryImage(), inventoryX + ((item.getInventoryImage().getWidth() * row)), inventoryY + (((item.getInventoryImage().getHeight()/2) * column))));
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
	
	public void render(){
		for (Button button : buttons) {
            button.render(Art.stat);
		}
	}
	

    private Button addButton(Button button) {
        buttons.add(button);
        return button;
    }
}
