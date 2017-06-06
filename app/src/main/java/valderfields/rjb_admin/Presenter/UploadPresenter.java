package valderfields.rjb_admin.Presenter;

import android.os.Message;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import valderfields.rjb_admin.Model.NetUtil;
import valderfields.rjb_admin.View.UploadIMGActivity;

/**
 * 上传事件，向服务器上传图片
 * Created by 11650 on 2017/5/25.
 */

public class UploadPresenter {

    private UploadIMGActivity activity;
    //图片路径列表
    private List<String> imgPath = new ArrayList<>();

    public UploadPresenter(UploadIMGActivity activity)
    {
        this.activity = activity;
    }

    /**
     * 更新图片数组，更新显示适配
     * @param data 新的数据集
     */
    public void UpdateImgListData(List<String> data){
        for(int i=0;i<data.size();i++){
            boolean isExist = false;
            for(int j=0;j<imgPath.size();j++){
                if(data.get(i).equals(imgPath.get(j))){
                    isExist = true;
                    break;
                }
            }
            if(!isExist){
                imgPath.add(data.get(i));
            }
        }
        activity.isShowDelete = false;
        activity.adapter.setIsShowDelete(false);
        activity.adapter.setData(imgPath);
    }

    /**
     * 删除图片
     * @param i 图片id
     */
    public void DeleteItem(int i){
        imgPath.remove(i);
        activity.adapter.setData(imgPath);
    }

    /**
     * 上传图片
     */
    public void uploadIMGs(){
        Log.e("IMG","uploadIMG");
        new Thread(new Runnable() {
            @Override
            public void run() {
                NetUtil.PersonalOkHttpCilent
                        .newCall(NetUtil.getIMGRequest(imgPath)).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        Log.e("IMG","onFailure");
                        Message message = activity.handler.obtainMessage();
                        message.arg1 = 0x01;
                        activity.handler.sendMessage(message);
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        if(response.code()==200){
                            Message message = activity.handler.obtainMessage();
                            message.arg1 = 0x02;
                            message.obj = response.body().string();
                            activity.handler.sendMessage(message);
                        }
                       else{
                            Message message = activity.handler.obtainMessage();
                            message.arg1 = 0x03;
                            activity.handler.sendMessage(message);
                        }
                    }
                });
            }
        }).start();
    }

}
