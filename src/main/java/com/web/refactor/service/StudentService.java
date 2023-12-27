package com.web.refactor.service;

import com.web.refactor.domain.entity.Student;
import com.web.refactor.domain.entity.Study;
import com.web.refactor.exception.student.NotFoundStudentException;
import com.web.refactor.exception.study.NotFoundStudyException;
import com.web.refactor.model.request.StudentRequest;
import com.web.refactor.model.request.StudyStudentRequest;
import com.web.refactor.repository.StudentRepository;
import com.web.refactor.repository.StudyRepository;
import com.web.refactor.repository.StudyStudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StudentService {

	private final StudyRepository studyRepository;
	private final StudentRepository studentRepository;
	private final StudyStudentRepository studyStudentRepository;

	@Transactional
	public void inputStudent(StudentRequest.Info request) {

		studentRepository.save(request.toEntity());
	}

	@Transactional
	public void inputStudyStudent(StudyStudentRequest.Info request) {

		Study study = studyRepository.findById(request.studyId()).orElseThrow(NotFoundStudyException::new);
		Student student = studentRepository.findById(request.studentId()).orElseThrow(NotFoundStudentException::new);

		studyStudentRepository.save(request.toEntity());
	}
}
