package bean;

import annotations.TargetDescriptor;
import filter.CalcCentroidsFilter;
import impl.ImageEvent;
import interfaces.ImageListener;
import pipes.SupplierPipe;
import util.Coordinate;

import java.awt.*;
import java.beans.*;
import java.io.File;
import java.io.IOException;
import java.io.StreamCorruptedException;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

/**
 * Created by sereGkaluv on 23-Nov-15.
 */
public class CalcCentroids extends TextArea
implements ImageListener, VetoableChangeListener, PropertyChangeListener {
    private static final String WIDTH = "width";
    private static final String HEIGHT = "height";
    private static final String ACCURACY = "accuracy";
    private static final String CENTROIDS_FILE_PATH = "centroidsFilePath";
    private static final String RESULT_FAILED = "FAILED.";
    private static final int MIN_SIZE_VALUE = 0;
    private static final int MIN_ACCURACY_VALUE = 1;

    private final PropertyChangeSupport _pcs = new PropertyChangeSupport(this);
    private final VetoableChangeSupport _vcs = new VetoableChangeSupport(this);

    @TargetDescriptor
    private int _width = 150;
    @TargetDescriptor
    private int _height = 300;
    @TargetDescriptor
    private int _accuracy = 3;
    @TargetDescriptor
    private String _centroidsFilePath = "";

    private transient ImageEvent _lastImageEvent;

    public CalcCentroids() {
        setColumns(1);
        setSize(getWidth(), getHeight());

        _pcs.addPropertyChangeListener(this);
        _vcs.addVetoableChangeListener(this);
    }

    @Override
    public int getWidth() {
        return _width;
    }

    public void setWidth(int width)
    throws PropertyVetoException {
        int temp = _width;
        fireVetoableChange(this, WIDTH, temp, width);

        _width = width;
        firePropertyChange(this, WIDTH, temp, width);
    }

    @Override
    public int getHeight() {
        return _height;
    }

    public void setHeight(int height)
    throws PropertyVetoException {
        int temp = _height;
        fireVetoableChange(this, HEIGHT, temp, height);

        _height = height;
        firePropertyChange(this, HEIGHT, temp, height);
    }

    public int getAccuracy() {
        return _accuracy;
    }

    public void setAccuracy(int accuracy)
    throws PropertyVetoException {
        int temp = _accuracy;
        fireVetoableChange(this, ACCURACY, temp, accuracy);

        _accuracy = accuracy;
        firePropertyChange(this, ACCURACY, temp, accuracy);
    }

    public String getCentroidsFilePath() {
        return _centroidsFilePath;
    }

    public void setCentroidsFilePath(String centroidsFilePath)
    throws PropertyVetoException {
        String temp = _centroidsFilePath;
        fireVetoableChange(this, CENTROIDS_FILE_PATH, temp, centroidsFilePath);

        _centroidsFilePath = centroidsFilePath;
        firePropertyChange(this, CENTROIDS_FILE_PATH, temp, centroidsFilePath);
    }

    @Override
    @TargetDescriptor
    public void onImageEvent(ImageEvent imageEvent) {
        try {
            _lastImageEvent = imageEvent;

            CalcCentroidsFilter centroidsFilter = new CalcCentroidsFilter(
                new SupplierPipe<>(imageEvent)
            );

            List<Coordinate> coordinateList = centroidsFilter.read();
            if (coordinateList != null && !coordinateList.isEmpty()) {

                String results = prepareResults(coordinateList, loadShouldList(getCentroidsFilePath()));
                setText(results);

            } else {

                setText(RESULT_FAILED);
            }

            repaint();

        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        }
    }

    public String prepareResults(List<Coordinate> isSolderingPlaces, List<Coordinate> shouldSolderingPlaces)
    throws StreamCorruptedException {

        if (isValidList(isSolderingPlaces) && isValidList(shouldSolderingPlaces)) {

            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < shouldSolderingPlaces.size(); ++i) {

                Coordinate should = shouldSolderingPlaces.get(i);

                //Check if 'is' list is big enough according to should list.
                if (i < isSolderingPlaces.size()) {
                    Coordinate is = isSolderingPlaces.get(i);

                    //Checking if 'is' value is in 'should' accuracy range.
                    if (isInAccuracyRange(is, should)) {

                        sb.append("PASSED. Soldering place #");
                        sb.append(i);
                        sb.append(" is in the accuracy range.\r\n");

                    } else {
                        sb.append("FAILED. Soldering place #");
                        sb.append(i);
                        sb.append(" is out of the accuracy range.\r\n");
                    }

                    sb.append(getCoordinatesString(is, should));

                } else {
                    sb.append("FAILED. Soldering place #");
                    sb.append(i);
                    sb.append(" was not detected.\r\n\r\n");
                }
            }

            return sb.toString();
        }

        return RESULT_FAILED;
    }

    /**
     * Loads should list from the property file.
     *
     * @param centroidsFilePath path to the property file.
     * @return list with should coordinates.
     */
    private List<Coordinate> loadShouldList(String centroidsFilePath) {
        try {
            List<Coordinate> shouldList = new LinkedList<>();

            Properties coordinates = new Properties();
            coordinates.load(getClass().getClassLoader().getResourceAsStream(centroidsFilePath));

            Enumeration<?> c = coordinates.propertyNames();
            while (c.hasMoreElements()) {
                String coordinate = (String) c.nextElement();
                String[] position = coordinates.getProperty(coordinate).split(":");

                if (position.length == 2) {
                    int x = Integer.getInteger(position[0]);
                    int y = Integer.getInteger(position[1]);

                    shouldList.add(new Coordinate(x, y));
                }
            }

            return shouldList;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Checks if list is null and empty.
     *
     * @param list calculated coordinate.
     * @return true if is not null and is not empty, otherwise false.
     */
    private <T> boolean isValidList(List<T> list) {
        return list != null && list.isEmpty();
    }

    /**
     * Compares two Coordinates.
     *
     * @param is calculated coordinate.
     * @param should expected value for this coordinate.
     * @return true if 'is coordinate' present inside or on accuracy border,
     *         false if it lays elsewhere.
     */
    private boolean isInAccuracyRange(Coordinate is, Coordinate should) {
        boolean isInRangeX = Math.abs(should._x - is._x) <= getAccuracy();
        boolean isInRangeY = Math.abs(should._y - is._y) <= getAccuracy();

        return isInRangeX && isInRangeY;
    }

    /**
     * Prepares Coordinate comparison string.
     *
     * @param is calculated coordinate.
     * @param should expected value for this coordinate.
     * @return Coordinate comparison string
     */
    private String getCoordinatesString(Coordinate is, Coordinate should) {
        return "INFO. Expected [" + should._x + ", " + should._y + "] | Received [" + is._x + ", " + is._y + "]\r\n\r\n";
    }

    protected void reload() {
        if (_lastImageEvent != null) onImageEvent(_lastImageEvent);
    }

    protected void firePropertyChange(
        Object source,
        String propertyName,
        Object oldValue,
        Object newValue
    ) {

        _pcs.firePropertyChange(
            new PropertyChangeEvent(source, propertyName, oldValue, newValue)
        );
    }

    protected void fireVetoableChange(
        Object source,
        String propertyName,
        Object oldValue,
        Object newValue
    ) throws PropertyVetoException {

        _vcs.fireVetoableChange(
                new PropertyChangeEvent(source, propertyName, oldValue, newValue)
        );
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        Object source = evt.getSource();

        if (source instanceof CalcCentroids) {
            ((CalcCentroids) source).reload();
        }
    }

    @Override
    public void vetoableChange(PropertyChangeEvent evt) throws PropertyVetoException {
        String propertyName = evt.getPropertyName();

        if (propertyName != null) {

            switch (propertyName) {

                case WIDTH: {
                    Integer newWidth = (Integer) evt.getNewValue();

                    if (newWidth == null) {
                        String msg = "Width should not be null.";
                        throw new PropertyVetoException(msg, evt);
                    }

                    int width = newWidth;

                    if (width < MIN_SIZE_VALUE) {
                        String msg = "Width size should be > " + MIN_SIZE_VALUE + ".";
                        throw new PropertyVetoException(msg, evt);
                    }

                    break;
                }

                case HEIGHT: {
                    Integer newHeight = (Integer) evt.getNewValue();

                    if (newHeight == null) {
                        String msg = "Height should not be null.";
                        throw new PropertyVetoException(msg, evt);
                    }

                    int height = newHeight;

                    if (height < MIN_SIZE_VALUE) {
                        String msg = "Height size should be > " + MIN_SIZE_VALUE + ".";
                        throw new PropertyVetoException(msg, evt);
                    }

                    break;
                }

                case ACCURACY: {
                    Integer newAccuracy = (Integer) evt.getNewValue();

                    if (newAccuracy == null) {
                        String msg = "Accuracy should not be null.";
                        throw new PropertyVetoException(msg, evt);
                    }

                    int accuracy = newAccuracy;

                    if (accuracy < MIN_ACCURACY_VALUE) {
                        String msg = "Accuracy size should be > " + MIN_ACCURACY_VALUE + ".";
                        throw new PropertyVetoException(msg, evt);
                    }

                    break;
                }

                case CENTROIDS_FILE_PATH: {
                    String newPath = (String) evt.getNewValue();

                    if (newPath == null || newPath.trim().isEmpty()) {
                        String msg = "Centroids path should not be empty.";
                        throw new PropertyVetoException(msg, evt);
                    }

                    File file = new File(newPath);
                    if(!file.exists() || file.isDirectory()) {
                        String msg = "Centroids path should target existing file.";
                        throw new PropertyVetoException(msg, evt);
                    }

                    break;
                }
            }
        }
    }
}
