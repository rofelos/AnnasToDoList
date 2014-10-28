package thanoschatz.com.annastodolist;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.UUID;

public class ToDo {

    private UUID mId;
    private String mTitle;
    private Date mDate;
    private boolean mDone;



    private static final String JSON_ID = "id";
    private static final String JSON_TITLE = "title";
    private static final String JSON_DONE = "done";
    private static final String JSON_DATE = "date";


    public ToDo() {
        mId = UUID.randomUUID();
        mDate = new Date();
    }

    public ToDo(JSONObject json) throws JSONException{
        mId = UUID.fromString(json.getString(JSON_ID));
        if (json.has(JSON_TITLE)){
            mTitle = json.getString(JSON_TITLE);
        }
        mDone = json.getBoolean(JSON_DONE);
        mDate = new Date(json.getLong(JSON_DATE));
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject json = new JSONObject();
        json.put(JSON_ID, mId.toString());
        json.put(JSON_TITLE, mTitle);
        json.put(JSON_DONE, mDone);
        json.put(JSON_DATE, mDate.getTime());
        return json;
    }


    public UUID getId() {
        return mId;
    }

    @Override
    public String toString() {
        return mTitle;
    }


    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public Date getDate() {

        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public boolean isDone() {
        return mDone;
    }

    public void setDone(boolean done) {
        mDone = done;
    }
}
