import java.util.*;
/**
 * Main entry to program.
 */
public class p1
{

	private static String name;
	private static String Id;
	private static String CurrId;
	private static String TempId;
	private static String Age;
	private static String Gender;
	private static String Pin;
	private static String Accnumber;
	private static String Amount;
	private static String SourceAcc;
	private static String DestAcc;
	private static String Type;
	private static String Balance;

	public static void main(String argv[]) 
	{
		System.out.println(":: PROGRAM START");
		
		if (argv.length < 1) {
			System.out.println("Need database properties filename");
		} else {
			BankingSystem.init(argv[0]);
			BankingSystem.testConnection();
			BankingSystem.dropTable();
			BankingSystem.createTable();
			screenOne();
			//BatchInputProcessor.run(argv[0]);
			
		}
	}

	public static void screenOne()
	{
		Scanner s1 = new Scanner(System.in);
		System.out.println("Welcome to the Self Services Banking System!");
		//int input = 0;
		String input = "";
		while (!(input.equals("3")) || !(input.equals("2")) || !(input.equals("1")))
		{
			System.out.println("Main menu");
			System.out.println("Enter 1 for New Customer");
			System.out.println("Enter 2 to login");
			System.out.println("Enter 3 to Exit");
			//input = s1.nextInt();
			input = s1.next();
			if(input.equals("1"))
			{
				newCustomer();
			}
			else if (input.equals("2"))
			{
				login();
			}
			else if (input.equals("3"))
			{
				System.out.println("Bye");
				break;
			}
			else
			{
				System.out.println("Invalid Input");
			}
		}

	}

	public static void screentwo()
	{
		Scanner s4 = new Scanner(System.in);
		//int input = 0;
		String input = "";
		while(!(input.equals("7")))
		{
			System.out.println("Customer Main Menu");
			System.out.println("Enter 1 for Open Account");
			System.out.println("Enter 2 for Close Account");
			System.out.println("Enter 3 for Deposit");
			System.out.println("Enter 4 for Withdraw");
			System.out.println("Enter 5 for Transfer");
			System.out.println("Enter 6 for Account Summary");
			System.out.println("Enter 7 for Exit");
			//input = s4.nextInt();
			input = s4.next();
			if(input.equals("1"))
			{
				openAcc();
			}
			else if (input.equals("2"))
			{
				closeAcc();
			}
			else if (input.equals("3"))
			{
				depos();
			}
			else if (input.equals("4"))
			{
				withdra();	
			}
			else if (input.equals("5"))
			{
				transf();
			}
			else if (input.equals("6"))
			{
				Summary();	
			}
			else if (input.equals("7"))
			{
				System.out.println("Previous menu");
			}
			else
			{
				System.out.println("Invalid input");
			}
		}

	}

	public static Boolean checkInt(String s)
	{
		Boolean done1 = true;
		if(s==null)
		{
			return false;
		}
		int length = s.length();
		for(int i = 0; i<length;i++)
		{
			Boolean m = Character.isLetter(s.charAt(i));
			if( m == true)
			{
				done1 = false;
				break;
			}
		}
		return done1;
	}

	public static void openAcc()
	{
		Scanner s5 = new Scanner(System.in);
		System.out.println("Open new Account");
		System.out.println("Enter Your Id");
		Boolean done  = false;
		String input = "";
		while (done != true)
		{
			input = s5.next();
			if (input != null && !input.isEmpty())
				{
					//TempId = input;
					//done = true;
					Boolean c = checkInt(input);
					if(c == true)
					{
						int k = Integer.parseInt(input);
						if(k>99)
						{
							Boolean c1 = BankingSystem.checkID(input);
							if(c1 == true)
							{
								TempId = input;
								done = true;
							}
							else
							{
								System.out.println("Id does not exist");
								break;
							}
						}
						else
						{
							System.out.println("Invalid Id, , please enter id again");
						}
					}
					else
					{
						System.out.println("Invalid id, , please enter id again");
					}
				}
			}

			done = false;
			if (TempId !=null && TempId.equals(input))
			{
			System.out.println("Enter your account Type: Enter 'C' for Checking and 'S' for Savings");
			while(done != true)
			{
				input = s5.next();
				if(input.equals("C") || input.equals("S"))
				{
					Type = input;
					done = true;
				}
				else
				{
					System.out.println("Invalid type, , please enter type again");
				}
			}

			System.out.println("Enter your balance");
			done = false;
			while(done != true)
			{
				input = s5.next();
				if (input != null && !input.isEmpty())
				{
				//Balance = input;
				//done = true;
					Boolean c = checkInt(input);
					if(c == true)
					{
						int k = Integer.parseInt(input);
						if(k>0)
						{
							Balance = input;
							done = true;
						}
						else
						{
							System.out.println("Invalid Balance, please enter balance again");
						}
					}
					else
					{
						System.out.println("Invalid balance, , please enter balance again");
					}		
				}
			}
		}
			if(done != false)
			{
			BankingSystem.openAccount(TempId,Type,Balance);
		}
		
	}

	public static void closeAcc()
	{
		Scanner s6 = new Scanner(System.in);
		System.out.println("Close account");
		System.out.println("Enter your account number");
		Boolean done = false;
		String input = "";
		while (done!=true)
		{
			input = s6.next();
			if(input != null && !input.isEmpty())
			{
				//Accnumber = input;
				//done = true;
				Boolean c = checkInt(input);
				if(c == true)
				{
					int k = Integer.parseInt(input);
					if(k>999)
					{
						Boolean c1 = BankingSystem.checkNumber(input);
						if(c1==true)
						{
							c1 = BankingSystem.authen(k,CurrId);
							if(c1 == true)
							{
								Accnumber = input;
								done = true;
							}
							else
							{
								System.out.println("This is not your account");
								break;
							}
						}
						else
						{
							System.out.println("Account number does not exist");
							break;
						}
					}
					else
					{
						System.out.println("Invalid account number,  please enter account number again");
					}
				}
				else
				{
					System.out.println("Invalid account number,  please enter account number again");
				}
			}
		}

		if(done != false)
		{
		BankingSystem.closeAccount(Accnumber);
		}
	}

	public static void depos()
	{
		Scanner s7 = new Scanner(System.in);
		System.out.println("Deposit");
		System.out.println("Enter your account number");
		Boolean done = false;
		String input = "";
		while (done!=true)
		{
			input = s7.next();
			if(input!=null && !input.isEmpty())
			{
				//Accnumber = input;
				//done = true;A
				Boolean c = checkInt(input);
				if(c == true)
				{
					int k = Integer.parseInt(input);
					if(k>999)
					{
							Boolean c1 = BankingSystem.checkNumber(input);
							if(c1==true)
							{
									Accnumber = input;
									done = true;
							}
							else
							{
								System.out.println("Account number does not exist");
								break;
							}
					}
					else
					{
						System.out.println("Invalid account number,  please enter account number again");
					}
				}
				else
				{
					System.out.println("Invalid account number,  please enter account number again");
				}
			}
			else
			{
				System.out.println("Invalid account number,  please enter account number again");
			}

		}

		done = false;

		if(Accnumber!=null && Accnumber.equals(input))
		{
		System.out.println("Enter deposit amount");
		while(done != true)
		{
			input = s7.next();
			if(input!=null && !input.isEmpty())
			{
				//Amount = input;
				//done = true;
				Boolean c = checkInt(input);
				if(c == true)
				{
					int k = Integer.parseInt(input);
					if(k>=0)
					{
						Amount = input;
						done = true;
					}
					else
					{
						System.out.println("Invalid amount");
					}
				}
				else
				{
					System.out.println("Invalid amount");
				}
			}

		}
	}
		if(done!=false)
		{
		BankingSystem.deposit(Accnumber,Amount);
		}

	}

	public static void withdra()
	{
		Scanner s8 = new Scanner(System.in);
		System.out.println("Withdraw");
		System.out.println("Enter your account number");
		Boolean done = false;
		String input = "";
		while (done!=true)
		{
			input = s8.next();
			if(input!=null && !input.isEmpty())
			{
				//Accnumber = input;
				//done = true;
				Boolean c = checkInt(input);
				if(c == true)
				{
					int k = Integer.parseInt(input);
					if(k>999)
					{
						Boolean c1 = BankingSystem.checkNumber(input);
						if(c1==true)
						{
							c1 = BankingSystem.authen(k,CurrId);
							if(c1 == true)
							{
								Accnumber = input;
								done = true;
							}
							else
							{
								System.out.println("This is not your account, withdraw failed");
								break;
							}
						}
						else
						{
							System.out.println("Account number does not exist, withdraw failed");
							break;
						}
					}
					else
					{
						System.out.println("Invalid account number,  please enter account number again");
					}
				}
				else
				{
					System.out.println("Invalid account number,  please enter account number again");
				}
			}

		}

		done = false;
		if(Accnumber != null && Accnumber.equals(input))
		{
			System.out.println("Enter withdraw amount");
		while (done != true)
		{
			input = s8.next();
			if(input!=null && !input.isEmpty())
			{
				//Amount = input;
				//done = true;
				Boolean c = checkInt(input);
				if(c == true)
				{
					int k = Integer.parseInt(input);
					if(k>=0)
					{
						Boolean d2 = BankingSystem.balanceCheck(k,Accnumber);
						if(d2 == true)
						{
							Amount = input;
							done = true;
						}
						else
						{
							System.out.println("Insufficent funds,  withdraw failed");
							break;
						}
					}
					else
					{
						System.out.println("Invalid amount,  please enter amount again");
					}
				}
				else
				{
					System.out.println("Invalid amount, please enter amount again");
				}
			}

		}
	}
		if(done!=false)
		{
		BankingSystem.withdraw(Accnumber,Amount);
		}
	}

	public static void transf()
	{
		Scanner s9 = new Scanner(System.in);
		System.out.println("Transfer");
		System.out.println("Enter source account number");
		Boolean done = false;
		String input = "";
		while (done !=true)
		{
			input = s9.next();
			if(input!=null && !input.isEmpty())
			{
				//SourceAcc = input;
				//done = true;
				Boolean c = checkInt(input);
				if(c == true)
				{
					int k = Integer.parseInt(input);
					if(k>999)
					{
						Boolean c1 = BankingSystem.checkNumber(input);
						if(c1==true)
						{
							c1 = BankingSystem.authen(k,CurrId);
							if(c1 == true)
							{
								SourceAcc = input;
								done = true;
							}
							else
							{
								System.out.println("This is not your account, transfer failed");
								break;
							}
						}
						else
						{
							System.out.println("Account does not exist, transfer failed");
							break;
						}
					}
					else
					{
						System.out.println("Invalid account number, please enter account number again");
					}
				}
				else
				{
					System.out.println("Invalid account number, please enter account number again");
				}

			}
		}

		done = false;
		if(SourceAcc != null && SourceAcc.equals(input))
		{
		System.out.println("Enter destination account number");
		while(done!=true)
		{
			input = s9.next();
			if(input!=null && !input.isEmpty())
			{
				//DestAcc = input;
				//done = true;
				Boolean c = checkInt(input);
				if(c == true)
				{
					int k = Integer.parseInt(input);
					if(k>999)
					{
						if(BankingSystem.checkNumber(input))
						{
							DestAcc = input;
							done = true;
						}
						else
						{
							System.out.println("Account does not exist, Transfer failed");
							break;
						}
					}
					else
					{
						System.out.println("Invalid account number, please enter account number again");
					}
				}
				else
				{
					System.out.println("Invalid account number, please enter account number again");
				}
			}
		}
		}

		done = false;
		if(DestAcc!=null && DestAcc.equals(input))
		{

		System.out.println("Enter transfer amount");
		done = false;
		while (done != true)
		{
			input = s9.next();
			if(input!=null && !input.isEmpty())
			{
				//Amount = input;
				//done = true;
				Boolean c = checkInt(input);
				if(c == true)
				{
					int k = Integer.parseInt(input);
					if(k>=0)
					{
						Boolean d2 = BankingSystem.balanceCheck(k,SourceAcc);
						if(d2 == true)
						{
							Amount = input;
							done = true;
						}
						else
						{
							System.out.println("Insufficent funds, Transfer failed");
							break;
						}
					}
					else
					{
						System.out.println("Invalid amount, , please enter amount again");
					}
				}
				else
				{
					System.out.println("Invalid amount, please enter amount again");
				}
			}

		}
		}
		

		if (done!=false)
		{
		BankingSystem.transfer(SourceAcc,DestAcc,Amount);
		}

	}

	public static void Summary()
	{
		BankingSystem.accountSummary(CurrId);
	}
	
	public static void newCustomer()
	{
		Scanner s2 = new Scanner(System.in);
		System.out.println("Create new Customer");
		System.out.println("Enter Your name");
		Boolean done  = false;
		String input = "";
		while (done != true)
		{
			input = s2.nextLine();
			if (input != null && !input.isEmpty())
			{
				name = input;
				done = true;
			}
		}


		System.out.println("Enter your Gender: Enter 'M' for male and 'F' for female");
		done = false;
		while(done != true)
		{
			input = s2.next();
			if (input.equals("F") || input.equals("M"))
			{
				Gender = input;
				done = true;
			}
			else
			{
				System.out.println("Invaid Gender: enter gender again");
			}
		}

		System.out.println("Enter your Age");
		done = false;
		while(done != true)
		{
			input = s2.next();
			if (input != null && !input.isEmpty())
			{
				//Age = input;
				//done = true;
				Boolean c = checkInt(input);
				if(c == true)
				{
					int k = Integer.parseInt(input);
					if(k>0)
					{
						Age = input;
						done = true;
					}
					else
					{
						System.out.println("Invalid age, enter age again");
					}
				}
				else
				{
					System.out.println("Invalid age, enter age again");
				}
			}
		}

		System.out.println("Enter your pin: Pin should be of 4 digits");
		done = false;
		while(done != true)
		{
			input = s2.next();
			if (input != null && !input.isEmpty())
			{
				Boolean c = checkInt(input);
				if(c == true)
				{
					
						Pin = input;
						done = true;
				}
				else
				{
					System.out.println("Invalid Pin, enter pin again");
				}
			}
		}

		BankingSystem.newCustomer(name,Gender,Age,Pin);
	}

	public static void login()
	{
		Scanner s3 = new Scanner(System.in);
		System.out.println("Enter Customer Id");
		Boolean done = false;
		String input = "";
		while (done != true)
		{
			input = s3.next();
			if (input != null && !input.isEmpty())
			{
				Boolean c = checkInt(input);
				if(c == true)
				{
					int k = Integer.parseInt(input);
					if(k>99 || k==0)
					{
						Boolean c1 = BankingSystem.checkID(input);
						if(c1 == true || input.equals("0"))
						{
							Id = input;
							done = true;
						}
						else
						{
							System.out.println("Id does not exist");
							break;
						}
					}
					else
					{
						System.out.println("Invalid Id, please enter id again");
					}
				}
				else
				{
					System.out.println("Invalid id, please enter id again");
				}
			}
		}

		done = false;
		if(Id != null && Id.equals(input))
		{
		System.out.println("Enter Pin");
		while (done != true)
		{
			input = s3.next();
			if (input != null && !input.isEmpty())
			{
				//Pin = input;
				//done = true;
				Boolean c = checkInt(input);
				if(c == true)
				{
					
					Pin = input;
					done = true;
				}
				else
				{
					System.out.println("Invalid Pin, please enter pin again");
				}
			}
		}


		if(Id.equals("0") && Pin.equals("0"))
		{
			screenThree();
		}
		else
		{
			Boolean m = BankingSystem.login(Id,Pin);
			if( m == true)
			{
				
				CurrId = Id;
				screentwo();
			}
			else
			{
				System.out.println("Login failed");
			}
		}
		}
	}

	public static void screenThree()
	{
		Scanner s10 = new Scanner(System.in);
		String input = "";
		while (!(input.equals("4")))
		{
			System.out.println("Administrator Main Menu");
			System.out.println("Enter 1 for Customer account Summary");
			System.out.println("Enter 2 for Report A");
			System.out.println("Enter 3 for Report B");
			System.out.println("Enter 4 for exit");
			input = s10.next();
			if(input.equals("1"))
			{
				adminSummary();
			}
			else if(input.equals("2"))
			{
				rA();
			}
			else if(input.equals("3"))
			{
				rB();
			}
			else if(input.equals("4"))
			{
				System.out.println("Previous menu");
			}
			else
			{
				System.out.println("Invalid input");
			}
		}

	}

	public static void adminSummary()
	{
		Scanner s11 = new Scanner(System.in);
		System.out.println("Summary");
		System.out.println("Enter Id of Customer");
		Boolean done = false;
		String input = "";
		while(done!=true)
		{
			input = s11.next();
			if (input != null && !input.isEmpty())
			{
				//Id = input;
				//done = true;
				Boolean c = checkInt(input);
				if(c == true)
				{
					int k = Integer.parseInt(input);
					if(k>99)
					{
						Boolean c1 = BankingSystem.checkID(input);
						if(c1 == true)
						{
							Id = input;
							done = true;
						}
						else
						{
							System.out.println("Id does not exist");
							break;
						}
					}
					else
					{
						System.out.println("Invalid Id, please enter id again");
					}
				}
				else
				{
					System.out.println("Invalid id, please enter id again");
				}
			}

		}


		if(done!=false)
		{
		BankingSystem.accountSummary(Id);
		}

	}

	public static void rA()
	{
		BankingSystem.reportA();
	}

	public static void rB()
	{
		Scanner s11 = new Scanner(System.in);
		System.out.println("Summary");
		System.out.println("Enter minimum age of Customer");
		Boolean done = false;
		String input = "";
		while(done!=true)
		{
			input = s11.next();
			if (input != null && !input.isEmpty())
			{
				Boolean c = checkInt(input);
				if(c == true)
				{
					int k = Integer.parseInt(input);
					if(k>0)
					{
						Age = input;
						done = true;
					}
					else
					{
						System.out.println("Invalid age, please enter age again");
					}
				}
				else
				{
					System.out.println("Invalid age, please enter age again");
				}
			}

		}
		done = false;
		String minAge = Age;

		System.out.println("Enter maximum age of Customer");
		while(done!=true)
		{
			input = s11.next();
			if (input != null && !input.isEmpty())
			{
				Boolean c = checkInt(input);
				if(c ==true)
				{
					int k = Integer.parseInt(input);
					if(k>0)
					{
						Age = input;
						done = true;
					}
					else
					{
						System.out.println("Invalid age, please enter age again");
					}
				}
				else
				{
					System.out.println("Invalid age, please enter age again");
				}
			}

		}
		done = false;
		String maxAge = Age;


		BankingSystem.reportB(minAge,maxAge);
	}

}