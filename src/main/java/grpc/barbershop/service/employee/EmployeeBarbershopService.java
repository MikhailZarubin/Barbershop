package grpc.barbershop.service.employee;

import barbershop.proto.EmployeeBarbershopServiceGrpc;
import barbershop.proto.GrpcBarbershopService;
import barbershop.proto.GrpcBarbershopServiceId;
import barbershop.proto.GrpcStatus;
import com.google.protobuf.Empty;
import grpc.barbershop.service.common.BarbershopSharedData;
import grpc.barbershop.service.common.IBarbershopSharedData;
import grpc.barbershop.util.Constants;
import grpc.barbershop.util.service.BarbershopService;
import io.grpc.stub.StreamObserver;

import java.util.List;

public class EmployeeBarbershopService extends EmployeeBarbershopServiceGrpc.EmployeeBarbershopServiceImplBase {
    private final IBarbershopSharedData mBarbershopSharedData = BarbershopSharedData.getInstance();

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
    public void addBarbershopService(GrpcBarbershopService request, StreamObserver<GrpcBarbershopServiceId> responseObserver) {
        int serviceId = mBarbershopSharedData.addService(new BarbershopService(request.getName(), request.getMinutesDuration(), request.getRublePrice()));
        responseObserver.onNext(GrpcBarbershopServiceId.newBuilder()
                .setId(serviceId)
                .build());
        responseObserver.onCompleted();
    }

    @Override
    public void removeBarbershopService(GrpcBarbershopServiceId request, StreamObserver<GrpcStatus> responseObserver) {
        boolean isSuccessful = mBarbershopSharedData.removeService(request.getId());
        responseObserver.onNext(GrpcStatus.newBuilder()
                .setIsSuccessful(isSuccessful ? Constants.STATUS_SUCCESS : Constants.STATUS_NOT_SUCCESS)
                .build());
        responseObserver.onCompleted();
    }
}
