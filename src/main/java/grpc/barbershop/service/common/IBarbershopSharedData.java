package grpc.barbershop.service.common;

import grpc.barbershop.util.service.BarbershopService;

import java.util.List;

public interface IBarbershopSharedData {
    List<BarbershopService> getServices();
    BarbershopService getServiceById(int id);
    int addService(BarbershopService service);
    boolean removeService(int serviceId);
}
