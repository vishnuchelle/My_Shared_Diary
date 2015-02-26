package umkc.diaryapp;

import android.app.Activity;
import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SimpleCursorAdapter;

/**
 * Created by sundarsagar on 2/24/2015.
 */
public class DiaryEditRowsView extends ListActivity {
    private DiaryDbAdapter mDbHelper;
    private Cursor mDiaryCursor;
    private static final int DELETE_ID = Menu.FIRST;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.edit_rows_diary);
        renderEditListView();
    }

    private void renderEditListView() {
        mDbHelper = new DiaryDbAdapter(this);
        mDiaryCursor = mDbHelper.getAllNotes();
        startManagingCursor(mDiaryCursor);
        String[] from = new String[] { DiaryDbAdapter.KEY_TITLE,
                DiaryDbAdapter.KEY_CREATED };
        int[] to = new int[] { R.id.text1, R.id.created, R.id.checkbox_row };
        SimpleCursorAdapter notes = new SimpleCursorAdapter(this,
                R.layout.edit_rows_diary, mDiaryCursor, from, to);
        setListAdapter(notes);
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch (item.getItemId()) {
            case DELETE_ID:
                deleteRow();
                return true;
        }
        return super.onMenuItemSelected(featureId, item);
    }

    private void deleteRow(){

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(0, DELETE_ID, 0, R.string.menu_delete);
        return true;
    }
}
