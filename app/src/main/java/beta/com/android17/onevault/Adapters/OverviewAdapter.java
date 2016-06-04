package beta.com.android17.onevault.Adapters;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import beta.com.android17.onevault.Menu.Overview.OnListFragmentInteractionListener;
import beta.com.android17.onevault.R;

/**
 * specified {@link OnListFragmentInteractionListener}.
 */
public class OverviewAdapter extends RecyclerView.Adapter<OverviewAdapter.ViewHolder> {

    private final String[] mValues, mContents;
    private final OnListFragmentInteractionListener mListener;

    public OverviewAdapter(String[] items, String[] contents, OnListFragmentInteractionListener listener) {
        mValues = items;
        mContents = contents;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_overview_contents, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mIdView.setText(mValues[position]);
        holder.mConView.setTextColor(Color.GREEN);
        if(position >= 7)
            holder.mConView.setTextColor(Color.RED);
        holder.mConView.setText(mContents[position]);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final View mView;
        public final TextView mIdView;
        public final TextView mConView;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.id);
            mConView = (TextView) view.findViewById(R.id.overview_content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" ;
        }

        @Override
        public void onClick(View v) {

        }


    }
}
