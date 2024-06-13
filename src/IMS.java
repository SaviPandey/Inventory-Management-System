import java.sql.*;		//importing sql package
import java.io.*;		//importing io package
import java.util.*;		//importing utility package


class IMS
{
  
  //creating object of scanner class for data input
	static Scanner sc=new Scanner(System.in);	
  
  //creating connection object
	static Connection con;		
	static
	{
		try
		{
      //defining path of class
			Class.forName("com.mysql.jdbc.Driver");		
      //importing drivers
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/ims","root","Kamlakar123@");		// here should be the password of mysql server
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
	
  //login Method
	public static boolean login()throws SQLException		
	{
		System.out.println("\t\tLOGIN TO IMS APPLICATION\n\n");
		java.io.Console console = System.console();
		System.out.print("\t\t\t\t");
      		String username = console.readLine("Username: ");
		System.out.print("\t\t\t\t");
		String password = new String(console.readPassword("Password: "));
		System.out.println("\n\n\n");
		if(username.equals("admin")&&password.equals("password"))
		{
			return true;
		}
		else
		{
				return false;
		}
	}
	
  
  //METHOD TO ADD PRODUCT
	public static void addNewProduct()throws SQLException		
	{
		try
		{
			System.out.println("\n\n\n\n");
			System.out.println("\t\t\t\tEnter Product Name");
			System.out.print("\t\t\t\t");
			sc.nextLine();
			String name=sc.nextLine();
			System.out.println("\t\t\t\tEnter Product Purchase Price");
			System.out.print("\t\t\t\t");
			int pp=sc.nextInt();
			System.out.println("\t\t\t\tEnter Product Sale Price");
			System.out.print("\t\t\t\t");
			double sp=sc.nextDouble();
			System.out.println("\t\t\t\tEnter Product Quantity");
			System.out.print("\t\t\t\t");	
			int Q=sc.nextInt();
			PreparedStatement pst=con.prepareStatement("insert into Product (productName,purchasePrice,salePrice,ProductQty) values(?,?,?,?)",Statement.RETURN_GENERATED_KEYS);		//object declaration for passing SQL insert querry
			pst.setString(1,name);
			pst.setInt(2,pp);
			pst.setDouble(3,sp);
			pst.setInt(4,Q);	
			if(pst.executeUpdate()>0)
			{
				System.out.println("\t\t\t\tData Inserted ");
			}
			else
			{
				System.out.println("\t\t\t\tData not Inserted");	
			}
		}
		catch(Exception e)
		{
			System.out.println("Exception Occured");
		}
	}
	
  //METHOD TO UPDATE PRODUCT QUANTITY
	public static void updateProductQuantity()throws SQLException		
	{
		System.out.println("\n\n\n\n\t\t\t\tEnter id of Product whoseQuantity is to be updated");
		System.out.print("\t\t\t\t");
		int id=sc.nextInt();
    
    //object declaration to pass SQL select Query
		PreparedStatement pst=con.prepareStatement("select * from Product where productid=?");	
    
		pst.setInt(1,id);
    
    //result set to store result obtained from select query
		ResultSet rs=pst.executeQuery();		
    
		if(rs.next())
		{
			int qt=rs.getInt(5);
			System.out.println("\t\t\t\tEnter Updated Product Quantity");
			System.out.print("\t\t\t\t");
			int Q=sc.nextInt();
			pst=con.prepareStatement("update Product set productQty=? where productid=? ");		//object to pass SQL update Query
			pst.setInt(1,(Q+qt));
			pst.setInt(2,id);
			if(pst.executeUpdate()>0)
			{
				System.out.println("\t\t\t\tQuantity Updated Successfully!!!!");
			}
			else
			{
				System.out.println("\t\t\t\tQuantity not Updated");
			}
		}
		else
		{
			System.out.println("\t\t\t\tProduct not found at the given id");
		}
	}
	
  //METHOD TO REMOVE PRODUCT
	public static void removeProduct()throws SQLException		
	{
		System.out.println("\n\n\n\n\t\t\t\tEnter id of Product which is to be removed");
		System.out.print("\t\t\t\t");
		int k=sc.nextInt();
		PreparedStatement pst=con.prepareStatement("select * from Product where ProductId=?");		//object declaration to pass SQL select Query
		pst.setInt(1,k);
		ResultSet rs=pst.executeQuery();		//result set to stort result of the anove select query
		if(rs.next())
		{
			pst=con.prepareStatement("delete from Product where productid=?");		//passing query to delete
			pst.setInt(1,k);
			if(pst.executeUpdate()>0)
			{
				System.out.println("\t\t\t\tProduct Deleted");
			}
			else
			{
				System.out.println("\t\t\t\tProduct not deleted");
			}
		}
		else
		{
			System.out.println("\t\t\t\tProduct Record Not Found");
		}
	}
	
  //METHOD TO SEE ALL PRODUCTS
	public static void viewAllProducts()throws SQLException		
	{
		PreparedStatement pst=con.prepareStatement("select * from product");		//passing the SQL select query to the Product table
		ResultSet rs=pst.executeQuery();
		System.out.println("\n\n\n");
		System.out.println("\t\t\t\t\tDETAILS OF ALL PRODUCTS ");
		System.out.println("\t\t\t\t+---------------------------------------+\n");
		System.out.println("\t\t\t\tID\tNAME\tPP\tSP\tQUANTITY\n");
		System.out.println("\t\t\t\t+---------------------------------------+\n");
			while(rs.next())
		{
			System.out.println("\t\t\t\t"+rs.getInt(1)+"\t"+rs.getString(2)+"\t"+rs.getInt(3)+"\t"+rs.getDouble(4)+"\t"+rs.getInt(5)+"\t"); 		//printing product details
			System.out.println("\n\t\t\t\t+---------------------------------------+\n");
		}
	}
	
  
  //METHOD TO VIEW STOCK
	public static void viewStock()throws SQLException		
	{
		System.out.println("\n\n\n\n\t\t\t\tEnter Product ID Whose Stock you Want to see");
		System.out.print("\t\t\t\t");
		int id=sc.nextInt();
		PreparedStatement pst=con.prepareStatement("select * from Product where Productid=?");		//passing SQL query to select
		pst.setInt(1,id);
		ResultSet rs=pst.executeQuery();		//storing result set of the execut query
		System.out.println("\n\n\n");
		System.out.println("\t\t\t\t\tDETAILS OF GIVEN STOCK");
		System.out.println("\t\t\t\t+-----------------------+\n");
		System.out.println("\t\t\t\tPID\tName\tQuantity\n");
		System.out.println("\t\t\t\t+-----------------------+\n");
		while(rs.next())
		{
			System.out.println("\t\t\t\t"+rs.getInt(1)+"\t"+rs.getString(2)+"\t"+rs.getInt(5));
			System.out.println("\n\t\t\t\t+-----------------------+\n");
		}
	}
  
  //FUNCTION TO INSERT PRODUCT SALE DETAILS
	public static void insertProductSaleDetails()throws SQLException		
	{
		System.out.println("\t\t\t\tEnter Product ID");
		System.out.print("\t\t\t\t");
		int pid=sc.nextInt();
		PreparedStatement pst1=con.prepareStatement("select * from product where productid=?");		//passing the sql select Query
		pst1.setInt(1,pid);
		ResultSet rs=pst1.executeQuery();		//storing the result set of the above Sql select query from product table
		if(rs.next())
		{
			System.out.println("\t\t\t\tEnter Sale Quantity");
			System.out.print("\t\t\t\t");
			int Qt=sc.nextInt();
			System.out.println("\t\t\t\tEnter Date");
			sc.nextLine();
			System.out.print("\t\t\t\t");
			String date=sc.nextLine();
			double sp=rs.getDouble(4);
			sp=sp*Qt;
			pst1.setInt(1,pid);	
			int qt=rs.getInt(5);
			pst1=con.prepareStatement("update Product set productQty=? where productid=? ");		//passing thr SQL update Query
			pst1.setInt(1,(qt-Qt));
			pst1.setInt(2,pid);
			if(pst1.executeUpdate()>0)
			{
				System.out.println("\t\t\t\tProduct Quantity Updated");
			}
			else
			{
				System.out.println("\t\t\t\tProduct Quantity not Updated");
			}
		
			PreparedStatement pst=con.prepareStatement("insert into Sale (productID,date,saleQty,price)values(?,?,?,?)",Statement.RETURN_GENERATED_KEYS);		//passing the SQL insert Query
			pst.setInt(1,pid);
			@SuppressWarnings("deprecation")
			java.util.Date d=new java.util.Date(date);
			@SuppressWarnings("deprecation")
			java.sql.Date d1=new java.sql.Date(d.getYear(),d.getMonth(),d.getDate());
			pst.setDate(2,d1);
			pst.setInt(3,Qt);
			pst.setDouble(4,sp);
			if(pst.executeUpdate()>0)
			{
				System.out.println("\t\t\t\tData Inserted ");
			}
			else
			{
				System.out.println("\t\t\t\tData not Inserted");
			}
		}
		else
		{
			System.out.println("Product Record Not Found");
		}
	}
  
  // METHOD TO UPDATE SALE DETAILS
	@SuppressWarnings("deprecation")
	public static void updateProductSaleDetails()throws SQLException		
	{
		double sp=0;
		System.out.println("\n\n\n\n\t\t\t\tEnter Sale id of Product whose sale details are to be updated");
		System.out.print("\t\t\t\t");
		int sid=sc.nextInt();
		PreparedStatement pst=con.prepareStatement("select * from Sale where SaleId=?");		//Passing the SQL select Query for table Sale
		pst.setInt(1,sid);
		ResultSet rs=pst.executeQuery();		//Stroing the result set from sale table
		if(rs.next())		//next is necessary to move the memory pointer to the first location and then to move it forward
		{
			int pid=rs.getInt(2);
			System.out.println("\t\t\t\tEnter Sale Quantity : ");
			System.out.print("\t\t\t\t");
			int Qt=sc.nextInt();
			System.out.println("\t\t\t\tEnter Date");
			sc.nextLine();
			System.out.print("\t\t\t\t");
			String date=sc.nextLine();
			PreparedStatement pst1=con.prepareStatement("select * from product where productid=?");		//passing the SQL select Query to the product table
			pst1.setInt(1,pid);
			ResultSet rs1=pst1.executeQuery();		//storing the result set from the product table
			if(rs1.next())
			{
				sp=rs1.getDouble(4);
				sp=sp*Qt;
				int qt=rs1.getInt(5);
				PreparedStatement pst3=con.prepareStatement("update Product set productQty=? where productid=? ");		//passing the SQL update query to the product set
				pst3.setInt(1,(qt-Qt));
				pst3.setInt(2,pid);
				if(pst3.executeUpdate()>0)
				{
					System.out.println("\t\t\t\tProduct Quantity Updated");
				}
				else
				{
					System.out.println("\t\t\t\tProduct Quantity not Updated");
				}
			}	
			else
			{
					System.out.println("\t\t\t\tProduct Record Not Found");
			}
			PreparedStatement pst4=con.prepareStatement("update Sale set price=? , date=?,saleQty=? where saleid=?");		//passing the SQL update Query to the sale table
			java.util.Date d=new java.util.Date(date);
			java.sql.Date d1=new java.sql.Date(d.getYear(),d.getMonth(),d.getDate());
			pst4.setDate(2,d1);
			pst4.setInt(3,Qt);
			pst4.setDouble(1,sp);
			pst4.setInt(4,sid);
			if(pst4.executeUpdate()>0)
			{
				System.out.println("\t\t\t\tData Inserted ");
			}
			else
			{
				System.out.println("\t\t\t\tData not Inserted");
			}
		}
		else
		{
			System.out.println("Product Record Not Found");
		}
	}
	
  
  // METHOD TO VIEW PRODUCT SALE DETAILS
	public static void viewProductSaleDetails()throws SQLException		
	{
		PreparedStatement pst=con.prepareStatement("select * from sale");		//passing the SQL Select Query to the Sale table
		ResultSet rs=pst.executeQuery();
		System.out.println("\n\n\n");
		System.out.println("\t\t\t\t\tDETAILS OF SALES");
		System.out.println("\t\t\t\t+---------------------------------+\n");
		System.out.println("\t\t\t\tSaleID\tProID\tPrice\tDate");
		System.out.println("\t\t\t\t+---------------------------------+\n");
		while(rs.next())
		{
		System.out.println("\t\t\t\t"+rs.getInt(1)+"\t"+rs.getInt(2)+"\t"+rs.getDouble(3)+"\t"+rs.getDate(4));
		System.out.println("\n\t\t\t\t+---------------------------------+\n");
		}
	}	
	
  //METHOD TO VIEW PROFIT DETAILS
	@SuppressWarnings("deprecation")
	public static void viewProfitDetails()throws SQLException,  InterruptedException,IOException	
	{
		System.out.println("\n\n\n\n\t\t\t\tEnter date between which profit is to be calculatd");
		System.out.print("\t\t\t\t");
		String date1=sc.next();
		System.out.print("\t\t\t\t");
		String date2=sc.next();
		double tp=0;
		java.util.Date d=new java.util.Date(date1);
		java.sql.Date d1=new java.sql.Date(d.getYear(),d.getMonth(),d.getDate());
		java.util.Date d2=new java.util.Date(date2);
		java.sql.Date d3=new java.sql.Date(d2.getYear(),d2.getMonth(),d2.getDate());
		int pid=0;
		int QT=0;
		clearScreen();
		PreparedStatement pst=con.prepareStatement("select * from Sale where date > ?&&date <?");		//passing the SQL select Query to the Sale table
		pst.setDate(1,d1);
		pst.setDate(2,d3);
		ResultSet rs=pst.executeQuery();		//storing the result set from the Sale table
		System.out.println("\n\n\n");
		System.out.println("\t\t\t\t\t\t\tDETAILS OF PROFIT\n");
		System.out.println("\t\t\tProductId\tProductName\tPurchasePrice\tSalePrice\tProfit\t\tDate");
		while(rs.next())
		{
			pid=rs.getInt(2);
			QT=rs.getInt(5);
			PreparedStatement pst1=con.prepareStatement("select * from Product where productId=?");		//Passing the SQL select Query to the product table
			pst1.setInt(1,pid);
			ResultSet rs1=pst1.executeQuery();		//Storing the Results obtained from the product table
			while(rs1.next())
			{
				int pp=rs1.getInt(3);
				double sp=rs1.getDouble(4);
				System.out.println("\t\t\t"+rs1.getInt(1)+"\t\t"+rs1.getString(2)+"\t \t"+pp+"\t\t"+sp+"\t\t"+((sp-pp)*QT)+"\t\t"+rs.getDate(4));
				tp=tp+((sp-pp)*QT);
			}
		}
		System.out.println("\n\t\t\t\t\t\tTotal Profit = "+tp);
	}
	
	public static void clearScreen() throws InterruptedException , IOException , SQLException		//Clear Screen Method
	{
		new ProcessBuilder("cmd","/c","cls").inheritIO().start().waitFor();
	}
	
  
  //MAIN FUNTION
	public static void main(String[] var)throws SQLException		
	{
		try		//try block to prevent the program from crashing
		{
			clearScreen();
			System.out.print("\n\n\n\n\t\t\t\t");
			if(login())
			{
				int ch;
				do
				{
					clearScreen();
					System.out.println("\n\n\n\n\t\t\t\tWELCOME TO INVENTORY MANAGEMENT SYSTEM :");
					System.out.println("\t\t\t\tEnter :\n\t\t\t\t1---->To Manage Stock\n\t\t\t\t2---->For Sales Details");
					System.out.println("\t\t\t\t3---->To Manage Product\n\t\t\t\t4---->To View Profit Details\n\t\t\t\t5---->To Exit");
					System.out.print("\t\t\t\t");
					ch=sc.nextInt();
					switch (ch)
					{
					case 1:
						clearScreen();
						System.out.println("\n\n\n\n\t\t\t\tEnter :\n\t\t\t\ta---->Update Product Quantity In Stock\n\t\t\t\tb---->View Stock");
						System.out.print("\t\t\t\t");
						char ch1=sc.next().charAt(0);
						switch (ch1)
						{
						case 'a':
							clearScreen();
							updateProductQuantity();
						break;
						case 'b':
							clearScreen();
							viewStock();
						break;
						default:
							System.out.println("\t\t\t\tInvalid Input");
						}
					break;
					case 2:
						clearScreen();
						System.out.println("\n\n\n\n\t\t\t\tEnter :\n\t\t\t\ta---->Insert Product Sale Details\n\t\t\t\tb---->Update Product Stock Details\n\t\t\t\tc---->View Product Sale Details");
						System.out.print("\t\t\t\t");
						char ch2=sc.next().charAt(0);
						switch (ch2)
						{
						case 'a':
							clearScreen();
							insertProductSaleDetails();
						break;
						case 'b':
							clearScreen();
							updateProductSaleDetails();
						break;
						case 'c':
							clearScreen();
							viewProductSaleDetails();
						break;
						default:
							System.out.println("\t\t\t\tInvalid Input");
						}
					break;
					case 3:
						clearScreen();
						System.out.println("\n\n\n\n\t\t\t\tEnter :\n\t\t\t\ta---->Add new Product\n\t\t\t\tb---->View All Products\n\t\t\t\tc---->Remove Product");
						System.out.print("\t\t\t\t");
						char ch3=sc.next().charAt(0);
						switch (ch3)
						{
						case 'a':
							clearScreen();
							addNewProduct();
						break;
						case 'b':
							clearScreen();
							viewAllProducts();
						break;
						case 'c':
							clearScreen();
							removeProduct();
						break;
						default:
							clearScreen();
							System.out.println("\n\n\n\n\t\t\t\tInvalid Input");
						}
					break;
					case 4:
						clearScreen();
						viewProfitDetails();
					break;
					case 5:
						clearScreen();
						System.out.println();
					break;
					default:
						clearScreen();
						System.out.println("\n\n\n\n\t\t\t\tINVALID INPUT TRY AGAIN");
					}
					if(ch!=5)
						{
						System.out.println("\n\n\t\t\t\tEnter :\n\t\t\t\t1---->to go back to the main menu\n\t\t\t\t2---->To  exit");
						System.out.print("\t\t\t\t");
						ch=sc.nextInt();
						if(ch==2)
						{
							clearScreen();
							System.out.println("\n\n\n\n\t\t\t\tTHANK YOU FOR USING IMS\n\t\t\t\tDeveloped by : \n\t\t\t\tPraveg Vashishtha\n\t\t\t\tHarshita Ashwani\n\t\t\t\tAbhilash Kumar\n\t\t\t\tShubham Gupta");
							ch=5;
						}
					}
				}while(ch!=5);
			}	
		}
		catch(Exception e)		//catch block to handle Exceptions
		{
			if(!(e.getMessage().equals(null)))
			{
				System.out.println("Exception Occured");
			}
		}
	}
}
