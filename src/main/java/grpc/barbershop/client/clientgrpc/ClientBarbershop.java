package grpc.barbershop.client.clientgrpc;

import barbershop.proto.*;
import com.google.protobuf.Empty;
import grpc.barbershop.service.common.BarbershopManager;
import grpc.barbershop.util.scheduler.Date;
import grpc.barbershop.util.scheduler.Time;
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
    public ArrayList<GrpcFreeTime> getFreeTimes(Date date) {
        ArrayList<GrpcFreeTime> freeTimes = new ArrayList<>();
        Iterator<GrpcFreeTime> freeTimesIterator = mBlockingStub.getFreeTime(GrpcDate.newBuilder()
                .setDay(date.getDay())
                .setMonth(date.getMonth())
                .setYear(date.getYear())
                .build());
        while (freeTimesIterator.hasNext()) {
            freeTimes.add(freeTimesIterator.next());
        }
        return freeTimes;
    }

    @Override
    public GrpcAppointmentResponse appointmentToBarbershop(Date date, Time time, int serviceId) {
        return mBlockingStub.appointmentToBarbershop(GrpcAppointmentRequest.newBuilder()
                .setDate(GrpcDate.newBuilder()
                        .setDay(date.getDay())
                        .setMonth(date.getMonth())
                        .setYear(date.getYear())
                        .build())
                .setTime(GrpcTime.newBuilder()
                        .setHour(String.valueOf(time.getHours()))
                        .setMinutes(String.valueOf(time.getMinutes()))
                        .build())
                .setBarbershopServiceId(GrpcBarbershopServiceId.newBuilder()
                        .setId(serviceId)
                        .build())
                .build());
    }

    @Override
    public GrpcStatus removeAppointment(Date date, int appointmentId) {
        return mBlockingStub.removeAppointmentById(GrpcAppointmentId.newBuilder()
                .setId(BarbershopManager.parseGlobalAppointmentId(date, appointmentId))
                .build());
    }

    @Override
    public void shutdown() {
        mChannel.shutdown();
    }
}
