The BankAccount Class holds all of the functions and the balances for the two simulated accounts in this repository.
The BankingSimulator class is where all the threads are created and executed
The remaining classes (Withdrawal, Depositor, Transferrer, InternalAuditor, and USTreasuryAuditor) all call the functions from the BankAccount class, and put the threads to sleep for a random amount of time
