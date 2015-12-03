package util;

/**
 * Created by sereGkaluv on 15-Nov-15.
 */
public enum JAIOperators {
    THRESHOLD("threshold"),
    THRESHOLD_X("ThresholdX"),
    THRESHOLD_Y("ThresholdY"),
    MEDIAN("MedianFilter"),
    ERODE("erode"),
    DILATE("dilate"),
    INVERT("invert"),
    FILE_LOAD("fileload"),
    FILE_STORE("filestore");

    private final String _operatorValue;

    JAIOperators(String operatorValue) {
        _operatorValue = operatorValue;
    }

    /**
     * Returns operator in JAI friendly format.
     *
     * @return JAI operator.
     */
    public String getOperatorValue() {
        return _operatorValue;
    }
}
