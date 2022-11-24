package grpc.barbershop.service;

import grpc.barbershop.service.client.ClientBarbershopService;
import grpc.barbershop.service.employee.EmployeeBarbershopService;
import grpc.barbershop.util.Constants;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class ServiceMain {
    public static void main(String[] args) throws IOException, InterruptedException {
        Server clientServer = ServerBuilder
                .forPort(Constants.CLIENT_SERVER_PORT)
                .addService(new ClientBarbershopService()).build();

        Server employeeServer = ServerBuilder
                .forPort(Constants.EMPLOYEE_SERVER_PORT)
                .addService(new EmployeeBarbershopService()).build();

        clientServer.start();
        employeeServer.start();

        if (!clientServer.isTerminated()) {
            clientServer.awaitTermination();
        }
        if (!employeeServer.isTerminated()) {
            employeeServer.isTerminated();
        }
    }
}
