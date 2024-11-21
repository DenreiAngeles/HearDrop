package Design;

public class DesignUtils {
    public static void clearScreen(int delay) {
        try {
            Thread.sleep(delay);
            System.out.print("\033[H\033[2J");
            System.out.flush();
        } catch (Exception e) {
            System.out.println("Error clearing screen: " + e.getMessage());
        }
    }

    public static void printMainMenuHeader() {
        clearScreen(1000);
        System.out.println("|////////////////////////////////////////////////////////////////////////////////////////////|");
        System.out.println("|         H E A R D R O P :  D R O P P I N G  H E L P  W H E R E  I T ' S  H E A R D         |");
        System.out.println("|////////////////////////////////////////////////////////////////////////////////////////////|");
    }

    public static void printHeader(String role, String username) {
        clearScreen(1000);
        System.out.println("=======================================================================");
        System.out.println("                W E L C O M E ,  " + role.toUpperCase() + " " + username + "      ");
        System.out.println("-----------------------------------------------------------------------");
    }
}

