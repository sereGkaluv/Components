package util;

/**
 * Created by sereGkaluv on 06-Dec-15.
 */
public enum KernelOption {
    KERNEL_4_5("Kernel 4x5"),
    KERNEL_8_9("Kernel 8x9"),
    KERNEL_12_13("Kernel 12x13");

    private final String _kernelName;

    KernelOption(String kernelName) {
        _kernelName = kernelName;
    }

    public String getKernelName() {
        return _kernelName;
    }
}
