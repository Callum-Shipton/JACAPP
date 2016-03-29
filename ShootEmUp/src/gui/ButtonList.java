package gui;

import java.util.ArrayList;

import display.Art;

public class ButtonList extends MenuItem{
	
	ArrayList<Button> buttons = new ArrayList<Button>();
	int gap;
	int height;
	
	public ButtonList(int x ,int y, int height, int gap){
		super(x, y);
		this.gap = gap;
		this.height = height;
	}
	
	public void addButton(TypeButton type){
		buttons.add(ButtonBuilder.buildButton(type, x, y + ((gap + height) * buttons.size())));
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
	}
	
	public void render(){
		for(Button button : buttons){
			button.render(Art.stat);
		}
	}
}
