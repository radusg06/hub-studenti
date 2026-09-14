package ro.hubstudentesc.persistence.entity.faculties;

import jakarta.persistence.*;
import ro.hubstudentesc.persistence.entity.auth.User;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "reviews", schema = "faculties",
        uniqueConstraints = @UniqueConstraint(columnNames = {"faculty_id", "author_id"})
)
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "faculty_id", nullable = false)
    private FacultyNode faculty;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    @Column(nullable = false)
    private Short ratingGeneral;

    private Short ratingTeachers;

    private Short ratingFacilities;

    private Short ratingOpportunities;

    @Column(nullable = false)
    private String comment;

    @Column(nullable = false)
    private boolean isVerifiedStudent;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    public UUID getId() {return id;}
    public void setId(UUID id) {this.id = id;}

    public FacultyNode getFaculty() {return faculty;}
    public void setFaculty(FacultyNode faculty) {this.faculty = faculty;}

    public User getAuthor() {return author;}
    public void setAuthor(User author) {this.author = author;}

    public Short getRatingGeneral() {return ratingGeneral;}
    public void setRatingGeneral(Short ratingGeneral) {this.ratingGeneral = ratingGeneral;}

    public Short getRatingTeachers() {return ratingTeachers;}
    public void setRatingTeachers(Short ratingTeachers) {this.ratingTeachers = ratingTeachers;}

    public Short getRatingFacilities() {return ratingFacilities;}
    public void setRatingFacilities(Short ratingFacilities) {this.ratingFacilities = ratingFacilities;}

    public Short getRatingOpportunities() {return ratingOpportunities;}
    public void setRatingOpportunities(Short ratingOpportunities) {this.ratingOpportunities = ratingOpportunities;}

    public String getComment() {return comment;}
    public void setComment(String comment) {this.comment = comment;}

    public boolean isVerifiedStudent() {return isVerifiedStudent;}
    public void setVerifiedStudent(boolean verifiedStudent) {isVerifiedStudent = verifiedStudent;}

    public LocalDateTime getCreatedAt() {return createdAt;}
    public void setCreatedAt(LocalDateTime createdAt) {this.createdAt = createdAt;}
}