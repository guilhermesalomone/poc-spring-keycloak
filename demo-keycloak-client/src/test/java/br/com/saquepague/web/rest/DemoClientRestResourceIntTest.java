package br.com.saquepague.web.rest;

import br.com.saquepague.DemoKeycloakClientApp;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
/**
 * Test class for the DemoClientRest REST controller.
 *
 * @see DemoClientRestResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoKeycloakClientApp.class)
public class DemoClientRestResourceIntTest {

    private MockMvc restMockMvc;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        DemoClientRestResource demoClientRestResource = new DemoClientRestResource();
        restMockMvc = MockMvcBuilders
            .standaloneSetup(demoClientRestResource)
            .build();
    }

    /**
    * Test defaultAction
    */
    @Test
    public void testDefaultAction() throws Exception {
        restMockMvc.perform(get("/api/demo-client-rest/default-action"))
            .andExpect(status().isOk());
    }

}
