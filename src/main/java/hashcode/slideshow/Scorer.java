package hashcode.slideshow;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Scorer {

    public int getScore(Set<String> first, Set<String> second) {
        HashSet<String> onlyFirst = new HashSet<String>(first);
        onlyFirst.removeAll(second);
        HashSet<String> onlySecond = new HashSet<String>(second);
        onlySecond.removeAll(first);

        HashSet<String> both = new HashSet<String>(first);
        both.retainAll(second);
        int score = Math.min(Math.min(onlyFirst.size(), onlySecond.size()), both.size());
        return score;


    }

    public int getScore(Slide a, Slide b) {
        Set<String> photosA = getSlideTags(a);
        Set<String> photosB = getSlideTags(b);
        return getScore(photosA, photosB);
    }

    public static Set<String> getSlideTags(Slide s) {
        Set<String> tags = s.getFirstPhoto().getTags();
        Foto photo2 = s.getSecondPhoto();
        if (photo2 != null) {
            tags.addAll(photo2.getTags());
        }
        return tags;
    }

    public int getScore(List<Slide> slideshow) {
        int score = 0;
        Slide lastSlide = new Slide(new Foto(-1, true, Collections.<String>emptySet()), null);
        for (Slide slide : slideshow) {
            score += getScore(lastSlide, slide);
            lastSlide = slide;
        }

        return score;
    }
}
