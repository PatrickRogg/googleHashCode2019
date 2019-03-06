package hashcode.slideshow;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Parser {
	String filePath;
	
	public Parser(String filePath) {
		this.filePath = filePath;
	}
	
	public List<Foto> readFile() {
	    File file = new File(filePath); 
	    Scanner scanner;
		try {
			scanner = new Scanner(file);
			int photoId = 0;
			List<Foto> photos = new ArrayList<Foto>();
			scanner.nextLine();
			while(scanner.hasNextLine()) {
				String input = scanner.nextLine();
				String[] inputElements = input.split(" ", 3);
				String[] tagsArray = inputElements[2].split(" ");
				Set<String> tags = new HashSet<String>(Arrays.asList(tagsArray));
				
				if ("V".equals(inputElements[0])) {
					Foto photo = new Foto(photoId++, false, tags);
					photos.add(photo);
				} else {
					Foto photo = new Foto(photoId++, true, tags);
					photos.add(photo);
				}
			}
			return photos;
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}
}
