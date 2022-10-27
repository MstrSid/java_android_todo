package by.kos.todolist;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import com.google.android.material.snackbar.Snackbar;

public class AddNoteActivity extends AppCompatActivity {

  private EditText edtText;
  private RadioGroup radioGroupPriority;
  private RadioButton rBtnLow;
  private RadioButton rBtnMedium;
  private RadioButton rBtnHigh;
  private Button btnAddNote;
  private NoteDatabase noteDatabase;
  private Handler handler = new Handler(Looper.getMainLooper());

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_add_note);
    initViews();
    noteDatabase = NoteDatabase.getInstance(getApplication());

    btnAddNote.setOnClickListener(view -> {
      saveNote(view);
    });
  }

  private int getPriority() {
    int priority;
    if (rBtnLow.isChecked()) {
      priority = 0;
    } else if (rBtnMedium.isChecked()) {
      priority = 1;
    } else {
      priority = 2;
    }
    return priority;
  }

  private void saveNote(View view) {
    String text = edtText.getText().toString().trim();
    if (!text.isEmpty()) {
      int priority = getPriority();
      Thread thread = new Thread(() -> {
        noteDatabase.notesDao().add(new Note(text, priority));
        handler.post(() -> finish());
      });
      thread.start();


    } else {
      Snackbar.make(view, R.string.txt_error_empty, Snackbar.LENGTH_SHORT).show();
    }
  }

  private void initViews() {
    edtText = findViewById(R.id.edtText);
    radioGroupPriority = findViewById(R.id.radioGroupPriority);
    rBtnLow = findViewById(R.id.rBtnLow);
    rBtnMedium = findViewById(R.id.rBtnMedium);
    rBtnHigh = findViewById(R.id.rBtnHigh);
    btnAddNote = findViewById(R.id.btnAddNote);
  }

  public static Intent newIntent(Context context) {
    return new Intent(context, AddNoteActivity.class);
  }
}