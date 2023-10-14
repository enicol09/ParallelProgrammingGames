import java.util.ArrayList;

/**
This class is helpful in extracting our program and is 
still considered one of the most important. Specifically, 
this list contains two lists. One is the so-called to do 
list that contains all the links that are waiting to be executed. 
The other is α used - vistited list that contains all the links 
that have been executed. This list prevents us from possible repetitions.
 *
 * @author Elia Νicolaou 1012334. - Hw5 - EPL222
 * @version 1.0
 * @since 1/5/20
 *
 */
public class ToDo_Lists {

	public String main_root;  //contains the root of the starting url. (get host)
	Link mainlink;  //the starting link
	
	public ArrayList<Link> Used ; //the list that has the links that have been executed
	public ArrayList<Link> links; //the to-do list.


	/**This is the constructor.
	 * The constructor gets as a parameter the starting_link of the whole program
	 * then it creates the list , and finds the root depending on the starting link.
	 * @param link - the starting link
	 */
	public ToDo_Lists(Link link) {
		this.links = new ArrayList<Link>();
		this.Used =  new ArrayList<Link>();
		this.mainlink = link;
		
		//System.out.println(link);
		
	    main_root = get_host(mainlink);   
	    
	}

	/**
	 * Return the host part of the starting url - link
	 * @param link - the starting link
	 * @return the host part
	 */
	private String get_host(Link link) {
	   return link.MyLink.getHost();	
	}

	/** This function is used for adding links to the links list .
	 *  The links list has all 
	 * the links that are waiting to be executed.
	 * 
	 * @param link  - the link that is going to be added .
	 */
	public void AddToLinks(Link link) {
		//first it checks whether the new link has the same host with the starting link
		if (get_host(link).equals(main_root)) {
			
			if (!Used.contains(link) && !links.contains(link)) // then it checks whether has been executed or added before
				links.add(link);
		} else {
			Used.add(link);
		}
	}
	
	
	/** This function is used for removing links from the links list and adding them into the used list 
	 * in order to prevent repetition.
	 * 
	 * @param link - the link to be removed
	 */
	public void RemoveFromList(Link link) {
		links.remove(link);
		Used.add(link);
	}
	
	
	/** This function is used 
	 *  for taking a link from the links list .  
	 *  If the list is empty it returns null
	 *  
	 * @return a link 
	 */
	public Link Take_Link() {
	  if(links.size() == 0) {
		  return null;
	  }
		for(int i =0 ; i< links.size() ; i++) {
			Link s = links.get(i);
			if(!Used.contains(s)) {
				Used.add(s);
				return s;
			}
		}
		return null;
	}

	/**This function is very important for the operation 
	 * of the program as it essentially decides whether 
	 * the depth should be changed, ie the links should start to deepen more.
	 * If there is a link in the list that still has the depth
	 *  that exists as a parameter then it returns false - the depth must not change
	 *   otherwise it returns true 
	 *   
	 * @param depth  - the depth we want to check
	 * @return true / false based on what i wrote above
	 */
	public boolean Change_Depth(int depth) {
		for (Link s : links) {
			if (s.depth == depth)
				return false;
		}
		return true;
	}

	

}
