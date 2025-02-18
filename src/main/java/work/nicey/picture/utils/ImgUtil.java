package work.nicey.picture.utils;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileNameUtil;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ImgUtil {

    public static void main(String[] args) {
//        try {
//            File imageFile = new File("C:\\Users\\Zhaoyu\\Pictures\\a.jpg");
//            Color dominantColor = getDominantColor(imageFile, 100);
//            System.out.println("主色调RGB值: " + dominantColor);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        // https://vigen-invi.oss-cn-shanghai.aliyuncs.com/service_dashscope/ImageOutPainting/2025-02-18/public/4d0f5e77-c0ff-4dee-a757-138931343ef4/result-9fe6803d-e696-4fd6-8e51-60f3b2bec1a6.jpg?OSSAccessKeyId=LTAI5t7aiMEUzu1F2xPMCdFj&Expires=1739857160&Signature=6xVzBltg9diczJ43lpKthQPF6e0%3D
        // result-9fe6803d-e696-4fd6-8e51-60f3b2bec1a6.jpg
        String name = "https://vigen-invi.oss-cn-shanghai.aliyuncs.com/service_dashscope/ImageOutPainting/2025-02-18/public/4d0f5e77-c0ff-4dee-a757-138931343ef4/result-9fe6803d-e696-4fd6-8e51-60f3b2bec1a6.jpg?OSSAccessKeyId=LTAI5t7aiMEUzu1F2xPMCdFj&Expires=1739857160&Signature=6xVzBltg9diczJ43lpKthQPF6e0%3D";
        System.out.println(FileNameUtil.getName(name));
        System.out.println(FileUtil.mainName(name));
        System.out.println(FileUtil.getSuffix(name));

    }

    public static Color getDominantColor(File imageFile, int scaleWidth) throws IOException {
        BufferedImage image = ImageIO.read(imageFile);
        BufferedImage scaledImage = scaleImage(image, scaleWidth);

        Map<Integer, Integer> colorCountMap = new HashMap<>();
        int width = scaledImage.getWidth();
        int height = scaledImage.getHeight();

        // 遍历所有像素统计颜色
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = scaledImage.getRGB(x, y);
                int quantizedColor = quantizeColor(rgb);
                colorCountMap.put(quantizedColor,
                        colorCountMap.getOrDefault(quantizedColor, 0) + 1);
            }
        }

        // 获取出现频率最高的颜色
        int dominantQuantizedColor = Collections.max(colorCountMap.entrySet(),
                Comparator.comparingInt(Map.Entry::getValue)).getKey();

        return getColorFromQuantized(dominantQuantizedColor);
    }

    // 颜色量化（将RGB各分量从8位减少到5位）
    private static int quantizeColor(int rgb) {
        int r = (rgb >> 16) & 0xFF;
        int g = (rgb >> 8) & 0xFF;
        int b = rgb & 0xFF;
        return ((r >> 3) << 10) | ((g >> 3) << 5) | (b >> 3);
    }

    // 从量化值还原颜色（取颜色区间中间值）
    private static Color getColorFromQuantized(int quantizedColor) {
        int r = ((quantizedColor >> 10) & 0x1F) << 3;
        int g = ((quantizedColor >> 5) & 0x1F) << 3;
        int b = (quantizedColor & 0x1F) << 3;

        // 添加中间值偏移（+4）
        r = Math.min(255, r + 4);
        g = Math.min(255, g + 4);
        b = Math.min(255, b + 4);

        return new Color(r, g, b);
    }

    // 图片缩放处理
    private static BufferedImage scaleImage(BufferedImage original, int targetWidth) {
        if (targetWidth <= 0 || original.getWidth() <= targetWidth) {
            return original;
        }

        int originalWidth = original.getWidth();
        int originalHeight = original.getHeight();
        int targetHeight = (int) ((double) originalHeight / originalWidth * targetWidth);

        BufferedImage scaledImage = new BufferedImage(
                targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = scaledImage.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(original, 0, 0, targetWidth, targetHeight, null);
        g.dispose();

        return scaledImage;
    }

    // 扩展方法：获取前N个主色调
    public static List<Color> getTopDominantColors(File imageFile, int topN, int scaleWidth) throws IOException {
        BufferedImage image = ImageIO.read(imageFile);
        BufferedImage scaledImage = scaleImage(image, scaleWidth);

        Map<Integer, Integer> colorCountMap = new HashMap<>();
        int width = scaledImage.getWidth();
        int height = scaledImage.getHeight();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = scaledImage.getRGB(x, y);
                int quantizedColor = quantizeColor(rgb);
                colorCountMap.put(quantizedColor,
                        colorCountMap.getOrDefault(quantizedColor, 0) + 1);
            }
        }

        List<Map.Entry<Integer, Integer>> sortedEntries = new LinkedList<>(colorCountMap.entrySet());
        sortedEntries.sort((e1, e2) -> e2.getValue().compareTo(e1.getValue()));

        List<Color> topColors = new LinkedList<>();
        for (int i = 0; i < Math.min(topN, sortedEntries.size()); i++) {
            topColors.add(getColorFromQuantized(sortedEntries.get(i).getKey()));
        }

        return topColors;
    }
}
