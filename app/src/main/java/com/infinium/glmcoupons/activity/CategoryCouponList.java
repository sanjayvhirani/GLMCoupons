package com.infinium.glmcoupons.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.infinium.glmcoupons.R;
import com.infinium.glmcoupons.adapter.CouponListAdapter;
import com.infinium.glmcoupons.client.MyLoopJPost;
import com.infinium.glmcoupons.client.NetworkUrls;
import com.infinium.glmcoupons.pojo.Coupon;
import com.infinium.glmcoupons.utils.Common;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Lalit Dhameliya on 09/28/2016.
 */

public class CategoryCouponList extends AppCompatActivity {

    private CategoryCouponList mContext;
    private RecyclerView rvCoupenList;
    private ArrayList<Coupon> arrCoupenList;
    private CouponListAdapter adapter;
    private String ServiceTypeId = "1";
    private ProgressBar pbLoading;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_coupon_list);

        init();
    }

    /**
     * This method is used for initialization of views.
     */
    private void init() {
        mContext = CategoryCouponList.this;

        arrCoupenList = new ArrayList<>();
        adapter = new CouponListAdapter(mContext, arrCoupenList);
        pbLoading = (ProgressBar) findViewById(R.id.pb_loading);
        rvCoupenList = (RecyclerView) findViewById(R.id.act_category_coupen_list_recyclerview);
        rvCoupenList.setHasFixedSize(true);
        rvCoupenList.setLayoutManager(new GridLayoutManager(mContext, 2));
        rvCoupenList.setAdapter(adapter);

        ServiceTypeId = getIntent().getStringExtra("ServiceTypeId");
        getSupportActionBar().setTitle(getIntent().getStringExtra("ServiceTypeName"));

        if (Common.isNetworkAvailable(mContext)) {
            getCoupenListApiCall();
        } else {
            Common.showNETWORDDisabledAlert(mContext);
        }
    }

    private void getCoupenListApiCall() {
        RequestParams params = new RequestParams();
        MyLoopJPost myLoopJGet = new MyLoopJPost(mContext, "", onLoopJGetCoupenListApiCallComplete, NetworkUrls.couponListUrl + ServiceTypeId, params);
    }

    MyLoopJPost.OnLoopJPostCallComplete onLoopJGetCoupenListApiCallComplete = new MyLoopJPost.OnLoopJPostCallComplete() {
        @Override
        public void response(String result) {
            try {
                JSONObject jsonObject = new JSONObject(result);
                if (jsonObject.getString("result").trim().equals("1")) {
                    JSONArray jsonArrayCoupen = jsonObject.getJSONArray("datamodal");
                    for (int i = 0; i < jsonArrayCoupen.length(); i++) {
                        JSONObject jobj = jsonArrayCoupen.getJSONObject(i);
                        Coupon coupon = new Coupon(jobj.getString("SRegisterId"),
                                jobj.getString("ServiceName"),
                                jobj.getString("ShortHeading"),
                                jobj.getString("ImageHeading"),
                                jobj.getString("BalCoupons"),
                                jobj.getString("PhotoPath"),
                                jobj.getString("MerchantId"),
                                jobj.getString("MerchantName"),
                                jobj.getString("City"),
                                jobj.getString("Area"),
                                jobj.getString("ThumbPhoto"),
                                jobj.getString("MerchantTitle"),
                                jobj.getString("SOfferId"),
                                jobj.getString("OfferPrice"),
                                jobj.getString("Discount"),
                                jobj.getString("Price"));

                        arrCoupenList.add(coupon);
                    }
                    pbLoading.setVisibility(View.GONE);
                    if (arrCoupenList != null && arrCoupenList.size() > 0) {
                        adapter.notifyDataSetChanged();
                    }
                } else {

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };
}
