@isTest
public class TestRestrictContactByName {
    @isTest public static void testRestrictContactByName2(){
        Contact con = new Contact(FirstName='Shishir',LastName='INVALIDNAME');
        insert con;
        Test.startTest();
        Database.SaveResult result= Database.insert(con,false);
        Test.stopTest();
        System.assert(!result.isSuccess());
        System.assert(result.getErrors().size() > 0);
        
	}
}