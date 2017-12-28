// TreasuryService.aidl
package mmulla4.project2.uic.edu.treasuryservice;


// Declare any non-default types here with import statements

import java.util.List;

interface TreasuryService {
    List monthlyCash(int year);
    List dailyCash(int day, int month,int year,int workingDays);
    int yearlyAvg(int year);


}
