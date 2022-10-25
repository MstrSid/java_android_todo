package by.kos.todolist;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

  private LinearLayout llNotes;
  private FloatingActionButton btnAddNote;
  ArrayList<Note> notes;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    initViews();
    Random random = new Random();
    notes = new ArrayList<>();
    for (int i = 0; i < 20; i++) {
      notes.add(new Note(i, "Note" + i, random.nextInt(3)));
    }
    showNotes();
  }

  private void initViews() {
    llNotes = findViewById(R.id.llNotes);
    btnAddNote = findViewById(R.id.btnAddNote);
  }

  private void showNotes() {
    for (Note note : notes) {
      Log.d("item_p", String.valueOf(note.getPriority()));
      View noteView = getLayoutInflater().inflate(R.layout.note_item, llNotes, false);
      TextView noteItem = noteView.findViewById(R.id.tvNote);
      noteItem.setText(note.getText());
      int colorResId;
      switch (note.getPriority()) {
        case 0:
          colorResId = android.R.color.holo_green_light;
          noteItem.setBackgroundColor(Color.parseColor("red"));
          break;
        case 1:
          colorResId = android.R.color.holo_orange_light;
          noteItem.setBackgroundColor(Color.parseColor("yellow"));
          break;
        default:
          colorResId = android.R.color.holo_red_light;
          noteItem.setBackgroundColor(Color.parseColor("green"));
          break;
      }
      int color = ContextCompat.getColor(this, colorResId);
      noteItem.setBackgroundColor(color);
      llNotes.addView(noteView);
    }
  }
}