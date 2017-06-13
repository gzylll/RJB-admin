package valderfields.rjb_admin.presenter;

import android.content.Context;
import android.graphics.Point;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import valderfields.rjb_admin.R;

/**
 * Created by 11650 on 2017/5/24.
 */

public class GridAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;

    private final int mGridWidth;
    //是否显示删除图标
    private Boolean isDelete = false;

    private List<String> imgList = new ArrayList<>();

    /**
     * 构造函数，传入Context，计算每个View的尺寸。
     * @param context
     */
    public GridAdapter(Context context) {
        mContext = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int width = 0;

        Point size = new Point();
        wm.getDefaultDisplay().getSize(size);
        width = size.x;

        mGridWidth = width / 4;
    }

    /**
     * 更新数据
     * @param images 新的数据集
     */
    public void setData(List<String> images) {
        if(images != null && images.size()>0){
            imgList = images;
        }else{
            imgList.clear();
        }
        notifyDataSetChanged();
    }

    /**
     * 设置是否显示删除图标
     * @param is 是否删除
     */
    public void setIsShowDelete(Boolean is){
        isDelete = is;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return imgList.size();
    }

    @Override
    public String getItem(int position) {
        return imgList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null)
        {
            convertView = mInflater.inflate(R.layout.grid_img,parent,false);
            holder = new ViewHolder(convertView);
        }
        else
        {
            holder = (ViewHolder)convertView.getTag();
        }

        if(holder!=null){
            holder.bindData(getItem(position));
        }

        return convertView;
    }

    class ViewHolder{
        ImageView img;
        ImageView imgDelete;

        ViewHolder(View view){
            img = (ImageView) view.findViewById(R.id.imageView1);
            imgDelete = (ImageView)view.findViewById(R.id.delete_markView);
            view.setTag(this);
        }

        /**
         * 绑定图片
         * @param dataPath 图片路径
         */
        void bindData(String dataPath){
            imgDelete.setVisibility(isDelete?View.VISIBLE:View.GONE);
            File imageFile = new File(dataPath);
            if (imageFile.exists()) {
                // 显示图片开源组件Picasso
                Picasso.with(mContext)
                        .load(imageFile)
                        .placeholder(R.drawable.mis_default_error)
                        .resize(mGridWidth, mGridWidth)
                        .centerCrop()
                        .into(img);
            }else{
                img.setImageResource(R.drawable.mis_default_error);
            }
        }
    }
}
