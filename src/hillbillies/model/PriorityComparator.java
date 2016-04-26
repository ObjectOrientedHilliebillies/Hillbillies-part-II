package hillbillies.model;

import java.util.Comparator;

public class PriorityComparator implements Comparator<Task>{

	@Override
	public int compare(Task o1, Task o2) {
		return o1.getPriority().compareTo(o2.getPriority());
	}
}
