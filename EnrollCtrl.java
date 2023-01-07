package domain;

import java.util.List;
import java.util.Map;

import domain.exceptions.EnrollmentRulesViolationException;

public class EnrollCtrl {
    public void enroll(Student s, List<CSE> courses) throws EnrollmentRulesViolationException {
        Map<Term, Map<Course, Double>> transcript = s.getTranscript();
        for (CSE o : courses) {
            for (Map.Entry<Term, Map<Course, Double>> tr : transcript.entrySet()) {
                for (Map.Entry<Course, Double> r : tr.getValue().entrySet()) {
                    if (r.getKey().equals(o.getCourse()) && r.getValue() >= 10) {
                        throw new EnrollmentRulesViolationException(String.format("The student has already passed %s", o.getCourse().getName()));
                    }
                }
            }
            List<Course> prereqs = o.getCourse().getPrerequisites();
            nextPre:
            for (Course pre : prereqs) {
                for (Map.Entry<Term, Map<Course, Double>> tr : transcript.entrySet()) {
                    for (Map.Entry<Course, Double> r : tr.getValue().entrySet()) {
                        if (r.getKey().equals(pre) && r.getValue() >= 10) {
                            continue nextPre;
                        }
                    }
                }
                throw new EnrollmentRulesViolationException(String.format("The student has not passed %s as a prerequisite of %s", pre.getName(), o.getCourse().getName()));
            }
            for (CSE o2 : courses) {
                if (o == o2) {
                    continue;
                }
                if (o.getExamTime().equals(o2.getExamTime())) {
                    throw new EnrollmentRulesViolationException(String.format("%s and %s have an exam time overlap", o.getCourse().getName(), o2.getCourse().getName()));
                }
            }
            for (Student.CourseSection cs : s.getCurrentTerm()) {
                if (cs.getCourse().equals(o.getCourse())) {
                    throw new EnrollmentRulesViolationException(String.format("The student is already enrolled in %s in this term", o.getCourse().getName()));
                }
            }
            s.takeCourse(o.getCourse(), o.getSection());
        }
    }
}