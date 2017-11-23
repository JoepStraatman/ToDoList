package nl.joepstraatman.todolist;

import android.database.Cursor;
import android.net.wifi.p2p.WifiP2pManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private ToDoDatabase db;
    private ToDoAdapter adapt;
    private ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = ToDoDatabase.getInstance(getApplicationContext());
        adapt = new ToDoAdapter(getApplicationContext(), db.selectAll());
        list = (ListView) findViewById(R.id.toDo);
        list.setAdapter(adapt);
        list.setOnItemClickListener(new ListViewContainer());
        list.setOnItemLongClickListener(new ListViewContainerLong());
    }


    public void addItem(View view) {
        EditText et = (EditText) findViewById(R.id.inp);
        db.insert(et.getText().toString(), 0);
        updateData();
        et.setText("");
    }

    private void updateData() {
        adapt.swapCursor(db.selectAll());
    }


    private class ListViewContainer implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent,
                                View view,
                                int position,
                                long id) {
            //String picked = ("" +(parent.getItemAtPosition(position)));
            //Toast toast = Toast.makeText(getApplicationContext(), picked, Toast.LENGTH_SHORT);
            //toast.show();
            db.update(id);
            updateData();
        }
    }
    private class ListViewContainerLong implements AdapterView.OnItemLongClickListener {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent,
                                       View view,
                                       int position,
                                       long id) {
            db.delete(id);
            updateData();
            return false;
        }
    }
}