package com.rocket.laf.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TermsDto {
    
    private Date tDate;
    private String tVersion;
    private String tPolicyVersion;
    private String tPolicyTitle;
    private String tPolicyContent;
    private String tLocationVersion;
    private String tLocationTitle;
    private String tLocationContent;
    private String tPrivacyVersion;
    private String tPrivacyTitle;
    private String tPrivacyContent;

}