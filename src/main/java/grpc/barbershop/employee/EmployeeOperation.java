package grpc.barbershop.employee;

public enum EmployeeOperation {
    ADD_SERVICE(1),
    REMOVE_SERVICE(2),
    GET_SERVICES(3);

    private final int mNumberOperation;

    EmployeeOperation(int numberOperation) {
        mNumberOperation = numberOperation;
    }

    public int getNumberOperation() {
        return mNumberOperation;
    }
}
