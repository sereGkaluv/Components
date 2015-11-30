import filter.ForwardingFilter;
import interfaces.Readable;
import interfaces.Writable;

import java.awt.image.BufferedImage;
import java.security.InvalidParameterException;

/**
 * Created by sereGkaluv on 23-Nov-15.
 */
public class ROIFilter extends ForwardingFilter<BufferedImage> {
    public ROIFilter(Readable<BufferedImage> input, Writable<BufferedImage> output) throws InvalidParameterException {
        super(input, output);
    }

    public ROIFilter(Readable<BufferedImage> input) throws InvalidParameterException {
        super(input);
    }

    public ROIFilter(Writable<BufferedImage> output) throws InvalidParameterException {
        super(output);
    }


}
