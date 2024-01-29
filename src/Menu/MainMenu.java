package Menu;

import api.AdminResource;
import api.HotelResource;
import model.Customer;
import model.IRoom;
import model.Reservation;

import java.text.DateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MainMenu {
    private static final HotelResource hotelResource = new HotelResource();
    private static final AdminResource adminResource = new AdminResource();
    public static Scanner scanner = new Scanner(System.in);

    public static int getUserChoice() {
        System.out.println("Enter your choice");
        while (!scanner.hasNextInt()) {
            scanner.next();
            System.out.println("Invalid choice, Please enter a number");
        }
        return scanner.nextInt();
    }

    public static Date getDateFromUserInput() {
        Date date = null;
        boolean validDate = false;
        final String DATEPATTERN = "^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/\\d{4}$";
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Pattern pattern = Pattern.compile(DATEPATTERN);

        while(!validDate) {
            String input = scanner.next();

                Matcher matcher = pattern.matcher(input);
                if(matcher.matches()) {
                    validDate = true;
                    try {
                        date = dateFormat.parse(input);
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                } else
                    System.out.println("Invalid Date format, please enter the date (dd/mm/yyyy)");

        }

        return date;
    }

    private static void findAndReserveRoom() {
        System.out.println("Enter check in date(dd/mm/yy)");
        Date checkInDate = getDateFromUserInput();
        System.out.println("Enter check out date(dd/mm/yyyy");
        Date checkOutDate = getDateFromUserInput();
        while(checkInDate.after(checkOutDate)) {
            System.out.println("Check in date is after check out date");
            mainMenu();
        }
            Collection<IRoom> availableRooms = HotelResource.findARoom(checkInDate, checkOutDate);
            if (availableRooms.isEmpty()) {
                System.out.println("No Rooms are available for the selected date range");
            } else {
                System.out.println("Available rooms ");
                System.out.println("Room Number:  Price:  Room Type:");
                for (IRoom room : availableRooms) {
                    System.out.println(room.toString());
                }
                System.out.println("Enter the room number to reserve");
                String roomNumber = scanner.next();
                IRoom selectedRoom = HotelResource.getRoom(roomNumber);
                if (selectedRoom == null) {
                    System.out.println("Invalid room number");
                } else {
                    System.out.println("Enter the email");
                    String customerEmail = scanner.next();
                    while (!Customer.isValidEmail(customerEmail)) {
                        System.out.println("Invalid email format, try again with Example: name@domain.com");
                        customerEmail = scanner.next();
                    }
                    Customer customer = HotelResource.getCustomer(customerEmail);
                    if (customer == null) {
                        System.out.println("Customer does not exist. Please create an Account");

                        System.out.println("Enter First Name ");
                        String firstName = scanner.next();
                        System.out.println("Enter Last Name ");
                        String lastName = scanner.next();
                        HotelResource.createACustomer(customerEmail, firstName, lastName);
                        customer = HotelResource.getCustomer(customerEmail);
                    }
                    Reservation reservation = HotelResource.bookARoom(customerEmail, selectedRoom, checkInDate, checkOutDate);
                    if (reservation == null)
                        return;
                    System.out.println("Reservation successfully created");
                    System.out.println("\t Name: \t\t\t Email:  \t\t Room: \t\t Price: \t\t Room Type: \t\t Check in Date: \t\t\t\t Check out Date:");
                    System.out.println(reservation.toString());
                }
            }

    }

    private static void seeMyReservations() {
        System.out.println("Enter your email");
        String customerEmail = scanner.next();
        Collection<Reservation> reservations = HotelResource.getCustomerReservations(customerEmail);
        if (reservations.isEmpty()) {
            System.out.println("No reservation found");
        } else {
            System.out.println("---------Reservation Info----------");
            System.out.println("\t Name: \t\t\t Email:  \t\t Room: \t\t Price: \t\t Room Type: \t\t Check in Date: \t\t\t\t Check out Date:");
            for (Reservation reservation : reservations) {
                System.out.println(reservation.toString());
            }
        }
    }

    private static void createAccount() {
        System.out.println("Enter your email");
        String customerEmail = scanner.next();
        while(!Customer.isValidEmail(customerEmail)){
            System.out.println("Invalid email format, try again with Example: name@domain.com");
            customerEmail = scanner.next();
        }

        Customer customer = HotelResource.getCustomer(customerEmail);



            if (customer != null) {
                System.out.println("Account already exist with that email");

            } else
            {
                System.out.println("Enter the first name");
                String firstName = scanner.next();

                while(!isValidName(firstName)){
                    System.out.println("Invalid name format, try again with Example: name/Name");
                    firstName = scanner.next();
               }
                System.out.println("Enter the last name");
                String lastName = scanner.next();
                while(!isValidName(lastName)){
                   System.out.println("Invalid name format, try again with Example: name/Name");
                   lastName = scanner.next();
                }
                HotelResource.createACustomer(customerEmail, firstName, lastName);
                System.out.println("Successful account created");
            }



    }
    private static boolean isValidName(String name) {
        String regex = "^[a-zA-Z ]+$";
        return name.matches(regex);
    }

    public static void mainMenu() {
        boolean exit = false;


        while (!exit) {
            System.out.println("\n Main Menu");
            System.out.println("1. Find and reserve a room");
            System.out.println("2. See my reservations");
            System.out.println("3. Create an account");
            System.out.println("4. Admin");
            System.out.println("5. Exit");


            int choice = getUserChoice();


            switch (choice) {
                case 1:
                    findAndReserveRoom();
                    break;
                case 2:
                    seeMyReservations();
                    break;
                case 3:
                    createAccount();
                    break;
                case 4:
                    AdminMenu.adminMenu();
                    break;
                case 5:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid option, please choose again");
                    break;

            }
            System.out.println();
        }
        scanner.close();
    }
}

