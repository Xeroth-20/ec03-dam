package dev.jx.ec03.view;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

import dev.jx.ec03.R;
import dev.jx.ec03.entity.Course;
import dev.jx.ec03.entity.Topic;
import dev.jx.ec03.network.ImageRequester;
import dev.jx.ec03.util.JsonUtils;

public class TopicRecyclerView extends RecyclerView {

    public TopicRecyclerView(@NonNull Context context) {
        super(context);
    }

    public TopicRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TopicRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        setHasFixedSize(true);
        setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        setAdapter(new TopicRecyclerViewAdapter(JsonUtils.parseAsList(R.raw.topics, Topic[].class)));

        int itemSpacing = getContext().getResources().getDimensionPixelSize(R.dimen.topic_item_spacing);
        addItemDecoration(new TopicItemDecoration(itemSpacing));
    }

    private static class TopicRecyclerViewAdapter extends RecyclerView.Adapter<TopicItemViewHolder> {

        private final List<Topic> topics;

        public TopicRecyclerViewAdapter(List<Topic> topics) {
            this.topics = topics;
        }

        @NonNull
        @Override
        public TopicItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.topic_item, parent, false);
            return new TopicItemViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull TopicItemViewHolder holder, int position) {
            holder.setTopic(topics.get(position));
            holder.init();
        }

        @Override
        public int getItemCount() {
            return topics.size();
        }
    }


    private static class TopicItemViewHolder extends RecyclerView.ViewHolder {

        private Topic topic;

        public TopicItemViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void init() {
            if (topic != null) {
                TextView topicTitleTextView = itemView.findViewById(R.id.topic_item_title_text_view);
                TextView topicReadingMinutes = itemView.findViewById(R.id.topic_item_reading_minutes_text_view);
                NetworkImageView topicImageView = itemView.findViewById(R.id.topic_item_image_view);

                topicTitleTextView.setText(topic.getName());
                topicReadingMinutes.setText(itemView.getResources().getString(R.string.topic_item_reading_minutes, String.valueOf(topic.getReadingMinutes())));
                ImageRequester.getInstance().setImageFromUrl(topicImageView, topic.getImageUrl());
            }
        }

        public void setTopic(Topic topic) {
            this.topic = topic;
        }
    }

    private static class TopicItemDecoration extends RecyclerView.ItemDecoration {

        private final int spacing;

        public TopicItemDecoration(int spacing) {
            this.spacing = spacing;
        }

        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull State state) {
            if (parent.getChildAdapterPosition(view) < parent.getAdapter().getItemCount() - 1) {
                outRect.bottom = spacing;
            }
        }
    }
}
