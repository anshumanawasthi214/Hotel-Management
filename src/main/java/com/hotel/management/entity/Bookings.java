package com.hotel.management.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name="bookings")
public class Bookings {
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@NotNull(message="checkIn date is required")
	private LocalDate checkInDate;
	@Future(message="checkOut date must be after checkin Date required")
	private LocalDate checkOutDate;
	
	@Min(value=1,message="Number of adults not be less than 1")
	private int numberOfAdults;
	
	@Min(value=0,message="Number of children not be less than 0")
	private int numberOfChildren;
	
	private int totalNumberOfGuest;
	
	private String bookingConfirmationCode;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="user_id")
	private User user;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="room_id")
	private Room room;
	
	
	public void calculateTotalNumberOfGuest(){
		this.totalNumberOfGuest=this.numberOfAdults+this.numberOfChildren;
	}
	
	public void setTotalNumOfAdults(int numOfAdults) {
		this.numberOfAdults=numOfAdults;
		calculateTotalNumberOfGuest();
	}
	
	public void setTotalNumOfChildren(int numOfChildren) {
		this.numberOfChildren=numOfChildren;
		calculateTotalNumberOfGuest();
	}

	@Override
	public String toString() {
		return "Bookings [id=" + id + 
				", checkInDate=" + checkInDate + 
				", checkOutDate=" + checkOutDate
				+ ", numberOfAdults=" + numberOfAdults 
				+ ", numberOfChildren=" + numberOfChildren
				+ ", totalNumberOfGuest=" + totalNumberOfGuest 
				+ ", bookingConfirmationCode=" + bookingConfirmationCode+"]";
	}

}
