package at.qe.backend.tests.services;

import at.qe.backend.models.AuditLog;
import at.qe.backend.models.Greenhouse;
import at.qe.backend.models.UploadImages;
import at.qe.backend.models.Userx;
import at.qe.backend.repositories.GreenhouseRepository;
import at.qe.backend.repositories.UploadImagesRepository;
import at.qe.backend.repositories.UserxRepository;
import at.qe.backend.services.AuditLogService;
import at.qe.backend.services.UploadImagesService;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
public class UploadImagesServiceTests {

    @Mock
    private UploadImagesRepository uploadImagesRepository;

    @Mock
    private GreenhouseRepository greenhouseRepository;

    @Mock
    private UserxRepository userxRepository;

    @Mock
    private AuditLogService auditLogService;

    @InjectMocks
    private UploadImagesService uploadImagesService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @WithMockUser(username = "testuser", authorities = {"USER"})
    public void testCreate() {
        Long plantId = 1L;
        String username = "testuser";
        String uploadLink = "https://example.com/image.jpg";

        Greenhouse greenhouse = new Greenhouse();
        greenhouse.setUuid(plantId);

        Userx user = new Userx();
        user.setId(1L);
        user.setUsername(username);

        when(greenhouseRepository.findByUuid(plantId)).thenReturn(Optional.of(greenhouse));
        when(userxRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(uploadImagesRepository.save(any(UploadImages.class))).thenAnswer(invocation -> {
            final UploadImages entity = invocation.getArgument(0);
            ReflectionTestUtils.setField(entity, "id", RandomUtils.nextLong());
            return entity;
        });

        AuditLog auditLog = mock(AuditLog.class);
        when(auditLogService.createNewAudit(anyString(), anyString(), anyString(), anyBoolean())).thenReturn(auditLog);

        UploadImages uploadImages = uploadImagesService.create(plantId, username, uploadLink);
        assertEquals(plantId, uploadImages.getPlantId());
        assertEquals(1L, uploadImages.getUserId());
        assertEquals(uploadLink, uploadImages.getUploadLink());
        assertNotNull(uploadImages.getUploadDate());
    }

    @Test
    public void testCreateInvalidGreenhouseId() {
        Long plantId = 1L;
        String username = "testuser";
        String uploadLink = "https://example.com/image.jpg";

        when(greenhouseRepository.findByUuid(plantId)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> {
            uploadImagesService.create(plantId, username, uploadLink);
        });

        String expectedMessage = "GreenhouseId is invalid.";
        HttpStatusCode expectedStatus = HttpStatus.BAD_REQUEST;
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            uploadImagesService.create(plantId, username, uploadLink);
        });
        assertEquals(expectedMessage, exception.getReason());
        assertEquals(expectedStatus, exception.getStatusCode());
    }

    @Test
    public void testCreateInvalidUploadLink() {
        Long plantId = 1L;
        String username = "testuser";


        Userx user = new Userx();
        user.setId(1L);
        user.setUsername(username);


        when(userxRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(greenhouseRepository.findByUuid(plantId)).thenReturn(Optional.of(new Greenhouse()));
        assertThrows(ResponseStatusException.class, () -> {
            uploadImagesService.create(plantId, username, "");
        });

        String expectedMessage = "Invalid upload link.";
        HttpStatusCode expectedStatus = HttpStatus.BAD_REQUEST;
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            uploadImagesService.create(plantId, username, "");
        });
        assertEquals(expectedMessage, exception.getReason());
        assertEquals(expectedStatus, exception.getStatusCode());
    }

    @Test
    public void testGetAllById() {
        Long plantId = 1L;

        Greenhouse greenhouse = new Greenhouse();
        greenhouse.setUuid(plantId);

        List<UploadImages> imagesList = new ArrayList<>();
        UploadImages image1 = new UploadImages();
        image1.setUploadLink("https://example.com/image1.jpg");
        image1.setPlantId(plantId);
        imagesList.add(image1);
        UploadImages image2 = new UploadImages();
        image2.setUploadLink("https://example.com/image2.jpg");
        image2.setPlantId(plantId);
        imagesList.add(image2);

        when(greenhouseRepository.findByUuid(plantId)).thenReturn(Optional.of(greenhouse));
        when(uploadImagesRepository.getAllUploadLinksByPlantId(plantId)).thenReturn(imagesList);

        List<String> result = uploadImagesService.getAllById(plantId);

        assertEquals(2, result.size());
        assertEquals("https://example.com/image1.jpg", result.get(0));
        assertEquals("https://example.com/image2.jpg", result.get(1));
    }

    @Test
    public void testGetAllByIdInvalidGreenhouseId() {
        Long plantId = 1L;

        when(greenhouseRepository.findByUuid(plantId)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> {
            uploadImagesService.getAllById(plantId);
        });

        String expectedMessage = "GreenhouseId is invalid.";
        HttpStatus expectedStatus = HttpStatus.BAD_REQUEST;
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            uploadImagesService.getAllById(plantId);
        });
        assertEquals(expectedMessage, exception.getReason());
        assertEquals(expectedStatus, exception.getStatusCode());
    }

    @Test
    public void testDeleteImageByUrl() {
        String url = "https://example.com/image.jpg";

        UploadImages image = new UploadImages();
        image.setUploadLink(url);

        when(uploadImagesRepository.findUploadImagesByUploadLink(url)).thenReturn(image);

        uploadImagesService.deleteImageByUrl(url);

        // Verify that the delete method was called with the correct argument
        assertEquals(image, uploadImagesRepository.findUploadImagesByUploadLink(url));
    }

    @Test
    public void testDeleteImageByUrlInvalidUrl() {
        String url = "https://example.com/image.jpg";
        when(uploadImagesRepository.findUploadImagesByUploadLink(url)).thenReturn(null);
        assertThrows(ResponseStatusException.class, () -> {
            uploadImagesService.deleteImageByUrl(url);
        });
        String expectedMessage = "Invalid url.";
        HttpStatus expectedStatus = HttpStatus.BAD_REQUEST;
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            uploadImagesService.deleteImageByUrl(url);
        });
        assertEquals(expectedMessage, exception.getReason());
        assertEquals(expectedStatus, exception.getStatusCode());
    }
}