package grpc.barbershop.employee.employeegrpc;

import barbershop.proto.EmployeeBarbershopServiceGrpc;
import barbershop.proto.GrpcBarbershopService;
import barbershop.proto.GrpcBarbershopServiceId;
import barbershop.proto.GrpcStatus;
import com.google.protobuf.Empty;
import io.grpc.ManagedChannel;

import java.util.ArrayList;
import java.util.Iterator;

public class EmployeeBarbershop implements IEmployeeBarbershop {
    ManagedChannel mChannel;
    EmployeeBarbershopServiceGrpc.EmployeeBarbershopServiceBlockingStub mBlockingStub;

    public EmployeeBarbershop(ManagedChannel channel) {
        mChannel = channel;
        mBlockingStub = EmployeeBarbershopServiceGrpc.newBlockingStub(channel);
    }

    @Override
    public GrpcBarbershopServiceId addService(String barbershopServiceName, int barbershopServiceMinutesDuration, int barbershopServiceRublePrice) {
        return mBlockingStub.addBarbershopService(GrpcBarbershopService.newBuilder()
                .setId(GrpcBarbershopServiceId.newBuilder().build())
                .setName(barbershopServiceName)
                .setMinutesDuration(barbershopServiceMinutesDuration)
                .setRublePrice(barbershopServiceRublePrice)
                .build());
    }

    @Override
    public GrpcStatus removeService(int barbershopServiceId) {
        return mBlockingStub.removeBarbershopService(GrpcBarbershopServiceId.newBuilder()
                .setId(barbershopServiceId)
                .build());
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
    public void shutdown() {
        mChannel.shutdown();
    }
}
