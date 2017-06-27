package valderfields.rjb_admin.model;

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
    //更新用户信息
    public static String updateUserUrl = host+"/UpdateServlet";
    //更新我的信息
    public static String updateMyInfoUrl = host+"/AdminUpdateServlet";
    //登录
    public static String loginUrl = host+"/AdminLoginServlet";
    //获取管理员信息
    public static String adminInfoUrl = host+"/QueryAdminServlet";
    //增加管理员
    public static String addAdminUrl = host+"/AdminAddServlet";
    //导出标签
    public static String tagUrl = host+"/QueryImagesServlet";

    public   static String getUpdateUserInfoUrl = host+"/UpdateUser";

    public static void saveSession(String header)
    {
        String[] infos = header.split(";");
        User.setSession(infos[0]);
    }

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
    public static Request getUserRequest()
    {
        RequestBody body = new FormBody.Builder().build();
        return new Request.Builder()
                .addHeader("Cookie",User.getSession())
                .url(userUrl)
                .post(body)
                .build();
    }


    /**
     * 修改信息
     * @param name 用户名
     * @param aid ID
     * @param password 密码
     * @return
     */
    public static Request getUpdateMyInfoRequest(String name, String aid,String password)
    {
        RequestBody body = new FormBody.Builder()
                .add("name",name)
                .add("aid",aid)
                .add("password",password)
                .build();
        return new Request.Builder()
                .addHeader("Cookie",User.getSession())
                .url(updateMyInfoUrl)
                .post(body)
                .build();
    }

    /**
     * 登录
     * @param name 用户名
     * @param password 密码
     * @return Request
     */
    public static Request getLoginRequest(String name,String password)
    {
        RequestBody body = new FormBody.Builder()
                .add("name",name)
                .add("password",password)
                .build();
        return new Request.Builder()
                .url(loginUrl)
                .post(body)
                .build();
    }

    /**
     * 获取管理员列表
     * @return Request
     */
    public static Request getAdminInfoRequest()
    {
        RequestBody body = new FormBody.Builder().build();
        return new Request.Builder()
                .addHeader("Cookie",User.getSession())
                .url(adminInfoUrl)
                .post(body)
                .build();
    }


    /**
     * 增加管理员
     * @param name 用户名
     * @param password 密码
     * @return Request
     */
    public static Request getAddAdminRequest(String name,String password)
    {
        RequestBody body = new FormBody.Builder()
                .add("name",name)
                .add("isroot","false")
                .add("password",password)
                .build();
        return new Request.Builder()
                .addHeader("Cookie",User.getSession())
                .url(addAdminUrl)
                .post(body)
                .build();
    }

    public static Request getTagRequest(String search)
    {
        RequestBody body = new FormBody.Builder()
                .add("index",search)
                .build();
        return new Request.Builder()
                .addHeader("Cookie",User.getSession())
                .url(tagUrl)
                .post(body)
                .build();
    }

    public static Request getRequestWithSession(String url, RequestBody requestBody)
    {
        return new Request.Builder()
                .addHeader("Cookie",User.getSession().split(";")[0])
                .url(url).post(requestBody)
                .build();
    }
}
