package by.kos.todolist;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

  private FloatingActionButton btnAddNote;
  private RecyclerView rcvNotes;
  private NotesAdapter notesAdapter;

  private final Database notes = Database.getInstance();


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    initViews();

    notesAdapter = new NotesAdapter();
    notesAdapter.setOnNoteClickListener(note -> {
      notes.remove(note.getId());
      showNotes();
    });
    rcvNotes.setAdapter(notesAdapter);
    rcvNotes.setLayoutManager(new LinearLayoutManager(this));

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
    rcvNotes = findViewById(R.id.rcvNotes);
    btnAddNote = findViewById(R.id.btnAddNote);
  }

  private void showNotes() {
    notesAdapter.setNotes(notes.getNotes());
    notesAdapter.notifyDataSetChanged();
  }
}