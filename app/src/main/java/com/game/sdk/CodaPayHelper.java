package com.game.sdk;

public class CodaPayHelper {
    public interface  onFinishListener{
        public void onFinishListenerDelegate();
    }

    private   onFinishListener mOnFinishListener;

    public void setOnFinishListener(onFinishListener finishListener){
        mOnFinishListener=finishListener;
    }

    public void fireFinishListener()
    {
        if (mOnFinishListener!=null){
            mOnFinishListener.onFinishListenerDelegate();
        }
    }
}
