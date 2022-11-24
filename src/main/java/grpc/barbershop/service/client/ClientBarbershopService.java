package grpc.barbershop.service.client;

import barbershop.proto.*;
import com.google.protobuf.Empty;
import grpc.barbershop.service.common.*;
import grpc.barbershop.util.Constants;
import grpc.barbershop.util.scheduler.Date;
import grpc.barbershop.util.scheduler.Time;
import grpc.barbershop.util.scheduler.TimeInterval;
import grpc.barbershop.util.service.BarbershopService;
import io.grpc.stub.StreamObserver;

import java.util.List;

public class ClientBarbershopService extends ClientBarbershopServiceGrpc.ClientBarbershopServiceImplBase {
    private final IBarbershopSharedData mBarbershopSharedData = BarbershopSharedData.getInstance();
    private final IBarbershopScheduler mBarbershopScheduler = BarbershopScheduler.getInstance();

    @Override
    public void getBarbershopServices(Empty request, StreamObserver<GrpcBarbershopService> responseObserver) {
        List<BarbershopService> barbershopServices = mBarbershopSharedData.getServices();
        barbershopServices.forEach(service -> {
            GrpcBarbershopService barbershopService = GrpcBarbershopService.newBuilder()
                    .setId(GrpcBarbershopServiceId.newBuilder()
                            .setId(service.getId())
                            .build())
                    .setName(service.getName())
                    .setMinutesDuration(service.getMinutesDuration())
                    .setRublePrice(service.getRublePrice())
                    .build();
            responseObserver.onNext(barbershopService);
        });
        responseObserver.onCompleted();
    }

    @Override
    public void getFreeTime(GrpcDate request, StreamObserver<GrpcFreeTime> responseObserver) {
        List<TimeInterval> freeTimes = mBarbershopScheduler.getFreeTimeByDate(new Date(request.getDay(), request.getMonth(), request.getYear()));
        freeTimes.forEach(freeTime -> {
            GrpcFreeTime grpcFreeTime = GrpcFreeTime.newBuilder()
                    .setStartFreeTime(GrpcTime.newBuilder()
                            .setHour(String.valueOf(freeTime.getStartTime().getHours()))
                            .setMinutes(String.valueOf(freeTime.getStartTime().getMinutes()))
                            .build())
                    .setMinutesDuration(freeTime.getMinutesDuration())
                    .build();
            responseObserver.onNext(grpcFreeTime);
        });
        responseObserver.onCompleted();
    }

    @Override
    public void appointmentToBarbershop(GrpcAppointmentRequest request, StreamObserver<GrpcAppointmentResponse> responseObserver) {
        BarbershopService barbershopService = mBarbershopSharedData.getServiceById(request.getBarbershopServiceId().getId());

        Date appointmentDate = new Date(request.getDate().getDay(), request.getDate().getMonth(), request.getDate().getYear());
        TimeInterval appointmentTimeInterval = new TimeInterval(new Time(Integer.parseInt(request.getTime().getHour()), Integer.parseInt(request.getTime().getMinutes())), barbershopService.getMinutesDuration());

        String appointmentId = mBarbershopScheduler.appointmentToBarbershop(appointmentDate, appointmentTimeInterval);
        int rublePriceService = barbershopService.getRublePrice();

        responseObserver.onNext(GrpcAppointmentResponse.newBuilder()
                .setAppointmentId(GrpcAppointmentId.newBuilder()
                        .setId(appointmentId)
                        .build())
                .setPrice(rublePriceService)
                .setIsSuccessful(appointmentId.equals(Constants.DEFAULT_GLOBAL_APPOINTMENT_ID) ? Constants.STATUS_NOT_SUCCESS : Constants.STATUS_SUCCESS)
                .build());
        responseObserver.onCompleted();
    }

    @Override
    public void removeAppointmentById(GrpcAppointmentId request, StreamObserver<GrpcStatus> responseObserver) {
        boolean isSuccessful = mBarbershopScheduler.removeAppointmentById(request.getId());
        responseObserver.onNext(GrpcStatus.newBuilder()
                .setIsSuccessful(isSuccessful ? Constants.STATUS_SUCCESS : Constants.STATUS_NOT_SUCCESS)
                .build());
        responseObserver.onCompleted();
    }
}
