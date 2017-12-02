
/**
 * Class that contains the main method 
 */
public class StoreManagementApp
{
    public static void main(String args[])
    {
        System.out.println("Welcome to the inventory management Software");
        // Create an object for StoreManagement Class
        StoreManagement mgr = new StoreManagement();
        // Call manageStore function to interact with inventory
        mgr.manageStore();
    }
}
