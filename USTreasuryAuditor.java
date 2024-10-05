/* Thomas Sutton
 * banking Simulator -> US Treasury Auditor Class
 * Last Updated: October 5, 2024
 */

package project2;

import java.util.Random;

public class USTreasuryAuditor implements Runnable{

	private static final int MAX_SLEEP = 4500;
	private Random sleepTime = new Random();
	private BankAccount jointAccount1, jointAccount2;
	private static int lastTransNum = 0;
	
	// constructor for treasury department auditor
	public USTreasuryAuditor(BankAccount shared1, BankAccount shared2) {
		jointAccount1 = shared1;
		jointAccount2 = shared2;
	} // end of the constructor method
	
	// setter method for this 
	public static void setLastTransNumUST(int newNum) {
		lastTransNum = newNum;
	}
	
	public void run() {
		
		while(true) {
			
			
			try {
				jointAccount1.treasuryAudit(jointAccount1, jointAccount2, lastTransNum);
				Thread.sleep(sleepTime.nextInt(MAX_SLEEP) + 1);
				
			} // end of try block
			
			catch (InterruptedException e) {
				
				e.printStackTrace();
			} //  end of catch block
			
		} //  end of the while statement
		
	} // end of the run method
	
} // end of the USTreasuryAuditor class
