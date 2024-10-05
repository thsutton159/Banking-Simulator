/* Thomas Sutton
 * Banking Simulator -> Transferrer class
 * Last Updated: October 5, 2024
 */

package project2;

import java.util.Random;

public class Transferrer implements Runnable{
	
	private static final int MAX_SLEEP = 350;
	private int MAX_TRANSFER_AMOUNT = 600;
	private Random sleepTime = new Random();
	private Random transferAmount = new Random();
	private BankAccount jointAccount1, jointAccount2;
	private String name;
	
	public Transferrer(BankAccount shared1, BankAccount shared2, String thName) {
		jointAccount1 = shared1;
		jointAccount2 = shared2;
		name = thName;
	}
	
	public void run() {
		
		while(true) {
			
			try {
				
				jointAccount1.transfer(jointAccount1, jointAccount2, transferAmount.nextInt(MAX_TRANSFER_AMOUNT) + 1, name);
				Thread.sleep(sleepTime.nextInt(MAX_SLEEP) + 1);
				
			} //  end of the try block
			
			catch(Exception e) {
				
			} //  end of the catch block
			
		} //  end of the while loop
		
	} //  end of the run block

} //  end of the Transferrer class
