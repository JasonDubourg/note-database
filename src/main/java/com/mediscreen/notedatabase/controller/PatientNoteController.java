package com.mediscreen.notedatabase.controller;

import com.mediscreen.notedatabase.entity.PatientNote;
import com.mediscreen.notedatabase.exception.PatientNoteNotFoundException;
import com.mediscreen.notedatabase.repository.PatientNoteRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@Api(description = "Gestion des patients")
@RestController
public class PatientNoteController {

    @Autowired
    PatientNoteRepository patientNoteRepository;

    @ApiOperation(value = "Récupère une note de patient selon son ID")
    @ApiResponses(value = {@ApiResponse(code = 404, message = "Patient with id { id } doesn't have note")})
    @GetMapping(value = "/patient-note/{patientId}")
    public PatientNote findPatientNoteById(@PathVariable("patientId") int patientId){
        PatientNote patientNote = patientNoteRepository.findPatientNoteByPatientId(patientId);
        if (Objects.isNull(patientNote)){
            throw new PatientNoteNotFoundException("Patient with id " + patientId + " doesn't have note");
        }
        return patientNote;
    }

    @ApiOperation(value = "Récupère toutes les notes")
    @ApiResponses(value = {@ApiResponse(code = 404, message = "Any notes in database")})
    @GetMapping(value = "/patientsNotes")
    public List<PatientNote> findAllPatientsNotes(){
        List<PatientNote> patientNotesList = patientNoteRepository.findAll();
        if (patientNotesList.isEmpty()){
            throw new PatientNoteNotFoundException("Any notes in database");
        }
        return patientNotesList;
    }

    @ApiOperation(value = "Sauvegarde la note d'un patient")
    @ApiResponses(value = {@ApiResponse(code = 404, message = "The patient with id {id} have already a note in database")})
    @PostMapping(value = "/patient-note")
    public PatientNote savePatientNote(@RequestBody PatientNote patientNote){
        PatientNote patientNoteToSave = patientNoteRepository.findPatientNoteByPatientId(patientNote.getPatientId());
        if(Objects.isNull(patientNoteToSave)){
            patientNoteRepository.save(patientNote);
        } else {
            throw new PatientNoteNotFoundException("The patient with id " + patientNote.getPatientId() + " have already a note in database");
        }
        return patientNote;
    }

    @ApiOperation(value = "Met à jour la note d'un patient")
    @ApiResponses(value = {@ApiResponse(code = 404, message = "The patient with id {id} to update note doesn't exist in database")})
    @PutMapping(value = "/patient-note/{patientId}")
    public PatientNote updatePatientNote(@PathVariable("patientId") int patientId, @RequestBody PatientNote patientNote){
        PatientNote patientNoteToUpdate = patientNoteRepository.findPatientNoteByPatientId(patientId);
        if(Objects.isNull(patientNoteToUpdate)){
            throw new PatientNoteNotFoundException("The patient with id " + patientId + " to update note doesn't exist in database");
        } else {
            patientNoteToUpdate.setNotes(patientNote.getNotes());
            patientNoteRepository.save(patientNoteToUpdate);
        }
        return patientNoteToUpdate;
    }

    @ApiOperation(value = "Supprime la note d'un patient")
    @ApiResponses(value = {@ApiResponse(code = 404, message = "The patient with id {id} to delete note doesn't exist in database")})
    @DeleteMapping(value = "/patient-note/{patientId}")
    public String deletePatientNote(@PathVariable(value = "patientId") int patientId){
        PatientNote patientNoteToDelete = patientNoteRepository.findPatientNoteByPatientId(patientId);
        String result = "Patient note successfully deleted";
        if(Objects.isNull(patientNoteToDelete)){
            throw new PatientNoteNotFoundException("The patient with id " + patientId + " to delete note doesn't exist in database");
        } else {
            patientNoteRepository.delete(patientNoteToDelete);
        }
        return result;
    }
}
