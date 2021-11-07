## checkout-system

This is a **Checkout System** prototype for an online electronics store owner.

The current version of this system has a number of features available for the store owner and user (who is adding products to their basket on the store). These features include, and are not limited to, adding/updating/removing/applying discounts and deals on products for the store owner. At the same time, the user can add/update/remove products and their quantity from the basket, while calculating the total price as well.

#### project-specifications:

    - The system was created using Spring Boot 2, JPA (Spring Data), Java 8 and a MySQL DB.
    - All the operations are supported through the RESTful web service.
    - The software development was done using a Test-driven approach.
    - To enhance the API usage and understanding, a Swagger UI is available with the application. (`/swagger-ui.html`)
    - The application runs on http://localhost:8080/

#### usage:

The following table shows the REST API endpoints, explaining the usage of this application along with an explanation. The technical implementation of these endpoints can be seen in the swagger.

Path | Method | Role | Explanation
-----|--------|------|------------
`/createProduct` | `POST` | Store Owner | creates a new product using the product object and saves it to DB
`/product/removeProduct/{id}` | `DELETE` | Store Owner | removes the product from the DB using the product-id
`/products` | `GET` | Store Owner | gets all the products from the DB in the form of the product object
`/updateProduct/{id}` | `PUT` | Store Owner | updates a product using the product-id to change the product details and add a free item/discount deal for it (buy x get y% off on z)
`/basket/createBasket` | `POST` | User | creates a new basket for the user and returns the unique basket-id
`/basket/{basketId}` | `GET` | User | this gives the basket of the user with all the products, free items and costs with deals applied
`/basket/addProduct/{basketId}/{productId}/{quantity}` | `PUT` | User | adds a product to users basket based on their basked-id, the product they wish to add and it's quantity

#### build-and-run:

- Building and checking for correctness:
  ```$xslt
  run the following maven command from the project folder:
  $mvn clean install
  ```
  This command will run through the entire Maven Lifecycle; install all the dependencies, compile the program and run the tests automatically to produce a Build success/failure. Then it will be packaged into a `jar` for execution.
- Running from Spring Boot Maven Plugin:
  ```$xslt
  run the following maven command from the project folder
  $mvn spring-boot:run
  ```
- Running from executable jar file:
  ```$xslt
  run the command from the project folder
  $mvn clean install
  
  run the following java command from the project folder
  $java -jar target/checkoutSystem-0.0.1-SNAPSHOT.jar
  ```

#### future-works:

A few things to be worked on next:
- The current implementation of the user basket is done using a global `HashMap` which is destroyed if the  application is stopped. Instead, the state of the basket can be persisted.
- Adding more features for the store owner/user for a better experience. Ex: Authorizing the user and store owner for their perspective roles.


##### supplementary-information:

- `mvn clean install` snapshot:
    ```$xslt
    [INFO] -------------------------------------------------------
    [INFO]  T E S T S
    [INFO] -------------------------------------------------------
    [INFO] Running com.aman.checkoutsystem.service.BasketServiceTest
    [INFO] Tests run: 8, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 1.196 s - in com.aman.checkoutsystem.service.BasketServiceTest
    [INFO] Running com.aman.checkoutsystem.service.StoreOwnerServiceTest
    [INFO] Tests run: 6, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.001 s - in com.aman.checkoutsystem.service.StoreOwnerServiceTest
    [INFO] Running com.aman.checkoutsystem.domain.basket.BasketTest
    [INFO] Tests run: 8, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.001 s - in com.aman.checkoutsystem.domain.basket.BasketTest
    [INFO] 
    [INFO] Results:
    [INFO] 
    [INFO] Tests run: 22, Failures: 0, Errors: 0, Skipped: 0
    [INFO] 
    [INFO] ------------------------------------------------------------------------
    [INFO] BUILD SUCCESS
    [INFO] ------------------------------------------------------------------------
    [INFO] Total time:  7.575 s
    [INFO] Finished at: 2021-11-07T21:22:52+08:00
    [INFO] ------------------------------------------------------------------------
    ```

- Swagger UI:

  ![Swagger UI](/src/main/resources/static/swagger-ui.PNG)

- Test coverage:
  Covered 78% of the total lines.
  ![Test coverage](/src/main/resources/static/testCoverage.jpeg)
