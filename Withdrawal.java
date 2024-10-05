/* Thomas Sutton
 * Banking Simulator -> Withdrawal Class
 * Last Updated: October 5, 2024
 */


package project2;

import java.util.Random;

public class Withdrawal implements Runnable{
	
	private static final int MAX_WITHDRAWAL = 99;
	private static final int MAX_SLEEP = 200;
	private static Random withdrawalAmount = new Random();
	private static Random sleepTime = new Random();
	private BankAccount jointAccount1, jointAccount2;
	private int accountNum;
	private String thName;
	
	// constructor method for the withdrawal agents
	public Withdrawal(BankAccount shared1, BankAccount shared2, String name) {
		
		jointAccount1 = shared1;
		jointAccount2 = shared2;
		thName = name;
		
	} //  end of the constructor method
	

	public void run() {
		
		while(true) {
			
			try { // this method selects an account, attempts to deposit into the account, and then sleeps for a random time
				
				accountNum = (Math.random() <= 0.5) ? 1 : 2; // used to select an account with 50/50 probability
				
				if(accountNum == 1) {
					
						jointAccount1.withdraw(withdrawalAmount.nextInt(MAX_WITHDRAWAL) + 1, "1", thName); // deposits into account 1
					
				} else {

						jointAccount2.withdraw(withdrawalAmount.nextInt(MAX_WITHDRAWAL) + 1, "2", thName); // deposits into account 2
	
				} // end of if else statement
				
				Thread.sleep(sleepTime.nextInt(MAX_SLEEP) + 1); // puts the thread to sleep for a random amount of time up to MAX_SLEEP
				
			} // end of try block
			
			catch(InterruptedException exception){
				
				exception.printStackTrace(); // prints a stack trace
				
			} //  end of catch block
			
		} // end of while loop
		
	} //  end of the run block
	
} //  end of the Withdrawal class
