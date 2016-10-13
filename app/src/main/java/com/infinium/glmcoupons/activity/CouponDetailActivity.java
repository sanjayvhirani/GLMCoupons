package com.infinium.glmcoupons.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.infinium.glmcoupons.LoginActivity;
import com.infinium.glmcoupons.R;
import com.infinium.glmcoupons.client.MyLoopJPost;
import com.infinium.glmcoupons.client.NetworkUrls;
import com.infinium.glmcoupons.utils.Common;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Lalit Dhameliya on 10/01/2016.
 */

public class CouponDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private CouponDetailActivity mContext;
    private TextView tvTitle;
    private TextView tvAddress;
    private TextView tvCouponPrice;
    private TextView tvOfferPrice;
    private TextView tvTotal;
    private ImageView ivMainImage;
    private Button btnBuyNow;
    private String RegisterId;
    private TextView tvMinus;
    private TextView tvPlus;
    private EditText edtQuantity;
    private int quantity = 1;
    private int offerPrice;
    private TextView tvTerms;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon_detail);

        init();
    }

    /**
     * This method is used to initialize views.
     */
    private void init() {
        mContext = CouponDetailActivity.this;

        tvTitle = (TextView) findViewById(R.id.coupon_detail_tv_title);
        tvTerms = (TextView) findViewById(R.id.coupon_detail_tv_terms);
        tvAddress = (TextView) findViewById(R.id.coupon_detail_tv_address);
        tvCouponPrice = (TextView) findViewById(R.id.coupon_detail_tv_coupon_price);
        tvOfferPrice = (TextView) findViewById(R.id.coupon_detail_tv_offer_price);
        tvMinus = (TextView) findViewById(R.id.coupon_detail_tv_minus);
        tvPlus = (TextView) findViewById(R.id.coupon_detail_tv_plus);
        tvTotal = (TextView) findViewById(R.id.coupon_detail_tv_total);
        edtQuantity = (EditText) findViewById(R.id.coupon_detail_edt_quantity);
        ivMainImage = (ImageView) findViewById(R.id.coupon_detail_iv_main);

        tvPlus.setOnClickListener(this);
        tvMinus.setOnClickListener(this);
        edtQuantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                try {
                    quantity = Integer.parseInt(edtQuantity.getText().toString());
                    tvTotal.setText("Total      ₹" + quantity * offerPrice);
                } catch (Exception e) {
                    quantity = 0;
                    e.printStackTrace();
                }
            }
        });
        btnBuyNow = (Button) findViewById(R.id.coupon_detail_btn_buy_now2);
        btnBuyNow.setOnClickListener(this);

        RegisterId = getIntent().getStringExtra("SRegisterId");
        getSupportActionBar().setTitle(getIntent().getStringExtra("MerchantTitle"));
        getSupportActionBar().setSubtitle(getIntent().getStringExtra("area"));

        if (Common.isNetworkAvailable(mContext)) {
            getCoupenDetailApiCall();
        } else {
            Common.showNETWORDDisabledAlert(mContext);
        }
    }

    private void getCoupenDetailApiCall() {
        RequestParams params = new RequestParams();
        MyLoopJPost myLoopJGet = new MyLoopJPost(mContext, "", onLoopJGetCoupenListApiCallComplete, NetworkUrls.couponDetailUrl + RegisterId, params);
    }


    MyLoopJPost.OnLoopJPostCallComplete onLoopJGetCoupenListApiCallComplete = new MyLoopJPost.OnLoopJPostCallComplete() {
        @Override
        public void response(String result) {
            try {
                JSONObject jsonObject = new JSONObject(result);
                if (jsonObject.getString("result").trim().equals("1")) {
                    JSONObject datamodalJson = jsonObject.getJSONObject("datamodal");

                    JSONObject jobjServiceInfo = datamodalJson.getJSONArray("tblServiceInformation").getJSONObject(0);
                    jobjServiceInfo.getString("SRegisterId");
                    jobjServiceInfo.getString("ServiceNo");
                    jobjServiceInfo.getString("ImageHeading");
                    jobjServiceInfo.getString("MerchantName");
                    jobjServiceInfo.getString("Area");
                    jobjServiceInfo.getString("TermsCondition");

                    JSONObject jobjServiceOffers = datamodalJson.getJSONArray("tblServiceOffers").getJSONObject(0);
                    jobjServiceOffers.getString("SOfferId");
                    jobjServiceOffers.getString("OfferName");
                    jobjServiceOffers.getString("OfferDescription");
                    jobjServiceOffers.getString("Price");
                    jobjServiceOffers.getString("OfferPrice");
                    jobjServiceOffers.getString("Discount");

                    JSONObject jobjServicePhotos = datamodalJson.getJSONArray("tblServicePhotos").getJSONObject(0);
                    jobjServicePhotos.getString("PhotoPath");

                    offerPrice = Integer.parseInt(jobjServiceOffers.getString("OfferPrice"));

                    tvTerms.setText(jobjServiceInfo.getString("TermsCondition"));

                    tvTitle.setText(jobjServiceInfo.getString("ImageHeading"));
                    tvAddress.setText(jobjServiceInfo.getString("Area"));
                    tvOfferPrice.setText("₹" + offerPrice);
                    tvTotal.setText("Total      ₹" + offerPrice);
                    tvCouponPrice.setText("₹" + jobjServiceOffers.getString("Price"));
                    tvCouponPrice.setPaintFlags(tvCouponPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    Glide.with(mContext)
                            .load(NetworkUrls.ImageUrl + jobjServicePhotos.getString("PhotoPath"))
                            .placeholder(R.drawable.placeholder)
                            .into(ivMainImage);

                } else {

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    public void onClick(View view) {
        if (view == tvPlus) {
            quantity++;
            edtQuantity.setText(quantity + "");
        } else if (view == tvMinus) {
            if (quantity == 0) {
                return;
            }
            quantity--;
            edtQuantity.setText(quantity + "");
        } else if (view == btnBuyNow) {
            if (quantity == 0) {
                Common.showAlertDialog(this, "", "You can not buy 0 items!", true, null);
                return;
            }
            if (Common.getBooleanPrefrences(this, getString(R.string.pref_Logedin), getString(R.string.app_name))) {
                Common.showAlertDialog(this, "", "Thank you for buying coupon!!", true, null);
            } else {
                startActivity(new Intent(this, LoginActivity.class));

            }
        }
    }
}
