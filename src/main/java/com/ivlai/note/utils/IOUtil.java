package com.ivlai.note.utils;

import java.io.*;

public class IOUtil {


    /**
     * 将输入流转换为输出流
     *
     * @param inputStream  输入流
     * @param outputStream 输出流
     * @throws IOException IO获取异常
     */
    @Deprecated
    public static void inputStreamToOutputStream(InputStream inputStream, OutputStream outputStream) throws IOException {
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);

        byte[] bytes = new byte[1024];
        while (bufferedInputStream.read(bytes) >= 0) {
            bufferedOutputStream.write(bytes, 0, bytes.length);
        }
        inputStream.close();
        outputStream.close();
    }


    /**
     * 将输入流转换为输出流
     *
     * @param inputStream  输入流
     * @param outputStream 输出流
     * @throws IOException IO获取异常
     */
    public static void inputStreamToOutputStreamForJava8(InputStream inputStream, OutputStream outputStream) throws IOException {
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
        bufferedInputStream.transferTo(bufferedOutputStream);
        inputStream.close();
        outputStream.close();
    }

}