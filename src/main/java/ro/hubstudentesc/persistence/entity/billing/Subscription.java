package ro.hubstudentesc.persistence.entity.billing;

import jakarta.persistence.*;
import ro.hubstudentesc.enums.billing.SubscriptionTier;
import ro.hubstudentesc.persistence.entity.auth.User;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "subscriptions", schema = "billing")
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SubscriptionTier tier;

    @Column(unique = true)
    private String stripeCustomerId;

    @Column(unique = true)
    private String stripeSubId;

    @Column(nullable = false)
    private boolean isActive;

    private LocalDateTime currentPeriodEnd;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    public UUID getId() {return id;}
    public void setId(UUID id) {this.id = id;}

    public User getUser() {return user;}
    public void setUser(User user) {this.user = user;}

    public SubscriptionTier getTier() {return tier;}
    public void setTier(SubscriptionTier tier) {this.tier = tier;}

    public String getStripeCustomerId() {return stripeCustomerId;}
    public void setStripeCustomerId(String stripeCustomerId) {this.stripeCustomerId = stripeCustomerId;}

    public String getStripeSubId() {return stripeSubId;}
    public void setStripeSubId(String stripeSubId) {this.stripeSubId = stripeSubId;}

    public boolean isActive() {return isActive;}
    public void setActive(boolean active) {isActive = active;}

    public LocalDateTime getCurrentPeriodEnd() {return currentPeriodEnd;}
    public void setCurrentPeriodEnd(LocalDateTime currentPeriodEnd) {this.currentPeriodEnd = currentPeriodEnd;}

    public LocalDateTime getCreatedAt() {return createdAt;}
    public void setCreatedAt(LocalDateTime createdAt) {this.createdAt = createdAt;}

    public LocalDateTime getUpdatedAt() {return updatedAt;}
    public void setUpdatedAt(LocalDateTime updatedAt) {this.updatedAt = updatedAt;}
}