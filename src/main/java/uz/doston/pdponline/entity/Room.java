package uz.doston.pdponline.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private int number;
    private int floor;
    private double size;

    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

}
