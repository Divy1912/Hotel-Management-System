import javax.swing.plaf.nimbus.State;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.Connection;
import java.util.Scanner;
public class Main{

    private static final String url = "jdbc:mysql://localhost:3306/hotel_db";
    private static final String id = "root";
    private static final String pass = "123456";

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch (ClassNotFoundException e){
            System.out.println(e.getMessage());
        }

        try{
            Connection connection = DriverManager.getConnection(url,id,pass);
            Statement stmt = connection.createStatement();
            while(true){
                System.out.println();
                System.out.println("Welcome to Hotel Management System!");
                Scanner scanner = new Scanner(System.in);
                System.out.println("1. Reserve a room");
                System.out.println("2. View Reservation");
                System.out.println("3. Get Room Number");
                System.out.println("4. Update Reservation");
                System.out.println("5. Delete Reservation");
                System.out.println("0. Exit");
                System.out.print("Choose an option: ");
                int choice = scanner.nextInt();
                switch(choice){
                    case 1:
                        reserveRoom(scanner, stmt);
                        break;
                    case 2:
                        viewReservations(stmt);
                        break;
                    case 3:
                        getRoomNumber(stmt, scanner);
                        break;
                    case 4:
                        updateInfo(connection, stmt, scanner);
                        break;
                    case 5:
                        delete(connection, stmt, scanner);
                        break;
                    case 0:
                        exit();
                        scanner.close();
                        return;
                }

            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        catch (InterruptedException e){
            throw new RuntimeException(e);
        }
    }

    private static void reserveRoom(Scanner scanner, Statement stmt){
        try{
            scanner.nextLine();
            System.out.print("Enter guest name: ");
            String name = scanner.nextLine();

            System.out.print("Enter room number: ");
            int roomNumber = scanner.nextInt();
            System.out.print("Enter contact number: ");
            String contactNumber = scanner.next();

            String sql = "insert into reservations(guest_name,room_number,contact_number) values('"+name+"',"+roomNumber+",'"+contactNumber+"')";

                int i = stmt.executeUpdate(sql);
                if (i>0){
                    System.out.println("Reservation Successful!");
                }else {
                    System.out.println("Reservation failed");
                }

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    private static void viewReservations(Statement stmt){
        String sql = "select reservation_id, guest_name, room_number, contact_number, reservation_date from reservations";
        try{
            ResultSet rs = stmt.executeQuery(sql);
            System.out.println("Current Reservations:");
            System.out.println("+-------------------+-------------------+-------------------+------------------------+--------------------------+");
            System.out.println("| Reservation ID    | Guest             | Room Number       | Contact Number         | Reservation Date         |");
            System.out.println("+-------------------+-------------------+-------------------+------------------------+--------------------------+");

            while (rs.next()) {
                int reservationId = rs.getInt("reservation_id");
                String guestName = rs.getString("guest_name");
                int roomNumber = rs.getInt("room_number");
                String contactNumber = rs.getString("contact_number");
                String reservationDate = rs.getTimestamp("reservation_date").toString();

                // Format and display the reservation data in a table-like format
                System.out.printf("| %-17d | %-17s | %-17d | %-22s | %-24s |\n",
                        reservationId, guestName, roomNumber, contactNumber, reservationDate);
            }

            System.out.println("+-------------------+---------------+------------------+------------------------+--------------------------+");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void getRoomNumber(Statement stmt, Scanner scanner){
        try{
            System.out.print("Enter reservation id: ");
            int id = scanner.nextInt();
            System.out.print("Enter guest name: ");
            String name = scanner.next();

            String query = "select room_number from reservations where reservation_id = "+id+" and guest_name = '"+name+"'  ";
            ResultSet rs = stmt.executeQuery(query);
            if(rs.next()){
                int room = rs.getInt("room_number");
                System.out.println("Room number for reservation id = "+id+" and guest name "+name+" is: "+room);
            }else System.out.println("Room number for the given ID and guest name not found");
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    private static void updateInfo(Connection connection,Statement stmt, Scanner scanner){
        try{
            System.out.print("Enter reservation id: ");
            int id = scanner.nextInt();
            if (!reservationExists(connection, id)) {
                System.out.println("Reservation not found for the given ID.");
                return;
            }
            while (true){
                System.out.println("What do you want to update?");
                System.out.println("1. Name");
                System.out.println("2. Room number");
                System.out.println("3. Contact number");
                System.out.println("4. Done");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                if (choice == 4){
                    break;
                }
                switch (choice){
                    case 1:
                        scanner.nextLine();
                        System.out.print("Enter new name: ");
                        String name = scanner.nextLine();
                        String query = "UPDATE reservations SET guest_name = '" + name+ "' WHERE reservation_id = " + id;
                        int i = stmt.executeUpdate(query);
                        if(i>0){
                            System.out.println("Name updated successfully");
                        }else {
                            System.out.println("Sorry cannot update the name at this time please try again");
                        }
                        break;
                    case 2:
                        System.out.print("Enter new room number: ");
                        int room = scanner.nextInt();
                        String query1 = "UPDATE reservations SET room_number = " + room+ " WHERE reservation_id = " + id;
                        int j = stmt.executeUpdate(query1);
                        if(j>0){
                            System.out.println("Room number updated successfully");
                        }else {
                            System.out.println("Sorry cannot update the room number at this time please try again");
                        }
                        break;
                    case 3:
                        System.out.print("Enter new contact number: ");
                        String cn = scanner.next();
                        String query2 = "UPDATE reservations SET contact_number = '" +cn+ "' WHERE reservation_id = " + id;
                        int k = stmt.executeUpdate(query2);
                        if(k>0){
                            System.out.println("Contact number updated successfully");
                        }else {
                            System.out.println("Sorry cannot update the contact number at this time please try again");
                        }
                        break;
                    default:
                        System.out.println("Wrong choice, Please select from (1,2,3)");
                }
            }
            System.out.println("All changes are made successfully");
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    private static void delete(Connection connection,Statement stmt, Scanner scanner){
        try{
            System.out.print("Enter reservation id to be deleted: ");
            int id = scanner.nextInt();
            if (!reservationExists(connection, id)) {
                System.out.println("Reservation not found for the given ID.");
                return;
            }
            String query = "delete from reservations where reservation_id = "+id;
            int i = stmt.executeUpdate(query);
            if(i>0){
                System.out.println("Reservation deleted successfully!");
            }
            else {
                System.out.println("Cannot delete reservation");
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    private static boolean reservationExists(Connection connection, int reservationId) {
        try {
            String sql = "SELECT reservation_id FROM reservations WHERE reservation_id = " + reservationId;

            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {

                return resultSet.next(); // If there's a result, the reservation exists
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Handle database errors as needed
        }
    }

    public static void exit() throws InterruptedException {
        System.out.print("Exiting System");
        int i = 5;
        while(i!=0){
            System.out.print(".");
            Thread.sleep(1000);
            i--;
        }
        System.out.println();
        System.out.println("ThankYou For Using Hotel Reservation System!!!");
    }

}