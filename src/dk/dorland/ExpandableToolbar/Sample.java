package dk.dorland.ExpandableToolbar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class Sample extends Activity {
    private final int ANIMATION_DURATION = 200;
    private AlertDialog alertDialog;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        // Find the ListView resource.
        ListView listView = (ListView) findViewById(R.id.listView);

        // Create contents
        String[] listContents = new String[] {
                "Scrooge McDuck",
                "Huey Duck",
                "Dewey Duck",
                "Louie Duck",
                "Launchpad McQuack",
                "Gyro Gearloose",
                "Doofus Drake",
                "Duckworth the Butler",
                "Webbigail Vanderquack",
                "Mrs. Bentina Beakley"};

        // Set up our adapter
        CustomListAdapter listAdapter = new CustomListAdapter(this, R.layout.list_item);
        listView.setAdapter(listAdapter);

        for (int i = 0; i < listContents.length; i++) {
            listAdapter.add(listContents[i]);
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {

                // Animate the toolbar
                View toolbar = view.findViewById(R.id.toolbar);
                ExpandAnimation animation = new ExpandAnimation(toolbar, ANIMATION_DURATION);
                toolbar.startAnimation(animation);

                // Attach listener to button
                CustomButton button1 = (CustomButton) view.findViewById(R.id.button1);
                button1.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        v.getBackground().setColorFilter(Color.parseColor("#00ff00"), PorterDuff.Mode.DARKEN);
                    }
                });

                // Close other toolbars
                int count = parent.getCount();
                for (int i = 0; i < count; i++) {
                    View v = parent.getChildAt(i);
                    if (v != null) {
                        View t = v.findViewById(R.id.toolbar);
                        if (position != i && t.getVisibility() == View.VISIBLE) {
                            animation = new ExpandAnimation(t, ANIMATION_DURATION);
                            t.findViewById(R.id.toolbar).startAnimation(animation);
                        }
                    }
                }
            }
        });
    }

    private class CustomListAdapter extends ArrayAdapter<String> {

        public CustomListAdapter(Context context, int textViewResourceId) {
            super(context, textViewResourceId);

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.list_item, null);
            }

            TextView textView = (TextView) convertView.findViewById(R.id.row_text);
            textView.setText(getItem(position));

            return convertView;
        }
    }
}
