package components.filter;

import components.impl.ImageEvent;
import components.interfaces.Readable;
import components.interfaces.Writable;

import javax.media.jai.PlanarImage;
import java.awt.*;
import java.security.InvalidParameterException;

/**
 * Created by sereGkaluv on 23-Nov-15.
 */
public class ROIFilter extends EnhancedDataTransformationFilter<ImageEvent> {

    private final Rectangle _roi;

    public ROIFilter(Readable<ImageEvent> input, Writable<ImageEvent> output, Rectangle roi)
    throws InvalidParameterException {
        super(input, output);
        _roi = roi;
    }

    public ROIFilter(Readable<ImageEvent> input, Rectangle roi)
    throws InvalidParameterException {
        super(input);
        _roi = roi;
    }

    public ROIFilter(Writable<ImageEvent> output, Rectangle roi)
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
                _roi,
                image.getColorModel()
            )
        );

        //Setting shift value to event.
        return new ImageEvent(this, roiImage, (int) _roi.getX(), (int) _roi.getY());
    }
}
