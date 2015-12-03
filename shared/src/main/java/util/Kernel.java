package util;

import javax.media.jai.KernelJAI;

/**
 * Created by sereGkaluv on 03-Dec-15.
 */
public class Kernel {

    //TODO maybe customizer so kernel-values can be edited
    private static final int DEFAULT_KERNEL_SIZE_X = 12;
    private static final int DEFAULT_KERNEL_SIZE_Y = 13;
    private static final float[] DEFAULT_KERNEL_VALUES = {
        0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0,
        0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0,
        0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0,
        0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0,
        1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
        1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
        1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
        1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
        1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
        0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0,
        0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0,
        0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0,
        0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0,
    };

    private static final KernelJAI DEFAULT_KERNEL = new KernelJAI(
        DEFAULT_KERNEL_SIZE_X,
        DEFAULT_KERNEL_SIZE_Y,
        DEFAULT_KERNEL_VALUES
    );

    private KernelJAI _kernel;

    public Kernel() {
        _kernel = DEFAULT_KERNEL;
    }

    public KernelJAI getJAIKernel() {
        return _kernel;
    }

    public void loadNewKernel(int sizeX, int sizeY, float[] kernelValues) {
        _kernel = new KernelJAI(sizeX, sizeY, kernelValues);
    }
}
