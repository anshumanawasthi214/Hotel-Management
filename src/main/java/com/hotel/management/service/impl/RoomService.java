package com.hotel.management.service.impl;

import java.math.BigDecimal;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hotel.management.dto.Response;
import com.hotel.management.dto.RoomDto;
import com.hotel.management.entity.Room;
import com.hotel.management.exception.OurException;
import com.hotel.management.repository.RoomRepository;
import com.hotel.management.service.interfaces.IRoomService;
import com.hotel.management.utils.Utils;


@Service
public class RoomService implements IRoomService {
	
	@Autowired private RoomRepository roomRepo;
	

	@Autowired private AwsS3Service awsService;

	

	

	@Override
	public Response addNewRoom(MultipartFile photo, String roomType, BigDecimal roomPrice, String description) {
		
		Response response=new Response();
		try {
			String imageUrl=awsService.saveImageToS3(photo);
			Room room=new Room();
			room.setRoomType(roomType);
			room.setRoomPhotoUrl(imageUrl);
			room.setRoomDescription(description);
			room.setRoomPrice(roomPrice);
			Room saveRoom=roomRepo.save(room);
			RoomDto roomDto=Utils.mapRoomEntityToRoomDTO(saveRoom);
			response.setStatusCode(200);
			response.setMessage("Success");
			response.setRoom(roomDto);
			
		}catch(Exception e) {
			response.setStatusCode(500);
			response.setMessage("Error occured in saving Room... "+e.getMessage());			

		}
		return response;
	}

	@Override
	public List<String> getAllRoomTypes() {
		return roomRepo.findDistinctRoomTypes();
		
	}

	@Override
	public Response getAllRooms() {
		Response response=new Response();
		try {
			List<Room>roomList=roomRepo.findAll(Sort.by(Sort.Direction.DESC,"id"));
			List<RoomDto>roomDto=Utils.mapRoomListEntityToRoomListDTO(roomList);
			response.setStatusCode(200);
			response.setMessage("Success");
			response.setRoomList(roomDto);
			
		}catch(Exception e) {
			response.setStatusCode(500);
			response.setMessage("Error occured in saving Room... "+e.getMessage());			

		}
		return response;
	}

	@Override
	public Response deleteRoom(Long roomId) {
		Response response=new Response();
		try {
			roomRepo.findById(roomId).orElseThrow(()->new OurException("Room Not Found"));
			roomRepo.deleteById(roomId);
			response.setStatusCode(200);
			response.setMessage("Success");
			
		}catch(OurException e) {
			response.setStatusCode(404);
			response.setMessage(e.getMessage());			

		}
		return response;
	
	}

	@Override
	public Response updateRoom(Long roomId, String roomType, BigDecimal roomPrice, String description,
			MultipartFile photo) {
		Response response=new Response();
		try {
			String imgUrl=null;
			if(photo!=null && !photo.isEmpty()) {
				imgUrl=awsService.saveImageToS3(photo);
			}
			Room room =roomRepo.findById(roomId).orElseThrow(()->new OurException("Room Not Found"));
			
			if(roomType!=null)room.setRoomType(roomType);
			if(roomPrice!=null)room.setRoomPrice(roomPrice);
			if(description!=null)room.setRoomDescription(description);
			if(imgUrl!=null)room.setRoomPhotoUrl(imgUrl);
			
			Room updatedRoom=roomRepo.save(room);
			RoomDto roomDto=Utils.mapRoomEntityToRoomDTO(updatedRoom);
			response.setStatusCode(200);
			response.setMessage("Success");
			response.setRoom(roomDto);
			
		}catch(Exception e) {
			response.setStatusCode(500);
			response.setMessage("Error occured in saving Room... "+e.getMessage());			

		}
		return response;
	}

	@Override
	public Response getRoomById(Long roomId) {
		Response response=new Response();
		try {
			Room room=roomRepo.findById(roomId).orElseThrow(()->new OurException("Room Not Found"));
			RoomDto roomDto=Utils.mapRoomEntityToRoomDTOPlusBookings(room);
			response.setStatusCode(200);
			response.setMessage("Success");
			response.setRoom(roomDto);
			
		}catch(OurException e) {
			response.setStatusCode(404);
			response.setMessage(e.getMessage());			

		}
		return response;
	}

	@Override
	public Response getAvailableRoomsByDateAndType(LocalDate checkInDate, LocalDate checkOutDate, String roomType) {
		Response response=new Response();
		try {
			List<Room> availableRoom=roomRepo.findAvailableRoomsByDatesAndTypes(checkInDate,checkOutDate,roomType);
			List<RoomDto> roomDto=Utils.mapRoomListEntityToRoomListDTO(availableRoom);
			response.setStatusCode(200);
			response.setMessage("Successfull");
			response.setRoomList(roomDto);
			
		}catch(OurException e) {
			response.setStatusCode(404);
			response.setMessage(e.getMessage());			

		}
		return response;
	}

	@Override
	public Response getAvailableRooms() {
		Response response=new Response();
		try {
			List<Room> availableRoom=roomRepo.getAllAvailableRooms();
			List<RoomDto> roomDto=Utils.mapRoomListEntityToRoomListDTO(availableRoom);
			response.setStatusCode(200);
			response.setMessage("Successfull");
			response.setRoomList(roomDto);
			
		}catch(Exception e) {
			response.setStatusCode(500);
			response.setMessage(e.getMessage());			

		}
		return response;
	}

}
