package grpc.barbershop.client.clientgrpc;

import barbershop.proto.GrpcAppointmentResponse;
import barbershop.proto.GrpcBarbershopService;
import barbershop.proto.GrpcFreeTime;
import barbershop.proto.GrpcStatus;
import grpc.barbershop.util.scheduler.Date;
import grpc.barbershop.util.scheduler.Time;

import java.util.ArrayList;

public interface IClientBarbershop {
    ArrayList<GrpcBarbershopService> getServices();
    ArrayList<GrpcFreeTime> getFreeTimes(Date date);
    GrpcAppointmentResponse appointmentToBarbershop(Date date, Time time, int serviceId);
    GrpcStatus removeAppointment(Date date, int appointmentId);
    void shutdown();
}
