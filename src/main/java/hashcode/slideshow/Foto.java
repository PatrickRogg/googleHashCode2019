package hashcode.slideshow;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents the data read from one file - the Parser should fill this.
 *
 */
public class Foto implements Comparable<Foto> {
	private int id;
	private boolean isHorizontal;
	private Set<String> tags = new HashSet<String>();
	
	public Foto(int id, boolean isHorizontal, Set<String> tags2) {
		super();
		this.id = id;
		this.isHorizontal = isHorizontal;
		this.tags = tags2;
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public boolean isHorizontal() {
		return isHorizontal;
	}
	
	public void setHorizontal(boolean isHorizontal) {
		this.isHorizontal = isHorizontal;
	}

	public Set<String> getTags() {
		return tags;
	}

	public void setTags(Set<String> tags) {
		this.tags = tags;
	}
	
	@Override
	public String toString() {
		return this.id + " " + this.isHorizontal + " " + this.tags;
	}

	public int compareTo(Foto compareTo) {
		return compareTo.getTags().size() - this.tags.size();
	}
}