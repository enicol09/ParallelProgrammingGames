import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** This class is used 
 * for finding the connected links of a given link 
 * - is the link catcher - and normalized them.
 * 
 **/
public class LinkCatcher {
	
	private Pattern htmltag;
	private Pattern link;
	Link links; //the new link - the link to find the connected links for
	ToDo_Lists list; //the lists to add the new links

	/** This is the constructor.
	 * 
	 * @param url - the link
	 * @param lista - the ToDo_Lists list we use.
	 */
	public LinkCatcher(Link url, ToDo_Lists lista) {
		htmltag = Pattern.compile("<a\\b[^>]*href=\"[^>]*>(.*?)</a>");
		link = Pattern.compile("href=\"[^>]*\">");
		
		this.links = url;
		this.list = lista;
	}

	/** This function is really important for the whole program as is used for 
	 *  finding all the connected links of a given link . It calls the getlinks function. See bellow.
	 *  
	 *  It finds all the links and calls the AddToLinks function of the list class in order
	 *  to find out whether the links must be added to the list or not.
	 *  
	 *  Then it print the link that has been used.
	 * 
	 */
	public void links_catcher() {
		
		ArrayList<String> mylinks = new ArrayList<String>();
		mylinks = (ArrayList<String>) getLinks(links.MyLink);
		for (int i = 0; i < mylinks.size(); i++) {
			URL newLink = null;
			try {
				newLink = new URL(mylinks.get(i));
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Link myNewLink = new Link(newLink);
			myNewLink.depth= links.depth + 1;
			list.AddToLinks(myNewLink);
		}		
	
		System.out.println(links);
		
	}

	/** Return the connected links found of a given URL.
	 * 
	 * @param url - the given url
	 * 
	 * @return a list that contains the names of the connected urls.
	 */
	public List<String> getLinks(URL url) {
		List<String> links = new ArrayList<String>();
		try {
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));
			String s;
			StringBuilder builder = new StringBuilder();
			while ((s = bufferedReader.readLine()) != null) {
				builder.append(s);
			}

			Matcher tagmatch = htmltag.matcher(builder.toString());
			while (tagmatch.find()) {
				Matcher matcher = link.matcher(tagmatch.group());
				matcher.find();
				String link = matcher.group().replaceFirst("href=\"", "").replaceFirst("\">", "")
						.replaceFirst("\"[\\s]?target=\"[a-zA-Z_0-9]*", "");
				int last = link.indexOf('"');
				if (last != -1)
					link = link.substring(0, last);
				
				if (valid(link)) {
					links.add(makeAbsolute(url.toString(), link));
				}
			}
		} catch (Exception e) {
			
		} 
		
		return links;
	}

	/** Check if a given string is valid - ot not
	 * @param s - the string to be checked.
	 * @return true if it is valid , false if it is not.
	 */
	private boolean valid(String s) {
		if (s.matches("javascript:.*|mailto:.*")) {
			return false;
		}
		if((s==null)||(s.length() == 0))
			return false;
		return true;
	}

	
	/** Normalizes the url.
	 * 
	 * @param url - the given url to normlize
	 * @param link - the link to be normalized
	 * 
	 * @return the normalized link
	 */
	private String makeAbsolute(String url, String link) {
		if (link.matches("http://.*") || link.matches("https://.*")) {
			return link;
		}
		if (link.matches("/.*") && url.matches(".*$[^/]")) {
			return url + "/" + link;
		}
		if (link.matches("[^/].*") && url.matches(".*[^/]")) {
			return url + "/" + link;
		}
		if (link.matches("/.*") && url.matches(".*[/]")) {
			return url + link;
		}
		if (link.matches("/.*") && url.matches(".*[^/]")) {
			return url + link;
		}
		throw new RuntimeException("Cannot make the link absolute. Url: " + url + " Link " + link);
	}

}
