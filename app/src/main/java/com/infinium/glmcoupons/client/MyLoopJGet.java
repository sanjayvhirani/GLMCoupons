package com.infinium.glmcoupons.client;

import android.app.ProgressDialog;
import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONObject;


/**
 * Created by Sanjay on 07-11-2015.
 */
public class MyLoopJGet {


    private final OnLoopJGetCallComplete onLoopJGetCallComplete;
    AsyncHttpClient client = new AsyncHttpClient();
    private ProgressDialog dialog;
    private String message;
    private Context context;
    int DEFAULT_TIMEOUT = 60 * 1000;

    /**
     * @param ctx
     * @param progressMessage
     * @param onLoopJGetCallComplete
     * @param url
     */
    public MyLoopJGet(Context ctx, String progressMessage, final OnLoopJGetCallComplete onLoopJGetCallComplete, String url) {

        client.setTimeout(DEFAULT_TIMEOUT);
        message = progressMessage;
        this.context = ctx;
        this.onLoopJGetCallComplete = onLoopJGetCallComplete;
        if (!(message.equals(""))) {
            dialog = new ProgressDialog(ctx);
            dialog.setMessage(progressMessage);
        }
        System.out.println("Request url: " + url);

        client.get(url.replace(" ", "%20"), new JsonHttpResponseHandler() {

            @Override
            public void onStart() {
                // called before request is started
                if (!(message.equals(""))) {
                    dialog.setCancelable(false);
                    dialog.show();
                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // called when response HTTP status is "200 OK"
                if (!(message.equals(""))) {
                    if (dialog != null) {
                        dialog.hide();
                        dialog.dismiss();
                    }
                }
                System.out.println("Response : " + response.toString());
                /*try {
                    if (response.optString("response_code").equals("1101")) {
                        Common.showAlertDialog(context, "", Utils.getSiteMessage(context, "BLOCKEDBYADMIN"), true);
                        Utils.setPrefrencesBoolean(context, context.getResources().getString(R.string.pref_is_login_selfie), false);
                        Utils.setPrefrencesBoolean(context, context.getResources().getString(R.string.pref_is_login_google), false);
                        Utils.setPrefrencesBoolean(context, context.getResources().getString(R.string.pref_is_login_facebook), false);
                        Utils.setPrefrencesBoolean(context, context.getResources().getString(R.string.pref_is_login_twitter), false);
                        Utils.setPrefrencesBoolean(context, "username", false);

                        ProgressDialog pDialog = new ProgressDialog(context);
                        pDialog.setCancelable(false);
                        pDialog.setMessage(context.getString(R.string.msg_please_wait));
                        bluefusion.selfie.socialrating.social.ResponseListener responseListener = new bluefusion.selfie.socialrating.social.ResponseListener(pDialog);
//                SocialAuthAdapter adapter = new SocialAuthAdapter(responseListener);
                        SocialAuthAdapter adapter = SocialAuthAdapter.getInstance(responseListener);
                        adapter.signOut(context, "facebook");
                        adapter.signOut(context, "twitter");
                        adapter.signOut(context, "linkedin");

                        Intent intent = new Intent(context, StartActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        intent.putExtra("logout", true);
                        context.startActivity(intent);
                        ((Activity) context).finish();
                    } else {
                        onLoopJGetCallComplete.response(response.toString());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }*/
                onLoopJGetCallComplete.response(response.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                onLoopJGetCallComplete.response(responseString);
            }

//            @Override
//            public void onFailure(int statusCode, Header[] headers, JSONObject response, Throwable e) {
//                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
//                if (!(message.equals(""))) {
//                    if (dialog != null) {
//                        dialog.hide();
//                        dialog.dismiss();
//                    }
//                }
//                onLoopJGetCallComplete.response(response.toString());
//            }

            @Override
            public void onRetry(int retryNo) {
                // called when request is retried
            }
        });

    }


    public interface OnLoopJGetCallComplete {
        void response(String result);
    }
}
