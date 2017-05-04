package game.engine.entities;

import java.util.HashMap;
import java.util.Map;

public class ObjectActivityHandler {

    private static final ObjectActivityHandler activityHandler = new ObjectActivityHandler();

    private final Map<Integer, ObjectActivity> activities;

    private ObjectActivityHandler(){
        activities = new HashMap<Integer, ObjectActivity>();
    }

    public static void addActivity(int id, ObjectActivity activity){
        activityHandler.activities.put(id, activity);
    }

    public static boolean doActivity(int id){
        ObjectActivity a = activityHandler.activities.get(id);
        if(a != null){
        	a.activate();
        	return true;
        }
        return false;
    }

}
