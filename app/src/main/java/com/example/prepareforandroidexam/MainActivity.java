package com.example.prepareforandroidexam;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void scheduleJob(View v){

        //create persistable bundle
        PersistableBundle extras=new PersistableBundle();
        extras.putInt("count",10);
        Log.d("EXAMOLEJOBSERVICETAG","schedule job clicked");
        //create component name
        ComponentName componentName=new ComponentName(this,ExampleJobScheduler.class);

        //create JObinfo
        JobInfo jobInfo=new JobInfo.Builder(123,componentName)
                        .setRequiresCharging(true)

                        .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                        .setPeriodic(15*60*1000)
                        .setExtras(extras)
                         .build();
        Log.d("EXAMOLEJOBSERVICETAG","schedule job clicked");

        //Create Job scheduler and schedulr job
        JobScheduler scheduler=(JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        int result=scheduler.schedule(jobInfo);
        if(result==JobScheduler.RESULT_SUCCESS){
            Toast.makeText(this, "Result scheduled successfully", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Result scheduling failed", Toast.LENGTH_SHORT).show();
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void cancelJob(View v){
        JobScheduler scheduler=(JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        scheduler.cancel(123);
    }
}
