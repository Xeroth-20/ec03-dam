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
import dev.jx.ec03.network.ImageRequester;
import dev.jx.ec03.util.JsonUtils;

public class CourseRecyclerView extends RecyclerView {

    public CourseRecyclerView(@NonNull Context context) {
        super(context);
    }

    public CourseRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CourseRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        setHasFixedSize(true);
        setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        setAdapter(new CourseRecyclerViewAdapter(JsonUtils.parseAsList(R.raw.courses, Course[].class)));

        int itemSpacing = getContext().getResources().getDimensionPixelSize(R.dimen.course_item_spacing);
        addItemDecoration(new CourseItemDecoration(itemSpacing));
    }

    private static class CourseRecyclerViewAdapter extends RecyclerView.Adapter<CourseItemViewHolder> {

        private final List<Course> courses;

        public CourseRecyclerViewAdapter(List<Course> courses) {
            this.courses = courses;
        }

        @NonNull
        @Override
        public CourseItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_item, parent, false);
            return new CourseItemViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull CourseItemViewHolder holder, int position) {
            holder.setCourse(courses.get(position));
            holder.init();
        }

        @Override
        public int getItemCount() {
            return courses.size();
        }
    }

    private static class CourseItemViewHolder extends RecyclerView.ViewHolder {

        private Course course;

        public CourseItemViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void init() {
            if (course != null) {
                NetworkImageView courseImageView = itemView.findViewById(R.id.course_item_image_view);
                TextView courseTitleTextView = itemView.findViewById(R.id.course_item_title_text_view);
                TextView courseRatingTextView = itemView.findViewById(R.id.course_item_rating_text_view);
                TextView courseHoursTextView = itemView.findViewById(R.id.course_item_hours_text_view);

                courseTitleTextView.setText(course.getName());
                courseRatingTextView.setText(String.valueOf(course.getRating()));

                String courseHours;
                if (course.getHours() == Math.floor(course.getHours())) {
                    courseHours = String.format("%.0f", course.getHours());
                } else {
                    courseHours = String.format("%.1f", course.getHours());
                }

                courseHoursTextView.setText(itemView.getResources().getString(R.string.course_item_hours, courseHours));
                ImageRequester.getInstance().setImageFromUrl(courseImageView, course.getImageUrl());
            }
        }

        public void setCourse(Course course) {
            this.course = course;
        }
    }

    private static class CourseItemDecoration extends RecyclerView.ItemDecoration {

        private final int spacing;

        public CourseItemDecoration(int spacing) {
            this.spacing = spacing;
        }

        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull State state) {
            outRect.right = spacing;
            if (parent.getChildAdapterPosition(view) == 0) {
                outRect.left = spacing;
            }
        }
    }
}
