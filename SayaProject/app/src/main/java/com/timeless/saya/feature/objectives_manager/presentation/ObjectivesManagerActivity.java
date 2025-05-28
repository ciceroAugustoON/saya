package com.timeless.saya.feature.objectives_manager.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.timeless.saya.R;
import com.timeless.saya.core.MainActivity;
import com.timeless.saya.core.data.Result;
import com.timeless.saya.feature.auth.data.model.LoggedInUser;
import com.timeless.saya.feature.auth.data.repository.LoginRepository;
import com.timeless.saya.feature.objectives_manager.data.model.Habit;
import com.timeless.saya.feature.objectives_manager.data.repository.ObjectivesRepository;
import com.timeless.saya.feature.objectives_manager.domain.ObjectivesCallback;
import com.timeless.saya.feature.objectives_manager.domain.ObjectivesHolder;

import java.util.List;

public class ObjectivesManagerActivity extends AppCompatActivity implements ObjectivesCallback {
    private LoginRepository loginRepository = LoginRepository.getInstance(null, null);
    private ObjectivesRepository objectivesRepository = ObjectivesRepository.getInstance();
    private List<Habit> habitList;
    private ListView listView;
    private TextView title;
    private Button nextStepButton;
    private ProgressBar loading;
    private int step = 1;
    HabitViewAdapter adapter;
    ObjectivesHolder objectivesHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_habit_manager);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        listView = findViewById(R.id.habit_list_view);
        title = findViewById(R.id.habit_request_text);
        nextStepButton = findViewById(R.id.next_step_button);
        loading = findViewById(R.id.loading);
        objectivesHolder = new ObjectivesHolder();

        nextStepButton.setEnabled(false);
        togleButtonColor();
        nextStepButton.setOnClickListener((view) -> {
            List<Habit> habitsSelected;
            switch (step) {
                case 0:
                    break;
                case 1:
                    habitsSelected = adapter.getSelectedHabits();
                    objectivesHolder.defineHabitsHad(adapter.getSelectedHabits());
                    habitList.removeAll(habitsSelected);

                    adapter = new HabitViewAdapter(this, habitList);
                    runOnUiThread(() -> listView.setAdapter(adapter));
                    title.setText(R.string.objectives_step_3);
                    nextStepButton.setText("Finalizar");
                    step++;
                    break;
                case 2:
                    habitsSelected = adapter.getSelectedHabits();
                    objectivesHolder.defineDesiredHabits(habitsSelected);
                    nextStepButton.setEnabled(false);
                    togleButtonColor();
                    listView.setVisibility(View.GONE);
                    loading.setVisibility(View.VISIBLE);
                    objectivesHolder.postObjectives(loginRepository.getToken());

            }
        });

        loading.setVisibility(View.VISIBLE);
        objectivesRepository.getHabits(this);
    }

    @Override
    public void onHabitsLoaded(List<Habit> habits) {
        habitList = habits;
        adapter = new HabitViewAdapter(this, habitList);
        runOnUiThread(() -> listView.setAdapter(adapter));
        listView.setOnItemClickListener((adapterView, view, index, l) -> {
            adapter.toggleSelection(index);
            HabitViewAdapter.ViewHolder holder = (HabitViewAdapter.ViewHolder) view.getTag();
            if (adapter.isSelected(index)) {
                view.setBackgroundColor(getResources().getColor(R.color.selected_color_bg));
                holder.habitName.setTextColor(getResources().getColor(R.color.selected_color_txt));
                holder.habitIcon.setColorFilter(getResources().getColor(R.color.selected_color_txt));
            } else {
                view.setBackgroundColor(getResources().getColor(R.color.normal_color_bg));
                holder.habitName.setTextColor(getResources().getColor(R.color.normal_color_txt));
                holder.habitIcon.setColorFilter(getResources().getColor(R.color.normal_color_txt));
            }

            nextStepButton.setEnabled(!adapter.getSelectedHabits().isEmpty());
            togleButtonColor();
        });
        runOnUiThread(() -> loading.setVisibility(View.GONE));
    }

    private void togleButtonColor() {
        if (nextStepButton.isEnabled()) {
            nextStepButton.setBackgroundColor(getResources().getColor(R.color.button_enabled));
        } else {
            nextStepButton.setBackgroundColor(getResources().getColor(R.color.button_unabled));
        }
    }

    @Override
    public void onPostObjectivesComplete(Result<LoggedInUser> result) {
        if (result instanceof Result.Error) {
            Toast.makeText(this.getApplicationContext(), getResources().getText(R.string.error_objectives), Toast.LENGTH_LONG).show();

        } else {
            loginRepository.reloadLoggedInUser(((Result.Success<LoggedInUser>)result).getData());
            Intent intent = new Intent();
            intent.putExtra("successful", true);
            setResult(RESULT_OK, intent);
            finish();
        }

    }
}