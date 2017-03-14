package com.example.user.myadapter;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private HashMap<String, LocationInfo> unicorns = new HashMap<String, LocationInfo>();
    private ArrayList<LocationInfo> locList = new ArrayList<LocationInfo>();
    private ArrayList<UnicornInfo> uList;

    private CustomAdapter listAdapter;
    private ExpandableListView elview;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // add data for displaying in expandable list view
        loadData();

        // Get reference of the ExpandableListView
        elview = (ExpandableListView) findViewById(R.id.elview);
        // create the adapter by passing your ArrayList data
        listAdapter = new CustomAdapter(MainActivity.this, locList);
        // attach the adapter to the expandable list view
        elview.setAdapter(listAdapter);

        // setOnChildClickListener listener for child row (unicorn names) click
        elview.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                //get the group header
                LocationInfo headerInfo = locList.get(groupPosition);
                //get the child info
                UnicornInfo detailInfo = headerInfo.getUnicornLocation().get(childPosition);
                showInputBox(headerInfo.getLoc(), childPosition, detailInfo.getName());
                return true;
            }
        });
        // setOnGroupClickListener listener for group heading (locations) click
        elview.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                //get the group header
                LocationInfo headerInfo = locList.get(groupPosition);
                return false;
            }
        });


    }

    // Reload the list view when changes are made to it
    private void refreshListView() {
        listAdapter.notifyDataSetInvalidated();
        elview.setAdapter(listAdapter);
    }

    // Displays a dialog box that allows the user to enter a new location for the unicorn
    public void showInputBox(final String oldLocation, final int index, final String unicornName) {
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.setTitle("Input Box");
        dialog.setContentView(R.layout.input_box);
        TextView txtMessage = (TextView) dialog.findViewById(R.id.txtmessage);
        txtMessage.setText("Update Unicorn Location");
        txtMessage.setTextColor(Color.parseColor("#ff2222"));
        final EditText editText = (EditText) dialog.findViewById(R.id.txtinput);
        editText.setText(oldLocation);
        Button bt = (Button) dialog.findViewById(R.id.btdone);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String locationEdit = String.valueOf(editText.getText());


                //   Remove old unicorn from group

                LocationInfo oldLoc = new LocationInfo();
                oldLoc.setLoc(oldLocation);

                UnicornInfo oldUInfo = new UnicornInfo();
                oldUInfo.setName(unicornName);

                LocationInfo headerInfo = unicorns.get(oldLocation);
                uList = headerInfo.getUnicornLocation();

                /* Finds the index of the unicorn name that was selected and removes it */
                int index = 0;

                for (int i = 0; i < uList.size(); i++) {
                    if (unicornName.equals(uList.get(i).getName())) {
                        index = i;
                        break;
                    }

                }
                uList.remove(index);

                // Moves the unicorn to location specified in TextView

                addUnicorn(locationEdit, unicornName);
                refreshListView();
                dialog.dismiss();

            }
        });
        dialog.show();
    }

    // load some initial data into the list
    private void loadData() {

        addUnicorn("Pasture", "Hannah");
        addUnicorn("Pasture", "Joe");
        addUnicorn("Pasture", "Mary");
        addUnicorn("Barn", "Henry");
        addUnicorn("Barn", "Lola");
        addUnicorn("Valley", "Susie");
        addUnicorn("Shed", "Pyro");
        addUnicorn("Shed", "Spirit");

    }


    // Sorts the locations alphabetically
    private void sortLocations() {

        Collections.sort(locList, new Comparator<LocationInfo>(){
            public int compare(LocationInfo l1, LocationInfo l2) {
                return l1.getLoc().compareToIgnoreCase(l2.getLoc());
            }
        });
    }


    // Adds unicorns to the list
    private int addUnicorn(String location, String name) {

        int groupPosition = 0;

        LocationInfo headerInfo = unicorns.get(location);
        // add the group if doesn't exist
        if (headerInfo == null) {
            headerInfo = new LocationInfo();
            headerInfo.setLoc(location);
            unicorns.put(location, headerInfo);
            locList.add(headerInfo);
        }


        // get the children for the group
        ArrayList<UnicornInfo> uList = headerInfo.getUnicornLocation();
        // size of the children list

        int listSize = uList.size();
        // add to the counter
        listSize++;

        // create a new child and add that to the group
        UnicornInfo detailInfo = new UnicornInfo();
        detailInfo.setName(name);
        uList.add(detailInfo);

        // Sort the locations and unicorns in alphabetical order before they are loaded into the list view
        sortLocations();

        // Find the group position inside the list
        groupPosition = locList.indexOf(headerInfo);
        return groupPosition;


    }

}
