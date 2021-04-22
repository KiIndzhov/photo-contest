package com.telerikfinalproject.photocontest.controllers.RESTControllers;

import com.telerikfinalproject.photocontest.models.Contest;
import com.telerikfinalproject.photocontest.models.User;
import com.telerikfinalproject.photocontest.models.dtomodels.ContestCreateDto;
import com.telerikfinalproject.photocontest.models.dtomodels.ContestOutputDto;
import com.telerikfinalproject.photocontest.services.ScheduledService;
import com.telerikfinalproject.photocontest.services.contracts.ContestService;
import com.telerikfinalproject.photocontest.services.mappers.ContestModelMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

import static com.telerikfinalproject.photocontest.controllers.utils.Constants.*;

@Api(tags = {"contests"})
@RestController
@RequestMapping("/api/contest")
public class ContestController {

    private final ContestService contestService;
    private final ContestModelMapper contestModelMapper;

    @Autowired
    public ContestController(ContestService contestService, ContestModelMapper contestModelMapper) {
        this.contestService = contestService;
        this.contestModelMapper = contestModelMapper;

    }

    @ApiOperation(value = CREATE_CONTEST, response = Contest.class)
    @PostMapping(consumes = {"multipart/form-data", "application/json"})
    public void createContest(@RequestPart("contestCreateDto") ContestCreateDto contestCreateDto,
                              @RequestParam("image") MultipartFile image) {

        contestCreateDto.setCoverPhoto(image);
        Contest contest = contestModelMapper.dtoToContest(contestCreateDto);
        contestService.create(contest);
    }

    @ApiOperation(value = COVER_PHOTO_CONTEST, response = Resource.class)
    @GetMapping(value = "/{id}/coverPhoto", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public Resource getCoverPhotoByContestId(@PathVariable int id) {
        return contestService.getCoverPhotoByContestId(id);

    }

    @ApiOperation(value = CONTEST_BY_ID, response = Contest.class)
    @GetMapping("{id}")
    public ContestOutputDto getContestById(@PathVariable int id) {
        return contestModelMapper.contestToDto(contestService.getContestById(id));

    }

    @ApiOperation(value = ALL_CONTESTS, response = List.class)
    @GetMapping
    public List<ContestOutputDto> getAllContests() {
        return contestService.getAllContests().stream()
                .map(contestModelMapper::contestToDto)
                .collect(Collectors.toList());

    }

    @ApiOperation(value = ADD_USER_TO_CONTEST, response = User.class)
    @PutMapping("/{id}")
    public void addUserToContest(@PathVariable int id, @RequestParam int userId) {
        contestService.addUserToContest(id, userId);
    }

    @ApiOperation(value = CONTEST_FILTERS, response = List.class)
    @GetMapping("/filter")
    public List<ContestOutputDto> contestFilter(@RequestParam String filter) {
        return contestService.getFilteredContests(filter).stream()
                .map(contestModelMapper::contestToDto)
                .collect(Collectors.toList());
    }

    @ApiOperation(value = CONTEST_FILTERS_BY_USER_ID, response = List.class)
    @GetMapping("/filter/{userId}")
    public List<ContestOutputDto> contestFilterByUserId(@RequestParam String filter, @PathVariable int userId) {
        return contestService.getFilteredContestsByUserId(filter, userId).stream()
                .map(contestModelMapper::contestToDto)
                .collect(Collectors.toList());
    }
}
