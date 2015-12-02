import generic.EnhancedDataTransformationFilter;
import impl.ImageEvent;
import interfaces.Readable;
import interfaces.Writable;

import java.awt.image.BufferedImage;
import java.security.InvalidParameterException;

/**
 * Created by sereGkaluv on 23-Nov-15.
 */
public class ROIFilter extends EnhancedDataTransformationFilter<ImageEvent> {

    private final ROI _roi;

    public ROIFilter(Readable<ImageEvent> input, Writable<ImageEvent> output, ROI roi)
    throws InvalidParameterException {
        super(input, output);
        _roi = roi;
    }

    public ROIFilter(Readable<ImageEvent> input, ROI roi)
    throws InvalidParameterException {
        super(input);
        _roi = roi;
    }

    public ROIFilter(Writable<ImageEvent> output, ROI roi)
    throws InvalidParameterException {
        super(output);
        _roi = roi;
    }

    @Override
    protected ImageEvent process(ImageEvent imageEvent) {
        BufferedImage image = imageEvent.getImage();
        image = image.getSubimage(_roi.getX(), _roi.getY(), _roi.getWidth(), _roi.getHeight());

        return new ImageEvent(this, image);
    }
}
