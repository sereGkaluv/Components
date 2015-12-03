package filter;

import bean.Kernel;
import generic.EnhancedDataTransformationFilter;
import impl.ImageEvent;
import interfaces.Readable;
import interfaces.Writable;
import util.JAIOperators;

import javax.media.jai.JAI;
import javax.media.jai.KernelJAI;
import javax.media.jai.PlanarImage;
import java.awt.image.BufferedImage;
import java.awt.image.renderable.ParameterBlock;
import java.security.InvalidParameterException;

/**
 * Created by sereGkaluv on 15-Nov-15.
 */
public class OpeningFilter extends EnhancedDataTransformationFilter<ImageEvent> {

    private final KernelJAI _kernel;

    public OpeningFilter(Readable<ImageEvent> input, Writable<ImageEvent> output, Kernel kernel)
    throws InvalidParameterException {
        super(input, output);
        _kernel = kernel.getJAIKernel();
    }

    public OpeningFilter(Readable<ImageEvent> input, Kernel kernel)
    throws InvalidParameterException {
        super(input);
        _kernel = kernel.getJAIKernel();
    }

    public OpeningFilter(Writable<ImageEvent> output, Kernel kernel)
    throws InvalidParameterException {
        super(output);
        _kernel = kernel.getJAIKernel();
    }

    @Override
    protected ImageEvent process(ImageEvent image) {
        //Eroding and Dilating (Opening) source image.
        PlanarImage erodedImage = performTransformationStep(JAIOperators.ERODE, image.getImage());
        PlanarImage dilatedImage = performTransformationStep(JAIOperators.DILATE, erodedImage.getAsBufferedImage());

        //Returning new event.
        return new ImageEvent(this, dilatedImage.getAsBufferedImage());
    }

    /**
     * Applies given JAIOperator to given image.
     *
     * @param operator JAIOperator to be applied to image.
     * @param image Image that will be transformed by given JAIOperator.
     * @return Transformed Planar image
     */
    private PlanarImage performTransformationStep(JAIOperators operator, BufferedImage image) {
        //Transforming image according to the given JAI operator.
        return JAI.create(
             operator.getOperatorValue(),
             new ParameterBlock().add(_kernel).addSource(image)
        );
    }
}
