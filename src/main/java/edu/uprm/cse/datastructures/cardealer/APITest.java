package edu.uprm.cse.datastructures.cardealer;
import edu.uprm.cse.datastructures.cardealer.model.Car;
import edu.uprm.cse.datastructures.cardealer.model.CarTable;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class APITest {

    private HttpServer server;
    private WebTarget target;
    private static final String BASE_URI = "http://localhost:8080/cardealer/";

    @Before
    public void setUp() throws Exception {
        // start the server
        server = startServer();
        // create the client
        Client c = ClientBuilder.newClient();

        // uncomment the following line if you want to enable
        // support for JSON in the client (you also have to uncomment
        // dependency on jersey-media-json module in pom.xml and Main.startServer())
        // --
        // c.configuration().enable(new org.glassfish.jersey.media.json.JsonJaxbFeature());

        target = c.target(BASE_URI);
    }

    @After
    public void tearDown() throws Exception {
        server.stop();
    }

    private HttpServer startServer() {
        // create a resource config that scans for JAX-RS resources and providers
        // in edu.uprm.cse.datastructures.cardealer package
        final ResourceConfig rc = new ResourceConfig().packages("edu.uprm.cse.datastructures.cardealer");

        // create and start a new instance of grizzly http server
        // exposing the Jersey application at BASE_URI
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }

    private Car[] getCars() {
        return target.path("cars").request().get(Car[].class);
    }

    private Response updateCar(Car car) {
        return target.path("cars/" + car.getCarId() + "/update").request(MediaType.APPLICATION_JSON).put(Entity.entity(car, MediaType.APPLICATION_JSON));
    }

    private Car getCar(Car car) {
        return target.path("cars/"+ car.getCarId()).request().get(Car.class);
    }

    private Response addCar(Car car1) {
        return target.path("cars/add").request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(car1, MediaType.APPLICATION_JSON));
    }

    private Response getGetCarResponse(Car car) {
        return target.path("cars/" + car.getCarId()).request(MediaType.APPLICATION_JSON)
                .get();
    }

    private Response deleteCar(Car car) {
        return target.path("cars/" + car.getCarId() + "/delete").request(MediaType.APPLICATION_JSON)
                .delete();
    }

    /**
     * Test to see that Server can insert cars in order and return all or one specific car.
     */
    @Test
    public void test1Create1() {
        CarTable.resetCars();
        Car car1 = new Car(1,"Toyota", "RAV4", "LE",15000.50);

        Response response1 = addCar(car1);

        // Check response status code is 201 (Created)
        assertEquals("Response Status not equal", 201, response1.getStatus());
        

        CarTable.resetCars();
    }

    @Test
    public void test1Get(){
        CarTable.resetCars();
        Car car1 = new Car(1,"Toyota", "RAV4", "LE",15000.50);

        addCar(car1);


        Car responseCar1 = getCar(car1);

        // Check responseCar1 is car1
        assertEquals(car1, responseCar1);
        CarTable.resetCars();
    }

    @Test
    public void test1Create2(){
        CarTable.resetCars();
        Car car2 = new Car(2,"Toyota", "RAV4", "SE",10000);

        Response response2 = addCar(car2);

        // Check response status code is 201 (Created)
        assertEquals("Response Status not equal",  201, response2.getStatus());
        CarTable.resetCars();
    }

    @Test
    public void test1Get2(){
        CarTable.resetCars();
        Car car2 = new Car(2,"Toyota", "RAV4", "SE",10000);

        addCar(car2);

        Car responseCar2 = getCar(car2);

        // Check responseCar2 is car2
        assertEquals(car2, responseCar2);
        CarTable.resetCars();
    }

    @Test
    public void test1GetAll(){
        CarTable.resetCars();
        Car car1 = new Car(1,"Toyota", "RAV4", "LE",15000.50);
        Car car2 = new Car(2,"Toyota", "RAV4", "SE",10000);

        addCar(car1);

        addCar(car2);

        Car[] cars = getCars();

        // Check cars are in order
        assertArrayEquals("CarTable not in order", new Car[]{car1, car2}, cars);
        CarTable.resetCars();
    }

    @Test
    public void test1Create3(){
        CarTable.resetCars();
        Car car3 = new Car(3,"Honda", "Civic", "Sedan",20000);

        Response response3 = addCar(car3);

        // Check response status code is 201 (Created)
        assertEquals("Response Status not equal", 201, response3.getStatus());
        CarTable.resetCars();
    }

    @Test
    public void test1Get3(){
        CarTable.resetCars();
        Car car3 = new Car(3,"Honda", "Civic", "Sedan",20000);

        addCar(car3);
        Car responseCar3 = getCar(car3);

        // Check responseCar3 is car3
        assertEquals(car3, responseCar3);
        CarTable.resetCars();
    }

    @Test
    public void test1GetAll2(){
        CarTable.resetCars();

        Car car2 = new Car(2,"Toyota", "RAV4", "SE",10000);
        Car car3 = new Car(3,"Honda", "Civic", "Sedan",20000);
        Car car1 = new Car(1,"Toyota", "RAV4", "LE",15000.50);

        addCar(car1);

        addCar(car2);

        addCar(car3);

        Car[] cars = getCars();

        // Check cars are in order
        assertArrayEquals("CarTable not in order", new Car[]{car3, car1, car2}, cars);
        CarTable.resetCars();

    }

    @Test
    public void test2Create2(){
        CarTable.resetCars();
        Car car1 = new Car(1,"Toyota", "RAV4", "LE",15000.50);

        addCar(car1);

        getCar(car1);

        Car car2 = new Car(2,"Toyota", "RAV4", "SE",10000);

        Response response2 = addCar(car2);

        // Check response status code is 201 (Created)
        assertEquals("Response Status not equal",  201, response2.getStatus());

        CarTable.resetCars();
    }

    @Test
    public void test2Get2(){
        CarTable.resetCars();
        Car car1 = new Car(1,"Toyota", "RAV4", "LE",15000.50);

        addCar(car1);

        getCar(car1);

        Car car2 = new Car(2,"Toyota", "RAV4", "SE",10000);

        addCar(car2);

        Car responseCar2 = getCar(car2);

        Car[] cars = getCars();

        // Check cars are in order
        assertArrayEquals("CarTable not in order", new Car[]{car1, car2}, cars);

        // Check responseCar2 is car2
        assertEquals(car2, responseCar2);

        CarTable.resetCars();
    }

    @Test
    public void test2Update1(){
        CarTable.resetCars();
        Car car1 = new Car(1,"Toyota", "RAV4", "LE",15000.50);

        addCar(car1);

        getCar(car1);

        Car car2 = new Car(2,"Toyota", "RAV4", "SE",10000);

        addCar(car2);


        car1 = new Car(1,"Toyota", "RAV4", "XLE",10000);

        // Updates Car 1
        Response ResponseCar1Update = updateCar(car1);

        assertEquals("Response Status not equal",  200, ResponseCar1Update.getStatus());

        CarTable.resetCars();
    }

    @Test
    public void test2CheckUpdate1(){
        CarTable.resetCars();
        Car car1 = new Car(1,"Toyota", "RAV4", "LE",15000.50);

        addCar(car1);

        getCar(car1);

        Car car2 = new Car(2,"Toyota", "RAV4", "SE",10000);

        addCar(car2);


        car1 = new Car(1,"Toyota", "RAV4", "XLE",10000);

        // Updates Car 1
        updateCar(car1);

        Car responseCar1Updated = getCar(car1);

        // Check responseCar1 is car1
        assertEquals(car1, responseCar1Updated);

        CarTable.resetCars();
    }

    @Test
    public void test2GetAll(){
        CarTable.resetCars();
        Car car1 = new Car(1,"Toyota", "RAV4", "LE",15000.50);

        addCar(car1);

        getCar(car1);

        Car car2 = new Car(2,"Toyota", "RAV4", "SE",10000);

        addCar(car2);


        car1 = new Car(1,"Toyota", "RAV4", "XLE",10000);

        // Updates Car 1
        updateCar(car1);


        Car[] cars = getCars();

        // Check cars are in order
        assertArrayEquals("CarTable not in order", new Car[]{car2, car1}, cars);

        CarTable.resetCars();

    }

    @Test
    public void test2Create3(){
        CarTable.resetCars();
        Car car1 = new Car(1,"Toyota", "RAV4", "LE",15000.50);

        addCar(car1);

        getCar(car1);

        Car car2 = new Car(2,"Toyota", "RAV4", "SE",10000);

        addCar(car2);


        car1 = new Car(1,"Toyota", "RAV4", "XLE",10000);

        // Updates Car 1
        updateCar(car1);

        Car car3 = new Car(3,"Honda", "Civic", "Sedan",20000);

        Response response3 = addCar(car3);

        // Check response status code is 201 (Created)
        assertEquals("Response Status not equal", 201, response3.getStatus());

        CarTable.resetCars();

    }

    @Test
    public void test2Get3(){
        CarTable.resetCars();
        Car car1 = new Car(1,"Toyota", "RAV4", "LE",15000.50);

        addCar(car1);

        getCar(car1);

        Car car2 = new Car(2,"Toyota", "RAV4", "SE",10000);

        addCar(car2);


        car1 = new Car(1,"Toyota", "RAV4", "XLE",10000);

        // Updates Car 1
        updateCar(car1);

        Car car3 = new Car(3,"Honda", "Civic", "Sedan",20000);

        addCar(car3);

        Car responseCar3 = getCar(car3);

        // Check responseCar3 is car3
        assertEquals(car3, responseCar3);

        CarTable.resetCars();
    }

    @Test
    public void test2GetAll2(){
        CarTable.resetCars();
        Car car1 = new Car(1,"Toyota", "RAV4", "LE",15000.50);

        addCar(car1);

        getCar(car1);

        Car car2 = new Car(2,"Toyota", "RAV4", "SE",10000);

        addCar(car2);


        car1 = new Car(1,"Toyota", "RAV4", "XLE",10000);

        // Updates Car 1
        updateCar(car1);

        Car car3 = new Car(3,"Honda", "Civic", "Sedan",20000);

        addCar(car3);

        getCar(car3);

        Car[] cars = getCars();

        // Check cars are in order
        assertArrayEquals("CarTable not in order", new Car[]{car3, car2, car1}, cars);

        CarTable.resetCars();
    }

    @Test
    public void test2Delete1(){
        CarTable.resetCars();
        Car car1 = new Car(1,"Toyota", "RAV4", "LE",15000.50);

        addCar(car1);

        getCar(car1);

        Car car2 = new Car(2,"Toyota", "RAV4", "SE",10000);

        addCar(car2);


        car1 = new Car(1,"Toyota", "RAV4", "XLE",10000);

        // Updates Car 1
        updateCar(car1);

        Car car3 = new Car(3,"Honda", "Civic", "Sedan",20000);

        addCar(car3);

        getCar(car3);

        Response deleteResponse = deleteCar(car3);

        //Check Delete was OK
        assertEquals("Delete Response Status not 200", 200, deleteResponse.getStatus());

        CarTable.resetCars();
    }

    @Test
    public void test2CheckDelete1(){
        CarTable.resetCars();
        Car car1 = new Car(1,"Toyota", "RAV4", "LE",15000.50);

        addCar(car1);

        getCar(car1);

        Car car2 = new Car(2,"Toyota", "RAV4", "SE",10000);

        addCar(car2);


        car1 = new Car(1,"Toyota", "RAV4", "XLE",10000);

        // Updates Car 1
        updateCar(car1);

        Car car3 = new Car(3,"Honda", "Civic", "Sedan",20000);

        addCar(car3);

        getCar(car3);

        deleteCar(car3);

        Response getCar3Response = getGetCarResponse(car3);

        //Make sure Car 3 is not in list
        assertEquals("Get Response Status not 404", 404, getCar3Response.getStatus());

        CarTable.resetCars();
    }

    @Test
    public void test2GetAll3(){
        CarTable.resetCars();
        Car car1 = new Car(1,"Toyota", "RAV4", "LE",15000.50);

        addCar(car1);

        getCar(car1);

        Car car2 = new Car(2,"Toyota", "RAV4", "SE",10000);

        addCar(car2);


        car1 = new Car(1,"Toyota", "RAV4", "XLE",10000);

        // Updates Car 1
        updateCar(car1);

        Car car3 = new Car(3,"Honda", "Civic", "Sedan",20000);

        addCar(car3);

        getCar(car3);

        deleteCar(car3);

        Car[] cars = getCars();

        // Check cars are in order
        assertArrayEquals("CarTable not in order", new Car[]{car2, car1}, cars);

        CarTable.resetCars();
    }

    @Test
    public void test3Delete1(){
        CarTable.resetCars();
        Car car1 = new Car(1,"Toyota", "RAV4", "LE",15000.50);

        addCar(car1);

        Response deleteResponse = deleteCar(car1);

        //Check Delete was OK
        assertEquals("Delete Response Status not 200", 200, deleteResponse.getStatus());
        CarTable.resetCars();
    }

    @Test
    public void test3CheckDelete1(){
        CarTable.resetCars();
        Car car1 = new Car(1,"Toyota", "RAV4", "LE",15000.50);

        addCar(car1);

        deleteCar(car1);

        Response car1Response = getGetCarResponse(car1);

        //Make sure Car 1 is not in list
        assertEquals("Get Response Status not 404", 404, car1Response.getStatus());
        CarTable.resetCars();
    }

    @Test
    public void test3CreateAfterDelete1(){
        CarTable.resetCars();
        Car car1 = new Car(1,"Toyota", "RAV4", "LE",15000.50);

        addCar(car1);

        deleteCar(car1);

        getGetCarResponse(car1);

        Response car1Response = addCar(car1);

        //Check if status code is 201
        assertEquals("Response Status not equal",  201, car1Response.getStatus());
        CarTable.resetCars();
    }

    @Test
    public void test3Create2(){
        CarTable.resetCars();
        Car car1 = new Car(1,"Toyota", "RAV4", "LE",15000.50);

        addCar(car1);

        deleteCar(car1);

        getGetCarResponse(car1);

        addCar(car1);

        getCar(car1);

        Car car2 = new Car(2,"Toyota", "RAV4", "SE",15000.50);

        Response response2 = addCar(car2);

        //Check if status code is 201
        assertEquals("Response Status not equal",  201, response2.getStatus());
        CarTable.resetCars();
    }

    @Test
    public void test3Get2(){
        CarTable.resetCars();
        Car car1 = new Car(1,"Toyota", "RAV4", "LE",15000.50);

        addCar(car1);

        deleteCar(car1);

        getGetCarResponse(car1);

        addCar(car1);

        getCar(car1);

        Car car2 = new Car(2,"Toyota", "RAV4", "SE",15000.50);

        addCar(car2);

        Car responseCar2 = getCar(car2);

        //Check Car is Car2
        assertEquals("Car2 is not equal to received Car2", car2, responseCar2);
        CarTable.resetCars();
    }

    @Test
    public void test3GetAll(){
        CarTable.resetCars();
        Car car1 = new Car(1,"Toyota", "RAV4", "LE",15000.50);

        addCar(car1);

        deleteCar(car1);

        getGetCarResponse(car1);

        addCar(car1);

        getCar(car1);

        Car car2 = new Car(2,"Toyota", "RAV4", "SE",15000.50);

        addCar(car2);

        getCar(car2);

        Car[] cars = getCars();

        //Check if cars are in order
        assertArrayEquals("CarTable not in order", new Car[]{car1, car2}, cars);
        CarTable.resetCars();
    }

    @Test
    public void test3Delete2(){
        CarTable.resetCars();
        Car car1 = new Car(1,"Toyota", "RAV4", "LE",15000.50);

        addCar(car1);

        deleteCar(car1);

        getGetCarResponse(car1);

        addCar(car1);

        getCar(car1);

        Car car2 = new Car(2,"Toyota", "RAV4", "SE",15000.50);

        addCar(car2);

        getCar(car2);

        Response deleteResponse2 = deleteCar(car2);

        //Check Delete was OK
        assertEquals("Delete Response Status not 200", 200, deleteResponse2.getStatus());
        CarTable.resetCars();
    }

    @Test
    public void test3CheckDelete2(){
        CarTable.resetCars();
        Car car1 = new Car(1,"Toyota", "RAV4", "LE",15000.50);

        addCar(car1);

        deleteCar(car1);

        addCar(car1);

        Car car2 = new Car(2,"Toyota", "RAV4", "SE",15000.50);

        addCar(car2);

        deleteCar(car2);

        Response getCar2Response = getGetCarResponse(car2);

        //Make sure Car 2 is not in list
        assertEquals("Get Response Status not 404", 404, getCar2Response.getStatus());
        CarTable.resetCars();
    }

    @Test
    public void test3CreateC2(){
        CarTable.resetCars();
        Car car1 = new Car(1,"Toyota", "RAV4", "LE",15000.50);

        addCar(car1);

        deleteCar(car1);

        addCar(car1);

        Car car2 = new Car(2,"Toyota", "RAV4", "SE",15000.50);

        addCar(car2);

        deleteCar(car2);

        Response getCar2AgainResponse = addCar(car2);

        //Check if status code is 201
        assertEquals("Response Status not equal", 201, getCar2AgainResponse.getStatus());
        CarTable.resetCars();
    }

    @Test
    public void test3GetC2(){
        CarTable.resetCars();
        Car car1 = new Car(1,"Toyota", "RAV4", "LE",15000.50);

        addCar(car1);

        deleteCar(car1);

        addCar(car1);

        Car car2 = new Car(2,"Toyota", "RAV4", "SE",15000.50);

        addCar(car2);

        deleteCar(car2);

        addCar(car2);

        Car responseCar2Again = getCar(car2);

        //Check Car is Car2
        assertEquals("Car2 is not equal to received Car2", car2, responseCar2Again);
        CarTable.resetCars();
    }

    @Test
    public void test3GetAll2(){
        CarTable.resetCars();
        Car car1 = new Car(1,"Toyota", "RAV4", "LE",15000.50);

        addCar(car1);

        deleteCar(car1);

        addCar(car1);

        Car car2 = new Car(2,"Toyota", "RAV4", "SE",15000.50);

        addCar(car2);

        deleteCar(car2);

        addCar(car2);

        Car[] cars = getCars();

        //Check if cars are in order
        assertArrayEquals("CarTable not in order", new Car[]{car1, car2}, cars);

        CarTable.resetCars();
    }



}