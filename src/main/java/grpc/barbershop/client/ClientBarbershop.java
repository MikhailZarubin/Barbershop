package grpc.barbershop.client;

import barbershop.proto.*;
import com.google.protobuf.Empty;
import grpc.barbershop.service.common.BarbershopManager;
import grpc.barbershop.util.scheduler.Date;
import io.grpc.ManagedChannel;

import java.util.ArrayList;
import java.util.Iterator;

public class ClientBarbershop implements IClientBarbershop {
    ManagedChannel mChannel;
    ClientBarbershopServiceGrpc.ClientBarbershopServiceBlockingStub mBlockingStub;

    public ClientBarbershop(ManagedChannel channel) {
        mChannel = channel;
        mBlockingStub = ClientBarbershopServiceGrpc.newBlockingStub(channel);
    }

    @Override
    public ArrayList<GrpcBarbershopService> getServices() {
        ArrayList<GrpcBarbershopService> barbershopServices = new ArrayList<>();
        Iterator<GrpcBarbershopService> barbershopServicesIterator = mBlockingStub.getBarbershopServices(Empty.newBuilder().build());
        while (barbershopServicesIterator.hasNext()) {
            barbershopServices.add(barbershopServicesIterator.next());
        }
        return barbershopServices;
    }

    @Override
    public ArrayList<GrpcFreeTime> getFreeTimes(int day, int month, int year) {
        ArrayList<GrpcFreeTime> freeTimes = new ArrayList<>();
        Iterator<GrpcFreeTime> freeTimesIterator = mBlockingStub.getFreeTime(GrpcDate.newBuilder()
                .setDay(day)
                .setMonth(month)
                .setYear(year)
                .build());
        while (freeTimesIterator.hasNext()) {
            freeTimes.add(freeTimesIterator.next());
        }
        return freeTimes;
    }

    @Override
    public GrpcAppointmentResponse appointmentToBarbershop(int day, int month, int year, int hour, int minutes, int serviceId) {
        return mBlockingStub.appointmentToBarbershop(GrpcAppointmentRequest.newBuilder()
                .setDate(GrpcDate.newBuilder()
                        .setDay(day)
                        .setMonth(month)
                        .setYear(year)
                        .build())
                .setTime(GrpcTime.newBuilder()
                        .setHour(String.valueOf(hour))
                        .setMinutes(String.valueOf(minutes))
                        .build())
                .setBarbershopServiceId(GrpcBarbershopServiceId.newBuilder()
                        .setId(serviceId)
                        .build())
                .build());
    }

    @Override
    public GrpcStatus removeAppointment(int day, int month, int year, int appointmentId) {
        Date appointmentDate = new Date(day, month, year);
        return mBlockingStub.removeAppointmentById(GrpcAppointmentId.newBuilder()
                .setId(BarbershopManager.parseGlobalAppointmentId(appointmentDate, appointmentId))
                .build());
    }

    @Override
    public void shutdown() {
        mChannel.shutdown();
    }
}
