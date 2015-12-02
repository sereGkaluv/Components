import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;

/**
 * Created by sereGkaluv on 23-Nov-15.
 */
public class ImageLoaderBeanInfo extends SimpleBeanInfo {
    private static final String PROP_IMAGE_PATH = "imagePath";

    @Override
    public PropertyDescriptor[] getPropertyDescriptors() {
        try {
            PropertyDescriptor pd1 = new PropertyDescriptor(PROP_IMAGE_PATH, ImageLoader.class);

            return new PropertyDescriptor[]{pd1};
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
