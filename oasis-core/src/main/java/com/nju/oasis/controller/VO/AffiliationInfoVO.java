package com.nju.oasis.controller.VO;

import lombok.Data;

@Data
public class AffiliationInfoVO {

    String name;
    String homePageLink;
    String logoLink;
    String introduction;
    int authorCount;
    int docCount;
    int citationCount;

}
