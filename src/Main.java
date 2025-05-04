import services.*;
import models.Customer;

import java.sql.Date;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        boolean isRunning = true;
        while (isRunning) {
            System.out.println("✈️ ОНЛАЙН РЕЗЕРВАЦИЯ НА САМОЛЕТНИ БИЛЕТИ");
            System.out.println("-------------ДОБРЕ ДОШЛИ!-------------");
            System.out.println("\n🔹 Изберете опция:");
            System.out.println("1. Регистрация на нов потребител");
            System.out.println("2. Вход в системата");
            System.out.println("3. Изход");

            System.out.print("👉 Въведете номер на опция: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    CustomerService.addCustomer(scanner);
                    break;
                case 2:
                    loginUser(scanner);
                    break;
                case 3:
                    System.out.println("🔚 Изход от системата. ----------БЛАГОДАРИМ ВИ!---------------");
                    isRunning = false;
                    break;
                default:
                    System.out.println("⚠ Невалиден избор, опитайте пак.");
            }
        }
    }


    // Логин
    private static void loginUser(Scanner scanner) {
        System.out.println("\n🔑 Вход в системата:");
        System.out.print("👉 Имейл: ");
        String loginEmail = scanner.nextLine();
        System.out.print("👉 Парола: ");
        String loginPassword = scanner.nextLine();

        String role = LoginService.authenticate(loginEmail, loginPassword);

        if (role.equals("user")) {
            System.out.println("✅ Успешно влизане като потребител!");
            int customerId = CustomerService.getCustomerIdByEmail(loginEmail);  // ще трябва да добавим този метод
            customerMenu(scanner, customerId);
        } else if (role.equals("admin")) {
            System.out.println("✅ Успешно влизане като администратор!");
            adminMenu(scanner);
        } else {
            System.out.println("❌ Грешен имейл или парола.");
        }
    }


    // Потребителско меню
    private static void customerMenu(Scanner scanner, int customerId) {
        boolean isRunning = true;
        while (isRunning) {
            System.out.println("\n🔸 Потребителско меню:");
            System.out.println("1. Преглед на полети");
            System.out.println("2. Преглед на летища");
            System.out.println("3. Преглед на авикомпании");
            System.out.println("4. Преглед на самолети");
            System.out.println("5. Преглед на агенции");
            System.out.println("6. Търсене на полети по дестинация");
            System.out.println("7. Търсене на полети по дата");
            System.out.println("8. Провери свободни места за полет");
            System.out.println("9. Направи резервация");
            System.out.println("10. Изтрий резервация");
            System.out.println("11. Моите резервации");
            System.out.println("12. Провери статус на резервация по код");
            System.out.println("13. Изход");

            System.out.print("👉 Избор: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    FlightService.showAllFlights();
                    break;
                case 2:
                    AirportService.showAllAirports();
                    break;
                case 3:
                    AirlineService.showAllAirlines();
                    break;
                case 4:
                    AirplaneService.showAllAirplanes();
                    break;
                case 5:
                    AgenciesServices.listAgencies();
                    break;
                case 6:
                    System.out.print("👉 Въведи име на летище за пристигане: ");
                    String destinationName = scanner.nextLine();
                    FlightService.searchFlightsByDestinationName(destinationName);
                    break;
                case 7:
                    try {
                        System.out.print("Въведи дата на полета (YYYY-MM-DD): ");
                        String dateStr = scanner.nextLine();
                        Date date = Date.valueOf(dateStr);
                        FlightService.searchFlightsByDate(date);
                    } catch (IllegalArgumentException e) {
                        System.out.println("⚠ Грешен формат на датата!");
                    }
                    break;
                case 8:
                    BookingService.checkAvailableSeats(scanner);
                    break;
                case 9:
                    BookingService.addBooking(scanner, customerId);
                    break;
                case 10:
                    BookingService.deleteBooking(scanner);
                case 11:
                    BookingService.viewCustomerBookings(customerId);
                    break;
                case 12:
                    System.out.print("Въведи код на резервацията: ");
                    String bookingCode = scanner.nextLine();
                    BookingService.checkBookingStatusByCode(bookingCode);
                    break;
                case 13:
                    System.out.println("🔚 Излизане от потребителското меню.");
                    isRunning = false;
                    break;
                default:
                    System.out.println("⚠ Невалиден избор.");
            }
        }
    }

    // Администраторско меню
    private static void adminMenu(Scanner scanner) {
        boolean isRunning = true;
        while (isRunning) {
            System.out.println("\n🔸 Администраторско меню:");
            System.out.println("1. Управление на потребители");
            System.out.println("2. Преглед на всички резервации");
            System.out.println("3. Потвърди резервации");
            System.out.println("4. Управление на агенции");
            System.out.println("5. Управление на авиокомпании");
            System.out.println("6. Управление на самолети");
            System.out.println("7. Управление на летища");
            System.out.println("8. Управление на полети");
            System.out.println("9. Изход");

            System.out.print("👉 Избор: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    CustomerService.manageCustomers(scanner);
                    break;
                case 2:
                    BookingService.viewAllBookings();
                    break;
                case 3:
                    BookingService.manageBookings(scanner);
                    break;
                case 4:
                    AgenciesServices.addAgencies(scanner);
                    break;
                case 5:
                    AirlineService.addAirlines(scanner);
                    break;
                case 6:
                    AirplaneService.addAirplanes(scanner);
                    break;
                case 7:
                    AirportService.addAirports(scanner);
                    break;
                case 8:
                    FlightService.addFlights(scanner);
                    break;
                case 9:
                    System.out.println("🔚 Излизане от администраторското меню.");
                    isRunning = false;
                    break;
                default:
                    System.out.println("⚠ Невалиден избор.");

            }
        }
    }
}
