package com.wei.controller;

import com.alibaba.fastjson.JSONObject;
import com.wei.biz.UserService;
import com.wei.common.GeneralCommon;
import com.wei.exception.QueryParamException;
import com.wei.pojo.UserInfo;
import com.wei.vo.FileDownloadVo;
import com.wei.biz.UserService;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @ClassName BaseController
 * 描述 :
 * @Author weijunjie
 * @Date 2020/7/27 14:53
 */
public class BaseController {

    @Autowired
    private UserService userService;

    /**
     * @Description 获取用户名操作
     * @Author: weijunjie
     * @Date: 2020/8/13 15:55
     * @return:
     **/
    protected String getLoginUserName(HttpServletRequest request){
        //获取token
        UserInfo loginUserInfo = getLoginUserInfo(request);
        if(null == loginUserInfo){
            return null;
        }else{
            return loginUserInfo.getUserName();
        }
    }

    /**
     * @author: chenzhm
     * @date: 2020/9/2 8:33
     * @Description:
     */
    protected String getLoginAccountNo(HttpServletRequest request){
        UserInfo loginUserInfo = getLoginUserInfo(request);
        if(null == loginUserInfo){
            return null;
        }else{
            return loginUserInfo.getAccountNo();
        }
    }

    /**
     * @Description 获取用户名操作
     * @Author: weijunjie
     * @Date: 2020/8/13 15:55
     * @return:
     **/
    protected UserInfo getLoginUserInfo(HttpServletRequest request){
        //获取token
        return (UserInfo)request.getSession().getAttribute("userInfo");

    }

    /**
     *  获取用户code操作
     * @Title: getLoginUserCode
     * @author: yindy 2020/8/17 16:43
     * @param: [request]
     * @return: java.lang.String
     * @throws
     */
    protected String getLoginUserCode(HttpServletRequest request){
        //获取token
        String authorization = request.getHeader("Authorization");
        //校验获取token中数据对象
        if(authorization == null){
            return null;
        }else{
            String sub = MapUtils.getString(userService.getToken(authorization),"sub");
            if(StringUtils.isNotBlank(sub)){
                String userCode = JSONObject.parseObject(sub).getString("userCode");
                return userCode;
            }else{
                return null;
            }
        }
    }

    /**
     * @Description 解析获取查询数据对象
     * @Author: weijunjie
     * @Date: 2020/8/14 15:15
     * @return:
     **/
    protected Map<String,String> getQueryMap(String queryString){
        HashMap<String, String> map = new HashMap<>();
        try {
            if(StringUtils.isNotBlank(queryString)){
                if(queryString.contains("&")){
                    String[] split = queryString.split("&");
                    for(String s:split){
                        if(queryString.contains("=")){
                            String[] sp = s.split("=");
                            map.put(sp[0],sp[1]);
                        }else{
                            throw new QueryParamException("系统参数请求异常");
                        }
                    }
                }else{
                    if(queryString.contains("=")){
                        String[] split = queryString.split("=");
                        map.put(split[0],split[1]);
                    }else{
                        throw new QueryParamException("系统参数请求异常");
                    }
                }
            }
        }catch (Exception e){
            throw new QueryParamException("系统参数请求异常");
        }
        return map;
    }

    /**
     * @Description 解析获取查询数据对象
     * @Author: weijunjie
     * @Date: 2020/8/14 15:15
     * @return:
     **/
    protected boolean isQueryMap(String queryString){
        boolean flag ;
        if(StringUtils.isNotBlank(queryString)){
            if(queryString.contains("&") || queryString.contains("=")){
                flag = true;
            }else{
                flag = false;
            }
        }else{
            throw new QueryParamException("系统参数请求异常");
        }
        return flag;
    }

    /**
     * @Description 根据指定名称、指定路径下载文件
     * @Author: weijunjie
     * @Date: 2020/9/23 15:11
     **/
    protected void downloadFile(FileDownloadVo fileDownloadVo,
                                HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String fileName = fileDownloadVo.getFileName();
        String filePath = fileDownloadVo.getFilePath();
        //声明本次下载状态的记录对象
        //设置响应头和客户端保存文件名
        response.setCharacterEncoding("utf-8");
        response.setContentType("multipart/form-data");
        fileName = URLEncoder.encode(fileName,"utf-8");
        response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);

        //用于记录以完成的下载的数据量，单位是byte
        long downloadedLength = 0L;
        try(InputStream inputStream = new FileInputStream(filePath);OutputStream os = response.getOutputStream()) {
            //循环写入输出流
            byte[] b = new byte[GeneralCommon.INTEGER_2048];
            int length;
            while ((length = inputStream.read(b)) > 0) {
                os.write(b, 0, length);
                downloadedLength += b.length;
            }
        } catch (Exception e){
            throw e;
        }
    }



    /**
     * Excel文件输出到浏览器提供下载
     *
     * @param workBook
     * @param response
     * @param fileName
     * @throws Exception
     */
    protected void writeExcelToWeb(Workbook workBook, HttpServletResponse response, String fileName) throws Exception {
        OutputStream out = null;
        try {
            makeOutHeader(response,fileName);
            out = response.getOutputStream();
            workBook.write(out);

        } catch (Exception e) {

        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    throw new Exception();
                }
            }
        }
    }

    /**
     * word文件输出到浏览器提供下载
     */
    protected void writeWordToWeb(XWPFDocument element, HttpServletResponse response, String fileName) throws Exception {
        OutputStream out = null;
        try {
            makeOutHeader(response,fileName);
            out = response.getOutputStream();
            element.write(out);

        } catch (Exception e) {

        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    throw new Exception();
                }
            }
        }
    }

    private void makeOutHeader(HttpServletResponse response, String fileName){
        response.reset();// 清空输出流
        String filename8859 = new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
        String filenameutf8 = null;
        try {
            filenameutf8 = URLEncoder.encode(fileName, StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        response.setHeader("Content-Disposition", "attachment;filename=" + filename8859 + ";" +
                "filename*=utf-8''" + filenameutf8);//设定输出文件头
        response.setHeader("Set-Cookie", "fileDownload=true; path=/");

    }

    /**
     * @Description ftp服务器下载对应文件数据  -- 边压缩边下载
     * @Author weijunjie
     * @Date 2020/5/14 10:22
     **/
    @SuppressWarnings("all")
    protected void downloadFiles(String downloadName,HttpServletRequest request,
                                 HttpServletResponse response, List<FileDownloadVo> fileDowloadVos){

        //响应头的设置
        response.reset();
        response.setCharacterEncoding("utf-8");
        response.setContentType("multipart/form-data");

        //设置压缩包的名字
        downloadName = downloadName.contains(".zip")?downloadName:downloadName+".zip";
        String agent = request.getHeader("USER-AGENT");
        try {
            if (agent.contains("MSIE")||agent.contains("Trident")) {
                downloadName = URLEncoder.encode(downloadName, "UTF-8");
            } else {
                downloadName = new String(downloadName.getBytes("UTF-8"),"ISO-8859-1");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.setHeader("Content-Disposition", "attachment;fileName=\"" + downloadName + "\"");

        //设置压缩流：直接写入response，实现边压缩边下载
        ZipOutputStream zipos = null;
        try {
            zipos = new ZipOutputStream(new BufferedOutputStream(response.getOutputStream()));
            zipos.setMethod(ZipOutputStream.DEFLATED); //设置压缩方法
        } catch (Exception e) {
            e.printStackTrace();
        }

        //循环将文件写入压缩流
        DataOutputStream os = null;
        //遍历对象，获取文件下载全路径
        for(FileDownloadVo vo:fileDowloadVos){
            String fileName = vo.getFileName();
            String fileUrl = vo.getFilePath();
            try {
                //添加ZipEntry，并ZipEntry中写入文件流
                //这里，加上i是防止要下载的文件有重名的导致下载失败
                zipos.putNextEntry(new ZipEntry(fileName));
                os = new DataOutputStream(zipos);
                if(StringUtils.isBlank(fileUrl)){
                    continue;
                }
                InputStream is = null;
                is = new FileInputStream(new File(fileUrl));
                if(is == null){
                    continue;
                }else{
                    byte[] b = new byte[GeneralCommon.INTEGER_1024];
                    int length = 0;
                    while((length = is.read(b))!= -1){
                        os.write(b, 0, length);
                    }
                    is.close();
                    zipos.closeEntry();
                }
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
        }
        //关闭流
        try {
            os.flush();
            os.close();
            if(zipos != null) {
                zipos.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
