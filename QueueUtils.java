/**
 * COPYRIGHT MATERIAL -- DO NOT DISTRIBUTE
 *
 * @author Mehrdad Sabetzadeh 
 */
public class QueueUtils {
	public static Queue<String> merge(Queue<String>[] array) {
		if (array == null || array.length == 0) {
			throw new IllegalArgumentException();
		}

		Queue<String> result = new LinkedQueue<String>();
		boolean allEmpty = false;
		
		while (!allEmpty) {
			allEmpty = true;
			for (int i = 0; i < array.length; i++) {
				if (!array[i].isEmpty()) {
					allEmpty = false;
					result.enqueue(array[i].dequeue());
				}
			}
		}
		return result;
	}
}