import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * This is the most important class of our program as it implements the monitor.
 * The monitor is what makes our threats run synchronously and prevents
 * problems.
 * 
 * @author Elia Νicolaou 1012334. - Hw5 - EPL222
 * @version 1.0
 * @since 1/5/20
 *
 */
public class Monitor_URL_Impl implements Monitor_URL {

	ToDo_Lists list; // the lists - todolist & used
	private final Condition getter; // we have onlu one condition variable = getter
	private final Lock lock; // lock
	static int depth = 1; // the depth
	int stop; // the depth that the program must stop

	/**
	 * This is the constructor. The constructor initializes the locker & the
	 * condition variable.
	 * 
	 * @param stop          - the depth that the program must stop
	 * @param starting_link - the first link
	 * @param list          - the lists
	 */
	public Monitor_URL_Impl(int stop, Link starting_link, ToDo_Lists list) {

		// initialization
		this.lock = new ReentrantLock();
		getter = lock.newCondition();
		this.list = list;
		this.stop = stop;
	}

	/**
	 * This is the most important function of the whole program. It is the one who
	 * gives synchronized the threads links to run and gives them the okay about
	 * whether they will run or wait because they have a different depth and there
	 * are other threats that are not over.
	 * @param ID = the id of the thread.
	 * 
	 */
	@Override
	public Link Getter(int ID) {
		lock.lock();
		Link link = null;

		try {
			link = list.Take_Link();

			if (link != null) { // if it is not null - the list didn't had any link.
				while (list.Change_Depth(depth) || link.depth != depth) { // checking whether is okay to run.
					System.out.println(
							"I am thread with ID : " + ID + "and I am Waiting because I am in a different depth");

					getter.await(); // this thread must wait
				}

				link.ID = ID;
			}

			return link;

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
		return null;

	}

	/**
	 * This function decided if the depth need to change . It calls the change depth
	 * function of the ToDo_list . If the depth is going to change then all the
	 * threads that they have been waiting, they get the "okay" to run again. -
	 * SIGNAL ALL-
	 * 
	 * If the depth thas has just finished is the depth we wanted to reach then the
	 * program ends.
	 *
	 *@param link
	 */
	@Override
	public void Used(Link link) {
		lock.lock();
		if (list.Change_Depth(depth)) {
			depth++;
			if (stop < depth) {
				System.out.println("--- WE REACH THE DEPTH WE WANTED :) . END OF THE PROGRAM !!! ---");
				System.exit(0);
			}
			
			System.out.println("---------- CHANGING DEPTH !!!!  -------------");

			getter.signalAll();
		}

		lock.unlock();

	}

	/** 
	 * This function removes safely a link from the list.
	 * @param link - the link to be removed
	 */
	@Override
	public void remove(Link link) {
		lock.lock();
		list.RemoveFromList(link);
		lock.unlock();
	}

}
