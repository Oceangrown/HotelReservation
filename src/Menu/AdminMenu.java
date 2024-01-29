package Menu;

import api.AdminResource;
import api.HotelResource;
import model.*;

import javax.imageio.event.IIOReadProgressListener;
import java.text.ParseException;
import java.util.*;

import static Menu.MainMenu.getUserChoice;
import static Menu.MainMenu.scanner;
import static service.ReservationService.rooms;

public class AdminMenu {
    private static AdminResource adminResource = new AdminResource();
    private static HotelResource hotelResource = new HotelResource();
    private static final Set<String> roomNumbers = new HashSet<>();
    public static Scanner scanner = new Scanner(System.in);



    private static void seeAllCustomers(){
        Collection<Customer> customers = AdminResource.getAllCustomers();
        if(customers.isEmpty()){
           System.out.println("No Customers found");
       } else{
            System.out.println("Displaying all Customers");
            System.out.println("Name: \t\t Email:");
            for(Customer customer : customers){
                System.out.println(customer.toString());
            }
        }

    }
    private static void seeAllRooms(){
        Collection<IRoom> rooms = AdminResource.getAllRooms();
        if(rooms.isEmpty()){
            System.out.println("No rooms found");
        } else{
            System.out.println("Displaying all rooms");
            System.out.println("Room Number:  Price:  Room Type:");
            for(IRoom room : rooms){
                System.out.println(room.toString());
            }
        }

    }
    private static void seeAllReservations(){
        System.out.println("\t Name: \t\t\t Email:  \t\t Room: \t\t Price: \t\t Room Type: \t\t Check in Date: \t\t\t\t Check out Date:");
        AdminResource.displayAllReservations();
    }
    public static void roomCheck(){

    }
    private static boolean isRoomNumberValid(String roomNumber){
        return roomNumber.matches("\\d+");
    }


    private static void addARoom(){
        String roomNumber;
        do{
        System.out.print("Enter room number: ");
        roomNumber = scanner.next();
        if(isRoomNumberValid(roomNumber)) {
            if (roomNumbers.contains(roomNumber)) {
                System.out.println("Room Number already exist, enter a new room number");
            } else {
                roomNumbers.add(roomNumber);
                break;
            }
        }else{
                System.out.println("Enter a valid room number, should be a unique number");
            }
        } while(true);

        RoomType roomType;
        do{
            System.out.print("Enter room type (SINGLE/DOUBLE): ");
            String type = scanner.next();
            if(isRoomTypeValid(type)) {
                roomType = RoomType.valueOf(type.toUpperCase());
                break;
            } else{
                System.out.println("Invalid room type, please enter SINGLE or DOUBLE");
            }
            } while(true);



        double roomPrice;
        do {
            System.out.print("Enter room price: ");
            String priceStr = scanner.next();
            try {
                roomPrice = Double.parseDouble(priceStr);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid price. Please enter a valid number.");
            }
        } while (true);

        Room room = new Room(roomNumber, roomPrice, roomType);
        AdminResource.addRoom(room);
        System.out.println("Room added successfully.");

    }

    private static boolean isRoomTypeValid(String type) {
        try{
            RoomType.valueOf(type.toUpperCase());
            return true;
        }catch (IllegalArgumentException e){
            return false;
        }

    }

    public static void adminMenu(){
        boolean back = false;
        while(!back){
            System.out.println("\n Admin Menu");
            System.out.println("1. See all Customers");
            System.out.println("2. See all rooms");
            System.out.println("3. See all Reservations");
            System.out.println("4. Add a room");
            System.out.println("5. Back to main menu");


            int choice = getUserChoice();


            switch(choice){
                case 1:
                    seeAllCustomers();
                    break;
                case 2:
                    seeAllRooms();
                    break;
                case 3:
                    seeAllReservations();
                    break;
                case 4:
                    addARoom();
                    break;
                case 5:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice, Please try again");
                    break;
            }
        }
    }
}
