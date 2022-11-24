package grpc.barbershop.employee;

import grpc.barbershop.employee.employeegrpc.EmployeeBarbershop;
import grpc.barbershop.employee.employeegrpc.IEmployeeBarbershop;
import grpc.barbershop.util.Constants;
import io.grpc.ManagedChannelBuilder;

import java.util.Arrays;
import java.util.Optional;
import java.util.Scanner;

public class EmployeeMain {
    public static void main(String[] args) {
        IEmployeeBarbershop employeeBarbershop = new EmployeeBarbershop(ManagedChannelBuilder.forAddress(Constants.LOCAL_HOST, Constants.EMPLOYEE_SERVER_PORT)
                .usePlaintext()
                .build());

        System.out.println(EmployeeInterface.initClientMessage + '\n');

        Scanner consoleScanner = new Scanner(System.in);
        int clientMessage = consoleScanner.nextInt();
        consoleScanner.nextLine();
        while (clientMessage != Constants.EXIT_MESSAGE) {
            int currentClientMessage = clientMessage;
            Optional<EmployeeOperation> clientOperation = Arrays.stream(EmployeeOperation.values()).filter(operation -> operation.getNumberOperation() == currentClientMessage).findFirst();

            if (clientOperation.isPresent()) {
                switch (clientOperation.get()) {
                    case ADD_SERVICE -> {
                        System.out.println(EmployeeInterface.inputBarbershopServiceInfo + '\n' + EmployeeInterface.inputName);
                        String serviceName = consoleScanner.nextLine();
                        System.out.println(EmployeeInterface.inputDuration + '\n');
                        int serviceDuration = consoleScanner.nextInt();
                        consoleScanner.nextLine();
                        System.out.println(EmployeeInterface.inputRublePrice + '\n');
                        int rublePrice = consoleScanner.nextInt();
                        consoleScanner.nextLine();

                        System.out.println(employeeBarbershop.addService(serviceName, serviceDuration, rublePrice).toString() + '\n');
                    }
                    case REMOVE_SERVICE -> {
                        System.out.println(EmployeeInterface.inputBarbershopServiceInfo + '\n' + EmployeeInterface.inputId);
                        int serviceId = consoleScanner.nextInt();
                        consoleScanner.nextLine();

                        System.out.println(employeeBarbershop.removeService(serviceId).toString() + '\n');
                    }
                    case GET_SERVICES -> {
                        employeeBarbershop.getServices().forEach(barbershopService -> System.out.println(barbershopService.toString() + '\n'));
                    }
                }
            } else {
                System.out.println(EmployeeInterface.incorrectInput + '\n');
            }

            System.out.println(EmployeeInterface.initClientMessage + '\n');
            clientMessage = consoleScanner.nextInt();
            consoleScanner.nextLine();
        }

        employeeBarbershop.shutdown();
    }
}