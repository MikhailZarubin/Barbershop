package grpc.barbershop.client;

public enum ClientOperation {
    GET_SERVICES(1),
    GER_FREE_TIME(2),
    SCHEDULE_BARBERSHOP(3),
    REMOVE_SCHEDULING(4);

    private final int mNumberOperation;

    ClientOperation(int numberOperation) {
        mNumberOperation = numberOperation;
    }

    public int getNumberOperation() {
        return mNumberOperation;
    }
}