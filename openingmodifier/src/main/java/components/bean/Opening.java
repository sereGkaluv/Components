package components.bean;

import components.annotations.TargetDescriptor;
import components.filter.OpeningFilter;
import components.impl.ImageEvent;
import components.impl.handlers.ImageEventHandler;
import components.impl.vetoablehelpers.IntegerVetoable;
import components.interfaces.ImageListener;
import components.pipes.SupplierPipe;
import components.util.Kernel;

import javax.media.jai.KernelJAI;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.io.StreamCorruptedException;

/**
 * Created by sereGkaluv on 02-Dec-15.
 */
public class Opening extends ImageEventHandler implements ImageListener {
    private static final String RADIUS = "radius";
    private static final String KERNEL_ACCURACY_PERCENT = "kernelAccuracyPercent";
    private static final int MIN_RADIUS_VALUE = 1;
    private static final int MIN_ACCURACY_VALUE = 0;
    private static final int MAX_ACCURACY_VALUE = 100;

    @TargetDescriptor
    private int _radius = MIN_RADIUS_VALUE;
    @TargetDescriptor
    private int _kernelAccuracyPercent = MIN_ACCURACY_VALUE;

    private KernelJAI _kernel;
    private transient ImageEvent _lastImageEvent;

    public Opening() {
        super();
    }

    public int getRadius() {
        return _radius;
    }

    public void setRadius(int radius)
    throws PropertyVetoException {
        int temp = _radius;
        fireVetoableChange(this, RADIUS, temp, radius);

        _radius = radius;
        _kernel = Kernel.getJAIKernel(_radius, getKernelAccuracyPercent());

        firePropertyChange(this, RADIUS, temp, radius);
    }

    public int getKernelAccuracyPercent() {
        return _kernelAccuracyPercent;
    }

    public void setKernelAccuracyPercent(int kernelAccuracyPercent)
    throws PropertyVetoException {
        int temp = _kernelAccuracyPercent;
        fireVetoableChange(this, KERNEL_ACCURACY_PERCENT, temp, kernelAccuracyPercent);

        _kernelAccuracyPercent = kernelAccuracyPercent;
        _kernel = Kernel.getJAIKernel(getRadius(), _kernelAccuracyPercent);

        firePropertyChange(this, KERNEL_ACCURACY_PERCENT, temp, kernelAccuracyPercent);
    }

    @Override
    @TargetDescriptor
    public void onImageEvent(ImageEvent imageEvent) {
        try {
            _lastImageEvent = imageEvent;

            OpeningFilter openingFilter = new OpeningFilter(
                new SupplierPipe<>(imageEvent),
                _kernel
            );

            ImageEvent result = openingFilter.read();

            notifyAllListeners(result);
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
            notifyAllListeners(null);
        }
    }

    @Override
    protected void reload() {
        if (_lastImageEvent != null) onImageEvent(_lastImageEvent);
    }

    @Override
    public void vetoableChange(PropertyChangeEvent evt)
    throws PropertyVetoException {
        String propertyName = evt.getPropertyName();

        if (propertyName != null) {

            switch (propertyName) {

                case RADIUS: {
                    IntegerVetoable.validate(evt, MIN_RADIUS_VALUE);
                    break;
                }

                case KERNEL_ACCURACY_PERCENT: {
                    IntegerVetoable.validate(evt, MIN_ACCURACY_VALUE, MAX_ACCURACY_VALUE);
                    break;
                }
            }
        }
    }
}
