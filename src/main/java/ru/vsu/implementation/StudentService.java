package ru.vsu.implementation;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import ru.vsu.entity.ExamResult;
import ru.vsu.entity.Student;

public class StudentService implements ru.vsu.logic.StudentService {
    public StudentService() {
    }

    @Override
    public List<String> getAdultStudentsLastNameSorted(Collection<Student> students) {
        return students.stream()
                .filter((student) -> student.getAge() >= 18)
                .map(Student::getLastName)
                .sorted()
                .collect(Collectors.toList());
    }

    @Override
    public Set<Student> getExcellentStudents(Collection<Student> students) {
        return students.stream()
                .filter((student) -> student.getExamResults().stream()
                                    .map(ExamResult::getMark)
                                    .allMatch((num) -> num == 5))
                .collect(Collectors.toSet());
    }

    @Override
    public Double getAverageMark(Collection<Student> students) {
        return students.stream()
                .flatMapToInt((student) -> student.getExamResults().stream()
                                            .mapToInt(ExamResult::getMark))
                .average().orElse(0.0);
    }

    @Override
    public Student findYoungestStudent(Collection<Student> students) {
        return students.stream()
                .min(Comparator.comparing(Student::getAge)).orElse(null);
    }
}
