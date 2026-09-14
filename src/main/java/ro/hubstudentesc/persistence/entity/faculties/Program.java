package ro.hubstudentesc.persistence.entity.faculties;

import jakarta.persistence.*;
import ro.hubstudentesc.enums.facultiesEnums.DegreeLevel;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "programs", schema = "faculties")
public class Program {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "faculty_id", nullable = false)
    private FacultyNode faculty;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DegreeLevel degreeType;

    @Column(nullable = false)
    private Short durationYears;

    @Column(nullable = false)
    private Integer placesBudget;

    @Column(nullable = false)
    private Integer placesTax;

    private String curriculumPdfUrl;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    public UUID getId() {return id;}
    public void setId(UUID id) {this.id = id;}

    public FacultyNode getFaculty() {return faculty;}
    public void setFaculty(FacultyNode faculty) {this.faculty = faculty;}

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public DegreeLevel getDegreeType() {return degreeType;}
    public void setDegreeType(DegreeLevel degreeType) {this.degreeType = degreeType;}

    public Short getDurationYears() {return durationYears;}
    public void setDurationYears(Short durationYears) {this.durationYears = durationYears;}

    public Integer getPlacesBudget() {return placesBudget;}
    public void setPlacesBudget(Integer placesBudget) {this.placesBudget = placesBudget;}

    public Integer getPlacesTax() {return placesTax;}
    public void setPlacesTax(Integer placesTax) {this.placesTax = placesTax;}

    public String getCurriculumPdfUrl() {return curriculumPdfUrl;}
    public void setCurriculumPdfUrl(String curriculumPdfUrl) {this.curriculumPdfUrl = curriculumPdfUrl;}

    public LocalDateTime getCreatedAt() {return createdAt;}
    public void setCreatedAt(LocalDateTime createdAt) {this.createdAt = createdAt;}
}