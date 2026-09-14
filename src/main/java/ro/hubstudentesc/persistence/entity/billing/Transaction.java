package ro.hubstudentesc.persistence.entity.billing;

import jakarta.persistence.*;
import ro.hubstudentesc.enums.billing.PaymentPurpose;
import ro.hubstudentesc.enums.billing.PaymentStatus;
import ro.hubstudentesc.persistence.entity.auth.User;
import ro.hubstudentesc.persistence.entity.marketplace.Listing;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "transactions", schema = "billing")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "listing_id")
    private Listing listing;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @Column(nullable = false, length = 3)
    private String currency = "RON";

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentPurpose purpose;

    @Column(unique = true)
    private String paymentGatewayRef;

    private String receiptUrl;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    public UUID getId() {return id;}
    public void setId(UUID id) {this.id = id;}

    public User getUser() {return user;}
    public void setUser(User user) {this.user = user;}

    public Listing getListing() {return listing;}
    public void setListing(Listing listing) {this.listing = listing;}

    public BigDecimal getAmount() {return amount;}
    public void setAmount(BigDecimal amount) {this.amount = amount;}

    public String getCurrency() {return currency;}
    public void setCurrency(String currency) {this.currency = currency;}

    public PaymentStatus getStatus() {return status;}
    public void setStatus(PaymentStatus status) {this.status = status;}

    public PaymentPurpose getPurpose() {return purpose;}
    public void setPurpose(PaymentPurpose purpose) {this.purpose = purpose;}

    public String getPaymentGatewayRef() {return paymentGatewayRef;}
    public void setPaymentGatewayRef(String paymentGatewayRef) {this.paymentGatewayRef = paymentGatewayRef;}

    public String getReceiptUrl() {return receiptUrl;}
    public void setReceiptUrl(String receiptUrl) {this.receiptUrl = receiptUrl;}

    public LocalDateTime getCreatedAt() {return createdAt;}
    public void setCreatedAt(LocalDateTime createdAt) {this.createdAt = createdAt;}

    public LocalDateTime getUpdatedAt() {return updatedAt;}
    public void setUpdatedAt(LocalDateTime updatedAt) {this.updatedAt = updatedAt;}
}