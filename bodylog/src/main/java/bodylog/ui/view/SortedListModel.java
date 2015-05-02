package bodylog.ui.view;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;
import javax.swing.AbstractListModel;

/**
 * Implementation of ListModel using a TreeSet to create ordering. Used by
 * MoveSelector.
 *
 * @param <E> class of objects used in this model
 * @see bodylog.ui.view.MoveSelector
 */
public class SortedListModel<E> extends AbstractListModel<E> {

    SortedSet<E> model;

    /**
     * Creates a new ListModel for the given items.
     *
     * @param items the data to use
     */
    public SortedListModel(E items[]) {
        model = new TreeSet(Arrays.asList(items));
    }

    @Override
    public int getSize() {
        return model.size();
    }

    @Override
    public E getElementAt(int index) {
        return (E) model.toArray()[index];
    }

    public void add(E element) {
        if (model.add(element)) {
            fireContentsChanged(this, 0, getSize());
        }
    }

    public void addAll(E elements[]) {
        Collection<E> c = Arrays.asList(elements);
        model.addAll(c);
        fireContentsChanged(this, 0, getSize());
    }

    public void clear() {
        model.clear();
        fireContentsChanged(this, 0, getSize());
    }

    public boolean contains(E element) {
        return model.contains(element);
    }

    public E firstElement() {
        return model.first();
    }

    public Iterator iterator() {
        return model.iterator();
    }

    public E lastElement() {
        return model.last();
    }

    public boolean removeElement(E element) {
        boolean removed = model.remove(element);
        if (removed) {
            fireContentsChanged(this, 0, getSize());
        }
        return removed;
    }
}
