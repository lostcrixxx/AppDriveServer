package br.com.envolvedesenvolve.driveserver;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class UserListAdapter extends ArrayAdapter<String> {

    private String[] uId;
    private String[] uNames;
    private String[] uImages;
    private Activity context;

    public UserListAdapter(Activity context, String[] uId, String[] uNames, String[] uImages) {
        super(context, R.layout.list_row, uId);
        this.context = context;
        this.uId = uId;
        this.uNames = uNames;
        this.uImages = uImages;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.list_row, null, true);
        TextView textViewId = listViewItem.findViewById(R.id.tv_uid);
        TextView textViewName = listViewItem.findViewById(R.id.tv_uname);
        ImageView iv = listViewItem.findViewById(R.id.imageView3);

        textViewId.setText(uId[position]);
        textViewName.setText(uNames[position]);

        Picasso.with(context).load(uImages[position]).into(iv);

        return listViewItem;
    }
}
