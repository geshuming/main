package seedu.module.model.module;

import java.time.LocalDateTime;

/**
 * Represents semester details pertaining to a module.
 */
public class SemesterDetail {

    private final int semester;
    private final LocalDateTime examDate; // ISO standard datetime format
    private final int examDuration; // duration in minutes

    /**
     * examDate may be null to indicate no exams for this semester.
     */
    public SemesterDetail(int semester, LocalDateTime examDate, int examDuration) {
        this.semester = semester;
        this.examDate = examDate;
        this.examDuration = examDuration;
    }

    public int getDuration() {
        return examDuration;
    }

    public LocalDateTime getStartTime() {
        return examDate;
    }

    public int getSemester() {
        return semester;
    }
}
