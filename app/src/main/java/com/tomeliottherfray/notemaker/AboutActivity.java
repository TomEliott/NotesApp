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
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.support.design.widget.FloatingActionButton;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.content.Intent;

public class AboutActivity extends Activity
{
    @Override
    protected void onCreate(final Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_activity);

        // Buttons declaration
        final FloatingActionButton returnhome = findViewById(R.id.returnhome);
        final FloatingActionButton github = findViewById(R.id.github);
        final FloatingActionButton website = findViewById(R.id.website);

        // Button that will return to MainActivity
        returnhome.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent intent = new Intent(AboutActivity.this, MainActivity.class);
                finish();
                startActivity(intent);
            }
        });

        // Button that will open my profile on the GitHub website (for credits)
        github.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                String GitHubWebsite = "https://www.github.com/TomEliott"; // My GitHub profile
                Intent git = new Intent(Intent.ACTION_VIEW);
                git.setData(Uri.parse(GitHubWebsite));
                startActivity(git);
            }
        });

        // Button that will open my personal website (for credits)
        website.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                String TomEliottWebsite = "https://www.tomeliott.com"; // My website
                Intent website = new Intent(Intent.ACTION_VIEW);
                website.setData(Uri.parse(TomEliottWebsite));
                startActivity(website);
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