package com.aosproject.project_team6.NetworkTask;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ImageUpload_NetworkTask extends AsyncTask<Integer, String, Integer > {

    Context context = null;
    String mAddr = null;
    String devicePath;
    ImageView imageView;


    public ImageUpload_NetworkTask(Context context, String mAddr, String devicePath, ImageView imageView) {
        this.context = context;
        this.mAddr = mAddr;
        this.devicePath = devicePath;
        this.imageView = imageView;
    }

    @Override
    protected Integer doInBackground(Integer... integers) {
        File file = new File(devicePath);
        OkHttpClient okHttpClient = new OkHttpClient();



        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("image", file.getName(), RequestBody.create(file, MediaType.parse("image/jpeg")))
                .build();

        Request request = new Request.Builder()
                .url(mAddr)
                .post(requestBody)
                .build();

        try{
            Response response = okHttpClient.newCall(request).execute();
            Log.v("Message", "Success in ImgNetworkTask");
            return  1;
        }catch (Exception e){
            e.printStackTrace();
            Log.v("Message", "Fail in ImgNetworkTask");
            return  0;
        }


    } // doInBackground



}
