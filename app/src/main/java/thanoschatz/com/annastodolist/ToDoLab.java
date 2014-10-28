package thanoschatz.com.annastodolist;


import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.UUID;

public class ToDoLab {
    private static final String TAG = "ToDoLab";
    private static final String FILENAME = "toDos.json";




    private ArrayList<ToDo> mToDos;
    private AnnasToDoListJSONSerializer mSerializer;

    private static ToDoLab sToDoLab;
    private Context mAppContext;

    private ToDoLab(Context appContext) {
        mAppContext = appContext;
        mSerializer = new AnnasToDoListJSONSerializer(mAppContext,FILENAME);

        try {
            mToDos = mSerializer.loadToDos();
        } catch (Exception e) {
            mToDos = new ArrayList<ToDo>();
            Log.e(TAG, "Error loading crimes: ", e);
        }
    }

    public static ToDoLab get(Context c) {
        if (sToDoLab == null) {
            sToDoLab = new ToDoLab(c.getApplicationContext());
        }

        return sToDoLab;
    }

    public void addToDo(ToDo c) {
        mToDos.add(c);
    }

    public void deleteToDo(ToDo c) {
        mToDos.remove(c);
        this.saveToDos();
    }


    public boolean saveToDos(){
        try{
            mSerializer.saveToDos(mToDos);
            Log.d(TAG,"todos saved to file");
            return true;
        } catch (Exception e){
            Log.e(TAG, "Error saving todos:", e);
            return false;
        }
    }


    public ArrayList<ToDo> getToDos() {
        return mToDos;
    }

    public ToDo getToDo(UUID toDoid) {
        for (ToDo c : mToDos) {
            if (c.getId().equals(toDoid))
                return c;
        }
        return null;
    }


}

