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
        //Logically and two images.
        PlanarImage newImage = PlanarImage.wrapRenderedImage(
            OverlayDescriptor.create(backgroundImageEvent.getImage(), foregroundImageEvent.getImage(), null)
        );

        //Coping image properties.
        copyImageProperties(newImage, foregroundImageEvent.getImage());

        //Returning new event.
        return new ImageEvent(this, newImage);
    }

    /**
     * Copies all the parameters to new image from source.
     *
     * @param newImage image to which properties will be copied.
     * @param sourceImage image from which properties will be copied.
     */
    private void copyImageProperties(PlanarImage newImage, PlanarImage sourceImage) {
        newImage.setProperty(
            JAIOperators.THRESHOLD_X.getOperatorValue(),
            sourceImage.getProperty(JAIOperators.THRESHOLD_X.getOperatorValue())
        );

        newImage.setProperty(
            JAIOperators.THRESHOLD_Y.getOperatorValue(),
            sourceImage.getProperty(JAIOperators.THRESHOLD_Y.getOperatorValue())
        );
    }
}
