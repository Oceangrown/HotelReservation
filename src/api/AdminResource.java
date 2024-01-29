package api;

import model.Customer;
import model.IRoom;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;
import java.util.List;

public class AdminResource {
    private static final CustomerService customerService = CustomerService.getInstance();
    private static final ReservationService reservationService = ReservationService.getInstance();

    public static Customer getCustomer(String email){
        return customerService.getCustomer(email);
    }

    public static void addRoom(IRoom rooms){

            reservationService.addRoom(rooms);

    }


    public static Collection<IRoom> getAllRooms(){
        return reservationService.getAllRooms();
    }
    public static Collection<Customer> getAllCustomers(){
        return customerService.getAllCustomers();
    }
    public static void displayAllReservations(){
        reservationService.printAllReservations();
    }
}
