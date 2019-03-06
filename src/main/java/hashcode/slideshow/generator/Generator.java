package hashcode.slideshow.generator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


import hashcode.slideshow.Foto;
import hashcode.slideshow.Scorer;
import hashcode.slideshow.Slide;

public class Generator {

    private static final int MAXLOOPS_VERTICAL = 2;
    private static final int MAX_DIFF_IN_SIZE = 4;

    Scorer scorer = new Scorer();

    public List<Slide> createSlideshow(List<Foto> fotos) {
        List<Foto> verticalFotos = new ArrayList<Foto>();
        List<Foto> horizontalFotos = new ArrayList<Foto>();
        List<Slide> slides = new ArrayList<Slide>();
        for (Foto foto : fotos) {
            if (foto.isHorizontal()) {
                horizontalFotos.add(foto);
                slides.add(new Slide(foto, null));
            } else {
                verticalFotos.add(foto);
            }
        }

        for (int i = 0; i < verticalFotos.size(); i = i + 2) {
            slides.add(new Slide(verticalFotos.get(i), verticalFotos.get(i + 1)));
        }
//		slides.addAll(getVerticalFotoSlides(verticalFotos));

        return getOptimalSlides(slides);
    }

    private List<Slide> getOptimalSlides(List<Slide> slides) {
        List<Slide> result = new ArrayList<Slide>();
        Collections.sort(slides);
        Map<String, List<Slide>> slidesOfTag = getSlidesOfTag(slides);
        result.add(slides.get(0));
        slides.remove(0);

        int slidesSize = slides.size();
        for (int i = 0; i < slidesSize; i++) {
            Slide slide = result.get(i);

            Set<Slide> allSlidesThatContainTagOfSlide = new HashSet<Slide>();

            for (String tag : slide.getTags()) {
                List<Slide> sOfTag = slidesOfTag.get(tag);
                sOfTag.remove(slide);
                slidesOfTag.put(tag, sOfTag);
                allSlidesThatContainTagOfSlide.addAll(sOfTag);

            }

            if (allSlidesThatContainTagOfSlide.size() == 0) {
                result.add(slides.get(0));
                slides.remove(0);
                continue;
            }

            List<Slide> innerLoopSlides = new ArrayList<Slide>(allSlidesThatContainTagOfSlide);
            Slide optimalSlide = null;
            int optimalSlideScore = Integer.MIN_VALUE;
            for (int j = 0; j < innerLoopSlides.size(); j++) {
                Slide currSlide = innerLoopSlides.get(j);
                int currSlideScore = scorer.getScore(slide, currSlide);

                if (currSlideScore > optimalSlideScore) {
                    optimalSlide = currSlide;
                    optimalSlideScore = currSlideScore;
                }

                if (optimalSlideScore >= (slide.getTags().size() / 2)) {
                    break;
                }

                if (j >= 1000) {
                    break;
                }
            }
            slides.remove(optimalSlide);
            result.add(optimalSlide);
            if (i % 1000 == 0) {
                System.out.println("Score: " + scorer.getScore(result));
                System.out.println("Loop: " + i);
            }

        }
        return result;
    }

    private Map<String, List<Slide>> getSlidesOfTag(Collection<Slide> slides) {
        Map<String, List<Slide>> slidesOfTag = new HashMap<String, List<Slide>>();
        for (Slide slide : slides) {
            for (String tag : slide.getTags()) {
                if (slidesOfTag.containsKey(tag)) {
                    List<Slide> currentSlides = slidesOfTag.get(tag);
                    currentSlides.add(slide);
                    slidesOfTag.put(tag, currentSlides);
                } else {
                    List<Slide> currentSlides = new ArrayList<Slide>();
                    currentSlides.add(slide);
                    slidesOfTag.put(tag, currentSlides);
                }
            }

        }
        return slidesOfTag;
    }

    private List<Slide> getVerticalFotoSlides(List<Foto> verticalFotos) {
        List<Slide> result = new ArrayList<Slide>();
        int sumDuplicates = 0;

        while (verticalFotos.size() > 0) {
            Foto foto = verticalFotos.iterator().next();
            verticalFotos.remove(foto);

            List<Foto> innerLoopFotos = new ArrayList<Foto>(verticalFotos);
            Collections.reverse(innerLoopFotos);
            Foto optimalFoto = null;
            int optimalFotoDuplications = Integer.MAX_VALUE;
            int counter = 0;
            while (innerLoopFotos.size() > 0) {
                counter++;
                if (optimalFotoDuplications == 0 || counter > MAXLOOPS_VERTICAL) {
                    break;
                }
                Foto currFoto = innerLoopFotos.iterator().next();
                int currFotoTagSize = currFoto.getTags().size();
                Foto currFotoStorage = currFoto;
                currFoto.getTags().addAll(foto.getTags());
                int currFotoDuplications = currFotoTagSize + foto.getTags().size() - currFoto.getTags().size();
                if (optimalFotoDuplications > currFotoDuplications) {
                    optimalFoto = currFotoStorage;
                    optimalFotoDuplications = currFotoDuplications;
                }
                innerLoopFotos.remove(currFoto);
            }
            sumDuplicates += optimalFotoDuplications;
            verticalFotos.remove(optimalFoto);
            result.add(new Slide(foto, optimalFoto));
        }
        System.out.println("SumDuplicates: " + sumDuplicates);
        return result;
    }
}
