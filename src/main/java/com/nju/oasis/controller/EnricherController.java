package com.nju.oasis.controller;

import com.nju.oasis.controller.VO.ResultVO;
import com.nju.oasis.controller.form.EnrichForm;
import com.nju.oasis.controller.form.EnrichMappingForm;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

@RestController
@RequestMapping("/enrich")
public class EnricherController {

    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public ResultVO submitNewDocuments(@RequestPart(value = "mapping") EnrichMappingForm enrichForm,
                                       @RequestPart(value = "file") MultipartFile file){
        CSVFormat format = CSVFormat.DEFAULT.withFirstRecordAsHeader().withDelimiter(',');
        try {
            Reader inputFile = new InputStreamReader(file.getInputStream());
            CSVParser parser = format.parse(inputFile);
            List<CSVRecord> records = parser.getRecords();
            for (CSVRecord record : records){
                System.out.println(record.get(enrichForm.getTitle().getCsvColumn()));
            }
            System.out.println(enrichForm.getTitle().getCsvColumn());
            return ResultVO.SUCCESS(null);
        }
        catch (IOException e){
            e.printStackTrace();
            return ResultVO.FAILED(null, "文件读取意外中断");
        }
    }

}
