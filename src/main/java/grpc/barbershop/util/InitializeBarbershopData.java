package grpc.barbershop.util;

import grpc.barbershop.util.service.BarbershopService;

import java.util.ArrayList;
import java.util.List;

public class InitializeBarbershopData {
    public static final ArrayList<BarbershopService> INITIALIZE_BARBERSHOP_SERVICE = new ArrayList<>(
            List.of(new BarbershopService(1, "Men`s haircut", 30, 400),
                    new BarbershopService(2, "Woman`s haircut", 45, 600))
    );
    public static final int MAX_SERVICES_COUNT = 100;
    public static final int MAX_APPOINTMENT_COUNT = Constants.HOUR_OF_DAY * Constants.MINUTES_OF_HOUR;
}
