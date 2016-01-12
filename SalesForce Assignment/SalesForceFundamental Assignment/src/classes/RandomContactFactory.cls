public class RandomContactFactory {
    public static List<Contact> generateRandomContacts(Integer numberOfContacts,String lastName){
        List<Contact> newContactList = new List<Contact>();
        for(Integer index=0;index<numberOfContacts;index++){
            Contact con = new Contact(FirstName = 'Test' + index,LastName=lastName);
            newContactList.add(con);
        }
        return newContactList;
    }
}