package tools.images;

import org.imgscalr.Scalr;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class RescaleSpritesMain {

    private static final String SRC_ROOT = "E:/dev/libgdx/pacman/pacman-android/assets/sprites/";
    private static final String DST_ROOT = "E:/dev/libgdx/pacman/pacman-android/assets/sprites_x2/";

    
    public void rescale_x2(String rootSrc, String rootDst) throws IOException {
        for (File file : new File(rootSrc).listFiles()) {
            if (!file.getName().endsWith(".png")){
                continue;
            }
            rescale_x2(file, rootDst);
        }
    }
             

    private void rescale_x2(File srcFile, String dstRoot) throws IOException {
        File src = srcFile;
        String dstFilename = src.getName();
        File dst = new File(dstRoot, dstFilename);
        Files.copy(src.toPath(), dst.toPath(), StandardCopyOption.REPLACE_EXISTING);
        System.out.printf("old=%s, new=%s\n", src.getPath(), dst.getPath());
        BufferedImage dstImage = ImageIO.read(dst);
        dstImage.getWidth();
        BufferedImage doubleSized = Scalr.resize(
                dstImage,
                Scalr.Method.ULTRA_QUALITY,
                Scalr.Mode.FIT_EXACT,
                dstImage.getWidth() * 2,
                dstImage.getHeight() * 2);
        ImageIO.write(doubleSized, "png", dst);
    }

    public static void main(String[] args) throws IOException {
        new RescaleSpritesMain().rescale_x2(
                "E:/dev/libgdx/pacman/pacman-android/assets/tiledmap",
                "E:/dev/libgdx/pacman/pacman-android/assets/tiledmap_x2");

    }

}
