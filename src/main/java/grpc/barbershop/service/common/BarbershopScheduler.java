package grpc.barbershop.service.common;

import grpc.barbershop.util.Constants;
import grpc.barbershop.util.scheduler.BarbershopAppointment;
import grpc.barbershop.util.scheduler.Date;
import grpc.barbershop.util.scheduler.Time;
import grpc.barbershop.util.scheduler.TimeInterval;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BarbershopScheduler implements IBarbershopScheduler {
    private final HashMap<Date, ArrayList<BarbershopAppointment>> mAppointmentsByDate = new HashMap<>();
    private final BarbershopManager mBarbershopManager = new BarbershopManager();

    private BarbershopScheduler() {}

    private static BarbershopScheduler mInstance;

    public static synchronized IBarbershopScheduler getInstance() {
        if (mInstance == null) {
            mInstance = new BarbershopScheduler();
        }
        return mInstance;
    }

    @Override
    public synchronized List<TimeInterval> getFreeTimeByDate(Date date) {
        ArrayList<BarbershopAppointment> appointments = mAppointmentsByDate.get(date);
        if (appointments == null) {
            appointments = new ArrayList<>(List.of(generateDefaultAppointment()));
            mAppointmentsByDate.put(date, appointments);
        }
        return calculateFreeTimeIntervals(appointments);
    }

    private BarbershopAppointment generateDefaultAppointment() {
        Time endTimeOfDay = new Time(Constants.HOUR_OF_DAY, 0);
        return new BarbershopAppointment(Constants.DEFAULT_GLOBAL_APPOINTMENT_ID, Constants.DEFAULT_LOCAL_APPOINTMENT_ID, new TimeInterval(endTimeOfDay, 0));
    }

    private ArrayList<TimeInterval> calculateFreeTimeIntervals(ArrayList<BarbershopAppointment> appointments) {
        ArrayList<TimeInterval> freeTimeIntervals = new ArrayList<>();
        Time startTimeFreeInterval = new Time(0, 0);
        for (BarbershopAppointment appointment : appointments) {
            int freeTimeDuration;
            if (startTimeFreeInterval.getMinutes() > appointment.getInterval().getStartTime().getMinutes()) {
                freeTimeDuration = (appointment.getInterval().getStartTime().getHours() - startTimeFreeInterval.getHours() - 1) * 60;
                freeTimeDuration += Constants.MINUTES_OF_HOUR - startTimeFreeInterval.getMinutes();
            } else {
                freeTimeDuration = (appointment.getInterval().getStartTime().getHours() - startTimeFreeInterval.getHours()) * 60;
                freeTimeDuration += appointment.getInterval().getStartTime().getMinutes() - startTimeFreeInterval.getMinutes();
            }
            if (freeTimeDuration > 0) {
                freeTimeIntervals.add(new TimeInterval(startTimeFreeInterval, freeTimeDuration));
                startTimeFreeInterval = appointment.getInterval().getTimeAfterInterval();
            }
        }
        return freeTimeIntervals;
    }

    @Override
    public synchronized String appointmentToBarbershop(Date date, TimeInterval interval) {
        int startAppointment = Time.convertTimeToMinutes(interval.getStartTime());
        int finishAppointment = Time.convertTimeToMinutes(interval.getTimeAfterInterval());

        List<TimeInterval> freeTimes = getFreeTimeByDate(date);
        int freeTimeIndex = -1;
        for (TimeInterval freeTime : freeTimes) {
            int startFreeTime = Time.convertTimeToMinutes(freeTime.getStartTime());
            int finishFreeTime = Time.convertTimeToMinutes(freeTime.getTimeAfterInterval());

            if (startAppointment >= startFreeTime && finishAppointment <= finishFreeTime) {
                freeTimeIndex = freeTimes.indexOf(freeTime);
                break;
            }
        }

        boolean isSuccessful = freeTimeIndex != -1;

        if (isSuccessful) {
            int appointmentId = mBarbershopManager.getFreeIdByDate(date);
            mBarbershopManager.removeFreeIdByDate(date, appointmentId);

            String globalAppointmentId = BarbershopManager.parseGlobalAppointmentId(date, appointmentId);

            ArrayList<BarbershopAppointment> appointments = mAppointmentsByDate.get(date);
            appointments.add(freeTimeIndex, new BarbershopAppointment(globalAppointmentId, appointmentId, interval));

            return globalAppointmentId;
        }

        return Constants.DEFAULT_GLOBAL_APPOINTMENT_ID;
    }

    @Override
    public synchronized boolean removeAppointmentById(String appointmentId) {
        BarbershopManager.GlobalAppointmentId globalAppointmentId = BarbershopManager.unparseLocalAppointmentId(appointmentId);
        boolean isSuccessful = mAppointmentsByDate.get(globalAppointmentId.getDate()).removeIf(appointment -> appointment.getLocalId() == globalAppointmentId.getLocalId());

        if (isSuccessful) {
            mBarbershopManager.setFreeIdByDate(globalAppointmentId.getDate(), globalAppointmentId.getLocalId());
        }

        return isSuccessful;
    }
}
