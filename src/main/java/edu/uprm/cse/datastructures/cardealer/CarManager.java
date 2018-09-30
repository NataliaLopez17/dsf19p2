package edu.uprm.cse.datastructures.cardealer;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import edu.uprm.cse.datastructures.cardealer.util.SortedCircularDoublyLinkedList;
import edu.uprm.cse.datastructures.cardealer.util.SortedList;
import edu.uprm.cse.datastructures.cardealer.model.Car;
import edu.uprm.cse.datastructures.cardealer.model.CarComparator;
import edu.uprm.cse.datastructures.cardealer.model.CarList;

@Path("/cars")
public class CarManager {	
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Car[] getAllCar() {
    	SortedList<Car> L = CarList.getInstance().getCarList();
    	if (L.size() == 0) {
    		return new Car[1];
    	}
    	else {
    		Car[] result = new Car[L.size()];
    		for (int i=0; i < L.size(); ++i) {
    			result[i] = L.get(i);
    		}
    		return result;
    	}
    }
    
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Car getCarById(@PathParam("id") long id) {
    	SortedList<Car> L = CarList.getInstance().getCarList();
    	Car c;
    	for (int i=0; i < L.size(); ++i) {
    		c = L.get(i);
    		if (c.getCarId() == id) {
    			return c;
    		}
    	}
    	throw new WebApplicationException(404);
    }
    
    @POST
    @Path("/add")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addCar(Car newCar) {
    	SortedList<Car> L = CarList.getInstance().getCarList();
    	L.add(newCar);
    	return Response.status(201).build();
    }

    @PUT
    @Path("/{id}/update")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCar(Car updCar) {
    	SortedList<Car> L = CarList.getInstance().getCarList();
    	for (int i=0; i < L.size(); ++i) {
    		if (L.get(i).getCarId() == updCar.getCarId()) {
    			L.remove(i);
    			L.add(updCar);
    			return Response.status(Response.Status.OK).build();
    		}
    	}
    	return Response.status(Response.Status.NOT_FOUND).build();
    }

    @DELETE
    @Path("/{id}/delete")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteCar(@PathParam("id") long id) {
    	SortedList<Car> L = CarList.getInstance().getCarList();
    	for (int i=0; i < L.size(); ++i) {
    		if (L.get(i).getCarId() == id) {
    			L.remove(i);
    			return Response.status(Response.Status.OK).build();
    		}
    	}
    	return Response.status(Response.Status.NOT_FOUND).build();
    }

}
