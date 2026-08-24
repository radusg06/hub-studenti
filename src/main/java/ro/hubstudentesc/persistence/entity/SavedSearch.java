package ro.hubstudentesc.persistence.entity;


import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import ro.hubstudentesc.persistence.entity.auth.User;

@Entity
@NoArgsConstructor
@Table(name = "saved_searches")
public class SavedSearch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    @ManyToOne
    @JoinColumn(name = "user_id" ,  nullable = false)
    private User user;

    @Column(length = 100)
    private String keyword;

    @Column(length = 100)
    private String city;

    @Column(length = 100)
    private String program;

    public Long getId(){return id;}
    public void setId(Long id){this.id = id;}

    public String getCity(){return city;}
    public void setCity(String city){this.city = city;}

    public String getKeyword(){return keyword;}
    public void setKeyword(String keyword){this.keyword = keyword;}

    public String getProgram(){return program;}
    public void setProgram(String program){this.program = program;}

    public User getUser(){return user;}
    public void setUser(User user){this.user=user;}

}
