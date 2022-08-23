package org.soundwhere.backend.dataset;

import org.soundwhere.backend.dataset.dto.DatasetGenDTO;
import org.soundwhere.backend.err.LogicError;
import org.soundwhere.backend.util.Res;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/dataset")
public class DatasetCont {

    private final DatasetService service;

    public DatasetCont(DatasetService service) {this.service = service;}

    @PostMapping("/gen")
    public Res generate(@RequestBody DatasetGenDTO datasetGenDTO) throws LogicError {
        System.out.println(datasetGenDTO);
        var result = service.generate(datasetGenDTO.specs);
        return Res.ok(result);
    }
}
