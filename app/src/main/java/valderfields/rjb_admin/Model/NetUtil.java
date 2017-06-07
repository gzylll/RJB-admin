package valderfields.rjb_admin.Model;

import java.io.File;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * net
 * Created by 11650 on 2017/5/26.
 */

public class NetUtil {

    //host URL
    private static String host = "http://114.115.142.214:8080/ImageSortServer";
    //提交图片的URL
    private static String submitIMGUrl = host+"/AddImageServlet";
    //用户信息URL
    private static String userUrl = host+"/QueryUserServlet";
    //Client
    public static OkHttpClient PersonalOkHttpCilent = new OkHttpClient.Builder().build();

    /**
     * 获取上传图片的请求
     * @param imgPath 图片路径
     * @return 请求
     */
    public static Request getIMGRequest(List<String> imgPath)
    {
        MediaType MEDIA_TYPE = null;
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        for(int i=0;i<imgPath.size();i++){
            File file = new File(imgPath.get(i));
            if(file.getName().endsWith("jpg")||file.getName().endsWith("jpeg"))
            {
                MEDIA_TYPE = MediaType.parse("image/jpeg");
            }
            else if(file.getName().endsWith("png"))
            {
                MEDIA_TYPE = MediaType.parse("image/png");
            }
            else if(file.getName().endsWith("gif"))
            {
                MEDIA_TYPE = MediaType.parse("image/gif");
            }
            builder.addFormDataPart("img",file.getName(), RequestBody.create(MEDIA_TYPE,file));
        }

        MultipartBody requestBody = builder.build();
        return new Request.Builder()
                .url(submitIMGUrl)//地址
                .post(requestBody)//添加请求体
                .build();
    }

    /**
     * 获取用户列表reuqest
     */
    public static Request getUserRequest(String name,String password)
    {
        RequestBody body = new FormBody.Builder()
                .add("name",name)
                .add("password",password)
                .build();
        return new Request.Builder()
                .url(userUrl)
                .post(body)
                .build();
    }


}
