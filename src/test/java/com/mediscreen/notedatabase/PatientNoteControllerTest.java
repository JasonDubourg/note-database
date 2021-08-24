package com.mediscreen.notedatabase;

import com.mediscreen.notedatabase.controller.PatientNoteController;
import com.mediscreen.notedatabase.entity.PatientNote;
import com.mediscreen.notedatabase.repository.PatientNoteRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(PatientNoteController.class)
public class PatientNoteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatientNoteRepository patientNoteRepository;

    @DisplayName("GET : /patient-note/{patientId}")
    @Test
    void findPatientNoteById() throws Exception {
        // ARRANGE
        when(patientNoteRepository.findPatientNoteByPatientId(any(Integer.class))).thenReturn(new PatientNote());
        // ACT
        MvcResult mvcResult = this.mockMvc.perform(get("/patient-note/1")).andDo(print()).andReturn();
        int status = mvcResult.getResponse().getStatus();
        // ASSERT
        assertEquals(status, 200);
        verify(patientNoteRepository, times(1)).findPatientNoteByPatientId(any(Integer.class));
    }

    @DisplayName("GET : /patientsNotes")
    @Test
    void findAllPatientsNotes() throws Exception {
        // ARRANGE
        PatientNote patientNote1 = new PatientNote(1, "Note1");
        PatientNote patientNote2 = new PatientNote(2, "Note2");
        List<PatientNote> patientNoteList = new ArrayList<PatientNote>();
        patientNoteList.add(patientNote1);
        patientNoteList.add(patientNote2);
        when(patientNoteRepository.findAll()).thenReturn(patientNoteList);
        // ACT
        MvcResult mvcResult = this.mockMvc.perform(get("/patientsNotes")).andDo(print()).andReturn();
        int status = mvcResult.getResponse().getStatus();
        // ASSERT
        assertEquals(status, 200);
        verify(patientNoteRepository, times(1)).findAll();
    }

    @DisplayName("POST : /patient-note")
    @Test
    void savePatientNote() throws Exception {
        // ARRANGE
        PatientNote patientNoteToSave = new PatientNote(1, "Note");
        when(patientNoteRepository.save(patientNoteToSave)).thenReturn(patientNoteToSave);
        // ACT
        MvcResult mvcResult = mockMvc.perform(post("/patient-note").contentType(MediaType.APPLICATION_JSON).content("{\"note\": \"note\"}"))
                .andDo(print()).andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        // ASSERT
        assertEquals(200, response.getStatus());
    }

    @DisplayName("DELETE : /patient-note/{patientId}")
    @Test
    void deletePatientNote() throws Exception {
        // ARRANGE
        PatientNote patientNoteToDelete = new PatientNote(1, "Note");
        int id = patientNoteToDelete.getPatientId();
        when(patientNoteRepository.findPatientNoteByPatientId(id)).thenReturn(patientNoteToDelete);
        doNothing().when(patientNoteRepository).delete(patientNoteToDelete);
        // ACT
        MvcResult mvcResult = mockMvc.perform(delete("/patient-note/" + id).contentType(MediaType.APPLICATION_JSON)).andDo(print()).andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        // ASSERT
        assertEquals(204, response.getStatus());
        verify(patientNoteRepository, times(1)).delete(patientNoteToDelete);
    }

    @DisplayName("PUT : /patient-note/{patientId}")
    @Test
    void updatePatientNote() throws Exception {
        // ARRANGE
        int id = 1;
        String nouvelleNote = "Nouvelle note";
        PatientNote patientNoteToUpdate = new PatientNote(1, "Note");
        when(patientNoteRepository.findPatientNoteByPatientId(id)).thenReturn(patientNoteToUpdate);
        patientNoteToUpdate.setNotes(nouvelleNote);
        when(patientNoteRepository.save(patientNoteToUpdate)).thenReturn(patientNoteToUpdate);
        // ACT
        MvcResult mvcResult = mockMvc.perform(put("/patient-note/" + id).contentType(MediaType.APPLICATION_JSON).content("{\"note\": \"note\"}")).andDo(print()).andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        // ASSERT
        assertEquals(200, response.getStatus());
    }
}