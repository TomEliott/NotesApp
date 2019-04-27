package com.tomeliottherfray.notemaker;

/*
* NOTEMAKER APP
* Version 2.42
* By Tom-Eliott Herfray
* Student Number : 2999664
*
* March 2019
* */

// My imports
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.Menu;
import android.view.MenuItem;
import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import java.text.DateFormat;
import java.util.Date;

public class NotesActivity extends Activity
{
    private DBOpenHelper tdb; // Import database's assets
    private SQLiteDatabase sdb; // Import database's assets

    public String db_title; // Var that will host the title
    public String db_data; // Var that will host the data
    public String db_time; // Var that will host the last update time

    public int isnew; // To know if it is a new or a edited note
    public int db_id; // Var that will host the ID

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notes_activity);

        // Buttons declaration
        final FloatingActionButton save = findViewById(R.id.save);
        final FloatingActionButton returnlist = findViewById(R.id.returnlist);
        final FloatingActionButton delete = findViewById(R.id.delete);
        final FloatingActionButton share = findViewById(R.id.share);

        // Recover the data from MainActivity
        final Intent MainActivityIntent = getIntent();

        // EditText declaration
        final EditText title = findViewById(R.id.titleNotes);
        final EditText data = findViewById(R.id.dataNotes);

        // Open the database
        tdb = new DBOpenHelper(this, "test.db", null, 1);
        sdb = tdb.getWritableDatabase();

        // Recover selected ID and type of note (new or edited) from MainActivity
        final String selectedID = MainActivityIntent.getStringExtra("selectedID");
        final String isNew = MainActivityIntent.getStringExtra("isNew");
        db_id = Integer.parseInt(selectedID);
        isnew = Integer.parseInt(isNew);

        if (db_id == -1)
        {
            // Creating a new note
            //Toast.makeText(getApplicationContext(),"New note created",Toast.LENGTH_SHORT).show();
        } // Case where the user wants to create a new note
        else
        {
            // Editing a note
            //Toast.makeText(getApplicationContext(),"ID : "+selectedID,Toast.LENGTH_SHORT).show();

            // Will recover the data from the note
            Cursor c = sdb.query("test", new String[] {"ID", "TITLE_NAME", "DATA_NAME", "TIME_NAME"},
                    null, null, null, null, null);

            int x = 0;
            c.moveToFirst();
            while (x < c.getCount() && c.getInt(0) != db_id)
            {
                c.moveToNext();
                x++;
            }
            db_id = c.getInt(0); // Current ID
            db_title = c.getString(1); // Current title
            db_data = c.getString(2); // Current data
            db_time = c.getString(3); // Last update of the note
            title.setText(db_title); // Show title in the NotesActivity
            data.setText(db_data); // Show data in the NotesActivity

            // Display the date/time of the last update of the note
            Toast.makeText(getApplicationContext(),"Last update : "+db_time,Toast.LENGTH_SHORT).show();
        } // Case where the user wants to edit an existing note

        // Button that will save the note
        save.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                // Get the new title, data and time
                String title_text = title.getText().toString();
                String data_text = data.getText().toString();
                String time_text = DateFormat.getDateTimeInstance().format(new Date());

                if (isnew == 1)
                {
                    // Saving the new note
                    ContentValues newnote = new ContentValues();
                    newnote.put("TITLE_NAME", title_text);
                    newnote.put("DATA_NAME", data_text);
                    newnote.put("TIME_NAME", time_text);
                    sdb.insert("test", null, newnote);
                    isnew = 0; // the note is not longer 'new'
                } // Saving the new note
                else
                {
                    ContentValues editednote = new ContentValues();
                    editednote.put("TITLE_NAME", title_text);
                    editednote.put("DATA_NAME", data_text);
                    editednote.put("TIME_NAME", time_text);
                    sdb.update("test", editednote, "ID="+db_id, null);
                } // Updating the note
                Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();
            }
        });

        // Button that will return to MainActivity
        returnlist.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent intent = new Intent(NotesActivity.this, MainActivity.class);
                sdb.close();
                finish();
                startActivity(intent);
            }
        });

        // Button that will delete the current note and return to MainActivity
        delete.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent intent = new Intent(NotesActivity.this, MainActivity.class);
                // Delete the note from database
                sdb.delete("test", "ID="+db_id, null);
                sdb.close();
                finish();
                startActivity(intent);
            }
        });

        // Button that will share the note (using a new Intent)
        share.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                String title_text = title.getText().toString();
                String data_text = data.getText().toString();

                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT, title_text + "\n" + data_text);
                shareIntent.setType("text/plain");
                startActivity(shareIntent);

                /*
                You will be able to select any service available
                on your phone that accepts sending text (like Message, Twitter, NFC, ...)
                 */
            }
        });
    }

    // Management of the toolbar (removed)
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if (id == R.id.action_settings)
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}