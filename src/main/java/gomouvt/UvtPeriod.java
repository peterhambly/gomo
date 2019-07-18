package gomouvt;

/**
 * Gomo unique view time (UVT) object.
 *
 * @author  Peter Hambly
 * @version 1.0
 * @since   1.0
 */
public class UvtPeriod {

    /**
     * Start time in mS.
     */
    private Double startTime;
    /**
     * End time in mS.
     */
    private Double endTime;

    /**
     * Constructor.
     *
     * @param period1 start of period
     * @param period2 end of period
     * @throws UvtException if uvtPeriod if period ends before it starts
     */
    public UvtPeriod(
        final Double period1,
        final Double period2) throws UvtException {

        if (period2 <= period1) {
            throw new UvtException("UvtPeriod ends ("
                + period1 + ") before it starts ("
                + period2 + ")");
        }

        this.startTime = period1;
        this.endTime = period2;
    }

    /**
     * Test if periods overlap.
     *
     * @param uvtPeriod other UvtPeriod to check
     * @throws UvtException if uvtPeriod is null
     *
     * @return true or false
     */
    public boolean periodsOverlap(
        final UvtPeriod uvtPeriod) throws UvtException {
        boolean rVal = false;

        if (uvtPeriod == null) {
            throw new UvtException("UvtPeriod is null");
        }

        if ((uvtPeriod.getStartTime() >= startTime
          && uvtPeriod.getStartTime() <= endTime) // uvtPeriod is ahead
         || (uvtPeriod.getEndTime() >= startTime
          && uvtPeriod.getEndTime() <= endTime) // this is ahead
            ) {
            rVal = true;
        }

        return rVal;
    }

    /**
     * Merge overlapping periods.
     *
     * @param uvtPeriod to merge
     * @throws UvtException if uvtPeriod is null or does not overlap
     *
     * @return new uvtPeriod
     */
    public UvtPeriod mergePeriods(
        final UvtPeriod uvtPeriod) throws UvtException {

        if (uvtPeriod == null) {
            throw new UvtException("UvtPeriod is null");
        }

        if (this.periodsOverlap(uvtPeriod)) {
            Double period1;
            Double period2;

            if (uvtPeriod.getStartTime() >= this.startTime) {
                period1 = this.startTime;
            } else {
                period1 = uvtPeriod.getStartTime();
            }
            if (uvtPeriod.getEndTime() <= endTime) {
                period2 = this.endTime;
            } else {
                period2 = uvtPeriod.getEndTime();
            }
            UvtPeriod nuvtPeriod = new UvtPeriod(period1, period2);
//            System.out.println("Merge: " + nuvtPeriod.toString()
//                + " from: ("
//                + this.toString() + ", " + uvtPeriod.toString() + ")");
            return nuvtPeriod;
        } else {
            throw new UvtException("UvtPeriods ("
                + this.toString() + ", " + uvtPeriod.toString()
                + ") do not overlap");
        }
    }

    /**
     * toString method.
     *
     * @return UvtPeriod as a String
     */
    public String toString() {
        return this.startTime.toString() + "-" + this.endTime.toString();
    }

    /**
     * Get start time.
     *
     * @return start time
     */
    public Double getStartTime() {
        return startTime;
    }

    /**
     * Get end time.
     *
     * @return end time
     */
    public Double getEndTime() {
        return endTime;
    }

    /**
     * Get period.
     *
     * @return period in mS as double
     */
    public double getPeriod() {
        Double period = endTime - startTime;
        return period.doubleValue();
    }
}

