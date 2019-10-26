package com.pomole.ossdemo.common;
 
import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.*;
import com.pomole.ossdemo.config.OssConfig;
import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
 
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
 
/**
 * 
 * Description:aliyunOSSUtil
 */
@SuppressWarnings("unused")
public class AliyunOSSUtil {
 
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(AliyunOSSUtil.class);
 
	public static String upload(File file){
        logger.info("=========>OSS文件上传开始："+file.getName());
        String endpoint= OssConfig.POINT;
        String accessKeyId=OssConfig.KEY_ID;
        String accessKeySecret=OssConfig.KEY_SECRET;
        String bucketName=OssConfig.BUCKET_NAME1;
        String fileHost=OssConfig.HOST;
 
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = format.format(new Date());
 
        if(null == file){
            return null;
        }
 
        OSSClient ossClient = new OSSClient(endpoint,accessKeyId,accessKeySecret);
        try {
            //容器不存在，就创建
            /*if(!ossClient.doesBucketExist(bucketName)){
                ossClient.createBucket(bucketName);
                CreateBucketRequest createBucketRequest = new CreateBucketRequest(bucketName);
                createBucketRequest.setCannedACL(CannedAccessControlList.PublicRead);
                ossClient.createBucket(createBucketRequest);
            }*/
            //创建文件路径
            String fileUrl = fileHost+"/"+(dateStr + "/" + UUID.randomUUID().toString().replace("-","")+"-"+file.getName());
            //上传文件
            PutObjectResult result = ossClient.putObject(new PutObjectRequest(bucketName, fileUrl, file));
            //设置权限 这里是公开读
            ossClient.setBucketAcl(bucketName,CannedAccessControlList.PublicRead);
            if(null != result){
                logger.info("==========>OSS文件上传成功,OSS地址："+fileUrl);
                System.err.println(fileUrl);
                return fileUrl;
            }
        }catch (OSSException oe){
            logger.error(oe.getMessage());
        }catch (ClientException ce){
            logger.error(ce.getMessage());
        }finally {
            //关闭
            ossClient.shutdown();
        }
        return null;
    }

    public static List<String> fileFolder(String fileName) {
        String endpoint= OssConfig.POINT;
        String accessKeyId=OssConfig.KEY_ID;
        String accessKeySecret=OssConfig.KEY_SECRET;
        String bucketName=OssConfig.BUCKET_NAME1;
        String fileHost=OssConfig.HOST;
        // 创建OSSClient实例。
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        // 构造ListObjectsRequest请求。
        ListObjectsRequest listObjectsRequest = new ListObjectsRequest(bucketName);
        // 设置正斜线（/）为文件夹的分隔符。
        listObjectsRequest.setDelimiter("/");
        // 设置prefix参数来获取fun目录下的所有文件。
        if (StringUtils.isNotBlank(fileName)) {
            listObjectsRequest.setPrefix(fileName + "/");
        }
        // 列出文件
        ObjectListing listing = ossClient.listObjects(listObjectsRequest);
        // 遍历所有commonPrefix
        List<String> list = new ArrayList<>();
        for (String commonPrefix : listing.getCommonPrefixes()) {
            String newCommonPrefix = commonPrefix.substring(0, commonPrefix.length() - 1);
            String[] s = newCommonPrefix.split("/");
            list.add(s[1]);
        }
        // 关闭OSSClient
        ossClient.shutdown();
        return list;
    }
}