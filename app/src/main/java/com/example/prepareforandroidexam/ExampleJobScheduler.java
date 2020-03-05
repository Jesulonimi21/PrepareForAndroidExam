package com.example.prepareforandroidexam;

import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class ExampleJobScheduler extends JobService {
    public static final String TAG="EXAMOLEJOBSERVICETAG";
    boolean jobCancelled=false;
    @Override
    public boolean onStartJob(JobParameters jobParameters) {

        Log.d("EXAMOLEJOBSERVICETAG","in on job start");
        doInBackground(jobParameters);
        return true;
    }

    private void doInBackground(final JobParameters parameters){
        new Thread(new Runnable() {
            @Override
            public void run() {

                int count=parameters.getExtras().getInt("count");
             for(int i =0; i<=count; i++){
                Log.d(TAG,"Work at index"+i);
                if(jobCancelled==true){
                    return;
                }
                 try {
                     Thread.sleep(1000);
                 } catch (InterruptedException e) {
                     e.printStackTrace();
                 }
             }
             Log.d(TAG,"JOb Finished");
             jobFinished(parameters,false);
            }
        }).start();
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        jobCancelled=true;
        Log.d(TAG,"Job Cancelled");
        return true;
    }
}
