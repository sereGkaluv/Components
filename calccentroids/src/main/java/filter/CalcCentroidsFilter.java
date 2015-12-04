package filter;

import util.Coordinate;
import util.JAIOperators;
import interfaces.Readable;
import interfaces.Writable;

import javax.media.jai.PlanarImage;
import java.awt.image.Raster;
import java.io.StreamCorruptedException;
import java.security.InvalidParameterException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * This filter expects the bonding discs to be completely white: pixel value of 255 on a scale of 0..255
 * all other pixels in the image are expected to have a pixel value < 255
 * use this filter adapting eventually the package name
 */
public class CalcCentroidsFilter extends DataEnrichmentFilter<PlanarImage, List<Coordinate>> {
    private static final Set<Coordinate> CLOSED_LIST = new HashSet<>();
    private static final List<List<Coordinate>> FIGURES = new LinkedList<>();
    private static final int TARGET_COLOR = 255;

    private PlanarImage _image;

    public CalcCentroidsFilter(Readable<PlanarImage> input, Writable<List<Coordinate>> output)
    throws InvalidParameterException {
        super(input, output);
    }


    public CalcCentroidsFilter(Readable<PlanarImage> input)
    throws InvalidParameterException {
        super(input);
    }

    public CalcCentroidsFilter(Writable<List<Coordinate>> output)
    throws InvalidParameterException {
        super(output);
    }

    @Override
    protected boolean fillEntity(PlanarImage nextVal, List<Coordinate> entity) {
        if (nextVal == null) {
            try {
                sendEndSignal();
                return true;
            } catch (StreamCorruptedException e) {
                e.printStackTrace();
            }
        }

        _image = nextVal;

        entity.addAll(process(nextVal));
        return true;
    }

    @Override
    protected List<Coordinate> getNewEntityObject() {
        return new LinkedList<>();
    }

    private List<Coordinate> process(PlanarImage entity) {
        final Raster raster = entity.getAsBufferedImage().getRaster();

        for (int x = 0; x < raster.getWidth(); ++x) {
            for (int y = 0; y < raster.getHeight(); ++y) {

                if (isPixelOfColor(x, y, raster, TARGET_COLOR)) {

                    Coordinate coordinate = new Coordinate(x, y);
                    if(!CLOSED_LIST.contains(coordinate)) {
                        //If there is a not visited black pixel, save all pixels belonging to the same figure.
                        getNextFigure(coordinate, raster);
                    }
                }
            }
        }

        //Calculate the centroids of all figures.
        return calculateCentroids();
    }

    private void getNextFigure(Coordinate startCoordinate, Raster raster) {
        final List<Coordinate> figure = new LinkedList<>();

        addConnectedComponents(figure, startCoordinate, raster);
        FIGURES.add(figure);
    }

    //Deep recursion is replaced with queue processing. No more problems with stack overflow.
    private void addConnectedComponents(List<Coordinate> figure, Coordinate startCoordinate, Raster raster) {
        final LinkedList<Coordinate> queue = new LinkedList<>();
        queue.add(startCoordinate);

        while (!queue.isEmpty()) {
            final Coordinate c = queue.pollFirst();

            //Checking if current coordinate is not out bounds, and color on targeted pixel == TARGET_COLOR.
            if (isInBounds(c, raster) && isPixelOfColor(c._x, c._y, raster, TARGET_COLOR) && !CLOSED_LIST.contains(c)) {

                //Marking coordinate as visited and adding it to figure.
                CLOSED_LIST.add(c);
                figure.add(c);

                //Generating new possible coordinates for future checks.
                queue.add(new Coordinate(c._x - 1, c._y));
                queue.add(new Coordinate(c._x + 1, c._y));
                queue.add(new Coordinate(c._x, c._y - 1));
                queue.add(new Coordinate(c._x, c._y + 1));
            }
        }
    }

    private List<Coordinate> calculateCentroids() {
        final List<Coordinate> centroids = new LinkedList<>();

        for (List<Coordinate> figure : FIGURES) {

            //Finding average coordinate.
            int xA = 0;
            int yA = 0;

            for (Coordinate c : figure) {
                xA += c._x;
                yA += c._y;
            }

            xA /= figure.size();
            yA /= figure.size();

            double thresholdX = (double) _image.getProperty(JAIOperators.THRESHOLD_X.getOperatorValue());
            double thresholdY = (double) _image.getProperty(JAIOperators.THRESHOLD_Y.getOperatorValue());

            centroids.add(new Coordinate(
                xA + (int) thresholdX,
                yA + (int) thresholdY
            ));
        }

        return !centroids.isEmpty() ? centroids : null;
    }

    private boolean isInBounds(Coordinate coordinate, Raster raster) {
        return isInBound(coordinate._x, raster.getWidth()) && isInBound(coordinate._y, raster.getHeight());
    }

    private boolean isInBound(int value, int bound) {
        return value >= 0 && value < bound;
    }

    private boolean isPixelOfColor(int x, int y, Raster raster, int color) {
        return raster.getSample(x, y, 0) == color;
    }
}
