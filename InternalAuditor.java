/* Thomas Sutton
 * Banking Simulator -> Internal Auditor Class
 * Last Updated: October 5, 2024
 */

package project2;

import java.util.Random;

public class InternalAuditor implements Runnable{
	
	private static final int MAX_SLEEP = 5500;
	private Random sleepTime = new Random();
	private BankAccount jointAccount1, jointAccount2;
	private static int lastTransNum = 0;
	
	// constructor method for internal auditors
	public InternalAuditor(BankAccount shared1, BankAccount shared2) {
		jointAccount1 = shared1;
		jointAccount2 = shared2;
	}
	
	public static void setLastTransNumINT(int newNum) {
		lastTransNum = newNum;
	}
	
	public void run() {
		
		while(true) {
			
			
			try {
				jointAccount1.internalAudit(jointAccount1, jointAccount2, lastTransNum);
				Thread.sleep(sleepTime.nextInt(MAX_SLEEP) + 1);
				
			} // end of try block
			
			catch (InterruptedException e) {
				
				e.printStackTrace();
			} //  end of catch block
		}
	}

}
