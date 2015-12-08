package components.util;

import javax.media.jai.KernelJAI;

/**
 * Created by sereGkaluv on 03-Dec-15.
 */
public class Kernel {

    private Kernel() {
    }

    public static KernelJAI getJAIKernel(int radius, int kernelAccuracyPercent) {
        int size = radius + radius - 1;
        float[] matrix = new float[size * size];

        double smoothness = kernelAccuracyPercent / 100.0;
        double allowedLevel = (radius - 1) + smoothness;

        for (int i=0; i < matrix.length; ++i) {
            int x = (i) % (size);
            int y = (i) / (size);

            int width = Math.abs((x - radius) + 1);
            int height = Math.abs((y - radius) + 1);

            double hypo = Math.sqrt(Math.pow(width, 2) + Math.pow(height, 2));

            if (hypo <= allowedLevel) matrix[i] = 1;
        }

        return new KernelJAI(size, size, matrix);
    }
}
