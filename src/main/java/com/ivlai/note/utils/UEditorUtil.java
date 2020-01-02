package com.ivlai.note.utils;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings({"unchecked"})
public class UEditorUtil {

    /**
     * 获取参数
     *
     * @return {
     * <p>
     * }
     */
    public static Map<String, Object> getConfig() {
        String relativeFilePath = "/config/uEditor.config.json";
        return JsonUtil.relativeJsonFileToMap(relativeFilePath);
    }

    /**
     * 上传图片
     *
     * @param file 文件
     * @return {
     * "state": "SUCCESS",
     * "url": "upload/demo.jpg",
     * "title": "demo.jpg",
     * "original": "demo.jpg"
     * }
     */
    public static Object uploadImage(HttpServletRequest request, MultipartFile file) {
        HashMap<Object, Object> map = new HashMap<>();
        try {
            HashMap<Object, Object> img = FileUtil.saveWebFileAndReturnPath(request, "img", file, true);
            map.put("state", "SUCCESS");
            map.putAll(img);
        } catch (IOException e) {
            map.put("state", "文件保存异常");
        }
        return map;
    }

    /**
     * 上传涂鸦
     *
     * @param request request请求
     * @param upfile  base64代码
     * @return {
     * "state": "SUCCESS",
     * "url": "upload/demo.jpg",
     * "title": "demo.jpg",
     * "original": "demo.jpg"
     * }
     */
    public static Object uploadScrawl(HttpServletRequest request, String upfile) {
        HashMap<Object, Object> map = new HashMap<>();
        try {
            HashMap<Object, Object> img = FileUtil.saveWebFileAndReturnPathByBase64(request, "scrawl", upfile, "jpg");
            map.put("state", "SUCCESS");
            map.putAll(img);
        } catch (Exception e) {
            map.put("state", "文件保存异常");
        }
        return map;
    }

    /**
     * 上传视频
     *
     * @param file 文件
     * @return {
     * "state": "SUCCESS",
     * "url": "upload/demo.mp4",
     * "title": "demo.mp4",
     * "original": "demo.mp4"
     * }
     */
    public static Object uploadVideo(HttpServletRequest request, MultipartFile file) {
        HashMap<Object, Object> map = new HashMap<>();
        try {
            HashMap<Object, Object> img = FileUtil.saveWebFileAndReturnPath(request, "video", file, true);
            map.put("state", "SUCCESS");
            map.putAll(img);
        } catch (IOException e) {
            map.put("state", "文件保存异常");
        }
        return map;
    }


    /**
     * 上传附件
     *
     * @param file 文件
     * @return {
     * "state": "SUCCESS",
     * "url": "upload/demo.zip",
     * "title": "demo.zip",
     * "original": "demo.zip"
     * }
     */
    public static Object uploadFile(HttpServletRequest request, MultipartFile file) {
        HashMap<Object, Object> map = new HashMap<>();
        try {
            HashMap<Object, Object> img = FileUtil.saveWebFileAndReturnPath(request, "file", file, false);
            map.put("state", "SUCCESS");
            map.putAll(img);
        } catch (IOException e) {
            map.put("state", "文件保存异常");
        }
        return map;
    }

    /**
     * 列出图片列表
     *
     * @return {
     * "state": "SUCCESS",
     * "list": [{
     * "url": "upload/1.jpg"
     * }, {
     * "url": "upload/2.jpg"
     * }, ],
     * "start": 20,
     * "total": 100
     * }
     */
    public static Object listImage(HttpServletRequest request) {
        String realPath = request.getServletContext().getRealPath("upload");
        List<String> imageAllowFiles = (List<String>) getConfig().get("imageAllowFiles");

        ArrayList<String> list = new ArrayList<>();
        FileUtil.selectFileListAndWebFilePathByFolderPath(new File(realPath + "\\img"), null, list, "/upload/img", imageAllowFiles);
        FileUtil.selectFileListAndWebFilePathByFolderPath(new File(realPath + "\\scrawl"), null, list, "/upload/scrawl", imageAllowFiles);

        ArrayList<Map<String, String>> reList = new ArrayList<>();
        for (String s : list) {
            HashMap<String, String> map = new HashMap<>();
            map.put("url", s);
            reList.add(map);
        }

        HashMap<String, Object> reMap = new HashMap<>();
        reMap.put("state", "SUCCESS");
        reMap.put("list", reList);
        reMap.put("start", 0);
        reMap.put("total", reList.size());
        return reMap;
    }

    /**
     * 列出文件列表
     *
     * @return {
     * "state": "SUCCESS",
     * "list": [{
     * "url": "upload/1.jpg"
     * }, {
     * "url": "upload/2.jpg"
     * }, ],
     * "start": 20,
     * "total": 100
     * }
     */
    public static Object listFile(HttpServletRequest request) {
        String realPath = request.getServletContext().getRealPath("upload");

        ArrayList<String> list = new ArrayList<>();
        FileUtil.selectFileListAndWebFilePathByFolderPath(new File(realPath + "\\video"), null, list, "/upload/video", null);
        FileUtil.selectFileListAndWebFilePathByFolderPath(new File(realPath + "\\file"), null, list, "/upload/file", null);

        ArrayList<Map<String, String>> reList = new ArrayList<>();
        for (String s : list) {
            HashMap<String, String> map = new HashMap<>();
            map.put("url", s);
            reList.add(map);
        }

        HashMap<String, Object> reMap = new HashMap<>();
        reMap.put("state", "SUCCESS");
        reMap.put("list", reList);
        reMap.put("start", 0);
        reMap.put("total", reList.size());
        return reMap;
    }

    /**
     * 错误的请求
     *
     * @return {
     * "state":"错误信息"
     * }
     */
    public static Map<String, Object> wrongRequest() {
        HashMap<String, Object> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("state", "Wrong request");
        return objectObjectHashMap;
    }


}
