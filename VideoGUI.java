import java.awt.image.BufferedImage;

public class VideoGUI  extends ImageGUI{
    protected boolean mac = true;
    private static final double scale = 0.5;
    private static final boolean mirror = true;

    public VideoGUI(String title){
        super(title);
    }


    protected BufferedImage image;


    public void handleImage(){
        setImage1(image);
    }

    public void handleMousePress(int x, int y){
        System.out.println("Got mouse" + x + "," + y);
    }

    public void handleKeyPress(char key){
        System.out.println("Key pressed: " + key);
    }


}
