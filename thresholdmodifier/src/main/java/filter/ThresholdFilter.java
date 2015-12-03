package filter;

import generic.EnhancedDataTransformationFilter;
import impl.ImageEvent;
import interfaces.Readable;
import interfaces.Writable;
import util.JAIOperators;

import javax.media.jai.JAI;
import javax.media.jai.PlanarImage;
import java.awt.image.BufferedImage;
import java.awt.image.renderable.ParameterBlock;
import java.security.InvalidParameterException;

/**
 * Created by sereGkaluv on 23-Nov-15.
 */
public class ThresholdFilter extends EnhancedDataTransformationFilter<ImageEvent> {

    private final double[][] _parameters;

    public ThresholdFilter(Readable<ImageEvent> input, Writable<ImageEvent> output, double[]... parameters)
    throws InvalidParameterException {
        super(input, output);
        _parameters = parameters;
    }

    public ThresholdFilter(Readable<ImageEvent> input, double[]... parameters)
    throws InvalidParameterException {
        super(input);
        _parameters = parameters;
    }

    public ThresholdFilter(Writable<ImageEvent> output, double[]... parameters)
    throws InvalidParameterException {
        super(output);
        _parameters = parameters;
    }

    @Override
    protected ImageEvent process(ImageEvent imageEvent) {
        ParameterBlock pb = prepareParameterBlock(imageEvent.getImage(), _parameters);

        //Creating a new Planar Image according to parameter block, applying JAI Operator (filter).
        PlanarImage newImage = JAI.create(
            JAIOperators.THRESHOLD.getOperatorValue(),
            pb
        );

        //Returning new event.
        return new ImageEvent(this, newImage.getAsBufferedImage());
    }

    /**
     * Prepares parameter block.
     *
     * @param image source image
     * @param parameters array of double parameters. 1 element - color from, 2 element - color to,
     *                   3 element - color value that will replace all colors in range between 1 and 2 element
     * @return New instance of prepared parameter block
     */
    private ParameterBlock prepareParameterBlock(BufferedImage image, double[]... parameters) {
        ParameterBlock pb = new ParameterBlock();

        for (double[] parameterGroup : parameters) {
            pb.add(parameterGroup);
        }
        return pb.addSource(image);
    }
}
