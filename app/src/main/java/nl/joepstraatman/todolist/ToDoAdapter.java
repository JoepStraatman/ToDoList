package nl.joepstraatman.todolist;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

/**
 * Created by Gebruiker on 20-11-2017.
 */

public class ToDoAdapter extends ResourceCursorAdapter {

       public ToDoAdapter(Context context, Cursor cursor){
           super(context,R.layout.row_todo,cursor);

       }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        int ccindex = cursor.getColumnIndex("completed");
        int ctindex = cursor.getColumnIndex("title");
        ccindex = cursor.getInt(ccindex);
        String ctindex_string = cursor.getString(ctindex);
        TextView title = (TextView) view.findViewById(R.id.textv);
        CheckBox complete = (CheckBox) view.findViewById(R.id.check);
        title.setText(ctindex_string);
        if (ccindex == 1){
            complete.setChecked(true);
        }else{
            complete.setChecked(false);
        }
    }


}
