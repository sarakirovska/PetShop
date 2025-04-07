package com.example.petshopnew.entity;




import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private PetShopUser petShopUser;

    @OneToMany(mappedBy = "cart",fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> cartItems;
}
