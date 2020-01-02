package com.ivlai.note.utils;

import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.List;
import java.util.*;

/**
 * 文件处理工具类
 */
@SuppressWarnings({"unchecked"})
public class FileUtil {


    /**
     * 保存从web中上传的图片
     *
     * @param request       request请求
     * @param type          文件分类
     * @param file          文件
     * @param changFileName 是否改变名称
     * @return 文件访问路径
     * @throws IOException IO异常
     */
    public static HashMap<Object, Object> saveWebFileAndReturnPath(HttpServletRequest request, String type, MultipartFile file, boolean changFileName) throws IOException {
        /* 获取部分路径信息 */
        String realPath = request.getServletContext().getRealPath("");
        String yyyyMMdd = TimeUtil.getDateByFormat("yyyyMMdd");
        String hHmmss = changFileName ? TimeUtil.getDateByFormat("HHmmss") + "_" : "";

        /* 获取文件名 */
        String filename = file.getOriginalFilename(); /* 旧的文件名 */
        String original = hHmmss + filename; /* 新的文件保存名称 */

        /* 生成前置文件夹路径 */
        String folderPath = realPath + "\\upload\\" + type + "\\" + yyyyMMdd + "\\";
        File folderPathFile = new File(folderPath);
        if (!folderPathFile.exists()) {
            if (!folderPathFile.mkdirs()) {
                throw new RuntimeException("创建前置文件夹失败");
            }
        }

        /* 生成文件路径 */
        String filePath = folderPath + original;
        /* 保存文件 */
        File newFile = new File(filePath);
        file.transferTo(newFile);

        /* 生成文件URL访问路径 */
        String urlPath = "/upload/" + type + "/" + yyyyMMdd + "/" + original;
        /* 返回文件保存信息到Map */
        HashMap<Object, Object> map = new HashMap<>();
        map.put("url", urlPath);
        map.put("title", filename);
        map.put("original", original);
        return map;
    }

    /**
     * 解析Base64中的文件并保存为指定格式
     *
     * @param request request请求
     * @param type    分类 - 用于区分文件保存位置
     * @param upfile  base64编码字符串
     * @return 文件保存信息
     */
    public static HashMap<Object, Object> saveWebFileAndReturnPathByBase64(HttpServletRequest request, String type, String upfile, String suffix) throws IOException {

        /* 获取部分路径信息 */
        String realPath = request.getServletContext().getRealPath("");
        String yyyyMMdd = TimeUtil.getDateByFormat("yyyyMMdd");
        String hHmmss = TimeUtil.getDateByFormat("HHmmss");

        /* 解析Base64编码 */
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] decode = decoder.decode(upfile);

        /* 生成前置文件夹路径 */
        String folderPath = realPath + "\\upload\\" + type + "\\" + yyyyMMdd + "\\";
        File folderPathFile = new File(folderPath);
        if (!folderPathFile.exists()) {
            if (!folderPathFile.mkdirs()) {
                throw new RuntimeException("创建前置文件夹失败");
            }
        }

        /* 生成文件名 */
        String original = hHmmss + "_" + type + "." + suffix; /* 新的文件保存名称 */

        /* 生成文件路径 */
        String filePath = folderPath + original;
        /* 保存文件 */
        File newFile = new File(filePath);
        FileImageOutputStream fileImageOutputStream = new FileImageOutputStream(newFile);
        fileImageOutputStream.write(decode);
        fileImageOutputStream.flush();
        fileImageOutputStream.close();

        /* 生成文件URL访问路径 */
        String urlPath = "/upload/" + type + "/" + yyyyMMdd + "/" + original;
        /* 返回文件保存信息到Map */
        HashMap<Object, Object> map = new HashMap<>();
        map.put("url", urlPath);
        map.put("title", original);
        map.put("original", original);
        return map;
    }

    /**
     * @param folderFile      文件夹
     * @param fileList        文件列表集合
     * @param webFilePathList web访问路径集合
     * @param webPath         相对web访问路径
     * @param nameFilter      名称过滤器
     */
    public static void selectFileListAndWebFilePathByFolderPath(
            File folderFile, List<File> fileList, List<String> webFilePathList, String webPath, List<String> nameFilter
    ) {
        File[] files = folderFile.listFiles();
        if (null != files) {
            for (File file : files) {
                if (file.isFile()) {
                    /* 文件： */
                    String fileSuffix = getFileSuffix(file);
                    if (null != nameFilter && nameFilter.size() > 0) {
                        for (String name : nameFilter) {
                            if (name.equals(fileSuffix)) {
                                if (null != fileList) fileList.add(file);
                                if (null != webFilePathList) webFilePathList.add(webPath + "/" + file.getName());
                                break;
                            }
                        }
                    } else {
                        if (null != fileList) fileList.add(file);
                        if (null != webFilePathList) webFilePathList.add(webPath + "/" + file.getName());
                    }
                } else if (file.isDirectory()) {
                    /* 文件夹： */
                    selectFileListAndWebFilePathByFolderPath(file, fileList, webFilePathList, webPath + "/" + file.getName(), nameFilter);
                }
            }
        }
    }

    /**
     * 从File读取图片并转换为指定大小
     *
     * @param imgFile      图片地址
     * @param w            宽度 - px
     * @param h            高度 - px
     * @param s            缩放比例 - s%
     * @param outputStream 输出流
     */
    public static void zoomImgByFile(File imgFile, float w, float h, float s, OutputStream outputStream) throws IOException {

        BufferedImage bufferedImage = ImageIO.read(imgFile);
        String suffix = imgFile.getName().substring(imgFile.getName().lastIndexOf(".") + 1);

        /* 切换图片大小 */
        roomImgSize(w, h, s, outputStream, bufferedImage, suffix);
    }

    /**
     * 从URL获取文件并设置其大小
     *
     * @param url          文件URL
     * @param w            宽度 - px
     * @param h            高度 - px
     * @param s            缩放比例 - s%
     * @param outputStream 图片输出流
     * @param suffix       后缀名
     */
    public static void zoomImgByUrl(String url, float w, float h, float s, OutputStream outputStream, String suffix) throws IOException {

        URL url1 = new URL(NetUtil.cnUrlEncoding(url));

        BufferedImage bufferedImage = ImageIO.read(url1);

        /* 切换图片大小 */
        roomImgSize(w, h, s, outputStream, bufferedImage, suffix);
    }

    /**
     * 切换图片大小
     *
     * @param w 宽度
     * @param h 高度
     * @param s 缩放比例
     */
    private static void roomImgSize(float w, float h, float s, OutputStream outputStream, BufferedImage bufferedImage, String suffix) throws IOException {
        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();

        if (s > 1000) s = 1000;
        if (w > 20000) w = 20000;
        if (h > 20000) h = 20000;

        int newWidth;
        int newHeight;

        if (0 != w && 0 != h) {
            /* 同时有宽高 */
            newWidth = (int) w;
            newHeight = (int) h;
            newWidth = Math.max(newWidth, 1);
            newHeight = Math.max(newHeight, 1);
        } else if (0 == w && 0 == h) {
            /* 没有宽高 */
            newWidth = width;
            newHeight = height;
        } else if (0 == w) {
            /* 只有高 */
            newHeight = (int) h;
            newWidth = (int) (h / height * width);
            newWidth = Math.max(newWidth, 1);
        } else {
            /* 只有宽 */
            newWidth = (int) w;
            newHeight = (int) (w / width * height);
            newHeight = Math.max(newHeight, 1);
        }

        if (0 != s) {
            newWidth = (int) (newWidth * s / 100);
            newHeight = (int) (newHeight * s / 100);
            newWidth = Math.max(newWidth, 1);
            newHeight = Math.max(newHeight, 1);
        }

        BufferedImage tag = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_3BYTE_BGR);
        //绘制改变尺寸后的图
        tag.getGraphics().drawImage(bufferedImage, 0, 0, newWidth, newHeight, null);
        ImageIO.write(tag, suffix, outputStream);
    }

    /**
     * 复制文件 - 使用字节流 - 可复制所有类型文件
     *
     * @param file      源文件
     * @param file_save 保存的目标文件
     */
    public static void copyFile(File file, File file_save) throws IOException {
        BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file_save));
        byte[] bytes = new byte[1024];
        int length;
        while ((length = bufferedInputStream.read(bytes)) != -1) {
            bufferedOutputStream.write(bytes, 0, length);
            bufferedOutputStream.flush();
        }
        bufferedInputStream.close();
        bufferedOutputStream.close();
    }

    /**
     * 将指定文字保存到文件
     *
     * @param text 要保存的文本
     * @param file 要保存的文件
     */
    public static void saveFileByString(String text, File file) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
        bufferedWriter.write(text);
        bufferedWriter.flush();
        bufferedWriter.close();
    }

    /**
     * 将文件大小转换为 字符串类型
     *
     * @param size 文件大小
     * @return 字符串类型的文件大小
     */
    public static String countFileSizeToString(long size) {
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        if (size < 1024) {
            return String.valueOf(size) + "B";
        } else if (size < 1048576) {
            String format = decimalFormat.format(size / 1024.0);
            return format + "KB";
        } else if (size < 1073741824) {
            String format = decimalFormat.format(size / 1048576.0);
            return format + "MB";
        } else {
            String format = decimalFormat.format(size / 1073741824.0);
            return format + "GB";
        }
    }

    /**
     * 获取文件后缀名
     *
     * @param file 文件
     * @return 后缀名
     */
    public static String getFileSuffix(File file) {
        if (file.isFile()) {
            String name = file.getName();
            int i = name.lastIndexOf(".");
            return name.substring(i);
        } else {
            throw new RuntimeException("The specified path is not a file.");
        }
    }

    /**
     * 获取文件后缀名
     *
     * @param filePath 文件路径
     * @return 后缀名
     * @throws FileNotFoundException 文件不存在异常
     */
    public static String getFileSuffix(String filePath) throws FileNotFoundException {
        return getFileSuffix(new File(filePath));
    }


    /**
     * 生成验证码
     *
     * @param codeCount  验证码数值个数
     * @param width      宽度
     * @param height     高度
     * @param fontHeight 行高
     * @return 验证码和验证码图片
     */
    public static Map<String, Object> getVerificationCodeAndCodePic(
            Integer codeCount
            , Integer width
            , Integer height
            , Integer fontHeight
            , Integer codeX
            , Integer codeY
    ) {

        /* 验证码可用字符 */
        char[] codeSequence = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        if (null == codeCount) codeCount = 4; // 定义图片上显示验证码的个数
        if (null == width) width = 90; // 宽度
        if (null == height) height = 20; // 高度
        if (null == fontHeight) fontHeight = 18; // 行高
        if (null == codeX) codeX = 15; // 字体宽度
        if (null == codeY) codeY = 16; // 字体高度

        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics gd = bufferedImage.getGraphics();
        // 创建一个随机数生成器类
        Random random = new Random();
        // 将图像填充为白色
        gd.setColor(Color.WHITE);
        gd.fillRect(0, 0, width, height);

        // 创建字体，字体的大小应该根据图片的高度来定。
        Font font = new Font("Fixedsys", Font.BOLD, fontHeight);
        // 设置字体。
        gd.setFont(font);

        // 画边框。
        gd.setColor(Color.BLACK);
        gd.drawRect(0, 0, width - 1, height - 1);

        // 随机产生40条干扰线，使图象中的认证码不易被其它程序探测到。
        gd.setColor(Color.BLACK);
        for (int i = 0; i < 30; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            gd.drawLine(x, y, x + xl, y + yl);
        }
        // randomCode用于保存随机产生的验证码，以便用户登录后进行验证。
        StringBuffer randomCode = new StringBuffer();
        int red = 0, green = 0, blue = 0;
        // 随机产生codeCount数字的验证码。
        for (int i = 0; i < codeCount; i++) {
            // 得到随机产生的验证码数字。
            String code = String.valueOf(codeSequence[random.nextInt(36)]);
            // 产生随机的颜色分量来构造颜色值，这样输出的每位数字的颜色值都将不同。
            red = random.nextInt(255);
            green = random.nextInt(255);
            blue = random.nextInt(255);

            // 用随机产生的颜色将验证码绘制到图像中。
            gd.setColor(new Color(red, green, blue));
            gd.drawString(code, (i + 1) * codeX, codeY);

            // 将产生的四个随机数组合在一起。
            randomCode.append(code);
        }
        Map<String, Object> map = new HashMap<String, Object>();
        //存放验证码
        map.put("code", randomCode);
        //存放生成的验证码BufferedImage对象
        map.put("codePic", bufferedImage);
        return map;
    }

    public static Map<String, Object> getVerificationCodeAndCodePic() {
        return getVerificationCodeAndCodePic(null, null, null, null, null, null);
    }
}
