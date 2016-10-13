package com.infinium.glmcoupons.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.infinium.glmcoupons.R;
import com.infinium.glmcoupons.adapter.CategoryListAdapter;
import com.infinium.glmcoupons.client.MyLoopJPost;
import com.infinium.glmcoupons.client.NetworkUrls;
import com.infinium.glmcoupons.pojo.Category;
import com.infinium.glmcoupons.utils.Common;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Multidots on 26-Sep-16.
 */
public class SelectCategoryActivity extends AppCompatActivity {

    private SelectCategoryActivity mContext;
    private RecyclerView rvCategoryList;
    private ArrayList<Category> arrCategoryList;
    private CategoryListAdapter adapter;
    private ProgressBar pbLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_category);

        init();
    }

    /**
     * This method is used for initialization of views.
     */
    private void init() {
        mContext = SelectCategoryActivity.this;

        arrCategoryList = new ArrayList<>();
        adapter = new CategoryListAdapter(mContext, arrCategoryList);

        rvCategoryList = (RecyclerView) findViewById(R.id.act_category_list_recyclerview);
        pbLoading = (ProgressBar) findViewById(R.id.pb_loading);
        rvCategoryList.setHasFixedSize(true);
        rvCategoryList.setLayoutManager(new GridLayoutManager(mContext, 3));
        rvCategoryList.setAdapter(adapter);

        if (Common.isNetworkAvailable(mContext)) {
            getCategoryListApiCall();
        } else {
            Common.showNETWORDDisabledAlert(mContext);
        }
    }

    private void getCategoryListApiCall() {
        RequestParams params = new RequestParams();
        MyLoopJPost myLoopJGet = new MyLoopJPost(mContext, "", onLoopJGetCategoryListApiCallComplete, NetworkUrls.categoryListUrl, params);
    }

    MyLoopJPost.OnLoopJPostCallComplete onLoopJGetCategoryListApiCallComplete = new MyLoopJPost.OnLoopJPostCallComplete() {
        @Override
        public void response(String result) {
            try {
                JSONObject jsonObject = new JSONObject(result);
                if (jsonObject.getString("result").trim().equals("1")) {
                    JSONArray jsonArrayCoupen = jsonObject.getJSONArray("datamodal");
                    for (int i = 0; i < jsonArrayCoupen.length(); i++) {
                        JSONObject jobj = jsonArrayCoupen.getJSONObject(i);
                        Category category = new Category(jobj.getString("ServiceTypeId"),
                                jobj.getString("ServiceTypeCode"),
                                jobj.getString("ServiceTypeName"),
                                jobj.getString("IconPath"),
                                jobj.getString("DisplayOrder")
                        );

                        arrCategoryList.add(category);
                    }
                    pbLoading.setVisibility(View.GONE);
                    if (arrCategoryList != null && arrCategoryList.size() > 0) {
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