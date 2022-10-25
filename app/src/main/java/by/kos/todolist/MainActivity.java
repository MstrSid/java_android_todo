package by.kos.todolist;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

  private LinearLayout llNotes;
  private FloatingActionButton btnAddNote;
  private Database notes = Database.getInstance();


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    initViews();

    btnAddNote.setOnClickListener(view -> {
      startActivity(AddNoteActivity.newIntent(this));
    });
  }

  @Override
  protected void onResume() {
    super.onResume();
    showNotes();
  }

  private void initViews() {
    llNotes = findViewById(R.id.llNotes);
    btnAddNote = findViewById(R.id.btnAddNote);
  }

  private void showNotes() {
    llNotes.removeAllViews();
    for (Note note : notes.getNotes()) {
      View noteView = getLayoutInflater().inflate(R.layout.note_item, llNotes, false);
      TextView noteItem = noteView.findViewById(R.id.tvNote);
      noteItem.setText(note.getText());
      int colorResId;
      switch (note.getPriority()) {
        case 0:
          colorResId = android.R.color.holo_green_light;
          break;
        case 1:
          colorResId = android.R.color.holo_orange_light;
          break;
        default:
          colorResId = android.R.color.holo_red_light;
          break;
      }
      int color = ContextCompat.getColor(this, colorResId);
      noteItem.setBackgroundColor(color);
      llNotes.addView(noteView);
    }
  }
}