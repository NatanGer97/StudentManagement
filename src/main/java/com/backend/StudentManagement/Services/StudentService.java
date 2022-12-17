package com.backend.StudentManagement.Services;

import com.backend.StudentManagement.Repos.*;
import com.backend.StudentManagement.models.*;
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import org.apache.commons.collections4.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.web.client.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.*;
import org.springframework.web.client.*;

import java.util.*;
import java.util.stream.*;

@Service
public class StudentService {

    private final Logger logger = LoggerFactory.getLogger(StudentService.class);

    @Autowired
    StudentRepository studentRepository;
    @Autowired
    AwsService awsService;
    @Autowired
    private EmailServiceImpl emailService;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${email.ms.url}")
    String EMAIL_MS_URL;

    private RestTemplate restTemplate = new RestTemplateBuilder().build();

    public Iterable<Student> all() {
        return studentRepository.findAll();
    }

    public PaginationAndData all(int pageNumber, int pageSize, eSortOrder sortOrderField, Boolean isDescending) {
        PageRequest request = PageRequest.of(pageNumber, pageSize,
                isDescending ? Sort.by(sortOrderField.getValue()).descending() : Sort.by(sortOrderField.getValue()).ascending());
        Page<Student> studentPage = studentRepository.findAll(request);
        List<StudentOut> studentOutList = studentPage.getContent().stream()
                .map(student -> StudentOut.of(student, awsService)).collect(Collectors.toList());

        Pagination pagination = Pagination.of(studentPage.getNumber(), studentPage.getTotalPages());

        return PaginationAndData.of(pagination, studentOutList);

    }

    public Optional<Student> findById(Long id) {
        return studentRepository.findById(id);

    }


    public Student save(Student student) {
        return studentRepository.save(student);
    }

    public void delete(Student student) {
        studentRepository.delete(student);
    }

    public List<Student> getStudentWithSatHigherThan(Integer sat) {
        return studentRepository.findAllBySatScoreGreaterThan(sat);
    }

    /**
     * sends email to student with the given id in async manner
     *
     * @param emailDetails the email details
     */
    public void sendEmail(EmailDetails emailDetails) {
        new Thread(() -> {
            emailService.sendSimpleMail(emailDetails);
        }).start();
    }

    /**
     * sends email to all students in async manner
     */
    public void sendEmailToAllStudents(String msg) {
        String htmlResponse = "<htmlResponse><head>" +
                "<link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css\" rel=\"stylesheet\">\n" +
                "<head>" + "<body class=container><h6 class=text-center> %s </h6></body></htmlResponse>";
        new Thread(() -> {
            List<String> emails = IteratorUtils.toList(studentRepository.findAll().iterator())
                    .parallelStream()
                    .map(student -> student.getEmail())
                    .filter(email -> email != null && !email.isEmpty())
                    .collect(Collectors.toList());

            emails.forEach(email -> {
                emailService.sendSimpleMail(EmailDetails.of(email,
                        msg, "Hello from the other side"));
            });

        }).start();
    }


    /**
     * sends email to all students in microservice manner
     * @param msg the message to send
     */
    public void sendEmailToAll(String msg) {
        List<String> emails = IteratorUtils.toList(studentRepository.findAll().iterator())
                .parallelStream()
                .map(student -> student.getEmail())
                .filter(email -> email != null && !email.isEmpty())
                .collect(Collectors.toList());

        logger.info(emails.size() + " emails found");
        MessageAndEmails messageAndEmails = new MessageAndEmails(msg, emails);
        logger.info("sending to " + messageAndEmails);

        try {
            String emailReqAsString = objectMapper.writeValueAsString(messageAndEmails);
            logger.info("sending to " + emailReqAsString);
            String res = restTemplate.postForObject(EMAIL_MS_URL + "/api/email",
                    emailReqAsString, String.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }


    }


}
