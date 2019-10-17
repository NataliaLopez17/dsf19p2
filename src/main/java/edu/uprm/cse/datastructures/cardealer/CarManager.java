package edu.uprm.cse.datastructures.cardealer;

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
import edu.uprm.cse.datastructures.cardealer.model.CarList;
import edu.uprm.cse.datastructures.cardealer.util.SortedList;

@Path("/cars")
public class CarManager{
	

	/**
	 * Gets all the people in the list.
	 * 
	 * @return array - array containing all the people in the list.
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Car[] getAllCars() {
		SortedList<Car> cList = CarList.getInstance();
		Car[] array = new Car[cList.size()];
		for(int i = 0; i < cList.size(); i++) {
			array[i] = cList.get(i);
		}
		return array;
		
	}

	/**
	 * Gets the person in the id given.
	 * 
	 * @param id - id of person.
	 * @return person - if not found returns NotFoundException, else returns person.
	 */
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Car getPerson(@PathParam("id") long id) {
		SortedList<Car> cList = CarList.getInstance();
		for (int i = 0; i < cList.size(); i++) {
			if (cList.get(i).getCarId() == id)
				return cList.get(i);
		}
		throw new NotFoundException();
	}

	/**
	 * Adds the person to the list.
	 * 
	 * @param person - person to be added.
	 * 
	 * @return if person could be added.
	 */
	@POST
	@Path("/add")
	@Produces(MediaType.APPLICATION_JSON)
	public Response addCar(Car car) {
		SortedList<Car> cList = CarList.getInstance();
		cList.add(car);
		return Response.status(201).build();
	}

	/**
	 * Updates the person of given id..
	 * 
	 * @param person - person with the updated information.
	 * 
	 * @return if person could be updated successfully, or if not found.
	 */
	@PUT
	@Path("{id}/update")
	@Produces(MediaType.APPLICATION_JSON)
	public Response updatePerson(Car car) {
		SortedList<Car> cList = CarList.getInstance();
		for (int i = 0; i < cList.size(); i++) {
			if (car.getCarId() == cList.get(i).getCarId()) {
				cList.remove(i);
				cList.add(car);
				return Response.status(Response.Status.OK).build();
			}
		}
		return Response.status(Response.Status.NOT_FOUND).build();
	}

	/**
	 * Deletes the person of given id.
	 * 
	 * @param id - id of person to be deleted.
	 * 
	 * @return if person could be deleted successfully, or if not found.
	 */
	@DELETE
	@Path("{id}/delete")
	public Response deletePerson(@PathParam("id") long id) {
		SortedList<Car> cList = CarList.getInstance();
		for (int i = 0; i < cList.size(); i++) {
			if (id == cList.get(i).getCarId()) {
				cList.remove(cList.get(i));
				return Response.status(Response.Status.OK).build();
			}
		}
		return Response.status(Response.Status.NOT_FOUND).build();
	}


}
