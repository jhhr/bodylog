package bodylog.ui.edit;

import java.util.Comparator;
import javax.swing.DefaultComboBoxModel;

/**
 * Custom model to make sure the items are stored in a sorted order. The default
 * is to sort in the natural order of the item, but a Comparator can be used to
 * customize the sort order.
 */
class SortedComboBoxModel<E> extends DefaultComboBoxModel<E> {

    private Comparator comparator;

    /**
     * Create an empty model that will use the natural sort order of the item
     */
    public SortedComboBoxModel() {
        super();
    }

    /**
     * Create an empty model that will use the specified Comparator
     *
     * @param comparator the Comparator to use
     */
    public SortedComboBoxModel(Comparator comparator) {
        super();
        this.comparator = comparator;
    }

    /**
     * Create a model with data and use the natural sort order of the items.
     *
     * @param items the data to use
     */
    public SortedComboBoxModel(E items[]) {
        this(items, null);
    }

    /**
     * Create a model with data and use the specified Comparator.
     *
     * @param items the data to use
     * @param comparator the Comparator to use
     */
    public SortedComboBoxModel(E items[], Comparator comparator) {
        this.comparator = comparator;

        for (E item : items) {
            addElement(item);
        }
    }

    @Override
    public void addElement(E element) {
        insertElementAt(element, 0);
    }

    @Override
    public void insertElementAt(E element, int index) {
        int size = getSize();

        //  Determine where to insert element to keep model in sorted order
        for (index = 0; index < size; index++) {
            if (comparator != null) {
                E o = getElementAt(index);

                if (comparator.compare(o, element) > 0) {
                    break;
                }
            } else {
                Comparable c = (Comparable) getElementAt(index);

                if (c.compareTo(element) > 0) {
                    break;
                }
            }
        }

        super.insertElementAt(element, index);

        //  Select an element when it is added to the beginning of the model
        if (index == 0 && element != null) {
            setSelectedItem(element);
        }
    }
}
