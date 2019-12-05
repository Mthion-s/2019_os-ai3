
/**
 * 新的比较器
 */
import java.util.Comparator;
 
public class NewComparator implements Comparator<Struct> {
 
	@Override
	public int compare(Struct o1, Struct o2) {
		if(o1.fvalue>o2.fvalue)
			return 1;
		else if(o1.fvalue<o2.fvalue)
			return -1;
		else
			return 0;
	}
 
}
