/* Thomas Sutton
 * Banking Simulator -> BankAccount Class
 * Last Updated: October 5, 2024
 */

package project2;

// imports
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.io.File;
import java.io.FileWriter;
import java.time.format.DateTimeFormatter;

// start of the bank account class
public class BankAccount implements TheBank{

	// creates a lock to ensure mutually exclusive access to the bank account
	private Lock accountKey = new ReentrantLock();
	
	// variables for the bank account
	private static final int DEPOSIT_AMOUNT_ALERT = 450; // depots equal to or over this amount will be flagged
	private static final int WITHDRAWAL_AMOUNT_ALERT = 90; // withdrawals equal to or over this amount will be flagged
	private static final int TRANSFER_AMOUNT_ALERT = 500; // transfers equal to or over this amount will be flagged
	private static int transactionNum = 0; // tracks the number of transactions
	private int balance = 0; // tracks the account balance

	// flagged_transaction number
	// arguments: the $ amount moved during the transaction, name of the thread making the transaction, the transaction type, transaction number
	public void flagged_transaction(int amount, String name, String trans_type, int trans_num) {
		
		// if  statements differentiate between deposits, withdrawals, and transfers, and write a similar message based on each type
		
		if(trans_type.equals("Deposit")) { 
			System.out.println("*** FLAGGED TRANSACTION *** Agent " + name + " made a " + trans_type + " in excess of $" + DEPOSIT_AMOUNT_ALERT);
			System.out.println(); //  empty line for formatting reasons
			
		} // end of if statement for deposits
		
		if(trans_type.equals("Withdrawal")) {
			System.out.println("*** FLAGGED TRANSACTION *** Agent " + name + " made a " + trans_type + " in excess of $" + WITHDRAWAL_AMOUNT_ALERT);
			System.out.println(); // empty line for formatting reasons
			
		} // end of if statement for withdrawals
		
		if(trans_type.equals("Transfer")) {
			System.out.println("*** FLAGGED TRANSACTION *** Agent " + name + " made a " + trans_type + " in excess of $" + TRANSFER_AMOUNT_ALERT);
			System.out.println(); // empty line for formatting
			
		} // end of if statement for transfers
		
		
	} //  end of the flagged_transaction method
	
	// start of the deposit function
	// arguments: value being deposited, account number, name of the thread making the deposit
	public void deposit(int value, String accountNum, String name) {
		
		if(this.accountKey.tryLock()) { // checks to see if the lock is available

				try { // try block
					
					this.accountKey.lock(); // locks the account
					this.balance += value; // updates the balance in the current account
					transactionNum++; //  increments the transaction number
					
					// prints out the information in a very specific manor
					//all spaces and tabs are absolutely necessary for it to print how I want it to print in the console
					System.out.format(name + " deposits $" + value + " into JA#" + accountNum + 
						"\t\t\t\t\t\t 	(+) JA" + accountNum +" balance is $" + this.balance + "  \t\t\t\t\t  Transaction number: " + transactionNum + "\n");
					
					if(value > DEPOSIT_AMOUNT_ALERT) {
						System.out.println(); // empty line to make the console easier to read
						flagged_transaction(value, name, "Deposit", transactionNum); // calls the flagged_transaction function from above
					}
				
				} // end of the try block
				
				catch(Exception e) { // catch block
					
					System.out.println("Exception thrown making a deposit into an account"); // debug line, prints if error is thrown
					
				} // end of the catch block
				
				finally { // finally block
					
					System.out.println(); // prints an empty line to make the console easier to read
					this.accountKey.unlock(); // unlocks the account
				
					
				} // end of the finally block
				
				// had to add a second unlock statement or a single thread would completely lock other threads out of the account
				this.accountKey.unlock(); // I'm not sure why this was happening on my device, but both unlock statements are necessary
				
			} else {
				
				return; // returns to the main function
				
			} // end of the if else statement
		
	} //  end of the deposit method
	
	
	// start of withdraw method
	// arguments: amount being withdrawan, account number, name of the thread making the withdrawal
	public void withdraw(int amount, String accountNum, String name) {
		
		if(this.accountKey.tryLock()) { //  checks to see if the lock on this account is available
			
			try { // try block
				
				this.accountKey.lock(); // locks the account
				
				if(amount > this.balance) { // checks if the withdrawal amount is greater than the account balance
					
					// print statement
					System.out.println("\t\t\t\t\tAgent " + name + " attempts to withdraw $" + amount + " from Account #" + accountNum + " ***** WITHDRAWAL BLOCKED  - Insufficient Funds");
				
				} else { // else block
					
					transactionNum++; //  increments the transaction number
					this.balance -= amount; // subtracts the withdrawn amount
					
					// prints the transaction to the console in the formatted method
					System.out.format("\t\t\t\t	" +name + " withdraws $" + amount + " from JA#" + accountNum + 
							"\t\t(-) JA" + accountNum +" balance is $" + this.balance + "\t\t\t\t\t\t  Transaction number: " + transactionNum + "\n");
					
					if(amount > WITHDRAWAL_AMOUNT_ALERT) { // checks if withdrawal will set off an alert
						
						System.out.println(); // empty line to make the console easier to read
						flagged_transaction(amount, name, "Withdrawal", transactionNum); // calls the flagged_transaction function from above
						
					} //  end of if statement
					
					
				} //  end of the if else statement
				
				
			} // end of the try block
			
			
			catch(Exception e) { //  catch block
				
				System.out.println("Exception thrown trying to withdraw money from an account");
				
			} //  end of the catch block
			
			
			finally { // finally block
				
				this.accountKey.unlock(); //  unlocks the account
				
			} // end of the finally block
			
			
			this.accountKey.unlock(); // second unlock statement as described in the deposit method above
			System.out.println(); // prints an empty line to make the console easier to read
			
		} else { // else block
			
			return; // returns to the withdrawal class
			
		} //  end of the if else statement
		
	} //  end of the withdraw method
	
	
	
	// transfer method
	// arguments: jointAccount1 from the BankingSimulator class, jointAccount2 from the BankingSimmulator class, amount to transfer, thread making the transfer
	public void transfer(BankAccount jointAccount1, BankAccount jointAccount2, int amount, String name) {

		// unlike the deposit or withdraw method, a transfer requires that we have the locks on both accounts
		// no other threads can make changes during this time
		
		if(jointAccount1.accountKey.tryLock() && jointAccount2.accountKey.tryLock()) { //  see's if the locks on both accounts are available
			
			try { //  try block
				
				jointAccount1.accountKey.lock(); //  locks account 1
				jointAccount2.accountKey.lock(); //  locks account 2
				
				transactionNum++; //  increments the transaction number

				int coinFlip = (Math.random() <= 0.5) ? 1 : 2; // decides which account to deposit into
				
				if(coinFlip == 1) { // if account 1 is selected to be the doner
					
					// transfer from account 1 to account 2
					
					// checks to see if variable amount is greater than the balance in account 1
					if(amount > jointAccount1.balance) {
						
						jointAccount1.accountKey.unlock(); // unlocks account 1
						jointAccount2.accountKey.unlock(); // unlocks account 2
						return; //  returns to the transferrer class call
						
					} // end of if statement
					
					
					jointAccount1.balance -= amount; // subtracts the transfer amount from account 1
					jointAccount2.balance += amount; //  adds the transfer amount to account 2
					
					System.out.println("TRANSFER --> Agent " + name + " transferring $" + amount +" from JA1 to JA2 - - JA1 balance is now $" + jointAccount1.balance);
					System.out.println("TRANSFER COMPLETE --> JA2 balance is now $" + jointAccount2.balance);
					
					// checks if the transfer amount is above the alert level
					if(amount > TRANSFER_AMOUNT_ALERT) {
						
						flagged_transaction(amount, name, "Transfer", transactionNum); // calls the flagged transaction function
						
					} // end of the if statement
					
				} else { //  else statement for account selector

					// account 2 is the doner
					// transfer from account 2 to account 1
					
					// checks to see if variable amount is greater than the balance in account 2
					if(amount > jointAccount2.balance) {
						
						jointAccount1.accountKey.unlock(); // unlocks account 1
						jointAccount2.accountKey.unlock(); // unlocks account 2
						return; //  returns to the transferrer class call
						
					}
					
					jointAccount1.balance += amount; // adds the transfer amount to account 1
					jointAccount2.balance -= amount; // subtracts the transfer amount from account 2
					
					System.out.println("TRANSFER --> Agent " + name + " transferring $" + amount +" from JA2 to JA1 - - JA2 balance is now $" + jointAccount2.balance);
					System.out.println("TRANSFER COMPLETE --> JA1 balance is now $" + jointAccount1.balance);
					
					// checks if the transfer amount is above the alert level
					if(amount > TRANSFER_AMOUNT_ALERT) {
						
						flagged_transaction(amount, name, "Transfer", transactionNum); // calls the flagged transaction function
						
					} // end of the if statement
					
				} //  end of the internal (coinFlip) if else statement
				
			} // end of the try block
			
			catch(Exception e) {
				
				System.out.println("Exception thrown trying to make a transfer between accounts"); // debug line, prints if an exception is thrown
				
			} // end of the catch block
			
			finally {
				
				jointAccount1.accountKey.unlock(); // unlocks account 1
				jointAccount2.accountKey.unlock(); // unlocks account 2
				
			} //  end of the finally block
			
			jointAccount1.accountKey.unlock(); // unlocks account 1 again
			jointAccount2.accountKey.unlock(); // unlocks account 2 again
			
		} else { // external else statement
			
			return; // returns to the transferrer class
			
		} //  end of the if else statement
		
	} // end of the transfer method
	
	
	
	// internal auditor class
	// arguments: jointAccount1 from the BankingSimulator class, jointAccount2 from the BankingSimulator class, 
	// 	      and the transaction number from the last internalAudit call
	public void internalAudit(BankAccount jointAccount1, BankAccount jointAccount2, int last_trans_num) {
		
		if(jointAccount1.accountKey.tryLock() && jointAccount2.accountKey.tryLock()) { //  checks to see if both locks are available
			
			try { // try block
				
				jointAccount1.accountKey.lock(); // locks joint account 1
				jointAccount2.accountKey.lock(); // locks joint account 2

				// prints audit message to the console
				System.out.println("******************************************************************************************************************************************************************************************************************");
				System.out.println();
				System.out.println();
				System.out.println("Internal bank Audit Beginning...");
				System.out.println();
				System.out.println("\t\t The total number of transactions since the last internal audit is: " + (transactionNum - last_trans_num));
				System.out.println();
				System.out.println("\t\t Internal Bank Auditor finds the current account balance for JA1 to be: $" + jointAccount1.balance);
				System.out.println("\t\t Internal Bank Auditor finds the current account balance for JA2 to be: $" + jointAccount2.balance);
				System.out.println();
				System.out.println("Internal Bank Audit is now complete");
				System.out.println();
				System.out.println();
				System.out.println("******************************************************************************************************************************************************************************************************************");
				
				InternalAuditor.setLastTransNumINT(transactionNum); // sends this transaction number to the internalAudit class
				// this transaction number is then used as the new last_trans_num for the next internalAusit call
				
			} // end of the try block
			
			catch(Exception e) {
				
				System.out.println("Exception thrown by an internal auditor"); // debug line, prints if an exception is thrown
				
			} //  end of the catch block
			
			
			finally { // finally block
				
				jointAccount1.accountKey.unlock(); // unlocks account 1
				jointAccount2.accountKey.unlock(); //  unlocks account 2
				
			} // end of the finally block
			
			
			jointAccount1.accountKey.unlock(); // second unlock for account 1
			jointAccount2.accountKey.unlock(); // second unlock for account 2
			
		} else { // else statement
			
			return; // return to the call from the internal audit class
			
		} // end of the if else statement
		
	} //  end of the internalAudit method
	
	
	// United States Treasury audit method
	// arguments: jointAccount1 from the BankingSimulator class, jointAccount2 from the BankingSimulator class, 
	// 	      and the transaction number from the last treasuryAudit call
	public void treasuryAudit(BankAccount jointAccount1, BankAccount jointAccount2, int last_trans_num) {
		
		if(jointAccount1.accountKey.tryLock() && jointAccount2.accountKey.tryLock()) { //  checks to see if both locks are available
			
			try { // try block
				
				jointAccount1.accountKey.lock(); // locks joint account 1
				jointAccount2.accountKey.lock(); // locks joint account 2

				// prints treasury audit message
				System.out.println("******************************************************************************************************************************************************************************************************************");
				System.out.println();
				System.out.println();
				System.out.println("UNITED STATES DEPARTMENT OF THE TREASURY - Bank Audit Beginning...");
				System.out.println();
				System.out.println("\t\t The total number of transactions since the last Treasury Department audit is: " + (transactionNum - last_trans_num));
				System.out.println();
				System.out.println("\t\t TREASURY DEPT Auditor finds the current account balance for JA1 to be: $" + jointAccount1.balance);
				System.out.println("\t\t TREASURY DEPT Auditor finds the current account balance for JA2 to be: $" + jointAccount2.balance);
				System.out.println();
				System.out.println("UNITED STATES DEPARTMENT OF THE TREASURY - Bank Audit is now complete");
				System.out.println();
				System.out.println();
				System.out.println("******************************************************************************************************************************************************************************************************************");
				
				USTreasuryAuditor.setLastTransNumUST(transactionNum); // sends this transaction number to the USTreasuryAuditor class
				// this transaction number is then used as the last_trans_num in the next treasuryAudit call
				
			} // end of the try block
			
			catch(Exception e) { // catch block
				
				System.out.println("Exception thrown by an internal auditor");
				
			} //  end of the catch block
			
			finally {
				
				jointAccount1.accountKey.unlock(); // unlocks account 1
				jointAccount2.accountKey.unlock(); //  unlocks account 2
				
			} // end of the finally block
			
			jointAccount1.accountKey.unlock(); // second unlock for account 1
			jointAccount2.accountKey.unlock(); // second unlock for account 2
			
		} else { // 
			
			return; // returns to the call from the UST audit class
			
		} //  end of the if else statement
		
	} //  end of the internalAudit method
	
	
	
} //  end of the BankAccount class
