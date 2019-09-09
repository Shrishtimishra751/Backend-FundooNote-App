package com.bridgelabz.fundonoteapp.service.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bridgelabz.fundonoteapp.model.Label;
import com.bridgelabz.fundonoteapp.model.Note;
import com.bridgelabz.fundonoteapp.repository.LabelRepository;
import com.bridgelabz.fundonoteapp.repository.NoteReposirory;
import com.bridgelabz.fundonoteapp.service.NoteService;
import com.bridgelabz.fundonoteapp.util.JwtUtil;

@Service
@Transactional
public class NoteServiceImpl implements NoteService {
	
	@Autowired
	ESUserService eNoteService;

	@Autowired
	NoteReposirory noteRepository;
	@Autowired
	private LabelRepository labelRepository;

	@Override
	public Note createNote(Note note, String token) {
//		int userId = JwtUtil.parseJWT(token);
//		System.out.println(userId);
//		LocalTime time=LocalTime.now();

		Date date = new Date();
		Timestamp ts = new Timestamp(date.getTime());

		note.setCreatedOn(ts);
		note.setUserId(1);
		eNoteService.createNote(note);
		return noteRepository.save(note);
	}

	@Override
	public Note findById(int userId) {
		List<Note> noteInfo = noteRepository.findByUserId(userId);
		return noteInfo.get(0);
	}

	@Override
	public Note updateNote(Note note, String token) {
		System.out.println(note.toString());
		int userId = JwtUtil.parseJWT(token);
		List<Note> noteInfo = noteRepository.findByNoteIdAndUserId(note.getNoteId(), userId);

		Date date = new Date();
		Timestamp ts = new Timestamp(date.getTime());

		// LocalTime time=LocalTime.now();
		noteInfo.forEach(existingUser -> {
			existingUser
					.setCreatedOn(note.getCreatedOn() != null ? note.getCreatedOn() : noteInfo.get(0).getCreatedOn());
			existingUser.setDescription(
					note.getDescription() != null ? note.getDescription() : noteInfo.get(0).getDescription());
			existingUser.setTitle(note.getTitle() != null ? note.getTitle() : noteInfo.get(0).getTitle());
			existingUser.setArchive(note.isArchive());
			existingUser.setInTrash(note.isInTrash());
			existingUser.setPinned(note.isPinned());
			existingUser.setRemainder(note.getRemainder());
			/*
			 * existingUser .setUpdatedOn(note.getUpdatedOn() != null ? note.getUpdatedOn()
			 * : noteInfo.get(0).getUpdatedOn());
			 */
		});
		noteInfo.get(0).setUpdatedOn(ts);
		System.out.println(noteInfo.get(0).toString());
		
		return noteRepository.save(noteInfo.get(0));

	}

	@Override
	public String deleteNote(int noteId, String token) {
		int userId = JwtUtil.parseJWT(token);
		List<Note> noteInfo = noteRepository.findByNoteIdAndUserId(noteId, userId);
		noteRepository.delete(noteInfo.get(0));
		return "Deleted";
	}

	@Override
	public Note getNoteInfo(int noteId) {
		List<Note> noteInfo = noteRepository.findByNoteId(noteId);
		return noteInfo.get(0);
	}

	@Override
	public List<Note> getAllNotes() {

		return noteRepository.findAll();
	}

	@Override
	public List<Note> getNotes(String token) {
		int id = JwtUtil.parseJWT(token);
		List<Note> list = noteRepository.findByUserId(id);
		return list;
	}

	@Override
	public Label labelCreate(Label label, String token) {
		int userId = JwtUtil.parseJWT(token);
		label.setUserId(userId);

		return labelRepository.save(label);
	}

	@Override
	public Label labelUpdate(Label label, String token, int labelId) {
		int userId = JwtUtil.parseJWT(token);
		List<Label> list = labelRepository.findByUserIdAndLabelId(userId, labelId);
		list.forEach(userLabel -> {
			userLabel.setLabelName(label.getLabelName() != null ? label.getLabelName() : list.get(0).getLabelName());

		});
		label.setLabelId(labelId);
		label.setUserId(userId);
		return labelRepository.save(label);
	}

	@Override
	public String labelDelete(String token, int labelId) {
		int userId = JwtUtil.parseJWT(token);
		List<Label> list = labelRepository.findByUserIdAndLabelId(userId, labelId);
		labelRepository.delete(list.get(0));
		return "Deleted";
	}

	@Override
	public List<Label> getLabels(String token) {
		int userId = JwtUtil.parseJWT(token);
		List<Label> list = labelRepository.findByUserId(userId);
		return list;
	}

	@Override
	public List<Note> getNotesInTrash(String token) {
		int userId = JwtUtil.parseJWT(token);
		List<Note> notes = noteRepository.findByUserIdAndInTrash(userId, "True");
		return notes;
	}

	@Override
	public List<Note> getNotesInArchive(String Token) {
		int userId = JwtUtil.parseJWT(Token);
		List<Note> notes = noteRepository.findByUserIdAndIsArchive(userId, "True");
		return notes;
	}

	@Override
	public Label createNoteLabel(String token, Label label, int noteId) {
		int userId = JwtUtil.parseJWT(token);
		label.setUserId(userId);
		label.setNoteId(noteId);
		return labelRepository.save(label);
	}

	@Override
	public Label noteLabelUpdate(Label label, int noteId) {

		List<Label> list = labelRepository.findByNoteIdAndLabelId(noteId, label.getLabelId());
		list.forEach(userLabel -> {
			userLabel.setLabelName(label.getLabelName() != null ? label.getLabelName() : list.get(0).getLabelName());
//			userLabel.setLabelId(label.getLabelId() >= 0 ? label.getLabelId() : list.get(0).getLabelId());
//			userLabel.setUserId(label.getUserId() >= 0 ? label.getUserId() : list.get(0).getUserId());

		});
		label.setLabelId(list.get(0).getLabelId());
		label.setUserId(list.get(0).getUserId());
		label.setNoteId(noteId);
		return labelRepository.save(label);
	}

}
