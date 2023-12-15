package com.malveillance.uberif.model.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class IntersectionServiceTest {

    private PaneService paneService;
    private IntersectionService intersectionService;

    @BeforeEach
    void setUp() {
        // Mock the PaneService
        paneService = mock(PaneService.class);

        // Initialize IntersectionService with the mocked PaneService
        intersectionService = new IntersectionService(paneService);
    }

    @Test
    void testIntersectionServiceConstructor() {
        // Assert that the IntersectionService has been correctly initialized with PaneService
        assertNotNull(intersectionService);
        // You might want to assert that paneService is correctly set in intersectionService
        // However, this typically requires the intersectionService class to have a getter or some
        // observable behavior that reflects the use of paneService
    }
}
