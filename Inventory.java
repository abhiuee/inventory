
/**
 * Inventory class that contains information about the inventory
 */
public class Inventory
{
    // instance variables - replace the example below with your own
    private String type;
    private String description;
    private double appraisal;
    private double loan;
    private String loandate;
    private String status;

    /**
     * Null constructor
     */
    public Inventory()
    {
    }
    
    /**
     * Constructor that accepts all variables 
     */
    public Inventory(String type, String description,
                        double appraisal, double loan, 
                        String loandate, String status)
    {
        this.type = type;
        this.description = description;
        this.appraisal = appraisal;
        this.loan = loan;
        this.loandate = loandate;
        this.status = status;
    }

    /**
     * Setters and getters
     */
    public void setType(String type)
    {
        this.type = type;
    }
    
    public String getType()
    {
        return this.type;
    }
    
    public void setDescription(String description)
    {
        this.description = description;
    }
    
    public String getDescription()
    {
        return this.description;
    }
    
    public void setAppraisal(double appraisal)
    {
        this.appraisal = appraisal;
    }
    
    public double getAppraisal()
    {
        return this.appraisal;
    }
    
    public void setLoan(double loan)
    {
        this.loan = loan;
    }
    
    public double getLoan()
    {
        return this.loan;
    }
    
    public void setLoandate(String loandate)
    {
        this.loandate = loandate;
    }
    
    public String getLoandate()
    {
        return this.loandate;
    }
    
    public void setStatus(String status)
    {
        this.status = status;
    }
    
    public String getStatus()
    {
        return this.status;
    }
    
    /**
     * toString function for printList
     */
    public String toString()
    {
        return "Type: " + this.type + "\n" +
               "Description: " + this.description + "\n" +
               "Appraised Value: " + this.appraisal + "\n" +
               "Loan Amount: "  + this.loan + "\n" +
               "Loan Date: " + this.loandate + "\n" +
               "Status: " + this.status + "\n";
    }
    
    /**
     * Prints the information in delimited format to update pawn_inventory.txt file
     */
    public String toDelimitedString()
    {
        return this.type + ";" + this.description + ";" + 
               this.appraisal + ";" + this.loan + ";" +
               this.loandate + ";" + this.status + "\n";
    }
}
