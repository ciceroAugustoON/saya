package com.timeless.saya.feature.objectives_manager.presentation;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.timeless.saya.R;
import com.timeless.saya.core.util.HabitUtil;
import com.timeless.saya.feature.objectives_manager.data.model.Habit;

import java.util.ArrayList;
import java.util.List;

public class HabitViewAdapter extends BaseAdapter {
    private Context context;
    private List<Habit> habits;
    private LayoutInflater inflater;
    private SparseBooleanArray selectedItems;

    public HabitViewAdapter(Context context, List<Habit> habits) {
        this.context = context;
        this.habits = habits;
        this.selectedItems = new SparseBooleanArray();
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return habits.size();
    }

    @Override
    public Object getItem(int position) {
        return habits.get(position);
    }

    @Override
    public long getItemId(int position) {
        return ((Habit)getItem(position)).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_habit, parent, false);
            holder = new ViewHolder();
            holder.habitName = convertView.findViewById(R.id.habit_name);
            holder.habitIcon = convertView.findViewById(R.id.habit_icon);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Habit h = habits.get(position);
        holder.habitName.setText(h.getName());
        holder.habitIcon.setImageResource(HabitUtil.habitToIcon(h.getName()));

        convertView.setActivated(selectedItems.get(position, false));

        return convertView;
    }

    public void toggleSelection(int position) {
        if (selectedItems.get(position, false)) {
            selectedItems.delete(position);
        } else {
            selectedItems.put(position, true);
        }
        notifyDataSetChanged();
    }

    public boolean isSelected(int position) {
        return selectedItems.get(position);
    }

    public void clearSelections() {
        selectedItems.clear();
        notifyDataSetChanged();
    }

    public int getSelectedCount() {
        return selectedItems.size();
    }

    public List<Habit> getSelectedHabits() {
        List<Habit> selected = new ArrayList<>();
        for (int i = 0; i < selectedItems.size(); i++) {
            selected.add(habits.get(selectedItems.keyAt(i)));
        }
        return selected;
    }

    static class ViewHolder {
        TextView habitName;
        ImageView habitIcon;
    }
}
