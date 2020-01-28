package com.here.mwv.controller;


import com.here.mwv.model.MultiVechileSequencingRequest;
import com.here.mwv.model.MultiWayPointSequenceResponse;
import com.here.mwv.service.MultiWaypointSequenceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Slf4j
@RestController
@RequestMapping("/api/getRoute")
public class MultiWaypointSequenceController {

    @Autowired
    private MultiWaypointSequenceService multiWaypointSequenceService;
    @GetMapping(value = "/", consumes = "application/json", produces = "application/json")
    public ResponseEntity<MultiWayPointSequenceResponse> get(@RequestBody MultiVechileSequencingRequest multiVechileSequencingRequest){
// log.info("Request Body ",multiVechileSequencingRequest);

        return ResponseEntity.ok(multiWaypointSequenceService.getMultiWayPointSeqenceResponsne(multiVechileSequencingRequest));

    }
}