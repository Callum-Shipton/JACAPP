package display;

public class Art extends ArtLoader{

	@Override
	public void loadArt() {
		addArt("Ball", new Image("/Images/Ball.png", 1, 1));
		addArt("Paddle", new Image("/Images/Paddle.png", 1, 1));
		addArt("Numbers", new Image("/Images/Numbers.png", 1, 1));
		addArt("Background", new Image("/Images/Background.png", 1, 1));
	}
}
