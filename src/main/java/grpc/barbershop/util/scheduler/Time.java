package grpc.barbershop.util.scheduler;

import grpc.barbershop.util.Constants;

public class Time {
    private final int mHours;
    private final int mMinutes;

    public Time(int hours, int minutes) {
        mMinutes = minutes;
        mHours = hours;
    }

    public int getHours() {
        return mHours;
    }

    public int getMinutes() {
        return mMinutes;
    }

    public static int convertTimeToMinutes(Time time) {
        return time.getHours() * Constants.MINUTES_OF_HOUR + time.getMinutes();
    }
}
