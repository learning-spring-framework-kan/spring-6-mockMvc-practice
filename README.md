# spring-6-mockMvc-practice

- Added a domain Object, Customer with:
  - private fields.
  - @Data
  - @Builder
- Create CustomerService
  - added a constructor, created 3 test customers' data and have it as Map.
  - implemented CRUDs using customerMap object.
- Created CustomerController with:
  - @RestController
  - created 4 CRUDs, request mappers.

Tested Using Postman. ALl 4 endpoints works as expected.

Now: Testing the Controller...
- Using @WebMvcTest // 
  - This is used on TestClass, it does bring in slice of SpringContext 
  - ie only controllers (weblayer) that too without dependencies.
- we can use it with Controller class as: @WebMvcTest(CustomerController.class)
  - This bring in context only specified controller.
- Now @AutoWire the MovkMVC.


The `@WebMvcTest` annotation is a specialized testing annotation provided by the Spring Framework for testing Spring MVC controllers in a Spring Boot application. It's typically used for unit testing the web layer of your application, which includes controllers, request mapping, view resolution, and exception handling. Here's what `@WebMvcTest` does:

1. **Controller Isolation:** `@WebMvcTest` focuses on testing your Spring MVC controllers in isolation. It loads only the web layer components of your application while excluding other layers like the service and repository layers. This allows you to perform focused tests on your controllers without the need to load the entire application context.

2. **Minimal Configuration:** It configures a minimal Spring application context that includes only the necessary components for handling web requests and responses, such as controllers, view resolvers, and exception handlers. This helps reduce test execution time and keeps your tests specific to the web layer.

3. **Auto-configuration:** `@WebMvcTest` leverages Spring Boot's auto-configuration to set up a testing environment. It automatically configures the MockMvc framework, which is a powerful tool for simulating HTTP requests and verifying the behavior of your controllers.

4. **Limited Scope:** When you use `@WebMvcTest`, you need to specify which controllers to test by providing them as arguments to the annotation. For example, you can specify the controllers to be tested like this: `@WebMvcTest(YourController.class)`.

5. **Support for Custom Filters:** You can include custom filters, interceptors, and exception resolvers in your `@WebMvcTest` setup if your controllers rely on them.

Here's an example of how to use `@WebMvcTest` in a test class:

```java
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(YourController.class)
public class YourControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testControllerMethod() {
        // Use mockMvc to simulate HTTP requests and make assertions
    }
}
```

In this example, `@WebMvcTest(YourController.class)` tells Spring to load a minimal application context that includes the `YourController` class. You can then use `MockMvc` to perform HTTP request simulations and make assertions about the behavior of the controller.

Overall, `@WebMvcTest` is a valuable tool for testing your Spring MVC controllers in isolation, providing a focused and efficient way to verify the behavior of your web layer without the need to load the entire application context.



--
Use ArgumentCaptors as;

inject:
  @Captor
  ArgumentCaptor<UUID> uuidCaptor;

The `verify` method from Mockito ensures that a method has been called before by allowing you to specify which method on a mocked object you want to verify and how many times it should have been called. In the code you provided, this line is where verification is happening:

```java
verify(customerService).getCustomerById(uuidCaptor.capture());
```

Here's how `verify` works:

1. `verify(customerService)` specifies that you want to verify interactions with the `customerService` mock object.

2. `.getCustomerById(uuidCaptor.capture())` indicates that you're specifically interested in verifying the `getCustomerById` method of the `customerService` mock. Additionally, you're capturing the argument passed to this method using `uuidCaptor`.

3. The `uuidCaptor.capture()` part is where you capture the argument passed to the `getCustomerById` method. It uses an `ArgumentCaptor` (`uuidCaptor`) to capture the `UUID` argument passed to the method during the test execution.

4. The `verify` method verifies that the `getCustomerById` method was called as expected. By default, if you don't specify the number of times, it assumes the method should have been called once.

So, when you use `verify` in this manner, you're essentially checking that the `getCustomerById` method of the `customerService` mock object was called at least once with the argument captured by `uuidCaptor`. If it was called the expected number of times with the correct argument, the test passes. If not, the test fails. This is how Mockito ensures that a method has been called before in your test code.