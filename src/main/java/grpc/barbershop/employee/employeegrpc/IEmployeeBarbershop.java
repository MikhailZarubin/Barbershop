package grpc.barbershop.employee.employeegrpc;

import barbershop.proto.GrpcBarbershopService;
import barbershop.proto.GrpcBarbershopServiceId;
import barbershop.proto.GrpcStatus;

import java.util.ArrayList;

public interface IEmployeeBarbershop {
    GrpcBarbershopServiceId addService(String barbershopServiceName, int barbershopServiceMinutesDuration, int barbershopServiceRublePrice);
    GrpcStatus removeService(int barbershopServiceId);
    ArrayList<GrpcBarbershopService> getServices();
    void shutdown();
}
