package grpc.barbershop.client;

import grpc.barbershop.client.clientgrpc.ClientBarbershop;
import grpc.barbershop.client.clientgrpc.IClientBarbershop;
import grpc.barbershop.util.Constants;
import grpc.barbershop.util.scheduler.Date;
import grpc.barbershop.util.scheduler.Time;
import io.grpc.ManagedChannelBuilder;

import java.util.Arrays;
import java.util.Optional;
import java.util.Scanner;

public class ClientMain {
    public static void main(String[] args) {
        IClientBarbershop clientBarbershop = new ClientBarbershop(ManagedChannelBuilder.forAddress(Constants.LOCAL_HOST, Constants.CLIENT_SERVER_PORT)
                .usePlaintext()
                .build());

        System.out.println(ClientInterface.initClientMessage + '\n');

        Scanner consoleScanner = new Scanner(System.in);
        int clientMessage = consoleScanner.nextInt();
        while (clientMessage != Constants.EXIT_MESSAGE) {
            int currentClientMessage = clientMessage;
            Optional<ClientOperation> clientOperation = Arrays.stream(ClientOperation.values()).filter(operation -> operation.getNumberOperation() == currentClientMessage).findFirst();

            if (clientOperation.isPresent()) {
                switch (clientOperation.get()) {
                    case GET_SERVICES -> {
                        clientBarbershop.getServices().forEach(barbershopService -> System.out.println(barbershopService.toString() + '\n'));
                    }
                    case GER_FREE_TIME -> {
                        System.out.println(ClientInterface.inputDate + '\n' + ClientInterface.inputDay);
                        int day = consoleScanner.nextInt();
                        System.out.println(ClientInterface.inputMonth);
                        int month = consoleScanner.nextInt();
                        System.out.println(ClientInterface.inputYear);
                        int year = consoleScanner.nextInt();

                        clientBarbershop.getFreeTimes(new Date(day, month, year)).forEach(freeTime -> System.out.println(freeTime.toString() + '\n'));
                    }
                    case SCHEDULE_BARBERSHOP -> {
                        System.out.println(ClientInterface.inputDate + '\n' + ClientInterface.inputDay);
                        int day = consoleScanner.nextInt();
                        System.out.println(ClientInterface.inputMonth);
                        int month = consoleScanner.nextInt();
                        System.out.println(ClientInterface.inputYear);
                        int year = consoleScanner.nextInt();

                        System.out.println(ClientInterface.inputTime + '\n' + ClientInterface.inputHour);
                        int hour = consoleScanner.nextInt();
                        System.out.println(ClientInterface.inputMinutes);
                        int minutes = consoleScanner.nextInt();

                        System.out.println(ClientInterface.inputServiceId + '\n' + ClientInterface.inputId);
                        int serviceId = consoleScanner.nextInt();

                        System.out.println(clientBarbershop.appointmentToBarbershop(new Date(day, month, year), new Time(hour, minutes), serviceId).toString() + '\n');
                    }
                    case REMOVE_SCHEDULING -> {
                        System.out.println(ClientInterface.inputAppointmentDate + '\n' + ClientInterface.inputDay);
                        int day = consoleScanner.nextInt();
                        System.out.println(ClientInterface.inputMonth);
                        int month = consoleScanner.nextInt();
                        System.out.println(ClientInterface.inputYear);
                        int year = consoleScanner.nextInt();

                        System.out.println(ClientInterface.inputAppointmentId + '\n' + ClientInterface.inputId);
                        int appointmentId = consoleScanner.nextInt();

                        System.out.println(clientBarbershop.removeAppointment(new Date(day, month, year), appointmentId).toString() + '\n');
                    }
                }
            } else {
                System.out.println(ClientInterface.incorrectInput + '\n');
            }

            System.out.println(ClientInterface.initClientMessage + '\n');
            clientMessage = consoleScanner.nextInt();
        }

        clientBarbershop.shutdown();
    }
}