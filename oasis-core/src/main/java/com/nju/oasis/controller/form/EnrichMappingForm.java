package com.nju.oasis.controller.form;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class EnrichMappingForm {

    public static class MappingInfo {
        private String csvColumn;
        private String split;

        public String getCsvColumn() {
            return csvColumn;
        }

        public void setCsvColumn(String csvColumn) {
            this.csvColumn = csvColumn;
        }

        public String getSplit() {
            return split;
        }

        public void setSplit(String split) {
            this.split = split;
        }
    }

    private MappingInfo title;

    @JsonAlias("author.name")
    private MappingInfo authorName;

    @JsonAlias("author.author_affiliation")
    private MappingInfo authorAffiliation;

    @JsonAlias("publication_title")
    private MappingInfo publicationTitle;

    @JsonAlias("publication_year")
    private MappingInfo publicationYear;

    private MappingInfo volume;

    @JsonAlias("start_page")
    private MappingInfo startPage;

    @JsonAlias("end_page")
    private MappingInfo endPage;

    @JsonAlias("abstract")
    private MappingInfo abstractIntro;

    private MappingInfo doi;

    @JsonAlias("pdf_link")
    private MappingInfo pdfLink;

    private MappingInfo keywords;

    private MappingInfo publisher;

    @JsonAlias("document_identifier")
    private MappingInfo documentIdentifier;

    @JsonAlias("total_download")
    private MappingInfo totalDownload;

    @JsonAlias("total_citations")
    private MappingInfo totalCitations;

}
