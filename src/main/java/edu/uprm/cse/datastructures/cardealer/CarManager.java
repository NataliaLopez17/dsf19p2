package edu.uprm.cse.datastructures.cardealer;

import java.util.ArrayList;
import java.util.Collections;

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
import edu.uprm.cse.datastructures.cardealer.model.CarList;
import edu.uprm.cse.datastructures.cardealer.util.HashMap;
import edu.uprm.cse.datastructures.cardealer.util.Map;
import edu.uprm.cse.datastructures.cardealer.util.SortedList;

@Path("/cars")
public class CarManager{
	/*
	 * Returns an array of cars based on carList instance.
	 */
	private static Map<Long, Car> carMapList = new HashMap<>(20);

	/**
	 * Gets all the cars in the list.
	 * 
	 * @return array - array containing all the cars in the list.
	 */
	@GET
	@Path("")
	@Produces(MediaType.APPLICATION_JSON)
	public Car[] getAllCars() {		
		ArrayList<Car> newCarList = new ArrayList<>(); 
		for(Car car: carMapList.getValues()) {
			newCarList.add(car);
		}
		Collections.sort(newCarList, new CarComparator());				
		return  newCarList.toArray(new Car[0]);
	}

	/**
	 * Gets the car in the id given.
	 * 
	 * @param id - id of car.
	 * @return car - if not found returns NotFoundException, else returns the car.
	 */
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Car getCar(@PathParam("id") long index) {
		
		if(carMapList.get(index) != null)
			return carMapList.get(index);
		
		throw new NotFoundException(Response.status(Response.Status.NOT_FOUND).build());
		
	}

	/**
	 * Adds the car to the list.
	 * 
	 * @param car - car to be added.
	 * 
	 * @return status 201 if car could be added.
	 */
	@POST
	@Path("/add")
	@Produces(MediaType.APPLICATION_JSON)
	public Response addCar(Car car) {		
		if(car != null && carMapList.get(car.getCarId()) == null) {			
			carMapList.put(car.getCarId(), car);
			return Response.status(Response.Status.CREATED).build();
		}		
		return Response.status(Response.Status.NOT_ACCEPTABLE).build();		
	}

	/**
	 * Updates the car of given id.
	 * 
	 * @param car - car with the updated information.
	 * 
	 * @return status OK if car could be updated successfully, not found if not.
	 */
	@PUT
	@Path("/{id}/update")
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateCar(Car car, @PathParam ("id") long carID) {		
		if(car != null && car.getCarId() == carID && carMapList.get(carID) != null) {
			carMapList.put(carID, car);
			return Response.status(Response.Status.OK).build();
		}				
		return Response.status(Response.Status.NOT_FOUND).build();				
	}

	/**
	 * Deletes the car of given id.
	 * 
	 * @param id - id of person to be deleted.
	 * 
	 * @return status OK if car could be deleted successfully, not found if not.
	 */
	@DELETE
	@Path("/{id}/delete")
	@Produces(MediaType.APPLICATION_JSON)
	public Response removeCar(@PathParam("id") long carID) {
		if(carMapList.remove(carID) != null)
			return Response.status(Response.Status.OK).build();			
		return Response.status(Response.Status.NOT_FOUND).build();
	}


}
