package display;

public class Art implements ArtLoader{

	@Override
	public void loadArt() {
		ImageProcessor.addArt("Ball", new Image("/Images/Ball.png", 1, 1));
		ImageProcessor.addArt("Paddle", new Image("/Images/Paddle.png", 1, 1));
		ImageProcessor.addArt("Numbers", new Image("/Images/Numbers.png", 1, 1));
		ImageProcessor.addArt("Background", new Image("/Images/Background.png", 1, 1));
	}
}
