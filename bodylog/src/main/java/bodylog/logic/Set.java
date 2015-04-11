package bodylog.logic;

import java.util.Arrays;

/**
 * The last component in the logic hierarchy. Contains a list of values that can
 * be boolean, numerical or null. Contained in a Session. The values are written
 * to and read from session files.
 *
 * @see bodylog.logic.Move
 * @see bodylog.logic.Set
 */
public class Set {

    private Object[] values;

    /**
     * Constructs a new Set with no data.
     */
    public Set() {
        this.values = new Object[]{};
    }

    /**
     * Modifies the list of values in this Set to an array of given size. Used
     * for rows in tables in the UI, the row arrays must have the same length as
     * the column header.
     *
     * @param size the desired size
     * @return an array of the values
     */
    public Object[] toArray(int size) {
        return Arrays.copyOf(values, size);
    }

    /**
     * Converts the list of value in this Set to an array of the same size.
     *
     * @return an array of the values
     */
    public Object[] toArray() {
        return values;
    }

    /**
     *
     * @return the number of values in this Set
     */
    public int size() {
        return values.length;
    }

    /**
     * Adds a value to the specified index, replaces the existing value there if
     * present. Increases size of list to index if index is out of bounds.
     *
     * @param value value to be added
     * @param index index to be added to
     */
    public void addValue(Object value, int index) {
        try {
            values[index] = value;
        } catch (IndexOutOfBoundsException e) {
            values = Arrays.copyOf(values, index + 1);
            values[index] = value;
        }
    }

    /**
     * Adds a value to the list.
     *
     * @param value value to be added
     */
    public void addValue(Object value) {
        values = Arrays.copyOf(values, values.length + 1);
        values[values.length - 1] = value;
    }

    /**
     * Gets a value from the specified index. Checks if the index is out of
     * bounds and returns null if so.
     *
     * @param index index from which to get the value
     * @return the value
     */
    public Object getValue(int index) {
        Object value;
        try {
            value = values[index];
        } catch (IndexOutOfBoundsException e) {
            value = null;
        }
        return value;
    }

    /**
     * Converts this Set object into a string. Uses the values of this Set. Each
     * value is converted to a string by <code>DataHandling#setValueToString</code>.
     *
     * @return the Set in the format {value1, ... ,valueN}
     * @see bodylog.logic.DataHandling#setValueToString
     */
    @Override
    public String toString() {
        String strSet = "{";
        for (Object value : values) {
            String strValue = DataHandling.setValueToString(value);
            strSet += strValue + ",";
        }
        strSet = strSet.substring(0, strSet.length() - 1) + "}";
        return strSet;
    }
}
