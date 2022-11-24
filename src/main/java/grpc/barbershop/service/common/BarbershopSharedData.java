package grpc.barbershop.service.common;

import grpc.barbershop.util.service.BarbershopService;
import grpc.barbershop.util.InitializeBarbershopData;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.TreeSet;
import java.util.stream.IntStream;

public class BarbershopSharedData implements IBarbershopSharedData {
    private final ArrayList<BarbershopService> mBarbershopServices = new ArrayList<>();
    private final TreeSet<Integer> mFreeIds = new TreeSet<>();

    private static BarbershopSharedData mInstance;

    private BarbershopSharedData() {
        mBarbershopServices.addAll(InitializeBarbershopData.INITIALIZE_BARBERSHOP_SERVICE);
        mFreeIds.addAll(IntStream.range(1, InitializeBarbershopData.MAX_SERVICES_COUNT).boxed().toList());

        mBarbershopServices.forEach(service -> mFreeIds.remove(service.getId()));
    }

    public static synchronized BarbershopSharedData getInstance() {
        if (mInstance == null) {
            mInstance = new BarbershopSharedData();
        }
        return mInstance;
    }

    @Override
    public List<BarbershopService> getServices() {
        return new ArrayList<>(mBarbershopServices);
    }

    @Override
    public synchronized int addService(BarbershopService service) {
        int serviceId = mFreeIds.first();
        mFreeIds.remove(serviceId);

        service.setId(serviceId);
        mBarbershopServices.add(service);
        return serviceId;
    }

    @Override
    public synchronized boolean removeService(int serviceId) {
        boolean isSuccessful = mBarbershopServices.removeIf(service -> service.getId() == serviceId);
        if (isSuccessful) {
            mFreeIds.add(serviceId);
        }
        return isSuccessful;
    }

    @Override
    public BarbershopService getServiceById(int id) {
        Optional<BarbershopService> barbershopService = mBarbershopServices.stream().filter(service -> service.getId() == id).findFirst();
        return barbershopService.orElse(null);
    }
}
