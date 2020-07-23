package com.example.coreweb.utils;

import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public final class FileIO {
    private FileIO(){}

    public static File convertToFile(MultipartFile multipartFile) {
        if (multipartFile==null) return null;
        File file = new File(multipartFile.getOriginalFilename());
        try (OutputStream outputStream = new FileOutputStream(file)) {
            outputStream.write(multipartFile.getBytes());
        } catch (IOException e) {
        }
        return file;
    }

    public static List<byte[]> convertMultipartFiles(MultipartFile[] multipartFiles) {
        List<byte[]> filesList = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            try {
                Image image = ImageIO.read(FileIO.convertToFile(multipartFile));
                if (image != null) filesList.add(multipartFile.getBytes());
            } catch (IOException e) {
            }
        }

        return filesList;
    }

    public static boolean isNotEmpty(MultipartFile[] multipartFiles) {
        if (multipartFiles == null) return false;
        for (MultipartFile multipartFile : multipartFiles) {
            if (!multipartFile.isEmpty())
                return true;
        }
        return false;
    }

    public static byte[] getScaledImage(byte[] image, int width, int height) throws IOException {
//        InputStream is = new ByteArrayInputStream(image);
//        OutputStream os = new ByteArrayOutputStream();
//        Thumbnails.of(is)
//                .size(width, height)
//                .toOutputStream(os);
//        os.write(image);
        Image img = ImageIO.read(new ByteArrayInputStream(image));
        BufferedImage bi = createResizedCopy(img,width,height,true);

        return getBytes(bi);
    }

    private static BufferedImage createResizedCopy(Image originalImage,
                                                   int scaledWidth, int scaledHeight,
                                                   boolean preserveAlpha) {
        System.out.println("resizing...");
        int imageType = preserveAlpha ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
        BufferedImage scaledBI = new BufferedImage(scaledWidth, scaledHeight, imageType);
        Graphics2D g = scaledBI.createGraphics();
        if (preserveAlpha) {
            g.setComposite(AlphaComposite.Src);
        }
        g.drawImage(originalImage, 0, 0, scaledWidth, scaledHeight, null);
        g.dispose();
        return scaledBI;
    }

    public static byte[] getBytes(BufferedImage img) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(img, "png", baos);
        return baos.toByteArray();
    }


    public static byte[] scaleImage(byte[] image,String ext, int height) throws IOException {
        // Get a BufferedImage object from a byte array

        InputStream in = new ByteArrayInputStream(image);
        BufferedImage originalImage = ImageIO.read(in);
        BufferedImage resizedImage = resizeImage(originalImage, height);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(resizedImage, ext, baos);
        baos.flush();
        image = baos.toByteArray();
        baos.close();


        return image;
    }

    private static BufferedImage resizeImage(BufferedImage originalImage, int height){

        int type = originalImage.getType() == 0? BufferedImage.TYPE_INT_ARGB : originalImage.getType();

        int IMG_HEIGHT = height;
        int IMG_WIDTH = height * originalImage.getWidth()/originalImage.getHeight();

        BufferedImage resizedImage = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, type);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, IMG_WIDTH, IMG_HEIGHT, null);
        g.dispose();
        g.setComposite(AlphaComposite.Src);

        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        return resizedImage;
    }

}
