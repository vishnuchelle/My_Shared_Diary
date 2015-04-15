package com.example.vishnuchelle.applocation;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

public class TaskService extends IntentService {

	public TaskService() {
		super("TaskService");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onHandleIntent(Intent arg0) {

		// Do some task
		Log.i("TaskService", "Service running");
	}

}
