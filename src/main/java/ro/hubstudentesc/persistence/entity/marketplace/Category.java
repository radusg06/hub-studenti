package ro.hubstudentesc.persistence.entity.marketplace;

import jakarta.persistence.*;

@Entity
@Table(name = "categories", schema = "marketplace")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String slug;

    private String iconName;

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public String getSlug() {return slug;}
    public void setSlug(String slug) {this.slug = slug;}

    public String getIconName() {return iconName;}
    public void setIconName(String iconName) {this.iconName = iconName;}
}