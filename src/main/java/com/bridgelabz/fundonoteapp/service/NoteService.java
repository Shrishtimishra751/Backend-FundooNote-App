package com.bridgelabz.fundonoteapp.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.bridgelabz.fundonoteapp.model.Label;
import com.bridgelabz.fundonoteapp.model.Note;
public interface NoteService {
	public Note createNote(Note note, String token);

	public Note findById(int userId);

	public Note updateNote(Note note, String token);

	public String deleteNote(int noteId,String token);

	public Note getNoteInfo(int noteId);

	public List<Note> getAllNotes();

	public List<Note> getNotes(String token);
	
	public List<Note> getNotesInTrash(String token);
	
	public List<Note> getNotesInArchive(String Token);

	public Label labelCreate(Label label, String token);

	public Label labelUpdate(Label label, String token,int labelId);

	public String labelDelete(String token, int labelId);

	public List<Label> getLabels(String token);

	public Label createNoteLabel(String token, Label label,int noteId);

	public Label noteLabelUpdate(Label label, int noteId);
}