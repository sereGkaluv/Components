package util;

import java.beans.PropertyEditorSupport;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by sereGkaluv on 06-Dec-15.
 */
public class KernelOptionsSupport extends PropertyEditorSupport {

    @Override
    public String[] getTags() {
        List<String> options = new LinkedList<>();

        for (KernelOption kernelOption : KernelOption.values()) {
            options.add(kernelOption.getKernelName());
        }
        return (String[]) options.toArray();
    }
}
