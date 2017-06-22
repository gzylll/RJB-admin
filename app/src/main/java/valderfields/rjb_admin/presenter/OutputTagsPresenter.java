package valderfields.rjb_admin.presenter;

import android.os.Environment;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Observable;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import valderfields.rjb_admin.model.NetUtil;
import valderfields.rjb_admin.view.OutputTagsActivity;

/**
 * Created by 11650 on 2017/6/21.
 */

public class OutputTagsPresenter extends Observable{

    private OutputTagsActivity activity;
    public String data;

    public OutputTagsPresenter(OutputTagsActivity activity){
        this.activity = activity;
    }

    public void searchData(String keywords){
        NetUtil.PersonalOkHttpCilent.newCall(
                NetUtil.getTagRequest(keywords)
        ).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                NotifyObserver("onFailure");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.code()==200){
                    data = response.body().string();
                   NotifyObserver("success");
                }else{
                    NotifyObserver("网络错误");
                }

            }
        });
    }
    public void saveData(){
        File root = Environment.getExternalStorageDirectory();
        File tag = new File(root.getPath()+"/Tag");
        if(!tag.exists()&&!tag.isDirectory()) {
            if(tag.mkdir()){
                File tags = new File(tag.getPath()+"/tags-"+new Date().getTime()+".txt");
                try {
                    if(tags.createNewFile())
                    {
                        BufferedWriter output = new BufferedWriter(new FileWriter(tags));
                        output.write(data);
                        output.close();
                        NotifyObserver("写入成功");
                    }
                    else
                    {
                        NotifyObserver("创建文件失败");
                    }
                } catch (IOException e) {
                    NotifyObserver("创建文件失败");
                }
            }else{
                NotifyObserver("创建文件夹失败");
            }
        }else{
            File tags = new File(tag.getPath()+"/tags-"+new Date().getTime()+".txt");
            try {
                if(tags.createNewFile())
                {
                    BufferedWriter output = new BufferedWriter(new FileWriter(tags));
                    output.write(data);
                    output.close();
                    NotifyObserver("写入成功");
                }
                else
                {
                    NotifyObserver("创建文件失败");
                }
            } catch (IOException e) {
                NotifyObserver("创建文件失败");
            }
        }
    }

    public void NotifyObserver(String s){
        setChanged();
        notifyObservers(s);
    }
}
