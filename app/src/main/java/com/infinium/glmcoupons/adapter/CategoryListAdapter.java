package com.infinium.glmcoupons.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.infinium.glmcoupons.R;
import com.infinium.glmcoupons.activity.CategoryCouponList;
import com.infinium.glmcoupons.client.NetworkUrls;
import com.infinium.glmcoupons.pojo.Category;

import java.util.ArrayList;

/**
 * Created by Sanjay Hirani on 09/28/2016.
 */
public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.ViewHolder> {
    private final String TAG = CategoryListAdapter.this.getClass().getName();
    private ArrayList<Category> rowItems;
    private Context mContext;
    private LayoutInflater inflater;

    public CategoryListAdapter(Context mContext, ArrayList<Category> arrCouponList) {
        this.mContext = mContext;
        this.rowItems = arrCouponList;
        this.inflater = LayoutInflater.from(mContext);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_category, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Category category = rowItems.get(position);
        Log.e(TAG, "onBindViewHolder: image url : " + NetworkUrls.ImageUrl + category.getIconPath());
        Glide.with(mContext)
                .load(NetworkUrls.ImageUrl + category.getIconPath())
                .placeholder(R.drawable.placeholder)
                .into(holder.ivCategory);

        holder.tvName.setText(category.getServiceTypeName());
        holder.llCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, CategoryCouponList.class);
                intent.putExtra("ServiceTypeId", category.getServiceTypeId());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return rowItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final LinearLayout llCategory;
        private final ImageView ivCategory;
        private final TextView tvName;

        public ViewHolder(View itemView) {
            super(itemView);
            llCategory = (LinearLayout) itemView.findViewById(R.id.item_category_ll_main);
            ivCategory = (ImageView) itemView.findViewById(R.id.item_coupon_iv_category);
            tvName = (TextView) itemView.findViewById(R.id.item_coupon_tv_name);
//            int width = Common.getDeviceWidth(mContext) / 2;
//            int height = width;
//            ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) ivCategory.getLayoutParams();
//            params.width = width;
//            params.height = height;
//            ivCategory.setLayoutParams(params);

        }
    }
}
