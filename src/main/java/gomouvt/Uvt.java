package gomouvt;

import java.util.StringJoiner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.ArrayList;

/**
 * Gomo unique view time (UVT) object.
 *
 * @author  Peter Hambly
 * @version 1.0
 * @since   1.0
 */
public class Uvt {

    /**
     * List of UVT periods (start and stop times in mS).
     */
    private ArrayList<UvtPeriod> uvtList;

    /**
     * Constructor.
     */
    public Uvt() {
        uvtList = new ArrayList<UvtPeriod>();
    }

    /**
     * Prints unique view time results.
     *
     * @return Total UVT as a double
     */
    public double getResults() {
        double totalPeriod = 0.0;

        for (int i = 0; i < uvtList.size(); i++) {
            UvtPeriod u = uvtList.get(i);
            totalPeriod += u.getPeriod();
        }

        return totalPeriod;
    }

    /**
     * Adds period args to Uvt.
     *
     * @param testArgs arguments to program. A string array with the strings
     *        in the form n-m. Where n and m are times in milliseconds.
     *        n<=m and must not be negative
     * @throws UvtException if testArgs is null or zero length
     */
    public void addPeriod(
        final String[] testArgs)
        throws UvtException {

        if (testArgs == null || testArgs.length == 0) {
            throw new UvtException("testArgs is null or zero length");
        }

        // Check args are in the correct format
        String pattern = "[0-9\\.]{1,}\\-[0-9\\.]{1,}";
        Pattern regexp = Pattern.compile(pattern);
        for (String s: testArgs) {

            Matcher m;
            try {
                m = regexp.matcher(s);
            } catch (Exception e) {
                throw new UvtException("String arg: '" + s
                    + "' contains invalid characters", e);
            }

            if (m.matches()) {
                String[] splitString = s.split("-");
                if (splitString.length == 2) {
                    addToUvtList(new UvtPeriod(
                        new Double(splitString[0]),
                        new Double(splitString[1])));
                } else { // this should not occur
                    throw new UvtException("String arg: '" + s
                        + "' contains >1 dash: " + (splitString.length - 1));
                }
            } else { // again this should not occur
                throw new UvtException("String arg: '" + s
                    + "' contains invalid characters");
            }
        }

//        System.out.println("uvtList: " + uvtList.toString());
    }

    /**
     * Adds uvtPeriod to list.
     *
     * @param uvtPeriod UVT period object (start and end time in mS)
     * @throws UvtException because of UvtPeriod error
     */
    private void addToUvtList(
        final UvtPeriod uvtPeriod)
        throws UvtException {

        if (uvtList.size() == 0) { // Nothing in list - add
//            System.out.println("Add; uvtPeriod: " + uvtPeriod.toString());
            uvtList.add(uvtPeriod);
        } else {
            boolean merged = false;
            for (int i = 0; i < uvtList.size(); i++) {
                UvtPeriod u = uvtList.get(i);
                // Check to see if new UvtPeriod overlaps any
                // current member of the list.
                // If it overlaps: merge it
                if (u.periodsOverlap(uvtPeriod)) {
                    UvtPeriod nuvtPeriod = u.mergePeriods(uvtPeriod);
//                    System.out.println("Merge new into: " + i
//                        + "; nuvtPeriod: " + nuvtPeriod.toString());
                    uvtList.set(i, nuvtPeriod);
                    merged = true;
                }
            }

            if (!merged) {
//                System.out.println("Add; uvtPeriod: "
//                    + uvtPeriod.toString());
                uvtList.add(uvtPeriod);
            }
        }

        // Now check to see if any adjacent periods overlap and need merging
        if (uvtList.size() > 1) {
            for (int i = 1; i < uvtList.size(); i++) {
                UvtPeriod uvtPeriod1 = uvtList.get(i - 1);
                UvtPeriod uvtPeriod2 = uvtList.get(i);
                if (uvtPeriod1.periodsOverlap(uvtPeriod2)) {
                    uvtList.set((i - 1), uvtPeriod1.mergePeriods(uvtPeriod2));
//                    System.out.println("Merged: " + (i - 1));
//                    System.out.println("Remove: " + i);
                    uvtList.remove(i);
                } /* else {
                    System.out.println("No overlap: " + i + ", "
                        + (i - 1));
                } */
            }
        }
    }

    /**
     * Print uvtPeriod list.
     *
     * @return uvtPeriod list as a comma separated String
     */
    public String toString() {
        StringJoiner periods = new StringJoiner(", ");
        for (UvtPeriod u: uvtList) {
            periods.add(u.toString());
        }

        return periods.toString();
    }
}

