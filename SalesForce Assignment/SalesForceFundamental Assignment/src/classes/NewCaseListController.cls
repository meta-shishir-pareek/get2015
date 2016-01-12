public class NewCaseListController {
    public List<Case> getNewCases(){
        List<Case> caseRecords = [SELECT ID,CaseNumber FROM Case where status='New'];
        return caseRecords;
    }
}