package in.vishal.bankingapp.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "accounts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String accountNumber;

    private Double balance = 0.0;

    private boolean frozen = false;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
