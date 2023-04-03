package at.qe.backend.tests;

import at.qe.backend.api.exceptions.AccessPointNotFoundException;
import at.qe.backend.api.exceptions.AccessPointNotPublishedException;
import at.qe.backend.api.model.DTO.AccessPointSettingDTO;
import at.qe.backend.api.services.SettingService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
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
