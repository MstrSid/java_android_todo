package by.kos.todolist;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.ItemTouchHelper.SimpleCallback;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import by.kos.todolist.NotesAdapter.NotesViewHolder;
import by.kos.todolist.NotesAdapter.OnNoteClickListener;
import by.kos.todolist.NotesAdapter.OnNoteLongClickListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

  private FloatingActionButton btnAddNote;
  private RecyclerView rcvNotes;
  private NotesAdapter notesAdapter;

  private NoteDatabase noteDatabase;
  private Handler handler = new Handler(Looper.getMainLooper());


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    noteDatabase = NoteDatabase.getInstance(getApplication());
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
            Note note = notesAdapter.getNotes().get(position);
            Thread thread = new Thread(() -> {
              noteDatabase.notesDao().remove(note.getId());
              handler.post(() -> {
                showNotes();
              });
            });
            thread.start();
          }
        });

    itemTouchHelper.attachToRecyclerView(rcvNotes);

    btnAddNote.setOnClickListener(view -> startActivity(AddNoteActivity.newIntent(this)));
  }

  @Override
  protected void onResume() {
    super.onResume();
    showNotes();
    Log.d("Mys", "Resumed");
  }

  private void initViews() {
    rcvNotes = findViewById(R.id.rcvNotes);
    btnAddNote = findViewById(R.id.btnAddNote);
  }

  private void showNotes() {
    Thread thread = new Thread(() -> {
      List<Note> notes = noteDatabase.notesDao().getNotes();
      handler.post(() -> {
        notesAdapter.setNotes(notes);
        notesAdapter.notifyDataSetChanged();
      });
    });
    thread.start();
  }
}