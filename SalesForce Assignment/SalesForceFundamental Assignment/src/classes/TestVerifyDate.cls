@isTest
public class TestVerifyDate {
    @isTest
    public static void testCheckDates(){
        Date date1=Date.newInstance(2015, 10, 30);
        Date date2=Date.newInstance(2016, 01, 14);
        date testDate= VerifyDate.CheckDates(date1, date2);
        Date date3=Date.newInstance(2015, 10, 31);
        System.assertEquals(date3, testDate);
    }
    
     @isTest
    public static void testCheckDates2(){
        Date date1=Date.newInstance(2015, 11, 28);
        Date date2=Date.newInstance(2015, 12, 15);
        date testDate= VerifyDate.CheckDates(date1, date2);
        Date date3=Date.newInstance(2015, 12, 15);
        System.assertEquals(date3, testDate);
    }
}