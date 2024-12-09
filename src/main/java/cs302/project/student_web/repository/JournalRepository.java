package cs302.project.student_web.repository;

import cs302.project.student_web.model.JournalModel;
import cs302.project.student_web.model.UserModel;
import jakarta.persistence.EntityManager;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JournalRepository extends JpaRepository <JournalModel, Long>{
    Optional<JournalModel> findByUserId(Long userId);
    Optional<JournalModel> findByTitle(String title);
}
