package test;

import application.Application;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = Application.class)
@SpringBootTest
public class SpringIntegrationTest {

}