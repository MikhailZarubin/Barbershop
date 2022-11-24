package grpc.barbershop.employee;

public class EmployeeInterface {
    public static final String initClientMessage = """
            Hello!
            1. Press 1 to add a barbershop service.
            2. Press 2 to remove a barbershop service.
            3. Press 3 to get a list of barbershop services.
            0. Press 0 to exit.
            """;

    public static final String incorrectInput = """
            Incorrect input. Please retry.
            """;

    public static final String inputBarbershopServiceInfo = """
            Please input barbershop service info.
            """;

    public static final String inputName = """
            Service name:
            """;

    public static final String inputDuration = """
            Service minutes duration:
            """;


    public static final String inputRublePrice = """
            Service ruble price:
            """;

    public static final String inputBarbershopServiceId= """
            Please input barbershop service id.
            """;
    public static final String inputId = """
            Id:
            """;
}
