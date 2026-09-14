package ro.hubstudentesc.persistence.entity.marketplace;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "listing_images", schema = "marketplace")
public class ListingImage {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "listing_id" , nullable = false)
    private Listing listing;

    @Column(nullable = false)
    private String imageUrl;

    @Column(nullable = false)
    private Short displayOrder;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    public UUID getId() {return id;}
    public void setId(UUID id) {this.id = id;}

    public Listing getListing() {return listing;}
    public void setListing(Listing listing) {this.listing = listing;}

    public String getImageUrl() {return imageUrl;}
    public void setImageUrl(String imageUrl) {this.imageUrl = imageUrl;}

    public Short getDisplayOrder() {return displayOrder;}
    public void setDisplayOrder(Short displayOrder) {this.displayOrder = displayOrder;}

    public LocalDateTime getCreatedAt() {return createdAt;}
    public void setCreatedAt(LocalDateTime createdAt) {this.createdAt = createdAt;}
}
