package com.syelodge.web.web;

import com.syelodge.common.dto.UniversityDto;
import com.syelodge.service.university.UniversityService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/** Public list of institutions that accept SYE Lodge listings. */
@RestController
@RequestMapping("/api/universities")
public class UniversityController {

    private final UniversityService universityService;

    public UniversityController(UniversityService universityService) {
        this.universityService = universityService;
    }

    @GetMapping
    public List<UniversityDto> list() {
        return universityService.listPublic();
    }
}
