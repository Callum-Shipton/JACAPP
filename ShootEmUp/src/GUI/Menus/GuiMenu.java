package GUI.Menus;

import java.util.*;

import Display.Art;
import Display.DPDTRenderer;
import GUI.Button;
import GUI.GuiComponent;
import Main.ShootEmUp;

public abstract class GuiMenu extends GuiComponent {

    protected List<Button> buttons = new ArrayList<Button>();
    protected DPDTRenderer r = new DPDTRenderer(Art.ShaderStat);

    protected Button addButton(Button button) {
        buttons.add(button);
        return button;
    }

    public void render() {
        super.render();

        for (Button button : buttons) {
            button.render();
        }
    }

    public void update() {
        super.update();

        for (Button button : buttons) {
            button.update();
        }
    }

    public void buttonPressed(Button button) {
    	
    }
    
    protected void popMenu() {
    	ShootEmUp.menuStack.pop();
    }

}
