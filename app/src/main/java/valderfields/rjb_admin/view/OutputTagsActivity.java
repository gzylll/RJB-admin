package valderfields.rjb_admin.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import valderfields.rjb_admin.R;
import valderfields.rjb_admin.model.jxJSON;
import valderfields.rjb_admin.presenter.OutputTagsPresenter;

public class OutputTagsActivity extends AppCompatActivity implements Observer{

    private EditText tag;
    private Button search;
    private Button export;
    private OutputTagsPresenter presenter;
    ProgressDialog progressDialog;
    //list中显示的用户数据
    private List<HashMap<String,String>> dataList = new ArrayList<>();
    private SimpleAdapter adapter;
    private ListView tagList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_output_tags);
        initView();
        presenter = new OutputTagsPresenter(this);
        presenter.addObserver(this);
    }

    private void initView(){
        getSupportActionBar().setTitle("导出标签");
        tag = (EditText)findViewById(R.id.tagName);
        search = (Button)findViewById(R.id.search);
        export = (Button)findViewById(R.id.export);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tag.getText().toString().equals("")){
                    Search("all");
                }else{
                    Search(tag.getText().toString());
                }
            }
        });
        export.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dataList.size()==0){
                    Toast.makeText(OutputTagsActivity.this,"请先查询数据",Toast.LENGTH_SHORT).show();
                }else{
                    progressDialog.setMessage("写入文件");
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                    presenter.saveData();
                }
            }
        });
        progressDialog = new ProgressDialog(this);
        tagList = (ListView)findViewById(R.id.tagList);
        adapter = new SimpleAdapter(this,
                                    dataList,
                                    R.layout.item_tags,
                                    new String[]{"picture_name","finish_time"},
                                    new int[]{R.id.pictureName,R.id.finishedTime});
        tagList.setAdapter(adapter);
    }

    public void Search(String s){
        progressDialog.setMessage("查询数据中");
        progressDialog.setCancelable(false);
        progressDialog.show();
        presenter.searchData(s);
    }

    public void showResult(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void update(Observable o, Object arg) {
        if(o instanceof OutputTagsPresenter){
            if(arg.equals("success")){
                progressDialog.dismiss();
                Log.i("data",presenter.data);
                dataList.clear();
                dataList.addAll(jxJSON.jxTagsData(presenter.data));
                showResult();
            }else if(arg.equals("写入成功")){
                progressDialog.dismiss();
                showDialog();
            }else{
                progressDialog.dismiss();
                Toast.makeText(OutputTagsActivity.this,arg.toString(),Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("文件已被存储在外置内存卡下的Tag文件夹内");
        builder.setPositiveButton("确定", null);
        Dialog showDialog = builder.create();
        showDialog.show();
    }
}
