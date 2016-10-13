package com.infinium.glmcoupons.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.infinium.glmcoupons.R;
import com.infinium.glmcoupons.activity.CouponDetailActivity;
import com.infinium.glmcoupons.client.NetworkUrls;
import com.infinium.glmcoupons.pojo.Coupon;
import com.infinium.glmcoupons.utils.Common;

import java.util.ArrayList;

/**
 * Created by Lalit Dhameliya on 09/28/2016.
 */
public class CouponListAdapter extends RecyclerView.Adapter<CouponListAdapter.ViewHolder> {
    private ArrayList<Coupon> rowItems;
    private Context mContext;
    private LayoutInflater inflater;

    public CouponListAdapter(Context mContext, ArrayList<Coupon> arrCouponList) {
        this.mContext = mContext;
        this.rowItems = arrCouponList;
        this.inflater = LayoutInflater.from(mContext);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_coupon, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Coupon coupon = rowItems.get(position);
        Glide.with(mContext)
                .load(NetworkUrls.ImageUrl + coupon.getThumbPhoto())
                .placeholder(R.drawable.placeholder)
                .into(holder.ivCoupon);

        holder.tvDiscount.setText(coupon.getShortHeading());
        holder.tvCoupenTitle.setText(coupon.getMerchantTitle());
        holder.tvCoupenSubTitle.setText(coupon.getArea());
        holder.tvCoupenPrice.setText("₹" + coupon.getPrice());
        holder.tvCoupenPrice.setPaintFlags(holder.tvCoupenPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        holder.tvCoupenOfferPrice.setText("Now @ ₹" + coupon.getOfferPrice());

        holder.rlMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, CouponDetailActivity.class);
                intent.putExtra("SRegisterId", coupon.getSRegisterId());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return rowItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final RelativeLayout rlMain;
        private final ImageView ivCoupon;
        private final TextView tvDiscount;
        private final TextView tvCoupenTitle;
        private final TextView tvCoupenSubTitle;
        private final TextView tvCoupenPrice;
        private final TextView tvCoupenOfferPrice;

        public ViewHolder(View itemView) {
            super(itemView);
            rlMain = (RelativeLayout) itemView.findViewById(R.id.item_coupon_rl_main);
            ivCoupon = (ImageView) itemView.findViewById(R.id.item_coupon_iv_coupon);
            tvDiscount = (TextView) itemView.findViewById(R.id.item_coupon_tv_discount);
            tvCoupenTitle = (TextView) itemView.findViewById(R.id.item_coupon_tv_title);
            tvCoupenSubTitle = (TextView) itemView.findViewById(R.id.item_coupon_tv_sub_title);
            tvCoupenPrice = (TextView) itemView.findViewById(R.id.item_coupon_tv_original_price);
            tvCoupenOfferPrice = (TextView) itemView.findViewById(R.id.item_coupon_tv_price);
            int width = Common.getDeviceWidth(mContext) / 2;
            int height = width;
            ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) ivCoupon.getLayoutParams();
            params.width = width;
            params.height = height;
            ivCoupon.setLayoutParams(params);

        }
    }
}
