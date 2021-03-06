package com.dailyhome.back.order.domain;

import com.dailyhome.back.user.domain.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Table(name = "orders") // ORDER은 SQL예약어. 다른 이름으로 설정.
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User orderer;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    private List<OrderItem> orderItems;

    @CreatedDate
    private LocalDateTime orderedAt;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Builder
    public Order(
            Long id,
            User orderer,
            List<OrderItem> orderItems,
            LocalDateTime orderedAt,
            OrderStatus status
    ) {
        this.id = id;
        this.orderer = orderer;
        this.orderItems = orderItems;
        this.orderedAt = orderedAt;
        this.status = status;
    }
}
