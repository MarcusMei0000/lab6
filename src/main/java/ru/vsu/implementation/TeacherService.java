package ru.vsu.implementation;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import ru.vsu.entity.Student;
import ru.vsu.entity.Subject;
import ru.vsu.entity.Teacher;

public class TeacherService implements ru.vsu.logic.TeacherService {
    public TeacherService() {
    }

    @Override
    public List<String> getSingleSubjectLecturerFio(Collection<Teacher> teachers) {
        return (List)teachers.stream()
                .filter((teacher) -> teacher.getSubjects().stream()
                        .count() == 1)
                .map(Teacher::getFullName)
                .collect(Collectors.toList());
    }

    @Override
    public Map<String, List<Student>> getTeacherNameToSupervisedStudentsMap(Collection<Student> students) {
        return (Map)students.stream()
                .filter((student) -> student.getSupervisor() != null)
                .collect(Collectors.groupingBy((student) -> student.getSupervisor().getFullName(), Collectors.toList()));
    }

    @Override
    public BigDecimal getTeachersSalarySum(Collection<Teacher> teachers) {
        return (BigDecimal)teachers.stream()
                .map(Teacher::getSalary)
                .reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
    }

    @Override
    public String findTeacherBySubject(Collection<Teacher> teachers, Subject subject) {
        return (String)teachers.stream()
                .filter((teacher) -> teacher.getSubjects().contains(subject))
                .map(Teacher::getFullName)
                .findAny().orElse(null);
    }
}
