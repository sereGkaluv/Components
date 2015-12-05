package filter;

import impl.ImageEvent;
import interfaces.Readable;
import interfaces.Writable;
import util.JAIOperators;
import util.Kernel;

import javax.media.jai.JAI;
import javax.media.jai.KernelJAI;
import javax.media.jai.PlanarImage;
import java.awt.image.renderable.ParameterBlock;
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
        //Dilating and Eroding (Closing) source image.
        PlanarImage dilatedImage = performTransformationStep(JAIOperators.DILATE, imageEvent.getImage());
        PlanarImage erodedImage = performTransformationStep(JAIOperators.ERODE, dilatedImage);

        //Coping image properties.
        copyImageProperties(erodedImage, imageEvent.getImage());

        //Returning new event.
        return new ImageEvent(this, erodedImage);
    }

    /**
     * Applies given JAIOperator to given image.
     *
     * @param operator JAIOperator to be applied to image.
     * @param image Image that will be transformed by given JAIOperator.
     * @return Transformed Planar image
     */
    private PlanarImage performTransformationStep(JAIOperators operator, PlanarImage image) {
        //Transforming image according to the given JAI operator.
        return JAI.create(
            operator.getOperatorValue(),
            new ParameterBlock().add(_kernel).addSource(image)
        );
    }

    /**
     * Copies all the parameters to new image from source .
     *
     * @param newImage image to which properties will be copied.
     * @param sourceImage image from which properties will be copied.
     */
    private void copyImageProperties(PlanarImage newImage, PlanarImage sourceImage) {
        if (sourceImage.getProperty(JAIOperators.THRESHOLD_X.getOperatorValue()) != null) {
            newImage.setProperty(
                JAIOperators.THRESHOLD_X.getOperatorValue(),
                sourceImage.getProperty(JAIOperators.THRESHOLD_X.getOperatorValue())
            );
        }

        if (sourceImage.getProperty(JAIOperators.THRESHOLD_Y.getOperatorValue()) != null) {
            newImage.setProperty(
                JAIOperators.THRESHOLD_Y.getOperatorValue(),
                sourceImage.getProperty(JAIOperators.THRESHOLD_Y.getOperatorValue())
            );
        }
    }
}