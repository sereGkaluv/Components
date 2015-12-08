package components.filter;

import components.impl.ImageEvent;
import components.interfaces.Readable;
import components.interfaces.Writable;
import components.util.JAIOperators;

import javax.media.jai.JAI;
import javax.media.jai.KernelJAI;
import javax.media.jai.PlanarImage;
import java.awt.image.renderable.ParameterBlock;
import java.security.InvalidParameterException;


/**
 * Created by f00 on 03.12.15.
 */
public class DilateFilter  extends EnhancedDataTransformationFilter<ImageEvent> {

    private final KernelJAI _kernel;

    public DilateFilter(Readable<ImageEvent> input, KernelJAI kernel)
    throws InvalidParameterException {
        super(input);
        _kernel = kernel;
    }

    public DilateFilter(Readable<ImageEvent> input, Writable<ImageEvent> output, KernelJAI kernel)
    throws InvalidParameterException {
        super(input, output);
        _kernel = kernel;
    }

    public DilateFilter(Writable<ImageEvent> output, KernelJAI kernel)
    throws InvalidParameterException {
        super(output);
        _kernel = kernel;
    }

    @Override
    protected ImageEvent process(ImageEvent imageEvent) {
        //Dilating source image.
        PlanarImage dilatedImage = performTransformationStep(JAIOperators.DILATE, imageEvent.getImage());

        //Returning new event.
        return new ImageEvent(this, dilatedImage, imageEvent.getShiftX(), imageEvent.getShiftY());
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
}
