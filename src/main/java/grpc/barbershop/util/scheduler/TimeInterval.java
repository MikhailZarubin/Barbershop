package grpc.barbershop.util.scheduler;

import grpc.barbershop.util.Constants;

import java.lang.module.Configuration;

public class TimeInterval {
    private final Time mStartTime;
    private final int mMinutesDuration;

    public TimeInterval(Time startTime, int minutesDuration) {
        mMinutesDuration = minutesDuration;
        mStartTime = startTime;
    }

    public Time getStartTime() {
        return mStartTime;
    }

    public int getMinutesDuration() {
        return mMinutesDuration;
    }

    public Time getTimeAfterInterval() {
        int hours = mStartTime.getHours() + mMinutesDuration / Constants.MINUTES_OF_HOUR;
        int minutes = mStartTime.getMinutes() + mMinutesDuration % Constants.MINUTES_OF_HOUR;
        if (minutes >= Constants.MINUTES_OF_HOUR) {
            minutes -= Constants.MINUTES_OF_HOUR;
            hours++;
        }
        return new Time(hours, minutes);
    }
}
