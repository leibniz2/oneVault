package beta.com.android17.onevault.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.Arrays;
import java.util.List;

import beta.com.android17.onevault.R;
import beta.com.android17.onevault.Utility.RunTime;
import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * Created by RRosall on 12/13/2015.
 */
public class ProfileAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    public static final int TYPE_PROFILE_HEADER = 0;
    public static final int TYPE_PROFILE_OPTIONS = 1;
    public static final int TYPE_PHOTO = 2;

    private static final int MIN_ITEMS_COUNT = 2;

    private final Context context;
    private final int cellSize;
    private final int avatarSize;

    private final String profilePhoto;
//    private final List<String> photos;

    public ProfileAdapter(Context context) {
        this.context = context;
        this.cellSize = RunTime.getScreenWidth(context) / 3;
        this.avatarSize = 88;
        this.profilePhoto = "profile";
//        this.photos = "photos";
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return TYPE_PROFILE_HEADER;
       else
            return TYPE_PROFILE_OPTIONS;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (TYPE_PROFILE_HEADER == viewType) {
            final View view = LayoutInflater.from(context).inflate(R.layout.view_user_profile_header, parent, false);
            StaggeredGridLayoutManager.LayoutParams layoutParams = (StaggeredGridLayoutManager.LayoutParams) view.getLayoutParams();
            layoutParams.setFullSpan(true);
            view.setLayoutParams(layoutParams);
            return new ProfileHeaderViewHolder(view);
        } else if (TYPE_PROFILE_OPTIONS == viewType) {
            final View view = LayoutInflater.from(context).inflate(R.layout.user_profile_options, parent, false);
            StaggeredGridLayoutManager.LayoutParams layoutParams = (StaggeredGridLayoutManager.LayoutParams) view.getLayoutParams();
            layoutParams.setFullSpan(true);
            view.setLayoutParams(layoutParams);
            return new ProfileOptionsViewHolder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        if (TYPE_PROFILE_HEADER == viewType) {
            bindProfileHeader((ProfileHeaderViewHolder) holder);
        } else {
            bindProfileOptions((ProfileOptionsViewHolder) holder);
        }
    }

    private void bindProfileHeader(final ProfileHeaderViewHolder holder) {
//        Picasso.with(context)
//                .load(profilePhoto)
//                .placeholder(R.drawable.img_circle_placeholder)
//                .resize(avatarSize, avatarSize)
//                .centerCrop()
//                .transform(new CircleTransformation())
//                .into(holder.ivUserProfilePhoto);
    }

    private void bindProfileOptions(final ProfileOptionsViewHolder holder) {
        holder.vButtons.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                holder.vButtons.getViewTreeObserver().removeOnPreDrawListener(this);
//                holder.vUnderline.getLayoutParams().width = holder.btnGrid.getWidth();
                holder.vUnderline.requestLayout();
                return false;
            }
        });
    }


    @Override
    public int getItemCount() {
        return MIN_ITEMS_COUNT;
    }

    static class ProfileHeaderViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.ivUserProfilePhoto)
        ImageView ivUserProfilePhoto;
        @InjectView(R.id.vUserDetails)
        View vUserDetails;
        @InjectView(R.id.btnFollow)
        Button btnFollow;
        @InjectView(R.id.vUserStats)
        View vUserStats;
        @InjectView(R.id.vUserProfileRoot)
        View vUserProfileRoot;

        public ProfileHeaderViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }

    static class ProfileOptionsViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.btnGrid)
        ImageButton btnInfo;
        @InjectView(R.id.btnList)
        ImageButton btnSummary;
        @InjectView(R.id.btnMap)
        ImageButton btnTransaction;

        @InjectView(R.id.vUnderline)
        View vUnderline;
        @InjectView(R.id.vButtons)
        View vButtons;

        public ProfileOptionsViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }



}
