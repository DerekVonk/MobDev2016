package nl.md.hva.vonk.seriesapp;


import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SeriesAdapter extends RecyclerView.Adapter<SeriesAdapter.ViewHolder> {

    private Cursor seriesCursor;


    public SeriesAdapter(Cursor cursor) {
        seriesCursor = cursor;
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
        View seriesView = inflater.inflate(R.layout.row_series, parent, false);

        // Return a new holder instance
        SeriesAdapter.ViewHolder viewHolder = new SeriesAdapter.ViewHolder(seriesView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(SeriesAdapter.ViewHolder holder, int position) {
        TextView textView = holder.description;

        if (seriesCursor !=null && seriesCursor.moveToPosition(position)) {
            String series = (seriesCursor.getString(seriesCursor.getColumnIndex(DBHelper.COLUMN_SERIES)));
            textView.setText(series);
        }
    }

    @Override
    public int getItemCount() {
        return (seriesCursor == null ? 0 : seriesCursor.getCount());
    }
}
