
/**
 * this class is about threads. Essentially it is the object of the threads.
 * From here, the functions of the monitor will be performed. The threads run
 * indefinitely.
 * 
 * @author Elia Νicolaou 1012334. - Hw5 - EPL222
 * @version 1.0
 * @since 1/5/20
 *
 */
public class MyURL implements Runnable {

	Link link; // the link that the thread will have
	Monitor_URL MyMonitor; // my monitor
	int ID; // the id of the thread
	LinkCatcher catcher; // the linkcatcher - from here we will get all the next links
	ToDo_Lists lists; // the objet toDo_Lists that has the needed lists.

	/**
	 * This is the constructor.
	 * 
	 * @param Mymonitor - the monitor we use
	 * @param ID        - the ID of the thread
	 * @param lists     - the todo_lists & used.
	 */
	public MyURL(Monitor_URL Mymonitor, int ID, ToDo_Lists lists) {
		this.MyMonitor = Mymonitor;
		this.ID = ID;
		this.lists = lists;
	}

	/**
	 * This is the run method that runs actually indefinitely. The thread uses three
	 * main functions of the monitor, Getter - in order to take the link Remove - in
	 * order to remove a link from the list Used - in order to find out if the depth
	 * is going to be changed.
	 *
	 */
	@Override
	public void run() {

		while (true) { // run indefinitely

			link = MyMonitor.Getter(ID); // first getter

			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			// if the link we got is not null......

			if (link != null) {
				//finding all the new links
				
				catcher = new LinkCatcher(link, lists);
				catcher.links_catcher();
			
				//removing the link & checking if the depth must be changed.
				MyMonitor.remove(link);
				
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				
				MyMonitor.Used(link);
			}
		}

	}

}
