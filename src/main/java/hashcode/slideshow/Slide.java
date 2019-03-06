package hashcode.slideshow;

import java.util.HashSet;
import java.util.Set;

public class Slide implements Comparable<Slide> {
	
	private Foto firstPhoto;
	
	private Foto secondPhoto;

	public Slide(Foto firstPhoto, Foto secondPhoto) {
		super();
		this.firstPhoto = firstPhoto;
		this.secondPhoto = secondPhoto;
	}

	public Foto getFirstPhoto() {
		return firstPhoto;
	}

	public void setFirstPhoto(Foto firstPhoto) {
		this.firstPhoto = firstPhoto;
	}

	public Foto getSecondPhoto() {
		return secondPhoto;
	}

	public void setSecondPhoto(Foto secondPhoto) {
		this.secondPhoto = secondPhoto;
	}
	
	public Set<String> getTags() {
		Set<String> tags = new HashSet<String>();
		if(firstPhoto != null) {
			tags.addAll(firstPhoto.getTags());
		}
		
		if(secondPhoto != null) {
			tags.addAll(secondPhoto.getTags());
		}
		return tags;
	}
	
	public boolean isValid() {
		if (secondPhoto != null) {
			if (!firstPhoto.isHorizontal() && !secondPhoto.isHorizontal())  {
				return true;
			} else return false;
		}
		else {
			if (firstPhoto.isHorizontal())  {
				return true;
			} else return false;
		}
	}

	public int compareTo(Slide compareTo) {
		return this.getTags().size() - compareTo.getTags().size();
	}
	


}
