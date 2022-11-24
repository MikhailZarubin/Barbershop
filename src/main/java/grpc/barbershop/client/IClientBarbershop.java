package grpc.barbershop.client;

import barbershop.proto.GrpcAppointmentResponse;
import barbershop.proto.GrpcBarbershopService;
import barbershop.proto.GrpcFreeTime;
import barbershop.proto.GrpcStatus;

import java.util.ArrayList;

public interface IClientBarbershop {
    ArrayList<GrpcBarbershopService> getServices();
    ArrayList<GrpcFreeTime> getFreeTimes(int day, int month, int year);
    GrpcAppointmentResponse appointmentToBarbershop(int day, int month, int year, int hour, int minutes, int serviceId);
    GrpcStatus removeAppointment(int day, int month, int year, int appointmentId);
    void shutdown();
}
