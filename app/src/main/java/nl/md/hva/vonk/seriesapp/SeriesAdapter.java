package nl.md.hva.vonk.seriesapp;


import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class SeriesAdapter extends RecyclerView.Adapter<SeriesAdapter.ViewHolder> {

    private Cursor seriesCursor;


    public SeriesAdapter(Cursor cursor) {
        seriesCursor = cursor;
    }

    /**
     * Constructor
     */
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView description;
        private ItemClickListener listener;

        public ViewHolder(View itemView) {
            super(itemView);
            description = (TextView) itemView.findViewById(R.id.decription);

//            itemView.setOnClickListener(this);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // wat moet hier?
                }
            });
        }

        public void setClickListener(ItemClickListener listener) {
            this.listener = listener;
        }

        @Override
        public void onClick(View v) {
            listener.onClickItem(getLayoutPosition());
        }
    }

    public interface ItemClickListener {
        void onClickItem(int pos);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
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

            holder.setClickListener(new ItemClickListener() {
                @Override
                public void onClickItem(int pos) {
                    // wat moet hier?
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return (seriesCursor == null ? 0 : seriesCursor.getCount());
    }
}
