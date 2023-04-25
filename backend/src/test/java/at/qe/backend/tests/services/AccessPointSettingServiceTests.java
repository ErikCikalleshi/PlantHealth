package at.qe.backend.tests.services;

import at.qe.backend.exceptions.AccessPoint.AccessPointNotFoundException;
import at.qe.backend.exceptions.AccessPoint.AccessPointNotPublishedException;
import at.qe.backend.api.model.dto.AccessPointSettingDTO;
import at.qe.backend.api.services.SettingService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
public class AccessPointSettingServiceTests {
    @Autowired
    SettingService settingService;

    @Test
    public void getSettingInvalidAP() {
        Assertions.assertThrows(AccessPointNotFoundException.class,()-> settingService.getSetting(123145));
    }

    @Test
    public void getSettingsValidAP() throws AccessPointNotFoundException, AccessPointNotPublishedException {
       Assertions.assertEquals(AccessPointSettingDTO.class, settingService.getSetting(1).getClass());
    }
}
