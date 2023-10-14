
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**This is the main class. In this here the user passes the necessary
 *  information in order to run the program. 
 *  This data is the number of threads,
 *  the depth we want our execution to reach and 
 *  the original website we are researching. If the original website 
 *  does not exist then the program automatically runs from the page: https://www.cs.ucy.ac.cy
 *  
 * @author Elia Νicolaou 1012334. - Hw5 - EPL222
 * @version 1.0
 * @since 1/5/20
 *
 */
public class Web_Crawler_Main {

	static Scanner scan = new Scanner(System.in);

	/** This function just reads a string - this string represents the starting link of our program.
	 * @return
	 */
	private static String Read_String() {
		String s = null;
		//in order to find if the user wants to give the starting link - i added a question
		// 1 = means he/she wants to add the starting root = 0 wants to use the default.
		System.out.println("--- Do you want to give the root (starting link ) : answer 1 for yes , 0 for no --- ");
		
		int n = scan.nextInt();
		if (n == 1) {
			System.out.print("--- Please give the first URL - the first link : ");
			if (scan.hasNext())
				s = scan.next();
		}

		return s;
	}

	/** This is the main function . Here the user adds all the necessary information.
	 *  it doesn't use any parameters.
	 * @param args
	 * @throws MalformedURLException
	 */
	public static void main(String[] args) throws MalformedURLException {
		
		//getting the information from the user
		
		System.out.println("--- WELCOME TO THE WEB CRAWLER PROGRAM ---");
		System.out.println("- You have to give some information first : ");
		
		//first we get the number of threads -> must be between 5-10
		System.out.println("-- Please give the number of threads:");

		int url_threads = scan.nextInt();
		while (url_threads < 5 || url_threads > 10) {
			System.out.println("The number of threads must be between 5 and 10 : Please give again :");
			url_threads = scan.nextInt();
		}

		//second the depth we want the program to reach , must be >3
		System.out.println("-- Please give the number of depth you want to reach :");
		int depth = scan.nextInt();
        while(depth<3) {
        	System.out.println("-- Sorry! The depth must be greater or equal 3. Give again:");
        	depth = scan.nextInt();
        }
		
		String root;
		root = Read_String(); //reads the starting link (third)
		
		
        if(root == null) {
        	
        	root = "https://www.cs.ucy.ac.cy";
        }
        
        //my program first , it finds all the links that the starting link can reach
        
		Link starting_link = null;

		starting_link = new Link(new URL(root));

		System.out.println(" <depth>    <thread id>   <URL>");
		
		//creating the lists - lists consists two arraylists one for the links we want to find
		//and one for the already used links.
		ToDo_Lists lists = new ToDo_Lists(starting_link);

		//the link catcher is the part that finds all the possible links the program can reach
		LinkCatcher catcher = new LinkCatcher(starting_link, lists);
		catcher.links_catcher();

		//creating the threads & monitor
		Monitor_URL monitor = new Monitor_URL_Impl(depth, starting_link, lists);

		Thread[] links_th = new Thread[url_threads];

		int ID = 0;
		for (int i = 0; i < url_threads; i++) {
			links_th[i] = new Thread(new MyURL(monitor, ID, lists));
			ID++;
		}

		//run the threads
		for (int i = 0; i < url_threads; i++) {
			links_th[i].start();
		}

	}
}
