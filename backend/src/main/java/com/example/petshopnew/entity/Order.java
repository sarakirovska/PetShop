package com.example.petshopnew.entity;






import com.example.petshopnew.entity.enums.OrderStatus;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private PetShopUser petShopUser;


    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.EAGER)
    private List<OrderItem> orderItems;

    @Column(nullable = false)
    private double totalPrice;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private LocalDateTime createdAt;
}
