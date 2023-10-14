import java.net.URL;

/**
 * This class represents the Urls - the links.
 *  Is a helpful class for our program as it contains all
 *  the necessary information for the links.
 *  
 * @author Elia Nicolaou 1012334
 *
 */
public class Link {
	

	public URL MyLink; //the url 
	public int depth;  // the depth of the link
	public int ID;  // the id of the thread
	static int unique_id = 0; // the unique id of the thres

	/** This is the constructor.
	 * 
	 * @param Mylink the url for the link
	 */
	public Link(URL Mylink) {
		this.MyLink = Mylink;
	}

	/**
	 * This is the toString method.
	 */
	@Override
	public String toString() {
		unique_id++;
		return " < " + depth + " >  < " + ID + " >  < " + MyLink + " > ";
	}

	/** This the equals method.
	 *
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Link other = (Link) obj;
		if (MyLink == null) {
			if (other.MyLink != null)
				return false;
		} else if (!MyLink.equals(other.MyLink))
			return false;
		return true;
	}

}
