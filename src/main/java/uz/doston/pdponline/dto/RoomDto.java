package uz.doston.pdponline.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RoomDto {
    @JsonProperty("hotel_id")
    private Integer HotelId;
    private int number;
    private int floor;
    private double size;
}
