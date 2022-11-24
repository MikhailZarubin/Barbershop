package grpc.barbershop.client;

public class ClientInterface {
    public static final String initClientMessage = """
            Hello!
            1. Press 1 to get a list of barbershop services.
            2. Press 2 to get the free time of the barbershop wizard.
            3. Press 3 to schedule for a barbershop.
            4. Press 4 to remove the scheduling.
            0. Press 0 to exit.
            """;

    public static final String incorrectInput = """
            Incorrect input. Please retry.
            """;

    public static final String inputDate = """
            Please input date.
            """;

    public static final String inputDay = """
            Day:
            """;

    public static final String inputMonth = """
            Month:
            """;

    public static final String inputYear = """
            Year:
            """;

    public static final String inputTime = """
            Please input time:
            """;
    public static final String inputHour = """
            Hour:
            """;

    public static final String inputMinutes = """
            Minutes:
            """;

    public static final String inputServiceId = """
            Please input barbershop service id.
            """;

    public static final String inputAppointmentId = """
            Please input appointment id.
            """;

    public static final String inputAppointmentDate = """
            Please input appointment id.
            """;
    public static final String inputId = """
            Id:
            """;
}
