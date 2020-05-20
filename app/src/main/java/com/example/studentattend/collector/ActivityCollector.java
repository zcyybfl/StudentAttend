package com.example.studentattend.collector;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

public class ActivityCollector {

    public static List<Activity> activities = new ArrayList<>();

    public static void addActivity(Activity activity) {
        activities.add(activity);
    }

    public static void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    public static void finishAll(boolean flag) {
        for (Activity activity : activities) {
            if (!activity.isFinishing()) {
                if (activity.getClass().getSimpleName().equals("MainActivity") && flag) {
                    continue;
                }
                activity.finish();
            }
        }
    }
}
