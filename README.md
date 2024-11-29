![Project Logo](images/1.png)
## Project Overview 📖
**HearDrop** is a console-based donation matching system aimed at connecting donors with recipients. This project aligns with two Sustainable Development Goals:
- **SDG 2: Zero Hunger** 🥗
- **SDG 12: Responsible Consumption and Production** ♻️

The system simplifies the donation process, ensuring efficient distribution of resources and minimizing waste.

---
![Table of Contents](images/2.png)
## Table of Contents 📚
1. [Features](#features-✨)
   - [Donor Features](#donor-features-🫴)
   - [Recipient Features](#recipient-features-🫶)
2. [How It Works](#how-it-works-⚙️)
   - [User Registration](#Step-1:-User-Registration)
   - [User Login]()
   - [Donor Workflow]()
   - [Recipient Workflow]()
   - [Database Management]()
   - [Status Updates]()
3. [Tech Used](#tech-used-💻)
4. [OOP Principles](#oop-principles-🧩)
   - [Encapsulation]()
   - [Inheritance]()
   - [Polymorphism]()
   - [Abstraction]()
5. [Setup Instructions](#setup-instructions-🔧)
   - [Prerequisites]()
   - [Installation]()
6. [System Modules](#system-modules-🗂️)
   - [DAO (Data Access Object)]()
   - [Database]()
   - [Main]()
   - [Menus]()
   - [Models]()
   - [Service]()
   - [Utils]()
7. [SDG Implementation](#sdg-implementation-🌍)
8. [Future Enhancements](#future-enhancements-🚀)
9. [Contributors](#contributors-🙌)
10. [Back to Top](#heardrop-dropping-help-where-its-heard-🌟)

---
## Features ✨
![Features](images/4.png)

### Donor Features 🫴
- **Donate Item**: Add donation details such as item name, description, quantity and pickup location.
- **View My Donated Items**: See a list of your donations and their statuses.
- **Edit Donated Items**: Update item details or pickup locations.
- **Remove Donated Items**: Remove your donations (can only be used if the donation status is still availble)

### Recipient Features 🫶
- **View Available Donations**: Browse donations listed by donors that are still available to be reserved or claimed.
- **Reserve Item**: Reserve an item with a specified pickup time.
- **View My Reserved Items**: Track reserved items and their pickup details.
- **Remove My Reserved Items**: Remove your reserved donations from your reservation list and will be updated as available again.

---
## How It Works ⚙️
![How it Works](images/5.png)

### Step 1: User Registration  
- Users register to the system with a unique username and a password.

### Step 2: User Login  
- Users log in with their registered credentials, then selecting their role as: **Donor** or **Recipient**.

### Step 3: Donor Workflow  
1. **Add Donation**: Donors provide item details (e.g., name, description, quantity, and pickup location).  
2. **View Donations**: Track all donated items and their statuses (available, reserved, etc.).  
3. **Edit Donations**: Modify item details or update pickup location.

### Step 4: Recipient Workflow  
1. **View Donations**: Recipients browse available donations.  
2. **Reserve Items**: Reserve an item and specify a pickup time.  
3. **Track Reservations**: View reserved items and their pickup details.

### Step 5: Database Management  
- All user and donation data are stored in MySQL tables, ensuring persistence and integrity.  

### Step 6: Status Updates  
- Reserved items are marked with the recipient's username and pickup schedule.  
- Donors can view the updated status of their donations.



---
## Tech Used 💻
![Tech Used](images/6.png)

- **Programming Language**: Java ☕
- **Database Management System**: MySQL 🛢️
- **Utilities**: Java JDBC (Java SQL Connector)

---
## OOP Principles 🧩
![OOP Principles](images/7.png)

### Encapsulation
Encapsulation restricts direct access to some of an object's components, ensuring sensitive data is private and accessible through getter and setter methods. 

Example: `User` class `username` and `password`
```java
public class User {
    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
```
- By making the `username` and `password` fields private and providing public getters and setters, we control how these attributes are accessed and modified.
---
### Inheritance
Inheritance allows a class to derive properties and behaviors from another class.

Example: `DonorMenu` and `RecipientMenu` Classes Extending `BaseMenu`

- `BaseMenu.java`
```java
public abstract class BaseMenu {
    public abstract void displayMenu();

    public abstract void handleChoice(int choice);

    public void show() {
        while (true) {
            displayMenu();
            System.out.print("Enter your choice: ");
            int choice = Integer.parseInt(scanner.nextLine());

            if (choice == getExitChoice()) {
                return;
            }

            handleChoice(choice);
        }
    }
}
```
- `DonorMenu` extends `BaseMenu`
```java
public class DonorMenu extends BaseMenu {
    @Override
    public void displayMenu() {
        DesignUtils.printHeader("donor", username);
        System.out.println("\n--- Donor Menu ---\n");
        System.out.println("1. Donate Item");
        System.out.println("2. View My Donated Items");
        System.out.println("3. Edit Donated Items");
        System.out.println("4. Remove Donated Item");
        System.out.println("5. Back to Main Menu");
    }

    @Override
    public void handleChoice(int choice) {
        switch (choice) {
            case 1:
                donorService.donateItem(donorId, username);
                break;
            case 2: 
                donorService.viewMyDonatedItems(donorId);
                break;
            case 3:
                donorService.editDonatedItem(donorId);
                break;
            case 4:
                donorService.removeDonatedItem(donorId);
                break;
            default: 
                System.out.println("Invalid choice. Please try again.");
        }
    }
}
```
- `RecipientMenu` extends `BaseMenu`
```java
public class RecipientMenu extends BaseMenu {
    @Override
    public void displayMenu() {
        DesignUtils.printHeader("recipient", recipientUsername);
        System.out.println("\n--- Recipient Menu ---\n");
        System.out.println("1. View Available Items");
        System.out.println("2. Reserve Item");
        System.out.println("3. View My Reserved Items");
        System.out.println("4. Remove Reserved Item");
        System.out.println("5. Back to Main Menu");
    }

    @Override
    public void handleChoice(int choice) {
        switch (choice) {
            case 1: 
                recipientService.viewAvailableItems();
                break;
            case 2: 
                recipientService.reserveItem(recipientUsername);
                break;
            case 3: 
                recipientService.viewMyReservedItems(recipientUsername);
                break;
            case 4: 
                recipientService.removeReservedItem(recipientUsername);
                break;
            default: 
                System.out.println("Invalid choice. Please try again.");
        }
    }
}
```
- Both `DonorMenu` and `RecipientMenu` inherit the basic `show()` functionality from `BaseMenu` but override the `displayMenu()` method to show specific options for donors and recipients.
---
### Polymorphism
Polymorphism allows one interface to be used for different data types or objects. Methods in derived classes can override the parent class's methods to provide specific implementations.

Example: `DonorMenu` and `RecipientMenu` overriding `displayMenu` from `BaseMenu`

- `BaseMenu`
```java
public abstract class BaseMenu {
    protected static Scanner scanner = new Scanner(System.in);

    public abstract void displayMenu();

    public abstract void handleChoice(int choice);

    public void show() {
        while (true) {
            displayMenu();
            System.out.print("Enter your choice: ");
            int choice = Integer.parseInt(scanner.nextLine());

            if (choice == getExitChoice()) {
                return;
            }

            handleChoice(choice);
        }
    }
}
```
- `DonorMenu` and `RecipientMenu` overriding `displayMenu`
```java
public class DonorMenu extends BaseMenu {
    @Override
    public void displayMenu() {
        DesignUtils.printHeader("donor", username);
        System.out.println("\n--- Donor Menu ---\n");
        System.out.println("1. Donate Item");
        System.out.println("2. View My Donated Items");
        System.out.println("3. Edit Donated Items");
        System.out.println("4. Remove Donated Item");
        System.out.println("5. Back to Main Menu");
    }
}

public class RecipientMenu extends BaseMenu {
    @Override
    public void displayMenu() {
        DesignUtils.printHeader("recipient", recipientUsername);
        System.out.println("\n--- Recipient Menu ---\n");
        System.out.println("1. View Available Items");
        System.out.println("2. Reserve Item");
        System.out.println("3. View My Reserved Items");
        System.out.println("4. Remove Reserved Item");
        System.out.println("5. Back to Main Menu");
    }
}
```
- When calling `show()`
```java
    BaseMenu donorMenu = new DonorMenu(user.getId(), user.getUsername());
    donorMenu.show();

    BaseMenu recipientMenu = new RecipientMenu(user.getUsername());
    recipientMenu.show(); 
```

- By defining `BaseMenu` as the reference type for both `DonorMenu` and `RecipientMenu`, the same interface (i.e., `show()`) can be used to call different implementations of `displayMenu()` and `handleChoice()`, depending on the actual object type. This allows for dynamic behavior, where the menu is displayed differently for donors and recipients, even though they are both being handled by the `BaseMenu` reference.
---
### Abstraction
Abstraction hides implementation details from the user and only exposes essential functionalities.

Example: `BaseDAO` implementing abstraction
```java
public abstract class BaseDAO<T> {
    protected Connection connection;

    protected abstract T mapResultSetToObject(ResultSet rs) throws SQLException;

    public BaseDAO() {
        connection = HearDropDB.getConnection();
    }

    // Other CRUD methods here
    // They rely on the mapResultSetToObject method for retrieving data
}
```
- `DonationDAO` implementing mapResultSetToObject method
```java
public class DonationDAO extends BaseDAO<Donation> {
    @Override
    protected Donation mapResultSetToObject(ResultSet rs) throws SQLException {
        return new Donation(
            rs.getInt("id"),
            rs.getInt("donor_id"),
            rs.getString("item_name"),
            rs.getString("description"),
            rs.getInt("quantity"),
            rs.getString("pickup_location"),
            rs.getString("status"),
            rs.getString("recipient_username"),
            rs.getString("pickup_datetime"),
            rs.getString("donor_username")
        );
    }

    // Other CRUD methods specific to donations
}

```

- `BaseDAO` abstracts common database operations like `add`, `getById`, `getList`, `update`, and `remove`, but the actual logic of mapping a `ResultSet` to a specific object (like `Donation` or `User`) is left to be implemented by subclasses. This approach encapsulates database operations while allowing specific entity classes to define how their data is mapped from the database.


---
## Setup Instructions 🔧
![Setup Instructions](images/8.png)

### Prerequisites
You must have downloaded and has set-up the following in order to continue:
- Java Development Kit (JDK)
- MySQL Server and JDBC (Java Connector)
- IDE or Terminal

### Installation
1. Clone the repository:  
   ```bash
   git clone https://github.com/username/HearDrop.git
2. Navigate to the project directory:
   ```bash
   cd HearDrop
3. Compile and run the program:
   ```bash
   javac Main.java
   java Main
   ```
---
## System Modules 🗂️
![System Modules](images/9.png)
### System Directory Structure
```plaintext
├── src/
|   ├── DAO/
|   |   ├── BaseDAO.java
|   |   ├── DonationDAO.java
|   |   └── UserDAO.java
|   |
|   ├── Database/
|   |   └── HearDropDB.java
|   |
|   ├── Main/
|   |   └── Main.java
|   |
|   ├── Menus/
|   |   ├── BaseMenu.java
|   |   ├── DonorMenu.java
|   |   └── RecipientMenu.java
|   |
|   ├── Models/
|   |   ├── User.java
|   |   └── Donation.java
|   |
|   ├── Service/
|   |   ├── DonationService.java
|   |   ├── RecipientService.java
|   |   └── UserService.java
|   |
|   └── Utils/
|       ├── DesignUtils.java
|       └── LogUtils.java
|
```
This project contains seven (7) packages and fifteen (15) modules all working together to run the system.
### Directory Explanation
- **DAO (Data Access Object)** -
    Contains data access objects for handling database operations related to `User` and `Donation`. The `BaseDAO` provides common CRUD (Create, Read, Update, Delete) functionalities.

- **Database** -
    Houses the `HearDropDB` class for database connection and setup.

- **Main** -
    Includes the entry point of the program `Main.java`.

- **Menus** -
    Contains classes for user interface logic tailored to roles such as `DonorMenu` and `RecipientMenu` modeled with `BaseMenu`.

- **Models** -
    Represents entities such as `User` and `Donation`.

- **Service** -
    Provides business logic, such as handling donations (`DonationService`), recipient operations (`RecipientService`) and user operations (`UserService`).

- **Utils** -
    Contains utility classes like `DesignUtils` for formatting and `LogUtils` for error logging.

### Classes 

| **Class**           | **Package**  | **Description**                                                                                                                                                                                                 |
|----------------------|--------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `BaseDAO<T>`         | DAO          | Abstract class that provides reusable CRUD operations (`add`, `getById`, `getList`, `update`, `remove`). Subclasses implement `mapResultSetToObject` to define how to map database results to specific objects. |
| `DonationDAO`        | DAO          | Extends `BaseDAO` to handle operations specific to `Donation` objects, such as retrieving donations by donor or recipient and updating donation details.                                                       |
| `UserDAO`            | DAO          | Extends `BaseDAO` to manage operations related to `User`, such as user registration, login, and retrieving user details.                                                                                      |
| `HearDropDB`         | Database     | Provides a singleton database connection for all DAO classes to interact with the database.                                                                                                                   |
| `Donation`           | Models       | Represents a donation item, including its details like `item_name`, `quantity`, `pickup_location`, `status`, and donor/recipient information.                                                                |
| `User`               | Models       | Represents a user in the system with attributes like `id`, `username`, and `password`.                                                                                                                        |
| `BaseMenu`           | Menus        | Abstract class for menu interfaces. Defines a structure for displaying menus, handling user choices, and managing menu navigation.                                                                             |
| `DonorMenu`          | Menus        | Extends `BaseMenu` to provide a menu interface for donors, including options to donate items, view donations, edit donations, and remove items.                                                               |
| `RecipientMenu`      | Menus        | Extends `BaseMenu` to provide a menu interface for recipients, including options to view available items, reserve items, and manage their reservations.                                                        |
| `DesignUtils`        | Utils        | Provides utility methods for styling and formatting console output, such as headers and dividers.                                                                                                              |
| `LogUtils`           | Utils        | Provides utility methods for logging errors and information to a file.                                                                                                                                         |
| `Main`               | Main         | Entry point of the application. Handles user authentication and role-based menu redirection (donor or recipient).                                                                                              |
| `DonationService`    | Service      | Contains business logic for managing donations, such as creating, updating, and deleting donations, using `DonationDAO`.                                                                                       |
| `UserService`        | Service      | Handles user-related business logic, such as registration, login validation, and retrieving user details, using `UserDAO`.                                                                                     |
| `RecipientService`   | Service      | Contains business logic for managing recipient-related actions, such as reserving items, viewing available donations, and managing reservations, using `DonationDAO`.                                          |

---
## SDG Implementation 🌍
![SDG Implementation](images/10.png)
1. **Zero Hunger**: Facilitates the distribution of food and resources to those in need.
2. **Responsible Consumption and Production**: Reduces waste by ensuring donations reach recipients effectively.
---
## Future Enhancements 🚀
![SDG Implementation](images/11.png)
- Add real-time notifications for new donations or reservations.
- Implement a web-based interface for broader accessibility.
- Enhance reporting features for tracking donations and reservations.
---
## Project Developers 🙌



