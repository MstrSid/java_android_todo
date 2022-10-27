package by.kos.todolist;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.ItemTouchHelper.SimpleCallback;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
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

    rcvNotes.setAdapter(notesAdapter);
    rcvNotes.setLayoutManager(new LinearLayoutManager(this));

    ItemTouchHelper itemTouchHelper = new ItemTouchHelper(
        new SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
          @Override
          public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull ViewHolder viewHolder,
              @NonNull ViewHolder target) {
            return false;
          }

          @Override
          public void onSwiped(@NonNull ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();
            Note note = notes.getNotes().get(position);
            notes.remove(note.getId());
            showNotes();
          }
        });

    itemTouchHelper.attachToRecyclerView(rcvNotes);

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