package com.timeless.saya.feature.relatory.presentation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.timeless.saya.R;
import com.timeless.saya.core.data.Result;
import com.timeless.saya.core.util.HabitUtil;
import com.timeless.saya.core.util.RemoteDataCallback;
import com.timeless.saya.feature.auth.data.model.LoggedInUser;
import com.timeless.saya.feature.objectives_manager.data.model.Habit;
import com.timeless.saya.feature.objectives_manager.data.repository.ObjectivesRepository;
import com.timeless.saya.feature.objectives_manager.domain.ObjectivesCallback;
import com.timeless.saya.feature.relatory.data.model.Relatory;
import com.timeless.saya.feature.relatory.data.repository.RelatoryRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class RelatoryFragment extends Fragment {
    private ObjectivesRepository objectivesRepository;
    private RelatoryRepository relatoryRepository;

    private TextView offensiveValue;
    private TextView timeSavedValue;
    private RecyclerView habitsRecyclerView;
    private HabitsAdapter habitsAdapter;

    private String token;

    public static Fragment newInstance(String token) {
        return new RelatoryFragment(token);
    }

    public RelatoryFragment(String token) {
        this.token = token;
        objectivesRepository = ObjectivesRepository.getInstance();
        relatoryRepository = RelatoryRepository.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_relatory, container, false);

        offensiveValue = view.findViewById(R.id.offensiveValue);
        timeSavedValue = view.findViewById(R.id.timeSavedValue);
        habitsRecyclerView = view.findViewById(R.id.habitsRecyclerView);

        setupRecyclerView();

        loadRelatoryData();

        return view;
    }

    private void setupRecyclerView() {
        habitsAdapter = new HabitsAdapter(new ArrayList<>());
        habitsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        habitsRecyclerView.setAdapter(habitsAdapter);
        habitsRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private void loadRelatoryData() {
        relatoryRepository.getRelatory(token, (data) -> {
            objectivesRepository.getHabits(new ObjectivesCallback() {
                @Override
                public void onHabitsLoaded(List<Habit> habits) {
                    updateUI(data, habits);
                }

                @Override
                public void onPostObjectivesComplete(Result<LoggedInUser> result) {

                }
            });
        });
    }

    private void updateUI(Relatory relatory, List<Habit> habitList) {
        if (relatory == null) return;

        offensiveValue.setText(String.valueOf(relatory.getOffensive()));
        timeSavedValue.setText(String.valueOf(relatory.getTimeSaved()));
        List<HabitProgress> habits = new ArrayList<>();
        if (!(relatory.getTasksFinishedByHabit() == null || relatory.getTasksFinishedByHabit().isEmpty())) {
            for (Map.Entry<Long, Integer> entry : relatory.getTasksFinishedByHabit().entrySet()) {
                Habit habit = habitList.stream().filter(h -> Objects.equals(h.getId(), entry.getKey())).findFirst().get();

                habits.add(new HabitProgress(
                        habit.getName(),
                        entry.getValue(),
                        HabitUtil.habitToIcon(habit.getName())
                ));
            }
        }
        habitsAdapter.updateData(habits);
    }

    private class HabitsAdapter extends RecyclerView.Adapter<HabitsAdapter.HabitViewHolder> {

        private List<HabitProgress> habits;

        public HabitsAdapter(List<HabitProgress> habits) {
            this.habits = habits;
        }

        public void updateData(List<HabitProgress> newHabits) {
            this.habits = newHabits;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public HabitViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_habit_relatory, parent, false);
            return new HabitViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull HabitViewHolder holder, int position) {
            HabitProgress habit = habits.get(position);
            holder.habitName.setText(habit.getName());
            holder.tasksFinished.setText(
                    getResources().getString(R.string.task_finished_habit_relatory, habit.getCompletedTasks())
            );
            holder.habitIcon.setImageResource(habit.getIconResId());
        }

        @Override
        public int getItemCount() {
            return habits.size();
        }

        class HabitViewHolder extends RecyclerView.ViewHolder {
            ImageView habitIcon;
            TextView habitName;
            TextView tasksFinished;

            public HabitViewHolder(@NonNull View itemView) {
                super(itemView);
                habitIcon = itemView.findViewById(R.id.habit_icon2);
                habitName = itemView.findViewById(R.id.habitRelatoryName);
                tasksFinished = itemView.findViewById(R.id.tasksFinishedRelatory);
            }
        }
    }

    public static class HabitProgress {
        private String name;
        private int completedTasks;
        private int iconResId;

        public HabitProgress(String name, int completedTasks, int iconResId) {
            this.name = name;
            this.completedTasks = completedTasks;
            this.iconResId = iconResId;
        }

        public String getName() { return name; }
        public int getCompletedTasks() { return completedTasks; }
        public int getIconResId() { return iconResId; }
    }
}