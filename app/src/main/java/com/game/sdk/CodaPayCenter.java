package com.game.sdk;

import android.app.Activity;
import android.content.Intent;
import com.unity3d.player.UnityPlayer;

import org.json.JSONException;
import org.json.JSONObject;

public class CodaPayCenter {
    public static CodaPayHelper helper=new CodaPayHelper();
    public static void OpenPayWebView(Activity activity, String url) {
        Intent intent=new Intent(activity, CodaPayActivity.class);
        intent.putExtra("URL",url);
        activity.startActivity(intent);


        helper.setOnFinishListener(new CodaPayHelper.onFinishListener() {
            @Override
            public void onFinishListenerDelegate() {
                try{
                    JSONObject rootObject=new JSONObject();
                    rootObject.put("Type","Pay");
                    rootObject.put("AuthChannel","4");
                    rootObject.put("Result","Finish");

                    //通知Unity
                    UnityPlayer.UnitySendMessage("SDK_OBJ", "OnGameSDKCallback", rootObject.toString());
                }
                catch (JSONException e){

                }

            }
        });
    }
}
