package filter;

import impl.ImageEvent;
import interfaces.Readable;
import interfaces.Writable;
import util.JAIOperators;

import javax.media.jai.PlanarImage;
import javax.media.jai.operator.OverlayDescriptor;
import java.security.InvalidParameterException;

/**
 * Created by sereGkaluv on 04-Dec-15.
 */
public class OverlayFilter extends DataMergeFilter<ImageEvent, ImageEvent, ImageEvent> {

    public OverlayFilter(Readable<ImageEvent> baseInput, Readable<ImageEvent> overInput, Writable<ImageEvent> output)
    throws InvalidParameterException {
        super(baseInput, overInput, output);
    }

    public OverlayFilter(Readable<ImageEvent> baseInput, Readable<ImageEvent> overInput)
    throws InvalidParameterException {
        super(baseInput, overInput);
    }

    public OverlayFilter(Writable<ImageEvent> output)
    throws InvalidParameterException {
        super(output);
    }

    @Override
    protected ImageEvent process(ImageEvent backgroundImageEvent, ImageEvent foregroundImageEvent) {

        //if can be merged
        if (backgroundImageEvent != null && foregroundImageEvent != null) {

            //Logically and two images.
            PlanarImage newImage = PlanarImage.wrapRenderedImage(
                OverlayDescriptor.create(backgroundImageEvent.getImage(), foregroundImageEvent.getImage(), null)
            );

            //Returning new event.
            return new ImageEvent(
                this,
                newImage,
                backgroundImageEvent.getShiftX(),
                backgroundImageEvent.getShiftY()
            );

        } else if (backgroundImageEvent == null) {

            //if no background -> return foreground
            return foregroundImageEvent;

        } else {

            //if no foreground -> return background
            return backgroundImageEvent;
        }
    }
}
