package ro.hubstudentesc.persistence.entity.marketplace;

import jakarta.persistence.*;
import ro.hubstudentesc.enums.marketPlaceEnums.ListingCondition;
import ro.hubstudentesc.enums.marketPlaceEnums.ListingStatus;
import ro.hubstudentesc.persistence.entity.auth.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "listings", schema = "marketplace")
public class Listing {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "seller_id", nullable = false)
    private User seller;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(nullable = false, length = 3)
    private String currency = "RON";

    @Column(nullable = false)
    private boolean isNegotiable;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ListingCondition condition;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ListingStatus status;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private Integer viewsCount = 0;

    @Column(nullable = false)
    private boolean isPromoted;

    private LocalDateTime promotedUntil;

    private String materialPdfUrl;

    @Column(nullable = false)
    private boolean requiresPayment;

    @Column(nullable = false)
    private String contactPhone;

    @Column(nullable = false)
    private String contactEmail;

    @Column(nullable = false)
    private boolean contactWhatsapp;

    private String preferredContactInterval;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    public UUID getId() {return id;}
    public void setId(UUID id) {this.id = id;}

    public User getSeller() {return seller;}
    public void setSeller(User seller) {this.seller = seller;}

    public Category getCategory() {return category;}
    public void setCategory(Category category) {this.category = category;}

    public String getTitle() {return title;}
    public void setTitle(String title) {this.title = title;}

    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}

    public BigDecimal getPrice() {return price;}
    public void setPrice(BigDecimal price) {this.price = price;}

    public String getCurrency() {return currency;}
    public void setCurrency(String currency) {this.currency = currency;}

    public boolean isNegotiable() {return isNegotiable;}
    public void setNegotiable(boolean negotiable) {isNegotiable = negotiable;}

    public ListingCondition getCondition() {return condition;}
    public void setCondition(ListingCondition condition) {this.condition = condition;}

    public ListingStatus getStatus() {return status;}
    public void setStatus(ListingStatus status) {this.status = status;}

    public String getLocation() {return location;}
    public void setLocation(String location) {this.location = location;}

    public Integer getViewsCount() {return viewsCount;}
    public void setViewsCount(Integer viewsCount) {this.viewsCount = viewsCount;}

    public boolean isPromoted() {return isPromoted;}
    public void setPromoted(boolean promoted) {isPromoted = promoted;}

    public LocalDateTime getPromotedUntil() {return promotedUntil;}
    public void setPromotedUntil(LocalDateTime promotedUntil) {this.promotedUntil = promotedUntil;}

    public String getMaterialPdfUrl() {return materialPdfUrl;}
    public void setMaterialPdfUrl(String materialPdfUrl) {this.materialPdfUrl = materialPdfUrl;}

    public boolean isRequiresPayment() {return requiresPayment;}
    public void setRequiresPayment(boolean requiresPayment) {this.requiresPayment = requiresPayment;}

    public String getContactPhone() {return contactPhone;}
    public void setContactPhone(String contactPhone) {this.contactPhone = contactPhone;}

    public String getContactEmail() {return contactEmail;}
    public void setContactEmail(String contactEmail) {this.contactEmail = contactEmail;}

    public boolean isContactWhatsapp() {return contactWhatsapp;}
    public void setContactWhatsapp(boolean contactWhatsapp) {this.contactWhatsapp = contactWhatsapp;}

    public String getPreferredContactInterval() {return preferredContactInterval;}
    public void setPreferredContactInterval(String preferredContactInterval) {this.preferredContactInterval = preferredContactInterval;}

    public LocalDateTime getCreatedAt() {return createdAt;}
    public void setCreatedAt(LocalDateTime createdAt) {this.createdAt = createdAt;}

    public LocalDateTime getUpdatedAt() {return updatedAt;}
    public void setUpdatedAt(LocalDateTime updatedAt) {this.updatedAt = updatedAt;}
}