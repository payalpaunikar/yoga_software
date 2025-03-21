package com.yoga.service;


import com.yoga.component.Role;
import com.yoga.datamodel.Branch;
import com.yoga.datamodel.Employee;
import com.yoga.datamodel.User;
import com.yoga.dto.StudentFeesAlertDto;
import com.yoga.dto.request.EmployeeRequest;
import com.yoga.dto.response.EmployeeResponse;
import com.yoga.exception.EmployeeAlreadyExitException;
import com.yoga.exception.UserNotFoundException;
import com.yoga.repository.BranchRepository;
import com.yoga.repository.EmployeeRepository;
import com.yoga.repository.StudentFeeRepository;
import com.yoga.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    private EmployeeRepository employeeRepository;

    private BranchRepository branchRepository;

    private StudentFeeRepository studentFeeRepository;

    Logger logger = LoggerFactory.getLogger(AdminService.class);


    @Autowired
    public AdminService(UserRepository userRepository,PasswordEncoder passwordEncoder,
                        EmployeeRepository employeeRepository,BranchRepository branchRepository,StudentFeeRepository studentFeeRepository){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.employeeRepository = employeeRepository;
        this.branchRepository = branchRepository;
        this.studentFeeRepository = studentFeeRepository;
    }


    @Transactional
    public EmployeeResponse addEmployee(EmployeeRequest employeeRequest){
        Optional<User> exsistingUser = userRepository.findByEmail(employeeRequest.getEmail());

        if (exsistingUser.isEmpty()){
           User newUser = new User();
                   converEmployeeRequestToUser(employeeRequest,newUser);
            newUser.setRole(Role.EMPLOYEE);

          User saveUser = userRepository.save(newUser);

          Employee employee = new Employee();
                  convertEmployeeRequestToEmployee(employeeRequest,employee);
          employee.setUser(saveUser);
          Employee saveEmployee =  employeeRepository.save(employee);

           EmployeeResponse newEmployeeResponse = new EmployeeResponse();
           newEmployeeResponse = convertEmployeeToEmployeeResponse(saveEmployee,newEmployeeResponse);
           newEmployeeResponse = convertUserToEmployeeResponse(saveUser,newEmployeeResponse);

          return newEmployeeResponse;
        }

        throw new EmployeeAlreadyExitException("Employee email alredy have in our system");
    }



    public String deleteEmployee(Long empId){
        Employee existingEmployee = employeeRepository.findById(empId)
                .orElseThrow(()-> new UserNotFoundException("employee with : "+empId+" id is not found in the system"));

        //I adde casced property if user(parent) deleted then automatically employee(child) also deleted
        userRepository.delete(existingEmployee.getUser());

        return "Employee is deleted succefully";
    }


    public EmployeeResponse updateEmployee(Long empId,EmployeeRequest employeeRequest){
        Employee existingEmployee = employeeRepository.findById(empId)
                .orElseThrow(()-> new UserNotFoundException("employee with : "+empId+" id is not found in our system"));

        existingEmployee = convertEmployeeRequestToEmployee(employeeRequest,existingEmployee);
       existingEmployee.getUser().setEmail(employeeRequest.getEmail());
       Employee saveEmployee =  employeeRepository.save(existingEmployee);

        EmployeeResponse employeeResponse = new EmployeeResponse();
                convertEmployeeToEmployeeResponse(existingEmployee,employeeResponse);
                convertUserToEmployeeResponse(saveEmployee.getUser(),employeeResponse);
        return employeeResponse;

    }


    public List<EmployeeResponse> getListOfEmployee(){
        List<Employee> employeeList = employeeRepository.findAll();
        List<EmployeeResponse> employeeResponseList = employeeList.stream()
                .map(employee -> {
                    EmployeeResponse employeeResponse = new EmployeeResponse();
                    employeeResponse = convertEmployeeToEmployeeResponse(employee,employeeResponse);
                    if (employee.getBranch() !=null) {
                        employeeResponse.setBranchName(employee.getBranch().getBranchName());
                    }
                    return employeeResponse;
                }).toList();

        return employeeResponseList;
    }


    public EmployeeResponse getEmployeeById(Long empId){
        Employee existingEmployee = employeeRepository.findById(empId)
                .orElseThrow(()-> new UserNotFoundException("employee with : "+empId+" id is not found in the system"));

        EmployeeResponse newEmployeeResponse = new EmployeeResponse();
        newEmployeeResponse = convertEmployeeToEmployeeResponse(existingEmployee,newEmployeeResponse);
        newEmployeeResponse = convertUserToEmployeeResponse(existingEmployee.getUser(),newEmployeeResponse);

        return newEmployeeResponse;
    }


    public String assignBranchToEmployee(Long branchId,Long employeeId){
        Branch existingBranch = branchRepository.findById(branchId)
                .orElseThrow(()-> new UserNotFoundException("branch with : "+branchId+" id is not found in the system"));

        Employee exitingEmployee = employeeRepository.findById(employeeId)
                .orElseThrow(()->new UserNotFoundException("employee with : "+employeeId+" id is not found in the system"));

        exitingEmployee.setBranch(existingBranch);
        employeeRepository.save(exitingEmployee);

        return "employee id : "+employeeId+" is assign to "+existingBranch.getBranchId()+" branch id";
    }


    public List<StudentFeesAlertDto> getStudentFeeAlert(){
        List<Object[]> results = studentFeeRepository.getStudentFeeAlert(LocalDate.now());
        List<StudentFeesAlertDto> dtoList = new ArrayList<>();

        for (Object[] result : results) {
            StudentFeesAlertDto dto = new StudentFeesAlertDto(
                    (Long) result[0],
                    (String) result[1],
                    (String) result[2],
                    (String) result[3],
                    (String) result[4],
                    (Date) result[5],
                    (Long) result[6],
                    (Double) result[7],
                    (Double) result[8],
                    (Double) result[9],
                    (Timestamp) result[10]
            );
            dtoList.add(dto);
           // logger.info(dto.getStudentId().toString()+dto.getJoiningDate()+dto.getNextInstallmentDate()+dto.getPaidAmount());
        }
        return dtoList;

    }


    private User converEmployeeRequestToUser(EmployeeRequest employeeRequest,User newUser){
        newUser.setEmail(employeeRequest.getEmail());
        newUser.setPassword(passwordEncoder.encode(employeeRequest.getPassword()));
        return newUser;
    }

    private Employee convertEmployeeRequestToEmployee(EmployeeRequest employeeRequest,Employee employee){
        employee.setFirstName(employeeRequest.getFirstName());
        employee.setLastName(employeeRequest.getLastName());
        employee.setFatherName(employeeRequest.getFatherName());
        employee.setMobileNumber(employeeRequest.getMobileNumber());
        employee.setAddress(employeeRequest.getAddress());
        employee.setJoiningDate(LocalDate.parse(employeeRequest.getJoiningDate()));
        employee.setSalary(employeeRequest.getSalary());

        return employee;
    }


    private EmployeeResponse convertUserToEmployeeResponse(User user,EmployeeResponse employeeResponse){
       employeeResponse.setEmail(user.getEmail());
       return employeeResponse;
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
