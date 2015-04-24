package bodylog.logic;

import bodylog.files.Constant;
import bodylog.logic.abstracts.VariableList;
import java.time.LocalDate;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;

/**
 * The second component in the logical hierarchy. Contains a date and a list of
 * Sets. The date is written to and read from session files.
 *
 * @see bodylog.logic.Move
 * @see bodylog.logic.Set
 */
public class Session extends VariableList {

    private TemporalAccessor date;
    private final ArrayList<Set> sets;

    /**
     * Constructs a new Session with the given date and an empty Set list.
     *
     * @param date a TemporalAccessor constructed by a DateTimeFormatter
     *
     * @see bodylog.files.Constant#FILE_DATE_FORMAT
     */
    public Session(TemporalAccessor date) {
        super();
        this.date = LocalDate.from(date);
        this.sets = new ArrayList<>();
    }

    /**
     * Constructs a new Session with the date set to current system time.
     *
     * @see java.time.LocalDate
     */
    public Session() {
        super();
        //not calling other constructor to avoid superfluous call LocalDate.from(LocalDate.now())
        this.date = LocalDate.now();
        this.sets = new ArrayList<>();
    }

    /**
     * Sets a new date.
     *
     * @param date a TemporalAccessor, parsed from text by a DateTimeFormatter
     *
     * @see java.time.LocalDate
     * @see bodylog.files.Constant#FILE_DATE_FORMAT
     * @see bodylog.files.Constant#UI_DATE_FORMAT
     */
    public void setDate(TemporalAccessor date) {
        this.date = LocalDate.from(date);
    }

    /**
     * Converts the date of this Session into a string format used in files.
     *
     * @return a date as a string
     * @see bodylog.files.Constant#FILE_DATE_FORMAT
     */
    public String getUIDateString() {
        return Constant.UI_DATE_FORMAT.format(date);
    }

    /**
     * Converts the date of this Session into a string format used by the UI.
     *
     * @return a date as a string
     * @see bodylog.files.Constant#UI_DATE_FORMAT
     */
    public String getFileDateString() {
        return Constant.FILE_DATE_FORMAT.format(date);
    }

    /**
     *
     * @return the list of sets contained in this Session
     */
    public ArrayList<Set> getSets() {
        return sets;
    }

    /**
     * Checks the size of each Set contained in this Session and returns the
     * largest value. When writing Sets to file each will be the same size but
     * when reading from file they may be of different length.
     *
     * Not used in anything yet.
     *
     * @return the length of the largest Set
     */
    public int maxSetSize() {
        int size = 0;
        for (Set set : sets) {
            size = Math.max(size, set.size());
        }
        return size;
    }

    /**
     * Gets the number of Sets in this Session.
     *
     * @return the number of Sets
     */
    public int getSetCount() {
        return sets.size();
    }

    /**
     * Gets the set at the specified index. Doesn't check whether it's out of
     * bound or not. Will throw an exception if it is.
     *
     * @param index the index from which to get the set
     * @return the set at the index
     */
    public Set getSet(int index) {
        return sets.get(index);
    }

    /**
     * Adds a Set to this Session.
     *
     * @param set the Set to be added
     */
    public void addSet(Set set) {
        sets.add(set);
    }

    /**
     * Removes the the set at the specified index. Doesn't check if it's out of
     * bounds or not.
     *
     * @param index the index from which to remove
     */
    public void removeSet(int index) {
        sets.remove(index);
    }
}
