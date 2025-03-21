package com.yoga.service;


import com.yoga.datamodel.Branch;
import com.yoga.datamodel.Employee;
import com.yoga.dto.StudentFeesAlertDto;
import com.yoga.dto.response.BranchResponse;
import com.yoga.dto.response.EmployeeResponse;
import com.yoga.repository.EmployeeRepository;
import com.yoga.repository.StudentFeeRepository;
import com.yoga.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService {

        private EmployeeRepository employeeRepository;

        private UserRepository userRepository;

        private StudentFeeRepository studentFeeRepository;


        @Autowired
        public EmployeeService(EmployeeRepository employeeRepository,UserRepository userRepository,
                               StudentFeeRepository studentFeeRepository){
            this.employeeRepository = employeeRepository;
            this.userRepository = userRepository;
            this.studentFeeRepository=studentFeeRepository;
        }


        public List<EmployeeResponse> getListOfEmployeeAccordingBranch(Long branchId){
            List<Employee> employeeList = employeeRepository.findByEmployeeAccordindBranch(branchId);

            List<EmployeeResponse> employeeResponseList = employeeList.stream().map(
                    employee -> {
                        EmployeeResponse employeeResponse = new EmployeeResponse();
                         employeeResponse = convertEmployeeToEmployeeResponse(employee,employeeResponse);
                         employeeResponse.setEmail(employee.getUser().getEmail());
                         employeeResponse.setBranchName(employee.getBranch().getBranchName());

                         return employeeResponse;
                    }

            ).toList();

            return employeeResponseList;
        }


        public List<StudentFeesAlertDto> getBranchWiseStudentDueFeeAlert(Long batchId){
            List<Object[]> dueStudentList = studentFeeRepository.getStudentFeeAlertBranchWise(LocalDate.now(),batchId);

            List<StudentFeesAlertDto> studentFeesAlertDtoList = new ArrayList<>();

            for (Object[] dueStudent : dueStudentList){
                StudentFeesAlertDto studentFeesAlertDto = new StudentFeesAlertDto(
                        (Long) dueStudent[0],
                        (String) dueStudent[1],
                        (String) dueStudent[2],
                        (String) dueStudent[3],
                        (String) dueStudent[4],
                        (Date) dueStudent[5],
                        (Long) dueStudent[6],
                        (Double) dueStudent[7],
                        (Double) dueStudent[8],
                        (Double) dueStudent[9],
                        (Timestamp) dueStudent[10]
                );

                studentFeesAlertDtoList.add(studentFeesAlertDto);
            }

            return studentFeesAlertDtoList;
        }

        public BranchResponse getBranchByUserId(Long userId){
            Employee employee = employeeRepository.findEmployeeByUserId(userId);

            Branch existingBranch = employee.getBranch();

            BranchResponse branchResponse = new BranchResponse();
            branchResponse.setBranchId(existingBranch.getBranchId());
            branchResponse.setBranchName(existingBranch.getBranchName());
            branchResponse.setCity(existingBranch.getCity());
            branchResponse.setAddress(existingBranch.getAddress());
            branchResponse.setBranchName(existingBranch.getBranchName());
            return branchResponse;

        }

    private EmployeeResponse convertEmployeeToEmployeeResponse(Employee employee,EmployeeResponse employeeResponse){
        employeeResponse.setFirstName(employee.getFirstName());
        employeeResponse.setEmployeeId(employee.getEmployeeId());
        employeeResponse.setAddress(employee.getAddress());
        employeeResponse.setFatherName(employee.getFatherName());
        employeeResponse.setLastName(employee.getLastName());
        employeeResponse.setJoiningDate(String.valueOf(employee.getJoiningDate()));
        employeeResponse.setMobileNumber(employee.getMobileNumber());
        employeeResponse.setSalary(employee.getSalary());
        return employeeResponse;
    }
}
