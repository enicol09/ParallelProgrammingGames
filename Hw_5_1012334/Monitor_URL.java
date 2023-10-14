
/** This is the interface for the monitor
 *  The monitor has three important functions 
 *   Getter - get the links from the list
 *   Used - check whether the depth must be change
 *   Remove - remove a link from the list
 *   
 * @author Elia Νicolaou 1012334. - Hw5 - EPL222
 * @version 1.0
 * @since 1/5/20
 *
 */
public interface Monitor_URL {

	public Link Getter(int id) ;

	public void Used(Link link);

	void remove(Link link);
  
   
}
