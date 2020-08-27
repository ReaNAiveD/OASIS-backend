package com.nju.oasis.controller;

import com.nju.oasis.controller.VO.ResultVO;
import com.nju.oasis.controller.form.EnrichForm;
import com.nju.oasis.controller.form.EnrichMappingForm;
import com.nju.oasis.service.EnricherService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

@RestController
@RequestMapping("/enrich")
public class EnricherController {

    @Autowired
    EnricherService enricherService;

    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public ResultVO submitNewDocuments(@RequestPart(value = "mapping") EnrichMappingForm enrichForm,
                                       @RequestPart(value = "file") MultipartFile file){
        return enricherService.loadCSVFile(enrichForm, file);
    }

}
