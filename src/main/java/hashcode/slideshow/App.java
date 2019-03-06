package hashcode.slideshow;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import hashcode.slideshow.generator.Generator;

public class App {

    public static final String[] fileList = {
//			"a_example.txt",
//			"c_memorable_moments.txt",
//			"d_pet_pictures.txt",
            "e_shiny_selfies.txt",
//			"b_lovely_landscapes.txt"
    };

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        Writer writer = new Writer("outputs/");
        List<Foto> photos = new ArrayList<Foto>();
        List<Slide> slideshow = new ArrayList<Slide>();
        int score = 0;

        for (String fileName : fileList) {
            Parser p = new Parser(fileName);
            System.out.println(fileName);
            photos = p.readFile();
            Collections.sort(photos);
            slideshow = new Generator().createSlideshow(photos);

            score += writer.createOutPutFile(fileName, slideshow);
        }
        System.out.println("_________________________");
        System.out.println("Summe: " + score);

        System.out.println("Time: " + (System.currentTimeMillis() - startTime));
    }
}
