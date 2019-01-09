package stream.mokulive.storagefrontend.utils;

import com.aliyun.oss.OSSClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class AliyunOssHelper {

    public static final String UPLOAD_TYPE_GOODS = "1";
    public static final String UPLOAD_TYPE_BUILDING = "2";

    @Value("${accessKeyId}")
    private String accessKeyId;

    @Value("${accessKeySecret}")
    private String accessKeySecret;

    @Value("${bucketName}")
    private String bucketName;

    @Value("${goodsDirectory}")
    private String goodsDirectory;

    @Value("${buildingDirectory}")
    private String buildingDirectory;

    public String uploadImg(MultipartFile file, String userId,String type) throws IOException {
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss").format(new Date());
        String endpoint = "http://oss-cn-shanghai.aliyuncs.com";
        String area = "oss-cn-shanghai.aliyuncs.com";
        String fileUrl = "https://" + bucketName + "." + area + "/";
        String directory = "";
        if(UPLOAD_TYPE_GOODS.equals(type)){
            directory = goodsDirectory;
        } else if(UPLOAD_TYPE_BUILDING.equals(type)){
            directory = buildingDirectory;
        }

        // 创建OSSClient实例。
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);

        // 上传文件流。
        InputStream inputStream = file.getInputStream();

        String objectName = directory + userId + "-" + timeStamp + "-" + file.getOriginalFilename();
        ossClient.putObject(bucketName, objectName, inputStream);

        // 关闭OSSClient。
        ossClient.shutdown();

        fileUrl += objectName;

        return fileUrl;
    }
}
