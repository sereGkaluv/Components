package filer;

import generic.EnhancedDataTransformationFilter;
import impl.ImageEvent;
import interfaces.Readable;
import interfaces.Writable;
import util.JAIOperators;
import util.Kernel;

import javax.media.jai.JAI;
import javax.media.jai.KernelJAI;
import javax.media.jai.PlanarImage;
import java.awt.image.BufferedImage;
import java.awt.image.renderable.ParameterBlock;
import java.security.InvalidParameterException;


/**
 * Created by f00 on 03.12.15.
 */
public class DilateFilter  extends EnhancedDataTransformationFilter<ImageEvent> {

    private final KernelJAI _kernel;

    /* constructors */
    public DilateFilter(Readable<ImageEvent> input, Kernel kernel) throws InvalidParameterException {
        super(input);
        _kernel = kernel.getJAIKernel();
    }

    public DilateFilter(Readable<ImageEvent> input, Writable<ImageEvent> output, Kernel kernel) throws InvalidParameterException {
        super(input, output);
        _kernel = kernel.getJAIKernel();
    }

    public DilateFilter(Writable<ImageEvent> output, Kernel kernel) throws InvalidParameterException {
        super(output);
        _kernel = kernel.getJAIKernel();
    }

    /**
     *  Process-method
     *
     * @param image
     *
     * @return ErodedImage
     */
    @Override
    protected ImageEvent process(ImageEvent image) {
        PlanarImage erodedImage = performTransformationStep(JAIOperators.DILATE, image.getImage());

        //Returning new event.
        return new ImageEvent(this, erodedImage.getAsBufferedImage());
    }

    /**
     * A helping method
     *
     * @param operator
     * @param image
     * @return
     */
    private PlanarImage performTransformationStep(JAIOperators operator, BufferedImage image) {
        //Transforming image according to the given JAI operator.
        return JAI.create(
                operator.getOperatorValue(),
                new ParameterBlock().add(_kernel).addSource(image)
        );
    }
}
