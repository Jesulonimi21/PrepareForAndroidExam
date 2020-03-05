package com.example.prepareforandroidexam.WorkManager;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.prepareforandroidexam.R;

import static com.example.prepareforandroidexam.WorkManager.WorkManagerActivity.KEY_TASK_DESC;

public class MyWorker extends Worker {
    public static final String  KEY_TASK_OUTPUT_DATA="output_data";

    public MyWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        //get input data
        Data data=getInputData();
        String desc=data.getString(KEY_TASK_DESC);
        showNotification("title",desc);
        Log.d("WORKMANAGER","called");

        //screate output data
        Data outputData=new Data.Builder().putString(KEY_TASK_OUTPUT_DATA,"Task FInished successfully").build();

        //return output data
        return Result.success(outputData);
    }


    public void showNotification(String title,String description){
        NotificationManager manager=(NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel channel=new NotificationChannel("lonimi","lonimi",NotificationManager.IMPORTANCE_LOW);
            manager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder notifBuilder=new NotificationCompat.Builder(getApplicationContext(),"lonimi")
                                                    .setContentTitle(title)
                                                    .setContentText(description)
                                                    .setSmallIcon(R.mipmap.ic_launcher);

        manager.notify(1,notifBuilder.build());
    }
}
