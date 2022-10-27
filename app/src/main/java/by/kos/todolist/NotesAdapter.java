package by.kos.todolist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import by.kos.todolist.NotesAdapter.NotesViewHolder;
import java.util.ArrayList;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesViewHolder> {

  List<Note> notes = new ArrayList<>();

  public List<Note> getNotes() {
    return new ArrayList<>(notes);
  }

  private OnNoteClickListener onNoteClickListener;

  public void setNotes(List<Note> notes) {
    this.notes = notes;
  }

  public void setOnNoteClickListener(OnNoteClickListener onNoteClickListener) {
    this.onNoteClickListener = onNoteClickListener;
  }


  @NonNull
  @Override
  public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false);
    return new NotesViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
    Note note = notes.get(position);
    holder.noteView.setText(note.getText());
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
    int color = ContextCompat.getColor(holder.itemView.getContext(), colorResId);
    holder.noteView.setBackgroundColor(color);

    holder.itemView.setOnClickListener(view -> {
      if (onNoteClickListener != null) {
        onNoteClickListener.onNoteClick(note);
      }
    });
  }

  @Override
  public int getItemCount() {
    return notes.size();
  }

  class NotesViewHolder extends RecyclerView.ViewHolder {

    private TextView noteView;

    public NotesViewHolder(@NonNull View itemView) {
      super(itemView);
      noteView = itemView.findViewById(R.id.tvNote);
    }
  }

  interface OnNoteClickListener {

    void onNoteClick(Note note);
  }
}


