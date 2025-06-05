package io.github.jotabrc.ov_ims_product.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Set;

@Data
@Accessors(chain = true)
@Builder
@NoArgsConstructor @AllArgsConstructor
@Entity(name = "tb_product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 36, nullable = false, unique = true)
    private String uuid;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(length = 500)
    private String description;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "tb_product_category",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categories;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "is_active")
    private boolean isActive;

    @Version
    private long version;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now(ZoneOffset.UTC);
        updatedAt = LocalDateTime.now(ZoneOffset.UTC);
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now(ZoneOffset.UTC);
    }
}
