package edu.uprm.cse.datastructures.cardealer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import edu.uprm.cse.datastructures.cardealer.model.Car;
import edu.uprm.cse.datastructures.cardealer.model.CarComparator;
import edu.uprm.cse.datastructures.cardealer.util.HashMap;
import edu.uprm.cse.datastructures.cardealer.util.Map;
import edu.uprm.cse.datastructures.cardealer.util.SortedList;

@Path("/cars")
public class CarManager {		
	private static Map<Long, Car> mapList = new HashMap(20);
//	Returns an array of cars based on carList instance.
	@GET
	@Path("")
	@Produces(MediaType.APPLICATION_JSON)
	public Car[] getAllCars() {		
		ArrayList<Car> newCarList = new ArrayList<>(); 
		for(Car car: mapList.getValues()) {
			newCarList.add(car);
		}
		Collections.sort(newCarList, new CarComparator());				
		return  newCarList.toArray(new Car[0]);
	}
	
//	Returns a car with the given id_Key, and throws an exception if the given id_ID is not found or is null.
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Car getCar(@PathParam("id") long index) {
		
		if(mapList.get(index) != null)
			return mapList.get(index);
		
		throw new NotFoundException(Response.status(Response.Status.NOT_FOUND).build());
		
	}
	
//	Adds the given car to the list if the id is unique. 
//Returns response "CREATED" if id is unique and "NOT_ACCEPTABLE" if id is duplicate.
	@POST
	@Path("/add")
	@Produces(MediaType.APPLICATION_JSON)
	public Response addCar(Car car) {		
		if(car != null && mapList.get(car.getCarId()) == null) {			
			mapList.put(car.getCarId(), car);
			return Response.status(Response.Status.CREATED).build();
		}		
		return Response.status(Response.Status.NOT_ACCEPTABLE).build();		
	}
	
//	If the given car is in the list its attributes are modified as given,
//returns "OK" if successful and "NOT_FOUND" if car is not in list.
	@PUT
	@Path("/{id}/update")
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateCar(Car car, @PathParam ("id") long carID) {		
		if(car != null && car.getCarId() == carID && mapList.get(carID) != null) {
			mapList.put(carID, car);
			return Response.status(Response.Status.OK).build();
		}				
		return Response.status(Response.Status.NOT_FOUND).build();				
	}

//	If given car id is in the list it is removed returning "OK" and "NOT_FOUND" if car is not on the list.
	@DELETE
	@Path("/{id}/delete")
	@Produces(MediaType.APPLICATION_JSON)
	public Response removeCar(@PathParam("id") long carID) {
		if(mapList.remove(carID) != null)
			return Response.status(Response.Status.OK).build();			
		return Response.status(Response.Status.NOT_FOUND).build();
	}	
	
}