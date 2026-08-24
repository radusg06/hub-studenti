package ro.hubstudentesc.persistence.entity.marketplace;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.NoArgsConstructor;
import ro.hubstudentesc.enums.marketPlaceEnums.ListingCondition;
import ro.hubstudentesc.persistence.entity.auth.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Table(name = "marketplace")
public class MarketPlace {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "seller_id" , nullable = false)
    private User seller;

    @ManyToOne
    @JoinColumn(name = "category_id" , nullable = false)
    private Category category;

    @Column(nullable = false)
    @NotBlank
    private String title;

    @Column(nullable = false)
    @NotBlank
    private String description;

    @Column(nullable = false , precision = 10 , scale = 2)
    @PositiveOrZero
    private BigDecimal price;

    @Column(nullable = false , length = 3)
    private String currency="RON";

    @Column(name = "is_negotiable" , nullable = false)
    private boolean negotiable = false;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull
    private ListingCondition condition = ListingCondition.USED;

    public Long getId(){return id;}
    public void setId(Long id){this.id=id;}

    public User getUser(){return user;}
    public void setUser(User user){this.user=user;}

    public String getTitle(){return title;}
    public void setTitle(String title){this.title=title;}

    public String getDescription(){return description;}
    public void setDescription(String description){this.description=description;}

    public BigDecimal getPrice(){return price;}
    public void setPrice(BigDecimal price){this.price = price;}

    public LocalDateTime getCreatedAt(){return createdAt;}
    public void setCreatedAt(LocalDateTime createdAt){this.createdAt=createdAt;}

}
