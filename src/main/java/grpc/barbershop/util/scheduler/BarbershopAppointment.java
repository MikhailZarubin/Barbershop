package grpc.barbershop.util.scheduler;

public class BarbershopAppointment {
    private final String mGlobalId;
    private final int mLocalId;
    private final TimeInterval mInterval;

    public BarbershopAppointment(String globalId, int localId, TimeInterval interval) {
        mGlobalId = globalId;
        mLocalId = localId;
        mInterval = interval;
    }

    public String getGlobalId() {
        return mGlobalId;
    }

    public int getLocalId() {
        return mLocalId;
    }

    public TimeInterval getInterval() {
        return mInterval;
    }
}
