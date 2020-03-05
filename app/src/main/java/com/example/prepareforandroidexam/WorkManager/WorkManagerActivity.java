package com.example.prepareforandroidexam.WorkManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.prepareforandroidexam.R;

import static com.example.prepareforandroidexam.WorkManager.MyWorker.KEY_TASK_OUTPUT_DATA;

public class WorkManagerActivity extends AppCompatActivity {
    public static final String KEY_TASK_DESC="description";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_manager);

        //Create Data Object
        Data data=new Data.Builder().putString(KEY_TASK_DESC,"I am description").build();

        //create constrants
        Constraints constraints=new Constraints.Builder().setRequiresCharging(true).build();

        //Create Work Rewuest and add data and constraints
        final WorkRequest request=new OneTimeWorkRequest.Builder(MyWorker.class)
                .setConstraints(constraints)
                .setInputData(data)
                .build();


        findViewById(R.id.workReqBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WorkManager.getInstance().enqueue(request);
            }
        });


        final TextView requestText=(TextView) findViewById(R.id.requestText);


        //monitor status of Work request using livedata
        WorkManager.getInstance().getWorkInfoByIdLiveData(request.getId())
                    .observe(this, new Observer<WorkInfo>() {
                        @Override
                        public void onChanged(WorkInfo workInfo) {

                            if(workInfo.getState().isFinished()){
                                Data receivedData=workInfo.getOutputData();
                                requestText.append(receivedData.getString(KEY_TASK_OUTPUT_DATA));
                            }
                            String status=workInfo.getState().name();
                            requestText.append(status+"-");
                        }
                    });

    }
}
