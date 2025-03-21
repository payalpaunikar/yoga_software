package com.yoga.service;


import com.yoga.datamodel.Attendance;
import com.yoga.datamodel.Batch;
import com.yoga.datamodel.Student;
import com.yoga.datamodel.StudentAttendance;
import com.yoga.dto.request.GetAttendanceRequest;
import com.yoga.dto.request.MarkedAttendanceRequest;
import com.yoga.exception.UserNotFoundException;
import com.yoga.repository.AttendanceRepository;
import com.yoga.repository.StudentAttendanceRepository;
import com.yoga.repository.BatchRepository;
import com.yoga.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class AttendanceService {

    private StudentAttendanceRepository studentAttendanceRepository;

    private BatchRepository batchRepository;

    private StudentRepository studentRepository;

    private AttendanceRepository attendanceRepository;


    @Autowired
    public AttendanceService(StudentAttendanceRepository studentAttendanceRepository, BatchRepository batchRepository,
                             StudentRepository studentRepository,AttendanceRepository attendanceRepository){
        this.studentAttendanceRepository = studentAttendanceRepository;
        this.batchRepository = batchRepository;
        this.studentRepository = studentRepository;
        this.attendanceRepository= attendanceRepository;
    }


    @Transactional(readOnly = true)
    public List<GetAttendanceRequest> getBatchWiseStudentAttendanceList(Long batchId,String attendanceDate){

        LocalDate attendanceDateParse = LocalDate.parse(attendanceDate);

        //check attendance presenset for the date
        Optional<Attendance> attendance = attendanceRepository.findByAttendanceDateAndBatchId(attendanceDateParse,batchId);

        if (attendance.isEmpty()){
            List<Student> studentList = studentRepository.findActiveStudentListAccordingDate(attendanceDateParse,batchId);

            return studentList.stream()
                    .map(student ->{
                            GetAttendanceRequest getAttendanceRequest = new GetAttendanceRequest();
                            convertStudentToGetAttendanceRequest(student,getAttendanceRequest);
                            getAttendanceRequest.setIsPresent(false);

                            return getAttendanceRequest;
                            }
                            ).toList();


        }else {

            List<StudentAttendance> studentAttendanceList = studentAttendanceRepository.findByAttendance(attendance.get());

            return studentAttendanceList.stream()
                    .map(studentAttendance ->{
                        Student student = studentAttendance.getStudent();
                        GetAttendanceRequest getAttendanceRequest = new GetAttendanceRequest();
                        convertStudentToGetAttendanceRequest(student,getAttendanceRequest);
                        getAttendanceRequest.setIsPresent(studentAttendance.getIsPresent());
                        return getAttendanceRequest;
                    }).toList();
        }

    }


    @Transactional
    public String markedAttendace(Long batchId, String attendanceDate, List<MarkedAttendanceRequest> markedAttendanceRequestList){

        LocalDate attendanceParseDate = LocalDate.parse(attendanceDate);

        //check attendance presenset for the date
        Optional<Attendance> attendance = attendanceRepository.findByAttendanceDateAndBatchId(attendanceParseDate,batchId);

        if (attendance.isEmpty()){
            Batch existingBatch = batchRepository.findById(batchId)
                    .orElseThrow(()-> new UserNotFoundException("batch with id : "+batchId+" is not found"));

            Attendance newAttendance = new Attendance();
            newAttendance.setAttendanceDate(LocalDate.parse(attendanceDate));
            newAttendance.setBatch(existingBatch);

            Attendance saveAttendance = attendanceRepository.save(newAttendance);

            List<StudentAttendance> studentAttendanceList = markedAttendanceRequestList.stream()
                    .map(markedAttendanceRequest -> {
                        StudentAttendance studentAttendance = new StudentAttendance();

                        Student existingStudent = studentRepository.findById(markedAttendanceRequest.getStudentId())
                                .orElseThrow(()-> new UserNotFoundException("Student not fount with : "+markedAttendanceRequest.getStudentId()+" id"));
                        studentAttendance.setAttendance(saveAttendance);
                        studentAttendance.setIsPresent(markedAttendanceRequest.getIsPresent());
                        studentAttendance.setStudent(existingStudent);
                        return studentAttendance;
                    }).toList();

            saveAttendance.setStudentAttendances(studentAttendanceList);

            return "Student attendace save successfully";

        }

        //convert the marked attendance array list to map
        Map<Long,Boolean> attendanceMap = markedAttendanceRequestList.stream()
                .collect(Collectors.toMap(MarkedAttendanceRequest::getStudentId,MarkedAttendanceRequest::getIsPresent));

        List<StudentAttendance> updatedStudentAttendance = studentAttendanceRepository.findByAttendance(attendance.get())
                        .stream()
                                .peek(studentAttendance -> {
                                    if (attendanceMap.containsKey(studentAttendance.getStudent().getStudentId())){
                                        studentAttendance.setIsPresent(attendanceMap.get(studentAttendance.getStudent().getStudentId()));
                                    }
                                }).toList();



       studentAttendanceRepository.saveAll(updatedStudentAttendance);

        return "student attence is save succefully";

    }


    private GetAttendanceRequest convertStudentToGetAttendanceRequest(Student student, GetAttendanceRequest getAttendanceRequest){
        getAttendanceRequest.setStudentId(student.getStudentId());
        getAttendanceRequest.setFirstName(student.getFirstName());
        getAttendanceRequest.setFatherName(student.getFatherName());
        getAttendanceRequest.setLastName(student.getLastName());
        getAttendanceRequest.setMobileNumber(student.getMobileNumber());

        return getAttendanceRequest;
    }


}
