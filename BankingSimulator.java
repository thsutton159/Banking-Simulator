/* Thomas Sutton
 * Banking Simulator -> BankingSumulator Class
 * Last Updated: October 5, 2024
 */

package project2;

// imports
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;


public class BankingSimulator { // start of class Banking Simulator

	public static final int MAX_AGENTS = 19; // sets the maximum number of agents (threads) that will be running
	// 10 withdrawal agents, 5 depositor agents, 2 transfer agents, 1 internal auditor agent, 1 US treasury department thread
	
	public static final int MAX_ACCOUNTS = 2; // sets the maximum number of joint bank accounts accessed 
	
	// start of the main function
	public static void main(String [] args) {
		
		ExecutorService application = Executors.newFixedThreadPool(MAX_AGENTS); // the main executor object
		
		// defines the two joint accounts
		BankAccount jointAccount1 = new BankAccount();
		BankAccount jointAccount2 = new BankAccount();
		
		try {
			
			// the current sleep times (in milliseconds):
			// depositor agents: 1700, withdrawal agents: 200, internal audit agents: 4500, us treasury agents: 5000, transfer agents 350
			
			// the next section instantiates the threads
			// use "DT" for depositor threads, "WT" for withdrawal threads, "TT" for transfer threads,
			// "IA" for internal audit threads, and "UST" for the US treasury thread
			
			// depositor threads
			Depositor DT1 = new Depositor(jointAccount1, jointAccount2, "DT1");
			Depositor DT2 = new Depositor(jointAccount1, jointAccount2, "DT2");
			Depositor DT3 = new Depositor(jointAccount1, jointAccount2, "DT3");
			Depositor DT4 = new Depositor(jointAccount1, jointAccount2, "DT4");
			Depositor DT5 = new Depositor(jointAccount1, jointAccount2, "DT5");
			
			// withdrawal threads 
			Withdrawal WT1 = new Withdrawal(jointAccount1, jointAccount2, "WT1");
			Withdrawal WT2 = new Withdrawal(jointAccount1, jointAccount2, "WT2");
			Withdrawal WT3 = new Withdrawal(jointAccount1, jointAccount2, "WT3");
			Withdrawal WT4 = new Withdrawal(jointAccount1, jointAccount2, "WT4");
			Withdrawal WT5 = new Withdrawal(jointAccount1, jointAccount2, "WT5");
			Withdrawal WT6 = new Withdrawal(jointAccount1, jointAccount2, "WT6");
			Withdrawal WT7 = new Withdrawal(jointAccount1, jointAccount2, "WT7");
			Withdrawal WT8 = new Withdrawal(jointAccount1, jointAccount2, "WT8");
			Withdrawal WT9 = new Withdrawal(jointAccount1, jointAccount2, "WT9");
			Withdrawal WT10 = new Withdrawal(jointAccount1, jointAccount2, "WT10");
			
			// transfer threads
			Transferrer TT1 = new Transferrer(jointAccount1, jointAccount2, "TT1");
			Transferrer TT2 = new Transferrer(jointAccount1, jointAccount2, "TT2");
			
			// internal auditor threads
			InternalAuditor IA1 = new InternalAuditor(jointAccount1, jointAccount2);
			InternalAuditor IA2 = new InternalAuditor(jointAccount1, jointAccount2);
			
			// US treasury auditor thread
			USTreasuryAuditor UST = new USTreasuryAuditor(jointAccount1, jointAccount2);
			
			System.out.println("***     SIMULATION STARTING, WELCOME     ***"); // prints the heading for the simulation
			System.out.println(); // prints an empty line for formatting purposes
			
			// this is where the threads will be started - threads will be in a random order
			
			// the next two lines are for formatting
			System.out.println("Deposit Agents:\t\t\t	Withdrawal Agents:\t\t		Balances:							Transaction Number:");
			System.out.println("---------------\t\t\t	--------------------\t\t	------------------------\t\t\t\t\t----------------------------");
			
			// starts the depositor agents
			application.execute(DT1);
			application.execute(DT2);
			application.execute(DT3);
			application.execute(DT4);
			application.execute(DT5);
			
			// starts the withdrawal agents
			application.execute(WT1);
			application.execute(WT2);
			application.execute(WT3);
			application.execute(WT4);
			application.execute(WT5);
			application.execute(WT6);
			application.execute(WT7);
			application.execute(WT8);
			application.execute(WT9);
			application.execute(WT10);
			
			// starts the transferrer agents
			application.execute(TT1);
			application.execute(TT2);
			
			// starts internal auditor agents
			application.execute(IA1);
			application.execute(IA2);
			
			// starts US Treasury Auditor
			application.execute(UST);
			
			//listWriter.close(); // closes the list writer
			
		} // end of try block
		catch(Exception exception){
			
			exception.printStackTrace(); // prints a stack trace
			
		} // end of catch exception
		
		application.shutdown(); // terminates the executor
		
	} // end of main method
	
} // end of class BankingSimulator


