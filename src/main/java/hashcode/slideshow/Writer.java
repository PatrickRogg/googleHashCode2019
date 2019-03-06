package hashcode.slideshow;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class Writer {

    private String folderName;

    public Writer(String folderName) {
        this.folderName = folderName;
    }

    public int createOutPutFile(String fileName, List<Slide> slideshow) {
        Scorer sc = new Scorer();
        int score = sc.getScore(slideshow);
        File file = new File(folderName + "/" + (fileName.replace(".", "_")) + "/" + score + "/" + fileName);
        file.getParentFile().mkdirs();
        try {
            file.createNewFile();

            PrintWriter writer = new PrintWriter(file);
            writer.write("" + slideshow.size());


            for (Slide slide : slideshow) {
                writer.write("\n");
                if (!slide.isValid()) {
                    throw new RuntimeException("slde invalid :" + slide);
                }

                writer.write("" + slide.getFirstPhoto().getId());


                if (slide.getSecondPhoto() != null) {
                    writer.write(" " + slide.getSecondPhoto().getId());
                }

            }
            System.out.println(fileName + ": " + score);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return score;
    }
}
