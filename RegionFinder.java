import java.awt.*;
import java.awt.image.*;
import java.util.*;

/**
 * Code provided for PS-1
 * Region growing algorithm: finds and holds regions in an image.
 * Each region is a list of contiguous points with colors similar to a target color.
 * Dartmouth CS 10, Winter 2025
 *
 * @author Tim Pierson, Dartmouth CS10, Winter 2025, based on prior terms RegionFinder
 * @author Richmond Kwalah Tettey, CS10, 18/01/2025
 */
public class RegionFinder {
    private static final int maxColorDiff = 20;				// how similar a pixel color must be to the target color, to belong to a region
    private static final int minRegion = 50; 				// how many points in a region to be worth considering

    private BufferedImage image;                            // the image in which to find regions
    private BufferedImage recoloredImage;                   // the image with identified regions recolored

    private ArrayList<ArrayList<Point>> regions;			// a region is a list of points
    // so the identified regions are in a list of lists of points

    //this region is a list of points that represents the largest region
    private ArrayList<Point> largestRegion;

    public RegionFinder() {
        this.image = null;
    }

    public RegionFinder(BufferedImage image) {
        this.image = image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public BufferedImage getImage() {
        return image;
    }

    public BufferedImage getRecoloredImage() {
        return recoloredImage;
    }

    /**
     * Sets regions to the flood-fill regions in the image, similar enough to the trackColor.
     */


    public void findRegions(Color targetColor) {
        // TODO: YOUR CODE HERE
        BufferedImage visited = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);


        //stores valid regions with targetColor or arraylist of points of target color
         regions = new ArrayList<>();

         //stores the largest region
         largestRegion = new ArrayList<>();


        //iterate in each pixel of image to find pixels that match target color
        for(int y = 0; y < image.getHeight(); y++){

            for(int x = 0; x < image.getWidth(); x++){

                //get current color of image at position (x,y) coordinate
                Color imageColor = new Color(image.getRGB(x,y));

                if(visited.getRGB(x,y) == 0 && colorMatch(targetColor, imageColor)){

                    //create a new region
                    ArrayList<Point> newRegion = new ArrayList<>();

                    //create a new to visit list
                    ArrayList<Point> toVisit = new ArrayList<>();


                    toVisit.add(new Point(x,y));


                    while(!toVisit.isEmpty()){

                        //to get point from to toVisit arraylist to check its neighbours
                        Point p = toVisit.get(0);
                        toVisit.remove(0);


                        if (visited.getRGB((int) p.getX(), (int) p.getY()) == 0){


                            //add p to new Region
                            newRegion.add(p);
                            visited.setRGB((int) p.getX(), (int) p.getY(),1);


                            /**
                             * loop through each neighbour, check if it is
                             * not visited add is similar to targetColor,
                             * then add to toVisit list.
                             * */
                            for(int ny = Math.max(0, (int) p.getY() - 1); ny <= Math.min(image.getHeight() - 1, (int) p.getY() + 1); ny++){

                                for(int nx = Math.max(0, (int) p.getX() - 1); nx <= Math.min(image.getWidth() - 1, (int) p.getX() + 1); nx++){

                                    Color neighborColor = new Color(image.getRGB(nx,ny));

                                    if(colorMatch(targetColor, neighborColor) && visited.getRGB(nx,ny) == 0){
                                        toVisit.add(new Point(nx,ny));
                                    }

                                }
                            }
                            //end of neighbour iteration

                        }

                    }

                    if (newRegion.size() > minRegion){
                        regions.add(newRegion);

                        if(newRegion.size() > largestRegion.size()){
                            largestRegion = newRegion;
                        }
                    }
                    //end of while loop

                }

            }
        }
        //end of all pixel iterations

    }



    /**
     * Tests whether the two colors are "similar enough" (your definition, subject to the maxColorDiff threshold, which you can vary).
     */
    protected static boolean colorMatch(Color c1, Color c2) {
        // TODO: YOUR CODE HERE
        if ((Math.abs(c1.getRed() - c2.getRed()) < maxColorDiff)
            && ((Math.abs(c1.getGreen() - c2.getGreen()) < maxColorDiff)
            && (Math.abs(c1.getBlue() - c2.getBlue()) < maxColorDiff))) {
            return true;
        } else {
            return false;
        }

    };


    /**
     * Returns the largest region detected (if any region has been detected)
     */
    public ArrayList<Point> largestRegion() {
        // TODO: YOUR CODE HERE
        return largestRegion;
    }

    /**
     * Sets recoloredImage to be a copy of image,
     * but with each region a uniform random color,
     * so we can see where they are
     */
    public void recolorImage() {
        // First copy the original
        recoloredImage = new BufferedImage(image.getColorModel(), image.copyData(null), image.getColorModel().isAlphaPremultiplied(), null);
        // Now recolor the regions in it
        // TODO: YOUR CODE HERE

        for(ArrayList<Point> region : regions ) {

            for ( Point point: region){

                Color recolor = new Color(10,205,100);
                recoloredImage.setRGB((int) point.getX(),(int) point.getY(),recolor.getRGB());
            }
        }
    }

}