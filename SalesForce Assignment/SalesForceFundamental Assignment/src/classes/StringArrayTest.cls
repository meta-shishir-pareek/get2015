public class StringArrayTest {
    public static List<String> generateStringArray(Integer n){
        List<String> testString = new List<String>();
        for(Integer i=0;i<n;i++){
            testString.add('Test ' + i);
        }
        
        return testString;
    }
}