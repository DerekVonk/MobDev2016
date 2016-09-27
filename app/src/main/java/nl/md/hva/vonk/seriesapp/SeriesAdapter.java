package nl.md.hva.vonk.seriesapp;


import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SeriesAdapter extends RecyclerView.Adapter<SeriesAdapter.ViewHolder> {

    private Cursor remindersCursor;

    public SeriesAdapter(Cursor cursor) {
        remindersCursor = cursor;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView description;

        public ViewHolder(View itemView) {
            super(itemView);
            description = (TextView) itemView.findViewById(R.id.decription);
        }
    }


    public SeriesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View reminderView = inflater.inflate(R.layout.row_reminder, parent, false);

        // Return a new holder instance
        SeriesAdapter.ViewHolder viewHolder = new SeriesAdapter.ViewHolder(reminderView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(SeriesAdapter.ViewHolder holder, int position) {
        TextView textView = holder.description;
        if (remindersCursor !=null && remindersCursor.moveToPosition(position))
        {
            String reminder = (remindersCursor.getString(remindersCursor.getColumnIndex(DBHelper.COLUMN_SERIES)));
            textView.setText(reminder);
        }
    }

    @Override
    public int getItemCount() {
        return (remindersCursor == null ? 0 : remindersCursor.getCount());
    }
}
