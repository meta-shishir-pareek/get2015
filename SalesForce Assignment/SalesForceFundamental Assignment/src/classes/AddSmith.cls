public class AddSmith {
    public static void addSmith(){
   


// Add a contact to this account.
Contact con = new Contact(
    FirstName='Carol',
    LastName='Smith',
    Phone='(415)555-1212',
    Department='Wingo'
 );
     insert con;
        
    
Lead lead = new Lead(
 FirstName='Carol',
 LastName='Smith',
 Company='Metacube');
    insert lead;
    }
}