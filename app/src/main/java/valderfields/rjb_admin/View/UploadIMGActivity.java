package valderfields.rjb_admin.View;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import me.nereo.multi_image_selector.MultiImageSelectorActivity;
import valderfields.rjb_admin.Presenter.GridAdapter;
import valderfields.rjb_admin.Presenter.UploadPresenter;
import valderfields.rjb_admin.R;

public class UploadIMGActivity extends AppCompatActivity implements
        AdapterView.OnItemLongClickListener{

    private int REQUEST_IMAGE = 1;

    private GridView imgArea;
    public GridAdapter adapter;
    private UploadPresenter presenter;

    private Button submit;
    private static ProgressDialog dialog;

    private int imgCount;

    public Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.arg1){
                case 0x01:
                    dialog.dismiss();
                    Toast.makeText(UploadIMGActivity.this,"连接错误",Toast.LENGTH_SHORT).show();
                    break;
                case 0x02:
                    dialog.dismiss();
                    Toast.makeText(UploadIMGActivity.this,msg.obj.toString(),Toast.LENGTH_SHORT).show();
                    break;
                case 0x03:
                    dialog.dismiss();
                    Toast.makeText(UploadIMGActivity.this,"上传失败",Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    public boolean isShowDelete = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_img);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("上传图片");
        }
        initView();
        presenter = new UploadPresenter(this);
    }

    private void initView(){
        imgArea = (GridView)findViewById(R.id.IMG_area);
        adapter = new GridAdapter(this);
        imgArea.setAdapter(adapter);
        imgArea.setOnItemLongClickListener(this);
        submit = (Button)findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.uploadIMGs();
                showDialog();
            }
        });
        SharedPreferences sp = getSharedPreferences("setting",MODE_PRIVATE);
        imgCount = sp.getInt("imgCount",9);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.upload_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.selectIMG:
                Intent intent = new Intent(this, MultiImageSelectorActivity.class);
                // 是否显示调用相机拍照
                intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, true);
                // 最大图片选择数量
                intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, imgCount);
                // 设置模式 (多选/MultiImageSelectorActivity.MODE_MULTI)
                intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, MultiImageSelectorActivity.MODE_MULTI);
                startActivityForResult(intent, REQUEST_IMAGE);
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 接受图片选择的数据
     * @param requestCode 请求码
     * @param resultCode 结果码
     * @param data 结果数据
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_IMAGE){
            if(resultCode == RESULT_OK){
                // 获取返回的图片列表,并更新
                presenter.UpdateImgListData(
                        data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT));
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 图片长按删除
     */
   @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
       if (isShowDelete) {
           isShowDelete = false;
           adapter.setIsShowDelete(false);
       } else {
           isShowDelete = true;
           adapter.setIsShowDelete(true);
           imgArea.setOnItemClickListener(new AdapterView.OnItemClickListener() {

               @Override
               public void onItemClick(AdapterView<?> parent, View view,
                                       int position, long id) {
                   if(isShowDelete)
                        presenter.DeleteItem(position);//删除选中项
               }
           });
       }
       return true;
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(isShowDelete&& event.KEYCODE_BACK == keyCode){
            isShowDelete = false;
            adapter.setIsShowDelete(false);
            return true;
        }
        else{
            return super.onKeyDown(keyCode, event);
        }
    }

    public void showDialog(){
        dialog = new ProgressDialog(this);
        dialog.setMessage("上传中");
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.show();
    }

}
