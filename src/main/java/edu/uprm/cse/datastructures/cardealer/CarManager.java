package edu.uprm.cse.datastructures.cardealer;

import java.util.ArrayList;
import java.util.Collection;

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
import edu.uprm.cse.datastructures.cardealer.util.CircularSortedDoublyLinkedList;

@Path("/cars")
public class CarManager{
	private static CircularSortedDoublyLinkedList<Car> cList = new CircularSortedDoublyLinkedList<Car>(new CarComparator());

	/**
	 * Gets all the people in the list.
	 * 
	 * @return array - array containing all the people in the list.
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Car[] getAllCars() {
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
		for (int i = 0; i < cList.size(); i++) {
			if (id == cList.get(i).getCarId()) {
				cList.remove(cList.get(i));
				return Response.status(Response.Status.OK).build();
			}
		}
		throw new NotFoundException();
	}


}
