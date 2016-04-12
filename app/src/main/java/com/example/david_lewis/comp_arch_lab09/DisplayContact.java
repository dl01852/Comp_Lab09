package com.example.david_lewis.comp_arch_lab09;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

public class DisplayContact extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_contact);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar(); // this snippet of code is how to change the title of your toolbar
        if(actionBar != null) // just some error checking for a potential null reference.
            actionBar.setTitle("Display Contacts");

        // These two lines adds a back arrow button in your toolbar to go back to the previous page.
        // Arrow is there by default and is hidden by default. (No icon needed)
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        // i didn't define R.id.home  That id is created by default from android for the home button.
        if(id == android.R.id.home)
            NavUtils.navigateUpTo(this, new Intent(this,MainActivity.class)); // this is how you go back up to the main class
                                                                             // you could also use .NavigateUpTask if the mainActivity is the parent activity.
                                                                             // make the main Activity the Parent activity in the AndroidManifext.xml file
        return super.onOptionsItemSelected(item);
    }

}
