package filter;

import impl.ImageEvent;
import interfaces.Writable;
import interfaces.Readable;
import util.JAIOperators;

import javax.media.jai.JAI;
import javax.media.jai.PlanarImage;
import javax.media.jai.operator.MedianFilterDescriptor;
import java.awt.image.renderable.ParameterBlock;
import java.security.InvalidParameterException;

/**
 * Created by sereGkaluv on 15-Nov-15.
 */
public class MedianFilter extends EnhancedDataTransformationFilter<ImageEvent> {

    private final int _maskSize;

    public MedianFilter(Readable<ImageEvent> input, Writable<ImageEvent> output, int maskSize)
    throws InvalidParameterException {
        super(input, output);
        _maskSize = maskSize;
    }

    public MedianFilter(Readable<ImageEvent> input, int maskSize)
    throws InvalidParameterException {
        super(input);
        _maskSize = maskSize;
    }

    public MedianFilter(Writable<ImageEvent> output, int maskSize)
    throws InvalidParameterException {
        super(output);
        _maskSize = maskSize;
    }

    @Override
    protected ImageEvent process(ImageEvent imageEvent) {
        ParameterBlock pb = prepareParameterBlock(imageEvent.getImage(), _maskSize);

        //Creating a new Planar Image according to parameter block
        PlanarImage newImage = JAI.create(
            JAIOperators.MEDIAN.getOperatorValue(),
            pb
        );

        //Coping image properties.
        copyImageProperties(newImage, imageEvent.getImage());

        //Returning new event.
        return new ImageEvent(this, newImage);
    }

    /**
     * Prepares parameter block.
     *
     * @param image source image
     * @param maskSize size of the mask that will be used by Median Filter
     * @return New instance of prepared parameter block
     */
    private ParameterBlock prepareParameterBlock(PlanarImage image, int maskSize) {
        return new ParameterBlock()
            .add(MedianFilterDescriptor.MEDIAN_MASK_PLUS)
            .add(maskSize)
            .addSource(image);
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
