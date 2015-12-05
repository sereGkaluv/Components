package filter;

import impl.ImageEvent;
import interfaces.Readable;
import interfaces.Writable;
import util.JAIOperators;

import javax.media.jai.PlanarImage;
import java.awt.*;
import java.security.InvalidParameterException;

/**
 * Created by sereGkaluv on 23-Nov-15.
 */
public class ROIFilter extends EnhancedDataTransformationFilter<ImageEvent> {

    private final Region _roi;

    public ROIFilter(Readable<ImageEvent> input, Writable<ImageEvent> output, Region roi)
    throws InvalidParameterException {
        super(input, output);
        _roi = roi;
    }

    public ROIFilter(Readable<ImageEvent> input, Region roi)
    throws InvalidParameterException {
        super(input);
        _roi = roi;
    }

    public ROIFilter(Writable<ImageEvent> output, Region roi)
    throws InvalidParameterException {
        super(output);
        _roi = roi;
    }

    @Override
    protected ImageEvent process(ImageEvent imageEvent) {
        PlanarImage image = imageEvent.getImage();

        //Recreating a new Planar Image cropped by given _roi Rectangle.
        PlanarImage roiImage = PlanarImage.wrapRenderedImage(
            image.getAsBufferedImage(
                new Rectangle(_roi.getX(), _roi.getY(), _roi.getWidth(), _roi.getHeight()),
                image.getColorModel()
            )
        );

        //Setting shift value to planar image.
        roiImage.setProperty(JAIOperators.THRESHOLD_X.getOperatorValue(), _roi.getX());
        roiImage.setProperty(JAIOperators.THRESHOLD_Y.getOperatorValue(), _roi.getY());

        return new ImageEvent(this, image);
    }
}
