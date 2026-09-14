package ro.hubstudentesc.persistence.entity.faculties;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import ro.hubstudentesc.enums.facultiesEnums.DegreeLevel;
import ro.hubstudentesc.persistence.entity.auth.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "ai_user_preferences", schema = "faculties")
public class AiUserPreferences {

    @Id
    private UUID userId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    private BigDecimal budgetLimit;

    @JdbcTypeCode(SqlTypes.ARRAY)
    @Column(nullable = false)
    private String[] preferredCities = new String[0];

    @JdbcTypeCode(SqlTypes.ARRAY)
    @Column(nullable = false)
    private String[] interests = new String[0];

    private BigDecimal highschoolGrade;

    @Enumerated(EnumType.STRING)
    private DegreeLevel preferredDegree;

    @Column(columnDefinition = "jsonb")
    private String cachedAiRanking;

    private LocalDateTime lastGeneratedAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    public UUID getUserId() {return userId;}
    public void setUserId(UUID userId) {this.userId = userId;}

    public User getUser() {return user;}
    public void setUser(User user) {this.user = user;}

    public BigDecimal getBudgetLimit() {return budgetLimit;}
    public void setBudgetLimit(BigDecimal budgetLimit) {this.budgetLimit = budgetLimit;}

    public String[] getPreferredCities() {return preferredCities;}
    public void setPreferredCities(String[] preferredCities) {this.preferredCities = preferredCities;}

    public String[] getInterests() {return interests;}
    public void setInterests(String[] interests) {this.interests = interests;}

    public BigDecimal getHighschoolGrade() {return highschoolGrade;}
    public void setHighschoolGrade(BigDecimal highschoolGrade) {this.highschoolGrade = highschoolGrade;}

    public DegreeLevel getPreferredDegree() {return preferredDegree;}
    public void setPreferredDegree(DegreeLevel preferredDegree) {this.preferredDegree = preferredDegree;}

    public String getCachedAiRanking() {return cachedAiRanking;}
    public void setCachedAiRanking(String cachedAiRanking) {this.cachedAiRanking = cachedAiRanking;}

    public LocalDateTime getLastGeneratedAt() {return lastGeneratedAt;}
    public void setLastGeneratedAt(LocalDateTime lastGeneratedAt) {this.lastGeneratedAt = lastGeneratedAt;}

    public LocalDateTime getUpdatedAt() {return updatedAt;}
    public void setUpdatedAt(LocalDateTime updatedAt) {this.updatedAt = updatedAt;}
}
