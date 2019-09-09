package com.bridgelabz.fundonoteapp.service.impl;

import java.io.IOException;
import java.util.Map;

import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bridgelabz.fundonoteapp.model.Note;
import com.fasterxml.jackson.databind.ObjectMapper;
@Component
public class ESUserService {
	
	private String INDEX = "note";
	private String TYPE = "data";
	
	@Autowired
	private RestHighLevelClient client;

	@Autowired
	private ObjectMapper mapper;

	public String createNote(Note note) {
	@SuppressWarnings("unchecked")
	Map<String, Object> noteMapper = mapper.convertValue(note, Map.class);
	IndexRequest indexRequest = new IndexRequest(INDEX, TYPE, String.valueOf(note.getNoteId())).source(noteMapper);
	IndexResponse indexResponse = null;
	try {
	indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
	} catch (IOException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
	}
	return indexResponse.getResult().name();
	}

	public Note findById(int noteId) throws IOException {
	GetRequest getRequest = new GetRequest(INDEX, TYPE, String.valueOf(noteId));
	GetResponse getResponse;
	Note note = null;
	getResponse = client.get(getRequest, RequestOptions.DEFAULT);
	Map<String, Object> resultMap = getResponse.getSource();
	note = mapper.convertValue(resultMap, Note.class);
	return note;
	}

//		public String update(NotesDto notesDto) {
//		Notes note = findById(notesDto.getTitle());
//		UpdateRequest updateRequest = new UpdateRequest(INDEX, TYPE, resultDocument.getId());
//		Map<String, Object> documentMapper = objectMapper.convertValue(document, Map.class);
//		updateRequest.doc(documentMapper);
//		UpdateResponse updateResponse = client.update(updateRequest, RequestOptions.DEFAULT);
//		return updateResponse.getResult().name();
//		}
	}

