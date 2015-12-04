package filter;

import impl.ImageEvent;
import interfaces.Readable;
import interfaces.Writable;
import util.JAIOperators;
import util.Kernel;

import javax.media.jai.KernelJAI;
import javax.media.jai.PlanarImage;
import java.security.InvalidParameterException;

/**
 * Created by sereGkaluv on 04-Dec-15.
 */
public class ClosingFilter extends EnhancedDataTransformationFilter<ImageEvent> {

    private final KernelJAI _kernel;

    public ClosingFilter(Readable<ImageEvent> input, Writable<ImageEvent> output, Kernel kernel)
    throws InvalidParameterException {
        super(input, output);
        _kernel = kernel.getJAIKernel();
    }

    public ClosingFilter(Readable<ImageEvent> input, Kernel kernel)
    throws InvalidParameterException {
        super(input);
        _kernel = kernel.getJAIKernel();
    }

    public ClosingFilter(Writable<ImageEvent> output, Kernel kernel)
    throws InvalidParameterException {
        super(output);
        _kernel = kernel.getJAIKernel();
    }

    @Override
    public ImageEvent process(ImageEvent imageEvent) {
        //Closing image.
        PlanarImage newImage = imageEvent.getImage();

        //Coping image properties.
        copyImageProperties(newImage, imageEvent.getImage());

        //Returning new event.
        return new ImageEvent(this, newImage);
    }

    /**
     * Copies all the parameters to new image from source .
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