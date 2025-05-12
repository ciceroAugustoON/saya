package com.timeless.saya.feature.tasklist.presentation;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.timeless.saya.R;
import com.timeless.saya.feature.tasklist.domain.model.Task;
import com.timeless.saya.feature.tasklist.domain.util.TaskUtil;

import java.util.List;

public class TaskViewAdapter extends BaseAdapter {
    private Context context;
    private List<Task> tasks;
    private LayoutInflater inflater;

    public TaskViewAdapter(Context context, List<Task> tasks) {
        this.context = context;
        this.tasks = tasks;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return (tasks == null)? 0 : tasks.size();
    }

    @Override
    public Object getItem(int position) {
        return tasks.get(position);
    }

    @Override
    public long getItemId(int position) {
        return tasks.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item, parent, false);
            holder = new ViewHolder();
            holder.name = convertView.findViewById(R.id.task_name);
            holder.duration = convertView.findViewById(R.id.task_duration);
            holder.difficulty = convertView.findViewById(R.id.task_difficulty);
            holder.easy = convertView.findViewById(R.id.easy_skull);
            holder.medium = convertView.findViewById(R.id.medium_skull);
            holder.hard = convertView.findViewById(R.id.hard_skull);
            holder.habit_icon = convertView.findViewById(R.id.habit_icon);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Task task = tasks.get(position);
        holder.name.setText(task.getName());
        holder.duration.setText("Duração: " + (task.getTimeSecs() / 60) + " min");

        if (task.getDifficulty().getValue() < 3) {
            holder.hard.setColorFilter(Color.parseColor("#CECECE"));
            if (task.getDifficulty().getValue() < 2) {
                holder.medium.setColorFilter(Color.parseColor("#CECECE"));
            }
        }
        holder.habit_icon.setImageResource(TaskUtil.habitToIcon(task));

        return convertView;
    }

    static class ViewHolder {
        TextView name;
        TextView duration;
        TextView difficulty;
        ImageView easy, medium, hard;
        ImageView habit_icon;
    }
}