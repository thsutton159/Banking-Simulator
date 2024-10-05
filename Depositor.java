/* Thomas Sutton
 * Bank Simulator -> Depositor Class
 * Last Updated: October 5, 2024
 */

package project2;

import java.util.Random;

public class Depositor implements Runnable{
	
	private static final int MAX_DEPOSIT = 600; // maximum $ amount that can be deposited
	private static final int MAX_SLEEP = 1700; // maximum time in milliseconds that the agent can sleep
	private static Random depositAmount = new Random(); // random variable for deposits
	private static Random sleepTime = new Random(); //  random variable for sleep time
	private BankAccount jointAccount1, jointAccount2;
	private int accountNum; // integer that will be used to decide between bank accounts
	private String thName; // used to hold the name of the thread making a deposit
	
	// Depositor constructor arguments: account number 1, account number 2, thread name
	public Depositor(BankAccount shared1, BankAccount shared2, String name) {
		
		this.jointAccount1 = shared1;
		this.jointAccount2 = shared2;
		this.thName = name;
		
	} // end of Depositor constructor method

	public void run() {
		
		while(true) {
			
			try { // this method selects an account, attempts to deposit into the account, and then sleeps for a random time
				
				accountNum = (Math.random() <= 0.5) ? 1 : 2; // used to select an account with 50/50 probability
				
				if(accountNum == 1) {
					
						jointAccount1.deposit(depositAmount.nextInt(MAX_DEPOSIT) + 1, "1", thName); // deposits into account 1
					
				} else {

						jointAccount2.deposit(depositAmount.nextInt(MAX_DEPOSIT) + 1, "2", thName); // deposits into account 2
	
				} // end of if else statement
				
				Thread.sleep(sleepTime.nextInt(MAX_SLEEP) + 1); // puts the thread to sleep for a random amount of time up to MAX_SLEEP
				
			} // end of try block
			
			catch(InterruptedException exception){
				
				exception.printStackTrace(); // prints a stack trace
			} //  end of catch block
			
		}// end of while loop
		
	} // end of the run block
	
} // end of Depositor class
