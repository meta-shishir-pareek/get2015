public class ContactandLeadSearch {
    public static List<List<SObject>> searchContactsAndLeads(String param){
      List<List<SObject>> searchList = [FIND :param IN ALL FIELDS RETURNING Contact, Lead];
        return searchList;
    }
    
}