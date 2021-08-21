package com.mediscreen.notedatabase.repository;

import com.mediscreen.notedatabase.entity.PatientNote;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientNoteRepository extends MongoRepository<PatientNote, String> {
    public PatientNote findPatientNoteByPatientId(int patientId);
}
