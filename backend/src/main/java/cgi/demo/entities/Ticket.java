package cgi.demo.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Screening screening;

    private Date purchaseDate;
    @Column(nullable = false)
    private int seat;
    @Column(nullable = false)
    private int row;

    @ManyToOne
    private User user;
}
