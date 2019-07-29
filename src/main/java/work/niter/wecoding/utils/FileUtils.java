package work.niter.wecoding.utils;

import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.Base64Utils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @Author: Cangwu
 * @Date: 2019/7/25 20:33
 * @Description:
 */
public class FileUtils {

    public static MultipartFile fileToMultipart(String filePath) {
        try {
            File file = new File(filePath);
            FileInputStream inputStream = new FileInputStream(file);
            MultipartFile multipartFile = new MockMultipartFile(file.getName(), "png", "images", inputStream);
            return multipartFile;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean base64ToFile(String filePath,  String base64Data)
            throws Exception {
        String dataPrix = "";
        String data = "";

        if (base64Data == null || "".equals(base64Data)) {
            return false;
        } else {
            String[] d = base64Data.split("base64,");
            if (d != null && d.length == 2) {
                dataPrix = d[0];
                data = d[1];
            } else {
                return false;
            }
        }

        byte[] bs = Base64Utils.decodeFromString(data);
        org.apache.commons.io.FileUtils.writeByteArrayToFile(new File(filePath), bs);
        return true;
    }
}
