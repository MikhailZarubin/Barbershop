package grpc.barbershop.service.common;

import grpc.barbershop.util.scheduler.Date;
import grpc.barbershop.util.scheduler.TimeInterval;

import java.util.List;

public interface IBarbershopScheduler {
    List<TimeInterval> getFreeTimeByDate(Date date);
    String appointmentToBarbershop(Date date, TimeInterval interval);
    boolean removeAppointmentById(String appointmentId);
}
