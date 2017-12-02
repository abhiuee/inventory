
/**
 * StoreManagement class loads data, adds items, etc.
 */

import java.io.*;
import java.util.*;

public class StoreManagement
{
    // Data Members
    private ArrayList<Inventory> inventoryData;
    private static final String logFileName = "logfile.txt";
    private static final String inventoryFileName = "pawn_inventory.txt";
    
    /**
     * Null Constructor
     */
    public StoreManagement()
    {
        this.inventoryData = new ArrayList<Inventory>();
    }
    
    /**
     * Main entry function for the program. Loads data and calls showMenu private function
     */
    public void manageStore()
    {
        System.out.println("Loading data....");
        try
        {
            this.loadData();
        } catch (IOException ie)
        {
            System.out.println("IO Exception " + ie);
        }
        this.showMenu();
    }
    
    /**
     * showMenu private function lets user select the action
     */
    private void showMenu()
    {
        Scanner input = new Scanner(System.in);
        while(true)
        {
            System.out.println("---Menu---");
            System.out.println("Select an option: \n" +
                               " 1) Print List\n" +
                               " 2) Add Item\n" +
                               " 3) Change Item Status\n" +
                               " 4) Item Sale\n" + 
                               " 5) Quit\n");
            int selection = input.nextInt();
            input.nextLine();
            switch (selection) {
                case 1:
                    this.printList();
                    break;
                case 2:
                    this.addItem();
                    break;
                case 3:
                    this.changeItem();
                    break;
                case 4:
                    this.saleItem();
                    break;
                case 5:
                    this.exit();
                    break;
                default:
                    System.out.println("Invalid selection");
                    break;
            }
        }
    }
    
    /**
     * Main function to perform sale of an item
     */
    private void saleItem()
    {
        System.out.println("----Sale Item----");
        Scanner input = null;
        Boolean found = false;
        // Print all the items which have status = SA
        for (int i = 0; i < this.inventoryData.size(); i++)
        {
            if (this.inventoryData.get(i).getStatus().equals("SA"))
            {
                System.out.println("Item No: " + Integer.toString(i+1));
                System.out.println(this.inventoryData.get(i));
                found = true;
            }
        }
        // Return if no item found
        if (!found)
        {
            System.out.println("No items are for sale");
            return;
        }
        // Get item number from the user
        input = new Scanner(System.in);
        System.out.println("Enter the Item No: to be sold");
        int selection = input.nextInt();
        input.nextLine();
        // Check if the item number is valid
        if (selection > this.inventoryData.size())
        {
            System.out.println("Wrong selection");
        } else
        {
            // log Changes
            logChanges("Sale Item", this.inventoryData.get(selection-1).toString());
            // remove the item
            this.inventoryData.remove(selection-1);
        }
    }
    
    /**
     * Function to perform exit. Also updates the pawn_inventory.txt file
     */
    private void exit()
    {
        System.out.println("Exiting...");
        File file = new File(inventoryFileName);
        FileWriter fw = null;
        BufferedWriter bw = null;
        try
        {
            if (!file.exists())
            {
                file.createNewFile();
            } 
            fw = new FileWriter(file);
            bw = new BufferedWriter(fw);
            for (Inventory obj : this.inventoryData)
            {
                bw.write(obj.toDelimitedString());
            }
        } catch (IOException ie)
        {
            System.out.println("IO Exception " + ie);
        } finally {
            try
            {
                if (bw != null)
                {
                    bw.close();
                }
                if (fw != null)
                {
                    fw.close();
                }
            } catch (IOException iex)
            {
                System.out.println("IO Exception " + iex);
                    
            }
        }
        
        System.exit(1);
    }
    
    /**
     * Function to perform change the item status action
     */
    private void changeItem()
    {
        System.out.println("----Change Item----");
        Scanner input = null;
        Boolean found = false;
        // Print all the items with status LO
        for (int i = 0; i < this.inventoryData.size(); i++)
        {
            if (this.inventoryData.get(i).getStatus().equals("LO"))
            {
                System.out.println("Item No: " + Integer.toString(i+1));
                System.out.println(this.inventoryData.get(i));
                found = true;
            }
        }
        // Return if none found
        if (!found)
        {
            System.out.println("No items found");
            return;
        }
        // Prompt the user to provide an item number
        input = new Scanner(System.in);
        System.out.println("Enter the Item No: to be changed");
        int selection = input.nextInt();
        input.nextLine();
        // check for validity of item number
        if (selection > this.inventoryData.size())
        {
            System.out.println("Wrong selection");
        } else
        {
            // Log changes
            logChanges("Change Item", this.inventoryData.get(selection-1).toString());
            // Update the status to SA
            this.inventoryData.get(selection-1).setStatus("SA");
        }
    }
    
    /**
     * Function to perform add item action
     */
    private void addItem()
    {
        Scanner input = new Scanner(System.in);
        System.out.println("----Add Item----");
        // Prompt user for all the required inputs and do error checking on them
        System.out.println("Enter the item type (HA for hardware"
                            + ",JE for jewelry and MI for misc):");
        String itemtype = input.nextLine();
        if (!(itemtype.equals("JE") || itemtype.equals("HA") || itemtype.equals("MI")))
        {
            System.out.println("Wrong itemtype");
            return;
        }
        System.out.println("Enter description of item: ");
        String description = input.nextLine();
        System.out.println("Enter the loan date in YYYY-MM-DD format:");
        String loandate = input.nextLine();
        System.out.println("Enter the loan status:");
        String status = input.nextLine();
        if (!(status.equals("SA") || status.equals("LO")))
        {
            System.out.println("Wrong status");
            return;
        }
        System.out.println("Enter the appraisal value");
        double appraisal = Double.parseDouble(input.nextLine());
        double loanvalue = 0.0;
        // Compute loan value based on appraised value and itemtype
        if (itemtype.equals("HA"))
        {
            loanvalue = 0.3*appraisal;
        }
        if (itemtype.equals("JE"))
        {
            loanvalue = 0.2*appraisal;
        }
        if (itemtype.equals("MI"))
        {
            loanvalue = 0.15*appraisal;
        }
        Inventory obj = new Inventory(itemtype, description, appraisal, loanvalue, loandate, status);
        this.inventoryData.add(obj);
        // log changes
        logChanges("Add Item", obj.toString());
    }
    
    /**
     * Function that performs printList operation
     */
    private void printList()
    {
        for (Inventory inv : this.inventoryData)
        {
            System.out.println(inv.toString());
        }
    }
    
    /**
     * Function that logs the changes to the logfile. Takes in the operation and the object.toString() as parameters
     */
    private void logChanges(String operation, String information)
    {
        File file = new File(logFileName);
        FileWriter fw = null;
        BufferedWriter bw = null;
        try
        {
            if (!file.exists())
            {
                file.createNewFile();
            } 
            fw = new FileWriter(file, true);
            bw = new BufferedWriter(fw);
            bw.write(operation);
            bw.write("\n");
            bw.write(information);
        } catch (IOException ie)
        {
            System.out.println("IO Exception " + ie);
        } finally {
            try
            {
                if (bw != null)
                {
                    bw.close();
                }
                if (fw != null)
                {
                    fw.close();
                }
            } catch (IOException iex)
            {
                System.out.println("IO Exception " + iex);
                    
            }
        }   
    }
    
    /**
     * Function that performs initial data loading
     */
    private void loadData() throws IOException
    {
        File file = new File(inventoryFileName);
        FileReader in = null;
        BufferedReader br = null;
        String line = null;
        try 
        {
            in = new FileReader(file);
            br = new BufferedReader(in);
            while ((line = br.readLine()) != null)
            {
                String [] tokens = line.split(";");
                if (tokens.length < 6)
                {
                    System.out.println("Bad data");
                } else
                {
                    Inventory newObj = new Inventory(tokens[0], tokens[1], Double.parseDouble(tokens[2]), Double.parseDouble(tokens[3]), tokens[4], tokens[5]);
                    this.inventoryData.add(newObj);
                }
            }
        } catch (FileNotFoundException fnfe)
        {
            System.out.println("Inventory file not found " + fnfe);
        }
        catch(IOException ioe)
        {
            System.out.println("I/O Exception: " + ioe); 
        }
        finally {
            if (br != null)
            {
                br.close();
            }
            if (in != null)
            {
                in.close();
            }
        }
    }
}
